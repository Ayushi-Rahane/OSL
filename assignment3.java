/*
 
 * Assignment No 3
Title: Simulation of the scheduling algorithms.
Objective: To implement non-pre-emptive CPU scheduling algorithms.
Name: Ayushi Rahane
UCN: UCE2024014
Batch: B-4

 */

import java.util.*;
class process{
    int AT,BT,CT,TAT,WT;
    String pid;
}

public class assignment3{
    static Scanner sc = new Scanner(System.in);
    static process p[];
    static int n;
    static void accept(){
          System.out.println("Enter the total no. of processes: ");
            n = sc.nextInt();
            p = new process[n];
            for(int i=0;i<n;i++){
                p[i] = new process();
                System.out.println("Enter the process id: ");
                p[i].pid = sc.next();
                System.out.println("Enter the arrival time: ");
                p[i].AT = sc.nextInt();
                System.out.println("Enter the burst time: ");
                p[i].BT = sc.nextInt();
               
            }
            
    }

   static void fcfs(){
        //sorting
	  	process temp = new process();
	  	for(int i=0;i<n-1;i++) {
	  		for(int j=0;j<n-1-i;j++) {
	  			if(p[j].AT>p[j+1].AT) {
	  				temp = p[j];
	  				p[j] = p[j+1];
	  				p[j+1]=temp;
	  			}
	  		}
	  	}


          System.out.println("P  AT BT FT TAT  WT  ");
	  	for(int i=0;i<n;i++) {
	  		if(i==0) {
	  			p[0].CT=p[0].AT+p[0].BT;		
	  		}
	  		else {
	  			p[i].CT= p[i-1].CT+p[i].BT;	
	  		}
	  		
	  		p[i].TAT=p[i].CT - p[i].AT;
		 	p[i].WT = p[i].TAT - p[i].BT;
	  		System.out.println("P"+(i+1)+" "+p[i].AT+"  "+p[i].BT+"  "+p[i].CT+"  "+p[i].TAT+"  "+p[i].WT);
	  	}

        	//Calculating AWT and ATAT
	  	int avgtat=0, avgwt=0;
	  	for(int i=0;i<n;i++) {
	  	    avgtat += p[i].TAT;
	  	    avgwt += p[i].WT;
	  	}
	  	avgtat=avgtat/n;
	  	avgwt=avgwt/n;
	  	System.out.println("Average Turn Around Time: "+avgtat);
	  	System.out.println("Average Waiting Time: "+avgwt);
	
	  	System.out.println("Gantt chart: ");
	  	for(int i=0;i<n;i++) {
	  		System.out.print("|"+p[i].pid+"|");
	  	}
	  	
    }

