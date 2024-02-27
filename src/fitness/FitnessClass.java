package fitness;

/**
 * Represents a fitness class, including details about the offer, instructor, studio location, time,
 * enrolled members, and guests.
 */
public class FitnessClass {
    private Offer classInfo;
    private Instructor instructor;
    private Location studio;
    private Time time;
    private MemberList members;
    private MemberList guests;

    /**
     * Constructs a new FitnessClass with specified details.
     *
     * @param classInfo  Information about the class offer.
     * @param instructor The instructor leading the class.
     * @param studio     The location of the class.
     * @param time       The scheduled time for the class.
     */
    public FitnessClass(Offer classInfo, Instructor instructor, Location studio, Time time) {
        this.classInfo = classInfo;
        this.instructor = instructor;
        this.studio = studio;
        this.time = time;
        this.members = new MemberList();
        this.guests = new MemberList();
    }

    /**
     * Returns the class offer information.
     *
     * @return The Offer instance for this class.
     */
    public Offer getClassInfo() {
        return classInfo;
    }

    /**
     * Returns the instructor of the class.
     *
     * @return The Instructor instance for this class.
     */
    public Instructor getInstructor() {
        return instructor;
    }

    /**
     * Returns the studio location of the class.
     *
     * @return The Location instance for this class.
     */
    public Location getStudio() {
        return studio;
    }

    /**
     * Returns the scheduled time of the class.
     *
     * @return The Time instance for this class.
     */
    public Time getTime() {
        return time;
    }

    /**
     * Returns the list of members enrolled in the class.
     *
     * @return The MemberList instance containing enrolled members.
     */
    public MemberList getMembers() {
        return members;
    }

    /**
     * Returns the list of guests attending the class.
     *
     * @return The MemberList instance containing guests.
     */
    public MemberList getGuests() {
        return guests;
    }

    /**
     * Checks if the given member's home studio matches the class's studio.
     *
     * @param guest The member to check.
     * @return true if the member's home studio is the same as the class's studio, false otherwise.
     */
    public boolean isHomeStudio(Member guest) {
        return guest.getHomeStudio().equals(this.studio);
    }

    /**
     * Attempts to add a guest to the class if they have a guest pass and their home studio matches the class's studio.
     *
     * @param guest The guest to add.
     * @return true if the guest was successfully added, false otherwise.
     */
    public boolean addGuest(Member guest) {
        // Assuming each premium member has a limited number of guest passes.
        // Assuming guests are only allowed if the class is at their home studio.
        if (guest instanceof Premium && ((Premium) guest).getGuestPass() > 0
                && guest.getHomeStudio().equals(this.studio)) {
            // Assuming guests list is managed in a way similar to members.
            boolean added = guests.addGuest(guest);
            if (added) {
                ((Premium) guest).useGuestPass(); // method to decrement guest pass count
            }
            return added;
        }
        return false;
    }

    /**
     * Removes a guest from the class and refunds a guest pass.
     *
     * @param guest The guest to remove.
     * @return true if the guest was successfully removed, false otherwise.
     */
    public boolean removeGuest(Member guest) {
        ((Premium) guest).addGuestPass();
        return guests.remove(guest);
    }

    /**
     * Attempts to add a member to the class.
     *
     * @param member The member to add.
     * @return true if the member was successfully added, false otherwise.
     */
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

    /**
     * Removes a member from the class.
     *
     * @param member The member to remove.
     * @return true if the member was successfully removed, false otherwise.
     */
    public boolean removeMember(Member member) {
        return members.remove(member);
    }

    /**
     * Returns a string representation of the fitness class, including class information, instructor, time, and studio.
     *
     * @return A string representation of the fitness class.
     */
    @Override
    public String toString() {
        return String.format("%s - %s, %s, %s", classInfo, instructor, time, studio.name());
    }

}



