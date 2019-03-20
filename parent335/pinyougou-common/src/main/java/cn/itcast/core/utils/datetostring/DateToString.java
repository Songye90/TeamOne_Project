package cn.itcast.core.utils.datetostring;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateToString {

    public Date dataToString(String time) {
        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("yyyy-MM-dd");
        try {
            date = dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
