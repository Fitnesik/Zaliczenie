package com.example.zaliczenie;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;

import androidx.core.view.WindowInsetsCompat;

import java.util.Random;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText editTextLength, editTextName, editTextSurname;
    CheckBox cbUpper, cbDigits, cbSpecial;
    Button btnGenerate, btnConfirm;

    String lastGeneratedPassword = "";


    String lowerCase = "abcdefghijklmnopqrstuvwxyz";
    String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String digits = "0123456789";
    String specialChars = "!@#$%^&*()_+-=.";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        editTextLength = findViewById(R.id.length);
        editTextName = findViewById(R.id.name);
        editTextSurname = findViewById(R.id.surename);

        Spinner stanowisko = findViewById(R.id.stanowisko);
        String[] stanowiska = {"Kierownik", "Starszy programista", "Młody programista", "Tester"};

        cbUpper = findViewById(R.id.hashUpper);
        cbDigits = findViewById(R.id.hashNum);
        cbSpecial = findViewById(R.id.hashSpecial);

        btnGenerate = findViewById(R.id.btnGen);
        btnConfirm = findViewById(R.id.btnConfirm);


        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int length = Integer.parseInt(editTextLength.getText().toString());
                boolean includeUpper = cbUpper.isChecked();
                boolean includeDigits = cbDigits.isChecked();
                boolean includeSpecial = cbSpecial.isChecked();

                lastGeneratedPassword = generatePassword(length, includeUpper, includeDigits, includeSpecial);

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Wygenerowane hasło")
                        .setMessage(lastGeneratedPassword)
                        .setPositiveButton("OK", null)
                        .show();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String surname = editTextSurname.getText().toString();
                String position = stanowisko.getSelectedItem().toString();

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Dane pracownika")
                        .setMessage("Imię: " + name + "\n" +
                                "Nazwisko: " + surname + "\n" +
                                "Stanowisko: " + position + "\n\n" +
                                "Hasło: " + lastGeneratedPassword)
                        .setPositiveButton("OK", null)
                        .show();
            }
        });








        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stanowiska);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        stanowisko.setAdapter(adapter);


        stanowisko.setOnItemSelectedListener(this);





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private String generatePassword(int length, boolean includeUpper, boolean includeDigits, boolean includeSpecial) {
        Random random = new Random();
        StringBuilder password = new StringBuilder();


        for (int i = 0; i < length; i++) {
            password.append(lowerCase.charAt(random.nextInt(lowerCase.length())));
        }

        if (includeUpper && length > 0) {
            password.setCharAt(length - 1, upperCase.charAt(random.nextInt(upperCase.length())));
        }


        if (includeDigits && length > 0) {
            password.setCharAt(0, digits.charAt(random.nextInt(digits.length())));
        }


        if (includeSpecial && length > 1) {
            password.setCharAt(1, specialChars.charAt(random.nextInt(specialChars.length())));
        }

        return password.toString();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selectedItem = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}