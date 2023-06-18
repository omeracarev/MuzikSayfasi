package org.newest.msorgapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // ARAMANOTA BAŞLAR
    public static final String ARAMA_NOTA = "ARAMA_NOTA";
    public static final String NOTA_ID = "NOTA_ID";
    public static final String NOTA_ADI = "NOTA_ADI";
    public static final String NOTA_URL = "NOTA_URL";
    public static final String NOTA_PDF = "NOTA_PDF";
    public static final String NOTA_SONG = "NOTA_SONG";

    // ARAMANOTA BAŞLAR
    public static final String ARAMA_NOTAG = "ARAMA_NOTAG";
    public static final String NOTA_IDG = "NOTA_IDG";
    public static final String NOTA_ADIG = "NOTA_ADIG";
    public static final String NOTA_URLG = "NOTA_URLG";
    public static final String NOTA_PDFG = "NOTA_PDFG";
    public static final String NOTA_SONGG = "NOTA_SONGG";


    //ARAMANOTA BİTER

    // SONEKLER BAŞLAR
    public static final String SONEKLER = "SONEKLER";
    public static final String POP_ID = "POP_ID";
    public static final String POP_AD = "POP_AD";
    public static final String POP_ALT = "POP_ALT";
    public static final String POP_IMG = "POP_IMG";
    public static final String POP_VIDURL = "POP_VIDURL";
    public static final String POP_PDF = "POP_PDF";

    //SONEKLER BİTER

    // EVERYSAN BAŞLAR
    public static final String EVERYSAN = "EVERYSAN";
    public static final String SAN_ID = "SAN_ID";
    public static final String SAN_AD = "SAN_AD";
    public static final String SAN_IMG = "SAN_IMG";
    public static final String SAN_ACK = "SAN_ACK";

    // EVERYSANG BAŞLAR
    public static final String EVERYSANG = "EVERYSANG";
    public static final String SAN_IDG = "SAN_IDG";
    public static final String SAN_ADG = "SAN_ADG";
    public static final String SAN_IMGG = "SAN_IMGG";
    public static final String SAN_ACKG = "SAN_ACKG";


    //EVERYSAN BİTER

    // SARPOPI BAŞLAR
    public static final String SARPOPI = "SARPOPI";
    public static final String SARK_ID = "SARK_ID";
    public static final String SARK_AD = "SARK_AD";
    public static final String SARK_URL = "SARK_URL";
    public static final String SARK_PDF = "SARK_PDF";
    public static final String SARK_IMG = "SARK_IMG";
    public static final String SARK_VIDURL = "SARK_VIDURL";

    //SARPOPİ BİTER

    // MSYAZLAR BAŞLAR
    public static final String MSYAZLAR = "MSYAZLAR";
    public static final String YAZ_ID = "YAZ_ID";
    public static final String YAZ_YAZAR = "YAZ_YAZAR";
    public static final String YAZ_ASYAZ = "YAZ_ASYAZ";
    public static final String YAZ_BAS = "YAZ_BAS";
    public static final String YAZ_TARIH = "YAZ_TARIH";
    public static final String YAZ_RES1 = "YAZ_RES1";
    public static final String YAZ_BACKBAN = "YAZ_BACKBAN";
    public static final String YAZ_BAS1 = "YAZ_BAS1";
    public static final String YAZ_PAR1 = "YAZ_PAR1";
    public static final String YAZ_BAS2 = "YAZ_BAS2";
    public static final String YAZ_PAR2 = "YAZ_PAR2";
    public static final String YAZ_RES2 = "YAZ_RES2";
    public static final String YAZ_BAS3 = "YAZ_BAS3";
    public static final String YAZ_PAR3 = "YAZ_PAR3";
    public static final String YAZ_RES3 = "YAZ_RES3";
    public static final String YAZ_BAS4 = "YAZ_BAS4";
    public static final String YAZ_PAR4 = "YAZ_PAR4";
    public static final String YAZ_RES4 = "YAZ_RES4";
    public static final String YAZ_BAS5 = "YAZ_BAS5";
    public static final String YAZ_PAR5 = "YAZ_PAR5";
    public static final String YAZ_RES5 = "YAZ_RES5";


    //MSYAZLAR BİTER

    //KULBI BAŞLAR
    public static final String KULBI = "KULBI";
    public static final String KUL_ID = "KUL_ID";
    public static final String KUL_AD = "KUL_AD";
    public static final String KUL_SIF = "KUL_SIF";
    public static final String KUL_EMAIL = "KUL_EMAIL";
    public static final String KUL_HATIR = "KUL_HATIR";

    //KULBİ BİTER

    //FAVSAR BAŞLAR
    public static final String FAVSAR = "FAVSAR";
    public static final String FAV_ID = "FAV_ID";
    public static final String FAV_AD = "FAV_AD";
    public static final String FAV_SANAD = "FAV_SANAD";
    public static final String FAV_VIDURL = "FAV_VIDURL";
    public static final String FAV_PDF = "FAV_PDF";
    public static final String FAV_RES = "FAV_RES";
    public static final String FAV_KUL = "FAV_KUL";

    //FAVSAR BİTER

    //FAVYAZ BAŞLAR
    public static final String FAVYAZ = "FAVYAZ";
    public static final String FAVY_ID = "FAVY_ID";
    public static final String FAVY_AD = "FAVY_AD";
    public static final String FAVY_ACK = "FAVY_ACK";
    public static final String FAVY_IMG = "FAVY_IMG";
    public static final String FAVY_KUL = "FAVY_KUL";


    //FAVYAZ BİTER

    //EKCALLAR BAŞLAR
    public static final String EKCALLAR = "EKCALLAR";
    public static final String EK_ID = "EK_ID";
    public static final String EK_AD = "EK_AD";
    public static final String EK_ACK = "EK_ACK";
    public static final String EK_VID = "EK_VID";
    public static final String EK_URL = "EK_URL";
    public static final String EK_KUL = "EK_KUL";



    //EKCALLAR BİTER

    //AYARLAR BAŞLAR

    public static final String AYARLAR = "AYARLAR";
    public static final String AYAR_ID = "AYAR_ID";
    public static final String AYAR_AD = "AYAR_AD";
    public static final String AYAR_DURUM = "AYAR_DURUM";

    //AYARLAR BİTER

    //BİLDİRİMLER BAŞLAR

    public static final String BILDIRIMLER = "BILDIRIMLER";
    public static final String BIL_ID = "BIL_ID";
    public static final String BIL_ACK = "BIL_ACK";
    public static final String BIL_ALT = "BIL_ALT";

    //BİLDİRİMLER BİTER

    //PDF KAYITLARI BAŞLAR

    public static final String PDFKAYLAR = "PDFKAYLAR";
    public static final String PDF_ID = "PDF_ID";
    public static final String PDF_AD = "PDF_AD";
    public static final String PDF_ACK = "PDF_ACK";
    public static final String PDF_X = "PDF_X";
    public static final String PDF_Y = "PDF_Y";
    public static final String PDF_PAINT = "PDF_PAINT";
    public static final String PDF_SIZE = "PDF_SIZE";

    //PDF KAYITLARI BİTER

    //İLGİLENİLEN SANATÇILAR BAŞLAR

    public static final String ILGSANLAR = "ILGSANLAR";
    public static final String ILGSAN_ID = "ILGSAN_ID";
    public static final String ILGSAN_AD = "ILGSAN_AD";


    //İLGİLENİLEN SANATÇILAR BİTER

    //Gonderilen Mesajlar Başlar

    public static final String DATABASE_NAME = "msapp.db";





    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    public static void deleteDatabase(Context mContext) {
        mContext.deleteDatabase(DATABASE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + ARAMA_NOTA + " (" + NOTA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NOTA_ADI + " TEXT, " + NOTA_URL + " TEXT, " + NOTA_PDF + " TEXT," + NOTA_SONG + " TEXT) ";
        db.execSQL(createTableStatement);

        String createTableStatement1g = "CREATE TABLE " + ARAMA_NOTAG + " (" + NOTA_IDG + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NOTA_ADIG + " TEXT, " + NOTA_URLG + " TEXT, " + NOTA_PDFG + " TEXT," + NOTA_SONGG + " TEXT) ";
        db.execSQL(createTableStatement1g);

       String createTableStatement2 = "CREATE TABLE " + SONEKLER + " (" + POP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + POP_AD + " TEXT, " + POP_ALT + " TEXT, " + POP_IMG + " TEXT, " + POP_VIDURL + " TEXT, " + POP_PDF + " TEXT) ";
        db.execSQL(createTableStatement2);

        String createTableStatement3 = "CREATE TABLE " + EVERYSAN + " (" + SAN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SAN_AD + " TEXT, " + SAN_ACK + " TEXT, " + SAN_IMG + " TEXT) ";
        db.execSQL(createTableStatement3);

        String createTableStatement3g = "CREATE TABLE " + EVERYSANG + " (" + SAN_IDG + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SAN_ADG + " TEXT, " + SAN_ACKG + " TEXT, " + SAN_IMGG + " TEXT) ";
        db.execSQL(createTableStatement3g);

        String createTableStatement4 = "CREATE TABLE " + SARPOPI + " (" + SARK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SARK_AD + " TEXT, " + SARK_URL + " TEXT, " + SARK_PDF + " TEXT," + SARK_IMG + " TEXT, " + SARK_VIDURL + " TEXT) ";
        db.execSQL(createTableStatement4);

        String createTableStatement5 = "CREATE TABLE " + MSYAZLAR + " (" + YAZ_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + YAZ_YAZAR + " TEXT, " + YAZ_ASYAZ + " TEXT, " + YAZ_BAS + " TEXT," + YAZ_TARIH + " TEXT, " + YAZ_RES1 + " TEXT, " + YAZ_BACKBAN + " TEXT, " + YAZ_BAS1 + " TEXT, " + YAZ_PAR1 + " TEXT, " + YAZ_BAS2 + " TEXT, " + YAZ_PAR2 + " TEXT, " + YAZ_RES2 + " TEXT, " + YAZ_BAS3 + " TEXT, " + YAZ_PAR3 + " TEXT, " + YAZ_RES3 + " TEXT, " + YAZ_BAS4 + " TEXT, " + YAZ_PAR4 + " TEXT, " + YAZ_RES4 + " TEXT, " + YAZ_BAS5 + " TEXT, " + YAZ_PAR5 + " TEXT, " + YAZ_RES5 +" TEXT ) ";
        db.execSQL(createTableStatement5);

        String createTableStatement6 = "CREATE TABLE " + KULBI + " (" + KUL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KUL_AD + " TEXT, " + KUL_SIF + " TEXT, " + KUL_EMAIL + " TEXT," + KUL_HATIR + " TEXT) ";
        db.execSQL(createTableStatement6);

        String createTableStatement7 = "CREATE TABLE " + FAVSAR + " (" + FAV_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FAV_AD + " TEXT, " + FAV_SANAD + " TEXT, " + FAV_VIDURL + " TEXT," + FAV_PDF + " TEXT," + FAV_RES + " TEXT, " + FAV_KUL + " TEXT) ";
        db.execSQL(createTableStatement7);

        String createTableStatement8 = "CREATE TABLE " + FAVYAZ + " (" + FAVY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FAVY_AD + " TEXT, " + FAVY_ACK + " TEXT," + FAVY_IMG + " TEXT, " + FAVY_KUL + " TEXT) ";
        db.execSQL(createTableStatement8);

        String createTableStatement9 = "CREATE TABLE " + EKCALLAR + " (" + EK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + EK_AD + " TEXT, " + EK_ACK + " TEXT, " + EK_VID + " TEXT, " + EK_URL + " TEXT, " + EK_KUL + " TEXT) ";
        db.execSQL(createTableStatement9);

        String createTableStatement10 = "CREATE TABLE " + AYARLAR + " (" + AYAR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + AYAR_AD + " TEXT, " + AYAR_DURUM + " TEXT) ";
        db.execSQL(createTableStatement10);

        String createTableStatement11 = "CREATE TABLE " + BILDIRIMLER + " (" + BIL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + BIL_ACK + " TEXT, " + BIL_ALT + " TEXT) ";
        db.execSQL(createTableStatement11);

        String createTableStatement12 = "CREATE TABLE " + PDFKAYLAR + " (" + PDF_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PDF_AD + " TEXT, " + PDF_ACK + " TEXT, " + PDF_X + " TEXT, " + PDF_Y + " TEXT," + PDF_PAINT + " TEXT," + PDF_SIZE + " TEXT) ";
        db.execSQL(createTableStatement12);

        String createTableStatement13 = "CREATE TABLE " + ILGSANLAR + " (" + ILGSAN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ILGSAN_AD + " TEXT) ";
        db.execSQL(createTableStatement13);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public boolean addaramanota(Notarama notarama){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NOTA_ADI,notarama.getNotaname());
        cv.put(NOTA_URL,notarama.getNotaurl());
        cv.put(NOTA_PDF,notarama.getNotapdf());
        cv.put(NOTA_SONG,notarama.getNotasong());
        long insert = db.insert(ARAMA_NOTA, null, cv);

        if (insert == -1){
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }

    }

    public boolean addaramanotag(Notarama notarama){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NOTA_ADIG,notarama.getNotaname());
        cv.put(NOTA_URLG,notarama.getNotaurl());
        cv.put(NOTA_PDFG,notarama.getNotapdf());
        cv.put(NOTA_SONGG,notarama.getNotasong());
        long insert = db.insert(ARAMA_NOTAG, null, cv);

        if (insert == -1){
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }

    }

    public boolean deletearamanotag(Notarama notarama){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM "+ARAMA_NOTAG+" WHERE "+ NOTA_IDG +" = "+ notarama.getId();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            cursor.close();
            db.close();
            return true;
        } else {
            cursor.close();
            db.close();
            return false;
        }
    }


    public boolean addpdfkay(Pdfkaylar pdfkaylar){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PDF_AD,pdfkaylar.getPdfad());
        cv.put(PDF_ACK,pdfkaylar.getPdfack());
        cv.put(PDF_X,pdfkaylar.getPdfx());
        cv.put(PDF_Y,pdfkaylar.getPdfy());
        cv.put(PDF_PAINT,pdfkaylar.getPdfpaint());
        cv.put(PDF_SIZE,pdfkaylar.getPdfsize());
        long insert = db.insert(PDFKAYLAR, null, cv);

        if (insert == -1){
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }
    }

    public boolean deletepdfkay(Pdfkaylar pdfkaylar){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM "+PDFKAYLAR+" WHERE "+ PDF_ID +" = "+ pdfkaylar.getPdfid();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            cursor.close();
            db.close();
            return true;
        } else {
            cursor.close();
            db.close();
            return false;
        }
    }

    public boolean addeverysan(Everysan everysan){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SAN_AD,everysan.getSanad());
        cv.put(SAN_ACK,everysan.getSanack());
        cv.put(SAN_IMG,everysan.getSanimg());
        long insert = db.insert(EVERYSAN, null, cv);

        if (insert == -1){
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }

    }

    public boolean addeverysang(Everysan everysan){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SAN_ADG,everysan.getSanad());
        cv.put(SAN_ACKG,everysan.getSanack());
        cv.put(SAN_IMGG,everysan.getSanimg());
        long insert = db.insert(EVERYSANG, null, cv);

        if (insert == -1){
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }

    }

    public boolean deleteverysang(Everysan everysan){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM "+EVERYSANG+" WHERE "+ SAN_IDG +" = "+ everysan.getSanid();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            cursor.close();
            db.close();
            return true;
        } else {
            cursor.close();
            db.close();
            return false;
        }
    }


    public boolean addsonekler(Sonekler sonekler){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(POP_AD,sonekler.getPopad());
        cv.put(POP_ALT,sonekler.getPopalt());
        cv.put(POP_VIDURL,sonekler.getPopvidurl());
        cv.put(POP_PDF,sonekler.getPoppdf());
        cv.put(POP_IMG,sonekler.getPopimg());
        long insert = db.insert(SONEKLER, null, cv);

        if (insert == -1){
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }

    }

    public boolean addsarpopi(Sarpopi sarpopi){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SARK_AD,sarpopi.getSarkad());
        cv.put(SARK_URL,sarpopi.getSarkurl());
        cv.put(SARK_PDF,sarpopi.getSarkpdf());
        cv.put(SARK_IMG,sarpopi.getSarkimg());
        cv.put(SARK_VIDURL,sarpopi.getSarkvidurl());
        long insert = db.insert(SARPOPI, null, cv);

        if (insert == -1){
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }

    }



   public boolean addkulbi(Kulbi kulbi) {
       SQLiteDatabase db = this.getWritableDatabase();
       ContentValues cv = new ContentValues();
       cv.put(KUL_AD,kulbi.getKulad());
       cv.put(KUL_SIF,kulbi.getKulsifre());
       cv.put(KUL_EMAIL,kulbi.getKulemail());
       cv.put(KUL_HATIR,kulbi.getKulhatir());

       long insert = db.insert(KULBI, null, cv);

       if (insert == -1){
           db.close();
           return false;
       } else {
           db.close();
           return true;
       }
   }

   public boolean addbildirimler(Bildirimler bildirimler){
       SQLiteDatabase db = this.getWritableDatabase();
       ContentValues cv = new ContentValues();
       cv.put(BIL_ACK,bildirimler.getBilack());
       cv.put(BIL_ALT,bildirimler.getBilalt());
       long insert = db.insert(BILDIRIMLER, null, cv);

       if (insert == -1){
           db.close();
           return false;
       } else {
           db.close();
           return true;
       }
   }

    public boolean addAyarlar(Ayarlar ayarlar){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(AYAR_AD,ayarlar.getAyarad());
        cv.put(AYAR_DURUM,ayarlar.getAyardurum());
        long insert = db.insert(AYARLAR, null, cv);

        if (insert == -1){
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }
    }

    public boolean addfavsar(Favsar favsar) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FAV_AD,favsar.getFavad());
        cv.put(FAV_SANAD,favsar.getFavsanad());
        cv.put(FAV_VIDURL,favsar.getFavvidurl());
        cv.put(FAV_PDF,favsar.getFavpdf());
        cv.put(FAV_RES,favsar.getFavres());
        cv.put(FAV_KUL,favsar.getFavkul());

        long insert = db.insert(FAVSAR, null, cv);

        if (insert == -1){
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }
    }

    public boolean addfavyaz(Favyaz favyaz) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FAVY_AD,favyaz.getFavyad());
        cv.put(FAVY_ACK,favyaz.getFavyack());
        cv.put(FAVY_IMG,favyaz.getFavyimg());
        cv.put(FAVY_KUL,favyaz.getFavykul());

        long insert = db.insert(FAVYAZ, null, cv);

        if (insert == -1){
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }
    }

    public boolean addekcallar(Ekcallar ekcallar) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(EK_AD,ekcallar.getEkad());
        cv.put(EK_ACK,ekcallar.getEkack());
        cv.put(EK_VID,ekcallar.getEkvid());
        cv.put(EK_URL,ekcallar.getEkurl());
        cv.put(EK_KUL,ekcallar.getEkkul());


        long insert = db.insert(EKCALLAR, null, cv);

        if (insert == -1){
             db.close();
            return false;
        } else {
            db.close();
            return true;
        }
    }

    public boolean addilgsanlar(Ilgsanlar ilgsanlar){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ILGSAN_AD,ilgsanlar.getIlgsan_ad());

        long insert = db.insert(ILGSANLAR,null,cv);
        if (insert == -1){
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }
    }

    public boolean deleteilgsan(Ilgsanlar ilgsanlar){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM "+ILGSANLAR+" WHERE "+ ILGSAN_ID +" = "+ ilgsanlar.getIlgsan_id();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            cursor.close();
            db.close();
            return true;
        } else {
            cursor.close();
            db.close();
            return false;
        }
    }


    public boolean deleteekcal(Ekcallar ekcallar){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM "+EKCALLAR+" WHERE "+ EK_ID +" = "+ ekcallar.getEkid();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            cursor.close();
            db.close();
            return true;
        } else {
            cursor.close();
            db.close();
            return false;
        }
    }

    public boolean deletesonek(Sonekler sonekler){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM "+SONEKLER+" WHERE "+ POP_ID +" = "+ sonekler.getPopid();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            cursor.close();
            db.close();
            return true;
        } else {
            cursor.close();
            db.close();
            return false;
        }
    }

    public boolean deletefavsar(Favsar favsar){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM "+FAVSAR+" WHERE "+ FAV_ID +" = "+ favsar.getFavid();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            cursor.close();
            db.close();
            return true;
        } else {
            cursor.close();
            db.close();
            return false;
        }
    }

    public boolean deletefavyaz(Favyaz favyaz){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM "+FAVYAZ+" WHERE "+ FAVY_ID +" = "+ favyaz.getFavyid();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            cursor.close();
            db.close();
            return true;
        } else {
            cursor.close();
            db.close();
            return false;
        }
    }

    public List<Sarpopi> sarpopicek(){
        List<Sarpopi> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM "+ SARPOPI;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);

        if (cursor.moveToFirst()){
            do {
                int sarkid = cursor.getInt(0);
                String sarkad = cursor.getString(1);
                String sarkurl = cursor.getString(2);
                String sarkpdf = cursor.getString(3);
                String sarkimg = cursor.getString(4);
                String sarkvidurl = cursor.getString(5);

                Sarpopi sarpopi = new Sarpopi(sarkid,sarkad,sarkurl,sarkpdf,sarkimg,sarkvidurl);
                returnList.add(sarpopi);
            } while (cursor.moveToNext());
        } else {

        }

        cursor.close();
        db.close();

        return  returnList;
    }


    public boolean deleteayar(Ayarlar ayarlar){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM "+AYARLAR+" WHERE "+ AYAR_ID +" = "+ ayarlar.getAyarid();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            cursor.close();
            db.close();
            return true;
        } else {
            cursor.close();
            db.close();
            return false;
        }


    }

    public boolean deletekulbi(Kulbi kulbi){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM "+KULBI+" WHERE "+ KUL_ID +" = "+ kulbi.getKulid();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            cursor.close();
            db.close();
            return true;
        } else {
            cursor.close();
            db.close();
            return false;
        }


    }

    public boolean deletebildirim(Bildirimler bildirimler){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM "+BILDIRIMLER+" WHERE "+ BIL_ID +" = "+ bildirimler.getBilid();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            cursor.close();
            db.close();
            return true;
        } else {
            cursor.close();
            db.close();
            return false;
        }


    }

    public List<Ayarlar> ayarlarcek(){
        List<Ayarlar> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM "+ AYARLAR;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);

        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String ayarad = cursor.getString(1);
                String ayardurum = cursor.getString(2);

                Ayarlar newayarlar = new Ayarlar(id,ayarad,ayardurum);
                returnList.add(newayarlar);
            } while (cursor.moveToNext());
        } else {

        }

        cursor.close();
        db.close();

        return  returnList;

    }

    public List<Bildirimler> bildirimcek(){
        List<Bildirimler> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM "+ BILDIRIMLER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);

        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String bildirad = cursor.getString(1);
                String bildiralt = cursor.getString(2);

                Bildirimler newbildirim = new Bildirimler(id,bildirad,bildiralt);
                returnList.add(newbildirim);
            } while (cursor.moveToNext());
        } else {

        }

        cursor.close();
        db.close();

        return  returnList;

    }


    public List<Notarama> aramanota(){
        List<Notarama> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM "+ ARAMA_NOTA;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);

        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String notaname = cursor.getString(1);
                String notaurl = cursor.getString(2);
                String notapdf = cursor.getString(3);
                String notasong = cursor.getString(4);

                Notarama newnotarama = new Notarama(id,notaname,notaurl,notapdf,notasong);
                returnList.add(newnotarama);
            } while (cursor.moveToNext());
        } else {

        }

        cursor.close();
        db.close();

        return  returnList;
    }

    public List<Notarama> aramanotag(){
        List<Notarama> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM "+ ARAMA_NOTAG;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);

        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String notaname = cursor.getString(1);
                String notaurl = cursor.getString(2);
                String notapdf = cursor.getString(3);
                String notasong = cursor.getString(4);

                Notarama newnotarama = new Notarama(id,notaname,notaurl,notapdf,notasong);
                returnList.add(newnotarama);
            } while (cursor.moveToNext());
        } else {

        }

        cursor.close();
        db.close();

        return  returnList;
    }

    public List<Pdfkaylar> kaypdfcek(){
        List<Pdfkaylar> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM "+ PDFKAYLAR;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);

        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String pdfad = cursor.getString(1);
                String pdfack = cursor.getString(2);
                String pdfx = cursor.getString(3);
                String pdfy = cursor.getString(4);
                String pdfpaint = cursor.getString(5);
                String pdfsize = cursor.getString(6);

                Pdfkaylar pdfkaylar = new Pdfkaylar(id,pdfad,pdfack,pdfx,pdfy,pdfpaint,pdfsize);
                returnList.add(pdfkaylar);
            } while (cursor.moveToNext());
        } else {

        }

        cursor.close();
        db.close();

        return  returnList;
    }

    public List<Sonekler> sonekcek(){
        List<Sonekler> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM "+ SONEKLER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);

        if (cursor.moveToFirst()){
            do {
                int popid = cursor.getInt(0);
                String popad = cursor.getString(1);
                String popalt = cursor.getString(2);
                String popimg = cursor.getString(3);
                String popvidurl = cursor.getString(4);
                String poppdf = cursor.getString(5);

                Sonekler newsonek = new Sonekler(popid,popad,popalt,popimg,popvidurl,poppdf);
                returnList.add(newsonek);
            } while (cursor.moveToNext());
        } else {

        }

        cursor.close();
        db.close();

        return  returnList;
    }

    public List<Everysan> everysans(){
        List<Everysan> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM "+ EVERYSAN;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);

        if (cursor.moveToFirst()){
            do {
                int sanid = cursor.getInt(0);
                String sanad = cursor.getString(1);
                String sanack = cursor.getString(2);
                String sanimg = cursor.getString(3);

                Everysan everysan = new Everysan(sanid,sanad,sanack,sanimg);
                returnList.add(everysan);
            } while (cursor.moveToNext());
        } else {

        }

        cursor.close();
        db.close();

        return  returnList;
    }

    public List<Everysan> everysansg(){
        List<Everysan> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM "+ EVERYSANG;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);

        if (cursor.moveToFirst()){
            do {
                int sanid = cursor.getInt(0);
                String sanad = cursor.getString(1);
                String sanack = cursor.getString(2);
                String sanimg = cursor.getString(3);

                Everysan everysan = new Everysan(sanid,sanad,sanack,sanimg);
                returnList.add(everysan);
            } while (cursor.moveToNext());
        } else {

        }

        cursor.close();
        db.close();

        return  returnList;
    }



    public List<Kulbi> kulbicek(){
        List<Kulbi> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM "+ KULBI;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);

        if (cursor.moveToFirst()){
            do {
                int kulid = cursor.getInt(0);
                String kulad = cursor.getString(1);
                String kulsifre = cursor.getString(2);
                String email = cursor.getString(3);
                String hatir = cursor.getString(4);

                Kulbi kulbi = new Kulbi(kulid,kulad,kulsifre,email,hatir);
                returnList.add(kulbi);
            } while (cursor.moveToNext());
        } else {

        }

        cursor.close();
        db.close();

        return  returnList;
    }

    public List<Favsar> favsarcekdb(){
        List<Favsar> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM "+ FAVSAR;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);

        if (cursor.moveToFirst()){
            do {
                int favid = cursor.getInt(0);
                String favad = cursor.getString(1);
                String favsanad = cursor.getString(2);
                String favidurl = cursor.getString(3);
                String favpdf = cursor.getString(4);
                String favres = cursor.getString(5);
                String favkul = cursor.getString(6);

                Favsar favsar = new Favsar(favid,favad,favsanad,favidurl,favpdf,favres,favkul);
                returnList.add(favsar);
            } while (cursor.moveToNext());
        } else {

        }

        cursor.close();
        db.close();

        return  returnList;
    }

    public List<Favyaz> favyazcekdb(){
        List<Favyaz> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM "+ FAVYAZ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);

        if (cursor.moveToFirst()){
            do {
                int favid = cursor.getInt(0);
                String favad = cursor.getString(1);
                String favsanad = cursor.getString(2);
                String favidurl = cursor.getString(3);
                String favpdf = cursor.getString(4);

                Favyaz favyaz = new Favyaz(favid,favad,favsanad,favidurl,favpdf);
                returnList.add(favyaz);
            } while (cursor.moveToNext());
        } else {

        }

        cursor.close();
        db.close();

        return  returnList;
    }


    public List<Ekcallar> ekcalcek(){
        List<Ekcallar> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM "+ EKCALLAR;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);

        if (cursor.moveToFirst()){
            do {
                int ekid = cursor.getInt(0);
                String ekad = cursor.getString(1);
                String ekack = cursor.getString(2);
                String ekvid = cursor.getString(3);
                String ekurl = cursor.getString(4);
                String ekkul = cursor.getString(5);


                Ekcallar ekcallar = new Ekcallar(ekid,ekad,ekack,ekvid,ekurl,ekkul);
                returnList.add(ekcallar);
            } while (cursor.moveToNext());
        } else {

        }

        cursor.close();
        db.close();

        return  returnList;
    }

    public List<Ilgsanlar> ilgsancek(){
        List<Ilgsanlar> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM "+ ILGSANLAR;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);

        if (cursor.moveToFirst()){
            do {
                int ilgsanid = cursor.getInt(0);
                String ilgsanad = cursor.getString(1);


                Ilgsanlar ilgsanlar = new Ilgsanlar(ilgsanid,ilgsanad);
                returnList.add(ilgsanlar);
            } while (cursor.moveToNext());
        } else {

        }

        cursor.close();
        db.close();

        return  returnList;
    }

}
