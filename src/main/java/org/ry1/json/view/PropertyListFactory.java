/* $Id$ */

package org.ry1.json.view;

import org.ry1.json.PropertyList;
import org.springframework.beans.factory.FactoryBean;

public class PropertyListFactory implements FactoryBean {
	private PropertyList propertyList;
	
	public void setPropertyList(PropertyList propertyList) {
		this.propertyList = propertyList;
	}
	
	public Object getObject() throws Exception {
		return propertyList;
	}

	@SuppressWarnings("unchecked")
	public Class getObjectType() {
		return PropertyList.class;
	}

	public boolean isSingleton() {
		return true;
	}

}
