package apidemo;

import apidemo.TicketDlg.AmntUnit;
import apidemo.util.TCombo;
import apidemo.util.UpperField;
import com.ib.client.Order;
import com.ib.client.OrderCondition;
import com.ib.client.OrderType;

import javax.swing.*;

public class AdjustedPanel extends OnOKPanel {
	final TCombo < OrderType > m_adjustedOrderType = new TCombo <> ( OrderType.None, OrderType.STP, OrderType.STP_LMT, OrderType.TRAIL, OrderType.TRAIL_LIMIT );
	final UpperField m_triggerPrice = new UpperField ( );
	final UpperField m_adjustedStopPrice = new UpperField ( );
	final UpperField m_adjustedStopLimitPrice = new UpperField ( );
	final UpperField m_adjustedTrailingAmount = new UpperField ( );
	final TCombo < AmntUnit > m_adjustedTrailingAmountUnit = new TCombo <> ( AmntUnit.values ( ) );
	/**
	 *
	 */
	private final JDialog m_parentDlg;
	private final Order m_order;

	public AdjustedPanel ( JDialog parentDlg, Order order ) {
		m_parentDlg = parentDlg;
		m_order = order;
		m_adjustedOrderType.setSelectedItem ( m_order.adjustedOrderType ( ) );

		m_triggerPrice.setText ( m_order.triggerPrice ( ) );
		m_adjustedStopPrice.setText ( m_order.adjustedStopPrice ( ) );
		m_adjustedStopLimitPrice.setText ( m_order.adjustedStopLimitPrice ( ) );
		m_adjustedTrailingAmount.setText ( m_order.adjustedTrailingAmount ( ) );
		m_adjustedTrailingAmountUnit.setSelectedItem ( AmntUnit.fromInt ( m_order.adjustableTrailingUnit ( ) ) );

		add ( "Adjust to order type", m_adjustedOrderType );
		add ( "Trigger price", m_triggerPrice );
		add ( "Adjusted stop price", m_adjustedStopPrice );
		add ( "Adjusted stop limit price", m_adjustedStopLimitPrice );
		add ( "Adjusted trailing amount", m_adjustedTrailingAmount );
		add ( "Adjusted trailing amount unit", m_adjustedTrailingAmountUnit );
	}

	public OrderCondition onOK () {
		m_order.adjustedOrderType ( m_adjustedOrderType.getSelectedItem ( ) );
		m_order.triggerPrice ( m_triggerPrice.getDouble ( ) );
		m_order.adjustedStopPrice ( m_adjustedStopPrice.getDouble ( ) );
		m_order.adjustedStopLimitPrice ( m_adjustedStopLimitPrice.getDouble ( ) );
		m_order.adjustedTrailingAmount ( m_adjustedTrailingAmount.getDouble ( ) );
		m_order.adjustableTrailingUnit ( m_adjustedTrailingAmountUnit.getSelectedItem ( ).m_val );

		return null;
	}
}