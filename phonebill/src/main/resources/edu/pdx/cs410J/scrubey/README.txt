This program parses input from the command line and allows the user to print phone call
information via the '-print' flag.  A program runs successfully given the following
command line input:

java edu.pdx.cs410J.<login-id>.Project1 -README -print "Customer name" Caller-number
   Callee-number startDate startTime endDate endTime

The -README and -print flags are optional.  Note, if the customer's name is more than
one word, it must be contained in quotation marks.  Caller/callee-numbers must be in
nnn-nnn-nnnn format, where all instances of 'n' are digits 0 - 9.  Dates must be entered
as mm/dd/yyyy; mm and dd may contain leading zeros, if applicable.

The user's input is then distributed amongst PhoneBill and PhoneCall objects for use in
subsequent parts of this project.
