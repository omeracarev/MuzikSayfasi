package org.newest.msorgapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
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

public class CreateMsProf extends AppCompatActivity {
    ImageView imagepropop;
    private static final int TAKE_PICTURE = 1;
    private static final int PICK_IMAGE = 100;
    public static final int PERMISSION_READ = 0;
    private Uri imageUri;
    final int PIC_CROP = 1;
    int ilgcount = 0;
    int glowidth;
    int gloheight;
    CountDownTimer countDownTimer;
    ImageView closegiric;
    TextView kaygirtex;
    ImageView addphoto;
    EditText kuledtex;
    EditText sifedtex;
    EditText emedtex;
    TextView pinlisana,createprobut;
    LinearLayout horiclay;
    HorizontalScrollView hormsprof;
    private String URL = "https://www.xn--mziksayfas-9db95d.com/newnoti.php";

    //Kaydı Runtime oluştur.

    public void addkayvol(){
        Context context = CreateMsProf.this;
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        String kulad = kuledtex.getText().toString();
        String sifre = sifedtex.getText().toString();
        String email = emedtex.getText().toString();
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Bilgiler İşleniyor...");
        progressDialog.setCancelable(true);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onResponse(String response) {
                try {
                    response = response.trim();

                    if (!response.equalsIgnoreCase("no")||!response.equalsIgnoreCase("")){
                        Kulbi kulbi = new Kulbi(-1,kulad,sifre,email,response);
                        Boolean addkulbi = databaseHelper.addkulbi(kulbi);
                        if (addkulbi){
                            List<Ekcallar> ekcallars = databaseHelper.ekcalcek();
                            String kalp = ekcallars.get(0).getEkad();
                            String puan = ekcallars.get(0).getEkvid();
                            int intkalp = Integer.parseInt(kalp.trim())+5;
                            for (int i=0; i<ekcallars.size(); i++){
                                if (databaseHelper.deleteekcal(ekcallars.get(i))){

                                }
                            }

                            if (databaseHelper.addekcallar(new Ekcallar(-1,Integer.toString(intkalp),"0",puan,"0","0"))){
                                Toast.makeText(context, "+5 Can Hediye Edildi...", Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(context, "Profiliniz Başarıyla Oluşturuldu...", Toast.LENGTH_LONG).show();
                            Handler handler = new Handler();
                            Runnable runnable = new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    Intent intent = new Intent(context,kulprofilo.class);
                                    startActivity(intent);
                                    finish();
                                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                                }
                            };
                            handler.postDelayed(runnable,2000);
                        }
                    }
                }
                catch (Exception e){
                    Toast.makeText(context, "Bağlantı Hatası, Lütfen Tekrar Deneyin...", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Toast.makeText(context, "Bağlantı Hatası, Lütfen Tekrar Deneyin...", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> data = new HashMap<>();
                data.put("procontrol", kulad);
                data.put("kulsifre", sifre);
                data.put("kulmail", email);
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

    public void createPro(Context context){
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        String kulad = kuledtex.getText().toString();
        String sifre = sifedtex.getText().toString();
        String email = emedtex.getText().toString();

        if (kulad.equalsIgnoreCase(null)||
                sifre.equalsIgnoreCase(null)||
                email.equalsIgnoreCase(null)){
            Snackbar.make(findViewById(R.id.createmsanalay),"Alanlar boş bırakılamaz...",BaseTransientBottomBar.LENGTH_LONG);
        } else if ((kulad.length()>12 || kulad.length()<8)&&(sifre.length()>12 || kulad.length()<8)&&(!email.contains("@"))&&(!kulad.contains(">") || !kulad.contains("<"))){
            Toast.makeText(context, "Kullanıcı adı ve şifre en az 8, en fazla 12 karakterli olmalıdır, ve email @ içermelidir", Toast.LENGTH_LONG).show();
        } else if(ilgcount<3) {
            Toast.makeText(context,"Lütfen en az 3, en fazla 5 sanatçı seçin!",Toast.LENGTH_LONG).show();
        } else {
            addkayvol();
        }



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ms_prof);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        gloheight = displayMetrics.heightPixels;
        glowidth = displayMetrics.widthPixels;

        closegiric=  findViewById(R.id.closegiric);
        kaygirtex  = findViewById(R.id.kaygirtex);
        addphoto  =  findViewById(R.id.addphoto);
        kuledtex   = findViewById(R.id.kuledtex);
        sifedtex   = findViewById(R.id.sifedtex);
        emedtex    = findViewById(R.id.emedtex);
        pinlisana  = findViewById(R.id.pinlisana);
        horiclay =   findViewById(R.id.horiclay);
        hormsprof =  findViewById(R.id.hormsprof);
        createprobut = findViewById(R.id.createprobut);

        kaygirtex.setTag("bastex");
        pinlisana.setTag("bastex");
        kuledtex.setTag("basedit");
        sifedtex.setTag("basedit");
        emedtex.setTag("basedit");

        ViewEditor(kaygirtex,12,12);
        ViewEditor(kuledtex,20,20);
        ViewEditor(pinlisana,25,25);
        ViewEditor(sifedtex,20,20);
        ViewEditor(emedtex,20,20);

        createprobut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPro(v.getContext());
            }
        });

        closegiric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertbuilder();
            }
        });

        File dirstore = new File(CreateMsProf.this.getExternalFilesDir(null)+"/msapp/profotos");
        if (dirstore.exists()){
            File[] listedfile = dirstore.listFiles();
            if (listedfile.length>0){
                addphoto.setImageURI(Uri.fromFile(listedfile[0]));
            }

        }



       addphoto.setOnClickListener(new View.OnClickListener() {
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
                               imagepropop = addphoto;
                               openCamera();
                               break;
                           case R.id.pickgallery:
                               imagepropop = addphoto;
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

        createproftumsan(CreateMsProf.this,horiclay);
        int maxDeg = hormsprof.getWidth()+glowidth+glowidth/2;

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                 countDownTimer = new CountDownTimer(maxDeg*10,1) {
                     @Override
                     public void onTick(long millisUntilFinished) {
                         hormsprof.setScrollX((int) millisUntilFinished);
                     }

                     @Override
                     public void onFinish() {
                         hormsprof.setScrollX(0);
                     }
                 }.start();
            }
        };
        handler.postDelayed(runnable,1000);

    }

    public void createproftumsan(Context context, LinearLayout linearLayout){
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        ArrayList everysan = (ArrayList) databaseHelper.everysans();
        ArrayList everysang = (ArrayList) databaseHelper.everysansg();

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

            anasanimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (touchdet[0]){
                        touchdet[0] = false;
                        anasanimg.setImageResource(R.drawable.ic_checkico);
                        Ilgsanlar ilgsanlar = new Ilgsanlar(-1,bol[1]);
                        Boolean ekle = databaseHelper.addilgsanlar(ilgsanlar);
                        if (ekle){
                            Snackbar.make(findViewById(R.id.createmsanalay),bol[1]+" Eklendi...",BaseTransientBottomBar.LENGTH_LONG).show();
                            ilgcount++;
                        }
                    } else {
                        touchdet[0] = true;
                        anasanimg.setImageResource(R.drawable.transparentshape);
                        ArrayList ilgsanlar = (ArrayList) databaseHelper.ilgsancek();
                        for (int a=0; a<ilgsanlar.size(); a++){
                            String[] bolilg = ilgsanlar.get(a).toString().split(">");
                            if (bolilg[1].equalsIgnoreCase(bol[1])){
                                Ilgsanlar silig = new Ilgsanlar(Integer.parseInt(bolilg[0]),bolilg[1]);
                                Boolean delete = databaseHelper.deleteilgsan(silig);
                                if (!delete){
                                    Snackbar.make(findViewById(R.id.createmsanalay),bol[1]+" Çıkarıldı...",BaseTransientBottomBar.LENGTH_LONG).show();
                                    ilgcount--;
                                }
                            }
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


            File imgFile = new  File(CreateMsProf.this.getExternalFilesDir(null)+"/msapp/sanresnew/"+bol[3]);

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
                        Ilgsanlar ilgsanlar = new Ilgsanlar(-1,bol[1]);
                        Boolean ekle = databaseHelper.addilgsanlar(ilgsanlar);
                        if (ekle){
                            Snackbar.make(findViewById(R.id.createmsanalay),bol[1]+" Eklendi...",BaseTransientBottomBar.LENGTH_LONG).show();
                            ilgcount++;
                        }
                    } else {
                        touchdet[0] = true;
                        anasanimg.setImageResource(R.drawable.transparentshape);
                        ArrayList ilgsanlar = (ArrayList) databaseHelper.ilgsancek();
                        for (int a=0; a<ilgsanlar.size(); a++){
                            String[] bolilg = ilgsanlar.get(a).toString().split(">");
                            if (bolilg[1].equalsIgnoreCase(bol[1])){
                                Ilgsanlar silig = new Ilgsanlar(Integer.parseInt(bolilg[0]),bolilg[1]);
                                Boolean delete = databaseHelper.deleteilgsan(silig);
                                if (!delete){
                                    Snackbar.make(findViewById(R.id.createmsanalay),bol[1]+" Çıkarıldı...",BaseTransientBottomBar.LENGTH_LONG).show();
                                    ilgcount--;
                                }
                            }
                        }
                    }
                }
            });

            anasantex.setTag("bastex");
            ViewEditor(anasantex,35,35);
            anasantex.setText(bol[1]);

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
                imagepropop.setImageBitmap(getRoundedShape(selectedBitmap));
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "PROFOTO_" + timeStamp + ".jpg";

                File dirstore = new File(CreateMsProf.this.getExternalFilesDir(null)+"/msapp/profotos");
                if (dirstore.exists()){
                    File[] dosyalar = dirstore.listFiles();
                    for (int i=0; i<dosyalar.length; i++){
                        if (dosyalar[i].delete()){

                        }
                    }
                }

                File imstore = new File(CreateMsProf.this.getExternalFilesDir(null)+"/msapp/profotos/"+imageFileName);
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

    public void alertbuilder (){

        AlertDialog.Builder alertdialog = new AlertDialog.Builder(CreateMsProf.this);
        alertdialog.setCancelable(true);
        alertdialog.setMessage("Henüz profilinizi oluşturamadınız, eğer çıkarsanız girdiğiniz bilgiler kaybolacak...");
        alertdialog.setTitle("Kaydının Henüz Tamamlanamadı...");
        alertdialog.setIcon(R.drawable.mslogobuy);
        alertdialog.setPositiveButton("Devam Et", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertdialog.setNegativeButton("Çık", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                 Intent intent = new Intent(CreateMsProf.this,MainActivity.class);
                 startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });
        alertdialog.create();
        alertdialog.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CreateMsProf.this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }
}