package pl.maxmati.crimes;


import com.google.common.collect.MinMaxPriorityQueue;

public class Scheduler {

    private static final int N = 2;
    private Reader reader;

    private Container cont = new Container();

    public Scheduler(Reader reader){
        this.reader = reader;
    }

    public void run(){
        try {
            int amount = Integer.parseInt(new String(reader.getLine()));
            cont.setSize(amount);

            String[] originLatLon = new String(reader.getLine()).split(" ");
            Point origin = Point.make(Double.parseDouble(originLatLon[0]),
                    Double.parseDouble(originLatLon[1]), null);



            Thread[] threads = new Thread[N];
            Worker[] workers = new Worker[N];
            for (int i = 0; i < N; i++) {
                workers[i] = new Worker(origin,amount,reader);
                threads[i] = new Thread(workers[i],String.valueOf(i));
                threads[i].start();
            }

            for (int i = 0; i < N; i++) {
                threads[i].join();
                MinMaxPriorityQueue<Container.Entry> l = workers[i].getList();
                for (Container.Entry en : l)
                    cont.put(en);
            }

            reader.close();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(reader.line);
        }
    }

    public void printResult() {
        Container.Entry e;
        MinMaxPriorityQueue<Container.Entry> list = cont.getList();
        while ((e = list.poll()) != null){
            System.out.println(e.id);
        }
    }
}
