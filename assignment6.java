/*
*Assignment no: 06
Title : Write a program to implement Banker’s Algorithm
Objective :To implement the Banker’s Algorithm to determine the safe state of a
system during resource allocation.
Name: Ayushi Rahane
UCE: UCE2024014
Batch: B4
**/

import java.util.*;

public class assignment6 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Banker's Algorithm");
        //Validation 1: Check if the number of processes and resources are positive integers

        System.out.print("Enter the number of processes (positive integer): ");
        while (!sc.hasNextInt()) {
            System.out.print("Invalid input. Please enter a positive integer for the number of processes: ");
            sc.next(); // Clear the invalid input
        }
        int n = sc.nextInt();
        while (n <= 0) {
            System.out.print("Invalid input. Please enter a positive integer for the number of processes: ");
            n = sc.nextInt();
        }
        System.out.print("Enter the number of resources (positive integer): "); 

        while (!sc.hasNextInt()) {
            System.out.print("Invalid input. Please enter a positive integer for the number of resources: ");
            sc.next(); // Clear the invalid input
        }
        int res = sc.nextInt();
        while (res <= 0) {
            System.out.print("Invalid input. Please enter a positive integer for the number of resources: ");
            res = sc.nextInt();
        }
        int[][] max = new int[n][res]; //max[i][j] = maximum resources of process i of resource j
        int[][] allot = new int[n][res]; //allot[i][j] = allocated resources of process i of resource j
        int[][] need = new int[n][res]; //need[i][j] = need resources of process i of resource j
        int[] avail = new int[res]; //avail[j] = available resources of resource j
        //Validation 2: Check if the maximum and allocated resources are non-negative integers
        System.out.println("----- Enter the maximum resources ----- ");
        for (int i = 0; i < n; i++) {
            System.out.println("-------------------------Process "+(i+1)+"-------------------------");
            for (int j = 0; j < res; j++) {
                System.out.print("Enter the maximum resources for process " + (i+1) + " of resource " + (j+1) + ": ");
                while (!sc.hasNextInt()) {
                    System.out.print("Invalid input. Please enter a non-negative integer: ");
                    sc.next(); // Clear the invalid input
                }
                max[i][j] = sc.nextInt();
                while (max[i][j] < 0) {
                    System.out.print("Invalid input. Please enter a non-negative integer: ");
                    max[i][j] = sc.nextInt(); 
                }
            }
        }
        System.out.println("----- Enter the allocated resources ----- ");
        for (int i = 0; i < n; i++) {
            System.out.println("-------------------------Process "+(i+1)+"-------------------------");
            for (int j = 0; j < res; j++) {
                System.out.print("Enter the allocated resources for process " + (i+1) + " of resource " + (j+1) + ": ");
                while (!sc.hasNextInt()) {
                    System.out.print("Invalid input. Please enter a non-negative integer: ");
                    sc.next(); // Clear the invalid input
                }
                allot[i][j] = sc.nextInt();
                while (allot[i][j] < 0) {
                    System.out.print("Invalid input. Please enter a non-negative integer: ");
                    allot[i][j] = sc.nextInt();
                }
            }
        }
        System.out.println("----- Enter the available resources ----- ");
        for (int i = 0; i < res; i++) {
            System.out.print("Enter the available resources of resource " + (i+1) + ": ");
            while (!sc.hasNextInt()) {
                System.out.print("Invalid input. Please enter a non-negative integer: ");
                sc.next(); // Clear the invalid input
            }
            avail[i] = sc.nextInt();
            while (avail[i] < 0) {
                System.out.print("Invalid input. Please enter a non-negative integer: ");
                avail[i] = sc.nextInt();
            }
        }
        //Validation 3: Check if the allocated resources are less than or equal to the maximum resources
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < res; j++) {
                if (allot[i][j] > max[i][j]) {
                    System.out.println("Error: Allocated resources cannot exceed maximum resources.");
                    return;
                }
            }
        }
     
        //Validation 4: Check if the need matrix is valid (non-negative and less than or equal to max matrix)

        System.out.println("----- Calculating Need Matrix ----- ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < res; j++) {
                need[i][j] = max[i][j] - allot[i][j];
                if (need[i][j] < 0) {
                    System.out.println("Error: Need matrix cannot be negative.");
                    return;
                }
            }
        }
        
        //Validation 5: Check if the need matrix is less than or equal to the maximum resources
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < res; j++) {
                if (need[i][j] > max[i][j]) {
                    System.out.println("Error: Need matrix cannot exceed maximum resources.");
                    return;
                }
            }
        }
        //Validation 6: Check if the available resources are non-negative integers
        for (int i = 0; i < res; i++) {
            if (avail[i] < 0) {
                System.out.println("Error: Available resources cannot be negative.");
                return;
            }
        }
     //Display the matrices 
        System.out.println("\nProcess\tAllocation\tMax\t\tNeed");
        for (int i = 0; i < n; i++) {
            System.out.print("P" + (i+1) + "\t");
            // Print the allocation matrix
            for (int j = 0; j < res; j++) {
                System.out.print(allot[i][j] + " ");
            }
            // Print the max matrix
            System.out.print("\t\t");
            for (int j = 0; j < res; j++) {
                System.out.print(max[i][j] + " ");
            }
            // Print the need matrix
            System.out.print("\t\t");
            for (int j = 0; j < res; j++) {
                System.out.print(need[i][j] + " ");
            }
            System.out.println();

        }

        boolean[] finish = new boolean[n]; //finish[i] = true if process i has finished executing
        int[] safeSeq = new int[n]; //it will store the safe sequence
        int count = 0; //count of processes in safe sequence
        //Finding the safe sequence
        while (count < n) {
            boolean found = false;
            //Find a process which is not finished and whose needs can be satisfied with current available resources
            for (int p = 0; p < n; p++) {
                //Check if the process is not finished and its needs can be satisfied with available resources
                if (!finish[p]) {
                    int j;
                    //Check if the need of process p can be satisfied with available resources
                    for (j = 0; j < res; j++) {
                        if (need[p][j] > avail[j]) {
                            break; //need of process p cannot be satisfied with available resources
                        }
                    }
                    //If all needs of process p can be satisfied with available resources
                    if (j == res) {
                        //Add the allocated resources of process p to available resources
                        for (int k = 0; k < res; k++) {
                            avail[k] += allot[p][k];
                        }
                        safeSeq[count++] = p; //increment count of processes in safe sequence
                        //Mark process p as finished
                        finish[p] = true;
                        found = true; //break; //break the loop to find the next process
                    }
                }
            }
            //If no process was found in the last iteration, then the system is not in a safe state
            if (!found) {
                System.out.println("System is not in a safe state.");
                return;
            }

        }
        //If the system is in a safe state, print the safe sequence
        System.out.println("System is in a safe state.\nSafe sequence is: ");
        for (int i = 0; i < n; i++) {
            //Print the safe sequence
            System.out.print("P" + safeSeq[i] + " ");
        }
    }
}



