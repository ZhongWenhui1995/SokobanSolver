package zwh.move.rule;

import zwh.map.MapSymble;

public class DefaultMapMoveRule implements IMapMoveRule {
    @Override
    public Character getCharOfGoalAfterMove(char goalChar, char moveChar) {
	Character res = null;
	if (moveChar == MapSymble.MAN_CHAR || moveChar == MapSymble.MAN_ON_GOAL_CHAR) {
	    if (goalChar == MapSymble.GROUND_CHAR || goalChar == MapSymble.BOX_CHAR) {
		res = MapSymble.MAN_CHAR;
	    } else if (goalChar == MapSymble.GOAL_CHAR || goalChar == MapSymble.BOX_ON_GOAL_CHAR) {
		res = MapSymble.MAN_ON_GOAL_CHAR;
	    }
	} else if (moveChar == MapSymble.BOX_CHAR || moveChar == MapSymble.BOX_ON_GOAL_CHAR) {
	    if (goalChar == MapSymble.GROUND_CHAR || goalChar == MapSymble.BOX_CHAR) {
		res = MapSymble.BOX_CHAR;
	    } else if (goalChar == MapSymble.GOAL_CHAR || goalChar == MapSymble.BOX_ON_GOAL_CHAR) {
		res = MapSymble.BOX_ON_GOAL_CHAR;
	    }
	}
	return res;
    }

    
    @Override
    public Character getCharOfMoveAfterMove(char moveChar) {
	Character res = null;
	if (moveChar == MapSymble.MAN_CHAR || moveChar == MapSymble.BOX_CHAR) {
	    res = MapSymble.GROUND_CHAR;
	} else if (moveChar == MapSymble.MAN_ON_GOAL_CHAR || moveChar == MapSymble.BOX_ON_GOAL_CHAR) {
	    res = MapSymble.GOAL_CHAR;
	}
	return res;
    }
}
