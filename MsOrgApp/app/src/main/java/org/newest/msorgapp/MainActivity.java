package org.newest.msorgapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Application;
import android.app.DownloadManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.text.InputType;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import org.newest.msorgapp.Services.GetNewsActionService;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;




public class MainActivity extends AppCompatActivity{
    private static final int PERMISSION_READ = 0;
    private int ilgsayi=0;
    private int addCounter = 0;
    private RewardedAd mRewardedAd;
    public static final int PICKFILE_RESULT_CODE = 1;
    private final String TAG = "MainActivity";
    private Timer timer;
    private String bastex = "bastex";
    private String basimg = "basimg";
    private DatabaseHelper databaseHelper;
    private MediaPlayer mediaPlayer;
    public int glowidth;
    public int gloheight;
    private ArrayList allgames = new ArrayList<String>();
    private Application application;
    private ImageView mainsearch;
    private String URL2 = "https://www.xn--mziksayfas-9db95d.com/login.php";
    private String URL3 = "https://www.xn--mziksayfas-9db95d.com/newnoti.php";
    private String uriyolmp;
    private String currentSark;
    private SeekBar curseekpop;
    private ImageView curimgpop;
    private TextView puantextus,hearttextus;
    private TextView hosgeltex;
    private ImageView mainprofil;
    private static final int MATCHPARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    private static final int WRAPCONTENT = LinearLayout.LayoutParams.WRAP_CONTENT;


