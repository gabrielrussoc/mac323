/* Exercicio 1.3.36 - Random iterator (Algs 4)
 * Nome: Gabriel de Russo e Carmo
 * N USP: 9298041
 * Data: 21/03/2016
 * OBS: Compilado com 'javac-algs4' */
import edu.princeton.cs.algs4.*;

public class TesteVisual {
    /* Devolve a quantos elementos menores que 'a' existem, com base
     * no vetor de frequencia 'f' */
    private static int me (int a, int[] f) {
        int cont = 0;
        for (int i = 0; i < a; i++)
            if (f[i] > 0) cont++;
        return cont;
    }

    /* Recebe dois argumentos:
     * N = permutação de 1..N
     * T = quantidade de repetições do experimento */
    public static void main (String[] args) {
        int i, j, k, ans;
        int n = Integer.parseInt (args[0]);
        int t = Integer.parseInt (args[1]);
        int[] f = new int[n];
        int[] fat = new int[n +1];
        RandomQueue<Integer> q = new RandomQueue<Integer> ();
        fat[0] = 1;
        for (i = 0; i < n; i++) {
            fat[i + 1] = (i + 1) * fat[i];
            q.enqueue (i);
        }
        Histogram histogram = new Histogram (fat[n]);
        /* Para cada teste, conto qual permutacao foi obtida (olhando para ordem
         * lexicografica) e adiciono seu valor no histograma */
        for (i = 0; i < t; i++) {
            for (j = 0; j < n; j++) f[j] = 1;
            j = 1; ans = 0;
            for (Integer a : q) {
                k = me (a, f);
                ans += k * fat[n - j];
                f[a] = 0;
                j++;
            }
            histogram.addDataPoint (ans);
        }
        histogram.draw ();
    }
}


