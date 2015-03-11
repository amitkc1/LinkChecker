package com.amitkc.linkchecker;


import com.amitkc.utilities.ColorHelper;
import com.amitkc.utilities.FileAndUrlObjects;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by amitchaudari on 3/10/15.
 */
public class LinkChecker extends FileAndUrlObjects implements ColorHelper{



    public static void main(String[] args) throws IOException{

        readDataFile();
        printURLs();
        testURLs(url);


    }






    public static void testURLs(String url) throws IOException {


            connectToURL(url);
            checkIfLessThan300();
            checkIfMoreThan400();
            checkIfRedirect();

    }

    public static void connectToURL(String url) throws IOException {

        System.out.println("URL Check Started; wait for \"Completed\" message to open the file");
        urls.add("Original URL	" + "Returned URL(May or May not be redirected)" + "	Response Code	" + "ServerID");
        failedUrls.add("URLS");
        con = new URL(url).openConnection();
        con1 = (HttpURLConnection) (new URL(url).openConnection());
        con1.setInstanceFollowRedirects(false);
        con1.setConnectTimeout(10000);
        con1.setReadTimeout(10000);

    }

    public static void checkIfLessThan300() throws IOException{

        if (con1.getResponseCode() < 300) {
            System.out.println(ColorHelper.ANSI_GREEN+" URL Number: "+ (urlNumber+1)+" "+ColorHelper.ANSI_RESET);
            url = con.getURL().toString();
            responseCode = con1.getResponseCode();
            serverID = con1.getHeaderField("ServerID") != null ? con1.getHeaderField("ServerID") : con1.getHeaderField("Server-ID");
            System.out.println(ANSI_GREEN+" Original url: " + url + " Response Code:" + responseCode +" No Redirection"+" Server ID is: "+serverID+" "+ANSI_RESET);
            count = count + 1;
            urls.add(originalURLs[urlNumber] + "	" + url + "	" + responseCode + "	" + serverID);
        }
    }

    public static void checkIfMoreThan400() throws IOException{

        if (con1.getResponseCode() >= 400) {
            System.out.println(ColorHelper.ANSI_RED+" URL Number: "+ (urlNumber+1)+" "+ColorHelper.ANSI_RESET);
            url = con.getURL().toString();
            responseCode = con1.getResponseCode();
            serverID = con1.getHeaderField("ServerID") != null ? con1.getHeaderField("ServerID") : con1.getHeaderField("Server-ID");
            System.out.println(ColorHelper.ANSI_RED+ " orignal url: " + url + " Response Code:" + responseCode+" No Redirection"+" Server ID is: "+serverID+" "+ANSI_RESET);
            failCount = failCount + 1;
            urls.add(originalURLs[urlNumber] + "	" + url + "	" + responseCode + "	" + serverID);
            failedUrls.add(originalURLs[urlNumber]);
        }
    }

    public static void checkIfRedirect() throws IOException{
        if (con1.getResponseCode() == 301 || con1.getResponseCode() == 302) {

            redirected = redirected + 1;
            url = con.getURL().toString();
            responseCode = con1.getResponseCode();

            serverID = con1.getHeaderField("ServerID") != null ? con1.getHeaderField("ServerID") : con1.getHeaderField("Server-ID");
            System.out.println(ColorHelper.ANSI_BLUE+"URL Number:"+ (urlNumber+1)+ANSI_RESET);
            System.out.println(ColorHelper.ANSI_BLUE+"Orignal url: " + url + " Response Code:" + responseCode+" Server ID is: "+serverID+ColorHelper.ANSI_RESET);
            urls.add(originalURLs[urlNumber] + "	" + url + "	" + responseCode + "	" + serverID);
            is = con.getInputStream();
            String redirected = con.getURL().toString();
            HttpURLConnection con2 = (HttpURLConnection) (new URL(redirected).openConnection());
            con2.setConnectTimeout(10000);
            con2.setReadTimeout(10000);
            url = con.getURL().toString();
            responseCode = con2.getResponseCode();

            serverID = con1.getHeaderField("ServerID") != null ? con1.getHeaderField("ServerID") : con1.getHeaderField("Server-ID");
            if (responseCode < 300) {
                count = count + 1;
            }
//						ansiOut.outln("URL Number:"+ (i+1));
//						ansiOut.outln("Redirected url:	" + url + " Response Code is " + responseCode+" Server ID is: "+serverID);
            urls.add(originalURLs[urlNumber] + "	" + url + "	" + responseCode + "	" + serverID);

        }
    }
}
