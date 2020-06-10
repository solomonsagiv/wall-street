package exp;

import locals.L;
import options.fullOptions.PositionCalculator;
import serverObjects.BASE_CLIENT_OBJECT;
import serverObjects.indexObjects.INDEX_CLIENT_OBJECT;
import serverObjects.stockObjects.STOCK_OBJECT;
import java.util.ArrayList;
import java.util.HashMap;

public class Exps {

    // Variables
    public BASE_CLIENT_OBJECT client;
    private HashMap<ExpEnum, Exp> expMap = new HashMap<>( );
    private ArrayList< Exp > expList = new ArrayList<>( );
    private PositionCalculator positionCalculator;
    Exp mainExp;

    public Exp getExp(ExpEnum exp ) {
        return expMap.get( exp );
    }

    // Constructor
    public Exps( INDEX_CLIENT_OBJECT client ) {
        this.client = client;
        positionCalculator = new PositionCalculator( client );
    }

    public Exps( STOCK_OBJECT client ) {
        this.client = client;
        positionCalculator = new PositionCalculator( client );
    }

    public void addExp( Exp exp, ExpEnum expEnum ) {
        expList.add(exp);
        expMap.put( expEnum, exp );
    }

    private void initStartEndStrikes( double future ) {

        double last = L.modulu( future );
        double margin = client.getStrikeMargin( );

        double startStrike = last - ( margin * 10 );
        double endStrike = last + ( margin * 10 );

        client.setStartStrike( startStrike );
        client.setEndStrike( endStrike );

    }

    public void initOptions( double future ) {

        initStartEndStrikes( future );

        for ( Exp exp : getExpList( ) ) {
            System.out.println( "Init options: " +exp.getOptions().toString( ) );
            exp.getOptions().initOptions( );
            System.out.println( exp.getOptions().toStringVertical( ) );
        }

    }

    // Getters and setters
    public HashMap< ExpEnum, Exp > getExpMap() {
        return expMap;
    }

    public void setExpMap(HashMap< ExpEnum, Exp > expMap) {
        this.expMap = expMap;
    }

    public ArrayList< Exp > getExpList() {
        return expList;
    }

    public void setExpList(ArrayList< Exp > expList) {
        this.expList = expList;
    }

    public PositionCalculator getPositionCalculator() {
        return positionCalculator;
    }

    public void setPositionCalculator( PositionCalculator positionCalculator ) {
        this.positionCalculator = positionCalculator;
    }

    public Exp getMainExp() {
        if ( mainExp == null ) throw new NullPointerException( "Main exp not set" );
        return mainExp;
    }

    public void setMainExp(Exp mainExp) {
        this.mainExp = mainExp;
    }

}
