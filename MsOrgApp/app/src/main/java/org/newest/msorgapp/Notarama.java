package org.newest.msorgapp;

public class Notarama {
    private int id;
    private String notaname;
    private String notaurl;
    private String notapdf;
    private String notasong;

    public Notarama(int id, String notaname, String notaurl, String notapdf, String notasong) {
        this.id = id;
        this.notaname = notaname;
        this.notaurl = notaurl;
        this.notapdf = notapdf;
        this.notasong = notasong;
    }

    public Notarama(){

    }



    @Override
    public String toString() {
        return id+">"+notaname+">"+notaurl+">"+notapdf+">"+notasong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNotaname() {
        return notaname;
    }

    public void setNotaname(String notaname) {
        this.notaname = notaname;
    }

    public String getNotaurl() {
        return notaurl;
    }

    public void setNotaurl(String notaurl) {
        this.notaurl = notaurl;
    }

    public String getNotapdf() {
        return notapdf;
    }

    public void setNotapdf(String notapdf) {
        this.notapdf = notapdf;
    }

    public String getNotasong() {
        return notasong;
    }

    public void setNotasong(String notasong) {
        this.notasong = notasong;
    }
}
