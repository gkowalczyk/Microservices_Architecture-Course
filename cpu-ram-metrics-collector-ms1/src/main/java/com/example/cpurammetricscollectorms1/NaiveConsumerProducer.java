package com.example.cpurammetricscollectorms1;

import java.time.Duration;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class NaiveConsumerProducer {

    private static final Random generator = new Random();
    private static final Queue<String> queue = new LinkedList<>();

    public static void main(String[] args) {

        int itemCount = 5;
        Thread producer = new Thread(() -> {
            for (int i = 0; i < itemCount; i++) {
                try {
                    Thread.sleep(Duration.ofSeconds(generator.nextInt(5)).toMillis());
                } catch (InterruptedException interruptedException) {
                    throw new RuntimeException(interruptedException);
                }
                String item = "Item no" + i;
                synchronized (queue) {

                    queue.add(item);
                    queue.notifyAll();
                }
                System.out.println("Producent add item" + item);
            }
        });

        Thread consumer = new Thread(() -> {
            int itemsLeft = itemCount;
            while (itemsLeft > 0) {
                String item;
                synchronized (queue) {
                   while(queue.isEmpty()) {
                       try {
                           queue.wait();
                       } catch (InterruptedException interruptedException) {
                           throw new RuntimeException(interruptedException);
                       }
                   }
                    item = queue.poll();
                }

                itemsLeft--;
                System.out.println("Consumer got item" + item);
            }
        });
        consumer.start();;
        producer.start();

    }
}
