package com.bilgin;

import java.util.Arrays;
import java.util.List;

public class Haberturk implements ISite {

    private List<String> moneyTypes = Arrays.asList("euro", "dolar", "bitcoin", "sterlin", "usd");

    private static String haberturkEkonomi = "https://www.haberturk.com/ekonomi/";
    private List<String> startsWithPrefixList = Arrays.asList("https://www.haberturk.com/havadurumu/",
            "https://www.haberturk.com/nobetci-eczaneler/",
            "https://www.haberturk.com/htgastro/",
            "https://www.haberturk.com/akademi",
            "https://www.haberturk.com/yenimedya/akil-oyunlari/",
            "https://www.haberturk.com/galeri/",
            "https://www.haberturk.com/video/",
            "https://www.haberturk.com/bulmaca/",
            "https://www.haberturk.com/tv-rehberi/",
            "https://www.haberturk.com/ekonomi/",
            "https://www.haberturk.com/ramazan",
            "https://www.haberturk.com/spor/",
            "https://www.haberturk.com/secim",
            "https://www.haberturk.com/namaz-vakitleri");

    private List<String> containsWithMiddleOfList = Arrays.asList("meali-ve-turkce-okunusu",
            "ve-turkce-okunusu",
            "turkce-okunusu-ve-faziletleri",
            "turkce-okunusu-fazileti");

    private List<String> endsWithList = Arrays.asList("-cuma-namazi-vakti");


    @Override
    public List<String> startsWithPrefixList() {
        return this.startsWithPrefixList;
    }

    @Override
    public List<String> containsWithMiddleOfList() {
        return this.containsWithMiddleOfList;
    }

    @Override
    public List<String> endsWithList() {
        return this.endsWithList;
    }

    @Override
    public String baseUrl() {
        return "https://www.haberturk.com";
    }

    @Override
    public boolean customValidate(String url) {
        if (url.toLowerCase().startsWith(haberturkEkonomi) && endsWithNeKadar(url, moneyTypes)) {
            return true;
        }
        return false;
    }

    @Override
    public String getName() {
        return "haberturk";
    }

    private boolean endsWithNeKadar(String url, String text) {
        //https://www.haberturk.com/ekonomi/562-euro-ne-kadar
        String postfix = "-ne-kadar";
        if (url.endsWith(text + postfix)) {
            return true;
        }
        return false;
    }

    private boolean endsWithNeKadar(String url, List<String> textList) {
        //https://www.haberturk.com/ekonomi/562-euro-ne-kadar
        for (String text : textList) {
            if (endsWithNeKadar(url, text)) {
                return true;
            }
        }
        return false;
    }

}
