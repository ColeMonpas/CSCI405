
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Random;

class homework2{
    public static void main(String[] args) {
        // Time lists for each sorting algorithm
        ArrayList<Long> insertionTime = new ArrayList<Long>();
        ArrayList<Long> shellTime = new ArrayList<Long>();
        ArrayList<Long> shannonTime = new ArrayList<Long>();

        // Changeable variables
        int numberOfTests = 5;
        int arraySize = 1000;
        int arrayType = 1; // 0 = random, 1 = ordered, 2 = backwards ordered 
        double percentChange = 0;

        // Time variables
        Long startTime;
        long endTime;
        long durationInNano;
        long durationInMillis;

        System.out.println("");

        // Testing area: 
        for(percentChange = 0; percentChange <= 0.5; percentChange += 0.25){
            for(arraySize = 10000; arraySize <= 40000; arraySize += 10000){
                for(arrayType = 0; arrayType < 3; arrayType++){
                    System.out.print("For size = " + arraySize + ", ");

                    if(arrayType == 0){
                        System.out.print("Randomly Sorted, ");
                    }else if(arrayType == 1){
                        System.out.print("Already Sorted, " + percentChange + " percent unsorted");
                    }else if(arrayType == 2){
                        System.out.print("Inversely Sorted, " + percentChange + " percent unsorted");
                    }
                    System.out.println("");

                    insertionTime.clear();
                    shellTime.clear();
                    shannonTime.clear();
                    
                    for(int i = 0; i < numberOfTests; i++){
                        // Insertion Sort
                        int arr[] = genArray(arraySize, arrayType, percentChange);
                        startTime = System.nanoTime();
                        insertionSort(arr);
                        endTime = System.nanoTime();
                        durationInNano = (endTime - startTime);
                        durationInMillis = TimeUnit.NANOSECONDS.toMillis(durationInNano);
                        insertionTime.add(durationInMillis);

                        // Shell Sort
                        int arr2[] = genArray(arraySize, arrayType, percentChange);
                        startTime = System.nanoTime();
                        shellSort(arr2);
                        endTime = System.nanoTime();
                        durationInNano = (endTime - startTime);
                        durationInMillis = TimeUnit.NANOSECONDS.toMillis(durationInNano);
                        shellTime.add(durationInMillis);

                        // Shannon Sort
                        int arr3[] = genArray(arraySize, arrayType, percentChange);
                        startTime = System.nanoTime();
                        shannonSort(arr3);
                        endTime = System.nanoTime();
                        durationInNano = (endTime - startTime);
                        durationInMillis = TimeUnit.NANOSECONDS.toMillis(durationInNano);
                        shannonTime.add(durationInMillis);
                    }
                    
                    // Finding the averages: 
                    double averageInsertion = 0;
                    double averageShell = 0;
                    double averageShannon = 0;
                    for(int i = 0; i < numberOfTests; i++){
                        averageInsertion += insertionTime.get(i);
                        averageShell += shellTime.get(i);
                        averageShannon += shannonTime.get(i);
                    }
                    averageInsertion = averageInsertion/numberOfTests;
                    averageShell = averageShell/numberOfTests;
                    averageShannon = averageShannon/numberOfTests;

                    //System.out.println("Insertion Sort Times: " + insertionTime.toString());
                    System.out.println("Insertion Sort Average: " + averageInsertion);
                    //System.out.println("Shell Sort Times: " + shellTime.toString());
                    System.out.println("Shell Sort Average: " + averageShell);
                    //System.out.println("Shannon Sort Times: " + shannonTime.toString());
                    System.out.println("Shannon Sort Average: " + averageShannon);
                    System.out.println("");
                }
                System.out.println("------------------------------------");
            }
        }
        
        
        
    }

    public static int[] genArray(int n, int value, double percent){
        Random rand = new Random();
        int[] arr = new int[n];
        switch(value){
            case 0: // random array size n from 0 to n.
                for(int i = 0; i < arr.length; i++){
                    arr[i] = rand.nextInt(n);
                }
                break;
            case 1: // ordered array
                for(int i = 0; i < arr.length; i++){
                    arr[i] = i;
                }
                break;
            case 2: // backwards array
                for(int i = n; i > 0; i--){
                    arr[n-i] = i;
                }
                break;
            default:
                // none
            break;
        }

        if(percent > 0){
            double amount = n*percent;
            for(int i = 0; i < amount; i++){
                int position = rand.nextInt(n);
                arr[position] = rand.nextInt(n);
            }
        }

        return arr;
    }

    // insertion sort
    public static void insertionSort(int arr[]){
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }

    // shell sort
    public static void shellSort(int arrayToSort[]){
        int n = arrayToSort.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int key = arrayToSort[i];
                int j = i;
                while (j >= gap && arrayToSort[j - gap] > key) {
                    arrayToSort[j] = arrayToSort[j - gap];
                    j -= gap;
                }
                arrayToSort[j] = key;
            }
        }
    }

    // Shannon Sort
    public static int[] shannonSort(int[] arr){
        int[] cmpasn = {0,0};
        int gap_increase = 10;
        int gap = 10;
        int start = 0;
        int finish = gap;
        int height = 0;
        int gaps = 0;
        if(arr.length % gap == 0){
            gaps = arr.length / gap; 
        }else{
            gaps = (arr.length / gap) + 1;
        }
        for(int i = 0; i < gaps; i++){
            height = 0;
            if(arr.length % gap == 0){
                height = arr.length / gap;
            }else{
                height = (arr.length / gap) + 1;
            }
            for(int j = 0; j < height; j++){
                arr = insertionGapSort(arr, start, finish, cmpasn);
                start += gap;
                finish += gap;    
            }
            gap += + gap_increase;
            if(gap > arr.length){
                gap = arr.length;
            }
            start = 0;
            finish = gap;
        }
        return cmpasn;
    }
    public static int[] insertionGapSort(int[] arr, int start, int finish, int[] cmpasn){
        if(finish > arr.length){
            finish = arr.length;
        }
        for (int i = start+1; i < finish; ++i) {
            int key = arr[i];
            int j = i - 1;
            while (j >= start && arr[j] > key) {
                cmpasn[0]++;
                arr[j + 1] = arr[j];
                cmpasn[1]++;
                j = j - 1;
            }
            cmpasn[0]++;
            arr[j + 1] = key;
            cmpasn[1]++;
        }
        return arr;
	}

    // printing arrays
    static void printArray(int arr[])
    {
        int n = arr.length;
        for (int i = 0; i < n; ++i)
            System.out.print(arr[i] + " ");
        System.out.println();
    }
}