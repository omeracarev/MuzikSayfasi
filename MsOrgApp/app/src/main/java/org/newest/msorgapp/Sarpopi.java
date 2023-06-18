package org.newest.msorgapp;

public class Sarpopi {

    private int sarkid;
    private String sarkad;
    private String sarkurl;
    private String sarkpdf;
    private String sarkimg;
    private String sarkvidurl;

    public Sarpopi(int sarkid, String sarkad, String sarkurl, String sarkpdf, String sarkimg, String sarkvidurl) {
        this.sarkid = sarkid;
        this.sarkad = sarkad;
        this.sarkurl = sarkurl;
        this.sarkpdf = sarkpdf;
        this.sarkimg = sarkimg;
        this.sarkvidurl = sarkvidurl;
    }

    public Sarpopi() {
    }

    @Override
    public String toString() {
        return sarkid+">"+sarkad+">"+sarkurl+">"+sarkpdf+">"+sarkimg+">"+sarkvidurl;
    }
    public int getSarkid() {
        return sarkid;
    }

    public void setSarkid(int sarkid) {
        this.sarkid = sarkid;
    }

    public String getSarkad() {
        return sarkad;
    }

    public void setSarkad(String sarkad) {
        this.sarkad = sarkad;
    }

    public String getSarkurl() {
        return sarkurl;
    }

    public void setSarkurl(String sarkurl) {
        this.sarkurl = sarkurl;
    }

    public String getSarkpdf() {
        return sarkpdf;
    }

    public void setSarkpdf(String sarkpdf) {
        this.sarkpdf = sarkpdf;
    }

    public String getSarkimg() {
        return sarkimg;
    }

    public void setSarkimg(String sarkimg) {
        this.sarkimg = sarkimg;
    }

    public String getSarkvidurl() {
        return sarkvidurl;
    }

    public void setSarkvidurl(String sarkvidurl) {
        this.sarkvidurl = sarkvidurl;
    }
}
