/* Exercicio 5.2.8 - Ordered operations for tries (Algs 4)
 * Nome: Gabriel de Russo e Carmo
 * N USP: 9298041
 * Data: 05/06/2016
 * OBS: Compilado com 'javac-algs4' */
import edu.princeton.cs.algs4.*;

/* Code from Algorithms, 4th Edition by Robert Sedgewick and Kevin Wayne. */
/* Additional methods and modifications by Gabriel Russo from Universidade de São Paulo */

public class TrieST<Value> {
    private static final int R = 256;        // extended ASCII


    private Node root;      // root of trie
    private int N;          // number of keys in trie

    // R-way trie node
    private static class Node {
        private Object val;
        private Node[] next = new Node[R];
        private int partOf;
    }

   /**
     * Initializes an empty string symbol table.
     */
    public TrieST() {
    }


    /* START OF GABRIEL'S CODE */
    /* Returns the position of the key if the keys were ordered 
     * or -1 if key is not present */
    public int rank (String key) {
        if (!contains (key)) return -1;
        return rank (root, key, 0);
    }

    private int rank (Node x, String key, int d) {
        if (d == key.length ()) return 0;
        char c = key.charAt (d);
        int ans = (x.val != null) ? 1 : 0;
        for (char cc = 0; cc < c; cc++)
            if (x.next[cc] != null)
                ans += x.next[cc].partOf;
        return ans + rank (x.next[c], key, d+1);
    }

    /* Returns the i-th string if the keys were ordered 
     * or null if 'i' is out of range */
    public String select (int i) {
        if (i < 0 || i >= size ()) return null;
        return select (root, i, new StringBuilder ());
    }

    private String select (Node x, int i, StringBuilder s) {
        if (x.val != null) {
            if (i == 0) return s.toString ();            
            i--;
        }
        for (char c = 0; c < R; c++) {
            if (x.next[c] != null) {
                if (i >= x.next[c].partOf) i -= x.next[c].partOf;
                else return select (x.next[c], i, s.append(c));
            }
        }
        return null;
    }

    /* Returns the ceil of key (the smallest string that is greater or equal to key) */
    public String ceil (String key) {
        return ceil (root, key, 0, true, new StringBuilder ());
    }

    private String ceil (Node x, String key, int d, boolean pre, StringBuilder s) {
        if (x == null) return null;
        //StdOut.println ("Prefix: " + s.toString() + " d = " + d + " (" + x.val + ")");
        if (!pre && x.val != null) return s.toString ();
        if (pre && d == key.length () && x.val != null) return s.toString ();
        char c = d < key.length () ? key.charAt (d) : 0;
        for (char cc = pre ? c : 0; cc < R; cc++) {
            String tmp = ceil (x.next[cc], key, d + 1, pre && (cc == c), s.append (cc));
            if (tmp != null) return tmp;
            s.deleteCharAt (d);
        }
        return null;
    }

    /* Returns the floor of key (the biggest string that is less or equal to key) */
    public String floor (String key) {
        return floor (root, key, 0, true, new StringBuilder ());
    }
    
    private String floor (Node x, String key, int d, boolean pre, StringBuilder s) {
        if (x == null) return null;
        //StdOut.println ("Prefix: " + s.toString() + " d = " + d + " (" + x.val + ")");
        if (pre && d == key.length ()) {
            if (x.val != null) return s.toString ();
            return null;
        }
        char c = d < key.length () ? key.charAt (d) : (R - 1);
        for (char cc = pre ? c : (R - 1); cc > 0; cc--) {
            String tmp = floor (x.next[cc], key, d + 1, pre && (cc == c), s.append (cc));
            if (tmp != null) return tmp;
            s.deleteCharAt (d);
        }
        if (x.val != null) return s.toString ();
        return null;
    }


    /**
     * Returns the value associated with the given key.
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol table
     *     and <tt>null</tt> if the key is not in the symbol table
     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
     */
    public Value get(String key) {
        Node x = get(root, key, 0);
        if (x == null) return null;
        return (Value) x.val;
    }

