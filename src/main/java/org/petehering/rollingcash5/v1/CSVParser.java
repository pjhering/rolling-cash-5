package org.petehering.lotto.v4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import static java.lang.System.out;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSVParser
{

    private final DateFormat DFMT = new SimpleDateFormat("MM/dd/yyyy");
    private final NumberFormat CFMT = NumberFormat.getCurrencyInstance();
    private final File ROOT;
    private final FilenameFilter FILTER = new FilenameFilter()
    {
        private final Pattern GOOD = Pattern.compile("^*.*csv$");

        @Override
        public boolean accept(File dir, String name)
        {
            Matcher m = GOOD.matcher(name);
            return m.matches();
        }
    };

    public CSVParser(File dir) throws Exception
    {
        if (dir.isDirectory())
        {
            this.ROOT = dir;
        }
        else
        {
            throw new Exception("expected a directory");
        }
    }

    public TreeSet<Draw> parse() throws Exception
    {
        TreeSet<Draw> set = new TreeSet<>();
        File[] files = ROOT.listFiles(FILTER);

        for (File f : files)
        {
            set.addAll(parse(f));
        }
        return set;
    }

    private Collection<? extends Draw> parse(File f) throws Exception
    {
        out.println(f.getName());
        
        TreeSet<Draw> set = new TreeSet<>();
        FileReader reader = new FileReader(f);
        BufferedReader buffer = new BufferedReader(reader);
        String line = null;
        int num = 1;

        while ((line = buffer.readLine()) != null)
        {
            try
            {
                out.println(num + ": " + line);
                set.add(parse(line));
            }
            catch (Exception ex)
            {
                out.printf("%s, line %d, %s%n", f.getName(), num, ex.getMessage());
            }
            finally
            {
                num += 1;
            }
        }

        return set;
    }

    //DATE, 1ST, 2ND, 3RD, 4TH, 5TH, 6TH, KICKER, JACKPOT, PAYOUT
    private Draw parse(String line) throws Exception
    {
        String[] field = line.split("\\s*,\\s*");
        Date date = DFMT.parse(field[0]);
        int n1 = Integer.parseInt(field[1]);
        int n2 = Integer.parseInt(field[2]);
        int n3 = Integer.parseInt(field[3]);
        int n4 = Integer.parseInt(field[4]);
        int n5 = Integer.parseInt(field[5]);
        int n6 = Integer.parseInt(field[6]);
        int kick = Integer.parseInt(field[7]);
        double jack = CFMT.parse(field[8]).doubleValue();
        double pay = Double.parseDouble(field[9]);
        
        return new Draw(
                date,
                new int[]{n1, n2, n3, n4, n5, n6},
                kick,
                jack,
                pay);
    }
}
