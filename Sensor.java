package hardware;

/**
 * sensors must report the information to a
 * logging system so that the data can be collected and used to calibrate the controller.
 */
public interface Sensor {




    /**
     * Each sensor simulator must collect and
     * report on the temperature of the oven simulator every 100 milliseconds.
     *
     * Each report must include a time stamp and a sensor identifier.
     */
    public abstract void temperatureReport();

    public abstract void timer(long milliseconds);

    public abstract void updateTemperature(int ovenTemperature);

    public abstract void updateTimestamp();

    /**
     * To set sensor on or off.
     * @param state set sensor on or off based on oven.
     */
    public abstract void setSensorState(boolean state);



}
