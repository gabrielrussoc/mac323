public class Plot {
    
    private static double eval (int m, int n) {
        double med = 0;
        for (int i = 0; i < m; i++){
            int maxi = 0;
            RandomWalker w = new RandomWalker();
            for (int j = 0; j < n; j++) {
                w.step();
                maxi = Math.max(maxi, w.distance());
            }
            med += maxi;
        }
        return med/m;
    }

    private static void curve (int n, int m) {
        for (int i = 0; i < n; i++)
            StdDraw.filledCircle (i, eval(m,i), .05);
    }

    private static void curve2 (double x0, double y0, double x1, double y1) {
        double gap = .001;
        double err = .00025;
        double xm = (x0 + x1) / 2;
        double ym = (y0 + y1) / 2;
        double fxm = Math.sqrt(xm);
        if (x1 - x0 < gap || Math.abs (ym - fxm) < err) {
            StdDraw.line (x0, y0, x1, y1);
            return;
        }
        curve2 (x0, y0, xm, fxm);
        StdDraw.filledCircle (xm, fxm, .005);
        curve2 (xm, fxm, x1, y1);
    }
    
    private static void curve3 (double x0, double y0, double x1, double y1) {
        double gap = .001;
        double err = .00025;
        double xm = (x0 + x1) / 2;
        double ym = (y0 + y1) / 2;
        double fxm = 1.5*Math.sqrt(xm);
        if (x1 - x0 < gap || Math.abs (ym - fxm) < err) {
            StdDraw.line (x0, y0, x1, y1);
            return;
        }
        curve3 (x0, y0, xm, fxm);
        StdDraw.filledCircle (xm, fxm, .005);
        curve3 (xm, fxm, x1, y1);
    }

    public static void main (String[] args) {
        int n = Integer.parseInt (args[0]);
        int m = Integer.parseInt (args[1]);
        
        StdDraw.setXscale (-50, n);
        StdDraw.setYscale (-5, n/10);
        
        StdDraw.setPenColor(0,0,0);
        StdDraw.text(-2.5,-2.5,"0");
        StdDraw.text(-25,n/10-2,"n");
        StdDraw.text(-25,n/10-3,"_");
        StdDraw.text(-25,n/10-7,"10");
        StdDraw.text(n-25,-2.5,"n");
        
        StdDraw.text(n/2,-2.5,"Number of steps");
        StdDraw.text(-25,n/20,"Maximum distance", 90);
        
        
        StdDraw.setPenColor(255,0,0);
        StdDraw.text(4*n/5, n/80, "Sqrt (n)");
        curve2(0,0,n,n/10);
        
        StdDraw.setPenColor(0,255,255);
        StdDraw.text(4*n/5, n/120, "1.5 * Sqrt (n)");
        curve3(0,0,n,n/10);
        

        StdDraw.setPenColor(0,0,0);
        StdDraw.line(0,0,n,0);
        StdDraw.line(0,0,0,n);
        curve (n, m);
        
    }
}

