/*
 * Copyright 2017 original authors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package org.particleframework.inject.value.factorywithvalue

import org.particleframework.context.ApplicationContext
import org.particleframework.context.DefaultApplicationContext
import org.particleframework.context.annotation.Bean
import org.particleframework.context.annotation.Factory
import org.particleframework.context.annotation.Value
import spock.lang.Specification

/**
 * @author Graeme Rocher
 * @since 1.0
 */
class FactoryWithValueSpec extends Specification {

    void "test configuration injection with @Value"() {
        given:
        System.setProperty("foo.bar", "8080")
        ApplicationContext context = new DefaultApplicationContext("test").start()
        A a = context.getBean(A)
        B b = context.getBean(B)

        expect:
        a.port == 8080
        b.a != null
        b.port == 8080
        cleanup:
        System.setProperty("foo.bar", "")
    }



}