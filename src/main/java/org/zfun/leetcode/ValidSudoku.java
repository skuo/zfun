package org.zfun.leetcode;

import java.util.HashSet;
import java.util.Set;

/*
rule{1}: there can be only one occurs of int from {1-9} is a row
rule{2}: there can be only one occurs of int from {1-9} is a column
rule{3}: there can be only one occurs of int from {1-9} is a 3x3 sub-box
 */
public class ValidSudoku {
    public boolean isValidSudoku(char[][] board) {
        // check row
        for (int i = 0; i < 9; i++) {
            Set<Character> set = new HashSet<>();
            for (int j = 0; j < 9; j++) {
                char c = board[i][j];
                if (c == '.')
                    continue;
                if (Character.getNumericValue(c) > 9 || Character.getNumericValue(c) < 1)
                    return false;
                if (set.contains(c))
                    return false;
                // save char to set
                set.add(c);
            }
        }

        // check column
        for (int j = 0; j < 9; j++) {
            Set<Character> set = new HashSet<>();
            for (int i = 0; i < 9; i++) {
                char c = board[i][j];
                if (c == '.')
                    continue;
                if (Character.getNumericValue(c) > 9 || Character.getNumericValue(c) < 1)
                    return false;
                if (set.contains(c))
                    return false;
                // save char to set
                set.add(c);
            }
        }

        // check sub-box
        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++) {
                Set<Character> set = new HashSet<>();
                for (int i = 3 * x; i < 3 * (x + 1); i++)
                    for (int j = 3 * y; j < 3 * (y + 1); j++) {
                        char c = board[i][j];
                        if (c == '.')
                            continue;
                        if (Character.getNumericValue(c) > 9 || Character.getNumericValue(c) < 1)
                            return false;
                        if (set.contains(c))
                            return false;
                        // save char to set
                        set.add(c);
                    }
            }

        return true;
    }

    public static void main(String[] args) {
        ValidSudoku obj = new ValidSudoku();
        char[][] board = { 
                { '.', '8', '7', '6', '5', '4', '3', '2', '1' },
                { '2', '.', '.', '.', '.', '.', '.', '.', '.' }, 
                { '3', '.', '.', '.', '.', '.', '.', '.', '.' },
                { '4', '.', '.', '.', '.', '.', '.', '.', '.' }, 
                { '5', '.', '.', '.', '.', '.', '.', '.', '.' },
                { '6', '.', '.', '.', '.', '.', '.', '.', '.' }, 
                { '7', '.', '.', '.', '.', '.', '.', '.', '.' },
                { '8', '.', '.', '.', '.', '.', '.', '.', '.' }, 
                { '9', '.', '.', '.', '.', '.', '.', '.', '.' } 
                };
        System.out.println(obj.isValidSudoku(board));

    }
}
