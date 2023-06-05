import processing.core.PApplet;

public class App extends PApplet {
    public final int fps = 30;
    public static App ctx;
    public static TrailMap tmap;
    public static DataMap dmap;
    public final int width = 1920;
    public final int height = 1080;
    public final int GRID = 1; //in pixels
    public final float pharamone = 100f;
    public final float agent_speed = 1f;
    public final float collision_buffer = 2f;
    public final float sensor_distance = 10f;
    public final float sensor_size = 2f;
    public final float sensor_angle = PApplet.PI / 4;
    public final float rotation_angle = PApplet.PI / 4;
    public final float decay_rate = 0.995f;
    public final int blur_kernel_size = 3;
    public final int num_of_particles = 3000;
    public int clock = 0;
    
    public static void main(String[] args) {
        PApplet.main(App.class);
    }
    public void setup() {
        ctx = this;
        tmap = new TrailMap(ctx);
        dmap = new DataMap(ctx, this.num_of_particles);
        frameRate(fps);
    }

    public void settings() {
        size(width * GRID, height * GRID);
    }
    
    public void draw(){
        if (clock % 20 == 0){
            long start = System.currentTimeMillis();
            tmap.draw();
            long end = System.currentTimeMillis();
            long elapsed = end - start;
            System.out.println("draw update took " + elapsed + "ms to execute");
        }
        
        
        //dmap.draw();
        long start2 = System.currentTimeMillis();
        tmap.update(dmap);
        long end2 = System.currentTimeMillis();
        long elapsed2 = end2 - start2;
        System.out.println("dmap update took " + elapsed2 + "ms to execute");
        dmap.update(tmap);
        clock ++;
        
    }
}

