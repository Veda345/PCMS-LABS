/**
 * Created by vorona on 07.05.16.
 */

import java.util.*;
import java.io.*;

public class D {
    FastScanner in;
    PrintWriter out;

    public void solve() throws IOException {
        int n;
        n = in.nextInt();
        List<Job> jobs = new ArrayList<Job>();
        for (int i = 0; i < n; i++) {
            int p, d;
            p = in.nextInt();
            d = in.nextInt();
            Job cur = new Job(p, d, i);
            jobs.add(cur);
        }
        Collections.sort(jobs, new Comparator<Job>() {
            @Override
            public int compare(Job o1, Job o2) {
                return Integer.compare(o1.d, o2.d);
            }
        });

        TreeSet<Job> s = new TreeSet<Job>(new Comparator<Job>() {
            @Override
            public int compare(Job o1, Job o2) {
                if (o1.p != o2.p)
                return Integer.compare(o1.p, o2.p);
                else
                    return Integer.compare(o1.id, o2.id);
            }
        });
        int time = 0;
        for (Job job : jobs) {
            s.add(job);
            time += job.p;
            if (job.d < time) {
                Job j = s.last();
                s.remove(j);
                time -= j.p;
            }
        }

        time = 0;
        int order[] = new int[n];
        for (int i = 0; i < n; i++)
            order[i] = -1;
        for (Job job : jobs) {
            if (s.contains(job)) {
                order[job.id] = time;
                time += job.p;
            }
        }

        out.println(s.size());
        for (int i : order) {
            out.print(i + " ");
        }

    }


    public void run() {
        try {
            in = new FastScanner(new File("p1sumu.in"));
            out = new PrintWriter(new File("p1sumu.out"));

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
        new D().run();

    }

    class Job {
        int p, d, id;

        Job(int p, int d, int id) {
            this.p = p;
            this.d = d;
            this.id = id;
        }
    }
}
