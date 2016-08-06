
import java.util.ArrayList;
import java.util.List;

/**
 * Binary Search Tree implementation.<br>
 * Reference: MIT Open Course Ware, 6.006
 *
 * @author skorkmaz
 */
public class BinarySearchTree {

    private Node root;

    public BinarySearchTree() {
    }

    public BinarySearchTree(int key) {
        insert(key);
    }

    public BinarySearchTree(int[] keyArray) {
        if (keyArray != null) {
            insert(keyArray);
        }
    }

    public boolean insert(int key, int dKey) {
        return insert(root, key, dKey);
    }

    private boolean insert(Node startNode, int key, int dKey) {
        boolean couldInsert;
        Node newNode = new Node(key, startNode);
        if (key <= startNode.getKey() - dKey) {
            if (exists(startNode.getLeft())) {
                couldInsert = insert(startNode.getLeft(), key, dKey);
            } else {
                startNode.setLeft(newNode);
                couldInsert = true;
            }
        } else {
            if (exists(startNode.getRight())) {
                couldInsert = insert(startNode.getRight(), key, dKey);
            } else {
                if (key >= startNode.getKey() + dKey) {
                    startNode.setRight(newNode);
                    couldInsert = true;
                } else {
                    couldInsert = false;
                }
            }
        }
        return couldInsert;
    }

    private boolean exists(Node node) {
        return node != null;
    }

    private void insert(int key) {
        if (!exists(root)) {
            root = new Node(key, null);
        } else {
            insert(root, key);
        }
    }

    public final void insert(int[] keyArray) {
        for (int i = 0; i < keyArray.length; i++) {
            insert(keyArray[i]);
        }
    }

    public Node find(Node startNode, int keyToFind) {
        Node node;
        if (startNode == null) {
            node = null;
        } else {
            if (startNode.getKey() == keyToFind) {
                node = startNode;
            } else {
                if (keyToFind < startNode.getKey()) {
                    node = find(startNode.getLeft(), keyToFind);
                } else {
                    node = find(startNode.getRight(), keyToFind);
                }
            }
        }
        return node;
    }

    private void insert(Node startNode, int key) {
        Node newNode = new Node(key, startNode);
        if (!isRoot(newNode)) {
            if (key == startNode.getKey()) {
                throw new IllegalArgumentException("key " + key + " already exists in binary search tree!");
            }
            if (key < startNode.getKey()) {
                if (exists(startNode.getLeft())) {
                    insert(startNode.getLeft(), key);
                } else {
                    startNode.setLeft(newNode);
                }
            } else {
                if (exists(startNode.getRight())) {
                    insert(startNode.getRight(), key);
                } else {
                    startNode.setRight(newNode);
                }
            }
        }
    }

    public boolean deleteKey(int keyToDelete) {
        Node nodeToDelete = find(root, keyToDelete);
        if (nodeToDelete == null) {
            return false;
        } else {
            deleteNode(nodeToDelete);
            return true;
        }
    }

    private void deleteNode(Node nodeToDelete) {
        if (nodeToDelete.getLeft() == null && nodeToDelete.getRight() == null) { //no child nodes (leafs)
            updateParentsChild(nodeToDelete, null);
        } else if (nodeToDelete.getLeft() != null) {
            nodeToDelete.getLeft().setParent(nodeToDelete.getParent());
            updateParentsChild(nodeToDelete, nodeToDelete.getLeft());
            if (nodeToDelete.getRight() != null) { //place right tree under max of left tree
                Node maxOfLeft = getMax(nodeToDelete.getLeft());
                maxOfLeft.setRight(nodeToDelete.getRight());
                nodeToDelete.getRight().setParent(maxOfLeft);
            }
        } else { //on the left no child node, on the right at least one child node
            nodeToDelete.getRight().setParent(nodeToDelete.getParent());
            updateParentsChild(nodeToDelete, nodeToDelete.getRight());
        }
    }

    public void deleteAll() {
        while (exists(root)) {
            System.out.println("Deleting " + root.getKey() + "...");
            deleteNode(root);
        }
    }

    public int getMaxKey() {
        return getMax(root).getKey();
    }

    private Node getMax(Node startNode) {
        Node rightNode = startNode;
        while (true) {
            Node nextRightNode = rightNode.getRight();
            if (nextRightNode == null) {
                break;
            }
            rightNode = nextRightNode;
        }
        return rightNode;
    }

    public int getMinKey() {
        return getMin(root).getKey();
    }

    private Node getMin(Node startNode) {
        Node leftNode = startNode;
        while (true) {
            Node nextLeftNode = leftNode.getLeft();
            if (nextLeftNode == null) {
                break;
            }
            leftNode = nextLeftNode;
        }
        return leftNode;
    }

    private boolean isRoot(Node node) {
        return node.getParent() == null;
    }

    private void updateParentsChild(Node nodeToDelete, Node childNode) {
        if (isRoot(nodeToDelete)) {
            if (exists(root.getLeft())) {
                root = root.getLeft();
            } else if (exists(root.getRight())) {
                root = root.getRight();
            } else {
                root = null;
            }
        } else {
            if (nodeToDelete.isIsLeftOfParent()) {
                nodeToDelete.getParent().setLeft(childNode);
            } else {
                nodeToDelete.getParent().setRight(childNode);
            }
        }
    }

    public void printTree() {
        printTree(root);
    }

    private void printTree(Node startNode) {
        if (!exists(startNode)) {
            System.out.println("root is null!");
        } else {
            if (isRoot(startNode)) {
                System.out.println("key = " + startNode.getKey() + ", parent is null (root node) ");
            } else {
                System.out.println("key = " + startNode.getKey() + ", parent.key = " + startNode.getParent().getKey() + ", isLeftOfParent = " + startNode.isIsLeftOfParent());
            }
            if (exists(startNode.getLeft())) {
                printTree(startNode.getLeft());
            }
            if (exists(startNode.getRight())) {
                printTree(startNode.getRight());
            }
        }
    }

    private List<Node> getNodeList() {
        List<Node> nodeList = new ArrayList<>();
        fillNodeList(root, nodeList);
        return nodeList;
    }

    private void fillNodeList(Node startNode, List<Node> nodeList) {
        if (!exists(startNode)) {
            System.out.println("startNode is null!");
        } else {
            nodeList.add(startNode);
            if (exists(startNode.getLeft())) {
                fillNodeList(startNode.getLeft(), nodeList);
            }
            if (exists(startNode.getRight())) {
                fillNodeList(startNode.getRight(), nodeList);
            }
        }
    }

    public boolean nodeListIsEqualTo(BinarySearchTree aBST) {
        boolean isEqual = true;
        if (aBST == null) {
            isEqual = false;
        } else {
            List<Node> nodeList = this.getNodeList();
            List<Node> nodeListToCompare = aBST.getNodeList();
            if (nodeList.size() != nodeListToCompare.size()) {
                isEqual = false;
            } else {
                for (int i = 0; i < nodeList.size(); i++) {
                    if (nodeList.get(i).getKey() != nodeListToCompare.get(i).getKey()) {
                        isEqual = false;
                        break;
                    }
                }
            }
        }
        return isEqual;
    }
}
