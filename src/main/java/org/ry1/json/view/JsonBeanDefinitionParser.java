/* $Id$ */

package org.ry1.json.view;

import java.util.List;

import org.ry1.json.MethodInvokingTransformer;
import org.ry1.json.PropertyList;
import org.ry1.json.PropertyListElement;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

public class JsonBeanDefinitionParser extends AbstractBeanDefinitionParser {
	
	@Override
	protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
		return parsePropertyList(element);
	}
	
	@SuppressWarnings("unchecked")
	private AbstractBeanDefinition parsePropertyList(Element element) {
		ManagedList elements = new ManagedList();
		
		List<Element> propertyChildren = DomUtils.getChildElementsByTagName(element, "property");
		for (Element propertyElement : propertyChildren) {
			elements.add(parseProperty(propertyElement, false));
		}
		
		List<Element> listChildren = DomUtils.getChildElementsByTagName(element, "list");
		for (Element listElement : listChildren) {
			elements.add(parseProperty(listElement, true));
		}
		
		if (elements.size() > 0) {
			BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(PropertyList.class);
			builder.addPropertyValue("elements", elements);
			return builder.getBeanDefinition();
		}
		
		return null;
	}
	
	private AbstractBeanDefinition parseProperty(Element element, boolean list) {
		BeanDefinitionBuilder elementBuilder = BeanDefinitionBuilder.rootBeanDefinition(PropertyListElement.class);
		elementBuilder.addConstructorArg(element.hasAttribute("name") ? element.getAttribute("name") : null);
		elementBuilder.addConstructorArg(element.hasAttribute("target") ? element.getAttribute("target") : null);
		
		Element transformerElement = DomUtils.getChildElementByTagName(element, "transformer");
		if (transformerElement != null) {
			if (transformerElement.hasAttribute("method")) {
				BeanDefinitionBuilder transformerBuilder = BeanDefinitionBuilder.rootBeanDefinition(MethodInvokingTransformer.class);
				transformerBuilder.addConstructorArgReference(transformerElement.getAttribute("ref"));
				transformerBuilder.addConstructorArg(transformerElement.getAttribute("method"));
				elementBuilder.addConstructorArg(transformerBuilder.getBeanDefinition());
			}
			else {
				elementBuilder.addConstructorArgReference(transformerElement.getAttribute("ref"));
			}
		}
		else if (element.hasAttribute("transformer-ref")) {
			elementBuilder.addConstructorArgReference(element.getAttribute("transformer-ref"));
		}
		else {
			elementBuilder.addConstructorArg(null);
		}
		
		if (element.hasAttribute("ref")) {
			elementBuilder.addConstructorArgReference(element.getAttribute("ref"));
		}
		else {
			elementBuilder.addConstructorArg(parsePropertyList(element));
		}
		elementBuilder.addConstructorArg(list);
		
		return elementBuilder.getBeanDefinition();
	}
}
