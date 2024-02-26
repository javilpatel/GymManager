package fitness;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Scanner;

public class StudioManager {
    private MemberList memberList;
    private Schedule schedule;

    public StudioManager() {
        this.memberList = new MemberList();
        this.schedule = new Schedule();
    }

    public void run() {
        try {
            memberList.load(new File("memberList.txt"));
            schedule.load(new File("classSchedule.txt"));

            // Print loaded members
            System.out.println("-Loaded Members-");
            memberList. printAllMembers();
            System.out.println("-end of list-\n");

            // Print loaded fitness classes
            System.out.println("-Loaded Fitness Classes-");
            System.out.println(schedule.printClasses());
            System.out.println("-end of class list-\n");

            System.out.println("Studio Manager is up and running...");

            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String input = scanner.nextLine().trim();
                if (input.equals("Q")) {
                    System.out.println("Studio Manager terminated.");
                    break;
                }
                processCommand(input);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("The date contains characters");
        }
    }



    private void processCommand(String command) {
        String[] tokens = command.split(" ");


        switch (tokens[0]) {
            case "AB":
                // Add a basic member
                addMember(tokens, "Basic");
                break;
            case "AF":
                // Add a family member
                addMember(tokens, "Family");
                break;
            case "AP":
                // Add a premium member
                addMember(tokens, "Premium");
                break;
            case "C":
                // Cancel membership
                cancelMembership(tokens);
                break;
            case "S":
                // Display class schedule
                System.out.println(schedule);
                break;
            case "PM":
                // Print members by profile
                memberList.printByMember();
                break;
            case "PC":
                // Print members by county
                System.out.println("-list of members sorted by county then zipcode-");
                memberList.printByCounty();
                System.out.println("-end of list-");
                break;
            case "PF":
                // Print members with fees
                memberList.printFees();
                break;
            case "R":
                // Take attendance for a member
                takeAttendance(tokens, false);
                break;
            case "U":
                // Remove a member from a class
                removeAttendance(tokens, false);
                break;
            case "RG":
                // Take attendance for a guest
                takeAttendance(tokens, true);
                break;
            case "UG":
                // Remove a guest from a class
                removeAttendance(tokens, true);
                break;
            default:
                System.out.println(tokens[0] + " is an invalid command!");
                break;
        }
    }

