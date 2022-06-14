package ru.ncedu.java.tasks;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.*;

public class XPathCallerImpl implements XPathCaller {
    public Element[] getEmployees(Document src, String deptno, String docType) {
        XPath xPath = XPathFactory.newInstance().newXPath();
        try {
            XPathExpression expression = xPath.compile("//employee[@deptno='" + deptno + "']");
            NodeList nodeList = (NodeList) expression.evaluate(src, XPathConstants.NODESET);
            Element[] elements = new Element[nodeList.getLength()];
            for (int i = 0; i < nodeList.getLength(); ++i) {
                elements[i] = (Element) nodeList.item(i);
            }
            return elements;
        } catch (XPathExpressionException e) {
            System.err.println(":(");
            return new Element[0];
        }
    }

    public String getHighestPayed(Document src, String docType) {
        XPath xPath = XPathFactory.newInstance().newXPath();
        try {
            XPathExpression expression = xPath.compile("//employee/sal");
            NodeList nodeList = (NodeList) expression.evaluate(src, XPathConstants.NODESET);
            String highestPayed;
            double maxSalary = 0;
            Node temp;

            for (int i = 0; i < nodeList.getLength(); ++i) {
                temp = nodeList.item(i).getFirstChild();
                if (Double.parseDouble(temp.getNodeValue()) > maxSalary) {
                    maxSalary = Double.parseDouble(temp.getNodeValue());
                }
            }
            expression = xPath.compile("//employee[sal=" + maxSalary +"]/ename");
            highestPayed = ((Node) expression.evaluate(src, XPathConstants.NODE)).getFirstChild().getNodeValue();

            return highestPayed;
        } catch (XPathExpressionException e) {
            System.err.println(":(");
            return "";
        }
    }

    public String getHighestPayed(Document src, String deptno, String docType) {
        XPath xPath = XPathFactory.newInstance().newXPath();
        try {
            XPathExpression expression = xPath.compile("//employee[@deptno='" + deptno + "']/sal");
            NodeList nodeList = (NodeList) expression.evaluate(src, XPathConstants.NODESET);
            String highestPayed;
            double maxSalary = 0;
            Node temp;

            for (int i = 0; i < nodeList.getLength(); ++i) {
                temp = nodeList.item(i).getFirstChild();
                if (Double.parseDouble(temp.getNodeValue()) > maxSalary) {
                    maxSalary = Double.parseDouble(temp.getNodeValue());
                }
            }
            expression = xPath.compile("//employee[sal=" + maxSalary +"]/ename");
            highestPayed = ((Node) expression.evaluate(src, XPathConstants.NODE)).getFirstChild().getNodeValue();

            return highestPayed;
        } catch (XPathExpressionException e) {
            System.err.println(":(");
            return "";
        }
    }

    public Element[] getTopManagement(Document src, String docType) {
        XPath xpath = XPathFactory.newInstance().newXPath();
        String expression = null;
        if (docType.equals("emp-hier")){
            expression = "//employee[count(ancestor::*) = 0]";
        } else {
            expression = "//employee[not(@mgr)]";
        }
        NodeList nodeList = null;
        try {
            nodeList = (NodeList) xpath.compile(expression).evaluate(src, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {

        }

        return toElementArray(nodeList);
    }

    public Element[] getOrdinaryEmployees(Document src, String docType) {
        XPath xpath = XPathFactory.newInstance().newXPath();
        String expression = null;
        if(docType.equals("emp-hier")){
            expression = "//employee[not(./employee)]";
        } else{
            expression = "//employee[not(@empno = (//@mgr))]";
        }

        NodeList nodeList = null;

        try {
            nodeList = (NodeList) xpath.compile(expression).evaluate(src, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {

        }
        return toElementArray(nodeList);
    }

    public Element[] getCoworkers(Document src, String empno, String docType) {
        XPath xPath = XPathFactory.newInstance().newXPath();
        String expression;
        if(docType.equals("emp-hier")){
            expression = "//employee[@empno = '" + empno + "']/ancestor::*[1]/child::employee[@empno != '" + empno + "']";
        } else {
            expression = "//employee[@mgr = //employee[@empno = //employee[@empno = '" + empno + "']/@mgr]/@empno and @empno != '" + empno + "']";
        }
        NodeList nodeList = null;
        try {
            nodeList = (NodeList) xPath.compile(expression).evaluate(src, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {

        }
        return toElementArray(nodeList);
    }

    private Element[] toElementArray(NodeList list){
        Element[] elements = new Element[list.getLength()];
        for (int i = 0; i < elements.length; ++i){
            if (list.item(i).getNodeType() == Node.ELEMENT_NODE){
                Element temp = (Element)list.item(i);
                NodeList tempList = temp.getChildNodes();
                for (int j = 0; j < tempList.getLength(); ++j){
                    if(tempList.item(j).getNodeType() == Node.TEXT_NODE){
                        temp.appendChild(tempList.item(j));
                    }
                }
                elements[i] = temp;
            }
        }
        return elements;
    }
}
