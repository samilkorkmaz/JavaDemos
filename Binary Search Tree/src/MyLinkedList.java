
/**
 * Simple linked list.
 *
 * @author skorkmaz
 */
public class MyLinkedList {

    public static class Element {

        private Element parent;
        private final int key;

        public Element(Element parent, int key) {
            this.parent = parent;
            this.key = key;
        }
    }

    public void insertBefore(Element startElement, Element elementToAdd) {
        if (startElement == null) {
            System.out.println("Cannot insert because startElement == null!");
        } else {
            elementToAdd.parent = startElement.parent;
            startElement.parent = elementToAdd;
        }
    }
    
    public void insertAfter(Element startElement, Element elementToAdd) {
        if (startElement == null) {
            System.out.println("Cannot insert because startElement == null!");
        } else {
            elementToAdd.parent = startElement;
        }
    }

    public static void printFrom(Element startElement) {
        System.out.println("-----------------------------");
        if (startElement == null) {
            System.out.println("startElement == null");
        } else {
            while (true) {
                System.out.println("startElement.key = " + startElement.key);
                startElement = startElement.parent;
                if (startElement == null) {
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        Element startElement = new Element(null, 0);
        for (int i = 1; i < 3; i++) {
            Element nextElement = new Element(startElement, i);
            startElement = nextElement;
        }
        printFrom(startElement);
        MyLinkedList mll = new MyLinkedList();
        Element element = new Element(null, -1);
        mll.insertBefore(startElement, element);
        printFrom(startElement);
        
        element = new Element(null, 100);
        mll.insertAfter(startElement, element);
        printFrom(element);
    }
}
