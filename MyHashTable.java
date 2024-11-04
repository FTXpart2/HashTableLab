public class MyHashTable {
    private DLList<Entry>[] table;
    private static final int SIZE = 26; // Assuming one entry per letter of the alphabet

    @SuppressWarnings("unchecked")
    public MyHashTable() {
        // Specify the generic type for DLList
        table = (DLList<Entry>[]) new DLList<?>[SIZE]; 
        for (int i = 0; i < SIZE; i++) {
            table[i] = new DLList<Entry>(); // Specify the generic type here as well
        }
    }

    public void addCountry(Country country) {
        int index = getIndex(country.getAbbreviation());
        table[index].add(new Entry(country, new DLList<MyImage>()));
    }

    public void addImage(Country country, MyImage image) {
        int index = getIndex(country.getAbbreviation());
        for (Entry entry : table[index]) {
            if (entry.getCountry().equals(country)) {
                entry.getImages().add(image);
                return;
            }
        }
    }

    public Country getCountry(String abbreviation) {
        int index = getIndex(abbreviation);
        for (Entry entry : table[index]) {
            if (entry.getCountry().getAbbreviation().equals(abbreviation)) {
                return entry.getCountry();
            }
        }
        return null;
    }

    public MyImage[] getImages(Country country) {
        int index = getIndex(country.getAbbreviation());
        for (Entry entry : table[index]) {
            if (entry.getCountry().equals(country)) {
                return entry.getImages().toArray(); // No arguments, assumes method to return an Object[]
            }
        }
        return new MyImage[0]; // No images found
    }

    public void removeImage(Country country, MyImage image) {
        int index = getIndex(country.getAbbreviation());
        for (Entry entry : table[index]) {
            if (entry.getCountry().equals(country)) {
                entry.getImages().remove(image);
                return;
            }
        }
    }
    public void removeCountry(String abbreviation) {
    int index = getIndex(abbreviation);
    DLList<Entry> entryList = table[index];
    for (int i = 0; i < entryList.size(); i++) {
        Entry entry = entryList.get(i);
        if (entry.getCountry().getAbbreviation().equals(abbreviation)) {
            entryList.remove(i); // Remove the entry if found
            return;
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
        return abbreviation.charAt(0) - 'a'; // Simple hash function
    }

    private class Entry {
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
