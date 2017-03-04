/**
 * Created by vorona on 07.05.16.
 */

import java.io.*;
import java.util.*;

public class B {
    FastScanner in;
    PrintWriter out;

    public void solve() throws IOException {
        int n;
        n = in.nextInt();
        ArrayList<Integer> a = new ArrayList<>();
        ArrayList<Integer> b = new ArrayList<>();

        long suma = 0, sumb = 0;
        for (int i = 0; i < n; i++) {
            int p;
            p = in.nextInt();
            a.add(p);
            suma += p;
        }

        for (int i = 0; i < n; i++) {
            int p;
            p = in.nextInt();
            b.add(p);
            sumb += p;
        }

        ArrayList<Integer> I = new ArrayList<>();
        ArrayList<Integer> J = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (a.get(i) <= b.get(i)) {
                I.add(i);
            } else {
                J.add(i);
            }
        }

        int ax = -1, by = -1, indx = -1;
        for (int p : I) {
            if (a.get(p) > ax) {
                ax = a.get(p);
                indx = p;
            }
        }
        for (int p : J) {
            if (b.get(p) > by) {
                by = b.get(p);
            }
        }

        long sum = -1;
        for (int i = 0; i < n; i++) {
            if ((a.get(i) + b.get(i)) > sum)
                sum = a.get(i) + b.get(i);
        }

        long Cmax = Math.max(suma, sumb);
        Cmax = Math.max(Cmax, sum);

        boolean flag = false;
        if (ax < by) {
            flag = true;
            for (int i = 0; i < n; i++) {
                int tmp = a.get(i);
                a.set(i, b.get(i));
                b.set(i, tmp);
            }
        }
        ArrayList<Integer> fst = new ArrayList<>();
        ArrayList<Integer> snd = new ArrayList<>();

        if (indx != -1)
            snd.add(indx);
        for (int p : I) {
            if (p != indx) {
                fst.add(p);
                snd.add(p);
            }
        }

        ArrayList<Integer> rfst = new ArrayList<>();
        ArrayList<Integer> rsnd = new ArrayList<>();

        if (indx != -1)
            rfst.add(indx);
        for (int p : J) {
            rfst.add(p);
            rsnd.add(p);
        }

        out.println(Cmax);
        long time = 0;
        long ordA[] = new long[n];
        for (int p : fst) {
            ordA[p] = time;
            time += a.get(p);
        }
        time = Cmax;
        for (int p : rfst) {
            ordA[p] = time - a.get(p);
            time -= a.get(p);
        }


        time = 0;
        long ordB[] = new long[n];
        if (snd.isEmpty()) {
            snd.add(rsnd.get(0));
            rsnd.remove(rsnd.get(0));
        }
        for (int p : snd) {
            ordB[p] = time;
            time += b.get(p);
        }
        time = Cmax;
        for (int p : rsnd) {
            ordB[p] = time - b.get(p);
            time -= b.get(p);
        }

        if (flag) {
            for (long z : ordB)
                out.print(z + " ");
            out.println();

            for (long z : ordA)
                out.print(z + " ");
            out.println();
        } else {
            for (long z : ordA)
                out.print(z + " ");
            out.println();

            for (long z : ordB)
                out.print(z + " ");
            out.println();
        }

    }


    public void run() {
        try {
            in = new FastScanner(new File("o2cmax.in"));
            out = new PrintWriter(new File("o2cmax.out"));

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


    public static void main(String[] arg) {
        new B().run();

    }

}
