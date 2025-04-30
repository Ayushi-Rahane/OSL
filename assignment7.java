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

    static int n, r1, r2, head;
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
        head = sc.nextInt();
        while (head < r1 || head > r2) {
            System.out.println("Head position should be between " + r1 + " and " + r2 + ". Please enter again: ");
            head = sc.nextInt();
        }
    }

    public static void fcfs() {
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
        int[] localReq = Arrays.copyOf(req, n);
        boolean[] visited = new boolean[n];
        int localHead = head, totalSeek = 0;

        System.out.println("\nOrder of execution and seek movements:");

        for (int i = 0; i < n; i++) {
            int minDiff = Integer.MAX_VALUE, index = -1;

            for (int j = 0; j < n; j++) {
                if (!visited[j]) {
                    int diff = Math.abs(localReq[j] - localHead);
                    if (diff < minDiff) {
                        minDiff = diff;
                        index = j;
                    }
                }
            }

            visited[index] = true;
            System.out.println("Head moves from " + localHead + " to " + localReq[index] + " -> Seek = " + minDiff);
            totalSeek += minDiff;
            localHead = localReq[index];
        }

        System.out.println("\nTotal seek time: " + totalSeek);
        System.out.println("Average seek time: " + (float) totalSeek / n);
    }

    public static void scan() {
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
            System.out.println("\nEnter\n1. FCFS\n2. SSTF\n3. SCAN\n4. C-SCAN\n7. Exit");
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
                case 7:
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (c != 7);
    }
}
