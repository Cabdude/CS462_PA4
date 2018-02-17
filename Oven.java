package hardware;

import java.util.*;

/**
 * The requirements of an Oven
 *
 * Note: All methods are intended to be used synchronously.
 * Hence, those methods that take a significant amount of time 
 * (e.g., cookFor()) must block the calling thread of execution 
 * until they complete.
 *
 * @author  Prof. David Bernstein, James Madison University
 * @verison 1.0
 */
public interface Oven
{
    /**
     * Turn the Oven off (regardless of what it is currently doing)
     */
    public abstract void off();


    /**
     * Turn the Oven on.
     */
    public abstract void on();


    /**
     * Cook (whatever is in the Oven) at the current temperature
     * for a given amount of time.
     *
     * This method blocks until the given amount of time has passed.
     *
     * @param minutes   The amount of (simulated) time to cook for
     * @return false if the oven was turned off early; true otherwise
     */
    public abstract boolean cookFor(int minutes);


    /**
     * Cook (whatever is in the Oven) until the internal temperature
     * (of whatever is in the Oven) reaches the desired temperature.
     *
     * This method blocks until the internal temperature has been reached.
     *
     * @param internalTemperature   The desired internal temp of the food
     * @return false if the oven was turned off early; true otherwise
     */
    public abstract boolean cookUntil(int internalTemperature);


    /**
     * Set the temperature of the Oven. 
     *
     * This method blocks until the given temperature is reached.
     *
     * @param internalTemperature   The desired internal temp of the food
     * @return false if the oven was turned off early; true otherwise
     */
    public abstract boolean setTemperature(int temperature);
}
