package pl.maxmati.crimes;

import com.google.common.collect.MinMaxPriorityQueue;

public class Container {
    MinMaxPriorityQueue<Entry> data;

    public void setSize(int maxSize) {
        data = MinMaxPriorityQueue.maximumSize(maxSize).expectedSize(maxSize).create();
    }
    public synchronized void put(Entry entry) {
        data.add(entry);
    }
    public synchronized Entry put(int id, double distance, int lineId, Entry entry) {
        if (entry == null)
            entry = new Entry(id,distance,lineId);
        else {
            entry.id = id;
            entry.distance = distance;
            entry.lineId = lineId;
        }
        if(data.offer(entry))
            entry = null;
        return entry;
    }

    public MinMaxPriorityQueue<Entry> getList(){
        return data;
    }

    static class Entry implements Comparable<Entry>{
        public int id;
        public double distance;
        public int lineId;
        public Entry(int id, double distance, int lineId){
            this.id = id;
            this.distance = distance;
            this.lineId = lineId;
        }

        @Override
        public int compareTo(Entry e) {
            if(distance > e.distance)
                return 1;
            else if(distance < e.distance)
                return -1;
            else {
                if (lineId > e.lineId)
                    return 1;
                else
                    return -1;
            }
        }
    }
}
