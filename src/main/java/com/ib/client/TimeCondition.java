package com.ib.client;

public class TimeCondition extends OperatorCondition {

    public static final OrderConditionType conditionType = OrderConditionType.Time;
    private String m_time;

    protected TimeCondition() {
    }

    @Override
    public String toString() {
        return "time" + super.toString( );
    }

    public String time() {
        return m_time;
    }

    public void time( String m_time ) {
        this.m_time = m_time;
    }

    @Override
    protected String valueToString() {
        return m_time;
    }

    @Override
    protected void valueFromString( String v ) {
        m_time = v;
    }

}