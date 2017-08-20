
public class WordDictionary {

    private int id;
    private int dict_lang_id;
    private int lang_pos;
    private String word;

    public WordDictionary(int id, int dict_lang_id, int lang_pos, String word) {
        this.id = id;
        this.dict_lang_id = dict_lang_id;
        this.lang_pos = lang_pos;
        this.word = word;
    }

    public WordDictionary(int dict_lang_id, int lang_pos, String word) {
        this.dict_lang_id = dict_lang_id;
        this.lang_pos = lang_pos;
        this.word = word;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDict_lang_id() {
        return dict_lang_id;
    }

    public void setDict_lang_id(int dict_lang_id) {
        this.dict_lang_id = dict_lang_id;
    }

    public int getLang_pos() {
        return lang_pos;
    }

    public void setLang_pos(int lang_pos) {
        this.lang_pos = lang_pos;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
