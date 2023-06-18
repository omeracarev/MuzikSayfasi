package org.newest.msorgapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class guncelact extends AppCompatActivity {
    private String URL2 = "https://www.xn--mziksayfas-9db95d.com/login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_NoActionBar_Launcher2);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guncelact);

        Bundle extras = getIntent().getExtras();
        String kulad = extras.getString("kulad");
        String kulmail = extras.getString("kulmail");
        String kulsifre = extras.getString("kulsifre");
        String kulyen = extras.getString("kulyen");

        DatabaseHelper databaseHelper = new DatabaseHelper(guncelact.this);
        ProgressDialog progressDialog = new ProgressDialog(guncelact.this);
        progressDialog.setMessage("Güncelleniyor....");
        progressDialog.setCancelable(true);
        progressDialog.show();
        if (isNetworkAvailable()){

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
                @RequiresApi(api = Build.VERSION_CODES.P)
                @Override
                public void onResponse(String response) {
                    try {
                        ArrayList arraykul = (ArrayList) databaseHelper.kulbicek();
                        String[] bolar = arraykul.get(0).toString().split(">");
                        Kulbi kulbi = new Kulbi(Integer.parseInt(bolar[0]),bolar[1],bolar[2],bolar[3],bolar[4]);
                        Boolean succ = databaseHelper.deletekulbi(kulbi);
                        if (!succ){

                            Kulbi kulbi1 = new Kulbi(-1,kulyen,kulsifre,kulmail,"ok");
                            Boolean kulok = databaseHelper.addkulbi(kulbi1);

                            if (kulok){
                                Boolean guncel = false;
                                ArrayList favsarlist = (ArrayList) databaseHelper.favsarcekdb();
                                for (int i=0; i<favsarlist.size(); i++){
                                    String[] bol = favsarlist.get(i).toString().split(">");
                                    Favsar favsar = new Favsar(Integer.parseInt(bol[0]),bol[1],bol[2],bol[3],bol[4],bol[5],bol[6]);
                                    Boolean olay = databaseHelper.deletefavsar(favsar);
                                    if (!olay){
                                        Favsar favsar1 = new Favsar(-1,bol[1],bol[2],bol[3],bol[4],bol[5],kulyen);
                                        Boolean olay2 = databaseHelper.addfavsar(favsar1);
                                        if (olay2){
                                            guncel=true;
                                        } else {

                                            guncel=false;
                                        }
                                    } else {

                                        guncel=false;
                                    }

                                }

                                if (guncel){
                                    Intent intent = new Intent(guncelact.this,MainActivity.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                    finishAndRemoveTask();
                                } else {
                                    Toast.makeText(guncelact.this, "Güncelleme Başarısız", Toast.LENGTH_SHORT).show();
                                }


                            } else {
                                Toast.makeText(guncelact.this, "Güncelleme Sırasında Bir Hata Oluştu, Çıkış Yapılacak...", Toast.LENGTH_LONG).show();

                            }


                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(guncelact.this, "Güncelleme Başarısız Oldu, Lütfen Tekrar Deneyin...", Toast.LENGTH_SHORT).show();
                        }

                    }
                    catch (Exception e){
                        Toast.makeText(guncelact.this, "İnternet Bağlantınızda Bir Problem olabilir, Lütfen Tekrar Deneyin...", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(guncelact.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map <String, String> data = new HashMap<>();
                    data.put("kules",kulad);
                    data.put("kulguncelle",kulyen);
                    data.put("kulsifre",kulsifre);
                    data.put("kulmail",kulmail);
                    return data;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(guncelact.this);
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(guncelact.this, "Güncelleme İşlemi İçin İnternete Bağlanmalısınız...", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(guncelact.this,MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            finishAndRemoveTask();
        }

    }

    private boolean isNetworkAvailable() {

        // Initialize connectivity manager
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Initialize network info
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        // get connection status
        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();

        // display snack bar

        if (isNetworkAvailable()){
            return true;
        } else {
            return false;
        }

    }
}