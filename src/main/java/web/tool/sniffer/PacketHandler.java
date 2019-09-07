package web.tool.sniffer;

import com.sun.jna.Pointer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import web.protocol.Packet;
import web.tool.dump.TcpDump;

import java.io.Closeable;
import java.sql.Timestamp;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Builder
public class PacketHandler implements Closeable {
    private final TimestampPrecision timestampPrecision;
    private final Pointer handle;

    public Packet sendPacket(Packet packet) throws PacketNativeException {
        if (packet == null) {
            throw new NullPointerException("packet may not be null");
        }
        sendPacket(packet.getRawData());
        return packet;
    }

    public void sendPacket(byte[] bytes) throws PacketNativeException {
        sendPacket(bytes, bytes.length);
    }

    public void sendPacket(byte[] bytes, int len) throws PacketNativeException {
        if (bytes == null) {
            throw new NullPointerException("bytes may not be null");
        }

        int response = NativeMappings.pcap_sendpacket(handle, bytes, len);

        if (response < 0) {
            throw new PacketNativeException("Error occurred in pcap_sendpacket()");
        }
    }

    public TcpDump dumpOpen(String filePath) throws PacketNativeException {
        if (filePath == null) {
            throw new NullPointerException("filePath must not be null.");
        }

        return new TcpDump(open(filePath), timestampPrecision);
    }

    private Pointer open(String filePath) throws PacketNativeException {
        return apply(o -> NativeMappings.pcap_dump_open(handle, filePath));
    }

    private Pointer apply(NativeMappingsFunction function, Object... objects) throws PacketNativeException {
        ReentrantReadWriteLock handleLock = getHandlerLock();
        Pointer pointer;
        try {
            pointer = (Pointer) function.apply(objects);
            if (pointer == null) {
                throw new PacketNativeException("NativeMappingsFunction Error : " + function);
            }
        } finally {
            handleLock.readLock().unlock();
        }
        return pointer;
    }

    private ReentrantReadWriteLock getHandlerLock() {
        ReentrantReadWriteLock handleLock = new ReentrantReadWriteLock(true);
        if (!handleLock.readLock().tryLock()) {
            throw new RuntimeException("read fail");
        }
        return handleLock;
    }


    @Override
    public void close() {
        NativeMappings.pcap_close(handle);
    }

    public Timestamp getTimestamp() {
        return new ThreadLocal<Timestamp>().get();
    }

    @Getter
    @AllArgsConstructor
    public enum TimestampPrecision {
        MICRO(0),
        NANO(1);

        private final int value;
    }
}
