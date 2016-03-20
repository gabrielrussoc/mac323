/* Exercicio 1.3.35 - Random queue (Algs 4)
 * Nome: Gabriel de Russo e Carmo
 * N USP: 9298041
 * Data: 20/03/2016
 * OBS: Compilado com 'javac-algs4' */
import edu.princeton.cs.algs4.*;
public class Bridge {
    /* Imprime maos para um jogo de bridge */
    public static void main (String[] args) {
        RandomQueue<Card> q = new RandomQueue<Card> ();
        for (int i = 0; i < 52; i++) q.enqueue (new Card (i));
        StdOut.println ("P1 P2 P3 P4\n");
        for (int i = 0; !q.isEmpty (); i++)
            StdOut.println (q.dequeue () + " " + q.dequeue () + " " + q.dequeue () + " " + q.dequeue ());
    }   
} 

