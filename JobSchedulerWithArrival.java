import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Collections;

class JobWithArrival {
    int jobId;
    int processingTime;
    int arrivalTime;

    public JobWithArrival(int jobId, int processingTime, int arrivalTime) {
        this.jobId = jobId;
        this.processingTime = processingTime;
        this.arrivalTime = arrivalTime;
    }
}

public class JobSchedulerWithArrival {
    public static void main(String[] args) {
        try {
            File inputFile = new File("src/input/task3-input.txt");
            Scanner scanner = new Scanner(inputFile);

            // List to hold all jobs before they arrive
            List<JobWithArrival> allJobs = new ArrayList<>();

            // Read each line from the file and add jobs to the list
            while (scanner.hasNextInt()) {
                int jobId = scanner.nextInt();
                int processingTime = scanner.nextInt();
                int arrivalTime = scanner.nextInt();
                allJobs.add(new JobWithArrival(jobId, processingTime, arrivalTime));
            }

            // Sort jobs by arrival time to handle them in order
            Collections.sort(allJobs, (a, b) -> a.arrivalTime - b.arrivalTime);

            // Priority queue to prioritize jobs by processing time (SPT)
            PriorityQueue<JobWithArrival> minHeap = new PriorityQueue<>((a, b) -> a.processingTime - b.processingTime);

            int currentTime = 0;
            int totalCompletionTime = 0;
            int[] executionOrder = new int[allJobs.size()];
            int index = 0;
            int jobsProcessed = 0;
            int nextJobIndex = 0;

            // Process jobs as they arrive dynamically
            while (jobsProcessed < allJobs.size() || !minHeap.isEmpty()) {
                // Add all jobs that have arrived by current time
                while (nextJobIndex < allJobs.size() && allJobs.get(nextJobIndex).arrivalTime <= currentTime) {
                    minHeap.add(allJobs.get(nextJobIndex));
                    nextJobIndex++;
                }

                // Process the next job from the priority queue
                if (!minHeap.isEmpty()) {
                    JobWithArrival currentJob = minHeap.poll();
                    currentTime += currentJob.processingTime;
                    totalCompletionTime += currentTime - currentJob.arrivalTime;
                    executionOrder[index++] = currentJob.jobId;
                    jobsProcessed++;
                } else if (nextJobIndex < allJobs.size()) {
                    // No jobs in queue, skip to the arrival time of the next job
                    currentTime = allJobs.get(nextJobIndex).arrivalTime;
                }
            }

            // Output the execution order
            System.out.print("Execution order: [");
            for (int i = 0; i < executionOrder.length; i++) {
                System.out.print(executionOrder[i]);
                if (i < executionOrder.length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.print("]");

            // Calculate the average completion time
            double averageCompletionTime = (double) totalCompletionTime / allJobs.size();
            System.out.println("\nAverage completion time: " + averageCompletionTime);

        } catch (FileNotFoundException e) {
            System.out.println("Error: Input file not found.");
            e.printStackTrace();
        }
    }
}
