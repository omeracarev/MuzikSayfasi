package org.newest.msorgapp;

public class Ayarlar {
    private int ayarid;
    private String ayarad;
    private String ayardurum;

    public Ayarlar(int ayarid, String ayarad, String ayardurum) {
        this.ayarid = ayarid;
        this.ayarad = ayarad;
        this.ayardurum = ayardurum;
    }

    public Ayarlar(){

    }

    @Override
    public String toString() {
        return ayarid+">"+ayarad+">"+ayardurum;
    }

    public int getAyarid() {
        return ayarid;
    }

    public void setAyarid(int ayarid) {
        this.ayarid = ayarid;
    }

    public String getAyarad() {
        return ayarad;
    }

    public void setAyarad(String ayarad) {
        this.ayarad = ayarad;
    }

    public String getAyardurum() {
        return ayardurum;
    }

    public void setAyardurum(String ayardurum) {
        this.ayardurum = ayardurum;
    }
}
