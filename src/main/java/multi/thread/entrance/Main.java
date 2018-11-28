package multi.thread.entrance;


import multi.thread.consume.Consumer;
import multi.thread.consume.ConsumerOne;
import multi.thread.produce.Producer;
import multi.thread.produce.ProducerOne;
import multi.thread.result.ResultList;
import multi.thread.threadpool.ConsumeThread;
import multi.thread.threadpool.ProduceThread;

public class Main {
    public static void main(String[] args) {
        ResultList resultList = new ResultList();
        Consumer consumer = new ConsumerOne("con1",resultList);
        Producer producer = new ProducerOne("pro1",resultList);

        ConsumeThread consumeThread =  new ConsumeThread(consumer,resultList);
        ProduceThread produceThread = new ProduceThread(producer,resultList);

        while (true) {
            consumeThread.run();
            produceThread.run();
        }
    }
}
