/* Exercicio Busca de links em páginas WWW
 * Nome: Gabriel de Russo e Carmo
 * N USP: 9298041
 * Data: 02/07/2016 
 * OBS: Compilado com javac-algs4 */
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.TreeSet;

import edu.princeton.cs.algs4.*;

public class LinkFinderNEW { 
    
    /* Devolve true se a string s tem prefixo "mailto" */
    private static boolean is_mailto (String s) {
        String mt = "mailto";
        if (s.length () < mt.length ()) return false;
        return s.substring(0, mt.length()).equals(mt);
    }

    /* Devolve true se a string s tem prefixo "javascript" */
    private static boolean is_javascript (String s) {
        String js = "javascript";
        if (s.length () < js.length ()) return false;
        return s.substring(0, js.length()).equals(js);
    }

    /* Adiciona a url inteira se o link não é completo */
    private static String fix (String s, String url) {
        int n = s.length();
        int m = url.length();
        if (n > 3) if (s.substring(0,4).equals("http")) return s;
        if (n > 2) if (s.substring(0,3).equals("ftp")) return s;
        int i = s.charAt(0) == '/' ? 1 : 0;
        return url + s.substring(i,n);
    }

    public static void main(String[] args) { 
        boolean sorted = false;
        TreeSet<String> ts = new TreeSet<String>();
        if (args.length > 1) sorted = true; 
        int x = sorted ? 1 : 0;

        if (args[x].charAt(args[x].length()-1) != '/') args[x] = args[x] + "/";
        In in = new In(args[x]);
        String input = in.readAll();
        
        /* Como foi instruído que devíamos ignorar imagens e arquivos, supus que links são apenas
         * aqueles que são vistos pelo usuário na página. Desse modo, links são da forma
         * <a ... href="link" ...>...</a> */
        String regexp = "<a\\s[^>]*?href=\"([^\"]*?)\"[^>]*?>.*?</a>";
        Pattern pattern = Pattern.compile(regexp, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String foo = matcher.group(1);
            if (is_javascript(foo) || is_mailto(foo) || foo.length() == 0) continue;
            foo = fix(foo, args[x]);
            if (sorted) ts.add(foo);
            else System.out.println(foo);
        }
        if (sorted) for (String s : ts) System.out.println(s);
    }

}
