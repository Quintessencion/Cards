package com.simbirsoft.igorverbkin.mycards.mvp.presenter;

import android.util.Patterns;

import java.util.regex.Pattern;

public class UtilsValidator {

    public static final Pattern NAME_USER = Pattern.compile("^[а-яА-Яa-zA-Z][ а-яА-Яa-zA-Z]{1,29}$");
    public static final Pattern PASSWORD_USER = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");

    public static boolean validateName(String textName) {
        return NAME_USER.matcher(textName).find();
    }

    public static boolean validateEmail(String textEmail) {
        return Patterns.EMAIL_ADDRESS.matcher(textEmail).find();
    }

    public static void validatePassword(String textPassword) {

    }
}
