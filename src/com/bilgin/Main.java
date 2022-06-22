package com.bilgin;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class Main {

    public static void main(String[] args) {
        WebCrawler webCrawler = new WebCrawler();
        WrapInt counter = new WrapInt(0, 0);
        List<ISite> siteList = getSiteList();

        long startTime = System.currentTimeMillis();//System.nanoTime();
        webCrawler.crawl(1, siteList.get(0), siteList.get(0).baseUrl(), new ArrayList<String>(), counter);
        long endTime = System.currentTimeMillis();//System.nanoTime();
        long millis = (endTime - startTime);  //divide by 1000000 when using nanoTime to get milliseconds.
        String durationStr = String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
        System.out.println("Total Duration -> " + durationStr + " Total Records:" + counter.getFilteredCount());

    }

    private static List<ISite> getSiteList() {
        List<ISite> siteList = new ArrayList<ISite>();
        siteList.add(new Haberturk());
        return siteList;
    }
}
