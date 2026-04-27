package interfaces;

public interface AlertSystem {
    void generateAlert(double totalConsumption, String roomName);
    void provideRecommendations();
}
