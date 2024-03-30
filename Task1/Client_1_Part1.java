package Task1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

//Maryam Mohammed Ali 1079679
//Omnia Osama Ahmed 1084505
//Nourhan Ahmed Elmehalawy 1078096
public class Client_1_Part1 {
    public static void main(String[] args) {

        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket();
            InetAddress aHost = InetAddress.getByName(args[0]);
            int serverPort = 20000;

            /*
            args[0] = ip address of host.
            args[1] = block number
            args[2] = data in block (String).
            args[3] = count of leading zeros.
            */

            int blockNumber = Integer.parseInt(args[1]);
            String data = args[2];
            int leadingZeros = Integer.parseInt(args[3]);

            Block_3_77 block = new Block_3_77(blockNumber, data, leadingZeros);

            //Converting the block object into a JSON string representation..
            String myBlock = Block_3_77.getJson(block);
            byte[] m = myBlock.getBytes();

            DatagramPacket request = new DatagramPacket(m, myBlock.length(), aHost, serverPort);
            aSocket.send(request);

            byte[] buffer = new byte[1000];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            aSocket.receive(reply);

            System.out.println("----------------------------------------------------------");
            System.out.println("Reply: " + new String(reply.getData(), 0, reply.getLength()));
            System.out.println("----------------------------------------------------------");


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