package DataStructures;

import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.min;

public class SegmentTree {

    static long inp[], segTree[], lazyTree[];

    // Here: 0-based array so left-child: 2*node+1 && right-child: 2*node+2
    // If-want: 1-based array, change: left-child to 2*node && right-child to 2*node+1

    static void build(int l, int r, int node) {
        if(l == r) {
            segTree[node] = inp[l];
            return;
        }

        int mid = (l + r) / 2;
        build(l, mid, 2 * node + 1);                // left-child
        build(mid + 1, r, 2 * node + 2);         // right-child

        // Here: min of both child is assigned as it is RMQ
        // change as per requirement..

        segTree[node] = min(segTree[2 * node + 1], segTree[2 * node + 2]);
    }

    static long query(int l, int r, int node, int query_l, int query_r) {
        if(query_l > r || query_r < l) {            // if no overlap..
            return Long.MAX_VALUE;     // return that value that will neglect this range..
        }

        if(l >= query_l && r <= query_r) {          // If total overlap..
            // [l, r] inside [query_l, query_r]..
            return segTree[node];       // return this node value as we don't need to go any further..
        }

        // partial overlap..so go on both sides now..
        int mid = (l + r) / 2;
        long val_left = query(l, mid, 2 * node + 1, query_l, query_r);          // query left-child
        long val_right = query(mid + 1, r, 2 * node + 2, query_l, query_r);  // query right-child

        // As this is RMQ so return minimum..
        // Change as per requirements..

        return min(val_left, val_right);
    }

    // Don't go for Point Update as you can do same with Range Update in same Complexity..
    static void point_update(int l, int r, int node, int pos, int val) {
        if(l == r) {        // reached leaf node..so update now..
            // Update this as required..
            inp[pos] = val;
            segTree[node] = val;
            return;
        }

        int mid = (l + r) / 2;

        // check if given update_pos is on left child..
        if(pos >= l && pos <= mid) point_update(l, mid, 2 * node + 1, pos, val);
            // else on right child..
        else point_update(mid + 1, r, 2 * node + 2, pos, val);

        // Since child nodes are updated..Update their parent node now..
        // Here it is RMQ so taken min of both childs..
        // Change as per requirement..
        segTree[node] = min(segTree[2 * node + 1], segTree[2 * node + 2]);
    }

    // Range Update method with Lazy Propagation..
    static void range_update(int l, int r, int node, int query_l, int query_r, long val) {
        // check if previous updates on this node are left..
        if(lazyTree[node] != 0) {
            // Update: Replace old to new value..
            // Change it as per requirement..
            // for eg: to increment do --> segTree[node] += lazyTree[node]
            segTree[node] = lazyTree[node];

            if(l != r) {            // if not a leaf node
                // update childs as per requirement as above..
                lazyTree[2 * node + 1] = lazyTree[node];
                lazyTree[2 * node + 2] = lazyTree[node];
            }

            // now since this node is done updating..change its lazy to '0'
            lazyTree[node] = 0;
        }

        // check if no-overlap..
        if(query_l > r || query_r < l) return;

        // check if total-overlap..
        if(l >= query_l && r <= query_r) {
            // reached the final node (not necessarily leaf node) and update it as per requirement..
            // Change it as per requirement..
            // for eg: to increment do --> segTree[node] += val
            segTree[node] = val;

            if(l != r) {            // if not a leaf node..
                // update childs as per above requirements..
                lazyTree[2 * node + 1] = val;
                lazyTree[2 * node + 2] = val;
            }

            // since all required updates are done..we don't go further now..
            return;
        }

        // If partial-overlap..
        int mid = (l + r) / 2;

        // go for left-child
        range_update(l, mid, 2 * node + 1, query_l, query_r, val);
        // go-for right-child
        range_update(mid + 1, r, 2 * node + 2, query_l, query_r, val);

        // since we have updated both child of this node by now..update this parent node..
        // change it as per requirement..
        segTree[node] = min(segTree[2 * node + 1], segTree[2 * node + 2]);
    }

    public static void main(String args[]) {

        int size = 5;

        inp = new long[size];
        segTree = new long[4 * size];
        lazyTree = new long[4 * size];

        for(int i=0;i<size;i++) {
            inp[i] = abs(new Random().nextLong());
        }

        build(0, size - 1, 0);

        // Get Minimum in range: 1-4
        System.out.println(query(0, size - 1, 0, 0, 3));

        // Update range 1-3 to -1
        range_update(0, size - 1, 0, 0, 2, -1);

        // Get Minimum in range: 1-4
        System.out.println(query(0, size - 1, 0, 0, 3));
    }
}
