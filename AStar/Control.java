
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;
import java.util.ArrayList;
import java.util.List;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.device.LDCMotor;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.robotics.SampleProvider;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class Control {
	
	public static final int STEP = 24;
	
	Move move = new Move();
	
	//ONE
    public static final int[][] NODESLL = {   		
        	//WITH ALL FOUR CHANGING OBSTACLES 
        	   //                            M
    		{ 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1 },
    		{ 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1 },
    		{ 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1 },
    		{ 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1 },
    		{ 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 },
    		{ 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
    		{ 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 },
    		{ 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
    		{ 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 },
    		{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0 },
    		{ 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0 },
    		{ 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0 },
    		{ 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0 },
    		{ 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0 },
    		{ 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 },
               //                            M
            };
    
    public static final int[][] NODESLR = {
    		//WITH ALL FOUR CHANGING OBSTACLES
//    		                            M
    		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1 },
    		{ 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1 },
    		{ 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1 },
    		{ 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1 },
    		{ 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1 },
    		{ 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
    		{ 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 },
    		{ 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
    		{ 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 },
    		{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0 },
    		{ 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0 },
    		{ 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0 },
    		{ 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0 },
    		{ 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0 },
    		{ 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 },
    		//start point(18,5)
    		//end point(2,14)
    		};
    
    //THREE
    public static final int[][] NODESRL = {   		
        	//WITH ALL FOUR CHANGING OBSTACLES 
        	   //                            M
    		{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1 },
    		{ 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1 },
    		{ 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1 },
    		{ 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1 },
    		{ 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1 },
    		{ 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
    		{ 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 },
    		{ 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
    		{ 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0 },
    		{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0 },
    		{ 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0 },
    		{ 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    		{ 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    		{ 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    		{ 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            };
    
    //FOUR
    public static final int[][] NODESRR = {   		
        	//WITH ALL FOUR CHANGING OBSTACLES 
        	   //                            M
    		{ 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1 },
    		{ 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1 },
    		{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1 },
    		{ 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1 },
    		{ 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 },
    		{ 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
    		{ 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1 },
    		{ 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1 },
    		{ 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    		{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    		{ 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1 },
    		{ 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1 },
    		{ 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1 },
    		{ 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1 },
    		{ 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1 },
               //                            M
            };
	
	float[] rgb = new float[3];
	
	static EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S1);
	
	
	int locationCount = 0;
	
	int redCountOne = 0;
	int whiteCountOne = 0;
	
	int redCountTwo = 0;
	int whiteCountTwo = 0;
	
	int totalCount = 0;
	
	boolean redCountUpdated = false;
	boolean whiteCountUpdated = false;
	boolean flag = false;
	
		
	boolean patternFound = false;
	int patternCount =0;

	int red = 0;
	int white = 0;

	public void setRed(int value) {
		red = value;
	}
	public int getRed() {
		return red;
	}
	
	public boolean senseRed(){
		if(getAverageValue() < (getRed()+10) && getAverageValue() > (getRed()-10)){
			return true;
		}
		return false;

	}
	
	public void setWhite(int value) {
		white = value;
	}
	public int getWhite() {
		return white;
	}
	
	public boolean senseWhite(){
		if(getAverageValue() < (getWhite()+20) && getAverageValue() > (getWhite()-20)){
			return true;
		}
		return false;

	}
	
	public static int getAverageValue(){
		float[] rgb = new float[3];

		int sum = 0;
		
		for (int i = 0; i < 3; i++) {
			colorSensor.getRGBMode().fetchSample(rgb, 0);
			sum += (rgb[0] + rgb[1] + rgb[2]) * 100;
		}
		
		return sum / 3;
	}

	void moveTwocmForward() {
		Motor.A.setSpeed(101);
		Motor.D.setSpeed(99);
		Motor.A.rotate(42,true);
		Motor.D.rotate(42,true);
		locationCount -=1;
	}
	
	void moveTwocmBackward() {
		Motor.A.setSpeed(100);
		Motor.D.setSpeed(100);
		Motor.A.rotate(-42,true);
		Motor.D.rotate(-42,true);
		locationCount +=1;
	}	

	 int Localization(){
		 
		 //Backtrack to prev color
		 while(senseRed()){
			 moveTwocmBackward();
			 Delay.msDelay(1700);
			 flag = true;
		 }
		 
		 while(senseWhite() && !flag){
			 moveTwocmBackward();
			 Delay.msDelay(1700);
		 }
		 
		 while(!patternFound){
			 
			 	 
			 
			 moveTwocmForward();
			 Delay.msDelay(2000);
			 
				if(senseRed()){
					redCountUpdated = false;
					while(!whiteCountUpdated){
						whiteCountTwo = whiteCountOne;
						whiteCountOne = 0;
						whiteCountUpdated = true;

					}
					
					redCountOne += 1;
					
				 }else if(senseWhite()){
					 whiteCountUpdated = false;
					 while(!redCountUpdated){
						redCountTwo = redCountOne;
						redCountOne = 0;
						redCountUpdated = true;
					}
					 whiteCountOne += 1;
					
				 }
				
				System.out.println(whiteCountOne + " " + whiteCountTwo  + " " + redCountOne + " "+ redCountTwo);
				
				
				if(whiteCountOne == 3){
					 patternCount = 1; //(Coordinates  = 0.52, 0.52)
					 patternFound = true;
					
				 }
				 
				else if(redCountOne == 3 && whiteCountTwo == 1 && redCountTwo == 3) {
					 patternCount = 2; //(Coordinates  = 0.41, 0.41)
					 patternFound = true;
				 }
				
				//make it go back to coordinate (0.305, 0.305)
			 
		 }
		 
		 System.out.println(patternCount);
		 
		 if(patternCount == 1){
				for(int i =0;i<15;i++){
					Delay.msDelay(1000);
					moveTwocmBackward();
				}
			}else if(patternCount == 2){
				for(int i =0;i<13;i++){
					Delay.msDelay(1000);
					moveTwocmBackward();
				}
			}

		 return locationCount;
	 }
	 
	    private ArrayList<Node> openList = new ArrayList<Node>();
	    private ArrayList<Node> closeList = new ArrayList<Node>();

	    public Node findMinFNodeInOpneList() {
	        Node tempNode = openList.get(0);
	        for (Node node : openList) {
	            if (node.F < tempNode.F) {
	                tempNode = node;
	            }
	        }
	        return tempNode;
	    }

	    public ArrayList<Node> findNeighborNodes(Node currentNode, int[][] nodes) {
	        ArrayList<Node> arrayList = new ArrayList<Node>();
	        int upX = currentNode.x;
	        int upY = currentNode.y + 1;
	        if (canReach(upX, upY, nodes) && !exists(closeList, upX, upY)) {
	            arrayList.add(new Node(upX, upY));
	        }
	        int downX = currentNode.x;
	        int downY = currentNode.y - 1;
	        if (canReach(downX, downY, nodes) && !exists(closeList, downX, downY)) {
	            arrayList.add(new Node(downX, downY));
	        }
	        int leftX = currentNode.x - 1;
	        int leftY = currentNode.y;
	        if (canReach(leftX, leftY, nodes) && !exists(closeList, leftX, leftY)) {
	            arrayList.add(new Node(leftX, leftY));
	        }
	        int rightX = currentNode.x + 1;
	        int rightY = currentNode.y;
	        if (canReach(rightX, rightY, nodes) && !exists(closeList, rightX, rightY)) {
	            arrayList.add(new Node(rightX, rightY));
	        }
	        
//	        int up_left_X = currentNode.x - 1;
//	        int up_left_Y = currentNode.y + 1;
//	        if (canReach(up_left_X, up_left_Y, nodes) && !exists(closeList, up_left_X, up_left_Y)) {
//	            arrayList.add(new Node(up_left_X, up_left_Y));
//	        }
//	        
//	        int up_right_X = currentNode.x + 1;
//	        int up_right_Y = currentNode.y + 1;
//	        if (canReach(up_right_X, up_right_Y, nodes) && !exists(closeList, up_right_X, up_right_Y)) {
//	            arrayList.add(new Node(up_right_X, up_right_Y));
//	        }
//	        
//	        int down_right_X = currentNode.x + 1;
//	        int down_right_Y = currentNode.y - 1;
//	        if (canReach(down_right_X, down_right_Y, nodes) && !exists(closeList, down_right_X, down_right_Y)) {
//	            arrayList.add(new Node(down_right_X, down_right_Y));
//	        }
//	        
//	        int down_left_X = currentNode.x - 1;
//	        int down_left_Y = currentNode.y - 1;
//	        if (canReach(down_left_X, down_left_Y, nodes) && !exists(closeList, down_left_X, down_left_Y)) {
//	            arrayList.add(new Node(down_left_X, down_left_Y));
//	        }
	        
	        return arrayList;
	    }

	    public boolean canReach(int x, int y, int[][] nodes) {
	        if (x >= 0 && x < nodes.length && y >= 0 && y < nodes[0].length) {
	            return nodes[x][y] == 0;
	        }
	        return false;
	    }

	    public Node findPath(Node startNode, Node endNode, int[][] nodes) {

	        // add the start point open list
	        openList.add(startNode);

	        while (openList.size() > 0) {
	            // go through the open list to find NODE with the smallest F
	            Node currentNode = findMinFNodeInOpneList();
	            // remove it from open list
	            openList.remove(currentNode);
	            // add it to close list
	            closeList.add(currentNode);

	            ArrayList<Node> neighborNodes = findNeighborNodes(currentNode, nodes);
	            for (Node node : neighborNodes) {
	                if (exists(openList, node)) {
	                    foundPoint(currentNode, endNode, node);
	                } else {
	                    notFoundPoint(currentNode, endNode, node);
	                }
	            }
	            if (find(openList, endNode) != null) {
	                return find(openList, endNode);
	            }
	        }

	        return find(openList, endNode);
	    }
	    
	    private static ArrayList<Node> outputThePath(Node parent) {
	    		ArrayList<Node> arrayList = new ArrayList<Node>();

	        while (parent != null) {
	            arrayList.add(new Node(parent.x, parent.y));
	            parent = parent.parent;
	        }
	        
	        return arrayList;
	    }
	    
	    private void foundPoint(Node tempStart, Node end, Node node) {
	        int G = calcG(tempStart, node);
	        int H = calcH(end, node);
	        
	        if (G < node.G) {
	            node.parent = tempStart;
	            node.G = G;
	            node.calcF();
	        }
	    }

	    private void notFoundPoint(Node tempStart, Node end, Node node) {
	        node.parent = tempStart;
	        node.G = calcG(tempStart, node);
	        node.H = calcH(end, node);
	        node.calcF();
	        openList.add(node);
	    }

//	    private int calcG(Node start, Node node) {
//	    		int diffX = (int) Math.pow((node.x-start.x), 2);
//			int diffY = (int) Math.pow((node.y-start.y), 2);
//			int gcost = (int) Math.sqrt(diffX + diffY);
//			return gcost;
//	    }
//
//	    private int calcH(Node end, Node node) {
//	    		int diffX = (int) Math.pow((node.x-end.x), 2);
//			int diffY = (int) Math.pow((node.y-end.y), 2);
//			int hCost = (int) Math.sqrt(diffX + diffY);
//			return hCost;
//	    }
	    
	    private int calcG(Node start, Node node) {
	        int G = STEP;
	        int parentG = node.parent != null ? node.parent.G : 0;
	        return G + parentG;
	    }

	    private int calcH(Node end, Node node) {
	        int step = Math.abs(node.x - end.x) + Math.abs(node.y - end.y);
	        return step * STEP;
	    }
	    
	    public static Node find(List<Node> nodes, Node point) {
	        for (Node n : nodes)
	            if ((n.x == point.x) && (n.y == point.y)) {
	                return n;
	            }
	        return null;
	    }

	    public static boolean exists(List<Node> nodes, Node node) {
	        for (Node n : nodes) {
	            if ((n.x == node.x) && (n.y == node.y)) {
	                return true;
	            }
	        }
	        return false;
	    }

	    public static boolean exists(List<Node> nodes, int x, int y) {
	        for (Node n : nodes) {
	            if ((n.x == x) && (n.y == y)) {
	                return true;
	            }
	        }
	        return false;
	    }
	   
	    public  void AStarStart(Node startNode, Node endNode, int[][] Nodes) {
			
			
	    	Delay.msDelay(500);
	        
	        Node parent = findPath(startNode, endNode, Nodes);
	       
	        ArrayList<Node> path1 = outputThePath(parent);
	        
	        ArrayList<Node> orderedPath = new ArrayList<Node>();       
	        
	        orderedPath.clear();
	        
	        for(int i = path1.size()-1; i > -1 ; i--){
	        	orderedPath.add(path1.get(i));
	        }
	       
	        move.followGivenPath(orderedPath); 
	        
	    }
	    
       public  void AStarStartAgain(Node startNode, Node endNode, int[][] Nodes) {
			openList.clear();
			closeList.clear();
	        
	        Node parent = findPath(startNode, endNode, Nodes);
	       
	        ArrayList<Node> path1 = outputThePath(parent);
	        
	        ArrayList<Node> orderedPath = new ArrayList<Node>();       
	        
	        orderedPath.clear();
	        
	        for(int i = path1.size()-1; i > -1 ; i--){
	        	orderedPath.add(path1.get(i));
	        }
	       
	        move.followGivenPath(orderedPath); 
	        
	    }
       
       private int getThreshold() {
			int value = getAverageValue();
			LCD.drawInt(value, 4, 0, 3);
			return value;
	}
       
       private synchronized void waitForUser(String message) throws InterruptedException {
			
			if (message != null) {
				LCD.drawString(message, 0, 2, false);
			}
			
			Button.ESCAPE.waitForPressAndRelease();
	}
	    
	    
	 public void run() throws InterruptedException{
		 
		 boolean objOnLeft = true;
		 
		 System.out.println("R or L Button to choose path");
		 
		 int id = Button.waitForAnyEvent();
		 
		 //LOCALIZATION
		 waitForUser("red  ");
		 setRed(getThreshold());
		 waitForUser(null);
		 
		 waitForUser("white");
		 setWhite(getThreshold());
		 waitForUser(null);
		 		 
		 Localization();
		 
		 if(id == Button.ID_LEFT){
			objOnLeft = true;
		}
		 
		if(id == Button.ID_RIGHT){
			objOnLeft = false;
		}
		
//		LCD.clear();
		
		
			
			Delay.msDelay(1000);
			
			if(objOnLeft == true){
				AStarStart(new Node(18,5), new Node(0, 15), NODESLL);
				System.out.println("REACHED!");
			}else{
				AStarStart(new Node(18,5), new Node(0, 15), NODESLR);
				System.out.println("REACHED!");
			}
			Sound.beep();
			Delay.msDelay(2000);
			
			//AFTER IT ENTERS GOAL
//			if(senseRed()){
//				System.out.println("RED");
//				move.moveOutofGoal();
//				move.setCurrentDirection(0);
//				AStarStartAgain(new Node(0,13), new Node(18,5), NODESRL);
//			 }else if(! (senseRed())){
				 System.out.println("GREEN");
				 move.moveOutofGoal();
				 move.setCurrentDirection(0);
				 AStarStartAgain(new Node(0,13), new Node(18,5), NODESRR);
//			 }
			
//			if(flag ==true){
//				System.out.println(110);
//				//AStarStartAgain(new Node(0,13), new Node(18,5), NODESRL);
//			}else{
//				AStarStartAgain(new Node(0,13), new Node(18,5), NODESRR);
//				//System.out.println(111);
//			}
		
	 }
	 
	 
	 
	
}
