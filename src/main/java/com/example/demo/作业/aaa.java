import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 计算给定函数 y=1/x 在定义域 [1,100]上围城与X轴围成的面积，计算步长0.01
 *
 * @author junfengchen
 *
 */
public class aaa {

    public static class NodeArea extends RecursiveTask<Float> {

        private static final long serialVersionUID = -8437583391117009271L;
        public static final float THRESHOLD = 0.01f;
        public static final int SLIC_NUM = 10;

        public static AtomicInteger counter = new AtomicInteger(0);

        private float startLoc = 0.0f;
        private float endLoc = 0.0f;



        public NodeArea(float startLoc, float endLoc) {
            this.startLoc = startLoc;
            this.endLoc = endLoc;
        }

        @Override
        protected Float compute() {

            float sum = 0.0f;

            boolean canCompute = (endLoc - startLoc)<= THRESHOLD;

            if (canCompute) {
                sum = calc(startLoc, endLoc);
                counter.incrementAndGet();
                System.out.println(counter + "--> calc result between " + (startLoc  ) + " -- " + (endLoc));
            }
            else {
                float step = (endLoc - startLoc)/SLIC_NUM;

                for (int i=0; i<SLIC_NUM; i++) {
                    NodeArea task = new NodeArea(startLoc + i*step, startLoc+ (i+1)*step);
                    task.fork();

                    sum += task.join();
                }
            }

            return sum;
        }

        private float calc(float startXLoc, float endXLoc) {
            float rectArea = 1/(endXLoc) * THRESHOLD;
            float trigleArea = 0.5f * (1/startXLoc - 1/endXLoc)*0.01f;

            return rectArea + trigleArea;
        }
    }

    public static void main(String[] args) {

        NodeArea nodeA1 = new NodeArea(1, 100);
        ForkJoinPool pool = new ForkJoinPool();

        Future<Float> result = pool.submit(nodeA1);
        long startPoint = System.currentTimeMillis();
        try {
            System.out.println("Final Area:" + result.get());
            System.out.println("The whole process consumes " + (System.currentTimeMillis() - startPoint) + " ms");
        } catch (InterruptedException | ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}