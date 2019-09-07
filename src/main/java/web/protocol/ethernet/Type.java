package web.protocol.ethernet;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Type {

    // http://www.ktword.co.kr/word/abbr_view.php?nav=&m_temp1=2039&id=852
    IPV4((short) 0x0800, "IPv4"),
    ARP((short) 0x0806, "ARP");

    private short value;
    private String name;

    Type(short value, String name) {
        this.value = value;
        this.name = name;
    }

    public static Type getInstance(Short value){
        return Arrays.stream(Type.values()).filter(v -> v.value == value).findFirst().get();
    }
}