/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.orion.domain.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Map.Entry;

import org.jclouds.orion.domain.MutableBlobProperties;

import com.google.common.base.Preconditions;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * @author timur
 * 
 */
public class MutableBlobPropertiesSerializer implements
	JsonDeserializer<MutableBlobProperties> {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement,
     * java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
     */
    @Override
    public MutableBlobProperties deserialize(JsonElement from, Type arg1,
	    JsonDeserializationContext arg2) throws JsonParseException {
	MutableBlobProperties to = new MutableBlobPropertiesImpl();
	try {
	    convertObject(to, from.getAsJsonObject());
	} catch (IllegalAccessException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IllegalArgumentException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (InvocationTargetException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return to;
    }

    private void convertObject(Object obj, JsonObject jsonObject)
	    throws IllegalAccessException, IllegalArgumentException,
	    InvocationTargetException {
	for (Entry<String, JsonElement> jsonEntry : jsonObject
		.getAsJsonObject().entrySet()) {
	    if (jsonEntry.getValue().isJsonPrimitive()) {
		setPrimitive(obj, jsonEntry);
	    } else if (jsonEntry.getValue().isJsonObject()) {
		convertObject(
			getGetMethod(obj, jsonEntry.getKey()).invoke(obj),
			jsonEntry.getValue().getAsJsonObject());
	    } else if (jsonEntry.getValue().isJsonArray()) {
		// TODO array case needs to be implemented
	    }
	}
    }

    /**
     * @param obj
     * @param fieldName
     * @return
     */
    private Method getGetMethod(Object obj, String fieldName) {
	for (Method method : obj.getClass().getMethods()) {
	    // check if method name is a setKey
	    if (method.getName().startsWith(
		    "get" + fieldName.substring(0, 1).toUpperCase()
			    + fieldName.substring(1))) {
		// check if the method has only one parameter
		if (method.getParameterTypes().length == 0) {
		    return method;
		}

	    }
	}
	return null;
    }

    /**
     * @param to
     * @param jsonEntry
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    private void setPrimitive(Object to, Entry<String, JsonElement> jsonEntry)
	    throws IllegalAccessException, IllegalArgumentException,
	    InvocationTargetException {
	Method setMethod = Preconditions.checkNotNull(
		getSetMethod(to, jsonEntry.getKey()),
		"method could not be found");
	// Return type of getAs and the setter/put parameter type
	// should be the same
	for (Method jsonMethod : jsonEntry.getValue().getClass().getMethods()) {
	    int index = setMethod.getParameterTypes().length;
	    if (index > 0) {
		index--;
	    }
	    if (jsonMethod.getName().startsWith("getAs")) {
		if ((setMethod.getParameterTypes()[index] == jsonMethod
			.getReturnType())
			|| ((jsonMethod.getReturnType() == String.class) && (setMethod
				.getParameterTypes()[index] == Object.class))
			|| ((Number.class.isAssignableFrom(jsonMethod
				.getClass())) && Number.class
				.isAssignableFrom(setMethod.getParameterTypes()[index]))) {
		    if (index == 0) {
			setMethod.invoke(to,
				jsonMethod.invoke(jsonEntry.getValue()));
		    } else if (index == 1) {
			setMethod.invoke(to, jsonEntry.getKey(),
				jsonMethod.invoke(jsonEntry.getValue()));
		    }
		}
	    }
	}
    }

    /**
     * Only works with bean naming conventions
     * 
     * @param to
     * @param fieldName
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    private Method getSetMethod(Object to, String fieldName)
	    throws IllegalAccessException, IllegalArgumentException,
	    InvocationTargetException {
	for (Method method : to.getClass().getMethods()) {
	    // check if method
	    // name is a setKey
	    // 2 options have been considered a map or a java bean

	    if (method.getName().startsWith(
		    "set" + fieldName.substring(0, 1).toUpperCase()
			    + fieldName.substring(1))
		    || (Map.class.isAssignableFrom(to.getClass()) && method
			    .getName().startsWith("put"))) {

		return method;

	    }
	}
	return null;
    }
}
