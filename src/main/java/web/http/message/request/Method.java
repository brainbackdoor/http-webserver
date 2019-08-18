package web.http.message.request;

import web.http.exception.MethodNotAllowedException;

import java.util.Arrays;

public enum Method {
    /**
     * 서버가 리소스에 대해 수행해주길 바라는 동작
     * <p>
     * Created by brainbackdoor on 2019-08-18.
     */
    GET, HEAD, PUT, POST, TRACE, OPTIONS, DELETE;

    public static Method of(String input) {
        return Arrays.stream(values())
                .filter(v -> v.equalsTo(input))
                .findFirst()
                .orElseThrow(MethodNotAllowedException::new);
    }

    public boolean equalsTo(String input) {
        return this.name().equals(input);
    }
}
