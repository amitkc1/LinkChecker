package com.amitkc.linkchecker;


import com.amitkc.utilities.ColorHelper;
import com.amitkc.utilities.FileAndUrlObjects;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by amitchaudari on 3/10/15.
 */
public class LinkChecker extends FileAndUrlObjects implements ColorHelper{



    public static void main(String[] args) throws IOException{

        readDataFile();
        testURLs();


    }






    public static void testURLs() {
        System.out.println("URL Check Started; wait for \"Completed\" message to open the file");
        for(urlNumber =1; urlNumber<=originalURLs.length-1; urlNumber++){
            url = dataArray.get(urlNumber);
            try{

                connectToURL(url);
                checkIfLessThan300();
                checkIfMoreThan400();
                checkIfRedirect();

            }
            catch(FileNotFoundException e) {
                failCount = failCount + 1;
                url = con.getURL().toString();
                urls.add(originalURLs[urlNumber] + "	" + url + "	" + 403);
                failedUrls.add(originalURLs[urlNumber]);
            }
            catch(IOException e)
            {
                internalErrorCount=internalErrorCount+1;
                url=con.getURL().toString();
                urls.add(originalURLs[urlNumber]+"	"+url + "	" + 500);
                failedUrls.add(originalURLs[urlNumber]);
            }
            catch(Exception e)
            {
                genericError=genericError+1;
                e.printStackTrace();
                url=con.getURL().toString();
                urls.add(originalURLs[urlNumber]+"	"+url+"	"+e.getMessage());
                failedUrls.add(originalURLs[urlNumber]);
            }
        }


    }

    public static void connectToURL(String url) throws IOException {


        urls.add("Original URL	" + "Returned URL(May or May not be redirected)" + "	Response Code	");
        failedUrls.add("URLS");
        con = new URL(url).openConnection();
        con1 = (HttpURLConnection) (new URL(url).openConnection());
        con1.setInstanceFollowRedirects(false);
        con1.setConnectTimeout(10000);
        con1.setReadTimeout(10000);

    }

    public static void checkIfLessThan300() throws IOException{

        if (con1.getResponseCode() < 300) {
            System.out.println(ColorHelper.ANSI_GREEN+" URL Number: "+ (urlNumber+1));
            url = con.getURL().toString();
            responseCode = con1.getResponseCode();
            System.out.println(ANSI_GREEN+" Original url: " + url + " Response Code:" + responseCode +" No Redirection");
            count = count + 1;
            urls.add(originalURLs[urlNumber] + "	" + url + "	" + responseCode);
        }
    }

    public static void checkIfMoreThan400() throws IOException{

        if (con1.getResponseCode() >= 400) {
            System.out.println(ColorHelper.ANSI_RED+" URL Number: "+ (urlNumber+1)+" ");
            url = con.getURL().toString();
            responseCode = con1.getResponseCode();
            System.out.println(ColorHelper.ANSI_RED+ " Original url: " + url + " Response Code:" + responseCode+" No Redirection");
            failCount = failCount + 1;
            urls.add(originalURLs[urlNumber] + "	" + url + "	" + responseCode);
            failedUrls.add(originalURLs[urlNumber]);
        }
    }

    public static void checkIfRedirect() throws IOException{
            if (con1.getResponseCode() == 301 || con1.getResponseCode() == 302) {
                redirected = redirected + 1;
                url = con.getURL().toString();
                responseCode = con1.getResponseCode();
                System.out.println(ColorHelper.ANSI_BLUE + "URL Number:" + (urlNumber + 1));
                System.out.println(ColorHelper.ANSI_BLUE + "Original url: " + url + " Response Code:" + responseCode);
                urls.add(originalURLs[urlNumber] + "	" + url + "	" + responseCode);
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
                urls.add(originalURLs[urlNumber] + "	" + url + "	" + responseCode);
            }

        }
    }



