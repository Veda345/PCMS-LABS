/**
 * Created by vorona on 16.04.16.
 */

import java.io.*;
import java.util.*;

public class E {
    FastScanner in;
    //    Scanner in;
    PrintWriter out;

    public void solve() throws IOException {
        int n = in.nextInt();
        int m = in.nextInt();
        int[] k = new int[n];
        for (int i = 0; i < n; i++) {
            k[i] = in.nextInt();
        }
        int[] suf_mas = suffixArray(k);
        int[] lcp = lcp(suf_mas, k);


        Stack<Item> stack = new Stack<>();
        long maxLength = 0;
        long maxCount = 0;
        int start = 0;

        lcp[n - 1] = 0;

        int x;
        for (int i = 0; i < n; i++) {
            x = 1;
            while (!stack.isEmpty() && lcp[i] <= stack.peek().len) {
                Item it = stack.pop();
                x += it.cnt;
                if (x * it.len > maxLength * maxCount) {
                    maxCount = x;
                    maxLength = it.len;
                    start = suf_mas[(int)it.i];
                }
            }
            if (stack.isEmpty() || lcp[i] > stack.peek().len) {
                stack.push(new Item(x, lcp[i], i));
            }
        }
        if (maxCount * maxLength < n) {
            out.println(n);
            out.println(n);
            for (int i = 0; i < n; i++) {
                out.print(k[i] + " ");
            }
            return;
        }

        out.println(maxCount * maxLength);
        out.println(maxLength);
        for (int i = start; i < start + maxLength; i++)
            out.print(k[i] + " ");

    }

    public int[] suffixArray(final int[] s) {
        int n = s.length;
        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++)
            order[i] = n - 1 - i;

        Arrays.sort(order, new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return Integer.compare(s[a], s[b]);
            }
        });
        int[] suf_mas = new int[n];
        int[] colour = new int[n];
        for (int i = 0; i < n; i++) {
            suf_mas[i] = order[i];
            colour[i] = s[i];
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

    public static int[] lcp(int[] suf_mas, int[] s) {
        int n = suf_mas.length;
        int[] m = new int[n];
        for (int i = 0; i < n; i++)
            m[suf_mas[i]] = i;
        int[] lcp = new int[n];
        for (int i = 0, h = 0; i < n; i++) {
            if (m[i] < n - 1) {
                for (int j = suf_mas[m[i] + 1]; Math.max(i, j) + h < s.length && s[i + h] == s[j + h]; ++h)
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
            in = new FastScanner(new File("refrain.in"));
            out = new PrintWriter(new File("refrain.out"));


//            in = new FastScanner(new File("input.txt"));
//            out = new PrintWriter(new File("output.txt"));

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
        new E().run();

    }

    class Item {
        long cnt, len, i;

        Item(long l, long c, long k) {
            cnt = l;
            len = c;
            i = k;
        }
    }
}
