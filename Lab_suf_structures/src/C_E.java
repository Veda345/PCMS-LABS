/**
 * Created by vorona on 16.04.16.
 */

import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class C_E {
//    FastScanner in;
    Scanner in;
    PrintWriter out;

    int p = 0;
    Node root;
    StringBuilder sb;
    String str;
    int m;
    public void solve() throws IOException {
        int n;
        n = in.nextInt();
        m = in.nextInt();
        root = new Node(0, 0, 0, null, ++p);
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++) {
            s.append(in.next());
        }
        s.append("$");
        str = s.toString();
        int t = str.length();
        suf_tree(str);
        countLeaves(root);
        walk(root);
        if (max == 0) {
            out.println(t-1);
            out.println(t-1);
            for (int i = 0; i < str.length()-1; i++) {
                out.print(str.charAt(i) + " ");
            }
            return;
        }
        out.println(max);
        out.println(max_node.length);

        sb = new StringBuilder();
        getString(max_node);
        str = sb.toString();
        for (int i = 0; i < str.length(); i++) {
            out.print(str.charAt(i) + " ");
        }
    }

    int max = 0;
    Node max_node;

    void walk(Node node) {
        countLen(node);
        int tmp = node.leaves * node.length;
        if (tmp > max) {
            max = tmp;
            max_node = node;
        }
        for (int i = 0; i < node.child.length; i++) {
            if (node.child[i] != null) {
                walk(node.child[i]);
            }
        }
    }


    void countLen(Node node) {
        if (node.length == 0 && node.parent != null) {
            countLen(node.parent);
            node.length += node.parent.length + node.right - node.left;
        }
    }

    int countLeaves(Node node) {
boolean f = false;
        for (int i = 0; i < node.child.length; i++) {
            if (node.child[i] != null) {
                f = true;
                node.leaves += countLeaves(node.child[i]);
            }
        }
        if (!f) return 1;
        return node.leaves;
    }

    void getString(Node node) {
        if (node != null && node.parent != null) {
            getString(node.parent);
            sb.append(str.substring(node.left, node.right));
        }
    }

    public void suf_tree(String s) {
        int n = s.length();
        byte[] a = new byte[n];
        for (int i = 0; i < n; i++) {
            a[i] = (byte) (s.charAt(i) - '0');
            if (s.charAt(i) == '$')
                a[i] = (byte)(m);

        }

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


    public void run() {
        try {
//            in = new FastScanner(new File("refrain.in"));
//            out = new PrintWriter(new File("refrain.out"));
            in = new Scanner(System.in);
            out = new PrintWriter(System.out);

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

    public class Node {
        int i;
        int left;
        int right;
        int depth;
        Node parent;
        Node[] child;
        Node link;
        int length;
        int leaves;

        Node(int left, int right, int depth, Node parent, int i) {
            this.i = i;
            this.left = left;
            this.right = right;
            this.depth = depth;
            this.parent = parent;
            child = new Node[m+1];
            link = null;
            length = 0;
            leaves = 0;
        }
    }

    public static void main(String[] arg) {
        new C_E().run();

    }
}
