package web.tool.sniffer;

import java.net.InetAddress;

public interface PcapAddress {

    InetAddress getAddress();

    InetAddress getNetmask();

    InetAddress getBroadcastAddress();

    InetAddress getDestinationAddress();
}
