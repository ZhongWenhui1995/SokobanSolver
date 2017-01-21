package zwh.move;

import zwh.exception.UnvalidPointException;
import zwh.map.MapDirection;
import zwh.map.MapSymble;
import zwh.map.SokobanMap;
import zwh.map.point.Point;
import zwh.move.rule.DefaultMapMoveRule;
import zwh.move.rule.IMapMoveRule;

public class ManMover {
    
    /**
     * 如果往该方向移动一格是合法的移动，则返回移动后的SokobanMap，否则返回原来的SokobanMap
     * @param map
     * @param direction
     * @return
     */
    public static SokobanMap moveOneStep(SokobanMap map, int direction){
	return ManMover.moveOneStep(map, direction, new DefaultMapMoveRule());
    }
    
    public static SokobanMap moveOneStep(SokobanMap map, int direction, IMapMoveRule mapRule) {
	switch (direction) {
	case MapDirection.UP:
	    return ManMover.up(map, mapRule);
	case MapDirection.DOWN:
	    return ManMover.down(map, mapRule);
	case MapDirection.LEFT:
	    return ManMover.left(map, mapRule);
	case MapDirection.RIGHT:
	    return ManMover.right(map, mapRule);
	default:
	    break;
	}
	return null;
    }
    
    public static SokobanMap up(SokobanMap map, IMapMoveRule mapRule) {
	SokobanMap resMap = ManMover.move(map, MapDirection.UP, mapRule);
	return resMap;
    }

    public static SokobanMap up(SokobanMap map) {
	return ManMover.up(map, new DefaultMapMoveRule());
    }
    
    public static SokobanMap down(SokobanMap map, IMapMoveRule mapRule){
	SokobanMap resMap = ManMover.move(map, MapDirection.DOWN, mapRule);
	return resMap;
    }
    
    public static SokobanMap down(SokobanMap map){
	return ManMover.down(map, new DefaultMapMoveRule());
    }
    
    public static SokobanMap left(SokobanMap map, IMapMoveRule mapRule){
	SokobanMap resMap = ManMover.move(map, MapDirection.LEFT, mapRule);
	return resMap;
    }
    
    public static SokobanMap left(SokobanMap map){
	return ManMover.left(map, new DefaultMapMoveRule());
    }
    
    public static SokobanMap right(SokobanMap map, IMapMoveRule mapRule){
	SokobanMap resMap = ManMover.move(map, MapDirection.RIGHT, mapRule);
	return resMap;
    }
    
    public static SokobanMap right(SokobanMap map){
	return ManMover.right(map, new DefaultMapMoveRule());
    }

    /**
     * 每次移动一格，修改传入的地图参数，返回新的地图
     * 
     * @param map
     * @param movePoint
     * @param direction
     * @param mapRule
     * @return
     */
    private static SokobanMap move(SokobanMap map, int direction, IMapMoveRule mapRule) {
	SokobanMap resMap = null;
	Point movePoint = map.manPoint;
	Point nextPoint = ManMover.getTargetPoint(movePoint, direction);
	Point nextNextPoint = ManMover.getTargetPoint(nextPoint, direction);
	try {
	    char  moveChar = map.getCharByPoint(movePoint);
	    char goalChar = map.getCharByPoint(nextPoint);
	    //判断需要移动的物体是否为人（只有人能被移动）
	    if (MapSymble.isMan(moveChar)) {
		//判断人的目的地点是否为墙壁或者另一个人
		if (goalChar == MapSymble.WALL_CHAR || MapSymble.isMan(goalChar)) {
		    resMap = map;
		}else if(goalChar == MapSymble.GROUND_CHAR || goalChar == MapSymble.GOAL_CHAR){//判断人的目的地点是否为地板或目标地点
		    resMap = map.modifyPoint(movePoint, mapRule.getCharOfMoveAfterMove(moveChar));
		    resMap = resMap.modifyPoint(nextPoint, mapRule.getCharOfGoalAfterMove(goalChar, moveChar));
		    resMap = new SokobanMap(resMap.getMapList(), resMap.path + MapDirection.getPath(direction));
		}else if(MapSymble.isBox(goalChar)){//判断人的目的地点是否为箱子
		    char nextNextChar = map.getCharByPoint(nextNextPoint);
		    //判断人的目的地点的下个地点是否为地板和goal
		    if(nextNextChar == MapSymble.GROUND_CHAR || nextNextChar == MapSymble.GOAL_CHAR){
			resMap = map.modifyPoint(movePoint, mapRule.getCharOfMoveAfterMove(moveChar));
			resMap = resMap.modifyPoint(nextPoint, mapRule.getCharOfGoalAfterMove(goalChar, moveChar));
			resMap = resMap.modifyPoint(nextNextPoint, mapRule.getCharOfGoalAfterMove(nextNextChar, goalChar));
			resMap = new SokobanMap(resMap.getMapList(), resMap.path + MapDirection.getPath(direction).toUpperCase());
		    }else{
			resMap = map;
		    }
		}
	    }else{
		resMap = map;
	    }
	    return resMap;
	} catch (UnvalidPointException e) {
	    System.out.println("ManMover>move: " + e.getMessage());
//	    e.printStackTrace();
	    return map;
	}
    }

