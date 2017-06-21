package org.petehering.rollingcash5.v1;

import java.io.PrintStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Draw implements Serializable, Comparable<Draw>
{

    private static final NumberFormat CFMT = NumberFormat.getCurrencyInstance();
    private static final DateFormat DFMT = new SimpleDateFormat("MM/dd/yyy");
    private final Date date;
    private final int[] numbers;
    private final int kicker;
    private final double jackpot;
    private final double payout;

    public Draw(Date date, int[] numbers, int kicker, double jackpot, double payout)
    {
        this.date = Objects.requireNonNull(date);
        this.numbers = Objects.requireNonNull(numbers);
        this.kicker = kicker;
        this.jackpot = jackpot;
        this.payout = payout;
    }
    
    public void printTo(PrintStream out)
    {
        out.println(this.toString());
    }

    public Date getDate()
    {
        return date;
    }

    public int[] getNumbers()
    {
        return numbers;
    }

    public int getKicker()
    {
        return kicker;
    }

    public double getJackpot()
    {
        return jackpot;
    }

    public double getPayout()
    {
        return payout;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
        {
            return true;
        }
        if(obj == null)
        {
            return false;
        }
        if(getClass() != obj.getClass())
        {
            return false;
        }
        final Draw other = (Draw) obj;
        if(!Objects.equals(this.date, other.date))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.date);
        return hash;
    }
    
    @Override
    public String toString()
    {
        return new StringBuilder()
                .append(DFMT.format(date)).append(", ")
                .append(numbers[0]).append(", ")
                .append(numbers[1]).append(", ")
                .append(numbers[2]).append(", ")
                .append(numbers[3]).append(", ")
                .append(numbers[4]).append(", ")
                .append(numbers[5]).append(", ")
                .append(kicker).append(", ")
                .append(String.format("$%.2f", jackpot)).append(", ")
                .append(String.format("%.2f", payout))
                .toString();
    }

    @Override
    public int compareTo(Draw o)
    {
        return date.compareTo(o.date);
    }
}
