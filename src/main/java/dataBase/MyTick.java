package dataBase;

import java.time.LocalDateTime;

public class MyTick {
    public LocalDateTime time;
    public long value;

    public MyTick(LocalDateTime time, long value) {
        this.time = time;
        this.value = value;
    }
}
