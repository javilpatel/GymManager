package fitness;

public enum Location {
    BRIDGEWATER("08807", "Somerset County"),
    EDISON("08837", "Middlesex County"),
    FRANKLIN("08873", "Somerset County"),
    PISCATAWAY("08854", "Middlesex County"),
    SOMERVILLE("08876", "Somerset County");

    private final String zipCode;
    private final String county;

    Location(String zipCode, String county) {
        this.zipCode = zipCode;
        this.county = county;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCounty() {
        return county;
    }

    @Override
    public String toString() {
        return this.name() + " " + zipCode + " " + county;
    }
}



