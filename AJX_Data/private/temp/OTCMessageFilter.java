package com.riskval.portfolio.filter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.protobuf.Message;
import com.riskval.analytics.RVDate;
import com.riskval.core.DateUtil;
import com.riskval.message.filter.MessageFilter;
import com.riskval.portfolio.filter.OTCMessageFilter.Builder.FilterWrapper;
import com.riskval.portfolio.filter.OTCMessageFilter.Builder.Operator;
import com.riskval.portfolio.otcdata.OTCDataField;

public class OTCMessageFilter implements MessageFilter {
	// key->name of col, value->value needed to pass the filter
	private Map<OTCDataField, Object> filterConditions;
	private Map<OTCDataField, FilterWrapper> filterConditionsWithOperator;

	private OTCMessageFilter(Map<OTCDataField, Object> filterConditions) {
		this.filterConditions = new HashMap<OTCDataField, Object>(filterConditions);
	}

	private OTCMessageFilter(Map<OTCDataField, Object> filterConditions,
			Map<OTCDataField, FilterWrapper> filterConditionsWithOperator) {
		this.filterConditions = new HashMap<OTCDataField, Object>(filterConditions);
		this.filterConditionsWithOperator = new HashMap<OTCDataField, FilterWrapper>(filterConditionsWithOperator);
	}

	/**
	 * Refer to LiveBucketRiskReceiverFilterTest class for example to build and use
	 * the msg filter.
	 * 
	 * @return
	 */
	public static class Builder {
		private Map<OTCDataField, Object> filterConditions;
		private Map<OTCDataField, FilterWrapper> filterConditionsWithOperator;

		public Builder() {
			filterConditions = new HashMap<OTCDataField, Object>();
			filterConditionsWithOperator = new HashMap<OTCDataField, FilterWrapper>();
		}

		public OTCMessageFilter build() {
			return new OTCMessageFilter(this.filterConditions, this.filterConditionsWithOperator);
		}

		/**
		 * This method takes in a key, value pair and adds to the map and returns the
		 * same object back to the caller. Special case for Date and RVDate since they
		 * are transferred as long from protobuf. For example 2018-09-17 will be stored
		 * as 20190917 long. Uses DateUtil to convert to the long value
		 */
		public Builder addCriteria(OTCDataField key, Object value) {
			if (value instanceof RVDate) {
				// convert into long, since data from protobuf is in long format
				filterConditions.put(key, Long.valueOf(DateUtil.dateToYYYMMDD(((RVDate) value).toDate())));
			} else if (value instanceof Date) {
				filterConditions.put(key, Long.valueOf(DateUtil.dateToYYYMMDD((Date) value)));
			} else {
				filterConditions.put(key, value);
			}
			return this;
		}

		public Builder addCriteria(OTCDataField key, Operator operator, Object value) {
			if (value instanceof RVDate) {
				// convert into long, since data from protobuf is in long format
				filterConditions.put(key, Long.valueOf(DateUtil.dateToYYYMMDD(((RVDate) value).toDate())));
				filterConditionsWithOperator.put(key,
						new FilterWrapper(operator, Long.valueOf(DateUtil.dateToYYYMMDD(((RVDate) value).toDate()))));
			} else if (value instanceof Date) {
				filterConditions.put(key, Long.valueOf(DateUtil.dateToYYYMMDD((Date) value)));
				filterConditionsWithOperator.put(key,
						new FilterWrapper(operator, Long.valueOf(DateUtil.dateToYYYMMDD((Date) value))));
			} else {
				filterConditions.put(key, value);
				filterConditionsWithOperator.put(key, new FilterWrapper(operator, value));
			}
			return this;
		}

		public enum Operator {
			EQUALS("="), GROUPBY("groupby"), NOT_EQUALS("!="), GREATER(">"), LOWER("<"), GREATER_EQUALS(">="),
			LOWER_EQUALS("<="), LIKE("like"), NOT_LIKE("not-like"), START_WITH("start-with"), END_WITH("end-with"),
			IN("in"), NOT_IN("not-in"), IS_NULL("is-null"), IS_NOT_NULL("is-not-null");

			private String val;

			private Operator(String operator) {
				this.val = operator;
			}

			@Override
			public String toString() {
				return val;
			}
		}

		public class FilterWrapper {
			private Object value;
			private Operator operator;

			public FilterWrapper(Operator operator, Object value) {
				this.value = value;
				this.operator = operator;
			}

			public Object getObject() {
				return this.value;
			}

