import java.util.ArrayList;
import java.util.List;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.utility.Delay;

public class AStar {
	
	float[] rgb = new float[3];
	
	//static EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S1);
	
		//ONE
		int startLeft;
		//TWO
		int startRight;
		//THREE
		int goalLeft;
		//FOUR
		int goalRight; 
		
		//ONE 
		public void setStartLeft(int value) {
			startLeft = value;
		}
		public int getStartLeft() {
			return startLeft;
		}
		//TWO
		public void setStartRight(int value) {
			startRight = value;
		}
		public int getStartRight() {
			return startRight;
		}
		//THREE
		public void setGoalLeft(int value) {
			goalLeft = value;
		}
		public int getGoalLeft() {
			return startLeft;
		}
		//FOUR
		public void setGoalRight(int value) {
			startRight = value;
		}
		public int getGoalRight() {
			return startRight;
		}

		//THRESHOLDS AND VALUES MAYBE NEED TO BE ADJUSTED PER COLOR (-10 to something else)
		//ONE
		public boolean senseStartLeft(){
			if(getAverageValue() < (getStartLeft()+10) && getAverageValue() > (getStartLeft()-10)){
				return true;
			}
			return false;
		}
		//TWO
		public boolean senseStartRight(){
			if(getAverageValue() < (getStartRight()+10) && getAverageValue() > (getStartRight()-10)){
				return true;
			}
			return false;
		}
		//THREE
		public boolean senseGoalLeft(){
			if(getAverageValue() < (getGoalLeft()+10) && getAverageValue() > (getGoalLeft()-10)){
				return true;
			}
			return false;
		}
		//FOUR
		public boolean senseGoalRight(){
			if(getAverageValue() < (getGoalRight()+10) && getAverageValue() > (getGoalRight()-10)){
				return true;
			}
			return false;
		}
		
		//AVG VALUE FOR COLOR SENSOR
		public static int getAverageValue(){
			float[] rgb = new float[3];

			int sum = 0;
			
			for (int i = 0; i < 3; i++) {
				//colorSensor.getRGBMode().fetchSample(rgb, 0);
				sum += (rgb[0] + rgb[1] + rgb[2]) * 100;
			}
			
			return sum / 3;
		}
		
		//Button Press
		 private synchronized static void waitForUser(String message) throws InterruptedException {
				
				if (message != null) {
					LCD.drawString(message, 0, 2, false);
				}
				
				Button.ESCAPE.waitForPressAndRelease();
		}
    
