package org.zfun.leetcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

public class BoardSearch {

    public boolean searchStringDfs(char[][] board, String searchStr) {
        boolean found = false;
        
        // check for bad inputs
        if (searchStr == null || searchStr.length() == 0 || searchStr.length() > 16)
            return false;
        
        // find the starting coords
        int index = 0;
        Set<Coord> startingCoords = new HashSet<>();
        for (int i = 0; i< 4; i++)
            for (int j=0; j<4; j++)
                if (board[i][j] == searchStr.charAt(index))
                    startingCoords.add(new Coord(i,j));
        if (startingCoords.size() == 0)
            return false;
        
        // dfs
        for (Coord coord : startingCoords) {
            List<Coord> path = new LinkedList<>();
            path.add(coord);
            found = findStringDfs(coord, path, board, searchStr, index+1);
            if (found) {
                printPath(board, path);
                break;
            }
        }
        
        return found;
    }

    private void printPath(char[][] board, List<Coord> path) {
        for (Coord c : path) 
            System.out.println(c.toString() + " " + board[c.getI()][c.getJ()]);
        System.out.println();
    }
    
    private boolean findStringDfs(Coord coord, List<Coord> path, char[][] board, String searchStr, int index) {
        boolean found = false;
        // find all the neighbors
        List<Coord> neighbors = findNeighbors(coord);
        char searchC = searchStr.charAt(index);
        for (Coord c : neighbors) {
            char boardC = board[c.getI()][c.getJ()];
            if (!path.contains(c) && 
                    boardC == searchC) {
                path.add(c);
                // found a char in the right sequence
                if (index+1 == searchStr.length()) {
                    // found the last char, no need to continue, return true
                    found = true;
                    break;
                }
                // add char to path, call dfs.
                // if found then return path.  If not found remove char from path
                found = findStringDfs(c, path, board, searchStr, index+1);
                if (found)
                    break;
                else
                    path.remove(c);
            }
        }
        return found;
    }
    
    private List<Coord> findNeighbors(Coord coord) {
        List<Coord> neighbors = new LinkedList<>();
        for (int i = coord.getI()-1; i<= coord.getI()+1; i++)
            for (int j = coord.getJ()-1; j<= coord.getJ()+1; j++)
                if (i>=0 && i<4 && j>=0 && j< 4 && (i != coord.getI() || j != coord.getJ()))
                    neighbors.add(new Coord(i,j));
        return neighbors;
    }
    
    public boolean searchStringBfs(char[][] board, String searchStr) {
        boolean found = false;
        
        // check for bad inputs
        if (searchStr == null || searchStr.length() == 0 || searchStr.length() > 16)
            return false;
        
        // find the starting coords
        int index = 0;
        Queue<List<Coord>> pathQueue = new LinkedList<>();
        for (int i = 0; i< 4; i++)
            for (int j=0; j<4; j++)
                if (board[i][j] == searchStr.charAt(index)) {
                    List<Coord> path = new LinkedList<>(Arrays.asList(new Coord(i,j)));
                    pathQueue.offer(path);
                }
        if (pathQueue.size() == 0)
            return false;
        
        // bfs        
        while (pathQueue.peek() != null) {
            // set up path, level and lastCoord
            List<Coord> path = pathQueue.poll();
            int level = path.size()-1;
            Coord lastCoord = path.get(level);
            // check the neighbors, which are really the children
            List<Coord> neighbors = findNeighbors(lastCoord);
            char searchC = searchStr.charAt(level+1);
            for (Coord c : neighbors) {
                char boardC = board[c.getI()][c.getJ()];
                if (!path.contains(c) && 
                        boardC == searchC) {
                    // found the next char
                    List<Coord> newPath = new LinkedList<>(path);
                    newPath.add(c);
                    // is the char the last one
                    if (newPath.size() == searchStr.length()) {
                        found = true;
                        path = newPath;
                        break;
                    }
                    // put the new path in the queue
                    pathQueue.offer(newPath);
                }
            }
            if (found) {
                printPath(board, path);
                break;
            }
        }
        
        return found;
    }
    
    public static void main(String[] args) {
        char[][] board = new char[4][4];
        // configure
        board[0][0] = 'Y';
        board[0][1] = 'T';
        board[0][2] = 'A';
        board[0][3] = 'K';
        board[1][0] = 'S';
        board[1][1] = 'T';
        board[1][2] = 'E';
        board[1][3] = 'E';
        board[2][0] = 'C';
        board[2][1] = 'L';
        board[2][2] = 'S';
        board[2][3] = 'A';
        board[3][0] = 'X';
        board[3][1] = 'E';
        board[3][2] = 'L';
        board[3][3] = 'N';
        
        BoardSearch obj = new BoardSearch();
        
        // DFS
        String searchStr = "SEATTLE";
        boolean found = obj.searchStringDfs(board, searchStr);
        System.out.println(searchStr + " in board=" + found + "\n");
        
        searchStr = "SEAN";
        found = obj.searchStringDfs(board, searchStr);
        System.out.println(searchStr + " in board=" + found + "\n");

        searchStr = "SMILE";
        found = obj.searchStringDfs(board, searchStr);
        System.out.println(searchStr + " in board=" + found + "\n");

        // BFS
        searchStr = "SEATTLE";
        found = obj.searchStringBfs(board, searchStr);
        System.out.println("BFS " + searchStr + " in board=" + found + "\n");

        searchStr = "SEAN";
        found = obj.searchStringBfs(board, searchStr);
        System.out.println("BFS " + searchStr + " in board=" + found + "\n");

        searchStr = "SMILE";
        found = obj.searchStringBfs(board, searchStr);
        System.out.println("BFS " + searchStr + " in board=" + found + "\n");
}
    
    @Data
    @AllArgsConstructor
    private class Coord {
        Integer i;
        Integer j;

        /* This is just to show how Eclipse generate a hashdoce.  It is based on using
         * a prime number=31 and result=1
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + ((i == null) ? 0 : i.hashCode());
            result = prime * result + ((j == null) ? 0 : j.hashCode());
            return result;
        }
        */
    }
}
