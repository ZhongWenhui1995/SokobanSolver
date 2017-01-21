package zwh.solver;

import zwh.map.SokobanMap;

public interface ISokobanSolver {
    /**
     * 开始路径搜索
     * @param map
     * @throws Exception
     */
    void solve(final SokobanMap map) throws Exception;
    
    /**
     * 如果成功解决，将返回结果SokobanMap，否则返回null
     * @return
     */
    SokobanMap getSolvedMap();
    
    /**
     * 获取执行的时间
     * @return long
     */
    long getSolvedTime();
}
