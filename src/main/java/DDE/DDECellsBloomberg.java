package DDE;

public class DDECellsBloomberg extends DDECells {

    @Override
    public boolean isWorkWithDDE() {
        return true;
    }

    @Override
    public void initCells() {
        addCell(DDECellsEnum.IND_BID, "R2C2");
        addCell(DDECellsEnum.IND, "R2C3");
        addCell(DDECellsEnum.IND_ASK, "R2C4");
        addCell(DDECellsEnum.OPEN, "R13C4");
        addCell(DDECellsEnum.HIGH, "R13C1");
        addCell(DDECellsEnum.LOW, "R13C2");
        addCell(DDECellsEnum.BASE, "R11C5");
        addCell(DDECellsEnum.FUT_DAY, "R9C10");
        addCell(DDECellsEnum.FUT_WEEK, "R10C10");
        addCell(DDECellsEnum.FUT_MONTH, "R11C10");
        addCell(DDECellsEnum.E1, "R12C10");
        addCell(DDECellsEnum.E2, "R13C10");
    }
}