    /**
     * 返回新的地图
     * 
     * @param mapList
     *            地图
     * @param movePoint
     *            需要移动的物体所在坐标
     * @param direction
     *            移动方向
     * @return 1.map 2.null:不可移动
     */
    // private static SokobanMap move(SokobanMap map, Point movePoint, int
    // direction, IMapMoveRule mapRule) {
    // SokobanMap resMap;
    // Point nextPoint = ManMover.getTargetPoint(movePoint, direction);
    // char moveChar;
    // char targetChar;
    // try {
    // moveChar = map.getCharByPoint(movePoint);
    // targetChar = map.getCharByPoint(nextPoint);
    // //如果为下面情况，则表明可以移动
    // if(moveChar == MapSymble.GOAL_CHAR
    // || moveChar == MapSymble.GROUND_CHAR){
    // return map;
    // }
    // //如果为下面的情况则表明不能移动
    // if (targetChar == MapSymble.WALL_CHAR
    // || targetChar == MapSymble.MAN_CHAR
    // || targetChar == MapSymble.MAN_ON_GOAL_CHAR
    // || moveChar == MapSymble.WALL_CHAR
    // || (moveChar == MapSymble.BOX_CHAR && targetChar == MapSymble.BOX_CHAR)
    // || (moveChar == MapSymble.BOX_ON_GOAL_CHAR && targetChar ==
    // MapSymble.BOX_CHAR)
    // || (moveChar == MapSymble.BOX_CHAR && targetChar ==
    // MapSymble.BOX_ON_GOAL_CHAR)
    // || (moveChar == MapSymble.BOX_ON_GOAL_CHAR && targetChar ==
    // MapSymble.BOX_ON_GOAL_CHAR)) {
    // return null;
    // }
    // resMap = ManMover.move(map, nextPoint, direction, mapRule);
    // if(resMap != null){
    // resMap = resMap.modifyPoint(nextPoint,
    // mapRule.getCharOfGoalAfterMove(targetChar, moveChar));
    // resMap = resMap.modifyPoint(movePoint,
    // mapRule.getCharOfMoveAfterMove(moveChar));
    // return resMap;
    // }
    // return null;
    // } catch (Exception e) {
    // System.out.println(e.getMessage());
    // return null;
    // }
    // }

    /**
     * 根据当前Point，获取往目标方向前进一格的Point
     * @param currPoint 当前Point
     * @param direction 目标方向
     * @return 前进一格后的Point
     */
    public static Point getTargetPoint(Point currPoint, int direction) {
	Point goalPoint;
	switch (direction) {
	case MapDirection.UP:
	    goalPoint = new Point(currPoint.x - 1, currPoint.y);
	    break;
	case MapDirection.DOWN:
	    goalPoint = new Point(currPoint.x + 1, currPoint.y);
	    break;
	case MapDirection.LEFT:
	    goalPoint = new Point(currPoint.x, currPoint.y - 1);
	    break;
	case MapDirection.RIGHT:
	    goalPoint = new Point(currPoint.x, currPoint.y + 1);
	    break;
	default:
	    return new Point(-1, -1);
	}
	return goalPoint;
    }
}
