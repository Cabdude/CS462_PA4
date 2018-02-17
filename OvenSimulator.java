package hardware;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This class simulates an Oven. A simple simulator of an "intelligent" oven.
 *
 * @author Caleb Bishop
 */
public class OvenSimulator implements Oven {
    private boolean ovenStatus = false;
    private Lock lock;

    private int temperature;

    //milliseconds in 1 second.
    private int msec = 1000;


    private SensorSimulator[] sensors;

    private SensorLog sensorLog;

    public OvenSimulator() {
        sensorLog = new SensorLog();
        this.sensors = new SensorSimulator[8];

        sensors[0] = new SensorSimulator(System.currentTimeMillis(),
                SensorSimulator.Identifier.B_LowerLeft);

        sensors[1] = new SensorSimulator(System.currentTimeMillis(),
                SensorSimulator.Identifier.B_LowerRight);

        sensors[2] = new SensorSimulator(System.currentTimeMillis(),
                SensorSimulator.Identifier.B_UpperLeft);

        sensors[3] = new SensorSimulator(System.currentTimeMillis(),
                SensorSimulator.Identifier.B_UpperRight);

        sensors[4] = new SensorSimulator(System.currentTimeMillis(),
                SensorSimulator.Identifier.T_LowerLeft);

        sensors[5] = new SensorSimulator(System.currentTimeMillis(),
                SensorSimulator.Identifier.T_LowerRight);

        sensors[6] = new SensorSimulator(System.currentTimeMillis(),
                SensorSimulator.Identifier.T_UpperLeft);

        sensors[7] = new SensorSimulator(System.currentTimeMillis(),
                SensorSimulator.Identifier.T_UpperRight);

        linkSensors();
    }

    /**
     * Turn the Oven off (regardless of what it is currently doing).
     */
    @Override
    public void off() {
        updateSensors(this.temperature);
        sensorLog.UpdateLogFile();
        ovenStatus = false;
        turnOffSensors();
    }

    /**
     * Turn the Oven on.
     */
    @Override
    public void on() {
        lock = new ReentrantLock();
        ovenStatus = true;
        turnOnSensors();
    }


    /**
     * Cook (whatever is in the Oven) at the current temperature for a given amount of time.
     * <p>
     * This method blocks until the given amount of time has passed.
     *
     * @param minutes The amount of (simulated) time to cook for
     * @return false if the oven was turned off early; true otherwise
     */
    @Override
    public synchronized boolean cookFor(int minutes) {
        int div = 10;
        long mseconds = (minutes / div) * msec;//1000

        return timer(mseconds);
    }

    /**
     * Cook (whatever is in the Oven) until the internal temperature (of whatever is in the Oven)
     * reaches the desired temperature.
     * <p>
     * This method blocks until the internal temperature has been reached.
     *
     * @param internalTemperature The desired internal temp of the food
     * @return false if the oven was turned off early; true otherwise
     */
    @Override
    public synchronized boolean cookUntil(int internalTemperature) {
        // TODO Auto-generated method stub
        int div = 50;
        long mseconds = (internalTemperature / div) * msec;//1000
        updateSensors(internalTemperature);

        boolean t_result =  timer(mseconds);

        if(t_result){
            this.temperature = internalTemperature;
            updateSensors(this.temperature);
        }

        return t_result;
    }

    /**
     * Set the temperature of the Oven.
     * <p>
     * This method blocks until the given temperature is reached.
     *
     * @param temperature The desired internal temp of the food
     * @return false if the oven was turned off early; true otherwise
     */
    @Override
    public synchronized boolean setTemperature(int temperature) {
        int div = 100;
        long mseconds = (temperature / div) * msec;//1000

        boolean t_result = timer(mseconds);

        if(t_result){
            this.temperature = temperature;
        }
        updateSensors(this.temperature);
        return t_result;
    }

    /**
     * This method locks until the current amount of time given is passed or oven is turned off.
     *
     * @param milliseconds amount of time to wait.
     * @return true if ovenStatus is on, false if off.
     */
    private synchronized boolean timer(long milliseconds) {
        lock.lock();
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0L;
        while (elapsedTime < milliseconds && ovenStatus) {
            elapsedTime = System.currentTimeMillis() - startTime;
        }
        lock.unlock();

        return ovenStatus;
    }


    private void turnOnSensors() {
        for(int i = 0; i < sensors.length;i++){
            sensors[i].setSensorState(true);
        }
    }

    private void turnOffSensors() {
        for(int i = 0; i < sensors.length;i++){
            sensors[i].setSensorState(false);
        }
    }


    private void updateSensors(int temperature){
        for(int i = 0; i < sensors.length;i++){
            sensors[i].updateTemperature(temperature);
        }
    }

    private void linkSensors(){
        for(int i = 0 ; i < sensors.length;i++){
            sensors[i].linkLogger(sensorLog);
        }
    }

}
