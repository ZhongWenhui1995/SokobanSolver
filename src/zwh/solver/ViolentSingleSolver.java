package zwh.solver;

import java.util.Date;
import java.util.Stack;

import zwh.map.MapDirection;
import zwh.map.SokobanMap;
import zwh.move.ManMover;
import zwh.solver.judge.IJudger;

public class ViolentSingleSolver implements ISokobanSolver {

    private SokobanMap solvedMap = null;
    private IJudger judger;
    private long time = 0;
    
    public ViolentSingleSolver(IJudger judger) {
	this.judger = judger;
    }
    
    @Override
    public void solve(final SokobanMap map) throws Exception{
	String startTime = String.valueOf(new Date().getTime());
	System.out.println("ViolentSingleSolver: START TIME>" + startTime);
	findPath(map);
	String endTime = String.valueOf(new Date().getTime());
	System.out.println("ViolentSingleSolver: END TIME>" + endTime);
	this.time = Long.valueOf(endTime) - Long.valueOf(startTime);
	this.judger.clear();
    }

    private void findPath(SokobanMap map) {
	Stack<SokobanMap> maps = new Stack<SokobanMap>();
	maps.add(map);
	while (!maps.isEmpty()) {
	    map = maps.pop();
	    SokobanMap nextMap = this.getNextMap(map);
	    while (nextMap != null && this.solvedMap ==null) {
		if (this.judger.isSolved(nextMap)) {
		    this.solvedMap = nextMap;
		    return;
		}
		maps.add(nextMap);
		nextMap = this.getNextMap(map);
	    }
	}
    }
    
    /**
     * 返回下一个可行的地图，如果没有则返回null
     * @param map
     * @return
     */
    public SokobanMap getNextMap(SokobanMap map) {
	for(int direction : MapDirection.directions){
	    SokobanMap nextMap = ManMover.moveOneStep(map, direction);
	    if(! this.judger.isPathed(nextMap)){
		return nextMap;
	    }
	}
	return null;
    }

    @Override
    public SokobanMap getSolvedMap() {
	return this.solvedMap;
    }

    @Override
    public long getSolvedTime() {
	return this.time;
    }
}
