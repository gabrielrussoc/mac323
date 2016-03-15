import edu.princeton.cs.algs4.*;

public class RandomWalker {
    private int x, y;
    
    /* Instancia um RandomWalker na origem */
    public RandomWalker () {
        x = y = 0;
    }    

    /* Da um passo aleatorio em algumas das 4 direcoes: norte, sul,
     * leste ou oeste */
    public void step () {
        double p = StdRandom.uniform ();
        if (p < 1./4.) x++;
        else if (p < 2./4.) x--;
        else if (p < 3./4.) y++;
        else y--;
    }
    
    /* Devolve a distancia manhattan de um RandomWalker para a origem */
    public int distance () {
        return Math.abs (x) + Math.abs (y);
    }

    /* Retorna a coordenada x */
    public int x () {
        return x;
    }
    
    /* Retorna a coordenada y */
    public int y () {
        return y;
    }

    /* Para fins de teste, recebe um argumento na linha de comando:
     * o numero de passos para um RandomWalker */
    public static void main (String[] args) {
        int q = Integer.parseInt (args[0]);
        int maxi = 0;
        RandomWalker w = new RandomWalker ();
        for (int i = 0; i < q; i++) {
            w.step ();
            maxi = Math.max (maxi, w.distance ());
        }
        StdOut.println ("Max dist: " + maxi);
    }
}

