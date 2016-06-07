package com.example.java2.notdefteriuyg;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Giris extends AppCompatActivity {

    EditText sifre;
    SharedPreferences sp;
    SharedPreferences.Editor edt;

    TextView sifirla;
    Button giris,sifremiUnuttum,bsifirla,biptal;

    int say;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);

        this.setTitle("Giriş Yap");

        sifre = (EditText) findViewById(R.id.txtSifreGiris);
        sp = getApplicationContext().getSharedPreferences("kisi", MODE_PRIVATE);
        sifirla=(TextView) findViewById(R.id.sifirla);
        giris=(Button) findViewById(R.id.btnGiris);
        sifremiUnuttum=(Button) findViewById(R.id.btnSifre);
        bsifirla=(Button) findViewById(R.id.btnSifirla);
        biptal=(Button) findViewById(R.id.btnİptal);

        //İlk açıldığında sıfırlama ve ipta butonları görünmesin.
        bsifirla.setVisibility(View.INVISIBLE);
        biptal.setVisibility(View.INVISIBLE);

        //Kayıt ekranını görmek istersek temizleyip programı tekrar çalıştırıyoruz.
        /*
        edt=sp.edit();
        edt.clear();
        edt.commit();
        */

    }

    public void fncGiris(View v) {
        if (sifre.getText().toString().trim().length() == 0) {
            sifre.setError("Lütfen bir şifre giriniz!");
            sifre.setText("");
            sifre.requestFocus();
        } else if (sifre.getText().toString().trim().length() < 4){
            sifre.setError("Girilen şifre en az 4 karakter olmalıdır!");
            sifre.setText("");
            sifre.requestFocus();
        } else {
                //Girilen şifre ile sp'nin içibdeki sifre aynıysa
                if (sifre.getText().toString().trim().equals(sp.getString("sifre", ""))) {
                    Intent i =  new Intent(Giris.this, Notlar.class);
                    startActivity(i);
                    finish();
                } else {
                    sifre.setText("");
                    sifre.setError("Yanlış şifre girdiniz!");
                    sifre.requestFocus();
                }
        }


    }

    public void fncSifreSoru(View v) {

        //Ekrana AlertDialog belirtiyoruz.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Daha önce kayıt olurken girilen şifre hatırlatma sorusunu soruyoruz.
        builder.setTitle(sp.getString("sifreSoru",""));

        //Text tipinde input oluşturyoruz. Verilen cevap buradan alınacak.
        final EditText cevap = new EditText(this);
        cevap.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(cevap);

        //Tamam ve İptal Butonları
        builder.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Get ile cevap aşada yakalanıyor.
                //Yoksa ekrana alert dialog çıkmıyor.
            }
        });
        builder.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sp.getString("sifreCevap", "").equals(cevap.getText().toString().trim())) {
                    //Cevap doğru ise sifre alanına sp içerisindeki sifreyi yapıştır.
                    //Şifre text olarak görünsün.
                    sifre.setInputType(InputType.TYPE_CLASS_TEXT);
                    sifre.setText(sp.getString("sifre",""));
                    //Sifre alanına tıklanamasın.
                    sifre.setEnabled(false);
                    //dialog penceresi kapansın.
                    dialog.cancel();
                } else {
                    cevap.setError("Cevap yanlış!");
                    cevap.setText("");
                    cevap.requestFocus();
                    say++;
                    if (say > 2) {
                        sifirla.setText("Uygulamayı Sıfırlama" +"\n"+
                            "Tüm uygulayı sıfırlamak isterseniz butona tıklayınız." +"\n"+
                                "\n"+"Not: Bu işlem sonrası verileriniz geri getirilemez!" +"\n");
                        dialog.cancel();
                        say=0;
                        /*
                            Giriş yapılan sifre ve butonları kapat
                            Yukarıda kapalı halde olan mesaj ve butonları aç
                         */
                        sifre.setVisibility(View.INVISIBLE);
                        giris.setVisibility(View.INVISIBLE);
                        sifremiUnuttum.setVisibility(View.INVISIBLE);
                        bsifirla.setVisibility(View.VISIBLE);
                        biptal.setVisibility(View.VISIBLE);
                        biptal.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //İptal ederse tekrar görünür yap.
                                sifirla.setText("");
                                sifre.setVisibility(View.VISIBLE);
                                giris.setVisibility(View.VISIBLE);
                                sifremiUnuttum.setVisibility(View.VISIBLE);
                                bsifirla.setVisibility(View.INVISIBLE);
                                biptal.setVisibility(View.INVISIBLE);
                            }
                        });
                        bsifirla.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Silme işlemini kabul ederse herşeyi sil.
                                edt = sp.edit();
                                edt.clear();
                                edt.commit();
                                DB db = new DB(Giris.this);
                                db.tabloBosalt("notlars");
                                Intent i = new Intent(Giris.this, Kontrol.class);
                                startActivity(i);
                                finish();
                            }
                        });
                    }

                }
            }
        });

    }


}
