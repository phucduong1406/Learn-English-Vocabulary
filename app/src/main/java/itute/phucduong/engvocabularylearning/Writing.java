package itute.phucduong.engvocabularylearning;

public class Writing {
    private String name;

    public Writing() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Writing(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
