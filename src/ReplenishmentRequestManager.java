import java.io.*;
import java.util.ArrayList;

public class ReplenishmentRequestManager {
    private ArrayList<ReplenishmentRequest> replenishmentRequests;

    public ReplenishmentRequestManager() {
        this.replenishmentRequests = new ArrayList<>();
        loadReplenishmentRequestsFromCSV("../Data/ReplenishRequest.csv");
    }

    private void loadReplenishmentRequestsFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header line

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length < 4) {
                    System.out.println("Invalid data: " + line);
                    continue;
                }

                String requestID = fields[0].trim();
                String medicine = fields[1].trim();
                int quantity = Integer.parseInt(fields[2].trim());
                String status = fields[3].trim();

                ReplenishmentRequest request = new ReplenishmentRequest(requestID, medicine, quantity, status);
                replenishmentRequests.add(request);
            }
        } catch (IOException e) {
            System.out.println("Error reading replenishment requests file: " + e.getMessage());
        }
    }

    public ArrayList<ReplenishmentRequest> getReplenishmentRequests() {
        return this.replenishmentRequests;
    }
}
