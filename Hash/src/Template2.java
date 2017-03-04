/**
 * Created by vorona on 21.05.15.
 */

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

public class Template2 {
    FastScanner in;
    int Size = 1 << 14;
    Node mas[] = new Node[Size];

    public void solve() throws IOException {
        in = new FastScanner(new File("map.in"));
        int k = 0, m = 0;
        String str;
        while (true) {
            try {
                str = in.next();
                m++;
                if (str.equals("put")) {
                    String a = in.next();
                    String b = in.next();
                }
                if (str.equals("delete")) {
                    String a = in.next();
                }

                if (str.equals("get")) {
                    String a = in.next();
                    k++;
                    if (k == 37 || k == 63)
                        System.out.print(m + " ");
                }



            } catch (Exception ex) {
                break;
            }
        }

    }





    public void run() {
        try {


            solve();

            //out.close();
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
        private String value;
        private Node next;
        private Node prev;
        private String key;


        Node(String key, String value) {
            this.value = value;
            this.next = null;
            this.prev = null;
            this.key = key;

        }

    }

    public static void main(String[] arg) {
        new Template2().run();

    }
}
