import java.lang.*;
import java.util.*;
import edu.princeton.cs.algs4.*;

/* Exercicio 3.1.52 - Word frequencies 
 * disponivel em http://introcs.cs.princeton.edu/java/31datatype/
 * Nome: Gabriel de Russo e Carmo
 * N USP: 9298041
 * Data: 08/03/2016
 * OBS: Compilado com 'javac-algs4' */
public class WordFrequencies {
    
    /* Minha solucao abusa das bibliotecas do Java. Eu carrego uma linha de stdin por vez
     * na memoria, retiro todas suas palavras e vou colocando-as num HashMap, sempre
     * mantendo suas frequencias. Depois, ordeno uma lista das chaves do mapa com base
     * em seus valores e imprimo-as em stdout. */
    
    /* Devolve um HashMap com as palavras de stdin e suas frequencias */
    private static HashMap<String, Integer> getWords () {
        String line, word;
        HashMap<String, Integer> m = new HashMap<String, Integer> ();
        int i, j;
        char c;
        Integer v;

        while (!StdIn.isEmpty()) {
            line = (StdIn.readLine() + '$').toLowerCase ();
            i = 0;
            for (j = 0; j < line.length (); j++) {
                c = line.charAt (j);
                if (!Character.isAlphabetic(c) && c != '-') {
                    if (j > i) {
                        word = line.substring (i, j);
                        v = m.get (word);
                        if (v == null) m.put (word, 1);
                        else m.put (word, v + 1);
                    }
                    i = j + 1;
                }
            }
        }
        return m;
    } 

    public static void main (String[] args) {
        HashMap<String, Integer> map = getWords ();
        List<String> words = new ArrayList<String> (map.keySet ());
        Collections.sort (words, new Comparator<String> () {
            public int compare (String a, String b) {
                int u = map.get (a), v = map.get (b);
                return (u == v) ? a.compareTo (b) : v - u;
            }
        });
        for (String word : words)
            StdOut.println(word + " " + map.get (word));
    }
}

