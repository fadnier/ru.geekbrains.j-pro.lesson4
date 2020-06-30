public class Main {
    static volatile int triger = 1;
    static Object monitor = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                repeat(1,2, 'A');
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                repeat(2,3, 'B');
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                repeat(3,1, 'C');
            }
        }).start();
    }

    public static void repeat(int a, int b, char c) {
        synchronized (monitor) {
            while (triger != a) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.print(c);
            triger = b;
            monitor.notifyAll();
        }
    }
}
