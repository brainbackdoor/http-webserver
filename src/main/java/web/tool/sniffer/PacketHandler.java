package web.tool.sniffer;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.math.NumberUtils;
import web.protocol.Packet;
import web.tool.dump.TcpDump;

import java.io.Closeable;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Builder
@AllArgsConstructor
public class PacketHandler implements Closeable {
    private final Pointer handle;
    private final TimestampPrecision timestampPrecision;

    public Packet sendPacket(Packet packet) throws PacketNativeException {
        if (packet == null) {
            throw new NullPointerException("packet may not be null");
        }
        sendPacket(packet.getRawData());
        return packet;
    }

    public void sendPacket(byte[] bytes) throws PacketNativeException {
        int response = NativeMappings.pcap_sendpacket(handle, bytes, bytes.length);

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

    public byte[] getNextRawPacket() throws PacketNativeException {
        PointerByReference headerPP = new PointerByReference();
        PointerByReference dataPP = new PointerByReference();

        return getNextRawPacket(headerPP, dataPP);
    }

    private byte[] getNextRawPacket(PointerByReference headerPP, PointerByReference dataPP) throws PacketNativeException {
        int response = NativeMappings.pcap_next_ex(handle, headerPP, dataPP);

        if (response == NumberUtils.INTEGER_ONE) {
            Pointer headerP = headerPP.getValue();
            Pointer dataP = dataPP.getValue();
            return dataP.getByteArray(0, NativeMappings.pcap_pkthdr.getCaplen(headerP));
        }

        throw new PacketNativeException("getNextRawPacket() response error");
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

    @Getter
    @AllArgsConstructor
    public enum TimestampPrecision {
        MICRO(0),
        NANO(1);

        private final int value;
    }
}
