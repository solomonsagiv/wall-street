package api.tws;

import api.Downloader;
import com.ib.client.TickAttr;

public interface ITwsRequester {
    
    void request( Downloader downloader );
    void reciever( int tickerId, int field, double price, TickAttr attribs );
    void sizeReciever ( int tickerId, int field, int size );

}
