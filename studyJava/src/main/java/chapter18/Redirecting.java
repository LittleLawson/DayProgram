package chapter18;

import java.io.*;

public class Redirecting {
    public static void main(String[] args) {
        PrintStream console = System.out;
        try {
            //get the FileInputStream in BufferedInputStream
            BufferedInputStream in = new BufferedInputStream(new FileInputStream("E:\\intellij_Project\\DayProgram\\studyJava\\src\\main\\java\\chapter18\\Redirecting.java"));

            //A PrintStream adds functionality to another output stream, namely the ability to print representations of various data values conveniently.
            PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream("E:\\intellij_Project\\DayProgram\\studyJava\\src\\main\\resources\\test.out")));

            System.setIn(in);//Reassigns the "standard" input stream.
            System.setOut(out);
            System.setErr(out);

            //get the inputStream to BufferedReader
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String s;
            while ((s = br.readLine()) != null) {
                System.out.println(s);
            }
            out.close();
            System.setOut(console);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
