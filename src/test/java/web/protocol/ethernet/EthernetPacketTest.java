package web.protocol.ethernet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.protocol.Packet;
import web.protocol.SimplePacket;
import web.protocol.ethernet.EthernetPacket.EthernetHeader;
import web.tool.dump.TcpDump;
import web.tool.sniffer.NetworkInterface;
import web.tool.sniffer.NetworkInterfaceService;
import web.tool.sniffer.PacketHandler;
import web.tool.sniffer.PacketNativeException;
import web.util.ByteUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static web.protocol.ethernet.EthernetPacket.EthernetHeader.*;

class EthernetPacketTest {
    private static final String PCAP_FILE_KEY = EthernetPacketTest.class.getName() + ".pcapFile";
    private static final String PCAP_FILE = System.getProperty(PCAP_FILE_KEY, "Dump.pcap");
    PacketHandler handler;

    @BeforeEach
    void setUp() throws Exception {
        handler = getHandler();
    }

    @Test
    @DisplayName("Ethernet Packet을 생성한다.")
    void constructor() {
        EthernetHeader header = createHeader();
        EthernetPacket packet = new EthernetPacket(header, new SimplePacket());

        assertThat(packet).isNotNull();
    }

    @Test
    @DisplayName("Ethernet Packet을 전송한다.")
    void arp() throws Exception {
        EthernetPacket expected = new EthernetPacket(createHeader(), new SimplePacket());
        Packet actual = handler.sendPacket(expected);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Packet을 pcap 파일에 저장한다.")
    void save() throws PacketNativeException {
        EthernetPacket expected = new EthernetPacket(createHeader(), new SimplePacket());
        save(expected);
        Packet actual = createEthernetPacket(read());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("ARP를 이용하여 공유기 주소를 알아온다.")
    void search() {
    }

    @AfterEach
    void tearDown() {
        handler.close();
    }

    private PacketHandler getHandler() throws Exception {
        NetworkInterface nif = NetworkInterfaceService.findByName("en0");
        return nif.openLive(65536, NetworkInterface.PromiscuousMode.PROMISCUOUS, 10);
    }

    private EthernetHeader createHeader() {
        MacAddress src = MacAddress.getByName("00:00:00:00:00:01");
        MacAddress dst = MacAddress.ETHER_BROADCAST_ADDRESS;
        Type protocolType = Type.ARP;

        return new EthernetHeader(dst, src, protocolType);
    }

    private Packet createEthernetPacket(byte[] rawData) {
        MacAddress dstAddr = ByteUtils.getMacAddress(rawData, DST_ADDR_OFFSET);
        MacAddress srcAddr = ByteUtils.getMacAddress(rawData, SRC_ADDR_OFFSET);
        Type type = Type.getInstance(ByteUtils.getShort(rawData, TYPE_OFFSET));

        EthernetHeader header = new EthernetHeader(dstAddr, srcAddr, type);
        return new EthernetPacket(header, new SimplePacket());
    }

    private byte[] read() throws PacketNativeException {
        PacketHandler handler = TcpDump.openOffline(PCAP_FILE);
        byte[] rawData = handler.getNextRawPacket();
        handler.close();
        return rawData;
    }

    private void save(EthernetPacket packet) throws PacketNativeException {
        TcpDump dumper = handler.dumpOpen(PCAP_FILE);
        dumper.dump(packet);
        dumper.close();
    }
}