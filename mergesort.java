/**
 * Algorithm taken from https://www.geeksforgeeks.org/merge-sort/
 */

public class mergesort extends Thread {
    private int[] arr;
    private static int maxThreads;
    private static int numThreads;
    private int start;
    private int end;

    // Initial contructor
    public mergesort(int arr[], int maxThreads, int start, int end) {
        this.arr = arr;
        this.maxThreads = maxThreads;
        this.start = start;
        this.end = end;
        if (arr.length < 15) {
            System.out.print("Array entered: ");
            printArray();
            System.out.println();
            System.out.print("Sorted  array: ");
        }
    }

    // Constructor for recursive calls
    public mergesort(int arr[], int start, int end) {
        this.arr = arr;        
        this.start = start;
        this.end = end;        
    }

    // Merges two subarrays of arr[]. 
    // First subarray is arr[l..m] 
    // Second subarray is arr[m+1..r] 
    public void merge(int arr[], int l, int m, int r){ 
        // Find sizes of two subarrays to be merged 
        int n1 = m - l + 1; 
        int n2 = r - m;  
        /* Create temp arrays */
        int L[] = new int [n1]; 
        int R[] = new int [n2];
        /*Copy data to temp arrays*/
        for (int i=0; i<n1; ++i){
            L[i] = arr[l + i]; 
        }             
        for (int j=0; j<n2; ++j){
            R[j] = arr[m + 1+ j];
        }          
        /* Merge the temp arrays */  
        // Initial indexes of first and second subarrays 
        int i = 0, j = 0;  
        // Initial index of merged subarry array 
        int k = l; 
        while (i < n1 && j < n2){ 
            if (L[i] <= R[j]){ 
                arr[k] = L[i]; 
                i++; 
            } 
            else { 
                arr[k] = R[j]; 
                j++; 
            } 
            k++; 
        } 
        /* Copy remaining elements of L[] if any */
        while (i < n1){ 
            arr[k] = L[i]; 
            i++; 
            k++; 
        }   
        /* Copy remaining elements of R[] if any */
        while (j < n2){ 
            arr[k] = R[j]; 
            j++; 
            k++; 
        } 
    } 
    // Main function that sorts arr[l..r] using 
    // merge()
    public void sort(int arr[], int l, int r){ 
        if (l < r){ 
            // Find the middle point 
            int m = (l+r)/2;  
            // Sort first and second halves 
            sort(arr, l, m); 
            sort(arr , m+1, r);  
            // Merge the sorted halves 
            merge(arr, l, m, r); 
        } 
    }  
    // Allows new threads to start sorting their portion of the array
    @Override
    public void run(){
        parallelSort(arr, start, end);
    }
    
    public void parallelSort(int arr[], int l, int r){ 
        if (l < r){ 
            // Only makes more threads if the max number hasn't been reached
            if (maxThreads == 1) {
                sort(arr, start, end);                
            }
            else if(maxThreads > numThreads){
                /**
                 * Gets the mid point of the current array so that the left and right sides of each new sub array can be sorted
                */
                numThreads++;
                int midPoint = (start + end)/2;
                mergesort left  = new mergesort(arr, start, midPoint);
                left.start();
                mergesort right = new mergesort(arr, midPoint+1, end);
                right.start();
                try {                    
                    left.join();
                    right.join();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                } 
                merge(arr, start, midPoint,end); 
            }
            else{
                sort(arr, start, end);
            }
        } 
    }  

    public void printArray(){        
        for (int i=0; i<arr.length; ++i){
            System.out.print(arr[i] + " "); 
        }      
        System.out.println();  
    }  
}