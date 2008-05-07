/* $Id$ */

package org.ry1.json.view;

import java.util.Locale;

import org.ry1.json.PropertyList;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

public class JsonViewResolver implements ViewResolver, BeanFactoryAware, Ordered {
	private String matchingPrefix = "json:";
	private BeanFactory beanFactory;
	private int order;
	
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public void setMatchingPrefix(String matchingPrefix) {
		this.matchingPrefix = matchingPrefix;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
	
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		if (matchingPrefix != null) {
			if (!viewName.startsWith(matchingPrefix)) {
				return null;
			}
			else {
				viewName = viewName.substring(matchingPrefix.length());
			}
		}
		
		PropertyList propertyList = (PropertyList) beanFactory.getBean(viewName, PropertyList.class);
		
		if (propertyList != null) {
			return new JsonView(propertyList);
		}
		else {
			return null;
		}
	}

}