	//ONE
    public static final int[][] NODESLL = {   		
        	//WITH ALL FOUR CHANGING OBSTACLES 
        	   //                            M
                { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1 },
                { 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1 },
                { 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                { 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        /*M*/   { 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },  /*M*/
                { 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0 },
                { 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0 },
                { 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0 },
                { 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0 },
                { 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0 },
                { 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 },
               //                            M
            };
    
    //TWO
    public static final int[][] NODESLR = {   		
        	//WITH ALL FOUR CHANGING OBSTACLES 
        	   //                            M
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1 },
                { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1 },
                { 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1 },
                { 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                { 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        /*M*/   { 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },  /*M*/
                { 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0 },
                { 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0 },
                { 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0 },
                { 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0 },
                { 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0 },
                { 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 },
               //                            M
            };
    
    //THREE
    public static final int[][] NODESRL = {   		
        	//WITH ALL FOUR CHANGING OBSTACLES 
        	   //                            M
                { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1 },
                { 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1 },
                { 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1 },
                { 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1 },
                { 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                { 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        /*M*/   { 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },  /*M*/
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0 },
                { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
                { 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
               //                            M
            };
    
    //FOUR
    public static final int[][] NODESRR = {   		
        	//WITH ALL FOUR CHANGING OBSTACLES 
        	   //                            M
                { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1 },
                { 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1 },
                { 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1 },
                { 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1 },
                { 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                { 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        /*M*/   { 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },  /*M*/
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0 },
                { 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0 },
                { 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0 },
                { 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
               //                            M
            };
    
    private ArrayList<Node> openList = new ArrayList<Node>();
    private ArrayList<Node> closeList = new ArrayList<Node>();

    public Node findMinFNodeInOpneList() {
        Node tempNode = openList.get(0);
        for (Node node : openList) {
            if (node.F < tempNode.F) {
                tempNode = node;
            }
//            if (node.F == tempNode.F) {
//            		if (node.G < tempNode.G) {
//            			tempNode = node;
//            		}
//            }
            //improve here~!
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
        
        int up_left_X = currentNode.x - 1;
        int up_left_Y = currentNode.y + 1;
        if (canReach(up_left_X, up_left_Y, nodes) && !exists(closeList, up_left_X, up_left_Y)) {
            arrayList.add(new Node(up_left_X, up_left_Y));
        }
        
        int up_right_X = currentNode.x + 1;
        int up_right_Y = currentNode.y + 1;
        if (canReach(up_right_X, up_right_Y, nodes) && !exists(closeList, up_right_X, up_right_Y)) {
            arrayList.add(new Node(up_right_X, up_right_Y));
        }
        
        int down_right_X = currentNode.x + 1;
        int down_right_Y = currentNode.y - 1;
        if (canReach(down_right_X, down_right_Y, nodes) && !exists(closeList, down_right_X, down_right_Y)) {
            arrayList.add(new Node(down_right_X, down_right_Y));
        }
        
        int down_left_X = currentNode.x - 1;
        int down_left_Y = currentNode.y - 1;
        if (canReach(down_left_X, down_left_Y, nodes) && !exists(closeList, down_left_X, down_left_Y)) {
            arrayList.add(new Node(down_left_X, down_left_Y));
        }
        
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

    private int calcG(Node start, Node node) {
    		int diffX = (int) Math.pow((node.x-start.x), 2);
		int diffY = (int) Math.pow((node.y-start.y), 2);
		int gcost = (int) Math.sqrt(diffX + diffY);
		return gcost;
    }

    private int calcH(Node end, Node node) {
    		int diffX = (int) Math.pow((node.x-end.x), 2);
		int diffY = (int) Math.pow((node.y-end.y), 2);
		int hCost = (int) Math.sqrt(diffX + diffY);
		return hCost;
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

    public void start() {
    	
    	
    	try {
			waitForUser("READY");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Delay.msDelay(1000);
    	
        Node startNode1 = new Node(16, 3);
        
        Node endNode1 = new Node(1, 12);
        //Node endNode1 = new Node(2, 12); for LL
        //Node endNode1 = new Node(1, 12); for LR
        
        //if statement----button choose board
        Node parent = new AStar().findPath(startNode1, endNode1, NODESLR);
        
        Move move = new Move();
       
        ArrayList<Node> path1 = outputThePath(parent);
        
        ArrayList<Node> orderedPath = new ArrayList<Node>();
        
//        System.out.println("\n");
//
//        for (int i = 0; i < NODESLL.length; i++) {
//            for (int j = 0; j < NODESLL[0].length; j++) {
//                if (exists(path1, i, j)) {
//                    System.out.print("@, ");
//                } else {
//                    System.out.print(NODESLL[i][j] + ", ");
//                }
//
//            }
//            System.out.println();
//
//        }
        
//        System.out.println("");
//        
//        for(int i = 0; i < path1.size(); i++){
//        	System.out.println(path1.get(i).getX() + " " + path1.get(i).getY());
//        }
//        
        
        orderedPath.clear();
        
        for(int i = path1.size()-1; i > -1 ; i--){
        	orderedPath.add(path1.get(i));
        }
        
//        System.out.println("");
//
//        
//        for(int i = 0; i < orderedPath.size(); i++){
//        	System.out.println(orderedPath.get(i).getX() + " " + orderedPath.get(i).getY());
//        }
        
//        System.out.println("");
//        
//        System.out.println("BELOW IS WHAT IT WILL DO WHEN TESTED ON ROBOT, BUT IT JUST PRINTS FOR NOW");
//        
//        System.out.println("");

       
      //move
        move.followGivenPath(orderedPath); 
        
        // if statement --- detect the color and choose the path an run again;
    }
}
