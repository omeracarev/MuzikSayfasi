package org.newest.msorgapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SearchView;
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
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class usersearchdata extends AppCompatActivity {
   LinearLayout usersearchanalay,scrolall,scrollbenzer;
   ImageView closekuli;
   SearchView searchkuli;
    int gloaltindex = 0;
    int glowidth,gloheight;
   TextView benzertarzbaslik;
   ArrayList kuldizoar = new ArrayList<String>();
    GridLayout gridLayout;
    private String URL2 = "https://www.xn--mziksayfas-9db95d.com/login.php";
    private String URL3 = "https://www.xn--mziksayfas-9db95d.com/newnoti.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usersearchdata);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        gloheight = displayMetrics.heightPixels;
        glowidth = displayMetrics.widthPixels;

        usersearchanalay = findViewById(R.id.usersearchanalay);
        scrolall = findViewById(R.id.scrollall);
        scrollbenzer = findViewById(R.id.scrollbenzer);
        closekuli = findViewById(R.id.closekuli);
        searchkuli = findViewById(R.id.searchkuli);
        benzertarzbaslik = findViewById(R.id.benzertarzbaslik);
        gridLayout = new GridLayout(usersearchdata.this);

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                searchkuli.setFocusable(true);
                searchkuli.setFocusableInTouchMode(true);
            }
        };
        handler.postDelayed(runnable,1000);

        kulicek(scrolall);
        allkuldizo();
        benzertarzlardetect();

        searchkuli.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                scrolall.removeAllViews();
                kulicek(scrolall);
                return false;
            }
        });


            searchkuli.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    gridLayout.removeAllViews();

                    View getView = scrolall.getChildAt(scrolall.getChildCount()-1);
                    if (getView.getVisibility()!=View.INVISIBLE){
                        getView.setVisibility(View.INVISIBLE);
                    }

                    for (int i=0; i<kuldizoar.size(); i++){
                        newText = newText.toLowerCase();
                        if (kuldizoar.get(i).toString().contains(newText)){
                            LinearLayout sanlay = (LinearLayout) LinearLayout.inflate(usersearchdata.this,R.layout.everysanlarlay,null);
                            GridLayout.LayoutParams param =new GridLayout.LayoutParams();
                            LinearLayout anapinlay = sanlay.findViewById(R.id.anapinlay);
                            ImageView anasanimg = sanlay.findViewById(R.id.anasanimg);
                            TextView anasantex = sanlay.findViewById(R.id.anasantex);

                            param.height = gloheight/6;
                            param.width = glowidth/4+glowidth/16;
                            sanlay.setLayoutParams(param);
                            anasantex.setTag("bastex");
                            ViewEditor(anasantex,30,30);
                            anasantex.setText(kuldizoar.get(i).toString());
                            gridLayout.addView(sanlay);
                            cekotherfoto(kuldizoar.get(i).toString(),anasanimg);
                            int finalI = i;
                            anasanimg.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    profpop(v,kuldizoar.get(finalI).toString());
                                }
                            });
                        }
                    }
                    gridLayout.setRowCount((gridLayout.getChildCount()/3)+1);


                    return false;
                }
            });



        closekuli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(usersearchdata.this,kulprofilo.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });

    }

    public void benzertarzlardetect(){
        DatabaseHelper databaseHelper = new DatabaseHelper(usersearchdata.this);
        ArrayList kulbicek = (ArrayList) databaseHelper.kulbicek();
        String[] kuli = kulbicek.get(0).toString().split(">");
        String kulad = kuli[1];

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL3, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onResponse(String response) {
                try {
                    response = response.trim();
                    String[] kullar = response.split(">");
                    ArrayList arrayList = new ArrayList<String>();
                    for (int v=0; v<kullar.length; v++){
                        arrayList.add(kullar[v]);
                    }

                    Set<String> set = new HashSet<>(arrayList);
                    arrayList.clear();
                    arrayList.addAll(set);

                    for (int i=0; i<arrayList.size(); i++) {
                        LinearLayout sanlay = (LinearLayout) LinearLayout.inflate(usersearchdata.this, R.layout.everysanlarlay, null);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(glowidth/4+glowidth/16,LinearLayout.LayoutParams.MATCH_PARENT);
                        sanlay.setLayoutParams(layoutParams);

                        LinearLayout anapinlay = sanlay.findViewById(R.id.anapinlay);
                        ImageView anasanimg = sanlay.findViewById(R.id.anasanimg);
                        TextView anasantex = sanlay.findViewById(R.id.anasantex);

                        anasantex.setTag("bastex");
                        ViewEditor(anasantex,30,30);
                        anasantex.setText(arrayList.get(i).toString());
                        cekotherfoto(arrayList.get(i).toString(),anasanimg);

                        int finalI = i;
                        anasanimg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                profpop(v,arrayList.get(finalI).toString());
                            }
                        });

                        scrollbenzer.addView(sanlay);
                    }



                }
                catch (Exception e){
                    Toast.makeText(usersearchdata.this, "Bir Sorun Oluştu...", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Toast.makeText(usersearchdata.this, "Bir Sorun Oluştu...", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> data = new HashMap<>();
                data.put("benkulcek",kulad);
                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(usersearchdata.this);
        requestQueue.add(stringRequest);

        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {

                requestQueue.stop();
                request.cancel();
            }
        });

    }

    public void allusercount(){
        //Tüm kullanıcı adlarını çektir sonra onları arrayliste aktar
        //Daha sonra contains ile filtrele
        //filtrelenen kullanıcı sayısını 3'e böl ve gridviewın rowunu ayarla
        //Hiçbir şey yoksa defaultta bırak
    }

    public void initFavsar2(LinearLayout linearLayout, String kulad){
        DatabaseHelper databaseHelper = new DatabaseHelper(usersearchdata.this);
        ArrayList everysan = (ArrayList) databaseHelper.everysans();
        ArrayList everysang = (ArrayList) databaseHelper.everysansg();
        ArrayList aramanota = (ArrayList) databaseHelper.aramanota();
        ArrayList aramanotag = (ArrayList) databaseHelper.aramanotag();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onResponse(String response) {
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

                        for (int i=0; i<sarkadlar.length; i++){
                            Boolean isStored = false;
                            String[] sarkdiz = sarkadlar[i].split(" - ");
                            for (int a=0; a<aramanota.size(); a++){
                                String[] bol = aramanota.get(a).toString().split(">");
                                if (sarkadlar[i].equalsIgnoreCase(bol[1])){
                                    isStored = false;
                                }
                            }
                            for (int a=0; a<aramanotag.size(); a++){
                                String[] bol = aramanotag.get(a).toString().split(">");
                                if (sarkadlar[i].equalsIgnoreCase(bol[1])){
                                    isStored = true;
                                }
                            }

                            LinearLayout linearLayout1 = new LinearLayout(usersearchdata.this);
                            linearLayout1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT));
                            linearLayout1.setOrientation(LinearLayout.VERTICAL);
                            linearLayout.addView(linearLayout1);

                            ImageView imageView = new ImageView(usersearchdata.this);
                            imageView.setLayoutParams(new LinearLayout.LayoutParams(glowidth/4,gloheight/10));
                            for (int s=0; s<everysan.size(); s++){
                                String[] bolsa = everysan.get(s).toString().split(">");
                                if (sarkdiz[0].equalsIgnoreCase(bolsa[1])){
                                    int drawableID2 = usersearchdata.this.getResources().getIdentifier(bolsa[3], "drawable", getPackageName());
                                    imageView.setImageResource(drawableID2);

                                }
                            }

                            if (everysang.size()>0){
                                for (int s=0; s<everysang.size(); s++){
                                    String[] bolsa = everysang.get(s).toString().split(">");
                                    if (sarkdiz[0].equalsIgnoreCase(bolsa[1])){
                                        File imgFile = new  File(usersearchdata.this.getExternalFilesDir(null)+"/msapp/sanresnew/"+bolsa[3]);

                                        if(imgFile.exists()){

                                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                                            imageView.setImageBitmap(myBitmap);

                                        }

                                    }
                                }
                            }

                            linearLayout1.addView(imageView);

                            LinearLayout linearLayout2 = new LinearLayout(usersearchdata.this);
                            linearLayout2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                            linearLayout2.setOrientation(LinearLayout.VERTICAL);
                            linearLayout1.addView(linearLayout2);

                            TextView textView = new TextView(usersearchdata.this);
                            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                            textView.setTypeface(ResourcesCompat.getFont(usersearchdata.this,R.font.raleway_medium));
                            textView.setTextColor(Color.WHITE);
                            textView.setGravity(Gravity.CENTER);
                            textView.setText(sarkdiz[1]);
                            textView.setTag("bastex");
                            ViewEditor(textView,20,20);
                            linearLayout2.addView(textView);

                            String geText = textView.getText().toString();
                            if (geText.length()>11){
                                geText = geText.substring(0,7)+"...";
                                textView.setText(geText);
                            }

                            Boolean finalIsStored = isStored;
                            int finalI = i;
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(v.getContext(),msarkibak.class);
                                    intent.putExtra("sarkad",sarkadlar[finalI]);
                                    intent.putExtra("isStored", finalIsStored.toString());
                                    intent.putExtra("whereback","usersearchdata");
                                    startActivity(intent);
                                    finish();
                                    overridePendingTransition(R.anim.slide_out_left,R.anim.slide_in_right);
                                }
                            });

                        }

                    }


                }
                catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> data = new HashMap<>();
                data.put("favsarcek",kulad);

                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(usersearchdata.this);
        requestQueue.add(stringRequest);

        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.stop();
                request.cancel();
            }
        });
    }

    public void initIlgsanlay2(LinearLayout linearLayout, String kulad){
        DatabaseHelper databaseHelper = new DatabaseHelper(usersearchdata.this);
        ArrayList everysan = (ArrayList) databaseHelper.everysans();
        ArrayList everysang = (ArrayList) databaseHelper.everysansg();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL3, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onResponse(String response) {
                try {
                    response = response.trim();
                    //phpd0e hepsini coz



                    if (!response.equalsIgnoreCase("no")){
                        String[] resdiz = response.split(">");
                        for (int i=0;i<resdiz.length; i++){
                            for (int t=0; t<everysan.size(); t++){
                                String[] bol = everysan.get(t).toString().split(">");

                                if (resdiz[i].equalsIgnoreCase(bol[1])){
                                    View sanlayi = View.inflate(usersearchdata.this,R.layout.everysanlarlay,null);
                                    linearLayout.addView(sanlayi);

                                    LinearLayout anapinlay = sanlayi.findViewById(R.id.anapinlay);
                                    ImageView anasanimg = sanlayi.findViewById(R.id.anasanimg);
                                    TextView anasantex = sanlayi.findViewById(R.id.anasantex);

                                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(glowidth/5, LinearLayout.LayoutParams.MATCH_PARENT);
                                    layoutParams.setMargins(glowidth/80,gloheight/80,glowidth/80,gloheight/80);
                                    anapinlay.setLayoutParams(layoutParams);

                                    int drawableID = usersearchdata.this.getResources().getIdentifier(bol[3], "drawable", getPackageName());
                                    anasanimg.setImageResource(drawableID);

                                    anasanimg.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                        }
                                    });

                                    anasantex.setTag("bastex");
                                    ViewEditor(anasantex,35,35);
                                    anasantex.setText(bol[1]);

                                }


                            }

                            if (everysang.size()>0){
                                for (int t=0; t<everysang.size(); t++){
                                    String[] bol = everysang.get(t).toString().split(">");
                                    if (resdiz[i].equalsIgnoreCase(bol[1])){
                                        View sanlayi = View.inflate(usersearchdata.this,R.layout.everysanlarlay,null);
                                        linearLayout.addView(sanlayi);

                                        LinearLayout anapinlay = sanlayi.findViewById(R.id.anapinlay);
                                        ImageView anasanimg = sanlayi.findViewById(R.id.anasanimg);
                                        TextView anasantex = sanlayi.findViewById(R.id.anasantex);

                                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(glowidth/5, LinearLayout.LayoutParams.MATCH_PARENT);
                                        layoutParams.setMargins(glowidth/120,gloheight/120,glowidth/120,gloheight/120);
                                        anapinlay.setLayoutParams(layoutParams);


                                        File imgFile = new  File(usersearchdata.this.getExternalFilesDir(null)+"/msapp/sanresnew/"+bol[3]);

                                        if(imgFile.exists()){

                                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                                            BitmapDrawable ob = new BitmapDrawable(getResources(), myBitmap);
                                            anasanimg.setImageBitmap(myBitmap);

                                        }

                                        anasanimg.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                            }
                                        });

                                        anasantex.setTag("bastex");
                                        ViewEditor(anasantex,35,35);
                                        anasantex.setText(bol[1]);
                                    }


                                }
                            }


                        }
                    }


                }
                catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> data = new HashMap<>();
                data.put("ilgsanaracek",kulad);

                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(usersearchdata.this);
        requestQueue.add(stringRequest);

        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.stop();
                request.cancel();
            }
        });
    }


    public void profpop(View view, String kulad){
        PopupWindow popUp = new PopupWindow(view.getContext());
        View layout = View.inflate(view.getContext(),R.layout.onprofview,null);

        ImageView closepoppro = layout.findViewById(R.id.closepoppro);
        ImageView personimg = layout.findViewById(R.id.personimg);
        TextView personkulad = layout.findViewById(R.id.personkulad);
        TextView ilgsanbaso = layout.findViewById(R.id.ilgsanbaso);
        TextView favsarbaso = layout.findViewById(R.id.favsarbaso);
        LinearLayout onprofilsan = layout.findViewById(R.id.onprofilsan);
        LinearLayout onprofilfav = layout.findViewById(R.id.onprofilfav);

        initIlgsanlay2(onprofilsan,kulad);
        initFavsar2(onprofilfav,kulad);



        cekotherfoto(kulad,personimg);

        personkulad.setText(kulad);

        personkulad.setTag("bastex");
        ilgsanbaso.setTag("bastex");
        favsarbaso.setTag("bastex");




        ViewEditor(personkulad,16,16);
        ViewEditor(ilgsanbaso,25,25);
        ViewEditor(favsarbaso,25,25);



        closepoppro.setOnClickListener(new View.OnClickListener() {
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
        popUp.showAsDropDown(view);
        darkenBackground(0.3f);
        popUp.update((glowidth)/10, (gloheight)/10, glowidth/2+glowidth/4, gloheight/2+gloheight/4);


    }

    private void darkenBackground(Float bgcolor) {
        usersearchdata mMainActivity = usersearchdata.this;
        WindowManager.LayoutParams lp = mMainActivity.getWindow().getAttributes();
        lp.alpha = bgcolor;
        mMainActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mMainActivity.getWindow().setAttributes(lp);
    }


    public void plusearch(GridLayout gridLayout, ImageView dahasi, LinearLayout analay){

        DatabaseHelper databaseHelper = new DatabaseHelper(usersearchdata.this);
        ArrayList kuldiz = (ArrayList) databaseHelper.kulbicek();
        String[] kulceko = kuldiz.get(0).toString().split(">");


        analay.removeView(dahasi);
        gloaltindex = gloaltindex+18;
        gridLayout.setRowCount((gloaltindex+18)/3);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL3, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onResponse(String response) {
                try {
                    response = response.trim();
                    String[] kullar = response.split(">");

                    if (kullar.length>0){
                        for (int i=0; i<kullar.length; i++){
                            LinearLayout sanlay = (LinearLayout) LinearLayout.inflate(usersearchdata.this,R.layout.everysanlarlay,null);
                            GridLayout.LayoutParams param =new GridLayout.LayoutParams();
                            LinearLayout anapinlay = sanlay.findViewById(R.id.anapinlay);
                            ImageView anasanimg = sanlay.findViewById(R.id.anasanimg);
                            TextView anasantex = sanlay.findViewById(R.id.anasantex);

                            param.height = gloheight/6;
                            param.width = glowidth/4+glowidth/16;
                            sanlay.setLayoutParams(param);
                            anasantex.setTag("bastex");
                            ViewEditor(anasantex,30,30);
                            anasantex.setText(kullar[i]);
                            cekotherfoto(kullar[i],anasanimg);
                            gridLayout.addView(sanlay);

                            int finalI = i;
                            anasanimg.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    profpop(v,kullar[finalI]);
                                }
                            });
                        }
                    }



                    ImageView dahasi2 = new ImageView(usersearchdata.this);
                    dahasi2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,130));
                    dahasi2.setImageResource(R.drawable.ic_add_circle);
                    analay.addView(dahasi2);

                    dahasi2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            plusearch(gridLayout,dahasi2,analay);
                        }
                    });
                }
                catch (Exception e){
                    Toast.makeText(usersearchdata.this, "Bir Sorun Oluştu...", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Toast.makeText(usersearchdata.this, "Bir Sorun Oluştu...", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> data = new HashMap<>();
                data.put("altindex", gloaltindex+"");
                data.put("ustindex","18");
                data.put("kulinget",kulceko[1]);
                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(usersearchdata.this);
        requestQueue.add(stringRequest);

        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.stop();
                request.cancel();
            }
        });

    }

    public void allkuldizo(){
        Context context = usersearchdata.this;
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        ArrayList kuli = (ArrayList) databaseHelper.kulbicek();
        String[] kullar = kuli.get(0).toString().split(">");
        String kulad = kullar[1];

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL3, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onResponse(String response) {
                try {
                    response = response.trim();
                    String[] kullar = response.split(">");
                    for (int i=0; i<kullar.length; i++){
                        kuldizoar.add(kullar[i]);
                    }

                    Collections.sort(kuldizoar);




                }
                catch (Exception e){
                    Toast.makeText(usersearchdata.this, "Bir Sorun Oluştu...", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Toast.makeText(usersearchdata.this, "Bir Sorun Oluştu...", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> data = new HashMap<>();
                data.put("allkuldizo",kulad);
                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(usersearchdata.this);
        requestQueue.add(stringRequest);

        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.stop();
                request.cancel();
            }
        });
    }

    public void kulicek(LinearLayout scrollall){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL3, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onResponse(String response) {
                try {
                    response = response.trim();
                    String[] kullar = response.split(">");

                    gridLayout.setColumnCount(3);
                    gridLayout.setRowCount(6);
                    gridLayout.setUseDefaultMargins(true);
                    gridLayout.setOrientation(GridLayout.HORIZONTAL);
                    gridLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));


                    for (int i=0; i<kullar.length; i++){
                        LinearLayout sanlay = (LinearLayout) LinearLayout.inflate(usersearchdata.this,R.layout.everysanlarlay,null);
                        GridLayout.LayoutParams param =new GridLayout.LayoutParams();
                        LinearLayout anapinlay = sanlay.findViewById(R.id.anapinlay);
                        ImageView anasanimg = sanlay.findViewById(R.id.anasanimg);
                        TextView anasantex = sanlay.findViewById(R.id.anasantex);

                        param.height = gloheight/6;
                        param.width = glowidth/4+glowidth/16;
                        sanlay.setLayoutParams(param);
                        anasantex.setTag("bastex");
                        ViewEditor(anasantex,30,30);
                        anasantex.setText(kullar[i]);
                        cekotherfoto(kullar[i],anasanimg);
                        gridLayout.addView(sanlay);

                        int finalI = i;
                        anasanimg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                profpop(v,kullar[finalI]);
                            }
                        });
                    }

                    scrollall.addView(gridLayout);

                    ImageView dahasi = new ImageView(usersearchdata.this);
                    dahasi.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,130));
                    dahasi.setImageResource(R.drawable.ic_add_circle);
                    scrollall.addView(dahasi);

                    dahasi.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            plusearch(gridLayout,dahasi,scrollall);
                        }
                    });


                }
                catch (Exception e){
                    Toast.makeText(usersearchdata.this, "Bir Sorun Oluştu...", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Toast.makeText(usersearchdata.this, "Bir Sorun Oluştu...", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> data = new HashMap<>();
                data.put("altindex", gloaltindex+"");
                data.put("ustindex","18");
                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(usersearchdata.this);
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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                imageView.setImageResource(R.drawable.ic_person_pin);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> data = new HashMap<>();
                data.put("otherfoto",kulad);

                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(usersearchdata.this);
        requestQueue.add(stringRequest);

        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.stop();
                request.cancel();
            }
        });

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
        Intent intent = new Intent(usersearchdata.this,kulprofilo.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }
}