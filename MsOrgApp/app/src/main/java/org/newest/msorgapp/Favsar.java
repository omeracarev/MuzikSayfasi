package org.newest.msorgapp;

public class Favsar {
    private int favid;
    private String favad;
    private String favsanad;
    private String favvidurl;
    private String favpdf;
    private String favres;
    private String favkul;

    public Favsar(int favid, String favad, String favsanad, String favvidurl, String favpdf, String favres, String favkul) {
        this.favid = favid;
        this.favad = favad;
        this.favsanad = favsanad;
        this.favvidurl = favvidurl;
        this.favpdf = favpdf;
        this.favres = favres;
        this.favkul = favkul;
    }

    public Favsar() {
    }

    public int getFavid() {
        return favid;
    }

    @Override
    public String toString() {
        return favid+">"+favad+">"+favsanad+">"+favvidurl+">"+favpdf+">"+favres+">"+favkul;
    }

    public void setFavid(int favid) {
        this.favid = favid;
    }

    public String getFavad() {
        return favad;
    }

    public void setFavad(String favad) {
        this.favad = favad;
    }

    public String getFavsanad() {
        return favsanad;
    }

    public void setFavsanad(String favsanad) {
        this.favsanad = favsanad;
    }

    public String getFavvidurl() {
        return favvidurl;
    }

    public void setFavvidurl(String favvidurl) {
        this.favvidurl = favvidurl;
    }

    public String getFavpdf() {
        return favpdf;
    }

    public void setFavpdf(String favpdf) {
        this.favpdf = favpdf;
    }

    public String getFavres() {
        return favres;
    }

    public void setFavres(String favres) {
        this.favres = favres;
    }

    public String getFavkul() {
        return favkul;
    }

    public void setFavkul(String favkul) {
        this.favkul = favkul;
    }
}
