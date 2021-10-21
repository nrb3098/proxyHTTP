import java.net.ServerSocket;
import java.net.Socket;

public class App {
    public static void main(String[] args) throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            while (true) {
                // manejador de cliente
                try( Socket cliente = serverSocket.accept()){
                    ManejadorCliente.manejarCliente(cliente);
                }
            }
        }
    }
}
