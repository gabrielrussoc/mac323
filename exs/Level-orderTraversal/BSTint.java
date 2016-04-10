/* Exercicio 3.2.37 - Level-order traversal (Algs 4)
 * Nome: Gabriel de Russo e Carmo
 * N USP: 9298041
 * Data: 09/04/2016
 * OBS: Compilado com 'javac-algs4' */
import edu.princeton.cs.algs4.*;

/* Implementacao minimal de uma BST nao balaceada de inteiros. Nao ha remocao */
public class BSTint {
    private Node root;

    /* Nos da arvore */
    private class Node {
        private int key;
        private Node left, right;

        /* Devolve um no com a chave key */
        public Node (int key) {
            this.key = key;
        }
    }

    /* Devolve uma BST vazia */
    public BSTint () {}

    /* Devolve true se a BST contem a chave key */
    public boolean contains (int key) {
        return contains (root, key);
    }

    private boolean contains (Node x, int key) {
        if (x == null) return false;
        if (x.key > key) return contains (x.left, key);
        else if (x.key < key) return contains (x.right, key);
        return true;
    }

    /* Devolve o no que contenha a chave key (null caso nao exista) */
    private Node subTree (Node x, int key) {
        if (x == null) return x;
        if (x.key > key) return subTree (x.left, key);
        else if (x.key < key) return subTree (x.right, key);
        return x; 
    }

    /* Adiciona a chave key na BST */
    public void put (int key) {
        root = put (root, key);
    }

    private Node put (Node x, int key) {
        if (x == null) return new Node (key);
        if (x.key > key) x.left = put (x.left, key);
        else if (x.key < key) x.right = put (x.right, key);
        return x;
    }

    /* Imprime a subarvore da chave key em ordem (raiz-esq-dir) */
    public void printLevel (int key) {
        Node x, cur;
        x = subTree (root, key);
        Queue<Node> q = new Queue<Node> ();
        if (x != null) q.enqueue (x);
        while (!q.isEmpty ()) {
            cur = q.dequeue ();
            StdOut.printf (cur.key + " ");
            if (cur.left != null) q.enqueue (cur.left);
            if (cur.right != null) q.enqueue (cur.right);
        }
        StdOut.println ();
    }
}

