package utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.sun.javafx.scene.traversal.Direction;

import gameengine.model.interfaces.Scrolling;
import objects.ScrollType;


public class ReflectionUtil {
	
	public static Object newInstanceOf(String className) 
			throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			ClassNotFoundException, NoSuchMethodException, SecurityException{
		Class<?> c = Class.forName(className);
		Constructor<?> constructor = c.getConstructor();
		return constructor.newInstance();
	}
	
	public static Method getMethodFromClass(String className, String methodName, Class<?>... parameterTypes)
			throws NoSuchMethodException, SecurityException, ClassNotFoundException{
		
		Class<?> c = Class.forName(className);
		return c.getMethod(methodName, parameterTypes);
	}
	
	
	
	public static Object getInstance(String classPath, Object[] parameters, Class<?>[] parameterTypes) throws ClassNotFoundException, 
																			NoSuchMethodException, SecurityException, 
																			InstantiationException, IllegalAccessException, 
																			IllegalArgumentException, InvocationTargetException{
		Class<?> classRequested = Class.forName(classPath);
		System.out.println(classRequested.getConstructors()[0]);
		Constructor<?> classConstructor = classRequested.getConstructor(parameterTypes);
		return classConstructor.newInstance(parameters); 
	}
	
}
	/**private static Class<?>[] getClassTypes(Object[] parameters) throws ClassNotFoundException {
		Class<?>[] classTypes = new Class<?>[parameters.length];
		for (int j = 0; j < parameters.length; j++) {
			//System.out.println(parameters[j].getClass());
			classTypes[j] = parameters[j].getClass();
		}
		return classTypes;
	
	}**/
