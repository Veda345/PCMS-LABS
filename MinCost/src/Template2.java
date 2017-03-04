import java.util.*;
import java.io.*;

public class Template2{
    FastScanner in;
    PrintWriter out;

    int INF = 2_000_000_000;
    int n, m;
    int[] d;
    ArrayList<ArrayList<Edge>> edges;
    int cost;

    int minPath (int s, int t) {
        ArrayDeque<Integer> q = new ArrayDeque<>();
        Arrays.fill(d, INF);
        q.add(s);
        d[s] = 0;
        q.add(s);

        int[] p = new int[n];
        Edge[] ed = new Edge[n];
        int[] num = new int[n];

        while (!q.isEmpty() && d[t] == INF) {
            int v = q.pop();
            for (int i = 0; i < edges.get(v).size(); i++) {
                Edge to = edges.get(v).get(i);
                if (d[to.u] != INF && d[to.v] > d[to.u] + to.cost && to.cap > to.f) {
                    q.add(to.v);
                    d[to.v] = d[v]+to.cost;
                    p[to.v] = to.u;
                    ed[to.v] = to;
                    num[to.v] = i;
                }
            }
        }

        if (d[t] == INF)  return -1;
        int addflow = INF;
        for (int v = t; v != s; v = p[v]) {
            Edge pr = ed[v];
            addflow = Math.min(addflow, pr.cap - pr.f);
        }
        for (int v = t; v != s; v = p[v]) {
            edges.get(p[v]).get(num[v]).f += addflow;
            edges.get(num[v]).get(p[v]).f -= addflow;
            cost += edges.get(p[v]).get(num[v]).cost * addflow;
        }
        return addflow;
    }

    public void run() {
        try {
            in = new FastScanner(new File("mincost.in"));
            out = new PrintWriter(new File("mincost.out"));

            solve();

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void solve() throws IOException {
        n = in.nextInt();
        m = in.nextInt();
        edges = new ArrayList<>(n);

        for (int i = 0; i < n; i++)
            edges.add(new ArrayList<Edge>());

        for (int i = 0; i < m; ++i) {
            int u = in.nextInt();
            int v = in.nextInt();
            int cap = in.nextInt();
            int cost = in.nextInt();

            Edge e = new Edge(u, v, cap, cost);
            Edge rev = new Edge(v, u, 0, -cost);
            e.rev = rev;
            rev.rev = e;
            edges.get(u).add(e);
            edges.get(v).add(rev);
        }

        d = new int[n];
        while (minPath(0, n - 1) > 0) {

        }
        out.println(cost);

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

    class Edge {
        int u;
        int v;
        int cap;
        int cost;
        int f;
        Edge rev;

        Edge(int u, int v, int cap, int cost) {
            this.u = u;
            this.v = v;
            this.cap = cap;
            this.cost = cost;
            f = 0;
        }
    }
}
