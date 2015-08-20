package fieldeducation.fashioninsta;

public class ListItemActivity {
    private String code;
    private String word;

    public ListItemActivity(String code, String word)
    {
        this.code = code;
        this.word = word;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String toString()
    {
        return this.code + ", " + this.word;
    }
}
