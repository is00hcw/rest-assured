/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jayway.restassured.itest.java;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.itest.java.support.WithJetty;
import com.jayway.restassured.specification.RequestSpecification;
import org.junit.Ignore;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.specification.ProxySpecification.host;
import static org.hamcrest.Matchers.equalTo;

//@Ignore("Removed when manually having setup a Proxy such as Charles on port 8888")
public class ProxyITest extends WithJetty {

    @Test public void
    using_proxy_with_hostname_and_port() {
        given().
                proxy("127.0.0.1", 8888).
                param("firstName", "John").
                param("lastName", "Doe").
        when().
                get("/greetJSON").
        then().
                body("greeting.firstName", equalTo("John")).
                body("greeting.lastName", equalTo("Doe"));
    }

    @Test public void
    using_proxy_with_hostname() {
        given().
                proxy("127.0.0.1").
                param("firstName", "John").
                param("lastName", "Doe").
        when().
                get("/greetJSON").
        then().
                body("greeting.firstName", equalTo("John")).
                body("greeting.lastName", equalTo("Doe"));
    }

    @Test public void
    using_proxy_with_hostname_as_a_uri() {
        given().
                proxy("http://127.0.0.1:8888").
                param("firstName", "John").
                param("lastName", "Doe").
        when().
                get("/greetJSON").
        then().
                body("greeting.firstName", equalTo("John")).
                body("greeting.lastName", equalTo("Doe"));
    }

    @Ignore("Doesnt work with Charles?")
    @Test public void
    using_proxy_with_https_scheme() {
        given().
                proxy("https://127.0.0.1:8888").
                param("firstName", "John").
                param("lastName", "Doe").
        when().
                get("/greetJSON").
        then().
                body("greeting.firstName", equalTo("John")).
                body("greeting.lastName", equalTo("Doe"));
    }

    @Test public void
    using_proxy_with_uri() throws URISyntaxException {
        given().
                proxy(new URI("http://127.0.0.1:8888")).
                param("firstName", "John").
                param("lastName", "Doe").
        when().
                get("/greetJSON").
        then().
                body("greeting.firstName", equalTo("John")).
                body("greeting.lastName", equalTo("Doe"));
    }

    @Test public void
    using_proxy_with_proxy_specification() {
        given().
                proxy(host("localhost").and().withPort(8888).and().withScheme("http")).
                param("firstName", "John").
                param("lastName", "Doe").
        when().
                get("/greetJSON").
        then().
                body("greeting.firstName", equalTo("John")).
                body("greeting.lastName", equalTo("Doe"));
    }

    @Test public void
    using_proxy_with_specification() {
        RequestSpecification specification = new RequestSpecBuilder().setProxy("localhost").build();

        given().
                specification(specification).
                param("firstName", "John").
                param("lastName", "Doe").
        when().
                get("/greetJSON").
        then().
                body("greeting.firstName", equalTo("John")).
                body("greeting.lastName", equalTo("Doe"));
    }
}