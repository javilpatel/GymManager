package fitness;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MemberList {
    private static final int NOT_FOUND = -1;
    private static final int GROW_SIZE = 4;
    private Member[] members;
    private int size;

    public MemberList() {
        this.members = new Member[GROW_SIZE];
        this.size = 0;
    }

    private int find(Member member) {
        for (int i = 0; i < size; i++) {
            if (members[i].equals(member)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    public Member findMember(Profile profile) {
        for (int i = 0; i < size; i++) {
            if (members[i].getProfile().equals(profile)) {
                return members[i];
            }
        }
        return null; // Member not found
    }

    private void grow() {
        Member[] newMembers = new Member[members.length + GROW_SIZE];
        for (int i = 0; i < size; i++) {
            newMembers[i] = members[i];
        }
        members = newMembers;
    }

    public boolean contains(Member member) {
        for (Member m : members) {
            if (m != null && m.getProfile().equals(member.getProfile())) {
                return true;
            }
        }
        return false;
    }


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
                    member = new Family(profile, expire, homeStudio, false); // Assuming no guest for Family members
                    break;
                case "P":
                    member = new Premium(profile, expire, homeStudio, 0); // Assuming 0 guest passes for Premium members
                    break;
            }
            if (member != null) {
                add(member);
            }
        }
        scanner.close();
    }


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

    public void printFees() {
        for (int i = 0; i < size; i++) {
            System.out.printf("%s, Fee: %.2f%n", members[i], members[i].bill());
        }
    }
    public void printAllMembers() {
        for (int i = 0; i < this.size; i++) {
            if (this.members[i] != null) {
                System.out.println(this.members[i].toString());
            }
        }
    }

}




