package stocksHandler;

public class MiniStock {

    private String name;
    private double lastPrice = 0;
    private double lastPrice_0 = 0;
    private double bid = 0;
    private double bid_0_for_delta = 0;
    private double ask = 0;
    private double ask_0_for_delta = 0;
    private double volume = 0;
    private double volume_0_for_baskets = 0;
    private double volume_0_for_delta = 0;
    private double weight = 0;
    private double delta = 0;
    private MiniStockDDECells ddeCells;

    public MiniStock(String name) {
        this.name = name;
    }

    public MiniStock(String name, int row) {
        this.name = name;
        this.ddeCells = new MiniStockDDECells(row);
    }

    @Override
    public String toString() {
        return "MiniStock{" +
                "name='" + name + '\'' +
                ", lastPrice=" + lastPrice +
                ", volume=" + volume +
                ", bid=" + bid +
                ", ask=" + ask +
                ", weight=" + weight +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public void setLastPrice_0(double lastPrice_0) {
        this.lastPrice_0 = lastPrice_0;
    }

    public double getLastPrice_0() {
        return lastPrice_0;
    }

    public void setVolume_0_for_baskets(double volume_0_for_baskets) {
        this.volume_0_for_baskets = volume_0_for_baskets;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getVol_0() {
        return volume_0_for_baskets;
    }

    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public double getBid_0_for_delta() {
        return bid_0_for_delta;
    }

    public void setBid_0_for_delta(double bid_0_for_delta) {
        this.bid_0_for_delta = bid_0_for_delta;
    }

    public double getAsk() {
        return ask;
    }

    public void setAsk(double ask) {
        this.ask = ask;
    }

    public double getAsk_0_for_delta() {
        return ask_0_for_delta;
    }

    public void setAsk_0_for_delta(double ask_0_for_delta) {
        this.ask_0_for_delta = ask_0_for_delta;
    }

    public double getVolume_0_for_baskets() {
        return volume_0_for_baskets;
    }

    public double getVolume_0_for_delta() {
        if (this.volume_0_for_delta == 0) {
            setVolume_0_for_delta(volume);
        }
        return volume_0_for_delta;
    }

    public double getDelta() {
        return delta;
    }

    public MiniStockDDECells getDdeCells() {
        return ddeCells;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setVolume_0_for_delta(double volume_0_for_delta) {
        this.volume_0_for_delta = volume_0_for_delta;
    }

    public void append_delta(double delta) {
        this.delta += delta;
    }

    // Cells
    public class MiniStockDDECells {

        // Variables
        private String lastPriceCell = "R%sC27";
        private String volumeCell = "R%sC28";
        private String nameCell = "R%sC26";
        private String bidCell = "R%sC29";
        private String askCell = "R%sC30";
        private String weightCell = "R%sC31";
        private int row;

        // Constructor
        public MiniStockDDECells(int row) {
            this.row = row;
            this.lastPriceCell = String.format(lastPriceCell, row);
            this.volumeCell = String.format(volumeCell, row);
            this.nameCell = String.format(nameCell, row);
            this.bidCell = String.format(bidCell, row);
            this.askCell = String.format(askCell, row);
            this.weightCell = String.format(weightCell, row);
        }

        // Getters and Setters
        public String getLastPriceCell() {
            return lastPriceCell;
        }

        public String getBidCell() {
            return bidCell;
        }

        public String getAskCell() {
            return askCell;
        }

        public void setLastPriceCell(String lastPriceCell) {
            this.lastPriceCell = lastPriceCell;
        }

        public String getVolumeCell() {
            return volumeCell;
        }

        public void setVolumeCell(String volumeCell) {
            this.volumeCell = volumeCell;
        }

        public String getNameCell() {
            return nameCell;
        }

        public void setNameCell(String nameCell) {
            this.nameCell = nameCell;
        }

        public int getRow() {
            return row;
        }

        public void setRow(int row) {
            this.row = row;
        }

        public String getWeightCell() {
            return weightCell;
        }

    }

}

