package com.example.java2.notdefteriuyg;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NotDuzenle extends AppCompatActivity {

    DB db = new DB(this);
    EditText baslik, icerik;
    Button guncelle, sil;
    String gelenid2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_duzenle);

        this.setTitle("Not Düzenleme");

        baslik=(EditText) findViewById(R.id.txtBaslikYeni);
        icerik=(EditText) findViewById(R.id.txtİcerikYeni);

        //Gönderilen id'yi alırken hata vermesin diye notlar sınıfının içinden alındı.
        //Bu sınıfın içinden alınıncada çalışıyor fakat başta null hatası veriyor
        gelenid2=Notlar.gelenid;
        // Toast.makeText(NotDuzenle.this, gelenid2, Toast.LENGTH_SHORT).show();

        Cursor cr = db.dataGetir("notlars", "where notid = '" + gelenid2 + "'");
        cr.moveToNext();
        baslik.setText(cr.getString(1));
        icerik.setText(cr.getString(2));

    }

    public void fncNotSil(View v){
        int res = db.sil("notlars","notid",""+gelenid2);
        if (res > 0) {
            //Başarılı
            Intent i = new Intent(NotDuzenle.this, Notlar.class);
            startActivity(i);
            finish();
        } else {
            //Başarısız
            Toast.makeText(NotDuzenle.this, "Silme Hatası!", Toast.LENGTH_SHORT).show();
        }
    }

    public void fncNotGuncelle(View v){
        int res = db.guncelle("notlars", "notid", "" + gelenid2, new String[]{"baslik", "icerik"}, new String[]{baslik.getText().toString(), icerik.getText().toString()});
        if (res > 0) {
            Intent i = new Intent(NotDuzenle.this, Notlar.class);
            startActivity(i);
            finish();
        } else {
            Toast.makeText(NotDuzenle.this, "Güncelleme Hatası!", Toast.LENGTH_SHORT).show();
        }
    }
}
