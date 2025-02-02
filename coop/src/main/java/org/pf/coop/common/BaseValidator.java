package org.pf.coop.common;

import java.math.BigDecimal;

public class BaseValidator {

	/**
	 * Checks whether the provided string is alphanumeric with underscore. Returns true if check succeeds, false otherwise.
	 * @param str - the string to be checked.
	 * @return true or false based on the check.
	 */
	public boolean isAlphaNumericWithUnderscore(String str){
		if (str.matches("^[A-Za-z0-9_]+$")){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks whether the provided string is alpha numeric. Returns true if check succeeds, false otherwise.
	 * @param str - the string to be checked.
	 * @return true or false based on the check.
	 */
	public boolean isAlphaNumeric(String str){
		if (str.matches("^[A-Za-z0-9]+$")){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks whether the provided string is alpha numeric with space. Returns true if check succeeds, false otherwise. 
	 * @param str - the string to be checked.
	 * @return true or false based on the check.
	 */
	public boolean isAlphaNumericWithSpace(String str){
		if (str.matches("^[A-Za-z0-9 ]+$")){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks whether the provided string is numeric. Returns true if check succeeds, false otherwise.
	 * @param str - the string to be checked.
	 * @return true or false based on the check.
	 */
	public boolean isNumeric(String str){
		if (str.matches("^[0-9]+$")){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks whether the provided string is numeric with spaces. Returns true if check succeeds, false otherwise.
	 * @param str - the string to be checked.
	 * @return true or false based on the check.
	 */
	public boolean isNumericWithSpace(String str){
		if (str.matches("^[0-9 ]+$")){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks whether the provided string is a valid email address. Returns true is check succeeds, false otherwise.
	 * @param str - the string to be checked.
	 * @return true or false based on the check.
	 */
	public boolean isEmail(String str){
		if (str.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks whether the length of the provided string is between startLength and endLength, both inclusive. 
	 * Returns true if check succeeds, false otherwise.
	 * @param str - the string to be checked.
	 * @param startLength - the minimum length
	 * @param endLength - the maximum length
	 * @return true or false based on the check.
	 */
	public boolean lengthRange(String str, int startLength, int endLength){
		if (str.length()>=startLength && str.length()<=endLength){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks whether the provided value falls within startValue and endValue, both inclusive. Returns true if check succeeds, false otherwise.
	 * @param val - the value (Long type) to be checked.
	 * @param startValue - the minimum value
	 * @param endValue - the maximum value
	 * @return true or false based on the check.
	 */
	public boolean valueRange(Long val, Long startValue, Long endValue){
		if (val.longValue() >= startValue.longValue() && val.longValue() <= endValue.longValue()){
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks whether the provided value falls within startValue and endValue, both inclusive. Returns true if check succeeds, false otherwise.
	 * @param val - the value (BigDecimal type) to be checked.
	 * @param startValue - the minimum value
	 * @param endValue - the maximum value
	 * @return true or false based on the check.
	 */
	public boolean valueRange(BigDecimal val, BigDecimal startValue, BigDecimal endValue){
		if (val.compareTo(startValue) >= 0 && val.compareTo(endValue) <= 0){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks whether the provided value is a negative value. Returns true if check succeeds, false otherwise.
	 * @param val - the value (BigDecimal type) to be checked.
	 * @return true or false based on the check.
	 */
	public boolean isNegative(BigDecimal val){
		if (val.compareTo(new BigDecimal(0.00))<0){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks whether the provided value is less than zero. Returns true if check succeeds, false otherwise.
	 * @param val - the value (BigDecimal type) to be checked.
	 * @return true or false based on the check.
	 */	
	public boolean isZeroOrNegative(BigDecimal val){
		if (val.compareTo(new BigDecimal(0.00))<=0){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks whether the provided value is a negative value. Returns true if check succeeds, false otherwise.
	 * @param val - the value (Long type) to be checked.
	 * @return true or false based on the check.
	 */	
	public boolean isNegative(Long val){
		if (val.longValue()<0){
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks whether the provided value is less than zero. Returns true if check succeeds, false otherwise.
	 * @param val - the value (Long type) to be checked.
	 * @return true or false based on the check.
	 */	
	public boolean isZeroOrNegative(Long val){
		if (val.longValue()<=0){
			return true;
		} else {
			return false;
		}
	}
	
}
