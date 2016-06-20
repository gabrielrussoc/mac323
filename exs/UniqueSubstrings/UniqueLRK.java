/* Exercicio 5.3.32 - Unique substrings (Algs4)
 * Nome: Gabriel de Russo e Carmo
 * N USP: 9298041
 * Data: 19/06/2016
 * OBS: Compilado com 'javac-algs4' */
import edu.princeton.cs.algs4.*;
import java.util.*;

public class UniqueLRK {
    private static String input;
    private static long X = 257;
    private static long MOD = 1000000007;
    private static long[] hash;
    private static long[] x;

    /* Devolve o rolling hash de input[i..j] */
    private static long calc (int i, int j) {
        return (MOD + hash[j+1] - ((hash[i] * x[j-i+1]) % MOD)) % MOD;
    }

    /* Devolve o numero de substrings unicas de tamanho l */
    private static int uniqueSub (int l) {
        if (input.length () < l) return 0;
        HashSet<Long> s = new HashSet<Long> ();
        for (int i = 0; i <= input.length () - l; i++)
            s.add (calc(i,i+l-1));
        return s.size ();
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
        hash = new long[input.length() + 1];
        x = new long[input.length() + 1];

        /* Constroi o rolling hash dos prefixos */
        hash[0] = 0;
        x[0] = 1;
        for (int i = 0; i < input.length (); i++) {
            x[i+1] = (x[i] * X) % MOD;
            hash[i+1] = (hash[i] * X + (long) input.charAt(i)) % MOD;
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
