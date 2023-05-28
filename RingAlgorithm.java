import java.util.ArrayList;
import java.util.List;

public class RingAlgorithm {
    private int processId;
    private int numProcesses;
    private boolean isLeader;

    public RingAlgorithm(int processId, int numProcesses) {
        this.processId = processId;
        this.numProcesses = numProcesses;
        this.isLeader = false;
    }

    public void runElection() {
        int nextProcessId = (processId + 1) % numProcesses;

        while (nextProcessId != processId) {
            // Send election message to the next process
            sendMessage(nextProcessId, "ELECTION");

            // Wait for response
            String response = receiveMessage();

            if (response.equals("ELECTION")) {
                // Forward the election message to the next process
                nextProcessId = (nextProcessId + 1) % numProcesses;
            } else if (response.equals("LEADER")) {
                // Received leader message, stop the election
                isLeader = false;
                return;
            }
        }

        // If the process reaches here, it becomes the leader
        announceLeader();
    }

    private void announceLeader() {
        isLeader = true;
        System.out.println("Process " + processId + " is the leader.");
        // Send leader message to the next process
        int nextProcessId = (processId + 1) % numProcesses;
        sendMessage(nextProcessId, "LEADER");
    }

    private void sendMessage(int processId, String message) {
        // Code to send a message to the specified process
        // Implementation depends on the network communication mechanism
    }

    private String receiveMessage() {
        // Code to receive a message from the current process's left neighbor
        // Implementation depends on the network communication mechanism
        return "ELECTION"; // Placeholder response for demonstration purposes
    }

    public boolean isLeader() {
        return isLeader;
    }

    public static void main(String[] args) {
        int numProcesses = 5; // Number of processes in the ring

        // Create an array to hold the processes
        List<RingAlgorithm> processes = new ArrayList<>();

        // Create and initialize the processes
        for (int i = 0; i < numProcesses; i++) {
            RingAlgorithm process = new RingAlgorithm(i, numProcesses);
            processes.add(process);
        }

        // Run the election on each process
        for (int i = 0; i < numProcesses; i++) {
            RingAlgorithm process = processes.get(i);
            process.runElection();
            System.out.println("Process " + process.processId + " is leader: " + process.isLeader());
        }
    }
}

