package DDE;

import java.util.HashMap;
import java.util.Map;

public abstract class DDECells {

    public DDECells() {
        initCells();
    }

    Map cells = new HashMap<DDECellsEnum, String>();

    public abstract boolean isWorkWithDDE();

    public abstract void initCells();

    public void addCell(DDECellsEnum ddeCellsEnum, String cellLocation) {
        cells.put(ddeCellsEnum, cellLocation);
    }

    public String getCell(DDECellsEnum ddeCellsEnum) {
        return (String) cells.get(ddeCellsEnum);
    }

}