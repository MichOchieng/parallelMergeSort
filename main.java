import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class main {

    private static boolean useFile;
    private static boolean useArr;    
    private static ArrayList<Integer> temp = new ArrayList<>();

    public static void main(String[] args) {
        /**
         * Allows user to chose between input file or random array
         * Allows user to chose how many threads will be used in sorting
         * Checks to see if the user is using a reasonable number of threads
         */
        int arr[] = new int[1000000];
        Scanner sc = new Scanner(System.in); 
        System.out.println("Would you like to use an input file or generate and array? Enter 1 for File or 0 for Array");
        int inputMethod = sc.nextInt();  
        inputMethod(inputMethod);
        System.out.println("Enter how many threads you would like to use (between 1-7)");
        int numThreads = sc.nextInt();  
        threadCheck(numThreads);        
        if (useArr) {
            sortWithArray(arr,numThreads);
        } else if(useFile){       
            System.out.println("Enter input file name");        
            String fileName = sc.next();             
            File file = new File(fileName);    
            if (file.exists()) {
                setFileLength(file);
                int arr2[] = new int[temp.size()];
                sortWithFile(file,arr2,numThreads);  
            } else {
                System.out.println("The file '" + fileName + "' does not exist in this directory.");
            }        
            
        }
    } 

    private static void sortWithArray(int arr[],int numThreads){
      /**
        * Fills the test array with random values
        * Starts timer
        * Begins sorting
        * Stops timer
        * Prints total sort time
        */
        fillArr(arr);            
        long start = System.nanoTime();
        mergesort sort = new mergesort(arr,numThreads,0,arr.length-1);            
        sort.parallelSort(arr,0,arr.length-1);            
        // sort.printArray();                        
        long end = System.nanoTime();
        long total = (end - start)/1000000;
        System.out.println("Total sort time: " + total + " ms.");  
    }

    private static void sortWithFile(File file,int arr2[],int numThreads) {
        /**
             * Creates a file object linked to file in the directory
             * Finds how many values there are in the file
             * Creates an array and stores the file values in the array
             * Starts timer
             * Sorts the array
             * Stops timer
             */        
        System.out.println("This file has " + temp.size() + " values.");        
        fillFromFile(arr2,file);            
        long start = System.nanoTime();
        mergesort sort = new mergesort(arr2,numThreads,0,arr2.length-1);                   
        sort.parallelSort(arr2,0,arr2.length-1);    
        System.out.println("Sorted Array");       
        sort.printArray();
        long end = System.nanoTime();
        long total = (end - start)/1000000;
        System.out.println("Total sort time: " + total + " ms."); 
    }


    // Fills an array with file values
    private static void fillFromFile(int arr[],File file) {
        for (int i = 0; i < temp.size(); i++) {
            arr[i] = temp.get(i);
        }
        System.out.println("File values");
        for (int x: arr) {
            System.out.print(x + " ");
        }
        System.out.println();
    }
    // Finds how many values are in the input file
    private static void setFileLength(File file) {
        try (Scanner sc = new Scanner(file)) {
			while(sc.hasNext()){                	                
                temp.add(sc.nextInt());                
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
    }

    // Method used for filling test array
    private static void fillArr(int arr2[]){
        Random random = new Random();
        for (int i = 0; i < arr2.length; i++) {
            arr2[i] = random.nextInt(100);
        }
    }
    // Checks to see if the user is using a reasonable number of threads
    private static void threadCheck(int x){
        Scanner sc = new Scanner(System.in); 
        if(x >7 || x<1){
            System.out.println("Please enter a number between 1-7");
            x = sc.nextInt();  
            threadCheck(x);
        }
    }

    // Checks to see if the user entered either 1 or 0 for input method selection
    private static void inputMethod(int x){
        Scanner sc = new Scanner(System.in); 
        if(x != 1 & x != 0){
            System.out.println("Please enter 1 for FILE and 0 for ARRAY");
            x = sc.nextInt();  
            inputMethod(x);
        }if(x == 1){
           useFile = true;
        }
        if(x == 0){
           useArr = true;
        }
    }
    
}