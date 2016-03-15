import edu.princeton.cs.algs4.*;

/* Exercicio Probabilidade Critica 3D
 * Nome: Gabriel de Russo e Carmo
 * N USP: 9298041
 * Data: 15/03/2016
 * OBS: Compilado com 'javac-algs4' */
public class BooleanMatrix3D {

    /* Gera uma matriz booleana n x n x n com probabilidade p 
     * para cada entrada ser 'true' */
    public static boolean[][][] random (int n, double p) {
        boolean[][][] a = new boolean[n][n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                for (int k = 0; k < n; k++)
                    a[i][j][k] = StdRandom.bernoulli (p);
        return a;
    }

    /* Le da entrada padrao uma matriz cubica por camadas, isto e,
     * para cada uma das n linhas, le uma matriz n x n */
    public static boolean[][][] read (int n) {
        boolean[][][] a = new boolean[n][n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                for (int k = 0; k < n; k++)
                    a[i][j][k] = StdIn.readBoolean();
        return a;
    }

    /* Recebe uma matriz cubica e imprime-a por camadas, isto e,
     * para cada uma das n linhas, imprime uma matriz n x n */
    public static void print (boolean[][][] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    if (a[i][j][k]) StdOut.print (1 + " ");
                    else StdOut.print (0 + " ");
                }
                StdOut.println ();
            }
            StdOut.println ();
        }
    }
}

