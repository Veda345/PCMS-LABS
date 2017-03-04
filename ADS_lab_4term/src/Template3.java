import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 * Created by vorona on 10.03.16.
 */
public class Template3 {
    FastScanner in;
    PrintWriter out;

    private int[] zFunc(int n, String str) {
        int z[] = new int[n];
        for (int i = 1, l = 0, r = 0; i < n; ++i) {
            if (i <= r)
                z[i] = Math.min(r - i + 1, z[i - l]);
            while (i + z[i] < n && str.charAt(z[i]) == str.charAt(i + z[i]))
                ++z[i];
            if (i + z[i] - 1 > r) {
                l = i;
                r = i + z[i] - 1;
            }
        }
        return z;
    }


    public void solve() throws IOException {
        String s, t;
        s = in.next();
        t = in.next();
        String str = s + "#" + t;
        String str2 = (new StringBuilder(t + "#" + s).reverse().toString());
        int n = str.length(), sl = s.length(), tl = t.length();

        int z[] = zFunc(n, str);
        int z1[] = zFunc(n, str2);

        ArrayList<Integer> ar = new ArrayList<>();
        for (int i = sl + 1; i < n; i++) {
            if (i - 1 <= tl && z[i] + z1[n - (i - sl - 1) - sl] >= sl - 1)
                ar.add(i - sl);
        }

        out.println(ar.size());
        for (int i : ar) out.print(i + " ");
    }


    public void run() {
        try {
            in = new FastScanner(new File("search3.in"));
            out = new PrintWriter(new File("search3.out"));

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
        new Template3().run();

    }
}

