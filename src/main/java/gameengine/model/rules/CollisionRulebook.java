package gameengine.model.rules;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import exception.CollisionRuleNotFoundException;
import exception.MovementRuleNotFoundException;
import gameengine.controller.interfaces.RuleActionHandler;
import objects.GameObject;
import utils.ReflectionUtil;
import utils.ResourceReader;

public class CollisionRulebook{
    private static final String resourcesPath = "GameEngineCollisionProperties";
    private static final String rulesPath = "gameengine.model.rules.collisionrules.";
	private ResourceReader resources;
	private RuleActionHandler handler;
	
	
	public CollisionRulebook(RuleActionHandler handler) {
		resources = new ResourceReader(resourcesPath);
		this.handler = handler;
	}
	 
	public void applyRules(GameObject mainChar, GameObject obj) throws CollisionRuleNotFoundException{
		for(String property: obj.getPropertiesList()){
			if(resources.containsResource(property)) {
				String ruleName = rulesPath + resources.getResource(property);
	        		Object[] parameters = new Object[]{handler, mainChar, obj};
	        		Class<?>[] parameterTypes = new Class<?>[]{RuleActionHandler.class, GameObject.class, GameObject.class};
	                try {
						ReflectionUtil.runMethod(ruleName, "applyRule", parameters, parameterTypes);
					} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException
							| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						throw (new CollisionRuleNotFoundException());
					}
			}
		}
	}
}
