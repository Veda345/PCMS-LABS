import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

class Node {
    String key, value;
    Node next, prev;
    Node(){};
    Node(String key, String value) {
        this.key = key;
        this.value = value;
        next = null;
        prev = null;
    }
}
public class Map1 {
    FastScanner in;
    PrintWriter out, in1;
    static long p = 251;
    static long mod = 1000000;

    public void run() {
        try {
           // in = new FastScanner(new File("map.in"));
           // out = new PrintWriter(new File("map.out"));

            solve1();

           // out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    class FastScanner {
        public BufferedReader br;
        StringTokenizer st;

        FastScanner(File f) {
            try {
                br = new BufferedReader(new FileReader(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        String next() throws NullPointerException {
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

    Node map[] = new Node[(int) Map1.mod];

    public void solve1() throws IOException {

        in1 = new PrintWriter(new File("map.in"));

        Random r = new Random();
        for (int i = 0; i < 10000; i++) {
            int k = r.nextInt(3);
            int p;
            switch (k) {
                case 0:
                    in1.print("put ");
                    p = r.nextInt(5) + 1;
                    for (int j = 0; j < p; j++) {
                        int t = r.nextInt(10);
                        in1.print(t);
                    }
                    in1.print(" ");
                    p = r.nextInt(5) + 1;
                    for (int j = 0; j < p; j++) {
                        int t = r.nextInt(10);
                        in1.print(t);
                    }
                    in1.println();
                    break;
                case 1:
                    in1.print("get ");
                    p = r.nextInt(5) + 1;
                    for (int j = 0; j < p; j++) {
                        int t = r.nextInt(10);
                        in1.print(t);
                    }
                    in1.println();
                    break;
                case 2:
                    in1.print("delete ");
                    p = r.nextInt(5) + 1;
                    for (int j = 0; j < p; j++) {
                        int t = r.nextInt(10);
                        in1.print(t);
                    }
                    in1.println();
                    break;
            }
        }
        in1.close();

        in = new FastScanner(new File("map.in"));
        out = new PrintWriter(new File("map1.out"));

        solve();
        out.close();


        in = new FastScanner(new File("map.in"));
        out = new PrintWriter(new File("map2.out"));

        Map<String, String> hm = new HashMap<String, String>();

        String str;
        // out.print(4);
        while (true) {
            try {
                str = in.next();

                if (str.equals("put")) {
                    String a = in.next();
                    String b = in.next();
                    hm.put(a, b);
                    // out.print(5);
                }
                if (str.equals("delete")) {
                    String a = in.next();
                    hm.remove(a);
                    // out.print(6);

                }

                if (str.equals("get")) {
                    String a = in.next();
                    if (hm.containsKey(a))
                        out.println(hm.get(a));
                    else out.println("none");
                    // out.print(7);
                }


            } catch (Exception ex) {
                // out.print(8);
                break;
            }
        }
        out.close();
        FastScanner in2, in3;
        in2 = new FastScanner(new File("map1.out"));
        in3 = new FastScanner(new File("map2.out"));
        int j = 0;
        while (true) {

            try {
                String s1, s2;
                s1 = in2.next();
                s2 = in3.next();
                j++;
                if (!s1.equals(s2)) {
                    System.out.println("oops num " + j + ", found " + s1 + " expected " + s2);
                    //System.out. println();

                }
            } catch (Exception ex) {
                System.out.println("end");
                break;
            }
        }
    }

    private static int hash_funcion(String s) {

        long hash = 0, p_pow = 1;
        for (int i=0; i<s.length(); ++i)
        {
            hash += (s.charAt(i) - 'a' + 1) * p_pow;
            hash %= mod;
            p_pow *= Map1.p;
        }
        return (int)(Math.abs(hash % mod));
    }
    public void solve() throws IOException {
       // out.print(8);
        try {
            while (true) {
                String op = in.next();
                String key = in.next();
                int key_hash = hash_funcion(key);
                switch (op) {
                    case "put":
                        //System.out.print("put");
                        String value = in.next();
                        if(map[key_hash] == null) {
                            map[key_hash] = new Node(key, value);
                        }
                        else {
                            Node current = map[key_hash];
                            while (current != null) {
                                if (current.key.equals(key)) {
                                    current.value = value;
                                    break;
                                }
                                if(current.next == null) {
                                    Node next = new Node(key, value);
                                    current.next = next;
                                    next.prev = current;
                                    break;
                                }
                                current = current.next;
                            }
                        }
                        break;
                    case "get":
                        //System.out.print("get");
                        boolean success = false;
                        if(map[key_hash] != null) {
                            Node current = map[key_hash];
                            while (current != null) {
                                if (current.key.equals(key)) {
                                    success = true;
                                    out.write(current.value + "\n");
                                    break;
                                }
                                current = current.next;
                            }
                        }
                        if(!success)
                            out.write("none\n");
                        break;
                    case "delete":
                        //System.out.print("delete");
                        Node current = map[key_hash];
                        while(current != null) {
                            if(current.key.equals(key))
                                break;
                            current = current.next;
                        }
                        if(current != null) {
                            if(current.next != null)
                                current.next.prev = current.prev;
                            if(current.prev != null)
                                current.prev.next = current.next;
                            else
                                map[key_hash] = current.next;
                        }
                        break;
                }
            }
        }
        catch (Exception e){
            //e.printStackTrace();
        };
    }

    public static void main(String[] args) {
        new Map1().run();
    }
}