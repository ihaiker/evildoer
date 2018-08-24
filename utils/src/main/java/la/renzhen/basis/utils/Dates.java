package la.renzhen.basis.utils;

import com.google.common.collect.Lists;
import la.renzhen.basis.Tuple;
import lombok.Data;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 日期工具类<p>
 *
 * @author <a href="mailto:zhouhaichao@2008.sina.com">haiker</a>
 * @version 18/06/2018 10:04 PM
 */
public class Dates {

    static final String FORMATE_TIME = "HH:mm:ss";
    static final String DEFAULT_FORMATE = "yyyy-MM-dd HH:mm:ss";


    public static Date now() {
        return new Date();
    }

    public static Date yesterday() {
        return offset(-1);
    }

    public static String yesterday(String format) {
        return format(offset(-1), format);
    }

    public static Date weekDay(int day) {
        return weekDay(now(), day);
    }

    public static Date weekDay(Date date, int day) {
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        cal.setTime(date);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, day);
        return cal.getTime();
    }

    /**
     * @return 获取星期一
     */
    public static Date monday() {
        return weekDay(Calendar.MONDAY);
    }

    /**
     * 获取指定日期所在星期的星期一
     *
     * @param date 日期
     * @return 获取星期一
     */
    public static Date monday(Date date) {
        return weekDay(date, Calendar.MONDAY);
    }

    /**
     * @param date 指定日期
     * @return 指定日期的所在周的星期天
     */
    public static Date sunday(Date date) {
        return weekDay(date, Calendar.SUNDAY);
    }

    /**
     * @return 获取星期天
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

    public static Date monthFirstDay() {
        return monthFirstDay(now());
    }

    public static Date monthFirstDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date monthLastDay() {
        return monthLastDay(now());
    }

    public static Date monthLastDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
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
        if (delta < 0) {
            return format(date);
        }

        if (delta < TimeUnit.MINUTES.toMillis(1)) {
            return TimeUnit.MILLISECONDS.toSeconds(delta) + "秒前";
        } else if (delta < TimeUnit.HOURS.toMillis(1)) {
            return TimeUnit.MILLISECONDS.toMinutes(delta) + "分钟前";
        } else if (delta < TimeUnit.DAYS.toMillis(1)) {
            return TimeUnit.MILLISECONDS.toHours(delta) + "小时前";
        } else {
            int day = (int) TimeUnit.MILLISECONDS.toDays(delta);
            switch (day) {
                case 1:
                    return "昨天 " + format(date, FORMATE_TIME);
                case 2:
                    return "前天 " + format(date, FORMATE_TIME);
                default:
                    if (day < 7) {
                        if (isSameWeek(now(), date)) {
                            return "本" + weekName(date) + format(date, " HH:mm:ss");
                        } else {
                            return "上" + weekName(date) + format(date, " HH:mm:ss");
                        }
                    }
            }
        }
        return format(date, DEFAULT_FORMATE);
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
        return offset(new Date(), TimeUnit.DAYS, offset);
    }

    public static Date offset(TimeUnit field, int offset) {
        return offset(new Date(), field, offset);
    }

    public static Date offset(Date date, int offset) {
        return offset(date, TimeUnit.DAYS, offset);
    }

    public static Date offset(Date date, TimeUnit field, int offset) {
        return new Date(date.getTime() + field.toMillis(offset));
    }

    public static Date offset(Date date, Duration duration){
        return new Date(date.getTime() + duration.toMillis());
    }
    public static Date offset(Duration duration){
        return offset(new Date(),duration);
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

    public static Tuple.Pair<Date, Date> weekRange() {
        return weekRange(now());
    }

    public static List<Date> weekDays() {
        return weekDays(now());
    }

    public static Tuple.Pair<Date, Date> weekRange(Date date) {
        return Tuple.T.pair(round(monday(date)), round(sunday(date)));
    }

    public static List<Date> weekDays(Date date) {
        List<Date> days = new ArrayList<>();
        Tuple.Pair<Date, Date> range = weekRange(date);
        days.add(range.getA());
        days.addAll(list(round(range.getA()), round(range.getB()), TimeUnit.DAYS));
        days.add(range.getB());
        return days;
    }

    public static boolean isSameDay(Date a, Date b) {
        return round(a).equals(round(b));
    }

    public static boolean isSameWeek(Date a, Date b) {
        return isSameDay(monday(a), monday(b));
    }

    public static boolean isSameMonth(Date a, Date b) {
        return isSameDay(monthFirstDay(a), monthFirstDay(b));
    }

    public static String weekName(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekCode = calendar.get(Calendar.DAY_OF_WEEK);
        switch (weekCode) {
            case Calendar.MONDAY:
                return "周一";
            case Calendar.TUESDAY:
                return "周二";
            case Calendar.WEDNESDAY:
                return "周三";
            case Calendar.THURSDAY:
                return "周四";
            case Calendar.FRIDAY:
                return "周五";
            case Calendar.SATURDAY:
                return "周六";
            case Calendar.SUNDAY:
                return "周日";
        }
        return "";
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