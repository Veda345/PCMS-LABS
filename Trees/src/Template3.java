import java.util.*;
import java.io.*;

public class Template3 {
    FastScanner in;
    PrintWriter out;
    public Node root = null;
    Node mas[] = new Node[10];


    public void solve() throws IOException {
        int n, m;
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 0; i < n;  i++) {
            insert(i + 1);
        }

        for (int i = 0; i < m; i++) {
            int l, r;
            l = in.nextInt();
            r = in.nextInt();

            split(root,l, 0, 1);
            split(mas[1], r, 1, 2);
            merge(mas[1], mas[0]);
            merge(mas[0], mas[2]);

        }
        write(mas[2]);

    }


    private  void write (Node rot) {
        if (rot.left != null) write(rot.left);
        if (rot.right != null) write(rot.right);
        out.print(rot.key + " ");
    }

    private void keep_parent(Node v) {
        set_parent(v.left, v);
        set_parent(v.right, v);
    }

    private void set_parent(Node child, Node parent) {
        if (child != null)
            child.parent = parent;
    }


    private void rotate(Node parent, Node child) {
        Node gparent = parent.parent;
        if (gparent != null)
            if (gparent.left == parent)
                gparent.left = child;
            else
                gparent.right = child;

        if (parent.left == child) {
            parent.left = child.right;
            child.right = parent;
        } else {
            parent.right = child.left;
            child.left = parent;
        }

        keep_parent(child);
        keep_parent(parent);
        child.parent = gparent;
    }

    private Node splay(Node v) {
        if (v.parent == null) {
            return v;
        }
        Node parent = v.parent;
        Node gparent = parent.parent;
        if (gparent == null) {
            rotate(parent, v);
            return v;
        } else {
            if ((gparent.left == parent) == (parent.left == v)) {
                rotate(gparent, parent);
                rotate(parent, v);
            } else {
                rotate(parent, v);
                rotate(gparent, v);
            }
            return splay(v);
        }
    }

    private void split(Node rot, int key, int r1, int r2) {
        if (rot == null) {
            mas[r1] = mas[r2] = null;
            return;
        }
        rot = search(rot, key);
        if (rot != null && rot.key == key) {
            set_parent(rot.left, null);
            set_parent(rot.right, null);
            mas[r1] = rot.left;
            mas[r2] = rot.right;
            return;

        }
        if (rot != null &&  rot.key < key) {
            Node right = rot.right;
            rot.right = null;
            set_parent(right, null);
            mas[r1] = rot;
            mas[r2] = right;

        } else {
            Node left = rot.left;
            rot.left = null;
            set_parent(left, null);
            mas[r1] = left;
            mas[r2] = rot;


        }
    }

    private void insert( int key) {
        split(root, key, 0, 1);
        root = new Node(key, null, mas[0], mas[1]);
        keep_parent(root);

    }

    private Node merge(Node left, Node right) {
        if (right == null)
            return left;
        if (left == null)
            return right;
        right = search(right, left.key);
        right.left = left;
        left.parent = right;
        return right;
    }

    private void remove(Node rot, int key) {
        rot = search(rot, key);
        set_parent(rot.left, null);
        set_parent(rot.right, null);
        root = merge(rot.left, rot.right);


    }




    private Node search(Node x, int val) {
        if (x == null || val == x.key)
            return x;
        Node v;
        if (val < x.key)
            v = search(x.left, val);
        else
            v = search(x.right, val);
        //return v;
        return splay(v);
    }




    public void run() {
        try {
            in = new FastScanner(new File("bst.in"));
            out = new PrintWriter(new File("bst.out"));

            solve();
            out.print(12345);
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
        private int key;
        private Node left;
        private Node right;
        private Node parent;

        Node(int key, Node parent, Node left, Node right) {
            this.key = key;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        Node() {
            this.key = 0;
            this.right = null;
            this.left = null;
            this.parent = null;

        }

    }

    public static void main(String[] arg) {
        new Template3().run();

    }
}
