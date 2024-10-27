import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Scanner;

class JobWithPriority {
    int jobId;
    int processingTime;
    int priority;

    public JobWithPriority(int jobId, int processingTime, int priority) {
        this.jobId = jobId;
        this.processingTime = processingTime;
        this.priority = priority;
    }
}

public class JobSchedulerWithPriority {
    public static void main(String[] args) {
        try {
            File inputFile = new File("src/input/task2-input.txt");
            Scanner scanner = new Scanner(inputFile);

            // Min-heap priority queue to prioritize jobs by priority and processing time
            PriorityQueue<JobWithPriority> minHeap = new PriorityQueue<>((a, b) -> {
                if (a.priority == b.priority) {
                    return a.processingTime - b.processingTime; // SPT within same priority
                } else {
                    return a.priority - b.priority; // Higher priority jobs first (lower priority number)
                }
            });

            //Read each line from the file and add jobs to the priority queue
            while (scanner.hasNextInt()) {
                int jobId = scanner.nextInt();
                int processingTime = scanner.nextInt();
                int priority = scanner.nextInt();
                minHeap.add(new JobWithPriority(jobId, processingTime, priority));
            }

            int currentTime = 0;
            int totalCompletionTime = 0;
            int[] executionOrder = new int[minHeap.size()];
            int index = 0;

            //Process jobs based on priority and SPT within priority
            while (!minHeap.isEmpty()) {
                JobWithPriority currentJob = minHeap.poll();
                currentTime += currentJob.processingTime;
                totalCompletionTime += currentTime;
                executionOrder[index++] = currentJob.jobId;
            }

            // Output the execution order
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
