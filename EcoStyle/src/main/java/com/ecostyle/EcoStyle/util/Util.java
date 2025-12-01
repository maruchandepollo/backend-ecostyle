package com.ecostyle.EcoStyle.util;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Util {
    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public String generarID() {
        StringBuilder sb = new StringBuilder(32);

        for(int x = 0; x < 32; x++) {
            int index = RANDOM.nextInt(CARACTERES.length());
            sb.append(CARACTERES.charAt(index));
        }
        String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));

        return sb.toString() + fecha;
    }
}
