package zwh.map;

import java.util.ArrayList;
import java.util.List;

import zwh.exception.UnvalidPointException;
import zwh.map.point.Point;

/**
 * 保存地图及相关信息：地图信息，人的位置，长度、高度，
 * 并提供地图的简单功能：获取某个位置点的字符，判断此地图与其它地图是否相等，返回修改指定位置字符后的地图
 * @author zwh
 *
 */
public class SokobanMap {
    public final String mapStr;
    public final Point manPoint;
    public final String path;
    public final int mx;
    public final int my;
    private Boolean isValid = null;

    public SokobanMap(String mapStr, String path) {
	this.mx = mapStr.indexOf(MapSymble.SPLIT_LINE_SYMBLE);
	this.my = mapStr.indexOf(MapSymble.SPLIT_LINE_SYMBLE);
	this.mapStr = mapStr;
	this.manPoint = this.getManPoint();
	this.path = path;
    }

    public SokobanMap(List<String> mapList, String path) {
	this.mx = mapList.size();
	this.my = mapList.get(0).length();
	this.mapStr = SokobanMap.initializeMapList(mapList);
	this.manPoint = this.getManPoint();
	this.path = path;
    }

    public SokobanMap(SokobanMap map) {
	this(map.mapStr, map.path);
    }
    
    public SokobanMap(SokobanMap map, String path){
	this(map.mapStr, path);
    }
    
    /**
     * 将list形式的地图转化为String形式的地图
     * @param mapList
     * @return
     */
    private static String initializeMapList(List<String> mapList) {
	String mapStr = "";
	for (String temp : mapList) {
	    mapStr += MapSymble.SPLIT_LINE_SYMBLE + temp;
	}
	return mapStr.substring(1);
    }

    /**
     * 获取人的位置点
     * @return
     */
    private Point getManPoint() {
	int x = 0, y = 0;
	int index = this.mapStr.indexOf(MapSymble.MAN);
	if(index < 0){
	    index = this.mapStr.indexOf(MapSymble.MAN_ON_GOAL);
	}
	x = index / (this.my + MapSymble.SPLIT_LINE_SYMBLE.length());
	y = (index - x * MapSymble.SPLIT_LINE_SYMBLE.length()) % this.my;
	return new Point(x, y);
    }

    /**
     * 返回mapList
     * 
     * @return
     */
    public List<String> getMapList() {
	List<String> res = new ArrayList<String>();
	int preIndex  = 0;
	int nextIndex = this.mapStr.indexOf(MapSymble.SPLIT_LINE_SYMBLE);
	while(nextIndex != -1){
	    res.add(this.mapStr.substring(preIndex, nextIndex));
	    preIndex = nextIndex + 1;
	    nextIndex = this.mapStr.indexOf(MapSymble.SPLIT_LINE_SYMBLE, nextIndex + 1);
	}
	res.add(this.mapStr.substring(preIndex));
	return res;
    }

    public char getCharByPoint(int x, int y) throws UnvalidPointException {
	return getCharByPoint(new Point(x, y));
    }
    
    /**
     * 获取指定位置的字符
     * @param point
     * @return
     * @throws UnvalidPointException point不合法
     */
    public char getCharByPoint(Point point) throws UnvalidPointException {
	if (this.isValidPoint(point)) {
	    int index = point.x * (this.my + MapSymble.SPLIT_LINE_SYMBLE.length()) + point.y;
	    return mapStr.charAt(index);
	}
	throw new UnvalidPointException(point, this.mx, this.my, "SokobanMap>getCharByPoint: ");
    }

    /**
     * 先创建一个当前SokobanMap的副本，然后将指定地点的字符修改为给定字符，并返回创建的副本，当前的SokobanMap没有发生变化
     * 
     * @param point 指定地点
     * @param newObj 指定字符
     * @return 新创建的SokobanMap
     * @throws Exception point可能不合法
     */
    public SokobanMap modifyPoint(Point point, char newObj) throws UnvalidPointException {
	if (this.isValidPoint(point)) {
	    int index = point.x * (this.my + MapSymble.SPLIT_LINE_SYMBLE.length()) + point.y;
	    String newMapStr = this.mapStr.substring(0, index) + newObj + this.mapStr.substring(index + 1);
	    return new SokobanMap(newMapStr, this.path);
	}
	throw new UnvalidPointException(point, this.mx, this.my, "SokobanMap>modifyPoint: ");
    }
    
    public boolean isValid(){
	if(this.isValid == null){
	    this.isValid = MapChecker.isValidMap(this); 
	}
	return this.isValid;
    }
    
    public boolean isSameMap(SokobanMap map){
	if(this.mapStr.equals(map.mapStr)){
	    return true;
	}
	return false;
    }
    
    public boolean isValidPoint(Point point){
	if(point.x >=0 && point.x < this.mx && point.y >=0 && point.y < this.my){
	    return true;
	}
	return false;
    }
}
