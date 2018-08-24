package la.renzhen.basis.utils;

import la.renzhen.basis.Tuple;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 日期类链式处理<p>
 *
 * @author <a href="mailto:zhouhaichao@2008.sina.com">haiker</a>
 * @version 11/08/2018 4:19 PM
 */
public class DateChain {

    SimpleDateFormat format;
    Date date;

    public static DateChain date(Date date) {
        DateChain dataLine = new DateChain();
        dataLine.date = new Date();
        dataLine.format = new SimpleDateFormat(Dates.DEFAULT_FORMATE);
        return dataLine;
    }

    public static DateChain now() {
        return date(new Date());
    }

    public static DateChain yesterday() {
        return now().offset(-1);
    }

    public static DateChain format(String format) {
        DateChain dateLine = now();
        dateLine.format = new SimpleDateFormat(format);
        return dateLine;
    }

    public DateChain pattern(String format) {
        this.format = new SimpleDateFormat(format);
        return this;
    }

    @SneakyThrows
    public DateChain parse(String source) {
        this.date = format.parse(source);
        return this;
    }

    public DateChain offset(int offset) {
        return offset(TimeUnit.DAYS, offset);
    }

    public DateChain offset(TimeUnit field, int offset) {
        this.date = Dates.offset(date, field, offset);
        return this;
    }

    public DateChain offsetHour(int offset) {
        return offset(TimeUnit.HOURS, offset);
    }

    public DateChain offsetMinute(int offset) {
        return offset(TimeUnit.MINUTES, offset);
    }

    public DateChain offsetSecond(int offset) {
        return offset(TimeUnit.SECONDS, offset);
    }

    /**
     * @param field {@link Calendar#HOUR},{@link Calendar#MINUTE},{@link Calendar#SECOND}
     * @return this
     */
    public DateChain round(int field) {
        date = Dates.round(date, field);
        return this;
    }

    public DateChain round() {
        date = Dates.round(date);
        return this;
    }

    public String weekName() {
        return Dates.weekName(date);
    }

    public DateChain monday() {
        date = Dates.monday(date);
        return this;
    }

    public DateChain sunday() {
        date = Dates.sunday(date);
        return this;
    }

    public DateChain weekDay(int day) {
        date = Dates.weekDay(date, day);
        return this;
    }

    public DateChain monthFirstDay() {
        date = Dates.monthFirstDay(date);
        return this;
    }

    public DateChain monthLastDay() {
        date = Dates.monthLastDay(date);
        return this;
    }

    public Tuple.Pair<Date, Date> weekRange() {
        return Dates.weekRange(date);
    }

    public boolean isSameMonth(Date b) {
        return Dates.isSameDay(Dates.monthFirstDay(date), Dates.monthFirstDay(b));
    }

    public boolean isSameDay(Date b) {
        return Dates.isSameDay(date, b);
    }

    public boolean isSameWeek(Date b) {
        return Dates.isSameDay(Dates.monday(date), Dates.monday(b));
    }

    public String petty() {
        return Dates.petty(date);
    }

    public String string() {
        return format.format(date);
    }

    public String string(String format) {
        return Dates.format(date, format);
    }

    public Date date() {
        return date;
    }

    public List<Date> weekDays() {
        return Dates.weekDays(date);
    }

    @Override
    public String toString() {
        return this.string();
    }
}