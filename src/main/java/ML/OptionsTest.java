package ML;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OptionsTest {


    List<StrikeTest> strikes;
    HashMap<Integer, OptionTest> optionsMap;

    public OptionsTest() {
        strikes = new ArrayList<StrikeTest>();
        optionsMap = new HashMap<Integer, OptionTest>();
    }

    // Test optionsTest
    public static void main(String[] args) {

        OptionsTest optionsTest = new OptionsTest();

        // Create call
        OptionTest call = new OptionTest("c", 3130, 0);
        // Create put
        OptionTest put = new OptionTest("p", 3130, 0);
        // Create strike with call and put inside
        StrikeTest strike = new StrikeTest(call, put, 3130);
        // Add the strike to optionsTest class
        optionsTest.addStrike(strike);

        System.out.println(optionsTest.toStringVertical());
    }

    public OptionTest getOption(String name) {

        double targetStrike = Double.parseDouble(name.substring(1));

        for (StrikeTest strike : strikes) {
            if (strike.getStrike() == targetStrike) {
                if (name.toLowerCase().contains("c")) {
                    return strike.getCall();
                } else {
                    return strike.getPut();
                }
            }
        }
        return null;
    }

    public StrikeTest getStrikeInMoney(double index, int strikeFarLevel) {

        double margin = 100000;
        int returnIndex = 0;

        List<StrikeTest> strikes = getStrikes();

        for (int i = 0; i < strikes.size(); i++) {

            StrikeTest strike = strikes.get(i);
            double newMargin = absolute(strike.getStrike() - index);

            if (newMargin < margin) {
                margin = newMargin;
                returnIndex = i;
            } else {
                return strikes.get(returnIndex + strikeFarLevel);
            }
        }
        return null;
    }

    public OptionTest getOption(String side, double targetStrike) {
        for (StrikeTest strike : strikes) {
            if (strike.getStrike() == targetStrike) {
                if (side.toLowerCase().contains("c")) {
                    return strike.getCall();
                } else {
                    return strike.getPut();
                }
            }
        }
        return null;
    }

    // Return single strike by strike price (double)
    public StrikeTest getStrike(double strikePrice) {
        for (StrikeTest strike : strikes) {
            if (strikePrice == strike.getStrike()) {
                return strike;
            }
        }
        return null;
    }

    // Return list of strikes prices
    public ArrayList<Double> getStrikePricesList() {
        ArrayList<Double> list = new ArrayList<>();
        strikes.forEach(strike -> list.add(strike.getStrike()));
        return list;
    }

    // Remove strike from strikes arr by strike price (double)
    public void removeStrike(double strike) {
        int indexToRemove = 0;

        for (int i = 0; i < strikes.size(); i++) {
            if (strikes.get(i).getStrike() == strike) {
                indexToRemove = i;
            }
        }
        strikes.remove(indexToRemove);
    }

    // Remove strike from strikes arr by strike class
    public void removeStrike(StrikeTest strike) {
        strikes.remove(strike);
    }

    // Add strike to strikes arr
    public void addStrike(StrikeTest strike) {

        boolean contains = getStrikePricesList().contains(strike.getStrike());

        // Not inside
        if (!contains) {
            strikes.add(strike);
        }
    }

    public OptionTest getOptionById(int id) {
        return optionsMap.get(id);
    }

    // Set option in strikes arr
    public void setOption(OptionTest optionTest) {
        // HashMap
        optionsMap.put(optionTest.getId(), optionTest);

        // Strikes list
        boolean callPut = optionTest.getSide().toLowerCase().contains("c");

        StrikeTest strike = getStrike(optionTest.getStrike());

        if (strike != null) {

            if (callPut) {
                if (strike.getCall() == null) {
                    strike.setCall(optionTest);
                }
            } else {
                if (strike.getPut() == null) {
                    strike.setPut(optionTest);
                }
            }
        } else {

            // Create new if doesn't exist
            strike = new StrikeTest();
            strike.setStrike(optionTest.getStrike());

            if (callPut) {
                strike.setCall(optionTest);
            } else {
                strike.setPut(optionTest);
            }

            // Add strike
            addStrike(strike);
        }
    }

    public List<StrikeTest> getStrikes() {
        return strikes;
    }

    public void setStrikes(List<StrikeTest> strikes) {
        this.strikes = strikes;
    }

    public String toStringVertical() {
        String string = "";
        for (StrikeTest strike : strikes) {
            string += strike.toString() + "\n\n";
        }
        return string;
    }

    public JSONObject getOptionsAsJson() {
        JSONObject json = new JSONObject();

        JSONObject callJson;
        JSONObject putJson;
        JSONObject strikeJson;

        for (StrikeTest strike : strikes) {

            callJson = new JSONObject();
            putJson = new JSONObject();
            strikeJson = new JSONObject();

            OptionTest call = strike.getCall();
            callJson.put("bid", call.getBid());
            callJson.put("ask", call.getAsk());
            callJson.put("bid_ask_counter", call.getBidAskCounter());

            OptionTest put = strike.getPut();
            putJson.put("bid", put.getBid());
            putJson.put("ask", put.getAsk());
            putJson.put("bid_ask_counter", put.getBidAskCounter());

            strikeJson.put("call", callJson);
            strikeJson.put("put", putJson);

            json.put(str(strike.getStrike()), strikeJson);
        }
        return json;
    }


    public String str(Object o) {
        return String.valueOf(o);
    }


    public double absolute(double d) {
        return Math.abs(d);
    }


}
