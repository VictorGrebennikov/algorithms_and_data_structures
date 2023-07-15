package seminar_4;

public class BinTree {
    Node root;

    public boolean add(int value) {
    if (root == null) {
        root = new Node(value);
        root.color = Color.black;
        return true;
    }
    Node newNode = addNode(root, value);
    if (newNode == null)
        return false;
    balanceAfterInsert(newNode);
    return true;
}
    private Node addNode(Node node, int value) {
        if (node.value == value)
            return null;
        if (node.value > value) {
            if (node.left == null) {
                node.left = new Node(value);
                return node.left;
            } else
                return addNode(node.left, value);
        } else  {
            if (node.right == null) {
                node.right = new Node(value);
                return node.right;
            } else
                return addNode(node.right, value);
        }
    }
    public boolean contain(int value) {
        Node currentNode = root;
        while (currentNode != null) {
            if (currentNode.value == value)
                return true;
            if (currentNode.value > value)
                currentNode = currentNode.left;
            else
                currentNode = currentNode.right;
        }
        return false;
    }

    private class Node {
        int value;
        Node left;
        Node right;
        Color color;
        Node parent;
        Node() {
            this.color = Color.red;
        }

        Node(int _value) {
            this.value = _value;
            this.color = Color.red;
        }
    }
    private void balanceAfterInsert(Node node) {
        if (node.parent == null) {
            node.color = Color.black;
            return;
        }

        if (node.parent.color == Color.black) {
            return;
        }

        Node grandparent = getGrandparent(node);
        Node uncle = getUncle(node);

        if (uncle != null && uncle.color == Color.red) {
            node.parent.color = Color.black;
            uncle.color = Color.black;
            grandparent.color = Color.red;
            balanceAfterInsert(grandparent);
        } else {
            if (node == node.parent.right && node.parent == grandparent.left) {
                rotateLeft(node.parent);
                node = node.left;
            } else if (node == node.parent.left && node.parent == grandparent.right) {
                rotateRight(node.parent);
                node = node.right;
            }

            node.parent.color = Color.black;
            grandparent.color = Color.red;
            if (node == node.parent.left && node.parent == grandparent.left) {
                rotateRight(grandparent);
            } else {
                rotateLeft(grandparent);
            }
        }
    }
    private Node getGrandparent(Node node) {
        if (node != null && node.parent != null) {
            return node.parent.parent;
        }
        return null;
    }

    private Node getUncle(Node node) {
        Node grandparent = getGrandparent(node);
        if (grandparent != null) {
            if (node.parent == grandparent.left) {
                return grandparent.right;
            } else {
                return grandparent.left;
            }
        }
        return null;
    }

    private void rotateLeft(Node node) {
        Node pivot = node.right;
        node.right = pivot.left;
        if (pivot.left != null) {
            pivot.left.parent = node;
        }
        pivot.parent = node.parent;
        if (node.parent == null) {
            root = pivot;
        } else if (node == node.parent.left) {
            node.parent.left = pivot;
        } else {
            node.parent.right = pivot;
        }
        pivot.left = node;
        node.parent = pivot;
    }

    private void rotateRight(Node node) {
        Node pivot = node.left;
        node.left = pivot.right;
        if (pivot.right != null) {
            pivot.right.parent = node;
        }
        pivot.parent = node.parent;
        if (node.parent == null) {
            root = pivot;
        } else if (node == node.parent.right) {
            node.parent.right = pivot;
        } else {
            node.parent.left = pivot;
        }
        pivot.right = node;
        node.parent = pivot;
    }

    enum Color {red, black}
}
