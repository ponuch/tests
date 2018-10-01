/**
* https://petr-mitrichev.blogspot.com/2013/05/fenwick-tree-range-updates.html
* https://gist.github.com/jakab922/ec51c6a6c56e5cab7c229d3c926ada2a
*/
package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ArrayManipulator2 {

    /*
     * Complete the arrayManipulation function below.
     */
    static int arrayManipulation(int n, int[][] queries) {
        /*
         * Write your code here.
         */
        int[] fwam = new int[n];
        int[] fwaa = new int[n];


        for(int i = 0; i < queries.length; i++) {
            int minIndex = queries[i][0];
            int maxIndex = queries[i][1];
            int appendValue = queries[i][2];

            rangeAdd(fwam, fwaa, minIndex, maxIndex, appendValue);
        }

        int result = 0;
        int prev = 0;
        for(int i = 1; i < n ; i++){
            int curr = rangeQuery(fwam, fwaa, i);
            result = Math.max(result, curr - prev);
            prev = curr;
        }


        System.out.println(result);
        return result;
    }

    private static int rangeQuery(int[] fwam, int[] fwaa, int at){
        int m = 0;
        int a = 0;
        int st = at;

        while(at > 0){
            m += fwam[at];
            a += fwaa[at];
            at -= at & (-at);
        }
        return m * st + a;
    }

    private static void update (int[] fwam, int[] fwaa, int at, int m, int a){
        int s = fwam.length;
        while (at < s){
            fwam[at] += m;
            fwaa[at] += a;
            at += at & (-at);
        }
    }

    private static void rangeAdd(int[] fwam, int[] fwaa, int minIndex, int maxIndex, int appendValue){
        update(fwam, fwaa, minIndex, appendValue, -appendValue * (minIndex - 1));
        update(fwam, fwaa, maxIndex, -appendValue, appendValue * maxIndex);
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        /*arrayManipulation(10, null);
        System.exit(0);*/
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("out.txt"));

        String[] nm = scanner.nextLine().split(" ");

        int n = Integer.parseInt(nm[0].trim());

        int m = Integer.parseInt(nm[1].trim());

        int[][] queries = new int[m][3];

        for (int queriesRowItr = 0; queriesRowItr < m; queriesRowItr++) {
            String[] queriesRowItems = scanner.nextLine().split(" ");

            for (int queriesColumnItr = 0; queriesColumnItr < 3; queriesColumnItr++) {
                int queriesItem = Integer.parseInt(queriesRowItems[queriesColumnItr].trim());
                queries[queriesRowItr][queriesColumnItr] = queriesItem;
            }
        }

        int result = arrayManipulation(n, queries);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();
    }
}