    private void addMember(String[] tokens, String membershipType) {

        if (tokens.length < 5) {
            System.out.println("Not enough data tokens.");
            return;
        }

        String firstName = tokens[1];
        String lastName = tokens[2];
        Date dob = new Date(tokens[3]); // This constructor needs to parse the date string.
        Location homeStudio;

        try {
            homeStudio = Location.valueOf(tokens[4].toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(tokens[4] + ": invalid studio location!");
            return;
        }

        Profile profile = new Profile(firstName, lastName, dob);
        Date expire = calculateExpirationDate(membershipType);
        Member member = createMember(profile, expire, homeStudio, membershipType);

        // Checking if the date is valid and not in the future
        if (!dob.isValid()) {
            System.out.println("DOB " + dob.toString() + ": invalid calendar date!");
            return;
        }

        if (dob.isFutureDate()) {
            System.out.println("DOB " + dob.toString() + ": cannot be today or a future date!");
            return;
        }

        // Check if the person is at least 18 years old
        if (dob.calculateAge() < 18) {
            System.out.println("DOB " + dob.toString() + ": must be 18 or older to join!");
            return;
        }

        if (memberList.contains(member)) {
            System.out.println(firstName + " " + lastName + " is already in the member database.");
            return;
        }

        memberList.add(member);
        System.out.println(firstName + " " + lastName + " added.");
    }


    private void cancelMembership(String[] tokens) {
        if (tokens.length < 4) {
            System.out.println("Invalid command. Not enough data tokens.");
            return;
        }

        String firstName = tokens[1];
        String lastName = tokens[2];
        Date dob = new Date(tokens[3]); // Assumes this constructor correctly parses the date string.

        Profile profile = new Profile(firstName, lastName, dob);
        Member memberToCancel = memberList.findMember(profile);

        if (memberToCancel == null) {
            System.out.println(firstName + " " + lastName + " is not in the database.");
        } else {
            if (memberList.remove(memberToCancel)) {
                System.out.println(firstName + " " + lastName + " removed.");
            }
        }
    }

    private void takeAttendance(String[] tokens, boolean isGuest) {
        if (tokens.length < 5) {
            System.out.println("Invalid command. Not enough data tokens.");
            return;
        }

        String classType = tokens[1];
        Instructor instructor;
        Location studio;
        try {
            instructor = Instructor.valueOf(tokens[2].toUpperCase());
            studio = Location.valueOf(tokens[3].toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid instructor or studio location.");
            return;
        }

        String firstName = tokens[4];
        String lastName = tokens[5];
        Date dob = new Date(tokens[6]);

        Profile profile = new Profile(firstName, lastName, dob);
        Member member = memberList.findMember(profile);

        if (member == null) {
            System.out.println("Member not found in the database.");
            return;
        }

        if (member.getExpire().compareTo(Date.today()) < 0) {
            System.out.println("Membership expired.");
            return;
        }

        FitnessClass fitnessClass = schedule.findClass(classType, instructor, studio);

        if (fitnessClass == null) {
            System.out.println("Class not found in the schedule.");
            return;
        }

        if (isGuest) {
            if (!fitnessClass.addGuest(member)) {
                System.out.println("Failed to add guest to the class.");
                return;
            }
        } else {
            if (!fitnessClass.addMember(member)) {
                System.out.println("Failed to add member to the class.");
                return;
            }
        }

        System.out.println("Attendance taken successfully.");
    }

    private void removeAttendance(String[] tokens, boolean isGuest) {
        if (tokens.length < 5) {
            System.out.println("Invalid command. Not enough data tokens.");
            return;
        }

        String classType = tokens[1];
        Instructor instructor;
        Location studio;
        try {
            instructor = Instructor.valueOf(tokens[2].toUpperCase());
            studio = Location.valueOf(tokens[3].toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid instructor or studio location.");
            return;
        }

        String firstName = tokens[4];
        String lastName = tokens[5];
        Date dob = new Date(tokens[6]);

        Profile profile = new Profile(firstName, lastName, dob);
        Member member = memberList.findMember(profile);

        if (member == null) {
            System.out.println("Member not found in the database.");
            return;
        }

        FitnessClass fitnessClass = schedule.findClass(classType, instructor, studio);

        if (fitnessClass == null) {
            System.out.println("Class not found in the schedule.");
            return;
        }

        if (isGuest) {
            if (!fitnessClass.removeGuest(member)) {
                System.out.println("Failed to remove guest from the class.");
                return;
            }
        } else {
            if (!fitnessClass.removeMember(member)) {
                System.out.println("Failed to remove member from the class.");
                return;
            }
        }

        System.out.println("Attendance removed successfully.");
    }

    // Inside StudioManager class
    private Date calculateExpirationDate(String membershipType) {
        Calendar cal = Calendar.getInstance();
        switch (membershipType) {
            case "Basic":
                cal.add(Calendar.MONTH, 1); // Basic membership expires in 1 month
                break;
            case "Family":
                cal.add(Calendar.MONTH, 3); // Family membership expires in 3 months
                break;
            case "Premium":
                cal.add(Calendar.YEAR, 1); // Premium membership expires in 1 year
                break;
        }
        // Assuming Date class has a constructor that accepts year, month, and day
        return new Date(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
    }

    private Member createMember(Profile profile, Date expire, Location homeStudio, String membershipType) {
        Member member;
        switch (membershipType) {
            case "Basic":
                member = new Basic(profile, expire, homeStudio, 0); // Assuming 0 classes attended initially for Basic members
                break;
            case "Family":
                member = new Family(profile, expire, homeStudio, false); // Assuming no guest for Family members
                break;
            case "Premium":
                member = new Premium(profile, expire, homeStudio, 0); // Assuming 0 guest passes initially for Premium members
                break;
            default:
                throw new IllegalArgumentException("Invalid membership type");
        }
        return member;
    }

}