    /**
     * Does this symbol table contain the given key?
     * @param key the key
     * @return <tt>true</tt> if this symbol table contains <tt>key</tt> and
     *     <tt>false</tt> otherwise
     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
     */
    public boolean contains(String key) {
        return get(key) != null;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        return get(x.next[c], key, d+1);
    }

    /**
     * Inserts the key-value pair into the symbol table, overwriting the old value
     * with the new value if the key is already in the symbol table.
     * If the value is <tt>null</tt>, this effectively deletes the key from the symbol table.
     * @param key the key
     * @param val the value
     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
     */
    public void put(String key, Value val) {
        if (val == null) delete(key);
        else {
            int s = !contains (key) ? 1 : 0;
            root = put(root, key, val, 0, s);
        }
    }

    private Node put(Node x, String key, Value val, int d, int s) {
        if (x == null) x = new Node();
        x.partOf += s;
        if (d == key.length()) {
            if (x.val == null) N++;
            x.val = val;
            return x;
        }
        char c = key.charAt(d);
        x.next[c] = put(x.next[c], key, val, d+1, s);
        return x;
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return N;
    }

    /**
     * Is this symbol table empty?
     * @return <tt>true</tt> if this symbol table is empty and <tt>false</tt> otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns all keys in the symbol table as an <tt>Iterable</tt>.
     * To iterate over all of the keys in the symbol table named <tt>st</tt>,
     * use the foreach notation: <tt>for (Key key : st.keys())</tt>.
     * @return all keys in the sybol table as an <tt>Iterable</tt>
     */
    public Iterable<String> keys() {
        return keysWithPrefix("");
    }

    /**
     * Returns all of the keys in the set that start with <tt>prefix</tt>.
     * @param prefix the prefix
     * @return all of the keys in the set that start with <tt>prefix</tt>,
     *     as an iterable
     */
    public Iterable<String> keysWithPrefix(String prefix) {
        Queue<String> results = new Queue<String>();
        Node x = get(root, prefix, 0);
        collect(x, new StringBuilder(prefix), results);
        return results;
    }

