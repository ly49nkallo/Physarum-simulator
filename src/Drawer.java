import processing.core.PApplet;

public class Drawer implements Runnable{
    App ctx;
    TrailMap parent;
    int height, width, offsetX, offsetY;

    public Drawer(App ctx, TrailMap parent, int height, int width, int offsetX, int offsetY){
        this.ctx = ctx;
        this.parent = parent;
        System.out.println("Created worker on thread " + Thread.currentThread().getId());
        this.height = height;
        this.width = width;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }
    public void run() {
        for(int i = offsetY; i < height + offsetY; i++){
            for(int j = offsetX; j < width + offsetX; j++){
                // some idiot reversed i and j in the source code ... 
                float x = j * ctx.GRID;
                float y = i * ctx.GRID;
                float d = PApplet.constrain(parent.get_trail(i,j), 0, 255f);
                ctx.fill(0);
                ctx.noStroke();
                ctx.square(x, y, ctx.GRID);
            }
        }
    }
}
