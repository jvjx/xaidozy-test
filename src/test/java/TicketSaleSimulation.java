public class TicketSaleSimulation {
    private static int ticketNumber = 100;
    private static final Object LOCK = new Object();

    public static void main(String[] args) {
        for (int i = 1; i <= 5; i++) {
            Thread ticketWindow = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        synchronized (LOCK) {
                            if (ticketNumber <= 0) {
                                break;
                            }
                            System.out.println(Thread.currentThread().getName() + " 售出车票：" + ticketNumber);
                            ticketNumber--;
                        }
                    }
                }
            }, "售票点-" + i);

            ticketWindow.start();
        }
    }
}
