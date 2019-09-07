package web.tool.sniffer;

import web.protocol.Packet;

public interface PacketListener {
    void gotPacket(Packet packet);
}
