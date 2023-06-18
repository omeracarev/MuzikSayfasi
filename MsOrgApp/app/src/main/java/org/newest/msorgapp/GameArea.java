package org.newest.msorgapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.plattysoft.leonids.ParticleSystem;
import java.util.ArrayList;

public class GameArea extends AppCompatActivity {
    TextView gametextad,gametextsark,puantex,heartcount;
    ImageView gemimg,heartimg,sanimg;
    LinearLayout scrolay,cardlay1,cardlay2,cardlay3,cardlay4;
    SeekBar seekBar;
    ScrollView scrollView;
    int[] notesRaw;
    String[] notenames;
    String[] notenames2;
    int widtho,heighto,vites=7,currentTime,currentY,visibleBoundsY1,visibleBoundsY2;
    CountDownTimer countDownTimer;
    boolean isTapping;
    Boolean point1=false,point2=false;
    int curpuan = 0;
    String cursarkad;
    ProgressDialog progressDialog;

    ArrayList cardlay1recta = new ArrayList<Integer>();
    ArrayList cardlay2recta = new ArrayList<Integer>();
    ArrayList cardlay3recta = new ArrayList<Integer>();
    ArrayList cardlay4recta = new ArrayList<Integer>();

    ArrayList cardlay1rectb = new ArrayList<Integer>();
    ArrayList cardlay2rectb = new ArrayList<Integer>();
    ArrayList cardlay3rectb = new ArrayList<Integer>();
    ArrayList cardlay4rectb = new ArrayList<Integer>();

    public void vitesayar(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;

        if (height<1000){
            vites=7;
        } else if (height>1000 && height<1300){
            vites = 6;
        } else if (height>1300 && height<1700){
            vites=5;
        } else if (height>1700 && height<2100){
            vites=4;
        } else if (height>2100){
            vites = 3;
        }

        //Devicelara göre ayarla
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_area);
        vitesayar();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
       widtho = displayMetrics.widthPixels;
        heighto = displayMetrics.heightPixels;

        Bundle bundle = getIntent().getExtras();
        cursarkad = bundle.getString("sarkname");

        cardlay1 = findViewById(R.id.cardlay1);
        cardlay2 = findViewById(R.id.cardlay2);
        cardlay3 = findViewById(R.id.cardlay3);
        cardlay4= findViewById(R.id.cardlay4);

        scrolay = findViewById(R.id.scrolay);

        scrollView = findViewById(R.id.scrollView);
        seekBar = findViewById(R.id.seekBar);
        progressDialog = new ProgressDialog(this);

        notenames2 = new String[]{"do","do#","re","re#","mi","fa","fa#","sol","sol#","la","la#","si","do","do#","re","re#","mi","fa","fa#","sol","sol#","la","la#","si","do","do#","re","re#","mi","fa","fa#","sol","sol#","la","la#","si","do","do#","re","re#","mi","fa","fa#","sol","sol#","la","la#","si","do","do#","re","re#","mi","fa","fa#","sol","sol#","la","la#","si","do","sus"};
        notesRaw = new int[]{R.raw.c2,R.raw.db2,R.raw.d2,R.raw.eb2,R.raw.e2,R.raw.f2,R.raw.gb2,R.raw.g2,R.raw.ab2,R.raw.a2,R.raw.bb2,
                R.raw.b2,R.raw.c3,R.raw.db3,R.raw.d3,R.raw.eb3,R.raw.e3,R.raw.f3,R.raw.gb3,R.raw.g3,R.raw.ab3,R.raw.a3,R.raw.bb3,
                R.raw.b3,R.raw.c4,R.raw.db4,R.raw.d4,R.raw.eb4,R.raw.e4,R.raw.f4,R.raw.gb4,R.raw.g4,R.raw.ab4,R.raw.a4,R.raw.bb4,
                R.raw.b4,R.raw.c5,R.raw.db5,R.raw.d5,R.raw.eb5,R.raw.e5,R.raw.f5,R.raw.gb5,R.raw.g5,R.raw.ab5,R.raw.a5,R.raw.bb5,R.raw.b5,R.raw.c6,R.raw.db6,R.raw.d6,R.raw.eb6,R.raw.e6,R.raw.f6,R.raw.gb6,R.raw.g6,R.raw.ab6,R.raw.a6,R.raw.bb6,R.raw.b6,R.raw.c7,0};
        notenames = new String[]{"C2","C#2","D2","D#2","E2","F2","F#2","G2","G#2","A2","A#2","B2","C3","C#3","D3","D#3","E3","F3","F#3","G3","G#3","A3","A#3","B3","C4","C#4","D4","D#4","E4","F4","F#4","G4","G#4","A4","A#4","B4","C5","C#5","D5","D#5","E5","F5","F#5","G5","G#5","A5","A#5","B5","C6","C#6","D6","D#6","E6","F6","F#6","G6","G#6","A6","A#6","B6","C7","sus"};

