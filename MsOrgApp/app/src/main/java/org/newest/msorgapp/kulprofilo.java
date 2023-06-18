package org.newest.msorgapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
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
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class kulprofilo extends AppCompatActivity {
    ImageView kulprores,mainmenu,mainsearchi;
    private static final int TAKE_PICTURE = 1;
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;
    private String URL2 = "https://www.xn--mziksayfas-9db95d.com/login.php";
    private String URL3 = "https://www.xn--mziksayfas-9db95d.com/newnoti.php";
    final int PIC_CROP = 1;
    int glowidth,gloheight;
    EditText kuledtexfilo,sifedtexfilo,emedtexfilo;
    TextView hosbasprof,ilgsanprof,favsarprof;
    LinearLayout ilgsanlay,favsarlay;


    public void alertcikis (){

        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setCancelable(false);
        alertdialog.setMessage("Devam ederseniz profilinizden çıkış yapılacak. Onaylıyor musunuz?");
        alertdialog.setTitle("Çıkış Yap");
        alertdialog.setIcon(R.drawable.mslogobuy);
        alertdialog.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              exitProfile();
            }
        });

        alertdialog.setNegativeButton("Geri", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertdialog.create();
        alertdialog.show();

    }

    public void alertsilpro (){

        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setCancelable(false);
        alertdialog.setMessage("Devam ederseniz favori şarkılarınız, puanlarınız, mesajlarınız ve kayıtlı çalışmalarınızın tümü silinecek. Onaylıyor musunuz? ");
        alertdialog.setTitle("Profili Sil");
        alertdialog.setIcon(R.drawable.mslogobuy);
        alertdialog.setPositiveButton("Evet, Profilimi Sil", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                  profiliKaldir();
            }
        });

        alertdialog.setNegativeButton("Geri", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertdialog.create();
        alertdialog.show();

    }

    public void saveallchanges(){
        String kulad = kuledtexfilo.getText().toString();
        String sifre = sifedtexfilo.getText().toString();
        String email = emedtexfilo.getText().toString();

        DatabaseHelper databaseHelper = new DatabaseHelper(kulprofilo.this);
        ArrayList kulist = (ArrayList) databaseHelper.kulbicek();
        String[] kuli = kulist.get(0).toString().split(">");
        Kulbi kulbi = new Kulbi(Integer.parseInt(kuli[0]),kuli[1],kuli[2],kuli[3],kuli[4]);
        Boolean deletekul = databaseHelper.deletekulbi(kulbi);
        if (!deletekul){
            Kulbi newkulbi = new Kulbi(-1,kulad,sifre,email,kuli[4]);
            Boolean addkul = databaseHelper.addkulbi(newkulbi);
            if (addkul){
                Snackbar.make(findViewById(R.id.kulprofilli),"Değişiklikler Başarıyla Kaydedildi", BaseTransientBottomBar.LENGTH_LONG).show();
            }
        }
    }

    public void profiliKaldir(){
        DatabaseHelper databaseHelper = new DatabaseHelper(kulprofilo.this);
        ArrayList kullar = (ArrayList) databaseHelper.kulbicek();
        String[] kulstr = kullar.get(0).toString().split(">");
        String kulid = kulstr[4];


        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL2 , new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onResponse(String response) {
                try {
                    response = response.trim();
                    if (response.equalsIgnoreCase("succ")){
                        for (int i=0; i<kullar.size(); i++){
                            String[] kulstr2 = kullar.get(i).toString().split(">");
                            Kulbi eskikulbi = new Kulbi(Integer.parseInt(kulstr2[0]),kulstr2[1],kulstr2[2],kulstr2[3],kulstr2[4]);
                            Boolean delete = databaseHelper.deletekulbi(eskikulbi);
                            if (!delete){
                                File klasor = new File(kulprofilo.this.getExternalFilesDir(null)+"/msapp/profotos");
                                File[] filediz = klasor.listFiles();
                                for (int i1=0; i1<filediz.length; i1++){
                                    Boolean sildi = filediz[i1].delete();
                                    if (sildi){

                                    }
                                }
                            } else {
                                Toast.makeText(kulprofilo.this, "Profiliniz Silinirken Bir Hata Oluştu, Lütfen Tekrar Deneyin...", Toast.LENGTH_LONG).show();
                            }
                        }


                    }  else {

                    }

                }
                catch (Exception e){
                    Toast.makeText(kulprofilo.this, "Profiliniz Silinirken Bir Hata Oluştu, Lütfen Tekrar Deneyin...", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Toast.makeText(kulprofilo.this, "Profiliniz Silinirken Bir Hata Oluştu, Lütfen Tekrar Deneyin...", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> data = new HashMap<>();
                data.put("deletekuliko", kulid);
                data.put("deletekulad",kulstr[1]);
                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(kulprofilo.this);
        requestQueue.add(stringRequest);

        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                request.cancel();
            }
        });
    }

    public void ilgSanEkle(Context context, String sanad){
        DatabaseHelper databaseHelper = new DatabaseHelper(kulprofilo.this);
        Ilgsanlar ilgsanlar = new Ilgsanlar(-1,sanad);
        Boolean ilgekle = databaseHelper.addilgsanlar(ilgsanlar);
        if (ilgekle){
            Snackbar.make(findViewById(R.id.kulprofilli),sanad+" Eklendi...",BaseTransientBottomBar.LENGTH_SHORT).show();
            ArrayList arrayList = (ArrayList) databaseHelper.ilgsancek();
            ilgsanprof.setText("İlgİlendİğİm Sanatçılar ("+arrayList.size()+")");
        } else {
            Snackbar.make(findViewById(R.id.kulprofilli),sanad+" Eklenemedi...",BaseTransientBottomBar.LENGTH_SHORT).show();
        }
    }

    public void ilgSanCikar(Context context, String sanad){

        DatabaseHelper databaseHelper = new DatabaseHelper(kulprofilo.this);
        ArrayList ilgsanar = (ArrayList) databaseHelper.ilgsancek();


            for (int i=0; i<ilgsanar.size(); i++){
                String[] ilgsanik = ilgsanar.get(i).toString().split(">");
                if (ilgsanik[1].equalsIgnoreCase(sanad)){
                    Ilgsanlar ilgsanlar = new Ilgsanlar(Integer.parseInt(ilgsanik[0]),ilgsanik[1]);
                    Boolean delete = databaseHelper.deleteilgsan(ilgsanlar);
                    if (!delete){
                        Snackbar.make(findViewById(R.id.kulprofilli),sanad+" Çıkarılıdı...",BaseTransientBottomBar.LENGTH_SHORT).show();
                        ArrayList arrayList = (ArrayList) databaseHelper.ilgsancek();
                        ilgsanprof.setText("İlgİlendİğİm Sanatçılar ("+arrayList.size()+")");
                    } else {
                        Snackbar.make(findViewById(R.id.kulprofilli),sanad+" Çıkarılamadı...",BaseTransientBottomBar.LENGTH_SHORT).show();
                    }
                }
            }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kulprofilo);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ArrayList kullar = (ArrayList) databaseHelper.kulbicek();
        String[] bilgilerkul = kullar.get(0).toString().split(">");

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        gloheight = displayMetrics.heightPixels;
        glowidth = displayMetrics.widthPixels;




        mainmenu = findViewById(R.id.mainmenu);
        mainsearchi = findViewById(R.id.mainsearchi);
        kulprores = findViewById(R.id.kulprores);
        kuledtexfilo = findViewById(R.id.kuledtexfilo);
        sifedtexfilo = findViewById(R.id.sifedtexfilo);
        emedtexfilo = findViewById(R.id.emedtexfilo);
        hosbasprof = findViewById(R.id.hosbasprof);
        ilgsanprof = findViewById(R.id.ilgsanprof);
        favsarprof = findViewById(R.id.favsarprof);
        ilgsanlay = findViewById(R.id.ilgsanlay);
        favsarlay = findViewById(R.id.favsarlay);

        kuledtexfilo.setTag("basedit");
        sifedtexfilo.setTag("basedit");
        emedtexfilo.setTag("basedit");
        hosbasprof.setTag("bastex");
        ilgsanprof.setTag("bastex");
        favsarprof.setTag("bastex");

        ViewEditor(hosbasprof,15,15);
        ViewEditor(kuledtexfilo,20,20);
        ViewEditor(sifedtexfilo,20,20);
        ViewEditor(emedtexfilo,20,20);
        ViewEditor(ilgsanprof,20,20);
        ViewEditor(favsarprof,20,20);

        mainsearchi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),usersearchdata.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

            }
        });

        File klasor = new File(kulprofilo.this.getExternalFilesDir(null)+"/msapp/profotos");
        File[] filediz = klasor.listFiles();

        if (filediz!=null){
            if (filediz.length!=0){
                kulprores.setImageURI(Uri.fromFile(filediz[0]));
            }

        }


        kuledtexfilo.setText(bilgilerkul[1]);
        sifedtexfilo.setText(bilgilerkul[2]);
        emedtexfilo.setText(bilgilerkul[3]);

        kulprores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
                setForceShowIcon(popupMenu);
                popupMenu.getMenuInflater().inflate(R.menu.askphoto,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.askphoto:
                                openCamera();
                                break;
                            case R.id.pickgallery:
                                openGallery();
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        mainmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
                setForceShowIcon(popupMenu);
                popupMenu.getMenuInflater().inflate(R.menu.menuprofnav,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.navsave:
                                saveallchanges();
                                break;
                            case R.id.navmainpage:
                                 Intent intent = new Intent(v.getContext(),MainActivity.class);
                                 startActivity(intent);
                                 finish();
                                 overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                                break;
                            case R.id.navexit:
                                 alertcikis();
                                break;
                            case R.id.navdelete:
                                 alertsilpro();
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });


        initIlgsanlay(ilgsanlay);
        initFavsar(favsarlay);

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
               kuledtexfilo.setFocusable(true);
               sifedtexfilo.setFocusable(true);
               emedtexfilo.setFocusable(true);
               kuledtexfilo.setFocusableInTouchMode(true);
                sifedtexfilo.setFocusableInTouchMode(true);
                emedtexfilo.setFocusableInTouchMode(true);
            }
        };
        handler.postDelayed(runnable,1000);


    }

    public void exitProfile(){
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        List<Kulbi> kularray = (ArrayList) databaseHelper.kulbicek();
        List<Favsar> favlar = (ArrayList) databaseHelper.favsarcekdb();
        List<Ilgsanlar> ilgsanlar = (ArrayList) databaseHelper.ilgsancek();
        List<Favyaz> favyazs = databaseHelper.favyazcekdb();
        List<Ekcallar> ekcallars = databaseHelper.ekcalcek();
        List<Pdfkaylar> pdfkaylars = databaseHelper.kaypdfcek();

        if (!kularray.isEmpty()){
            Boolean delete = databaseHelper.deletekulbi(kularray.get(0));
            if (!delete){

                File imstore = new File(kulprofilo.this.getExternalFilesDir(null)+"/msapp/profotos");
                File[] imdiz = imstore.listFiles();
                if (imdiz.length>0 && imstore.exists()){
                    for (int a=0; a<imdiz.length; a++){
                        imdiz[a].delete();
                    }
                }


                if (!favlar.isEmpty()){
                    for (int i=0; i<favlar.size(); i++){
                        Boolean delfav = databaseHelper.deletefavsar(favlar.get(i));
                        if (!delfav){

                        }
                    }
                }


                if (!ilgsanlar.isEmpty()){
                    for (int i=0; i<ilgsanlar.size(); i++){
                        Boolean dele = databaseHelper.deleteilgsan(ilgsanlar.get(i));
                        if (!dele){

                        }
                    }
                }

                if (!favyazs.isEmpty()){
                    for (int i=0; i<favyazs.size(); i++){
                        if (databaseHelper.deletefavyaz(favyazs.get(i))){

                        }
                    }
                }


                if (!ekcallars.isEmpty()){
                    for (int i=0; i<ekcallars.size(); i++){
                        if (!databaseHelper.deleteekcal(ekcallars.get(i))){

                        }
                    }
                    Ekcallar ekcallar = new Ekcallar(-1,"0","1","0","0","0");
                    if (databaseHelper.addekcallar(ekcallar)){

                    }

                }

                if (!pdfkaylars.isEmpty()){
                    for (int i=0; i<pdfkaylars.size(); i++){
                        if (databaseHelper.deletepdfkay(pdfkaylars.get(i))){

                        }
                    }
                }

                Toast.makeText(this, "Çıkış Başarılı", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
        }
        }
    }

    public static void setForceShowIcon(PopupMenu popupMenu) {
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void initIlgsanlay(LinearLayout linearLayout){

        Context context = linearLayout.getContext();
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        ArrayList everysan = (ArrayList) databaseHelper.everysans();
        ArrayList everysang = (ArrayList) databaseHelper.everysansg();

        ArrayList arrayList = (ArrayList) databaseHelper.ilgsancek();
        ilgsanprof.setText("İlgİlendİğİm Sanatçılar ("+arrayList.size()+")");

        for (int i=0; i<everysan.size(); i++){

            final Boolean[] touchdet = {true};

            String[] bol = everysan.get(i).toString().split(">");
            View sanlayi = View.inflate(context,R.layout.everysanlarlay,null);
            linearLayout.addView(sanlayi);

            LinearLayout anapinlay = sanlayi.findViewById(R.id.anapinlay);
            ImageView anasanimg = sanlayi.findViewById(R.id.anasanimg);
            TextView anasantex = sanlayi.findViewById(R.id.anasantex);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(glowidth/4, LinearLayout.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(glowidth/80,gloheight/80,glowidth/80,gloheight/80);
            anapinlay.setLayoutParams(layoutParams);

            int drawableID = this.getResources().getIdentifier(bol[3], "drawable", getPackageName());
            anasanimg.setBackgroundResource(drawableID);

            ArrayList ilgsanlar = (ArrayList) databaseHelper.ilgsancek();
            if (ilgsanlar.size()>0){
                for (int i1=0; i1<ilgsanlar.size(); i1++){
                    String[] boli = ilgsanlar.get(i1).toString().split(">");
                    if (bol[1].equalsIgnoreCase(boli[1])){
                        anasanimg.setImageResource(R.drawable.ic_checkico);
                        touchdet[0] = false;

                    }
                }

            }



            anasanimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (touchdet[0]){
                        touchdet[0] = false;
                        anasanimg.setImageResource(R.drawable.ic_checkico);
                        ilgSanEkle(v.getContext(),bol[1]);


                    } else {
                        ArrayList ilgson = (ArrayList) databaseHelper.ilgsancek();

                        if (ilgson.size()>3){
                            touchdet[0] = true;
                            anasanimg.setImageResource(R.drawable.transparentshape);
                            ilgSanCikar(v.getContext(),bol[1]);
                        } else {
                            Toast.makeText(context, "En Az 3 Sanatçı Kalmalı...", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            anasantex.setTag("bastex");
            ViewEditor(anasantex,35,35);
            anasantex.setText(bol[1]);



        }

        for (int i=0; i<everysang.size(); i++){

            final Boolean[] touchdet = {true};

            String[] bol = everysang.get(i).toString().split(">");
            View sanlayi = View.inflate(context,R.layout.everysanlarlay,null);
            linearLayout.addView(sanlayi);

            LinearLayout anapinlay = sanlayi.findViewById(R.id.anapinlay);
            ImageView anasanimg = sanlayi.findViewById(R.id.anasanimg);
            TextView anasantex = sanlayi.findViewById(R.id.anasantex);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(glowidth/4, LinearLayout.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(glowidth/80,gloheight/80,glowidth/80,gloheight/80);
            anapinlay.setLayoutParams(layoutParams);


            File imgFile = new  File(context.getExternalFilesDir(null)+"/msapp/sanresnew/"+bol[3]);

            if(imgFile.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                BitmapDrawable ob = new BitmapDrawable(getResources(), myBitmap);
                anasanimg.setBackground(ob);

            }

           ArrayList ilgsanlar = (ArrayList) databaseHelper.ilgsancek();
            if (ilgsanlar.size()>0){
                for (int i1=0; i1<ilgsanlar.size(); i1++){
                    String[] boli = ilgsanlar.get(i1).toString().split(">");
                    if (bol[1].equalsIgnoreCase(boli[1])){
                        anasanimg.setImageResource(R.drawable.ic_checkico);
                        touchdet[0] = false;
                    }
                }

            }




            anasanimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (touchdet[0]){
                        touchdet[0] = false;
                        anasanimg.setImageResource(R.drawable.ic_checkico);
                        ilgSanEkle(v.getContext(),bol[1]);
                    } else {
                        ArrayList ilgson = (ArrayList) databaseHelper.ilgsancek();

                        if (ilgson.size()>3){
                            touchdet[0] = true;
                            anasanimg.setImageResource(R.drawable.transparentshape);
                            ilgSanCikar(v.getContext(),bol[1]);
                        } else {
                            Toast.makeText(context, "En Az 3 Sanatçı Kalmalı...", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });

            anasantex.setTag("bastex");
            ViewEditor(anasantex,35,35);
            anasantex.setText(bol[1]);

        }


    }

    public void initFavsar(LinearLayout linearLayout){
        Context context = linearLayout.getContext();
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        ArrayList favsar = (ArrayList) databaseHelper.favsarcekdb();
        ArrayList everysan = (ArrayList) databaseHelper.everysans();
        ArrayList everysang = (ArrayList) databaseHelper.everysansg();

        for (int i=0; i<favsar.size(); i++){
            ArrayList aramanotag = (ArrayList) databaseHelper.aramanotag();
            String[] favbol = favsar.get(i).toString().split(">");
            String sarki = favbol[1]+" - "+favbol[2];
            Boolean isStored = false;

            for (int a=0; a<aramanotag.size(); a++){
                String[] bol = aramanotag.get(a).toString().split(">");
                if (sarki.equalsIgnoreCase(bol[1])){
                    isStored = true;
                }
            }

            LinearLayout linearLayout1 = new LinearLayout(context);
            linearLayout1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT));
            linearLayout1.setOrientation(LinearLayout.VERTICAL);
            linearLayout.addView(linearLayout1);

            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(glowidth/4,gloheight/10));
            for (int s=0; s<everysan.size(); s++){
                String[] bolsa = everysan.get(s).toString().split(">");
                if (favbol[1].equalsIgnoreCase(bolsa[1])){
                    int drawableID2 = context.getResources().getIdentifier(bolsa[3], "drawable", getPackageName());
                    imageView.setImageResource(drawableID2);

                }
            }
            if (everysang.size()>0){
                for (int s=0; s<everysang.size(); s++){
                    String[] bolsa = everysang.get(s).toString().split(">");
                    if (favbol[1].equalsIgnoreCase(bolsa[1])){
                        File imgFile = new  File(context.getExternalFilesDir(null)+"/msapp/sanresnew/"+bolsa[3]);

                        if(imgFile.exists()){

                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                            imageView.setImageBitmap(myBitmap);

                        }

                    }
                }
            }


            linearLayout1.addView(imageView);

            LinearLayout linearLayout2 = new LinearLayout(context);
            linearLayout2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout2.setOrientation(LinearLayout.VERTICAL);
            linearLayout1.addView(linearLayout2);

            TextView textView = new TextView(context);
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            textView.setTypeface(ResourcesCompat.getFont(context,R.font.raleway_medium));
            textView.setTextColor(Color.WHITE);
            textView.setGravity(Gravity.CENTER);
            textView.setText(favbol[2]);
            textView.setTag("bastex");
            ViewEditor(textView,25,25);
            linearLayout2.addView(textView);

            String geText = textView.getText().toString();
            if (geText.length()>9){
                geText = geText.substring(0,6)+"...";
                textView.setText(geText);
            }

            ImageView imageView1 = new ImageView(context);
            imageView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,gloheight/16));
            imageView1.setImageResource(R.drawable.ic_favfill_large);
            linearLayout2.addView(imageView1);

            Boolean finalIsStored = isStored;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),msarkibak.class);
                    intent.putExtra("sarkad",favbol[1]+" - "+favbol[2]);
                    intent.putExtra("isStored", finalIsStored.toString());
                    intent.putExtra("whereback","kulprofilo");
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_out_left,R.anim.slide_in_right);
                }
            });

            imageView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    linearLayout.removeView(linearLayout1);
                    favkaldir(favbol[1],favbol[2],v.getContext(),findViewById(R.id.kulprofilli));
                    favsarprof.setText("Favorİ Şarkılarım "+"("+linearLayout.getChildCount()+")");
                }
            });
        }

        favsarprof.setText("Favorİ Şarkılarım "+"("+linearLayout.getChildCount()+")");
    }

    public void favkaldir(String favsanad, String favsarkad, Context context,LinearLayout linearLayout){

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        ArrayList favsarlar = (ArrayList) databaseHelper.favsarcekdb();
        for(int i=0; i<favsarlar.size(); i++){
            String[] bol = favsarlar.get(i).toString().split(">");
            if (bol[1].equalsIgnoreCase(favsanad)&&bol[2].equalsIgnoreCase(favsarkad)){
                Favsar favsar = new Favsar(Integer.parseInt(bol[0]),bol[1],bol[2],bol[3],bol[4],bol[5],bol[6]);
                Boolean deletefav = databaseHelper.deletefavsar(favsar);
                if (!deletefav){

                    Snackbar snackbar = Snackbar.make(linearLayout, bol[2]+" favorilerden kaldırıldı", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            performCrop(imageUri);
        }

        if (resultCode == RESULT_OK && requestCode == TAKE_PICTURE){

            imageUri = data.getData();
            performCrop(imageUri);

        }

        if (resultCode == RESULT_OK && requestCode == PIC_CROP){
            if (data != null) {
                // get the returned data
                Bundle extras = data.getExtras();
                // get the cropped bitmap
                Bitmap selectedBitmap = extras.getParcelable("data");
                kulprores.setImageBitmap(getRoundedShape(selectedBitmap));
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "PROFOTO_" + timeStamp + ".jpg";

                File dirstore = new File(kulprofilo.this.getExternalFilesDir(null)+"/msapp/profotos");
                if (!dirstore.exists()){
                    dirstore.mkdirs();
                    File[] dosyalar = dirstore.listFiles();
                    for (int i=0; i<dosyalar.length; i++){
                        if (dosyalar[i].delete()){

                        }
                    }
                } else {
                    File[] dosyalar = dirstore.listFiles();
                    for (int i=0; i<dosyalar.length; i++){
                        if (dosyalar[i].delete()){

                        }
                    }
                }

                File imstore = new File(kulprofilo.this.getExternalFilesDir(null)+"/msapp/profotos/"+imageFileName);
                if (!imstore.exists()){
                    try {
                        imstore.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                getRoundedShape(selectedBitmap).compress(Bitmap.CompressFormat.JPEG, 100, bytes);

                try {
                    FileOutputStream fo = new FileOutputStream(imstore);
                    fo.write(bytes.toByteArray());
                    fo.flush();
                    fo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }

    private void performCrop(Uri picUri) {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            // set crop properties here
            cropIntent.putExtra("crop", true);
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 128);
            cropIntent.putExtra("outputY", 128);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "Resmi düzenlemek için galeriden tekrar seçebilirsiniz";
        }
    }

    public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        // TODO Auto-generated method stub
        int targetWidth = glowidth;
        int targetHeight = glowidth;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight,Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth,
                        targetHeight), null);
        return targetBitmap;
    }


    private void openCamera(){
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, TAKE_PICTURE);
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
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
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(kulprofilo.this,MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_out_left,R.anim.slide_in_right);

    }
}