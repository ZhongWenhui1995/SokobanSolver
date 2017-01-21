package zwh.exception;

import zwh.map.point.Point;

public class UnvalidPointException extends Exception {

    private Point point;
    private int mx = 0;
    private int my = 0;
    private String prefixInfo = "";

    public UnvalidPointException(Point point, int mx, int my) {
	this.point = point;
	this.mx = mx;
	this.my = my;
    }
    
    public UnvalidPointException(Point point, int mx, int my, String prefixInfo) {
	this.point = point;
	this.mx = mx;
	this.my = my;
	this.prefixInfo = prefixInfo;
    }

    @Override
    public String getMessage() {
	return prefixInfo + "Unvalid Point: " + point.toString() + "    MX(" + mx + ")  " + "MY(" + my + ")"
		+ "    RULE:[0 <= x < MX, 0 <= y < MY]";
    }

    @Override
    public String toString() {
	return getMessage();
    }
}
