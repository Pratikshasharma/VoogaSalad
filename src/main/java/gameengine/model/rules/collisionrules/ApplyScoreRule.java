package gameengine.model.rules.collisionrules;

import gameengine.controller.interfaces.RuleActionHandler;

import objects.GameObject;

public class ApplyScoreRule implements CollisionRule{

    public void applyRule(RuleActionHandler handler, GameObject mainChar, GameObject obj) {
            handler.modifyScore(Integer.parseInt(obj.getProperty("points")));
    }
}