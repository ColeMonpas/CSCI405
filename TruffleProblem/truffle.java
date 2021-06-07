/*
-------------------------------
    Cole Monpas
    CSCI 405
    Programming Assignment 3 
-------------------------------
*/



import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class truffle{
    public static void main(String[] args){
        // getting the input text file name: 
        String fileName = "";

        try{
            // finding the file name: 
            if(args.length == 1){
                fileName = args[0];
            }else{
                System.out.println("ERROR: Input file name");
                System.exit(1);
            }
            
            // setting up the file system
            File file = new File(fileName);
            Scanner reader = new Scanner(file);
            int rows = 0;
            int columns = 0;

            // finding the size of the array
            String[] columnFinder = reader.nextLine().split("\\s+");
            for(int i = 0; i < columnFinder.length; i++){
                // # of columns
                columns++;
            }
            reader.close();
            reader = new Scanner(file);
            while(reader.hasNextLine()){
                // # of rows
                reader.nextLine();
                rows++;
            }
            reader.close();


            reader = new Scanner(file);
            // adds all of the integers into an arrayList
            ArrayList<Integer> tempLand = new ArrayList<>();
            while(reader.hasNextLine()){
                tempLand.add(reader.nextInt());
            }
            reader.close();
            
            // adding the arrayList data to the actual array 
            int[][] land = new int[rows][columns]; 
            for(int i = 0; i < rows; i++){
                for(int j = 0; j < columns; j++){
                    land[i][j] = tempLand.remove(0);
                }
            }

            // finding the solution
            solution(land, rows, columns);

        } catch(FileNotFoundException e){
            System.out.println("ERROR: Could not find the file");
        }
    }

    /*
    This functions solves the truffles problem and also prints out the path 
    to take.
    */
    public static void solution(int[][] land, int rows, int columns){

        // initializing this table to hold the amount of truffles picked up on a path. 
        // The largest number in the top row will be the start of the path, and the value there
        // will be the most amount of truffles you can pick up.
        int[][] truffleTable = new int[rows][columns];
        for(int[] row : truffleTable){
            Arrays.fill(row, 0);
        }

/*
        #---------------------------WHERE THE MAGIC HAPPENS---------------------------#
*/
        // this is the actual algorithm: 
        for(int row = rows-1; row >= 0; row--){
            for(int col = 0; col < columns; col++){
                // moving down:
                int down = (row == rows-1) ? 0 : truffleTable[row+1][col];
                // moving down and to the right: 
                int downRight = (row == rows-1 || col == columns-1) ? 0 : truffleTable[row+1][col+1];
                // moving down and to the left: 
                int downLeft = (row == rows-1 || col == 0) ? 0 : truffleTable[row+1][col-1];
                // dynamic programming where we save the already calculated values and the new values: 
                truffleTable[row][col] = land[row][col] + Math.max(down, Math.max(downRight, downLeft));
            }
        }
/*
        #------------------------------END OF THE MAGIC------------------------------#

        --The rest after this is just for printing:
*/

        
        // printing out max amount of truffles you can gather: 
        int maxTruffleIndex = maxIndex(truffleTable[0]);
        // the start of the path is always on row 0, and the greatest value in the first row is where we start the path
        System.out.println("Max truffles: " + truffleTable[0][maxTruffleIndex]);

        // printing the actual path: 
        boolean searching = true; 
        int row = 0;
        int col = maxTruffleIndex;
        System.out.println("[" + row + "," + col + "] - " + land[row][col]);
        while(searching){
            row++;
            // if we are on the last row, just quit. 
            if(row == rows){
                searching = false;
                continue; 
            }
            // if we are on the left border of the array only look down and to the right
            if(col == 0){
                int optionOne = truffleTable[row][col + 1]; // down and right
                int optionTwo = truffleTable[row][col]; // down 
                // find the max of the options:
                if(optionOne > optionTwo){
                    col++;
                    System.out.println("[" + row + "," + col + "] - " + land[row][col]);
                    continue;
                }
                System.out.println("[" + row + "," + col + "] - " + land[row][col]);
                continue;
            }
            // if we are on the right border of the array only look down and to the left
            if(col == columns-1){
                int optionOne = truffleTable[row][col - 1]; // down and to the left
                int optionTwo = truffleTable[row][col]; // down
                // find the max of the options:
                if(optionOne > optionTwo){
                    col--;
                    System.out.println("[" + row + "," + col + "] - " + land[row][col]);
                    continue;
                }
                System.out.println("[" + row + "," + col + "] - " + land[row][col]);
                continue;
            }

            // when we are in the middle of the array you can look at all three options 
            int optionOne = truffleTable[row][col + 1]; // down and right
            int optionTwo = truffleTable[row][col]; // down
            int optionThree = truffleTable[row][col - 1]; // down and left
            int max = Math.max(optionOne, Math.max(optionTwo, optionThree));
            // find the max of the options:
            if(optionOne == max){
                col++;
                System.out.println("[" + row + "," + col + "] - " + land[row][col]);
            }else if(optionTwo == max){
                System.out.println("[" + row + "," + col + "] - " + land[row][col]);
            }else if(optionThree == max){
                col--;
                System.out.println("[" + row + "," + col + "] - " + land[row][col]);
            }
        }
    }

    /*
        this function returns the index of the largest int in an array
    */
    public static int maxIndex(int[] array){
        int maxAt = 0;
        for (int i = 0; i < array.length; i++) {
            maxAt = array[i] > array[maxAt] ? i : maxAt;
        }
        return maxAt;
    }
}