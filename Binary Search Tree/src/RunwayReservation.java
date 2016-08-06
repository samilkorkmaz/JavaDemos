
/**
 * Runway reservation using binary search tree.<br>
 * Reference: MIT Open Course Ware, 6.006
 *
 * @author skorkmaz
 */
public class RunwayReservation {
    
    public static void main(String[] args) {        
        BinarySearchTree runwaySchedule = new BinarySearchTree(10);        
        System.out.println("------------------");
        runwaySchedule.insert(0, 5);
        runwaySchedule.printTree();        
        System.out.println("------------------");
        runwaySchedule.insert(7, 5);
        runwaySchedule.printTree();
        System.out.println("------------------");
        runwaySchedule.insert(15, 5);
        runwaySchedule.printTree();        
        System.out.println("------------------");
        runwaySchedule.insert(12, 5);
        runwaySchedule.printTree();
        System.out.println("------------------");
        runwaySchedule.insert(5, 5);
        runwaySchedule.printTree();        
        System.out.println("------------------");
        runwaySchedule.insert(20, 5);
        runwaySchedule.printTree();        
    }

}
