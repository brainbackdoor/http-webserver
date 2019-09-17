package web.tool.sniffer;

@FunctionalInterface
public interface PacketListener {
    void gotPacket(byte[] packet);
}
