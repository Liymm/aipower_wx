package com.aipower.utils;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class XmlUtil {
    public static Object fromXml(String xml, Class objClass){
        Serializer serializer = new Persister();
        try {
            return serializer.read(objClass, xml);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
