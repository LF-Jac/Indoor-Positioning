package com.example.ipsdemo;

public class HeadingCheck {

	int counter = 0; int countStart = 0;
	double preHeading = 0.00; double amendedHeading = 0.00; static double diffHeading;
	int[] turnDetected = new int[10];
	static int uTurnToggle; static int firstToggle; static int preToggle; static int mapToggle;
	
	// This is the constructor of the class
    public HeadingCheck() {}
    
    public String checkStarts(double fromGyro)
    {
    	//Record for previous heading for the first time!
    	if(counter == 0)
    	{
    		//change uTurnToggle and mapToggle if starting another way round.
    		preHeading = fromGyro; uTurnToggle = 0; mapToggle = 0;
    		counter++;
    	}
    	else
    	{
    		//Check if previous heading is in 4th quarter!
    		if(preHeading >= 270 && preHeading <= 360)
    		{
    			//check if current heading is in 1st quarter!
    			if(fromGyro >= 0 && fromGyro <= 90)
    			{
    				amendedHeading = fromGyro + 360;
    				
    				//calculate for difference in heading orientation!
    		    	diffHeading = Math.abs(amendedHeading - fromGyro);
    			}
    			else
    			{
    				//calculate for difference in heading orientation!
    		    	diffHeading = Math.abs(preHeading - fromGyro);
    			}
    		}
    		//check if previous heading is in 1st quarter!
    		else if(preHeading >= 0 && preHeading <= 90)
    		{
    			//check if current heading is in 4th quarter!
    			if(fromGyro >= 270 && fromGyro <= 360)
    			{
    				amendedHeading = preHeading + 360;
    				
    				//calculate for difference in heading orientation!
    		    	diffHeading = Math.abs(amendedHeading - fromGyro);
    			}
    			else
    			{
    				//calculate for difference in heading orientation!
    		    	diffHeading = Math.abs(preHeading - fromGyro);
    			}
    		}
    		else
    		{
    			//calculate for difference in heading orientation!
    	    	diffHeading = Math.abs(preHeading - fromGyro);
    		}
    	}
    	//After getting diffHeading, reset preHeading to current Heading!!
    	preHeading = fromGyro;
    	
    	//if difference in heading exceeds threshold of 50 degrees, turn detected! Mark it! Fill up array of 10!
    	if(countStart <= 9)
    	{
    		if(diffHeading >= 35)
    		{
    			turnDetected[countStart] = 1;
    			countStart++;
    		}
    		else
    		{
    			turnDetected[countStart] = 0;
    			countStart++;
    		}
    	}
    	//Array full, replace value one place to the left!
    	else
    	{
    		if(diffHeading >= 35)
    		{
    			turnDetected[0] = turnDetected[1];
    			turnDetected[1] = turnDetected[2];
    			turnDetected[2] = turnDetected[3];
    			turnDetected[3] = turnDetected[4];
    			turnDetected[4] = turnDetected[5];
    			turnDetected[5] = turnDetected[6];
    			turnDetected[6] = turnDetected[7];
    			turnDetected[7] = turnDetected[8];
    			turnDetected[8] = turnDetected[9];
    			turnDetected[9] = 1;
    		}
    		else
    		{
    			turnDetected[0] = turnDetected[1];
    			turnDetected[1] = turnDetected[2];
    			turnDetected[2] = turnDetected[3];
    			turnDetected[3] = turnDetected[4];
    			turnDetected[4] = turnDetected[5];
    			turnDetected[5] = turnDetected[6];
    			turnDetected[6] = turnDetected[7];
    			turnDetected[7] = turnDetected[8];
    			turnDetected[8] = turnDetected[9];
    			turnDetected[9] = 0;
    		}
    	}
    	
    	//Counter reset every round!!
    	int counter = 0;
    	for(int i=0; i<=turnDetected.length-1; i++)
    	{
    		if((turnDetected[i] != 0) && (turnDetected[i] == 1))
    		{
    			counter++;
    		}
    	}
    	
    	//U-turn Detected. Mapping Change.
    	/*if(diffHeading > 150)
    	{
    		//set u turn counter.
    		if(uTurnToggle == 0)
    		{
    			uTurnToggle = 1;
    			
    			//reset all CP detection.
    			GraphPlot.cpDetectedCounter = 0; GraphPlot.cpDetectedCounter2 = 0; GraphPlot.cpDetectedCounter3 = 0; GraphPlot.cpDetectedCounter4 = 0;
    		}
    		else
    		{
    			uTurnToggle = 0;
    			//reset all CP detection.
    			GraphPlot.cpDetectedCounter = 0; GraphPlot.cpDetectedCounter2 = 0; GraphPlot.cpDetectedCounter3 = 0; GraphPlot.cpDetectedCounter4 = 0;
    		}
    	}
    	else
    	{
    		//uTurnToggle remain.
    		if(uTurnToggle == 0)
    		{
    			uTurnToggle = 0;
    		}
    		else
    		{
    			uTurnToggle = 1;
    		}
    	}*/
    	
    	//for every 10 steps, if turn detected to be more than 3 times, eliminate zig-zag!
    	//if system is already in Wi_Fi mode, check for CP, if not detected, continue Wi_Fi!
    	if(counter >= 3 || GraphPlot.calibrationLevel == 3)
    	{
    		//If already went to Wi_Fi, keep on checking for CP availability! 
        	if(GraphPlot.calibrationLevel == 3)
        	{       		
        		return "CPcheck";
        	}
        	else
        	{       		
        		//return "Wifi";
        		return "CPcheck";
        	}
    	}
    	else
    	{
    		return "CPcheck";
    		
    	}  	
    }
}
