package com.example.ipsdemo;
public class GraphPlot {
	   
	int WL3 = 0;int WL4 = 0;int WL5 = 0;int WL6 = 0;int WL7 = 0;int WL8 = 0;
	int RStep = 0; static int calibrationLevel; static int curCounter; static int cpToggle;
	double SLength = 0.00; static double gyro; static double DWalked; static double CDistance; static double holdCDistance; static double mapDistance;
    static double holdFinalX; static double holdFinalY;
	static int cpDetectedCounter; static int cpDetectedCounter2; static int cpDetectedCounter3; static int cpDetectedCounter4;
	String headingStatus = "";
		
	// This is the constructor of the class
    public GraphPlot() {}
    
    public void setWifiLevel(int WifiLevel3, int WifiLevel4, int WifiLevel5, int WifiLevel6, int WifiLevel7, int WifiLevel8)
	{
		WL3 = WifiLevel3; WL4 = WifiLevel4; WL5 = WifiLevel5; WL6 = WifiLevel6; WL7 = WifiLevel7; WL8 = WifiLevel8;		
	}
    
    public void setVariables(int realStep, double stepLength, double heading)
    {
    	calibrationLevel = 0; RStep = realStep; SLength = stepLength; gyro = heading;
    	DWalked = DWalked + SLength;
    }
    
    public int process(int CLevel)
    {	
    	calibrationLevel = CLevel;
    	headingStatus = MainActivity.hdcheck1.checkStarts(gyro);
  	
    	if(headingStatus == "CPcheck")
    	{
    		//MainActivity.write1.testwrite("CPcheck");
    		//Run PDR.
    		MainActivity.cpcheck1.setWifiLevel(WL3, WL4, WL5, WL6, WL7, WL8);
    		MainActivity.cpcheck1.CP_PDR(RStep, SLength, DWalked);
    	}
    	else if(headingStatus == "Wifi")
    	{
    		//MainActivity.write1.testwrite("Wifi");
    		//Switch to Wifi fingerprinting.
    		MainActivity.wifi1.WiFi(WL3, WL4);  		
    	}
    	return calibrationLevel;
    }
}
