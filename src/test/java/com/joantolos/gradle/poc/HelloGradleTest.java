package com.joantolos.gradle.poc;

import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HelloGradleTest {

    @Before
    public void setUp(){

    }

    @After
    public void tearDown(){

    }

    @Test
    public void sayHiTest(){
        Assert.assertEquals("Hello gradle world", HelloGradle.sayHi());
    }
}
