package com.example.ipsdemo;

public class CPpositioning {
	int CLevel = 0; static String phase = ""; static int mapToggle;
	
	// This is the constructor of the class
    public CPpositioning(){}
    
    public void CPposition(int realStep, int stepsCP1toCP2, double SLength, double correctedStepLength)
    {    		
    	//when stepsFromCP1toCP2 is determined, correct with calibrated step length! CP-PDR!
       	//Every time true step detected, it'll process this part!
       	if(stepsCP1toCP2 != 0 || correctedStepLength != 0)
       	{       		
       		GraphPlot.calibrationLevel = 1; phase = "11";
       		if(stepsCP1toCP2 == 0 || correctedStepLength == 0)
       		{
       			if(correctedStepLength == 0)
       			{
       				correctedStepLength = SLength;
       			}
       			else
       			{      				
       				if(GraphPlot.cpToggle == 1)
       				{
       					//correctedStepLength = correctedStepLength + 1000;
       					
       					GraphPlot.cpToggle = 0;
       				}
       				else
       				{
       					//use previous correctedStepLength.
       				}
       			}
       		}	
       		
       		//For Mapping, detecting u turn.
       		if(HeadingCheck.firstToggle == 0)
        	{
       			//preToggle Determine which emtrance map to follow first!.
        		HeadingCheck.preToggle = HeadingCheck.uTurnToggle;
        		
        		GraphPlot.CDistance = Math.abs(GraphPlot.CDistance + correctedStepLength);
        		GraphPlot.DWalked = Math.abs(GraphPlot.DWalked + SLength);
        		GraphPlot.mapDistance = Math.abs(GraphPlot.mapDistance + correctedStepLength);
        		HeadingCheck.firstToggle++;
        	}
       		else
       		{
       			if(HeadingCheck.preToggle == 0)
       			{
       				if(HeadingCheck.uTurnToggle == 0)
       				{
       					GraphPlot.CDistance = Math.abs(GraphPlot.CDistance + correctedStepLength);
       					GraphPlot.DWalked = Math.abs(GraphPlot.DWalked + SLength);
       					GraphPlot.mapDistance = Math.abs(GraphPlot.mapDistance + correctedStepLength);
       				}
       				else
       				{
       					if(GraphPlot.cpDetectedCounter == 0 && GraphPlot.cpDetectedCounter2 == 0 && GraphPlot.cpDetectedCounter3 == 0 && GraphPlot.cpDetectedCounter4 == 0)
       					{
       						GraphPlot.CDistance = Math.abs(GraphPlot.CDistance - correctedStepLength);
               				GraphPlot.DWalked = Math.abs(GraphPlot.DWalked - SLength);
               				GraphPlot.mapDistance = Math.abs(GraphPlot.mapDistance - correctedStepLength);
       					}
       					else
       					{
       						//cp detected after uTurn. mapDistance reset in CPCheck. map toggle. 
       						HeadingCheck.mapToggle = 1;
       						
       						GraphPlot.CDistance = Math.abs(GraphPlot.CDistance + correctedStepLength);
           					GraphPlot.DWalked = Math.abs(GraphPlot.DWalked + SLength);
           					GraphPlot.mapDistance = Math.abs(GraphPlot.mapDistance + correctedStepLength);
       					}
       				}
       			}
       			else
       			{
       				if(HeadingCheck.uTurnToggle == 0)
       				{
       					if(GraphPlot.cpDetectedCounter == 0 && GraphPlot.cpDetectedCounter2 == 0 && GraphPlot.cpDetectedCounter3 == 0 && GraphPlot.cpDetectedCounter4 == 0)
       					{
       						GraphPlot.CDistance = Math.abs(GraphPlot.CDistance - correctedStepLength);
           					GraphPlot.DWalked = Math.abs(GraphPlot.DWalked - SLength);
           					GraphPlot.mapDistance = Math.abs(GraphPlot.mapDistance - correctedStepLength);
       					}
       					else
       					{
       						//cp detected after uTurn. mapDistance reset in CPCheck. map toggle. 
       						HeadingCheck.mapToggle = 0;
       						
       						GraphPlot.CDistance = Math.abs(GraphPlot.CDistance + correctedStepLength);
           					GraphPlot.DWalked = Math.abs(GraphPlot.DWalked + SLength);
           					GraphPlot.mapDistance = Math.abs(GraphPlot.mapDistance + correctedStepLength);
       					}
       					
       				}
       				else
       				{
       					GraphPlot.CDistance = Math.abs(GraphPlot.CDistance + correctedStepLength);
       					GraphPlot.DWalked = Math.abs(GraphPlot.DWalked + SLength);
       					GraphPlot.mapDistance = Math.abs(GraphPlot.mapDistance + correctedStepLength);
       				}
       			}
       		}  
       		MainActivity.cal1.CoordinatesCalculation(SLength, correctedStepLength);   		      		
       	}
       	else
       	{  	
       		GraphPlot.calibrationLevel = 2; phase = "10";
       		if(stepsCP1toCP2 == 0 && correctedStepLength == 0)
       		{
       			correctedStepLength = SLength;
       		}
       		
       		//For Mapping, detecting u turn.
       		if(HeadingCheck.firstToggle == 0)
        	{
        		HeadingCheck.preToggle = HeadingCheck.uTurnToggle;
        		
        		GraphPlot.CDistance = Math.abs(GraphPlot.CDistance + correctedStepLength);
        		GraphPlot.DWalked = Math.abs(GraphPlot.DWalked + SLength);
        		GraphPlot.mapDistance = Math.abs(GraphPlot.mapDistance + correctedStepLength);
        		HeadingCheck.firstToggle++;
        	}
       		else
       		{
       			if(HeadingCheck.preToggle == 0)
       			{
       				if(HeadingCheck.uTurnToggle == 0)
       				{
       					GraphPlot.CDistance = Math.abs(GraphPlot.CDistance + correctedStepLength);
       					GraphPlot.DWalked = Math.abs(GraphPlot.DWalked + SLength);
       					GraphPlot.mapDistance = Math.abs(GraphPlot.mapDistance + correctedStepLength);
       				}
       				else
       				{
       					if(GraphPlot.cpDetectedCounter == 0 && GraphPlot.cpDetectedCounter2 == 0 && GraphPlot.cpDetectedCounter3 == 0 && GraphPlot.cpDetectedCounter4 == 0)
       					{
       						GraphPlot.CDistance = Math.abs(GraphPlot.CDistance - correctedStepLength);
               				GraphPlot.DWalked = Math.abs(GraphPlot.DWalked - SLength);
               				GraphPlot.mapDistance = Math.abs(GraphPlot.mapDistance - correctedStepLength);
       					}
       					else
       					{
       						//cp detected after uTurn. mapDistance reset in CPCheck. map toggle. 
       						HeadingCheck.mapToggle = 1;
       						
       						GraphPlot.CDistance = Math.abs(GraphPlot.CDistance + correctedStepLength);
           					GraphPlot.DWalked = Math.abs(GraphPlot.DWalked + SLength);
           					GraphPlot.mapDistance = Math.abs(GraphPlot.mapDistance + correctedStepLength);
       					}
       				}
       			}
       			else
       			{
       				if(HeadingCheck.uTurnToggle == 0)
       				{
       					if(GraphPlot.cpDetectedCounter == 0 && GraphPlot.cpDetectedCounter2 == 0 && GraphPlot.cpDetectedCounter3 == 0 && GraphPlot.cpDetectedCounter4 == 0)
       					{
       						GraphPlot.CDistance = Math.abs(GraphPlot.CDistance - correctedStepLength);
           					GraphPlot.DWalked = Math.abs(GraphPlot.DWalked - SLength);
           					GraphPlot.mapDistance = Math.abs(GraphPlot.mapDistance - correctedStepLength);
       					}
       					else
       					{
       						//cp detected after uTurn. mapDistance reset in CPCheck. map toggle. 
       						HeadingCheck.mapToggle = 0;
       						
       						GraphPlot.CDistance = Math.abs(GraphPlot.CDistance + correctedStepLength);
           					GraphPlot.DWalked = Math.abs(GraphPlot.DWalked + SLength);
           					GraphPlot.mapDistance = Math.abs(GraphPlot.mapDistance + correctedStepLength);
       					}
       				}
       				else
       				{
       					GraphPlot.CDistance = Math.abs(GraphPlot.CDistance + correctedStepLength);
       					GraphPlot.DWalked = Math.abs(GraphPlot.DWalked + SLength);
       					GraphPlot.mapDistance = Math.abs(GraphPlot.mapDistance + correctedStepLength);
       				}
       			}
       		}	
       		MainActivity.cal1.CoordinatesCalculation(SLength, correctedStepLength);        		
       	}
    }
}
