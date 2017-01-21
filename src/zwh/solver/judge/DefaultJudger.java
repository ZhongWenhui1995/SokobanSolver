package zwh.solver.judge;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import zwh.map.MapSymble;
import zwh.map.SokobanMap;

/**
 * 每经过一次求解过程后，都要调用clear，清理paths和solvedMap
 * @author zwh
 *
 */
public class DefaultJudger implements IJudger{
    private Set<String> paths = new ConcurrentSkipListSet<String>();

    public int getPathNums(){
	return paths.size();
    }
    
    @Override
    public void clear(){
	paths.clear();
    }
    
    @Override
    public boolean isSolved(SokobanMap map) {
	if(! map.mapStr.contains(MapSymble.GOAL) && ! map.mapStr.contains(MapSymble.MAN_ON_GOAL)){
	    return true;
	}
	return false;
    }

    /**
     * 判断当前地图情况是否之前已经出现过，如果没有则将添加当前地图情况，返回false，
     * 
     * @param map
     * @return
     */
    @Override
    public boolean isPathed(SokobanMap map) {
	if (!paths.contains(map.mapStr)) {
	    paths.add(map.mapStr);
	    return false;
	}
	return true;
    }
}
