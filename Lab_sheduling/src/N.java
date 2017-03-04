import java.io.*;
import java.util.*;

public class N {
    FastScanner in;
    PrintWriter out;

    public void solve() throws IOException {
        int n;
        n = in.nextInt();
        ArrayList<Integer> a = new ArrayList<>();
        ArrayList<Integer> b = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            a.add(in.nextInt());
        }

        for (int i = 0; i < n; i++) {
            b.add(in.nextInt());
        }

        ArrayList<Job> jobs = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            jobs.add(new Job(a.get(i), b.get(i), i));
        }

        ArrayList<Job> L = new ArrayList<>();
        ArrayList<Job> R = new ArrayList<>();

        while (jobs.size() > 0) {
            Job ma = jobs.get(0);
            if (ma.a < ma.b) {
                L.add(ma);
            } else {
                R.add(ma);
            }
            jobs.remove(ma);
        }
        Collections.sort(L, new Comparator<Job>() {
            @Override
            public int compare(Job o1, Job o2) {
                return Integer.compare(o1.a, o2.a);
            }
        });
        Collections.sort(R, new Comparator<Job>() {
            @Override
            public int compare(Job o1, Job o2) {
                return -Integer.compare(o1.b, o2.b);
            }
        });
        L.addAll(R);
        long time1 = 0, time2 = L.get(0).a;

        for (Job job : L) {
            time2 = Math.max(time2, time1 + job.a);
            time1 += job.a;
            time2 += job.b;
        }

        out.println(time2);
        for (int i = 0; i < 2; i++) {
            for (Job job : L) {
                out.print(job.id + 1 + " ");
            }
            out.println();
        }
    }


    public void run() {
        try {
            in = new FastScanner(new File("f2cmax.in"));
            out = new PrintWriter(new File("f2cmax.out"));

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
        new N().run();

    }

    class Job {
        int a, b, id;

        Job(int a, int b, int id) {
            this.a = a;
            this.b = b;
            this.id = id;
        }
    }

}
