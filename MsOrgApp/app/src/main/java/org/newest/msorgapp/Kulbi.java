package org.newest.msorgapp;

public class Kulbi {
    private int kulid;
    private String kulad;
    private String kulsifre;
    private String kulemail;
    private String kulhatir;

    public Kulbi(int kulid, String kulad, String kulsifre, String kulemail, String kulhatir) {
        this.kulid = kulid;
        this.kulad = kulad;
        this.kulsifre = kulsifre;
        this.kulemail = kulemail;
        this.kulhatir = kulhatir;
    }

    public Kulbi() {
    }

    public int getKulid() {
        return kulid;
    }

    @Override
    public String toString() {
        return kulid+">"+kulad+">"+kulsifre+">"+kulemail+">"+kulhatir;
    }

    public void setKulid(int kulid) {
        this.kulid = kulid;
    }

    public String getKulad() {
        return kulad;
    }

    public void setKulad(String kulad) {
        this.kulad = kulad;
    }

    public String getKulsifre() {
        return kulsifre;
    }

    public void setKulsifre(String kulsifre) {
        this.kulsifre = kulsifre;
    }

    public String getKulemail() {
        return kulemail;
    }

    public void setKulemail(String kulemail) {
        this.kulemail = kulemail;
    }

    public String getKulhatir() {
        return kulhatir;
    }

    public void setKulhatir(String kulhatir) {
        this.kulhatir = kulhatir;
    }
}
