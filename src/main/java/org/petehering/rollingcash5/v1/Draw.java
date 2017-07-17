package org.petehering.rollingcash5.v1;

import java.time.DayOfWeek;
import java.util.Date;
import static org.petehering.rollingcash5.v1.CSVReader.*;

public class Draw implements Comparable<Draw>
{
    public final DayOfWeek dayOfWeek;
    public final Date playDate;
    public final int[] numbers;
    public final double jackpot;
    public final int payouts;
    
    public Draw (DayOfWeek dow, Date pd, int[] ns, double jp, int pos)
    {
        this.dayOfWeek = dow;
        this.playDate = pd;
        this.numbers = ns;
        this.jackpot = jp;
        this.payouts = pos;
    }
    
    @Override
    public String toString ()
    {
        return new StringBuilder()
                .append(dayOfWeek.toString()).append(",")
                .append(date.format(playDate)).append(",")
                .append(numbers[0]).append(",")
                .append(numbers[1]).append(",")
                .append(numbers[2]).append(",")
                .append(numbers[3]).append(",")
                .append(numbers[4]).append(",")
                .append(currency.format(jackpot)).append(",")
                .append(payouts).toString();
    }

    @Override
    public int compareTo(Draw that)
    {
        return this.playDate.compareTo(that.playDate);
    }
}
