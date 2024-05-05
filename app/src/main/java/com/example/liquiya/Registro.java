package com.example.liquiya;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.liquiya.Menu;
import com.example.liquiya.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {
    Button buttonRegister;
    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextUsername;
    TextView textViewMessage;

    EditText editTextApellido;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        editTextUsername = findViewById(R.id.Registro_Nombre);
        editTextApellido = findViewById(R.id.Registro_Apellido);
        editTextEmail = findViewById(R.id.Registro_Correo);
        editTextPassword = findViewById(R.id.Registro_Contraseña);
        buttonRegister = findViewById(R.id.BRegistrarse);
        textViewMessage = findViewById(R.id.Mensaje);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String username = editTextUsername.getText().toString().trim();
        String apellido = editTextApellido.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty() || username.isEmpty() || apellido.isEmpty()) {
            textViewMessage.setText("Por favor, complete todos los campos.");
            return;
        }

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    textViewMessage.setText("Registro exitoso para " + mAuth.getCurrentUser().getEmail());
                    editTextUsername.setText("");
                    editTextEmail.setText("");
                    editTextPassword.setText("");
                    editTextApellido.setText("");
                    Map<String, Object> map = new HashMap<>();
                    map.put("Correo", email);
                    map.put("Nombre", username);
                    map.put("Apellido", apellido);
                    String id = mAuth.getCurrentUser().getUid();
                    mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task2) {
                            if (task2.isSuccessful()){
                                startActivity(new Intent(Registro.this, Menu.class));
                                finish();
                            }
                        }
                    });
                } else {
                    textViewMessage.setText("Error al registrar el usuario: " + task.getException().getMessage());
                }
            }
        });
    }
}
