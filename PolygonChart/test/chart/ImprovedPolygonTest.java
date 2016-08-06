package chart;

import polygonchart.ImprovedPolygon;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests of ImprovedPolygon.
 *
 * @author samil korkmaz
 * @date November 2014
 */
public class ImprovedPolygonTest {
    
    private final int[] xArray = {0, 5, 10, 15, 15};
    private final int[] yArray = {0, 5, 3, 10, 0};
    ImprovedPolygon ip = new ImprovedPolygon(xArray, yArray, xArray.length);

    public ImprovedPolygonTest() {
    }

    @Test
    public void testContains_1() {
        //Check if all polygon edges are on polygon:
        boolean result = true;
        for (int i = 0; i < xArray.length; i++) {
            result = result && ip.contains(xArray[i], yArray[i]);
            //System.out.println(xArray[i] + ", " + yArray[i] + " in = " + ip.contains(xArray[i], yArray[i]));
        }        
        assertTrue(result);
    }
    
    @Test
    public void testContains_2() {        
        //point is on 45 deg line
        int x = 10;
        int y = 10;
        boolean result = ip.contains(x, y);
        assertFalse(result);
    }
    
    @Test
    public void testContains_3() {        
        //point is on 45 deg line
        int x = -10;
        int y = -10;
        boolean result = ip.contains(x, y);
        assertFalse(result);
    }
    
    @Test
    public void testContains_4() {        
        //point is on horizontal line
        int x = 5;
        int y = 0;
        boolean result = ip.contains(x, y);
        assertTrue(result);
    }
    
    @Test
    public void testContains_5() {        
        //point is on horizontal line
        int x = 16;
        int y = 0;
        boolean result = ip.contains(x, y);
        assertFalse(result);
    }
    
    @Test
    public void testContains_6() {        
        //point is on horizontal line
        int x = -5;
        int y = 0;
        boolean result = ip.contains(x, y);
        assertFalse(result);
    }
    
    @Test
    public void testContains_7() {        
        //point is on vertical line
        int x = 15;
        int y = 4;
        boolean result = ip.contains(x, y);
        assertTrue(result);
    }
    
    @Test
    public void testContains_8() {        
        //point is on vertical line
        int x = 15;
        int y = 12;
        boolean result = ip.contains(x, y);
        assertFalse(result);
    }
    
    @Test
    public void testContains_9() {        
        //point is on vertical line
        int x = 15;
        int y = -1;
        boolean result = ip.contains(x, y);
        assertFalse(result);
    }
    
    @Test
    public void testContains_10() {        
        //point is inside polygon
        int x = 8;
        int y = 2;
        boolean result = ip.contains(x, y);
        assertTrue(result);
    }
    
    @Test
    public void testContains_11() {        
        //point is outside polygon
        int x = 8;
        int y = -2;
        boolean result = ip.contains(x, y);
        assertFalse(result);
    }
    
    @Test
    public void testContains_12() {        
        //point is outside polygon
        int x = 8;
        int y = 8;
        boolean result = ip.contains(x, y);
        assertFalse(result);
    }
}
