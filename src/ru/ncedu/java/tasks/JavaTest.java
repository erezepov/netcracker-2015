package ru.ncedu.java.tasks;

import sun.java2d.loops.GraphicsPrimitive;

import java.util.*;

/**
 * Created by eschy_000 on 19.11.2015.
 */

    class Outer {
void test() {
    System.out.print("Base");
}
    }
class Child extends Outer {
    void test() {
        System.out.print("chi");
    }
}


public class JavaTest {



    public static void main(String[] args) {
        Child a = new Child();
        Outer b = (Outer) a;
        b.test();
    }

}
