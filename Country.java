public class Country {
    private String name;
    private String abbreviation;
    private MyImage[] images; // Array to hold images
    private int imageCount; // Number of images currently stored

    public Country(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.images = new MyImage[10]; // Limit to 10 images
        this.imageCount = 0;
    }

    public void addImage(MyImage image) {
        if (imageCount < images.length) {
            images[imageCount++] = image; // Add image and increment count
        }
    }

    public MyImage getImage(int index) {
        if (index >= 0 && index < imageCount) {
            return images[index];
        }
        return null;
    }

    public void removeImage(int index) {
        if (index >= 0 && index < imageCount) {
            // Shift images to the left
            for (int i = index; i < imageCount - 1; i++) {
                images[i] = images[i + 1];
            }
            images[--imageCount] = null; // Nullify the last image
        }
    }

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public int getImageCount() {
        return imageCount; // Return the number of images
    }

    public MyImage[] getImages() {
        return images; // Return the images array (up to the imageCount)
    }
}
