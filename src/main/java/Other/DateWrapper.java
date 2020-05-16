package Other;

import Exceptions.NegativeDateException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateWrapper {

    public DateWrapper() {
    }

    public int getDaysBetweenDates(String startDate, String endDate) throws NegativeDateException {
        Date first = null;
        Date second = null;
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        try {
            first = df.parse(startDate);
            second = df.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long difference = second.getTime() - first.getTime();
        long days = difference / (24 * 60 * 60 * 1000);
        if (days < 0) {
            throw new NegativeDateException("end date smaller than start date");
        }
        return (int) days;
    }
}
