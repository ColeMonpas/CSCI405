import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class homework3{

    public static void main(String []args) {
        arrayTest test = new arrayTest();
        int size = Integer.valueOf(args[0]);
        test.start(size);
    }

    private static class arrayTest{
        public void start(int size){
            int[] array = new int[size];

            array = genArray(array);
            long startTime = System.nanoTime();
            array = bubbleSort(array);
            long endTime = System.nanoTime();
            long durationInNano = (endTime - startTime);
            long durationInMillis = TimeUnit.NANOSECONDS.toMillis(durationInNano);
            System.out.println("BubbleSort: " + durationInMillis);

            array = genArray(array);
            startTime = System.nanoTime();
            array = gnomeSort(array, array.length);
            endTime = System.nanoTime();
            durationInNano = (endTime - startTime);
            durationInMillis = TimeUnit.NANOSECONDS.toMillis(durationInNano);
            System.out.println("GnomeSort: " + durationInMillis);
        }

        private int[] genArray(int[] array){
            Random rand = new Random();
            for(int i = 0; i < array.length; i++){
                array[i] = rand.nextInt(999);
                //array[i] = i;
            }
            return array;
        }

        private int[] reverseArray(int[] a, int n){
        int[] b = new int[n];
        int j = n;
        for (int i = 0; i < n; i++) {
            b[j - 1] = a[i];
            j = j - 1;
        }
        return b;
    }

        private int[] bubbleSort(int[] array){
            int n = array.length;
            for (int i = 0; i < n-1; i++){
                for (int j = 0; j < n-i-1; j++){
                    if (array[j] > array[j+1]){
                        int temp = array[j];
                        array[j] = array[j+1];
                        array[j+1] = temp;
                    }
                }
            }
            return array;
        }

        private int[] gnomeSort(int[] array, int n){
            int index = 0;
            while (index < n) {
                if (index == 0)
                    index++;
                if (array[index] >= array[index - 1])
                    index++;
                else {
                    int temp = 0;
                    temp = array[index];
                    array[index] = array[index - 1];
                    array[index - 1] = temp;
                    index--;
                }
            }
            return array;
        }

    }
}