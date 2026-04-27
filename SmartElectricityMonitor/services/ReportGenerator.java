package services;

import models.Room;
import java.util.*;

public class ReportGenerator {

    public static void generateGreenRoomRanking(HashMap<String, Room> rooms) {
        // Java Collections: Converting map values to a List for sorting
        List<Room> roomList = new ArrayList<>(rooms.values());
        
        // Sorting rooms based on electricity consumption (lowest first)
        Collections.sort(roomList, new Comparator<Room>() {
            @Override
            public int compare(Room r1, Room r2) {
                return Double.compare(r1.getTotalMonthlyConsumption(), r2.getTotalMonthlyConsumption());
            }
        });

        // String Handling: Using StringBuilder for efficient string concatenation
        StringBuilder report = new StringBuilder();
        report.append("\n==================================================\n");
        report.append("           GREEN ROOM RANKING REPORT\n");
        report.append("==================================================\n");
        
        int rank = 1;
        for (Room room : roomList) {
            report.append(rank).append(". Room ").append(room.getRoomNumber())
                  .append("\t - ").append(String.format("%.2f", room.getTotalMonthlyConsumption()))
                  .append(" kWh/month\n");
            rank++;
        }
        report.append("==================================================\n");
        System.out.println(report.toString());
        
        if (!roomList.isEmpty()) {
            System.out.println("[WINNER] The Greenest Room is Room " + roomList.get(0).getRoomNumber() + " with lowest consumption!");
            System.out.println("[ECO-FRIENDLY] Thank you for supporting a sustainable campus!");
        }
    }
}
