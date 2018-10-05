package com.example.ipsdemo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	static int countPressed = 0;
	static int CLevel;
	private Button counter;
	private SensorManager mSensorManager = null;
	private Sensor senAccelerometer;
	private Sensor senGyroscope;
	private Sensor senMagnetic;
	private Sensor senLinearAccel;
    private Accelerometer asyncTask1;
    private Magnetic asyncTask2;
    private GyroScope asyncTask3;
    private SavingToFile asyncTask4;
    private StepsDetection asyncTask5;
    private WifiRSSI asyncTask6;
    private Handler handlerWrite = new Handler(); 
    private Handler handlerWifi = new Handler();  
	private String ipAddress;
	
	// angular speeds from gyro.
    private float[] gyro = new float[3];
 
    // rotation matrix from gyro data.
    private float[] gyroMatrix = new float[9];
 
    // orientation angles from gyro matrix.
    private float[] gyroOrientation = new float[3];
 
    // magnetic field vector.
    private float[] magnet = new float[3];
 
    // accelerometer vector.
    private float[] accel = new float[3];
 
    // orientation angles from accel and magnet.
    private float[] accMagOrientation = new float[3];
 
    // final orientation angles from sensor fusion
    //private float[] fusedOrientation = new float[3];
 
    // accelerometer and magnetometer based rotation matrix.
    private float[] rotationMatrix = new float[9];
    
    // gravity vector.
    private float[] linearAccel = new float[3];
	
	private int timeCount = 100;
    private int count = 0;
    private float[] compare = new float[5];
    private int step = 0;
    private int arrCount = 0;
    private int[] array = new int[1000];
    private float[] angle = new float[1000];
    private float[] timer = new float[1000];
    private int realStep = 0;
    private double stepLength = 0;
    private double diff = 0.00;
    private int stepTimerCount = 0;

    private WifiManager mainWifi;
    private List<ScanResult> wifiList;
    private int WifiLevel3 = 0;
    private int WifiLevel4 = 0;
    private int WifiLevel5 = 0;
    private int WifiLevel6 = 0;
    private int WifiLevel7 = 0;
    private int WifiLevel8 = 0;
    static double[] averagedDatabasex1 = new double[10];
    static double[] averagedDatabasey1 = new double[10];
    
    
    double s1A=0; double s1B=0; double s2A=0; double s2B=0; double s3A=0; double s3B=0; 
    double s4A=0; double s4B=0; double s5A=0; double s5B=0; double s6A=0; double s6B=0;
    
    static GraphPlot plot1;
    static WriteToFile write1;
    static HeadingCheck hdcheck1;
    static CPCheck cpcheck1;
    static CPpositioning position1;
    static coordinateCalculation cal1;
    static WifiFingerprinting wifi1;
    static MappingCP map1;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Declare the count button!
        counter = (Button) findViewById(R.id.counter);        
        // Add event listener to button!
        counter.setOnClickListener(this);
        
        Toast.makeText(this, "Application Started", Toast.LENGTH_SHORT).show();
       	
 	   //initialize variables!
	   gyroOrientation[0] = 0.0f;
       gyroOrientation[1] = 0.0f;
       gyroOrientation[2] = 0.0f;

       // initialize gyroMatrix with identity matrix!
       gyroMatrix[0] = 1.0f; gyroMatrix[1] = 0.0f; gyroMatrix[2] = 0.0f;
       gyroMatrix[3] = 0.0f; gyroMatrix[4] = 1.0f; gyroMatrix[5] = 0.0f;
       gyroMatrix[6] = 0.0f; gyroMatrix[7] = 0.0f; gyroMatrix[8] = 1.0f;     

       // Initiate wifi service manager!
       mainWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
       
       // Check for wifi is disabled!
       if (mainWifi.isWifiEnabled() == false)
       {
    	   // If wifi disabled then enable it
           Toast.makeText(getApplicationContext(), "Wifi is disabled..making it enabled", 
           Toast.LENGTH_LONG).show();
                 
           mainWifi.setWifiEnabled(true);
       }
       
       //Sensors running parallel!
       mSensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
   
       asyncTask1 = new Accelerometer();
       asyncTask1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
   	
       asyncTask2 = new Magnetic();
       asyncTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
   	
       asyncTask3 = new GyroScope();
       asyncTask3.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
   	
       asyncTask4 = new SavingToFile();
       asyncTask4.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
   	
       asyncTask5 = new StepsDetection();
       asyncTask5.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
   		
       asyncTask6 = new WifiRSSI();
       asyncTask6.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);   	
       
       plot1 = new GraphPlot();
       write1 = new WriteToFile();
       hdcheck1 = new HeadingCheck();
       cpcheck1 = new CPCheck();
       position1 = new CPpositioning();
       cal1 = new coordinateCalculation();
       wifi1 = new WifiFingerprinting();
       map1 = new MappingCP();
       
       write1.create();
       //write1.testwrite("Created");
     }

    //Event for Button Clicked Manually!
    @Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
    	if (v.getId() == R.id.counter)
    	{
    		countPressed++;   		
    	}   		
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present!
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }	
    
    //Sending to Server on another asyncTask!
    private class SendingToServer extends AsyncTask<String, Integer, Double> {

        @Override
        protected Double doInBackground(String... params) {
     	
        	postData(params[0] + "," + params[1] + "," + params[2] + "," + params[3]);
        	return null;
        }       
    }  
    
    //Send Data to Server!
    public void postData(String valueIWantToSend) {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(ipAddress + "SendDataToServer.php");

		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("myHttpData", valueIWantToSend));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request!
			HttpResponse response = httpclient.execute(httppost);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}
    
    //Send Data to Server!
    public void postData3(String valueIWantToSend) {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(ipAddress + "SendDataToServer3.php");

		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("myHttpData", valueIWantToSend));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request!
			HttpResponse response = httpclient.execute(httppost);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}
    
  //Running accelerometer sensor for Heading Orientation!
    private class Accelerometer extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params) {   	        
     	   senAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);  
     	   startLogging();
 		
     	   return null;      
        }                
    }
    
    //Running magnetic field sensor (compass) for Heading Orientation!
    private class Magnetic extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params) {   
     	   senMagnetic = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
     	   startLogging2();
        
     	   return null;  
        }
    }   
      
    //Running gyroscope sensor for Heading Orientation!
    private class GyroScope extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params) {	    	   
     	   senGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
     	   startLogging3();
     	   
     	   return null;    	   
        }
    }  
    
    //Running linear acceleration/gravity sensors for steps detection!
    private class StepsDetection extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params) {  
     	   senLinearAccel = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
           //senGravity = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        	 
     	   startLogging4();
 		
     	   return null;      
        }                
    }
  
    //Running WIFI RSSI!
    private class WifiRSSI extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params) {         	
     	   	mainWifi.startScan();

     	   	startLogging5();
 		
     	   	return null;      
        }       
        
        @Override
        protected void onPostExecute(Void result)
        {
     	   handlerWifi.postDelayed(new Runnable() {
       	        @Override
       	        public void run() {      	        	
       	        	asyncTask6 = new WifiRSSI();
       	        	asyncTask6.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); 
       	        }
       	    }, 50);
        }
    }
    
    private void startLogging5(){
    	wifiList = mainWifi.getScanResults();
    	    	
    	for(ScanResult results : wifiList) {
            Log.d("SSID  result", results.SSID);
            
            //CorrectionPoints RSSI values.
            if(results.SSID.equals("AP1"))
            {
            	WifiLevel3 = results.level;
            }
            
            if(results.SSID.equals("AP2"))
            {
            	WifiLevel4 = results.level;
            }
            
            if(results.SSID.equals("edison1"))
            {
            	WifiLevel5 = results.level;
            }
            
            if(results.SSID.equals("edison2"))
            {
            	WifiLevel6 = results.level;
            }
            
            if(results.SSID.equals("edison3"))
            {
            	WifiLevel7 = results.level;
            }
            
            if(results.SSID.equals("edison4"))
            {
            	WifiLevel8 = results.level;
            }       
    	}  	
    }
      
    //Saving to File (via server) automatically every 50ms - wireless!
    private class SavingToFile extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params) {   	   
     	   //Determining +ve and -ve peaks for steps count. (ONLY FOR HOLDING ON HAND)!
       	    if(count == 0)
       	    {
       	    	compare[0] = linearAccel[2];
       	    	count++;
       	    }
       	    else if(count == 1)
       	    {
       	    	compare[1] = linearAccel[2];
       	    	count++;
       	    }
       	    else if(count == 2)
       	    {
       	    	compare[2] = linearAccel[2];
       	    	count++;
       	    }
       	    else
       	    {
       	    	//When array is full with values, always replace them when new values came in!
       	    	if(count != 3)
       	    	{
       	    		compare[0] = compare[1];
       	    		compare[1] = compare[2];
       	    		compare[2] = linearAccel[2];
       	    	}
       	    	
       	    	//To detect Peak!
       	    	if((compare[1] > compare[0]) && (compare[1] >= compare[2]) && (compare[1] > 1.0) && (compare[1] < 10.0))
       	    	{
       	    		step = 10;  	    		
       	    	}
       	    	//To detect Valley!
       	    	else if((compare[1] < compare[0]) && (compare[1] <= compare[2]) && (compare[1] < -1.1) && (compare[1] > -6.5))
    	    	{	    		
        			step = 7;    		
    	    	}
       	    	else
       	    	{
       	    		step = 0;
       	    	}
       	    	count++;
       	    }
    	    	
       	    //Determine real valid step!(eliminating other unwanted peaks)
       	    if((step == 7) || (step == 10))
       	    {
       	    	if(arrCount == 0)
       	    	{
       	    		array[0] = step;
       	    		angle[0] = linearAccel[2];
       	    		timer[0] = timeCount;
       	    		arrCount++;
       	    	}
       	    	else
       	    	{
       	    		if(step == array[arrCount-1])
       	    		{
       	    			array[arrCount-1] = step;
       	    			angle[arrCount-1] = linearAccel[2];
       	    			timer[arrCount-1] = timeCount;
       	    		}
       	    		else
       	    		{
       	    			array[arrCount] = step;
       	    			angle[arrCount] = linearAccel[2];
       	    			timer[arrCount] = timeCount;
       	    			arrCount++;
       	    			
       	    			//For calculating step lengths! 
       	    			//If consecutive peak valley detected, Calculate step length for True Step!
       	    			if(array[arrCount-2] == 10 && array[arrCount-1] == 7)
       	    			{
       	    				//For 1st time!
       	    				if(stepTimerCount == 0)
       	    				{
       	    					realStep++;
       	    					double x = Math.abs(angle[arrCount-2]);
       	    					double y = Math.abs(angle[arrCount-1]);
       	    					diff = Math.abs(x - y);
       	    					double firstSqrt = Math.sqrt(diff);
       	    					double secondSqrt =  Math.sqrt(firstSqrt);
       	    					
       	    					stepLength = 61 * secondSqrt;
       	    					
       	    					plot1.setWifiLevel(WifiLevel3, WifiLevel4, WifiLevel5, WifiLevel6, WifiLevel7, WifiLevel8);
       	    					plot1.setVariables(realStep, stepLength, gyroOrientation[0]);
       	    					CLevel = plot1.process(CLevel);
       	    				    
       	    					//Convert to String before send to server!       		
       	    		       		String value0 = Double.toString(coordinateCalculation.finalX);
       	    			    	String value1 = Double.toString(coordinateCalculation.finalY);
       	    			    	String value2 = Double.toString(coordinateCalculation.finalXA);
       	    			    	String value3 = Double.toString(coordinateCalculation.finalYA);	
       	    			    	new SendingToServer().execute(value0, value1, value2, value3);   	    				
       	    					
       	    					stepTimerCount++;
       	    				}
       	    				//Subsequently!
       	    				else
       	    				{
       	    					realStep++;;
       	    					double x = Math.abs(angle[arrCount-2]);
       	    					double y = Math.abs(angle[arrCount-1]);
       	    					diff = Math.abs(x - y);
       	    					double firstSqrt = Math.sqrt(diff);
       	    					double secondSqrt =  Math.sqrt(firstSqrt);
       	    					
       	    					stepLength = 61 * secondSqrt;  

       	    					plot1.setWifiLevel(WifiLevel3, WifiLevel4, WifiLevel5, WifiLevel6, WifiLevel7, WifiLevel8);
       	    					plot1.setVariables(realStep, stepLength, gyroOrientation[0]); 
       	    					CLevel = plot1.process(CLevel);
       	    					      	    					
       	    					//Convert to String before send to server!       		
       	    		       		String value0 = Double.toString(coordinateCalculation.finalX);
       	    			    	String value1 = Double.toString(coordinateCalculation.finalY);
       	    			    	String value2 = Double.toString(coordinateCalculation.finalXA);
       	    			    	String value3 = Double.toString(coordinateCalculation.finalYA);	
       	    			    	new SendingToServer().execute(value0, value1, value2, value3);    	    				
       	    				}       	    				
       	    			}
       	    			else
       	    			{
       	    				stepLength = 0;
       	    			}
       	    		}
       	    	}
       	    }
       	    else
       	    {
       	    	stepLength = 0;
       	    }

       	    
	    	
    		//String ts = new SimpleDateFormat("HH:mm:ss:SSS").format(Calendar.getInstance().getTime());  	    
    	    timeCount = timeCount + 50;
     	   
    	    return null;
        }
        
        @Override
        protected void onPostExecute(Void result)
        {
     	   handlerWrite.postDelayed(new Runnable() {
       	        @Override
       	        public void run() {      	        	
       	        	asyncTask4 = new SavingToFile();
       	        	asyncTask4.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); 
       	        }
       	    }, 50);
        }
    }
    
    //Get values from linearAcceleration/Gravity sensor for Steps detection!
    private void startLogging4(){

 	   mSensorManager.registerListener(new SensorEventListener() {
 		   @Override
 		   public void onSensorChanged(SensorEvent sensorEvent) {
 			   	// copy new linearAccel data into linearAccel array!
 	            System.arraycopy(sensorEvent.values, 0, linearAccel, 0, 3);	           
 		   }
 		   
 		   @Override
 		   public void onAccuracyChanged(Sensor sensor, int i) {}
 		   
 	   }, senLinearAccel, SensorManager.SENSOR_DELAY_FASTEST);
    }
    
    //Get values from Accelerometer!
    private void startLogging(){

 	   mSensorManager.registerListener(new SensorEventListener() {
 		   @Override
 		   public void onSensorChanged(SensorEvent sensorEvent) {
 			   	// copy new accelerometer data into accel array
 	            // then calculate new orientation!
 	            System.arraycopy(sensorEvent.values, 0, accel, 0, 3);
 	            /*System.arraycopy(sensorEvent.values, 0, linearAccel, 0, 3);
 	            
 	            linearAccel[0] = (float) (linearAccel[0] - 9.81);
 	            linearAccel[1] = (float) (linearAccel[1] - 9.81);
 	            linearAccel[2] = (float) (linearAccel[2] - 9.81);*/
 	            calculateAccMagOrientation();
 		   }
 		   
 		   @Override
 		   public void onAccuracyChanged(Sensor sensor, int i) {}
 		   
 	   }, senAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
    }
    
    public void calculateAccMagOrientation() {
 	    if(SensorManager.getRotationMatrix(rotationMatrix, null, accel, magnet)) {
 	        SensorManager.getOrientation(rotationMatrix, accMagOrientation);
 	    }	   	   
 	    
 	}
    
  //Get values from MagneticField!
    private void startLogging2() {
 	   mSensorManager.registerListener(new SensorEventListener() {
 		   @Override
 		   public void onSensorChanged(SensorEvent sensorEvent) {                               				   
 			   // copy new magnetometer data into magnet array!
 	            System.arraycopy(sensorEvent.values, 0, magnet, 0, 3);
 		   }
 		   
 		   @Override
 		   public void onAccuracyChanged(Sensor sensor, int i) {}
 		   
 	   }, senMagnetic, SensorManager.SENSOR_DELAY_FASTEST);
    }
    
    //Get values for Gyroscope then sensor fusion!
    private void startLogging3(){
 	   mSensorManager.registerListener(new SensorEventListener() {
 		   @Override
 		   public void onSensorChanged(SensorEvent sensorEvent) {                               
 			   // process gyroscope data!
 	           gyroFunction(sensorEvent);
 	            
 		   }
 		   
 		   @Override
 		   public void onAccuracyChanged(Sensor sensor, int i) {}
 		   
 	   }, senGyroscope, SensorManager.SENSOR_DELAY_FASTEST);
    }
    
    public static final float EPSILON = 0.000000001f;
 	 
 	private void getRotationVectorFromGyro(float[] gyroValues, float[] deltaRotationVector, float timeFactor)
 	{
 	    float[] normValues = new float[3];
 	 
 	    // Calculate the angular speed of the sample!
 	    float omegaMagnitude = (float)Math.sqrt(gyroValues[0] * gyroValues[0] + gyroValues[1] * gyroValues[1] + gyroValues[2] * gyroValues[2]);
 	 
 	    // Normalize the rotation vector if it's big enough to get the axis!
 	    if(omegaMagnitude > EPSILON) {
 	        normValues[0] = gyroValues[0] / omegaMagnitude;
 	        normValues[1] = gyroValues[1] / omegaMagnitude;
 	        normValues[2] = gyroValues[2] / omegaMagnitude;
 	    }
 	 
 	    // Integrate around this axis with the angular speed by the timestep
 	    // in order to get a delta rotation from this sample over the timestep
 	    // We will convert this axis-angle representation of the delta rotation
 	    // into a quaternion before turning it into the rotation matrix.
 	    float thetaOverTwo = omegaMagnitude * timeFactor;
 	    float sinThetaOverTwo = (float)Math.sin(thetaOverTwo);
 	    float cosThetaOverTwo = (float)Math.cos(thetaOverTwo);
 	    deltaRotationVector[0] = sinThetaOverTwo * normValues[0];
 	    deltaRotationVector[1] = sinThetaOverTwo * normValues[1];
 	    deltaRotationVector[2] = sinThetaOverTwo * normValues[2];
 	    deltaRotationVector[3] = cosThetaOverTwo;
 	} 
 	
 	private static final float NS2S = 1.0f / 1000000000.0f;
 	private float timestamp;
 	private boolean initState = true;
 	 
 	public void gyroFunction(SensorEvent event) {
 	    // don't start until first accelerometer/magnetometer orientation has been acquired!
 	    if (accMagOrientation == null)
 	        return;
 	 
 	    // initialization of the gyroscope based rotation matrix!
 	    if(initState) {
 	        float[] initMatrix = new float[9];
 	        initMatrix = getRotationMatrixFromOrientation(accMagOrientation);
 	        float[] test = new float[3];
 	        SensorManager.getOrientation(initMatrix, test);
 	        gyroMatrix = matrixMultiplication(gyroMatrix, initMatrix);
 	        initState = false;
 	    }
 	 
 	    // copy the new gyro values into the gyro array!
 	    // convert the raw gyro data into a rotation vector!
 	    float[] deltaVector = new float[4];
 	    if(timestamp != 0) {
 	        final float dT = (event.timestamp - timestamp) * NS2S;
 	        System.arraycopy(event.values, 0, gyro, 0, 3);
 	        getRotationVectorFromGyro(gyro, deltaVector, dT / 2.0f);
 	    }
 	 
 	    // measurement done, save current time for next interval!
 	    timestamp = event.timestamp;
 	 
 	    // convert rotation vector into rotation matrix!
 	    float[] deltaMatrix = new float[9];
 	    SensorManager.getRotationMatrixFromVector(deltaMatrix, deltaVector);
 	 
 	    // apply the new rotation interval on the gyroscope based rotation matrix!
 	    gyroMatrix = matrixMultiplication(gyroMatrix, deltaMatrix);
 	 
 	    // get the gyroscope based orientation from the rotation matrix!
 	    SensorManager.getOrientation(gyroMatrix, gyroOrientation);
 	    
 	    gyroOrientation[0] = (float)Math.toDegrees(gyroOrientation[0]);
 	    gyroOrientation[1] = (float)Math.toDegrees(gyroOrientation[1]);
 	    gyroOrientation[2] = (float)Math.toDegrees(gyroOrientation[2]);	    
 	    
 	    //float newRange = 180;
 	    //float oldRange = -180;
 	    
 	    //Converting gyroscope values into 360 degrees!
 	    if(gyroOrientation[0] < 0)
 	    {
 	    	gyroOrientation[0] = gyroOrientation[0] + 360;
 	    }
 	    if(gyroOrientation[1] < 0)
 	    {
 	    	gyroOrientation[1] = gyroOrientation[1] + 360;
 	    }	
 	    if(gyroOrientation[2] < 0)
 	    {
 	    	gyroOrientation[2] = gyroOrientation[2] + 360;
 	    }	
 	}  
 	
 	private float[] getRotationMatrixFromOrientation(float[] o) {
 	    float[] xM = new float[9];
 	    float[] yM = new float[9];
 	    float[] zM = new float[9];
 	 
 	    float sinX = (float)Math.sin(o[1]);
 	    float cosX = (float)Math.cos(o[1]);
 	    float sinY = (float)Math.sin(o[2]);
 	    float cosY = (float)Math.cos(o[2]);
 	    float sinZ = (float)Math.sin(o[0]);
 	    float cosZ = (float)Math.cos(o[0]);
 	 
 	    // rotation about x-axis (pitch)
 	    xM[0] = 1.0f; xM[1] = 0.0f; xM[2] = 0.0f;
 	    xM[3] = 0.0f; xM[4] = cosX; xM[5] = sinX;
 	    xM[6] = 0.0f; xM[7] = -sinX; xM[8] = cosX;
 	 
 	    // rotation about y-axis (roll)
 	    yM[0] = cosY; yM[1] = 0.0f; yM[2] = sinY;
 	    yM[3] = 0.0f; yM[4] = 1.0f; yM[5] = 0.0f;
 	    yM[6] = -sinY; yM[7] = 0.0f; yM[8] = cosY;
 	 
 	    // rotation about z-axis (azimuth)
 	    zM[0] = cosZ; zM[1] = sinZ; zM[2] = 0.0f;
 	    zM[3] = -sinZ; zM[4] = cosZ; zM[5] = 0.0f;
 	    zM[6] = 0.0f; zM[7] = 0.0f; zM[8] = 1.0f;
 	 
 	    // rotation order is y, x, z (roll, pitch, azimuth)
 	    float[] resultMatrix = matrixMultiplication(xM, yM);
 	    resultMatrix = matrixMultiplication(zM, resultMatrix);
 	    return resultMatrix;
 	}
 	
 	private float[] matrixMultiplication(float[] A, float[] B) {
 	    float[] result = new float[9];
 	 
 	    result[0] = A[0] * B[0] + A[1] * B[3] + A[2] * B[6];
 	    result[1] = A[0] * B[1] + A[1] * B[4] + A[2] * B[7];
 	    result[2] = A[0] * B[2] + A[1] * B[5] + A[2] * B[8];
 	 
 	    result[3] = A[3] * B[0] + A[4] * B[3] + A[5] * B[6];
 	    result[4] = A[3] * B[1] + A[4] * B[4] + A[5] * B[7];
 	    result[5] = A[3] * B[2] + A[4] * B[5] + A[5] * B[8];
 	 
 	    result[6] = A[6] * B[0] + A[7] * B[3] + A[8] * B[6];
 	    result[7] = A[6] * B[1] + A[7] * B[4] + A[8] * B[7];
 	    result[8] = A[6] * B[2] + A[7] * B[5] + A[8] * B[8];
 	 
 	    return result;
 	}	
}