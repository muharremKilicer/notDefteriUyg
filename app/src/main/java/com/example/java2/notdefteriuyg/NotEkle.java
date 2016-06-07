package com.example.java2.notdefteriuyg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NotEkle extends AppCompatActivity {
    DB db = new DB(this);
    EditText baslik, icerik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_ekle);

        this.setTitle("Yeni Not Ekle");

        baslik=(EditText) findViewById(R.id.txtBaslikYeni);
        icerik= (EditText) findViewById(R.id.txtİcerik);

    }

    public void yeniNot(View v){
        if (baslik.getText().toString().trim().length() ==0){
            baslik.setError("Başlık girin!");
            baslik.setText("");
            baslik.requestFocus();
        }else{
            //İçerik boş olabilir
            int result = db.kayit("notlars", new String[]{baslik.getText().toString().trim(), icerik.getText().toString()});
            if (result > 0) {
                Intent i = new Intent(NotEkle.this, Notlar.class);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(NotEkle.this, "Not Ekleme Hatası.", Toast.LENGTH_SHORT).show();
            }

        }
    }


}
