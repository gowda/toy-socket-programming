import java.io.*;
import java.net.*;

public class SClient {
    public static void main (String args[])
    {
        Socket s;
        PrintStream output;

        /* read from file related */
        FileInputStream inStream;
        DataInputStream inData;
        BufferedReader inBuffer;
        String line;

        try {
            s = new Socket ("localhost", 1025);
            output = new PrintStream (s.getOutputStream());

            /* input related */
            inStream = new FileInputStream ("/etc/passwd");
            inData = new DataInputStream (inStream);
            inBuffer = new BufferedReader (new InputStreamReader (inData));

            while ((line = inBuffer.readLine()) != null) {
                output.println (line);
            }

            output.flush();


            /* input related */
            inStream.close();

            output.close();
            s.close();

        } catch (IOException e) {
            System.out.println (e);
        }

    }
}