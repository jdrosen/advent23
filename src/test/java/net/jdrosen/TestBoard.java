package net.jdrosen.advent23;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.beans.Transient;


public class TestBoard {

    @Test
    public void testGoNorth() {

        ElfPosition testElf = new ElfPosition(0,0);
        testElf.goNorth();

        assertEquals(testElf.ypos,-1);
        assertEquals(testElf.xpos,0);


    }

    @Test
    public void testGoInCirclt() {

        ElfPosition testElf = new ElfPosition(0,0);
        testElf.goNorth();
        testElf.goEast();
        testElf.goSouth();
        testElf.goWest();

        assertEquals(testElf.ypos,0);
        assertEquals(testElf.xpos,0);


    }
    
}
