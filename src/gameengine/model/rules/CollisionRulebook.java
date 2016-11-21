package gameengine.model.rules;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import gameengine.controller.RuleActionHandler;
<<<<<<< HEAD
import gameengine.model.rules.collisionrules.CollisionRule;
=======
>>>>>>> master
import objects.GameObject;
import utils.ReflectionUtil;
import utils.ResourceReader;

public class CollisionRulebook{
	private ResourceReader resources;
	private RuleActionHandler handler;
	
	public CollisionRulebook(RuleActionHandler handler) {
		resources = new ResourceReader("resources/GameEngineObjectProperties");
		this.handler = handler;
	}
	//might need to fully specify classpath to rule in properties file, instead of just rule name
	 
	public void applyRules(GameObject mainChar, GameObject obj) throws ClassNotFoundException{
		for(String property: obj.getPropertiesList()){
			String ruleName = resources.getResource(property);
			try {
				Method method = ReflectionUtil.getMethodFromClass(ruleName, "applyRule");
				method.invoke(mainChar, obj);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | ClassNotFoundException | NoSuchMethodException
					| SecurityException e) {
				throw new ClassNotFoundException();
			}
		}
	}
}
