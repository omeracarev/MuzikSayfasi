package org.newest.msorgapp;

public class Favyaz {
    private int favyid;
    private String favyad;
    private String favyack;
    private String favyimg;
    private String favykul;


    public Favyaz(int favyid, String favyad, String favyack, String favyimg, String favykul) {
        this.favyid = favyid;
        this.favyad = favyad;
        this.favyack = favyack;
        this.favyimg = favyimg;
        this.favykul = favykul;
    }

    public Favyaz() {
    }

    public int getFavyid() {
        return favyid;
    }

    @Override
    public String toString() {
        return favyid+">"+favyad+">"+favyack+">"+favyimg+">"+favykul;
    }

    public void setFavyid(int favyid) {
        this.favyid = favyid;
    }

    public String getFavyad() {
        return favyad;
    }

    public void setFavyad(String favyad) {
        this.favyad = favyad;
    }

    public String getFavyack() {
        return favyack;
    }

    public void setFavyack(String favyack) {
        this.favyack = favyack;
    }

    public String getFavykul() {
        return favykul;
    }

    public void setFavykul(String favykul) {
        this.favykul = favykul;
    }

    public String getFavyimg() {
        return favyimg;
    }

    public void setFavyimg(String favyimg) {
        this.favyimg = favyimg;
    }
}
