package com.example.liquiya;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Animacion extends AppCompatActivity {
    private static final long TIEMPO_ESPERA = 5000;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_animacion);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Animacion.this.startActivity(new Intent(Animacion.this, com.example.liquiya.MainActivity.class));
                Animacion.this.finish();
            }
        }, 5000);
    }
}
