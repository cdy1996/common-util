package com.cdy.export;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


/**
 * xml工具类
 * Created by 陈东一
 * 2018/6/5 22:31
 */
public class XmlUtil {
    
    /**
     * 生成空的xml文件头
     *
     * @param xmlPath String
     * @return Document
     */
    public static Document createEmptyXmlFile(String xmlPath) throws IOException {
        if (xmlPath == null || "".equals(xmlPath)) {
            return null;
        }
        XMLWriter output;
        Document document = DocumentHelper.createDocument();
        OutputFormat format = OutputFormat.createPrettyPrint();
        output = new XMLWriter(new FileWriter(xmlPath), format);
        output.write(document);
        output.close();
        
        return document;
    }
    
    /**
     * 根据xml文件路径取得document对象
     *
     * @param xmlPath String
     * @return Document
     */
    public static Document getDocument(String xmlPath) throws IOException, DocumentException {
        if (xmlPath == null || "".equals(xmlPath)) {
            return null;
        }
        File file = new File(xmlPath);
        if (!file.exists()) {
            return createEmptyXmlFile(xmlPath);
        }
        SAXReader reader = new SAXReader();
        return reader.read(xmlPath);
    }
    
    /**
     * 获取xml根节点
     * @param document Document
     * @return Element
     */
    public static Element getRootNode(Document document) {
        if (document == null) {
            return null;
        }
        return document.getRootElement();
    }
    
    /**
     * 获取xml根节点
     * @param xmlPath String
     * @return Element
     */
    public static Element getRootNode(String xmlPath) throws IOException, DocumentException {
        if (xmlPath == null || "".equals(xmlPath.trim())) {
            return null;
        }
        Document document = getDocument(xmlPath);
        if (document == null) {
            return null;
        }
        return getRootNode(document);
    }
    
    
    /**
     * 根据子节点名称得到指定的子节点
     * @param parent Element
     * @param childName String
     * @return List<Element>
     */
    @SuppressWarnings("unchecked")
    public static List<Element> getChildElements(Element parent, String childName) {
        childName = childName.trim();
        if (parent == null) {
            return null;
        }
        childName += "//";
        return (List<Element>) parent.selectNodes(childName);
    }
    
    /**
     * 获取所有子节点
     * @param parent Element
     * @return List<Element>
     */
    public static List<Element> getChildElements(Element parent) {
        if (parent == null) {
            return null;
        }
        Iterator<Element> itr = parent.elementIterator();
        if (itr == null) {
            return null;
        }
        List<Element> childList = new ArrayList<>();
        while (itr.hasNext()) {
            Element kidElement = itr.next();
            if (kidElement != null) {
                childList.add(kidElement);
            }
        }
        return childList;
    }
    
    
    /**
     * 判断节点是否还有子节点
     * @param e Element
     * @return boolean
     */
    public static boolean hasChild(Element e) {
        if (e == null) {
            return false;
        }
        return e.hasContent();
    }
    
    /**
     * 得到指定节点的属性的迭代器
     * @param e Element
     * @return Iterator<Attribute>
     */
    @SuppressWarnings("unchecked")
    public static Iterator<Attribute> getAttrIterator(Element e) {
        if (e == null) {
            return null;
        }
        return (Iterator<Attribute>) e.attributeIterator();
    }
    
    /**
     * 遍历指定节点的所有属性
     * @param e Element
     * @return List<Attribute>
     */
    public static List<Attribute> getAttributeList(Element e) {
        if (e == null) {
            return null;
        }
        List<Attribute> attributeList = new ArrayList<Attribute>();
        Iterator<Attribute> atrIterator = getAttrIterator(e);
        if (atrIterator == null) {
            return null;
        }
        while (atrIterator.hasNext()) {
            Attribute attribute = atrIterator.next();
            attributeList.add(attribute);
        }
        return attributeList;
    }
    
    /**
     * 得到指定节点的指定属性
     * @param element Element
     * @param attrName String
     * @return Attributes
     */
    public static Attribute getAttribute(Element element, String attrName) {
        attrName = attrName.trim();
        if (element == null) {
            return null;
        }
        if ("".equals(attrName)) {
            return null;
        }
        return element.attribute(attrName);
    }
    
    /**
     * 获取指定节点指定属性的值
     * @param e Element
     * @param attrName String
     * @return String
     */
    public static String attrValue(Element e, String attrName) {
        return getAttribute(e, attrName).getValue();
    }
    
