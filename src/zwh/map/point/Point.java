package zwh.map.point;

public class Point {
    public final int x;
    public final int y;
    
    public Point(int x, int y){
	this.x = x;
	this.y = y;
    }
    
    public boolean equals(Point oPoint){
	if(this.x == oPoint.x && this.y == oPoint.y){
	    return true;
	}
	return false;
    }
    
    @Override
    public String toString() {
	return "Point [x=" + x + ", y=" + y + "]";
    }
}
