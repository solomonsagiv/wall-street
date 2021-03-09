package stocksHandler;

public class MiniStock {

    private String name;
    private double lastPrice = 0;
    private double lastPrice_0 = 0;
    private double volume = 0;
    private double volume_0 = 0;
    private MiniStockDDECells ddeCells;
    
    public MiniStock( String name ) {
        this.name = name;
    }

    public MiniStock( String name, int row ) {
        this.name = name;
        this.ddeCells = new MiniStockDDECells( row );
    }

    @Override
    public String toString() {
        return "MiniStock{" +
                "name='" + name + '\'' +
                ", lastPrice=" + lastPrice +
                ", volume=" + volume +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice( double lastPrice ) {
        this.lastPrice = lastPrice;
    }

    public void setLastPrice_0( double lastPrice_0 ) {
        this.lastPrice_0 = lastPrice_0;
    }

    public double getLastPrice_0() {
        return lastPrice_0;
    }

    public void setVolume_0( double volume_0 ) {
        this.volume_0 = volume_0;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume( double volume ) {
        this.volume = volume;
    }

    public double getVol_0() {
        return volume_0;
    }

    public MiniStockDDECells getDdeCells() {
        return ddeCells;
    }

    // Cells
    public class MiniStockDDECells {

        // Variables
        private String lastPriceCell = "R%sC27";
        private String volumeCell = "R%sC28";
        private String nameCell = "R%sC26";
        private int row;

        // Constructor
        public MiniStockDDECells( int row ) {
            this.row = row;
            this.lastPriceCell = String.format( lastPriceCell, row );
            this.volumeCell = String.format( volumeCell, row );
            this.nameCell = String.format( nameCell, row );
        }

        // Getters and Setters
        public String getLastPriceCell() {
            return lastPriceCell;
        }

        public void setLastPriceCell( String lastPriceCell ) {
            this.lastPriceCell = lastPriceCell;
        }

        public String getVolumeCell() {
            return volumeCell;
        }

        public void setVolumeCell( String volumeCell ) {
            this.volumeCell = volumeCell;
        }

        public String getNameCell() {
            return nameCell;
        }

        public void setNameCell( String nameCell ) {
            this.nameCell = nameCell;
        }

        public int getRow() {
            return row;
        }

        public void setRow( int row ) {
            this.row = row;
        }

    }

}

