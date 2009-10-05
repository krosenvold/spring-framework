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

package org.springframework.test.context.concurrency;

import org.junit.runner.RunWith;
import org.junit.Test;
import static org.junit.Assert.*;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.concurrency.model.Client;
import org.springframework.test.context.concurrency.model.SubBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import junit.framework.Assert;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * This test is run as part of the regular test battery. As such it only verifies a number of assumptions that should hold ture.
 * The  test #TestContextManagerConcurrencyRunnerTest runs this test a number of times in parallel.
 *
 * If the parallel test fails, you should always check this test first.  
 *
 * @author <a href="mailto:kristian@zeniorD0Tno">Kristian Rosenvold</a>
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
     "classpath:org/springframework/test/context/concurrency/model/applicationContext-concurrency-simple.xml"
}, loader=MockContextLoader.class)
public class SpringJUnit4ClassRunnerEnvironmentAssumptionsTests {
    @Autowired
    Client client;
    @Autowired
    SubBean subBean1;

    AtomicInteger runCount = new AtomicInteger();

    private String threadInfo;

    public SpringJUnit4ClassRunnerEnvironmentAssumptionsTests() {
        threadInfo = getThreadInfo();
    }

    private String getThreadInfo() {
        return this.toString() + "Thread" + Thread.currentThread().toString();
    }

    @Test
    public void testThatWeReNotRunningInTheSameInstance(){
         assertEquals(1, runCount.incrementAndGet());
    }

	@Test
	public
	void testThatWeAreCreatedOnTheSameThreadAsWeRun(){
      assertEquals( getThreadInfo(), this.threadInfo);
	}

    @Test
    public void testThatScopeProxiedInstancesAreServedByDifferentInstances(){
        assertFalse("Expect different instance for differnet bean", client.getSessionStorage().id().equals(client.getSessionStorageLazy().id()));
        assertEquals("Expect same instance for alias", client.getSessionStorage().id(), client.getSessionStorageAlias().id());
    }


    @Test
    public void testSubBean1() {
        Assert.assertNotNull( subBean1.getService());
        Assert.assertEquals( SubBean.class, subBean1.getClass());
    }

    @Test
    public void testClient1() {
        Assert.assertNotNull( "Object is supposed to be autowired (1) !!!", client);
        Assert.assertNotNull( "Object is supposed to be autowired (2) !!!", client.getSessionStorage());
        Assert.assertNotNull("Object is supposed to be autowired (3) !!!",  client.getSessionStorageAlias());
        Assert.assertNotNull( "Object is supposed to be autowired (4) !!!", client.getSessionStorageLazy());
        Assert.assertNotNull( "Object is supposed to be autowired (5) !!!", client.getRequestStorage());
        Assert.assertNotNull( "Object is supposed to be autowired (6) !!!", subBean1);
        Assert.assertNotNull( "Object is supposed to be autowired (7) !!!", subBean1.getService());
        Assert.assertEquals( "Object is supposed to be autowired (8) !!!", SubBean.class, subBean1.getClass());
    }


}
