package org.newest.msorgapp;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class SplashScreen extends AppCompatActivity {
    ArrayList tumsarklar = new ArrayList<String>();
    public static final int PERMISSION_WRITE = 0;
    LinearLayout spscreen;
    ArrayList sarkadlar = new ArrayList<String>();
    ArrayList sarkurller = new ArrayList<String>();
    ArrayList tumsanlar = new ArrayList<String>();
    ArrayList sanimgler = new ArrayList<String>();
    ArrayList sarpopiler = new ArrayList<String>();
    String basarisiz = "Başarısız...";
    int a=0;
    DatabaseHelper databaseHelper;
    String[] rawayir;
  public static void deleteCache(Context context) {
    try {
      File dir = context.getCacheDir();
      deleteDir(dir);
    } catch (Exception e) {}
  }
  public static boolean deleteDir(File dir) {
    if (dir != null && dir.isDirectory()) {
      String[] children = dir.list();
      for (int i = 0; i < children.length; i++) {
        boolean success = deleteDir(new File(dir, children[i]));
        if (!success) {
          return false;
        }
      }
      return dir.delete();
    } else if(dir!= null && dir.isFile()) {
      return dir.delete();
    } else {
      return false;
    }
  }

  public void createFolders(Context context){
      File msapp = new File(context.getExternalFilesDir(null)+"/msapp");

      databaseHelper = new DatabaseHelper(this);
      ArrayList ekcalcek = (ArrayList) databaseHelper.ekcalcek();
      if (ekcalcek.size()<1){
        Ekcallar ekcallar = new Ekcallar(-1,"5","1","0","0","0");
        Boolean okey = databaseHelper.addekcallar(ekcallar);
        if (okey){
          Toast.makeText(context, "Hediyeler Tanımlandı...", Toast.LENGTH_SHORT).show();
        }
      }

      if (!msapp.exists()){
        msapp.mkdirs();
        File pdfler = new File(context.getExternalFilesDir(null)+"/msapp/pdfnews");
        if (!pdfler.exists()){
          pdfler.mkdirs();
        }
        File sanresler = new File(context.getExternalFilesDir(null)+"/msapp/sanresnew");
        if (!sanresler.exists()){
          sanresler.mkdirs();
        }

        File profimler = new File(context.getExternalFilesDir(null)+"/msapp/profotos");
        if (!profimler.exists()){
          profimler.mkdirs();
        }
      }
    }

    public boolean clearData (){
        Boolean booli = false;
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(SplashScreen.this);

            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            int mCurrentVersion = pInfo.versionCode;
            SharedPreferences mSharedPreferences = getSharedPreferences("app_name",  Context.MODE_PRIVATE);
            SharedPreferences.Editor mEditor = mSharedPreferences.edit();
            mEditor.apply();
            int last_version = mSharedPreferences.getInt("last_version", -1);
            if(last_version != mCurrentVersion)
            {
               //Versiyon kodu 10 ve büyükse verileri silme
                if (databaseHelper.aramanota().size()>0){
                    booli = true;
                } else  {
                    booli = false;
                }

            } else {

            }
            mEditor.putInt("last_version", mCurrentVersion);
            mEditor.commit();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            booli = false;

        }

        if (booli){
            return true;
        } else {
            return false;
        }

    }

    private String[] getAllRawResources() {
        Field fields[] = R.raw.class.getDeclaredFields() ;
        String[] names = new String[fields.length] ;
        ArrayList arrayList = new ArrayList<String>();

        try {
            for( int i=0; i<fields.length; i++ ) {
                Field f = fields[i] ;
                  names[i] = f.getName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return names ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.Theme_AppCompat_NoActionBar_Launcher2);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        rawayir = new String[]{"c2","db2","d2","eb2","e2","f2","gb2","g2","ab2","a2","bb2","b2","c3","db3","d3","eb3","e3","f3","gb3","g3","ab3","a3","bb3","b3","c4","db4","d4","eb4","e4","f4","gb4","g4","ab4","a4","bb4","b4","c5","db5","d5","eb5","e5","f5","gb5","g5","ab5","a5","bb5","b5","c6","db6","d6","eb6","e6","f6","gb6","g6","ab6","a6","bb6","b6","c7"};



      if (clearData()){
         databaseHelper = new DatabaseHelper(SplashScreen.this);
            databaseHelper.deleteDatabase(SplashScreen.this);
            deleteCache(SplashScreen.this);
            Toast.makeText(this, "Uygulama Güncellendi, Tekrar Başlatın!", Toast.LENGTH_SHORT).show();
            ((ActivityManager) SplashScreen.this.getSystemService(ACTIVITY_SERVICE))
                    .clearApplicationUserData();
            return;
        } else {

        }

        spscreen = findViewById(R.id.spscreen);
        checkPermission();

        databaseHelper = new DatabaseHelper(this);
        ArrayList arrayList = new ArrayList<String>();
      rawayir = new String[]{"c2","db2","d2","eb2","e2","f2","gb2","g2","ab2","a2","bb2","b2","c3","db3","d3","eb3","e3","f3","gb3","g3","ab3","a3","bb3","b3","c4","db4","d4","eb4","e4","f4","gb4","g4","ab4","a4","bb4","b4","c5","db5","d5","eb5","e5","f5","gb5","g5","ab5","a5","bb5","b5","c6","db6","d6","eb6","e6","f6","gb6","g6","ab6","a6","bb6","b6","c7"};
      for (int i=0; i<rawayir.length; i++){
        arrayList.add(rawayir[i]);
      }
        for (int i=0; i<getAllRawResources().length; i++){
            if (!arrayList.contains(getAllRawResources()[i])){
              tumsarklar.add(getAllRawResources()[i]);
            }

        }

        if (checkPermission()){
          File mydir;

          mydir = new File(SplashScreen.this.getExternalFilesDir(null) + "/msapp");

          if (!mydir.exists()) {
            mydir.mkdirs();

          }
        }

        createFolders(SplashScreen.this);

        //Şarkının adları başlar

        sarkadlar.add("Adamlar - Acının İlacı");
        sarkadlar.add("Duman - Ah");
        sarkadlar.add("maNga - Alışırım Gözlerimi Kapamaya");
        sarkadlar.add("Mavi Gri - Altüst Olmuşum");
        sarkadlar.add("Duman - Aman Aman");
        sarkadlar.add("Yedinci Ev - Anlat Ona");
        sarkadlar.add("Model - Antidepresan Gülümsemesi");
        sarkadlar.add("Mor Ve Ötesi - Araf");
        sarkadlar.add("Yüksek Sadakat - Aşk Durdukça");
        sarkadlar.add("Teoman - Aşk Kırıntıları");
        sarkadlar.add("Gripin - Aşk Nerden Nereye");
        sarkadlar.add("Yüzyüzeyken Konuşuruz - Ateş Edecek Misin?");
        sarkadlar.add("Ufuk Beydemir - Ay Tenli Kadın");
        sarkadlar.add("Duman - Bal");
        sarkadlar.add("Batuhan Mutlugil - Bambaşka");
        sarkadlar.add("Teoman - Bana Öyle Bakma");
        sarkadlar.add("Deniz Tekin - Belki");
        sarkadlar.add("Dolu Kadehi Ters Tut - Belki");
        sarkadlar.add("Emre Aydın - Belki Bir Gün Özlersin");
        sarkadlar.add("Aylin Aslım - Ben Kalender Meşrebim");
        sarkadlar.add("Adamlar - Benden Bana");
        sarkadlar.add("maNga - Beni Benimle Bırak");
        sarkadlar.add("Feridun Düzağaç - Beni Bırakma");
        sarkadlar.add("Cem Adrian - Beni Hatırladın Mı?");
        sarkadlar.add("Pinhani - Beni Sen İnandır");
        sarkadlar.add("Son Feci Bisiklet - Bikinisinde Astronomi");
        sarkadlar.add("maNga - Bir Kadın Çizeceksin");
        sarkadlar.add("Model - Bir Melek Vardı");
        sarkadlar.add("Model - Bir Pazar Kahvaltısı & Emre Aydın");
        sarkadlar.add("Karsu - Bırak Beni Böyle");
        sarkadlar.add("Yüzyüzeyken Konuşuruz - Bodrum");
        sarkadlar.add("Yüzyüzeyken Konuşuruz - Boş Gemiler");
        sarkadlar.add("Duman - Bu Akşam");
        sarkadlar.add("Şebnem Ferah - Bu Aşk Fazla Sana");
        sarkadlar.add("Son Feci Bisiklet - Bu Kız");
        sarkadlar.add("Can Kazaz - Bunca Yıl");
        sarkadlar.add("Model - Buzdan Şato");
        sarkadlar.add("Mor Ve Ötesi - Cambaz");
        sarkadlar.add("maNga - Cevapsız Sorular");
        sarkadlar.add("Teoman - Çoban Yıldızı");
        sarkadlar.add("Mor Ve Ötesi - Daha Mutlu Olamam");
        sarkadlar.add("Model - Değmesin Ellerimiz");
        sarkadlar.add("Mor Ve Ötesi - Deli");
        sarkadlar.add("Duman - Dibine Kadar");
        sarkadlar.add("Yüzyüzeyken Konuşuruz - Dinle Beni Bi");
        sarkadlar.add("Yüzyüzeyken Konuşuruz - Dinle Beni Bi (Kolay)");
        sarkadlar.add("Pinhani - Dön Bak Dünyaya");
        sarkadlar.add("maNga - Dünyanın Sonuna Doğmuşum");
        sarkadlar.add("maNga - Dünyanın Sonuna Doğmuşum (Kolay)");
        sarkadlar.add("Mor Ve Ötesi - Dünyaya Bedel");
        sarkadlar.add("maNga - Dursun Zaman");
        sarkadlar.add("Feridun Düzağaç - Düşler Sokağı");
        sarkadlar.add("Teoman - Efendi ve Kölesi");
        sarkadlar.add("Mor Ve Ötesi - Eksik");
        sarkadlar.add("Son Feci Bisiklet - Elektrot");
        sarkadlar.add("Duman - Elleri Ellerime");
        sarkadlar.add("Duman - Eski Köprünün Altında");
        sarkadlar.add("maNga - Fly To Stay Alive");
        sarkadlar.add("Eskitilmiş Yaz - Geceler Şimdi");
        sarkadlar.add("Farah Zeynep Abdullah - Gel ya da Git");
        sarkadlar.add("Farah Zeynep Abdullah - Gel Ya Da Git (Kolay)");
        sarkadlar.add("Deniz Tekin - Gelir miyim");
        sarkadlar.add("Teoman - Gemiler");
        sarkadlar.add("Leyla ile Mecnun - Geri Dönme");
        sarkadlar.add("Dolu Kadehi Ters Tut - Gitme");
        sarkadlar.add("Dolu Kadehi Ters Tut - Gitme (Kolay)");
        sarkadlar.add("Duman - Haberin Yok Ölüyorum");
        sarkadlar.add("maNga - Hayat Bu İşte");
        sarkadlar.add("Yüksek Sadakat - Haydi Gel İçelim");
        sarkadlar.add("Duman - Helal Olsun");
        sarkadlar.add("Pinhani - Hele Bi Gel");
        sarkadlar.add("Adamlar - Hepinize El Salladım");
        sarkadlar.add("Zerrin Özer - Her Şey Seninle Güzel");
        sarkadlar.add("Duman - Her Şeyi Yak");
        sarkadlar.add("Duman - Her Şeyi Yak (Kolay)");
        sarkadlar.add("Lalfizu - Hikaye");
        sarkadlar.add("Şebnem Ferah - Hoşçakal");
        sarkadlar.add("maNga - Işıkları Söndürseler Bile");
        sarkadlar.add("Karsu - Jest Oldu");
        sarkadlar.add("Can Ozan - Kalbimden Tenime");
        sarkadlar.add("Yüzyüzeyken Konuşuruz - Kazılı Kuyum");
        sarkadlar.add("Seksendört - Kendime Yalan Söyledim");
        sarkadlar.add("Duman - Kırmış Kalbini");
        sarkadlar.add("Dua Lipa - Kiss And Make Up");
        sarkadlar.add("Mor Ve Ötesi - Küçük Sevgilim");
        sarkadlar.add("Cem Adrian - Kül");
        sarkadlar.add("Cem Adrian - Kül (Kolay)");
        sarkadlar.add("Teoman - Kupa Kızı ve Sinek Valesi");
        sarkadlar.add("Leyla ile Mecnun - Fon Müziği");
        sarkadlar.add("Dolu Kadehi Ters Tut - Madem");
        sarkadlar.add("Model - Makyaj");
        sarkadlar.add("Duman - Manası Yok");
        sarkadlar.add("Teoman - Mavi Kuşla Küçük Kız");
        sarkadlar.add("Can Bonomo - Meczup");
        sarkadlar.add("Duman - Melankoli");
        sarkadlar.add("Güler Özince - Merkür Retrosu");
        sarkadlar.add("Model - Mey");
        sarkadlar.add("Umut Kaya - Mor Yazma");
        sarkadlar.add("Pinhani - Ne Güzel Güldün");
        sarkadlar.add("Duman - Oje");
        sarkadlar.add("Yüzyüzeyken Konuşuruz - Ölsem Yeridir");
        sarkadlar.add("Sertap Erener - Olsun");
        sarkadlar.add("Hayko Cepkin - Ölüyorum");
        sarkadlar.add("Duman - Öyle Dertli");
        sarkadlar.add("Mor Ve Ötesi - Oyunbozan");
        sarkadlar.add("Yüzyüzeyken Konuşuruz - Para Hala Bende");
        sarkadlar.add("Teoman - Paramparça");
        sarkadlar.add("Son Feci Bisiklet - Pazar Ve Ertesi");
        sarkadlar.add("Model - Pembe Mezarlık");
        sarkadlar.add("Model - Pembe Mezarlık (Kolay)");
        sarkadlar.add("Ed Sheeran - Perfect");
        sarkadlar.add("Adamlar - Rüyalarda Buruşmuşum");
        sarkadlar.add("Yüzyüzeyken Konuşuruz - Sandal");
        sarkadlar.add("Can Ozan - Sar Bu Şehri");
        sarkadlar.add("Model - Sarı Kurdeleler");
        sarkadlar.add("Adamlar - Sarılırım Birine");
        sarkadlar.add("Ogün Şanlısoy - Saydım");
        sarkadlar.add("Özlem Tekin - Sen Anla");
        sarkadlar.add("Model - Sen Ona Aşıksın");
        sarkadlar.add("Model - Sen Ona Aşıksın (Kolay)");
        sarkadlar.add("Yüzyüzeyken Konuşuruz - Sen Varsın Diye");
        sarkadlar.add("Duman - Senden Daha Güzel");
        sarkadlar.add("Teoman - Senden Önce Senden Sonra");
        sarkadlar.add("Madrigal - Seni Dert Etmeler");
        sarkadlar.add("Pera - Seni Kaybettiğimde");
        sarkadlar.add("Duman - Seni Kendime Sakladım");
        sarkadlar.add("Pera - Sensiz Ben");
        sarkadlar.add("Teoman - Serseri");
        sarkadlar.add("Mor Ve Ötesi - Sevda Çiçeği");
        sarkadlar.add("Sena Şener - Sevmemeliyiz");
        sarkadlar.add("Yedinci Ev - Sevsene Beni");
        sarkadlar.add("Batuhan Kordel - Sıcak Şarap");
        sarkadlar.add("Silverstein - Smile in Your Sleep");
        sarkadlar.add("Cem Karaca - Tamirci Çırağı");
        sarkadlar.add("30 Seconds To Mars - The Kill");
        sarkadlar.add("Duman - Tövbe");
        sarkadlar.add("Teoman - Uçurtmalar");
        sarkadlar.add("Pera - Unut & Toygar Işıklı");
        sarkadlar.add("Yüzyüzeyken Konuşuruz - Uykusuz ve Dengesiz");
        sarkadlar.add("Eskitilmiş Yaz - Uyursam geçer mi");
        sarkadlar.add("Evdeki Saat - Uzunlar");
        sarkadlar.add("Birkan Nasuhoğlu - Varsa Yoksa");
        sarkadlar.add("maNga - We Could Be The Same");
        sarkadlar.add("maNga - Yad Eller");
        sarkadlar.add("Athena - Yalan");
        sarkadlar.add("Duman - Yanıbaşımdan");
        sarkadlar.add("Sena Şener - Yine Mi Yol");
        sarkadlar.add("Cem Adrian - Yine Mi Yol");
        sarkadlar.add("TNK - Yine Yazı Bekleriz");
        sarkadlar.add("Duman - Yürekten");




        //Şarkının adları biter

        //Şarkı urller başlar

        sarkurller.add("https://www.youtube.com/embed/ogfRz9ngDdM");
        sarkurller.add("https://www.youtube.com/embed/gPTmTczM55w");
        sarkurller.add("https://www.youtube.com/embed/GUVjF5kbpO4");
        sarkurller.add("https://www.youtube.com/embed/JkBIhuzIaY0");
        sarkurller.add("https://www.youtube.com/embed/kgNt3DexIjY");
        sarkurller.add("https://www.youtube.com/embed/TWW3Rr01yfg");
        sarkurller.add("https://www.youtube.com/embed/6NZi_KuJ2lc");
        sarkurller.add("https://www.youtube.com/embed/yo2MzKNU5xs");
        sarkurller.add("https://www.youtube.com/embed/lZU_3eXqVn0");
        sarkurller.add("https://www.youtube.com/embed/Q4jicYaDReE");
        sarkurller.add("https://www.youtube.com/embed/Ycjr5QhxW3Y");
        sarkurller.add("https://www.youtube.com/embed/Ix98YuBM-gE");
        sarkurller.add("https://www.youtube.com/embed/eP95dgUYNwo");
        sarkurller.add("https://www.youtube.com/embed/u8f3wbpzBqI");
        sarkurller.add("https://www.youtube.com/embed/m09WPWrcAUQ");
        sarkurller.add("https://www.youtube.com/embed/trUCgRw3844");
        sarkurller.add("https://www.youtube.com/embed/mrPaeweaitA");
        sarkurller.add("https://www.youtube.com/embed/mrPaeweaitA");
        sarkurller.add("https://www.youtube.com/embed/no9Vt_G9A68");
        sarkurller.add("https://www.youtube.com/embed/pT_qVEZNv68");
        sarkurller.add("https://www.youtube.com/embed/FWetCMDHD18");
        sarkurller.add("https://www.youtube.com/embed/Ian4XIJlAeg");
        sarkurller.add("https://www.youtube.com/embed/UToMkpznfCs");
        sarkurller.add("https://www.youtube.com/embed/F5QvNv76Buo");
        sarkurller.add("https://www.youtube.com/embed/OYYLZE2n4wQ");
        sarkurller.add("https://www.youtube.com/embed/Z6LzOkb-nYc");
        sarkurller.add("https://www.youtube.com/embed/VB0GqbKPO_c");
        sarkurller.add("https://www.youtube.com/embed/pVnmm8q9KB8");
        sarkurller.add("https://www.youtube.com/embed/xJTc04_A8QI");
        sarkurller.add("https://www.youtube.com/embed/cBkbZc41nMw");
        sarkurller.add("https://www.youtube.com/embed/U3cytmGsUH4");
        sarkurller.add("https://www.youtube.com/embed/oRD23S41FHE");
        sarkurller.add("https://www.youtube.com/embed/Xe_sw2j_YS0");
        sarkurller.add("https://www.youtube.com/embed/CVzWUpY_cgg");
        sarkurller.add("https://www.youtube.com/embed/P2DDLZNKRFI");
        sarkurller.add("https://www.youtube.com/embed/oC87YZLE7A0");
        sarkurller.add("https://www.youtube.com/embed/tQIv09qgDpk");
        sarkurller.add("https://www.youtube.com/embed/HmlffdM1IXg");
        sarkurller.add("https://www.youtube.com/embed/3cni-6Y7FI8");
        sarkurller.add("https://www.youtube.com/embed/GUkeUEb7DOs");
        sarkurller.add("https://www.youtube.com/embed/hyfstKza2lU");
        sarkurller.add("https://www.youtube.com/embed/VECWwmfv60o");
        sarkurller.add("https://www.youtube.com/embed/yVv3KIM5_mI");
        sarkurller.add("https://www.youtube.com/embed/WgYA9FR8mQE");
        sarkurller.add("https://www.youtube.com/embed/rqKOOL48sbI");
        sarkurller.add("https://www.youtube.com/embed/Vn3lYpCJvS0");
        sarkurller.add("https://www.youtube.com/embed/6hWbc84jYdM");
        sarkurller.add("https://www.youtube.com/embed/ObQDTTq8sfA");
        sarkurller.add("https://www.youtube.com/embed/rTPdDJ24RwI");
        sarkurller.add("https://www.youtube.com/embed/wr6iQn1WhYU");
        sarkurller.add("https://www.youtube.com/embed/ADn4Ol8MefU");
        sarkurller.add("https://www.youtube.com/embed/HBr30djUApY");
        sarkurller.add("https://www.youtube.com/embed/kvyqpFXqg9U");
        sarkurller.add("https://www.youtube.com/embed/9DH_saczUAA");
        sarkurller.add("https://www.youtube.com/embed/ab1Qe6d2fyo");
        sarkurller.add("https://www.youtube.com/embed/n_zNi4rpkTk");
        sarkurller.add("https://www.youtube.com/embed/tfYf618h1pI");
        sarkurller.add("https://www.youtube.com/embed/jn0HS8gnnAA");
        sarkurller.add("https://www.youtube.com/embed/qJJjQFcJKyo");
        sarkurller.add("https://www.youtube.com/embed/7tCClRAcias");
        sarkurller.add("https://www.youtube.com/embed/BpcjZStqZ0Q");
        sarkurller.add("https://www.youtube.com/embed/W5wm4po1lk0");
        sarkurller.add("https://www.youtube.com/embed/GkELNdi24OM");
        sarkurller.add("https://www.youtube.com/embed/bKsfBoE4JbU");
        sarkurller.add("https://www.youtube.com/embed/If--Gba-8KY");
        sarkurller.add("https://www.youtube.com/embed/cOefu9twctc");
        sarkurller.add("https://www.youtube.com/embed/cpLk-YdKw1U");
        sarkurller.add("https://www.youtube.com/embed/CnjTPZm2eug");
        sarkurller.add("https://www.youtube.com/embed/CRfoTyX17Y4");
        sarkurller.add("https://www.youtube.com/embed/eBNmerMoelY");
        sarkurller.add("https://www.youtube.com/embed/oz0v5JDHai4");
        sarkurller.add("https://www.youtube.com/embed/ARCHPP21H4k");
        sarkurller.add("https://www.youtube.com/embed/Uzs6jx_zzHw");
        sarkurller.add("https://www.youtube.com/embed/NL9spxZVOZc");
        sarkurller.add("https://www.youtube.com/embed/iG2jDnTfR08");
        sarkurller.add("https://www.youtube.com/embed/v2LqXx9hlsE");
        sarkurller.add("https://www.youtube.com/embed/fjXU7Y8VYzo");
        sarkurller.add("https://www.youtube.com/embed/pPwlKxRcYEs");
        sarkurller.add("https://www.youtube.com/embed/Rpj_YsI5WMU");
        sarkurller.add("https://www.youtube.com/embed/HqczItOHas4");
        sarkurller.add("https://www.youtube.com/embed/vX6zso1XHsg");
        sarkurller.add("https://www.youtube.com/embed/FfKw00m9SNo");
        sarkurller.add("https://www.youtube.com/embed/hTHfBsCgb4k");
        sarkurller.add("https://www.youtube.com/embed/bUdpduP9zAA");
        sarkurller.add("https://www.youtube.com/embed/ftYRSD6KTmE");
        sarkurller.add("https://www.youtube.com/embed/bHV3STRp6NY");
        sarkurller.add("https://www.youtube.com/embed/kc8oKRLZHbY");
        sarkurller.add("https://www.youtube.com/embed/gcx-bhJT9mA");
        sarkurller.add("https://www.youtube.com/embed/-Lo239YmmVE");
        sarkurller.add("https://www.youtube.com/embed/Y_2WjdHXaw0");
        sarkurller.add("https://www.youtube.com/embed/T8lvXsyWkUs");
        sarkurller.add("https://www.youtube.com/embed/BRzK_heH-lM");
        sarkurller.add("https://www.youtube.com/embed/vCH5lGzXEhc");
        sarkurller.add("https://www.youtube.com/embed/O3w7amsipB8");
        sarkurller.add("https://www.youtube.com/embed/pAfEo2aLs3Q");
        sarkurller.add("https://www.youtube.com/embed/suTmhaD_N7Q");
        sarkurller.add("https://www.youtube.com/embed/SNbZNyuhS9M");
        sarkurller.add("https://www.youtube.com/embed/0olYImvOGoI");
        sarkurller.add("https://www.youtube.com/embed/ReOb77RgKIE");
        sarkurller.add("https://www.youtube.com/embed/8O8wdqZDNKo");
        sarkurller.add("https://www.youtube.com/embed/Je0k8wxoEBQ");
        sarkurller.add("https://www.youtube.com/embed/Vh3RAb3FsDU");
        sarkurller.add("https://www.youtube.com/embed/4wj3sBKeZQA");
        sarkurller.add("https://www.youtube.com/embed/I2YDxjXCWiM");
        sarkurller.add("https://www.youtube.com/embed/1Mz6IXULgqg");
        sarkurller.add("https://www.youtube.com/embed/Syr4uXqkL2I");
        sarkurller.add("https://www.youtube.com/embed/WnICW-3LkRs");
        sarkurller.add("https://www.youtube.com/embed/-T1tr9HPrLE");
        sarkurller.add("https://www.youtube.com/embed/ZrWA32esRRM");
        sarkurller.add("https://www.youtube.com/embed/urWsu_uimTg");
        sarkurller.add("https://www.youtube.com/embed/EBpyra83Gt0");
        sarkurller.add("https://www.youtube.com/embed/BJgINRLDE0Q");
        sarkurller.add("https://www.youtube.com/embed/3bfDbc_l91U");
        sarkurller.add("https://www.youtube.com/embed/lYotDTjFOl8");
        sarkurller.add("https://www.youtube.com/embed/Xwd8lbFWgz4");
        sarkurller.add("https://www.youtube.com/embed/kmz8eDuz4_Q");
        sarkurller.add("https://www.youtube.com/embed/3XJabNYVejI");
        sarkurller.add("https://www.youtube.com/embed/CozijhULTBk");
        sarkurller.add("https://www.youtube.com/embed/RqX6w2q881Y");
        sarkurller.add("https://www.youtube.com/embed/_Cqaitb_mq4");
        sarkurller.add("https://www.youtube.com/embed/MG4iShP0AAo");
        sarkurller.add("https://www.youtube.com/embed/zLsLzUfLhGA");
        sarkurller.add("https://www.youtube.com/embed/BoZr1MBqA40");
        sarkurller.add("https://www.youtube.com/embed/zPUV0lKg3Ck");
        sarkurller.add("https://www.youtube.com/embed/TpU_6qvjTUg");
        sarkurller.add("https://www.youtube.com/embed/oKRbCaDXce4");
        sarkurller.add("https://www.youtube.com/embed/cwMM4Q1bVy0");
        sarkurller.add("https://www.youtube.com/embed/oUzSPGTgioo");
        sarkurller.add("https://www.youtube.com/embed/PMvH1SZKX-k");
        sarkurller.add("https://www.youtube.com/embed/TPtRlKHBfn0");
        sarkurller.add("https://www.youtube.com/embed/5SuLJ6lHCmk");
        sarkurller.add("https://www.youtube.com/embed/yU2cYHhriMc");
        sarkurller.add("https://www.youtube.com/embed/mFQOb5Wx7nY");
        sarkurller.add("https://www.youtube.com/embed/9Ww-Wg51BaE");
        sarkurller.add("https://www.youtube.com/embed/QokoH6aFGz0");
        sarkurller.add("https://www.youtube.com/embed/WG0exaW70ck");
        sarkurller.add("https://www.youtube.com/embed/lv1WPH7bnDA");
        sarkurller.add("https://www.youtube.com/embed/GI54GQcS3Dw");
        sarkurller.add("https://www.youtube.com/embed/KofgzrCbLOo");
        sarkurller.add("https://www.youtube.com/embed/WM5icem8gQ0");
        sarkurller.add("https://www.youtube.com/embed/9aaR7i7wRrM");
        sarkurller.add("https://www.youtube.com/embed/-wkpJW2pC5c");
        sarkurller.add("https://www.youtube.com/embed/Cx8cdi3wUfc");
        sarkurller.add("https://www.youtube.com/embed/YnLzwzWajKc");
        sarkurller.add("https://www.youtube.com/embed/8a8-TPxob7k");
        sarkurller.add("https://www.youtube.com/embed/fzBYc7Y1kC4");
        sarkurller.add("https://www.youtube.com/embed/z-jZPyQCbck");
        sarkurller.add("https://www.youtube.com/embed/z-jZPyQCbck");
        sarkurller.add("https://www.youtube.com/embed/KcjG0XSL9-0");
        sarkurller.add("https://www.youtube.com/embed/qhnO1pVOuDM");

        //Şarkı urller biter



        //Tüm Sanatçılar Başlar
        for (int i=0; i<sarkadlar.size(); i++){
            String[] sarkadlardiz = sarkadlar.get(i).toString().split(" - ");
            if (!tumsanlar.contains(sarkadlardiz[0])){
                tumsanlar.add(sarkadlardiz[0]);
            }
        }






        //Tüm Sanatçılar Biter

        //Sanatçı Resimleri Başlar

        sanimgler.add("ic_adamlar");
        sanimgler.add("ic_duman");
        sanimgler.add("ic_mangaaa");
        sanimgler.add("ic_mavi_gri");
        sanimgler.add("ic_yedinciev");
        sanimgler.add("ic_model");
        sanimgler.add("ic_morveotesi");
        sanimgler.add("ic_yukseksadakat");
        sanimgler.add("ic_teoman");
        sanimgler.add("ic_covgripin");
        sanimgler.add("ic_yzkon");
        sanimgler.add("ic_ufukbeydemir");
        sanimgler.add("ic_batuhanmutlu");
        sanimgler.add("ic_deniztekin");
        sanimgler.add("ic_dolkado");
        sanimgler.add("ic_emreaydn");
        sanimgler.add("ic_aylinaslim");
        sanimgler.add("ic_feridundz");
        sanimgler.add("ic_cemadrian");
        sanimgler.add("ic_pinhani");
        sanimgler.add("ic_sfbimage");
        sanimgler.add("ic_karsu");
        sanimgler.add("ic_sebnem");
        sanimgler.add("ic_cankazaz");
        sanimgler.add("ic_eskitilmi_yaz");
        sanimgler.add("ic_farah");
        sanimgler.add("ic_lm");
        sanimgler.add("ic_zerrin_zer");
        sanimgler.add("ic_lalfizu");
        sanimgler.add("ic_canozan");
        sanimgler.add("ic_seksendrt");
        sanimgler.add("ic_dualipafoto");
        sanimgler.add("ic_canbonomo");
        sanimgler.add("ic_gulerozince");
        sanimgler.add("ic_umutkaya");
        sanimgler.add("ic_sertapere");
        sanimgler.add("ic_haykocepkin");
        sanimgler.add("ic_edsherfoto");
        sanimgler.add("ic_ogunsanlisoy");
        sanimgler.add("ic_ozlemtekin");
        sanimgler.add("ic_madrigal");
        sanimgler.add("ic_pera");
        sanimgler.add("ic_sanasener");
        sanimgler.add("ic_batuhan_kordel");
        sanimgler.add("ic_silverstein");
        sanimgler.add("ic_cemkaracares");
        sanimgler.add("ic_thirtysec");
        sanimgler.add("ic_evdeki_saat");
        sanimgler.add("ic_birkannasuh");
        sanimgler.add("ic_athenacov");
        sanimgler.add("ic_tnk");



        //Sanatçı Resimleri Biter

        //Popüler Şarkılar Başlar
        sarpopiler.add("Cem Adrian - Kül");
        sarpopiler.add("Farah Zeynep Abdullah - Gel ya da Git");
        sarpopiler.add("Can Kazaz - Bunca Yıl");
        sarpopiler.add("Duman - Her Şeyi Yak");
        sarpopiler.add("Model - Sen Ona Aşıksın");
        sarpopiler.add("Yüzyüzeyken Konuşuruz - Dinle Beni Bi");
        sarpopiler.add("Dolu Kadehi Ters Tut - Gitme");
        sarpopiler.add("Sena Şener - Sevmemeliyiz");
        sarpopiler.add("maNga - Dünyanın Sonuna Doğmuşum");
        sarpopiler.add("Son Feci Bisiklet - Bikinisinde Astronomi");
        //Popüler Şarkılar Biter

        ArrayList sarklist = (ArrayList) databaseHelper.aramanota();
        ArrayList sanlist = (ArrayList) databaseHelper.everysans();
        ArrayList popilist = (ArrayList) databaseHelper.sarpopicek();

        if (sarklist.size()>0){
            a++;
        } else {
            for (int i=0; i<sarkadlar.size(); i++){

                Notarama notarama = new Notarama(-1,sarkadlar.get(i).toString(),sarkurller.get(i).toString(),tumsarklar.get(i).toString(),"no");
                Boolean addAramanota = databaseHelper.addaramanota(notarama);
                if (!addAramanota){
                    Toast.makeText(SplashScreen.this, basarisiz, Toast.LENGTH_SHORT).show();
                }

            }
        }

        if (sanlist.size()>0){
            a++;
        } else {
            for (int i=0; i<tumsanlar.size(); i++){
                Everysan everysan = new Everysan(-1,tumsanlar.get(i).toString(),"no",sanimgler.get(i).toString());
                Boolean addEverySan = databaseHelper.addeverysan(everysan);
                if (!addEverySan){
                    Toast.makeText(SplashScreen.this, basarisiz, Toast.LENGTH_SHORT).show();
                }


            }
        }

        if (popilist.size()>0){
            a++;
        } else {
            for (int i=0; i<sarpopiler.size(); i++){
                Sarpopi sarpopi = new Sarpopi(-1,sarpopiler.get(i).toString(),"no","no","no","no");
                Boolean addSarpopi = databaseHelper.addsarpopi(sarpopi);
                if (!addSarpopi){
                    Toast.makeText(SplashScreen.this, basarisiz, Toast.LENGTH_SHORT).show();
                }


            }
        }

        if (a==3){
          Intent intent = new Intent(SplashScreen.this,MainActivity.class);
          startActivity(intent);
          finish();
        }





    }
    public boolean checkPermission() {
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.N){

            return true;
        } else {
            int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            if((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_WRITE);
                return false;
            }
        }

        return true;
    }

  @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.N){
          Intent intent = new Intent(SplashScreen.this,SplashScreen.class);
          startActivity(intent);
          finish();
        } else {
            if (requestCode==PERMISSION_WRITE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
              Intent intent = new Intent(SplashScreen.this,SplashScreen.class);
              startActivity(intent);
            } else {
              Toast.makeText(this, "Devam etmek için lütfen kabul edin, sadece notaların depolanması amacıyla bu izin istenir...", Toast.LENGTH_LONG).show();
              finish();
            }
        }

    }

}