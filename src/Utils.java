public class Utils {
    //utils
    public static float shift(float x, float size) {
        if (x < 0) {
            return x + size;
        }
        if (x >= size) {
            return x - size;
        }
        return x;
    }
    public static int shift(int x, int size) {
        if (x < 0) {
            return x + size;
        }
        if (x >= size) {
            return x - size;
        }
        return x;
    }
    
}
