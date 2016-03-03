import edu.princeton.cs.algs4.*;

/* Exercicio 1.6.15 - Hubs and autorithies 
 * disponivel em http://introcs.cs.princeton.edu/java/16pagerank/
 * Nome: Gabriel de Russo e Carmo
 * N USP: 9298041
 * Data: 03/03/2016 */

/* Apos rodar alguns testes usando os programas do livro (Transition e PageRank)
 * pode-se notar que o rank das autorithies eh muito menor do que o dos hubs para uma
 * grande quantidade de vertices. De fato, como as autorithies so enviam links,
 * espera-se que elas sejam pouco visitadas, uma vez que a cada vertice, existe apenas 
 * 0.1 * 1/|vertices| de chance delas serem visitadas (com excecao dos vertices com grau
 * de saida 0). No caso dos hubs, a grande quantidade de arcos que eles recebem faz com que
 * seu ranking seja muito mais alto. */ 

public class HubsAndAutorithies {
    
    /* Imprime na tela um grafo aleatorio com n vertices e m arestas 
     * (sem nenhuma restricao) */
    private static void generateGraph (int n, int m) {
        int u, v;
        for (int i = 0; i < m; i++) {
            u = StdRandom.uniform (0, n);
            v = StdRandom.uniform (0, n);
            StdOut.println (u + " " + v);
        }
    }
    
    /* Imprime na tela 'h' novos hubs para um grafo de 'n' vertices.
     * Cada novo hub recebe arcos de 10% dos 'n' vertices ja existentes,
     * escolhidos aleatoriamente sem restricao */
    private static void addHubs (int n, int h) {
        int u;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < (n + 9)/10; j++) {
                u = StdRandom.uniform (0, n);
                StdOut.println (u + " " + (n + i));
            }
        }
    }

    /* Imprime na tela 'a' novas autorithies para um grafo de n + h vertices.
     * Cada nova authority manda arcos para 10% dos 'n' vertices ja existentes,
     * escolhidos aleatoriamente sem restricao */
    private static void addAutorithies (int n, int a, int h) {
        int u;
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < (n + 9)/10; j++) {
                u = StdRandom.uniform (0, n);
                StdOut.println ((n + h + i) + " " + u);
            }
        }
    }
    /* Recebe 4 parametros via linha de comando: numero de vertices, numero de arestas
     * numero de hubs e numero de authorities */
    public static void main (String[] args) {
        int n, m, hubs, autorithies;
        n = Integer.parseInt (args[0]);
        m = Integer.parseInt (args[1]);
        hubs = Integer.parseInt (args[2]);
        autorithies = Integer.parseInt (args[3]);
        
        StdOut.println (n + hubs + autorithies);
        generateGraph (n, m);
        addHubs (n, hubs);
        addAutorithies (n, autorithies, hubs);
    }   
}

