package com.example.currency;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import com.example.currency.R;


public class MainActivity extends AppCompatActivity {
    private EditText sourceAmount, targetAmount;
    private Spinner sourceCurrency, targetCurrency;

    private String[] currencies = {"USD", "EUR", "VND"};
    private double[][] exchangeRates = {
            {1.0, 0.92, 25345}, // USD to USD, EUR, VND
            {1.08, 1.0, 27413}, // EUR to USD, EUR, VND
            {0.000039, 0.000036, 1.0} // VND to USD, EUR, VND
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sourceAmount = findViewById(R.id.sourceAmount);
        targetAmount = findViewById(R.id.targetAmount);
        sourceCurrency = findViewById(R.id.sourceCurrency);
        targetCurrency = findViewById(R.id.targetCurrency);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceCurrency.setAdapter(adapter);
        targetCurrency.setAdapter(adapter);

        sourceAmount.addTextChangedListener(textWatcher);
        sourceCurrency.setOnItemSelectedListener(selectionListener);
        targetCurrency.setOnItemSelectedListener(selectionListener);
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            convertCurrency();
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    private final AdapterView.OnItemSelectedListener selectionListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            convertCurrency();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    };

    private void convertCurrency() {
        String amountStr = sourceAmount.getText().toString();
        if (amountStr.isEmpty()) {
            targetAmount.setText("");
            return;
        }

        double amount = Double.parseDouble(amountStr);
        int sourceIndex = sourceCurrency.getSelectedItemPosition();
        int targetIndex = targetCurrency.getSelectedItemPosition();

        double convertedAmount = amount * exchangeRates[sourceIndex][targetIndex];
        targetAmount.setText(String.format("%.2f", convertedAmount));
    }
}
