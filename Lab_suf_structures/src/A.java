/**
 * Created by vorona on 16.04.16.
 */

import java.util.*;
import java.io.*;

public class A {
    FastScanner in;
    PrintWriter out;

    int p = 1;
    Node root = new Node(p);

    public void solve() throws IOException {
        String str = in.next();
        build(str);
        out.println(p + " " + (p - 1));
        dfs(root);
    }

class Node {
    int num;
    Map<Character, Node> child;

    Node(int num) {
        this.num = num;
        child = new HashMap<Character, Node>();
    }
}

    void add(String s) {
        Node current = root;
        for (char c : s.toCharArray())  {
            if (!current.child.containsKey(c)) {
                current.child.put(c,new Node(++p));
            }
            current = current.child.get(c);
        }
    }

    void build(String s) {
        int n = s.length();
        for (int i = 0; i < n; i++) {
            add(s.substring(i));
        }
    }

    void dfs(Node node) {
        for (char ch : node.child.keySet()) {
            out.println(node.num + " " + node.child.get(ch).num + " " + ch);
            dfs(node.child.get(ch));
        }
    }


    public void run() {
        try {
            in = new FastScanner(new File("trie.in"));
            out = new PrintWriter(new File("trie.out"));

            solve();

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(File f) {
            try {
                br = new BufferedReader(new FileReader(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }


    public static void main(String[] arg) {
        new A().run();

    }
}
