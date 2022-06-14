package ru.ncedu.java.tasks;


import java.io.ByteArrayInputStream;

/**
 * Created by eschy_000 on 13.11.2015.
 */
public class SimpleXMLTest {
    public static void main (String[] args) {
        SimpleXML simpleXML = new SimpleXMLImpl();
        System.out.println(simpleXML.createXML("root", "<R&D>"));
        try {
            System.out.println(simpleXML.parseRootElement(new ByteArrayInputStream("<prefix:root>simple</prefix:root>".getBytes())));
        } catch (Exception e) {
            System.err.println("Noooooooooooooooo!");
        }
    }
}
