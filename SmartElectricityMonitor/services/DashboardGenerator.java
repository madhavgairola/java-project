package services;

import models.Room;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * DashboardGenerator - Generates an HTML dashboard file with real computed values.
 * This connects our Java backend logic to a visual frontend report.
 * Uses: FileWriter (Java I/O), StringBuilder (String Handling), Collections (Sorting)
 */
public class DashboardGenerator {

    public static void generateDashboard(HashMap<String, Room> rooms, String filePath) {
        // Sort rooms by efficiency % for the leaderboard (lowest % = greenest)
        List<Room> sortedRooms = new ArrayList<>(rooms.values());
        Collections.sort(sortedRooms, new Comparator<Room>() {
            @Override
            public int compare(Room r1, Room r2) {
                return Double.compare(r1.getUsagePercentage(), r2.getUsagePercentage());
            }
        });

        // Get all rooms as a list (unsorted) for the cards
        List<Room> allRooms = new ArrayList<>(rooms.values());

        // String Handling: Using StringBuilder to build the entire HTML report
        StringBuilder html = new StringBuilder();

        // ---- HTML HEAD ----
        html.append("<!DOCTYPE html>\n<html lang=\"en\">\n<head>\n");
        html.append("    <meta charset=\"UTF-8\">\n");
        html.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
        html.append("    <title>Smart Campus Dashboard</title>\n");
        html.append("    <link href=\"https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap\" rel=\"stylesheet\">\n");
        html.append("    <style>\n");
        html.append(getCSS());
        html.append("    </style>\n</head>\n<body>\n\n");

        // ---- HEADER ----
        html.append("    <header>\n");
        html.append("        <h1>Smart Electricity Monitor</h1>\n");
        html.append("        <p class=\"subtitle\">Live Campus Telemetry</p>\n");
        html.append("    </header>\n\n");

        // ---- TIMER BAR ----
        html.append("    <div class=\"timer-bar\">\n");
        html.append("        <div class=\"live-dot\"></div>\n");
        html.append("        <span>MONTHLY SIMULATION</span>\n");
        html.append("        <span id=\"clock\">1/April/2026</span>\n");
        html.append("    </div>\n\n");

        // ---- ROOM CARDS (dynamically generated from Java data) ----
        html.append("    <div class=\"dashboard-grid\">\n");

        int cardIndex = 0;
        for (Room room : allRooms) {
            double consumption = room.getTotalMonthlyConsumption();
            double threshold = room.getThreshold();

            String roomId = "room" + cardIndex;

            html.append("        <div class=\"card\">\n");
            html.append("            <div class=\"card-header\">\n");
            html.append("                <span class=\"room-title\">Room ").append(room.getRoomNumber()).append("</span>\n");
            html.append("                <div class=\"status-dot\" id=\"dot-").append(roomId).append("\"></div>\n");
            html.append("            </div>\n");
            html.append("            <div class=\"consumption-value\">\n");
            html.append("                <span id=\"val-").append(roomId).append("\">0.0</span>");
            html.append("<span class=\"unit\">kWh</span>\n");
            html.append("            </div>\n");
            html.append("            <div class=\"progress-bar\">\n");
            html.append("                <div class=\"progress-fill\" id=\"prog-").append(roomId).append("\"></div>\n");
            html.append("            </div>\n");
            html.append("            <div class=\"meta-info\">\n");
            html.append("                <span id=\"msg-").append(roomId).append("\">Current Usage</span>\n");
            html.append("                <span>Threshold: ").append(String.format("%.0f", threshold)).append("</span>\n");
            html.append("            </div>\n");
            html.append("        </div>\n\n");

            cardIndex++;
        }

        // ---- LEADERBOARD (sorted by Java's Collections.sort) ----
        html.append("        <div class=\"card leaderboard-card\">\n");
        html.append("            <div class=\"leaderboard-header\">\n");
        html.append("                <svg width=\"20\" height=\"20\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"var(--text-secondary)\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"><polygon points=\"12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2\"></polygon></svg>\n");
        html.append("                <span class=\"room-title\" style=\"margin:0\">Green Room Ranking</span>\n");
        html.append("            </div>\n");
        html.append("            <ul class=\"leaderboard-list\">\n");

        int rank = 1;
        for (Room room : sortedRooms) {
            String rankClass = (rank == 1) ? " winner-text" : "";
            html.append("                <li class=\"leaderboard-item").append(rankClass).append("\">\n");
            html.append("                    <div class=\"rank-info\">\n");
            html.append("                        <span class=\"rank-number\">").append(String.format("%02d", rank)).append("</span>\n");
            html.append("                        <span>Room ").append(room.getRoomNumber()).append("</span>\n");
            html.append("                    </div>\n");
            html.append("                    <span id=\"rank-").append(rank).append("-val\">0.00 kWh</span>\n");
            html.append("                </li>\n");
            rank++;
        }

        html.append("            </ul>\n");
        html.append("        </div>\n");
        html.append("    </div>\n\n");

        // ---- JAVASCRIPT (with real Java-computed values injected) ----
        html.append("    <script>\n");
        html.append("        const easeOutQuart = (t) => 1 - Math.pow(1 - t, 4);\n\n");

        html.append("        function animateData(id, targetValue, duration, maxThreshold) {\n");
        html.append("            const valEl = document.getElementById('val-' + id);\n");
        html.append("            const progEl = document.getElementById('prog-' + id);\n");
        html.append("            const dotEl = document.getElementById('dot-' + id);\n");
        html.append("            const msgEl = document.getElementById('msg-' + id);\n");
        html.append("            const startTime = performance.now();\n\n");
        html.append("            function update(currentTime) {\n");
        html.append("                const elapsed = currentTime - startTime;\n");
        html.append("                const progress = Math.min(elapsed / duration, 1);\n");
        html.append("                const easeProgress = easeOutQuart(progress);\n");
        html.append("                const currentVal = targetValue * easeProgress;\n\n");
        html.append("                valEl.textContent = currentVal.toFixed(1);\n");
        html.append("                const percentage = Math.min((currentVal / maxThreshold) * 100, 100);\n");
        html.append("                progEl.style.width = percentage + '%';\n\n");
        html.append("                if (currentVal >= maxThreshold) {\n");
        html.append("                    progEl.style.backgroundColor = 'var(--accent-red)';\n");
        html.append("                    valEl.style.color = 'var(--accent-red)';\n");
        html.append("                    dotEl.className = 'status-dot active-red';\n");
        html.append("                    if(msgEl) { msgEl.textContent = 'Limit Exceeded'; msgEl.style.color = 'var(--accent-red)'; }\n");
        html.append("                } else if (progress > 0.5) {\n");
        html.append("                    progEl.style.backgroundColor = 'var(--accent-green)';\n");
        html.append("                    dotEl.className = 'status-dot active-green';\n");
        html.append("                } else {\n");
        html.append("                    progEl.style.backgroundColor = 'var(--text-secondary)';\n");
        html.append("                }\n\n");
        html.append("                if (progress < 1) { requestAnimationFrame(update); }\n");
        html.append("                else { updateRankings(); }\n");
        html.append("            }\n");
        html.append("            requestAnimationFrame(update);\n");
        html.append("        }\n\n");

        // Generate the updateRankings function with real sorted values
        html.append("        function updateRankings() {\n");
        rank = 1;
        for (Room room : sortedRooms) {
            html.append("            document.getElementById('rank-").append(rank).append("-val').textContent = '")
                .append(String.format("%.2f", room.getTotalMonthlyConsumption())).append(" kWh';\n");
            rank++;
        }
        html.append("        }\n\n");

        // Simulated 30-day monthly counter showing dates like "01 April" to "30 April"
        html.append("        const clockEl = document.getElementById('clock');\n");
        html.append("        const SIM_DURATION = 20000;\n");
        html.append("        const TOTAL_DAYS = 30;\n");
        html.append("        let simStart = null;\n\n");
        html.append("        function updateSimClock() {\n");
        html.append("            if (!simStart) return;\n");
        html.append("            const elapsed = Date.now() - simStart;\n");
        html.append("            const progress = Math.min(elapsed / SIM_DURATION, 1);\n");
        html.append("            const currentDay = Math.max(1, Math.ceil(progress * TOTAL_DAYS));\n");
        html.append("            clockEl.textContent = currentDay + '/April/2026';\n");
        html.append("            if (progress < 1) { requestAnimationFrame(updateSimClock); }\n");
        html.append("            else { clockEl.textContent = '30/April/2026'; }\n");
        html.append("        }\n\n");

        // DOMContentLoaded — inject real values from Java computation
        html.append("        window.addEventListener('DOMContentLoaded', () => {\n");
        html.append("            simStart = Date.now();\n");
        html.append("            requestAnimationFrame(updateSimClock);\n\n");

        cardIndex = 0;
        int delay = 500;
        for (Room room : allRooms) {
            double consumption = room.getTotalMonthlyConsumption();
            double threshold = room.getThreshold();

            String roomId = "room" + cardIndex;
            int duration = 15000 + (cardIndex * 2000);

            html.append("            // Room ").append(room.getRoomNumber())
                .append(" — Value computed by Java: ").append(String.format("%.2f", consumption)).append(" kWh\n");
            html.append("            setTimeout(() => animateData('").append(roomId).append("', ")
                .append(String.format("%.2f", consumption)).append(", ").append(duration).append(", ")
                .append(String.format("%.0f", threshold)).append("), ").append(delay).append(");\n");

            cardIndex++;
        }

        html.append("        });\n");
        html.append("    </script>\n</body>\n</html>\n");

        // ---- FILE I/O: Write the generated HTML to disk ----
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(html.toString());
            System.out.println("\n[Dashboard] Visual report generated successfully: " + filePath);
        } catch (IOException e) {
            System.err.println("[ERROR] Failed to generate dashboard: " + e.getMessage());
        }
    }

    /**
     * Returns the CSS styles as a String.
     * Separated into its own method for code readability.
     */
    private static String getCSS() {
        StringBuilder css = new StringBuilder();
        css.append("        :root {\n");
        css.append("            --bg-base: #0a0a0a; --surface: rgba(255,255,255,0.03);\n");
        css.append("            --surface-hover: rgba(255,255,255,0.06); --border: rgba(255,255,255,0.08);\n");
        css.append("            --text-primary: #ffffff; --text-secondary: #a1a1aa;\n");
        css.append("            --accent-green: #10b981; --accent-green-glow: rgba(16,185,129,0.2);\n");
        css.append("            --accent-red: #ef4444; --accent-red-glow: rgba(239,68,68,0.2);\n");
        css.append("            --transition: all 0.4s cubic-bezier(0.16,1,0.3,1);\n");
        css.append("        }\n");
        css.append("        * { box-sizing: border-box; margin: 0; padding: 0; }\n");
        css.append("        body { font-family: 'Inter', sans-serif; background-color: var(--bg-base); color: var(--text-primary); min-height: 100vh; display: flex; flex-direction: column; align-items: center; padding: 4rem 2rem; line-height: 1.5; background-image: radial-gradient(circle at 15% 50%, rgba(16,185,129,0.04), transparent 25%), radial-gradient(circle at 85% 30%, rgba(239,68,68,0.04), transparent 25%); }\n");
        css.append("        header { text-align: center; margin-bottom: 4rem; animation: fadeInDown 1s ease-out; }\n");
        css.append("        h1 { font-size: 2.5rem; font-weight: 600; letter-spacing: -0.05em; margin-bottom: 0.5rem; background: linear-gradient(to right, #fff, #a1a1aa); -webkit-background-clip: text; -webkit-text-fill-color: transparent; }\n");
        css.append("        p.subtitle { color: var(--text-secondary); font-size: 1rem; font-weight: 300; letter-spacing: 0.02em; text-transform: uppercase; }\n");
        css.append("        .dashboard-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(320px, 1fr)); gap: 1.5rem; width: 100%; max-width: 1100px; margin-bottom: 3rem; }\n");
        css.append("        .card { background: var(--surface); border: 1px solid var(--border); border-radius: 16px; padding: 2rem; backdrop-filter: blur(12px); transition: var(--transition); opacity: 0; transform: translateY(20px); animation: fadeInUp 0.8s ease-out forwards; }\n");
        css.append("        .card:hover { background: var(--surface-hover); transform: translateY(-4px); box-shadow: 0 20px 40px -10px rgba(0,0,0,0.5); }\n");
        css.append("        .card:nth-child(1) { animation-delay: 0.1s; } .card:nth-child(2) { animation-delay: 0.2s; } .card:nth-child(3) { animation-delay: 0.3s; }\n");
        css.append("        .card-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 2rem; }\n");
        css.append("        .room-title { font-size: 1.125rem; font-weight: 500; color: var(--text-secondary); }\n");
        css.append("        .status-dot { width: 8px; height: 8px; border-radius: 50%; background-color: var(--text-secondary); transition: var(--transition); }\n");
        css.append("        .status-dot.active-green { background-color: var(--accent-green); box-shadow: 0 0 12px var(--accent-green-glow); }\n");
        css.append("        .status-dot.active-red { background-color: var(--accent-red); box-shadow: 0 0 12px var(--accent-red-glow); animation: pulse-red 2s infinite; }\n");
        css.append("        .consumption-value { font-size: 3.5rem; font-weight: 300; letter-spacing: -0.04em; margin-bottom: 0.25rem; display: flex; align-items: baseline; gap: 0.5rem; }\n");
        css.append("        .unit { font-size: 1rem; color: var(--text-secondary); font-weight: 400; }\n");
        css.append("        .progress-bar { width: 100%; height: 4px; background: var(--border); border-radius: 2px; margin-top: 2rem; overflow: hidden; }\n");
        css.append("        .progress-fill { height: 100%; width: 0%; border-radius: 2px; transition: width 2s cubic-bezier(0.16,1,0.3,1), background-color 1s ease; }\n");
        css.append("        .meta-info { display: flex; justify-content: space-between; margin-top: 1rem; font-size: 0.875rem; color: var(--text-secondary); }\n");
        css.append("        .leaderboard-card { grid-column: 1 / -1; padding: 0; overflow: hidden; }\n");
        css.append("        .leaderboard-header { padding: 1.5rem 2rem; border-bottom: 1px solid var(--border); display: flex; align-items: center; gap: 0.75rem; }\n");
        css.append("        .leaderboard-list { list-style: none; }\n");
        css.append("        .leaderboard-item { display: flex; justify-content: space-between; align-items: center; padding: 1.25rem 2rem; border-bottom: 1px solid rgba(255,255,255,0.02); transition: var(--transition); }\n");
        css.append("        .leaderboard-item:hover { background: rgba(255,255,255,0.02); }\n");
        css.append("        .leaderboard-item:last-child { border-bottom: none; }\n");
        css.append("        .rank-info { display: flex; align-items: center; gap: 1rem; }\n");
        css.append("        .rank-number { font-size: 0.875rem; color: var(--text-secondary); width: 24px; }\n");
        css.append("        .winner-text { color: var(--accent-green); font-weight: 500; }\n");
        css.append("        .timer-bar { display: flex; align-items: center; justify-content: center; gap: 0.75rem; margin-bottom: 3rem; padding: 0.75rem 1.5rem; background: var(--surface); border: 1px solid var(--border); border-radius: 100px; font-size: 0.875rem; color: var(--text-secondary); letter-spacing: 0.05em; font-variant-numeric: tabular-nums; animation: fadeInUp 0.8s ease-out forwards; }\n");
        css.append("        .timer-bar .live-dot { width: 6px; height: 6px; border-radius: 50%; background: var(--accent-green); box-shadow: 0 0 8px var(--accent-green-glow); animation: blink 1.5s infinite; }\n");
        css.append("        @keyframes blink { 0%, 100% { opacity: 1; } 50% { opacity: 0.3; } }\n");
        css.append("        @keyframes fadeInUp { from { opacity: 0; transform: translateY(20px); } to { opacity: 1; transform: translateY(0); } }\n");
        css.append("        @keyframes fadeInDown { from { opacity: 0; transform: translateY(-20px); } to { opacity: 1; transform: translateY(0); } }\n");
        css.append("        @keyframes pulse-red { 0% { box-shadow: 0 0 0 0 rgba(239,68,68,0.4); } 70% { box-shadow: 0 0 0 10px rgba(239,68,68,0); } 100% { box-shadow: 0 0 0 0 rgba(239,68,68,0); } }\n");
        return css.toString();
    }
}
