/* Limiar da conexidade 2D
 * Nome: Gabriel de Russo e Carmo
 * N USP: 9298041
 * Data: 27/04/2016 */
import edu.princeton.cs.algs4.*;
import java.util.ArrayList;
import java.lang.System;

public class LC2D {
    public static void main (String[] args) {
        double d = Double.parseDouble (args[0]);
        int t = Integer.parseInt (args[1]);
        if (args.length > 2) {
            int s = Integer.parseInt (args[2]);
            StdRandom.setSeed (s);
        }
        int g = (int) Math.ceil (1. / d);
        ArrayList<Point2Di>[][] v = (ArrayList<Point2Di>[][]) new ArrayList[g + 2][g + 2];
        DynamicUF uf = new DynamicUF (); 

        for (int i = 0; i < g + 2; i++)
            for (int j = 0; j < g + 2; j++)
                v[i][j] = new ArrayList<Point2Di> ();
        
        double soma, soma2;
        soma = soma2 = 0;
        long tot = 0;
        for (int tc = 0; tc < t; tc++) {
            boolean done = false;
            int n = 0;
            
            for (int i = 1; i < g + 1; i++)
                for(int j = 1; j < g + 1; j++)
                    v[i][j].clear ();
            uf.reset();

            StdOut.print("Teste " + tc);
            long ini = System.currentTimeMillis();
            while (!done) {
                double x = StdRandom.uniform();
                double y = StdRandom.uniform();
                Point2Di p = new Point2Di (x, y, n++);
                int r = 1 + (int) (x * g);
                int c = 1 + (int) (y * g);
                for (int i = r - 1; i <= r + 1; i++)
                    for (int j = c - 1; j <= c + 1; j++)
                        for (Point2Di q : v[i][j])
                            if (p.point.distanceTo (q.point) <= d)
                                uf.union (p.id, q.id);
                v[r][c].add (p);
                if (uf.count () == 1 && n > 1)
                    done = true;
            }
            long temp = (System.currentTimeMillis() - ini);
            StdOut.println(" " + temp + "ms");
            tot += temp;
            soma += n;
            soma2 += (long) n * (long) n;
        }
        StdOut.println ("Total " + tot + "ms");
        double med = soma / (double) t;
        double var = (soma2 - t * med * med) / (double) t;
        StdOut.println (med + " " + var);
    }
}


