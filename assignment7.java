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

public class assignment7 {
    static Scanner sc = new Scanner(System.in);

    static int n, r1, r2, main_head;
    static int[] req;

    // Common accept() method
    static void accept() {
        // Validation 1: check if the number of requests is positive
        System.out.println("Enter the number of requests: ");
        while (true) {
            n = sc.nextInt();
            if (n <= 0) {
                System.out.println("Number of requests should be positive. Please enter again: ");
            } else {
                break;
            }
        }

        // Accept and validate disk range
        System.out.println("Enter the Min Range: ");
        r1 = sc.nextInt();
        System.out.println("Enter the Max Range: ");
        r2 = sc.nextInt();

        req = new int[n];

        // Validation 2: check if the requests are within the valid range
        for (int i = 0; i < n; i++) {
            System.out.println("Enter the request " + (i + 1) + ": ");
            req[i] = sc.nextInt();
            while (req[i] < r1 || req[i] > r2) {
                System.out.println("Request should be between " + r1 + " and " + r2 + ". Please enter again: ");
                req[i] = sc.nextInt();
            }
        }

        // Validation 3: check if the head position is within the valid range
        System.out.println("Enter the head position: ");
        main_head = sc.nextInt();
        while (main_head < r1 || main_head > r2) {
            System.out.println("Head position should be between " + r1 + " and " + r2 + ". Please enter again: ");
            main_head = sc.nextInt();
        }
    }

    public static void fcfs() {
        int head = main_head;
        int seek = 0;
        System.out.println("\nOrder of execution and seek movements:");
        for (int i = 0; i < n; i++) {
            int distance = Math.abs(req[i] - head);
            System.out.println("Head moves from " + head + " to " + req[i] + " -> Seek = " + distance);
            seek += distance;
            head = req[i];
        }

        System.out.println("\nTotal seek time: " + seek);
        System.out.println("Average seek time: " + (float) seek / n);
    }

    public static void sstf() {
        int head = main_head;
        boolean[] visited = new boolean[n]; // to mark visited requests
        int totalSeek = 0;

        System.out.println("\nOrder of execution and seek movements:");

        for (int i = 0; i < n; i++) {
            int minDiff = Integer.MAX_VALUE;
            int index = -1;

            // Find the request with minimum seek time
            for (int j = 0; j < n; j++) {
                if (!visited[j]) {
                    int diff = Math.abs(req[j] - head);
                    if (diff < minDiff) {
                        minDiff = diff;
                        index = j;
                    }
                }
            }

            // Serve the closest request
            visited[index] = true;
            System.out.println("Head moves from " + head + " to " + req[index] + " -> Seek = " + minDiff);
            totalSeek += minDiff;
            head = req[index];
        }

        System.out.println("\nTotal seek time: " + totalSeek);
        System.out.println("Average seek time: " + (float) totalSeek / n);
    
    }

    public static void scan() {
        int head = main_head;
        System.out.println("Enter direction (left or right): ");
        String direction = sc.next().toLowerCase();
        while (!direction.equals("left") && !direction.equals("right")) {
            System.out.println("Please enter either 'left' or 'right': ");
            direction = sc.next().toLowerCase();
        }

        ArrayList<Integer> requestList = new ArrayList<>();
        for (int r : req) requestList.add(r);
        requestList.add(head);
        Collections.sort(requestList);

        int index = requestList.indexOf(head), totalSeek = 0, current = head;

        System.out.println("\nOrder of execution and seek movements:");

        if (direction.equals("left")) {
            for (int i = index - 1; i >= 0; i--) {
                int seek = Math.abs(current - requestList.get(i));
                System.out.println("Head moves from " + current + " to " + requestList.get(i) + " -> Seek = " + seek);
                totalSeek += seek;
                current = requestList.get(i);
            }
            if (current != r1) totalSeek += Math.abs(current - r1);
            current = r1;
            for (int i = index + 1; i < requestList.size(); i++) {
                int seek = Math.abs(current - requestList.get(i));
                System.out.println("Head moves from " + current + " to " + requestList.get(i) + " -> Seek = " + seek);
                totalSeek += seek;
                current = requestList.get(i);
            }
        } else {
            for (int i = index + 1; i < requestList.size(); i++) {
                int seek = Math.abs(current - requestList.get(i));
                System.out.println("Head moves from " + current + " to " + requestList.get(i) + " -> Seek = " + seek);
                totalSeek += seek;
                current = requestList.get(i);
            }
            if (current != r2) totalSeek += Math.abs(current - r2);
            current = r2;
            for (int i = index - 1; i >= 0; i--) {
                int seek = Math.abs(current - requestList.get(i));
                System.out.println("Head moves from " + current + " to " + requestList.get(i) + " -> Seek = " + seek);
                totalSeek += seek;
                current = requestList.get(i);
            }
        }
        System.out.println("\nTotal seek time: " + totalSeek);
    }

