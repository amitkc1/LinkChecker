package com.amitkc.utilities;

import java.io.*;

/**
 * Created by amitchaudari on 3/10/15.
 */
public class FileObjects {

    public static  File file = null;
    public static  FileWriter fStream=null;
    public static  BufferedWriter out=null;
    public static File directory = new File(".");
    public static InputStreamReader in=null;
    public static BufferedReader buff =null;
    public static String fileToRead = null;

    public static void load_data_file(String dataFile) throws Exception {
        URLObjects.dataArray.clear();

        try
        {
            String line="";
            BufferedReader br=new BufferedReader(new FileReader(dataFile));
            while((line=br.readLine())!=null) {
                URLObjects.dataArray.add(line);
            }
            br.close();
            System.out.println("The data file length is "+ URLObjects.dataArray.size());
        }

        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println ("Unable to read from file");
        }
    }

    public static String getDataFieldValue(String dataElement) throws Exception
    {
        String strKeyword=dataElement.split("\\~")[0];
        int intRowNum=Integer.parseInt(dataElement.split("\\~")[1]);
        String [] data_fieldnames=URLObjects.dataArray.get(0).split(",");
        for(int colInd=0;colInd<data_fieldnames.length;colInd++)
        {
            if((data_fieldnames[colInd]).indexOf(strKeyword)>=0) {
                return URLObjects.dataArray.get(intRowNum).split(",")[colInd];
            }
        }
        return "";
    }

    public static void getDataFromCSV() throws Exception
    {
        for (int i = 1; i <URLObjects.dataArray.size(); i++)
        {
            URLObjects.originalURLs[i-1]=getDataFieldValue("URLS~"+i);
            URLObjects.originalURLs[i-1]=encodeURL(URLObjects.originalURLs[i - 1]);
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
