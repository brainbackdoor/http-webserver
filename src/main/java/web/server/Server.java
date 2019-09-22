package web.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final Logger log = LoggerFactory.getLogger(Server.class);
    private static final int DEFAULT_PORT = 8080;
    public static final int DEFAULT_THREAD_POOL = 20;

    public static void main(String[] args) throws IOException {
        int port = DEFAULT_PORT;

        try (ServerSocket socket = new ServerSocket(port)) {
            log.info("Web Application Server started {} port.", port);
            ExecutorService executorService = Executors.newFixedThreadPool(DEFAULT_THREAD_POOL);

            Socket connection;
            while ((connection = socket.accept()) != null) {
                RequestHandler requestHandler = new RequestHandler(connection);
                executorService.execute(requestHandler);
            }
        }
    }
}
