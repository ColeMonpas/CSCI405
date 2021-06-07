import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// Cole Monpas

class doubloons{
    public static void main(String[] args){
        // getting the input text file name: 
        String fileName = args[0];
        System.out.println();

        try{
            // setting up variables for reading the data into them
            File file = new File(fileName);
            Scanner reader = new Scanner(file);
            int MinInterval = 0;
            ArrayList<Integer> doubloonsList = new ArrayList<Integer>();

            // grabbing the min interval number
            MinInterval = Integer.parseInt(reader.next());
            reader.nextLine();
            // grabbing all of the doubloons from the file
            while(reader.hasNextLine()){
                doubloonsList.add(Integer.parseInt(reader.next()));
            }
            reader.close();

            // printing out the input data
            System.out.println("Input information: ");
            System.out.println("Min Interval, " + MinInterval);
            System.out.print("Doubloons");
            printList(doubloonsList);
            System.out.println();

            // Find the solution
            solution(doubloonsList, MinInterval);

        } catch(FileNotFoundException e){
            System.out.println("ERROR: Could not find the file");
        }
    }

    public static void printList(ArrayList<Integer> list){
        for(int i = 0; i < list.size(); i++){
            System.out.print(", ");
            System.out.print(list.get(i));
        }
        System.out.println();
    }

    public static void solution(ArrayList<Integer> doubloonsList, int minInt){

        // dyn prog stores the memorized solutions
        ArrayList<Integer> dynProg = new ArrayList<Integer>();

        // filling up the new dp list
        for(int i = 0; i < doubloonsList.size()+2+minInt; i++){
            dynProg.add(0);
        }

        ArrayList<ArrayList<Pair>> pastPositions = new ArrayList<ArrayList<Pair>>();
        for(int i = 0; i < dynProg.size(); i++){
            pastPositions.add(new ArrayList<Pair>());
        }

        // finding the solution: 
        for(int i = doubloonsList.size()-1; i >= 1; --i){
            int option0 = dynProg.get(i+1);
            int option1 = dynProg.get(i+minInt)+doubloonsList.get(i);
            int pick = Math.max( option0, option1);
            if(option0 == pick){        
                //adding the past positions to the trace back 
                pastPositions.get(i).add( new Pair(i+1, doubloonsList.get(i), i));
            }else{
                //adding the past positions to the trace back 
                pastPositions.get(i).add( new Pair(i+minInt, doubloonsList.get(i), i));
            }
            // setting the dyn prog of the max of the options. 
            dynProg.set(i, pick);
        }

        // finding the start position of the dynProg list. 
        int maxDoubloons = dynProg.get(1);
        int startPosition = 0;
        for(int i = 1; i < dynProg.size(); i++){
            if(dynProg.get(i) == maxDoubloons){
                startPosition = i;
            }else{
                break;
            }
        }

        // adding the values and positions to the output of the best positions to choose
        ArrayList<Integer> pickedValues = new ArrayList<Integer>();
        ArrayList<Integer> pickedPositions = new ArrayList<Integer>();

        boolean looping = true;
        while(looping){
            if(pastPositions.get(startPosition).size() > 0){
                int positionTemp = pastPositions.get(startPosition).get(0).index;
                int valueTemp = pastPositions.get(startPosition).get(0).value;
                pickedValues.add(valueTemp);
                pickedPositions.add(positionTemp);
                startPosition = pastPositions.get(startPosition).get(0).pastIndex;
            }else{
                looping = false; 
            }
        }

        // checking for conflicts in the positions: 
        
        for(int i = 1; i < pickedPositions.size(); i++){
            int look = pickedPositions.get(i);
            int last = pickedPositions.get(i-1);
            if(Math.abs(look - last) < minInt){
                if(pickedValues.get(i) > pickedValues.get(i-1)){
                    pickedValues.remove(i-1);
                    pickedPositions.remove(i-1);
                    i--;
                }else{
                    pickedValues.remove(i);
                    pickedPositions.remove(i);
                    i--;
                }
            }
        }
        
        System.out.println("Output Information: ");
        System.out.println("Positions: " + pickedPositions);
        System.out.println("Values: " + pickedValues);
        System.out.println("Total: " + maxDoubloons);
    }

}

class Pair{
    Integer pastIndex;
    Integer value;
    Integer index;

    public Pair(Integer pastIndex, Integer value, Integer index) {
        this.pastIndex = pastIndex;
        this.value = value;
        this.index = index;
    }

    @Override
    public String toString() {
        return String.valueOf( pastIndex) + "," + String.valueOf(value) + "," + String.valueOf(index);
    }
}