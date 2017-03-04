import java.io.*;
import java.util.*;


public class Set {
    class SetNode {
        int key;
        SetNode next, prev;
        SetNode(){};
        SetNode(int key) {
            this.key = key;
            next = null;
            prev = null;
        }
    }

    FastScanner in;
    PrintWriter out, in1;
    SetNode map[] = new SetNode[(int)mod];
    static long p = 251;
    static long mod = 10000000;

    public void run() {
        try {
           // in = new FastScanner(new File("map.in"));
            //out = new PrintWriter(new File("map.out"));

            solve1();

           // out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void solve1() throws IOException {

        in1 = new PrintWriter(new File("map.in"));

        Random r = new Random();
        for (int i = 0; i < 100000; i++) {
            int k = r.nextInt(3);
            int p;
            switch (k) {
                case 0:
                    in1.print("put ");
                    p = r.nextInt(1) + 1;
                    for (int j = 0; j < p; j++) {
                        int t = r.nextInt(10);
                        in1.print(t);
                    }
                    in1.println();
                    break;
                case 1:
                    in1.print("exists ");
                    p = r.nextInt(1) + 1;
                    for (int j = 0; j < p; j++) {
                        int t = r.nextInt(10);
                        in1.print(t);
                    }
                    in1.println();
                    break;
                case 2:
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

       // Map<String, String> hm = new HashMap<String, String>();
        HashSet<Integer> hm = new HashSet<Integer>();

        String str;
        // out.print(4);
        while (true) {
            try {
                str = in.next();

                if (str.equals("insert")) {
                    int a = in.nextInt();
                    //String b = in.next();
                    hm.add(a);
                    //hm(a, b);
                    // out.print(5);
                }

                if (str.equals("exists")) {
                    int a = in.nextInt();
                    if (hm.contains(a))
                        out.println("true");
                    else out.println("false");
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

    private static int hash_funcion(int x) {

        return (int)Math.abs((x * p) % mod);
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

    public void solve() throws IOException {
        try {
            while (true) {
                String op = in.next();
                int key = in.nextInt();
                int key_hash = hash_funcion(key);
                switch (op) {
                    case "insert":
                        if(map[key_hash] == null) {
                            map[key_hash] = new SetNode(key);
                        }
                        else {
                            SetNode current = map[key_hash];
                            while (current != null) {
                                if (current.key == key) {
                                    break;
                                }
                                if(current.next == null) {
                                    SetNode next = new SetNode(key);
                                    current.next = next;
                                    next.prev = current;
                                    break;
                                }
                                current = current.next;
                            }
                        }
                        break;
                    case "exists":
                        boolean success = false;
                        if(map[key_hash] != null) {
                            SetNode current = map[key_hash];
                            while (current != null) {
                                if (current.key == key) {
                                    success = true;
                                    out.write("true\n");
                                    break;
                                }
                                current = current.next;
                            }
                        }
                        if(!success)
                            out.write("false\n");
                        break;
                    case "delete":
                        SetNode current = map[key_hash];
                        while(current != null) {
                            if(current.key == key)
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
        }catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Set().run();
    }
}
