package org.newest.msorgapp.Services;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import static androidx.core.app.NotificationCompat.FLAG_AUTO_CANCEL;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.newest.msorgapp.DatabaseHelper;
import org.newest.msorgapp.Ekcallar;
import org.newest.msorgapp.Everysan;
import org.newest.msorgapp.Favsar;
import org.newest.msorgapp.Favyaz;
import org.newest.msorgapp.Ilgsanlar;
import org.newest.msorgapp.Kulbi;
import org.newest.msorgapp.Notarama;
import org.newest.msorgapp.Pdfkaylar;
import org.newest.msorgapp.R;
import org.newest.msorgapp.Utils;
import org.newest.msorgapp.kulprofilo;
import org.newest.msorgapp.msarkibak;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class GetNewsActionService extends BroadcastReceiver {
    private static final String URL = "https://www.xn--mziksayfas-9db95d.com/newnoti.php";
    DatabaseHelper databaseHelper;

    @Override
    public void onReceive(Context context, Intent intent) {
        databaseHelper = new DatabaseHelper(context);

        String action = intent.getAction();


        if (!action.equalsIgnoreCase("ArkaPlanAktivitesi")) {

            String[] bolact = action.split("x0x0x0x0x");

            if (bolact[0].equalsIgnoreCase("kulprofilo")) {
                Intent acint = new Intent(context, kulprofilo.class);
                acint.setFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(acint);
            } else if (bolact[0].equalsIgnoreCase("sarkad")) {
                Intent acint = new Intent(context, msarkibak.class);
                acint.putExtra("sarkad", bolact[1]);
                acint.putExtra("whereback", "mainactivity");
                acint.setFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(acint);
                Toast.makeText(context, "Şarkı açıldı, uygulamaya geri dönün...", Toast.LENGTH_SHORT).show();
            }

        }


        //Başka receiver classı aç dne
        if (isNetworkAvailable(context)) {
            comparearamanota(context, databaseHelper);
            comparesanlar(context, databaseHelper);
            comparefav(context, databaseHelper);
            accountUpdate(context, databaseHelper);
            checkprofoto(context, databaseHelper);
            updateilgsan(context, databaseHelper);
            compareGameScore(context, databaseHelper);
            compareScoreBoard(context, databaseHelper);

            //pending intentlere extralar ekle ve buraya gönder

          /*
          TO-DO List
          1) Favori ekleme ya da çıkarma olayları buradan güncellenecek (Tamamlandı)
          2) Profil Girişi ve kaydı da buradan denetlenecek(Tamamlandı)
          3) Random Öneri Paylaşımı buradan ya da başka popuptan her 24 saatte bir yapılacak (Güncelleme ile yapılacak)
          5) Pinlenen Sanatçıların kaydı da buradan oluşturulacak ve veritabanına aktarılacak (Tamamlandı)
          6) Shared Preferences kısmından splash screende güncelleme sonrası uygulamayı sıfırlama işlemi yapılacak
          7) Uygulamayı tanıtmaya yönelik snackbarlar düzenlenecek
           */
        }

    }

    public void compareScoreBoard(Context context, DatabaseHelper databaseHelper) {
        List<Pdfkaylar> pdfkaylars = databaseHelper.kaypdfcek();
        List<Kulbi> kulbis = databaseHelper.kulbicek();
        if (!kulbis.isEmpty() && !pdfkaylars.isEmpty()) {
            String kulad = pdfkaylars.get(0).getPdfad();
            String puan = pdfkaylars.get(0).getPdfack();
            String tarih = pdfkaylars.get(0).getPdfx();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
                try {

                } catch (Exception e) {

                }
            }, e -> {

            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> data = new HashMap<>();
                    data.put("skortablekul", kulad);
                    data.put("skortablepuan", puan);
                    data.put("skortabletarih", tarih);
                    return data;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);

            requestQueue.addRequestFinishedListener(request -> {
                requestQueue.stop();
                request.cancel();
            });
        }


    }

    public void compareGameScore(Context context, DatabaseHelper databaseHelper) {
        List<Kulbi> kulbis = databaseHelper.kulbicek();
        List<Ekcallar> ekcallars = databaseHelper.ekcalcek();
        String puan = ekcallars.get(0).getEkvid();
        String kalp = ekcallars.get(0).getEkad();

        if (!kulbis.isEmpty() && !ekcallars.isEmpty()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
                try {
                    response = response.trim();
                    if (response.equalsIgnoreCase("succ")) {

                    }
                } catch (Exception e) {

                }
            }, e -> {

            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> data = new HashMap<>();
                    data.put("comparegamepuhe", puan + ">" + kalp);
                    data.put("compgamekulad", kulbis.get(0).getKulad());
                    return data;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);

            requestQueue.addRequestFinishedListener(request -> {
                requestQueue.stop();
                request.cancel();
            });
        }


    }

    public void istekControl(Context context, DatabaseHelper databaseHelper) {
        List<Kulbi> kulbis = databaseHelper.kulbicek();
        List<Favyaz> favyazs = databaseHelper.favyazcekdb();

        if (!kulbis.isEmpty()) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
                try {
                    response = response.trim();
                    if (!response.equalsIgnoreCase("") || response.equalsIgnoreCase("unsucc")) {

                        if (!favyazs.isEmpty()) {
                            Boolean attir = false;
                            for (int i = 0; i < favyazs.size(); i++) {
                                if (!favyazs.get(i).getFavyad().equalsIgnoreCase("Yeni İstekler")) {
                                    String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                                    Boolean addnotif = databaseHelper.addfavyaz(new Favyaz(-1, "Yeni İstekler", "Bekleyen arkadaşlık isteklerin var!", currentTime, "null"));
                                    if (addnotif) {
                                        attir = true;
                                    }
                                }
                            }

                            if (attir == true) {
                                Intent intent = new Intent(context, GetNewsActionService.class);
                                intent.putExtra("bekistler", "bekistler");
                                intent.setAction("kulprofilox0x0x0x0xkulprofilo");
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
                                NotificationCompat.Action action = new NotificationCompat.Action(R.drawable.ic_msic, "Gözat", pendingIntent);
                                createFirstNotification(context, "Yeni İstekler", "Bekleyen arkadaşlık isteklerin var!", action, pendingIntent);
                            }

                        } else {
                            Intent intent = new Intent(context, GetNewsActionService.class);
                            intent.putExtra("bekistler", "bekistler");
                            intent.setAction("kulprofilox0x0x0x0xkulprofilo");
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
                            NotificationCompat.Action action = new NotificationCompat.Action(R.drawable.ic_msic, "Gözat", pendingIntent);
                            createFirstNotification(context, "Yeni İstekler", "Bekleyen arkadaşlık isteklerin var!", action, pendingIntent);
                        }


                    }


                } catch (Exception e) {

                }
            }, error -> {

            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("bekistcek", kulbis.get(0).getKulad());
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

    }

    public void updateilgsan(Context context, DatabaseHelper databaseHelper) {
        List<Ilgsanlar> ilgsanlar = databaseHelper.ilgsancek();
        List<Kulbi> kulcek = databaseHelper.kulbicek();
        if (!ilgsanlar.isEmpty() && !kulcek.isEmpty()) {
            String[] kulbi = kulcek.get(0).toString().split(">");
            String kulid = kulbi[4];
            String hepsi = "";
            for (int i = 0; i < ilgsanlar.size(); i++) {
                String[] ilgsanik = ilgsanlar.get(i).toString().split(">");
                hepsi = hepsi + ilgsanik[1] + ">";
            }

            hepsi = hepsi.substring(0, hepsi.length() - 1);

            String finalHepsi = hepsi;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {

            }, e -> {

            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> data = new HashMap<>();
                    data.put("ilgsanlarcek", kulid);
                    data.put("ilgsantum", finalHepsi);
                    return data;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
            requestQueue.addRequestFinishedListener(request -> {
                requestQueue.stop();
                request.cancel();
            });

        }


    }

    public void updateprofoto(Context context, String kulid) {
        File imstore = new File(context.getExternalFilesDir(null) + "/msapp/profotos");
        File[] imdiz = imstore.listFiles();
        if (imdiz != null) {
            if (imdiz.length != 0) {
                String filepath = imdiz[0].getPath();
                Bitmap bmfile = BitmapFactory.decodeFile(filepath);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmfile.compress(Bitmap.CompressFormat.PNG, 20, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();
                String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
                    try {
                        response = response.trim();
                        if (response.equalsIgnoreCase("succ")) {

                        }

                    } catch (Exception e) {

                    }
                }, e -> {

                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> data = new HashMap<>();
                        data.put("enkulid", kulid);
                        data.put("enimage", encodedImage);
                        return data;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);


                requestQueue.addRequestFinishedListener(request -> {
                    requestQueue.stop();
                    request.cancel();
                });
            }
        }
    }

    public void checkprofoto(Context context, DatabaseHelper databaseHelper) {
        List<Kulbi> kulupdate = databaseHelper.kulbicek();

        if (!kulupdate.isEmpty()) {
            String[] bolkul = kulupdate.get(0).toString().split(">");
            String kulido = bolkul[3];

            File imstore = new File(context.getExternalFilesDir(null) + "/msapp/profotos");
            File[] imdiz = imstore.listFiles();
            if (imdiz != null) {
                if (imdiz.length != 0) {
                    String imname = imdiz[0].getName();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
                        try {
                            response = response.trim();
                            if (response.equalsIgnoreCase("succ")) {
                                updateprofoto(context, bolkul[3]);
                            }

                        } catch (Exception e) {

                        }
                    }, e -> {

                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> data = new HashMap<>();
                            data.put("kulidos", kulido);
                            data.put("fotoname", imname);
                            return data;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    requestQueue.add(stringRequest);

                    requestQueue.addRequestFinishedListener(request -> {
                        requestQueue.stop();
                        request.cancel();
                    });
                }

            }

        }


    }


    public void accountUpdate(Context context, DatabaseHelper databaseHelper) {
        List<Kulbi> kulupdate = databaseHelper.kulbicek();
        if (!kulupdate.isEmpty()) {
            String finalKulad = kulupdate.get(0).getKulad();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
                try {
                    response = response.trim();
                    if (response.equalsIgnoreCase("succ")) {

                    }
                } catch (Exception e) {

                }
            }, e -> {

            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> data = new HashMap<>();
                    data.put("updatekulbi", finalKulad);
                    data.put("updatesifre", kulupdate.get(0).getKulsifre());
                    data.put("updatemail", kulupdate.get(0).getKulemail());
                    data.put("kulid", Integer.toString(kulupdate.get(0).getKulid()));
                    return data;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);

            requestQueue.addRequestFinishedListener(request -> {
                requestQueue.stop();
                request.cancel();
            });

        }

    }

    public String method(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == '<') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public void favcekos(Context context, DatabaseHelper databaseHelper) {
        //Buradaki isimleri gönder, orada içermeyenleri fava ekle
        List<Kulbi> kulcek = databaseHelper.kulbicek();
        if (!kulcek.isEmpty()) {
            String kulad = kulcek.get(0).getKulad();
            List<Favsar> favlar = databaseHelper.favsarcekdb();
            String tumad = "";

            for (int i = 0; i < favlar.size(); i++) {
                String[] bol = favlar.get(i).toString().split(">");
                String birles = bol[1] + " - " + bol[2];
                tumad = tumad + birles + "<";
            }

            tumad = method(tumad);
            String finalTumad = tumad;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {

            }, e -> {

            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> data = new HashMap<>();
                    data.put("favoriekle", finalTumad);
                    data.put("favkuli", kulad);
                    return data;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);

            requestQueue.addRequestFinishedListener(request -> {
                requestQueue.stop();
                request.cancel();
            });
        }


    }

    public void comparefav(Context context, DatabaseHelper databaseHelper) {
        List<Kulbi> kulcek = databaseHelper.kulbicek();
        List<Favsar> favlar = databaseHelper.favsarcekdb();

        if (!kulcek.isEmpty()) {
            String[] bol = kulcek.get(0).toString().split(">");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
                try {
                    response = response.trim();

                    if (favlar.size() != Integer.parseInt(response)) {
                        favcekos(context, databaseHelper);
                    }


                } catch (Exception e) {

                }
            }, e -> {

            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> data = new HashMap<>();
                    data.put("favsaycek", bol[1]);
                    return data;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);

            requestQueue.addRequestFinishedListener(request -> {
                requestQueue.stop();
                request.cancel();
            });
        }
    }

    public void sanrescek(Context context, String sanad, DatabaseHelper databaseHelper) {
        List<Everysan> everysan = databaseHelper.everysansg();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
            try {
                response = response.trim();

                File imgfile = new File(context.getExternalFilesDir(null) + "/msapp/sanresnew/" + sanad + ".jpg");
                if (!imgfile.exists()) {
                    byte[] decodedString = Base64.decode(response, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    try (OutputStream os = new BufferedOutputStream(new FileOutputStream(imgfile))) {
                        decodedByte.compress(Bitmap.CompressFormat.PNG, 100, os);
                    }
                    for (int i = 0; i < everysan.size(); i++) {
                        String[] bolres = everysan.get(i).toString().split(">");
                        if (bolres[1].equalsIgnoreCase(sanad) && bolres[3].equalsIgnoreCase("no")) {
                            Everysan silres = new Everysan(Integer.parseInt(bolres[0]), bolres[1], bolres[2], bolres[3]);
                            Boolean isdeleted = databaseHelper.deleteverysang(silres);
                            if (!isdeleted) {
                                Everysan everysan1 = new Everysan(-1, bolres[1], bolres[2], imgfile.getName());
                                databaseHelper.addeverysang(everysan1);
                            }
                        }
                    }

                }


            } catch (Exception e) {

            }
        }, error -> {

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("sanrescek", sanad);
                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

        requestQueue.addRequestFinishedListener(request -> {
            requestQueue.stop();
            request.cancel();
        });
    }

    public void sancek(Context context, int fark, DatabaseHelper databaseHelper) {
        List<Everysan> arrayList = databaseHelper.everysansg();
        ArrayList isimler = new ArrayList<String>();

        for (int a = 0; a < arrayList.size(); a++) {
            String[] bol = arrayList.get(a).toString().split(">");
            isimler.add(bol[1]);
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
            try {
                response = response.trim();
                String[] sanadlar = response.split(">");

                if (sanadlar != null) {
                    for (int i = 0; i < sanadlar.length; i++) {
                        if (!isimler.contains(sanadlar[i])) {
                            Everysan everysan = new Everysan(-1, sanadlar[i], "no", "no");
                            databaseHelper.addeverysang(everysan);

                        }
                    }
                }

            } catch (Exception e) {

            }
        }, error -> {

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("sanfark", Integer.toString(fark));
                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

        requestQueue.addRequestFinishedListener(request -> {
            requestQueue.stop();
            request.cancel();
        });
    }

    public void comparesanlar(Context context, DatabaseHelper databaseHelper) {
        List<Everysan> arrayList = databaseHelper.everysans();
        List<Everysan> arrayList2 = databaseHelper.everysansg();
        int toparrt = arrayList.size() + arrayList2.size();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
            try {
                response = response.trim();
                int fark = Integer.parseInt(response);
                if (fark != 0) {

                    sancek(context, fark, databaseHelper);
                } else {
                    for (int i = 0; i < arrayList2.size(); i++) {
                        String[] bol = arrayList2.get(i).toString().split(">");
                        if (bol[3].equalsIgnoreCase("no")) {
                            sanrescek(context, bol[1], databaseHelper);
                        }
                    }
                }

            } catch (Exception e) {

            }
        }, error -> {

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("compsan", Integer.toString(toparrt));
                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

        requestQueue.addRequestFinishedListener(request -> {
            requestQueue.stop();
            request.cancel();
        });

    }

    public void comparearamanota(Context context, DatabaseHelper databaseHelper) {
        List<Notarama> arrayList = databaseHelper.aramanota();
        List<Notarama> arrayList2 = databaseHelper.aramanotag();
        int toplamarr = arrayList.size() + arrayList2.size();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
            try {
                response = response.trim();
                int fark = Integer.parseInt(response);
                if (fark != 0) {
                    songlarcek(context, fark, databaseHelper);
                } else {
                    for (int i = 0; i < arrayList2.size(); i++) {
                        String[] bol = arrayList2.get(i).toString().split(">");
                        if (bol[3].equalsIgnoreCase("no")) {
                            pdfcek(context, bol[1]);
                        }
                    }
                }

            } catch (Exception e) {

            }
        }, error -> {

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("compnot", Integer.toString(toplamarr));
                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

        requestQueue.addRequestFinishedListener(request -> {
            requestQueue.stop();
            request.cancel();
        });


    }

    public void pdfcek(Context context, String sarkad) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        List<Notarama> notarama = databaseHelper.aramanotag();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
            try {
                response = response.trim();

                File pdffile = new File(context.getExternalFilesDir(null) + "/msapp/pdfnews/" + sarkad + ".pdf");
                if (!pdffile.exists()) {
                    byte[] pdfAsBytes = Base64.decode(response, 0);
                    try (FileOutputStream os = new FileOutputStream(pdffile, false)) {
                        os.write(pdfAsBytes);
                        os.flush();
                    }

                    for (int i = 0; i < notarama.size(); i++) {
                        String[] bolres = notarama.get(i).toString().split(">");
                        if (bolres[1].equalsIgnoreCase(sarkad) && bolres[3].equalsIgnoreCase("no")) {
                            Notarama silnot = new Notarama(Integer.parseInt(bolres[0]), bolres[1], bolres[2], bolres[3], bolres[4]);
                            Boolean isdeleted = databaseHelper.deletearamanotag(silnot);
                            if (!isdeleted) {
                                Notarama addnotarama = new Notarama(-1, bolres[1], bolres[2], pdffile.getName(), bolres[4]);
                                Boolean addnot = databaseHelper.addaramanotag(addnotarama);
                                if (addnot) {
                                    Intent intent = new Intent(context, GetNewsActionService.class);
                                    intent.putExtra("getsong", addnotarama.getNotaname());
                                    intent.setAction("sarkadx0x0x0x0x" + addnotarama.getNotaname());
                                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
                                    NotificationCompat.Action action = new NotificationCompat.Action(R.drawable.ic_msic, "Şarkıya Git", pendingIntent);
                                    createFirstNotification(context, "Yeni Şarkı Yayında!", addnotarama.getNotaname(), action, pendingIntent);
                                    String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                                    Boolean addnotif = databaseHelper.addfavyaz(new Favyaz(-1, "Yeni Şarkı Yayında!", addnotarama.getNotaname(), currentTime, "null"));
                                    if (addnotif) {

                                    }
                                } else {

                                }
                            }
                        }
                    }

                }


            } catch (Exception e) {

            }
        }, error -> {

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("pdfcek", sarkad);
                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

        requestQueue.addRequestFinishedListener(request -> {
            requestQueue.stop();
            request.cancel();
        });
    }

    public void songlarcek(Context context, int fark, DatabaseHelper databaseHelper) {
        List<Notarama> arrayList = databaseHelper.aramanotag();
        ArrayList isimler = new ArrayList<String>();
        for (int i = 0; i < arrayList.size(); i++) {
            String[] bol = arrayList.get(i).toString().split(">");
            isimler.add(bol[1]);
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, response -> {
            try {
                response = response.trim();
                String[] bolres = response.split("<");
                String[] sarkadlar = bolres[0].split(">");
                String[] sarkuriler = bolres[1].split(">");
                String[] sarkmpler = bolres[2].split(">");

                if (sarkadlar.length > 0) {
                    for (int i = 0; i < sarkadlar.length; i++) {
                        if (!isimler.contains(sarkadlar[i])) {
                            Notarama notarama = new Notarama(-1, sarkadlar[i], sarkuriler[i], "no", sarkmpler[i]);
                            Boolean succ = databaseHelper.addaramanotag(notarama);
                            if (succ) {
                                Intent intent = new Intent(context, GetNewsActionService.class);
                                intent.putExtra("anasayfa", "anasayfa");
                                intent.setAction("mainactx0x0x0x0xmainact");
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
                                NotificationCompat.Action action = new NotificationCompat.Action(R.drawable.ic_msic, "Güncelleniyor...", pendingIntent);
                                createFirstNotification(context, "Şarkılar Eklendi", "Güncellenen Şarkılar Var!", action, pendingIntent);

                            }
                        }

                    }
                }

            } catch (Exception e) {

            }
        }, error -> {

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("fark", Integer.toString(fark));
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

    public void createFirstNotification(Context context, String title, String text, NotificationCompat.Action action, PendingIntent pendingIntent) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Utils.FIRST_CHANNEL_ID)
                .setSmallIcon(R.drawable.mslogobuy)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .addAction(action);
        builder.build().flags |= FLAG_AUTO_CANCEL;

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        managerCompat.notify(Utils.FIRST_NOTIFICATION_ID, builder.build());

    }


    private boolean isNetworkAvailable(Context context) {

        // Initialize connectivity manager
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

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


}
