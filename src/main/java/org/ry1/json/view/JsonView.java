/* $Id$ */

package org.ry1.json.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ry1.json.JsonObject;
import org.ry1.json.PropertyList;
import org.springframework.web.servlet.View;

public class JsonView implements View {
	private PropertyList propertyList;
	private String beanName;

	public JsonView(PropertyList propertyList) {
		this(propertyList, null);
	}

	public JsonView(PropertyList propertyList, String beanName) {
		this.propertyList = propertyList;
		this.beanName = beanName;
	}

	public String getContentType() {
		return "text/javascript";
	}

	@SuppressWarnings("unchecked")
	public void render(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Object bean; 
		if (beanName != null) {
			bean = model.get(beanName);
		}
		else if(model.size() == 1) {
			bean = model.values().iterator().next();
		}
		else {
			// TODO exception type
			throw new RuntimeException("Bean name not specified");
		}
		
		response.getWriter().print(new JsonObject(bean, propertyList));
	}

}
