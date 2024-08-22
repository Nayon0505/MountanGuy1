package com.example.mountanguy;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mountanguy.databinding.ActivityHomeBinding;
import com.example.mountanguy.databinding.ActivityMainBinding;

public class Home extends AppCompatActivity {

    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());


binding.bottomNavigationView.setOnItemSelectedListener(item ->{

    switch(item.getItemId()){

        case R.id.home:
            replaceFragment(new HomeFragment());
            break;
        case R.id.profil:
            replaceFragment(new ProfilFragment());
            break;
     /*   case R.id.statistik:
            replaceFragment(new StatistikFragment());
            break;   für die zukunft gedacht in der man implementieren würde, dass man seine Daten bzw. eine Statistik über einen längeren Zeitraum betrachten kann*/
    }
    return true;
        });

    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment); // Hier wird das Fragment ersetzt
        fragmentTransaction.commit(); // Hier wird die Transaktion abgeschlossen
    }

}