package web.protocol.http.message.common;

import java.util.Arrays;

import static web.protocol.http.message.common.Header.Type.CONTENT_LENGTH;

public class Header {

    public static final String DELIMITER_HEADER = ": ";
    private final Type type;
    private final String value;

    private Header(final Type type, final String value) {
        this.type = type;
        this.value = value;
    }

    public static Header of(String input) {
        String[] splits = input.split(DELIMITER_HEADER);
        return new Header(Type.of(splits[0]), splits[1]);
    }

    public boolean isContentLength() {
        return this.type.equals(CONTENT_LENGTH);
    }

    public String getValue() {
        return value;
    }

    public enum Type {

        // 일반 헤더
        CONNECTION("Connection"),
        DATE("Date"),
        MIME_VERSION("MIME-Version"),
        VIA("Via"),

        // 일반 캐시 헤더
        CACHE_CONTROL("Cache-Control"),
        PRAGMA("Pragma"),

        // 요청 헤더
        CLIENT_IP("Client-IP"),
        FROM("From"),
        HOST("Host"),
        REFERER("Referer"),
        USER_AGENT("User-Agent"),

        // Accept 관련 헤더
        ACCEPT("Accept"),
        ACCEPT_CHARSET("Accept-Charset"),
        ACCEPT_ENCODING("Accept-Encoding"),
        ACCEPT_LANGUAGE("Accept-Language"),

        //조건부 요청 헤더
        EXPECT("Expect"),
        IF_MATCH("If-Match"),
        IF_MODIFIED_SINCE("If-Modified-Since"),

        // 요청 보안 헤더
        AUTHORIZATION("Authorization"),
        COOKIE("Cookie"),

        // 프락시 요청 헤더
        MAX_FORWARDS("Max-Forwards"),
        PROXY_AUTHORIZATION("Proxy-Authorization"),
        PROXY_CONNECTION("Proxy-Connection"),

        // 응답 헤더
        AGE("Age"),
        PUBLIC("Public"),
        RETRY_AFTER("Retry-After"),
        SERVER("Server"),

        // 협상 헤더
        ACCEPT_RANGES("Accept-Ranges"),
        VARY("Vary"),

        // 응답 보안 헤더
        PROXY_AUTHENTICATE("Proxy-Authenticate"),
        SET_COOKIE("Set-Cookie"),

        // 엔터티 헤더
        ALLOW("Allow"),
        LOCATION("Location"),

        // 콘텐츠 헤더
        CONTENT_BASE("Content-Base"),
        CONTENT_ENCODING("Content-Encoding"),
        CONTENT_LANGUAGE("Content-Language"),
        CONTENT_LENGTH("Content-Length"),
        CONTENT_LOCATION("Content-Location"),
        CONTENT_MD5("Content-MD5"),
        CONTENT_RANGE("Content-Range"),
        CONTENT_TYPE("Content-Type"),

        // 엔터티 캐싱 헤더
        ETAG("ETag"),
        EXPIRES("Expires"),
        LAST_MODIFIED("Last-Modified");

        private String value;

        Type(String value) {
            this.value = value;
        }

        public static Type of(String input) {
            return Arrays.stream(values())
                    .filter(v -> v.value.equals(input))
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);
        }
    }

    @Override
    public String toString() {
        return "Header{" +
                "protocolType=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}
