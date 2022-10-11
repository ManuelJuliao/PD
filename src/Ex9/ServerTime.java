package Ex9;

import java.io.Serial;
import java.io.Serializable;

public class ServerTime implements Serializable {

    @Serial
    private final static long serialVersionUID = 2L;
    private int hours;
    private int minutes;
    private int seconds;

    public ServerTime(int hours, int minutes, int seconds){
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    @Override
    public String toString(){
        return hours + ":" + minutes + ":" + seconds;
    }
}
