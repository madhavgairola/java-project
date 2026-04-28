package services;

import models.Room;
import java.util.*;

public class ReportGenerator {

    public static void generateGreenRoomRanking(HashMap<String, Room> rooms) {
        List<Room> roomList = new ArrayList<>(rooms.values());
        
        Collections.sort(roomList, new Comparator<Room>() {
            @Override
            public int compare(Room r1, Room r2) {
                return Double.compare(r1.getUsagePercentage(), r2.getUsagePercentage());
            }
        });

        StringBuilder report = new StringBuilder();
        report.append("\n==================================================\n");
        report.append("        GREEN ROOM RANKING REPORT\n");
        report.append("       (Ranked by Efficiency %)\n");
        report.append("==================================================\n");
        
        int rank = 1;
        for (Room room : roomList) {
            report.append(rank).append(". Room ").append(room.getRoomNumber())
                  .append("\t - ").append(String.format("%.2f", room.getTotalMonthlyConsumption()))
                  .append(" / ").append(String.format("%.0f", room.getThreshold()))
                  .append(" kWh (").append(String.format("%.1f", room.getUsagePercentage()))
                  .append("% used)\n");
            rank++;
        }
        report.append("==================================================\n");
        System.out.println(report.toString());
        
        if (!roomList.isEmpty()) {
            System.out.println("[WINNER] The Greenest Room is Room " + roomList.get(0).getRoomNumber() 
                + " using only " + String.format("%.1f", roomList.get(0).getUsagePercentage()) + "% of its threshold!");
            System.out.println("[ECO-FRIENDLY] Thank you for supporting a sustainable campus!");
        }
    }
}
