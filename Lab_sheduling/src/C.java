import java.io.*;
import java.util.*;

public class C {
    FastScanner in;
    PrintWriter out;

    ArrayList<ArrayList<Integer>> edges = new ArrayList<ArrayList<Integer>>();
    int n;


    public void solve() throws IOException {
        final double q[];
        int p[];
        int w[];
        int e[];
        ArrayList<Set<Integer>> j;



        n = in.nextInt();
        p = new int[n];
        q = new double[n];
        w = new int[n];
        e = new int[n];
        j = new ArrayList<>();
        int P[] = new int[n];

        for (int i = 0; i < n; i++) {
            p[i] = in.nextInt();
            j.add(new HashSet<Integer>());
        }
        for (int i = 0; i < n; i++) {
            w[i] = in.nextInt();
        }

        for (int i = 0; i < n; i++) {
            edges.add(new ArrayList<Integer>());
        }
        for (int i = 0; i < n - 1; i++) {
            int u, v;
            u = in.nextInt();
            v = in.nextInt();
            edges.get(u - 1).add(v - 1);
            P[u - 1] = v - 1;
        }
        int root = -1;
        for (int i = 0; i < n; i++) {
            if (edges.get(i).size() == 0) {
                root = i;
            }
        }
        w[root] = Integer.MIN_VALUE; //?
        for (int i = 0; i < n; i++) {
            e[i] = i;
            j.get(i).add(i);
            q[i] = (double) w[i] / (double) p[i];
        }
        TreeSet<Integer> list = new TreeSet<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Double.compare(q[o1], q[o2]);
            }
        });
        for (int i = 0; i < n; i++)
            list.add(i);

        while (list.size() > 1) {
            int pos = list.last();
            list.remove(pos);
            int par = P[pos];
            int i;
            for (i = 0; i < n; i++) {
                if (j.get(i).contains(par))
                    break;
            }
            w[i] += w[pos];
            p[i] += p[pos];
            q[i] = w[i] / p[i];    // пересчитаем значения в вершине
            P[pos] = e[i];         // предком работы j теперь будет последняя работа в \pi_i
            e[i] = e[pos];           // последней работой в \pi_i теперь будет последняя работа в \pi_j
            j.get(i).addAll(j.get(pos));
        }

        out.println(root + " " + e[root] + " " + P[root] + " " + P[e[root]]);
        out.println(e[root]);
        for (int aP : p) out.print(aP + " ");
    }


    public void run() {
        try {
            in = new FastScanner(new File("p1outtreewc.in"));
            out = new PrintWriter(new File("p1outtreewc.out"));

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
        new C().run();

    }
}
