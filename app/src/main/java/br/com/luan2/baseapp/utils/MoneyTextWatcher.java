package br.com.luan2.baseapp.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Luan on 17/05/17.
 */

public class MoneyTextWatcher implements TextWatcher{

    private String current = "";
    private EditText editText;


    public MoneyTextWatcher(EditText editText) {
        this.editText = editText;
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(!s.toString().equals(current)){
            editText.removeTextChangedListener(this);

            try {
                String replaceable = String.format("[%s,.]", NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).getCurrency().getSymbol());

                String cleanString = s.toString().replaceAll(replaceable, "");
                double parsed = Double.parseDouble(cleanString);
                String formatted = NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format((parsed / 100));

                current = formatted;
                editText.setText(formatted);
                editText.setSelection(formatted.length());
            }catch (Exception ex){

            }
            editText.addTextChangedListener(this);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    public static String getCleanString(String value) {
        String replaceable = String.format("[%s,.]", NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).getCurrency().getSymbol());
        String cleanString = value.toString().replaceAll(replaceable, "");
        return cleanString;
    }

    public static Float toFloat(String value) {
        String replaceable = String.format("[%s,.]", NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).getCurrency().getSymbol());
        String cleanString = value.toString().replaceAll(replaceable, "");
        double parsed = Double.parseDouble(cleanString);
        return new Float(parsed / 100);
    }

    public static String getCleanCurrencyString(String value) {
        String replaceable = String.format("[%s]", NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).getCurrency().getSymbol());
        String cleanString = value.toString().replaceAll(replaceable, "");
        return cleanString;
    }

    public static String formatString(String value) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setMinimumFractionDigits(2);
        decimalFormat.setMaximumFractionDigits(2);
        String format = decimalFormat.format(Float.parseFloat(value));

        String replaceable = String.format("[%s,.]", NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).getCurrency().getSymbol());
        String cleanString = format.toString().replaceAll(replaceable, "");
        double parsed = Double.parseDouble(cleanString);
        String formatted = NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format((parsed/100));

        return formatted;
    }

}
