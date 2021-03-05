package search;

import java.util.Objects;

public class BinarySearchSpan {

    //Pred:a[i] > a[i + 1]: i >= 0 && i + 1 < n
    //Post:a[R] <= x && (R == 0 || a[R - 1] > x) || R == n
    public static int firstX(final int[] a, int x) {
        //Pred: a[i] > a[i + 1]: i + 1 < n
        int l = -1;
        //l = -1
        int r = a.length;
        //r = n
        //Pred:a[i] > a[i + 1]: i >= 0 && i + 1 < n && l = -1 && r = n
        //Inv:  length >= r > l >= -1  && (a[l] > x || l == -1) && (a[r] <= x || r == n)
        while (r - l > 1) {
            //r - l > 1 && (a[l] > x || l == -1) && (a[r] <= x || r == n)
            int mid = (r + l) / 2;
            //mid = (r + l) / 2 && l < mid < r
            if (a[mid] > x) {
                //a[mid] > x
                l = mid;
                //l = mid && a[l] > x && r - l ~ (r - l') / 2 && (a[r] <= x || r == n)
            } else {
                //a[mid] <= x
                r = mid;
                //r = mid && a[r] <= x && r - l ~ (r' - l) / 2 && (a[r] <= x || r == n)
            }
            //(a[l] > x || l == -1) && (a[r] <= x || r == n)
        }
        //r - l == 1 && (a[l] > x || l == -1) && (a[r] <= x || r == n)
        return r;
    }

    //Pred:a[i] > a[i + 1]: i + 1 < n && -1 <= l < r <= n && (a[l] >= x || l == -1) && (a[r] < x || r == n)
    //Post:a[R] >= x && (R == n || a[R + 1] < x) || R == n
    public static int lastX(final int[] a, int l, int r, int x) {
        if (l + 1 == r) {
            //l + 1 == r && (a[l] >= x || l == -1) && (a[r] < x || r == n)
            return l;
        }
        int mid = (r + l) / 2;
        //mid = (r + l) / 2 && l < mid < r
        if (a[mid] >= x) {
            //mid < r && a[mid] >= x && (a[r] < x || r == n) && r - mid ~ (r - l) / 2
            //l = mid
            //Pred:a[i] > a[i + 1]: i + 1 < n && -1 < mid r <= n && a[mid] >= x && (a[r] < x || r == n)
            //Post:a[R] >= x && (R == n || a[R + 1] < x) || R == n
            return lastX(a, mid, r, x);
        }
        //l < mid && (a[l] >= x || l == -1) && a[mid] < x && mid - l ~ (r - l) / 2
        //r = mid
        //Pred:a[i] > a[i + 1]: i + 1 < n && -1 <= l < mid < n && (a[l] >= x || l == -1) && a[mid] < x
        //Post:a[R] >= x && (R == n || a[R + 1] < x) || R == n
        return lastX(a, l, mid, x);
    }

    //Pred:a[i] > a[i + 1]: i + 1 < n
    //Post:a[R] >= x && (R == n || a[R + 1] < x) || R == n
    public static int runLastX(final int[] a, int x) {
        //l < r && l == -1 && r == n
        return lastX(a, -1, a.length, x);
    }

    //Pred: args != null && args.length > 0 && args[i] > args[i + 1]: (i > 0 && i + 1 < args.length) && args - ints
    public static void main(String[] args) {
        Objects.requireNonNull(args);
        int x = Integer.parseInt(args[0]);
        int[] a = new int[args.length - 1];
        for (int i = 0; i < a.length; ++i) {
            a[i] = Integer.parseInt(args[i + 1]);
        }
        int l = firstX(a, x);
        int r = runLastX(a, x);
        /*System.err.println(l);
        System.err.println(r);*/
        System.out.println(l + " " + (r - l + 1));
    }
}
