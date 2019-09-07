package web.tool.dump;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import web.protocol.Packet;
import web.tool.sniffer.NativeMappings;
import web.tool.sniffer.NativeMappings.pcap_pkthdr;
import web.tool.sniffer.PacketHandler.TimestampPrecision;
import web.tool.sniffer.PacketNativeException;

import java.io.Closeable;
import java.sql.Timestamp;

import static web.tool.sniffer.PacketHandler.TimestampPrecision.MICRO;

public class TcpDump implements Closeable {
    private final Pointer pointer;
    private final TimestampPrecision timestampPrecision;

    public TcpDump(Pointer pointer, TimestampPrecision timestampPrecision) throws PacketNativeException {
        if (pointer == null) {
            throw new PacketNativeException("pcap not open!");
        }
        this.pointer = pointer;
        this.timestampPrecision = timestampPrecision;
    }

    public void dump(Packet packet) {
        dump(packet, new Timestamp(System.currentTimeMillis()));
    }

    public void dump(Packet packet, Timestamp timestamp) {
        if (packet == null || timestamp == null) {
            throw new NullPointerException("packet 혹은 timestamp가 null 입니다.");
        }

        dumpRaw(packet.getRawData(), timestamp);
    }

    public void dumpRaw(byte[] packet, Timestamp timestamp) {
        pcap_pkthdr header = new pcap_pkthdr();
        header.len = header.caplen = packet.length;
        header.ts = new NativeMappings.TimeVal();
        header.ts.tv_sec = new NativeLong(timestamp.getTime() / 1000L);
        header.ts.tv_usec = getTvUsec(timestamp);

        NativeMappings.pcap_dump(pointer, header, packet);
    }

    private NativeLong getTvUsec(Timestamp timestamp) {
        return (timestampPrecision.equals(MICRO))
                ? new NativeLong(timestamp.getNanos() / 1000L)
                : new NativeLong(timestamp.getNanos());
    }

    @Override
    public void close() {
        NativeMappings.pcap_dump_close(pointer);
    }
}
