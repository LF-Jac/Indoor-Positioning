package com.example.ipsdemo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import android.os.Environment;

public class WriteToFile {

	// save to file variables.
	private File root;
	private File root2;
	private File dir;
	private File dir2;
	private File file;
	private File file2;
	private PrintWriter pw;
	private PrintWriter pw2;
	private FileOutputStream f;
	private FileOutputStream f2;
	private String readFromFile = "";
	private int fileCounter= 0;
	
	// This is the constructor of the class
    public WriteToFile() {}
    
    //Checks if external storage is available for read and write! 
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    
    public void create()
    {
    	BufferedReader br = null;
  	   
  	   //Creating files to store data in phone!
  	   try{
             root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
             dir = new File (root.getAbsolutePath());
             dir.mkdirs();
             file = new File(dir, "counter.txt");
             
             br = new BufferedReader(new FileReader(file));
             String line = "";
             
             while ((line = br.readLine()) != null) {
                 readFromFile = line;
             }
          }
  	   	catch (FileNotFoundException e) {
             readFromFile =  "fileNotFound";
  	   	}
  	   	catch (IOException e) {
             readFromFile = "IOEx";
        }   
  	   	fileCounter = Integer.valueOf(readFromFile);
      	       	   
  	   	if(isExternalStorageWritable() == true)
        {
      	   root2 = android.os.Environment.getExternalStorageDirectory(); 
      	   dir2 = new File (root2.getAbsolutePath());
      	   dir2.mkdirs();
      	   file2 = new File(dir2, "PDR" + fileCounter + ".txt");
      	   fileCounter++;
      	  
      	   try {  	        
      	        f2 = new FileOutputStream(file2);
      	        pw2 = new PrintWriter(f2);
      	        pw2.println("Data");
       	        pw2.flush();
      	        
      	   }catch (IOException e) {
      	        e.printStackTrace();     	       
      	   }
      	   
      	   try {  	           	        
 	        	f = new FileOutputStream(file);
 	        	pw = new PrintWriter(f, false);
 	        	pw.println(Integer.toString(fileCounter));
 	        	pw.flush();   	        
      	   }catch (IOException e) {
 	        	e.printStackTrace();     	       
      	   }
        }
    }
    public void testwrite(String data1, String data2, String data3, String data4, String data5)
    {    	
    	pw2.println(data1 + "," + data2 + "," + data3 + "," + data4 + "," + data5);
	    pw2.flush();
    }
    
    //write to file for correction points
    public void write(String data1, String data2, String data3, String data4, String data5, String data6, String data7, String data8, String data9, String data10, String data11)
    {   	
    	pw2.println(data1 + "," + data2 + "," + data3 + "," + data4 + "," + data5 + "," + data6 + "," + data7 + "," + data8 + "," + data9 + "," + data10 + "," + data11);
	    pw2.flush();
    }
    
    //Write to file for mapping.
    public void write2(String data0, String data1, String data2, String data3, String data4, String data5, String data6, String data7, String data8)
    {    	
    	pw2.println(data0 + "," + data1 + "," + data2 + "," + data3 + "," + data4 + "," + data5 + "," + data6 + "," + data7 + "," + data8);
	    pw2.flush();
    }
}
