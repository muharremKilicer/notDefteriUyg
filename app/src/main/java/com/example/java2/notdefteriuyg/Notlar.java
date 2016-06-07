package com.example.java2.notdefteriuyg;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Notlar extends AppCompatActivity {

    DB db = new DB(this);
    ListView liste;
    ArrayList<String> ls = new ArrayList<>();
    ArrayList<String> idls = new ArrayList<>();
    static String gelenid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notlar);

        this.setTitle("Tüm Notlar");

        liste = (ListView) findViewById(R.id.listView);
        listeDoldur();


        //Notun üzerine tıklandığında açma
        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(Notlar.this, idls.get(position), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Notlar.this, NotDuzenle.class);
                //i.putExtra("id", idls.get(position));
                startActivity(i);
                gelenid=idls.get(position);
                //Toast.makeText(Notlar.this, gelenid, Toast.LENGTH_SHORT).show();
            }
        });
        
        //Üzerine uzun süre basınca
        liste.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder uyari = new AlertDialog.Builder(Notlar.this);
                uyari.setTitle("Silme Uyarısı!");
                uyari.setMessage(ls.get(position)+" başlıklı notu silmek istediğinize emin misiniz?");
                
                uyari.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int res = db.sil("notlars","notid",""+idls.get(position));
                        if (res > 0) {
                            //Başarılı
                            Toast.makeText(Notlar.this, "Silindi", Toast.LENGTH_SHORT).show();
                            listeDoldur();
                        } else {
                            //Başarısız
                            Toast.makeText(Notlar.this, "Silme Hatası!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                
                uyari.setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                
                uyari.create();
                uyari.show();
                
                //True olmazsa not düzenleme sayfasına gidiyor.
                return true;
            }
        });

    }

    public void listeDoldur(){
        ls.clear();
        //Tersten sıralayarak getir.
        Cursor cr = db.dataGetir("notlars", "order by notid desc");
        while(cr.moveToNext()){
            //ID leri ve isimleri çekiyoruz.
            ls.add(cr.getString(1));
            idls.add(cr.getString(0));
        }

        ArrayAdapter<String> adp = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, android.R.id.text1,ls);
        liste.setAdapter(adp);

    }

    public void fncNotEkle(View v){
        Intent i = new Intent(Notlar.this, NotEkle.class);
        startActivity(i);
        listeDoldur();
    }

    int backButtonCount;

    @Override
    public void onBackPressed() {

        if(backButtonCount >= 1)
        {
            listeDoldur();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        else
        {
            Toast.makeText(this, "Çıkmak için bir kez daha dokunun.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }

}
