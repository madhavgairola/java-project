package models;
import exceptions.InvalidUsageException;

public class Fan extends Appliance {
    public Fan(String roomNumber, double powerRatingKw, double dailyUsageHours) throws InvalidUsageException {
        super("Fan", roomNumber, powerRatingKw, dailyUsageHours);
    }

    @Override
    public double calculateMonthlyConsumption() {
        return powerRatingKw * dailyUsageHours * 30;
    }
}
