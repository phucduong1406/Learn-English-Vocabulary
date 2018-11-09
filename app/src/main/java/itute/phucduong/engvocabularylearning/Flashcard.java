package itute.phucduong.engvocabularylearning;

public class Flashcard {
    private String name;
    private String pronun;
    private String mean;
    private String image;

    public Flashcard() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Flashcard(String name, String pronun,String mean, String image) {
        this.name = name;
        this.pronun = pronun;
        this.mean = mean;
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPronun() {
        return pronun;
    }

    public void setPronun(String pronun) {
        this.pronun = pronun;
    }

}
