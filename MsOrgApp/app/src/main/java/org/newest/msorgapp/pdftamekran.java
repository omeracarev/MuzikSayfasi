package org.newest.msorgapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class pdftamekran extends AppCompatActivity {
     private PDFView tampdfview;
     private Bundle extras;
     private String pdfuri;
     private String sarkad;
     private Context context;
     private int glowidth,gloheight;
     private int[] colorDiz;
     private boolean menudisscheck = true;
     private ArrayList<DrawingText> drawingTexts = new ArrayList<DrawingText>();
     private ArrayList<PopupWindow> popupWindows = new ArrayList<PopupWindow>();


    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    private void shareScreenShot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri photoURI = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", imageFile);
        intent.setDataAndType(photoURI, "image/*");
        intent.putExtra(Intent.EXTRA_STREAM,photoURI);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdftamekran);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        glowidth = displayMetrics.widthPixels;
        gloheight = displayMetrics.heightPixels;

         colorDiz = new int[]{Color.BLACK,Color.BLUE,Color.RED,Color.YELLOW,Color.GREEN,Color.MAGENTA,Color.CYAN};

        tampdfview = findViewById(R.id.tampdfview);
        extras = getIntent().getExtras();
        context = pdftamekran.this;
        pdfuri = extras.getString("doc");
        sarkad = extras.getString("sarkad");
        Handler handler = new Handler();
        Runnable runnable = () -> {
            if (menudisscheck){
                createBottomMenuPop();
            }

        };
        handler.postDelayed(runnable,3000);

        if (!sarkad.equalsIgnoreCase("frommainact")){
            int rawID = context.getResources().getIdentifier(pdfuri,"raw",getPackageName());
            if (rawID!=0){
                InputStream inputStream = context.getResources().openRawResource(rawID);
                tampdfview.fromStream(inputStream)
                        .onLongPress(e -> {
                            int x = (int)e.getX();
                            int y = (int)e.getY();

                            switch (e.getAction()) {
                                case MotionEvent.ACTION_DOWN:
                            }

                            createAndInfilateNoteLabel(x-glowidth/2,y-(gloheight/2-gloheight/16),x,y);
                        })
                        .onTap(e -> {
                            if (!menudisscheck){
                                createBottomMenuPop();
                                menudisscheck = true;
                            }
                            return false;
                        })
                        .load();
            } else {
                tampdfview.fromFile(new File(pdftamekran.this.getExternalFilesDir(null)+"/msapp/pdfnews/"+pdfuri))
                        .onLongPress(e -> {
                            int x = (int)e.getX();
                            int y = (int)e.getY();

                            switch (e.getAction()) {
                                case MotionEvent.ACTION_DOWN:
                            }

                            createAndInfilateNoteLabel(x-glowidth/2,y-(gloheight/2-gloheight/16),x,y);
                        })
                        .onTap(e -> {
                            if (!menudisscheck){
                                createBottomMenuPop();
                                menudisscheck = true;
                            }
                            return false;
                        })
                        .load();

            }
        } else {
            tampdfview.fromUri(Uri.parse(pdfuri))
                    .onLongPress(e -> {
                        int x = (int)e.getX();
                        int y = (int)e.getY();

                        switch (e.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                        }

                        createAndInfilateNoteLabel(x-glowidth/2,y-(gloheight/2-gloheight/16),x,y);
                    })
                    .onTap(e -> {
                        if (!menudisscheck){
                            createBottomMenuPop();
                            menudisscheck = true;
                        }
                        return false;
                    })
                    .load();
        }

    }

    private void savepdfname(View view, View folderall ){
        PopupWindow popUp = new PopupWindow(context);
        popUp.setBackgroundDrawable(getResources().getDrawable(R.drawable.transparentshape));
        LinearLayout layout = (LinearLayout) LinearLayout.inflate(context,R.layout.savepdfpop,null);

        TextView savepdfbaslik = layout.findViewById(R.id.savepdfbaslik);
        EditText savepdfet = layout.findViewById(R.id.savedpdfet);
        ImageView savepdfok = layout.findViewById(R.id.savedpdfok);
        ImageView savepdfno = layout.findViewById(R.id.savedpdfno);

        ViewEditor(savepdfbaslik,40,40);
        ViewEditor(savepdfet,35,35);

        savepdfok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeScreenshot(savepdfet.getText().toString(),folderall);
                popUp.dismiss();
            }
        });

        savepdfno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp.dismiss();
            }
        });

        popUp.setOutsideTouchable(true);
        popUp.setFocusable(true);
        popUp.setContentView(layout);
        popUp.update(0, 0, glowidth/3, gloheight/3);
        popUp.showAtLocation(layout,Gravity.CENTER,0,0);
    }

    private void createBottomMenuPop(){

        PopupWindow popUp = new PopupWindow(context);
        popUp.setBackgroundDrawable(getResources().getDrawable(R.drawable.transparentshape));
        LinearLayout layout = (LinearLayout) LinearLayout.inflate(context,R.layout.bottom_menu_pop,null);

        ImageView hidemenu =layout.findViewById(R.id.drawhidemenu);
        ImageView drawmenu =layout.findViewById(R.id.drawmenu);
        ImageView deleteall=layout.findViewById(R.id.deleteallmenu);
        ImageView folderall=layout.findViewById(R.id.allfoldermenu);
        ImageView saveall  =layout.findViewById(R.id.saveallmenu);
        ImageView rotate   =layout.findViewById(R.id.rotatemenu);

        hidemenu.setOnClickListener(v -> {
            popUp.dismiss();
            menudisscheck = false;
            Snackbar.make(findViewById(R.id.tamlay),"Menüyü görmek için herhangi bir yere dokunun...", BaseTransientBottomBar.LENGTH_LONG).show();
        });

        drawmenu.setOnClickListener(v -> Snackbar.make(findViewById(R.id.tamlay),"Herhangi bir yere basılı tutun...", BaseTransientBottomBar.LENGTH_LONG).show());

        deleteall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(findViewById(R.id.tamlay),"Tüm Çizimler Silinecek...",BaseTransientBottomBar.LENGTH_LONG).setAction("Hepsini Sil", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindows.size()>0){
                            for (int i=0; i<popupWindows.size(); i++){
                                popupWindows.get(i).dismiss();
                            }
                        }

                    }
                }).show();
            }
        });

        saveall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                savepdfname(v,folderall);
            }
        });

        folderall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageGallery(folderall);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            popUp.setAttachedInDecor(true);
        }
        popUp.setOutsideTouchable(false);
        popUp.setFocusable(false);
        popUp.setContentView(layout);
        popUp.update(0, 0, glowidth/2, gloheight/8);
        try {
            popUp.showAtLocation(layout,Gravity.TOP|Gravity.CENTER,0,0);
        } catch (WindowManager.BadTokenException e){

        }

        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void createAndInfilateNoteLabel(int x, int y, int popx, int popy){
        PopupWindow popUp = new PopupWindow(context);
        LinearLayout layout = (LinearLayout) LinearLayout.inflate(context,R.layout.pdfediterpop,null);
        final int[] sontextsize = {40};
        final int[] sonTextColor = {Color.BLACK};

        ImageView pdfediterok = layout.findViewById(R.id.pdfediterok);
        TextView pdfediterboybas = layout.findViewById(R.id.pdfediterboybas);
        SeekBar pdfediterboyseek = layout.findViewById(R.id.pdfediterboyseek);
        TextView pdfediterbasrenk = layout.findViewById(R.id.pdfediterbasrenk);
        SeekBar pdfediterseekrenk = layout.findViewById(R.id.pdfediterseekrenk);
        ImageView pdfeditercancel = layout.findViewById(R.id.pdfeditercancel);
        EditText pdfediteret = layout.findViewById(R.id.pdfediteret);

        ViewEditor(pdfediterboybas,35,35);
        ViewEditor(pdfediterbasrenk,35,35);
        ViewEditor(pdfediteret,40,40);

        pdfediterboyseek.setMax(15);
        pdfediterboyseek.setProgress(0);
        pdfediterboyseek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sontextsize[0] = 40-progress;
                ViewEditor(pdfediteret,sontextsize[0],sontextsize[0]);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        pdfediterseekrenk.setMax(colorDiz.length-1);
        pdfediterseekrenk.setProgress(0);
        pdfediterseekrenk.setThumbTintList(ColorStateList.valueOf(colorDiz[0]));
        pdfediterseekrenk.setProgressTintList(ColorStateList.valueOf(colorDiz[0]));

        pdfediterseekrenk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBar.setThumbTintList(ColorStateList.valueOf(colorDiz[progress]));
                seekBar.setProgressTintList(ColorStateList.valueOf(colorDiz[progress]));
                pdfediteret.setTextColor(colorDiz[progress]);
                sonTextColor[0] = colorDiz[progress];
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });




        final Boolean[] disBool = {true};

        pdfediterok.setOnClickListener(v -> {
            disBool[0] = true;
            popUp.dismiss();
        });

        pdfeditercancel.setOnClickListener(v -> {
            disBool[0] = false;
            popUp.dismiss();
        });

        popUp.setOnDismissListener(() -> {
            String etget = pdfediteret.getText().toString();
            if (disBool[0] && !etget.equalsIgnoreCase("")){
                Paint mPaint = new Paint();
                mPaint.setAntiAlias(true);
                mPaint.setTextSize(pdfediteret.getTextSize());
                mPaint.setTypeface(ResourcesCompat.getFont(context,R.font.raleway_medium));
                float texw = mPaint.measureText(etget,0,etget.length());
                textpopup(x,y,popx,popy,etget, (int) pdfediteret.getTextSize(),texw, sonTextColor[0]);
            } else {

            }
        });

        popUp.setOutsideTouchable(true);
        popUp.setFocusable(true);
        popUp.setContentView(layout);
        popUp.update(x, y, glowidth/2, gloheight/3,true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            popUp.setAttachedInDecor(true);
        }
        popUp.showAtLocation(layout,Gravity.CENTER,x,y);

    }


    private void takeScreenshot(String name, View view) {
        String currentDate = new SimpleDateFormat("ddMMyyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HHmmss", Locale.getDefault()).format(new Date());
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Ekran Hazırlanıyor...");
        progressDialog.setCancelable(true);
        progressDialog.show();

            // image naming and path  to include sd card  appending name you choose for file
            File snapfolder = new File(context.getExternalFilesDir(null)+"/msapp/snapshots");
            if (!snapfolder.exists()){
                snapfolder.mkdirs();
            }

            tampdfview.setDrawingCacheEnabled(true);

            if (drawingTexts.size()>0){
                for (int i=0; i<drawingTexts.size(); i++){
                    tampdfview.addView(drawingTexts.get(i));
                }
            }

            // create bitmap screen capture
            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = Bitmap.createBitmap(tampdfview.getDrawingCache());
                    tampdfview.setDrawingCacheEnabled(false);
                    File imageFile = new File(snapfolder.getPath()+"/"+name+"_"+currentDate+currentTime+".jpg");
                    try{
                        FileOutputStream outputStream = new FileOutputStream(imageFile);
                        int quality = 100;
                        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                        outputStream.flush();
                        outputStream.close();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }

                    openImageGallery(view);
                    progressDialog.dismiss();
                }
            };
            handler.postDelayed(runnable,3000);
    }

    private void expandImage(Uri uri, String filename){
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


    private void openImageGallery(View view){

         tampdfview.removeAllViews();

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

        ViewEditor(imgalbaslik,30,30);

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
                        expandImage(Uri.fromFile(snaplists[finalI]),snaplists[finalI].getName());
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
                                if (event == DISMISS_EVENT_TIMEOUT || event == DISMISS_EVENT_CONSECUTIVE){
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
                        shareScreenShot(snaplists[finalI]);
                    }
                });

            }
        }



        popUp.setOutsideTouchable(true);
        popUp.setFocusable(true);
        popUp.setContentView(layout);
        popUp.update(0, 0, glowidth/2+glowidth/3, gloheight/2+gloheight/3);
        popUp.showAtLocation(layout,Gravity.CENTER,0,0);
    }


    private void textpopup(int x, int y, int popx, int popy, String text,int sontextsize, float texw, int sonTextColor){
        PopupWindow popUp = new PopupWindow(context);
        popUp.setBackgroundDrawable(getResources().getDrawable(R.drawable.transparentshape));
        LinearLayout layout = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(params);
        layout.setWeightSum(20);
        layout.setBackgroundResource(R.drawable.transparentshape);
        layout.setOrientation(LinearLayout.VERTICAL);

        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        textView.setTypeface(ResourcesCompat.getFont(context,R.font.raleway_medium));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(sonTextColor);
        textView.setTag("bastex");
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,sontextsize);
        layout.addView(textView);
        textView.setBackgroundColor(Color.TRANSPARENT);
        final int[] mCurrentX = {x};
        final int[] mCurrentY = {y};

        DrawingText drawingText = new DrawingText(context);
        drawingText.initVar(textView,popx,popy+gloheight/13);
        drawingTexts.add(drawingText);
        //OnTouch ekle taşı

        textView.setOnTouchListener(new View.OnTouchListener() {
            private float mDx;
            private float mDy;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    mDx = mCurrentX[0] - event.getRawX();
                    mDy = mCurrentY[0] - event.getRawY();
                    popUp.update(mCurrentX[0], mCurrentY[0], glowidth/5, gloheight/4);
                    layout.setBackgroundResource(R.drawable.textpopbg);
                    Snackbar.make(findViewById(R.id.tamlay),"Yazıyı parmağınızla sürükleyin veya silin...", BaseTransientBottomBar.LENGTH_LONG).setAction("Sil", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    popUp.dismiss();
                                }
                            })
                            .show();

                } else
                if (action == MotionEvent.ACTION_MOVE) {
                    mCurrentX[0] = (int) (event.getRawX() + mDx);
                    mCurrentY[0] = (int) (event.getRawY() + mDy);
                    popUp.update(mCurrentX[0], mCurrentY[0], -1, -1);
                } else if (action == MotionEvent.ACTION_UP){
                    layout.setBackgroundResource(R.drawable.transparentshape);
                    popUp.update(mCurrentX[0], mCurrentY[0], (int)texw+glowidth/40, gloheight/10);
                    drawingTexts.remove(drawingText);
                    drawingText.initVar(textView, (int)((int)event.getRawX()+texw/6),(int)event.getRawY()+gloheight/60);
                    drawingTexts.add(drawingText);
                }
                return true;
            }
        });

        popUp.setOutsideTouchable(false);
        popUp.setFocusable(false);
        popUp.setContentView(layout);
        popUp.update(mCurrentX[0], mCurrentY[0], (int)texw, gloheight/10);
        popUp.showAtLocation(layout,Gravity.CENTER,mCurrentX[0],mCurrentY[0]);
        popupWindows.add(popUp);

        popUp.setOnDismissListener(() -> {
            drawingTexts.remove(drawingText);
            tampdfview.removeView(drawingText);
        });


    }

    private void ViewEditor(@NonNull View view, int olc1, int olc2){
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

    public class DrawingText extends  View{
        Context context;
        Paint paint;
        TextView textView;
        int x;
        int y;

        public DrawingText(Context c) {
            super(c);
            context = c;
        }

        public void initVar(TextView textViewin, int xin, int yin){
            textView = textViewin;
            x = xin;
            y = yin;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            paint = new Paint();
            paint.setTextSize(textView.getTextSize());
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setColor(textView.getCurrentTextColor());
            paint.setStyle(Paint.Style.FILL);
            paint.setTypeface(textView.getTypeface());
            canvas.drawText(textView.getText().toString(),x,y,paint);

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}