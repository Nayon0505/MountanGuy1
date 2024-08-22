package com.example.mountanguy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class personalisierung extends AppCompatActivity {

    private DataBaseHelper DB;
    private EditText Alter, Größe, Gewicht;
    private Spinner Spinner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_personalisierung);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Alter = findViewById(R.id.alterinput);
        Größe = findViewById(R.id.größeinput);
        Gewicht = findViewById(R.id.gewichtinput);
        Spinner1 = findViewById(R.id.spinner1);
        DB = DataBaseHelper.getInstance(this);

        Spinner dropdown = findViewById(R.id.spinner1);
        String[] items = new String[]{"Wählen Sie ihr Geschlecht", "Männlich", "Weiblich"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        } else if (getIntent().hasExtra("alter")) {
            restoreFromIntent(getIntent());
        }


    }

    public void applyChange(View v) {

        DB.insertIntoDatabase("3", DataBaseHelper.COL_5, Alter.getText().toString());
        DB.insertIntoDatabase("3", DataBaseHelper.COL_6, Größe.getText().toString());
        DB.insertIntoDatabase("3", DataBaseHelper.COL_7, Gewicht.getText().toString());
        DB.insertIntoDatabase("3", DataBaseHelper.COL_8, String.valueOf(Spinner1.getSelectedItemPosition()));
        Log.d("Tag", "Erfolgreich eingefügt");

     /*   Intent z = new Intent(this, ANiveau.class);

        EditText alterInput = findViewById(R.id.alterinput);
        EditText größeInput = findViewById(R.id.größeinput);
        EditText gewichtInput = findViewById(R.id.gewichtinput);
        Spinner geschlechtSpinner = findViewById(R.id.spinner1);

        z.putExtra("alter", alterInput.getText().toString());
        z.putExtra("größe", größeInput.getText().toString());
        z.putExtra("gewicht", gewichtInput.getText().toString());
        z.putExtra("geschlecht_position", geschlechtSpinner.getSelectedItemPosition());

        startActivity(z);*/

    }

    private void restoreFromIntent(Intent intent) {
        EditText alterInput = findViewById(R.id.alterinput);
        EditText größeInput = findViewById(R.id.größeinput);
        EditText gewichtInput = findViewById(R.id.gewichtinput);
        Spinner geschlechtSpinner = findViewById(R.id.spinner1);

        alterInput.setText(intent.getStringExtra("alter"));
        größeInput.setText(intent.getStringExtra("größe"));
        gewichtInput.setText(intent.getStringExtra("gewicht"));
        geschlechtSpinner.setSelection(intent.getIntExtra("geschlecht_position", 0));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        EditText alterInput = findViewById(R.id.alterinput);
        EditText größeInput = findViewById(R.id.größeinput);
        EditText gewichtInput = findViewById(R.id.gewichtinput);
        Spinner geschlechtSpinner = findViewById(R.id.spinner1);

        outState.putString("alter", alterInput.getText().toString());
        outState.putString("größe", größeInput.getText().toString());
        outState.putString("gewicht", gewichtInput.getText().toString());
        outState.putInt("geschlecht_position", geschlechtSpinner.getSelectedItemPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        EditText alterInput = findViewById(R.id.alterinput);
        EditText größeInput = findViewById(R.id.größeinput);
        EditText gewichtInput = findViewById(R.id.gewichtinput);
        Spinner geschlechtSpinner = findViewById(R.id.spinner1);

        alterInput.setText(savedInstanceState.getString("alter"));
        größeInput.setText(savedInstanceState.getString("größe"));
        gewichtInput.setText(savedInstanceState.getString("gewicht"));
        geschlechtSpinner.setSelection(savedInstanceState.getInt("geschlecht_position"));
    }

  /*  public void launchANiveau(View v) {
        Intent i = new Intent(this, ANiveau.class);

        EditText alterInput = findViewById(R.id.alterinput);
        EditText größeInput = findViewById(R.id.größeinput);
        EditText gewichtInput = findViewById(R.id.gewichtinput);
        Spinner geschlechtSpinner = findViewById(R.id.spinner1);

        i.putExtra("alter", alterInput.getText().toString());
        i.putExtra("größe", größeInput.getText().toString());
        i.putExtra("gewicht", gewichtInput.getText().toString());
        i.putExtra("geschlecht_position", geschlechtSpinner.getSelectedItemPosition());

        startActivity(i);
    }*/
}