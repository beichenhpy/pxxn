package com.beichen.pxxn.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;
/**@author A51398
 * Springboot 开启后会自动加载执行这个方法！
 */
@Slf4j
@Component
public class InitQueue implements ApplicationListener<ContextRefreshedEvent> {
    public static LinkedBlockingQueue<Integer> linkedBlockingQueue;
    public static LinkedBlockingQueue<Integer> linkedBlockingQueueTmp;
    private static final Integer start = 0;
    private static final Integer end = 2200;
    private static final Integer size = 2200;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        linkedBlockingQueue = new LinkedBlockingQueue<>();
        //每次上限800
        for (int i = start; i < end; i++) {
            try {
                linkedBlockingQueue.put(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(new HandleMessage());
            //每次处理10个
            linkedBlockingQueueTmp = new LinkedBlockingQueue<>();
            for (int j = 0; j < 1; j++) {
                try {
                    linkedBlockingQueueTmp.put(linkedBlockingQueue.take());
                    log.info("队列剩余：{}",linkedBlockingQueue.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            thread.start();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
