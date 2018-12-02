package itute.phucduong.engvocabularylearning;

public class Writing {
    private String name;
    private String image;

    public Writing() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Writing(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
