package web.tool.analysis;

import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.cmd.SigarCommandBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetInfo extends SigarCommandBase {

    private static final Logger log = LoggerFactory.getLogger(NetInfo.class);

    public String getMacAddress() throws SigarException {
        return this.sigar.getNetInterfaceConfig(null).getHwaddr();
    }

    public String getIp() throws SigarException {
        return this.sigar.getNetInterfaceConfig(null).getAddress();
    }

    public String getNic() throws SigarException {
        return this.sigar.getNetInterfaceConfig(null).getName();
    }

    @Override
    public void output(String[] strings) throws SigarException {
        NetInterfaceConfig config = this.sigar.getNetInterfaceConfig(null);
        log.debug("primary interface : {}", config.getName());
        log.debug("primary ip address : {}", config.getAddress());
        log.debug("primary mac address : {}", config.getHwaddr());
        log.debug("primary netmask : {}", config.getNetmask());
        org.hyperic.sigar.NetInfo info = this.sigar.getNetInfo();
        log.debug("host name : {}", info.getHostName());
        log.debug("domain name : {}", info.getDomainName());
        log.debug("default gateway : {}", info.getDefaultGateway());
        log.debug("primary dns : {}", info.getPrimaryDns());
    }

    public static void main(String[] args) throws Exception {
        new org.hyperic.sigar.cmd.NetInfo().processCommand(args);
    }
}
