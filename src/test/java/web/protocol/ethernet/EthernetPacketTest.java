package web.protocol.ethernet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.protocol.Packet;
import web.protocol.SimplePacket;
import web.protocol.ethernet.EthernetPacket.EthernetHeader;
import web.protocol.helper.PacketTestHelper;
import web.protocol.ip.IpPacket;
import web.tool.analysis.NetInfo;
import web.tool.sniffer.PacketNativeException;
import web.util.ByteUtils;

import java.net.UnknownHostException;

import static org.assertj.core.api.Assertions.assertThat;
import static web.protocol.ethernet.EthernetPacket.EthernetHeader.*;
import static web.protocol.ethernet.Type.ARP;

public class EthernetPacketTest extends PacketTestHelper {

    @BeforeEach
    void setUp() throws Exception {
        NetInfo netInfo = new NetInfo();
        nicName = netInfo.getNic();
        macAddress = netInfo.getMacAddress();
        localIp = netInfo.getIp();
        handler = getHandler(nicName);
        listener = packet -> gotPacket(packet);
        packetStorage =  new PacketStorage();
    }

    @Test
    @DisplayName("Ethernet Packet을 생성한다.")
    void constructor() {
        EthernetHeader header = createEthernetHeader(ARP);
        EthernetPacket packet = new EthernetPacket(header, new SimplePacket());

        assertThat(packet).isNotNull();
    }

    @Test
    @DisplayName("Ethernet Packet을 전송한다.")
    void send() throws Exception {
        EthernetPacket expected = new EthernetPacket(createEthernetHeader(ARP), new SimplePacket());
        handler.sendPacket(expected);
        handler.loop(5, listener);

        assertThat(packetStorage.exist(expected)).isTrue();
    }

    @Test
    @DisplayName("Ethernet Packet을 pcap 파일에 저장한다.")
    void save() throws PacketNativeException, UnknownHostException {
        IpPacket ipPacket = new IpPacket(createIpHeader(), new SimplePacket());
        EthernetPacket expected = new EthernetPacket(createEthernetHeader(ARP), ipPacket);
        PacketTestHelper.save(handler, expected);
        Packet actual = buildEthernetPacket(read());

        assertThat(actual.getHeader()).isEqualTo(expected.getHeader());
    }

    public static EthernetHeader createEthernetHeader(Type protocolType) {
        MacAddress src = MacAddress.getByName(macAddress);
        MacAddress dst = MacAddress.ETHER_BROADCAST_ADDRESS;

        return new EthernetHeader(dst, src, protocolType);
    }

    public static Packet buildEthernetPacket(byte[] rawData) {
        EthernetHeader header = buildEthernetHeader(rawData);
        return new EthernetPacket(header, new SimplePacket());
    }

    public static EthernetHeader buildEthernetHeader(byte[] rawData) {
        MacAddress dstAddr = ByteUtils.getMacAddress(rawData, DST_ADDR_OFFSET);
        MacAddress srcAddr = ByteUtils.getMacAddress(rawData, SRC_ADDR_OFFSET);
        Type type = Type.getInstance(ByteUtils.getShort(rawData, TYPE_OFFSET));

        return new EthernetHeader(dstAddr, srcAddr, type);
    }

    private void gotPacket(byte[] raw) {
        Packet packet = buildEthernetPacket(raw);
        packetStorage.add(packet);
    }

    @AfterEach
    void tearDown() {
        handler.close();
    }
}