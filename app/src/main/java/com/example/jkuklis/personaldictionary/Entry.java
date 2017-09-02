package com.example.jkuklis.personaldictionary;

public class Entry {

    private int id;
    private int dictId;

    public Entry() {
    }

    public Entry(int dictId) {
        this.dictId = dictId;
    }

    public Entry(int id, int dictId, int pos) {
        this.id = id;
        this.dictId = dictId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDictId() {
        return dictId;
    }

    public void setDictId(int dictId) {
        this.dictId = dictId;
    }
}
