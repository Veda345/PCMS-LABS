/**
 * Created by vorona on 07.05.16.
 */

import java.io.*;
import java.util.*;

public class E {
    FastScanner in;
    PrintWriter out;

    public void solve() throws IOException {
        int n;
        n = in.nextInt();
        List<Job> jobs = new ArrayList<Job>();
        List<Job> jobsC = new ArrayList<Job>();
        for (int i = 0; i < n; i++) {
            int w, d;
            d = in.nextInt();
            w = in.nextInt();
            Job cur = new Job(w, d, i);
            jobs.add(cur);
        }
        jobsC.addAll(jobs);
        Collections.sort(jobs, new Comparator<Job>() {
            @Override
            public int compare(Job o1, Job o2) {
                return Integer.compare(o1.d, o2.d);
            }
        });

        TreeSet<Job> s = new TreeSet<Job>(new Comparator<Job>() {
            @Override
            public int compare(Job o1, Job o2) {
                if (o1.w != o2.w)
                return Integer.compare(o1.w, o2.w);
                else
                    return Integer.compare(o1.id, o2.id);
            }
        });

        int time = 0;
        for (Job job : jobs) {
            s.add(job);
            time++;
            if (job.d < time) {
                Job j = s.first();
                s.remove(j);
                time--;
            }
        }

        time = 0;
        int order[] = new int[n];
        for (int i = 0; i < n; i++)
            order[i] = -1;
        for (Job job : jobs) {
            if (s.contains(job)) {
                order[job.id] = time;
                time++;
            }
        }

        long sum = 0;
        for (int i = 0; i < n; i++) {
            if (order[i] == -1) {
                sum += jobsC.get(i).w;
                order[i] = time++;
            }
        }

        out.println(sum);

        for (int i : order) {
            out.print(i + " ");
        }

    }


    public void run() {
        try {
            in = new FastScanner(new File("p1sumwu.in"));
            out = new PrintWriter(new File("p1sumwu.out"));

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
        new E().run();

    }

    class Job {
        int w, d, id;

        Job(int w, int d, int id) {
            this.w = w;
            this.d = d;
            this.id = id;
        }
    }
}
