package org.newest.msorgapp;

public class Sonekler {
    private int popid;
    private String popad;
    private String popimg;
    private String popvidurl;
    private String poppdf;
    private String popalt;

    public Sonekler(int popid, String popad, String popimg, String popvidurl, String poppdf, String popalt) {
        this.popid = popid;
        this.popad = popad;

        this.popimg = popimg;
        this.popvidurl = popvidurl;
        this.poppdf = poppdf;
        this.popalt = popalt;
    }

    public Sonekler() {
    }

    @Override
    public String toString() {
        return popid+">"+popad+">"+popalt+">"+popimg+">"+popvidurl+">"+poppdf;
    }

    public int getPopid() {
        return popid;
    }

    public void setPopid(int popid) {
        this.popid = popid;
    }

    public String getPopad() {
        return popad;
    }

    public void setPopad(String popad) {
        this.popad = popad;
    }

    public String getPopimg() {
        return popimg;
    }

    public void setPopimg(String popimg) {
        this.popimg = popimg;
    }

    public String getPopvidurl() {
        return popvidurl;
    }

    public void setPopvidurl(String popvidurl) {
        this.popvidurl = popvidurl;
    }

    public String getPoppdf() {
        return poppdf;
    }

    public void setPoppdf(String poppdf) {
        this.poppdf = poppdf;
    }

    public String getPopalt() {
        return popalt;
    }

    public void setPopalt(String popalt) {
        this.popalt = popalt;
    }
}
