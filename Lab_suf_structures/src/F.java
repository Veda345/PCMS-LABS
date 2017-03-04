/**
 * Created by vorona on 16.04.16.
 */

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class F {
    FastScanner in;
    PrintWriter out;

    public void solve() throws IOException {
        String a = in.next();
        int[] suf_mas = suffixArray(a);
        int[] lcp = lcp(suf_mas, a);

        int n = a.length();
        long res = 0;
        for (int i = 0; i < n; i++) {
            res += (n - suf_mas[i]);
        }
        for (int i = 0; i < n - 1; i++) {
            res -= lcp[i];
        }
        out.print(res);
    }

    public int[] suffixArray(String s) {
        int n = s.length();
        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++)
            order[i] = n - 1 - i;

        Arrays.sort(order, (a, b) -> Character.compare(s.charAt(a), s.charAt(b)));
        int[] suf_mas = new int[n];
        int[] colour = new int[n];
        for (int i = 0; i < n; i++) {
            suf_mas[i] = order[i];
            colour[i] = s.charAt(i);
        }
        for (int len = 1; len < n; len *= 2) {
            int[] c = colour.clone();
            for (int i = 0; i < n; i++) {
                if (i > 0 && c[suf_mas[i - 1]] == c[suf_mas[i]] && suf_mas[i - 1] + len < n
                        && c[suf_mas[i - 1] + len / 2] == c[suf_mas[i] + len / 2])
                    colour[suf_mas[i]] = colour[suf_mas[i - 1]];
                else colour[suf_mas[i]] = i;
            }
            int[] cnt = new int[n];
            for (int i = 0; i < n; i++)
                cnt[i] = i;
            int[] s1 = suf_mas.clone();
            for (int i = 0; i < n; i++) {
                int sp = s1[i] - len;
                if (sp >= 0)
                    suf_mas[cnt[colour[sp]]++] = sp;
            }
        }
        return suf_mas;
    }

    public static int[] lcp(int[] suf_mas, String s) {
        int n = suf_mas.length;
        int[] m = new int[n];
        for (int i = 0; i < n; i++)
            m[suf_mas[i]] = i;
        int[] lcp = new int[n - 1];
        for (int i = 0, h = 0; i < n; i++) {
            if (m[i] < n - 1) {
                for (int j = suf_mas[m[i] + 1]; Math.max(i, j) + h < s.length() && s.charAt(i + h) == s.charAt(j + h); ++h)
                    ;
                lcp[m[i]] = h;
                if (h > 0)
                    --h;
            }
        }
        return lcp;
    }

    public void run() {
        try {
            in = new FastScanner(new File("count.in"));
            out = new PrintWriter(new File("count.out"));
            //in = new FastScanner(System.in);
            //out = new PrintWriter(System.out);

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
        new F().run();

    }
}
