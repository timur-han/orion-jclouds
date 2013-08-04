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
 * Unles required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either expres or implied.  See the License for the
 * specific language governing permisions and limitations
 * under the License.
 */
package org.jclouds.orion.features;

import static org.testng.Assert.assertEquals;

import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpResponse;
import org.jclouds.orion.OrionApi;
import org.jclouds.orion.internal.BaseOrionApiExpectTest;
import org.jclouds.orion.parse.ParseKeyListTest;
import org.jclouds.orion.parse.ParseKeyTest;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableSet;

/**
 * @author Timur Sungur
 */
@Test(groups = "unit", testName = "KeyApiExpectTest")
public class KeyApiExpectTest extends BaseOrionApiExpectTest {
   public HttpRequest list = HttpRequest.builder().method("GET")
                                        .endpoint("https://orionhub.org/my/keys")
                                        .addHeader("X-Api-Version", "0.0.1")
                                        .addHeader("Accept", "application/json")
                                        .addHeader("Authorization", "Basic aWRlbnRpdHk6Y3JlZGVudGlhbA==").build();
   
   public HttpResponse listResponse = HttpResponse.builder().statusCode(200).payload(
            payloadFromResource("/key_list.json")).build();

   public void testListKeysWhenResponseIs2xx() {

      OrionApi apiWhenKeysExists = requestSendsResponse(list, listResponse);

      assertEquals(apiWhenKeysExists.getKeyApi().list(), new ParseKeyListTest().expected());
   }

   public void testListKeysWhenResponseIs404() {
      HttpResponse listResponse = HttpResponse.builder().statusCode(404).build();

      OrionApi listWhenNone = requestSendsResponse(list, listResponse);

      assertEquals(listWhenNone.getKeyApi().list(), ImmutableSet.of());
   }

   public void testCreateKeyWhenResponseIs202() throws Exception {
      HttpRequest create = HttpRequest.builder()
               .method("POST")
               .endpoint("https://orionhub.org/my/keys")
               .addHeader("X-Api-Version", "0.0.1")
               .addHeader("Accept", "application/json")
               .addHeader("Authorization", "Basic aWRlbnRpdHk6Y3JlZGVudGlhbA==")
               .payload(
                     payloadFromStringWithContentType(
                           "{\"name\":\"rsa\",\"key\":\"ssh-rsa AAAAB3NzaC1yc2EAAAABIwAAAQEA0A5Pf5Cq...\"}",
                           "application/json")).build();

      HttpResponse createResponse = HttpResponse.builder().statusCode(202).message("HTTP/1.1 202 Accepted")
               .payload(payloadFromResourceWithContentType("/key.json", "application/json; charset=UTF-8"))
               .build();

      OrionApi apiWithNewKey = requestSendsResponse(create, createResponse);

      assertEquals(apiWithNewKey.getKeyApi().create(new ParseKeyTest().expected())
            .toString(), new ParseKeyTest().expected().toString());
   }
}
