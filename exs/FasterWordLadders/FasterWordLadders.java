/* Exercicio 4.1.12 - Faster Word Ladders(Algs 4)
 * disponivel em http://algs4.cs.princeton.edu/41graph/
 * Nome: Gabriel de Russo e Carmo
 * N USP: 9298041
 * Data: 15/05/2016
 * OBS: Compilado com 'javac-algs4' */
import edu.princeton.cs.algs4.*;
import java.util.*;

public class FasterWordLadders {
    private static int n = 16;
    private static String[] words = new String[n];
    private static String[] index = new String[n];
    private static HashMap<String, Integer> hm = new HashMap<String, Integer> ();

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

    /* Dobra a capacidade dos vetores words e index */
    private static void resize () {
        String n_words[] = new String[2*n];
        String n_index[] = new String[2*n];
        for (int i = 0; i < n; i++) {
            n_words[i] = words[i];
            n_index[i] = index[i];
        }
        index = n_index;
        words = n_words;
        n = 2 * n;
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

    public static void main (String[] args) {
        In in = new In (args[0]);
        int word_count = 0;

        /* Leitura da entrada */
        while (!in.isEmpty ()) {
            String s = in.readString ();
            if (hm.containsKey (s)) continue;
            words[word_count] = s;
            index[word_count] = s;
            hm.put (s, word_count++);
            if (word_count == n) resize ();
        }

        /* Montagem do grafo */
        Graph G = new Graph (word_count);
        /* Blocos de mesmo tamanho */
        Arrays.sort (words, 0, word_count, new helpSort (0));
        int i = 0, j;
        while (i < word_count) {
            j = i + 1;
            while (j < word_count && words[j].length () == words[i].length ()) j++;
            for (int k = 0; k < words[i].length (); k++) {
                Arrays.sort (words, i, j, new helpSort (k));
                for (int p = i; p < j; p++) {
                    int l = p + 1;
                    while (l < j && isNeighbor (words[p], words[l], k)) {
                        G.addEdge (hm.get (words[p]), hm.get (words[l]));
                        l++;
                    }
                }
            }
            i = j;
        }

        /* Blocos de tamanho diferente */
        Arrays.sort (words, 0, word_count);
        i = 0;
        for (i = 0; i < word_count; i++) {
            for (j = i + 1; j < word_count; j++) {
                int p = isPrefix (words[i], words[j]);
                if (p == 1) G.addEdge (hm.get (words[i]), hm.get (words[j]));
                else if (p == 0) break;
            }
        }

        /* Queries */
        while (!StdIn.isEmpty ()) {
            String from, to;
            from = StdIn.readString ();
            to = StdIn.readString ();
            BreadthFirstPaths bfs = new BreadthFirstPaths (G, hm.get (from));
            if (bfs.hasPathTo (hm.get (to))) {
                StdOut.println (bfs.distTo (hm.get (to)));
                for (int v : bfs.pathTo (hm.get (to)))
                    StdOut.println (index[v]);
            } else StdOut.println ("NOT CONNECTED");
        } 
    }
}
