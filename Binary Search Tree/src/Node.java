
/**
 * Binary Search Tree node data structure.
 *
 * @author skorkmaz
 */
public class Node {

    private final int key;
    private Node parent;
    private Node left;
    private Node right;
    private boolean isLeftOfParent;

    public Node(int key, Node parent) {
        this.key = key;
        this.parent = parent;
    }

    public int getKey() {
        return key;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
        if (left != null) {
            left.setIsLeftOfParent(true);
        }
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
        if (right != null) {
            right.setIsLeftOfParent(false);
        }
    }

    public boolean isIsLeftOfParent() {
        return isLeftOfParent;
    }

    public void setIsLeftOfParent(boolean isLeftOfParent) {
        this.isLeftOfParent = isLeftOfParent;
    }

    @Override
    public String toString() {
        return this != null ? String.valueOf(this.getKey()) : "null";
    }
}
