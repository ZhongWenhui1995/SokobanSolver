package zwh.map;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import zwh.exception.UnvalidPointException;
import zwh.map.point.Point;

/**
 * 判断地图是否有效
 * 
 * @author zwh
 *
 */
public class MapChecker {
    private static Set<String> pathedPoints = new HashSet<String>();

    /**
     * 判断是否为有效地图： 1.地图必须为长方形的规整图形 2.不能存在无效字符 3.WALL必须封闭 4.必须有且仅有一个人5.不能存在无效行
     * 
     * @param map
     * @return
     */
    public static boolean isValidMap(SokobanMap map) {
	if (MapChecker.isRetangleMap(map) && MapChecker.exitMan(map) && MapChecker.isClosure(map)) {
	    return true;
	}
	return false;
    }

    /**
     * 判断是否有无效行（只有地板没有其它，存在空格等）
     * 
     * @param map
     * @return
     */
    private static boolean exitUnvalidLine(SokobanMap map) {
	return false;
    }

    private static boolean exitUnvalidChar(SokobanMap map) {
	return false;
    }

    /**
     * 判断地图是否为长方形
     * 
     * @param mapList
     * @return
     */
    private static boolean isRetangleMap(SokobanMap map) {
	List<String> mapList = map.getMapList();
	int len = mapList.get(0).length();
	for (String line : mapList) {
	    if (len != line.length()) {
		return false;
	    }
	}
	return true;
    }

    /**
     * 该地图中是否有且仅有一个人
     * 
     * @param mapList
     * @return
     */
    private static boolean exitMan(SokobanMap map) {
	String mapStr = map.mapStr;
	int manNum = 0;
	for (int i = 0; i < mapStr.length(); i++) {
	    if (MapSymble.isMan(mapStr.charAt(i))) {
		manNum++;
	    }
	}
	return manNum == 1 ? true : false;
    }

    /**
     * 由起始墙壁附近的墙壁开始，沿着墙壁遍历，若可以走到起始点，则证明该围墙是封闭的， 返回true，否则返回false
     * 
     * @param mapList
     * @return
     */
    private static boolean isClosure(SokobanMap map) {
	MapChecker.pathedPoints.clear();
	int ind = map.mapStr.indexOf(MapSymble.WALL);
	if (ind >= map.my) {
	    return false;
	}
	// 由起点右边的墙壁开始遍历
	return MapChecker.isClosure(map, "0-" + ind, new Point(0, ind + 1), MapDirection.LEFT);
    }

    /**
     * 由起始墙壁附近的墙壁开始，沿着墙壁遍历，若可以走到起始点，则证明该围墙是封闭的， 返回true，否则返回false
     * 
     * @param mapList
     *            地图
     * @param startKey
     *            起始位置
     * @param currPoint
     *            当前位置
     * @param srcDest
     *            前一个位置的方向
     * @return
     */
    private static boolean isClosure(SokobanMap map, String startKey, Point currPoint, int srcDest) {
	String currKey = currPoint.x + "-" + currPoint.y;
	// 如果遍历过，直接返回
	if (MapChecker.pathedPoints.contains(currKey)) {
	    return false;
	}
	int x = currPoint.x;
	int y = currPoint.y;

	try {
	    // 判断是否为墙壁
	    if (map.getCharByPoint(x, y) != MapSymble.WALL_CHAR) {
		return false;
	    }
	    // 如果回到起点，返回true
	    if (startKey.equals(currKey)) {
		return true;
	    }
	    // System.out.println(currKey);
	    // 添加到已经set中
	    MapChecker.pathedPoints.add(currKey);

	    // 往上走
	    if (srcDest != MapDirection.UP) {
		// 如果下一个遍历的位置有效而且为墙壁，则进行遍历
		if (x != 0 && map.getCharByPoint(x - 1, y) == MapSymble.WALL_CHAR) {
		    // 如果该路径已经到达起点，直接返回，因为往上走，因此前一个位置的方向为down
		    if (isClosure(map, startKey, new Point(x - 1, y), MapDirection.DOWN)) {
			return true;
		    }
		}
	    }
	    // 往下走
	    if (srcDest != MapDirection.DOWN) {
		if (x != map.mx - 1 && map.getCharByPoint(x + 1, y) == MapSymble.WALL_CHAR) {
		    if (isClosure(map, startKey, new Point(x + 1, y), MapDirection.UP)) {
			return true;
		    }
		}
	    }
	    // 往左走
	    if (srcDest != MapDirection.LEFT) {
		if (y != 0 && map.getCharByPoint(x, y - 1) == MapSymble.WALL_CHAR) {
		    if (isClosure(map, startKey, new Point(x, y - 1), MapDirection.RIGHT)) {
			return true;
		    }
		}
	    }
	    // 往右走
	    if (srcDest != MapDirection.RIGHT) {
		if (y != map.my - 1 && map.getCharByPoint(x, y + 1) == MapSymble.WALL_CHAR) {
		    if (isClosure(map, startKey, new Point(x, y + 1), MapDirection.LEFT)) {
			return true;
		    }
		}
	    }
	    return false;
	} catch (UnvalidPointException e) {
	    e.printStackTrace();
	    return false;
	}

    }

}
