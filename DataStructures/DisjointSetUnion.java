package DataStructures;

public class DisjointSetUnion {

    static int[] arr;
    static int[] size;

    static void initialize(int n) {
        arr = new int[n + 1];
        size = new int[n + 1];

        for (int i = 0; i <= n; i++) {
            arr[i] = i;
            size[i] = 1;
        }
    }

    static int root(int r) {
        while (arr[r] != r) {
            arr[r] = arr[arr[r]];
            r = arr[r];
        }

        return r;
    }

    static void union(int u, int v) {
        int r1 = root(u);
        int r2 = root(v);

        if (r1 != r2) {
            if (size[r1] < size[r2]) {
                arr[r1] = arr[r2];
                size[r2] += size[r1];
            } else {
                arr[r2] = arr[r1];
                size[r1] += size[r2];
            }
        }
    }

    public static void main(String args[]) {

        int n = 5;
        initialize(n);

        union(1, 2);
        union(2, 3);

        if(root(1) == root(3)) {
            System.out.println("Success");
        }
        else {
            System.out.println("Failed");
        }

        if(root(1) == root(4)) {
            System.out.println("Failed");
        }
        else {
            System.out.println("Success");
        }
    }
}
