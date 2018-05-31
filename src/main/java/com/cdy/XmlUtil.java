package com.cdy;

import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * xml工具类
 * Created by 陈东一
 * 2018/5/26 14:08
 */
public class XmlUtil {
    
    private final static String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
    
    
    public static <T> String toString(Object obj) {
        XStream stream = getXStream();
        stream.processAnnotations(obj.getClass());
        return new StringBuffer(XML_DECLARATION).append(stream.toXML(obj)).toString();
    }
    
    
    public static <T> T parseXml(String string, Class<T> clazz) {
        XStream stream = getXStream();
        stream.processAnnotations(clazz);
        Object obj = stream.fromXML(string);
        try {
            return clazz.cast(obj);
        } catch (ClassCastException e) {
            return null;
        }
    }
    
    
    public static String getNodeValue(String node, String xml) {
        try {
            // 将字符串转为xml
            Document document = DocumentHelper.parseText(xml);
            // 查找节点
            Element element = (Element) document.selectSingleNode(node);
            if (element != null) {
                return element.getStringValue();
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    public static List getNodeValues(String node, String xml) {
        try {
            // 将字符串转为xml
            Document document = DocumentHelper.parseText(xml);
            // 查找节点
            List<Element> list = document.selectNodes(node);
            if (ListUtil.isNotEmpty(list)) {
                return list;
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    public static XStream getXStream() {
        return new XStream();
    }
    
    
    public static void main(String[] args) {
//        Map<String, String> map = new HashMap<>();
//        map.put("name", "cdy");
//        map.put("year", "22");
        User user = new User();
        user.setName("cdy");
        user.setYear("22");
        List<User> list = new ArrayList<>();
        list.add(user);
        list.add(user);
        String xml = toString(list);
        System.out.println(xml);
//        List<Element> year = getNodeValues("/map/entry/string", xml);
//        year.forEach(e -> System.out.println(e.getStringValue()));
    }
}

class User {
    String name;
    String year;
    
    public User() {
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getYear() {
        return year;
    }
    
    public void setYear(String year) {
        this.year = year;
    }
}