/*
 * Copyright 2002-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.test.context.concurrency.model;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;


/**
 * @author <a href="mailto:kristian@zenior*dot*no">Kristian Rosenvold</a>
 */
@Service
public class Client {

    @Resource(name="default")
	private SessionStorage sessionStorage;

    @Resource(name="default")
	private SessionStorage sessionStorageAlias;

    @Resource(name="lazyStorage1")
    private SessionStorage sessionStorageLazy;
    
    @Autowired
	private RequestStorage service2;

    public SessionStorage getSessionStorage() {
        return sessionStorage;
    }

    public SessionStorage getSessionStorageLazy() {
        return sessionStorageLazy;
    }

    public SessionStorage getSessionStorageAlias() {
        return sessionStorageAlias;
    }

    public RequestStorage getRequestStorage() {
        return service2;
    }
}
