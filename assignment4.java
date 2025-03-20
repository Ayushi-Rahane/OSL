/*
*Assignment no: 04
* Title: Simulation of memory allocation strategies. For example: First Fit,
Best Fit and Worst Fit.
Objective: Implementation of memory allocation strategies.
Name: Ayushi Rahane
UCE: UCE2024014
Batch: B4
**/
import java.util.*;
class memory{
	int id, sie,allocated=0;
}
class process{
	int id, sie,allocated=0;
}
public class assignment4 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		try {
			int mmblocks;
			//This do while loop ensure that the number of memory blocks should be greater than 0  - VALIDATION 1
            do {
                System.out.println("Enter the number of memory blocks: ");
                mmblocks = sc.nextInt();
                sc.nextLine();
                
                if (mmblocks <= 0) {
                    System.out.println("Error: The number of memory blocks must be greater than 0. Please re-enter.");
                }
            } while (mmblocks <= 0);
		//This section will accept no. of memory blocks and assign the size to the array of memory obj.
		memory mm[] = new memory[mmblocks];

		HashSet<Integer> memoryIds = new HashSet<>(); // It will store the unique memory block ids


		//for loop will accept id and size of each memory block
		for(int i=0;i<mmblocks;i++) {
			mm[i] = new memory();

			//This do while loop ensure that the memory block id should be unique - VALIDATION 2
			do {
				System.out.println("Enter the ID of memory block: ");
				mm[i].id = sc.nextInt();
				sc.nextLine();

				if (memoryIds.contains(mm[i].id)) {
					System.out.println("Error: Memory block ID must be unique. Please re-enter.");
				}
			} while (memoryIds.contains(mm[i].id));
             
			memoryIds.add(mm[i].id); // Add the memory block id to the Hashset
            
			//This do while loop ensure that the size of memory block should be greater than 0 - VALIDATION 3
			do {
				System.out.println("Enter the size of memory block: ");
				mm[i].sie = sc.nextInt();
				sc.nextLine();

				if (mm[i].sie <= 0) {
					System.out.println("Error: Memory block size must be greater than 0. Please re-enter.");
				}
			} while (mm[i].sie <= 0);
		}
		//This section will accept no. of processes ad assign the size to the array of process obj. 
		int pno;
		//This do while loop ensure that the number of processes should be greater than 0 - VALIDATION 4
		do{
			System.out.println("Enter the no. of processes: ");
			pno = sc.nextInt();
			sc.nextLine();
			if (pno <= 0) {
				System.out.println("Error: The number of memory blocks must be greater than 0. Please re-enter.");
			}
	} while (pno <= 0);

