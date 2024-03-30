package Task2_Omnia;

import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Client2_Part2
{
    public static void main(String[] args) {


        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket();
            InetAddress aHost = InetAddress.getByName(args[0]);
            int serverPort = 20000;

            int blockNumber = Integer.parseInt(args[1]);
            String data = args[2];
            int leadingZeros = Integer.parseInt(args[3]);

            //P.S Since ours applies one block at a time, we could remove the array below and directly use getJson(block)
            Block_3_77[] Blocks = new Block_3_77[]
                    {
                            new Block_3_77(blockNumber, data, leadingZeros)
                    };

            for (Block_3_77 block : Blocks) {
                String myBlock = Block_3_77.getJson(block);

                byte[] m = myBlock.getBytes();

                DatagramPacket request = new DatagramPacket(m, myBlock.length(), aHost, serverPort);
                aSocket.send(request);
                System.out.println("Block successfully sent to load balancer.");

                byte[] buffer = new byte[1000];
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);

                aSocket.receive(reply);

                System.out.println("\n----------------------------------------------------------");
                System.out.println("Reply: " + new String(reply.getData(), 0, reply.getLength()));
                System.out.println("----------------------------------------------------------");
            }

        } catch (SocketException var14) {
            System.out.println("Error Socket: " + var14.getMessage());
        } catch (IOException var15) {
            System.out.println("Error IO: " + var15.getMessage());
        } finally {
            if (aSocket != null) {
                aSocket.close();
            }

        }

    }

}

