import processing.core.PVector;
import processing.core.PApplet;

public class TrailMap {
    public float[] data;
    private App ctx;

    public TrailMap(App ctx) {
        this.data = new float[ctx.width * ctx.height];
        this.ctx = ctx;
        setup();
    }
    private void setup() {
    }
    public void set_trail (int i, int j, float v) {
        data[i * ctx.width + j] = v;
    }
    public float get_trail(int i, int j){
        return data[i * ctx.width + j];
    }
    public void print_trail(int i, int j){
        System.out.println(
            String.format("Data at (%i, %i) is %f", i, j, get_trail(i, j))
        );
    }
    public void draw(){
        // this thing is f**king slow
        for(int i = 0; i < ctx.height; i++){
            for(int j = 0; j < ctx.width; j++){
                // some idiot reversed i and j in the source code ... 
                float x = j * ctx.GRID;
                float y = i * ctx.GRID;
                float d = PApplet.constrain(get_trail(i,j), 0, 255f);
                ctx.fill(d);
                ctx.noStroke();
                ctx.square(x, y, ctx.GRID);
            }
        }
    }
    public float density(float x, float y) {
        float ss = ctx.sensor_size;
        int left_bnd =  PApplet.floor(x - ss);
        int right_bnd = PApplet.floor(x + ss);
        int upper_bnd = PApplet.floor(y - ss);
        int lower_bnd = PApplet.floor(y + ss);
        float tmp = 0f;
        for (int i = upper_bnd; i <= lower_bnd; i++) {
            for (int j = left_bnd; j <= right_bnd; j++) {
                tmp += get_trail(Utils.shift(i, ctx.height), Utils.shift(j, ctx.width));
            }
        }
        return tmp;
    }
    public void update(DataMap dmap) {
        
        //(1)sense
        //(2)rotate
        
        for (Particle p : dmap.particles){
            int i = PApplet.floor(Utils.shift(p.pos.y, ctx.height));
            int j = PApplet.floor(Utils.shift(p.pos.x, ctx.width));
            //(4)deposit
            set_trail(i, j, get_trail(i, j) + ctx.pharamone);
        }
        //(5)diffuse
        //let (i,j) be the upper left corner of the kernel
        //this thing needs to get multithreaded asap
        int n = 6;
        if ((float) ctx.width / (float) n % 1 > 0.01) {System.out.println("F*CK"); return;}
        Thread[] threads = new Thread[n];
        for (int i = 0; i < n; i++){
            threads[i] = new Thread(new Worker(ctx, this, 1080, 1920/n, 1920/n*i, 0));
        }
            for (Thread t : threads) {
            t.start();
        }
        try {
            for (Thread t : threads){
                t.join();
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        //(6)decay
        for (int i = 0; i < this.data.length; i++){
            this.data[i] *= ctx.decay_rate;
            if (this.data[i] < 1) this.data[i] = 0;
        }
        
    }
}