		process pp[]= new process[pno];
		HashSet<Integer> processIds = new HashSet<>(); // It will store the unique process ids
		//for loop will accept the id and size of each process
		for(int i=0;i<pno;i++) {
			pp[i] = new process();
			 do {
                    System.out.println("Enter the process ID: ");
                    pp[i].id = sc.nextInt();
                    sc.nextLine();

                    if (processIds.contains(pp[i].id)) {
                        System.out.println("Error: Process ID must be unique. Please re-enter.");
                    }
                } while (processIds.contains(pp[i].id));

                processIds.add(pp[i].id); // Add the process id to the Hashset
           //This do while loop ensure that the size of process should be greater than 0 - VALIDATION 5
			do {
					System.out.println("Enter the size of process: ");
					pp[i].sie = sc.nextInt();
					sc.nextLine();

					if (pp[i].sie <= 0) {
						System.out.println("Error: Process size must be greater than 0. Please re-enter.");
					}
				} while (pp[i].sie <= 0);
		}
		int c;
	do {
		System.out.println("Enter 1. to first fit\n 2. to best fit\n 3. to worst fit:  ");
		c = sc.nextInt();
		switch(c) {
		case 1:
			//First Fit Algo
			
			//Allocating the memory blocks to processes
			for(int i=0;i<pno;i++) {
				for(int j=0;j<mmblocks;j++) {
					//It will check whether the size of process is lesser than or equal to the size of memory block and check whether the process and memory block is allocated or not.
					if(pp[i].sie<=mm[j].sie && pp[i].allocated!=1 && mm[j].allocated!=1) {
						System.out.println("Process ID: "+pp[i].id+" of sie "+pp[i].sie+" is allocatedd to memory block id: "+mm[j].id+" of sie "+mm[j].sie);
						//mark as allocated
						pp[i].allocated=1; 
						mm[j].allocated=1;
						break;
					}
				}
			}
			//this for loop will check will remaining processes are not allocation
			for(int i=0;i<pno;i++) {
				if(pp[i].allocated!=1) {
					System.out.println("Process ID: "+pp[i].id+" is not allocated to any memory block.");
				}	
			}
			//Resetting the status
			for(int i=0;i<pno;i++) {
				pp[i].allocated=0;
			}
			for(int i=0;i<mmblocks;i++) {
				mm[i].allocated=0;
			}
			break;
		case 2:
			//Best Fit
			
			//sorting memory block in acsending order
			for(int i=0;i<mmblocks-1;i++) {
				for(int j=0;j<mmblocks-1-i;j++) {
					if(mm[j].sie>mm[j+1].sie) {
						memory temp = mm[j];
						mm[j]=mm[j+1];
						mm[j+1]=temp;
					}
				}
			}
			//Allocating the memory blocks to processes
			for(int i=0;i<pno;i++) {
				for(int j=0;j<mmblocks;j++) {
					//It will check whether the size of process is lesser than or equal to the size of memory block and check whether the process and memory block is allocated or not.
					if(pp[i].allocated!=1 && mm[j].allocated!=1 && pp[i].sie<=mm[j].sie) {
						System.out.println("Process ID: "+pp[i].id+" of sie "+pp[i].sie+" is allocatedd to memory block id: "+mm[j].id+" of sie "+mm[j].sie);
						//mark as allocated
						pp[i].allocated=1;
						mm[j].allocated=1;
						break;
					}
				}
			}
			//this for loop will check will remaining processes are not allocation
			for(int i=0;i<pno;i++) {
				if(pp[i].allocated!=1) {
					System.out.println("Process ID: "+pp[i].id+" is not allocated to any memory block.");
				}	
			}
			//Resetting the status
			for(int i=0;i<pno;i++) {
				pp[i].allocated=0;
			}
			for(int i=0;i<mmblocks;i++) {
				mm[i].allocated=0;
			}
			break;
		case 3:
			//Worst Fit
			
			//sorting memory block in decending order so that the memory block with large size will be stored at first
			//sorting is done using bubble sort
			for(int i=0;i<mmblocks-1;i++) {
				for(int j=0;j<mmblocks-1-i;j++) {
					if(mm[j].sie<mm[j+1].sie) {
						memory temp = mm[j];
						mm[j]=mm[j+1];
						mm[j+1]=temp;
					}
				}
			}
			//In this nested loop processes and memory blocks will get allocated 
			  for(int i=0;i<pno;i++) {
				  for(int j=0;j<mmblocks;j++) {
					//It will check whether the size of process is lesser than or equal to the size of memory block and check whether the process and memory block is allocated or not.
					  if(pp[i].allocated!=1 && mm[j].allocated!=1 && pp[i].sie<mm[j].sie) {
						  System.out.println("Process ID: "+pp[i].id+" of sie "+pp[i].sie+" is allocatedd to memory block id: "+mm[j].id+" of sie "+mm[j].sie);
						//mark as allocated
							pp[i].allocated=1;
							mm[j].allocated=1;
							break;
					  }
				  }
			  }
			//this for loop will check will remaining processes are not allocation
			  for(int i=0;i<pno;i++) {
					if(pp[i].allocated!=1) {
						System.out.println("Process ID: "+pp[i].id+" is not allocated to any memory block.");
					}	
				}
				//Resetting the status
				for(int i=0;i<pno;i++) {
					pp[i].allocated=0;
				}
				for(int i=0;i<mmblocks;i++) {
					mm[i].allocated=0;
				}
			break;
			default:
			System.out.println("Invalid choice");
		}
		System.out.println("Enter 1 to continue: ");
		c = sc.nextInt();
	}while(c==1);
		}
		catch(Exception e) {
			System.out.println("Error: "+e);
			System.out.println("Kindly run program again!\n Thank You");
		}
}
	}
/*
Output:
Enter the no. of memory blocks:
5
Enter the id of memory block:
1
Enter the sie of memory block:
100
Enter the id of memory block:
2
Enter the sie of memory block:
500
Enter the id of memory block:
3
Enter the sie of memory block:
200
Enter the id of memory block:
4
Enter the sie of memory block:
300
Enter the id of memory block:
5
Enter the sie of memory block:
600
Enter the no. of processes:
4
Enter the process id:
1
Enter the sie of process:
212
Enter the process id:
2
Enter the sie of process:
417
Enter the process id:
3
Enter the sie of process:
112
Enter the process id:
4
Enter the sie of process:
426
Enter 1. to first fit
2. to best fit
3. to worst fit: 
1
Process ID: 1 of sie 212 is allocatedd to memory block id: 2 of sie 500
Process ID: 2 of sie 417 is allocatedd to memory block id: 5 of sie 600
Process ID: 3 of sie 112 is allocatedd to memory block id: 3 of sie 200
Process ID: 4 is not allocated to any memory block.
Enter 1 to continue:
1
Enter 1. to first fit
2. to best fit
3. to worst fit: 
2
Process ID: 1 of sie 212 is allocatedd to memory block id: 4 of sie 300
Process ID: 2 of sie 417 is allocatedd to memory block id: 2 of sie 500
Process ID: 3 of sie 112 is allocatedd to memory block id: 3 of sie 200
Process ID: 4 of sie 426 is allocatedd to memory block id: 5 of sie 600
Enter 1 to continue:
1
Enter 1. to first fit
2. to best fit
3. to worst fit: 
3
Process ID: 1 of sie 212 is allocatedd to memory block id: 5 of sie 600
Process ID: 2 of sie 417 is allocatedd to memory block id: 2 of sie 500
Process ID: 3 of sie 112 is allocatedd to memory block id: 4 of sie 300
Process ID: 4 is not allocated to any memory block.
Enter 1 to continue:
0
*/