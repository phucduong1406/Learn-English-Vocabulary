package itute.phucduong.engvocabularylearning;

public class Topic {
    private String name;
    private String detail;
    // Image name (Without extension)
    private String image;
    private String icon;

    public Topic() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Topic(String name, String detail, String image, String icon) {
        this.name = name;
        this.detail = detail;
        this.image = image;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}
