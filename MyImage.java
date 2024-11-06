public class MyImage {
    private String landmarkName;
    private String url;

    public MyImage(String landmarkName, String url) {
        this.landmarkName = landmarkName;
        this.url = url;
    }

    public String getLandmarkName() {
        return landmarkName;
    }

    public String getUrl() {
        return url;
    }
}
