package com.example.ipsdemo;

public class MappingCP {

    double degToRad = Math.PI/180;
    double cp3Heading = 0.00; double cp4Heading = 0.00; double cp1Heading = 0.00; double cp2Heading = 0.00;
    static double cp1positionX = 0.00; static double cp1positionY = 0.00; static double cp2positionX = 0.00; static double cp2positionY = 0.00;
    static double cp3positionX = 0.00; static double cp3positionY = 0.00; static double cp4positionX = 0.00; static double cp4positionY = 0.00;
    
	// This is the constructor of the class
    public MappingCP(){}
    
    public void CPMap()
    {
    	if(CPCheck.whichCP == "3")
    	{
    		//cp3&cp4 heading remain the same.
       		cp3Heading=GraphPlot.gyro;
       			
       		//cp1 and cp2 same heading.
       		cp1Heading = cp3Heading + 85;
       		cp2Heading = cp3Heading + 185;
       		
       		if(cp1Heading > 360)
       		{
       			cp1Heading = cp1Heading-360;
       		}
       		if(cp2Heading > 360)
       		{
       			cp2Heading = cp2Heading-360;
       		}
       		
       		cp3positionX=(GraphPlot.DWalked * (Math.sin(cp3Heading*degToRad)));
       		cp3positionY=(GraphPlot.DWalked * (Math.cos(cp3Heading*degToRad)));
       			
       		cp4positionX=(1000 * (Math.sin(cp3Heading*degToRad)));
       		cp4positionY=(1000 * (Math.cos(cp3Heading*degToRad)));
       			
       		cp1positionX=((5000*(Math.sin(cp3Heading*degToRad))) + (500*(Math.sin(cp1Heading*degToRad))) + (100*(Math.cos((cp2Heading)*degToRad))));
       		cp1positionY=((5000*(Math.cos(cp3Heading*degToRad))) + (500*(Math.cos(cp1Heading*degToRad))) + (100*(Math.cos((cp2Heading)*degToRad))));
       			
       		cp2positionX=((5000*(Math.sin(cp3Heading*degToRad))) + (500*(Math.sin(cp1Heading*degToRad))) + (1100*(Math.sin(cp2Heading)*degToRad)));
       		cp2positionY=((5000*(Math.cos(cp3Heading*degToRad))) + (500*(Math.cos(cp1Heading*degToRad))) + (1100*(Math.cos(cp2Heading)*degToRad)));
       		
       		GraphPlot.CDistance = 0.00;
       		MainActivity.write1.write(CPCheck.whichCP, CPpositioning.phase, Double.toString(cp3positionX), Double.toString(cp3positionY), Double.toString(cp4positionX), Double.toString(cp4positionY), Double.toString(cp1positionX), Double.toString(cp1positionY), Double.toString(cp2positionX), Double.toString(cp2positionY), Double.toString(GraphPlot.gyro));
    	}
    	else if(CPCheck.whichCP == "4")
    	{
    		//cp3&cp4 heading remain the same.
       		cp3Heading=GraphPlot.gyro;
       			
       		//cp1 and cp2 same heading.
       		cp1Heading = cp3Heading - 85;
       		cp2Heading = cp3Heading - 185;
       		
       		if(cp1Heading < 0)
       		{
       			cp1Heading = cp1Heading+360;
       		}
       		if(cp2Heading < 0)
       		{
       			cp2Heading = cp2Heading+360;
       		}
       		
       		cp4positionX=(GraphPlot.DWalked * (Math.sin(cp3Heading*degToRad)));
       		cp4positionY=(GraphPlot.DWalked * (Math.cos(cp3Heading*degToRad)));
       		
       		cp3positionX=(1000 * (Math.sin(cp3Heading*degToRad)));
       		cp3positionY=(1000 * (Math.cos(cp3Heading*degToRad)));
 			
       		cp1positionX=((5000*(Math.sin(cp2Heading*degToRad))) + (1000*(Math.sin(cp1Heading*degToRad))) + (1100*(Math.cos((cp3Heading)*degToRad))));
       		cp1positionY=((5000*(Math.cos(cp2Heading*degToRad))) + (1000*(Math.cos(cp1Heading*degToRad))) + (1100*(Math.cos((cp3Heading)*degToRad))));
       			
       		cp2positionX=((4000*(Math.sin(cp3Heading*degToRad))) + (1000*(Math.sin(cp1Heading*degToRad))) + (1100*(Math.sin(cp2Heading)*degToRad))) + cp4positionX;
       		cp2positionY=((4000*(Math.cos(cp3Heading*degToRad))) + (1000*(Math.cos(cp1Heading*degToRad))) + (1100*(Math.cos(cp2Heading)*degToRad))) + cp4positionY;
       		
       		GraphPlot.CDistance = 0.00;
       		MainActivity.write1.write(CPCheck.whichCP, CPpositioning.phase, Double.toString(cp3positionX), Double.toString(cp3positionY), Double.toString(cp4positionX), Double.toString(cp4positionY), Double.toString(cp1positionX), Double.toString(cp1positionY), Double.toString(cp2positionX), Double.toString(cp2positionY), "");
    	}
    	else if(CPCheck.whichCP == "1")
    	{
    		//cp3&cp4 heading remain the same.
       		cp1Heading=GraphPlot.gyro;
       			
       		//cp1 and cp2 same heading.
       		cp3Heading = cp3Heading + 85;
       		cp4Heading = cp3Heading + 185;
       		
       		if(cp3Heading > 360)
       		{
       			cp3Heading = cp3Heading-360;
       		}
       		if(cp4Heading > 360)
       		{
       			cp4Heading = cp4Heading-360;
       		}
       		
       		cp1positionX=(GraphPlot.DWalked * (Math.sin(cp1Heading*degToRad)));
       		cp1positionY=(GraphPlot.DWalked * (Math.cos(cp1Heading*degToRad)));
       		
       		cp2positionX=(1000 * (Math.sin(cp1Heading*degToRad)));
       		cp2positionY=(1000 * (Math.cos(cp1Heading*degToRad)));
 			
       		cp3positionX=((5000*(Math.sin(cp1Heading*degToRad))) + (1000*(Math.sin(cp3Heading*degToRad))) + (100*(Math.cos((cp4Heading)*degToRad))));
       		cp3positionY=((5000*(Math.cos(cp1Heading*degToRad))) + (1000*(Math.cos(cp3Heading*degToRad))) + (100*(Math.cos((cp4Heading)*degToRad))));
       			
       		cp4positionX=((5000*(Math.sin(cp1Heading*degToRad))) + (1000*(Math.sin(cp3Heading*degToRad))) + (1100*(Math.sin(cp4Heading)*degToRad))) + cp2positionX;
       		cp4positionY=((5000*(Math.cos(cp1Heading*degToRad))) + (1000*(Math.cos(cp3Heading*degToRad))) + (1100*(Math.cos(cp4Heading)*degToRad))) + cp2positionY;
       		
       		GraphPlot.CDistance = 0.00;
       		MainActivity.write1.write(CPCheck.whichCP, CPpositioning.phase, Double.toString(cp3positionX), Double.toString(cp3positionY), Double.toString(cp4positionX), Double.toString(cp4positionY), Double.toString(cp1positionX), Double.toString(cp1positionY), Double.toString(cp2positionX), Double.toString(cp2positionY), "");
    	}
    	else if(CPCheck.whichCP == "2")
    	{
    		//cp3&cp4 heading remain the same.
       		cp1Heading=GraphPlot.gyro;
       			
       		//cp1 and cp2 same heading.
       		cp3Heading = cp3Heading - 85;
       		cp4Heading = cp3Heading - 185;
       		
       		if(cp3Heading < 0)
       		{
       			cp3Heading = cp3Heading+360;
       		}
       		if(cp4Heading < 0)
       		{
       			cp4Heading = cp4Heading+360;
       		}
       		
       		cp2positionX=(GraphPlot.DWalked * (Math.sin(cp1Heading*degToRad)));
       		cp2positionY=(GraphPlot.DWalked * (Math.cos(cp1Heading*degToRad)));
       		
       		cp1positionX=(1000 * (Math.sin(cp1Heading*degToRad)));
       		cp1positionY=(1000 * (Math.cos(cp1Heading*degToRad)));
 			
       		cp3positionX=((5000*(Math.sin(cp4Heading*degToRad))) + (500*(Math.sin(cp3Heading*degToRad))) + (1100*(Math.cos((cp1Heading)*degToRad))));
       		cp3positionY=((5000*(Math.cos(cp4Heading*degToRad))) + (500*(Math.cos(cp3Heading*degToRad))) + (1100*(Math.cos((cp1Heading)*degToRad))));
       			
       		cp4positionX=((4000*(Math.sin(cp4Heading*degToRad))) + (500*(Math.sin(cp3Heading*degToRad))) + (1100*(Math.sin(cp1Heading)*degToRad)));
       		cp4positionY=((4000*(Math.cos(cp4Heading*degToRad))) + (500*(Math.cos(cp3Heading*degToRad))) + (1100*(Math.cos(cp1Heading)*degToRad)));
       		
       		GraphPlot.CDistance = 0.00;
       		MainActivity.write1.write(CPCheck.whichCP, CPpositioning.phase, Double.toString(cp3positionX), Double.toString(cp3positionY), Double.toString(cp4positionX), Double.toString(cp4positionY), Double.toString(cp1positionX), Double.toString(cp1positionY), Double.toString(cp2positionX), Double.toString(cp2positionY), "");
    	}
    }
}
