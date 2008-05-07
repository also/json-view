/* $Id$ */

package org.ry1.json.view;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class JsonViewNamespaceHandler extends NamespaceHandlerSupport {

	public void init() {
		registerBeanDefinitionParser("list", new JsonBeanDefinitionParser());
	}

}
