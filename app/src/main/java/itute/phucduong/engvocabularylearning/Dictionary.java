package itute.phucduong.engvocabularylearning;

public class Dictionary {

    public String word;
    public String mean;
    public String detail;
    public String topic;
    public Boolean favorite_word;
    public Boolean recent_word;
    public Boolean my_word;


    public Dictionary() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Dictionary(String word, String mean, String detail, String topic, Boolean favorite_word, Boolean recent_word, Boolean my_word) {
        this.word = word;
        this.mean = mean;
        this.detail = detail;
        this.topic = detail;
        this.favorite_word = favorite_word;
        this.recent_word = recent_word;
        this.my_word = my_word;
    }
}
