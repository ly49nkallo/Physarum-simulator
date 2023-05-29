import processing.core.PVector;
import processing.core.PApplet;

public class Particle {
    private App ctx;
    public PVector pos;
    public PVector vel;

    private final float size = 10f;
    public Particle(App ctx, PVector pos, PVector vel) {
        this.ctx = ctx;
        this.pos = pos;
        this.vel = vel;
    }
    public void update() {
        pos.x = Utils.shift(pos.x + vel.x, ctx.width);
        pos.y = Utils.shift(pos.y + vel.y, ctx.height);
    }
    public float get_angle() {
        float theta = PApplet.atan2(vel.y, vel.x);
        return theta;
    }

    public void draw() {
        ctx.pushStyle();
        ctx.fill(255, 0, 0);
        ctx.stroke(0);
        ctx.circle(pos.x * ctx.GRID, pos.y * ctx.GRID, size);
        ctx.stroke(255);
        float sa = ctx.sensor_angle;
        ctx.line(
            pos.x * ctx.GRID, 
            pos.y * ctx.GRID,
            (pos.x + PApplet.cos(get_angle()) * ctx.sensor_distance) * ctx.GRID ,
            (pos.y + PApplet.sin(get_angle()) * ctx.sensor_distance) * ctx.GRID
        );
        ctx.stroke(0, 255, 0);
        ctx.line(
            pos.x * ctx.GRID, 
            pos.y * ctx.GRID,
            (pos.x + PApplet.cos(get_angle() - sa) * ctx.sensor_distance) * ctx.GRID ,
            (pos.y + PApplet.sin(get_angle() - sa) * ctx.sensor_distance) * ctx.GRID
        );
        ctx.stroke(0, 0, 255);
        ctx.line(
            pos.x * ctx.GRID, 
            pos.y * ctx.GRID,
            (pos.x + PApplet.cos(get_angle() + sa) * ctx.sensor_distance) * ctx.GRID ,
            (pos.y + PApplet.sin(get_angle() + sa) * ctx.sensor_distance) * ctx.GRID
        );
        ctx.popStyle();
    }
}

