package ru.ncedu.java.tasks;

import java.lang.reflect.*;
import java.util.*;

public class ReflectionsImpl implements Reflections {

    public Object getFieldValueByName(Object object, String fieldName) throws NoSuchFieldException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    public Set<String> getProtectedMethodNames(Class clazz) {
        Set<String> nameSet = new HashSet<>();
        for(Method method: clazz.getDeclaredMethods()) {
            if (Modifier.isProtected(method.getModifiers())) {
                nameSet.add(method.getName());
            }
        }
        return nameSet;
    }

    public Set<Method> getAllImplementedMethodsWithSupers(Class clazz) {
        Set<Method> allMethods = new HashSet<>(Arrays.asList(clazz.getDeclaredMethods()));
        if(clazz.getSuperclass() != null){
            allMethods.addAll(getAllImplementedMethodsWithSupers(clazz.getSuperclass()));
        }
        return allMethods;
    }

    public List<Class> getExtendsHierarchy(Class clazz) {
        List<Class> hierarchy = new LinkedList<>();
        while ((clazz = clazz.getSuperclass()) != null) {
            hierarchy.add(clazz);
        }
        return hierarchy;
    }

    public Set<Class> getImplementedInterfaces(Class clazz) {
        return new HashSet<>(Arrays.asList(clazz.getInterfaces()));
    }

    public List<Class> getThrownExceptions(Method method) {
        List<Class> exceptionList = new LinkedList<>();
        for (int i = 0; i < Array.getLength(method.getExceptionTypes()); ++i) {
            exceptionList.add(method.getExceptionTypes()[i]);
        }
        return exceptionList;
    }

    public String getFooFunctionResultForDefaultConstructedClass() {
        try {
            Constructor<SecretClass> constructor = SecretClass.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            SecretClass secretClass = constructor.newInstance();
            Method foo = secretClass.getClass().getDeclaredMethod("foo");
            foo.setAccessible(true);
            return (String) foo.invoke(secretClass);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException
                | InvocationTargetException e) {
            System.err.println(":(");
            return null;
        }
    }

    public String getFooFunctionResultForClass(String constructorParameter, String string, Integer... integers) {
        try {
            Constructor<SecretClass> constructor = SecretClass.class.getDeclaredConstructor(String.class);
            constructor.setAccessible(true);
            SecretClass secretClass = constructor.newInstance(constructorParameter);
            Method foo = secretClass.getClass().getDeclaredMethod("foo", new Class[]{String.class, Integer[].class});
            foo.setAccessible(true);
            return (String) foo.invoke(secretClass, string, integers);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException
                | InvocationTargetException e) {
            System.err.println(":(");
            return null;
        }
    }
}
