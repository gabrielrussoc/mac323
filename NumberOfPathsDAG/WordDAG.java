/* Exercicio 4.2.33 - Number of a paths in a DAG (Algs 4)
 * disponivel em http://algs4.cs.princeton.edu/42digraph/
 * Nome: Gabriel de Russo e Carmo
 * N USP: 9298041
 * Data: 15/05/2016
 * OBS: Compilado com 'javac-algs4' */
import edu.princeton.cs.algs4.*;
import java.util.HashMap;
import java.util.Comparator;
import java.util.Arrays;
import java.math.BigInteger;

public class WordDAG {
    private Digraph G;
    private String[] index;
    private HashMap<String, Integer> hm;
    private Topological topo;
    private int n;
    
    /* Comparador auxiliar para sortar palavras cicladas */
    private static class helpSort implements Comparator<String> {
        private int idx;

        public helpSort (int idx) {
            this.idx = idx;
        }

        public int compare (String a, String b) {
            int d = a.length () - b.length ();
            if (d != 0) return d;
            int t = a.length ();
            for (int i = 0; i < t; i++) {
                int c = a.charAt ((idx + i) % t) - b.charAt ((idx + i) % t);
                if (c != 0) return c;
            }
            return 0;
        }
    }
    
    /* Devolve true se 'a' e 'b' diferem apenas no ultimo caractere.
     * Ambas são cicladas 'idx' pra direita */
    private static boolean isNeighbor (String a, String b, int idx) {
        int t = a.length ();
        for (int i = 0; i < t - 1; i++)
            if (a.charAt ((idx + i) % t) != b.charAt ((idx + i) % t)) 
                return false;
        return true;
    }

    /* Verifica se a String 'a' é prefixo da String 'b'
     * Retorna 0 se não for prefixo ou a quantidade
     * de caracteres a mais que 'b' tem do que 'a' */
    private static int isPrefix (String a, String b) {
        if (a.length () > b.length ()) return 0;
        int ret = 0;
        for (int i = 0; i < a.length (); i++)
            if (a.charAt (i) != b.charAt (i)) return ret++;
        if (ret == 0) return b.length () - a.length ();
        else return 0;
    }
    
    public WordDAG (String[] strings) {
        n = strings.length;
        hm = new HashMap<String, Integer> ();
        index = new String[n];
        for (int i = 0; i < n; i++) {
            index[i] = strings[i];
            hm.put (strings[i], i);
        }
        G = new Digraph (n);
        /* Blocos de mesmo tamanho */
        Arrays.sort (strings, 0, n, new helpSort (0));
        int i = 0, j;
        while (i < n) {
            j = i + 1;
            while (j < n && strings[j].length () == strings[i].length ()) j++;
            for (int k = 0; k < strings[i].length (); k++) {
                Arrays.sort (strings, i, j, new helpSort (k));
                for (int p = i; p < j; p++) {
                    int l = p + 1;
                    while (l < j && isNeighbor (strings[p], strings[l], k)) {
                        G.addEdge (hm.get (strings[l]), hm.get (strings[p]));
                        l++;
                    }
                }
            }
            i = j;
        }
        
        /* Blocos de tamanho diferente */
        Arrays.sort (strings, 0, n);
        i = 0;
        for (i = 0; i < n; i++) {
            for (j = i + 1; j < n; j++) {
                int p = isPrefix (strings[i], strings[j]);
                if (p == 1) G.addEdge (hm.get (strings[j]), hm.get (strings[i]));
                else if (p == 0) break;
            }
        }
        topo = new Topological (G);
    }
    
    /* Printa o DAG */
    public void PrintDag () {
        Out saida = new Out ("saida.txt");
        for (int u = 0; u < n; u++)
            for (int v : G.adj (u))
                saida.println (index[u] + " " + index[v]);
        saida.close ();
    }

    /* Printa a quantidade de caminhos (arbitrariamente grande) */
    public void PrintPathCount (String a, String b) {
        Out saida = new Out ("saida.txt");
        int s = hm.get (a);
        int t = hm.get (b);
        Iterable<Integer> toposort = topo.order ();
        Stack<Integer> st = new Stack<Integer> ();
        boolean in = false;
        for (int i : toposort) {
            if (i == s) in = true;
            else if (i == t) break;
            if (in) st.push (i);
        }
        BigInteger[] dp = new BigInteger[n];
        dp[t] = BigInteger.ONE;
        for (int u : st) {
            dp[u] = BigInteger.ZERO;
            for (int v : G.adj(u)) {
                if(topo.rank (v) > topo.rank (u) && topo.rank (v) <= topo.rank (t))
                    dp[u] = dp[u].add (dp[v]);
            }
        }
        if (dp[s] == null) dp[s] = BigInteger.ZERO;
        saida.println (dp[s]);
        saida.close ();
    }

    public static void main (String[] args) {
        String s, t;
        String[] v = new String[1000000];
        s = StdIn.readString ();
        t = StdIn.readString ();
        int n = 0;
        while (!StdIn.isEmpty ()) v[n++] = StdIn.readString ();
        String[] vv = new String[n];
        for (int i = 0; i < n; i++) vv[i] = v[i];
        WordDAG G = new WordDAG (vv);
        //G.PrintDag ();
        G.PrintPathCount (s, t);
    }

}