    /**
     * 得到指定节点的所有属性及属性值
     * @param e Element
     * @return Map<String, String>
     */
    public static Map<String, String> getNodeAttrMap(Element e) {
        Map<String, String> attrMap = new HashMap<>();
        if (e == null) {
            return null;
        }
        List<Attribute> attributes = getAttributeList(e);
        if (attributes == null) {
            return null;
        }
        for (Attribute attribute : attributes) {
            String attrValueString = attrValue(e, attribute.getName());
            attrMap.put(attribute.getName(), attrValueString);
        }
        return attrMap;
    }
    
    /**
     * @param e
     * @return @参数描述 :
     * @方法功能描述: 遍历指定节点的下没有子节点的元素的text值
     * @方法名:getSingleNodeText
     * @返回类型：Map<String,String>
     * @时间：2011-4-15下午12:24:38
     */
    public static Map<String, String> getSingleNodeText(Element e) {
        Map<String, String> map = new HashMap<String, String>();
        if (e == null) {
            return null;
        }
        List<Element> kids = getChildElements(e);
        for (Element e2 : kids) {
            if (e2.getTextTrim() != null) {
                map.put(e2.getName(), e2.getTextTrim());
            }
        }
        return map;
    }
    

    

    
    /**
     * 根据xml路径和指定的节点的名称，得到指定节点,从根节点开始找
     * @param xmlFilePath
     * @param tagName
     * @param <T>
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    @SuppressWarnings("unchecked")
    public static <T> T getNameNode(String xmlFilePath, String tagName) throws IOException, DocumentException {
        xmlFilePath = xmlFilePath.trim();
        tagName = tagName.trim();
        if (xmlFilePath == null || tagName == null || "".equals(xmlFilePath) || "".equals(tagName)) {
            return null;
        }
        Element rootElement = getRootNode(xmlFilePath);
        if (rootElement == null) {
            return null;
        }
        List<Element> tagElementList = getNameElement(rootElement, tagName);
        if (tagElementList == null) {
            return null;
        }
        return (T) tagElementList;
    }
    
    /**
     * 得到指定节点下所有子节点的属性集合
     * @param parent Element
     * @return Map<Integer, Object>
     */
    public static Map<Integer, Object> getNameNodeAllKidsAttributeMap(Element parent) {
        Map<Integer, Object> allAttrMap = new HashMap<>();
        if (parent == null) {
            return null;
        }
        List<Element> childlElements = getChildElements(parent);
        if (childlElements == null) {
            return null;
        }
        for (int i = 0; i < childlElements.size(); i++) {
            Element childElement = childlElements.get(i);
            Map<String, String> attrMap = getNodeAttrMap(childElement);
            allAttrMap.put(i, attrMap);
        }
        return allAttrMap;
    }
    
    
    /**
     * 遍历指定的节点下所有的节点以及子节点
     * @param element Element
     * @param allkidsList null
     * @return List<Element>
     */
    public static List<Element> ransack(Element element, List<Element> allkidsList) {
        if (element == null) {
            return null;
        }
        if (hasChild(element)) {
            List<Element> kids = getChildElements(element);
            for (Element e : kids) {
                allkidsList.add(e);
                ransack(e, allkidsList);
            }
        }
        return allkidsList;
    }
    
    /**
     * 得到指定节点下的指定节点集合
     * @param element Element
     * @param nodeName String
     * @return List<Element>
     */
    public static List<Element> getNameElement(Element element, String nodeName) {
        nodeName = nodeName.trim();
        List<Element> kidsElements = new ArrayList<Element>();
        if (element == null) {
            return null;
        }
        if ("".equals(nodeName)) {
            return null;
        }
        List<Element> allKids = ransack(element, new ArrayList<Element>());
        if (allKids == null) {
            return null;
        }
        for (Element kid : allKids) {
            if (nodeName.equals(kid.getName())) {
                kidsElements.add(kid);
            }
        }
        return kidsElements;
    }
    
    /**
     * 验证节点是否唯一
     * @param element Element
     * @return int
     */
    public static int validateSingle(Element element) {
        int j = 1;
        if (element == null) {
            return j;
        }
        Element parent = element.getParent();
        List<Element> kids = getChildElements(parent);
        for (Element kid : kids) {
            if (element.equals(kid)) {
                j++;
            }
        }
        return j;
    }
}