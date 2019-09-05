//package web.protocol.tcp;
//
//import org.pcap4j.core.*;
//import org.pcap4j.util.MacAddress;
//import org.pcap4j.util.NifSelector;
//import web.protocol.Packet;
//
//import java.io.IOException;
//import java.net.Inet4Address;
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//@SuppressWarnings("javadoc")
//public class SendFragmentedEcho {
//
//    private static final String COUNT_KEY = SendFragmentedEcho.class.getName() + ".count";
//    private static final int COUNT = Integer.getInteger(COUNT_KEY, 3);
//
//    private static final String READ_TIMEOUT_KEY =
//            SendFragmentedEcho.class.getName() + ".readTimeout";
//    private static final int READ_TIMEOUT = Integer.getInteger(READ_TIMEOUT_KEY, 10); // [ms]
//
//    private static final String SNAPLEN_KEY = SendFragmentedEcho.class.getName() + ".snaplen";
//    private static final int SNAPLEN = Integer.getInteger(SNAPLEN_KEY, 65536); // [bytes]
//
//    private static final String TU_KEY = SendFragmentedEcho.class.getName() + ".tu";
//    private static final int TU = Integer.getInteger(TU_KEY, 4000); // [bytes]
//
//    private static final String MTU_KEY = SendFragmentedEcho.class.getName() + ".mtu";
//    private static final int MTU = Integer.getInteger(MTU_KEY, 1403); // [bytes]
//
//    private SendFragmentedEcho() {
//    }
//
//    public static void main(String[] args) throws Exception {
//        String[] temp = {"192.168.6.171", "38:f9:d3:1a:6e:24", "175.158.0.135", "00:00:0c:07:ac:0b"};
//        String strSrcIpAddress = temp[0]; // for InetAddress.getByName()
//        String strSrcMacAddress = temp[1]; // e.g. 12:34:56:ab:cd:ef
//        String strDstIpAddress = temp[2]; // for InetAddress.getByName()
//        String strDstMacAddress = temp[3]; // e.g. 12:34:56:ab:cd:ef
//
//        System.out.println(COUNT_KEY + ": " + COUNT);
//        System.out.println(READ_TIMEOUT_KEY + ": " + READ_TIMEOUT);
//        System.out.println(SNAPLEN_KEY + ": " + SNAPLEN);
//        System.out.println("\n");
//
//        PcapNetworkInterface nif;
//        try {
//            nif = new NifSelector().selectNetworkInterface();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return;
//        }
//
//        if (nif == null) {
//            return;
//        }
//
//        System.out.println(nif.getName() + "(" + nif.getDescription() + ")");
//
//        PcapHandle handle = nif.openLive(SNAPLEN, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, READ_TIMEOUT);
//        PcapHandle sendHandle = nif.openLive(SNAPLEN, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, READ_TIMEOUT);
//        ExecutorService pool = Executors.newSingleThreadExecutor();
//
//        MacAddress srcMacAddr = MacAddress.getByName(strSrcMacAddress, ":");
//        try {
//            handle.setFilter(
//                    "icmp and ether dst " + Pcaps.toBpfString(srcMacAddr), BpfProgram.BpfCompileMode.OPTIMIZE);
//
//            PacketListener listener = packet -> System.out.println(packet);
//
//            Task t = new Task(handle, listener);
//            pool.execute(t);
//
//            byte[] echoData = new byte[TU - 28];
//            for (int i = 0; i < echoData.length; i++) {
//                echoData[i] = (byte) i;
//            }
//
//            List<web.protocol.tcp.TcpPacket.TcpHeader.TcpOption> options = new ArrayList<>();
//            options.add(new TcpPacketHelper.TcpNoOperationOption());
//            byte dataOffset = 15;
//            byte reserved = (byte) 11;
//
////            UnknownPacket.Builder unknownb = new UnknownPacket.Builder();
////            unknownb.rawData(new byte[]{(byte) 0, (byte) 1, (byte) 2, (byte) 3});
//
//            TcpPacket.Builder b = new TcpPacket.Builder();
//            b.dstPort(TcpPort.HTTP)
//                    .srcPort(TcpPort.getInstance((short) 0))
//                    .sequenceNumber(1234567)
//                    .acknowledgmentNumber(7654321)
//                    .dataOffset(dataOffset)
//                    .reserved(reserved)
//                    .urg(false)
//                    .ack(false)
//                    .psh(false)
//                    .rst(false)
//                    .syn(true)
//                    .fin(false)
//                    .window((short) 9999)
//                    .checksum((short) 0xABCD)
//                    .urgentPointer((short) 1111)
//                    .options(options)
//                    .padding(new byte[]{(byte) 0xaa})
//                    .correctChecksumAtBuild(false)
//                    .correctLengthAtBuild(false)
//                    .paddingAtBuild(false);
////                    .payloadBuilder(unknownb);
//
//
//            IpV4Packet.Builder ipV4Builder = new IpV4Packet.Builder();
//            try {
//                ipV4Builder
//                        .version(IpVersion.IPV4)
//                        .ttl((byte) 100)
//                        .protocol(IpNumber.TCP)
//                        .srcAddr((Inet4Address) InetAddress.getByName(strSrcIpAddress))
//                        .dstAddr((Inet4Address) InetAddress.getByName(strDstIpAddress))
//                        .payloadBuilder(b)
//                        .correctChecksumAtBuild(true)
//                        .correctLengthAtBuild(true);
//            } catch (UnknownHostException e1) {
//                throw new IllegalArgumentException(e1);
//            }
//
//            EthernetPacket.Builder etherBuilder = new EthernetPacket.Builder();
//            etherBuilder
//                    .dstAddr(MacAddress.getByName(strDstMacAddress, ":"))
//                    .srcAddr(srcMacAddr)
//                    .type(EtherType.IPV4)
//                    .paddingAtBuild(true);
//
//            for (int i = 0; i < COUNT; i++) {
////                echoBuilder.sequenceNumber((short) i);
//                ipV4Builder.identification((short) i);
//
//                for (final Packet ipV4Packet : IpV4Helper.fragment(ipV4Builder.build(), MTU)) {
//                    etherBuilder.payloadBuilder(
//                            new AbstractPacket.AbstractBuilder() {
//                                @Override
//                                public Packet build() {
//                                    return ipV4Packet;
//                                }
//                            });
//
//                    Packet p = etherBuilder.build();
//                    System.out.println("!!" + p);
//                    sendHandle.sendPacket(p);
//
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        break;
//                    }
//                }
//
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    break;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (handle != null && handle.isOpen()) {
//                try {
//                    handle.breakLoop();
//                } catch (NotOpenException noe) {
//                }
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                }
//                handle.close();
//            }
//            if (sendHandle != null && sendHandle.isOpen()) {
//                sendHandle.close();
//            }
//            if (pool != null && !pool.isShutdown()) {
//                pool.shutdown();
//            }
//        }
//    }
//
//    private static class Task implements Runnable {
//
//        private PcapHandle handle;
//        private PacketListener listener;
//
//        public Task(PcapHandle handle, PacketListener listener) {
//            this.handle = handle;
//            this.listener = listener;
//        }
//
//        @Override
//        public void run() {
//            try {
//                handle.loop(-1, listener);
//            } catch (PcapNativeException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//            } catch (NotOpenException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
