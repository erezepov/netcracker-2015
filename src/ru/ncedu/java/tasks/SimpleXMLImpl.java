package ru.ncedu.java.tasks;

import org.xml.sax.SAXException;

import org.w3c.dom.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;


public class SimpleXMLImpl implements SimpleXML {
    public String createXML(String tagName, String textNode) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setValidating(false);
        try {
            Writer result = new StringWriter();
            Document document = factory.newDocumentBuilder().newDocument();
            Element root = document.createElement(tagName);
            root.appendChild(document.createTextNode(textNode));
            document.appendChild(root);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.transform(new DOMSource(document), new StreamResult(result));
            return result.toString();
        } catch (javax.xml.parsers.ParserConfigurationException |
                javax.xml.transform.TransformerException e) {
            System.err.println("Oh no!");
            return null;
        }
    }

    public String parseRootElement(InputStream xmlStream) throws SAXException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        saxParserFactory.setNamespaceAware(false);
        saxParserFactory.setValidating(true);
        try {
            SAXParser parser = saxParserFactory.newSAXParser();
            StringBuffer rootName = new StringBuffer();
            DefaultHandler handler = new MyHandler(rootName);
            parser.parse(xmlStream, handler);
            return rootName.toString();
        } catch (ParserConfigurationException | IOException e) {
            System.err.println("Oops!");
            return null;
        }

    }

    private class MyHandler extends DefaultHandler {

        StringBuffer rootName;
        boolean ok;

        MyHandler(StringBuffer rootName) {
            this.rootName = rootName;
            ok = false;
        }

        public void startElement (String uri, String localName,
                                  String qName, Attributes attributes)
                throws SAXException
        {
            if (!ok) {
                rootName.append(qName);
                ok = true;
            }
        }
    }
}
