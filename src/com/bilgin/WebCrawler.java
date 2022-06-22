package com.bilgin;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class WebCrawler {

    public boolean proxyFlag = false;
    private boolean fileIsThere = false;
    String[] queueToWriteFileArr = new String[100];
    int queueCounter = 0;
    String filePath = "";
   /* public static void main(String[] args) {
        WrapInt counter = new WrapInt(0, 0);
        List<ISite> siteList = getSiteList();
        crawl(1, siteList.get(0), siteList.get(0).baseUrl(), new ArrayList<String>(), counter);
    }*/


    public void crawl(int level, ISite site, String url, ArrayList<String> visited, WrapInt counter) {
        // counter.setTotalValue(counter.getTotalValue() + 1);
        if (level <= 5) {
            Document doc = request(site, url, visited, counter);
            if (doc != null) {
                for (Element link : doc.select("a[href]")) {
                    String nextLink = link.absUrl("href");
                    //if (!isVisited(visited, nextLink) && nextLink.startsWith(site.getBaseUrl()) && site.isValid(nextLink)) {
                    if (!isVisited(visited, nextLink) && nextLink.startsWith(site.baseUrl()) && Validator.validate(nextLink, site)) {
                        counter.setFilteredCount(counter.getFilteredCount() + 1);

                        long startTime = System.currentTimeMillis();//System.nanoTime();
                        crawl(level++, site, nextLink, visited, counter);
                        long endTime = System.currentTimeMillis();//System.nanoTime();
                        long duration = (endTime - startTime);  //divide by 1000000 when using nanoTime to get milliseconds.
                        if (duration != 0) {
                            System.out.println(duration + " DurationCrawl ->" + counter.getFilteredCount() + "." + url + "->" + nextLink);
                        }
                    }
                }
            }
        } else {
            // counter.setTotalValue(counter.getTotalValue() - 1);
            counter.setFilteredCount(counter.getFilteredCount() - 1);
        }
    }

    private boolean isVisited(ArrayList<String> visited, String nextLink) {
        boolean flag = false;
        if (visited.contains(nextLink)) {
            flag = true;
        } else if (nextLink.endsWith("#") && visited.contains(nextLink.substring(0, nextLink.length() - 1))) {
            flag = true;
        }
        return flag;
    }

    private void writeText(ISite site, String text1, String text2) {
        try {
            List<String> lines = Arrays.asList(text1, text2);
            WriteQueueForFile(site.getName(), lines);
            //Path file = getFilePath(site.getName());//s.get(filePath);
            System.out.println(text1);
            System.out.println(text2);
            // Files.write(file, lines, CREATE, APPEND);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void WriteQueueForFile(String name, List<String> lines) {
        try {
            if (queueCounter == queueToWriteFileArr.length) {
                String fileName = getFileName(name);//s.get(filePath);
                String lineArr = String.join("\n", queueToWriteFileArr);
                lineArr = lineArr + "\n";
                //Files.write(getFilePath(name), lineArr, CREATE, APPEND);
                FileWriter fw = new FileWriter(fileName, true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(lineArr);
                bw.close();
                //System.out.println(lineArr);
                queueCounter = 0;
            }
            queueToWriteFileArr[queueCounter] = lines.get(0) + "\n" + lines.get(1);//.stream().map(m->m);
            queueCounter++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Path getFilePath(String name) {
        try {
            String filePath = name + ".txt";
            if (!fileIsThere) {
                File f = new File(filePath);
                if (!f.exists()) {
                    f.createNewFile();
                }
                fileIsThere = true;
            }
            return Paths.get(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getFileName(String name) {
        try {

            if (!fileIsThere) {
                String newFilePath = name + ".txt";
                File f = new File(newFilePath);
                if (!f.exists()) {
                    f.createNewFile();
                }
                filePath = newFilePath;
                fileIsThere = true;
            }
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Document request(ISite site, String url, ArrayList<String> list, WrapInt counter) {
        try {
            ConnectionResult connectionResult = connectUrlAndGetDocument(url);
            if (connectionResult.getConnection().response().statusCode() == 200) {
                String text1 = counter.getFilteredCount() /*+ "/" + counter.getTotalValue()*/ + ": " + url;
                String text2 = "";//connectionResult.getDocument().title();

                ////long startTime = System.currentTimeMillis();//System.nanoTime();
                writeText(site, text1, text2);
                ////long endTime = System.currentTimeMillis();//System.nanoTime();
                ////long duration = (endTime - startTime);  //divide by 1000000 when using nanoTime to get milliseconds.
                ////System.out.println("Duration:" + duration);
                list.add(url);
                return connectionResult.getDocument();
            }
        } catch (Exception ex) {
            return null;
        }
        return null;
    }


    private ConnectionResult connectUrlAndGetDocument(String url) throws IOException {
        if (!proxyFlag) {
            try {
                Connection con = Jsoup.connect(url);
                Document doc = con.get();
                return new ConnectionResult(con, doc);
            } catch (IOException ex) {
                proxyFlag = true;
                return connectUrlWithProxy(url);
            }
        } else {
            return connectUrlWithProxy(url);
        }
    }

    private ConnectionResult connectUrlWithProxy(String url) throws IOException {
        try {
            Connection con = Jsoup.connect(url).sslSocketFactory(SSLHelper.socketFactory());
            Document doc = con.get();
            return new ConnectionResult(con, doc);
        } catch (IOException e) {
            return null;
        }
    }
}
