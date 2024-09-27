package options;

public class OptionsDDeCells {

    String fut;
    String futBid;
    String futAsk;

    public OptionsDDeCells(String fut, String futBid, String futAsk) {
        this.fut = fut;
        this.futBid = futBid;
        this.futAsk = futAsk;
    }

    public String getFut() {
        if (futBid == null) throw new NullPointerException("Fut cell is null");
        return fut;
    }

    public void setFut(String fut) {
        this.fut = fut;
    }

    public String getFutBid() {
        if (futBid == null) throw new NullPointerException("Fut bid cell is null");
        return futBid;
    }

    public void setFutBid(String futBid) {
        this.futBid = futBid;
    }

    public String getFutAsk() {
        if (futBid == null) throw new NullPointerException("Fut ask cell is null");
        return futAsk;
    }

    public void setFutAsk(String futAsk) {
        this.futAsk = futAsk;
    }
}
