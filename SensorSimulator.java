package hardware;

/**
 * PA4
 */
public class SensorSimulator implements Sensor,Runnable {


    enum Identifier {
        B_UpperLeft("B_UL"),
        B_UpperRight("B_UR"),
        B_LowerLeft("B_LL"),
        B_LowerRight("B_LR"),
        T_UpperLeft("T_UL"),
        T_UpperRight("T_UR"),
        T_LowerLeft("T_LL"),
        T_LowerRight("T_LR");

        String id;

        private Identifier(String id){
            this.id = id;
        }

        public String getId(){
            return this.id;
        }
    }

    private long timestamp;
    private Identifier id;

    private boolean sensorState;

    private int ovenTemperature;

    private SensorLog logger;


    private Thread sensorThread;

    public SensorSimulator(long timestamp,Identifier id){
        this.timestamp = timestamp;
        this.id = id;
        this.sensorState = false;
    }

    /**
     * Each sensor simulator must collect and
     * report on the temperature of the oven simulator every 100 milliseconds.
     *
     * Each report must include a time stamp and a sensor identifier.
     */
    @Override
    public void temperatureReport() {

        long milliseconds = 100;

        while(sensorState){
            timer(milliseconds);
            SensorReport report = new SensorReport(getOvenTemp(),this.timestamp,this.id);
            this.logger.addReport(report);
            this.logger.UpdateLogFile();
            updateTimestamp();
        }
    }


    public void linkLogger(SensorLog logger) {
        this.logger = logger;
    }

    @Override
    public void timer(long milliseconds) {
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0L;
        while (elapsedTime < milliseconds)
        {
            elapsedTime = System.currentTimeMillis() - startTime;
        }
    }

    @Override
    public void updateTemperature(int ovenTemperature) {
        this.ovenTemperature = ovenTemperature;
    }

    public int getOvenTemp(){
       return this.ovenTemperature;
    }


    @Override
    public void updateTimestamp() {
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * To set sensor on or off.
     *
     * @param state set sensor on or off based on oven.
     */
    @Override
    public void setSensorState(boolean state) {
        this.sensorState = state;

        if(!sensorState){
            sensorThread.interrupt();
            sensorThread = null;
        }
        else if (sensorThread == null && sensorState)
        {
            sensorThread = new Thread(this);
            sensorThread.start();
        }
    }


    /**
     *
     */
    @Override
    public void run() {
        temperatureReport();
    }
}
