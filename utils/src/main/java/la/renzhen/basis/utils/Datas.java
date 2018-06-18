package la.renzhen.basis.utils;

import com.google.common.collect.Lists;
import la.renzhen.basis.Tuple;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 日期工具类<p>
 *
 * @author <a href="mailto:zhouhaichao@2008.sina.com">haiker</a>
 * @version 18/06/2018 10:04 PM
 */
public class Datas {

    static final String FORMATE_TIME = "HH:mm:ss";
    static final String DEFAULT_FORMATE = "yyyy-MM-dd HH:mm:ss";


    public static Date now() {
        return new Date();
    }

    public static Date yesday() {
        return offset(-1);
    }

    public static Date weekDay(int day) {
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, day);
        return cal.getTime();
    }


    /**
     * 获取星期一
     */
    public static Date monday() {
        return weekDay(Calendar.MONDAY);
    }

    /**
     * 获取星期天
     */
    public static Date sunday() {
        return weekDay(Calendar.SUNDAY);
    }


    public static String format(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static String format(Date date) {
        return format(date, DEFAULT_FORMATE);
    }

    public static Date monthFirstDay(){
        return monthFirstDay(now());
    }

    public static Date monthFirstDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        return calendar.getTime();
    }

    public static Date monthLastDay(){
        return monthLastDay(now());
    }

    public static Date monthLastDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,1);
        calendar.set(Calendar.DAY_OF_MONTH,0);
        return calendar.getTime();
    }

    /**
     * 优雅的方式显示日期
     *
     * @param date 需要显示的日期
     * @return 显示内容
     */
    public static String petty(Date date) {
        Calendar calendar = Calendar.getInstance();
        long delta = calendar.getTimeInMillis() - date.getTime();
        if(delta < 0){
            return format(date);
        }

        if (delta < TimeUnit.MINUTES.toMillis(1)) {
            return TimeUnit.MILLISECONDS.toSeconds(delta) + "秒前";
        } else if (delta < TimeUnit.HOURS.toMillis(1)) {
            return TimeUnit.MILLISECONDS.toMinutes(delta) + "分钟前";
        } else if (delta < TimeUnit.DAYS.toMinutes(1)) {
            return TimeUnit.MILLISECONDS.toHours(delta) + "小时前";
        } else {
            switch ((int) TimeUnit.MILLISECONDS.toDays(delta)) {
                case 1:
                    return "昨天 " + format(date, FORMATE_TIME);
                case 2:
                    return "前天 " + format(date, FORMATE_TIME);
                default:
                    return format(date, DEFAULT_FORMATE);
            }
        }
    }

    public static Date round(Date date, int field) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (field) {
            case Calendar.HOUR:
                calendar.set(Calendar.HOUR_OF_DAY, 0);
            case Calendar.MINUTE:
                calendar.set(Calendar.MINUTE, 0);
            case Calendar.SECOND:
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
        }
        return calendar.getTime();
    }

    public static Date round(int field) {
        return round(new Date(), field);
    }

    public static Date round(Date date) {
        return round(date, Calendar.HOUR);
    }

    public static Date offset(int offset) {
        return offset(new Date(), Calendar.DAY_OF_YEAR, offset);
    }

    public static Date offset(int field, int offset) {
        return offset(new Date(), field, offset);
    }

    public static Date offset(Date date, int offset) {
        return offset(date, Calendar.DAY_OF_YEAR, offset);
    }

    public static Date offset(Date date, int field, int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, offset);
        return calendar.getTime();
    }

    public static Date parseDay(String date) {
        return parse(date + " 00:00:00", DEFAULT_FORMATE);
    }

    public static Date parse(String date) {
        return parse(date, DEFAULT_FORMATE);
    }

    @SneakyThrows
    public static Date parse(String date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.parse(date);
    }

    public static List<Date> list(Date start, Date end, TimeUnit unit) {
        List<Date> dates = Lists.newArrayList();
        iterator(start, end, unit).forEachRemaining(dates::add);
        return dates;
    }

    public static Iterator<Date> iterator(Date start, Date end, TimeUnit unit) {
        return new DateIterator(start.getTime(), end.getTime(), unit);
    }

    public static Tuple.Pair<Date,Date> weekRange(){

    }

    static class DateIterator implements Iterator<Date> {
        private long start;
        private long end;
        private TimeUnit unit;
        DateIterator(final long start, final long end, final TimeUnit unit) {
            this.start = start;
            this.end = end;
            this.unit = unit;
        }
        @Override
        public boolean hasNext() {
            start += unit.toMillis(1);
            return start < end;
        }
        @Override
        public Date next() {
            if (start > end) {
                throw new NoSuchElementException();
            }
            return new Date(start);
        }
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}