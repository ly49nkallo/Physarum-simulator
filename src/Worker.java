public class Worker implements Runnable{
    App ctx;
    TrailMap parent;
    int height, width, offsetX, offsetY;

    Worker(
        App ctx, 
        TrailMap parent, 
        int height, 
        int width, 
        int offsetX, 
        int offsetY) {
        this.ctx = ctx;
        this.parent = parent;
        //System.out.println("Created worker on thread " + Thread.currentThread().getId());
        this.height = height;
        this.width = width;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }
    public void run() {
        if (ctx.clock % 2 == 0){
            for (int i = offsetY; i < height + offsetY; i++){
                for (int j = offsetX; j < width + offsetX; j++){
                    float sum = 0;
                    float weight = 0;
                    for (int a = 0; a < ctx.blur_kernel_size; a++) {
                        for (int b = 0; b < ctx.blur_kernel_size; b++){
                            sum += parent.get_trail(
                                Utils.shift(i + a, ctx.height-1), 
                                Utils.shift(j + b, ctx.width-1))
                                ;
                            weight ++;
                        }
                    }
                    float ave = sum / weight;
                    parent.set_trail(
                        Utils.shift(i + (ctx.blur_kernel_size/2), ctx.height),
                        Utils.shift(j + (ctx.blur_kernel_size/2), ctx.width), 
                        ave
                    );
                }
            }
        }
    }
}