    static void sjf(){
           //sorting
	  	process temp = new process();
	  	for(int i=0;i<n-1;i++) {
	  		for(int j=0;j<n-1-i;j++) {
	  			if(p[j].AT>p[j+1].AT) {
	  				temp = p[j];
	  				p[j] = p[j+1];
	  				p[j+1]=temp;
	  			}
	  		}


	  	}

          int current_time = 0,  no_process_completed = 0;
          boolean[] done = new boolean[n];
           while ( no_process_completed < n) {
              int minIndex = -1;
              int minBT = Integer.MAX_VALUE;
              for (int i = 0; i < n; i++) {
                  if (!done[i] && p[i].AT <= current_time && p[i].BT < minBT) {
                      minBT = p[i].BT;
                      minIndex = i;
                  }
              }
             
                  p[minIndex].CT = current_time + p[minIndex].BT;
                  p[minIndex].TAT = p[minIndex].CT - p[minIndex].AT;
                  p[minIndex].WT = p[minIndex].TAT - p[minIndex].BT;
                  current_time = p[minIndex].CT;
                  done[minIndex] = true;
                   no_process_completed++;
              
          }
         
          // Display table
          System.out.println("P  AT  BT  CT  TAT  WT");
         int avgtat = 0;
          int avgwt = 0;
          for (int i = 0; i < n; i++) {
              System.out.println(p[i].pid + "  " + p[i].AT + "  " + p[i].BT + "  " +
                      p[i].CT + "  " + p[i].TAT + "  " + p[i].WT);
              avgtat += p[i].TAT;
              avgwt += p[i].WT;
          }
         
          System.out.println("Average Turn Around Time: " + avgtat / n);
          System.out.println("Average Waiting Time: " + avgwt / n);
         
    
    }
    static void rr(){
        System.out.println("Enter the time quantum: ");
        int tq = sc.nextInt();
         int current_time=0, no_process_completed=0;

          boolean[] done = new boolean[n];
          int[] rem_bt = new int[n];
          
          //copying the original BT into rem_bt
          for(int i=0;i<n;i++){
            rem_bt[i]=p[i].BT;
          }

          while(no_process_completed<n){
               boolean idel=true;
                for(int i=0;i<n;i++){
                    if(!done[i] && p[i].AT<=current_time){
                        idel=false;
                        if(rem_bt[i]<=tq){
                            current_time+=rem_bt[i];
                            p[i].CT=current_time;
                            p[i].TAT=p[i].CT-p[i].AT;
                            p[i].WT=p[i].TAT-p[i].BT;
                            done[i]=true;
                            no_process_completed++;
                        }
                        else{
                            current_time+=tq;
                            rem_bt[i]-=tq;
                        }
                    }
                    
                }
                if(idel){
                    current_time++;
                }

          }
        // Display table
        System.out.println("P  AT  BT  CT  TAT  WT");
        int avgtat = 0;
        int avgwt = 0;
        for (int i = 0; i < n; i++) {
            System.out.println(p[i].pid + "  " + p[i].AT + "  " + p[i].BT + "  " +
                    p[i].CT + "  " + p[i].TAT + "  " + p[i].WT);
            avgtat += p[i].TAT;
            avgwt += p[i].WT;
        }
        System.out.println("Average Turn Around Time: " + (double)avgtat / n);
        System.out.println("Average Waiting Time: " + (double)avgwt / n);
    }
    
    static void srtn(){
        //sorting
                process temp = new process();
                for(int i=0;i<n-1;i++) {
                    for(int j=0;j<n-1-i;j++) {
                        if(p[j].AT>p[j+1].AT) {
                            temp = p[j];
                            p[j] = p[j+1];
                            p[j+1]=temp;
                        }
                    }
                }

                int current_time = 0, no_process_completed = 0;
                boolean[] done = new boolean[n];
                int[] rem_bt = new int[n];
          
                //copying the original BT into rem_bt
                for(int i=0;i<n;i++){
                    rem_bt[i]=p[i].BT;
                }

                while (no_process_completed < n) {
                    int minIndex = -1;
                    int minBT = Integer.MAX_VALUE;
                    for (int i = 0; i < n; i++) {
                        if (!done[i] && p[i].AT <= current_time && rem_bt[i] < minBT) {
                            minBT = rem_bt[i];
                            minIndex = i;
                        }
                    }
                    //Process it excuted for 1 unit of time here
                    rem_bt[minIndex]--; 
                    current_time++;
                 if(rem_bt[minIndex]==0){
                        p[minIndex].CT = current_time;
                        p[minIndex].TAT = p[minIndex].CT - p[minIndex].AT;
                        p[minIndex].WT = p[minIndex].TAT - p[minIndex].BT;
                        current_time = p[minIndex].CT;
                        done[minIndex] = true;
                        no_process_completed++;
                 }
                    
                }

                // Display table
                System.out.println("P  AT  BT  CT  TAT  WT");
                int avgtat = 0;
                int avgwt = 0;
                for (int i = 0; i < n; i++) {
                    System.out.println(p[i].pid + "  " + p[i].AT + "  " + p[i].BT + "  " +
                            p[i].CT + "  " + p[i].TAT + "  " + p[i].WT);
                    avgtat += p[i].TAT;
                    avgwt += p[i].WT;
                }

                System.out.println("Average Turn Around Time: " + (double)avgtat / n);
                System.out.println("Average Waiting Time: " + (double)avgwt / n);   


    }
    
