package zwh.move.rule;

public interface IMapMoveRule {
    /**
     * 返回当moveChar移动到goalChar后，goalChar所处位置应该表示的字符
     * @param goalChar 目标地点字符（只能为GROUND,GOAL,BOX,BOX_ON_GOAL）
     * @param moveChar 移动的字符（只能为人和箱子，只能为MAN,BOX,MAN_ON_GOAL,BOX_ON_GOAL）
     * @return 移动后目标地点应该显示的字符
     * @see MapSymble
     */
    Character getCharOfGoalAfterMove(char goalChar, char moveChar);
    
    /**
     * 返回moveChar移动后，原来所处的位置应该显示的字符
     * @param moveChar 移动的字符（只能为人和箱子，只能为MAN,BOX,MAN_ON_GOAL,BOX_ON_GOAL）
     * @return 移动后原来地点应该显示的字符
     * @see MapSymble
     */
    Character getCharOfMoveAfterMove(char moveChar);
}
