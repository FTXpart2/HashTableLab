public class Country {
    private String name;
    private String abbreviation;

    public Country(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation.toLowerCase(); // Ensure lowercase
    }

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    @Override
    public int hashCode() {
        return abbreviation.hashCode(); // Hash based on abbreviation
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Country)) return false;
        Country other = (Country) obj;
        return abbreviation.equals(other.abbreviation);
    }
}
