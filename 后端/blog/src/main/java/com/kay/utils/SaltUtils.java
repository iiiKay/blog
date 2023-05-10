package com.kay.utils;

import java.util.Random;

public class SaltUtils {

    public static String getSalt() {
        Random ran = new Random();
        StringBuilder sb = new StringBuilder();
        char[] arr = new char[6];
        for (int j = 0; j < 6; j++) {
            int i = ran.nextInt(2);
            switch (i) {
                case 0:
                    arr[j] = (char) (ran.nextInt(26) + 97);
                    break;
                case 1:
                    arr[j] = (char) (ran.nextInt(26) + 65);
                    break;
            }
        }
        sb.append(arr);
        return sb.toString();
    }

}
