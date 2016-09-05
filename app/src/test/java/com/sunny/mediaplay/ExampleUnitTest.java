package com.sunny.mediaplay;

import com.sunny.mediaplay.util.Util;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void testReadMp3Info(){
        File file = new File("C:\\Users\\home\\Desktop\\mp3.mp3");
        Util.getMediaInfo(file);
    }
}