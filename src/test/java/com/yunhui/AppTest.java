package com.yunhui;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Sets;
import com.yunhui.core.uid.PreAsyReloadAtomicUidImpl;
import com.yunhui.core.uid.RealTimeSynReloadAtomicUidImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Title: AppTest.java <br>
 * Description: <br>
 * Create DateTime: 2019年04月24日 15:42 <br>
 *
 * @author yun
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class AppTest {

    @Autowired
    PreAsyReloadAtomicUidImpl preAsyReloadAtomicUid;


    @Autowired
    RealTimeSynReloadAtomicUidImpl realTimeSynReloadAtomicUid;

    @Test
    public void testPreAsyReloadAtomicUid() throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(1000);

        Set<Long> set = Sets.newConcurrentHashSet();

        AtomicLong setSize = new AtomicLong(0);

        AtomicBoolean loopSwitch = new AtomicBoolean(true);

        for (int i = 0; i < 100; i++) {

            executorService.submit(() -> {
                while (loopSwitch.get()) {

                    long uid = preAsyReloadAtomicUid.getUid();
//                        System.out.println(Thread.currentThread().getName() + ":" + uid);
                    set.add(uid);
                    setSize.incrementAndGet();

                }
            });

        }
        Stopwatch stopwatch = Stopwatch.createStarted();
        //跑1分钟 观察能get多少个号
        while (stopwatch.elapsed(TimeUnit.MILLISECONDS) < 1 * 60 * 1000) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        loopSwitch.set(false);

        Thread.sleep(3000L);

        System.out.println("awaitTermination set.size:" + set.size());
        System.out.println("awaitTermination setSize:" + setSize);
        System.out.println("awaitTermination curId:" + preAsyReloadAtomicUid.getCurrent().get());
        System.out.println("awaitTermination maxId:" + preAsyReloadAtomicUid.getUidBuffer());


    }


    @Test
    public void testRealTimeSynReloadAtomicUid() throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(1000);

        Set<Long> set = Sets.newConcurrentHashSet();

        AtomicLong setSize = new AtomicLong(0);

        AtomicBoolean loopSwitch = new AtomicBoolean(true);

        for (int i = 0; i < 100; i++) {

            executorService.submit(() -> {
                while (loopSwitch.get()) {

                    long uid = realTimeSynReloadAtomicUid.getUid();
//                        System.out.println(Thread.currentThread().getName() + ":" + uid);
                    set.add(uid);
                    setSize.incrementAndGet();

                }
            });

        }
        Stopwatch stopwatch = Stopwatch.createStarted();
        //跑1分钟 观察能get多少个号
        while (stopwatch.elapsed(TimeUnit.MILLISECONDS) < 1 * 60 * 1000) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        loopSwitch.set(false);

        Thread.sleep(3000L);

        System.out.println("awaitTermination set.size:" + set.size());
        System.out.println("awaitTermination setSize:" + setSize);
        System.out.println("awaitTermination curId:" + realTimeSynReloadAtomicUid.getCurrentUid().get());
        System.out.println("awaitTermination maxId:" + realTimeSynReloadAtomicUid.getUidBuffer());


    }
}
