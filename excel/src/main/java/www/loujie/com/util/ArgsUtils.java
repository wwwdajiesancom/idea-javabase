package www.loujie.com.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @name loujie
 * @date 2019/2/20
 */
public class ArgsUtils {

    /**
     * 格式化日期，根据不同的格式
     *
     * @param date   时间
     * @param format 格式[yyyy][-MM][-dd] [HH][:mm][:ss]
     * @return String
     */
    public static String formatDate(Date date, String format) {
        if (date == null) {
            date = Calendar.getInstance().getTime();
        }
        return new SimpleDateFormat(format).format(date);
    }
}
