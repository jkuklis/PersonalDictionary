public class DictionaryLanguage {

    private int id;
    private int dict_id;
    private int lang_no;
    private String lang_abbr;
    private String lang_name;

    public DictionaryLanguage(int id, int dict_id, int lang_no, String lang_abbr, String lang_name) {
        this.id = id;
        this.dict_id = dict_id;
        this.lang_no = lang_no;
        this.lang_abbr = lang_abbr;
        this.lang_name = lang_name;

    }

    public DictionaryLanguage(int dict_id, int lang_no, String lang_abbr, String lang_name) {
        this.dict_id = dict_id;
        this.lang_no = lang_no;
        this.lang_abbr = lang_abbr;
        this.lang_name = lang_name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDict_id(int dict_id) {
        this.dict_id = dict_id;
    }

    public void setLang_no(int lang_no) {
        this.lang_no = lang_no;
    }

    public void setLang_abbr(String lang_abbr) {
        this.lang_abbr = lang_abbr;
    }

    public void setLang_name(String lang_name) {
        this.lang_name = lang_name;
    }

    public int getId() {
        return id;
    }

    public int getDict_id() {
        return dict_id;
    }

    public int getLang_no() {
        return lang_no;
    }

    public String getLang_abbr() {
        return lang_abbr;
    }

    public String getLang_name() {
        return lang_name;
    }

}
