package com.example.java2.notdefteriuyg;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Kontrol extends AppCompatActivity {
    
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontrol);

        sp = getApplicationContext().getSharedPreferences("kisi",MODE_PRIVATE);

        if(sp.getBoolean("loginDurum",false)){
            //Kişi önceden kayıtlı ise giriş ekranına yönlendirilecek
            Intent i = new Intent(Kontrol.this, Giris.class);
            startActivity(i);
            finish();
        }else{
            //Kişi önceden kayıtlı değil ise kayıt ekranına yönlendirilecek
            Intent i = new Intent(Kontrol.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}
