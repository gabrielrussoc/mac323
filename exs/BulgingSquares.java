/* Exercicio 1.5.14 - Bulging Squares 
 * disponivel em http://introcs.cs.princeton.edu/java/15inout/
 * Nome: Gabriel de Russo e Carmo
 * N USP: 9298041
 * Data: 25/02/2016
 * OBS: Compilado com 'javac-algs4' */
public class BulgingSquares {

    /* As seguintes constantes sao responsaveis pela quantidade e tamanho
     * dos elementos da saida. Os valores CORNER e RADIUS foram obtidos 
     * experimentalmente. */
    private static final int SQUARES = 15;
    private static final double SIZE = 1./SQUARES;
    private static final double CORNER = SIZE/6;
    private static final double RADIUS = .42;

    /* Reponsavel por pintar os cantos do quadrado com centro em (x,y) 
     * de acordo com a mascara de bits 'pattern', conforme o seguinte:
     * 1----2
     * |    |
     * |    |
     * 0----3 */
    private static void fillDots (double x, double y, int pattern) {
        if ((pattern & (1 << 0)) != 0)
            StdDraw.filledSquare (x + CORNER, y + CORNER, CORNER/1.5);
        if ((pattern & (1 << 1)) != 0)
            StdDraw.filledSquare (x + CORNER, y + SIZE - CORNER, CORNER/1.5);
        if ((pattern & (1 << 2)) != 0)
            StdDraw.filledSquare (x + SIZE - CORNER, y + SIZE - CORNER, CORNER/1.5);
        if ((pattern & (1 << 3)) != 0)
            StdDraw.filledSquare (x + SIZE - CORNER, y + CORNER, CORNER/1.5);
    }
    
    /* Retorna a distancia euclideana entre dois pontos. */
    private static double distance (double x1, double y1, double x2, double y2) {
        return Math.sqrt ((x1 - x2) * (x1 - x2) + (y1 - y2)*(y1 - y2));   
    }

    /* Desenha o tabuleiro quadriculado */
    private static void drawGrid () {
        StdDraw.setPenColor (StdDraw.BLACK);
        int i, j;
        double x, y;
        for (i = 0; i < SQUARES; i++) {
            for (j = i % 2; j < SQUARES; j += 2) {
                x = j * SIZE + SIZE/2;
                y = i * SIZE + SIZE/2;
                StdDraw.filledSquare (x, y, SIZE/2);
            }
        }
    }
    
    /* Decide quais quadrados menores devem ser desenhados sobre uma celula 
     * do tabuleiro (tanto a posicao quanto a cor). */
    private static void drawDots () {
        int i, j, pattern;
        double x, y, sx, sy;
        for (i = 0; i < SQUARES; i++) {
            for(j = 0; j < SQUARES; j++) {
                x = i * SIZE + SIZE/2;
                y = j * SIZE + SIZE/2;
                if (distance (x, y, .5, .5) <= RADIUS) {
                    x -= .5; y -= .5; 
                    sx = Math.signum (x); sy = Math.signum (y);
                    
                    if (sx == 0) {
                        if (sy == 0) continue;
                        else if (sy > 0) pattern = (1 << 0) + (1 << 3);
                        else pattern = (1 << 1) + (1 << 2);
                    } else if (sx > 0) {
                        if (sy == 0) pattern = (1 << 0) + (1 << 1);
                        else if (sy > 0) pattern = (1 << 1) + (1 << 3);
                        else pattern = (1 << 0) + (1 << 2);
                    } else {
                        if (sy == 0) pattern = (1 << 2) + (1 << 3);
                        else if (sy > 0) pattern = (1 << 0) + (1 << 2);
                        else pattern = (1 << 1) + (1 << 3);
                    }   
                    
                    if ((i + j) % 2 == 0) 
                        StdDraw.setPenColor (StdDraw.WHITE);
                    else 
                        StdDraw.setPenColor (StdDraw.BLACK);
                                
                    fillDots (i * SIZE, j * SIZE, pattern);
                }
            }
        }
    }
    
    public static void main (String[] args) {
        drawGrid ();
        drawDots ();
    }
} 

