package zwh.solver.judge;

import zwh.map.SokobanMap;

public interface IJudger {
    boolean isSolved(SokobanMap map);
    boolean isPathed(SokobanMap map);
    void clear();
}
