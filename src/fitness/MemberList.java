package fitness;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MemberList {
    //Constants for not found index and array growth size
    private static final int NOT_FOUND = -1;
    private static final int GROW_SIZE = 4;

    // Array to store member objects
    private Member[] members;
    // Current number of members in the list
    private int size;

    /**
     * Constructor to initialize the MemberList with an initial capacity.
     */
    public MemberList() {
        this.members = new Member[GROW_SIZE];
        this.size = 0;
    }

    /**
     * Finds the index of a given member in the list.
     *
     * @param member The member to find.
     * @return The index of the member if found, otherwise -1.
     */
    private int find(Member member) {
        for (int i = 0; i < size; i++) {
            if (members[i].equals(member)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Finds a member by their profile.
     *
     * @param profile The profile to search for.
     * @return The member with the given profile if found, null otherwise.
     */
    public Member findMember(Profile profile) {
        for (int i = 0; i < size; i++) {
            if (members[i].getProfile().equals(profile)) {
                return members[i];
            }
        }
        return null; // Member not found
    }

    /**
     * Increases the capacity of the members array when needed.
     */
    private void grow() {
        Member[] newMembers = new Member[members.length + GROW_SIZE];
        for (int i = 0; i < size; i++) {
            newMembers[i] = members[i];
        }
        members = newMembers;
    }

    /**
     * Checks if a member exists in the list.
     *
     * @param member The member to check.
     * @return true if the member exists, false otherwise.
     */
    public boolean contains(Member member) {
        for (Member m : members) {
            if (m != null && m.getProfile().equals(member.getProfile())) {
                return true;
            }
        }
        return false;
    }

    public boolean addGuest(Member member) {
        if (size == members.length) {
            grow();
        }
        members[size++] = member;
        return true;
    }

    /**
     * Adds a new member to the list.
     *
     * @param member The member to add.
     * @return true if the member was added successfully, false if the member already exists.
     */
    public boolean add(Member member) {
        if (contains(member)) {
            return false;
        }
        if (size == members.length) {
            grow();
        }
        members[size++] = member;
        return true;
    }

    /**
     * Removes a member from the list.
     *
     * @param member The member to remove.
     * @return true if the member was removed successfully, false if the member was not found.
     */
    public boolean remove(Member member) {
        int index = find(member);
        if (index == NOT_FOUND) {
            return false;
        }
        for (int i = index; i < size - 1; i++) {
            members[i] = members[i + 1];
        }
        members[--size] = null;
        return true;
    }

    /**
     * Loads members from a given file.
     *
     * @param file The file from which to load members.
     * @throws FileNotFoundException if the file cannot be found.
     */
    public void load(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] tokens = line.split(" ");
            Profile profile = new Profile(tokens[1], tokens[2], new Date(tokens[3]));
            Date expire = new Date(tokens[4]);
            Location homeStudio = Location.valueOf(tokens[5].toUpperCase());
            Member member = null;
            switch (tokens[0].trim().toUpperCase()) {
                case "B":
                    member = new Basic(profile, expire, homeStudio, 0); // Assuming 0 classes attended for Basic members
                    break;
                case "F":
                    member = new Family(profile, expire, homeStudio, true); // Assuming no guest for Family members
                    break;
                case "P":
                    member = new Premium(profile, expire, homeStudio, 3);
                    break;
            }
            if (member != null) {
                add(member);
            }
        }
        scanner.close();
    }

    /**
     * Sorts and prints members by their county and zip code.
     */
    public void printByCounty() {
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                if (members[j].getHomeStudio().getCounty().compareTo(members[j + 1].getHomeStudio().getCounty()) > 0 ||
                        (members[j].getHomeStudio().getCounty().equals(members[j + 1].getHomeStudio().getCounty()) &&
                                members[j].getHomeStudio().getZipCode().compareTo(members[j + 1].getHomeStudio().getZipCode()) > 0)) {
                    Member temp = members[j];
                    members[j] = members[j + 1];
                    members[j + 1] = temp;
                }
            }
        }
        for (int i = 0; i < size; i++) {
            System.out.println(members[i]);
        }
    }

    /**
     * Sorts and prints members by their profile.
     */
    public void printByMember() {
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                if (members[j].getProfile().compareTo(members[j + 1].getProfile()) > 0) {
                    Member temp = members[j];
                    members[j] = members[j + 1];
                    members[j + 1] = temp;
                }
            }
        }
        for (int i = 0; i < size; i++) {
            System.out.println(members[i]);
        }
    }

    /**
     * Prints the membership fees for all members.
     */
    public void printFees() {
        for (int i = 0; i < size; i++) {
            System.out.printf("%s, Fee: %.2f%n", members[i], members[i].bill());
        }
    }

    /**
     * Returns the current size of the member list.
     *
     * @return The number of members in the list.
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns a string representation of all members in the list.
     *
     * @return A formatted string of all members.
     */
    public String getAllMembersAsString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.size; i++) {
            if (this.members[i] != null) {
                sb.append("   ").append(this.members[i].toString()).append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Prints all members in the list to the console.
     */
    public void printAllMembers() {
        for (int i = 0; i < this.size; i++) {
            if (this.members[i] != null) {
                System.out.println(this.members[i].toString());
            }
        }
    }

}




