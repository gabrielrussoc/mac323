import edu.princeton.cs.algs4.*;

/* Exercicio 2.4.18 - Percolation in three dimensions 
 * disponivel em http://introcs.cs.princeton.edu/java/24percolation/
 * Nome: Gabriel de Russo e Carmo
 * N USP: 9298041
 * Data: 08/03/2016
 * OBS: Compilado com 'javac-algs4' */
public class Percolation3D {
    
    public static final int[] dx = {1, -1, 0, 0, 0, 0};
    public static final int[] dy = {0, 0, 1, -1, 0, 0};
    public static final int[] dz = {0, 0, 0, 0, 1, -1};

    /* Recebe uma matriz booleana open e devolve outra, marcando
     * todas as celulas que foram visitadas pelo fluxo que sai do topo */ 
    public static boolean[][][] flow (boolean[][][] open) {
        int n = open.length;
        boolean[][][] full = new boolean[n][n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)  
                flow (open, full, 0, i, j);
        return full;
    }
    
    /* Marca em full que a tripla (i,j,k) foi visitada e faz o mesmo recursivamente para todos
     * os vizinhos validos que estejam abertos (verificados na matriz open) */
    public static void flow (boolean[][][] open, boolean[][][] full, int i, int j, int k) {
        int n = open.length;

        if (i < 0 || i >= n) return;
        if (j < 0 || j >= n) return;
        if (k < 0 || k >= n) return;
        if (!open[i][j][k]) return;
        if (full[i][j][k]) return;

        full[i][j][k] = true;
        
        for (int p = 0; p < 6; p++)
            flow (open, full, i + dx[p], j + dy[p], k + dz[p]);    
    }

    /* Verifica se ha percolacao no cubo representado pela matriz open */
    public static boolean percolates (boolean[][][] open) {
        int n = open.length;
        boolean[][][] full = flow (open);
        
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (full[n-1][i][j]) return true;
        
        return false;
    }

    /* Devolve a razao numero de percolacoes por m testes numa matriz n x n x n,
     * onde cada cada celula tem probabilidade p de estar aberta */
    public static double eval (int n, int m, double p) {
        boolean open[][][];
        int ans = 0;
        for (int i = 0; i < m; i++) {
            open = BooleanMatrix3D.random (n, p);
            if (Percolation3D.percolates (open)) ans++;
        }
        return (double) ans/m; 
    }
}

