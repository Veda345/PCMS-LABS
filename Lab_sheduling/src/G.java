/**
 * Created by vorona on 07.05.16.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class G {
    FastScanner in;
    PrintWriter out;

    public void solve() throws IOException {
        int n;
        n = in.nextInt();
        byte a[] = new byte[n];
        byte b[] = new byte[n];

        int time = 0;
        for (int i = 0; i < n; i++) {
            a[i] = (byte) in.nextInt();
            time += a[i];
        }

        int t = 0;
        for (int i = 0; i < n; i++) {
            b[i] = (byte) in.nextInt();
            t += b[i];
        }

        time = Math.min(time, t);

        int dpO[];

        int dp[] = new int[time + 1];
        for (int j = 0; j <= time; j++)
            dp[j] = Integer.MAX_VALUE / 2;
        dp[0] = 0;
        for (int i = 1; i < n + 1; i++) {
            dpO = dp;
            dp = new int[time + 1];
            for (int j = 0; j <= time; j++)
                dp[j] = Integer.MAX_VALUE / 2;

            for (int j = 0; j <= time; j++) {
                if (j >= a[i - 1]) {
                    dp[j] = Math.min(dp[j], dpO[j - a[i - 1]]);
                }
                dp[j] = Math.min(dp[j], dpO[j] + b[i - 1]);
            }
        }
        int ans = Integer.MAX_VALUE;
        for (int j = 0; j <= time; j++)
            ans = Math.min(ans, Math.max(j, dp[j]));
        out.print(ans);

    }


    public void run() {
        try {
            in = new FastScanner(new File("r2cmax.in"));
            out = new PrintWriter(new File("r2cmax.out"));

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
        new G().run();

    }

}
