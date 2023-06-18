package org.newest.msorgapp;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class msarkibak extends YouTubeBaseActivity {
    YouTubePlayerView youTubePlayerView;
    Boolean kulgir = false;
    private static final String URL = "-----";
    private static final String URLYOU = "-------";
    int heighto,widtho;
    Boolean favcheck = false;
    Context context;
    PDFView pdfView;
    Integer gloheight,glowidth;
    MediaPlayer mediaPlayer;
    Favsar favsar;
    String kulek;
    TextView digersarklar,tamekrantext;
    ImageView sharesong;

    YouTubePlayer.OnInitializedListener onInitializedListener;

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer!=null){
            mediaPlayer.pause();
        }

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Bundle extras = getIntent().getExtras();
        if (extras.getString("whereback").equalsIgnoreCase("msmessenger")){
            Intent intent = new Intent(msarkibak.this,kulprofilo.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        } else if (extras.getString("whereback").equalsIgnoreCase("kulprofilo")){
            Intent intent = new Intent(msarkibak.this,kulprofilo.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        }else if (extras.getString("whereback").equalsIgnoreCase("usersearchdata")){
            Intent intent = new Intent(msarkibak.this,usersearchdata.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        }else if (extras.getString("whereback").equalsIgnoreCase("mainactivity")){
            Intent intent = new Intent(msarkibak.this,MainActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        }

    }

    private void initlay(){
        digersarklar = findViewById(R.id.digersarklar);
        tamekrantext = findViewById(R.id.tamekrantext);

        ImageView sanimggor = findViewById(R.id.sanimggor);
        TextView sarkgiradi = findViewById(R.id.sarkgiradi);
        ImageView sanimgsark = findViewById(R.id.sanimgsark);
        ImageView favbar = findViewById(R.id.favbar);

        Context context = msarkibak.this;
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        List<Notarama> aramanota = databaseHelper.aramanota();
        List<Notarama> aramanotag = databaseHelper.aramanotag();
        List<Everysan> everysans = databaseHelper.everysans();
        List<Everysan> everysansg = databaseHelper.everysansg();
        List<Favsar> favsarList = databaseHelper.favsarcekdb();

        ArrayList sarkadlar = new ArrayList<String>();
        ArrayList sarkuriler = new ArrayList<String>();
        ArrayList sarkpdfler = new ArrayList<String>();
        ArrayList sanad = new ArrayList<String>();
        ArrayList sanimg = new ArrayList<String>();
        ArrayList favsarad = new ArrayList<String>();


        aramanota.addAll(aramanotag);
        everysans.addAll(everysansg);
        Bundle extras = getIntent().getExtras();
        String sarki = extras.getString("sarkad");
        String[] bol = sarki.split(" - ");

        for (int i=0; i<favsarList.size(); i++){
            favsarad.add(favsarList.get(i).getFavad()+" - "+favsarList.get(i).getFavsanad());
        }

        for (int i=0; i<aramanota.size(); i++){
            sarkadlar.add(aramanota.get(i).getNotaname());
            sarkuriler.add(aramanota.get(i).getNotaurl());
            sarkpdfler.add(aramanota.get(i).getNotapdf());
        }

        for (int i=0 ;i<everysans.size(); i++){
            sanad.add(everysans.get(i).getSanad());
            sanimg.add(everysans.get(i).getSanimg());
        }

        List<Kulbi> kulcek = databaseHelper.kulbicek();
        if (kulcek.size()>0){
            kulek = kulcek.get(0).getKulad();
            kulgir = true;
        }

        sarkgiradi.setText(sarki);
        sarkgiradi.setTypeface(ResourcesCompat.getFont(context,R.font.raleway_medium));
        ViewEditor(sarkgiradi,20,20);
        String imgRes = sanimg.get(sanad.indexOf(bol[0])).toString();
        String[] vidRes = sarkuriler.get(sarkadlar.indexOf(sarki)).toString().split("/");
        String pdfRes = sarkpdfler.get(sarkadlar.indexOf(sarki)).toString();

        int drawableID2 = context.getResources().getIdentifier(imgRes, "drawable", getPackageName());
        if (drawableID2 == 0){
            File imgFile = new  File(context.getExternalFilesDir(null)+"/msapp/sanresnew/"+imgRes);
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                sanimggor.setImageBitmap(myBitmap);
                sanimgsark.setImageBitmap(myBitmap);
                opentamek(tamekrantext,sarki,pdfRes,"true");
            }
        } else {
            sanimggor.setImageResource(drawableID2);
            sanimgsark.setImageResource(drawableID2);
            opentamek(tamekrantext,sarki,pdfRes,"false");
        }
        youTubePlayerView = findViewById(R.id.youtubeplayer);
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo(vidRes[vidRes.length-1]);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Snackbar snackbar = Snackbar
                        .make(findViewById(R.id.msarkibaklay), "Telefonunuzda Youtube Uygulaması Bulunmalıdır...", Snackbar.LENGTH_LONG);
                snackbar.setAction("Yükle", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String appPackageName = "com.google.android.youtube"; // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                        snackbar.dismiss();
                    }
                });
                snackbar.show();
            }
        };

        youTubePlayerView.initialize(URLYOU,onInitializedListener);

        pdfView = findViewById(R.id.pdfViewer);
            int rawID = context.getResources().getIdentifier(pdfRes, "raw", getPackageName());
            if (rawID==0){
                File file = new File(context.getExternalFilesDir(null)+"/msapp/pdfnews/"+pdfRes);
                if (file.exists()){
                    pdfView.fromFile(file).load();
                } else {
                   Snackbar snackbar =  Snackbar.make(findViewById(R.id.msarkibaklay),"Notayı Görüntüle", BaseTransientBottomBar.LENGTH_INDEFINITE);
                   snackbar.setAction("Tamam", new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           pdfcek(context,extras.getString("sarkad"));
                       }
                   });
                   snackbar.show();

                }

            } else {
                InputStream pdf = context.getResources().openRawResource(rawID);
                pdfView.fromStream(pdf).load();
            }

            if (favsarad.contains(sarki)){
                favbar.setImageResource(R.drawable.ic_favfill_large);
                favcheck = true;
                favsar = favsarList.get(favsarad.indexOf(sarki));
            } else {
                favbar.setImageResource(R.drawable.ic_favlargebos);
                favcheck = false;
            }

        favbar.setOnClickListener(v -> {
            if (kulgir){
                if (favcheck){
                    if (!databaseHelper.deletefavsar(favsar)){
                        yoyoclas(Techniques.Wobble,1000,0,v);
                        favcheck=false;
                        snackbar("Favorilerden Kaldırıldı","Tamam!",findViewById(R.id.msarkibaklay));
                        favbar.setImageResource(R.drawable.ic_favlargebos);
                    } else {
                        snackbar("Bir Sorun Oluştu...","Tekrar Dene",findViewById(R.id.msarkibaklay));
                    }
                } else {
                    Favsar favsar1 = new Favsar(-1,bol[0],bol[1],"no","no","no","no");
                    if (databaseHelper.addfavsar(favsar1)){
                        yoyoclas(Techniques.Wobble,1000,0,v);
                        favcheck=true;
                        snackbar("Favorilere Eklendi","Süper!",findViewById(R.id.msarkibaklay));
                        favbar.setImageResource(R.drawable.ic_favfill_large);
                    } else {
                        snackbar("Bir Sorun Oluştu...","Tekrar Dene",findViewById(R.id.msarkibaklay));
                    }
                }
            } else {
                snackbar("Lütfen profil girişi yapın","Tamam",findViewById(R.id.msarkibaklay));
            }

        });

            digersarklar.setOnClickListener(v -> {
                newSanPopup(digersarklar,bol[0],databaseHelper,imgRes);
            });



    }

    private void pdfcek(Context context, String sarkad){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Pdf Getiriliyor...");
        progressDialog.show();
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        List<Notarama> notarama = databaseHelper.aramanotag();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
            try {
                response = response.trim();

                File pdffile = new File(context.getExternalFilesDir(null)+"/msapp/pdfnews/"+sarkad+".pdf");
                if(!pdffile.exists()){
                    byte[] pdfAsBytes = Base64.decode(response, 0);
                    try (FileOutputStream os = new FileOutputStream(pdffile, false)) {
                        os.write(pdfAsBytes);
                        os.flush();
                        progressDialog.dismiss();
                        pdfView.fromFile(pdffile).load();
                    }

                    for (int i=0; i<notarama.size(); i++){
                        String[] bolres = notarama.get(i).toString().split(">");
                        if (bolres[1].equalsIgnoreCase(sarkad)&&bolres[3].equalsIgnoreCase("no")){
                            Notarama silnot = new Notarama(Integer.parseInt(bolres[0]),bolres[1],bolres[2],bolres[3],bolres[4]);
                            Boolean isdeleted = databaseHelper.deletearamanotag(silnot);
                            if (!isdeleted){
                                Notarama addnotarama = new Notarama(-1,bolres[1],bolres[2],pdffile.getName(),bolres[4]);
                                Boolean addnot = databaseHelper.addaramanotag(addnotarama);
                                if (addnot){

                                } else {

                                }
                            }
                        }
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
                data.put("pdfcek",sarkad);
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

    private void opentamek(View v, String sarkad, String pdfid, String isStored){
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),pdftamekran.class);
                intent.putExtra("sarkad",sarkad);
                intent.putExtra("isStored",isStored);
                intent.putExtra("doc",pdfid);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });
    }

    private void newSanPopup(View buti, String sanad, DatabaseHelper databaseHelper, String sanimg){
        Context context = buti.getContext();
        List<Notarama> tumsarklar = databaseHelper.aramanota();
        List<Notarama> tumsarklarg = databaseHelper.aramanotag();
        tumsarklar.addAll(tumsarklarg);
        PopupWindow popUp = new PopupWindow(context);
        LinearLayout layout = (LinearLayout) LinearLayout.inflate(context,R.layout.sanpopup,null);

        ImageView sanpopimg = layout.findViewById(R.id.sanpopimg);
        TextView sanpopad = layout.findViewById(R.id.sanpopad);
        android.widget.SearchView popsearching = layout.findViewById(R.id.popsearching);
        LinearLayout sanpophoriclay = layout.findViewById(R.id.sanpophoriclay);
        ImageView closesanpo = layout.findViewById(R.id.closesanpo);

        closesanpo.setOnClickListener(v -> popUp.dismiss());

        ViewEditor(sanpopad,20,20);
        sanpopad.setText(sanad);
        popsearching.setQueryHint("Buradan ara...");

        int drawableID2 = context.getResources().getIdentifier(sanimg, "drawable", getPackageName());
        if (drawableID2 == 0){
            File imgFile = new  File(context.getExternalFilesDir(null)+"/msapp/sanresnew/"+sanimg);
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
                sharesan.setImageResource(R.drawable.ic_play_circle_outline);
                msarkibakagir(sharesan,tumsarklar.get(i).getNotaname());
                textView.setText(" "+bol[1]);
                if (drawableID2 == 0){
                    File imgFile = new  File(context.getExternalFilesDir(null)+"/msapp/sanresnew/"+sanimg);
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

    private void msarkibakagir(View view,String sarkad){

        view.setOnClickListener(v -> {
            Bundle extras = getIntent().getExtras();

            Intent intent = new Intent(v.getContext(),msarkibak.class);
            intent.putExtra("sarkad",sarkad);
            intent.putExtra("whereback",extras.getString("whereback"));
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msarkibak);

        MobileAds.initialize(this, initializationStatus -> {
        });

        AdView adView = findViewById(R.id.adView4);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        context = this;
        initlay();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        heighto = displayMetrics.heightPixels;
        widtho = displayMetrics.widthPixels;
        glowidth = displayMetrics.widthPixels;
        gloheight = displayMetrics.heightPixels;

        digersarklar = findViewById(R.id.digersarklar);
        tamekrantext = findViewById(R.id.tamekrantext);
        sharesong = findViewById(R.id.mainsearch2);

        ViewEditor(digersarklar,20,20);
        ViewEditor(tamekrantext,20,20);

        digersarklar.setTypeface(ResourcesCompat.getFont(context,R.font.raleway_medium_italic));
        tamekrantext.setTypeface(ResourcesCompat.getFont(context,R.font.raleway_medium_italic));

        sharesong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();
                String sarkad = extras.getString("sarkad");
                List<Notarama> notaramas = databaseHelper.aramanota();
                List<Notarama> notaramas1 = databaseHelper.aramanotag();

                notaramas.addAll(notaramas1);

                for (int i=0; i<notaramas.size(); i++){
                    String[] tumbol = notaramas.get(i).toString().split(">");
                    if (tumbol[1].equalsIgnoreCase(sarkad)){
                        String[] bolic = tumbol[2].split("/");
                        String urik = "https://youtu.be/"+bolic[bolic.length-1];
                        shareYoutubeUris(urik);
                    }
                }

            }
        });

    }

    private void shareYoutubeUris(String uritext) {
        Intent intent2 = new Intent(); intent2.setAction(Intent.ACTION_SEND);
        intent2.setType("text/plain");
        intent2.putExtra(Intent.EXTRA_TEXT, uritext );
        startActivity(Intent.createChooser(intent2, "Paylaş"));
    }

    public void yoyoclas(Techniques name, int dur, int rep, View v){
        YoYo.with(name)
                .duration(dur)
                .repeat(rep)
                .playOn(v);
    }


    public void snackbar(String Message, String Actmessage,LinearLayout linearLayout){
        Snackbar snackbar = Snackbar
                .make(linearLayout, Message, Snackbar.LENGTH_LONG);
        snackbar.setAction(Actmessage, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    private void darkenBackground(Float bgcolor) {
        msarkibak mMainActivity = msarkibak.this;
        WindowManager.LayoutParams lp = mMainActivity.getWindow().getAttributes();
        lp.alpha = bgcolor;
        mMainActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mMainActivity.getWindow().setAttributes(lp);
    }

    public void ViewEditor(@NonNull View view, int olc1, int olc2){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
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

}
