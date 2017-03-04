/**
 * Created by vorona on 07.05.16.
 */

import java.io.*;
import java.util.*;

public class J {
    FastScanner in;
    PrintWriter out;

    public void solve() throws IOException {
        int n;
        n = in.nextInt();
        int sort[] = new int[1000000000];

        int d[] = new int[n];
        d[0] = in.nextInt();
        d[1] = in.nextInt();
        int A, B, C, D;
        A = in.nextInt();
        B = in.nextInt();
        C = in.nextInt();
        D = in.nextInt();
        for (int i = 2; i < n; i++) {
            d[i] = (int) (((((long) A * (long) d[i - 2]) + ((long) B * (long) d[i - 1]) + (long) C)) % D);
        }
        for (int i = 0; i < n; i++) {
            sort[d[i]]++;
        }

        int done = 0;
        int empty = 0;
        int tasks = n;
        for (int i = 0; i < 1000000000; i++) {
            if (sort[i] > 0) {
                {
                    int cnt = Math.min(empty, sort[i]);
                    empty -= cnt;
                    done += cnt;
                    tasks -= cnt;
                    if (tasks < empty) {
                        done = n;
                        break;
                    }
                }
            }
            empty++;
        }
//        Arrays.sort(d);

//        for (int i = 0; i < n; i++) {
//            if (d[i] > time) {
//                done++;
//                time++;
//            }
//        }
            out.println(done);
        }


    public void run() {
        try {
            in = new FastScanner(new File("p1p1sumu.in"));
            out = new PrintWriter(new File("p1p1sumu.out"));

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
        new J().run();

    }

}