			public Operator getOperator() {
				return this.operator;
			}
		}
	}

	@Override
	public boolean accept(Message msg) {
		// go through the filter conditions and see if the message satisfies them
		for (Map.Entry<OTCDataField, Object> entry : filterConditions.entrySet()) {
			if (!msg.getField(entry.getKey().getProtoField()).equals(entry.getValue())) {
				return false;
			}
		}
		return true;
	}
	
	public boolean acceptNew(Message msg) {
		// go through the filter conditions and see if the message satisfies them
		for (Map.Entry<OTCDataField, Object> entry : filterConditions.entrySet()) {
			if (filterConditionsWithOperator.containsKey(entry.getKey())) {
				
				OTCDataField key = entry.getKey();
				Object msgField = msg.getField(key.getProtoField());
				FilterWrapper wrap = filterConditionsWithOperator.get(key);
				
				if (!canPass(key, msgField, wrap.getOperator(), wrap.getObject())) {
					return false;
				}
			} else if (!msg.getField(entry.getKey().getProtoField()).equals(entry.getValue())) {
				return false;
			}
		}
		return true;
	}

	private boolean canPass(OTCDataField key, Object msgField, Operator operator, Object value) {
		switch (operator) {
		case EQUALS: return msgField.equals(value);
		case GROUPBY: return false;
		case NOT_EQUALS: return !msgField.equals(value);
		case GREATER: return isGreater(msgField, value);
		case LOWER: return isLower(msgField, value);
		case GREATER_EQUALS: return isGreaterEquals(msgField, value);
		case LOWER_EQUALS: return isLowerEquals(msgField, value);
		case LIKE: return false;
		case NOT_LIKE: return false;
		case START_WITH: return false;
		case END_WITH: return false;
		case IN: return false;
		case NOT_IN: return false;
		case IS_NULL: return msgField == null;
		case IS_NOT_NULL: return msgField != null;
		default:
			System.err.println("Not supported: " + operator);
			return false;
		}
	}

	private boolean isLowerEquals(Object msgField, Object testValue) {
		if (msgField == null && testValue == null) {
			return false; // might want to throw error here
		}
		// if it is the class => true, else if not the class => false
		// if it's null => false **
		if (msgField instanceof Integer && testValue instanceof Integer) {
			return (((Integer) msgField).compareTo((Integer) testValue) <= 0);
		} else if (msgField instanceof Long && testValue instanceof Long) {
			return (((Long) msgField).compareTo((Long) testValue) <= 0);
		}
		return false;
	}

	private boolean isGreaterEquals(Object msgField, Object testValue) {
		if (msgField == null && testValue == null) {
			return false;
		}
		if (msgField instanceof Integer && testValue instanceof Integer) {
			return (((Integer) msgField).compareTo((Integer) testValue) >= 0);
		} else if (msgField instanceof Long && testValue instanceof Long) {
			return (((Long) msgField).compareTo((Long) testValue) >= 0);
		}
		return false;
	}

	private boolean isLower(Object msgField, Object testValue) {
		if (msgField == null && testValue == null) {
			return false;
		}
		if (msgField instanceof Integer && testValue instanceof Integer) {
			return (((Integer) msgField).compareTo((Integer) testValue) < 0);
		} else if (msgField instanceof Long && testValue instanceof Long) {
			return (((Long) msgField).compareTo((Long) testValue) < 0);
		}
		return false;
	}

	private boolean isGreater(Object msgField, Object testValue) {
		if (msgField == null && testValue == null) {
			return false; // throw?
		}
		if (msgField instanceof Integer && testValue instanceof Integer) {
			return (((Integer) msgField).compareTo((Integer) testValue) > 0);
		} else if (msgField instanceof Long && testValue instanceof Long) {
			return (((Long) msgField).compareTo((Long) testValue) > 0);
		}
		return false;
	}

	public String toString() {
		// return filter conditions in pretty format
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<OTCDataField, Object> entry : filterConditions.entrySet()) {
			if (filterConditionsWithOperator.containsKey(entry.getKey())) {
				sb.append(entry.getKey().getSQLName()).append(" ")
						.append(filterConditionsWithOperator.get(entry.getKey()).getOperator().toString()).append(" ")
						.append(filterConditionsWithOperator.get(entry.getKey()).getObject()).append(";");
			} else {
				sb.append(entry.getKey().getSQLName()).append(" : ").append(entry.getValue()).append(";");
			}
		}
		return sb.toString();
	}
}