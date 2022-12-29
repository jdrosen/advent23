package net.jdrosen.advent23;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class TestBoard {

    @Test
    public void testGoNorth() {

        ElfPosition testElf = new ElfPosition(0,0);
        testElf.goNorth();

        assertEquals(testElf.ypos,-1);
        assertEquals(testElf.xpos,0);


    }
    
}
