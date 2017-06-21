package org.petehering.rollingcash5.v1;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import static java.util.Objects.requireNonNull;
import java.util.TreeSet;

public class Number implements Comparable<Number>
{
    private final TreeSet<LocalDate> hits;
    public final Integer VALUE;
    private LocalDate next;
    
    public Number(Integer value)
    {
        this.VALUE = requireNonNull(value);
        hits = new TreeSet<>();
    }
    
    public LocalDate getNext()
    {
        return next;
    }
    
    public void add(int year, int month, int day)
    {
        hits.add(LocalDate.of(year, month, day));
    }
    
    public void finish()
    {
        LocalDate first = hits.first();
        LocalDate last = hits.last();
        long total = hits.size();
        
        long interval = ChronoUnit.DAYS.between(first, last);
        long avg = interval / total;
        
        next = last.plusDays(avg);
    }
    
    @Override
    public String toString()
    {
        return VALUE + " :: " + next.toString();
    }

    @Override
    public int compareTo(Number o)
    {
        if(next == null)
        {
            throw new RuntimeException("next == null");
        }
        
        return this.next.compareTo(o.next);
    }
}
