package web.protocol.ip;

import web.protocol.Packet;

import java.net.InetAddress;

public interface IpPacket extends Packet {

    @Override
    IpHeader getHeader();

    interface IpHeader extends Header {

        IpVersion getVersion();

        IpNumber getProtocol();

        InetAddress getSrcAddr();

        InetAddress getDstAddr();
    }
}