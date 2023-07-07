import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UdpClient {
    public static final int DEST_PORT = 30001;
    public static final String DEST_IP = "127.0.0.1";
    private static final int DATA_LEN = 4096;
    byte[] inBuff = new byte[DATA_LEN];
    private DatagramPacket packet_in = new DatagramPacket(inBuff, inBuff.length);
    private DatagramPacket packet_out = null;

    public void start() throws IOException {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress ip = InetAddress.getByName(DEST_IP);
            packet_out = new DatagramPacket(new byte[0], 0, ip, DEST_PORT);
            try (Scanner sc = new Scanner(System.in)) {
                System.out.println("Por favor ingrese los datos:");
                String key = null;
                byte[] keyBuff = null;
                while (sc.hasNextLine()) {
                    key = sc.nextLine();
                    if ("exit".equals(key)) {
                        break;
                    }
                    keyBuff = key.getBytes("UTF-8"); // Especifica la codificación como UTF-8
                    packet_out.setData(keyBuff);
                    socket.send(packet_out);
                    socket.receive(packet_in);
                    String response = new String(inBuff, 0, packet_in.getLength());
                    System.out.println("Respuesta del servidor: " + response);
                    System.out.println("Por favor ingrese los datos:");
                }
            }
            System.out.println("=== Salida del cliente ===");
        }
    }

    public static void main(String[] args) throws IOException {
        new UdpClient().start();
    }
}
