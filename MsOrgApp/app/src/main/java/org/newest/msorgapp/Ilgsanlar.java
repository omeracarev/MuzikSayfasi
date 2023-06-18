package org.newest.msorgapp;

public class Ilgsanlar {
  private int ilgsan_id;
  private String ilgsan_ad;

    public Ilgsanlar(){

    }

    @Override
    public String toString() {
        return ilgsan_id+">"+ilgsan_ad;
    }

    public Ilgsanlar(int ilgsan_id, String ilgsan_ad) {
        this.ilgsan_id = ilgsan_id;
        this.ilgsan_ad = ilgsan_ad;
    }

    public int getIlgsan_id() {
        return ilgsan_id;
    }

    public void setIlgsan_id(int ilgsan_id) {
        this.ilgsan_id = ilgsan_id;
    }

    public String getIlgsan_ad() {
        return ilgsan_ad;
    }

    public void setIlgsan_ad(String ilgsan_ad) {
        this.ilgsan_ad = ilgsan_ad;
    }


}
