package org.petehering.rollingcash5.v1;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.YEAR;
import java.util.Collections;
import java.util.Date;
import java.util.List;

class HitInfo implements Comparable<HitInfo>
{
    private static final Calendar CAL = Calendar.getInstance();
    
    public final Integer NUMBER;
    public final List<Date> DATES;
    public Date nextHit;
    
    HitInfo (int number)
    {
        this.NUMBER = number;
        this.DATES = new ArrayList<> ();
    }
    
    void updateNextHit ()
    {
        Collections.sort(DATES);
        long first = DATES.get(0).getTime();
        long last = DATES.get(DATES.size() - 1).getTime();
        long period = (last - first) / DATES.size();
        nextHit = new Date (last + period);
    }

    @Override
    public int compareTo(HitInfo that)
    {
        if (nextHit == null)
        {
            updateNextHit ();
        }
        
        if (that.nextHit == null)
        {
            that.updateNextHit();
        }
        
        return this.nextHit.compareTo(that.nextHit);
    }
}
