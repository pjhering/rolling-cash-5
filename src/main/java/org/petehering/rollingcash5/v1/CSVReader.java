package org.petehering.rollingcash5.v1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.text.DateFormat;
import static java.text.DateFormat.SHORT;
import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

public class CSVReader
{
    
    static final DateFormat date = DateFormat.getDateInstance(SHORT);
    static final NumberFormat currency = NumberFormat.getCurrencyInstance();
    
    public Set<Draw> parse (File file) throws IOException
    {
        return parse (new FileReader (file));
    }
    
    public Set<Draw> parse (URL url) throws IOException
    {
        return parse (url.openStream());
    }
    
    public Set<Draw> parse (InputStream stream) throws IOException
    {
        return parse (new InputStreamReader (stream));
    }
    
    public Set<Draw> parse (Reader reader) throws IOException
    {
        try (BufferedReader buffer = new BufferedReader (reader))
        {
            Set<Draw> set = new TreeSet<> ();
            String line = null;
            
            while ((line = buffer.readLine()) != null)
            {
                try
                {
                    String[] field = line.split("\\s*,\\s*");
                    Draw draw = parseLine (field);
                    set.add(draw);
                }
                catch (Exception ex)
                {
                    System.out.println (ex.toString());
                }
            }
            
            return set;
        }
    }
   
    Draw parseLine (String[] field) throws Exception
    {
        if (field.length != 9)
        {
            error (field);
            throw new Exception ();
        }
        else
        try
        {
            DayOfWeek dow = DayOfWeek.valueOf(field[0].toUpperCase());
            Date pd = date.parse(field[1]);
            int[] ns = new int[5];
            ns[0] = Integer.parseInt(field[2]);
            ns[1] = Integer.parseInt(field[3]);
            ns[2] = Integer.parseInt(field[4]);
            ns[3] = Integer.parseInt(field[5]);
            ns[4] = Integer.parseInt(field[6]);
            double jp = currency.parse(field[7]).doubleValue();
            int pos = Integer.parseInt(field[8]);
            
            return new Draw (dow, pd, ns, jp, pos);
        }
        catch (IllegalArgumentException ex)
        {
            throw new Exception (ex);
        }
    }
    
    void error (String[] fields)
    {
        System.out.print("ERROR: ");
        System.out.println (String.join(", ", fields));
    }
}
