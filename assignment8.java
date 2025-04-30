/**
Assignment no: 08
 Write a program to implement Reader-Writer problem using semaphores
Name: Ayushi Rahane
UCE: UCE2024014
Batch: B4
**/


import java.util.concurrent.Semaphore;

class SharedResource {
    // Semaphore to control access to the resource
    static Semaphore mutex = new Semaphore(1);      // For readCount update
    static Semaphore wrt = new Semaphore(1);        // For writer access
    static int readCount = 0;                       // Number of active readers

    // The shared resource (could be a file, variable, DB, etc.)
    static void read(int readerID) {
        System.out.println("Reader " + readerID + " is reading.");
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        System.out.println("Reader " + readerID + " has finished reading.");
    }

    static void write(int writerID) {
        System.out.println("Writer " + writerID + " is writing.");
        try { Thread.sleep(1500); } catch (InterruptedException e) {}
        System.out.println("Writer " + writerID + " has finished writing.");
    }
}

class Reader extends Thread {
    int readerID;

    Reader(int id) {
        this.readerID = id;
    }

    public void run() {
        try {
            // Entry section
            SharedResource.mutex.acquire();
            SharedResource.readCount++;
            if (SharedResource.readCount == 1) {
                SharedResource.wrt.acquire(); // First reader blocks writers
            }
            SharedResource.mutex.release();

            // Critical section
            SharedResource.read(readerID);

            // Exit section
            SharedResource.mutex.acquire();
            SharedResource.readCount--;
            if (SharedResource.readCount == 0) {
                SharedResource.wrt.release(); // Last reader unblocks writers
            }
            SharedResource.mutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Writer extends Thread {
    int writerID;

    Writer(int id) {
        this.writerID = id;
    }

    public void run() {
        try {
            SharedResource.wrt.acquire(); // Writers wait for full access
            SharedResource.write(writerID);
            SharedResource.wrt.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class assignment8 {
    public static void main(String[] args) {
        // Create multiple readers and writers
        Reader r1 = new Reader(1);
        Reader r2 = new Reader(2);
        Writer w1 = new Writer(1);
        Reader r3 = new Reader(3);
        Writer w2 = new Writer(2);

        // Start threads
        r1.start();
        w1.start();
        r2.start();
        w2.start();
        r3.start();
    }
}

/*
PS F:\Btech\sem4\OSL> java -cp . assignment8
Reader 3 is reading.
Reader 1 is reading.
Reader 2 is reading.
Reader 3 has finished reading.
Reader 1 has finished reading.
Reader 2 has finished reading.
Writer 2 is writing.
Writer 2 has finished writing.
Writer 1 is writing.
Writer 1 has finished writing.
 */