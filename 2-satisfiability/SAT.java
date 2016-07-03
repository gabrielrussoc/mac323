/* Exercicio 4.2.26 2-satisfiability (Algs4).
 * Nome: Gabriel de Russo e Carmo
 * N USP: 9298041
 * Data: 25/05/2016
 * OBS: Compilado com 'javac-algs4' */
import edu.princeton.cs.algs4.*;

public class SAT {
    private static boolean[] instack, vis;
    private static int[] ans, head, to, nx, d, low, stack, comp;
    private static int n, m, time, es, ps, cs;
    
    /* Devolve o minimo entre os inteiros a,b */
    private static int min (int a, int b) {
        return a < b ? a : b;
    }

    /* Algoritmo de Tarjan para achar SCC. Importante ressaltar que
     * ele acha as componentes, vértices do kernel DAG, em ordenação
     * topológica reversa */
    private static void dfs (int u) {
        vis[u] = true;
        low[u] = d[u] = time++;
        stack[ps++] = u;
        instack[u] = true;
        for (int e = head[u]; e != -1; e = nx[e]) {
            int v = to[e];
            if (!vis[v]) {
                dfs (v);
                low[u] = min (low[u], low[v]);
            } else if (instack[v]) low[u] = min (low[u], d[v]);
        }
        if (low[u] == d[u]) {
            int v;
            do {
                v = stack[--ps];
                instack[v] = false;
                comp[v] = cs;
                if (ans[v] == 0) {
                   ans[v] = 1;
                   ans[v^1] = 2;
                } 
            } while (v != u);
            cs++;
        }
    }

    public static void main (String[] args) {
        n = StdIn.readInt ();
        m = StdIn.readInt ();
        
        /* Inicializações */
        head = new int[2*n];
        nx = new int[2*m];
        to = new int[2*m];
        d = new int[2*n];
        low = new int[2*n];
        comp = new int[2*n];
        stack = new int[2*n];
        ans = new int[2*n];
        vis = new boolean[2*n];
        instack = new boolean[2*n];
        for (int i = 0; i < 2*n; i++) head[i] = -1;
        es = ps = cs = time = 0;

        /* Leitura e montagem do grafo */
        for (int i = 0; i < m; i++) {
            int u, v;
            u = StdIn.readInt (); 
            v = StdIn.readInt ();
            u = u > 0 ? 2*(u-1) : 2*(-u-1)+1;
            v = v > 0 ? 2*(v-1) : 2*(-v-1)+1;
            to[es] = v; nx[es] = head[u^1]; head[u^1] = es++;
            to[es] = u; nx[es] = head[v^1]; head[v^1] = es++;
        }

        /* Calculo das SCC + topo do Kernel DAG */
        for (int u = 0; u < 2*n; u++) if(!vis[u]) dfs(u);

        /* Decide se tem resposta */
        boolean isSat = true;
        for (int u = 0; u < 2*n && isSat; u += 2)
            if(comp[u] == comp[u^1]) 
                isSat = false;

        /* Saida */
        if (isSat) {
            StdOut.println ("VERDADE");
            for (int u = 0; u < n; u++) StdOut.print ((ans[2*u] & 1) + " ");
            StdOut.println ();
        } else StdOut.println("MENTIRA");
    }
}
