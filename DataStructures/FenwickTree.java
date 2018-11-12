package DataStructures;

import java.util.Random;

import static java.lang.Math.abs;

public class FenwickTree {

    static long BIT[], a[];
    static int n;

    static void update(int pos, long val) {
        while(pos <= n) {
            BIT[pos] += val;        // change '+' sign by what is required..
            pos += (pos & -pos);
        }
    }

    static long query(int pos) {
        long sum = 0;
        while(pos > 0) {
            sum += BIT[pos];
            pos -= (pos & -pos);
        }

        return sum;
    }

    public static void main(String args[]) {

        n = 7;
        a = new long[n + 1];
        BIT = new long[n + 1];

        for(int i=1;i<=n;i++) {
            a[i] = i;
            update(i, a[i]);            // NlogN time to build initially..
        }

        System.out.println(query(4) - query(1));

        update(3, 4);

        System.out.println(query(4) - query(1));
    }
}
