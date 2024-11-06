public class MyHashTable {
    private DLList<Entry>[] table;
    private static final int SIZE = 26; // One entry per letter of the alphabet

    @SuppressWarnings("unchecked")
    public MyHashTable() {
        table = (DLList<Entry>[]) new DLList<?>[SIZE];
        for (int i = 0; i < SIZE; i++) {
            table[i] = new DLList<>();
        }
    }

    public void addCountry(Country country) {
        int index = getIndex(country.getAbbreviation());
        table[index].add(new Entry(country, new DLList<>()));
        System.out.println("Added country: " + country.getName() + " (" + country.getAbbreviation() + ")");
    }

    public void addImage(Country country, MyImage image) {
        int index = getIndex(country.getAbbreviation());
        for (Entry entry : table[index]) {
            if (entry.getCountry().equals(country)) {
                entry.getImages().add(image);
                System.out.println("Added image for " + country.getName() + ": " + image.getLandmarkName());
                return;
            }
        }
        // If the country was not found, add a new entry with the image
        DLList<MyImage> images = new DLList<>();
        images.add(image);
        table[index].add(new Entry(country, images));
    }

    public DLList<MyImage> getImages(Country country) {
        int index = getIndex(country.getAbbreviation());
        for (Entry entry : table[index]) {
            if (entry.getCountry().equals(country)) {
                return entry.getImages();
            }
        }
        return new DLList<>(); // Return an empty list if no images are found
    }

    public void removeImage(Country country, MyImage image) {
        int index = getIndex(country.getAbbreviation());
        for (Entry entry : table[index]) {
            if (entry.getCountry().equals(country)) {
                entry.getImages().remove(image);
                System.out.println("Removed image for " + country.getName() + ": " + image.getLandmarkName());
                return;
            }
        }
    }

    public void removeCountry(Country country) {
        int index = getIndex(country.getAbbreviation());
        DLList<Entry> entries = table[index];
        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).getCountry().equals(country)) {
                entries.remove(i);
                System.out.println("Removed country: " + country.getName() + " (" + country.getAbbreviation() + ")");
                break;
            }
        }
    }

    public DLList<Country> getCountries() {
        DLList<Country> countries = new DLList<>();
        for (DLList<Entry> entryList : table) {
            for (Entry entry : entryList) {
                countries.add(entry.getCountry());
            }
        }
        return countries;
    }

    private int getIndex(String abbreviation) {
        return abbreviation.toLowerCase().charAt(0) - 'a'; // Hash function based on first letter
    }

    private static class Entry {
        private Country country;
        private DLList<MyImage> images;

        public Entry(Country country, DLList<MyImage> images) {
            this.country = country;
            this.images = images;
        }

        public Country getCountry() {
            return country;
        }

        public DLList<MyImage> getImages() {
            return images;
        }
    }
}
