package web.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import support.dns.DomainNameService;
import web.http.connection.Host;

import static org.assertj.core.api.Assertions.assertThat;
import static web.http.connection.Host.isIpAddress;

class HostTest {
    private DomainNameService domainNameService = new DomainNameService();

    @Test
    @DisplayName("IP주소 여부를 확인한다.")
    void isIPAddress() {
        String domain = "www.domain.com";
        String ip = "192.168.100.4";

        boolean expectedByDomain = isIpAddress(domain);
        boolean expectedByIp = isIpAddress(ip);

        assertThat(expectedByDomain).isFalse();
        assertThat(expectedByIp).isTrue();
    }

    @Test
    @DisplayName("IP주소 조회 기능")
    void retrieveIpAddress() {
        String actualDomain = "www.domain.com";
        String actualIp = "";
        String expected = "192.168.100.4";

        if (!isIpAddress(actualDomain)) {
            actualIp = domainNameService.findByDomain(actualDomain);
        }

        assertThat(Host.of(actualIp)).isEqualTo(Host.of(expected));
    }

}