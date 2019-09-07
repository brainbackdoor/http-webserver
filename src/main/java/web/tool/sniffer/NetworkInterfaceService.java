package web.tool.sniffer;

import com.sun.jna.ptr.PointerByReference;
import web.tool.sniffer.NativeMappings.pcap_if;
import web.tool.sniffer.NetworkInterface.Errbuf;

import java.util.ArrayList;
import java.util.List;

public class NetworkInterfaceService {

    public static List<NetworkInterface> findAll() {
        pcap_if nic = retrieve();

        List<NetworkInterface> interfaces = findAll(nic);

        close(nic);
        return interfaces;
    }

    public static NetworkInterface findByName(String name) {
        return findAll().stream()
                .filter(v -> name.equals(v.getName()))
                .findFirst().get();
    }

    private static pcap_if retrieve() {
        PointerByReference pointerByReference = new PointerByReference();
        NativeMappings.pcap_findalldevs(pointerByReference, new Errbuf());
        return new pcap_if(pointerByReference.getValue());
    }

    private static List<NetworkInterface> findAll(pcap_if pcapIf) {
        List<NetworkInterface> interfaces = new ArrayList<>();
        for (pcap_if pif = pcapIf; pif != null; pif = pif.getNext()) {
            interfaces.add(NetworkInterface.of(pif, true));
        }
        return interfaces;
    }

    private static void close(pcap_if nic) {
        NativeMappings.pcap_freealldevs(nic.getPointer());
    }
}
