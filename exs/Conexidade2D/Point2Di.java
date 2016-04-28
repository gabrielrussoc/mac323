/* Problema da conexidade 2D
 * disponivel em https://github.com/victorsenam/pdfs-mac323/raw/master/conexidade-2d/enunciado.pdf
 * Nome: Gabriel de Russo e Carmo
 * N USP: 9298041
 * Data: 27/04/2016 */
import edu.princeton.cs.algs4.*;

public class Point2Di {
    public int id;
    public Point2D point;

    public Point2Di (double x, double y, int id) {
        point = new Point2D(x, y);
        this.id = id;
    }
}

