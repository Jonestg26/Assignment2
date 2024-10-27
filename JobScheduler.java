import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Scanner;

class Job {
    int jobId;
    int processingTime;

    public Job(int jobId, int processingTime) {
        this.jobId = jobId;
        this.processingTime = processingTime;
    }
}

public class JobScheduler {
    public static void main(String[] args) {
        try {
            File inputFile = new File("src/input/task1-input.txt");
            Scanner scanner = new Scanner(inputFile);

            // Min-heap priority queue to prioritize jobs by processing time
            PriorityQueue<Job> minHeap = new PriorityQueue<>((a, b) -> a.processingTime - b.processingTime);
            // Read each line from the file and add jobs to the priority queue
            while (scanner.hasNextInt()) {
                int jobId = scanner.nextInt();
                int processingTime = scanner.nextInt();
                minHeap.add(new Job(jobId, processingTime));
            }

            int currentTime = 0;
            int totalCompletionTime = 0;
            int[] executionOrder = new int[minHeap.size()];
            int index = 0;

            //Process jobs based on the SPT rule
            while (!minHeap.isEmpty()) {
                Job currentJob = minHeap.poll();
                currentTime += currentJob.processingTime;
                totalCompletionTime += currentTime;
                executionOrder[index++] = currentJob.jobId;
            }

            //Output the execution order
            System.out.print("Execution order: [");
            for (int i = 0; i < executionOrder.length; i++) {
                System.out.print(executionOrder[i]);
                if (i < executionOrder.length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");

            // Calculate the average completion time
            double averageCompletionTime = (double) totalCompletionTime / executionOrder.length;
            System.out.println("Average completion time: " + averageCompletionTime);

        } catch (FileNotFoundException e) {
            System.out.println("Error: Input file not found.");
            e.printStackTrace();
        }
    }
}
