import hardware.Oven;
import hardware.OvenSimulator;

/**
 * PA4
 */
public class PA4Driver {

    public static void main(String[] args){
        Oven oven = new OvenSimulator();
        oven.on();
        oven.setTemperature(300);
        oven.off();
    }

}
