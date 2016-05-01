/* Limiar da conexidade 2D
 * Nome: Gabriel de Russo e Carmo
 * N USP: 9298041
 * Data: 30/04/2016
 * OBS: Compilado com 'javac-algs4' */
import edu.princeton.cs.algs4.*;

public class DynamicUF {
    private int N; /* quantidade que o UF comporta */
    private int P; /* quantidade que o UF esta usando */
    private int[] id, sz;
    private int comp; /* numero de componentes */

    /* Instancia um UF dinamico. */
    public DynamicUF () {
        N = 16;
        id = new int[N];
        sz = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
            sz[i] = 1;
        }
        P = comp = 0;
    }

    /* Dobra a capacidade do UF */
    private void resize () {
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

    /* Devolve o identificador de um novo elemento do UF */
    public int newSite () {
        if (P == N) resize();
        comp++;
        return P++;
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
        comp--;
    }
    
    /* Devolve true se p e q estao na mesma componente. */
    boolean connected (int p, int q) {
        return find (p) == find (q);
    }

    /* Retorna quantas componentes temos no UF */
    public int count () {
        return comp;
    }
}

