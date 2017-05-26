package br.com.luan2.baseapp.utils;

/**
 * Created by bsilva on 25/04/17.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

    private static final String PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static boolean isValid(String args) {
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(args);
        return matcher.matches();
    }

}