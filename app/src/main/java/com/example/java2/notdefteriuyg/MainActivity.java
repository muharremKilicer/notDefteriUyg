package com.example.java2.notdefteriuyg;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /*
        Manifest dosyasının düzenlenmesi için
        <activity android:name=".Kontrol">
        kısmına hangi classın önce açılması gerektiğini beliritiyoruz.
        Buradan eski olan MainActivity kaldırıldığı için
        <activity android:name=".MainActivity" />
        kısmınıda alt tarafa ekliyoruz.
     */

    EditText sifre, sifreSoru,sifreCevap;
    SharedPreferences sp;
    SharedPreferences.Editor edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Üstte bulunan action bar'a title verildi.
        this.setTitle("Kayıt Ol");

        sifre=(EditText) findViewById(R.id.txtSifre);
        sifreSoru=(EditText) findViewById(R.id.txtSifreSoru);
        sifreCevap=(EditText) findViewById(R.id.txtSifreCevap);

        sp = getApplicationContext().getSharedPreferences("kisi",MODE_PRIVATE);
        edt = sp.edit();

    }

    public void fncKayit(View v){
        if(sifre.getText().toString().trim().length() < 4){
            sifre.setError("Şifreniz en az 4 karakter içermelidir!");
            sifre.setText("");
            sifre.requestFocus();
        }else if(sifreSoru.getText().toString().trim().length() == 0){
            sifreSoru.setError("Hatırlatma sorusu boş bırakılamaz!");
            sifreSoru.setText("");
            sifreSoru.requestFocus();
        }else{
            edt.putBoolean("loginDurum", true);
            edt.putString("sifre",      sifre.getText().toString().trim());
            edt.putString("sifreSoru",  sifreSoru.getText().toString().trim().toUpperCase());
            edt.putString("sifreCevap", sifreCevap.getText().toString().trim());
            edt.commit();

            //Tabloyu oluşturyoruz ki silerken eğer tablo yoksa program patlamasın...
            DB db=new DB(this);

            Intent i = new Intent(MainActivity.this, Notlar.class);
            startActivity(i);
            //finish() telefon üzerinden geri tuşuna basıldığı zaman tekrar bu
            // sayfaya geri gelmesini engelliyor
            finish();
        }

    }
}
