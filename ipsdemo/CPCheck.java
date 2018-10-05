package com.example.ipsdemo;
public class CPCheck {

    int WL3 = 0;int WL4 = 0;int WL5 = 0;int WL6 = 0;int WL7 = 0;int WL8 = 0;
    double SLength = 0.00; double correctedStepLength = 0.00;
    static String whichCP; 
    int stepsCP1 = 0; int stepsCP2 = 0; int stepsCP1toCP2 = 0; int realStep = 0;
    int RSSICP1 = 0; int RSSICP2 = 0;
    int toggle = 0;
    
    // This is the constructor of the class
    public CPCheck(){}
    
	public void setWifiLevel(int WifiLevel3, int WifiLevel4, int WifiLevel5, int WifiLevel6, int WifiLevel7, int WifiLevel8)
	{
		WL3 = WifiLevel3; WL4 = WifiLevel4; WL5 = WifiLevel5; WL6 = WifiLevel6; WL7 = WifiLevel7; WL8 = WifiLevel8;
	}
	
	//CP_PDR function!
    public void CP_PDR(int realStep, double SLength, double dWalked){    	
    	//Correction Points!! Getting auto calibration data from PDR & Wifi!
    	//If CP1 detected!
       	if((WL6 >= -64) && WL6 <= -50)
       	{
       		//so that this section is only accessed once even there's multiple same WifiLevel detected in range!
       		if(GraphPlot.cpDetectedCounter == 0)
       		{       			
       			whichCP = "1"; GraphPlot.calibrationLevel = 1; MainActivity.countPressed = 0;
       			
       			if(GraphPlot.cpDetectedCounter3 == 1 || GraphPlot.cpDetectedCounter4 == 1)
       			{
       				GraphPlot.curCounter = 0; GraphPlot.cpDetectedCounter3 = 0; GraphPlot.cpDetectedCounter4 = 0;
       			}
       			
       			//restore CP position after Wifi Fingerprinting.
       			if(GraphPlot.calibrationLevel == 3)
       			{
       				coordinateCalculation.previousX = coordinateCalculation.previousXA = MappingCP.cp1positionX;
       				coordinateCalculation.previousY = coordinateCalculation.previousYA = MappingCP.cp1positionY;
       				
       				GraphPlot.DWalked = 0.00;
       				GraphPlot.CDistance = 0.00;
       			}
       			
       			//Store info!
       			//if CP1 detected is the first!
       			switch (GraphPlot.curCounter)
       			{
       			case 0:
       				{
               		stepsCP1 = realStep; stepsCP2 = 0; stepsCP1toCP2 = 0;
               		RSSICP1 = WL6;
               		GraphPlot.holdCDistance = GraphPlot.CDistance;
               		GraphPlot.holdFinalX = coordinateCalculation.finalX;
               		GraphPlot.holdFinalY = coordinateCalculation.finalY;
               		GraphPlot.cpToggle = 0;
               		GraphPlot.curCounter=1; GraphPlot.cpDetectedCounter++;  GraphPlot.cpDetectedCounter2 = 0; 
       				}
       				break;
       			case 1:
       			{
       				RSSICP2 = WL6;
       				stepsCP2 = realStep;
               		stepsCP1toCP2 = Math.abs(stepsCP2 - stepsCP1);               		
               		correctedStepLength = 1000 / stepsCP1toCP2;
               		//GraphPlot.CDistance = GraphPlot.holdCDistance + 1000;
               		GraphPlot.CDistance = 0.00;
               		GraphPlot.DWalked = 0.00;
               		coordinateCalculation.previousX = GraphPlot.holdFinalX;
               		coordinateCalculation.previousY = GraphPlot.holdFinalY;
               		GraphPlot.cpToggle= 1;
               		GraphPlot.curCounter = 0; GraphPlot.cpDetectedCounter++;  GraphPlot.cpDetectedCounter2 = 0;
       			}  
       			break;
       			default:
       			}
       			GraphPlot.mapDistance = 0.00;
       			MainActivity.write1.write(CPCheck.whichCP, CPpositioning.phase, Integer.toString(stepsCP1), Integer.toString(stepsCP2), Integer.toString(stepsCP1toCP2), Integer.toString(WL6), Integer.toString(WL7), "", "", "", "");
       		}   
       	}
       	//If CP2 detected!
       	else if((WL7 >= -64) && (WL7 <= -50))
       	{
       		if(GraphPlot.cpDetectedCounter2 == 0)
       		{     		
       			whichCP = "2"; GraphPlot.calibrationLevel = 1; MainActivity.countPressed = 0;
       			
       			if(GraphPlot.cpDetectedCounter3 == 1 || GraphPlot.cpDetectedCounter4 == 1)
       			{
       				GraphPlot.curCounter = 0; GraphPlot.cpDetectedCounter3 = 0; GraphPlot.cpDetectedCounter4 = 0;
       			}
       			
       			//restore CP position after Wifi Fingerprinting.
       			if(GraphPlot.calibrationLevel == 3)
       			{
       				coordinateCalculation.previousX = coordinateCalculation.previousXA = MappingCP.cp2positionX;
       				coordinateCalculation.previousY = coordinateCalculation.previousYA = MappingCP.cp2positionY;
       				
       				GraphPlot.DWalked = 0.00;
       				GraphPlot.CDistance = 0.00;
       			}
       			
       			//Store info!
       			//if CP1 detected is the first!
       			switch (GraphPlot.curCounter)
       			{
       			case 0:
       				{
               		stepsCP1 = realStep; stepsCP2 = 0; stepsCP1toCP2 = 0;
               		RSSICP1 = WL7;
               		GraphPlot.holdCDistance = GraphPlot.CDistance;
               		GraphPlot.holdFinalX = coordinateCalculation.finalX;
               		GraphPlot.holdFinalY = coordinateCalculation.finalY;
               		GraphPlot.cpToggle = 0;
               		GraphPlot.curCounter=1; GraphPlot.cpDetectedCounter2++;  GraphPlot.cpDetectedCounter = 0;
       				}
       				break;
       			case 1:
       			{
       				RSSICP2 = WL7;
       				stepsCP2 = realStep;
               		stepsCP1toCP2 = Math.abs(stepsCP2 - stepsCP1);               		
               		correctedStepLength = 1000 / stepsCP1toCP2;
               		//GraphPlot.CDistance = GraphPlot.holdCDistance + 1000;
               		GraphPlot.CDistance = 0.00;
               		GraphPlot.DWalked = 0.00;
               		coordinateCalculation.finalX = GraphPlot.holdFinalX;
               		coordinateCalculation.finalY = GraphPlot.holdFinalY;
               		GraphPlot.cpToggle= 1;
               		GraphPlot.curCounter = 0; GraphPlot.cpDetectedCounter2++;  GraphPlot.cpDetectedCounter = 0;
       			}
       			break;
       			default:
       			}
       			GraphPlot.mapDistance = 0.00;
       			MainActivity.write1.write(CPCheck.whichCP, CPpositioning.phase, Integer.toString(stepsCP1), Integer.toString(stepsCP2), Integer.toString(stepsCP1toCP2), Integer.toString(WL6), Integer.toString(WL7), "", "", "", "");
        	}
       	}      	
       	//If CP3 detected!
       	else if((WL5 >= -58) && (WL5 <= -40))
       	{
       		if(GraphPlot.cpDetectedCounter3 == 0)
       		{
       			whichCP = "3"; GraphPlot.calibrationLevel = 1; toggle = 1; MainActivity.countPressed = 0;
       			
       			if(GraphPlot.cpDetectedCounter == 1 || GraphPlot.cpDetectedCounter2 == 1)
       			{
       				GraphPlot.curCounter = 0; GraphPlot.cpDetectedCounter = 0; GraphPlot.cpDetectedCounter2 = 0;
       			}
       			
       			//restore CP position after Wifi Fingerprinting.
       			if(GraphPlot.calibrationLevel == 3)
       			{
       				coordinateCalculation.previousX = coordinateCalculation.previousXA = MappingCP.cp3positionX;
       				coordinateCalculation.previousY = coordinateCalculation.previousYA = MappingCP.cp3positionY;
       				
       				GraphPlot.DWalked = 0.00;
       				GraphPlot.CDistance = 0.00;
       			}
       			
       			//Store info!
       			//if CP1 detected is the first!
       			switch (GraphPlot.curCounter)
       			{
       			case 0:
       				{
               		stepsCP1 = realStep; stepsCP1toCP2 = 0;
               		RSSICP1 = WL5;
               		GraphPlot.holdCDistance = GraphPlot.CDistance;
               		GraphPlot.holdFinalX = coordinateCalculation.finalX;
               		GraphPlot.holdFinalY = coordinateCalculation.finalY;
               		GraphPlot.cpToggle = 0;
               		GraphPlot.curCounter=1; GraphPlot.cpDetectedCounter3++;  GraphPlot.cpDetectedCounter4 = 0;
       				}
       				break;
       			case 1:
       			{
       				RSSICP2 = WL5;
       				stepsCP2 = realStep;
               		stepsCP1toCP2 = Math.abs(stepsCP2 - stepsCP1);               		
               		correctedStepLength = 1000 / stepsCP1toCP2;
               		//GraphPlot.CDistance = GraphPlot.holdCDistance + 1000;
               		GraphPlot.CDistance = 0.00;
               		GraphPlot.DWalked = 0.00;
               		coordinateCalculation.finalX = GraphPlot.holdFinalX;
               		coordinateCalculation.finalY = GraphPlot.holdFinalY;
               		GraphPlot.cpToggle= 1;
               		GraphPlot.curCounter = 0; GraphPlot.cpDetectedCounter3++;  GraphPlot.cpDetectedCounter4 = 0;
       			} 
       			break;
       			default:
       			}
       			GraphPlot.mapDistance = 0.00;
       			MainActivity.write1.write(CPCheck.whichCP, CPpositioning.phase, Integer.toString(stepsCP1), Integer.toString(stepsCP2), Integer.toString(stepsCP1toCP2), Integer.toString(WL5), Integer.toString(WL8), "", "", "", "");
       		}
       	}       	
       	//If CP4 detected!
       	else if((WL8 >= -65) && (WL8 <= -56))
       	{
       		if(GraphPlot.cpDetectedCounter4 == 0)
       		{      		   			 			
       			whichCP = "4"; GraphPlot.calibrationLevel = 1; MainActivity.countPressed = 0;
       			
       			if(GraphPlot.cpDetectedCounter == 1 || GraphPlot.cpDetectedCounter2 == 1)
       			{
       				GraphPlot.curCounter = 0; GraphPlot.cpDetectedCounter = 0; GraphPlot.cpDetectedCounter2 = 0;
       			}
       			
       			//restore CP position after Wifi Fingerprinting.
       			if(GraphPlot.calibrationLevel == 3)
       			{
       				coordinateCalculation.previousX = coordinateCalculation.previousXA = MappingCP.cp4positionX;
       				coordinateCalculation.previousY = coordinateCalculation.previousYA = MappingCP.cp4positionY;
       				
       				GraphPlot.DWalked = 0.00;
       				GraphPlot.CDistance = 0.00;
       			}
       			
       			//Store info!
       			//if CP1 detected is the first!
       			switch (GraphPlot.curCounter)
       			{
       			case 0:
       				{
       					stepsCP1 = realStep; stepsCP1toCP2 = 0;
                   		RSSICP1 = WL8;
                   		GraphPlot.holdCDistance = GraphPlot.CDistance;
                   		GraphPlot.holdFinalX = coordinateCalculation.finalX;
                   		GraphPlot.holdFinalY = coordinateCalculation.finalY;
                   		GraphPlot.cpToggle = 0;
                   		GraphPlot.curCounter=1; GraphPlot.cpDetectedCounter4++;  GraphPlot.cpDetectedCounter3 = 0;
       				}
       				break;
       			case 1:
       			{
       				RSSICP2 = WL8;
       				stepsCP2 = realStep;
               		stepsCP1toCP2 = Math.abs(stepsCP2 - stepsCP1 + 1);               		
               		correctedStepLength = 1000 / stepsCP1toCP2;
               		//GraphPlot.CDistance = GraphPlot.holdCDistance + 1000;
               		GraphPlot.CDistance = 0.00;
               		GraphPlot.DWalked = 0.00;
               		coordinateCalculation.finalX = GraphPlot.holdFinalX;
               		coordinateCalculation.finalY = GraphPlot.holdFinalY;
               		GraphPlot.cpToggle= 1;
               		GraphPlot.curCounter = 0; GraphPlot.cpDetectedCounter4++;  GraphPlot.cpDetectedCounter3 = 0;
       			} 
       			break;
       			default:
       			}
       			GraphPlot.mapDistance = 0.00;
       			MainActivity.write1.write(CPCheck.whichCP, CPpositioning.phase, Integer.toString(stepsCP1), Integer.toString(stepsCP2), Integer.toString(stepsCP1toCP2), Integer.toString(WL5), Integer.toString(WL8), "", "", "", "");
        	}      		
       	}
       	else
       	{
       		//From Wi_Fi, if no CP found, continue with Wi_Fi!
       		if(GraphPlot.calibrationLevel == 3)
       		{
       			//Switch to Wifi fingerprinting.
        		WifiFingerprinting WF1 = new WifiFingerprinting();
        		WF1.WiFi(WL3, WL4);
       		}       		
       	}      
       	
       	if(GraphPlot.calibrationLevel != 3)
       	{      		
       		if(toggle == 1)
       		{
       			MainActivity.map1.CPMap();
       			
       			toggle++;
       		}
       		
       		MainActivity.position1.CPposition(realStep, stepsCP1toCP2, SLength, correctedStepLength);       		
       	}
    }
}
