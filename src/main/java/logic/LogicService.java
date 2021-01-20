package logic;

import exp.ExpStrings;
import exp.Exps;
import serverObjects.BASE_CLIENT_OBJECT;
import service.MyBaseService;

public class LogicService extends MyBaseService {

    String expName;
    
    // Regular count
    int conRunnerUpCount = 0;
    int conRunnerDownCount = 0;
    int indRunnerUpCount = 0;
    int indRunnerDownCount = 0;

    // Local variables
    double conRunner = 0;
    double indRunner = 0;
    int competition_Number = 0;
    int conUpDown = 0;
    int indUpDown = 0;
    boolean conCompetion = false;
    boolean indCompetition = false;
    boolean first = false;
    boolean bool = true;
    boolean run = true;
    double margin = 0;
    Exps exps;

    // Constructor
    public LogicService( BASE_CLIENT_OBJECT client, String expName ) {
        super( client );
        this.expName = expName;
        this.exps = client.getExps();
    }

    @Override
    public String getName() {
        return "logic";
    }

    @Override
    public int getSleep() {
        return 200;
    }

    @Override
    public void go() {

        double future = 0;
        double index = getClient( ).getIndex( );

        if ( expName.equals( "DAY" ) ) {
            future = exps.getExp( ExpStrings.day ).getFuture();
        }
        
        // Margin
        if ( margin == 0 ) {
            if ( getClient( ).getRacesMargin( ) != 0 ) {
                margin = getClient( ).getRacesMargin( );
            } else {
                return;
            }
        }

        // set for the first time the future and stock 0
        if ( conRunner == 0.0 && indRunner == 0.0 && !first ) {
            first = true;
            conRunner = future;
            indRunner = index;
        }
        /**
         * Searching for the first competition
         **/
        if ( competition_Number == 0 ) {

            // Odd or Even
            if ( bool ) {

                bool = false;

                // hoze comes up
                if ( future >= conRunner + margin ) {
                    competition_Number = 1;
                    conCompetion = true;
                    conUpDown = 1;
                }

                // hoze comes down
                if ( future <= conRunner - margin ) {
                    competition_Number = 1;
                    conCompetion = true;
                    conUpDown = 2;
                }

                // stock comes up
                if ( index >= indRunner + margin ) {
                    competition_Number = 1;
                    indCompetition = true;
                    indUpDown = 1;
                }

                // stock comes down
                if ( index <= indRunner - margin ) {
                    competition_Number = 1;
                    indCompetition = true;
                    indUpDown = 2;
                }
            } else {

                bool = true;

                // stock comes up
                if ( index >= indRunner + margin ) {
                    competition_Number = 1;
                    indCompetition = true;
                    indUpDown = 1;
                }

                // stock comes down
                if ( index <= indRunner - margin ) {
                    competition_Number = 1;
                    indCompetition = true;
                    indUpDown = 2;
                }

                // hoze comes up
                if ( future >= conRunner + margin ) {
                    competition_Number = 1;
                    conCompetion = true;
                    conUpDown = 1;
                }

                // hoze comes down
                if ( future <= conRunner - margin ) {
                    competition_Number = 1;
                    conCompetion = true;
                    conUpDown = 2;
                }
            }
        }
        /**
         * In one competition
         **/
        if ( competition_Number == 1 ) {

            // hoze start the competition
            if ( conCompetion ) {

                // hoze is up
                if ( conUpDown == 1 ) {

                    // Exit 1 : no winners
                    if ( future <= conRunner ) {
                        competition_Number = 0;
                        conCompetion = false;
                        conUpDown = 0;
                    }

                    // Exit 2 : hoze win
                    if ( index >= indRunner + margin ) {
                        competition_Number = 0;
                        conCompetion = false;
                        conUpDown = 0;

//                        noisy( panel.conRacesField, Themes.GREEN );
                        getClient( ).conUpPlus( );
//                        conRunnerUpCount = 1;

                        conRunner = future;
                        indRunner = index;
                        return;
                    }

                    // new Competition
                    if ( index < indRunner - margin && !indCompetition ) {
                        competition_Number = 2;
                        indCompetition = true;
                        indUpDown = 2;
                    }
                }

                // hoze is down
                if ( conUpDown == 2 ) {

                    // Exit 1 : no winners
                    if ( future >= conRunner ) {
                        competition_Number = 0;
                        conCompetion = false;
                        conUpDown = 0;
                    }

                    // Exit 2 : hoze win
                    if ( index <= indRunner - margin ) {
                        competition_Number = 0;
                        conCompetion = false;
                        conUpDown = 0;

                        getClient( ).conDownPlus( );
//                        conRunnerDownCount = 1;
//                        noisy( panel.conRacesField, Themes.RED );
                        conRunner = future;
                        indRunner = index;
                    }

                    // Exit 3 : new Competition
                    if ( index > indRunner + margin && !indCompetition ) {
                        competition_Number = 2;
                        indCompetition = true;
                        indUpDown = 1;
                    }
                }
            }
            // stock start the competition
            if ( indCompetition ) {

                // stock is up
                if ( indUpDown == 1 ) {

                    // Exit 1 : no winners
                    if ( index <= indRunner ) {
                        competition_Number = 0;
                        indCompetition = false;
                        indUpDown = 0;
                    }

                    // Exit 2 : stock win
                    if ( future >= conRunner + margin ) {
                        competition_Number = 0;
                        indCompetition = false;
                        indUpDown = 0;

                        getClient( ).indUpPlus( );
//                        indRunnerUpCount = 1;
//                        noisy( panel.indRacesField, Themes.GREEN );

                        conRunner = future;
                        indRunner = index;
                    }

                    // Exit 3 : new competition
                    if ( future < conRunner - margin && !conCompetion ) {
                        competition_Number = 2;
                        conCompetion = true;
                        conUpDown = 2;
                    }
                }

                // stock is down
                if ( indUpDown == 2 ) {

                    // Exit 1 : no winners
                    if ( index >= indRunner ) {
                        competition_Number = 0;
                        indCompetition = false;
                        indUpDown = 0;
                    }

                    // Exit 2 : stock win
                    if ( future <= conRunner - margin ) {
                        competition_Number = 0;
                        indCompetition = false;
                        indUpDown = 0;

                        getClient( ).indDownPlus( );
//                        indRunnerDownCount = 1;
//                        noisy( panel.indRacesField, Themes.RED );

                        conRunner = future;
                        indRunner = index;
                    }

                    // Exit 3 : new competition
                    if ( future > conRunner + margin && !conCompetion ) {
                        competition_Number = 2;
                        conCompetion = true;
                        conUpDown = 1;
                    }
                }
            }
        }
        /**
         * In two competitions
         **/
        if ( competition_Number == 2 ) {

            // hoze up stock down
            if ( conUpDown == 1 && indUpDown == 2 ) {

                // Exit 3 : hoze close his competition
                if ( future <= conRunner ) {
                    competition_Number = 1;
                    conCompetion = false;
                    conUpDown = 0;
                }

                // Exit 4 : stock close his competition
                if ( index >= indRunner ) {
                    competition_Number = 1;
                    indCompetition = false;
                    indUpDown = 0;
                }
            }

            // stock up hoze down
            if ( indUpDown == 1 && conUpDown == 2 ) {

                // Exit 1 : hoze close his competition
                if ( future >= conRunner ) {
                    competition_Number = 1;
                    conCompetion = false;
                    conUpDown = 0;
                }

                // Exit 2 : stock close his competition
                if ( index <= indRunner ) {
                    competition_Number = 1;
                    indCompetition = false;
                    indUpDown = 0;
                }
            }
        }

        // fix 1
        if ( competition_Number == 2 ) {
            if ( !conCompetion || !indCompetition ) {
                conCompetion = false;
                indCompetition = false;
                competition_Number = 0;
                conRunner = future;
                indRunner = index;
                conUpDown = 0;
                indUpDown = 0;
            }
        }

        // fix 2
        if ( conCompetion && !indCompetition ) {
            if ( competition_Number == 2 ) {
                conCompetion = false;
                indCompetition = false;
                competition_Number = 0;
                conRunner = future;
                indRunner = index;
                conUpDown = 0;
                indUpDown = 0;
            }
        }

        // fix 3
        if ( indCompetition && !conCompetion ) {
            if ( competition_Number == 2 ) {
                conCompetion = false;
                indCompetition = false;
                competition_Number = 0;
                conRunner = future;
                indRunner = index;
                conUpDown = 0;
                indUpDown = 0;
            }
        }

        // fix 4
        if ( !conCompetion && !indCompetition && competition_Number == 1 ) {
            conCompetion = false;
            indCompetition = false;
            competition_Number = 0;
            conRunner = future;
            indRunner = index;
            conUpDown = 0;
            indUpDown = 0;
        }

        // setText to the window
        updateRaces( );

    }


    public void close() {
        run = false;
    }

    // SetText
    private void updateRaces() {
        getClient( ).setConUp( getClient( ).getConUp( ) + conRunnerUpCount );
        getClient( ).setConDown( getClient( ).getConDown( ) + conRunnerDownCount );
        getClient( ).setIndexUp( getClient( ).getIndexUp( ) + indRunnerUpCount );
        getClient( ).setIndexDown( getClient( ).getIndexDown( ) + indRunnerDownCount );

        // regular count
        conRunnerUpCount = 0;
        conRunnerDownCount = 0;
        indRunnerUpCount = 0;
        indRunnerDownCount = 0;
    }


    // Refresh the logic variables
    public void refresh() {
        conRunner = 0;
        indRunner = 0;
        competition_Number = 0;

        conUpDown = 0;
        indUpDown = 0;

        conCompetion = false;
        indCompetition = false;

        first = false;
        bool = true;
    }


}

