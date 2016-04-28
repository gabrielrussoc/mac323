/* Problema da conexidade 2D
 * disponivel em https://github.com/victorsenam/pdfs-mac323/raw/master/conexidade-2d/enunciado.pdf
 * Nome: Gabriel de Russo e Carmo
 * N USP: 9298041
 * Data: 27/04/2016 */
import edu.princeton.cs.algs4.*;
import java.util.ArrayList;

public class PC2D {
    public static void main (String[] args) {
        int n = StdIn.readInt ();
        double d = StdIn.readDouble ();
        int t = (int) Math.ceil (1. / d);
        ArrayList<Point2Di>[][] v = (ArrayList<Point2Di>[][]) new ArrayList[t + 2][t + 2];
        UF uf = new UF (n); 

        for (int i = 0; i < t + 2; i++)
            for (int j = 0; j < t + 2; j++)
                v[i][j] = new ArrayList<Point2Di> ();
        
        for (int k = 0; k < n; k++) {
            double x = StdIn.readDouble ();
            double y = StdIn.readDouble ();
            Point2Di p = new Point2Di (x, y, k);
            int r = 1 + (int) (x * t);
            int c = 1 + (int) (y * t);
            for (int i = r - 1; i <= r + 1; i++)
                for (int j = c - 1; j <= c + 1; j++)
                    for (Point2Di q : v[i][j])
                        if (p.point.distanceTo (q.point) <= d)
                            uf.union (p.id, q.id);
            v[r][c].add (p);
        }
        
        if (uf.count () == 1)
            StdOut.println ("Sim");
        else
            StdOut.println ("Nao");
    }
}

