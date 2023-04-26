package com.antar.passenger.utils;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Currency;


public class Utility {
    public static TextWatcher listenOperatorName(final EditText etPhoneNumber, final Context context, final TextView tvOperatorName) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                etPhoneNumber.removeTextChangedListener(this);
                if (s.toString().length() == 4) {
                    String operator = PhoneProviderHelper.checkPhoneProvider(s.toString(), context);
                    tvOperatorName.setText(operator);
                } else if (s.toString().length() < 4) {
                    tvOperatorName.setText("");
                }

                etPhoneNumber.addTextChangedListener(this);
            }
        };
    }

    public static TextWatcher currencyTW(final EditText editText, final Context context) {
        final SettingPreference sp = new SettingPreference(context);
        return new TextWatcher() {
            private String current = "";

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editText.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(".")) {
                        originalString = originalString.replaceAll("[$.]", "");
                    }
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    if (originalString.contains(sp.getSetting()[0] + " ")) {
                        originalString = originalString.replaceAll(sp.getSetting()[0] + " ", "");
                    }
                    if (originalString.contains(sp.getSetting()[0])) {
                        originalString = originalString.replaceAll(sp.getSetting()[0], "");
                    }
                    if (originalString.contains(sp.getSetting()[0])) {
                        originalString = originalString.replace(sp.getSetting()[0], "");
                    }
                    if (originalString.contains(sp.getSetting()[0])) {
                        originalString = originalString.replace(sp.getSetting()[0], "");
                    }
                    if (originalString.contains(" ")) {
                        originalString = originalString.replaceAll(" ", "");
                    }
                    longval = Long.parseLong(originalString);
                    if (longval == 0) {
                        editText.setText("");
                        editText.setSelection(editText.getText().length());
                    } else if (String.valueOf(longval).length() == 1) {
                        editText.setText(sp.getSetting()[0] + "0.0" + String.valueOf(longval));
                        editText.setSelection(editText.getText().length());
                    } else if (String.valueOf(longval).length() == 2) {
                        editText.setText(sp.getSetting()[0] + "0." + String.valueOf(longval));
                        editText.setSelection(editText.getText().length());
                    } else {

                        SettingPreference sp = new SettingPreference(context);
                        DecimalFormat formatter = new DecimalFormat("#,###,###");
                        String formattedString = formatter.format(longval);
                        editText.setText(sp.getSetting()[0] + formattedString.replace(",", "."));
                        editText.setSelection(editText.getText().length());
                    }
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                editText.addTextChangedListener(this);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        };
    }


    public static void currencyTXT(TextView text, String nomninal, Context context) {
        if (nomninal ==null) return;
        SettingPreference sp = new SettingPreference(context);
        if (nomninal.length() == 1) {
            text.setText(sp.getSetting()[0] + "0.0" + nomninal);
        } else if (nomninal.length() == 2) {
            text.setText(sp.getSetting()[0] + "0." + nomninal);
        } else {
            Double getprice = Double.valueOf(nomninal);
            DecimalFormat formatter = new DecimalFormat("#,###,###");
            String formattedString = formatter.format(getprice);
            text.setText(sp.getSetting()[0] + formattedString.replace(",", "."));
        }
    }

    public static void currencyET(EditText text, String nomninal, Context context) {
        SettingPreference sp = new SettingPreference(context);
        if (nomninal.length() == 1) {
            text.setText(sp.getSetting()[0] + "0.0" + nomninal);
        } else if (nomninal.length() == 2) {
            text.setText(sp.getSetting()[0] + "0." + nomninal);
        } else {
            Double getprice = Double.valueOf(nomninal);
            DecimalFormat formatter = new DecimalFormat("#,###,###");
            String formattedString = formatter.format(getprice);
            text.setText(sp.getSetting()[0] + formattedString.replace(",", "."));
        }
    }

    public static String convertCurrency(String nominal, Context context) {
        if (nominal ==null) return "";
        SettingPreference sp = new SettingPreference(context);
        if (nominal.length() == 1) {
            return sp.getSetting()[0] + "0.0" + nominal;
        } else if (nominal.length() == 2) {
            return sp.getSetting()[0] + "0." + nominal;
        } else {
            Double getPrice = Double.valueOf(nominal);
            DecimalFormat formatter = new DecimalFormat("#,###,###");
            String formattedString = formatter.format(getPrice);
            return sp.getSetting()[0] + formattedString.replace(",", ".");
        }
    }

