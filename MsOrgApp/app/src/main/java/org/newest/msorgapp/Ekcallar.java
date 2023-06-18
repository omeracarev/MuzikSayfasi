package org.newest.msorgapp;

public class Ekcallar {
    private int ekid;
    private String ekad;
    private String ekack;
    private String ekvid;
    private String ekurl;
    private String ekkul;

    public Ekcallar(int ekid, String ekad, String ekack, String ekvid, String ekurl, String ekkul) {
        this.ekid = ekid;
        this.ekad = ekad;
        this.ekack = ekack;
        this.ekvid = ekvid;
        this.ekurl = ekurl;
        this.ekkul = ekkul;
    }

    public Ekcallar() {
    }

    @Override
    public String toString() {
        return ekid+">"+ekad+">"+ekack+">"+ekvid+">"+ekurl+">"+ekkul;
    }

    public int getEkid() {
        return ekid;
    }

    public void setEkid(int ekid) {
        this.ekid = ekid;
    }

    public String getEkad() {
        return ekad;
    }

    public void setEkad(String ekad) {
        this.ekad = ekad;
    }

    public String getEkack() {
        return ekack;
    }

    public void setEkack(String ekack) {
        this.ekack = ekack;
    }

    public String getEkvid() {
        return ekvid;
    }

    public void setEkvid(String ekvid) {
        this.ekvid = ekvid;
    }

    public String getEkurl() {
        return ekurl;
    }

    public void setEkurl(String ekurl) {
        this.ekurl = ekurl;
    }

    public String getEkkul() {
        return ekkul;
    }

    public void setEkkul(String ekkul) {
        this.ekkul = ekkul;
    }
}
