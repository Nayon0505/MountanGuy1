package com.example.mountanguy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class registration extends AppCompatActivity {
    private EditText nameregister , emailregister , passwordregister, repasswortregister;
    private Button buttonregister;
    private DataBaseHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;



        });

        nameregister = findViewById(R.id.registername);
        emailregister = findViewById(R.id.registeremail);
        passwordregister = findViewById(R.id.registerpasswort);
        repasswortregister = findViewById(R.id.reregisterpasswort);
        buttonregister = findViewById(R.id.registerbutton);

        myDB = new DataBaseHelper(this);

        buttonregister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String nutzer = nameregister.getText().toString();
                String email = emailregister.getText().toString();
                String passwort = passwordregister.getText().toString();
                String passwortwiederholen = repasswortregister.getText().toString();

                if (nutzer.equals("") || email.equals("") || passwort.equals("") || passwortwiederholen.equals(""))
                    Toast.makeText(registration.this, "Bitte füllen sie alle Felder aus", Toast.LENGTH_SHORT).show();
                else {
                    if (passwort.length()<6){
                        Toast.makeText(registration.this, "Passwort muss mindestens 6 Zeichen haben", Toast.LENGTH_SHORT).show();
                    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        Toast.makeText(registration.this, "Bitte geben sie eine gültige E-Mail Adresse ein", Toast.LENGTH_SHORT).show();
                    } else if (!passwort.matches(".*[A-Z].*") ) {
                        Toast.makeText(registration.this, "Passwort muss mindestens einen Großbuchstaben enthalten", Toast.LENGTH_SHORT).show();
                    } else if ( !passwort.matches(".*[a-z].*")) {
                        Toast.makeText(registration.this, "Passwort muss mindestens einen kleinen Buchstaben enthalten", Toast.LENGTH_SHORT).show();
                    } else if (!passwort.matches(".*[0-9].*")) {
                        Toast.makeText(registration.this, "Passwort muss mindestens eine Zahl  enthalten", Toast.LENGTH_SHORT).show();
                    } else if ( !passwort.matches(".*[!@#$%^&*()//+=<>?].*")) {
                        Toast.makeText(registration.this, "Passwort muss mindestens ein Symbol enthalten", Toast.LENGTH_SHORT).show();

                    } else {
                        if (passwort.equals(passwortwiederholen)) {
                            Boolean checkUser = myDB.checkusername(nutzer);
                            if (checkUser == false) {
                                boolean checkEmail = myDB.checkuseremail(email);
                                if (checkEmail == false){
                                boolean var = myDB.registerUser(nutzer, email, passwort);
                                if (var == true) {
                                    Toast.makeText(registration.this, "Benutzer Erfolgreich Registriert", Toast.LENGTH_SHORT).show();

                                    SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("username", nutzer);  // Hier nutzer ist der registrierte Benutzername
                                    editor.apply();

                                    Intent h = new Intent(getApplicationContext(), Home.class);
                                    startActivity(h);
                                } else {
                                    Toast.makeText(registration.this, "Registration Gescheitert", Toast.LENGTH_SHORT).show();
                                }
                                }else{
                                    Toast.makeText(registration.this, "E-Mail wird bereits verwendet", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(registration.this, "Benutzer existiert bereits", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(registration.this, "Passwörter übereinstimmen nicht", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
               /*
                boolean var = myDB.registerUser(nameregister.getText().toString() , emailregister.getText().toString() , passwordregister.getText().toString());
                if(var){
                    Toast.makeText(registration.this, "Benutzer Erfolgreich Registriert!", Toast.LENGTH_SHORT).show();
                    Log.d("neue Registration: " ,
                            "Name: " +nameregister.getText().toString()+
                                    " Email: "+emailregister.getText().toString()+
                                    " Passwort: "+passwordregister.getText().toString());
                    launchPersonalisierung(v);
                }
                else
                    Toast.makeText(registration.this, "Fehler Bei Der Registration!", Toast.LENGTH_SHORT).show(); */
            }
        });


    }


    public void launchPersonalisierung(View v) {

        Intent i = new Intent(this, personalisierung.class);
        startActivity(i);
    }

    public void launchAngemeldet(View v) {

        Intent h = new Intent(this, Home.class);
        startActivity(h);
    }
    public void launchAnmeldung(View v) {

        Intent r = new Intent(this, MainActivity.class);
        startActivity(r);
    }
}