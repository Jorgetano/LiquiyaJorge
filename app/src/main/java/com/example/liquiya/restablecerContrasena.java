package com.example.liquiya;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class restablecerContrasena extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText correoRecuperar; // Referencia al EditText para el correo electrónico

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_restablecer_contrasena);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        correoRecuperar = findViewById(R.id.CorreoRecuperar); // Obtener referencia al EditText

        findViewById(R.id.BRecuperar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = correoRecuperar.getText().toString().trim();

                if (!email.isEmpty()) {
                    mAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Mostrar diálogo modal de éxito
                                        AlertDialog.Builder builder = new AlertDialog.Builder(restablecerContrasena.this);
                                        builder.setTitle("Correo Enviado");
                                        builder.setMessage("Se ha enviado un correo para restablecer la contraseña.");
                                        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                startActivity(new Intent(restablecerContrasena.this, MainActivity.class));

                                            }
                                        });
                                        builder.show();
                                    } else {
                                        // Mostrar diálogo modal de error
                                        AlertDialog.Builder builder = new AlertDialog.Builder(restablecerContrasena.this);
                                        builder.setTitle("Error");
                                        builder.setMessage("Error al enviar el correo de restablecimiento.");
                                        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                        builder.show();
                                    }
                                }
                            });
                } else {
                    // Mostrar diálogo modal para ingresar correo electrónico
                    AlertDialog.Builder builder = new AlertDialog.Builder(restablecerContrasena.this);
                    builder.setTitle("Correo Electrónico Vacío");
                    builder.setMessage("Ingresa tu correo electrónico.");
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            }
        });
    }
}
