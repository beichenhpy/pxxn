package com.beichen.pxxn.request;

import com.beichen.pxxn.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author A51398
 * @version 1.0
 * @description TODO
 * @since 2020/12/9 11:40
 */
@Slf4j
public class HandleMessage extends Thread {
    @Override
    public void run() {
        RequestVideoInfo requestVideoInfo = SpringContextUtils.getBean("requestVideoInfo", RequestVideoInfo.class);
        try {
            log.info("------Tmp队列里的值,{}",InitQueue.linkedBlockingQueueTmp);
            for (int i = 0; i < InitQueue.linkedBlockingQueueTmp.size(); i++) {
                Integer take = InitQueue.linkedBlockingQueueTmp.take();
                requestVideoInfo.test(take);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
