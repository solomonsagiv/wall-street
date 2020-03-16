package serverObjects.indexObjects;

import api.Manifest;
import dataBase.mySql.TablesHandler;
import dataBase.mySql.mySqlComps.MyColumnSql;
import dataBase.mySql.mySqlComps.MyLoadAbleColumn;
import dataBase.mySql.myTables.*;
import options.IndexOptions;
import options.Options;
import options.OptionsEnum;
import options.OptionsHandler;
import org.json.JSONArray;
import org.json.JSONObject;
import serverObjects.BASE_CLIENT_OBJECT;
import tws.TwsContractsEnum;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public abstract class INDEX_CLIENT_OBJECT extends BASE_CLIENT_OBJECT {

    private double future = 0;

    public INDEX_CLIENT_OBJECT() {
        super( );
    }

    public void setFuture( double future ) {
        if ( this.future == 0 ) {
            this.future = future;
            getOptionsHandler( ).initOptions( future );
        }
    }

    public double getFuture() {
        return future;
    }

    @Override
    public void initOptionsHandler() throws NullPointerException {

        IndexOptions weekOptions = new IndexOptions( getBaseId( ) + 1000, this, OptionsEnum.WEEK, getTwsHandler( ).getMyContract( TwsContractsEnum.OPT_WEEK ) );
        IndexOptions monthOptions = new IndexOptions( getBaseId( ) + 2000, this, OptionsEnum.MONTH, getTwsHandler( ).getMyContract( TwsContractsEnum.OPT_MONTH ) );
        IndexOptions quarterOptions = new IndexOptions( getBaseId( ) + 3000, this, OptionsEnum.QUARTER, getTwsHandler( ).getMyContract( TwsContractsEnum.OPT_QUARTER ) );

        OptionsHandler optionsHandler = new OptionsHandler( this ) {
            @Override
            public void initOptions() {
                addOptions( weekOptions );
                addOptions( monthOptions );
                addOptions( quarterOptions );
            }

            @Override
            public void initMainOptions() {
                setMainOptions( monthOptions );
            }
        };
        setOptionsHandler( optionsHandler );
    }

    @Override
    public void initTablesHandlers() {

        TablesHandler tablesHandler = new TablesHandler( );

        // ---------- Day ---------- //
        MyDayTable myDayTable = new MyDayTable( this, tablesHandler.getDayName( this ) ) {
            @Override
            public void initColumns() {
                addColumn( new MyColumnSql<>( this, "date", MyColumnSql.STRING ) {
                    @Override
                    public String getObject() {
                        return LocalDate.now( ).toString( );
                    }
                } );
                addColumn( new MyColumnSql<>( this, "exp_name", MyColumnSql.STRING ) {
                    @Override
                    public String getObject() {
                        return Manifest.EXP;
                    }
                } );
                addColumn( new MyColumnSql<>( this, "time", MyColumnSql.STRING ) {
                    @Override
                    public String getObject() {
                        return LocalTime.now( ).toString( );
                    }
                } );
                addColumn( new MyColumnSql<>( this, "con", MyColumnSql.DOUBLE ) {
                    @Override
                    public Double getObject() {
                        return client.getOptionsHandler( ).getMainOptions( ).getContract( );
                    }
                } );
                addColumn( new MyColumnSql<>( this, "conDay", MyColumnSql.DOUBLE ) {
                    @Override
                    public Double getObject() {
                        return client.getOptionsHandler( ).getOptions( OptionsEnum.WEEK ).getContract( );
                    }
                } );
                addColumn( new MyColumnSql<>( this, "conMonth", MyColumnSql.DOUBLE ) {
                    @Override
                    public Double getObject() {
                        return client.getOptionsHandler( ).getOptions( OptionsEnum.MONTH ).getContract( );
                    }
                } );
                addColumn( new MyColumnSql<>( this, "conQuarter", MyColumnSql.DOUBLE ) {
                    @Override
                    public Double getObject() {
                        return client.getOptionsHandler( ).getOptions( OptionsEnum.QUARTER ).getContract( );
                    }
                } );
                addColumn( new MyColumnSql<>( this, "ind", MyColumnSql.DOUBLE ) {
                    @Override
                    public Double getObject() {
                        return client.getIndex( );
                    }
                } );
                addColumn( new MyColumnSql<>( this, "con_up", MyColumnSql.INT ) {
                    @Override
                    public Integer getObject() {
                        return client.getConUp( );
                    }
                } );
                addColumn( new MyColumnSql<>( this, "con_down", MyColumnSql.INT ) {
                    @Override
                    public Integer getObject() {
                        return client.getConDown( );
                    }
                } );
                addColumn( new MyColumnSql<>( this, "index_up", MyColumnSql.INT ) {
                    @Override
                    public Integer getObject() {
                        return client.getIndexUp( );
                    }
                } );
                addColumn( new MyColumnSql<>( this, "index_down", MyColumnSql.INT ) {
                    @Override
                    public Integer getObject() {
                        return client.getIndexDown( );
                    }
                } );
                addColumn( new MyColumnSql<>( this, "options", MyColumnSql.STRING ) {
                    @Override
                    public String getObject() {
                        return client.getOptionsHandler( ).getMainOptions( ).getOptionsAsJson( ).toString( );
                    }
                } );
                addColumn( new MyColumnSql<>( this, "base", MyColumnSql.DOUBLE ) {
                    @Override
                    public Double getObject() {
                        return client.getBase( );
                    }
                } );
                addColumn( new MyColumnSql<>( this, "op_avg", MyColumnSql.DOUBLE ) {
                    @Override
                    public Double getObject() {
                        return client.getOptionsHandler( ).getMainOptions( ).getOpAvg( );
                    }
                } );
            }
        };

        tablesHandler.addTable( TablesEnum.DAY, myDayTable );

        // ---------- Sum ---------- //
        MySumTable mySumTable = new MySumTable( this, tablesHandler.getSumName( this ) ) {
            @Override
            public void initColumns() {
                addColumn( new MyColumnSql<>( this, "date", MyColumnSql.STRING ) {
                    @Override
                    public String getObject() {
                        return LocalDate.now( ).toString( );
                    }
                } );
                addColumn( new MyColumnSql<>( this, "exp_name", MyColumnSql.STRING ) {
                    @Override
                    public String getObject() {
                        return Manifest.EXP;
                    }
                } );
                addColumn( new MyColumnSql<>( this, "open", MyColumnSql.DOUBLE ) {
                    @Override
                    public Double getObject() {
                        return client.getOpen( );
                    }
                } );
                addColumn( new MyColumnSql<>( this, "high", MyColumnSql.DOUBLE ) {
                    @Override
                    public Double getObject() {
                        return client.getHigh( );
                    }
                } );
                addColumn( new MyColumnSql<>( this, "low", MyColumnSql.DOUBLE ) {
                    @Override
                    public Double getObject() {
                        return client.getLow( );
                    }
                } );
                addColumn( new MyColumnSql<>( this, "close", MyColumnSql.DOUBLE ) {
                    @Override
                    public Double getObject() {
                        return client.getIndex( );
                    }
                } );
                addColumn( new MyColumnSql<>( this, "con_up", MyColumnSql.INT ) {
                    @Override
                    public Integer getObject() {
                        return client.getConUp( );
                    }
                } );
                addColumn( new MyColumnSql<>( this, "con_down", MyColumnSql.INT ) {
                    @Override
                    public Integer getObject() {
                        return client.getConDown( );
                    }
                } );
                addColumn( new MyColumnSql<>( this, "index_up", MyColumnSql.INT ) {
                    @Override
                    public Integer getObject() {
                        return client.getIndexUp( );
                    }
                } );
                addColumn( new MyColumnSql<>( this, "index_down", MyColumnSql.INT ) {
                    @Override
                    public Integer getObject() {
                        return client.getIndexDown( );
                    }
                } );
                addColumn( new MyColumnSql<>( this, "op_avg", MyColumnSql.DOUBLE ) {
                    @Override
                    public Double getObject() {
                        return client.getOptionsHandler( ).getMainOptions( ).getOpAvg( );
                    }
                } );
                addColumn( new MyColumnSql<>( this, "options", MyColumnSql.STRING ) {
                    @Override
                    public String getObject() {
                        return client.getOptionsHandler( ).getAllOptionsAsJson( ).toString( );
                    }
                } );
                addColumn( new MyColumnSql<>( this, "base", MyColumnSql.DOUBLE ) {
                    @Override
                    public Double getObject() {
                        return client.getBase( );
                    }
                } );
                addColumn( new MyColumnSql<>( this, "con_bid_ask_counter", MyColumnSql.INT ) {
                    @Override
                    public Integer getObject() {
                        return client.getOptionsHandler( ).getMainOptions( ).getContractBidAskCounter( );
                    }
                } );
            }
        };

        tablesHandler.addTable( TablesEnum.SUM, mySumTable );

        // ---------- Status ---------- //
        MyStatusTable myStatusTable = new MyStatusTable( this, tablesHandler.getStatusName( ) ) {
            @Override
            public void initColumns() {
                addColumn( new MyColumnSql<>( this, "name", MyColumnSql.STRING ) {
                    @Override
                    public String getObject() {
                        return client.getName( );
                    }
                } );
                addColumn( new MyColumnSql<>( this, "time", MyColumnSql.STRING ) {
                    @Override
                    public String getObject() {
                        return LocalTime.now( ).toString( );
                    }
                } );
                addColumn( new MyColumnSql<>( this, "ind", MyColumnSql.DOUBLE ) {
                    @Override
                    public Double getObject() {
                        return client.getIndex( );
                    }
                } );
                addColumn( new MyLoadAbleColumn< Integer >( this, "conUp", MyColumnSql.INT ) {
                    @Override
                    public Integer getObject() {
                        return client.getConUp( );
                    }

                    @Override
                    public void setLoadedObject( Integer object ) {

                    }


                    @Override
                    public Integer getResetObject() {
                        return 0;
                    }

                } );
                addColumn( new MyLoadAbleColumn< Integer >( this, "conDown", MyColumnSql.INT ) {
                    @Override
                    public Integer getObject() {
                        return client.getConDown( );
                    }

                    @Override
                    public void setLoadedObject( Integer object ) {
                        client.setConDown( object );
                    }

                    @Override
                    public Integer getResetObject() {
                        return 0;
                    }

                } );
                addColumn( new MyLoadAbleColumn< Integer >( this, "indUp", MyColumnSql.INT ) {
                    @Override
                    public Integer getObject() {
                        return client.getIndexUp( );
                    }

                    @Override
                    public void setLoadedObject( Integer object ) {
                        client.setIndexUp( object );
                    }

                    @Override
                    public Integer getResetObject() {
                        return 0;
                    }

                } );
                addColumn( new MyLoadAbleColumn< Integer >( this, "indDown", MyColumnSql.INT ) {
                    @Override
                    public Integer getObject() {
                        return client.getIndexDown( );
                    }

                    @Override
                    public void setLoadedObject( Integer object ) {
                        client.setIndexDown( object );
                    }

                    @Override
                    public Integer getResetObject() {
                        return 0;
                    }

                } );
                addColumn( new MyColumnSql< Double >( this, "base", MyColumnSql.DOUBLE ) {
                    @Override
                    public Double getObject() {
                        return client.getBase( );
                    }
                } );
                addColumn( new MyColumnSql<>( this, "open", MyColumnSql.DOUBLE ) {
                    @Override
                    public Double getObject() {
                        return client.getOpen( );
                    }
                } );
                addColumn( new MyColumnSql<>( this, "high", MyColumnSql.DOUBLE ) {
                    @Override
                    public Double getObject() {
                        return client.getHigh( );
                    }
                } );
                addColumn( new MyColumnSql<>( this, "low", MyColumnSql.DOUBLE ) {
                    @Override
                    public Double getObject() {
                        return client.getLow( );
                    }
                } );
                addColumn( new MyLoadAbleColumn< String >( this, "options", MyColumnSql.STRING ) {
                    @Override
                    public String getObject() {
                        return client.getOptionsHandler( ).getAllOptionsAsJson( ).toString( );
                    }

                    @Override
                    public void setLoadedObject( String object ) {
                        JSONObject optionsData = new JSONObject( object );
                        for ( Options options : client.getOptionsHandler( ).getOptionsList( ) ) {
                            try {
                                options.setDataFromJson( optionsData.getJSONObject( options.getType( ).toString( ) ) );
                            } catch ( Exception e ) {
                                e.printStackTrace( );
                            }
                        }
                    }

                    @Override
                    public String getResetObject() {
                        return client.getOptionsHandler( ).getAllOptionsEmptyJson( ).toString( );
                    }
                } );
            }
        };

        tablesHandler.addTable( TablesEnum.STATUS, myStatusTable );

        // ---------- Arrays ---------- //
        MyArraysTable myArraysTable = new MyArraysTable( this, tablesHandler.getArraysName() ) {
            @Override
            public void initColumns() {
                addColumn( new MyColumnSql< String >( this, "name", MyColumnSql.STRING ) {
                    @Override
                    public String getObject() {
                        return client.getName( );
                    }
                });
                addColumn( new MyColumnSql< String >( this, "time", MyColumnSql.STRING ) {
                    @Override
                    public String getObject() {
                        return LocalTime.now( ).toString( );
                    }
                });
                addColumn( new MyLoadAbleColumn<String>( this, "indexlist", MyColumnSql.STRING ) {
                    @Override
                    public String getObject() {
                        return client.getIndexList( ).toString( );
                    }

                    @Override
                    public void setLoadedObject( String object ) {
                        convertJsonArrayToDoubleArray( new JSONArray( object ), ( ArrayList< Double > ) client.getIndexList( ) );
                    }

                    @Override
                    public String getResetObject() {
                        return new JSONArray( ).toString( );
                    }
                });
                addColumn( new MyLoadAbleColumn<String>( this, "opList", MyColumnSql.STRING ) {
                    @Override
                    public String getObject() {
                        return client.getOptionsHandler( ).getMainOptions( ).getOpList( ).toString( );
                    }

                    @Override
                    public void setLoadedObject( String object ) {
                        convertJsonArrayToDoubleArray( new JSONArray( object ), ( ArrayList< Double > ) client.getOptionsHandler( ).getMainOptions( ).getOpList( ) );
                    }

                    @Override
                    public String getResetObject() {
                        return new JSONArray( ).toString( );
                    }
                });
            }
        };

        tablesHandler.addTable( TablesEnum.ARRAYS, myArraysTable );

        // ---------- Bounds ---------- //
        MyBoundsTable myBoundsTable = new MyBoundsTable( this, "bounds.bounds" );
        tablesHandler.addTable( TablesEnum.BOUNDS, myBoundsTable );

        // Set Table handler
        setTablesHandler( tablesHandler );
    }
}