    public static void cscan() {
        int head = main_head;
        ArrayList<Integer> requestList = new ArrayList<>();
        for (int r : req) requestList.add(r);
        requestList.add(head);
        Collections.sort(requestList);

        int index = requestList.indexOf(head), totalSeek = 0, current = head;

        System.out.println("\nOrder of execution and seek movements:");

        for (int i = index + 1; i < requestList.size(); i++) {
            int seek = Math.abs(current - requestList.get(i));
            System.out.println("Head moves from " + current + " to " + requestList.get(i) + " -> Seek = " + seek);
            totalSeek += seek;
            current = requestList.get(i);
        }

        if (current != r2) totalSeek += Math.abs(current - r2);
        System.out.println("Head moves from " + r2 + " to " + r1 + " (circular jump) -> Seek = " + (r2 - r1));
        totalSeek += (r2 - r1);
        current = r1;

        for (int i = 0; i < index; i++) {
            int seek = Math.abs(current - requestList.get(i));
            System.out.println("Head moves from " + current + " to " + requestList.get(i) + " -> Seek = " + seek);
            totalSeek += seek;
            current = requestList.get(i);
        }

        System.out.println("\nTotal seek time: " + totalSeek);
    }

    public static void main(String[] args) {
        int c;
        accept();
        do {
            System.out.println("\nEnter\n1. FCFS\n2. SSTF\n3. SCAN\n4. C-SCAN\n5. Exit");
            c = sc.nextInt();
            switch (c) {
                case 1:
                    fcfs();
                    break;
                case 2:
                    sstf();
                    break;
                case 3:
                    scan();
                    break;
                case 4:
                    cscan();
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (c != 7);
    }
}


/*
Output:
Enter the number of requests: 
7
Enter the Min Range: 
0
Enter the Max Range: 
199
Enter the request 1: 
82
Enter the request 2: 
170
Enter the request 3: 
43
Enter the request 4: 
140
Enter the request 5: 
24
Enter the request 6: 
16
Enter the request 7: 
190
Enter the head position: 
50

Enter
1. FCFS
2. SSTF
3. SCAN
4. C-SCAN
7. Exit
1

Order of execution and seek movements:
Head moves from 50 to 82 -> Seek = 32
Head moves from 82 to 170 -> Seek = 88
Head moves from 170 to 43 -> Seek = 127
Head moves from 43 to 140 -> Seek = 97
Head moves from 140 to 24 -> Seek = 116
Head moves from 24 to 16 -> Seek = 8
Head moves from 16 to 190 -> Seek = 174

Total seek time: 642
Average seek time: 91.71429

Enter
1. FCFS
2. SSTF
3. SCAN
4. C-SCAN
7. Exit
2

Order of execution and seek movements:
Head moves from 50 to 43 -> Seek = 7
Head moves from 43 to 24 -> Seek = 19
Head moves from 24 to 16 -> Seek = 8
Head moves from 16 to 82 -> Seek = 66
Head moves from 82 to 140 -> Seek = 58
Head moves from 140 to 170 -> Seek = 30
Head moves from 170 to 190 -> Seek = 20

Total seek time: 208
Average seek time: 29.714285

Enter
1. FCFS
2. SSTF
3. SCAN
4. C-SCAN
7. Exit
3
Enter direction (left or right): 
right

Order of execution and seek movements:
Head moves from 50 to 82 -> Seek = 32
Head moves from 82 to 140 -> Seek = 58
Head moves from 140 to 170 -> Seek = 30
Head moves from 170 to 190 -> Seek = 20
Head moves from 199 to 43 -> Seek = 156
Head moves from 43 to 24 -> Seek = 19
Head moves from 24 to 16 -> Seek = 8

Total seek time: 332

Enter
1. FCFS
2. SSTF
3. SCAN
4. C-SCAN
7. Exit
4

Order of execution and seek movements:
Head moves from 50 to 82 -> Seek = 32
Head moves from 82 to 140 -> Seek = 58
Head moves from 140 to 170 -> Seek = 30
Head moves from 170 to 190 -> Seek = 20
Head moves from 199 to 0 (circular jump) -> Seek = 199
Head moves from 0 to 16 -> Seek = 16
Head moves from 16 to 24 -> Seek = 8
Head moves from 24 to 43 -> Seek = 19

Total seek time: 391
 */