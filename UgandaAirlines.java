import java.io.*;
import java.util.*;

public class UgandaAirlines {

    public static void main(String[] args) {
        String inputFileName = "input.txt";

        int dubaiFlightCount = 0;
        String maxPassengerFlight = null;
        int maxPassengers = -1;

        String firstUnderCapacityFlight = null;

        Map<String, Integer> destinationPassengerMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFileName))) {
            String line;
            boolean hasValidLine = false;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\s+");
                if (parts.length != 3 || !parts[0].equals("UgandaAirlines")) continue;

                String destination = parts[1];
                int passengers;

                try {
                    passengers = Integer.parseInt(parts[2]);
                } catch (NumberFormatException e) {
                    continue; // Skip malformed line
                }

                hasValidLine = true;

                // Count Dubai flights
                if (destination.equalsIgnoreCase("Dubai")) {
                    dubaiFlightCount++;
                }

                // Track max passenger flight
                if (passengers > maxPassengers) {
                    maxPassengers = passengers;
                    maxPassengerFlight = line;
                }

                // Track first under capacity flight
                if (firstUnderCapacityFlight == null && passengers < 100) {
                    firstUnderCapacityFlight = line;
                }

                // Aggregate passengers per destination
                destinationPassengerMap.put(destination,
                    destinationPassengerMap.getOrDefault(destination, 0) + passengers);
            }

            if (!hasValidLine) {
                // Print 4 lines with appropriate defaults
                System.out.println("0");
                System.out.println("No valid flights found");
                System.out.println("All flights have 100+ passengers");
                System.out.println("No popular route");
                return;
            }

            // Output Line 1: Dubai flight count
            System.out.println(dubaiFlightCount);

            // Output Line 2: Max passenger flight
            System.out.println(maxPassengerFlight);

            // Output Line 3: First under-capacity flight or fallback message
            if (firstUnderCapacityFlight != null) {
                System.out.println(firstUnderCapacityFlight);
            } else {
                System.out.println("All flights have 100+ passengers");
            }

            // Output Line 4: Most popular route
            String mostPopularDestination = null;
            int highestTotalPassengers = -1;

            for (Map.Entry<String, Integer> entry : destinationPassengerMap.entrySet()) {
                if (entry.getValue() > highestTotalPassengers) {
                    highestTotalPassengers = entry.getValue();
                    mostPopularDestination = entry.getKey();
                }
            }

            if (mostPopularDestination != null) {
                System.out.println(mostPopularDestination + " " + highestTotalPassengers);
            } else {
                System.out.println("No popular route");
            }

        } catch (FileNotFoundException e) {
            System.out.println("0");
            System.out.println("No valid flights found");
            System.out.println("All flights have 100+ passengers");
            System.out.println("No popular route");
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
