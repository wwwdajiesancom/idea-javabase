package com.loujie.www.sync;

/**
 * @name loujie
 * @date 2019/2/20
 */
public class Sync3 implements Runnable {

    private Accounter accounter;

    public Sync3(Accounter accounter) {
        this.accounter = accounter;
    }

    public void run() {
        // 也可以自定义🔒对象，byte[] bytes = new byte[0];它比Object obj = new Object();省空间
        synchronized (this.accounter) {
            this.accounter.add(500);
            this.accounter.add(-300);
            System.out.println(Thread.currentThread().getName() + ":" + this.accounter.getTotalAmount());
        }
    }

    public static class Accounter {

        private int totalAmount = 0;
        private String name;

        public Accounter(String name, int totalAmount) {
            this.name = name;
            this.totalAmount = totalAmount;
        }

        public int add(int amount) {
            this.totalAmount = this.totalAmount + amount;
            return this.totalAmount;
        }

        public int getTotalAmount() {
            return this.totalAmount;
        }

        public String getName() {
            return this.name;
        }
    }

}


