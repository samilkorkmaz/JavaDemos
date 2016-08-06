
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author skorkmaz
 */
public class BinarySearchTreeTest {
    
    private static final int[] DEFAULT_KEY_ARRAY = new int[]{100, 50, 150, 25, 75, 170, 130, 160, 180, 140, 120};

    public BinarySearchTreeTest() {
    }
    
    @Test
    public void testInsert_1() {
        BinarySearchTree bst = new BinarySearchTree();
        bst.insert(new int[]{1, 2});
        int[] expectedKeyList = new int[]{1, 2};
        assertTrue(bst.nodeListIsEqualTo(new BinarySearchTree(expectedKeyList)));
    }
    
    @Test
    public void testInsert_2() {
        BinarySearchTree bst = new BinarySearchTree(100);        
        assertTrue(bst.insert(90, 10));
    }
    
    @Test
    public void testInsert_3() {
        BinarySearchTree bst = new BinarySearchTree(100);        
        assertFalse(bst.insert(95, 10));
    }
    
    @Test
    public void testInsert_4() {
        BinarySearchTree bst = new BinarySearchTree(DEFAULT_KEY_ARRAY);        
        assertFalse(bst.insert(70, 10));
    }   
    
    @Test
    public void testInsert_5() {
        BinarySearchTree bst = new BinarySearchTree(DEFAULT_KEY_ARRAY);        
        assertTrue(bst.insert(70, 5));
    } 
    
    @Test
    public void testInsert_6() {
        BinarySearchTree bst = new BinarySearchTree(DEFAULT_KEY_ARRAY);        
        assertTrue(bst.insert(185, 5));
    }
    
    @Test
    public void testInsert_7() {
        BinarySearchTree bst = new BinarySearchTree(DEFAULT_KEY_ARRAY);        
        assertFalse(bst.insert(140, 5));
    }

    @Test
    public void testDeleteKey_1() {
        BinarySearchTree bst = new BinarySearchTree(DEFAULT_KEY_ARRAY);
        bst.deleteKey(150);
        int[] expectedKeyList = new int[]{100, 50, 25, 75, 130, 120, 140, 170, 160, 180};
        assertTrue(bst.nodeListIsEqualTo(new BinarySearchTree(expectedKeyList)));
    }
    
    @Test
    public void testDeleteKey_2() {
        BinarySearchTree bst = new BinarySearchTree(DEFAULT_KEY_ARRAY);
        bst.deleteKey(150);
        bst.deleteKey(100);
        int[] expectedKeyList = new int[]{50, 25, 75, 130, 120, 140, 170, 160, 180};
        assertTrue(bst.nodeListIsEqualTo(new BinarySearchTree(expectedKeyList)));
    }

    @Test
    public void testDeleteKey_3() {
        BinarySearchTree bst = new BinarySearchTree(DEFAULT_KEY_ARRAY);
        bst.deleteKey(150);
        bst.deleteKey(100);
        bst.deleteKey(180);
        int[] expectedKeyList = new int[]{50, 25, 75, 130, 120, 140, 170, 160};
        assertTrue(bst.nodeListIsEqualTo(new BinarySearchTree(expectedKeyList)));
    }

    @Test
    public void testDeleteAll() {
        BinarySearchTree bst = new BinarySearchTree(DEFAULT_KEY_ARRAY);
        bst.deleteAll();
        int[] expectedKeyList = new int[]{};
        assertTrue(bst.nodeListIsEqualTo(new BinarySearchTree(expectedKeyList)));
    }
    
    @Test
    public void testNodeListIsEqualTo_1() {
        BinarySearchTree bst1 = new BinarySearchTree(DEFAULT_KEY_ARRAY);
        BinarySearchTree bst2 = new BinarySearchTree(DEFAULT_KEY_ARRAY);
        assertTrue(bst1.nodeListIsEqualTo(bst2));
    }
    
    @Test
    public void testNodeListIsEqualTo_2() {
        BinarySearchTree bst1 = new BinarySearchTree(null);
        BinarySearchTree bst2 = new BinarySearchTree(DEFAULT_KEY_ARRAY);
        assertFalse(bst1.nodeListIsEqualTo(bst2));
    }
    
    @Test
    public void testNodeListIsEqualTo_3() {
        BinarySearchTree bst1 = new BinarySearchTree(DEFAULT_KEY_ARRAY);
        BinarySearchTree bst2 = new BinarySearchTree(new int[]{100, 150, 170, 130, 160, 180, 140, 120, 50, 25, 75});
        assertTrue(bst1.nodeListIsEqualTo(bst2));
    }
    
    @Test
    public void testGetMaxKey_1() {
        BinarySearchTree bst = new BinarySearchTree(DEFAULT_KEY_ARRAY);
        assertEquals(bst.getMaxKey(), 180);
    }
    
    @Test
    public void testGetMaxKey_2() {
        BinarySearchTree bst = new BinarySearchTree(DEFAULT_KEY_ARRAY);
        bst.deleteKey(180);
        bst.deleteKey(170);
        assertEquals(bst.getMaxKey(), 160);
    }
    
    @Test
    public void testGetMinKey_1() {
        BinarySearchTree bst = new BinarySearchTree(DEFAULT_KEY_ARRAY);
        assertEquals(bst.getMinKey(), 25);
    }
    
    @Test
    public void testGetMinKey_2() {
        BinarySearchTree bst = new BinarySearchTree(DEFAULT_KEY_ARRAY);
        bst.deleteKey(25);
        assertEquals(bst.getMinKey(), 50);
    }
}
