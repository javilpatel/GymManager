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
            memberList.printAllMembers();
            System.out.println("-end of list-\n");

            // Print loaded fitness classes
            System.out.println("-Fitness Class loaded-");
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
                // Display the class schedule
                schedule.displayClasses();
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
            System.out.println("Missing data tokens.");
            return;
        }

        String firstName = tokens[1];
        String lastName = tokens[2];
        String dobStr = tokens[3];
        Location homeStudio;

        // Validate date format and create Date object
        try {
            Date dob = new Date(dobStr); // Assumes Date constructor validates format

            if (!dob.isValid()) {
                System.out.println("DOB " + dob.toString() + ": invalid calendar date!");
                return;
            }
            if (dob.isFutureDate()) {
                System.out.println("DOB " + dob.toString() + ": cannot be today or a future date!");
                return;
            }
            if (dob.calculateAge() < 18) {
                System.out.println("DOB " + dob.toString() + ": must be 18 or older to join!");
                return;
            }

            // Attempt to parse the home studio location
            try {
                homeStudio = Location.valueOf(tokens[4].toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println(tokens[4] + ": invalid studio location!");
                return;
            }

            Profile profile = new Profile(firstName, lastName, dob);
            Date expire = calculateExpirationDate(membershipType);
            Member member = createMember(profile, expire, homeStudio, membershipType);

            if (memberList.contains(member)) {
                System.out.println(firstName + " " + lastName + " is already in the member database.");
                return;
            }

            memberList.add(member);
            System.out.println(firstName + " " + lastName + " added.");
        } catch (IllegalArgumentException e) {
            System.out.println("The date contains characters.");
        }
    }



    private void cancelMembership(String[] tokens) {
        if (tokens.length < 4) {
            System.out.println("Missing data tokens.");
            return;
        }

        String firstName = tokens[1];
        String lastName = tokens[2];
        String dobStr = tokens[3];

        // Attempt to parse the date, catching any parsing errors
        try {
            Date dob = new Date(dobStr); // Assuming this constructor throws an exception for invalid dates

            Profile profile = new Profile(firstName, lastName, dob);
            Member memberToCancel = memberList.findMember(profile);

            if (memberToCancel == null) {
                System.out.println(firstName + " " + lastName + " is not in the database.");
                return;
            } else {
                memberList.remove(memberToCancel);
                System.out.println(firstName + " " + lastName + " removed.");
            }
        } catch (Exception e) {
            System.out.println("The date contains characters.");
        }
    }


    private void takeAttendance(String[] tokens, boolean isGuest) {
        if (tokens.length < 5) { // Ensure there are enough tokens for a full command
            System.out.println("Missing data tokens.");
            return;
        }

        Offer classType;
        Instructor instructor;
        Location studio;

        try {
            classType = Offer.valueOf(tokens[1].toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(tokens[1] + " - class name does not exist.");
            return;
        }


        // Validate Instructor
        try {
            instructor = Instructor.valueOf(tokens[2].toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(tokens[2] + " - instructor does not exist.");
            return;
        }

        // Validate Location
        try {
            studio = Location.valueOf(tokens[3].toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(tokens[3] + " - invalid studio location.");
            return;
        }

        String firstName = tokens[4];
        String lastName = tokens[5];
        // Assuming constructor Date(String date) exists and works as intended
        Date dob = new Date(tokens[6]);

        Profile profile = new Profile(firstName, lastName, dob);
        Member member = memberList.findMember(profile);

        if (member == null) {
            System.out.println(firstName + " " + lastName + " " + tokens[6] + " is not in the member database.");
            return;
        }

        if (member.getExpire().compareTo(Date.today()) < 0) {
            System.out.println(firstName + " " + lastName + " " + tokens[6] + " Membership expired.");
            return;
        }

       FitnessClass fitnessClass1 = new FitnessClass(classType, instructor, studio, null);

        if(isValidClassName(schedule,fitnessClass1.getClassInfo().name())){
            if(isValidInstructor(schedule,fitnessClass1.getInstructor().name())){
                if(isValidLocation(fitnessClass1.getStudio().name())){
//                   Spinning by Davis does not exist at Edison
                    boolean validate = schedule.validLocation(fitnessClass1);
                    if (!validate) {
                        System.out.println(fitnessClass1.getClassInfo().name()+" by "+fitnessClass1.getInstructor().name()+" does not exist at "+fitnessClass1.getStudio().name().toString());
                        return;

                    }
                }else{
                    System.out.println(fitnessClass1.getStudio().name().toString()+" - invalid location.");
                    return;
                }
            }else{
                System.out.println(fitnessClass1.getInstructor().name()+" - instructor does not exist.");
                return;
            }
        }else{

            System.out.println(fitnessClass1.getClassInfo().name()+" - class name does not exist.");

            return;
        }















//        if (fitnessClass == null) {
//            System.out.println(classType + " - class name does not exist.");
//            return;
//        }
        FitnessClass fitnessClass = schedule.findClass(classType.name(), instructor, studio);

        if (fitnessClass.getMembers().contains(member)) {
            System.out.println(firstName + " " + lastName + " is already in the class.");
            return;
        }

        if (isGuest) {
            if (!fitnessClass.addGuest(member)) {
                System.out.println("Failed to add guest to the class.");
            } else {
                System.out.println(firstName + " " + lastName + " successfully added as a guest.");
            }
            return;
        }

        if (hasTimeConflict(member, fitnessClass)) {
            return;
        }

        if (member instanceof Basic && !member.getHomeStudio().equals(studio)) {
            System.out.println(firstName + " " + lastName + " is attending a class at " + studio.name() + " - [BASIC] home studio at " + member.getHomeStudio().name());
            return;
        }

        if (fitnessClass.addMember(member)) {
            System.out.println(firstName + " " + lastName + " attendance recorded for " + classType.name() + " at " + studio.name() + ", " + studio.getZipCode() + ", " + studio.getCounty());
        } else {
            System.out.println("Failed to add member to the class.");
        }
    }

    private boolean isValidClassName(Schedule classSchedule, String className){
        for(FitnessClass fitnessClass : classSchedule.getClasses()){
            if(fitnessClass!=null){
                if(fitnessClass.getClassInfo().name().equalsIgnoreCase(className)){
                    return true;
                }
            }
        }

        return false;

    }
    private boolean isValidInstructor(Schedule classSchedule, String instructorName){
        for(FitnessClass fitnessClass : classSchedule.getClasses()){
            if(fitnessClass!=null){

                if(fitnessClass.getInstructor().name().equalsIgnoreCase(instructorName)){
                    return true;
                }
            }
        }

        return false;

    }
    private boolean isValidLocation(String location){

        for(Location l: Location.values()){
            if(l.name().equalsIgnoreCase(location)){
                return true;
            }
        }

        return false;

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
    public boolean hasTimeConflict(Member member, FitnessClass newClass) {
        // Iterate through all classes to find any that the member is already registered for
        String fullName = member.getProfile().getFname() + " " + member.getProfile().getLname();

        for (FitnessClass existingClass : schedule.getClasses()) {
            if (existingClass != null && existingClass.getMembers().contains(member)) {
                // Here, you'd compare the enum values to decide if the times overlap
                // This is a placeholder logic. You'd need to replace it with actual comparison logic based on how your Time enum is structured
                if (areTimesOverlapping(existingClass.getTime(), newClass.getTime())) {
                    System.out.println("Time conflict - " + fullName + " is in another class held at " + newClass.getTime() + " - " + newClass.getInstructor() + ", " + existingClass.getTime() + ", " +newClass.getStudio().name());
                    return true; // Indicate there's a time conflict
                }
            }
        }
        return false; // No time conflict found
    }

    // This method needs to be defined by you to compare Time enum values meaningfully
    private boolean areTimesOverlapping(Time time1, Time time2) {
        // Assuming each class duration is 1 hour and 30 minutes for simplicity
        int durationMinutes = 90;

        int startMinutes1 = time1.getHour() * 60 + time1.getMinute();
        int endMinutes1 = startMinutes1 + durationMinutes;

        int startMinutes2 = time2.getHour() * 60 + time2.getMinute();
        int endMinutes2 = startMinutes2 + durationMinutes;

        // Check if time slots overlap
        return startMinutes1 < endMinutes2 && startMinutes2 < endMinutes1;
    }



}



