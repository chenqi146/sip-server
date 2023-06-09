package com.cqmike.sip.core.gb28181.helper;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.util.Optional;

/**
 * XmlHelper
 *
 * @author cqmike
 **/
public final class XmlHelper {

    /**
     * 转换xml为bean
     *
     * @author cqmike
     * @param xml
     * @param tClass
     * @since 1.0.0
     * @return
     */
    public static <T> T xmlToBean(String xml, Class<T> tClass) throws JAXBException {

        JAXBContext jaxbContext = JAXBContext.newInstance(tClass);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (T) unmarshaller.unmarshal(new StringReader(xml));
    }

    /**
     * 获取xml元素中间的文本值
     *
     * @author cqmike
     * @param element
     * @param tag
     * @since 1.0.0
     * @return
     */
    public static String getText(Element element, String tag) {
        return Optional.ofNullable(element)
                .map(e -> e.element(tag))
                .map(Element::getText)
                .orElse(null);
    }

    /**
     * 获取根元素节点
     *
     * @author cqmike
     * @param rawContent
     * @since 1.0.0
     * @return
     */
    public static Element getRootElement(byte[] rawContent) throws DocumentException {
        SAXReader reader = new SAXReader();
        reader.setEncoding("gb2312");
        Document xml = reader.read(new ByteArrayInputStream(rawContent));
        return xml.getRootElement();
    }

}
