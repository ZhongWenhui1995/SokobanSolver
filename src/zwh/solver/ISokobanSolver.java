package zwh.solver;

import zwh.map.SokobanMap;

public interface ISokobanSolver {
    void solve(final SokobanMap map) throws Exception;
    SokobanMap getSolvedMap();
    long getSolvedTime();
}
