package DDE;

public abstract class DDECells {

    private String indCell = "";
    private String indBidCell = "";
    private String indAskCell = "";
    private String futCell = "";
    private String futBidCell = "";
    private String futAskCell = "";

    public abstract boolean isWorkWithDDE();

    public String getIndCell() {
        return indCell;
    }

    public void setIndCell( String indCell ) {
        this.indCell = indCell;
    }

    public String getIndBidCell() {
        return indBidCell;
    }

    public void setIndBidCell( String indBidCell ) {
        this.indBidCell = indBidCell;
    }

    public String getIndAskCell() {
        return indAskCell;
    }

    public void setIndAskCell( String indAskCell ) {
        this.indAskCell = indAskCell;
    }

    public String getFutCell() {
        return futCell;
    }

    public void setFutCell( String futCell ) {
        this.futCell = futCell;
    }

    public String getFutBidCell() {
        return futBidCell;
    }

    public void setFutBidCell( String futBidCell ) {
        this.futBidCell = futBidCell;
    }

    public String getFutAskCell() {
        return futAskCell;
    }

    public void setFutAskCell( String futAskCell ) {
        this.futAskCell = futAskCell;
    }
}
