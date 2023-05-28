import java.util.ArrayList;
import java.util.List;

public class BullyAlgorithm {
    private int processId;
    private List<Integer> higherProcesses;

    public BullyAlgorithm(int processId, List<Integer> higherProcesses) {
        this.processId = processId;
        this.higherProcesses = higherProcesses;
    }

    public void runElection() {
        // Send election message to higher processes
        for (int higherProcess : higherProcesses) {
            sendMessage(higherProcess, "ELECTION");
        }

        // Wait for response from higher processes
        boolean hasHigherProcessResponded = false;
        for (int higherProcess : higherProcesses) {
            String response = receiveMessage(higherProcess);
            if (response.equals("OK")) {
                hasHigherProcessResponded = true;
                break;
            }
        }

        if (hasHigherProcessResponded) {
            // Received response from higher process, stop the election
            return;
        } else {
            // Process becomes the leader
            announceLeader();
        }
    }

    private void announceLeader() {
        System.out.println("Process " + processId + " is the leader.");
        // Send leader message to lower processes
        for (int lowerProcess = processId - 1; lowerProcess >= 0; lowerProcess--) {
            sendMessage(lowerProcess, "LEADER");
        }
    }

    private void sendMessage(int processId, String message) {
        // Code to send a message to the specified process
        // Implementation depends on the network communication mechanism
    }

    private String receiveMessage(int processId) {
        // Code to receive a message from the specified process
        // Implementation depends on the network communication mechanism
        return "OK"; // Placeholder response for demonstration purposes
    }

    public static void main(String[] args) {
        int numProcesses = 5; // Number of processes

        // Create an array to hold the processes
        List<BullyAlgorithm> processes = new ArrayList<>();

        // Create and initialize the processes
        for (int i = 0; i < numProcesses; i++) {
            List<Integer> higherProcesses = new ArrayList<>();
            for (int j = i + 1; j < numProcesses; j++) {
                higherProcesses.add(j);
            }
            BullyAlgorithm process = new BullyAlgorithm(i, higherProcesses);
            processes.add(process);
        }

        // Run the election on each process
        for (BullyAlgorithm process : processes) {
            process.runElection();
        }
    }
}

