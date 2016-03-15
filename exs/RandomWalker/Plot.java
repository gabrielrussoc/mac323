/* Exercicio 3.2.47 - Random walker
 * disponivel em http://introcs.cs.princeton.edu/java/32class/
 * Nome: Gabriel de Russo e Carmo
 * N USP: 9298041
 * Data: 15/03/2016
 * OBS: Compilado com 'javac-algs4' */

/* Essa classe so foi usada para a criacao de uma das imagens */
public class Plot {
    
    /* Devolve a media da distancia maxima de um walker para m simulacoes, cada
     * uma com n passos */
    private static double eval (int m, int n) {
        double med = 0;
        for (int i = 0; i < m; i++){
            int maxi = 0;
            RandomWalker w = new RandomWalker ();
            for (int j = 0; j < n; j++) {
                w.step();
                maxi = Math.max(maxi, w.distance ());
            }
            med += maxi;
        }
        return med / m;
    }

    /* Plota os valores medios para as distancias maximas de 0 ate n walkers
     * fazendo m simulacoes para cada n */
    private static void plotMaxDist (int n, int m) {
        for (int i = 0; i < n; i++)
            StdDraw.filledCircle (i, eval (m, i), .05);
    }

    /* Plota o grafico da raiz de N no retangulo (x0,y0) e (x1,y1) */
    private static void plotSqrt (double x0, double y0, double x1, double y1) {
        double gap = .001;
        double err = .00025;
        double xm = (x0 + x1) / 2;
        double ym = (y0 + y1) / 2;
        double fxm = Math.sqrt (xm);
        if (x1 - x0 < gap || Math.abs (ym - fxm) < err) {
            StdDraw.line (x0, y0, x1, y1);
            return;
        }
        plotSqrt (x0, y0, xm, fxm);
        StdDraw.filledCircle (xm, fxm, .005);
        plotSqrt (xm, fxm, x1, y1);
    }
    
    /* Plota o grafico de 1,5 * raiz de N no retangulo (x0,y0) e (x1,y1) */
    private static void plotSqrt2 (double x0, double y0, double x1, double y1) {
        double gap = .001;
        double err = .00025;
        double xm = (x0 + x1) / 2;
        double ym = (y0 + y1) / 2;
        double fxm = 1.5 * Math.sqrt (xm);
        if (x1 - x0 < gap || Math.abs (ym - fxm) < err) {
            StdDraw.line (x0, y0, x1, y1);
            return;
        }
        plotSqrt2 (x0, y0, xm, fxm);
        StdDraw.filledCircle (xm, fxm, .005);
        plotSqrt2 (xm, fxm, x1, y1);
    }

    /* Plota o grafico para todos os valores de n até 1000, fazendo 1000 testes
     * para cada valor. NAO TESTADO PARA OUTROS VALORES DE N, PERIGO DE GRAFICO FEIO */
    public static void main (String[] args) {
        int n = 1000;
        int m = 1000;
        
        StdDraw.setXscale (-n/20, n);
        StdDraw.setYscale (-n/200, n/10);
        
        StdDraw.setPenColor (0, 0, 0);
        StdDraw.text (-n/400, -n/400, "0");
        StdDraw.text (-n/40, n/10 - n/500, "n");
        StdDraw.text (-n/40, n/10 - n/333, "_");
        StdDraw.text (-n/40, n/10 - n/142, "10");
        StdDraw.text (n - n/40, -n/400, "n");
        
        StdDraw.text (n/2, -n/400, "Number of steps");
        StdDraw.text (-n/40, n/20, "Maximum distance", 90);
        
        
        StdDraw.setPenColor (255, 0, 0);
        StdDraw.text (4*n/5, n/80, "Sqrt (n)");
        plotSqrt (0, 0, n, n/10);
        
        StdDraw.setPenColor (0,255,255);
        StdDraw.text (4*n/5, n/120, "1.5 * Sqrt (n)");
        plotSqrt2 (0, 0, n, n/10);
        

        StdDraw.setPenColor (0, 0, 0);
        StdDraw.line (0, 0, n, 0);
        StdDraw.line (0, 0, 0, n);
        plotMaxDist (n, m);
    }
}

