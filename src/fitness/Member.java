package fitness;

public class Member implements Comparable<Member> {
    private Profile profile;
    private Date expire;
    private Location homeStudio;

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
        if (!(obj instanceof Member)) return false;
        Member other = (Member) obj;
        return profile.equals(other.profile) && expire.equals(other.expire) && homeStudio == other.homeStudio;
    }

    @Override
    public String toString() {
        return profile.toString() + ", Membership expires " + expire + ", Home Studio: " + homeStudio;
    }
}

// Basic class
class Basic extends Member {
    private int numClasses;

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

// Family class
class Family extends Member {
    private boolean guest;

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

// Premium class
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
