package com.example.volumeconvertor;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText valueEditText;
    private Spinner fromUnitSpinner;
    private Spinner toUnitSpinner;
    private Button convertButton;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        valueEditText = findViewById(R.id.valueEditText);
        fromUnitSpinner = findViewById(R.id.fromUnitSpinner);
        toUnitSpinner = findViewById(R.id.toUnitSpinner);
        convertButton = findViewById(R.id.convertButton);
        resultTextView = findViewById(R.id.resultTextView);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.volume_units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromUnitSpinner.setAdapter(adapter);
        toUnitSpinner.setAdapter(adapter);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertVolume();
            }
        });
    }

    private void convertVolume() {
        String valueStr = valueEditText.getText().toString();
        if (valueStr.isEmpty()) {
            resultTextView.setText("Please enter a value.");
            return;
        }

        double value = Double.parseDouble(valueStr);
        String fromUnit = fromUnitSpinner.getSelectedItem().toString();
        String toUnit = toUnitSpinner.getSelectedItem().toString();

        double convertedValue = convert(value, fromUnit, toUnit);
        displayResult(convertedValue, toUnit);
    }

    private double convert(double value, String fromUnit, String toUnit) {
        // Define conversion factors for various volume units
        double literToMilliliter = 1000;
        double literToGallon = 0.264172;
        double milliliterToLiter = 0.001;
        double milliliterToGallon = 0.000264172;
        double gallonToLiter = 3.78541;
        double gallonToMilliliter = 3785.41;

        if (fromUnit.equals("Liter") && toUnit.equals("Milliliter")) {
            return value * literToMilliliter;
        } else if (fromUnit.equals("Liter") && toUnit.equals("Gallon")) {
            return value * literToGallon;
        } else if (fromUnit.equals("Milliliter") && toUnit.equals("Liter")) {
            return value * milliliterToLiter;
        } else if (fromUnit.equals("Milliliter") && toUnit.equals("Gallon")) {
            return value * milliliterToGallon;
        } else if (fromUnit.equals("Gallon") && toUnit.equals("Liter")) {
            return value * gallonToLiter;
        } else if (fromUnit.equals("Gallon") && toUnit.equals("Milliliter")) {
            return value * gallonToMilliliter;
        } else {
            return value; // Default to no conversion
        }
    }

    private void displayResult(double convertedValue, String toUnit) {
        DecimalFormat df = new DecimalFormat("#.##");
        String result = df.format(convertedValue) + " " + toUnit;
        resultTextView.setText(result);
    }
}
