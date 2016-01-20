package com.joantolos.gradle.poc;

import com.google.gson.Gson;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JsonUtilsTest {

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
     * Testing the external Gson Library dependency
     * GSON from Google offers methods for work with Json
     */
    @Test
    public void gsonTest(){
        String greetingJson = new Gson().toJson(this.greeting);
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
