package world.ucode;

public class Speed {
    public long t = System.currentTimeMillis();
    public boolean getSpeed(long millis) {
        long new_t = System.currentTimeMillis();
        if (new_t - t >= millis) {
            t = new_t;
            return true;
        }
        return false;
    }
}