    public static void main(String[] args){
        int c;
        
       do{
        System.out.println("Enter 1. to FCFS\n 2. to SJF:\n 3. RR:\n 4. SRTN: ");
        c=sc.nextInt();
        switch(c) {
        case 1:
          accept();
          fcfs();
          break;
        
        case 2:
            accept();
            sjf();
            break;
        case 3:
           accept();
           rr();
            break;
        case 4:
           accept();
           srtn();
           break;
        }
        System.out.println("Enter 0 to continue: ");
        c = sc.nextInt();
      }while(c==0);
      
    }
}

/*
OUTPUT:
Enter 1. to FCFS
 2. to SJF:
 3. RR:
 4. SRTN:
1
Enter the total no. of processes: 
3
Enter the process id: 
p0
Enter the arrival time: 
0
Enter the burst time: 
10
Enter the process id: 
p1
Enter the arrival time: 
10
Enter the burst time: 
5
Enter the process id: 
p2
Enter the arrival time: 
15
Enter the burst time: 
8
P  AT BT FT TAT  WT  
P1 0  10  10  10  0
P2 10  5  15  5  0
P3 15  8  23  8  0
Average Turn Around Time: 7
Average Waiting Time: 0
Gantt chart:
|p0||p1||p2|Enter 0 to continue: 
0
Enter 1. to FCFS
 2. to SJF:
 3. RR:
 4. SRTN:
2
Enter the total no. of processes: 
4
Enter the process id: 
p1
Enter the arrival time: 
0
Enter the burst time: 
7
Enter the process id: 
p2
Enter the arrival time: 
2
Enter the burst time: 
4
Enter the process id: 
p3
Enter the arrival time: 
4
Enter the burst time: 
1
Enter the process id: 
p4
Enter the arrival time: 
5
Enter the burst time: 
4
P  AT  BT  CT  TAT  WT
p1  0  7  7  7  0
p2  2  4  12  10  6
p3  4  1  8  4  3
p4  5  4  16  11  7
Average Turn Around Time: 8
Average Waiting Time: 4
Enter 0 to continue:
0
Enter 1. to FCFS
 2. to SJF:
 3. RR:
 4. SRTN:
3
Enter the total no. of processes: 
5
Enter the process id: 
p1
Enter the arrival time: 
0
Enter the burst time: 
2
Enter the process id: 
p2
Enter the arrival time: 
2
Enter the burst time: 
3
Enter the process id: 
p3
Enter the arrival time: 
4
Enter the burst time: 
4
Enter the process id: 
p4
Enter the arrival time: 
6
Enter the burst time: 
5
Enter the process id: 
p5
Enter the arrival time: 
8
Enter the burst time: 
6
Enter the time quantum: 
4
P  AT  BT  CT  TAT  WT
p1  0  2  2  2  0
p2  2  3  5  3  0
p3  4  4  9  5  1
p4  6  5  18  12  7
p5  8  6  20  12  6
Average Turn Around Time: 6.8
Average Waiting Time: 2.8
Enter 0 to continue:
0
Enter 1. to FCFS
 2. to SJF:
 3. RR:
 4. SRTN:
4
Enter the total no. of processes: 
6
Enter the process id: 
p1
Enter the arrival time: 
0
Enter the burst time: 
8
Enter the process id: 
p2
Enter the arrival time: 
1
Enter the burst time: 
4
Enter the process id: 
p4
Enter the arrival time: 
3
Enter the burst time: 
1
Enter the process id: 
p3
Enter the arrival time: 
2
Enter the burst time: 
2
Enter the process id: 
p5
Enter the arrival time: 
4
Enter the burst time: 
3
Enter the process id: 
p6
Enter the arrival time: 
5
Enter the burst time: 
2
P  AT  BT  CT  TAT  WT
p1  0  8  20  20  12
p2  1  4  10  9  5
p3  2  2  4  2  0
p4  3  1  5  2  1
p5  4  3  13  9  6
p6  5  2  7  2  0
Average Turn Around Time: 7.333333333333333
Average Waiting Time: 4.0
Enter 0 to continue:
1
 */