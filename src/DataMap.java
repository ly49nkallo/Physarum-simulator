import processing.core.PVector;
import processing.core.PApplet;
import java.util.Random;


public class DataMap{
    Random random = new Random();
    private App ctx;
    public Particle[] particles;
    // keeps track of the agents (particles)
    public DataMap (App ctx, int num_of_particles) {
        this.ctx = ctx;
        this.particles = new Particle[num_of_particles];
        for (int i = 0; i < num_of_particles; i++) {
            this.particles[i] = new Particle(
                ctx, 
                new PVector(
                    random.nextFloat() * (float)ctx.width, 
                    random.nextFloat() * (float)ctx.height),
                PVector.mult(PVector.random2D(), ctx.agent_speed)
            );
        }
    }
    public void draw() {
        for (Particle p : this.particles){
            p.draw();
        }
    }
    float direction(Random rnd, float C, float L, float R )  {
        if (C > L && C > R) {
            return 0;
        } else if (C < L && C < R) {
            return (float)((rnd.nextInt()&1)<<1 - 1);
        } else if (L < R) {
            return 1;
        } else if (R < L) {
            return -1;
        }
        return 0;
    }
    public void update(TrailMap tmap) {
        for (Particle p : this.particles) {
            //(1)sense
            float pa = p.get_angle();
            float sa = ctx.sensor_angle;
            float ra = ctx.rotation_angle;
            float sd = ctx.sensor_distance;
            float xc = Utils.shift(p.pos.x + PApplet.cos(pa) * sd, ctx.width);
            float yc = Utils.shift(p.pos.y + PApplet.sin(pa) * sd, ctx.height);
            float xl = Utils.shift(p.pos.x + PApplet.cos(pa - sa) * sd, ctx.width);
            float yl = Utils.shift(p.pos.y + PApplet.sin(pa - sa) * sd, ctx.height);
            float xr = Utils.shift(p.pos.x + PApplet.cos(pa + sa) * sd, ctx.width);
            float yr = Utils.shift(p.pos.y + PApplet.sin(pa + sa) * sd, ctx.height);

            float C = tmap.density(xc, yc);
            float L = tmap.density(xl, yl);
            float R = tmap.density(xr, yr);
            
            //(2)rotate
            float da = ra * direction(this.random, C, L, R);
            float angle = Utils.shift(p.get_angle() + da, 2 * PApplet.PI);
            p.vel.x = PApplet.cos(angle) * ctx.agent_speed;
            p.vel.y = PApplet.sin(angle) * ctx.agent_speed;
            p.update();
        }
    }
}