        initGameArea();
        initializeScrolay();
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                initCardPopup();
              //  initcardbuttons();
            }
        };
        handler.postDelayed(runnable,2000);
        seekBar.setEnabled(false);
    }

    public void changeRatings(int puancur){
        int puan=0;
        int x = puancur/10000;
        if (x>1){
           puan = puancur%10000;
        } else {
            puan = puancur;
        }

        LinearLayout ratingLay = findViewById(R.id.ratingLay);
        ImageView star1 = (ImageView) ratingLay.getChildAt(0);
        ImageView star2 = (ImageView) ratingLay.getChildAt(1);
        ImageView star3 = (ImageView) ratingLay.getChildAt(2);
        ImageView star4 = (ImageView) ratingLay.getChildAt(3);
        ImageView star5 = (ImageView) ratingLay.getChildAt(4);
        if (puan>500&&puan<1500){
            star1.setImageResource(R.drawable.ic_star_half);
            star2.setImageResource(R.drawable.ic_star_border);
            star3.setImageResource(R.drawable.ic_star_border);
            star4.setImageResource(R.drawable.ic_star_border);
            star5.setImageResource(R.drawable.ic_star_border);

        }else if (puan>1500&&puan<2500){
            star1.setImageResource(R.drawable.ic_star);
            star2.setImageResource(R.drawable.ic_star_border);
            star3.setImageResource(R.drawable.ic_star_border);
            star4.setImageResource(R.drawable.ic_star_border);
            star5.setImageResource(R.drawable.ic_star_border);

        } else if (puan>2500&&puan<3500){
            star1.setImageResource(R.drawable.ic_star);
            star2.setImageResource(R.drawable.ic_star_half);
            star3.setImageResource(R.drawable.ic_star_border);
            star4.setImageResource(R.drawable.ic_star_border);
            star5.setImageResource(R.drawable.ic_star_border);

        } else if (puan>3500&&puan<5000){
            star1.setImageResource(R.drawable.ic_star);
            star2.setImageResource(R.drawable.ic_star);
            star3.setImageResource(R.drawable.ic_star_border);
            star4.setImageResource(R.drawable.ic_star_border);
            star5.setImageResource(R.drawable.ic_star_border);

        } else if (puan>5000&&puan<6000){
            star1.setImageResource(R.drawable.ic_star);
            star2.setImageResource(R.drawable.ic_star);
            star3.setImageResource(R.drawable.ic_star_half);
            star4.setImageResource(R.drawable.ic_star_border);
            star5.setImageResource(R.drawable.ic_star_border);

        } else if (puan>6000&&puan<7000){
            star1.setImageResource(R.drawable.ic_star);
            star2.setImageResource(R.drawable.ic_star);
            star3.setImageResource(R.drawable.ic_star);
            star4.setImageResource(R.drawable.ic_star_border);
            star5.setImageResource(R.drawable.ic_star_border);

        } else if (puan>7000&&puan<8000){
            star1.setImageResource(R.drawable.ic_star);
            star2.setImageResource(R.drawable.ic_star);
            star3.setImageResource(R.drawable.ic_star);
            star4.setImageResource(R.drawable.ic_star_half);
            star5.setImageResource(R.drawable.ic_star_border);

        } else if (puan>8000&&puan<9000){
            star1.setImageResource(R.drawable.ic_star);
            star2.setImageResource(R.drawable.ic_star);
            star3.setImageResource(R.drawable.ic_star);
            star4.setImageResource(R.drawable.ic_star);
            star5.setImageResource(R.drawable.ic_star_border);

        } else if (puan>9000&&puan<10000){
            star1.setImageResource(R.drawable.ic_star);
            star2.setImageResource(R.drawable.ic_star);
            star3.setImageResource(R.drawable.ic_star);
            star4.setImageResource(R.drawable.ic_star);
            star5.setImageResource(R.drawable.ic_star_half);

        } else if (puan>10000){
            star1.setImageResource(R.drawable.ic_star);
            star2.setImageResource(R.drawable.ic_star);
            star3.setImageResource(R.drawable.ic_star);
            star4.setImageResource(R.drawable.ic_star);
            star5.setImageResource(R.drawable.ic_star);
            yoyoclas(Techniques.Tada,500,0,ratingLay);
        }
    }

    public void initCardPopup(){

        int screenWidth=0,screenHeight = 0;

        Point size = new Point();
        try {
            this.getWindowManager().getDefaultDisplay().getRealSize(size);
            screenWidth = size.x;
            screenHeight = size.y;
        } catch (NoSuchMethodError e) {
            Log.i("error", "it can't work");
        }


        PopupWindow popUp = new PopupWindow(this);
        popUp.setBackgroundDrawable(getResources().getDrawable(R.drawable.transparentshape));
        LinearLayout layout = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(params);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(Color.TRANSPARENT);
        layout.setWeightSum(2);

        LinearLayout boslin = new LinearLayout(this);
        boslin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1));
        boslin.setOrientation(LinearLayout.HORIZONTAL);
        boslin.setBackgroundColor(Color.TRANSPARENT);
        //Color.parseColor("#23FDD835")
        layout.addView(boslin);

        LinearLayout butlin = new LinearLayout(this);
        butlin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1));
        butlin.setOrientation(LinearLayout.HORIZONTAL);
        butlin.setBackgroundColor(Color.parseColor("#9A000000"));
        butlin.setWeightSum(4);
        layout.addView(butlin);



        ImageView but1 = new ImageView(this);
        but1.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,1));
        but1.setImageResource(R.drawable.ic_butbir);
        butlin.addView(but1);

        ImageView but2 = new ImageView(this);
        but2.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,1));
        but2.setImageResource(R.drawable.ic_butiki);
        butlin.addView(but2);

        ImageView but3 = new ImageView(this);
        but3.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,1));
        but3.setImageResource(R.drawable.ic_butuc);
        butlin.addView(but3);

        ImageView but4 = new ImageView(this);
        but4.setLayoutParams(new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,1));
        but4.setImageResource(R.drawable.ic_butdort);
        butlin.addView(but4);

        popUp.setOutsideTouchable(false);
        popUp.setFocusable(false);
        popUp.setContentView(layout);
        popUp.showAtLocation(layout, Gravity.BOTTOM,widtho,heighto);
        popUp.update(0, screenHeight-heighto, widtho, heighto/4);

        for (int i=0; i<cardlay1.getChildCount(); i++){
            CardView cardView = (CardView) cardlay1.getChildAt(i);

            if (cardView.getTag(R.string.getact).toString().equalsIgnoreCase("yes")){
                cardlay1recta.add((int)(cardView.getY()+cardView.getHeight()-(3*heighto/6)));
                cardlay1rectb.add((int)(cardView.getY()+cardView.getHeight()-(3*heighto/6))-scrollView.getHeight()-cardView.getHeight()+(3*heighto/6));
            } else {
                cardlay1recta.add(0);
                cardlay1rectb.add(0);
            }
        }

        for (int i=0; i<cardlay2.getChildCount(); i++){
            CardView cardView = (CardView) cardlay2.getChildAt(i);
            if (cardView.getTag(R.string.getact).toString().equalsIgnoreCase("yes")){
                cardlay2recta.add((int)(cardView.getY()+cardView.getHeight()-(3*heighto/6)));
                cardlay2rectb.add((int)(cardView.getY()+cardView.getHeight()-(3*heighto/6))-scrollView.getHeight()-cardView.getHeight()+(3*heighto/6));
            } else {
                cardlay2recta.add(0);
                cardlay2rectb.add(0);
            }
        }

        for (int i=0; i<cardlay3.getChildCount(); i++){
            CardView cardView = (CardView) cardlay3.getChildAt(i);
            if (cardView.getTag(R.string.getact).toString().equalsIgnoreCase("yes")){
                cardlay3recta.add((int)(cardView.getY()+cardView.getHeight()-(3*heighto/6)));
                cardlay3rectb.add((int)(cardView.getY()+cardView.getHeight()-(3*heighto/6))-scrollView.getHeight()-cardView.getHeight()+(3*heighto/6));
            } else {
                cardlay3recta.add(0);
                cardlay3rectb.add(0);
            }
        }

        for (int i=0; i<cardlay4.getChildCount(); i++){
            CardView cardView = (CardView) cardlay4.getChildAt(i);
            if (cardView.getTag(R.string.getact).toString().equalsIgnoreCase("yes")){
                cardlay4recta.add((int)(cardView.getY()+cardView.getHeight()-(3*heighto/6)));
                cardlay4rectb.add((int)(cardView.getY()+cardView.getHeight()-(3*heighto/6))-scrollView.getHeight()-cardView.getHeight()+(3*heighto/6));
            } else {
                cardlay4recta.add(0);
                cardlay4rectb.add(0);
            }
        }

        but1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                yoyoclas(Techniques.Tada,1000,0,but1);
                if (event.getAction()==MotionEvent.ACTION_DOWN){



                    for (int i=cardlay1.getChildCount()-1; i>=0; i--){
                        CardView cardView = (CardView) cardlay1.getChildAt(i);
                        if (cardView.getTag(R.string.getact).toString().equalsIgnoreCase("yes")){
                            if (scrollView.getScrollY()<(int) cardlay1recta.get(i) && scrollView.getScrollY()> (int) cardlay1rectb.get(i)){

                                if (cardView.getTag(R.string.getnote)!=null ){
                                    for (int n=0; n<notenames.length; n++){
                                        if (notenames[n].equalsIgnoreCase(cardView.getTag(R.string.getnote).toString())){
                                            playNote(notesRaw[n]);
                                            isTapping = true;
                                            handlererit(cardView,cardlay1,R.drawable.ic_simsek);
                                        }
                                    }
                                }
                                break;
                            }
                        }

                    }
                }

                if (event.getAction()==MotionEvent.ACTION_UP){
                    isTapping = false;
                }
                return true;
            }
        });

        but2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                yoyoclas(Techniques.Tada,1000,0,but2);
                if (event.getAction()==MotionEvent.ACTION_DOWN){



                    for (int i=cardlay2.getChildCount()-1; i>=0; i--){
                        CardView cardView = (CardView) cardlay2.getChildAt(i);
                        if (cardView.getTag(R.string.getact).toString().equalsIgnoreCase("yes")){
                            if (scrollView.getScrollY()<(int) cardlay2recta.get(i) && scrollView.getScrollY()> (int) cardlay2rectb.get(i)){

                                if (cardView.getTag(R.string.getnote)!=null){
                                    for (int n=0; n<notenames.length; n++){
                                        if (notenames[n].equalsIgnoreCase(cardView.getTag(R.string.getnote).toString())){
                                            playNote(notesRaw[n]);
                                            isTapping = true;
                                            handlererit(cardView,cardlay2,R.drawable.ic_simsek);
                                        }
                                    }
                                }
                                break;
                            }
                        }

                    }
                }

                if (event.getAction()==MotionEvent.ACTION_UP){
                    isTapping = false;
                }
                return true;
            }
        });

        but3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    yoyoclas(Techniques.Tada,1000,0,but3);


                    for (int i=cardlay3.getChildCount()-1; i>=0; i--){
                        CardView cardView = (CardView) cardlay3.getChildAt(i);
                        if (cardView.getTag(R.string.getact).toString().equalsIgnoreCase("yes")){
                            if (scrollView.getScrollY()<(int) cardlay3recta.get(i) && scrollView.getScrollY()> (int) cardlay3rectb.get(i)){

                                if (cardView.getTag(R.string.getnote)!=null){
                                    for (int n=0; n<notenames.length; n++){
                                        if (notenames[n].equalsIgnoreCase(cardView.getTag(R.string.getnote).toString())){
                                            playNote(notesRaw[n]);
                                            isTapping = true;
                                            handlererit(cardView,cardlay3,R.drawable.ic_simsek);
                                        }
                                    }
                                }
                                break;
                            }
                        }

                    }
                }

                if (event.getAction()==MotionEvent.ACTION_UP){
                    isTapping = false;
                }
                return true;
            }
        });

        but4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    yoyoclas(Techniques.Tada,500,0,but4);

                    for (int i=cardlay4.getChildCount()-1; i>=0; i--){
                        CardView cardView = (CardView) cardlay4.getChildAt(i);
                        if (cardView.getTag(R.string.getact).toString().equalsIgnoreCase("yes")){
                            if (scrollView.getScrollY()<(int) cardlay4recta.get(i) && scrollView.getScrollY()> (int) cardlay4rectb.get(i)){

                                if (cardView.getTag(R.string.getnote)!=null){
                                    for (int n=0; n<notenames.length; n++){
                                        if (notenames[n].equalsIgnoreCase(cardView.getTag(R.string.getnote).toString())){
                                            playNote(notesRaw[n]);
                                            isTapping = true;
                                            handlererit(cardView,cardlay4,R.drawable.ic_simsek);
                                        }
                                    }
                                }
                                break;
                            }
                        }

                    }
                }

                if (event.getAction()==MotionEvent.ACTION_UP){
                    isTapping = false;
                }
                return true;
            }
        });


    }
    public void initGameArea(){
        gametextsark = findViewById(R.id.gametextsark);
        gametextad = findViewById(R.id.gametextad);
        puantex = findViewById(R.id.puantex);
        heartcount = findViewById(R.id.heartcount);
        gemimg = findViewById(R.id.gemimg);
        heartimg = findViewById(R.id.heartimg);
        sanimg = findViewById(R.id.sanimg);



        gametextad.setTag(R.string.bastex,"bastex");
        gametextsark.setTag(R.string.bastex,"bastex");

        puantex.setTag(R.string.bastex,"bastex");
        heartcount.setTag(R.string.bastex,"bastex");

        ViewEditor(gametextad,22,22);
        ViewEditor(gametextsark,30,30);

        ViewEditor(puantex,30,30);
        ViewEditor(heartcount,19,19);

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                yoyoclas(Techniques.RotateIn,1000,0,gemimg);
                yoyoclas(Techniques.RotateIn,1500,0,heartimg);
                handler.postDelayed(this,10000);
            }
        };
        handler.postDelayed(runnable,10000);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ArrayList arrayList = (ArrayList) databaseHelper.ekcalcek();
        String[] dizpuan = arrayList.get(0).toString().split(">");
        puantex.setText(dizpuan[3]);
        heartcount.setText(dizpuan[1]);
        curpuan=Integer.parseInt(puantex.getText().toString());
        changeRatings(curpuan);

        Bundle extras = getIntent().getExtras();
        String sarkad = extras.getString("sarkname");
        String[] sarkdiz = sarkad.split(" - ");

        gametextad.setText(sarkdiz[0]);
        gametextsark.setText(sarkdiz[1]);


    }


    public void yoyoclas(Techniques name, int dur, int rep, View v){
        YoYo.with(name)
                .duration(dur)
                .repeat(rep)
                .playOn(v);
    }


    public void setSark(String[] notes,Double[] vuruslar,int[] layindex0,int[] layindex1,int[] layindex2,int[] layindex3){
        for (int i=vuruslar.length-1; i>0; i--){
            initlay(cardlay1,layindex0[i],vuruslar[i],getResources().getDrawable(R.drawable.cardlay1bg),notes[i]);
            initlay(cardlay2,layindex1[i],vuruslar[i],getResources().getDrawable(R.drawable.cardlay2bg),notes[i]);
            initlay(cardlay3,layindex2[i],vuruslar[i],getResources().getDrawable(R.drawable.cardlay3bg),notes[i]);
            initlay(cardlay4,layindex3[i],vuruslar[i],getResources().getDrawable(R.drawable.cardlay4bg),notes[i]);
        }
    }


    public void initializeScrolay(){

        Bundle extras = getIntent().getExtras();
        String cursark = extras.getString("sarkname");
        SongLister songLister = new SongLister();
        SongLister2 songLister2 = new SongLister2();

        if (cursark.equalsIgnoreCase("Duman - Senden Daha Güzel")){
            setSark(songLister.sendendahaguzelnotes,songLister.sendendahaguzelvuruslar,songLister.sendendahaguzellay1,songLister.sendendahaguzellay2,songLister.sendendahaguzellay3,songLister.sendendahaguzellay4);
        } else if (cursark.equalsIgnoreCase("maNga - Dünyanın Sonuna Doğmuşum")){
            setSark(songLister.dunyaninsonunanotes,songLister.dunyaninsonunavuruslar,songLister.dunyaninsonunalay1,songLister.dunyaninsonunalay2,songLister.dunyaninsonunalay3,songLister.dunyaninsonunalay4);
        } else if (cursark.equalsIgnoreCase("Model - Pembe Mezarlık")){
            setSark(songLister.pembemezarliknotes,songLister.pembemezarlikvuruslar,songLister.pembemezarliklay1,songLister.pembemezarliklay2,songLister.pembemezarliklay3,songLister.pembemezarliklay4);
        } else if (cursark.equalsIgnoreCase("Farah Zeynep Abdullah - Gel ya da Git")){
          setSark(songLister.gelyadagitnotes,songLister.gelyadagitvuruslar,songLister.gelyadagitlay1,songLister.gelyadagitlay2,songLister.gelyadagitlay3,songLister.gelyadagitlay4);
        } else if (cursark.equalsIgnoreCase("Dolu Kadehi Ters Tut - Gitme")){
          setSark(songLister.gitmenotes,songLister.gitmevuruslar,songLister.gitmelay1,songLister.gitmelay2,songLister.gitmelay3,songLister.gitmelay4);
        } else if (cursark.equalsIgnoreCase("Cem Adrian - Kül")){
            setSark(songLister.kulnotes,songLister.kulvuruslar,songLister.kullay1,songLister.kullay2,songLister.kullay3,songLister.kullay4);
        } else if (cursark.equalsIgnoreCase("Yüzyüzeyken Konuşuruz - Dinle Beni Bi")){
            setSark(songLister2.dinlebenibinotes,songLister2.dinlebenibivuruslar,songLister2.dinlebenibilay1,songLister2.dinlebenibilay2,songLister2.dinlebenibilay3,songLister2.dinlebenibilay4);
        } else if (cursark.equalsIgnoreCase("Madrigal - Seni Dert Etmeler")){
            setSark(songLister2.senidertetmelernotes,songLister2.senidertetmelervuruslar,songLister2.senidertetmelerlay1,songLister2.senidertetmelerlay2,songLister2.senidertetmelerlay3,songLister2.senidertetmelerlay4);
        } else if (cursark.equalsIgnoreCase("Can Kazaz - Bunca Yıl")){
            setSark(songLister2.buncayilnotes,songLister2.buncayilvuruslar,songLister2.buncayillay1,songLister2.buncayillay2,songLister2.buncayillay3,songLister2.buncayillay4);
        } else if (cursark.equalsIgnoreCase("Son Feci Bisiklet - Bikinisinde Astronomi")){
            setSark(songLister2.bikinisindenotes,songLister2.bikinisindevuruslar,songLister2.bikinisindelay1,songLister2.bikinisindelay2,songLister2.bikinisindelay3,songLister2.bikinisindelay4);
        } else if (cursark.equalsIgnoreCase("Duman - Her Şeyi Yak")){
            setSark(songLister2.herseyiyaknotes,songLister2.herseyiyakvuruslar,songLister2.herseyiyaklay1,songLister2.herseyiyaklay2,songLister2.herseyiyaklay3,songLister2.herseyiyaklay4);
        }

        initStarterPage();
        initAutoScrollEvent();
    }

    public void initlay(LinearLayout linearLayout, int colwhich, double vurus, Drawable colorCode, String noteName){
        CardView cardView = new CardView(this);
        cardView.setCardElevation(0);
        if (colwhich==0){
            cardView.setCardBackgroundColor(Color.TRANSPARENT);
            cardView.setTag(R.string.getact,"no");
        } else {
            cardView.setBackground(colorCode);
            cardView.setRadius(widtho/30);
            cardView.setTag(R.string.getact,"yes");

            for (int i=0; i<notenames.length; i++){
                if (noteName.equalsIgnoreCase(notenames[i])){
                    touchCard(cardView,notesRaw[i],linearLayout);
                    cardView.setTag(R.string.getnote,noteName);
                }
            }

        }

        if (vurus==0.25){
            cardView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,heighto/32));
        } else if (vurus==0.5){
            cardView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,heighto/16));
        } else if (vurus==0.75){
            cardView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,heighto/12));
        } else if (vurus==1.0){
            cardView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,heighto/8));
        } else if (vurus==1.5){
            cardView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,heighto/6));
        } else if (vurus==2.0){
            cardView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,heighto/4));
        } else if (vurus==3.0){
            cardView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,heighto/3));
        } else if (vurus==4.0){
            cardView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,heighto/2));
        }
        linearLayout.addView(cardView);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) cardView.getLayoutParams();
        layoutParams.setMargins(widtho/100,heighto/100,widtho/100,widtho/100);
        cardView.setLayoutParams(layoutParams);

    }

    public void initAutoScrollEvent(){

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                autoScrollEvent(scrolay.getMeasuredHeight(),vites);
                seekBar.setMax(scrolay.getMeasuredHeight()-heighto);
                setAccellPoints();
                scrollseek();
                progressDialog.setMessage("Yükleniyor...");
                progressDialog.setCancelable(false);
                progressDialog.show();

            }
        };
        handler.postDelayed(runnable,1000);
    }

    public void setAccellPoints(){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int max = seekBar.getMax();
                int reduce = max/3;
                if (max-reduce>progress&&progress>max-(2*reduce)){
                    if (!point1){
                        accelCountDownTimer();
                        point1=true;
                    }
                } else if (max-(2*reduce)>progress&&progress>max-(3*reduce)){
                    if (!point2){
                        accelCountDownTimer();
                        point2=true;
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void autoScrollEvent(int toplam,int vites){
        ScrollView scrollView = findViewById(R.id.scrollView);
        countDownTimer = new CountDownTimer(toplam*vites, 1) {

            public void onTick(long millisUntilFinished) {
                int x=toplam*vites;
                int y=(int) (millisUntilFinished/vites);

                int xh=toplam*vites;
                scrollView.setSmoothScrollingEnabled(true);
                //Hızlanma olayı için düzgün oranla
                scrollView.smoothScrollTo(x,y);

                currentTime = (int) millisUntilFinished;
                currentY = y;

                visibleBoundsY1=scrollView.getScrollY();
                visibleBoundsY2=scrollView.getScrollY()-((heighto/20)*18);



            }

            public void onFinish() {
               Intent intent = new Intent(GameArea.this,GameOver.class);
               intent.putExtra("puankac",curpuan+"");
               intent.putExtra("sarkname",cursarkad);
               startActivity(intent);
               overridePendingTransition(R.anim.slide_out_left,R.anim.slide_in_right);
            }

        }.start();


    }

    public void playNote(int note){
        MediaPlayer mediaPlayer = MediaPlayer.create(GameArea.this,note);
        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.pause();
                mp.stop();
                mp.reset();
                mp.release();
                mp=null;
            }
        });
    }

    public void initStarterPage(){
        for (int i=0; i<scrolay.getChildCount(); i++){
            LinearLayout linearLayout = (LinearLayout) scrolay.getChildAt(i);
            CardView cardView = new CardView(this);
            cardView.setCardElevation(0);
            cardView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,heighto));
            cardView.setCardBackgroundColor(Color.TRANSPARENT);

            CardView cardView2 = new CardView(this);
            cardView2.setCardElevation(0);
            cardView2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,heighto));
            cardView2.setCardBackgroundColor(Color.TRANSPARENT);

            linearLayout.addView(cardView,0);
            linearLayout.addView(cardView2,linearLayout.getChildCount());

            cardView.setTag(R.string.getact,"no");
            cardView2.setTag(R.string.getact,"no");
        }


    }

    public void handlererit(CardView cardView,LinearLayout linearLayout, int drawResID){

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (isTapping){

                    int height = cardView.getHeight();
                    cardView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,height-(heighto/(vites*10))));
                    if (height-(heighto/(vites*10))>5){
                        curpuan=curpuan+1;
                        puantex.setText(""+curpuan);
                        changeRatings(curpuan);
                        new ParticleSystem(GameArea.this,5,R.drawable.ic_fireworks,500)
                                .setSpeedModuleAndAngleRange(0.2f, 0.5f, 60, 120)
                                .oneShot(cardView, 5);

                    }

                    if (height-(heighto/(vites*10))<=0){
                        cardView.setVisibility(View.INVISIBLE);
                        linearLayout.removeView(cardView);
                    }
                    handler.postDelayed(this,heighto/100);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) cardView.getLayoutParams();
                    layoutParams.setMargins(widtho/100,heighto/100,widtho/100,widtho/100);
                    cardView.setLayoutParams(layoutParams);
                } else {
                    handler.removeCallbacks(this);
                }

            }
        };
        if (isTapping){
            handler.postDelayed(runnable,heighto/100);
        } else {
            handler.removeCallbacks(runnable);
        }

    }

    public void touchCard(CardView cardView, int note,LinearLayout linearLayout){
        cardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    playNote(note);
                    isTapping = true;
                    handlererit(cardView,linearLayout,R.drawable.ic_simsek);
                }

                if (event.getAction()==MotionEvent.ACTION_OUTSIDE){
                    isTapping = false;
                }

                if (event.getAction()==MotionEvent.ACTION_UP){
                    isTapping = false;
                }
                return true;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseCountDownTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                resumeCountDownTimer();
            }
        };
        handler.postDelayed(runnable,2000);

    }

    public void pauseCountDownTimer(){
        if (countDownTimer!=null){
            countDownTimer.cancel();
            countDownTimer = null;
        }

    }

    public void resumeCountDownTimer(){
        if (countDownTimer==null){
            ScrollView scrollView = findViewById(R.id.scrollView);
            countDownTimer = new CountDownTimer(currentTime,1) {
                @Override
                public void onTick(long millisUntilFinished) {
                    int x=currentTime;
                    int y=(int) (millisUntilFinished/vites);

                    int xh=currentTime;
                    scrollView.setSmoothScrollingEnabled(true);
                    //Hızlanma olayı için düzgün oranla
                    scrollView.smoothScrollTo(x,y);

                    currentTime = (int) millisUntilFinished;
                    currentY = y;
                    visibleBoundsY1=scrollView.getScrollY();
                    visibleBoundsY2=scrollView.getScrollY()-((heighto/20)*18);

                }

                @Override
                public void onFinish() {
                    Intent intent = new Intent(GameArea.this,GameOver.class);
                    intent.putExtra("puankac",curpuan+"");
                    intent.putExtra("sarkname",cursarkad);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_out_left,R.anim.slide_in_right);
                }
            }.start();
        }

    }

    public void accelCountDownTimer(){
        if (vites>=2){
            if (countDownTimer!=null){
                countDownTimer.cancel();
                countDownTimer = null;

                resumeafteraccel();
            }
        }


    }

    public void scrollseek(){
        scrollView.setSmoothScrollingEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    seekBar.setProgress(scrollY);
                    if (scrollY<seekBar.getMax()){
                        if (progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                    }
                }
            });
        }
    }

    public void resumeafteraccel(){
        if (countDownTimer==null){
            vites--;
            ScrollView scrollView = findViewById(R.id.scrollView);
            countDownTimer = new CountDownTimer(currentY*vites,1) {
                @Override
                public void onTick(long millisUntilFinished) {
                    int x=currentY*vites;
                    int y=(int) (millisUntilFinished/vites);

                    int xh=currentTime;
                    scrollView.setSmoothScrollingEnabled(true);
                    scrollView.smoothScrollTo(x,y);

                    currentTime = (int) millisUntilFinished;
                    currentY = y;
                    visibleBoundsY1=scrollView.getScrollY();
                    visibleBoundsY2=scrollView.getScrollY()-((heighto/20)*18);

                }

                @Override
                public void onFinish() {
                    Intent intent = new Intent(GameArea.this,GameOver.class);
                    intent.putExtra("puankac",curpuan+"");
                    intent.putExtra("sarkname",cursarkad);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_out_left,R.anim.slide_in_right);
                }
            }.start();
        }

    }

    public void ViewEditor(@NonNull View view, int olc1, int olc2){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        double height = displayMetrics.heightPixels;
        double width = displayMetrics.widthPixels;



        if (view.getTag(R.string.bastex).toString().equalsIgnoreCase("bastex")){

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
        Intent intent = new Intent(GameArea.this,MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_out_left,R.anim.slide_in_right);
    }
}