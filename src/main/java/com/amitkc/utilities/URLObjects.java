package com.amitkc.utilities;

import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by amitchaudari on 3/10/15.
 */
public class URLObjects {

    public static ArrayList<String> dataArray=new ArrayList<String>();
    public static ArrayList<String> urls = new ArrayList<String>();
    public static ArrayList<String>failedUrls = new ArrayList<String>();
    public static String originalURLs[] = null;
    public static int failCount=0;
    public static int count=0;
    public static int internalErrorCount=0;
    public static int timeOutCount=0;
    public static int genericError=0;
    public static int redirected=0;
    public static String line =null;
    public static String url=null;
    public static int responseCode;
    public static String serverID;
    public static URLConnection con=null;
    public static HttpURLConnection con1 =null;

}
