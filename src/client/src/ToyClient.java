import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.net.*;

public class ToyClient {
    private static final int DEFAULT_SERVER_PORT = 1025;
    private static final String DEFAULT_SERVER_NAME = "localhost";

    private static SocketChannel s;

    private static CharsetEncoder encoder;
    private static CharsetDecoder decoder;

    private SocketChannel makeConnection (String name, int port) {
        SocketChannel s = null;

        try {
            s = SocketChannel.open();
            s.connect (new InetSocketAddress (name, port));

        } catch (IOException e) {
            System.err.println (e);
        } finally {
            return s;
        }
    }

    private ByteBuffer stringToByteBuffer (String str)
        throws IOException {
        return encoder.encode (CharBuffer.wrap(str));
    }

    private String byteBufferToString (ByteBuffer buffer)
        throws IOException {
        String data = null;

        data = decoder.decode(buffer).toString();

        return data;
    }

    private String receiveString ()
        throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate (1024);
        int ret = 0;
        String string;

        ret = s.read (buffer);

        buffer.flip();

        if (ret > 0) {
            string = byteBufferToString (buffer);
        } else {
            string = null;
        }

        return string;
    }

    private int sendString (String str)
        throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate (1024);
        int ret = 0;

        buffer.clear();
        buffer = stringToByteBuffer (str);

        ret = s.write (buffer);

        return ret;
    }

    public ToyClient()
        throws IOException {

        Charset charset;

        this.s = makeConnection (DEFAULT_SERVER_NAME,
                                 DEFAULT_SERVER_PORT);

        charset = Charset.forName ("UTF-8");

        this.encoder = charset.newEncoder();
        this.decoder = charset.newDecoder();
    }

    public static void main (String args[])
    {
        ToyClient client;

        /* read from file related */
        FileInputStream inStream;
        DataInputStream inData;
        BufferedReader inBuffer;
        String line;

        String r_line;

        try {
            client = new ToyClient();

            /* input related */
            inStream = new FileInputStream ("/etc/passwd");
            inData = new DataInputStream (inStream);
            inBuffer = new BufferedReader (new InputStreamReader (inData));

            while ((line = inBuffer.readLine()) != null) {
                client.sendString (new String (line + "\n"));
                r_line = client.receiveString ();
            }

            /* input related */
            inStream.close();

        } catch (IOException e) {
            System.out.println (e);
        }

    }
}