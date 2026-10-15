package com.example.proekt_razrabotvane_na_mobilni_prilojeniq;

import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Validations {
    public static boolean Validate(String text, String expression){
        final String regex = expression;
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(text);
        if(matcher.find())
            return true;
        return false;
    }

    public static void validateEmail(EditText editEmail, Button button){
        if(Validate(editEmail.getText().toString(),
                "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")){
            button.setEnabled(true);
        }else{
            button.setEnabled(false);
        }
    }
}
