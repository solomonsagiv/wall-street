package IDDE;

import serverObjects.BASE_CLIENT_OBJECT;

public class DDEHandler {

    private BASE_CLIENT_OBJECT client;
    private IDDEReader iddeReader;
    private IDDEWriter iddeWriter;

    public DDEHandler(BASE_CLIENT_OBJECT client, IDDEReader iddeReader, IDDEWriter iddeWriter) {
        this.client = client;
        this.iddeReader = iddeReader;
        this.iddeWriter = iddeWriter;
    }

    public BASE_CLIENT_OBJECT getClient() {
        return client;
    }

    public void setClient(BASE_CLIENT_OBJECT client) {
        this.client = client;
    }

    public IDDEReader getIddeReader() {
        return iddeReader;
    }

    public void setIddeReader(IDDEReader iddeReader) {
        this.iddeReader = iddeReader;
    }

    public IDDEWriter getIddeWriter() {
        return iddeWriter;
    }

    public void setIddeWriter(IDDEWriter iddeWriter) {
        this.iddeWriter = iddeWriter;
    }
}
