package web.protocol.tcp;

import web.protocol.Packet;

public interface TransportPacket extends Packet {

    @Override
    TransportHeader getHeader();

    interface TransportHeader extends Header {

        TcpPort getSrcPort();

        TcpPort getDstPort();
    }
}
