package sort;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * 比较排序算法
 */
public class SortCompare {
    //选择不同的排序算法
    public static double time(String alg,Double[]a){
        Stopwatch stopwatch=new Stopwatch();
        switch (alg){
            case "Insertion":
                Insertion.sort(a);
                break;
            case "Selection":
                Insertion.sort(a);
                break;
            case "Shell":
                Insertion.sort(a);
                break;
                default:
        }
        return stopwatch.elapsedTime();
    }
    //使用算法alg对T个长度为N的数组进行排序
    public static double timeRandomInput(String alg,int N,int T){
        double total=0.0;
        Double[] a=new Double[N];
        for (int i = 0; i <T ; i++) {
            for (int j = 0; j <N ; j++) {
                a[j]= StdRandom.uniform();
            }
            total+=time(alg,a);
        }
        return total;
    }

    public static void main(String[] args) {
        String alg1="Shell";
        String alg2="Insertion";
        int N=100000;
        int T=2;
        double t1=timeRandomInput(alg1,N,T);
        double t2=timeRandomInput(alg2,N,T);
        System.out.println("t1: "+t1);
        System.out.println("t2: "+t2);
        System.out.println("t2/t1: "+t2/t1);
    }
}
