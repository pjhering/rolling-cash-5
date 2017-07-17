package org.petehering.rollingcash5.v1;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Main
{
    private static final Set<Draw> allDraws = new TreeSet<> ();
    private static final Map<Integer, HitInfo> hitData = new HashMap<>();
    private static final FilenameFilter csvFilter = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name)
        {
            return name.toLowerCase().endsWith(".csv");
        }
    };
    
    public static void main(String[] args)
    {
        readAllDraws ();
        createHitData ();
        printHitData ();
    }
    
    private static void readAllDraws ()
    {
        CSVReader reader = new CSVReader ();
        File cwd = new File (".");
        File[] csvFiles = cwd.listFiles(csvFilter);
        
        for (File csvFile : csvFiles)
        {
            try
            {
                Set<Draw> draws = reader.parse(csvFile);
                allDraws.addAll(draws);
            }
            catch (Exception ex)
            {
                System.out.print ("ERROR: ");
                System.out.print (csvFile.getAbsolutePath ());
                System.out.print (" - ");
                System.out.println (ex.toString());
            }
        }
    }
    
    private static void createHitData ()
    {
        for (Draw draw : allDraws)
        {
            for (int number : draw.numbers)
            {
                if (!hitData.containsKey(number))
                {
                    hitData.put(number, new HitInfo (number));
                }
                
                HitInfo hit = hitData.get(number);
                hit.DATES.add(draw.playDate);
            }
        }
    }
    
    private static void printHitData ()
    {
        Collection<HitInfo> values = hitData.values();
        List<HitInfo> list = new ArrayList<> (values);
        Collections.sort(list);
        list.forEach(hit -> 
        {
            int number = hit.NUMBER;
            Date first = hit.DATES.get(0);
            Date last = hit.DATES.get(hit.DATES.size() - 1);
            
            System.out.print (number);
            System.out.print ('\t');
            System.out.print (hit.nextHit);
            System.out.print ('\n');
        });
    }
}