//    public static void convertLocaleCurrencyTV(TextView text, Context context,String nominal){
//        DecimalFormat formatter = new DecimalFormat("#,###,###");
//        switch (LocaleHelper.getLanguage(context))
//        {
//            case "en":
//                Currency currency = Currency.getInstance("USD");
//                DecimalFormat decimalFormat = new DecimalFormat("#¤");
//                decimalFormat.setCurrency(currency);
//                Double currencyDollar = 0.000067;
//                Double convertDollar = Double.parseDouble(nominal) * currencyDollar;
//
//                String formattedString = decimalFormat.format(convertDollar);
//
//                text.setText(formattedString.replace(",", "."));
//                break;
//            case "km":
//                Double currencyCambodianReal = 0.270410891973339136;
//                Double convertToCambodianReal = Double.parseDouble(nominal) * currencyCambodianReal;
//
//                if (nominal.length() == 1) {
//                    text.setText("៛"+"0.0" + nominal);
//                } else if (nominal.length() == 2) {
//                    text.setText("៛"+"0." + nominal);
//                } else {
//                    String formattedStringCambodiaReal = formatter.format(convertToCambodianReal);
//                    text.setText("៛" + formattedStringCambodiaReal.replace(",","."));
//                }
//                break;
//            case "in":
//                if (nominal.length() == 1) {
//                    text.setText("Rp"+"0.0" + nominal);
//                } else if (nominal.length() == 2) {
//                    text.setText("Rp"+"0." + nominal);
//                } else {
//                    Double getprice = Double.valueOf(nominal);
//                    String formattedStringRupiah = formatter.format(getprice);
//                    text.setText("Rp" + formattedStringRupiah.replace(",","."));
//                }
//                break;
//        }
//    }

    public static void convertLocaleCurrencyTV(TextView text, Context context,String nominal){
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        switch (LocaleHelper.getLanguage(context))
        {
            case "en":
            case "in":
            case "km":
                Double currencyCambodianReal = 0.270410891973339136;
                Double convertToCambodianReal = Double.parseDouble(nominal) * currencyCambodianReal;

                if (nominal.length() == 1) {
                    text.setText("៛"+"0.0" + nominal);
                } else if (nominal.length() == 2) {
                    text.setText("៛"+"0." + nominal);
                } else {
                    String formattedStringCambodiaReal = formatter.format(convertToCambodianReal);
                    text.setText("៛" + formattedStringCambodiaReal.replace(",","."));
                }
                break;
        }
    }

//    public static void convertLocaleCurrencyTV(TextView text, Context context,String nominal, String textAdditional){
//        DecimalFormat formatter = new DecimalFormat("#,###,###");
//        switch (LocaleHelper.getLanguage(context))
//        {
//            case "en":
//                Currency currency = Currency.getInstance("USD");
//                DecimalFormat decimalFormat = new DecimalFormat("#¤");
//                decimalFormat.setCurrency(currency);
//                Double currencyDollar = 0.000067;
//                Double convertDollar = Double.parseDouble(nominal) * currencyDollar;
//
//                String formattedString = decimalFormat.format(convertDollar);
//
//                text.setText(formattedString.replace(",", "."));
//                break;
//            case "km":
//                Double currencyCambodianReal = 0.270410891973339136;
//                Double convertToCambodianReal = Double.parseDouble(nominal) * currencyCambodianReal;
//
//                if (nominal.length() == 1) {
//                    text.setText(textAdditional + " ៛"+"0.0" + nominal);
//                } else if (nominal.length() == 2) {
//                    text.setText(textAdditional + " ៛"+"0." + nominal);
//                } else {
//                    String formattedStringCambodiaReal = formatter.format(convertToCambodianReal);
//                    text.setText(textAdditional + " ៛" + formattedStringCambodiaReal.replace(",","."));
//                }
//                break;
//            case "in":
//                if (nominal.length() == 1) {
//                    text.setText(textAdditional + " Rp"+"0.0" + nominal);
//                } else if (nominal.length() == 2) {
//                    text.setText(textAdditional + " Rp"+"0." + nominal);
//                } else {
//                    Double getprice = Double.valueOf(nominal);
//                    String formattedStringRupiah = formatter.format(getprice);
//                    text.setText(textAdditional + " Rp" + formattedStringRupiah.replace(",","."));
//                }
//                break;
//        }
//    }

    public static void convertLocaleCurrencyTV(TextView text, Context context,String nominal, String textAdditional){
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        switch (LocaleHelper.getLanguage(context))
        {
            case "en":
            case "in":
            case "km":
                Double currencyCambodianReal = 0.270410891973339136;
                Double convertToCambodianReal = Double.parseDouble(nominal) * currencyCambodianReal;

                if (nominal.length() == 1) {
                    text.setText(textAdditional + " ៛"+"0.0" + nominal);
                } else if (nominal.length() == 2) {
                    text.setText(textAdditional + " ៛"+"0." + nominal);
                } else {
                    String formattedStringCambodiaReal = formatter.format(convertToCambodianReal);
                    text.setText(textAdditional + " ៛" + formattedStringCambodiaReal.replace(",","."));
                }
                break;
        }
    }

}
