	public class Node {
	
        public int x;
        public int y;

        public int F;
        public int G;
        public int H;
        
        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int calcF() {
            this.F = this.G + this.H;
            return F;
        }
        
        public Node parent;
        
        public int getX() {
			return x;
		}
        
        public int getY() {
			return y;
		}
}
