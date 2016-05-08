import edu.princeton.cs.algs4.*;

public class ImplicitTreap<Item> {
    private Node root;
    public class Node {
        private int priority;
        private Item value;
        private Node left, right;
        private int size;

        public Node (Item value) {
            this.value = value;
            this.size = 1;
            this.priority = StdRandom.uniform(1123456789);
        }

        public Item getValue () {
            return this.value;
        }
    }   

    public ImplicitTreap () {}

    private Node[] split (Node t, int k) {
        Node[] ret = (Node[]) new Object[2];
        if (t == null) return ret;
        int key = t.left.size;
        if (key > k) {
            Node[] leftSplit = split (t.left, k);
            ret[0] = leftSplit[0];
            ret[1] = t;
            ret[1].left = leftSplit[1];
        } else {
            Node[] rightSplit = split (t.right, k-key-1);
            ret[0] = t;
            ret[0].right = rightSplit[0];
            ret[1] = rightSplit[1];
        }
        update (t);
        return ret;
    }

    private Node merge (Node l, Node r) {
        if (l == null) return r;
        if (r == null) return l;
        Node t;
        if (l.priority > r.priority) {
            t = l;
            t.right = merge (t.right, r);
        } else {
            t = r;
            t.left = merge (l, t.left);
        }
        update (l); update(r);
        return t;
    }

    private void update (Node t) {
        t.size = 1;
        if (t.left != null) t.size += t.left.size;
        if (t.right != null) t.size += t.right.size;
    }

    public Node add (int i, Item value) {
        Node[] aux = split (root, i); 
        Node ins = new Node (value);
        root = merge (merge (aux[0], ins), aux[1]);
        return ins;
    }

    public Item delete (int i) {
        Node[] aux = split (root, i); 
        Node[] aux2 = split (aux[1], 1);
        root = merge (aux[0], aux2[1]);
        return aux2[0].getValue ();
    }
}

