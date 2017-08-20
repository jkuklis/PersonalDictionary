public class Language {

    private int id;
    private int dictId;
    private int no;
    private String abbr;
    private String name;

    public Language() {
    }

    public Language(int id, int dictId, int no, String abbr, String name) {
        this.id = id;
        this.dictId = dictId;
        this.no = no;
        this.abbr = abbr;
        this.name = name;

    }

    public Language(int dictId, int no, String abbr, String name) {
        this.dictId = dictId;
        this.no = no;
        this.abbr = abbr;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDictId(int dictId) {
        this.dictId = dictId;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getDictId() {
        return dictId;
    }

    public int getNo() {
        return no;
    }

    public String getAbbr() {
        return abbr;
    }

    public String getName() {
        return name;
    }

}
