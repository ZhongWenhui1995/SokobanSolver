package zwh.map;

public class MapDirection {
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;
    public static final int[] directions = {1, 2, 3, 4};
    
    public static String getPath(int direction){
	String res = "";
	switch (direction) {
	case UP:
	    res = "u";
	    break;
	case DOWN:
	    res = "d";
	    break;
	case LEFT:
	    res = "l";
	    break;
	case RIGHT:
	    res = "r";
	    break;
	default:
	    break;
	}
	return res;
    }
}
