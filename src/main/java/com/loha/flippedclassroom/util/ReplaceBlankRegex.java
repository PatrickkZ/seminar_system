package com.loha.flippedclassroom.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplaceBlankRegex {
    private static Pattern p = Pattern.compile("\\s*|\t|\r|\n");

    public static String replaceBlank(String str){
        String dest = null;
        if(str == null){
            return dest;
        }else{
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
            return dest;
        }
    }

}
