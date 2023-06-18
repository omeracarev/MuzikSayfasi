package org.newest.msorgapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
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
import com.google.android.material.snackbar.Snackbar;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GameOver extends AppCompatActivity {
    private RewardedAd mRewardedAd;
    private final String TAG = "MainActivity";
    int puan = 1000;
    int ax=0;
    TextView gamesontext,gamesonpuansay,extracantext,tekrartext,listsec;
    ImageView tekrarimg,listimg;
    LinearLayout starslayodul;
    int gloheight,glowidth;
    String cursark;
    public void rewardedAdset(){

        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(this, "ca-app-pub-2627695336411451/2861102480",
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

                                Log.d(TAG, "Ad was dismissed.");

                                mRewardedAd = null;
                            }
                        });
                    }
                });


    }

    private void darkenBackground(Float bgcolor) {
        GameOver mGameOver = GameOver.this;
        WindowManager.LayoutParams lp = mGameOver.getWindow().getAttributes();
        lp.alpha = bgcolor;
        mGameOver.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mGameOver.getWindow().setAttributes(lp);
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

    public void changeRatings(int puan){

        LinearLayout ratingLay = findViewById(R.id.starslayodul);
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
            yoyoclas(Techniques.Tada,1000,1,ratingLay);
        }else if (puan>1500&&puan<2500){
            star1.setImageResource(R.drawable.ic_star);
            star2.setImageResource(R.drawable.ic_star_border);
            star3.setImageResource(R.drawable.ic_star_border);
            star4.setImageResource(R.drawable.ic_star_border);
            star5.setImageResource(R.drawable.ic_star_border);
            yoyoclas(Techniques.Tada,1000,1,ratingLay);
        } else if (puan>2500&&puan<3500){
            star1.setImageResource(R.drawable.ic_star);
            star2.setImageResource(R.drawable.ic_star_half);
            star3.setImageResource(R.drawable.ic_star_border);
            star4.setImageResource(R.drawable.ic_star_border);
            star5.setImageResource(R.drawable.ic_star_border);
            yoyoclas(Techniques.Tada,1000,1,ratingLay);
        } else if (puan>3500&&puan<5000){
            star1.setImageResource(R.drawable.ic_star);
            star2.setImageResource(R.drawable.ic_star);
            star3.setImageResource(R.drawable.ic_star_border);
            star4.setImageResource(R.drawable.ic_star_border);
            star5.setImageResource(R.drawable.ic_star_border);
            yoyoclas(Techniques.Tada,1000,1,ratingLay);
        } else if (puan>5000&&puan<6000){
            star1.setImageResource(R.drawable.ic_star);
            star2.setImageResource(R.drawable.ic_star);
            star3.setImageResource(R.drawable.ic_star_half);
            star4.setImageResource(R.drawable.ic_star_border);
            star5.setImageResource(R.drawable.ic_star_border);
            yoyoclas(Techniques.Tada,1000,1,ratingLay);
        } else if (puan>6000&&puan<7000){
            star1.setImageResource(R.drawable.ic_star);
            star2.setImageResource(R.drawable.ic_star);
            star3.setImageResource(R.drawable.ic_star);
            star4.setImageResource(R.drawable.ic_star_border);
            star5.setImageResource(R.drawable.ic_star_border);
            yoyoclas(Techniques.Tada,1000,1,ratingLay);
        } else if (puan>7000&&puan<8000){
            star1.setImageResource(R.drawable.ic_star);
            star2.setImageResource(R.drawable.ic_star);
            star3.setImageResource(R.drawable.ic_star);
            star4.setImageResource(R.drawable.ic_star_half);
            star5.setImageResource(R.drawable.ic_star_border);
            yoyoclas(Techniques.Tada,1000,1,ratingLay);
        } else if (puan>8000&&puan<9000){
            star1.setImageResource(R.drawable.ic_star);
            star2.setImageResource(R.drawable.ic_star);
            star3.setImageResource(R.drawable.ic_star);
            star4.setImageResource(R.drawable.ic_star);
            star5.setImageResource(R.drawable.ic_star_border);
            yoyoclas(Techniques.Tada,1000,1,ratingLay);
        } else if (puan>9000&&puan<10000){
            star1.setImageResource(R.drawable.ic_star);
            star2.setImageResource(R.drawable.ic_star);
            star3.setImageResource(R.drawable.ic_star);
            star4.setImageResource(R.drawable.ic_star);
            star5.setImageResource(R.drawable.ic_star_half);
            yoyoclas(Techniques.Tada,1000,1,ratingLay);
        } else if (puan>10000){
            star1.setImageResource(R.drawable.ic_star);
            star2.setImageResource(R.drawable.ic_star);
            star3.setImageResource(R.drawable.ic_star);
            star4.setImageResource(R.drawable.ic_star);
            star5.setImageResource(R.drawable.ic_star);
            yoyoclas(Techniques.Tada,1000,1,ratingLay);
        }
    }

    public void noheartalert(String songname, View buti){
        PopupWindow popUp = new PopupWindow(GameOver.this);
        LinearLayout layout = new LinearLayout(GameOver.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(params);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackground(getResources().getDrawable(R.drawable.gamebackgr_9));

        LinearLayout layout2 = new LinearLayout(GameOver.this);
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
        textView.setTag(R.string.bastex,"bastex");
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
        cardtext.setTag(R.string.bastex,"bastex");
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
        yadatex.setTag(R.string.bastex,"bastex");
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
        cardtext2.setTag(R.string.bastex,"bastex");
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
                    rewardAd(layout);
                    progressDialog.dismiss();
                    popUp.dismiss();
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

    public void buyheart(int price, int heartsay, LinearLayout linearLayout, TextView cointex, TextView hearttex){
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
                snackbar("Canlar Eklendi!","Süper!",linearLayout);
                yoyoclas(Techniques.Wobble,1000,0,linearLayout);
            } else {
                snackbar("Problem oluştu...","Tekrar Dene",linearLayout);
            }
        }




    }

    public void marketPopup(){

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ArrayList alpuan = (ArrayList) databaseHelper.ekcalcek();
        String[] alpuandiz = alpuan.get(0).toString().split(">");

        PopupWindow popUp = new PopupWindow(GameOver.this);
        View layout = View.inflate(this,R.layout.market_popup,null);

        LinearLayout viewlay = layout.findViewById(R.id.marketlay);

        ImageView closemarkpop = layout.findViewById(R.id.closemarkpop);
        ImageView coinim1 = layout.findViewById(R.id.coinim1);
        ImageView coinim2 = layout.findViewById(R.id.coinim2);
        ImageView coinim3 = layout.findViewById(R.id.coinim3);
        ImageView coinim4 = layout.findViewById(R.id.coinim4);

        TextView baslikpop = layout.findViewById(R.id.baslikpop);
        baslikpop.setTag(R.string.bastex,"bastex");
        ViewEditor(baslikpop,13,13);

        TextView heart1gem = layout.findViewById(R.id.heart1gem);
        heart1gem.setTag(R.string.bastex,"bastex");
        ViewEditor(heart1gem,20,20);

        TextView heart1coin = layout.findViewById(R.id.heart1coin);
        heart1coin.setTag(R.string.bastex,"bastex");
        ViewEditor(heart1coin,20,20);

        TextView heart3gem = layout.findViewById(R.id.heart3gem);
        heart3gem.setTag(R.string.bastex,"bastex");
        ViewEditor(heart3gem,20,20);

        TextView heart3coin = layout.findViewById(R.id.heart3coin);
        heart3coin.setTag(R.string.bastex,"bastex");
        ViewEditor(heart3coin,20,20);

        TextView heart10gem = layout.findViewById(R.id.heart10gem);
        heart10gem.setTag(R.string.bastex,"bastex");
        ViewEditor(heart10gem,20,20);

        TextView heart10coin = layout.findViewById(R.id.heart10coin);
        heart10coin.setTag(R.string.bastex,"bastex");
        ViewEditor(heart10coin,20,20);

        TextView heart20coin = layout.findViewById(R.id.heart20coin);
        heart20coin.setTag(R.string.bastex,"bastex");
        ViewEditor(heart20coin,20,20);

        TextView heart20gem = layout.findViewById(R.id.heart20gem);
        heart20gem.setTag(R.string.bastex,"bastex");
        ViewEditor(heart20gem,20,20);

        TextView totalpuan = layout.findViewById(R.id.totalpuan);
        totalpuan.setTag(R.string.bastex,"bastex");
        ViewEditor(totalpuan,20,20);

        totalpuan.setText(" x"+alpuandiz[3]);

        TextView totalheart = layout.findViewById(R.id.totalheart);
        totalheart.setTag(R.string.bastex,"bastex");
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


    public void rewardAd(LinearLayout linearLayout){
        Activity activityContext = GameOver.this;
        mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
            @Override
            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                // Handle the reward.
                int rewardAmount = rewardItem.getAmount();
                String rewardType = rewardItem.getType();
                DatabaseHelper databaseHelper = new DatabaseHelper(GameOver.this);
                ArrayList oduller = (ArrayList) databaseHelper.ekcalcek();
                String[] alpuandiz = oduller.get(0).toString().split(">");

                for (int i=0; i<oduller.size(); i++){
                    String[] pudiz = oduller.get(i).toString().split(">");
                    Ekcallar olddata = new Ekcallar(Integer.parseInt(pudiz[0]),pudiz[1],pudiz[2],pudiz[3],pudiz[4],pudiz[5]);
                    Boolean sildata = databaseHelper.deleteekcal(olddata);
                    if (sildata){
                        snackbar("Ödül tanımında bir problem oluştu, lütfen tekrar deneyin","OK",linearLayout);
                    }
                }

                Ekcallar newveri = new Ekcallar(-1,Integer.toString(Integer.parseInt(alpuandiz[1])+rewardAmount),alpuandiz[2],alpuandiz[3],alpuandiz[4],alpuandiz[5]);
                Boolean addnewveri = databaseHelper.addekcallar(newveri);
                if (addnewveri){

                }

            }
        });
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

    public void heartControl(String song){
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ArrayList kalpler = (ArrayList) databaseHelper.ekcalcek();
        String[] kalpdiz = kalpler.get(0).toString().split(">");
        int curkalp = Integer.parseInt(kalpdiz[1].trim());

        if (curkalp>0){
            for (int i=0; i<kalpler.size(); i++){
                ArrayList arrayList = (ArrayList) databaseHelper.ekcalcek();
                String[] arlist = arrayList.get(i).toString().split(">");
                Ekcallar ekcallar = new Ekcallar(Integer.parseInt(arlist[0]),arlist[1],arlist[2],arlist[3],arlist[4],arlist[5]);
                Boolean del = databaseHelper.deleteekcal(ekcallar);
                if (del){

                }
            }

            Ekcallar newekcal = new Ekcallar(-1,(curkalp-1)+"",kalpdiz[2],kalpdiz[3],kalpdiz[4],kalpdiz[5]);
            Boolean addek = databaseHelper.addekcallar(newekcal);
            if (addek){
                Intent intent = new Intent(GameOver.this,GameArea.class);
                intent.putExtra("sarkname",song);
                startActivity(intent);
            }
        } else {
            noheartalert(song,findViewById(R.id.listimg));
        }
    }

    public void sarkmenu(View view){
       view.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
               popupMenu.getMenuInflater().inflate(R.menu.oyunlistmenu,popupMenu.getMenu());
               setForceShowIcon(popupMenu);
               Intent intent = new Intent(v.getContext(),GameArea.class);
               popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                   @Override
                   public boolean onMenuItemClick(MenuItem item) {
                       switch (item.getItemId()){
                           case R.id.oysark1:
                               heartControl("Cem Adrian - Kül");

                               break;
                           case R.id.oysark2:
                               heartControl("maNga - Dünyanın Sonuna Doğmuşum");

                               break;
                           case R.id.oysark3:
                               heartControl("Model - Pembe Mezarlık");

                               break;
                           case R.id.oysark4:
                               heartControl("Farah Zeynep Abdullah - Gel ya da Git");

                               break;
                           case R.id.oysark5:
                               heartControl("Dolu Kadehi Ters Tut - Gitme");

                               break;

                           case R.id.oysark6:
                               heartControl("Duman - Senden Daha Güzel");

                               break;

                           case R.id.oysark7:
                               heartControl("Yüzyüzeyken Konuşuruz - Dinle Beni Bi");

                               break;

                           case R.id.oysark8:
                               heartControl("Madrigal - Seni Dert Etmeler");

                               break;

                           case R.id.oysark9:
                               heartControl("Can Kazaz - Bunca Yıl");

                               break;

                           case R.id.oysark10:
                               heartControl("Son Feci Bisiklet - Bikinisinde Astronomi");

                               break;

                           case R.id.oysark11:
                               heartControl("Duman - Her Şeyi Yak");

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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        rewardedAdset();

        Bundle bundle = getIntent().getExtras();
        cursark = bundle.getString("sarkname");

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        gloheight = displayMetrics.heightPixels;
        glowidth = displayMetrics.widthPixels;

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdView adView = findViewById(R.id.odulban);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        gamesontext = findViewById(R.id.gamesontext);
        gamesonpuansay = findViewById(R.id.gamesonpuansay);
        extracantext = findViewById(R.id.extracantext);
        starslayodul = findViewById(R.id.starslayodul);
        tekrartext = findViewById(R.id.tekrartext);
        listsec = findViewById(R.id.listsec);
        tekrarimg = findViewById(R.id.tekrarimg);
        listimg = findViewById(R.id.listimg);

        starslayodul.setPadding(glowidth/30,gloheight/30,glowidth/30,gloheight/30);
        gamesontext.setTag(R.string.bastex,"bastex");
        ViewEditor(gamesontext,5,5);
        gamesontext.setTypeface(ResourcesCompat.getFont(this,R.font.miss_fajardose));

        gamesonpuansay.setTag(R.string.bastex,"bastex");
        ViewEditor(gamesonpuansay,10,10);

        extracantext.setTag(R.string.bastex,"bastex");
        ViewEditor(extracantext,20,20);

        listsec.setTag(R.string.bastex,"bastex");
        ViewEditor(listsec,20,20);
        sarkmenu(listsec);
        sarkmenu(listimg);

        tekrartext.setTag(R.string.bastex,"bastex");
        ViewEditor(tekrartext,20,20);

        countText(gamesonpuansay);
        Bundle extras = getIntent().getExtras();
        changeRatings(Integer.parseInt(extras.getString("puankac")));
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ArrayList listodul = (ArrayList) databaseHelper.ekcalcek();
        List<Pdfkaylar> pdfkaylars = databaseHelper.kaypdfcek();
        List<Kulbi> kulbis = databaseHelper.kulbicek();
        String[] oduldiz = listodul.get(0).toString().split(">");
        for (int i=0; i<listodul.size(); i++){
            String[] listdiz = listodul.get(i).toString().split(">");
            Ekcallar eskiekcal = new Ekcallar(Integer.parseInt(listdiz[0]),listdiz[1],listdiz[2],listdiz[3],listdiz[4],listdiz[5]);
            Boolean deleteski = databaseHelper.deleteekcal(eskiekcal);
            if (deleteski){

            }
        }

        Ekcallar ekcallar = new Ekcallar(-1,oduldiz[1],oduldiz[2],extras.getString("puankac"),oduldiz[4],oduldiz[5]);
        Boolean addekcal = databaseHelper.addekcallar(ekcallar);
        if (addekcal){
            if (!kulbis.isEmpty()){
                if (!pdfkaylars.isEmpty()){

                    for (int i=0; i<pdfkaylars.size(); i++){
                        if (databaseHelper.deletepdfkay(pdfkaylars.get(i))){

                        }
                    }
                }

                if (databaseHelper.addpdfkay(new Pdfkaylar(-1,kulbis.get(0).getKulad(),extras.getString("puankac"),new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date()),"no","no","no"))){

                }
            }

        }



        tekrarimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList ekcalist = (ArrayList) databaseHelper.ekcalcek();
                String[] ekcaldiz = ekcalist.get(0).toString().split(">");
                if (Integer.parseInt(ekcaldiz[1])>0){

                    int newmed = Integer.parseInt(ekcaldiz[1])-1;
                    for (int i=0; i<ekcalist.size(); i++){
                        String[] gecdiz = ekcalist.get(i).toString().split(">");
                        Ekcallar ekcallar1 = new Ekcallar(Integer.parseInt(gecdiz[0]),gecdiz[1],gecdiz[2],gecdiz[3],gecdiz[4],gecdiz[5]);
                        Boolean dele = databaseHelper.deleteekcal(ekcallar1);
                        if (dele){

                        }
                    }

                    Ekcallar newveri = new Ekcallar(-1,Integer.toString(newmed),ekcaldiz[2],ekcaldiz[3],ekcaldiz[4],ekcaldiz[5]);
                    Boolean addveri = databaseHelper.addekcallar(newveri);
                    if (addveri){
                        Intent intent = new Intent(v.getContext(),GameArea.class);
                        intent.putExtra("sarkname",cursark);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_out_left,R.anim.slide_in_right);
                    }

                } else {
                    noheartalert("songname",v);
                }
            }
        });

        tekrartext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList ekcalist = (ArrayList) databaseHelper.ekcalcek();
                String[] ekcaldiz = ekcalist.get(0).toString().split(">");
                if (Integer.parseInt(ekcaldiz[1])>0){

                    int newmed = Integer.parseInt(ekcaldiz[1])-1;
                    for (int i=0; i<ekcalist.size(); i++){
                        String[] gecdiz = ekcalist.get(i).toString().split(">");
                        Ekcallar ekcallar1 = new Ekcallar(Integer.parseInt(gecdiz[0]),gecdiz[1],gecdiz[2],gecdiz[3],gecdiz[4],gecdiz[5]);
                        Boolean dele = databaseHelper.deleteekcal(ekcallar1);
                        if (dele){

                        }
                    }

                    Ekcallar newveri = new Ekcallar(-1,Integer.toString(newmed),ekcaldiz[2],ekcaldiz[3],ekcaldiz[4],ekcaldiz[5]);
                    Boolean addveri = databaseHelper.addekcallar(newveri);
                    if (addveri){
                        Intent intent = new Intent(v.getContext(),GameArea.class);
                        intent.putExtra("sarkname",cursark);
                        startActivity(intent);

                        overridePendingTransition(R.anim.slide_out_left,R.anim.slide_in_right);
                    }

                } else {
                    noheartalert("songname",v);
                }
            }
        });

        LinearLayout layexcan = findViewById(R.id.layexcan);
        for (int i=0; i<layexcan.getChildCount(); i++){
            View view = layexcan.getChildAt(i);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProgressDialog progressDialog = new ProgressDialog(v.getContext());
                    progressDialog.setMessage("Yükleniyor...");
                    progressDialog.setCancelable(true);
                    progressDialog.show();
                    if (mRewardedAd!=null){
                        rewardAd(findViewById(R.id.laygameover));
                        progressDialog.dismiss();
                    } else {
                        snackbar("Tekrarlayın...","Tamam",findViewById(R.id.laygameover));
                        progressDialog.dismiss();
                    }
                }
            });
        }



    }

    public void yoyoclas(Techniques name, int dur, int rep, View v){
        YoYo.with(name)
                .duration(dur)
                .repeat(rep)
                .playOn(v);
    }


    public void countText(TextView textView){
        Bundle extras = getIntent().getExtras();
        textView.setText(extras.getString("puankac"));
        ax=Integer.parseInt(textView.getText().toString())-60;
        puan = Integer.parseInt(textView.getText().toString());
       Handler handler = new Handler();
       Runnable runnable = new Runnable() {
           @Override
           public void run() {

               if (ax>=puan){
                  //while dongüsünü dene
               } else {
                   ax++;
                   textView.setText(ax+"");

               }
               handler.postDelayed(this, (long) 0.001);
           }
       };
       handler.postDelayed(runnable,(long) 0.001);
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
        Intent intent = new Intent(GameOver.this,MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_out_left,R.anim.slide_in_right);
    }
}