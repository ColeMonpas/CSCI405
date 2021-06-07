import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// Cole Monpas

public class skyline{

    public static void main(String[] args){
        
        Scanner input = new Scanner(System.in);
        System.out.print("Enter file name: ");
        String fileName = input.nextLine();

        try{
            File file = new File(fileName);
            FileReader reader = new FileReader(file);
            BufferedReader b_reader = new BufferedReader(reader);

            int c = 0;
            String data = "";
            int columnCount = 0;
            // turning the text file into a string
            while((c = b_reader.read()) != -1){
                char character = (char) c;
                if(character == '>'){
                    columnCount++;
                    data += " ";
                }else if(character == '<'){
                    data += "";
                }else{
                    data += character;
                }
            }
            
            // turning the string into an array
            int[][] array = stringToArray(data, columnCount);

            // printing the array
            System.out.println(Arrays.deepToString(array));

            // solve the problem with the array here: 
            ArrayList<int[]> solution = skylineSolve(array);

            // printing the solution
            System.out.println("");
            for(int i=0; i<solution.size(); i++){
                System.out.print("<");
                System.out.print(solution.get(i)[0]);
                System.out.print(" ");
                System.out.print(solution.get(i)[1]);
                System.out.print(">");
            }

            b_reader.close();
        } catch(IOException e){
            System.out.println("ERROR: Could not find the file");
        }
        
        input.close();
    }

    // -------------------------------------------------------------
    // converts the input string into an array
    private static int[][] stringToArray(String str, int columns){

        int word = 0;
        int[][] array = new int[columns][3];
        String[] words = str.split(" ");

        for(int i=0; i < array.length; i++){
            for(int j=0; j < array[i].length; j++){
                array[i][j] = Integer.parseInt(words[word]);
                word++;
            }
        }

        return array;
    }

    // -------------------------------------------------------------
    // solves the skyline problem
    private static ArrayList<int[]> skylineSolve(int[][] buildings){
        // finding the max height
        int[] heights = new int[buildings.length];
        for(int i=0; i < buildings.length; i++){
            heights[i] = buildings[i][1];
        }
        Arrays.sort(heights);
        int maxHeight = heights[heights.length-1];
        
        // finding the max width
        int maxWidth = buildings[buildings.length-1][2];

        // initializing the map 
        int[][] map = new int[maxHeight+2][maxWidth+2];
        for(int i=0; i<buildings.length; i++){
            for(int j=buildings[i][0]; j<buildings[i][2]; j++){
                map[buildings[i][1]][j] = 1;
            }
        }
        // set the base of the map to 1's
        for(int i=0; i<map[0].length; i++){
            map[0][i] = 1;
        }

        HashSet<Integer> sigNumbers = new HashSet<Integer>();
        for(int i=0; i<buildings.length; i++){
            sigNumbers.add(buildings[i][0]);
            sigNumbers.add(buildings[i][2]);
        }
        ArrayList<Integer> sigNums = new ArrayList<Integer>(sigNumbers);

        // sort the significant numbers we need to check. 
        Collections.sort(sigNums);

        // finally find the solutions
        ArrayList<int[]> solution = new ArrayList<int[]>();
        for(int i=0; i<sigNums.size(); i++){
            int x = sigNums.get(i);
            for(int j=maxHeight+1; j>=0; j--){ // may need to be +1
                if(map[j][x] == 1){ 
                    int[] input = new int[2];
                    input[0] = x;
                    input[1] = j;
                    solution.add(input);
                    break;
                }
            }
        }

        // remove not needed solutions. 
        int memory = 0;
        for(int i=0; i<solution.size()-1; i++){
            memory = solution.get(i)[1];
            if(memory == solution.get(i+1)[1]){
                solution.remove(i+1);
                i--;
            }
        }

        // printing the grid
        /*
        for(int i=map.length-1; i>=0; i--){
            for(int j=0; j<map[i].length; j++){
                System.out.print(map[i][j]);
            }
            System.out.println("");
        }
        */

        return solution;
    }
}