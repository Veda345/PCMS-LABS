/**
 * Created by vorona on 22.04.15.
 */

import java.io.*;
import java.util.StringTokenizer;

public class Template2 {
    FastScanner in;
    PrintWriter out;
    public Node root = new Node(-1000000001, 1);


    public void solve() throws IOException {

        String str;
        while (true) {
            try {
                str = in.next();
                char curCh = str.charAt(0);
                if (curCh == 'i') {
                    int v = in.nextInt();
                    ins(root, v);
                }
                if (curCh == 'd') {
                    int v = in.nextInt();
                    Node d = search(root, v);
                    if (d != null)
                        del(root, d);
                }

                if (curCh == 'e') {
                    int v = in.nextInt();

                    if (search(root, v) == null)
                        out.println("false");
                    else
                        out.println("true");
                }

                if (curCh == 'n') {
                    int v = in.nextInt();
                    Node c = search(root, v);
                    if (c == null) {
                        ins(root, v);
                        c = search(root, v);
                        if (next(c) == null)
                            out.println("none");
                        else
                            out.println(next(c).value);
                        del(root, c);
                    } else {
                        if (next(c) == null)
                            out.println("none");
                        else
                            out.println(next(c).value);
                    }
                }

                if (curCh == 'p') {
                    int v = in.nextInt();
                    Node c = search(root, v);
                    if (c == null) {
                        ins(root, v);
                        c = search(root, v);
                        if (prev(c) == null || prev(c) == root)
                            out.println("none");
                        else
                            out.println(prev(c).value);
                        del(root, c);
                    } else {
                        if (prev(c) == null || prev(c) == root)
                            out.println("none");
                        else
                            out.println(prev(c).value);
                    }
                }


            } catch (Exception ex) {
                break;
            }
        }
    }

    private Node merge(Node t, Node t1, Node t2) {

        if (t1 == null || t2 == null)
            if (t1 != null)
                t = t1;
            else
                t = t2;
        else if (t1.prior > t2.prior) {
            merge(t1.right, t1.right, t2);
            t = t1;
        } else

        {
            merge(t2.left, t1, t2.left);
            t = t2;
        }
        return t;
    }

    private void Split(Node t, int k, Node t1, Node t2) {
        if (t == null)
        t1 = t2 = null;
        else
        if (k > t.value) {
            Split(t.right, k, t.right, t2);
            t1 = t;
        }
        else{
            Split(t.left, k, t1, t.left);
            t2 = t;
        }

    }

    private void ins(Node x, int value) {
        if (value > x.value) {
            if (x.right != null)
                ins(x.right, value);
            else {
                x.right = new Node(value, 0, x, null, null);
            }
            return;
        }
        if (value < x.value) {
            if (x.left != null)
                ins(x.left, value);
            else {
                x.left = new Node(value, 0, x, null, null);
            }

        }

    }

    private void del(Node rot, Node z) {
        Node x = null, y = null;
        if (z.left != null && z.right != null) {
            x = null;
            y = next(z);
            if (y == y.parent.left)
                y.parent.left = null;
            else {
                z.value = y.value;
                y.parent.right = null;
            }
        }
        if (z.left != null ^ z.right != null) {
            y = z;
            if (y.left != null)
                x = y.left;
            else
                x = y.right;
        }
        if (z.left == null && z.right == null) {
            y = z;
            x = null;
        }

        if (x != null)
            x.parent = y.parent;
        if (y.parent == null)
            rot = x;
        else {
            if (y == y.parent.left)
                y.parent.left = x;
            else
                y.parent.right = x;
        }
    }


    private Node search(Node x, int val) {
        if (x == null || val == x.value)
            return x;
        if (val < x.value)
            return search(x.left, val);
        else
            return search(x.right, val);
    }

    private Node next(Node x) {
        if (x.right != null) {
            x = x.right;
            while (x.left != null)
                x = x.left;
            return x;
        }
        Node y = x.parent;
        while (y != null && x == y.right) {
            x = y;
            y = y.parent;
        }
        return y;
    }

    private Node prev(Node x) {
        if (x.left != null) {
            x = x.left;
            while (x.right != null)
                x = x.right;
            return x;
        }
        Node y = x.parent;
        while (y != null && x == y.left) {
            x = y;
            y = y.parent;
        }
        return y;
    }



    public void run() {
        try {
            in = new FastScanner(new File("bst.in"));
            out = new PrintWriter(new File("bst.out"));

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

    private class Node {
        private int value;
        private Node left;
        private Node right;
        private Node parent;
        private int prior;

        Node(int value, int prior, Node parent, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
            this.parent = parent;
            this.prior = prior;
        }

        Node(int value, int prior) {
            this.value = value;
            this.prior = prior;
            this.right = null;
            this.left = null;
            this.parent = null;

        }

    }

    public static void main(String[] arg) {
        new Template2().run();

    }
}
