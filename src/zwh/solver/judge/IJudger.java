package zwh.solver.judge;

import zwh.map.SokobanMap;

/**
 * 对地图执行情况的判断
 * @author zwh
 *
 */
public interface IJudger {
    /**
     * 是否解决
     * @param map
     * @return
     */
    boolean isSolved(SokobanMap map);
    /**
     * 是否已经遍历过
     * @param map
     * @return
     */
    boolean isPathed(SokobanMap map);
    /**
     * 清空缓存
     */
    void clear();
}
