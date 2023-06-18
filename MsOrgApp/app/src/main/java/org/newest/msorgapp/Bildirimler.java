package org.newest.msorgapp;

public class Bildirimler {
    private int bilid;
    private String bilack;
    private String bilalt;

    public Bildirimler(int bilid, String bilack, String bilalt) {
        this.bilid = bilid;
        this.bilack = bilack;
        this.bilalt = bilalt;
    }

    public Bildirimler(){

    }

    @Override
    public String toString() {
        return bilid+">"+bilack+">"+bilalt;
    }

    public int getBilid() {
        return bilid;
    }

    public void setBilid(int bilid) {
        this.bilid = bilid;
    }

    public String getBilack() {
        return bilack;
    }

    public void setBilack(String bilack) {
        this.bilack = bilack;
    }

    public String getBilalt() {
        return bilalt;
    }

    public void setBilalt(String bilalt) {
        this.bilalt = bilalt;
    }
}
