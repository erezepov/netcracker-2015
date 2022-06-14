package ru.ncedu.java.tasks;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by eschy_000 on 13.10.2015.
 */
public class ReflectionsTest {
    public static void main (String[] args) {
        Set<Method> methods = new ReflectionsImpl().getAllImplementedMethodsWithSupers(HashMap.class);
    }
}
