import edu.princeton.cs.algs4.*;

/* Exercicio Probabilidade Critica 3D
 * Nome: Gabriel de Russo e Carmo
 * N USP: 9298041
 * Data: 15/03/2016
 * OBS: Compilado com 'javac-algs4' */
public class ProbCritica3D {
    
    private static final int[] dx = {1, -1, 0, 0, 0, 0};
    private static final int[] dy = {0, 0, 1, -1, 0, 0};
    private static final int[] dz = {0, 0, 0, 0, 1, -1};

    /* Recebe uma matriz booleana open e devolve outra, marcando
     * todas as celulas que foram visitadas pelo fluxo que sai do topo */ 
    private static boolean[][][] flow (boolean[][][] open) {
        int n = open.length;
        boolean[][][] full = new boolean[n][n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)  
                flow (open, full, 0, i, j);
        return full;
    }
    
    /* Marca em full que a tripla (i,j,k) foi visitada e faz o mesmo recursivamente para todos
     * os vizinhos validos que estejam abertos (verificados na matriz open) */
    private static void flow (boolean[][][] open, boolean[][][] full, int i, int j, int k) {
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
    private static boolean percolates (boolean[][][] open) {
        int n = open.length;
        boolean[][][] full = flow (open);
        
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (full[n-1][i][j]) return true;
        
        return false;
    }

    /* Devolve a razao numero de percolacoes por m testes numa matriz n x n x n,
     * onde cada cada celula tem probabilidade p de estar aberta */
    private static double eval (int n, int m, double p) {
        boolean open[][][];
        int ans = 0;
        for (int i = 0; i < m; i++) {
            open = BooleanMatrix3D.random (n, p);
            if (percolates (open)) ans++;
        }
        return (double) ans/m; 
    }
    
    /* Devolve p tal que a probabilidade de uma matriz n x n x n com probabilidade p de 
     * ter cada sitio aberto percolar eh de 0.5 (os calculos sao feitos simulando m vezes
     * para um dado valor) */
    private static double findCritical (int n, int m) {
        double lo = 0, hi = 1;
        double eps = 1e-6;
        while (hi - lo > eps) {
            double mid = (lo+hi)/2;
            if (eval (n, m, mid) >= .5) hi = mid;
            else lo = mid + eps;
        }
        return lo;
    }
    
    public static void main (String[] args) {
        int n = Integer.parseInt (args[0]);
        int m = Integer.parseInt (args[1]);
        
        StdOut.println ("Probabilidade critica: " + findCritical (n, m));
    }
}


