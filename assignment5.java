import java.util.*;
public class assignment5 {
    public static Scanner sc = new Scanner(System.in);
    public static int[] accept(){
        System.out.println("Enter the length of Reference Page String: ");
        int n = sc.nextInt();
        sc.nextLine();
        int[] rs = new int[n];
        for(int i=0;i<n;i++) {
            System.out.println("Enter element "+(i+1)+" no: ");
            rs[i]=sc.nextInt();
        }
        return rs;

    }
    public static void fifo(){
        System.out.println("FIFO Page Replacement Algorithm");       
        int[] rs = accept();
        int n = rs.length;
        int c;
        do {
           
        System.out.println("Enter the Frame size: ");
        int fsize = sc.nextInt();
        sc.nextLine();
       
        int[] frame =  new int[fsize]; //Frame is created
        //front pointer will replace the page from the frame
        //rear pointer will insert the page if frame have empty space
        int page_fault=0, front=0,rear=-1;
        Arrays.fill(frame, -1); // all blocks of frame will be denoted as -1;
       
        for(int k=0; k<fsize; k++) {
         System.out.print("F" + (k+1) + "\t");
     }
     System.out.println("Page Fault");
        //Page insertion and replacement is done in this
        for(int i=0;i<n;i++) {
           
            int current_page = rs[i]; //current page is stored in current_page
            boolean found=false; // it will keep track whether the current page is present in frame or not
           
            //searches for current page in frame
            for(int j=0;j<fsize;j++) {
                if(frame[j]==current_page) {
                    found=true;
                    break;
                }
            }
           
            //if current page not found in frame then insertion of current page will take place in this section
            if(found==false) {
                System.out.println();
                if(rear<fsize-1) {
                    //if frame has empty space then page is inserted using rear pointer
                    rear++;
                    frame[rear]=current_page;
                }
                else {
                    //if the frame is not empty then the first page is replaced with the current page using front pointer
                    frame[front]=current_page;
                    front=(front+1)%fsize;
                }
                //as the insertion process is done so the page fault is occured to it is incremented
                page_fault++;
            }
           
            //Displaying the current state of frames
           
         
            // Display frames only (No extra row)
     for(int j = 0; j < fsize; j++) {
         if(frame[j] == -1) {
             System.out.print("-\t");  
         } else {
             System.out.print(frame[j] + "\t");
         }
     }
     System.out.println(found ? "*" : "F"); // Displays F for page Fault and * for no fault.
         
           
        }
        System.out.println("Total Page Fault: "+page_fault);
        System.out.println("Enter 1 to try for another frame size: ");
        c = sc.nextInt();
        }while(c==1);
    }

    public static void lru(){
        System.out.println("LRU Page Replacement Algorithm");
        
        
        int[] rs1 = accept();
        int n = rs1.length;
        int c;
do{
        System.out.println("Enter the Frame size: ");
        int fsize = sc.nextInt();
        sc.nextLine();
        
        // Step 2: Initialize variables
        int[] frame = new int[fsize];  // Frame array
        int[] index = new int[fsize];  // Stores index of last use. It will keep track of which page is used last
        Arrays.fill(frame, -1);  // Initialize frame as empty
        int pageFaults = 0;

        // Print header
        System.out.println("\nF1\tF2\tF3\tPage Fault");

        // Step 3-7: Process each page
        for (int i = 0; i < n; i++) {
            int currentPage = rs1[i];
            boolean found = false;

            // Check if page is already in frame
            for (int j = 0; j < fsize; j++) {
                if (frame[j] == currentPage) {
                    found = true;
                    index[j] = i; // Update last used index
                    break;
                }
            }

            if (!found) {
                int replaceIndex = -1;
                
                // Find an empty slot in the frame
                for (int j = 0; j < fsize; j++) {
                    if (frame[j] == -1) {
                        replaceIndex = j;
                        break;
                    }
                }

                // If no empty slot, find the Least Recently Used (LRU) page
                if (replaceIndex == -1) {
                    int minIndex = Integer.MAX_VALUE;
                    for (int j = 0; j < fsize; j++) {
                        if (index[j] < minIndex) {
                            minIndex = index[j];
                            replaceIndex = j;
                        }
                    }
                }

                // Replace the page
                frame[replaceIndex] = currentPage;
                index[replaceIndex] = i;
                pageFaults++;
            }

            // Display frame contents
            for (int j = 0; j < fsize; j++) {
                if (frame[j] == -1) {
                    System.out.print("-\t");
                } else {
                    System.out.print(frame[j] + "\t");
                }
            }
            System.out.println(found ? "No" : "Yes");
        }

        System.out.println("Total Page Faults: " + pageFaults);
        sc.close();
        System.out.println("Enter 1 to try for another frame size: ");
        c = sc.nextInt();
        }while(c==1);
    }

    public static void optimal()
    {
        System.out.println("Optimal Page Replacement Algorithm");
        int c;
        int[] rs3 = accept();
        int n = rs3.length;
        do{
        // Input: Frame size
        System.out.println("Enter the Frame size: ");
        int fsize = sc.nextInt();
        
        int[] frame1 = new int[fsize];  // Frame array
        Arrays.fill(frame1, -1);  // Initialize frame as empty
        int pageFault = 0;

        // Print table header
        System.out.println("\nF1\tF2\tF3\tPage Fault");

        // Process reference string
        for (int i = 0; i < n; i++) {
            int currentPage = rs3[i];
            boolean found = false;

            // Check if page is already in frame
            for (int j = 0; j < fsize; j++) {
                if (frame1[j] == currentPage) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                int replaceIndex = -1;

                // Check if there's an empty slot in the frame
                for (int j = 0; j < fsize; j++) {
                    if (frame1[j] == -1) {
                        replaceIndex = j;  //if the frame block is empty it will initialize it's index no. to replaceIndex, with the help of it the new page will be inserted at that empty location
                        break;
                    }
                }

                // If no empty slot, find the optimal page to replace
                if (replaceIndex == -1) {
                    int far_element = -1; //It keeps the track of page which is far

                    for (int j = 0; j < fsize; j++) {
                        int nextUse = Integer.MAX_VALUE;
                        for (int k = i + 1; k < n; k++) { // it will iterate the next pages present in ref string
                            if (frame1[j] == rs3[k]) { 
                                nextUse = k;  
                                break;
                            }
                        }
                        if (nextUse > far_element) { //If the next use element comes after the far-element then it is initialize to far_element and it's frame index no. will be store in replaceIndex
                            far_element = nextUse;
                            replaceIndex = j;
                        }
                    }
                }

                // Replace the page
                frame1[replaceIndex] = currentPage;
                pageFault++;
            }

            // Display frame contents
            for (int j = 0; j < fsize; j++) {
                if (frame1[j] == -1) {
                    System.out.print("-\t"); //If the frame is empty it will print -
                } else {
                    System.out.print(frame1[j] + "\t");
                }
            }
            System.out.println(found ? "No" : "Yes");
        }

        // Display total page faults
        System.out.println("Total Page Faults: " + pageFault);
        sc.close();
        System.out.println("Enter 1 to try for another frame size: ");
         c = sc.nextInt();

    }while(c==1);

    }
  public static void main(String[] args) {
	   Scanner sc = new Scanner(System.in);
	 System.out.println("Enter 1. to FIFO \n 2. LRU \n 3. Optimal:  ");
     int ch = sc.nextInt();
        switch(ch){
            case 1:
               fifo();
                break;
            case 2:
                lru();
                break;
         case 3:
                optimal();
                break;
            default:
                System.out.println("Invalid choice");
        }


  }
}

