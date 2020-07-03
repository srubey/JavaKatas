package edu.pdx.cs410J.scrubey;

import edu.pdx.cs410J.AbstractPhoneCall;

/**
 * This class is represents a <code>PhoneCall</code>.
 */
public class PhoneCall extends AbstractPhoneCall {

  protected String callerNumber;
  protected String calleeNumber;
  protected String startDate;
  protected String startTime;
  protected String endDate;
  protected String endTime;

  /**
   * Creates a new <code>PhoneCall</code>
   *
   * @param caller
   *        The 10-digit telephone number of the caller in nnn-nnn-nnnn format
   * @param callee
   *        The 10-digit telephone number of the callee in nnn-nnn-nnnn format
   * @param startDate
   *        The starting date of the telephone call in mm/dd/yyyy format.
   *        mm and dd may start with leading zeros, if applicable.
   * @param startTime
   *        The starting time of the telephone call in hh:mm format.
   *        hh may start with a leading zero, if applicable.
   * @param endDate
   *        The starting date of the telephone call in mm/dd/yyyy format.
   *        mm and dd may start with leading zeros, if applicable.
   * @param endTime
   *        The ending time of the telephone call in hh:mm format.
   *        hh may start with a leading zero, if applicable.
   */
  protected PhoneCall(String caller, String callee, String startDate, String startTime, String endDate, String endTime){
    this.callerNumber = caller;
    this.calleeNumber = callee;
    this.startDate = startDate;
    this.startTime = startTime;
    this.endDate = endDate;
    this.endTime = endTime;
  }

  /**
   * @return the caller's telephone number
   */
  @Override
  public String getCaller() {
    return this.callerNumber;
  }

  /**
   * @return the callee's telephone number
   */
  @Override
  public String getCallee() {
    return this.calleeNumber;
  }

  /**
   * @return the start time of the telephone call
   */
  @Override
  public String getStartTimeString() {
    return this.startTime;
  }

  /**
   * @return the end time of the telephone call
   */
  @Override
  public String getEndTimeString() {
    return this.endTime;
  }
}
