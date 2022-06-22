package com.bilgin;

import java.util.List;

public class Validator {

    public static boolean validate(String url, ISite iSite) {
        if (startsWith(url.toLowerCase(), iSite.startsWithPrefixList())) {
            return false;
        } else if (containsWith(url.toLowerCase(), iSite.containsWithMiddleOfList())) {
            return false;
        } else if (endsWith(url.toLowerCase(), iSite.endsWithList())) {
            return false;
        }
        if (iSite.customValidate(url)){
            return false;
        }
        return true;
    }

    private static boolean endsWith(String url, List<String> textList) {
        for (String text : textList) {
            if (url.endsWith(text)) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsWith(String url, List<String> textList) {
        for (String text : textList) {
            if (url.contains(text)) {
                return true;
            }
        }
        return false;
    }

    private static boolean startsWith(String url, List<String> textList) {
        for (String text : textList) {
            if (url.startsWith(text)) {
                return true;
            }
        }
        return false;
    }

}
