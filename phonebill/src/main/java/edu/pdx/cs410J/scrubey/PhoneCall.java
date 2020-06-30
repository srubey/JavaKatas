package edu.pdx.cs410J.scrubey;

import edu.pdx.cs410J.AbstractPhoneCall;

public class PhoneCall extends AbstractPhoneCall {
  protected String callerNumber;
  protected String calleeNumber;
  protected String startDate;
  protected String startTime;
  protected String endDate;
  protected String endTime;

  protected PhoneCall(String caller, String callee, String startDate, String startTime, String endDate, String endTime){
    this.callerNumber = caller;
    this.calleeNumber = callee;
    this.startDate = startDate;
    this.startTime = startTime;
    this.endDate = endDate;
    this.endTime = endTime;
  }

  @Override
  public String getCaller() {
    return this.callerNumber;
  }

  @Override
  public String getCallee() {
    return this.calleeNumber;
  }

  @Override
  public String getStartTimeString() {
    return this.startTime;
  }

  @Override
  public String getEndTimeString() {
    return this.endTime;
  }
}
