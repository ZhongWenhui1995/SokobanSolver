package zwh.map;

/**
 * 所有符号均只能用一个字符表达
 * @author zwh
 *
 */
public class MapSymble {
    public static final String WALL = "#";
    public static final String GROUND = "-";
    public static final String MAN_ON_GOAL = "+";
    public static final String GOAL = ".";
    public static final String BOX = "$";
    public static final String MAN = "@";
    public static final String SPLIT_LINE_SYMBLE = "|";
    public static final String BOX_ON_GOAL ="*";
    
    public static final char WALL_CHAR = '#';
    public static final char GROUND_CHAR = '-';
    public static final char MAN_ON_GOAL_CHAR = '+';
    public static final char GOAL_CHAR = '.';
    public static final char BOX_CHAR = '$';
    public static final char MAN_CHAR = '@';
    public static final char SPLIT_LINE_SYMBLE_CHAR = '|';
    public static final char BOX_ON_GOAL_CHAR = '*';
    
    public static boolean isBox(char obj){
	if(obj == MapSymble.BOX_CHAR || obj == MapSymble.BOX_ON_GOAL_CHAR){
	    return true;
	}
	return false;
    }
    
    public static boolean isMan(char obj){
	if(obj == MapSymble.MAN_CHAR || obj == MapSymble.MAN_ON_GOAL_CHAR){
	    return true;
	}
	return false;
    }
}
