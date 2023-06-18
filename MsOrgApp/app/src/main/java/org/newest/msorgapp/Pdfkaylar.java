package org.newest.msorgapp;

public class Pdfkaylar {
    private int pdfid;
    private String pdfad;
    private String pdfack;
    private String pdfx;
    private String pdfy;
    private String pdfpaint;
    private String pdfsize;

    public String getPdfsize() {
        return pdfsize;
    }

    public void setPdfsize(String pdfsize) {
        this.pdfsize = pdfsize;
    }

    public int getPdfid() {
        return pdfid;
    }

    public void setPdfid(int pdfid) {
        this.pdfid = pdfid;
    }

    public String getPdfad() {
        return pdfad;
    }

    public void setPdfad(String pdfad) {
        this.pdfad = pdfad;
    }

    public String getPdfack() {
        return pdfack;
    }

    public void setPdfack(String pdfack) {
        this.pdfack = pdfack;
    }

    public String getPdfx() {
        return pdfx;
    }

    public void setPdfx(String pdfx) {
        this.pdfx = pdfx;
    }

    public String getPdfy() {
        return pdfy;
    }

    public void setPdfy(String pdfy) {
        this.pdfy = pdfy;
    }

    public String getPdfpaint() {
        return pdfpaint;
    }

    public void setPdfpaint(String pdfpaint) {
        this.pdfpaint = pdfpaint;
    }



    public Pdfkaylar(int pdfid, String pdfad, String pdfack, String pdfx, String pdfy, String pdfpaint,String pdfsize) {
        this.pdfid = pdfid;
        this.pdfad = pdfad;
        this.pdfack = pdfack;
        this.pdfx = pdfx;
        this.pdfy = pdfy;
        this.pdfpaint = pdfpaint;
        this.pdfsize = pdfsize;
    }

    public Pdfkaylar(){

    }

    @Override
    public String toString() {
        return pdfid+">"+pdfad+">"+pdfack+">"+pdfx+">"+pdfy+">"+pdfpaint+">"+pdfsize;
    }

}
