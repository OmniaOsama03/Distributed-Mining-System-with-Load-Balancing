package TEST;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class Client_1 {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket();
            InetAddress aHost = InetAddress.getByName("172.17.17.111");
            int serverPort = 20000;

            System.out.print("Enter the block number: ");
            int blockNumber = input.nextInt();
            input.nextLine();

            System.out.print("Enter the data in the block: ");
            String data = input.nextLine();


            System.out.print("Enter the number of leading zeros: ");
            int leadingZeros = input.nextInt();
            input.nextLine();

            //P.S Since ours applies one block at a time, we could remove the array below and directly use getJson(block)
            Block_2[] Blocks = new Block_2[]
            {
                    new Block_2(blockNumber, data, leadingZeros)
            };

            for (Block_2 block : Blocks) {

                String myBlock = getJson(block);

                byte[] m = myBlock.getBytes();

                DatagramPacket request = new DatagramPacket(m, myBlock.length(), aHost, serverPort);
                aSocket.send(request);

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

    public static String getJson(Object o) {
        return new GsonBuilder().setPrettyPrinting().create().toJson(o);
    }
}



