package com.amitkc.linkchecker;


import com.amitkc.utilities.FileObjects;
import com.amitkc.utilities.URLObjects;

import java.io.IOException;

/**
 * Created by amitchaudari on 3/10/15.
 */
public class LinkChecker {



    public static void main(String[] args) throws IOException{
        readDataFile();
        printURLs();



    }


    public static void readDataFile() throws IOException {

        FileObjects.fileToRead = FileObjects.directory.getCanonicalPath() + "/Data/LinkChecker.csv";
        FileObjects.getFileSize();
        URLObjects.originalURLs = new String[URLObjects.dataArray.size()];
    }

    public static void printURLs(){

        for(int urlNumber =0; urlNumber<=URLObjects.originalURLs.length-1; urlNumber++){
            System.out.println(URLObjects.dataArray.get(urlNumber));
            testURLs(URLObjects.dataArray.get(urlNumber));
        }

    }

    public static void testURLs(String url){

        try{

            System.out.println("URL Check Started; wait for \"Completed\" message to open the file");
            URLObjects.urls.add("Original URL	"+"Returned URL(May or May not be redirected)"+"	Response Code	"+"ServerID");
            URLObjects.failedUrls.add("URLS");

        }catch(Exception e){

        }
    }
}