/*
Output:

Banker's Algorithm
Enter the number of processes (positive integer): 5
Enter the number of resources (positive integer): 3
----- Enter the maximum resources ----- 
-------------------------Process 1-------------------------
Enter the maximum resources for process 1 of resource 1: 7
Enter the maximum resources for process 1 of resource 2: 5
Enter the maximum resources for process 1 of resource 3: 3
-------------------------Process 2-------------------------
Enter the maximum resources for process 2 of resource 1: 3
Enter the maximum resources for process 2 of resource 2: 2
Enter the maximum resources for process 2 of resource 3: 2
-------------------------Process 3-------------------------
Enter the maximum resources for process 3 of resource 1: 9
Enter the maximum resources for process 3 of resource 2: 0
Enter the maximum resources for process 3 of resource 3: 2
-------------------------Process 4-------------------------
Enter the maximum resources for process 4 of resource 1: 2
Enter the maximum resources for process 4 of resource 2: 2
Enter the maximum resources for process 4 of resource 3: 2
-------------------------Process 5-------------------------
Enter the maximum resources for process 5 of resource 1: 4
Enter the maximum resources for process 5 of resource 2: 3
Enter the maximum resources for process 5 of resource 3: 3
----- Enter the allocated resources ----- 
-------------------------Process 1-------------------------
Enter the allocated resources for process 1 of resource 1: 0
Enter the allocated resources for process 1 of resource 2: 1
Enter the allocated resources for process 1 of resource 3: 0
-------------------------Process 2-------------------------
Enter the allocated resources for process 2 of resource 1: 2
Enter the allocated resources for process 2 of resource 2: 0
Enter the allocated resources for process 2 of resource 3: 0
-------------------------Process 3-------------------------
Enter the allocated resources for process 3 of resource 1: 3
Enter the allocated resources for process 3 of resource 2: 0
Enter the allocated resources for process 3 of resource 3: 2
-------------------------Process 4-------------------------
Enter the allocated resources for process 4 of resource 1: 2
Enter the allocated resources for process 4 of resource 2: 1
Enter the allocated resources for process 4 of resource 3: 1
-------------------------Process 5-------------------------
Enter the allocated resources for process 5 of resource 1: 0
Enter the allocated resources for process 5 of resource 2: 0
Enter the allocated resources for process 5 of resource 3: 2
----- Enter the available resources ----- 
Enter the available resources of resource 1: 3
Enter the available resources of resource 2: 3
Enter the available resources of resource 3: 2
----- Calculating Need Matrix ----- 

Process Allocation      Max             Need
P1      0 1 0           7 5 3           7 4 3
P2      2 0 0           3 2 2           1 2 2
P3      3 0 2           9 0 2           6 0 0
P4      2 1 1           2 2 2           0 1 1
P5      0 0 2           4 3 3           4 3 1
System is in a safe state.
Safe sequence is:
P1 P3 P4 P0 P2
 */