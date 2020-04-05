package api.tws;

import api.Downloader;
import com.ib.client.EClientSocket;
import com.ib.client.TickAttr;

public interface ITwsRequester {

    void request( Downloader downloader );
    void reciever( int tickerId, int field, double price, TickAttr attribs );

}
