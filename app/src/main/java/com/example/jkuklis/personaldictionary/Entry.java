package com.example.jkuklis.personaldictionary;

public class Entry {

    private int id;
    private int dictId;
    private int pos;

    public Entry() {
    }

    public Entry(int dictId, int pos) {
        this.dictId = dictId;
        this.pos = pos;
    }

    public Entry(int id, int dictId, int pos) {
        this.id = id;
        this.dictId = dictId;
        this.pos = pos;
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

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
