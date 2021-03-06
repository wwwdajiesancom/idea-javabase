多线程:
    进程、线程；
    进程：程序的执行过程，持有资源与线程。（是一个动态的）
    线程：线程是进程中的最小执行单元，同一个进程中可以存在多个线程，它们共用线程的资源

    常用实现方式：
        Thread,Runnable实现多线程

        volatile,保证了内存数据安全
        线程的方法:
            start(),启动线程
            join(),使其它线程等待当前线程执行完毕
            yield(),静态方法，当前线程释放资源，再去重新竞争资源

        错误停止线程：
            stop();//它是jdk1.1的时候推出的,现在已经停用了
            interrupt();//它也不能正确的退出线程，调用它之后，如果调用了sleep,wait等方法了，会出现异常

synchronized:
    它保证了同一时刻，最多只有一个线程可以执行此段代码，保证了线程安全性
    JVM会自动的通过使用monitor来加锁和解锁，保证了同时只有一个线程可以执行指定代码，从而保证了线程安全，同时具有可重入和不可中断的性质。
    他从原理、功能、特性上阐述了synchronized。


    例子：会出现的问题,
        count++;
        // 它的执行步骤：1.从缓存中获取；2，count+1;3.放入缓存
        // 多线程执行会出现，消失的请求

    线程的执行方式：串行，并行

    synchronized，有以下几种用法：
        1.对象锁：分为两种
            a.代码块锁,[需要手动指定要锁定的对象,]
            b.方法锁,[默认锁定的是this]
        2.类锁：也分为两种
            a.静态方法锁，他锁的是.class对象
            b.锁住类.class对象

        注意：对象锁与类锁它们锁定的对象是不一样，一般来说是互不干扰的

    synchronized，的一些特性：
        1.不可以被继承
        2.抛出异常，自动解锁
        3.可重入，指同一个线程的外层函数获得锁之后，内层函数可以直接的获取锁，而不需要等待；一个synchronized方法1调用了另外一个synchronized方法2；方法1叫做外层函数，方法2叫做内层函数
            它的优点是:1.避免了死锁;2.提升了封装性,减轻了代码量,不需要去加锁、解锁了
            可重入的粒度[scope],就是范围同一个线程
        4.不可中断，一个线程获取了这个对象锁，其它的线程对于这个对象锁，必须是等待，直到第一个线程释放了锁，如果不释放的话，就只能一直等待了
            对于这个特性，主要是针对Lock类的,他有中断其它线程的方法、或退出当前线程的方法操作，就是说如果等待时间过长，而，我可以中断已获得锁的线程或自己退出

    线程状态：

    synchronized原理:
        monitor,
        javap -verbose *.class;// 这样可以看到这个类的字节码文件

        用到了一个计数器，当是0时，可以获取锁；获取锁之后+1，如果有重入的方法，就再+1，直到退出计数器变成0；变成0后，其它的也就可以获取锁了；

        缺陷：
            效率低：
                1，锁的释放情况少【代码执行完成、代码出现异常退出】
                2，视图获取锁的时候，不能设置一个超时时间【在另一个获得锁的线程执行完毕前，无法获取锁，只能一直等待】
                3，不能中断一个视图获得锁的线程【】
            不够灵活：
                1.加锁和释放锁的时机比较单一
                2.每个锁都有单一的条件(锁住的对象)
                3.锁无法控制，我们不知道是否获取锁成功


        问题:
            1.什么是锁的升级、降级？什么是JVM中的偏斜锁、轻量级锁、重量级锁？
            2.多个线程等待同一个synchronized的锁，JVM用什么样的算法来决定他们的优先级呢？

