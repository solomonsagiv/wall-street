package options.optionsCalcs;

public class OptionsCalcBuilder {

    public static final String indexOptionsCalc = "indexOptionsCalc";
    public static final String stockOptionsCalc = "stockOptionsCalc";

    public static IOptionsCalcs getOptionsCalc(String optionsCalcString) {
        if (optionsCalcString.equals(indexOptionsCalc)) {
            return new IndexOptionsCalc();
        }

        if (optionsCalcString.equals(stockOptionsCalc)) {
            return new StockOptionsCalc();
        }
    }

}
