package com.example.mountanguy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText namelogin, passwortlogin;
    private Button buttonlogin;
    private DataBaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });
        namelogin = findViewById(R.id.loginname);
        passwortlogin = findViewById(R.id.loginpasswort);
        buttonlogin = findViewById(R.id.loginbutton);

        myDB = DataBaseHelper.getInstance(this);

//myDB.resetDatabase();
        myDB.insertInitialFoodItemsIfNeeded();
        myDB.openDatabase();


        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String nutzer = namelogin.getText().toString();
                String passwort = passwortlogin.getText().toString();

                if(nutzer.equals("")||passwort.equals(""))
                    Toast.makeText(MainActivity.this, "Bitte füllen sie alle felder aus", Toast.LENGTH_SHORT).show();
                else{
                    boolean checknutzerpass = myDB.checkusernamepassword(nutzer,passwort);
                    if(checknutzerpass==true){
                        Toast.makeText(MainActivity.this, "Anmeldung erfolgreich", Toast.LENGTH_SHORT).show();

                        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", nutzer); // Hier nutzer durch den tatsächlichen Benutzernamen ersetzen
                        editor.apply();

                        Intent h = new Intent(getApplicationContext(), Home.class);
                        startActivity(h);
                    }else{
                        Toast.makeText(MainActivity.this, "Fehler bei der Anmeldung", Toast.LENGTH_SHORT).show();
                    }
                }
                /*
                boolean var = myDB.checkUser(namelogin.getText().toString(), passwortlogin.getText().toString());
                if (var) {
                    Toast.makeText(MainActivity.this, "Anmeldung Erfolgreich", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, Home.class));
                    Log.d("Anmeldung: ",
                            "Name: " + namelogin.getText().toString() +
                                    " Passwort: " + passwortlogin.getText().toString());
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Fehler bei der Anmeldung", Toast.LENGTH_SHORT).show();
                    Log.d("Fehler Anmeldung: ",
                            "Name: " + namelogin.getText().toString() +
                                    " Passwort: " + passwortlogin.getText().toString());
                }*/
            }
        });


    }

    public void launchRegistration(View v) {

        Intent r = new Intent(this, registration.class);
        startActivity(r);
    }


}