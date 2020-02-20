package com.idoogroup.edalumno.Helpers;

import android.text.TextUtils;
import android.util.Patterns;

import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class Validators {

    public static boolean validarDouble(String d){
        try {
            Double.parseDouble(d);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public static boolean validarHttpIpAndPort(String d){
        String [] a = d.split(":");
        if (a.length!=3) return false;

        String http = a[0];
        String slashs = a[1].substring(0, 2);
        String ip = a[1].substring(2);
        String port = a[2];

        return "//".equals(slashs) && ("http".equals(http) || "https".equals(http)) && Patterns.IP_ADDRESS.matcher(ip).matches() && validarInteger(port);
    }

    public static boolean validarInteger(String i){

        try {
            Integer.parseInt(i);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public static boolean validarPhone(String tel){
        return Patterns.PHONE.matcher(tel).matches();
    }

    public static boolean validarEmail(String email){
        if(TextUtils.isEmpty(email))
            return false;
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean validarFecha(String fecha){
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        try {
            f.parse(fecha);
        }catch (Exception e){
            return false;
        }

        return true;
    }

    public static boolean validarNombreApellidos(String s) {
        return Pattern.matches("^[\\p{L} .'-]+$", s.trim());
    }

    public static boolean validarFecha(String fecha, String pattern) {
        SimpleDateFormat f = new SimpleDateFormat(pattern);
        try {
            f.parse(fecha);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public static boolean validarUsername(String s) {
        return Pattern.matches("^[a-zA-Z1-90]+$", s);
    }

}
