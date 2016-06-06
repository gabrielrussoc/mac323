/* Exercicio 5.2.2 - Unique substrings of length L (Algs4)
 * disponivel em http://algs4.cs.princeton.edu/52trie/
 * Nome: Gabriel de Russo e Carmo
 * N USP: 9298041
 * Data: 05/06/2016
 * OBS: Compilado com 'javac-algs4' */
import edu.princeton.cs.algs4.*;

public class UniqueL {
    private static TST<Integer> t;
    private static StringBuilder buf;
    private static String input;

    private static int uniqueSub (int l) {
        if (input.length () < l) return 0;
        t = new TST<Integer> ();
        buf = new StringBuilder ();
        for (int i = 0; i < l; i++) buf.append (input.charAt (i));
        t.put (buf.toString (), 1);
        int k = l;
        while (k < input.length ()) {
            buf.deleteCharAt (0);
            buf.append (input.charAt (k++));
            t.put (buf.toString (), 1);
        }
        return t.size ();
    }

    public static void main (String[] args) {
        int l = Integer.parseInt (args[0]);
        int n = 0;
        int[] ten = new int[10];
        ten[0] = 1;
        for (int i = 1; i < 10; i++) ten[i] = ten[i-1] * 10;
        
        if (l == 0 && args.length == 2) n = Integer.parseInt (args[1]);
        if (n == 0) input = StdIn.readLine ();
        else {
            StringBuilder tmp = new StringBuilder ();
            for (int i = 0; i < n; i++) tmp.append (StdRandom.uniform (10));
            input = new String (tmp);
        }

        /* Numero de substrings unicas num texto */
        if (l != 0) { 
            StdOut.println (uniqueSub (l));
        } else { /* Maior L para qual o texto é L completo */
            boolean done = false;
            int ans = 0;
            while (!done) {
                int k = ans + 1;
                if (uniqueSub (k) != ten[k]) done = true;
                else ans++;
            }
            StdOut.println (ans);
        }

    }
}
