/* Exercicio 4.2.35 - Core vertices (Algs 4)
 * disponivel em http://algs4.cs.princeton.edu/42digraph/
 * Nome: Gabriel de Russo e Carmo
 * N USP: 9298041
 * Data: 17/05/2016
 * OBS: Compilado com 'javac-algs4' */
import java.util.*;
import edu.princeton.cs.algs4.*;

public class CoreVertices {
    private static List<Integer>[] adj;
    private static int n, m;
    private static int[] comp, stack, low, d, deg;
    private static boolean[] vis, instack;
    private static int ps, cs, time;

    /* Devolve o minimo entre dois inteiros a,b */
    private static int min (int a, int b) {
        return a < b ? a : b;
    }

    /* Algoritmo de Tarjan para achar SCC */
    private static void dfs (int u) {
        vis[u] = true;
        low[u] = d[u] = time++;
        stack[ps++] = u;
        instack[u] = true;
        for (int v : adj[u]) {
            if (!vis[v]) {
                dfs(v);
                low[u] = min (low[u], low[v]);
            } else if (instack[v]) low[u] = min (low[u], d[v]);
        }
        if (low[u] == d[u]) {
            int v;
            do {
                v = stack[--ps];
                instack[v] = false;
                comp[v] = cs;
            } while (v != u);
            cs++;
        }
    }
    
    public static void main (String[] args) {
        n = StdIn.readInt ();
        m = StdIn.readInt ();
        
        /* Tem pouca coisa pra inicializar */
        adj = (ArrayList<Integer>[]) new ArrayList[n];
        stack = new int[n];
        instack = new boolean[n];
        vis = new boolean[n];
        comp = new int[n];
        low = new int[n];
        d = new int[n];
        time = cs = ps = 0;
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<Integer> ();

        /* Montagem do grafo */
        for (int i = 0; i < m; i++) {
            int u, v;
            u = StdIn.readInt ();
            v = StdIn.readInt ();
            adj[u].add (v);
        }

        /* Calculo das componentes */
        for (int i = 0; i < n; i++) if (!vis[i]) dfs (i);
        deg = new int[cs];
        
        /* Grau de entrada das componentes */
        for (int u = 0; u < n; u++)
            for (int v : adj[u])
                if (comp[u] != comp[v])
                    deg[comp[v]]++;

        /* Se apenas uma componente tem grau de entrada 0, todos os vertices dela sao core */
        int zero = 0;
        int p = -1;
        for (int u = 0; u < cs; u++) if (deg[u] == 0) { zero++; p = u; }
        if (zero == 1) {
            for (int u = 0; u < n; u++) if (comp[u] == p) StdOut.print (u + " ");
            StdOut.println ();
        }
    }
}

