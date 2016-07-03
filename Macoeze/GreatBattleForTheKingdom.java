/* Exercicio No Reinado de MACOEZE
 * Nome: Gabriel de Russo e Carmo
 * N USP: 9298041
 * Data: 19/05/2016
 * OBS: Compilado com 'javac-algs4' */
import edu.princeton.cs.algs4.*;
import java.lang.Comparable;
import java.util.HashMap;

public class GreatBattleForTheKingdom {
    private int[] to, nx, head, wei, hero;
    private int[][] d;
    private int n, m, k, es;
    private final int inf = 1234567890;
    private HashMap<String, Integer> hm;
    private String[] index;

    /* Classe auxiliar para a fila de prioridade */
    private class Vertex implements Comparable<Vertex> {
        public final int u, dist;
        public Vertex (int u, int dist) {
            this.u = u;
            this.dist = dist;
        }

        public int compareTo (Vertex o) {
            int x = this.dist - o.dist;
            return x;
        }
    }

    /* Roda um algoritmo de Dijkstra para cada heroi e para o feiticeiro */
    private void get_distances () {
        for (int i = 0; i < k + 1; i++) {
            MinPQ<Vertex> pq = new MinPQ<Vertex> ();  
            d[i][hero[i]] = 0;
            pq.insert (new Vertex (hero[i], 0));
            while (!pq.isEmpty ()) {
                Vertex x = pq.delMin ();
                if (x.dist > d[i][x.u]) continue;
                for (int e = head[x.u]; e != -1; e = nx[e]) {
                    int v = to[e];
                    int w = wei[e];
                    if (d[i][v] > d[i][x.u] + w) {
                        d[i][v] = d[i][x.u] + w;
                        pq.insert (new Vertex (v, d[i][v]));
                    }
                }
            }
        }
    }

    /* Para melhor organização, esta funcao é equivalente a main */
    private void solve () {
        n = StdIn.readInt ();
        m = StdIn.readInt ();
        k = StdIn.readInt ();

        /* Inicializações */
        es = 0;
        head = new int[n];
        to = new int[m];
        nx = new int[m];
        wei = new int[m];
        d = new int[k + 1][n];
        hero = new int[k + 1];
        hm = new HashMap<String, Integer> ();
        index = new String[n];
        for (int i = 0; i < n; i++) head[i] = -1;
        for (int i = 0; i < k + 1; i++)
            for (int j = 0; j < n; j++)
                d[i][j] = inf;

        /* Leitura do grafo. FUNCIONANDO PARA STRINGS */
        int cont = 0;
        for (int i = 0; i < m; i++) {
            String a, b;
            int u, v, w;
            a = StdIn.readString ();
            b = StdIn.readString ();
            if (!hm.containsKey (a)) { hm.put(a, cont); index[cont++] = a; }
            if (!hm.containsKey (b)) { hm.put(b, cont); index[cont++] = b; }
            u = hm.get (a); v = hm.get (b);
            w = StdIn.readInt ();
            to[es] = v; wei[es] = w; nx[es] = head[u]; head[u] = es++; 
        }

        /* Leitura da posicao dos herois e do feiticeiro */
        for (int i = 0; i < k; i++) hero[i] = hm.get (StdIn.readString ());
        hero[k] = hm.get (StdIn.readString ());

        /* Djisktras */
        get_distances ();

        /* Construcao da resposta. Se todos os heróis chegam numa cidade antes do 
         * feiticeiro, ela é segura */
        Queue<Integer> q = new Queue<Integer> ();
        int ans = 0;
        for (int c = 0; c < n; c++) {
            int h;
            for (h = 0; h < k; h++) if (d[h][c] >= d[k][c]) break;
            if (h == k) {
                ans++;
                q.enqueue (c);
            }
        }
        
        /* Saida */
        if (ans > 0) {
            StdOut.println ("VICTORY AND HAPPY EVER AFTER");
            StdOut.println (ans);
            for (int c : q) StdOut.print (index[c] + " ");
            StdOut.println ();
        } else StdOut.println ("DEMISE OF THE KINGDOM");
    }

    public static void main (String[] args) {
        new GreatBattleForTheKingdom ().solve ();
    }
}
