/**
 * Created by vorona on 19.12.15.
 * Во всех делах, где ум успешливый
 * победу праздновать спешит,
 * он ловит грустный и усмешливый
 * взгляд затаившейся души.
 */

import java.util.*;
import java.io.*;

public class Template {
    FastScanner in;
    PrintWriter out;

    int INF = 2_000_000_000;
    int n, m;
    int[] d;
    ArrayList<ArrayList<Edge>> graph;
    int[] head, used;
    ArrayList<Integer> mem;

    void bfs(int s, int t, int a) {
        ArrayDeque<Integer> q = new ArrayDeque<>();
        q.clear();
        q.add(s);

        Arrays.fill(d, INF);
        d[s] = 0;
        while (!q.isEmpty() && d[t] == INF) {
            int u = q.remove();
            for (Edge e : graph.get(u)) {
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

        for (; head[u] < graph.get(u).size(); head[u]++) {
            Edge e = graph.get(u).get(head[u]);
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

    int dfs2(int u, int t, int cmin) {
        if (u == t) {
            return cmin;
        }
        used[u] = 1;
        for (Edge e : graph.get(u)) {
            if (e.f > 0 && used[e.v] == 0) {
                int res = dfs2(e.v, t, Math.min(cmin, e.f));
                e.f -= res;
                e.rev.f += res;
                mem.add(e.id);
                return res;
            }
        }
        return 0;
    }

    public void run() {
        try {
            in = new FastScanner(new File("decomposition.in"));
            out = new PrintWriter(new File("decomposition.out"));

            solve();

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void solve() throws IOException {
        n = in.nextInt();
        m = in.nextInt();
        graph = new ArrayList<>(n);

        for (int i = 0; i < n; i++)
            graph.add(new ArrayList<Edge>());

        for (int i = 0; i < m; ++i) {
            int u = in.nextInt();
            int v = in.nextInt();
            int c= in.nextInt();
            u--;
            v--;

            Edge e1 = new Edge(u, v, c, i);
            Edge e2 = new Edge(v, u, 0, -1);
            e1.rev = e2;
            e2.rev = e1;
            graph.get(u).add(e1);
            graph.get(v).add(e2);

        }

        d = new int[n];
        head = new int[n];
        used = new  int[n];
        for (int a = INF; a > 0; a /= 2) {
            while (true) {
                bfs(0, n - 1, a);
                if (d[n - 1] == INF) {
                    break;
                }
                Arrays.fill(head, 0);
                while (dfs(0, n - 1, INF) > 0) {
                }
            }
        }

        ArrayList<ArrayList<Integer>> ha = new ArrayList<>();
        while (true) {
            mem = new ArrayList<>(n);
            mem.clear();
            Arrays.fill(used, 0);
            int res = dfs2(0, n-1, INF);
            if (res == 0) break;
            for (int i = 0; i< mem.size(); i++)
                mem.set(i, mem.get(i) + 1);
            mem.add(mem.size());
            mem.add(res);
            Collections.reverse(mem);
            ha.add(mem);
        }

        out.println(ha.size());
        for (ArrayList i:ha)  {
            for (int j = 0; j < i.size(); j++) {
                out.print(i.get(j) + " ");
            }
            out.println();
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
