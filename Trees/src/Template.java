/**
 * Created by vorona on 22.04.15.
 */

import java.util.*;
import java.io.*;

public class Template {
    FastScanner in;
    PrintWriter out;
    public Node root = new Node(-1000000001);


    public void solve() throws IOException {

    }

    private void ins(Node x, int value) {
        if (value > x.value) {
            if (x.right != null)
                ins(x.right, value);
            else {
                x.right = new Node(value, x, null, null);
            }
            return;
        }
        if (value < x.value) {
            if (x.left != null)
                ins(x.left, value);
            else {
                x.left = new Node(value, x, null, null);
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
            out = new PrintWriter(new File("bstsimple.out"));

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

        Node(int value, Node parent, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        Node(int value) {
            this.value = value;
            this.right = null;
            this.left = null;
            this.parent = null;

        }

    }

    public static void main(String[] arg) {
        new Template().run();

    }
}
