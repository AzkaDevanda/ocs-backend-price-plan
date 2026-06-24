package com.sts.sinorita.utils;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.StringReader;
import java.util.List;

public class XMLUtils {

    public static Document createDocument(String xmlSource, boolean validate, String encoding) throws Exception {
        SAXReader reader = new SAXReader();
        reader.setValidation(validate);
        return reader.read(new StringReader(xmlSource));
    }
    public static Element child(Element parent, String name) {
        return parent.element(name);
    }
    public static List<Element> children(Element parent, String name) {
        return parent.elements(name);
    }
}
