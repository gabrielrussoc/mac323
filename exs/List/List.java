/* Exercicio 3.5.27 - List (Algs 4)
 * Nome: Gabriel de Russo e Carmo
 * N USP: 9298041
 * Data: 07/05/2016
 * OBS: Compilado com 'javac-algs4' */
import edu.princeton.cs.algs4.*;
import java.util.HashMap;
import java.util.Iterator;

public class List<Item extends Comparable<Item>> implements Iterable<Item> {
    private RedBlackBST<Double, Item> rb;
    private HashMap<Item, Double> hash;
    private double l, r;

    /* Instancia uma nova lista */
    public List () {
        rb = new RedBlackBST<Double, Item> ();
        hash = new HashMap <Item, Double> ();
        l = 0.0;
        r = 1.0;
    }

    /* Devolve um iterador da lista */
    public Iterator<Item> iterator () {
        return new ListIterator ();
    }

    /* Implementação do iterador da lista */
    private class ListIterator implements Iterator<Item> {
        Iterable<Double> q = rb.keys (); 
        Iterator<Double> it = q.iterator ();

        public boolean hasNext () {
            return it.hasNext ();
        }

        public Item next () {
            return rb.get (it.next ());
        }
    }

    /* Adiciona um elemento na frente da lista */
    public void addFront (Item item) {
        rb.put (l, item);
        hash.put (item, l--);
    }

    /* Adiciona um elemento no fundo da lista */
    public void addBack (Item item) {
        rb.put (r, item);
        hash.put (item, r++);
    }

    /* Deleta o primeiro elemento da lista */
    public Item deleteFront () {
        Double del = rb.min ();
        Item ret = rb.get (del);
        rb.deleteMin ();
        hash.remove (ret);
        return ret;
    }

    /* Deleta o ultimo elemento da lista */
    public Item deleteBack () {
        Double del = rb.max ();
        Item ret = rb.get (del);
        rb.deleteMax ();
        hash.remove (ret);
        return ret;
    }

    /* Deleta um elemento da lista, se existir. BUGADO PARA REPETIÇÕES */
    public void delete (Item item) {
        if (!contains (item)) return;
        Double d = hash.get (item);
        rb.delete (d);
        hash.remove (item);
    } 
    
    /* Adiciona um elemento na i-esima posição da lista. Para posições fora da lista, utilize
     * addFront */
    public void add (int i, Item item) {
        if (i == 0) addFront (item);
        else {
            Double a = rb.select (i);
            Double b = rb.select (i-1);
            Double c = (a + b) / 2.;
            rb.put (c, item);
            hash.put (item, c);
        }
    
    }
    
    /* Deleta o i-esimo elemento */
    public Item delete (int i) {
        Double d = rb.select (i);
        Item ret = rb.get (d);
        hash.remove (ret);
        rb.delete (d);
        return ret;
    }

    /* Devolve true se a lista contem o item */
    public boolean contains (Item item) {
        return hash.containsKey (item);
    }

    /* Devolve true se a lista está vazia */
    public boolean isEmpty () {
        return size () == 0;
    }

    /* Devolve o tamanho da lista */
    public int size () {
        return rb.size ();
    }

    /* Devolve o i-ésimo elemento da lista */
    public Item get (int i) {
        return rb.get (rb.select (i));
    }

    /* Unit test */
    public static void main (String[] args) {
        List<String> l = new List<String> ();  
        l.addFront ("Fox");
        l.addFront ("Ai");
        l.addBack ("Que");
        l.addBack ("Homem");
        for (String s : l) StdOut.print (s + " ");
        StdOut.println ();
        l.deleteFront ();
        l.deleteBack ();
        for (String s : l) StdOut.print (s + " ");
        StdOut.println ();
        l.add (1, "Germano");
        for (String s : l) StdOut.print (s + " ");
        StdOut.println ();
        l.delete (1);
        for (String s : l) StdOut.print (s + " ");
        StdOut.println ();
        l.delete ("Fox");
        for (String s : l) StdOut.print (s + " ");
        StdOut.println ();
        StdOut.println (l.get (0));
        StdOut.println (l.size ());
        StdOut.println (l.contains ("Que"));
        StdOut.println (l.contains ("Fox"));
        l.delete ("Its your birthday today, nathan");
        l.add (0, "BUGOUUUUUUUUUU");
        for (String s : l) StdOut.print (s + " ");
        StdOut.println ();
    }
}
