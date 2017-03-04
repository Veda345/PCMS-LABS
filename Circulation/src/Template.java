import java.util.*;
import java.io.*;

public class Template {
    FastScanner in;
    PrintWriter out;

    int INF = 2_000_000_000;
    int n, m;
    int[] d;
    ArrayList<ArrayList<Edge>> edges;
    int[] start;

    void bfs(int s, int t, int a) {
        ArrayDeque<Integer> q = new ArrayDeque<>();
        q.clear();
        q.add(s);

        Arrays.fill(d, INF);
        d[s] = 0;
        while (!q.isEmpty() && d[t] == INF) {
            int u = q.remove();
            for (Edge e : edges.get(u)) {
                if (e.f <= e.c - a && d[u] + 1 < d[e.v]) {
                    d[e.v] = d[u] + 1;
                    q.add(e.v);
                }
            }
        }
    }

    int dfs(int u, int t, int cmin) {
        if (u == t) {
            return cmin;
        }

        for (; start[u] < edges.get(u).size(); start[u]++) {
            Edge e = edges.get(u).get(start[u]);
            if (d[u] + 1 == d[e.v] && e.f < e.c) {
                int res = dfs(e.v, t, Math.min(cmin, e.c - e.f));
                if (res > 0) {
                    e.rev.f -= res;
                    e.f += res;
                    return res;
                }
            }
        }

        return 0;
    }

    public void run() {
        try {
            in = new FastScanner(new File("circulation.in"));
            out = new PrintWriter(new File("circulation.out"));

            solve();

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void solve() throws IOException {
        n = in.nextInt();
        m = in.nextInt();
        edges = new ArrayList<>(n + 2);

        for (int i = 0; i < n + 2; i++)
            edges.add(new ArrayList<Edge>());

        for (int i = 0; i < m; ++i) {
            int u = in.nextInt();
            int v = in.nextInt();
            int cmin = in.nextInt();
            int cmax = in.nextInt();

            Edge e = new Edge(0, v, cmin, -1);
            Edge rev = new Edge(v, 0, 0, -1);
            e.rev = rev;
            rev.rev = e;
            edges.get(0).add(e);
            edges.get(v).add(rev);

            e = new Edge(u, v, cmax - cmin, i);
            rev = new Edge(v, u, 0, -1);
            e.rev = rev;
            rev.rev = e;
            edges.get(u).add(e);
            edges.get(v).add(rev);

            e.min = cmin;
            e = new Edge(u, n + 1, cmin, -1);
            rev = new Edge(n + 1, u, 0, -1);
            e.rev = rev;
            rev.rev = e;
            edges.get(u).add(e);
            edges.get(n + 1).add(rev);
        }

        d = new int[n + 2];
        start = new int[n + 2];
        for (int a = INF; a > 0; a /= 2) {
            while (true) {
                bfs(0, n + 1, a);
                if (d[n + 1] == INF) {
                    break;
                }
                Arrays.fill(start, 0);
                while (dfs(0, n + 1, INF) > 0) {
                }
            }
        }

        for (Edge e : edges.get(0)) {
            if (e.c > e.f) {
                out.println("NO");
                return;
            }
        }

        out.println("YES");
        int[] mem = new int[m];
        for (int i = 1; i <= n; i++) {
            for (Edge e : edges.get(i)) {
                if (e.id > -1)
                    mem[e.id] = e.f + e.min;
            }
        }
        for (int i : mem)
            out.println(i);
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

    class Edge {
        int u;
        int v;
        int c;
        int f;
        Edge rev;
        int min;
        int id;

        Edge(int u, int v, int c, int id) {
            this.u = u;
            this.v = v;
            this.c = c;
            this.id = id;
            f = 0;
        }
    }
}
