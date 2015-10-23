package pl.maxmati.crimes;

import com.google.common.collect.MinMaxPriorityQueue;
import javolution.text.CharArray;

public class Worker implements Runnable {

    private Point origin;
    private Container cont;
    private Reader reader;


    public Worker(Point origin, int amount, Reader reader){
        this.origin = origin;
        this.cont = new Container();
        cont.setSize(amount);
        this.reader = reader;
    }

    public MinMaxPriorityQueue<Container.Entry> getList(){
        return cont.getList();
    }
    @Override
    public void run() {
        Point p = null;

        Reader.Lines lines = new Reader.Lines();

        lines = reader.getLines(lines);
        int i = 0;

        CharArray[] tokens = new CharArray[3];
        Container.Entry entry = null;
        while (lines.data[i] != null) {

            if (!split(lines.data[i], tokens)) {
                if(++i >= Reader.LINES_AT_ONCE){
                    lines = reader.getLines(lines);
                    i = 0;
                }
                continue;
            }

            p = Point.make(tokens[1].toDouble(), tokens[2].toDouble(), p);
            entry = cont.put(tokens[0].toInt(), origin.distance(p), lines.counter + i, entry);

            if(++i >= Reader.LINES_AT_ONCE){
                lines = reader.getLines(lines);
                i = 0;
            }
        }
    }

    private char[] toChar(byte[] bytes, int start, int length){
        char[] chars = new char[length];
        for (int i = 0; i < length; i++)
            chars[i] = (char) bytes[i+start];

        return chars;
    }
    private boolean split(byte[] line, CharArray[] out){
        for (int i = 0; i < 3; i++) {
            if(out[i] == null)
                out[i] = new CharArray();

        }
        int counter = 0;
        int start = 0;
        boolean count = true;
        int length = line.length;

        for(int i = 0; i < length; ++i){
            byte c = line[i];
            if(c != ',' && c != '"')
                continue;
            if(c == '"')
                count = !count;
            else if(count)
                ++counter;
            if(counter == 1)
                out[0].setArray(toChar(line, start, i-start),0, i-start);
            else if (counter == 19)
                start = i +1;
            else if (counter == 20){
                if(start != i)
                    out[1].setArray(toChar(line, start, i-start),0, i-start);
                else
                    return false;
                start = i + 1;
            }else if(counter == 21){
                if(start != i)
                    out[2].setArray(toChar(line, start, i-start),0, i-start);
                else
                    return false;
                break;
            }
        }

        return true;
    }


}
