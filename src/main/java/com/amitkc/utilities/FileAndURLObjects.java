package com.amitkc.utilities;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by amitchaudari on 3/10/15.
 */
public class FileAndUrlObjects {

    public static  File file = null;
    public static  FileWriter fStream=null;
    public static  BufferedWriter out=null;
    public static File directory = new File(".");
    public static InputStreamReader in=null;
    public static BufferedReader buff =null;
    public static String fileToRead = null;
    public static ArrayList<String> dataArray=new ArrayList<String>();
    public static ArrayList<String> urls = new ArrayList<String>();
    public static ArrayList<String>failedUrls = new ArrayList<String>();
    public static String originalURLs[] = null;
    public static int fileNotFoundCount=0;
    public static int count=0;
    public static InputStream is = null;
    public static int internalErrorCount=0;
    public static int timeOutCount=0;
    public static int genericError=0;
    public static int redirected=0;
    public static String line =null;
    public static String url=null;
    public static int responseCode = 0;
    public static String serverID = null;
    public static URLConnection con=null;
    public static HttpURLConnection con1 =null;
    public static int urlNumber = 0;
    public static boolean isMobile = false;
    public static void printURLs(){

        for(urlNumber =0; urlNumber<=originalURLs.length-1; urlNumber++){
            System.out.println(dataArray.get(urlNumber));

        }

    }


    public static void load_data_file(String dataFile) throws Exception {
        dataArray.clear();

        try
        {
            String line="";
            BufferedReader br=new BufferedReader(new FileReader(dataFile));
            while((line=br.readLine())!=null) {
                dataArray.add(line);
            }
            br.close();
            System.out.println("The data file length is "+ dataArray.size());
        }

        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println ("Unable to read from file");
        }
    }

    public static void readDataFile() throws IOException {

        fileToRead = directory.getCanonicalPath() + "/Data/LinkChecker.csv";
        getFileSize();
        originalURLs = new String[dataArray.size()];
    }


    public static String getDataFieldValue(String dataElement) throws Exception
    {
        String strKeyword=dataElement.split("\\~")[0];
        int intRowNum=Integer.parseInt(dataElement.split("\\~")[1]);
        String [] data_fieldnames = dataArray.get(0).split(",");
        for(int colInd=0;colInd<data_fieldnames.length;colInd++)
        {
            if((data_fieldnames[colInd]).indexOf(strKeyword)>=0) {
                return dataArray.get(intRowNum).split(",")[colInd];
            }
        }
        return "";
    }

    public static void getDataFromCSV() throws Exception
    {
        for (int i = 1; i <dataArray.size(); i++)
        {
            originalURLs[i-1]=getDataFieldValue("URLS~"+i);
            originalURLs[i-1]=encodeURL(originalURLs[i - 1]);
        }
    }

    public static void getFileSize(){
        try{
            load_data_file(fileToRead);
        }catch(Exception e){
            System.out.println(ColorHelper.ANSI_RED+"Error while reading the file. Error is: "+e.getMessage()+ColorHelper.ANSI_RESET);
        }

    }

    public static String encodeURL(String s){

        String url;

        try{
            url = s.replaceAll(" ", "%20");

        }catch(Exception e){
            url=s;
        }

        return url;

    }
}
