package web.protocol;

import java.io.Serializable;
import java.util.List;

public interface Packet extends Serializable {

    Header getHeader();

    Packet getPayload();

    int length();

    byte[] getRawData();

    interface Header extends Serializable {

        int length();

        List<byte[]> getRawFields();
    }
}
