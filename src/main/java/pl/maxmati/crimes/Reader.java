package pl.maxmati.crimes;

import java.io.*;
import java.util.Arrays;

public class Reader {
    public static final int LINES_AT_ONCE = 25;
    public static final int BUFFER_SIZE = 5*1024*1024;
    public int line = 0;

    byte[] cb = new byte[BUFFER_SIZE];
    int currentChar=0;
    int nChar=0;

    private InputStream inputStream;

    public void open(String file){
        inputStream = System.in /*new BufferedInputStream(System.in)*/;
//        try {
//            inputStream = new FileInputStream(file);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }
    public byte[] getLine() {
        line++;
        ByteArrayOutputStream buffer = null;
        try{
            while (true) {
                if (currentChar >= nChar) {
                    nChar = inputStream.read(cb, 0, BUFFER_SIZE);
                    if(nChar == -1) {
                        if (buffer != null)
                            return buffer.toByteArray();
                        else
                            return null;
                    }
                    currentChar = 0;
                }

                int startChar = currentChar;
                int stopChar = -1;
                boolean eol = false;

                for (;currentChar < nChar; currentChar++) {
                    if (cb[currentChar] == '\n') {
                        stopChar = currentChar++;
                        eol = true;
                        break;
                    }
                }

                if(eol && stopChar-1 > 0 && cb[stopChar-1] == '\r')
                    --stopChar;

                if (eol) {
                    if (buffer == null) {
                        return Arrays.copyOfRange(cb,startChar, stopChar);

                    } else {
                        buffer.write(cb, startChar, stopChar);
                        return buffer.toByteArray();
                    }
                } else {
                    if (buffer == null)
                        buffer = new ByteArrayOutputStream(300);
                    buffer.write(cb, startChar, currentChar - startChar);
                }
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public synchronized Lines getLines(Lines old){
        old.data = new byte[LINES_AT_ONCE][];

        for (int i = 0; i < LINES_AT_ONCE; ++i) {
            old.data[i] = getLine();
        }

        old.counter = line;

        return old;
    }
    public void close(){
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Lines {
        public byte[][] data;
        public int counter;
    }
}
