/**
 * Created by vorona on 16.04.16.
 */

import java.io.*;
import java.util.StringTokenizer;

public class C_G {
    FastScanner in;
    PrintWriter out;

    int p = 0;
    Node root = new Node(0, 0, 0, null, ++p);
    int lcsN;
    String str;
    Node depth;
    StringBuilder sb;

    public void solve() throws IOException {
        String str1 = in.next();
        String str2 = in.next();
        int t = str1.length();
        str = str1 + "@" + str2 + "$";
        suf_tree(str);
        lcs(root, t, str.length());
//        out.println(lcsN);
        sb = new StringBuilder();
        getString(depth);
        out.print(sb.toString());
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
            a[i] = (byte) (s.charAt(i)-'a');
            if (s.charAt(i) == '@')
                a[i] = 26;
            if (s.charAt(i) == '$')
                a[i] = 27;

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

    void lcs(Node node, int l, int r) {
        if (node.left < l) {
            node.c.left = true;
            if (node.parent != null)
                node.parent.c.left = true;

        }
        if (node.left > l && node.left < r) {
            node.c.right = true;
            if (node.parent != null)
                node.parent.c.right = true;

        }
        for (char k = 0; k < 28; k++) {
            if (node.child[k] != null) {
                lcs(node.child[k], l, r);
            }
        }
        if (node.c.left && node.parent != null)
            node.parent.c.left = true;

        if (node.c.right && node.parent != null)
            node.parent.c.right = true;

        if (node.c.right && node.c.left) {
            countLenght(node);
            int curLength = node.length;
            if (lcsN < curLength) {
                lcsN = curLength;
                depth = node;
            }
        }
    }

    private void countLenght(Node node) {
        if (node.length == 0 && node.parent != null) {
            countLenght(node.parent);
            node.length += node.parent.length + node.right - node.left;
        }
    }

    private class check {
        boolean left, right;
    }

    public void run() {
        try {
            in = new FastScanner(new File("common.in"));
            out = new PrintWriter(new File("common.out"));
//            in = new Scanner(System.in);
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

    public class Node {
        int i;
        int left;
        int right;
        int depth;
        Node parent;
        Node[] child;
        Node link;
        check c;
        int length;

        Node(int left, int right, int depth, Node parent, int i) {
            this.i = i;
            this.left = left;
            this.right = right;
            this.depth = depth;
            this.parent = parent;
            child = new Node[28];
            link = null;
            c = new check();
        }
    }

    public static void main(String[] arg) {
        new C_G().run();

    }
}
