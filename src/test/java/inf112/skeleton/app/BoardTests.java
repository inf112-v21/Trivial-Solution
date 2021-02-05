package inf112.skeleton.app;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class BoardTests {


    @Test
    void testReadFromFileReadsWidthAndHeight(){
        Board bård = new Board();
        assertNotNull(bård.getHeight());
        assertNotNull(bård.getWidth());
    }

}
