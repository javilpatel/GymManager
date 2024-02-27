package fitness;

/**
 * Represents a generic member of the fitness center.
 * This class provides the basic properties and functionalities
 * for a fitness center member, including profile, membership expiration,
 * and home studio location.
 */
public class Member implements Comparable<Member> {
    private Profile profile;
    private Date expire;
    private Location homeStudio;

    /**
     * Constructs a new Member with specified profile, expiration date, and home studio.
     *
     * @param profile    The profile of the member.
     * @param expire     The expiration date of the membership.
     * @param homeStudio The home studio of the member.
     */
    public Member(Profile profile, Date expire, Location homeStudio) {
        this.profile = profile;
        this.expire = expire;
        this.homeStudio = homeStudio;
    }

    public Profile getProfile() {
        return profile;
    }

    public Date getExpire() {
        return expire;
    }

    public Location getHomeStudio() {
        return homeStudio;
    }
    /**
     * Calculates and returns the billing amount for the member.
     * This method should be overridden by subclasses to provide specific billing calculations.
     *
     * @return The billing amount due for the member.
     */
    public double bill() {
        // Return the next due amount
        return 0.0;
    }

    @Override
    public int compareTo(Member other) {
        return this.profile.compareTo(other.profile);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Member other = (Member) obj;
        return profile.equals(other.profile); // Assuming profile is the Profile object in Member
    }

    @Override
    public String toString() {
        return profile.toString() + ", Membership expires " + expire + ", Home Studio: " + homeStudio;
    }
}

/**
 * Represents a basic member of the fitness center.
 * Basic members have a set number of classes they can attend each month,
 * and their billing is calculated based on the base fee and any extra classes attended.
 */
class Basic extends Member {
    private int numClasses;

    /**
     * Constructs a new Basic member with specified profile, expiration, home studio, and number of classes.
     *
     * @param profile     The profile of the member.
     * @param expire      The expiration date of the membership.
     * @param homeStudio  The home studio of the member.
     * @param numClasses  The number of classes attended by the member.
     */
    public Basic(Profile profile, Date expire, Location homeStudio, int numClasses) {
        super(profile, expire, homeStudio);
        this.numClasses = numClasses;
    }

    @Override
    public String toString() {
        return super.toString() + ", (Basic) number of classes attended: " + numClasses;
    }

    @Override
    public double bill() {
        double baseFee = 39.99;
        int freeClasses = 4;
        double extraClassFee = 10.00;
        int extraClasses = Math.max(0, numClasses - freeClasses);
        return baseFee + extraClassFee * extraClasses;
    }
}

/**
 * Represents a family member of the fitness center.
 * Family members may have guest privileges and their billing is calculated
 * over a specified billing cycle.
 */
class Family extends Member {
    private boolean guest;

    /**
     * Constructs a new Family member with specified profile, expiration,
     * home studio, and guest privilege indicator.
     *
     * @param profile     The profile of the member.
     * @param expire      The expiration date of the membership.
     * @param homeStudio  The home studio of the member.
     * @param guest       Indicates whether the member has guest privileges.
     */
    public Family(Profile profile, Date expire, Location homeStudio, boolean guest) {
        super(profile, expire, homeStudio);
        this.guest = guest;
    }

    @Override
    public String toString() {
        String guestStatus = guest ? "guest-pass remaining: 1" : "guest-pass remaining: not eligible";
        return super.toString() + ", (Family) " + guestStatus;
    }

    @Override
    public double bill() {
        double baseFee = 49.99;
        int billingCycleMonths = 3;
        return baseFee * billingCycleMonths;
    }
}

/**
 * Represents a premium member of the fitness center.
 * Premium members receive a certain number of guest passes and
 * their billing is calculated with potential discounts for long-term commitments.
 */
class Premium extends Member {
    private int guestPass;

    public Premium(Profile profile, Date expire, Location homeStudio, int guestPass) {
        super(profile, expire, homeStudio);
        this.guestPass = guestPass;
    }
    public int getGuestPass() {
        return guestPass;
    }

    // Method to use a guest pass
    public void useGuestPass() {
        if (this.guestPass > 0) {
            this.guestPass--;
        } else {
            System.out.println("No guest passes left.");
        }
    }

    public void addGuestPass(){
        if( this.guestPass <= 2){
            this.guestPass++;
        }
    }

    @Override
    public String toString() {
        String guestStatus = guestPass > 0 ? "guest-pass remaining: " + guestPass : "guest-pass remaining: not eligible";
        return super.toString() + ", (Premium) " + guestStatus;
    }

    @Override
    public double bill() {
        double baseFee = 59.99;
        int freeMonth = 1;
        int billingCycleMonths = 12 - freeMonth;
        return baseFee * billingCycleMonths;
    }

}
