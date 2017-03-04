import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by vorona on 10.03.16.
 */
public class Template2 {
    FastScanner in;
    PrintWriter out;

    public void solve() throws IOException {
        String s;
        s = in.next();

        int n = s.length();
        int z[]= new int[n];
        for (int i=1, l=0, r=0; i<n; ++i) {
            if (i <= r)
                z[i] = Math.min(r-i+1, z[i-l]);
            while (i+z[i] < n && s.charAt(z[i]) == s.charAt(i+z[i]))
                ++z[i];
            if (i+z[i]-1 > r) {
                l = i;
                r = i + z[i] - 1;
            }
        }
        for (int i = 1; i < n; i++)
            out.print(z[i] + " ");

    }


    public void run() {
        try {
            in = new FastScanner(new File("z.in"));
            out = new PrintWriter(new File("z.out"));

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
        new Template2().run();

    }
}

