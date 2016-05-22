/* Exercicio Pontes
 * Nome: Gabriel de Russo e Carmo
 * N USP: 9298041
 * Data: 17/05/2016
 * OBS: Compilado com 'javac-algs4' */
import java.util.*;
import edu.princeton.cs.algs4.*;

public class Pontes {
    private static int[] to, nx, head;
    private static int n, m;
    private static int[] low, d;
    private static boolean[] vis;
    private static int time, es;

    /* Devolve o minimo entre dois inteiros a,b */
    private static int min (int a, int b) {
        return a < b ? a : b;
    }

    /* Algoritmo para detecçao das pontes 
     * A ideia é pegar uma árvore geradora do grafo (no caso, a árvore 
     * da dfs mesmo) e concluir as coisas com base nos nós mais altos que cada nó consegue
     * acessar. Teremos basicamente 2 tipos de arestas: as arestas da árvore (tree edges) e
     * as arestas de volta (back edges). Para cada nó, vamos guardar seu tempo de descoberta no vetor d[] e
     * qual o nó mais "antigo" que ele consegue acessar no vetor low[], considerando apenas sua subárvore
     * e uma backedge. Seja u um nó da ávore. Inicialmente o nó mais "antigo" que u alcança é si
     * próprio. Para cada v não visitado na sua lista de adjacência, disparamos a dfs e fazemos
     * que low[u] = min(low[v], low[v]), pois podemos descer de u até. Caso v já esteja visitado e não seja
     * o pai de u, temos uma backedge, portanto low[u] = min(low[u], d[v]), pois podemos pegar essa backedge para
     * v. Uma aresta u-v da árvore de dfs é ponte se não há como o vértice v alcançar u além desta. Isso se traduz
     * para low[v] > d[u], pois o nó mais antigo que v alcança é mais profundo que u.*/
    private static void dfs (int u, int p) {
        vis[u] = true;
        low[u] = d[u] = time++;
        for (int e = head[u]; e != -1; e = nx[e]) {
            int v = to[e];
            if (!vis[v]) {
                dfs(v, u);
                low[u] = min (low[u], low[v]);
                if (low[v] > d[u]) StdOut.println (to[e^1] + " " + to[e]);
            } else if (v != p) low[u] = min (low[u], d[v]);
        }
    }
    
    public static void main (String[] args) {
        n = StdIn.readInt ();
        m = StdIn.readInt ();
        
        /* Tem pouca coisa pra inicializar */
        to = new int[2*m];
        nx = new int[2*m];
        head = new int[n];
        vis = new boolean[n];
        low = new int[n];
        d = new int[n];
        time = es = 0;
        for (int i = 0; i < n; i++) head[i] = -1;

        /* Montagem do grafo */
        for (int i = 0; i < m; i++) {
            int u, v;
            u = StdIn.readInt ();
            v = StdIn.readInt ();
            to[es] = v; nx[es] = head[u]; head[u] = es++;
            to[es] = u; nx[es] = head[v]; head[v] = es++;
        }
        dfs (0, -1);
    }
}


