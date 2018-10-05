package com.example.ipsdemo;

public class WifiFingerprinting {

	int WL3 = 0; int WL4 = 0;   
    double[] euclideanDistance1 = new double[10];
    double smallestError = 0.00;
    int smallestPositionMarked = 0;

	// This is the constructor of the class
    public WifiFingerprinting(){}
    
    //Wi_Fi function!
    public void WiFi(int WifiLevel3, int WifiLevel4)
    {   
    	WL3 = WifiLevel3; WL4 = WifiLevel4;
    	
		//Wifi fingerprinting method! Using Nearest Neighbour Method!
    	for(int i=0; i<MainActivity.averagedDatabasex1.length; i++)
		{
			if(MainActivity.averagedDatabasex1[i] != 0 && MainActivity.averagedDatabasey1[i] != 0)
			{
				//obtaining distances for every fingerprint based on database!
				double temp = MainActivity.averagedDatabasex1[i] - (WifiLevel4);
				double temp2 = temp * (temp);
				double temp3 = MainActivity.averagedDatabasey1[i] - (WifiLevel3);
				double temp4 = temp3 * (temp3);
				double temp5 = temp2 + temp4;
				euclideanDistance1[i] = Math.sqrt(temp5);
				
				if(euclideanDistance1[i] == 0.00)
				{
					euclideanDistance1[i] = 0.01;
				}
				
				MainActivity.write1.write("-->", Integer.toString(WL3), Integer.toString(WL4), Double.toString(euclideanDistance1[i]), "", "", "", "", "", "", "");
			}
		}
		
		Fingerprinting(); 	
    }
    
    private void Fingerprinting()
    {
    	//if(CPCheck.whichCP == "3" || CPCheck.whichCP == "4")
    	//{
    		//finding for smallest euclidean distance!
        	for(int j=0; j<=7; j++)
        	{
        		if(euclideanDistance1[j] != 0)
        		{
        			if(j==4)
        			{
        				//threshold = 3!
        				if(euclideanDistance1[j] <= 15)
        				{
        					smallestError = euclideanDistance1[j];
        					smallestPositionMarked = j;
        				}
        				else
        				{
        					smallestError = euclideanDistance1[j];
        					smallestPositionMarked = -1;
        				}
        			}
        			else 
        			{
        				if(euclideanDistance1[j] <= smallestError)
        				{
        					if(euclideanDistance1[j] < 15)
        					{
        						smallestError = euclideanDistance1[j];
        						smallestPositionMarked = j;
        					}
        					else
        					{
        						smallestError = euclideanDistance1[j];
        						smallestPositionMarked = -1;
        					}
        				}
        				else
        				{
        					//do nothing.
        				}
        			}
        		}
        	}
    	//}
    	/*else if(CPCheck.whichCP == "1" || CPCheck.whichCP == "2")
    	{
    		//finding for smallest euclidean distance!
        	for(int j=0; j<=3; j++)
        	{
        		if(euclideanDistance1[j] != 0)
        		{
        			if(j==0)
        			{
        				//threshold = 3!
        				if(euclideanDistance1[j] <= 15)
        				{
        					smallestError = euclideanDistance1[j];
        					smallestPositionMarked = j;
        				}
        				else
        				{
        					smallestError = euclideanDistance1[j];
        					smallestPositionMarked = -1;
        				}
        			}
        			else 
        			{
        				if(euclideanDistance1[j] <= smallestError)
        				{
        					if(euclideanDistance1[j] < 15)
        					{
        						smallestError = euclideanDistance1[j];
        						smallestPositionMarked = j;
        					}
        					else
        					{
        						smallestError = euclideanDistance1[j];
        						smallestPositionMarked = -1;
        					}
        				}
        				else
        				{
        					//do nothing.
        				}
        			}
        		}
        	}
    	}*/ 	
    	
		if(smallestPositionMarked == 0)
		{
			CPpositioning.phase = "21";			
		}
		else if(smallestPositionMarked == 1)
		{
			CPpositioning.phase = "22";
		}
		else if(smallestPositionMarked == 2)
		{
			CPpositioning.phase = "23";
		}
		else if(smallestPositionMarked == 3)
		{
			CPpositioning.phase = "24";
		}
		if(smallestPositionMarked == 4)
		{
			CPpositioning.phase = "25";
		}
		else if(smallestPositionMarked == 5)
		{
			CPpositioning.phase = "26";
		}
		else if(smallestPositionMarked == 6)
		{
			CPpositioning.phase = "27";
		}
		else if(smallestPositionMarked == 7)
		{
			CPpositioning.phase = "28";
		}
		else
		{
			CPpositioning.phase = "20";
		}
		GraphPlot.calibrationLevel = 3;
		
		MainActivity.write1.write(CPCheck.whichCP, CPpositioning.phase, Integer.toString(WL3), Integer.toString(WL4), Double.toString(smallestError), Integer.toString(smallestPositionMarked), Double.toString(GraphPlot.gyro), "", "", "", "");
    }
}
