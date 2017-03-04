/**
 * Created by vorona on 10.03.16.
 */

import java.util.*;
import java.io.*;

public class Template {
    FastScanner in;
    PrintWriter out;

    public void solve() throws IOException {
        String s;
        s = in.next();

        int n = s.length();
        int pi[] = new int[n];
        for (int i = 1; i < n; ++i) {
            int j = pi[i - 1];
            while (j > 0 && s.charAt(i) != s.charAt(j))
                j = pi[j - 1];
            if (s.charAt(i) == s.charAt(j)) ++j;
            pi[i] = j;
        }

        int res = n;
        if (pi[n - 1] > 0) {
            int tmp = n - pi[n - 1];
            if (n % tmp > 0) {
                out.print(res);
                return;
            }
            for (int i = 0; i < n - tmp; i += tmp)
                if (s.charAt(i) != s.charAt(i + tmp)) {
                    out.print(res);
                    return;
                }
            res = n - pi[n - 1];
        }

        out.print(res);
    }


    public void run() {
        try {
            in = new FastScanner(new File("period.in"));
            out = new PrintWriter(new File("period.out"));

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
        new Template().run();

    }
}
