package com.example.ipsdemo;

public class coordinateCalculation {

	static String whichCP; 
	String region = "";
	double degToRad = Math.PI / 180;
	int countPressed = 1;
	double radianAngle = 0.00;
	double quadrantX = 0.00; double quadrantY = 0.00;
	double quadrantXA = 0.00; double quadrantYA = 0.00;
	static double finalX = 0.00; static double finalY = 0.00; static double previousX = 0.00; static double previousY = 0.00;
	static double finalXA = 0.00; static double finalYA = 0.00; static double previousXA = 0.00; static double previousYA = 0.00;
	
	// This is the constructor of the class
    public coordinateCalculation() {}
    
    public void CoordinatesCalculation(double stepLength, double correctedSLength)
    {  	
    	if(countPressed != 0 || stepLength != 0)
    	{
    		//To eliminate negative angles because of spikes!
    		if(GraphPlot.gyro >= 0)
    		{   			
    			//converting angle in degrees to radian!
    			radianAngle = GraphPlot.gyro * degToRad;
    			
    			//calculate distance for y and x axis respectively!
    			quadrantX = (stepLength * (Math.sin(radianAngle)));
                quadrantY = (stepLength * (Math.cos(radianAngle)));
                
                quadrantXA = (correctedSLength * (Math.sin(radianAngle)));
                quadrantYA = (correctedSLength * (Math.cos(radianAngle)));              
                
                
                //accumulate the distances for each plot!   
                finalX = (quadrantX + previousX);
                finalY = (quadrantY + previousY);
                
                finalXA = (quadrantXA + previousXA);
                finalYA = (quadrantYA + previousYA); 
                
                String.format("$%,.2f", finalX);String.format("$%,.2f", finalY);String.format("$%,.2f", finalXA);String.format("$%,.2f", finalYA);
    		}
    		else
            {
                //if rawAngle is negative value, do not process it!
            }
    		
    		//Store (x,y) values to be accumulated again!
            previousX = finalX;
            previousY = finalY;
            
            previousXA = finalXA;
            previousYA = finalYA;
            
            MainActivity.write1.write(CPCheck.whichCP, CPpositioning.phase, Double.toString(GraphPlot.DWalked), Double.toString(GraphPlot.CDistance), Double.toString(stepLength), Double.toString(correctedSLength), Double.toString(GraphPlot.gyro), Double.toString(finalX), Double.toString(finalY), Double.toString(finalXA), Double.toString(finalYA));
    	}    	
    }
}
