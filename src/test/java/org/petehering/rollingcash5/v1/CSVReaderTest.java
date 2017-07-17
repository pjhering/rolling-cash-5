package org.petehering.rollingcash5.v1;

import java.net.URL;
import java.util.Set;
import junit.framework.TestCase;

public class CSVReaderTest extends TestCase
{
    private final String[] line = {
        "Day of Week, Play Date, 1st, 2nd, 3rd, 4th, 5th, Jackpot Amount, Payouts",
        "Wednesday, 6/21/2017, 1, 3, 21, 34, 38, $140000.00, 176751",
        "Tuesday, 6/20/2017, 12, 16, 22, 25, 30, $130000.00, 43138"
    };
    
    private final String[] file = {
        "/test-data-1.csv",
        "/test-data-2.csv"
    };
    
    public void testParseLine ()
    {
        CSVReader r = new CSVReader ();
        
        for (String s : line)
        {
            String[] f = s.split("\\s*,\\s*");
            
            try
            {
                Draw d = r.parseLine(f);
                System.out.println(d);
            }
            catch (Exception ex)
            {
                r.error(f);
            }
        }
    }
    
    public void testParseURL ()
    {
        CSVReader r = new CSVReader ();
        
        for (String f : file)
        {
            URL u = this.getClass().getResource(f);
            
            try
            {
                Set<Draw> s = r.parse(u);
                
                s.forEach(d -> System.out.println (d));
            }
            catch (Exception ex)
            {
                System.out.println (ex.toString());
            }
        }
    }
}
