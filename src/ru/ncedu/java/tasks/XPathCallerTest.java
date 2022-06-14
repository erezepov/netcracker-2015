package ru.ncedu.java.tasks;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;

/**
 * Created by eschy_000 on 15.11.2015.
 */
public class XPathCallerTest {
    public static void main (String[] args) {
        XPathCaller xPathCaller = new XPathCallerImpl();
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new FileInputStream("D:\\development\\javaworks\\netcracker-2015\\src\\ru\\ncedu\\java\\tasks\\emp.xml"));
            Element[] elements = xPathCaller.getEmployees(document, "30", "");

            xPathCaller.getCoworkers(document, "7782", "");
        } catch (Exception e) {
            System.err.println("fuck");
        }
    }
}
