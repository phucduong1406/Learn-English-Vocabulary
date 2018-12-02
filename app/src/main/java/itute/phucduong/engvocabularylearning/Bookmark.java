package itute.phucduong.engvocabularylearning;

public class Bookmark {

    private String name;
    private String mean;

    public Bookmark(String name, String mean) {
        this.name = name;
        this.mean = mean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }
}