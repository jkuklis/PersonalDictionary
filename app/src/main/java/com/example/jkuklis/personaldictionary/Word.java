package com.example.jkuklis.personaldictionary;

public class Word {

    private int id;
    private int entryId;
    private int pos;
    private String string;

    public Word() {
    }

    public Word(int id, int entryId, int pos, String string) {
        this.id = id;
        this.entryId = entryId;
        this.pos = pos;
        this.string = string;
    }

    public Word(int entryId, int pos, String string) {
        this.entryId = entryId;
        this.pos = pos;
        this.string = string;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEntryId() {
        return entryId;
    }

    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