    private void displayOverDetect(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
            if (databaseHelper.bildirimcek().isEmpty()){
                if (!Settings.canDrawOverlays(this)) {
                    Handler handler = new Handler();
                    Runnable runnable = () -> alertDrawOnTop();
                    handler.postDelayed(runnable,2500);

                }
            }

        }
    }

    private void detectDuplicated(DatabaseHelper databaseHelper){
        List<Notarama> notaramas = databaseHelper.aramanotag();
        ArrayList<String> notadlar = new ArrayList<>();
        ArrayList<String> compareNotadlar = new ArrayList<>();
        ArrayList<Integer> deletedindex = new ArrayList<>();

        if (!notaramas.isEmpty()){
            for (int i=0; i<notaramas.size(); i++){
                notadlar.add(notaramas.get(i).getNotaname()+"x0x0x"+notaramas.get(i).getId());
            }

            for (int i=0; i<notadlar.size(); i++){
                String[] bol = notadlar.get(i).split("x0x0x");
                if (compareNotadlar.contains(bol[0])){
                    deletedindex.add(Integer.parseInt(bol[1]));
                }
                compareNotadlar.add(bol[0]);
            }

            for (int i=0; i<notaramas.size(); i++){
                for (int j=0; j<deletedindex.size(); j++){
                    if (notaramas.get(i).getId()==deletedindex.get(j)){
                        if (databaseHelper.deletearamanotag(notaramas.get(i))){

                        }
                    }
                }
            }
        }


    }

    private boolean nightmodedetect(){
        int nightModeFlags =
                this.getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                Handler handler = new Handler();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        alertbuilder();
                    }
                };
                handler.postDelayed(runnable,3000);
                break;

            case Configuration.UI_MODE_NIGHT_NO:

                break;

            case Configuration.UI_MODE_NIGHT_UNDEFINED:

                break;
        }
        return true;
    }

    private boolean procontrol (){
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        List<Kulbi> kulcek = databaseHelper.kulbicek();
        if (kulcek.size()>0){
            hosgeltex = findViewById(R.id.hosgeltext);
            hosgeltex.setText("Selam"+" "+kulcek.get(0).getKulad()+";)");
            return true;
        } else {

            return false;
        }

    }

    private void rewardedAdset(){

        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(this, "ca-app-pub-2627695336411451/7188689293",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d(TAG, loadAdError.getMessage());
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        Log.d(TAG, "Ad was loaded.");

                        mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                                Log.d(TAG, "Ad was shown.");
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.
                                Log.d(TAG, "Ad failed to show.");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Set the ad reference to null so you don't show the ad a second time.
                                Handler handler = new Handler();
                                Runnable runnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(MainActivity.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                };
                                handler.postDelayed(runnable,100);

                                Log.d(TAG, "Ad was dismissed.");

                                mRewardedAd = null;
                            }
                        });
                    }
                });


    }



    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_NoActionBar_Launcher2);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        application = getApplication();
        rewardedAdset();
        nightmodedetect();
        displayOverDetect();

        /*/
        TODO

        1) Can ile oyuna giriş sistemini ayarla
        2) Tüm dataları veritabanı ile eşleşecek biçimde arka planda güncelleme işlemini yap
        3) Eğer can olmazsa ödüllü reklam izleterek giriş hakkı ver
        4) Marketi tasarla
        5) Skor tablolarını tasarla, günlük,haftalık,aylık
         */


        allgames.add("Cem Adrian - Kül");
        allgames.add("maNga - Dünyanın Sonuna Doğmuşum");
        allgames.add("Model - Pembe Mezarlık");
        allgames.add("Farah Zeynep Abdullah - Gel ya da Git");
        allgames.add("Yüzyüzeyken Konuşuruz - Dinle Beni Bi");
        allgames.add("Son Feci Bisiklet - Bikinisinde Astronomi");
        allgames.add("Dolu Kadehi Ters Tut - Gitme");
        allgames.add("Duman - Senden Daha Güzel");
        allgames.add("Madrigal - Seni Dert Etmeler");
        allgames.add("Can Kazaz - Bunca Yıl");
        allgames.add("Duman - Her Şeyi Yak");



        realtimeNotification(MainActivity.this);

        databaseHelper = new DatabaseHelper(MainActivity.this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
         gloheight = displayMetrics.heightPixels;
         glowidth = displayMetrics.widthPixels;

        mainsearch = findViewById(R.id.mainsearch);
        hosgeltex = findViewById(R.id.hosgeltext);
        mainprofil = findViewById(R.id.mainprofim);
        mainsearch.setTag(basimg);
        ViewEditor(hosgeltex,15,15);

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                detectDuplicated(databaseHelper);
            }
        };
        handler.postDelayed(runnable,5000);






        mainprofil.setOnClickListener(v -> {
            ProgressDialog progressDialog = new ProgressDialog(v.getContext());
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Yükleniyor...");
            if (procontrol()){
                progressDialog.show();
                Intent intent = new Intent(v.getContext(),kulprofilo.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();

            } else {
                girisAcPopup(v.getContext(),mainprofil);
            }
        });



       /* kitfavbas.setOnClickListener(v -> {
            if (procontrol()){
                favorpopup(v.getContext(),kitfavbas);
            } else {
                Snackbar snackbar = Snackbar
                        .make(findViewById(R.id.mainact), "Lütfen Profil Girişi Yapın", Snackbar.LENGTH_LONG);
                snackbar.setAction("Tamam", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        girisAcPopup(v.getContext(),kitfavbas);
                    }
                });
                snackbar.show();
            }
        });

        */

        //preparePlayer(uriyolmp);
        LinearLayout.LayoutParams imlay = (LinearLayout.LayoutParams) mainsearch.getLayoutParams();
        imlay.setMargins(gloheight/180,gloheight/180,gloheight/180,gloheight/180);
        mainsearch.setLayoutParams(imlay);


        setScrollLay();

        mainsearch.setOnClickListener(v -> newSearchPopup(databaseHelper,v.getContext()));

        BroadcastReceiver onComplete=new BroadcastReceiver() {
            public void onReceive(Context ctxt, Intent intent) {

            }
        };

        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                // adRequest = new AdRequest.Builder().build();
            }
        });

    }

    private void  profotocek(){
        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
        ArrayList kular = (ArrayList) databaseHelper.kulbicek();
        String[] kularcek = kular.get(0).toString().split(">");
        String kulid = kularcek[4];

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onResponse(String response) {
                try {
                    response = response.trim();
                    if (!response.equalsIgnoreCase(null)){
                        File imstore = new File(MainActivity.this.getExternalFilesDir(null)+"/msapp/profotos");
                        File[] imdiz = imstore.listFiles();
                        if (imdiz.length>0){
                            for (int a=0; a<imdiz.length; a++){
                                imdiz[a].delete();
                            }
                        }
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        String imageFileName = "PROFOTO_" + timeStamp + ".jpg";
                        File newres = new File(MainActivity.this.getExternalFilesDir(null)+"/msapp/profotos/"+imageFileName);



                        byte[] decodedString = Base64.decode(response, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        decodedByte.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                        byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
                        try (FileOutputStream fos = new FileOutputStream(newres)) {
                            fos.write(bitmapdata);
                            fos.flush();

                        }
                    }
                }
                catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> data = new HashMap<>();
                data.put("kulidceken",kulid);
                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.stop();
                request.cancel();
            }
        });

    }

    private void ilgSanEkle(Context context, String sanad, LinearLayout linearLayout){
        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
        Ilgsanlar ilgsanlar = new Ilgsanlar(-1,sanad);
        Boolean ilgekle = databaseHelper.addilgsanlar(ilgsanlar);
        if (ilgekle){
            Snackbar.make(linearLayout,sanad+" Eklendi...",BaseTransientBottomBar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(linearLayout,sanad+" Eklenemedi...",BaseTransientBottomBar.LENGTH_SHORT).show();
        }
    }

    private void ilgSanCikar(Context context, String sanad, LinearLayout linearLayout){
        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
        ArrayList ilgsanar = (ArrayList) databaseHelper.ilgsancek();
        for (int i=0; i<ilgsanar.size(); i++){
            String[] ilgsanik = ilgsanar.get(i).toString().split(">");
            if (ilgsanik[1].equalsIgnoreCase(sanad)){
                Ilgsanlar ilgsanlar = new Ilgsanlar(Integer.parseInt(ilgsanik[0]),ilgsanik[1]);
                Boolean delete = databaseHelper.deleteilgsan(ilgsanlar);
                if (!delete){
                    Snackbar.make(linearLayout,sanad+" Çıkarılıdı...",BaseTransientBottomBar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(linearLayout,sanad+" Çıkarılamadı...",BaseTransientBottomBar.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void ilgsansecgec(String kulad, String sifre,String aydiz0, String aydiz1){
        final int[] countin = {0};
        final Boolean[] gecti = {false};
        PopupWindow popUp = new PopupWindow(MainActivity.this);
        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
        ArrayList everysan = (ArrayList) databaseHelper.everysans();
        ArrayList everysang = (ArrayList) databaseHelper.everysansg();

        everysan.addAll(everysang);

        LinearLayout layout = (LinearLayout) LinearLayout.inflate(MainActivity.this,R.layout.ilgsangirlay,null);

        TextView secilgtext = layout.findViewById(R.id.secilgtext);
        LinearLayout ilgscrolic = layout.findViewById(R.id.ilgscrolic);
        TextView devambutilg = layout.findViewById(R.id.devambutilg);

        secilgtext.setTag(bastex);
        devambutilg.setTag(bastex);
        ViewEditor(secilgtext,20,20);
        ViewEditor(devambutilg,25,25);

        devambutilg.setPadding(glowidth/60,gloheight/60,glowidth/60,gloheight/60);
        secilgtext.setPadding(glowidth/60,gloheight/60,glowidth/60,gloheight/60);

        devambutilg.setEnabled(false);

        for (int i=0; i<everysan.size(); i+=3){
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setWeightSum(3);
            ilgscrolic.addView(linearLayout);
            int a=i+3;
            if (a<=everysan.size()){
                for (int c=i; c<a; c++){

                    final Boolean[] touchdet = {true};

                    String[] sandizo = everysan.get(c).toString().split(">");
                    LinearLayout sanlay = (LinearLayout) LinearLayout.inflate(this,R.layout.everysanlarlay,null);
                    LinearLayout anapinlay = sanlay.findViewById(R.id.anapinlay);
                    ImageView anasanimg = sanlay.findViewById(R.id.anasanimg);
                    TextView anasantex = sanlay.findViewById(R.id.anasantex);

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, gloheight/5,1);
                    layoutParams.setMargins(glowidth/80,gloheight/80,glowidth/80,gloheight/80);
                    sanlay.setLayoutParams(layoutParams);

                    int drawableID = this.getResources().getIdentifier(sandizo[3], "drawable", getPackageName());
                    anasanimg.setBackgroundResource(drawableID);

                    anasantex.setTag(bastex);
                    ViewEditor(anasantex,35,35);
                    anasantex.setText(sandizo[1]);

                    File imgFile = new  File(MainActivity.this.getExternalFilesDir(null)+"/msapp/sanresnew/"+sandizo[3]);

                    if(imgFile.exists()){

                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        BitmapDrawable ob = new BitmapDrawable(getResources(), myBitmap);
                        anasanimg.setBackground(ob);

                    }

                    anasanimg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (touchdet[0]){
                                touchdet[0] = false;
                                anasanimg.setImageResource(R.drawable.ic_checkico);
                                ilgSanEkle(v.getContext(),sandizo[1],layout);
                                countin[0]++;
                                if (countin[0]==3){
                                    devambutilg.setEnabled(true);
                                    devambutilg.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Kulbi kulbi = new Kulbi(-1,kulad,sifre,aydiz0,aydiz1);
                                            DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);

                                            Boolean addkul = databaseHelper.addkulbi(kulbi);
                                            if (addkul){
                                                gecti[0] = true;
                                                popUp.dismiss();
                                                favcek(kulad);
                                                profotocek();
                                                gamestatcek(kulad);
                                                Snackbar snackbar = Snackbar.make(findViewById(R.id.mainact),"Giriş Başarılı...",BaseTransientBottomBar.LENGTH_INDEFINITE);
                                                snackbar.setAction("Profile Git", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        ProgressDialog progressDialog = new ProgressDialog(v.getContext());
                                                        progressDialog.setCancelable(false);
                                                        progressDialog.setMessage("Lütfen Bekleyiniz...");
                                                        progressDialog.show();
                                                        Handler handler = new Handler();
                                                        Runnable runnable = new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                Intent intent = new Intent(v.getContext(),kulprofilo.class);
                                                                startActivity(intent);
                                                                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                                                            }
                                                        };
                                                        handler.postDelayed(runnable,5000);
                                                    }
                                                });
                                                snackbar.show();
                                            } else {
                                                gecti[0] = false;
                                                popUp.dismiss();
                                                Toast.makeText(MainActivity.this,"Giriş Başarısız Oldu", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }

                            } else {
                                touchdet[0] = true;
                                anasanimg.setImageResource(R.drawable.transparentshape);
                                ilgSanCikar(v.getContext(),sandizo[1],layout);
                                countin[0]--;
                            }
                        }
                    });

                    linearLayout.addView(sanlay);
                }
            }
        }

        popUp.setOnDismissListener(() -> darkenBackground(1f));

        popUp.setOutsideTouchable(true);
        popUp.setFocusable(true);
        popUp.setContentView(layout);
        popUp.update(0, 0, glowidth/2+glowidth/3, gloheight/2+gloheight/3);
        popUp.showAtLocation(layout,Gravity.CENTER,0,0);
        darkenBackground(0.3f);
    }

    private void expandImage(Uri uri, String filename, Context context){
        PopupWindow popUp = new PopupWindow(context);
        LinearLayout layout = (LinearLayout) LinearLayout.inflate(context,R.layout.expandimagelay,null);
        TextView baslik = layout.findViewById(R.id.expandpdfbas);
        TouchImageView pdfView = layout.findViewById(R.id.expandpdfviewer);
        ViewEditor(baslik,25,25);
        baslik.setText(filename);
        pdfView.setImageURI(uri);
        ImageView closeexpand = layout.findViewById(R.id.expandpdfclose);

        closeexpand.setOnClickListener(v -> popUp.dismiss());
        popUp.setOutsideTouchable(true);
        popUp.setFocusable(true);
        popUp.setContentView(layout);
        popUp.update(0, 0, glowidth, gloheight);
        popUp.showAtLocation(layout,Gravity.CENTER,0,0);

    }

    private void shareScreenShot(File imageFile, Context context) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri photoURI = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", imageFile);
        intent.setDataAndType(photoURI, "image/*");
        intent.putExtra(Intent.EXTRA_STREAM,photoURI);
        startActivity(intent);
    }

    private void openImageGallery(View view){

        Context context = MainActivity.this;

        File snapfolder = new File(context.getExternalFilesDir(null)+"/msapp/snapshots");
        if (!snapfolder.exists()){
            snapfolder.mkdirs();
        }




        File[] snaplists = snapfolder.listFiles();


        PopupWindow popUp = new PopupWindow(context);
        LinearLayout layout = (LinearLayout) LinearLayout.inflate(context,R.layout.imagegallerysave,null);

        TextView imgalbaslik = layout.findViewById(R.id.imgalbaslik);
        ImageView imgalshut = layout.findViewById(R.id.imgalshut);
        LinearLayout imgaliclay = layout.findViewById(R.id.imgaliclay);

        ViewEditor(imgalbaslik,20,20);

        imgalshut.setOnClickListener(v -> popUp.dismiss());

        if (snaplists.length>0){
            for (int i=0; i<snaplists.length; i++){
                LinearLayout linearLayout = (LinearLayout) LinearLayout.inflate(context,R.layout.imgalicitems,null);
                imgaliclay.addView(linearLayout);

                ImageView imageView = linearLayout.findViewById(R.id.imgcalfile);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(glowidth/2+glowidth/8, 0,15));
                imageView.setImageURI(Uri.fromFile(snaplists[i]));

                EditText editText = linearLayout.findViewById(R.id.imgalrename);
                editText.setText(snaplists[i].getName());
                ViewEditor(editText,45,45);

                ImageView renamesaveimg = linearLayout.findViewById(R.id.renamesaveimg);
                ImageView shareimgalic =  linearLayout.findViewById(R.id.shareimgalic);
                ImageView deleteimgalic = linearLayout.findViewById(R.id.deleteimgalic);
                ImageView expandimgalic = linearLayout.findViewById(R.id.expandimgalic);

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
                layoutParams.setMargins(0,0,gloheight/60,0);
                linearLayout.setLayoutParams(layoutParams);

                int finalI = i;
                expandimgalic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        expandImage(Uri.fromFile(snaplists[finalI]),snaplists[finalI].getName(),context);
                    }
                });

                deleteimgalic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgaliclay.removeView(linearLayout);
                        Snackbar snackbar = Snackbar.make(imgaliclay,"Çalışma Silindi...",BaseTransientBottomBar.LENGTH_LONG);
                        snackbar.setAction("Geri Al",
                                v1 -> imgaliclay.addView(linearLayout));
                        snackbar.show();
                        snackbar.addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                            @Override
                            public void onDismissed(Snackbar transientBottomBar, int event) {
                                super.onDismissed(transientBottomBar, event);
                                if (event == DISMISS_EVENT_TIMEOUT){
                                    if (snaplists[finalI].delete()){

                                    }
                                }
                            }
                        });

                    }
                });


                renamesaveimg.setOnClickListener(v -> {
                    File folder = new File(v.getContext().getExternalFilesDir(null)+"/msapp/snapshots");
                    if (folder.exists()){
                        if (snaplists[finalI].exists()){
                            File to = new File(folder,editText.getText().toString()+".jpg");
                            if (snaplists[finalI].renameTo(to)){
                                Toast.makeText(context, "Dosya ismi güncellendi...", Toast.LENGTH_SHORT).show();
                                popUp.dismiss();
                            }

                        }


                    }
                });

                shareimgalic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareScreenShot(snaplists[finalI],context);
                    }
                });

            }
        }



        popUp.setOutsideTouchable(true);
        popUp.setFocusable(true);
        popUp.setContentView(layout);
        popUp.update(0, 0, glowidth, gloheight/2);
        popUp.showAtLocation(layout,Gravity.CENTER,0,0);
    }

    private void initScoreLay(LinearLayout linearLayout, int code){

    }

    private void scoreBoardPopup(){
        PopupWindow popUp = new PopupWindow(MainActivity.this);
        LinearLayout layout = (LinearLayout) LinearLayout.inflate(MainActivity.this,R.layout.scoreboard_main,null);

        TextView skorlarbas = layout.findViewById(R.id.baslikskorpop);
        LinearLayout horictag = layout.findViewById(R.id.scoreicboard);
        LinearLayout siraiclayo = layout.findViewById(R.id.siraiclayo);
        TextView totalpuan = layout.findViewById(R.id.totalpuan);
        TextView totalheart = layout.findViewById(R.id.totalheart);
        ImageView closeskorpop  = layout.findViewById(R.id.closeskorpop);
        TextView  lastweekbest  = layout.findViewById(R.id.lastweekbest);
        TextView  lastmonthbest = layout.findViewById(R.id.lastmonthbest);
        TextView  alltimesbest  = layout.findViewById(R.id.alltimesbest);

        ViewEditor(skorlarbas,15,15);
        ViewEditor(totalpuan ,20,20);
        ViewEditor(totalheart,20,20);
        ViewEditor(lastweekbest ,25,25);
        ViewEditor(lastmonthbest,25,25);
        ViewEditor(alltimesbest,25,25);

        totalpuan.setText(puantextus.getText().toString());
        totalheart.setText(hearttextus.getText().toString());

        closeskorpop.setOnClickListener(v -> popUp.dismiss());

        lastweekbest .setBackgroundResource(R.drawable.canodulbg);
        lastmonthbest.setBackgroundResource(R.drawable.girbutbg);
        alltimesbest.setBackgroundResource(R.drawable.girbutbg);
        skortableguncelle(siraiclayo,"hafta");

        lastweekbest.setOnClickListener(v -> {

            lastweekbest .setBackgroundResource(R.drawable.canodulbg);
            lastmonthbest.setBackgroundResource(R.drawable.girbutbg);
            alltimesbest.setBackgroundResource(R.drawable.girbutbg);

            skortableguncelle(siraiclayo,"hafta");

        });

        lastmonthbest.setOnClickListener(v -> {

            lastweekbest .setBackgroundResource(R.drawable.girbutbg);
            lastmonthbest.setBackgroundResource(R.drawable.canodulbg);
            alltimesbest.setBackgroundResource(R.drawable.girbutbg);

            skortableguncelle(siraiclayo,"ay");
        });

        alltimesbest.setOnClickListener(v -> {

            lastweekbest .setBackgroundResource(R.drawable.girbutbg);
            lastmonthbest.setBackgroundResource(R.drawable.girbutbg);
            alltimesbest.setBackgroundResource(R.drawable.canodulbg);

            skortableguncelle(siraiclayo,"tumzaman");
        });


        popUp.setOnDismissListener(() -> darkenBackground(1f));

        popUp.setOutsideTouchable(true);
        popUp.setFocusable(true);
        popUp.setContentView(layout);
        popUp.update(0, 0, glowidth/2+glowidth/3, gloheight/2+gloheight/3);
        popUp.showAtLocation(layout,Gravity.CENTER,0,0);
        darkenBackground(0.3f);
    }

    private void skortableguncelle(LinearLayout linearLayout, String hangi){
        List<Kulbi> kulbis = databaseHelper.kulbicek();
        String kuladiso = "";
        if (!kulbis.isEmpty()){
            kuladiso = kulbis.get(0).getKulad();
        }
        linearLayout.removeAllViews();
        String finalKuladiso = kuladiso;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL3, response -> {
            try {
                response = response.trim();
                if (!response.equalsIgnoreCase("no")||!response.equalsIgnoreCase("")){
                    String[] bol = response.split("eXeXeXe");
                    String[] kuladlar = bol[0].split(">");
                    String[] kulpuanlar = bol[1].split(">");

                    ArrayList<Integer> intpuanlar = new ArrayList<>();
                    ArrayList<String> tumkullar = new ArrayList<>();
                    for (int i=0; i<kulpuanlar.length; i++){
                        intpuanlar.add(Integer.parseInt(kulpuanlar[i]));
                        tumkullar.add(kuladlar[i]+" - "+kulpuanlar[i]);
                    }

                    Collections.sort(intpuanlar);
                    Collections.reverse(intpuanlar);

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(MATCHPARENT,WRAPCONTENT);
                    layoutParams.setMargins(glowidth/100,gloheight/90,glowidth/100,gloheight/90);

                    ArrayList<Integer> oduldizo = new ArrayList<>();
                    oduldizo.add(R.drawable.ic_medalking);
                    oduldizo.add(R.drawable.ic_medalcup);
                    oduldizo.add(R.drawable.ic_medalstar);
                    oduldizo.add(R.drawable.ic_medalflag);
                    int sira = 0;

                    for (int i=0; i<intpuanlar.size(); i++){
                        for (int j=0; j<tumkullar.size(); j++){
                            String[] boli = tumkullar.get(j).split(" - ");
                            if (intpuanlar.get(i)==Integer.parseInt(boli[1])){
                                sira++;
                                LinearLayout layout = (LinearLayout) LinearLayout.inflate(linearLayout.getContext(),R.layout.listedscoretable,null);
                                layout.setLayoutParams(layoutParams);
                                linearLayout.addView(layout);

                                ImageView kulimg = layout.findViewById(R.id.liscoreimg);
                                ImageView odullisted = layout.findViewById(R.id.odullisted);
                                TextView kuladtex = layout.findViewById(R.id.scorelistad);
                                TextView puanstex = layout.findViewById(R.id.listescoredpuantex);
                                LinearLayout listedscoredid = layout.findViewById(R.id.listedscoreid);
                                cekotherfoto(boli[0],kulimg);
                                kuladtex.setText("  "+boli[0]);
                                puanstex.setText("x "+boli[1]);
                                if (sira<=oduldizo.size()){
                                    odullisted.setImageResource(oduldizo.get(sira-1));
                                }

                                if (boli[0].equalsIgnoreCase(finalKuladiso)){
                                    listedscoredid.setBackgroundResource(R.drawable.canodulbg);
                                }

                            }
                        }
                    }

                }

            }
            catch (Exception e){

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> data = new HashMap<>();
                data.put("skorguncelcek",hangi);
                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.stop();
                request.cancel();
            }
        });
    }

    public void cekotherfoto(String kulad,ImageView imageView){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL3, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onResponse(String response) {
                try {
                    response = response.trim();
                    if (response.equalsIgnoreCase("no")){
                        imageView.setImageResource(R.drawable.ic_person_pin);
                    } else {
                        byte[] decodedString = Base64.decode(response, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        imageView.setImageBitmap(decodedByte);
                        if (response.equalsIgnoreCase("")){
                            imageView.setImageResource(R.drawable.ic_person_pin);
                        }
                    }


                }
                catch (Exception e){
                    imageView.setImageResource(R.drawable.ic_person_pin);
                }
            }
        }, e -> imageView.setImageResource(R.drawable.ic_person_pin)){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> data = new HashMap<>();
                data.put("otherfoto",kulad);

                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.stop();
                request.cancel();
            }
        });

    }



    private void skortablecek(String kulad){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL3, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onResponse(String response) {
                try {
                    response = response.trim();
                    DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                    List<Pdfkaylar> pdfkaylars = databaseHelper.kaypdfcek();
                    if (!response.equalsIgnoreCase("no") || !response.equalsIgnoreCase("")){

                        String[] dizio = response.split(">");
                        if (!pdfkaylars.isEmpty()){
                            for (int i=0; i<pdfkaylars.size(); i++){
                                if (!databaseHelper.deletepdfkay(pdfkaylars.get(i))){

                                }
                            }
                        }
                        Pdfkaylar pdfkaylar = new Pdfkaylar(-1,kulad,dizio[0],dizio[1],"0","0","0");
                        if (databaseHelper.addpdfkay(pdfkaylar)){

                        }
                    } else {

                    }

                }
                catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> data = new HashMap<>();
                data.put("skortablestat",kulad);
                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.stop();
                request.cancel();
            }
        });
    }

    private void gamestatcek(String kulad){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL3, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onResponse(String response) {
                try {
                    response = response.trim();
                    DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                    List<Ekcallar> ekcallars = databaseHelper.ekcalcek();
                    if (!response.equalsIgnoreCase("no")){

                        String[] dizio = response.split(">");
                        if (!ekcallars.isEmpty()){
                            for (int i=0; i<ekcallars.size(); i++){
                                if (!databaseHelper.deleteekcal(ekcallars.get(i))){

                                }
                            }
                        }
                        Ekcallar ekcallar = new Ekcallar(-1,dizio[1],"1",dizio[0],"0","0");
                        if (databaseHelper.addekcallar(ekcallar)){

                        }
                    } else {
                        if (!ekcallars.isEmpty()){
                            for (int i=0; i<ekcallars.size(); i++){
                                if (!databaseHelper.deleteekcal(ekcallars.get(i))){

                                }
                            }
                        }
                        Ekcallar ekcallar = new Ekcallar(-1,"5","1","0","0","0");
                        if (databaseHelper.addekcallar(ekcallar)){

                        }
                    }

                }
                catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> data = new HashMap<>();
                data.put("cekgamepu",kulad);
                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.stop();
                request.cancel();
            }
        });
    }

    private void ilgsancont(String kulad, String sifre, PopupWindow popupWindow, String aydiz0, String aydiz1,ProgressDialog progressDialog){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onResponse(String response) {
                try {
                  response = response.trim();
                  ilgsayi = Integer.parseInt(response);
                  if (ilgsayi>=3){
                      Kulbi kulbi = new Kulbi(-1,kulad,sifre,aydiz0,aydiz1);
                      DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);

                      Boolean addkul = databaseHelper.addkulbi(kulbi);
                      if (addkul){

                          popupWindow.dismiss();
                          favcek(kulad);
                          ilgsancek(kulad);
                          gamestatcek(kulad);
                          skortablecek(kulad);

                          profotocek();
                          Handler handler = new Handler();
                          Runnable runnable = new Runnable() {
                              @Override
                              public void run() {
                                  progressDialog.dismiss();
                                  Intent intent = new Intent(MainActivity.this,kulprofilo.class);
                                  startActivity(intent);
                                  finish();
                                  overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                              }
                          };
                          handler.postDelayed(runnable,3000);


                      } else {
                          progressDialog.dismiss();
                          Toast.makeText(MainActivity.this,"Giriş Başarısız Oldu", Toast.LENGTH_SHORT).show();
                      }
                  } else {
                      progressDialog.dismiss();
                      popupWindow.dismiss();
                   ilgsansecgec(kulad,sifre,aydiz0,aydiz1);
                  }
                }
                catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> data = new HashMap<>();
                data.put("ilgsanconti",kulad);
                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.stop();
                request.cancel();
            }
        });

    }



    private void profgir (String kulad, String sifre, PopupWindow popupWindow){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Lütfen Bekleyiniz");
        progressDialog.setCancelable(true);
        progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
                @RequiresApi(api = Build.VERSION_CODES.P)
                @Override
                public void onResponse(String response) {
                try {
                        response = response.trim();

                        String[] aydiz = response.split(">");

                        ilgsancont(kulad,sifre,popupWindow,aydiz[0],aydiz[1],progressDialog);


                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this,"Giriş Başarısız Oldu, Tekrar Deneyin", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Toast.makeText(MainActivity.this,"Giriş Başarısız Oldu, Tekrar Deneyin", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> data = new HashMap<>();
                data.put("usernamekul",kulad);
                data.put("passwordkul",sifre);

                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.stop();
                request.cancel();
            }
        });
    }

    private void newSanPopup(View buti, String sanad, DatabaseHelper databaseHelper, String sanimg, Boolean isStored){
        Context context = buti.getContext();
        List<Notarama> tumsarklar = databaseHelper.aramanota();
        List<Notarama> tumsarklarg = databaseHelper.aramanotag();
        tumsarklar.addAll(tumsarklarg);
        PopupWindow popUp = new PopupWindow(context);
        LinearLayout layout = (LinearLayout) LinearLayout.inflate(context,R.layout.sanpopup,null);

        ImageView sanpopimg = layout.findViewById(R.id.sanpopimg);
        TextView sanpopad = layout.findViewById(R.id.sanpopad);
        SearchView popsearching = layout.findViewById(R.id.popsearching);
        LinearLayout sanpophoriclay = layout.findViewById(R.id.sanpophoriclay);
        ImageView closesanpo = layout.findViewById(R.id.closesanpo);

        closesanpo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp.dismiss();
            }
        });

        ViewEditor(sanpopad,20,20);
        sanpopad.setText(sanad);
        popsearching.setQueryHint("Buradan ara...");

        int drawableID2 = MainActivity.this.getResources().getIdentifier(sanimg, "drawable", getPackageName());
        if (drawableID2 == 0){
                    File imgFile = new  File(MainActivity.this.getExternalFilesDir(null)+"/msapp/sanresnew/"+sanimg);
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                sanpopimg.setImageBitmap(myBitmap);
            }
        } else {
            sanpopimg.setImageResource(drawableID2);
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(glowidth/80,gloheight/160,glowidth/80,gloheight/160);
        for (int i=0; i<tumsarklar.size(); i++){
            String[] bol = tumsarklar.get(i).getNotaname().split(" - ");
            if (sanad.equalsIgnoreCase(bol[0])){
                LinearLayout linearLayout = (LinearLayout) LinearLayout.inflate(context,R.layout.listedsongshared,null);
                linearLayout.setLayoutParams(layoutParams);
                ImageView imageView = linearLayout.findViewById(R.id.sanimge);
                TextView textView = linearLayout.findViewById(R.id.sanadi);
                ImageView sharesan = linearLayout.findViewById(R.id.sharesan);
                msarkibakagir(sharesan,tumsarklar.get(i).getNotaname());
                sharesan.setImageResource(R.drawable.ic_play_circle_outline);
                textView.setText(" "+bol[1]);
                if (drawableID2 == 0){
                    File imgFile = new  File(MainActivity.this.getExternalFilesDir(null)+"/msapp/sanresnew/"+sanimg);
                    if(imgFile.exists()){
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        imageView.setImageBitmap(myBitmap);
                    }
                } else {
                    imageView.setImageResource(drawableID2);
                }
                sanpophoriclay.addView(linearLayout);

            }

        }



        popUp.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground(1f);
            }
        });

        popsearching.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                for (int i=0; i<sanpophoriclay.getChildCount(); i++){
                    LinearLayout linearLayout = (LinearLayout) sanpophoriclay.getChildAt(i);
                    linearLayout.setVisibility(View.GONE);
                    TextView textView = linearLayout.findViewById(R.id.sanadi);
                    if (textView.getText().toString().toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))){
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                }
                return false;
            }
        });



        popUp.setOutsideTouchable(true);
        popUp.setFocusable(true);
        popUp.setContentView(layout);
        popUp.update((glowidth)/10, (gloheight)/10, glowidth/2+glowidth/4, gloheight/2+gloheight/4);
        popUp.showAsDropDown(buti);
        darkenBackground(0.3f);

    }

    private void sifcek(EditText kuladed, EditText kulsifed, String email, LinearLayout layout){
        Context context = kuladed.getContext();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL2 , new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onResponse(String response) {
                try {
                    response = response.trim();
                    if (!response.equalsIgnoreCase("no")){
                        String[] bol = response.split("<");
                        kuladed.setText(bol[0]);
                        kulsifed.setText(bol[1]);
                        Snackbar.make(layout,"Kullanıcı Adı ve Şifre Yansıtıldı...",BaseTransientBottomBar.LENGTH_LONG).show();
                    }  else {
                        Snackbar.make(layout,"E-mail kayıtlı değil...",BaseTransientBottomBar.LENGTH_LONG).show();
                    }

                }
                catch (Exception e){
                    Snackbar.make(layout,"E-mail kayıtlı değil...",BaseTransientBottomBar.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Snackbar.make(layout,"E-mail kayıtlı değil...",BaseTransientBottomBar.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> data = new HashMap<>();
                data.put("email", email);
                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.stop();
                request.cancel();
            }
        });
    }

    private void girisprof(Context context){
        PopupWindow popUp = new PopupWindow(context);
        View layout = View.inflate(context,R.layout.prof_giris,null);

        TextView kaygirtexgir = layout.findViewById(R.id.kaygirtexgir);
        EditText kuledtexgir = layout.findViewById(R.id.kuledtexgir);
        EditText sifedtexgir = layout.findViewById(R.id.sifedtexgir);
        TextView girisyapin = layout.findViewById(R.id.girisyapin);
        TextView sifunutun = layout.findViewById(R.id.sifunutun);
        LinearLayout sifunutlayo = layout.findViewById(R.id.sifunutlayo);
        ImageView closegirickay = layout.findViewById(R.id.closegirickay);

        kaygirtexgir.setTag("bastex");
        kuledtexgir.setTag("basedit");
        sifedtexgir.setTag("basedit");
        girisyapin.setTag("bastex");
        sifunutun.setTag("bastex");

        ViewEditor(kaygirtexgir,15,15);
        ViewEditor(kuledtexgir,20,20);
        ViewEditor(sifedtexgir,20,20);
        ViewEditor(girisyapin,11,11);
        ViewEditor(sifunutun,25,25);

        closegirickay.setOnClickListener(v -> popUp.dismiss());
        girisyapin.setOnClickListener(v -> profgir(kuledtexgir.getText().toString(),sifedtexgir.getText().toString(),popUp));

        sifunutun.setOnClickListener(v -> {
            sifunutlayo.removeAllViews();
            EditText editText = new EditText(v.getContext());
            editText.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,17));
            editText.setTextColor(Color.WHITE);
            editText.setTag(R.string.basedit);
            ViewEditor(editText,30,30);
            editText.setGravity(Gravity.CENTER);
            editText.setHint("E-mail adresinizi girin...");
            editText.setFocusable(true);
            editText.setTypeface(ResourcesCompat.getFont(context,R.font.raleway_medium));
            editText.setFocusableInTouchMode(true);
            editText.setCursorVisible(false);
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            editText.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.msdef)));
            sifunutlayo.addView(editText);

            ImageView imageView = new ImageView(v.getContext());
            imageView.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,3));
            imageView.setImageResource(R.drawable.ic_okfriend);
            sifunutlayo.addView(imageView);

            sifunutlayo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sifcek(kuledtexgir,sifedtexgir,editText.getText().toString().trim(), (LinearLayout) layout);
                }
            });



        });



        popUp.setOnDismissListener(() -> darkenBackground(1f));

        popUp.setOutsideTouchable(true);
        popUp.setFocusable(true);
        popUp.setContentView(layout);
        popUp.showAtLocation(layout,Gravity.CENTER,0,0);
        popUp.update(0, 0, glowidth/2+glowidth/3, gloheight/2+gloheight/3);
        darkenBackground(0.3f);
    }

    private void girisAcPopup(Context context, View buti){
         PopupWindow popUp = new PopupWindow(context);
         View layout = View.inflate(context,R.layout.giris_popup,null);

        ImageView closegiric = layout.findViewById(R.id.closegiric);
        TextView basgirtex = layout.findViewById(R.id.basgirtex);
        LinearLayout laygirhor = layout.findViewById(R.id.laygirhor);
        HorizontalScrollView horgirpop = layout.findViewById(R.id.horgirpop);
        TextView createproftex = layout.findViewById(R.id.createproftex);
        TextView oncedenproftex = layout.findViewById(R.id.oncedenproftex);
        TextView girisbuti = layout.findViewById(R.id.girisbuti);

        basgirtex.setTag("bastex");
        createproftex.setTag("bastex");
        oncedenproftex.setTag("bastex");
        girisbuti.setTag("bastex");

        ViewEditor(basgirtex,15,15);
        ViewEditor(createproftex,12,12);
        ViewEditor(oncedenproftex,25,25);
        ViewEditor(girisbuti,13,13);

        girisbuti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp.dismiss();
                girisprof(context);
            }
        });

        createproftex.setOnClickListener(v -> {
            popUp.dismiss();
            Intent intent = new Intent(v.getContext(),CreateMsProf.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            finish();

        });

        for (int i=0; i<laygirhor.getChildCount(); i++){
            CardView cardView = (CardView) laygirhor.getChildAt(i);
            cardView.setLayoutParams(new LinearLayout.LayoutParams(glowidth/2+glowidth/4,LinearLayout.LayoutParams.MATCH_PARENT));

            LinearLayout linearLayout = (LinearLayout) cardView.getChildAt(0);

            TextView textView = (TextView) linearLayout.getChildAt(1);
            textView.setTag("bastex");
            ViewEditor(textView,25,25);
        }


        closegiric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp.dismiss();
                darkenBackground(1f);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    int maxDeg = horgirpop.getWidth()+glowidth+glowidth/2;

                    horgirpop.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                        @Override
                        public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                        }
                    });


                    CountDownTimer countDownTimer1;
                    final CountDownTimer[] countDownTimer2 = new CountDownTimer[1];

                    countDownTimer1 = new CountDownTimer(maxDeg*10,1) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            horgirpop.setScrollX((int) ((maxDeg)-(millisUntilFinished/10)));
                        }

                        @Override
                        public void onFinish() {
                             countDownTimer2[0] =  new CountDownTimer(maxDeg*10,1){
                               @Override
                               public void onTick(long millisUntilFinished) {
                                   horgirpop.setScrollX((int) millisUntilFinished/10);
                               }
                               @Override
                               public void onFinish() {

                               }

                           }.start();
                        }
                    }.start();

                }
            };
            handler.postDelayed(runnable,500);
        }


        popUp.setOutsideTouchable(false);
        popUp.setFocusable(false);
        popUp.setContentView(layout);
        popUp.showAtLocation(layout,Gravity.CENTER,0,0);
        popUp.update(0, 0, glowidth/2+glowidth/3, gloheight/2+gloheight/3);
        darkenBackground(0.3f);


    }

    private void favkaldir(String favsanad, String favsarkad, ImageView imageView, LinearLayout linearLayout, DatabaseHelper databaseHelper, Boolean favcek){
        List<Favsar> favsarlar = databaseHelper.favsarcekdb();

        for (int i=0; i<favsarlar.size(); i++) {
            if (favsarlar.get(i).getFavad().equalsIgnoreCase(favsanad)&&favsarlar.get(i).getFavsanad().equalsIgnoreCase(favsarkad)){
                Boolean deletefav = databaseHelper.deletefavsar(favsarlar.get(i));
                if (!deletefav){
                    yoyoclas(Techniques.Wobble,1000,0,imageView);
                    imageView.setImageResource(R.drawable.ic_favlargebos);
                    Snackbar snackbar = Snackbar.make(linearLayout, favsarkad+" favorilerden kaldırıldı", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        }
    }

    private boolean favekle(String favsanad, String favsarkad, String favvidurl, String favpdf, String favres, String favkul){
        if (databaseHelper.addfavsar(new Favsar(-1,favsanad,favsarkad,favvidurl,favpdf,favres,favkul))){
            return true;
        } else {
            return false;
        }
    }

    private void msarkibakagir(View view,String sarkad){

        view.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(),msarkibak.class);
            intent.putExtra("sarkad",sarkad);
            intent.putExtra("whereback","mainactivity");
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        });

    }

    private void sarkcek(String sarkad, ImageView imageView,SeekBar msaraseek){
        ProgressDialog progressDialog = new ProgressDialog(imageView.getContext());
        progressDialog.setMessage("Şarkı Hazırlanıyor...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://www.xn--mziksayfas-9db95d.com/songcek.php", new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onResponse(String response) {
                response = response.trim();
                if (!response.equalsIgnoreCase("no")){
                      uriyolmp = response;
                      prepareMp3(imageView,progressDialog,msaraseek);
                        } else {
                    progressDialog.dismiss();
                }

            }
        }, error -> {
            Toast.makeText(MainActivity.this,"internette bir sorun olabilir, sonra tekrar deneyin", Toast.LENGTH_SHORT).show();
             progressDialog.dismiss();
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> data = new HashMap<>();
                data.put("song",sarkad);
                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);

        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.stop();
                request.cancel();
            }
        });
    }

    private void prepareMp3(ImageView imageView, ProgressDialog progressDialog, SeekBar msaraseek){
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );

        if (isNetworkAvailable()){
            try {
                String[] encoders = uriyolmp.split("");
                for (int i=0; i<encoders.length; i++){
                    if (encoders[i].equals(" ")){
                        uriyolmp = uriyolmp.replace(" ","%20");
                    }

                    if (encoders[i].equals("ı")){
                        uriyolmp = uriyolmp.replace("ı","%C4%B1");
                    }

                    if (encoders[i].equals("ö")){
                        uriyolmp = uriyolmp.replace("ö","%C3%B6");
                    }

                    if (encoders[i].equals("ş")){
                        uriyolmp = uriyolmp.replace("ş","%C5%9F");
                    }

                    if (encoders[i].equals("ü")){
                        uriyolmp = uriyolmp.replace("ü","%C3%BC");
                    }

                    if (encoders[i].equals("ğ")){
                        uriyolmp = uriyolmp.replace("ğ","%C4%9F");
                    }

                    if (encoders[i].equals("ç")){
                        uriyolmp = uriyolmp.replace("ç","%C3%A7");
                    }

                    if (encoders[i].equals("İ")){
                        uriyolmp = uriyolmp.replace("İ","%C4%B0");
                    }

                    if (encoders[i].equals("Ç")){
                        uriyolmp = uriyolmp.replace("Ç","%C3%87");
                    }

                    if (encoders[i].equals("Ş")){
                        uriyolmp = uriyolmp.replace("Ş","%C5%9E");
                    }

                    if (encoders[i].equals("Ö")){
                        uriyolmp = uriyolmp.replace("Ö","%C3%96");
                    }

                    if (encoders[i].equals("Ü")){
                        uriyolmp = uriyolmp.replace("Ü","%C3%9C");
                    }

                }
                mediaPlayer.setDataSource(MainActivity.this,Uri.parse(uriyolmp));
                mediaPlayer.prepareAsync(); // might take long! (for buffering, etc)

            } catch (IOException e) {
                snackbar("Şarkı Hazırlanamadı..","Tekrar Dene",findViewById(R.id.mainact));
            }

        } else {
            Toast.makeText(application, "Lütfen İnternete Bağlanın...", Toast.LENGTH_SHORT).show();
        }

        mediaPlayer.setOnPreparedListener(mp -> {
            curimgpop = imageView;
            curseekpop = msaraseek;
            timer = new Timer();
            mp.start();
            msaraseek.setEnabled(true);
            progressDialog.dismiss();
            imageView.setImageResource(R.drawable.ic_pause_circle_outline);
            msaraseek.setMax(mp.getDuration());
            final Boolean[] detectTouch = {false};
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (mediaPlayer!=null){
                        if (!detectTouch[0] && mediaPlayer.isPlaying()){
                            msaraseek.setProgress(mp.getCurrentPosition());
                        }
                    }


                }
            },0,100);



            msaraseek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    detectTouch[0] =true;
                      mediaPlayer.pause();
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    detectTouch[0] = false;
                      mp.seekTo(seekBar.getProgress());
                      mediaPlayer.start();

                }
            });

            mediaPlayer.setOnCompletionListener(mp2 -> {
                mediaPlayer.pause();
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
                timer.cancel();
                imageView.setImageResource(R.drawable.ic_play_circle_outline);
                msaraseek.setEnabled(false);
                sarkcek(currentSark,imageView,msaraseek);
            });

        });




    }

    private void scrollMotor(int addCount,List<Notarama> tumsarklar,
                             ArrayList querysarklar, ArrayList getSanim, ArrayList getSanAd,
                             List<Favsar> tumfavsarlar, List<Kulbi> kulcek,
                             GridLayout gridLayout, LinearLayout layout, Context context){

        int finalCount = addCount+10;

        if (addCount+10>tumsarklar.size()){
             finalCount = tumsarklar.size();
        }




        for (int i=addCount; i<finalCount; i++) {
            String[] bolsark = tumsarklar.get(i).toString().split(">");
            String[] sarkdiz = bolsark[1].split(" - ");
            String[] vidurldiz = bolsark[2].split("/");
            querysarklar.add(bolsark[1].toLowerCase(Locale.ROOT));
            String imgRes = getSanim.get(getSanAd.indexOf(sarkdiz[0])).toString();

            LinearLayout linearLayout = (LinearLayout) LinearLayout.inflate(context, R.layout.mssonglayy, null);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(glowidth/2-glowidth/20,gloheight/3-gloheight/20);
            layoutParams.setMargins(glowidth/120,gloheight/120,glowidth/120,gloheight/120);
            linearLayout.setLayoutParams(layoutParams);

            ImageView sanim = linearLayout.findViewById(R.id.singerimg);
            TextView msarasarkad = linearLayout.findViewById(R.id.singernamee);
            TextView msarasarkad2 = linearLayout.findViewById(R.id.songnamee);
            SeekBar msaraseekbar = linearLayout.findViewById(R.id.singerseek);
            ImageView msaraopensong = linearLayout.findViewById(R.id.openwork);
            ImageView msarafavsong = linearLayout.findViewById(R.id.addfavsong);
            ImageView mssharesong = linearLayout.findViewById(R.id.sharesongi);

            mssharesong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shareYoutubeUris("https://youtu.be/"+vidurldiz[vidurldiz.length-1]);
                }
            });
            ViewEditor(msarasarkad,25,25);
            ViewEditor(msarasarkad2,35,35);

            int drawableID2 = MainActivity.this.getResources().getIdentifier(imgRes, "drawable", getPackageName());
            if (drawableID2 == 0){
                File imgFile = new  File(MainActivity.this.getExternalFilesDir(null)+"/msapp/sanresnew/"+imgRes);
                if(imgFile.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    BitmapDrawable ob = new BitmapDrawable(getResources(), myBitmap);
                    sanim.setBackground(ob);
                    msarkibakagir(msaraopensong,bolsark[1]);
                }
            } else {
                sanim.setBackgroundResource(drawableID2);
                msarkibakagir(msaraopensong,bolsark[1]);
            }

            sanim.setImageResource(R.drawable.ic_play_circle_outline);
            msarasarkad.setText(sarkdiz[0]);
            msarasarkad2.setText(sarkdiz[1]);
            gridLayout.addView(linearLayout);

            final Boolean[] favcheck = {false};

            if (tumfavsarlar.contains(bolsark[1])){
                msarafavsong.setImageResource(R.drawable.ic_favfill_large);
                favcheck[0] = true;
            }

            msarafavsong.setOnClickListener(v -> {
                if (favcheck[0] == true && kulcek.size()>0){
                    favkaldir(sarkdiz[0],sarkdiz[1],msarafavsong,layout,databaseHelper, favcheck[0]);
                } else if (favcheck[0] == false && kulcek.size()>0) {
                    if (favekle(sarkdiz[0],sarkdiz[1],vidurldiz[4],"local","local",kulcek.get(0).getKulad())){
                        yoyoclas(Techniques.Wobble,1000,0,msarafavsong);
                        msarafavsong.setImageResource(R.drawable.ic_favfill_large);
                        favcheck[0]=true;
                        Snackbar.make(layout,sarkdiz[1]+" eklendi...",Snackbar.LENGTH_LONG).show();
                    }
                }
            });

            msaraseekbar.setEnabled(false);
            sanim.setOnClickListener(v -> {
                if (mediaPlayer==null){
                    currentSark = bolsark[1];
                    sarkcek(bolsark[1],sanim,msaraseekbar);
                } else if (mediaPlayer!=null && currentSark.equalsIgnoreCase(bolsark[1])){
                    if (mediaPlayer.isPlaying()){
                        mediaPlayer.pause();
                        sanim.setImageResource(R.drawable.ic_play_circle_outline);
                    } else {
                        mediaPlayer.start();
                        sanim.setImageResource(R.drawable.ic_pause_circle_outline);
                    }
                } else if (mediaPlayer!=null&&!currentSark.equalsIgnoreCase(bolsark[1])){

                    mediaPlayer.pause();
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer.release();
                    mediaPlayer = null;
                    currentSark = bolsark[1];
                    curimgpop.setImageResource(R.drawable.ic_play_circle_outline);
                    timer.cancel();
                    curseekpop.setProgress(0);
                    curseekpop.setEnabled(false);
                    sarkcek(bolsark[1],sanim,msaraseekbar);
                }
            });

        }

    }

    private void shareYoutubeUris(String uritext) {
        Intent intent2 = new Intent(); intent2.setAction(Intent.ACTION_SEND);
        intent2.setType("text/plain");
        intent2.putExtra(Intent.EXTRA_TEXT, uritext );
        startActivity(Intent.createChooser(intent2, "Paylaş"));
    }

    private void searchMotor(String newText,GridLayout gridLayout,
                             List<Notarama> tumsarklar, List<Everysan> tumsanlar,
                             ArrayList tumfavsarlar,List<Kulbi> kulcek,LinearLayout layout,Context context){

        gridLayout.removeAllViews();

        List<Notarama> querySark = new ArrayList<Notarama>();
        ArrayList getSanAd = new ArrayList<String>();
        ArrayList getSanim = new ArrayList<String>();
        ArrayList querysarklar = new ArrayList<String>();

        for (int i=0; i<tumsarklar.size(); i++){
            if (tumsarklar.get(i).getNotaname().toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT))){
                querySark.add(tumsarklar.get(i));
                getSanAd.add(tumsarklar.get(i).getNotaname().split(" - ")[0]);
            }
        }

        for (int i=0; i<tumsanlar.size(); i++){
            for (int j=0; j<getSanAd.size(); j++){
                if (tumsanlar.get(i).getSanad().equalsIgnoreCase(getSanAd.get(j).toString())){
                    getSanim.add(tumsanlar.get(i).getSanimg());
                }
            }
        }


        for (int i=0; i<querySark.size(); i++) {
            String[] bolsark = querySark.get(i).toString().split(">");
            String[] sarkdiz = bolsark[1].split(" - ");
            String[] vidurldiz = bolsark[2].split("/");
            querysarklar.add(bolsark[1].toLowerCase(Locale.ROOT));
            String imgRes = getSanim.get(getSanAd.indexOf(sarkdiz[0])).toString();

            LinearLayout linearLayout = (LinearLayout) LinearLayout.inflate(context, R.layout.mssonglayy, null);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(glowidth/2-glowidth/20,gloheight/3-gloheight/20);
            layoutParams.setMargins(glowidth/120,gloheight/120,glowidth/120,gloheight/120);
            linearLayout.setLayoutParams(layoutParams);

            ImageView sanim = linearLayout.findViewById(R.id.singerimg);
            TextView msarasarkad = linearLayout.findViewById(R.id.singernamee);
            TextView msarasarkad2 = linearLayout.findViewById(R.id.songnamee);
            SeekBar msaraseekbar = linearLayout.findViewById(R.id.singerseek);
            ImageView msaraopensong = linearLayout.findViewById(R.id.openwork);
            ImageView msarafavsong = linearLayout.findViewById(R.id.addfavsong);
            ImageView mssharesong = linearLayout.findViewById(R.id.sharesongi);

            ViewEditor(msarasarkad,25,25);
            ViewEditor(msarasarkad2,35,35);

            mssharesong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shareYoutubeUris("https://youtu.be/"+vidurldiz[vidurldiz.length-1]);
                }
            });

            int drawableID2 = MainActivity.this.getResources().getIdentifier(imgRes, "drawable", getPackageName());
            if (drawableID2 == 0){
                File imgFile = new  File(MainActivity.this.getExternalFilesDir(null)+"/msapp/sanresnew/"+imgRes);
                if(imgFile.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    BitmapDrawable ob = new BitmapDrawable(getResources(), myBitmap);
                    sanim.setBackground(ob);
                    msarkibakagir(msaraopensong,bolsark[1]);
                }
            } else {
                sanim.setBackgroundResource(drawableID2);
                msarkibakagir(msaraopensong,bolsark[1]);
            }

            sanim.setImageResource(R.drawable.ic_play_circle_outline);
            msarasarkad.setText(sarkdiz[0]);
            msarasarkad2.setText(sarkdiz[1]);
            gridLayout.addView(linearLayout);

            final Boolean[] favcheck = {false};

            if (tumfavsarlar.contains(bolsark[1])){
                msarafavsong.setImageResource(R.drawable.ic_favfill_large);
                favcheck[0] = true;
            }

            msarafavsong.setOnClickListener(v -> {
                if (favcheck[0] == true && kulcek.size()>0){
                    favkaldir(sarkdiz[0],sarkdiz[1],msarafavsong,layout,databaseHelper, favcheck[0]);
                } else if (favcheck[0] == false && kulcek.size()>0) {
                    if (favekle(sarkdiz[0],sarkdiz[1],vidurldiz[4],"local","local",kulcek.get(0).getKulad())){
                        yoyoclas(Techniques.Wobble,1000,0,msarafavsong);
                        msarafavsong.setImageResource(R.drawable.ic_favfill_large);
                        favcheck[0]=true;
                        Snackbar.make(layout,sarkdiz[1]+" eklendi...",Snackbar.LENGTH_LONG).show();
                    }
                }
            });

            msaraseekbar.setEnabled(false);
            sanim.setOnClickListener(v -> {
                if (mediaPlayer==null){
                    currentSark = bolsark[1];
                    sarkcek(bolsark[1],sanim,msaraseekbar);
                } else if (mediaPlayer!=null && currentSark.equalsIgnoreCase(bolsark[1])){
                    if (mediaPlayer.isPlaying()){
                        mediaPlayer.pause();
                        sanim.setImageResource(R.drawable.ic_play_circle_outline);
                    } else {
                        mediaPlayer.start();
                        sanim.setImageResource(R.drawable.ic_pause_circle_outline);
                    }
                } else if (mediaPlayer!=null&&!currentSark.equalsIgnoreCase(bolsark[1])){

                    mediaPlayer.pause();
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer.release();
                    mediaPlayer = null;
                    currentSark = bolsark[1];
                    curimgpop.setImageResource(R.drawable.ic_play_circle_outline);
                    timer.cancel();
                    curseekpop.setProgress(0);
                    curseekpop.setEnabled(false);
                    sarkcek(bolsark[1],sanim,msaraseekbar);
                }
            });

        }
    }

    private void newSearchPopup(DatabaseHelper databaseHelper, Context context){
        List<Notarama> tumsarklar =  databaseHelper.aramanota();
        List<Everysan> tumsanlar =   databaseHelper.everysans();
        List<Notarama> tumsarklarg = databaseHelper.aramanotag();
        List<Everysan> tumsanlarg =  databaseHelper.everysansg();
        List<Favsar> tumfavlar = databaseHelper.favsarcekdb();
        List<Kulbi> kulcek = databaseHelper.kulbicek();
        ArrayList getSanAd = new ArrayList<String>();
        ArrayList getSanim = new ArrayList<String>();
        ArrayList querysarklar = new ArrayList<String>();
        ArrayList tumfavsarlar = new ArrayList<String>();

        tumsarklar.addAll(tumsarklarg);
        tumsanlar.addAll(tumsanlarg);

        if (tumfavlar.size()>0 && kulcek.size()>0){
            for (int i=0; i<tumfavlar.size(); i++){
                if (tumfavlar.get(i).getFavkul().equalsIgnoreCase(kulcek.get(0).getKulad())){
                   tumfavsarlar.add(tumfavlar.get(i).getFavad()+" - "+tumfavlar.get(i).getFavsanad());
                }
            }
        }




        for (int i=0; i<tumsanlar.size(); i++){
            getSanAd.add(tumsanlar.get(i).getSanad());
            getSanim.add(tumsanlar.get(i).getSanimg());
        }
        PopupWindow popUp = new PopupWindow(context);
        LinearLayout layout = (LinearLayout) LinearLayout.inflate(context,R.layout.mainsearchpop,null);

        AdView mAdView = layout.findViewById(R.id.msban);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        SearchView mainsearchview = layout.findViewById(R.id.mainsearchview);
        ScrollView scrollView = layout.findViewById(R.id.detectScroll);
        LinearLayout msiclay = layout.findViewById(R.id.msiclay);
        ProgressBar msicprog = layout.findViewById(R.id.msicprog);
        ImageView mscloseic = layout.findViewById(R.id.mscloseic);

        final int[] detectBottomint = {0};


        Handler handler = new Handler();
        Runnable runnable = () -> {
            mainsearchview.setFocusable(true);
            mainsearchview.setFocusableInTouchMode(true);
            msiclay.removeView(msicprog);


            GridLayout gridLayout = new GridLayout(context);
            gridLayout.setColumnCount(2);
            gridLayout.setRowCount(6);
            gridLayout.setUseDefaultMargins(true);
            gridLayout.setOrientation(GridLayout.HORIZONTAL);
            gridLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            msiclay.addView(gridLayout);

            scrollMotor(addCounter,tumsarklar,querysarklar,getSanim,getSanAd,tumfavsarlar,kulcek,gridLayout,layout,context);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                        if (!scrollView.canScrollVertically(1)){
                            detectBottomint[0]++;
                            if (detectBottomint[0]==1){
                                addCounter = addCounter+10;
                                scrollMotor(addCounter,tumsarklar,querysarklar,getSanim,getSanAd,tumfavsarlar,kulcek,gridLayout,layout,context);
                            }
                        }

                        if (scrollView.canScrollVertically(1)){
                            detectBottomint[0]=0;
                        }
                    }
                });
            }

            gridLayout.setRowCount((gridLayout.getChildCount()/3)+1);
            mainsearchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    newText = newText.toLowerCase(Locale.ROOT);
                    if (newText.isEmpty()){
                        addCounter=0;
                        gridLayout.removeAllViews();
                        scrollMotor(addCounter,tumsarklar,querysarklar,getSanim,getSanAd,tumfavsarlar,kulcek,gridLayout,layout,context);
                    } else {
                        searchMotor(newText,gridLayout,tumsarklar,tumsanlar,tumfavsarlar,kulcek,layout,context);
                    }

                    return false;
                }
            });



        };
        handler.postDelayed(runnable,2000);
        mscloseic.setOnClickListener(v ->
                popUp.dismiss());

        popUp.setOnDismissListener(() -> {
            addCounter = 0;
            if (mediaPlayer!=null){

                mediaPlayer.pause();
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer=null;
                timer.cancel();

            }

        });

        popUp.setOutsideTouchable(true);
        popUp.setFocusable(true);
        popUp.setContentView(layout);
        popUp.update((glowidth)/10, (gloheight)/10, glowidth, gloheight/2+gloheight/3+gloheight/6);
        popUp.showAsDropDown(mainsearch);

    }

    private void girisac(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                girisAcPopup(v.getContext(),v);
            }
        });
    }

    private void setScrollLay(){
       List<Ekcallar> alpuan = databaseHelper.ekcalcek();
        String[] alpuandiz = alpuan.get(0).toString().split(">");


        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Yükleniyor...");
        progressDialog.setCancelable(true);

        LinearLayout.LayoutParams laypar = new LinearLayout.LayoutParams(MATCHPARENT,(int) (gloheight/3));
        laypar.setMargins(gloheight/200,gloheight/90,gloheight/200,gloheight/120);

        LinearLayout.LayoutParams laypar2 = new LinearLayout.LayoutParams(MATCHPARENT,(int) (gloheight/4));
        laypar2.setMargins(gloheight/200,gloheight/90,gloheight/200,gloheight/120);


        LinearLayout kesfetlay = findViewById(R.id.kesfetlay);
        kesfetlay.setLayoutParams(laypar2);
        TextView kesprobas1 = findViewById(R.id.kesprobas1);
        TextView kesprobas3 = findViewById(R.id.kesprobas3);
        TextView kesprobas4 = findViewById(R.id.kesprobas4);
        TextView kesprobas5 = findViewById(R.id.kesprobas5);
        TextView kesproac1 = findViewById(R.id.kesproac1);
        TextView kesproac3 = findViewById(R.id.kesproac3);
        TextView kesproac4 = findViewById(R.id.kesproac4);
        TextView kesproac5 = findViewById(R.id.kesproac5);

        ImageView kesim1 = findViewById(R.id.kesim1);
        ImageView kesim3 = findViewById(R.id.kesim3);
        ImageView kesim4 = findViewById(R.id.kesim4);
        ImageView kesim5 = findViewById(R.id.kesim5);

        girisac(kesim1);
        girisac(kesim3);
        girisac(kesim4);
        girisac(kesim5);


        ViewEditor(kesprobas1,25,25);
        ViewEditor(kesprobas3,25,25);
        ViewEditor(kesprobas4,25,25);
        ViewEditor(kesprobas5,25,25);

        ViewEditor(kesproac1,38,38);
        ViewEditor(kesproac3,38,38);
        ViewEditor(kesproac4,38,38);
        ViewEditor(kesproac5,38,38);


        if (procontrol()){
            kesfetlay.setVisibility(View.GONE);
        }



        //Oyun Bölümü

        LinearLayout oyunlay = findViewById(R.id.oyunlay);
        LinearLayout oyunlaysa = findViewById(R.id.oyunlaysa);
        LinearLayout.LayoutParams layoy = (LinearLayout.LayoutParams) oyunlaysa.getLayoutParams();
        layoy.setMargins(glowidth/40,0,0,0);
        oyunlaysa.setLayoutParams(layoy);
        oyunlay.setLayoutParams(new LinearLayout.LayoutParams(MATCHPARENT,(int)(gloheight/3)));
        TextView cointext = findViewById(R.id.cointext);
        TextView hearttext = findViewById(R.id.hearttext);
        ImageView coinimg = findViewById(R.id.coinimg);
        TextView oyunlaybastex = findViewById(R.id.oyunbastexo);
        ImageView marketimg = findViewById(R.id.marketimg);
        hearttextus = hearttext;
        puantextus = cointext;

        ViewEditor(cointext,20,20);
        ViewEditor(hearttext,20,20);
        ViewEditor(oyunlaybastex,20,20);

        coinimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scoreBoardPopup();
            }
        });

        cointext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scoreBoardPopup();
            }
        });

        marketimg.setOnClickListener(v -> {
            marketPopup();
        });

        cointext.setText(" x"+alpuandiz[3]+"  ");
        hearttext.setText(" x"+alpuandiz[1]+"  ");

        LinearLayout oyunlino = findViewById(R.id.oyunlino);

        LinearLayout.LayoutParams getLay = new LinearLayout.LayoutParams(WRAPCONTENT,MATCHPARENT);
        getLay.setMargins(glowidth/40,gloheight/120,glowidth/60,gloheight/120);
        List<Everysan> everysanList = databaseHelper.everysans();
        List<Everysan> everysanListg = databaseHelper.everysansg();
        ArrayList getSanAd = new ArrayList<String>();
        ArrayList getSanim = new ArrayList<String>();
        everysanList.addAll(everysanListg);

        for (int i=0; i<everysanList.size(); i++){
            getSanAd.add(everysanList.get(i).getSanad());
            getSanim.add(everysanList.get(i).getSanimg());
        }

        for (int i=0; i<allgames.size(); i++){
            String sarkad = allgames.get(i).toString();
            String[] bolsar = sarkad.split(" - ");
            LinearLayout linearLayout = (LinearLayout) LinearLayout.inflate(oyunlino.getContext(),R.layout.mainsanlay,null);
            linearLayout.setLayoutParams(getLay);
            oyunlino.addView(linearLayout);
            ImageView sanimg = linearLayout.findViewById(R.id.mainsanimg);
            sanimg.setImageResource(R.drawable.ic_startgame);
            TextView santex = linearLayout.findViewById(R.id.mainsantex);
            String imgRes = getSanim.get(getSanAd.indexOf(bolsar[0])).toString();
            santex.setLayoutParams(new LinearLayout.LayoutParams(MATCHPARENT,gloheight/7));
            ViewEditor(sanimg,4,4);
            LinearLayout.LayoutParams impar = (LinearLayout.LayoutParams) sanimg.getLayoutParams();
            impar.setMargins(glowidth/30,gloheight/150,glowidth/30,0);
            sanimg.setLayoutParams(impar);
            ViewEditor(santex,25,25);
            santex.setText(bolsar[1]);
            int drawableID2 = MainActivity.this.getResources().getIdentifier(imgRes, "drawable", getPackageName());
            if (drawableID2 == 0){
                File imgFile = new  File(MainActivity.this.getExternalFilesDir(null)+"/msapp/sanresnew/"+imgRes);
                if(imgFile.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    BitmapDrawable ob = new BitmapDrawable(getResources(), myBitmap);
                    sanimg.setBackground(ob);
                }
            } else {
                sanimg.setBackgroundResource(drawableID2);
            }


            sanimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String[] splpuantex = hearttextus.getText().toString().split(" x");
                    String[] splpuantex2 = puantextus.getText().toString().split(" x");

                    if (Integer.parseInt(splpuantex[1].trim())>0){
                        int newmed = Integer.parseInt(splpuantex[1].trim())-1;
                        Ekcallar veri = new Ekcallar(Integer.parseInt(alpuandiz[0]),splpuantex[1].trim(),alpuandiz[2],splpuantex2[1].trim(),alpuandiz[4],alpuandiz[5]);
                        Ekcallar newveri = new Ekcallar(-1,Integer.toString(newmed),alpuandiz[2],splpuantex2[1].trim(),alpuandiz[4],alpuandiz[5]);
                        Boolean delveri = databaseHelper.deleteekcal(veri);
                        if (!delveri){
                            Boolean addveri = databaseHelper.addekcallar(newveri);
                            if (addveri){
                                Intent intent = new Intent(v.getContext(),GameArea.class);
                                intent.putExtra("sarkname",sarkad);
                                startActivity(intent);
                                finish();
                            }
                        }

                    } else {
                        noheartalert(sarkad,linearLayout);
                    }

                }
            });


        }

        LinearLayout onecikanlar = findViewById(R.id.onecikanlarlay);
        onecikanlar.setLayoutParams(laypar);
        TextView oncecik = findViewById(R.id.onceciktex);
        ViewEditor(oncecik,20,20);

        LinearLayout onhoriclay = findViewById(R.id.onhoriclay);

        LinearLayout.LayoutParams laysar = new LinearLayout.LayoutParams(WRAPCONTENT,MATCHPARENT);
        laysar.setMargins(glowidth/60,0,glowidth/60,0);
        List<Sarpopi> sarpopi = databaseHelper.sarpopicek();
        for (int i=0; i<sarpopi.size(); i++){
            String[] sarkad = sarpopi.get(i).getSarkad().split(" - ");
            LinearLayout linearLayout = (LinearLayout) LinearLayout.inflate(onecikanlar.getContext(),R.layout.mainsanlay,null);
            linearLayout.setLayoutParams(laysar);
            onhoriclay.addView(linearLayout);

            ImageView sanimg = linearLayout.findViewById(R.id.mainsanimg);
            TextView textView = linearLayout.findViewById(R.id.mainsantex);

            ViewEditor(sanimg,4,4);
            LinearLayout.LayoutParams impar = (LinearLayout.LayoutParams) sanimg.getLayoutParams();
            impar.setMargins(glowidth/30,gloheight/150,glowidth/30,0);
            sanimg.setLayoutParams(impar);

            textView.setText(sarkad[1]);
            ViewEditor(textView,25,25);
            textView.setLayoutParams(new LinearLayout.LayoutParams(MATCHPARENT,gloheight/6));

            String imgRes = getSanim.get(getSanAd.indexOf(sarkad[0])).toString();
            int drawableID2 = MainActivity.this.getResources().getIdentifier(imgRes, "drawable", getPackageName());
            if (drawableID2 == 0){
                File imgFile = new  File(MainActivity.this.getExternalFilesDir(null)+"/msapp/sanresnew/"+imgRes);
                if(imgFile.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    BitmapDrawable ob = new BitmapDrawable(getResources(), myBitmap);
                    sanimg.setBackground(ob);
                    msarkibakagir(sanimg,sarpopi.get(i).getSarkad());
                }
            } else {
                sanimg.setBackgroundResource(drawableID2);
                msarkibakagir(sanimg,sarpopi.get(i).getSarkad());

            }


        }

        //Reklamları ekle

        List<Notarama> tumsarklar = databaseHelper.aramanota();
        List<Notarama> tumsarklarg = databaseHelper.aramanotag();
        tumsarklar.addAll(tumsarklarg);
        ArrayList tumsarkad = new ArrayList<String>();
        ArrayList tumsarkpdf = new ArrayList<String>();
        for (int i=0; i<tumsarklar.size(); i++){
            if (tumsarklar.get(i).getNotaname().contains("(Kolay)")){
              tumsarkad.add(tumsarklar.get(i).getNotaname());
                tumsarkpdf.add(tumsarklar.get(i).getNotapdf());
            }
        }

        LinearLayout kolaypiyano = findViewById(R.id.kolaysarkilarlay);
        kolaypiyano.setLayoutParams(laypar);
        TextView kolpitext = findViewById(R.id.kolpitex);
        ViewEditor(kolpitext,20,20);

        LinearLayout kolhoriclay = findViewById(R.id.kolhoriclay);
        for (int i=0; i<tumsarkad.size(); i++){
            String[] sarkad = tumsarkad.get(i).toString().split(" - ");
            LinearLayout linearLayout = (LinearLayout) LinearLayout.inflate(kolhoriclay.getContext(),R.layout.mainsanlay,null);
            linearLayout.setLayoutParams(laysar);
            kolhoriclay.addView(linearLayout);

            ImageView sanimg = linearLayout.findViewById(R.id.mainsanimg);
            TextView textView = linearLayout.findViewById(R.id.mainsantex);

            ViewEditor(sanimg,4,4);
            LinearLayout.LayoutParams impar = (LinearLayout.LayoutParams) sanimg.getLayoutParams();
            impar.setMargins(glowidth/30,gloheight/150,glowidth/30,0);
            sanimg.setLayoutParams(impar);

            textView.setText(sarkad[1]);
            ViewEditor(textView,25,25);
            textView.setLayoutParams(new LinearLayout.LayoutParams(MATCHPARENT,gloheight/6));

            String imgRes = getSanim.get(getSanAd.indexOf(sarkad[0])).toString();
            int drawableID2 = MainActivity.this.getResources().getIdentifier(imgRes, "drawable", getPackageName());
            if (drawableID2 == 0){
                File imgFile = new  File(MainActivity.this.getExternalFilesDir(null)+"/msapp/sanresnew/"+imgRes);
                if(imgFile.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    BitmapDrawable ob = new BitmapDrawable(getResources(), myBitmap);
                    sanimg.setBackground(ob);
                    msarkibakagir(sanimg,tumsarkad.get(i).toString());
                }
            } else {
                sanimg.setBackgroundResource(drawableID2);
                msarkibakagir(sanimg,tumsarkad.get(i).toString());

            }
        }


        LinearLayout tumsanatcilar = findViewById(R.id.tumsanlarlay);
        tumsanatcilar.setLayoutParams(laypar);
        TextView tumsantex = findViewById(R.id.tumsanlartex);
        ViewEditor(tumsantex,20,20);

        LinearLayout tumsaniclay = findViewById(R.id.tumsanlariclay);
        for (int i=0; i<everysanList.size(); i++){
            String sanad = everysanList.get(i).getSanad();
            LinearLayout linearLayout = (LinearLayout) LinearLayout.inflate(kolhoriclay.getContext(),R.layout.mainsanlay,null);
            linearLayout.setLayoutParams(laysar);
            tumsaniclay.addView(linearLayout);

            ImageView sanimg = linearLayout.findViewById(R.id.mainsanimg);
            TextView textView = linearLayout.findViewById(R.id.mainsantex);

            ViewEditor(sanimg,4,4);
            LinearLayout.LayoutParams impar = (LinearLayout.LayoutParams) sanimg.getLayoutParams();
            impar.setMargins(glowidth/30,gloheight/150,glowidth/30,0);
            sanimg.setLayoutParams(impar);

            textView.setText(sanad);
            ViewEditor(textView,25,25);
            textView.setLayoutParams(new LinearLayout.LayoutParams(MATCHPARENT,gloheight/6));

            Boolean isSt = false;

            String imgRes = getSanim.get(getSanAd.indexOf(sanad)).toString();
            int drawableID2 = MainActivity.this.getResources().getIdentifier(imgRes, "drawable", getPackageName());
            if (drawableID2 == 0){
                File imgFile = new  File(MainActivity.this.getExternalFilesDir(null)+"/msapp/sanresnew/"+imgRes);
                if(imgFile.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    BitmapDrawable ob = new BitmapDrawable(getResources(), myBitmap);
                    sanimg.setBackground(ob);
                    isSt = true;
                }
            } else {
                sanimg.setBackgroundResource(drawableID2);

                isSt = false;
            }

            Boolean finalIsSt = isSt;
            sanimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newSanPopup(sanimg,sanad,databaseHelper,imgRes, finalIsSt);
                }
            });


        }

        LinearLayout favorilerim = findViewById(R.id.favsarklarlay);
        favorilerim.setLayoutParams(laypar);
        TextView favsarktext = findViewById(R.id.favsarklartex);
        LinearLayout favsarkiclay = findViewById(R.id.favsarklariclay);
        ViewEditor(favsarktext,20,20);

        if (!procontrol()){
            favorilerim.setVisibility(View.GONE);
        }

        List<Favsar> favsarList = databaseHelper.favsarcekdb();
        ArrayList favsarad = new ArrayList<String>();
        ArrayList favsarpdf = new ArrayList<String>();

        for (int i=0; i<favsarList.size(); i++){
            favsarad.add(favsarList.get(i).getFavad()+" - "+favsarList.get(i).getFavsanad());
            favsarpdf.add(favsarList.get(i).getFavpdf());
        }

        for (int i=0; i<favsarad.size(); i++){
            String[] sarkad = favsarad.get(i).toString().split(" - ");
            LinearLayout linearLayout = (LinearLayout) LinearLayout.inflate(kolhoriclay.getContext(),R.layout.mainsanlay,null);
            linearLayout.setLayoutParams(laysar);
            favsarkiclay.addView(linearLayout);

            ImageView sanimg = linearLayout.findViewById(R.id.mainsanimg);
            TextView textView = linearLayout.findViewById(R.id.mainsantex);

            sanimg.setImageResource(R.drawable.ic_startall);
            ViewEditor(sanimg,4,4);
            LinearLayout.LayoutParams impar = (LinearLayout.LayoutParams) sanimg.getLayoutParams();
            impar.setMargins(glowidth/30,gloheight/150,glowidth/30,0);
            sanimg.setLayoutParams(impar);

            textView.setText(sarkad[1]);
            ViewEditor(textView,25,25);
            textView.setLayoutParams(new LinearLayout.LayoutParams(MATCHPARENT,gloheight/6));

            String imgRes = getSanim.get(getSanAd.indexOf(sarkad[0])).toString();
            int drawableID2 = MainActivity.this.getResources().getIdentifier(imgRes, "drawable", getPackageName());
            if (drawableID2 == 0){
                File imgFile = new  File(MainActivity.this.getExternalFilesDir(null)+"/msapp/sanresnew/"+imgRes);
                if(imgFile.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    BitmapDrawable ob = new BitmapDrawable(getResources(), myBitmap);
                    sanimg.setBackground(ob);
                    msarkibakagir(sanimg,favsarad.get(i).toString());
                }
            } else {
                sanimg.setBackgroundResource(drawableID2);
                msarkibakagir(sanimg,favsarad.get(i).toString());

            }
        }
    }

    private void favcek (String kulad){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL2, response -> {
            try {
                response = response.trim();


                //phpd0e hepsini coz

                if (response.equalsIgnoreCase("unsucc")){

                } else {
                    String butun[] = response.split("<");
                    String sarkadlar[] = butun[0].split(">");
                    String vidler[] = butun[1].split(">");
                    String pdfler[] = butun[2].split(">");
                    String imgler[] = butun[3].split(">");

                    DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);

                    for (int i=0; i<sarkadlar.length; i++){
                        String[] bol = sarkadlar[i].split(" - ");

                        Favsar favsar = new Favsar(-1,bol[0],bol[1],vidler[i],pdfler[i],imgler[i],kulad);
                        Boolean succ = databaseHelper.addfavsar(favsar);
                    }

                }


            }
            catch (Exception e){

            }
        }, error -> {

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> data = new HashMap<>();
                data.put("favsarcek",kulad);

                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.stop();
                request.cancel();
            }
        });
    }


    private void ilgsancek(String kulad){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL2, response -> {
            try {
                response = response.trim();


                //phpd0e hepsini coz

                if (response.equalsIgnoreCase("no")){

                } else {
                    String butun[] = response.split(">");

                    DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);

                    for (int i=0; i<butun.length; i++){
                        Ilgsanlar ilgsanlar = new Ilgsanlar(-1,butun[i]);
                        Boolean succ = databaseHelper.addilgsanlar(ilgsanlar);
                    }

                }


            }
            catch (Exception e){

            }
        }, error -> {

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> data = new HashMap<>();
                data.put("ilgsanicek",kulad);

                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.stop();
                request.cancel();
            }
        });
    }

    public void realtimeNotification(Context context){
        Intent intent = new Intent(context, GetNewsActionService.class);
        intent.setAction("ArkaPlanAktivitesi");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_MUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,0,10,pendingIntent);
    }

    private void snackbar(String Message, String Actmessage,LinearLayout linearLayout){
        Snackbar snackbar = Snackbar
                .make(linearLayout, Message, Snackbar.LENGTH_LONG);
        snackbar.setAction(Actmessage, view -> snackbar.dismiss());
        snackbar.show();
    }



    private void yoyoclas(Techniques name, int dur, int rep, View v){
        YoYo.with(name)
                .duration(dur)
                .repeat(rep)
                .playOn(v);
    }


    private boolean isNetworkAvailable() {

        // Initialize connectivity manager
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Initialize network info
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();


        // get connection status
        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();

        // display snack bar

        if (isConnected){
            return true;
        } else {
            return false;
        }

    }
    private void darkenBackground(Float bgcolor) {
        MainActivity mMainActivity = MainActivity.this;
        WindowManager.LayoutParams lp = mMainActivity.getWindow().getAttributes();
        lp.alpha = bgcolor;
        mMainActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mMainActivity.getWindow().setAttributes(lp);
    }




    private void alertbuilder (){

        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setCancelable(false);
        alertdialog.setMessage("Gece Modu açıkken uygulama düzgün çalışmayabilir. Daha iyi bir deneyim için lütfen gece modunu kapatın...");
        alertdialog.setTitle("Gece Modunu Kapatın");
        alertdialog.setIcon(R.drawable.mslogobuy);
        alertdialog.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Intent intent = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        intent = new Intent(Settings.ACTION_DISPLAY_SETTINGS);
                        startActivity(intent);
                    }

                } catch (Exception e){
                    Toast.makeText(application,"Ayarlara Gidin ve Gece Modunu Kapatın...", Toast.LENGTH_LONG).show();
                }

            }
        });
        alertdialog.setNegativeButton("Geri",null);
        alertdialog.create();
        alertdialog.show();

    }

    private void alertDrawOnTop(){

        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setCancelable(false);
        alertdialog.setMessage("Bildirimleri daha işlevsel kılmak adına Müzik Sayfası uygulamasını üstte çalıştırabilirsiniz...");
        alertdialog.setTitle("Bildirimleri İşlevsel Kılın...");
        alertdialog.setIcon(R.drawable.mslogobuy);
        alertdialog.setPositiveButton("Ayarları Aç", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                    startActivityForResult(intent, 0);
                } catch (Exception e){
                    Toast.makeText(application,"Uygulamanın üstüne basılı tutup ayarlarına gidin ve üstte çalıştır iznini verin...", Toast.LENGTH_LONG).show();
                }

            }
        });
        alertdialog.setNeutralButton("Geri",null);
        alertdialog.setNegativeButton("Bir Daha Gösterme", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Bildirimler bildirimler = new Bildirimler(-1,"onTop","no");
                if (databaseHelper.addbildirimler(bildirimler)){
                    Toast.makeText(application, "Bu bildirim bir daha gösterilmeyecek...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        alertdialog.create();
        alertdialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAndRemoveTask();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==PERMISSION_READ && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        }

    }

    public void ViewEditor(@NonNull View view, int olc1, int olc2){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        double height = displayMetrics.heightPixels;
        double width = displayMetrics.widthPixels;



       if (view.getTag().toString().equalsIgnoreCase("bastex")){

           int ayar = (int) width/olc1;
              TextView textView = (TextView) view;
              textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,ayar);
        } else if (view.getTag().toString().equalsIgnoreCase("basedit")){
           int ayar = (int) width/olc1;
           EditText editText = (EditText) view;
           editText.setTextSize(TypedValue.COMPLEX_UNIT_PX,ayar);
       }  else if (view.getTag().toString().equalsIgnoreCase("basbut")){
           int ayar = (int) width/olc1;
           Button button = (Button) view;
           button.setTextSize(TypedValue.COMPLEX_UNIT_PX,ayar);
       } else if (view.getTag().toString().equalsIgnoreCase("basswitch")){
           int ayar = (int) width/olc1;
           Switch aSwitch = (Switch) view;
           aSwitch.setTextSize(TypedValue.COMPLEX_UNIT_PX,ayar);
       } else if (view.getTag().toString().equalsIgnoreCase("basimg")){
           int ayar1 = (int) width/olc1;
           int ayar2 = (int) width/olc2;
           ImageView imageView = (ImageView) view;
           imageView.setLayoutParams(new LinearLayout.LayoutParams(ayar1,ayar2));
       } else if (view.getTag().toString().equalsIgnoreCase("bascard")){
           int ayar1 = (int) width/olc1;
           int ayar2 = (int) width/olc2;
           CardView cardView = (CardView) view;
           cardView.setLayoutParams(new LinearLayout.LayoutParams(ayar1,ayar2));
       }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer==null){

        } else if (mediaPlayer!=null && mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    private void rewardAd(){
            Activity activityContext = MainActivity.this;
            mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    // Handle the reward.
                    int rewardAmount = rewardItem.getAmount();
                    String rewardType = rewardItem.getType();
                    DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                    ArrayList oduller = (ArrayList) databaseHelper.ekcalcek();
                    String[] alpuandiz = oduller.get(0).toString().split(">");

                    for (int i=0; i<oduller.size(); i++){
                        String[] pudiz = oduller.get(i).toString().split(">");
                        Ekcallar olddata = new Ekcallar(Integer.parseInt(pudiz[0]),pudiz[1],pudiz[2],pudiz[3],pudiz[4],pudiz[5]);
                        Boolean sildata = databaseHelper.deleteekcal(olddata);
                        if (sildata){
                            snackbar("Ödül tanımında bir problem oluştu, lütfen tekrar deneyin","OK",findViewById(R.id.mainact));
                        }
                    }

                    Ekcallar newveri = new Ekcallar(-1,Integer.toString(rewardAmount),alpuandiz[2],alpuandiz[3],alpuandiz[4],alpuandiz[5]);
                    Boolean addnewveri = databaseHelper.addekcallar(newveri);
                    if (addnewveri){

                    }



                }
            });
    }

    private void noheartalert(String songname, View buti){
        PopupWindow popUp = new PopupWindow(MainActivity.this);
        LinearLayout layout = new LinearLayout(MainActivity.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(params);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackground(getResources().getDrawable(R.drawable.gamebackgr_9));

        LinearLayout layout2 = new LinearLayout(MainActivity.this);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layout2.setWeightSum(20);
        layout2.setLayoutParams(params2);
        layout2.setOrientation(LinearLayout.VERTICAL);
        layout2.setBackgroundColor(Color.parseColor("#9A000000"));
        layout.addView(layout2);

        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,5));
        imageView.setImageResource(R.drawable.ic_gemheart);
        layout2.addView(imageView);

        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,5));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.WHITE);
        textView.setText("Hiç canın yok...");
        textView.setTypeface(ResourcesCompat.getFont(this,R.font.miss_fajardose));
        textView.setTag("bastex");
        ViewEditor(textView,9,9);
        layout2.addView(textView);

        LinearLayout cardlin = new LinearLayout(this);
        cardlin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,5));
        cardlin.setOrientation(LinearLayout.HORIZONTAL);
        cardlin.setPadding(glowidth/40,0,glowidth/40,0);
        layout2.addView(cardlin);

        CardView cardView = new CardView(this);
        cardView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        cardView.setUseCompatPadding(true);
        cardView.setCardBackgroundColor(Color.parseColor("#E91E63"));
        cardView.setRadius(glowidth/30);
        cardlin.addView(cardView);

        TextView cardtext = new TextView(this);
        cardtext.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        cardtext.setGravity(Gravity.CENTER);
        cardtext.setTextColor(Color.WHITE);
        cardtext.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_adwatch,0,0,0);
        cardtext.setText("+1 Can Kazan!");
        cardtext.setTypeface(ResourcesCompat.getFont(this,R.font.raleway_medium));
        cardtext.setTag("bastex");
        ViewEditor(cardtext,15,15);
        LinearLayout.LayoutParams textlay = (LinearLayout.LayoutParams) cardtext.getLayoutParams();
        textlay.setMargins(glowidth/15,0,glowidth/15,0);
        cardtext.setLayoutParams(textlay);
        cardView.addView(cardtext);

        TextView yadatex = new TextView(this);
        yadatex.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,2));
        yadatex.setGravity(Gravity.CENTER);
        yadatex.setTextColor(Color.WHITE);
        yadatex.setText("Ya da...");
        yadatex.setTypeface(ResourcesCompat.getFont(this,R.font.miss_fajardose));
        yadatex.setTag("bastex");
        ViewEditor(yadatex,15,15);
        layout2.addView(yadatex);


        LinearLayout cardlin2 = new LinearLayout(this);
        cardlin2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,3));
        cardlin2.setOrientation(LinearLayout.HORIZONTAL);
        cardlin2.setPadding(glowidth/40,0,glowidth/40,0);
        layout2.addView(cardlin2);

        CardView cardView2 = new CardView(this);
        cardView2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        cardView2.setUseCompatPadding(true);
        cardView2.setCardBackgroundColor(Color.parseColor("#E91E63"));
        cardView2.setRadius(glowidth/30);
        cardlin2.addView(cardView2);

        TextView cardtext2 = new TextView(this);
        cardtext2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        cardtext2.setGravity(Gravity.CENTER);
        cardtext2.setTextColor(Color.WHITE);
        cardtext2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_litmarket,0,0,0);
        cardtext2.setText("Markete Git...");
        cardtext2.setTypeface(ResourcesCompat.getFont(this,R.font.raleway_medium));
        cardtext2.setTag("bastex");
        ViewEditor(cardtext2,20,20);
        cardtext2.setLayoutParams(textlay);
        cardView2.addView(cardtext2);

        cardtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(v.getContext());
                progressDialog.setMessage("Yükleniyor...");
                progressDialog.setCancelable(true);
                progressDialog.show();
                if (mRewardedAd!=null){
                    rewardAd();
                    progressDialog.dismiss();
                } else {
                   snackbar("Tekrar dokunun...","Tamam",layout);
                    progressDialog.dismiss();
                }
            }
        });

        cardtext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp.dismiss();
                marketPopup();
            }
        });

        //Buranın tasarımını düzenle

        popUp.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground(1f);
            }
        });


        popUp.setOutsideTouchable(true);
        popUp.setFocusable(true);
        popUp.setContentView(layout);
        popUp.update(0, 0, glowidth/2+glowidth/4, gloheight/2+gloheight/8);
        popUp.showAtLocation(layout,Gravity.CENTER,0,0);
        darkenBackground(0.3f);

    }

    private void buyheart(int price, int heartsay, LinearLayout linearLayout, TextView cointex, TextView hearttex){
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ArrayList alpuan = (ArrayList) databaseHelper.ekcalcek();
        String[] alpuandiz = alpuan.get(0).toString().split(">");

        int totalcoin = Integer.parseInt(alpuandiz[3]);
        int totalheart = Integer.parseInt(alpuandiz[1]);

        if (price>totalcoin){
            snackbar("Yeterli puanınız yok...","OK",linearLayout);
        } else {
            int newtotalcoin = totalcoin-price;
            int newtotalheart = totalheart+heartsay;

            for (int i=0; i<alpuan.size(); i++){
                String[] alpuandiz2 = alpuan.get(i).toString().split(">");
                Ekcallar ekcallar = new Ekcallar(Integer.parseInt(alpuandiz2[0]),alpuandiz2[1],alpuandiz2[2],alpuandiz2[3],alpuandiz2[4],alpuandiz2[5]);
                Boolean delolds = databaseHelper.deleteekcal(ekcallar);
                if (delolds){
                    snackbar("Problem oluştu...","Tekrar Dene",linearLayout);
                }
            }

            Ekcallar newekcal = new Ekcallar(-1,Integer.toString(newtotalheart),alpuandiz[2],Integer.toString(newtotalcoin),alpuandiz[4],alpuandiz[5]);
            Boolean addekcal = databaseHelper.addekcallar(newekcal);

            if (addekcal){
                cointex.setText(" x"+newtotalcoin);
                hearttex.setText(" x"+newtotalheart);
                puantextus.setText(" x"+newtotalcoin);
                hearttextus.setText(" x"+newtotalheart);
                snackbar("Canlar Eklendi!","Süper!",linearLayout);
                yoyoclas(Techniques.Wobble,1000,0,linearLayout);
            } else {
                snackbar("Problem oluştu...","Tekrar Dene",linearLayout);
            }
        }




    }

    private void marketPopup(){

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ArrayList alpuan = (ArrayList) databaseHelper.ekcalcek();
        String[] alpuandiz = alpuan.get(0).toString().split(">");

        PopupWindow popUp = new PopupWindow(MainActivity.this);
        View layout = View.inflate(this,R.layout.market_popup,null);

        LinearLayout viewlay = layout.findViewById(R.id.marketlay);

        ImageView closemarkpop = layout.findViewById(R.id.closemarkpop);
        ImageView coinim1 = layout.findViewById(R.id.coinim1);
        ImageView coinim2 = layout.findViewById(R.id.coinim2);
        ImageView coinim3 = layout.findViewById(R.id.coinim3);
        ImageView coinim4 = layout.findViewById(R.id.coinim4);

        TextView baslikpop = layout.findViewById(R.id.baslikpop);
        baslikpop.setTag("bastex");
        ViewEditor(baslikpop,13,13);

        TextView heart1gem = layout.findViewById(R.id.heart1gem);
        heart1gem.setTag("bastex");
        ViewEditor(heart1gem,20,20);

        TextView heart1coin = layout.findViewById(R.id.heart1coin);
        heart1coin.setTag("bastex");
        ViewEditor(heart1coin,20,20);

        TextView heart3gem = layout.findViewById(R.id.heart3gem);
        heart3gem.setTag("bastex");
        ViewEditor(heart3gem,20,20);

        TextView heart3coin = layout.findViewById(R.id.heart3coin);
        heart3coin.setTag("bastex");
        ViewEditor(heart3coin,20,20);

        TextView heart10gem = layout.findViewById(R.id.heart10gem);
        heart10gem.setTag("bastex");
        ViewEditor(heart10gem,20,20);

        TextView heart10coin = layout.findViewById(R.id.heart10coin);
        heart10coin.setTag("bastex");
        ViewEditor(heart10coin,20,20);

        TextView heart20coin = layout.findViewById(R.id.heart20coin);
        heart20coin.setTag("bastex");
        ViewEditor(heart20coin,20,20);

        TextView heart20gem = layout.findViewById(R.id.heart20gem);
        heart20gem.setTag("bastex");
        ViewEditor(heart20gem,20,20);

        TextView totalpuan = layout.findViewById(R.id.totalpuan);
        totalpuan.setTag("bastex");
        ViewEditor(totalpuan,20,20);

        totalpuan.setText(" x"+alpuandiz[3]);

        TextView totalheart = layout.findViewById(R.id.totalheart);
        totalheart.setTag("bastex");
        ViewEditor(totalheart,20,20);

        totalheart.setText(" x"+alpuandiz[1]);

        heart1coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyheart(3000,1,viewlay,totalpuan,totalheart);
            }
        });

        heart3coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyheart(8000,3,viewlay,totalpuan,totalheart);
            }
        });

        heart10coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyheart(26000,10,viewlay,totalpuan,totalheart);
            }
        });

        heart20coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyheart(40000,20,viewlay,totalpuan,totalheart);
            }
        });

        coinim1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyheart(3000,1,viewlay,totalpuan,totalheart);
            }
        });

        coinim2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyheart(8000,3,viewlay,totalpuan,totalheart);
            }
        });

        coinim3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyheart(26000,10,viewlay,totalpuan,totalheart);
            }
        });

        coinim4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyheart(40000,20,viewlay,totalpuan,totalheart);
            }
        });

        closemarkpop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp.dismiss();
            }
        });


        popUp.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground(1f);
            }
        });




        popUp.setOutsideTouchable(true);
        popUp.setFocusable(true);
        popUp.setContentView(layout);
        popUp.update(0, 0, glowidth/2+glowidth/4, gloheight/2+gloheight/4);
        popUp.showAtLocation(layout,Gravity.CENTER,0,0);
        darkenBackground(0.3f);




    }
}

