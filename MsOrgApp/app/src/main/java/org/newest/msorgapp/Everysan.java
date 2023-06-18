package org.newest.msorgapp;

public class Everysan {
    private int sanid;
    private String sanad;
    private String sanack;
    private String sanimg;

    public Everysan(int sanid, String sanad, String sanack, String sanimg) {
        this.sanid = sanid;
        this.sanad = sanad;
        this.sanack = sanack;
        this.sanimg = sanimg;
    }

    @Override
    public String toString() {
        return sanid+">"+sanad+">"+sanack+">"+sanimg;
    }

    public Everysan() {
    }



    public int getSanid() {
        return sanid;
    }

    public void setSanid(int sanid) {
        this.sanid = sanid;
    }

    public String getSanad() {
        return sanad;
    }

    public void setSanad(String sanad) {
        this.sanad = sanad;
    }

    public String getSanack() {
        return sanack;
    }

    public void setSanack(String sanack) {
        this.sanack = sanack;
    }

    public String getSanimg() {
        return sanimg;
    }

    public void setSanimg(String sanimg) {
        this.sanimg = sanimg;
    }
}
