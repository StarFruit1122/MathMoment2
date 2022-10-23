package com.example.mathmoment2;

public class BooleanSquares {
    public static boolean[][] BlockedInCheck(int[][] grid) {
        int numBounds = 10;
        int p = 0;
        boolean[][] isBlocked = new boolean[grid.length][grid.length];

        while (p < numBounds) {
            int sequence = 10;
            int z = 0;

            int x = (int) (Math.random() * (grid.length - 1));
            int y = (int) (Math.random() * (grid.length - 1));
            int i = x;
            int j = y;

            while (z < sequence) {
                int up = 1;
                int down = 2;
                int right = 3;
                int left = 4;
                int random = (int) (Math.random() * 5);


                if (random == up) {
                    i += 1;
                    if (i >= isBlocked.length || isBlocked[i][j]) {
                        z = sequence;
                    }
                    else {
                        isBlocked[i][j] = true;
                        z++;
                    }
                } else if (random == down) {
                    i --;
                    if (i < 0|| isBlocked[i][j]) {
                        z = sequence;
                    }
                    else {
                        isBlocked[i][j] = true;
                        z++;
                    }
                } else if (random == right) {
                    j += 1;
                    if (j >= isBlocked.length || isBlocked[i][j]) {
                        z = sequence;
                    }
                    else {
                        isBlocked[i][j] = true;
                        z++;
                    }
                } else if (random == left) {
                    j --;
                    if (j < 0 || isBlocked[i][j]) {
                        z = sequence;
                    }
                    else {
                        isBlocked[i][j] = true;
                        z++;
                    }
                }
            }
            p ++;
        }
        return isBlocked;
    }

}
