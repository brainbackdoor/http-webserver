package web.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.protocol.http.message.request.RequestMessage;
import web.protocol.http.message.request.RequestMessageFactory;
import web.protocol.http.message.response.ResponseMessage;
import web.protocol.http.message.response.ResponseMessageFactory;
import web.server.resolver.RequestResolver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final RequestResolver resolver = new RequestResolver();

    public RequestHandler(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            RequestMessage request = RequestMessageFactory.of(in);
            ResponseMessage response = ResponseMessageFactory.of(request);

            DataOutputStream dos = new DataOutputStream(out);
            resolver.resolve(request, response);

            ResponseMessageFactory.response(response, dos);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
