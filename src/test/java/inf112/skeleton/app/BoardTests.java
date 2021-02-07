package inf112.skeleton.app;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class BoardTests {


    @Test
    public void testReadFromFileReadsWidthAndHeight(){
        // TODO: 07.02.2021 Koden i main må kjøres før konstruktøren til Board av en eller annen grunn. Finn ut hvorfor!
        Board bård = new Board();
        assertNotNull(bård.getHeight());
        assertNotNull(bård.getWidth());
    }

}
