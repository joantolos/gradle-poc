package com.joantolos.gradle.poc;

import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.joantolos.gradle.poc.utils.JsonUtils;

public class GsonTest {

    Greeting greeting;

    @Before
    public void setUp(){
        this.greeting = new Greeting("Hello on an Object");
    }

    @After
    public void tearDown(){
        this.greeting = null;
    }

    /**
     * Testing the internal utilitis Library dependency
     */
    @Test
    public void gsonTest(){
        String greetingJson = JsonUtils(this.greeting);
        Assert.assertNotNull(greetingJson);
        Assert.assertEquals("{\"content\":\"Hello on an Object\"}", greetingJson);
        Greeting greetingObject = new Gson().fromJson( greetingJson, Greeting.class);
        Assert.assertNotNull(greetingObject);
        Assert.assertEquals("Hello on an Object", greetingObject.getContent());
    }

    private class Greeting {
        private String content;

        private Greeting(String content){
            this.content = content;
        }

        public String getContent() {
            return content;
        }
    }
}