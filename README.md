# ⚡ Smart Electricity Usage Monitor System

A Java-based simulation system that monitors electricity usage of appliances across rooms (hostels, classrooms, offices) and promotes energy conservation through alerts, recommendations, and green room rankings.

Built as a **Project Based Learning (PBL)** submission focused on **Sustainability, Innovation, and Problem Solving** using core Java and Object-Oriented Programming concepts.

---

## 📌 Problem Statement

In colleges, hostels, and offices, electricity is wasted daily — ACs left running in empty classrooms, lights on during the daytime, fans running all night. There is no visibility into which rooms consume the most power, so nobody is held accountable.

## 💡 Our Solution

This system acts as a **smart monitoring layer** that:

- **Tracks** every appliance in every room — its type, power rating, and daily usage hours
- **Calculates** total monthly electricity consumption per room
- **Alerts** when any room crosses a safe usage threshold
- **Recommends** energy-saving actions
- **Ranks** rooms from greenest (least consumption) to most wasteful
- **Monitors concurrently** using multithreading — simulating real-world IoT behavior

---

## 🚀 How to Run

### Prerequisites

- **Java JDK 17+** installed on your system
- Verify installation by running:
  ```
  java -version
  javac -version
  ```
- If not installed, download from [Microsoft OpenJDK](https://learn.microsoft.com/en-us/java/openjdk/download) or run:
  ```
  winget install Microsoft.OpenJDK.17
  ```

### Steps to Compile and Run

1. **Clone the repository**
   ```bash
   git clone https://github.com/madhavgairola/java-project.git
   cd java-project/SmartElectricityMonitor
   ```

2. **Compile all Java files**
   ```bash
   javac -d bin exceptions/*.java interfaces/*.java models/*.java services/*.java threads/*.java main/*.java
   ```

3. **Run the program**
   ```bash
   java -cp bin main.Main
   ```

### Running the Dashboard UI

Simply open the `dashboard.html` file in any web browser by double-clicking it. It provides a visual simulation of the monitoring system with animated kWh counters synchronized to a 24-hour time-lapse clock.

---

## 📂 Project Structure

```
SmartElectricityMonitor/
│
├── main/
│   └── Main.java              # Entry point - creates rooms, starts threads
│
├── models/
│   ├── Appliance.java          # Abstract parent class for all appliances
│   ├── Fan.java                # Fan appliance (inherits Appliance)
│   ├── AC.java                 # Air Conditioner appliance (inherits Appliance)
│   ├── Light.java              # Light Bulb appliance (inherits Appliance)
│   └── Room.java               # Room containing a list of appliances
│
├── interfaces/
│   └── AlertSystem.java        # Interface for alert generation
│
├── exceptions/
│   └── InvalidUsageException.java  # Custom checked exception
│
├── services/
│   └── ReportGenerator.java    # Green Room Ranking report builder
│
├── threads/
│   └── MonitorThread.java      # Thread for concurrent room monitoring
│
└── dashboard.html              # Visual HTML/CSS/JS simulation dashboard
```

---

## 📖 Class Descriptions

| Class | Description |
|-------|-------------|
| **`Appliance.java`** | Abstract parent class with common properties (name, power rating, usage hours) and an abstract method `calculateMonthlyConsumption()`. Cannot be instantiated directly. |
| **`Fan.java`** | Concrete child class representing a ceiling fan. Inherits from `Appliance` and implements monthly consumption calculation. |
| **`AC.java`** | Concrete child class representing an air conditioner. Inherits from `Appliance` and implements monthly consumption calculation. |
| **`Light.java`** | Concrete child class representing a light bulb. Inherits from `Appliance` and implements monthly consumption calculation. |
| **`Room.java`** | Represents a physical room. Uses an `ArrayList<Appliance>` to store all appliances and calculates total room consumption. |
| **`AlertSystem.java`** | Interface that defines `generateAlert()` and `provideRecommendations()` methods. Implemented by `CampusAlertSystem` inner class in Main. |
| **`InvalidUsageException.java`** | Custom checked exception thrown when invalid data is entered (e.g., usage hours > 24 or negative power rating). |
| **`ReportGenerator.java`** | Service class that sorts rooms by consumption using `Collections.sort()` and builds a formatted ranking report using `StringBuilder`. |
| **`MonitorThread.java`** | Extends `Thread` class. Each instance monitors a single room concurrently, simulating real-time IoT monitoring behavior. |
| **`Main.java`** | Entry point. Creates rooms and appliances, stores them in a `HashMap`, handles exceptions with try-catch, implements the `AlertSystem` interface, and starts monitoring threads. |

---

## 🧠 OOP Concepts Mapping

| OOP Concept | Where It Is Used |
|-------------|-----------------|
| **Classes & Objects** | `Room`, `Fan`, `AC`, `Light` objects created in `Main.java` |
| **Constructors** | Every model class has parameterized constructors |
| **Abstraction** | `Appliance` is an `abstract class` with the abstract method `calculateMonthlyConsumption()` |
| **Inheritance (Hierarchical)** | `Fan`, `AC`, `Light` all extend `Appliance` using `extends` keyword |
| **Interfaces** | `AlertSystem` interface implemented by `CampusAlertSystem` in Main |
| **Exception Handling** | `InvalidUsageException` (custom checked exception) + `try-catch` blocks in Main and MonitorThread |
| **Collections Framework** | `ArrayList<Appliance>` in Room, `HashMap<String, Room>` in Main |
| **String Handling** | `StringBuilder` used in `ReportGenerator` for efficient report building |
| **Multithreading** | `MonitorThread extends Thread` — 3 threads monitor 3 rooms concurrently |
| **Packages** | Code organized into `models`, `interfaces`, `exceptions`, `services`, `threads`, `main` |
| **Static Methods** | `ReportGenerator.generateGreenRoomRanking()` is a static utility method |
| **Control Statements** | `if-else` for alert logic, `for-each` loops for iterating appliances |

---

## 💻 Sample Console Output

```
==================================================
    SMART ELECTRICITY USAGE MONITOR SYSTEM
==================================================

--- Starting Live Room Monitoring ---
[Monitor] Room 303 (Office) analyzed. Consumption: 253.80 kWh
[Monitor] Room 101 (Hostel) analyzed. Consumption: 390.60 kWh

[!] ALERT: High Electricity Usage in Room 101 (Hostel)! (390.60 kWh)
[*] RECOMMENDATION: Turn off ACs/Lights when not in use. Opt for natural sunlight.

[Monitor] Room 202 (Classroom) analyzed. Consumption: 408.00 kWh

[!] ALERT: High Electricity Usage in Room 202 (Classroom)! (408.00 kWh)
[*] RECOMMENDATION: Turn off ACs/Lights when not in use. Opt for natural sunlight.

==================================================
           GREEN ROOM RANKING REPORT
==================================================
1. Room 303 (Office)       - 253.80 kWh/month
2. Room 101 (Hostel)       - 390.60 kWh/month
3. Room 202 (Classroom)    - 408.00 kWh/month
==================================================

[WINNER] The Greenest Room is Room 303 (Office) with lowest consumption!
[ECO-FRIENDLY] Thank you for supporting a sustainable campus!
```

> **Note:** Thread execution order may vary between runs — this is expected behavior in multithreading.

---

## 🎓 Viva Preparation Guide

### Key Questions & Answers

**Q: What is the purpose of this project?**
> This project simulates a smart electricity monitoring system that promotes energy conservation on campus by tracking appliance usage, generating alerts for high consumption, and ranking rooms to encourage sustainable behavior.

**Q: Why is Appliance an abstract class instead of a normal class?**
> Because a generic "Appliance" doesn't exist in reality — you can only have a specific Fan, AC, or Light. Making it abstract prevents instantiation of a raw Appliance and forces every child class to define its own `calculateMonthlyConsumption()` logic.

**Q: Why use HashMap instead of an Array for storing rooms?**
> A `HashMap` allows instant O(1) lookup of any room by its room number as the key. In a real campus with thousands of rooms, this is far more efficient than looping through an entire array.

**Q: How does multithreading help here?**
> In real IoT systems, sensors monitor all rooms simultaneously, not one after another. By using threads, we simulate this concurrent monitoring behavior — each room is analyzed independently at the same time.

**Q: What happens if someone enters invalid data?**
> Our custom `InvalidUsageException` is thrown immediately. For example, if usage hours exceed 24 or power rating is negative, the system rejects the data before it can corrupt any calculations.

**Q: Where is the innovation in this project?**
> The Green Room Ranking system gamifies sustainability — rooms compete to be the most energy-efficient. Combined with real-time concurrent monitoring and automated alerts, this creates a smart campus concept that can scale to real-world IoT deployment.

---

## 🛠 Technologies Used

- **Language:** Java (JDK 17+)
- **Concepts:** Core OOP, Multithreading, Collections, Exception Handling
- **Dashboard:** HTML5, CSS3, Vanilla JavaScript
- **No external libraries or frameworks used**

---

## 👥 Authors

- Madhav Gairola

---

## 📄 License

This project is built for academic purposes as part of a college PBL evaluation.