    private void collect(Node x, StringBuilder prefix, Queue<String> results) {
        if (x == null) return;
        if (x.val != null) results.enqueue(prefix.toString());
        for (char c = 0; c < R; c++) {
            prefix.append(c);
            collect(x.next[c], prefix, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    /**
     * Returns all of the keys in the symbol table that match <tt>pattern</tt>,
     * where . symbol is treated as a wildcard character.
     * @param pattern the pattern
     * @return all of the keys in the symbol table that match <tt>pattern</tt>,
     *     as an iterable, where . is treated as a wildcard character.
     */
    public Iterable<String> keysThatMatch(String pattern) {
        Queue<String> results = new Queue<String>();
        collect(root, new StringBuilder(), pattern, results);
        return results;
    }

    private void collect(Node x, StringBuilder prefix, String pattern, Queue<String> results) {
        if (x == null) return;
        int d = prefix.length();
        if (d == pattern.length() && x.val != null)
            results.enqueue(prefix.toString());
        if (d == pattern.length())
            return;
        char c = pattern.charAt(d);
        if (c == '.') {
            for (char ch = 0; ch < R; ch++) {
                prefix.append(ch);
                collect(x.next[ch], prefix, pattern, results);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }
        else {
            prefix.append(c);
            collect(x.next[c], prefix, pattern, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    /**
     * Returns the string in the symbol table that is the longest prefix of <tt>query</tt>,
     * or <tt>null</tt>, if no such string.
     * @param query the query string
     * @return the string in the symbol table that is the longest prefix of <tt>query</tt>,
     *     or <tt>null</tt> if no such string
     * @throws NullPointerException if <tt>query</tt> is <tt>null</tt>
     */
    public String longestPrefixOf(String query) {
        int length = longestPrefixOf(root, query, 0, -1);
        if (length == -1) return null;
        else return query.substring(0, length);
    }

    // returns the length of the longest string key in the subtrie
    // rooted at x that is a prefix of the query string,
    // assuming the first d character match and we have already
    // found a prefix match of given length (-1 if no such match)
    private int longestPrefixOf(Node x, String query, int d, int length) {
        if (x == null) return length;
        if (x.val != null) length = d;
        if (d == query.length()) return length;
        char c = query.charAt(d);
        return longestPrefixOf(x.next[c], query, d+1, length);
    }

    /**
     * Removes the key from the set if the key is present.
     * @param key the key
     * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>
     */
    public void delete(String key) {
        int s = contains (key) ? 1 : 0;
        root = delete(root, key, 0, s);
    }

    private Node delete(Node x, String key, int d, int s) {
        if (x == null) return null;
        x.partOf -= s;
        if (d == key.length()) {
            if (x.val != null) N--;
            x.val = null;
        }
        else {
            char c = key.charAt(d);
            x.next[c] = delete(x.next[c], key, d+1, s);
        }

        // remove subtrie rooted at x if it is completely empty
        if (x.val != null) return x;
        for (int c = 0; c < R; c++)
            if (x.next[c] != null)
                return x;
        return null;
    }

    /* GABRIEL'S UNIT TEST */
    public static void main(String[] args) {
        TrieST<Integer> t = new TrieST<Integer> ();
        String s[] = {"Fox", "Germano", "Batata", "Arroz", "Arrozao", "Arr", "Zarabatana", "Indio", "India", "Batera", "Bateria"};
        
        StdOut.println ("*** Inserções ***");
        for(int i = 0; i < s.length; i++) {
            t.put (s[i], 1);
            StdOut.print (s[i] + " ");
        }
        StdOut.println ();

        /* Rank test */
        StdOut.println ("---- Rank ----");
        for(int i = 0; i < s.length; i++)
            StdOut.println("Rank("+s[i]+")" + " = " + t.rank (s[i]));
        StdOut.println("Rank(Xablau)" + " = " + t.rank ("Xablau"));

        /* Select test */
        StdOut.println ("---- Select ----");
        for(int i = -1; i <= s.length; i++) StdOut.println ("Select("+i+")" + " = " + t.select(i));

        /* Ceil test */
        StdOut.println ("---- Ceil ----");
        String ceil_data[] = {"Fo", "Arro", "Bateram", "Abacate", "Zzz", "Arroz", "Zarabatanao"};
        for (int i = 0; i < ceil_data.length; i++)
            StdOut.println ("Ceil de " + ceil_data[i] + " = " + t.ceil (ceil_data[i]));
        
        /* Floor test */
        StdOut.println ("---- Floor ----");
        String floor_data[] = {"Fo", "Arro", "Bateram", "Abacate", "Zzz", "Arroz", "Zarabatanao", "Barro"};
        for (int i = 0; i < floor_data.length; i++)
            StdOut.println ("Floor de " + floor_data[i] + " = " + t.floor (floor_data[i]));
        
        TrieST<Integer> tt = new TrieST<Integer> ();
        StdOut.println ("Teste na entrada padrão. Uso: ");
        StdOut.println ("[ceil/floor/rank/select/put/delete] [key/value]");
        while (!StdIn.isEmpty()) {
            String op, w;
            op = StdIn.readString ();
            w = StdIn.readString ();
            if (op.equals("ceil")) StdOut.println (tt.ceil(w));
            if (op.equals("floor")) StdOut.println (tt.floor(w));
            if (op.equals("rank")) StdOut.println (tt.rank(w));
            if (op.equals("select")) StdOut.println (tt.select(new Integer(w)));
            if (op.equals("put")) tt.put (w, 1);
            if (op.equals("delete")) tt.delete(w);
        }

        /* Strings aleatorias
        int n = 150;
        int d = 10;
        for (int i = 0; i < n; i++) {
            String s = new String ();
            for(int j = 0; j < d; j++) {
                char c = (char) StdRandom.uniform('a', 'a'+26);
                s = s + c;
            }
            StdOut.println (s);
            t.put (s, 1);
        } */
    }
}
