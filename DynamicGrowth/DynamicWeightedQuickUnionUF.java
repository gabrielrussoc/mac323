/* Exercicio 1.5.20 - Dynamic growth (Algs 4)
 * Nome: Gabriel de Russo e Carmo
 * N USP: 9298041
 * Data: 02/04/2016
 * OBS: Compilado com 'javac-algs4' */
import edu.princeton.cs.algs4.*;

public class DynamicWeightedQuickUnionUF {
    private int N;
    private int[] id, sz;

    /* Instancia um UF dinamico. */
    public DynamicWeightedQuickUnionUF () {
        N = 16;
        id = new int[N];
        sz = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }

    /* Devolve o identificador de p no UF.
     * O metodo DEVE ser usado antes de se conectar quaisquer elementos.
     * Com isso, sendo M o elemento máximo, temos tempo proporcional a M 
     * nas operações relacionadas com rediomensionamento da estrutura e memoria
     * proporcional a M.
     * Pessoalmente, acredito que seria muito mais eficiente se esse metodo fosse
     * privado e a estrutura se ajeitasse independentemente do cliente. Alem disso, 
     * se usamos poucos numeros grandes, temos um desperdicio de espaco tremendo.
     * Um hashing seria muito eficiente para identificar novos elementos, mas decidi
     * seguir as restricoes impostas no PACA. */
    public int newSite (int p) {
        while (p >= N) {
            int[] nid, nsz;
            nid = new int[2*N];
            nsz = new int[2*N];
            for (int i = 0; i < N; i++) {
                nid[i] = id[i];
                nsz[i] = sz[i];
            }
            for (int i = N; i < 2*N; i++) {
                nid[i] = i;
                nsz[i] = 1;
            }
            N = 2*N;
            id = nid;
            sz = nsz;
        }
        return p;
    }

    /* Devolve o representante da componente de p. */
    public int find (int p) {
        if (id[p] == p) return p;
        return id[p] = find (id[p]);   
    }

    /* Une as componentes que contem p e q. Caso ja estejam na mesma 
     * componente, nao faz nada. */
    public void union (int p, int q) {
        p = find (p); q = find (q);
        if (p == q) return;
        if (sz[p] > sz[q]) {
            int aux = p;
            p = q;
            q = aux;
        }
        sz[q] += sz[p];
        id[p] = q;
    }
    
    /* Devolve true se p e q estao na mesma componente. */
    boolean connected (int p, int q) {
        return find (p) == find (q);
    }
    
    public static void main (String[] args) {
        DynamicWeightedQuickUnionUF uf = new DynamicWeightedQuickUnionUF ();
        int p, q, max = 0;
        while (!StdIn.isEmpty()) {
            p = StdIn.readInt(); 
            q = StdIn.readInt();
            p = uf.newSite (p);
            q = uf.newSite (q);
            max = Math.max(max, Math.max (p, q));
            uf.union (p, q);
        }
        for (int i = 0; i <= max; i++)
            if (uf.find (i) != i) 
                StdOut.println (i + " " + uf.find (i));
    }
}
