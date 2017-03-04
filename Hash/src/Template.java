/**
 * Created by vorona on 21.05.15.
 */

import java.util.*;
import java.io.*;

public class Template {
    FastScanner in;
    PrintWriter out;
    int Size = 1 << 15;
    Node map[] = new Node[Size];
   // Node lastAdded = null;

    public void solve() throws IOException {
        String str;
        while (true) {
            try {
                str = in.next();

                if (str.equals("put")) {
                    String a = in.next();
                    String b = in.next();
                    int addr = 0;
                    for (int i = 0; i < a.length(); i++)
                        addr = (addr + (int) a.charAt(i) * (int) Math.abs(Math.pow(17, a.length() - 1 - i) + 1)) % Size;
                    ins(map[addr], a, b, addr);
                }
                if (str.equals("delete")) {
                    String a = in.next();
                    String b = in.next();
                    int addr = 0;
                    for (int i = 0; i < a.length(); i++)
                        addr = (addr + (int) a.charAt(i) * (int) Math.abs(Math.pow(17, a.length() - 1 - i) + 1)) % Size;
                    del(map[addr], a, b);

                }
                if (str.equals("deleteall")) {
                    String a = in.next();
                    int addr = 0;
                    for (int i = 0; i < a.length(); i++)
                        addr = (addr + (int) a.charAt(i) * (int) Math.abs(Math.pow(17, a.length() - 1 - i) + 1)) % Size;
                    delAll(map[addr], a);

                }

                if (str.equals("get")) {
                    String a = in.next();

                    int addr = 0;
                    for (int i = 0; i < a.length(); i++)
                        addr = (addr + (int) a.charAt(i) * (int) Math.abs(Math.pow(17, a.length() - 1 - i) + 1)) % Size;
                    search(map[addr], a);
                    out.println();
                }



            } catch (Exception ex) {
                out.close();
                break;
            }
        }

    }


    private void ins(Node x, String key, String value, int addr) {

        if (x == null) {
            map[addr] = new Node(key, value);
            //lastAdded = map[addr];
            return;
        }
        while (x.next != null && !x.key.equals(key)) {
            x = x.next;
        }

        if (!x.key.equals(key)) {
            Node s = new Node(key, value);
           // lastAdded = s;
            x.next = s;
            s.prev = x;
        } else {
            int j = 0;
            while (j < x.y.size() && !x.y.get(j).equals(value))
                j++;
            if(j == x.y.size()) {
                x.y.add(value);
               // Node s = new Node(key, value);
                //x.next = s;
               // s.prev = x;
            }

        }

    }

    private void del(Node x, String key, String value) {

        if (x == null) {
            return;
        }

        while (x.next != null && !x.key.equals(key)) {
            x = x.next;
        }

        if (x.key.equals(key)) {
            int j = 0;
            while (j < x.y.size() && !x.y.get(j).equals(value) )
                j++;
            if (j != x.y.size()) {
                x.y.remove(j);
            }

        }

    }

    private void delAll(Node x, String key) {

        if (x == null) {
            return;
        }

        while (x.next != null && !x.key.equals(key)) {
            x = x.next;
        }

        if (x.key.equals(key)) {
        x.y.clear();
        }

    }

    private void search(Node x, String key) {
        //boolean f = true;
       // while (x!=null) {
            while (x != null && x.next != null && !x.key.equals(key)) {
                x = x.next;
            }
            if ((x == null || !x.key.equals(key)) )  {
                out.println("0");
            }
            else {
                out.print(x.y.size()+ " ");
                for (int j = 0; j < x.y.size(); j++) {
                    out.print(x.y.get(j)+ " ");
                }
               // f = false;
                //x = x.next;
            }
       // }
    }


    public void run() {
        try {

            in = new FastScanner(new File("multimap.in")); //TODO
            out = new PrintWriter(new File("multimap.out"));

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
        //private String value;
        private Node next;
        private Node prev;
        private String key;
        private ArrayList y = new ArrayList();


        Node(String key, String value) {
            //this.value = value;
            this.y.add(value);
            this.next = null;
            this.prev = null;
            this.key = key;

        }

    }

    public static void main(String[] arg) {
        new Template().run();

    }
}
