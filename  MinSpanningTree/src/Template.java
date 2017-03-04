/**
 * Created by vorona on 23.11.15.
 *
 * ●▬▬▬▬▬▬ஜ۩۞۩ஜ۩۞۩ஜ۩۞۩ஜ▬▬▬▬▬▬●
 *┏┓┏┓┏┓╋╋┏┓╋╋╋╋╋╋╋╋╋╋╋╋┏┓
 *┃┃┃┃┃┃╋╋┃┃╋╋╋╋╋╋╋╋╋╋╋╋┃┃
 *┃┃┃┃┃┣━━┫┃┏━━┳━━┳┓┏┳━━┫┃
 *┃┗┛┗┛┃┃━┫┃┃┏━┫┏┓┃┗┛┃┃━╋┛
 *┗┓┏┓┏┫┃━┫┗┫┗━┫┗┛┃┃┃┃┃━╋┓
 *╋┗┛┗┛┗━━┻━┻━━┻━━┻┻┻┻━━┻┛
 *●▬▬▬▬▬▬ஜ۩۞۩ஜ۩۞۩ஜ۩۞۩ஜ▬▬▬▬▬▬●
 */

import java.util.*;
import java.io.*;

public class Template {
    FastScanner in;
    PrintWriter out;
    int[] used;
    static int INF = (int)(2 * 10e9 + 10);

    private void dfs(int v, ArrayList<ArrayList<edge>> where, int[] was, ArrayList<Integer> order) {
        was[v] = 1;
        for (edge i:where.get(v)) {
            if (was[i.b] == 0) {
                dfs(i.b, where, was, order);
            }
        }
        order.add(v);
    }
    private class edge {
        int a, b, w;
        edge(int u, int v, int c) {
            a = u;
            b = v;
            w = c;
        }
    }

    private long find(int root,int n, ArrayList<ArrayList<edge>> mas) {
        long res = 0;
        ArrayList<Integer>  mins = new ArrayList<Integer>(n);
        for (int i = 0; i < n; i++) mins.add(INF);

        ArrayList<ArrayList<edge>> zeros = new ArrayList<ArrayList<edge>>(n);
        for (int i = 0; i < n; i++) {
            zeros.add(new ArrayList<edge>());
        }
        for (int i = 0; i < n; i++)
            for (edge e:mas.get(i)) {
                int m = Math.min(e.w, mins.get(e.b));
                mins.set(e.b, m);
            }
        for (int i = 0; i < n; i++) {
            if (i != root)
                res += mins.get(i);
        }

        for (int i = 0; i < n; i++)
            for (edge e: mas.get(i))  {
                if (e.w == mins.get(e.b))
                    zeros.get(i).add(new edge(e.a, e.b, e.w - mins.get(e.b)));
            }
        used = new int[n];
        dfs(root, zeros, used, new ArrayList<Integer>());
        boolean fl = false;
        for (int i = 0; i < n; i++)
            if (used[i] == 0) fl = true;
        if (!fl) return res;
        used = new int[n];
        ArrayList<Integer> newComponents;
        newComponents = condensation(zeros);
        int sz = 0;
        for (int i: newComponents) {
            if (sz < i) sz = i;
        }
        sz++;
        ArrayList<ArrayList<edge>> cond = new ArrayList<ArrayList<edge>>(sz);
        for (int i = 0; i < sz; i++) {
            cond.add(new ArrayList<edge>());
        }
        for (int v = 0; v < n; ++v) {
            for (edge e: mas.get(v)) {
                int x = newComponents.get(v);
                int y = newComponents.get(e.b);
                if (x != y) {
                    cond.get(x).add(new edge(x, y, e.w - mins.get(e.b)));
                }
            }
        }
        res += find(newComponents.get(root), sz, cond);
        return res;
    }

    ArrayList<Integer> condensation(ArrayList<ArrayList<edge>> mas) {
        used = new int[mas.size()];
        ArrayList<Integer> order = new ArrayList<Integer>();
        for (int v = 0; v < mas.size(); ++v) {
            if (used[v] == 0) {
                dfs(v, mas,used, order);
            }
        }
        ArrayList<ArrayList<edge>> rev = new ArrayList<ArrayList<edge>>(mas.size());
        for (int i = 0; i < mas.size(); i++) {
            rev.add(new ArrayList<edge>());
        }
        for (int v = 0; v < mas.size(); ++v) {
            for (edge e:mas.get(v)) {
                rev.get(e.b).add(new edge(e.b, v, e.w));
            }
        }
        used = new int[mas.size()];
        ArrayList<Integer> ret = new ArrayList<Integer>(mas.size());
        for (int i = 0; i < mas.size(); i++) {
            ret.add(-1);
        }
        int color = 0;
        for (int i = 0; i < mas.size(); ++i) {
            int v = order.get(order.size() -1 - i);
            if (used[v] == 0) {
                dfs2(v, rev, color, ret);
                color++;
            }
        }

        return ret;
    }

    void dfs2 (int v, ArrayList<ArrayList<edge>> mas, int col, ArrayList<Integer> comp) {
        used[v] = 1;
        comp.set(v, col);
        for (edge e: mas.get(v))
            if (used[e.b] == 0)
                dfs2(e.b, mas, col, comp);
    }

    public void solve() throws IOException {
        int n, m;
        n = in.nextInt();
        m = in.nextInt();
        ArrayList<ArrayList<edge>> graph = new ArrayList<ArrayList<edge>>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<edge>());
        }

        for (int i = 0; i < m; i++) {
            int a, b, w;
            a = in.nextInt();
            b = in.nextInt();
            w = in.nextInt();
            a--;
            b--;
            graph.get(a).add(new edge(a, b, w));
        }
        used = new int[n];
        ArrayList<Integer> a = new ArrayList<Integer>();
        dfs(0, graph, used, a);
        for (int i:used) {
            if (i == 0) {
                out.println("NO");
                return;
            }
        }
        out.println("YES");
        out.println(find(0, n, graph));

    }

    public void run() {
        try {
            in = new FastScanner(new File("chinese.in"));
            out = new PrintWriter(new File("chinese.out"));

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
