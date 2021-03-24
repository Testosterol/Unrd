package com.testosterolapp.unrd;

import com.testosterolapp.unrd.data.BackgroundImage;
import com.testosterolapp.unrd.data.Result;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTests {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testBackgroundImageResourceFid(){
        BackgroundImage event = new BackgroundImage(4L,"test",
                "0","0","",false,
                4L, 0L);
        assertEquals(event.getResource_fid(), "test");
    }

    @Test
    public void testBackgroundImageResourceType(){
        BackgroundImage event = new BackgroundImage(4L,"test",
                "type test","0","",false,
                4L, 0L);
        assertEquals(event.getResource_type(), "type test");
    }

    @Test
    public void testBackgroundImageResourceUri(){
        BackgroundImage event = new BackgroundImage(4L,"test",
                "0","test uri","",false,
                4L, 0L);
        assertEquals(event.getResource_uri(), "test uri");
    }




}