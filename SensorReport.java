package hardware;

/**
 * PA4
 */
public class SensorReport {

    int temperature;
    long timestamp;
    SensorSimulator.Identifier id;
    public SensorReport(int temperature, long timestamp, SensorSimulator.Identifier id){
        this.temperature = temperature;
        this.timestamp = timestamp;
        this.id = id;
    }

    public String toString(){
        return String.format("{Temperature:%d | Timestamp:%d | Sensor ID: %s }",temperature,timestamp,id.getId());
    }


}
