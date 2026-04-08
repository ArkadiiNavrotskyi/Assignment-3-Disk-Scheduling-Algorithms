import java.util.Arrays;

 // Disk Scheduling Algorithm Simulator
 // This program simulates three disk scheduling algorithms: FCFS, SSTF, and SCAN
 // It runs three different cases to demonstrate the behavior of each algorithm

public class DiskScheduling {

    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println("Disk Scheduling Algorithm Simulator");
        System.out.println("=====================================\n");

        // --- CASE 1 ---
        System.out.println("=== CASE 1: Standard Request Queue ===");
        int head1 = 53;
        int[] requests1 = {98, 183, 37, 122, 14, 124, 65, 67};
        runAllAlgorithms(head1, requests1, "right");

        // --- CASE 2 ---
        System.out.println("\n=== CASE 2: Clustered Requests ===");
        int head2 = 50;
        int[] requests2 = {45, 48, 52, 90, 150, 160};
        runAllAlgorithms(head2, requests2, "right");

        // --- CASE 3 ---
        System.out.println("\n=== CASE 3: Fairness Challenge ===");
        int head3 = 15;
        int[] requests3 = {10, 12, 14, 16, 100, 102};
        runAllAlgorithms(head3, requests3, "right");
    }

    // Helper method to run all 3 algorithms for a specific case
    public static void runAllAlgorithms(int head, int[] requests, String direction) {
        FCFS(head, requests);
        SSTF(head, Arrays.copyOf(requests, requests.length));
        SCAN(head, Arrays.copyOf(requests, requests.length), direction);
        System.out.println("-------------------------------------------");
    }

    // 1. First Come First Serve (FCFS)
    // This algorithm services requests in the order they arrive, regardless of their position on the disk
    public static void FCFS(int head, int[] reqs) {
        int totalMovement = 0;
        int currentHead = head;

        System.out.println("\nAlgorithm: FCFS");
        System.out.print("Service Order: " + head);

        for (int req : reqs) {
            totalMovement += Math.abs(currentHead - req);
            currentHead = req;
            System.out.print(" -> " + req);
        }
        System.out.println("\nTotal Head Movement: " + totalMovement);
    }

    // 2. Shortest Seek Time First (SSTF)
    // This algorithm selects the request that is closest to the current head position, minimizing seek time for each request
    public static void SSTF(int head, int[] reqs) {
        int totalMovement = 0;
        int currentHead = head;
        boolean[] visited = new boolean[reqs.length];

        System.out.println("\nAlgorithm: SSTF");
        System.out.print("Service Order: " + head);

        for (int i = 0; i < reqs.length; i++) {
            int minDiff = Integer.MAX_VALUE;
            int closestIndex = -1;

            for (int j = 0; j < reqs.length; j++) {
                if (!visited[j]) {
                    int diff = Math.abs(currentHead - reqs[j]);
                    if (diff < minDiff) {
                        minDiff = diff;
                        closestIndex = j;
                    }
                }
            }

            visited[closestIndex] = true;
            totalMovement += minDiff;
            currentHead = reqs[closestIndex];
            System.out.print(" -> " + currentHead);
        }
        System.out.println("\nTotal Head Movement: " + totalMovement);
    }

    // 3. SCAN (Elevator Algorithm)
    // This algorithm moves the head in one direction (right or left),
    // servicing requests until it reaches the end of the disk, then reverses
    // direction and services remaining requests
    public static void SCAN(int head, int[] reqs, String direction) {
        int totalMovement = 0;
        int currentHead = head;

        System.out.println("\nAlgorithm: SCAN");
        System.out.print("Service Order: " + head);

        Arrays.sort(reqs);

        if (direction.equalsIgnoreCase("right")) {
            // right movment
            for (int req : reqs) {
                if (req >= currentHead) {
                    totalMovement += Math.abs(currentHead - req);
                    currentHead = req;
                    System.out.print(" -> " + req);
                }
            }
            // end of the disk 199
            if (currentHead != 199) {
                totalMovement += Math.abs(currentHead - 199);
                currentHead = 199;
                System.out.print(" -> 199");
            }
            // back to left
            for (int i = reqs.length - 1; i >= 0; i--) {
                if (reqs[i] < head) {
                    totalMovement += Math.abs(currentHead - reqs[i]);
                    currentHead = reqs[i];
                    System.out.print(" -> " + reqs[i]);
                }
            }
        } else {
            // left
            for (int i = reqs.length - 1; i >= 0; i--) {
                if (reqs[i] <= currentHead) {
                    totalMovement += Math.abs(currentHead - reqs[i]);
                    currentHead = reqs[i];
                    System.out.print(" -> " + reqs[i]);
                }
            }
            // end of the disk 0
            if (currentHead != 0) {
                totalMovement += Math.abs(currentHead - 0);
                currentHead = 0;
                System.out.print(" -> 0");
            }
            // Вack to right
            for (int req : reqs) {
                if (req > head) {
                    totalMovement += Math.abs(currentHead - req);
                    currentHead = req;
                    System.out.print(" -> " + req);
                }
            }
        }

        System.out.println("\nTotal Head Movement: " + totalMovement);
    }
}