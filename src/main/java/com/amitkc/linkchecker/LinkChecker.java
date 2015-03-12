package com.amitkc.linkchecker;


import com.amitkc.utilities.ColorHelper;
import com.amitkc.utilities.FileAndUrlObjects;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by amit chaudhari on 3/10/15.
 */
public class LinkChecker extends FileAndUrlObjects implements ColorHelper {

    public static void main(String[] args) throws IOException {

        readDataFile();
        testURLs();
        createReport();

    }

    public static void testURLs() {

        System.out.println("URL Check Started; wait for \"Completed\" message to open the file");
        urls.add("URL(May or May not be redirected)" + "	Response Code	");
        for (urlNumber = 1; urlNumber <= originalURLs.length - 1; urlNumber++) {
            url = dataArray.get(urlNumber);
            try {

                connectToURL(url);
                checkIfLessThan300();
                checkIfMoreThan400();
                checkIfRedirect();

            } catch (FileNotFoundException e) {
                fileNotFoundCount = fileNotFoundCount + 1;
                url = con.getURL().toString();
                urls.add(url + "	" + 403);
                failedUrls.add(originalURLs[urlNumber]);
            } catch (IOException e) {
                internalErrorCount = internalErrorCount + 1;
                url = con.getURL().toString();
                urls.add(url + "	" + 500);
                failedUrls.add(originalURLs[urlNumber]);
            } catch (Exception e) {
                genericError = genericError + 1;
                url = con.getURL().toString();
                urls.add(url + "	" + e.getMessage());
                failedUrls.add(originalURLs[urlNumber]);
            }
        }

    }

    public static void connectToURL(String url) throws IOException {


        failedUrls.add("URLS");
        con = new URL(url).openConnection();
        con1 = (HttpURLConnection) (new URL(url).openConnection());
        con1.setInstanceFollowRedirects(false);
        con1.setConnectTimeout(10000);
        con1.setReadTimeout(10000);

    }

    public static void checkIfLessThan300() throws IOException {

        if (con1.getResponseCode() < 300) {
            System.out.println(ColorHelper.ANSI_GREEN + " URL Number: " + (urlNumber + 1));
            url = con.getURL().toString();
            responseCode = con1.getResponseCode();
            System.out.println(ANSI_GREEN + " Original url: " + url + " Response Code:" + responseCode + " No Redirection");
            count = count + 1;
            urls.add(url + "	" + responseCode);
        }
    }

    public static void checkIfMoreThan400() throws IOException {

        if (con1.getResponseCode() >= 400) {
            System.out.println(ColorHelper.ANSI_RED + " URL Number: " + (urlNumber + 1) + " ");
            url = con.getURL().toString();
            responseCode = con1.getResponseCode();
            System.out.println(ColorHelper.ANSI_RED + " Original url: " + url + " Response Code:" + responseCode + " No Redirection");
            fileNotFoundCount = fileNotFoundCount + 1;
            urls.add(url + "	" + responseCode);
        }
    }

    public static void checkIfRedirect() throws IOException {

        if (con1.getResponseCode() == 301 || con1.getResponseCode() == 302) {
            redirected = redirected + 1;
            url = con.getURL().toString();
            responseCode = con1.getResponseCode();
            System.out.println(ColorHelper.ANSI_BLUE + "URL Number:" + (urlNumber + 1));
            System.out.println(ColorHelper.ANSI_BLUE + "Original url: " + url + " Response Code:" + responseCode);
            urls.add(url + "	" + responseCode);
            is = con.getInputStream();
            String redirected = con.getURL().toString();
            HttpURLConnection con2 = (HttpURLConnection) (new URL(redirected).openConnection());
            con2.setConnectTimeout(10000);
            con2.setReadTimeout(10000);
            url = con.getURL().toString();
            responseCode = con2.getResponseCode();
            if (responseCode < 300) {
                count = count + 1;
            }
            System.out.println("URL Number:" + (urlNumber + 1));
            System.out.println("Redirected url:	" + url + " Response Code is " + responseCode);
            urls.add(url + "	" + responseCode);
        }

    }

    public static void createReport() {

        try {
            file = new File(directory.getCanonicalPath() + "/reports/LinkCheckerReport.txt");
            System.out.println("File is " + file.toString());
            if (!file.exists()) {
                file.createNewFile();
            }
            fStream = new FileWriter(file);
            out = new BufferedWriter(fStream);
            for (urlNumber =0; urlNumber < urls.size(); urlNumber++) {
                out.write(urls.get(urlNumber));
                out.newLine();
            }
            out.close();
            System.out.println(ColorHelper.ANSI_WHITE+"Completed...!");
        } catch (Exception e) {
            System.out.println(ColorHelper.ANSI_RED+"Error generating Report file. Error is: "+e.getMessage());
        }
    }
}