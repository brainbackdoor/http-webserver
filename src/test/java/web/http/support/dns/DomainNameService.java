package web.http.support.dns;

import com.google.common.collect.Maps;

import java.util.Map;

public class DomainNameService {
    private static Map<String, String> addresses = Maps.newHashMap();

    static {
        addresses.put("www.domain.com", "192.168.100.4");
    }

    public String findByDomain(String domain) {
        return addresses.get(domain);
    }
}
