package pl.maxmati.crimes;

public class Main {
    public static void main(String[] args){
        Reader r = new Reader();
        r.open(args.length > 0 ? args[0] : "");
        Scheduler w = new Scheduler(r);
        w.run();
        w.printResult();
    }

}
