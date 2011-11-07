import java.io.*;
import java.net.*;

public class ToyClient {
    private static final int DEFAULT_SERVER_PORT = 1025;
    private static final String DEFAULT_SERVER_NAME = "localhost";

    private static Socket s;

    private static PrintStream output;

    public ToyClient()
        throws IOException {
        this.s = new Socket ("localhost", 1025);
        this.output = new PrintStream (s.getOutputStream());
    }

    public static void main (String args[])
    {
        ToyClient client;
        PrintStream output;

        /* read from file related */
        FileInputStream inStream;
        DataInputStream inData;
        BufferedReader inBuffer;
        String line;

        try {
            client = new ToyClient();
            output = client.output;

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

        } catch (IOException e) {
            System.out.println (e);
        }

    }
}