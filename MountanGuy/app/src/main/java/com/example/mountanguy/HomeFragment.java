package com.example.mountanguy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment implements FoodAdapter.OnItemClickListener {

    private DataBaseHelper databaseHelper;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewDiary;
    private FoodAdapter adapter;
    private List<DataBaseHelper.DiaryEntry> diaryEntries;
    private DiaryEntryAdapter diaryEntryAdapter;
    private Button addFoodButton;
    //private Button resetButton;
    private LinearLayout foodInputsLayout;
    private TextView totalKalorienTextView, totalProteineTextView, totalKohlenhydrateTextView, totalFetteTextView;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private float neededGrundumsatz;
    private float neededKalorienbedarf;
    private float neededZielKalorienbedarf;
    private float neededProteinbedarf;
    private float neededKohlenhydratebedarf;
    private float neededFettbedarf;


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        databaseHelper = DataBaseHelper.getInstance(getContext());
    }

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        addFoodButton = view.findViewById(R.id.add_food_button);
        foodInputsLayout = view.findViewById(R.id.food_inputs_layout);

        searchView = view.findViewById(R.id.searchView);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new FoodAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        recyclerViewDiary = view.findViewById(R.id.recyclerViewDiary);
        recyclerViewDiary.setLayoutManager(new LinearLayoutManager(getActivity()));
        diaryEntries = new ArrayList<>();
        diaryEntryAdapter = new DiaryEntryAdapter(diaryEntries);
        recyclerViewDiary.setAdapter(diaryEntryAdapter);

        totalKalorienTextView = view.findViewById(R.id.total_kalorien);
        totalProteineTextView = view.findViewById(R.id.total_proteine);
        totalKohlenhydrateTextView = view.findViewById(R.id.total_kohlenhydrate);
        totalFetteTextView = view.findViewById(R.id.total_fette);

        loadNeededNutrients();
        loadDiaryEntries();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchFood(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchFood(newText);
                return true;
            }
        });


        Button resetButton = view.findViewById(R.id.resetbutton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResetConfirmationDialog();
            }
        });

        if (addFoodButton != null) {
            addFoodButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("AddFoodButton", "Gedrückt");
                    showAddFoodDialog();
                }
            });
        }


        return view;
    }

    private void showAddFoodDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_food, null);
        builder.setView(dialogView);

        EditText foodNameInput = dialogView.findViewById(R.id.food_inputs_layout);
        EditText caloriesInput = dialogView.findViewById(R.id.calories_input);
        EditText proteinInput = dialogView.findViewById(R.id.protein_input);
        EditText carbsInput = dialogView.findViewById(R.id.carbs_input);
        EditText fatInput = dialogView.findViewById(R.id.fatsInput);
        EditText weightInput = dialogView.findViewById(R.id.weight);

        builder.setPositiveButton("Hinzufügen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = foodNameInput.getText().toString().trim();
                String caloriesStr = caloriesInput.getText().toString().trim();
                String proteinStr = proteinInput.getText().toString().trim();
                String carbsStr = carbsInput.getText().toString().trim();
                String fatStr = fatInput.getText().toString().trim();
                String weightStr = weightInput.getText().toString().trim();

                if (name.isEmpty() || caloriesStr.isEmpty() || proteinStr.isEmpty() || carbsStr.isEmpty()) {
                    Toast.makeText(getContext(), "Bitte füllen Sie alle Felder aus", Toast.LENGTH_SHORT).show();
                    return;
                }

                double calories = Double.parseDouble(caloriesStr);
                double protein = Double.parseDouble(proteinStr);
                double carbs = Double.parseDouble(carbsStr);
                double fats = Double.parseDouble(fatStr);
                double weight = Double.parseDouble(weightStr);

                boolean isFoodAdded = databaseHelper.addFood(name, calories, protein, carbs, fats, weight);
                if (isFoodAdded) {
                    Toast.makeText(getContext(), "Lebensmittel erfolgreich hinzugefügt", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Fehler beim Hinzufügen des Lebensmittels", Toast.LENGTH_SHORT).show();
                }

            }
        });

        builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void searchFood(String query) {
        Cursor cursor = databaseHelper.searchFood(query);
        List<String> foodList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String foodName = cursor.getString(cursor.getColumnIndex("NAME"));
                foodList.add(foodName);
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter = new FoodAdapter(foodList, this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(String foodItem) {
        showAmountDialog(foodItem);
    }

    private void showAmountDialog(String foodItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Menge für " + foodItem + " eingeben");

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_food_amount, null);
        builder.setView(dialogView);

        final EditText amountInput = dialogView.findViewById(R.id.amount_input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String amountStr = amountInput.getText().toString().trim();
                if (!amountStr.isEmpty()) {
                    double amount = Double.parseDouble(amountStr);
                    boolean isSaved = databaseHelper.addDiaryEntry(foodItem, amount);
                    if (isSaved) {
                        Toast.makeText(getContext(), "Menge für " + foodItem + ": " + amount + "g gespeichert", Toast.LENGTH_SHORT).show();
                        loadDiaryEntries(); // Laden der Tagebucheinträge beim Erstellen der Ansicht
                    } else {
                        Toast.makeText(getContext(), "Fehler beim Speichern der Menge", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Bitte Menge eingeben", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showResetConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Bestätigen Sie den Reset");
        builder.setMessage("Möchten Sie wirklich fortfahren? Alle Daten werden zurückgesetzt.");

        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                performReset();
            }
        });

        builder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void performReset() {
        // Hier implementieren Sie die Logik für den Reset
        databaseHelper.resetDiary();
        Toast.makeText(getContext(), "Daten wurden zurückgesetzt", Toast.LENGTH_SHORT).show();
        loadDiaryEntries();
    }


    private void loadDiaryEntries() {
        List<DataBaseHelper.DiaryEntry> diaryEntries = databaseHelper.getDiaryEntries();

        double totalKalorien = 0.0, totalProteine = 0.0, totalKohlenhydrate = 0.0, totalFette = 0.0;
        for (DataBaseHelper.DiaryEntry entry : diaryEntries) {
            totalKalorien += entry.getTotalKalorien();
            totalProteine += entry.getTotalEiweiße();
            totalKohlenhydrate += entry.getTotalKohlenhydrate();
            totalFette += entry.getTotalFette();
        }

        totalKalorienTextView.setText(String.format(Locale.getDefault(), "Gesamt Kalorien: %.2f kcal (%.2f / %.2f)", totalKalorien, totalKalorien, neededZielKalorienbedarf));
        totalProteineTextView.setText(String.format(Locale.getDefault(), "Gesamt Proteine: %.2f g (%.2f / %.2f)", totalProteine, totalProteine, neededProteinbedarf));
        totalKohlenhydrateTextView.setText(String.format(Locale.getDefault(), "Gesamt Kohlenhydrate: %.2f g (%.2f / %.2f)", totalKohlenhydrate, totalKohlenhydrate, neededKohlenhydratebedarf));
        totalFetteTextView.setText(String.format(Locale.getDefault(), "Gesamt Fette: %.2f g (%.2f / %.2f)", totalFette, totalFette, neededFettbedarf));

        DiaryEntryAdapter adapter = new DiaryEntryAdapter(diaryEntries);
        recyclerViewDiary.setAdapter(adapter);
    }


    private void loadNeededNutrients() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("nutrients", Context.MODE_PRIVATE);
        neededGrundumsatz = sharedPreferences.getFloat("grundumsatz", 0);
        neededKalorienbedarf = sharedPreferences.getFloat("kalorienbedarf", 0);
        neededZielKalorienbedarf = sharedPreferences.getFloat("zielKalorienbedarf", 0);
        neededProteinbedarf = sharedPreferences.getFloat("proteinbedarf", 0);
        neededKohlenhydratebedarf = sharedPreferences.getFloat("kohlenhydratebedarf", 0);
        neededFettbedarf = sharedPreferences.getFloat("fettbedarf", 0);
    }


}