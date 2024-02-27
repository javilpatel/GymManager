package fitness;

public class Profile implements Comparable<Profile> {
    private String fname;
    private String lname;
    private Date dob;

    public Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public Date getDob() {
        return dob;
    }

    @Override
    public int compareTo(Profile other) {
        int lastNameComparison = lname.compareTo(other.lname);
        if (lastNameComparison != 0) {
            return lastNameComparison;
        }
        int firstNameComparison = fname.compareTo(other.fname);
        if (firstNameComparison != 0) {
            return firstNameComparison;
        }
        return dob.compareTo(other.dob);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Profile other = (Profile) obj;
        return fname.equalsIgnoreCase(other.fname) && lname.equalsIgnoreCase(other.lname) && dob.equals(other.dob);
    }

    @Override
    public String toString() {
        return fname + ":" + lname + ":" + dob.toString();
    }
}


