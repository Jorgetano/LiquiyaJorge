package com.example.liquiya;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button Registrarse;
    Button btnIngresar;
    Button btnRecuperar;
    FirebaseAuth mAuth;
    EditText txtPassword;
    EditText txtUser;

    /* access modifiers changed from: protected */
    @SuppressLint("MissingInflatedId")
    public void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        this.mAuth = FirebaseAuth.getInstance();
        this.txtUser = findViewById(R.id.txtUser);
        this.txtPassword = findViewById(R.id.txtPass);
        this.btnIngresar = findViewById(R.id.BIniciar_sesion);
        this.Registrarse = findViewById(R.id.BRegistrarse);
        this.btnRecuperar = findViewById(R.id.BRecuperar);

        this.Registrarse.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, Registro.class));
            }
        });
        this.btnIngresar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email = MainActivity.this.txtUser.getText().toString().trim();
                String password = MainActivity.this.txtPassword.getText().toString().trim();
                if (!email.isEmpty() || !password.isEmpty()) {
                    MainActivity.this.loginUser(email, password);
                } else {
                    Toast.makeText(MainActivity.this, "Ingresar los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        this.btnRecuperar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, restablecerContrasena.class));
            }
        });

    }

    /* access modifiers changed from: private */
    public void loginUser(String email, String password) {
        this.mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    MainActivity.this.finish();
                    MainActivity.this.startActivity(new Intent(MainActivity.this, Menu.class));
                    Toast.makeText(MainActivity.this, "BIENVENIDO", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(MainActivity.this, "Error al iniciar sesión: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(Exception e) {
                Toast.makeText(MainActivity.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
