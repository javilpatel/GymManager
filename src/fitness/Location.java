package fitness;

/**
 * Enumerates the locations where fitness classes can be held,
 * including information about their zip codes and counties.
 */
public enum Location {
    /**
     * Bridgewater location with its zip code and county.
     */
    BRIDGEWATER("08807", "Somerset County"),
    /**
     * Edison location with its zip code and county.
     */
    EDISON("08837", "Middlesex County"),
    /**
     * Franklin location with its zip code and county.
     */
    FRANKLIN("08873", "Somerset County"),
    /**
     * Piscataway location with its zip code and county.
     */
    PISCATAWAY("08854", "Middlesex County"),
    /**
     * Somerville location with its zip code and county.
     */
    SOMERVILLE("08876", "Somerset County");

    private final String zipCode;
    private final String county;

    /**
     * Constructs a Location enum with specified zip code and county.
     *
     * @param zipCode The zip code of the location.
     * @param county The county where the location is situated.
     */
    Location(String zipCode, String county) {
        this.zipCode = zipCode;
        this.county = county;
    }

    /**
     * Returns the zip code of the location.
     *
     * @return The zip code of this location.
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Returns the county of the location.
     *
     * @return The county of this location.
     */
    public String getCounty() {
        return county;
    }

    /**
     * Provides a string representation of the location,
     * including its name, zip code, and county.
     *
     * @return A string representation of the location.
     */
    @Override
    public String toString() {
        return this.name() + " " + zipCode + " " + county;
    }
}




