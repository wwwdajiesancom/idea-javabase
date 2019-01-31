package www.loujie.sync;

/**
 * synchronized的用法
 *
 * @name loujie
 * @date 2019/1/28
 */
public class SyncTestDemo {

    public void abc() {

    }
}

class TestSyncMethod {

    private static int i = 0;

    public synchronized void add() {
        i++;
    }

}
