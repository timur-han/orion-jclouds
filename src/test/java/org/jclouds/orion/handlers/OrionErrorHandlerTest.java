/**
 * Licensed to jclouds, Inc. (jclouds) under one or more
 * contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  jclouds licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jclouds.orion.handlers;

import java.net.URI;

import org.easymock.EasyMock;
import org.easymock.IArgumentMatcher;
import org.testng.annotations.Test;

/**
 * 
 * @author Timur Sungur
 */
@Test(groups = "unit", testName = "OrionErrorHandlerTest")
public class OrionErrorHandlerTest {

	@Test
	public void test409MakesIllegalStateException() {
		assertCodeMakes(
				"POST",
				URI.create("https://orionhub.org"),
				409,
				"HTTP/1.1 409 Conflict",
				"\"{\"code\":\"InvalidState\",\"message\":\"An incompatible transition has already been queued for this resource\"}\"",
				IllegalStateException.class);
	}

	private void assertCodeMakes(String method, URI uri, int statusCode,
			String message, String content, Class<? extends Exception> expected) {
		assertCodeMakes(method, uri, statusCode, message, "application/json",
				content, expected);
	}

	private void assertCodeMakes(String method, URI uri, int statusCode,
			String message, String contentType, String content,
			Class<? extends Exception> expected) {

		/*
		 * OrionErrorHandler function = new OrionErrorHandler();
		 * 
		 * HttpCommand command = createMock(HttpCommand.class); HttpRequest
		 * request = HttpRequest.builder().method(method).endpoint(uri).build();
		 * HttpResponse response =
		 * HttpResponse.builder().statusCode(statusCode).
		 * message(message).payload(content).build();
		 * response.getPayload().getContentMetadata
		 * ().setContentType(contentType);
		 * 
		 * expect(command.getCurrentRequest()).andReturn(request).atLeastOnce();
		 * command.setException(classEq(expected));
		 * 
		 * replay(command);
		 * 
		 * function.handleError(command, response);
		 * 
		 * verify(command);
		 */
	}

	public static Exception classEq(final Class<? extends Exception> in) {
		EasyMock.reportMatcher(new IArgumentMatcher() {

			@Override
			public void appendTo(StringBuffer buffer) {
				buffer.append("classEq(");
				buffer.append(in);
				buffer.append(")");
			}

			@Override
			public boolean matches(Object arg) {
				return arg.getClass() == in;
			}

		});
		return null;
	}

}
