import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;


public class UdpClient {
    // Definir el destino para enviar el datagrama
    public static final int DEST_PORT = 30000;

    public static final String DEST_IP = "127.0.0.1";
    // Define el tamaño máximo de cada datagrama como 4K
    private static final int DATA_LEN = 4096;
    // Definir la matriz de bytes para recibir datos de la red.
    byte[] inBuff = new byte[DATA_LEN];


    // Cree un objeto DatagramPacket que acepte datos de respuesta
    private DatagramPacket packet_in = new DatagramPacket(inBuff, inBuff.length);

    // Definir un objeto DatagramPacket para enviar
    private DatagramPacket packet_out = null;



    public void start() throws IOException {
        try (DatagramSocket socket = new DatagramSocket()) {
            // Inicializa DatagramSocket para enviar
            InetAddress ip = InetAddress.getByName(DEST_IP);
            packet_out = new DatagramPacket(new byte[0], 0, ip, DEST_PORT);
            try (// Crear secuencia de entrada de teclado
            Scanner sc = new Scanner(System.in)) {
                System.out.println("Por favor ingrese los datos");
                // Constantemente lee la entrada del teclado
                String key =null;
                // Matriz de bytes correspondiente a los caracteres de entrada del teclado
                byte[] keyBuff = null;
                while (sc.hasNextLine()) {
                    key = sc.nextLine();
                    if ("exit".equals(key)) {
                        break;
                    }
                    // cadena de entrada → matriz de bytes
                    //TO DO
                    keyBuff= key.getBytes("UTF-8");
                    //keyBuff = key.getBytes();
                    // Establecer los datos de bytes en el DatagramPacket para enviar
                    packet_out.setData(keyBuff);
                    // enviar datagrama
                    socket.send(packet_out);
                    // Lee los datos en el Socket, y los datos leídos se colocan en la matriz de bytes encapsulada por inPacket.
                    socket.receive(packet_in);
                    System.out.println(new String(inBuff, 0, packet_in.getLength()));
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