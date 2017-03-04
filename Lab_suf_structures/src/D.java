/**
 * Created by vorona on 16.04.16.
 */

import java.io.*;
import java.util.*;

public class D {
//    Scanner in;
//
    FastScanner in;
    PrintWriter out;

    public void solve() throws IOException {
        String str = in.next();
        State[] st = sufAuto(str);

        int cnt = 0;
        ArrayList<Integer> a = new ArrayList<>();
        ArrayList<Integer> ar1 = new ArrayList<>();
        ArrayList<Integer> ar2 = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            State aSt = st[i];
            boolean f = false;
            for (int j = 0; j < aSt.next.length; j++) {
                if (aSt.next[j] > 0) {
                    f = true;
                    cnt++;
                    ar1.add(i);
                    ar2.add(j);
                }
            }
            if (!f)
                a.add(i);
        }
        out.println(size + " " + cnt);
        for (int i = 0; i < ar1.size(); i++) {
            out.println(ar1.get(i)+1 + " " + (st[ar1.get(i)].next[ar2.get(i)] + 1) + " " + (char) ('a' + ar2.get(i) - 1));
        }
        int pos = a.get(0);

        do {
            a.add(st[pos].link);
            pos = st[pos].link;
        } while (pos != -1);
        a.remove(a.size()-1);
        out.println(a.size());
        for (int t: a)
            out.print(t+1 + " ");
    }

    public int size = 1;
    public State[] sufAuto(String s) {
        int n = s.length();
        State[] st = new State[Math.max(2, 2 * n - 1)];
        st[0] = new State();
        int last = 0;

        for (char c : s.toCharArray()) {
            c -= 'a' - 1;
            int cur = size++;
            st[cur] = new State();
            st[cur].length = st[last].length + 1;
            st[cur].endpos = st[last].length;
            int p;
            for (p = last; p != -1 && st[p].next[c] == -1; p = st[p].link) {
                st[p].next[c] = cur;
            }
            if (p == -1) {
                st[cur].link = 0;
            } else {
                int q = st[p].next[c];
                if (st[p].length + 1 == st[q].length)
                    st[cur].link = q;
                else {
                    int clone = size++;
                    st[clone] = new State();
                    st[clone].length = st[p].length + 1;
                    st[clone].next = st[q].next.clone();
                    st[clone].link = st[q].link;
                    for (; p != -1 && st[p].next[c] == q; p = st[p].link)
                        st[p].next[c] = clone;
                    st[q].link = clone;
                    st[cur].link = clone;
                    st[clone].endpos = -1;
                }
            }
            last = cur;
        }
        for (int i = 1; i < size; i++) {
            st[st[i].link].l.add(i);
        }
        return st;
    }

    public void run() {
        try {
            in = new FastScanner(new File("automaton.in"));
            out = new PrintWriter(new File("automaton.out"));

//            in = new Scanner(System.in);
//            out = new PrintWriter(System.out);

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

    public static class State {
        int length;
        int link;
        int[] next = new int[27];
        int endpos;
        List<Integer> l = new ArrayList<>(0);

        State() {
            length = 0;
            link = -1;
            endpos = -1;
            Arrays.fill(next, -1);
        }
    }
}
