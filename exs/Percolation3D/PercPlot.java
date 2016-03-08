import edu.princeton.cs.algs4.*;

/* Exercicio 2.4.18 - Percolation in three dimensions 
 * disponivel em http://introcs.cs.princeton.edu/java/24percolation/
 * Nome: Gabriel de Russo e Carmo
 * N USP: 9298041
 * Data: 08/03/2016
 * OBS: Compilado com 'javac-algs4' */
public class PercPlot {
    
    /* Plota o grafico aproximado no retangulo com canto inferior esquerdo (x0, y0) e 
     * superior direito (x1, y1), com base em alguma funcao f */
    public static void curve (int n, int m, double x0, double y0, double x1, double y1) {
        double gap = .01;
        double err = .0025;
        double xm = (x0 + x1) / 2;
        double ym = (y0 + y1) / 2;
        double fxm = Percolation3D.eval (n, m, xm);
        if (x1 - x0 < gap || Math.abs (ym - fxm) < err) {
            StdDraw.line (x0, y0, x1, y1);
            return;
        }
        curve (n, m, x0, y0, xm, fxm);
        StdDraw.filledCircle (xm, fxm, .005);
        curve (n, m, xm, fxm, x1, y1);
    }

    public static void main (String[] args) {
        int n = Integer.parseInt (args[0]);
        int m = Integer.parseInt (args[1]);

        StdDraw.setXscale (-.1, 1.1);
        StdDraw.setYscale (-.1, 1.1);
        
        StdDraw.setPenColor (192, 192, 192);
        StdDraw.line(0, 0, 1.1, 0);
        StdDraw.line(0, 0, 0, 1.1);
        
        StdDraw.setPenColor (0, 0, 0);
        StdDraw.line(-.01, 1, 0.01, 1);
        StdDraw.line(1, -.01, 1, .01);
        StdDraw.text(-.03, -.04, "0");
        StdDraw.text(-.03, 1, "1");
        StdDraw.text(1, -.04, "1");
        
        StdDraw.text(.5, -.05, "site vacancy probabilty p");
        StdDraw.text(-.05, .5, "percolation probability", 90);
        StdDraw.text(1, -.04, "1");
        
        PercPlot.curve (n, m, 0.0, 0.0, 1.0, 1.0);
    }
}

