
import java.util.ArrayList;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class Move {
	
	boolean diagonal = true;
	int currentDirection = 45;
	
	EV3GyroSensor gyroSensor = new EV3GyroSensor(SensorPort.S2);
	final SampleProvider sp = gyroSensor.getAngleMode();
	private double currentAngle = 0;
	final private double circumference = 17.279;
	
	//THIS IS NOW FINE
	public void move(double distance){
		Motor.A.setSpeed(203);
		Motor.D.setSpeed(196);
		Motor.A.setAcceleration(4000);
		Motor.D.setAcceleration(4000);
		
		int distanceToAngle = (int) (360 * distance/circumference) ;
		
		Motor.A.rotate(distanceToAngle,true);
		Motor.D.rotate(distanceToAngle,true);
		
		Delay.msDelay(800);
		return;
	}
	
	public void moveBack(double distance){
		
		Motor.A.setSpeed(200);
		Motor.D.setSpeed(200);
		Motor.A.setAcceleration(4000);
		Motor.D.setAcceleration(4000);
		
		int distanceToAngle = (int) (360 * distance/circumference) ;
		
		Motor.A.rotate(-distanceToAngle,true);
		Motor.D.rotate(-distanceToAngle,true);
		
		Delay.msDelay(1000);
		return;
	}
	
	public void rotateL(double degree){
		degree = Math.round(degree);
		gyroSensor.reset();
		Delay.msDelay(1000);
		Motor.A.setSpeed(170);
		Motor.D.setSpeed(170);
		Motor.A.setAcceleration(4000);
		Motor.D.setAcceleration(4000);
		float [] sample = new float[sp.sampleSize()];
        sp.fetchSample(sample, 0);
		double value = 0;
		
		while(value != degree){
	        
			sp.fetchSample(sample, 0);
	        value = ((sample[0] - currentAngle));			
			
			if(degree - value < 0){
				Motor.A.forward();
				Motor.D.backward();
			}else{
				Motor.A.backward();
				Motor.D.forward();
			}
			
			sp.fetchSample(sample, 0);
	        value = ((sample[0] - currentAngle));
	        
			if (Math.abs(degree - value) < 8) {
				Motor.A.setAcceleration(17000);
				Motor.D.setAcceleration(17000);
				Motor.A.setSpeed((int)Math.abs(degree - value)+4);
				Motor.D.setSpeed((int)Math.abs(degree - value)+4);
			}
			
		}
		
		Motor.A.setSpeed(0);
		Motor.D.setSpeed(0);
		
		Delay.msDelay(1100);
		
		return;
	}
	
	public void rotateR(double degree){
		degree = Math.round(degree);
		gyroSensor.reset();
		Delay.msDelay(1000);
		Motor.A.setSpeed(170);
		Motor.D.setSpeed(170);
		Motor.A.setAcceleration(4000);
		Motor.D.setAcceleration(4000);
		float [] sample = new float[sp.sampleSize()];
        sp.fetchSample(sample, 0);
		double value = 0;
		
		while(value != degree){
	        
			sp.fetchSample(sample, 0);
	        value = ((sample[0] - currentAngle));
	        
			
			if(degree - value < 0){
				Motor.D.backward();
				Motor.A.forward();
			}else{
				Motor.D.forward();
				Motor.A.backward();
			}
			
			sp.fetchSample(sample, 0);
	        value = ((sample[0] - currentAngle));
	        
			if (Math.abs(degree - value) < 8) {
				Motor.A.setAcceleration(17000);
				Motor.D.setAcceleration(17000);
				Motor.A.setSpeed((int)Math.abs(degree - value)+4);
				Motor.D.setSpeed((int)Math.abs(degree - value)+4);
			}
			
		}
		
		Motor.A.setSpeed(0);
		Motor.D.setSpeed(0);
		
		Delay.msDelay(1100);
		
		return;
	}
	
	//Physical movement classes are above.
	//Below are all calculations. 
	
	public void setCurrentDirection(int currentDirection) {
		this.currentDirection = currentDirection;
	}
	
	public void setDiaganol(boolean diaganol) {
		this.diagonal = diaganol;
	}
	
	public int getCurrentDirection() {
		return currentDirection;
	}
	
	public boolean getDiaganol(){
		return diagonal;
	}
	
	//SET MOVE AND ROTATION METHODS
	void moveOneNode() {
		move(5);
	}

	void moveOneNodeDiagonal(){
		double x = Math.sqrt(50);
		move(x);
	}
	
	void moveOutofGoal(){
		moveBack(10);
		
		setCurrentDirection(0);
	}
	
	void RightNinetyRotation(){
		//90 Degrees Rotation
		rotateR(-90);
	}
	
	void LeftNinetyRotation(){
		//90 Degrees Rotation
		rotateL(90);
	}
	
	void RightFortyFiveRotation(){
		//45 Degrees Rotation
		rotateR(-45);
	}
	
	void LeftFortyFiveRotation(){
		//45 Degrees Rotation
		rotateL(45);
	}
	
	//FINDS OUT THE DIRECTION IT SHOULD FACE TO MOVE FROM ONE POINT TO ANOTHER
	 int nextDirection(int y, int x, int yNext, int xNext){
		
		if(y > yNext && x == xNext){
			//System.out.println("North");
			return 90;
			
		} else if(y == yNext && x < xNext){
			//System.out.println("East");
			return 0;
			
		} else if(y < yNext && x == xNext){
			//System.out.println("South");
			return 270;
			
		} else if(y == yNext && x > xNext){
			//System.out.println("West");
			return 180;
		} 
		
		//If this is ever returned, then we have a problem.
		System.out.println("PROBLEM IN MoveDirection()");
		return 360;
	}
	
	 //nextDirection parameter will be calculated using the previous method
	 //This takes the next direction and execution rotation depending on the current direction
	boolean rotateAndUpdateDirection(int nextDirection){
		
		//E = 0 
		//NE = 45
		//N = 90
		//NW = 135
		//W = 180
		//SW = 225
		//S = 270
		//SE = 315
		
		if(currentDirection%45 != 0 || nextDirection%45 != 0){
			System.out.println("NOPE");
			return diagonal;
		}
		
		//CHECK ITS DIRECTION AT FIRST TO KNOW WHAT THE DIRECTION WILL BE AFTER IT MOVES
		if(currentDirection == 0 || currentDirection == 90 || currentDirection == 180 || currentDirection == 270 || currentDirection == 360){
			//System.out.println("NOT DIAGANOL AT FIRST");
			diagonal = false;
		} else {
			//System.out.println("IS DIAGANOL AT FIRST");
			diagonal = true;
		}
		
		//VALUES THAT CANNOT WORK ARE CHECKED
		if(nextDirection > 360 - 1 || nextDirection < 0){
			System.out.println("THESE VALUES DO NOT WORK");
			return diagonal;
		}
		
		//IN CASE NO ROTATION IS NEEDED
		if(currentDirection - nextDirection == 0 || nextDirection - currentDirection == 0){
			//System.out.println("NO ROTATION");
			return diagonal;
		} 	
		
		//HERE'S THE CASES WHERE ROTATION HAPPENS
		if(currentDirection - nextDirection > 0){
			
			if(currentDirection - nextDirection == 45){
				
				//System.out.println("RIGHT TURN 45");
				RightFortyFiveRotation();
				
				if(diagonal){
					//System.out.println("NOT DIAGANOL AT END");
					diagonal = false; 
				} else {
					//System.out.println("IS DIAGANOL AT END");
					diagonal = true;
				}
				
			} else if(currentDirection - nextDirection == 90){
				
				//System.out.println("RIGHT TURN 90");
				RightNinetyRotation();
				
				if(diagonal){
					//System.out.println("IS DIAGANOL AT END");
				} else {
					//System.out.println("NOT DIAGANOL AT END");
				}
				
			} else if(currentDirection - nextDirection == 135){
				
				//System.out.println("RIGHT TURN 90");
				RightNinetyRotation();
				//System.out.println("RIGHT TURN 45");
				RightFortyFiveRotation();
				
				if(diagonal){
					//System.out.println("NOT DIAGANOL AT END");
					diagonal = false; 
				} else {
					//System.out.println("IS DIAGANOL AT END");
					diagonal = true;
				}
				
				
			} else if(currentDirection - nextDirection == 180){
				
				//System.out.println("RIGHT TURN 90");
				RightNinetyRotation();
				//System.out.println("RIGHT TURN 90");
				RightNinetyRotation();
				
				if(diagonal){
					//System.out.println("IS DIAGANOL AT END");
				} else {
					//System.out.println("NOT DIAGANOL AT END");
				}

			} else if(currentDirection - nextDirection == 225){
				
				//System.out.println("LEFT TURN 90");
				LeftNinetyRotation();
				//System.out.println("LEFT TURN 45");
				LeftFortyFiveRotation();
				
				if(diagonal){
					//System.out.println("NOT DIAGANOL AT END");
					diagonal = false; 
				} else {
					//System.out.println("IS DIAGANOL AT END");
					diagonal = true;
				}
				
			} else if(currentDirection - nextDirection == 270){
				
				//System.out.println("LEFT TURN 90");
				LeftNinetyRotation();
				
				if(diagonal){
					//System.out.println("IS DIAGANOL AT END");
				} else {
					//System.out.println("NOT DIAGANOL AT END");
				}

			} else if(currentDirection - nextDirection == 315){
				
				//System.out.println("LEFT TURN 45");
				LeftFortyFiveRotation();
				
				if(diagonal){
					//System.out.println("NOT DIAGANOL AT END");
					diagonal = false; 
				} else {
					//System.out.println("IS DIAGANOL AT END");
					diagonal = true;
				}
			}
			
		} else if(currentDirection - nextDirection < 0){

			if(currentDirection - nextDirection == -45){
				
				//System.out.println("LEFT TURN 45");
				LeftFortyFiveRotation();
				
				if(diagonal){
					//System.out.println("NOT DIAGANOL AT END");
					diagonal = false; 
				} else {
					//System.out.println("IS DIAGANOL AT END");
					diagonal = true;
				}
				
			} else if(currentDirection - nextDirection == -90){
				
				//System.out.println("LEFT TURN 90");
				LeftNinetyRotation();
				
				if(diagonal){
					//System.out.println("IS DIAGANOL AT END");
				} else {
					//System.out.println("NOT DIAGANOL AT END");
				}
				
			} else if(currentDirection - nextDirection == -135){
				
				//System.out.println("LEFT TURN 90");
				LeftNinetyRotation();
				//System.out.println("LEFT TURN 45");
				LeftFortyFiveRotation();
				
				if(diagonal){
					//System.out.println("NOT DIAGANOL AT END");
					diagonal = false; 
				} else {
					//System.out.println("IS DIAGANOL AT END");
					diagonal = true;
				}
				
			} else if(currentDirection - nextDirection == -180){
				
				//System.out.println("LEFT TURN 90");
				LeftNinetyRotation();
				//System.out.println("LEFT TURN 90");
				LeftNinetyRotation();
				
				if(diagonal){
					//System.out.println("IS DIAGANOL AT END");
				} else {
					//System.out.println("NOT DIAGANOL AT END");
				}

			} else if(currentDirection - nextDirection == -225){
				
				//System.out.println("RIGHT TURN 90");
				RightNinetyRotation();
				//System.out.println("RIGHT TURN 45");
				RightFortyFiveRotation();
				
				if(diagonal){
					//System.out.println("NOT DIAGANOL AT END");
					diagonal = false; 
				} else {
					//System.out.println("IS DIAGANOL AT END");
					diagonal = true;
				}
				
			}  else if(currentDirection - nextDirection == -270){
				
				//System.out.println("RIGHT TURN 90");
				RightNinetyRotation();
				
				if(diagonal){
					//System.out.println("IS DIAGANOL AT END");
				} else {
					//System.out.println("NOT STRAIGHT AT END");
				}

			} else if(currentDirection - nextDirection == -315){
				
				//System.out.println("RIGHT TURN 45");
				RightFortyFiveRotation();
				
				if(diagonal){
					//System.out.println("NOT DIAGANOL AT END");
					diagonal = false; 
				} else {
					//System.out.println("IS DIAGANOL AT END");
					diagonal = true;
				}
			}
		}
				
		//System.out.println("Direction was " + currentDirection);
		currentDirection = nextDirection;
		//System.out.println("Direction is now: " + currentDirection);
		return diagonal;
	}
	
	//Move the robot depending on weather it moves diagonally or not from one node to the other
	void checkMovementType(boolean diag){
		if(diag){
			//System.out.println("Moves to next diagonal node");
			moveOneNodeDiagonal();
		} else {
			//System.out.println("Moves to next adjacent node");
			moveOneNode();
		}
	}
	
	public void followGivenPath(ArrayList<Node> path){
		
		for(int i = 0; i < path.size() - 1; i++){			
			checkMovementType(rotateAndUpdateDirection(nextDirection(path.get(i).getX(), path.get(i).getY(), path.get(i+1).getX(), path.get(i+1).getY())));
		}
		
	}

}
