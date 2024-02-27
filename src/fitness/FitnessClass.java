package fitness;

public class FitnessClass {
    private Offer classInfo;
    private Instructor instructor;
    private Location studio;
    private Time time;
    private MemberList members;
    private MemberList guests;

    public FitnessClass(Offer classInfo, Instructor instructor, Location studio, Time time) {
        this.classInfo = classInfo;
        this.instructor = instructor;
        this.studio = studio;
        this.time = time;
        this.members = new MemberList();
        this.guests = new MemberList();
    }

    public Offer getClassInfo() {
        return classInfo;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public Location getStudio() {
        return studio;
    }

    public Time getTime() {
        return time;
    }

    public MemberList getMembers() {
        return members;
    }

    public MemberList getGuests() {
        return guests;
    }
    public boolean addGuest(Member guest) {
        // Assuming each premium member has a limited number of guest passes.
        // Assuming guests are only allowed if the class is at their home studio.
        if (guest instanceof Premium && ((Premium) guest).getGuestPass() > 0
                && guest.getHomeStudio().equals(this.studio)) {
            // Assuming guests list is managed in a way similar to members.
            boolean added = guests.add(guest);
            if (added) {
                ((Premium) guest).useGuestPass(); // method to decrement guest pass count
            }
            return added;
        }
        return false;
    }
    public boolean removeGuest(Member guest) {
        return guests.remove(guest);
    }
    public boolean addMember(Member member) {
        // This logic assumes the members list can check for existing members.
        if (members.contains(member)) {
            // Member is already in the class, so we don't add them again.
            return false;
        } else if (member instanceof Basic && !member.getHomeStudio().equals(this.studio)) {
            // Basic member trying to attend a class at a different studio.
            return false;
        } else {
            // Add the member to the class.
            members.add(member);
            return true;
        }
    }
    public boolean removeMember(Member member) {
        return members.remove(member);
    }

    @Override
    public String toString() {
        return String.format("%s - %s, %s, %s", classInfo, instructor, time, studio.name());
    }

}



