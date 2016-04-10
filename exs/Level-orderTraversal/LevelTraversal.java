/* Exercicio 3.2.37 - Level-order traversal (Algs 4)
 * Nome: Gabriel de Russo e Carmo
 * N USP: 9298041
 * Data: 09/04/2016
 * OBS: Compilado com 'javac-algs4' */
import edu.princeton.cs.algs4.*;

public class LevelTraversal {
    public static void main (String[] args) {
        BSTint tree = new BSTint ();
        int n, m, a;
        n = StdIn.readInt ();
        m = StdIn.readInt ();

        for (int i = 0; i < n; i++) {
            a = StdIn.readInt ();
            tree.put (a);
        }

        for (int i = 0; i < m; i++) {
            a = StdIn.readInt ();
            tree.printLevel (a);
        }
    }    
}

