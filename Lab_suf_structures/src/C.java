/**
 * Created by vorona on 16.04.16.
 */

import java.io.*;
import java.util.StringTokenizer;

public class C {
    FastScanner in;
    PrintWriter out;

    int p = 0;
    Node root = new Node(0, 0, 0, null, ++p);

    public void solve() throws IOException {
        String str = in.next();
        suf_tree(str);
        out.println(p + " " + (p-1));
        dfs(root);

    }

    public void suf_tree(String s) {
        int n = s.length();
        byte[] a = new byte[n];
        for (int i = 0; i < n; i++) a[i] = (byte) (s.charAt(i)-'a');

        Node node = root;
        for (int i = 0, tail = 0; i < n; i++, tail++) {
            Node last = null;
            while (tail >= 0) {
                Node ch = node.child[a[i - tail]];
                while (ch != null && tail >= ch.right - ch.left) {
                    tail -= ch.right - ch.left;
                    node = ch;
                    ch = ch.child[a[i - tail]];
                }
                if (ch == null) {
                    node.child[a[i]] = new Node(i, n, node.depth + node.right - node.left, node, ++p);
                    if (last != null)
                        last.link = node;
                    last = null;
                } else {
                    byte t = a[ch.left + tail];
                    if (t == a[i]) {
                        if (last != null)
                            last.link = node;
                        break;
                    } else {
                        Node splited = new Node(ch.left, ch.left + tail, node.depth + node.right - node.left, node, ++p);
                        splited.child[a[i]] = new Node(i, n, ch.depth + tail, splited, ++p);
                        splited.child[t] = ch;
                        ch.left += tail;
                        ch.depth += tail;
                        ch.parent = splited;
                        node.child[a[i - tail]] = splited;
                        if (last != null)
                            last.link = splited;
                        last = splited;
                    }
                }
                if (node == root) {
                    --tail;
                } else {
                    node = node.link;
                }
            }
        }
    }

    void dfs(Node node) {
        for (Node n : node.child) {
            if (n != null) {
                out.println(node.i + " " + n.i + " " + (n.left + 1) + " " + (n.right));
                dfs(n);
            }
        }
    }

    public void run() {
        try {
            in = new FastScanner(new File("tree.in"));
            out = new PrintWriter(new File("tree.out"));
//            in = new FastScanner(System.in);
//            out = new PrintWriter(System.out);

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

    public static class Node {
        int i;
        int left;
        int right;
        int depth;
        Node parent;
        Node[] child;
        Node link;

        Node(int left, int right, int depth, Node parent, int i) {
            this.i = i;
            this.left = left;
            this.right = right;
            this.depth = depth;
            this.parent = parent;
            child = new Node[26];
            link = null;
        }
    }

    public static void main(String[] arg) {
        new C().run();

    }
}