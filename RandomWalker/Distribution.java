/* Exercicio 3.2.47 - Random walker
 * disponivel em http://introcs.cs.princeton.edu/java/32class/
 * Nome: Gabriel de Russo e Carmo
 * N USP: 9298041
 * Data: 15/03/2016
 * OBS: Compilado com 'javac-algs4' */
import edu.princeton.cs.algs4.*;

public class Distribution {
    
    /* Pinta o quadrado com canto inferior esquerdo em (x, y) com cores
     * quentes, baseado no quantificador f */
    private static void paint (int x, int y, int f) {
        if (f == 0) StdDraw.setPenColor (255, 255, 255);
        else {
            int r, g;
            r = 255;
            g = Math.max (255 - 5 * (f - 1), 0);
            if (g == 0) r = Math.max (255 - 5 * (f - 52), 50);
            StdDraw.setPenColor (r, g, 0);
        }
        StdDraw.filledSquare (x + .5, y + .5, .5);
    }
    
    /* Simula M RandomWalkers, cada um dando N passos e plota
     * os resultados num grid quadrado de lado 2l */
    private static void simulate (int n, int m, int l) {
        int x, y;
        int[][] freq = new int[2*l][2*l];
        
        for (int i = 0; i < m; i++) {
            RandomWalker walker = new RandomWalker ();
            freq[l][l]++;
            for (int j = 0; j < n; j++) {
                walker.step ();
                x = walker.x () + l; 
                y = walker.y () + l;
                if (x >= 0 && x < 2*l && y >= 0 && y < 2*l)
                    freq[x][y]++; 
            }
        }
        
        for (int i = 0; i < 2*l; i++)
            for (int j = 0; j < 2*l; j++)
                paint (i, j, freq[i][j]);
    }

    /* Recebe 3 argumentos via linha de comando:
     * n = numero de passos
     * m = numero de RandomWalkers
     * l = metade do lado do grid */
    public static void main (String[] args) {
        int n, m, l;
        n = Integer.parseInt (args[0]);
        m = Integer.parseInt (args[1]);
        l = Integer.parseInt (args[2]);
        
        StdDraw.setXscale (0, 2*l);
        StdDraw.setYscale (0, 2*l);
        
        simulate (n, m, l);
    }
}

