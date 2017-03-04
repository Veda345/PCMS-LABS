import java.io.*;
import java.util.*;

public class H {
    FastScanner in;
    PrintWriter out;

    ArrayList<ArrayList<Integer>> edges = new ArrayList<ArrayList<Integer>>();
    int n;

    public void solve() throws IOException {
        int r[];
        int p[];
        func f[];
        n = in.nextInt();
        p = new int[n];
        r = new int[n];
        f = new func[n];
        int m;
        for (int i = 0; i < n; i++) {
            p[i] = in.nextInt();
        }
        for (int i = 0; i < n; i++) {
            r[i] = in.nextInt();
        }

        m = in.nextInt();

        for (int i = 0; i < n; i++) {
            edges.add(new ArrayList<Integer>());
        }
        for (int i = 0; i< m; i++) {
            int u, v;
            u = in.nextInt();
            v = in.nextInt();
            edges.get(u-1).add(v-1);
        }
        for (int i = 0; i < n; i++) {
            int a, b, c;
            a = in.nextInt();
            b = in.nextInt();
            c = in.nextInt();
            f[i] = new func(a, b, c);
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < edges.get(i).size(); j++)  {
                int v = edges.get(i).get(j);
                r[v] = Math.max(r[v], r[i] + p[i]);
            }

        }

        Job jobs[] = new Job[n];

        for (int i = 0; i < n; i++) {
            jobs[i] = new Job(p[i], r[i], i, f[i]);
        }

        Arrays.sort(jobs, new Comparator<Job>() {
            @Override
            public int compare(Job o1, Job o2) {
                return Integer.compare(o1.r, o2.r);
            }
        });



    }


    public void run() {
        try {
            in = new FastScanner(new File("p1precpmtnrifmax.in"));
            out = new PrintWriter(new File("p1precpmtnrifmax.out"));

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
        new H().run();

    }

    class func{
        int a, b, c;
        func(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

    class Job {
        int p, r, id;
        func f;
        Job (int p, int r, int id, func f) {
            this.p = p;
            this.r = r;
            this.id = id;
            this.f = f;
        }
    }
}
