package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.math3.distribution.FDistribution;
import org.apache.commons.math3.distribution.TDistribution;

public class ANOVA
{
    public static boolean check;
    public static double fTab;
    public static double kontrast2;
    public static double stDev;
    public static double pocIntervala;
    public static double krIntervala;
    public static double fIzracunato;

    public static List<Double> srednjaVrijednostKolone(Map<String, List<Double>> matrica)
    {
        List<Double> listaSrednjihVrijednostiKolona = matrica.values().stream().
                map(list->list.stream().mapToDouble(Double::doubleValue).average().orElse(0.0)).collect(Collectors.toList());
        Main.srVrijKolona = listaSrednjihVrijednostiKolona;
        return listaSrednjihVrijednostiKolona;
    }
    public static double srednjaVrijednostCijeleMatrice(Map<String, List<Double>> matrica)
    {
        double srednjaVrijednostCijeleMatrice = matrica.values().stream().flatMapToDouble(list->list.stream().mapToDouble(Double::doubleValue)).average().orElse(0.0);
        Main.srVrijCijeleMatrice = srednjaVrijednostCijeleMatrice;
        return srednjaVrijednostCijeleMatrice;
    }
    public static Map<String, List<Double>> greske(Map<String, List<Double>> matrica)
    {
        Map<String, List<Double>> greskeMapa = new HashMap<>();
        matrica.forEach((k,v) -> {
            double srednjaVrijednost = v.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
            List<Double> greske = v.stream().map(value->value-srednjaVrijednost).collect(Collectors.toList());
            greskeMapa.put(k, greske);
        });
        //greskeMapa.entrySet().stream().forEach(System.out::println);
        return greskeMapa;
    }
    public static List<Double> efektiKolona(Map<String, List<Double>> matrica)
    {
        List<Double> efekti = srednjaVrijednostKolone(matrica).stream().map(list->list-srednjaVrijednostCijeleMatrice(matrica)).collect(Collectors.toList());
        Main.efekti2 = efekti;
        return efekti;
    }
    public static double SSE(Map<String, List<Double>> matrica)
    {
        Map<String, List<Double>> greskeMapa = greske(matrica);
        double sse = greskeMapa.values().stream().flatMapToDouble(list->
                list.stream().mapToDouble(Double::doubleValue)).map(value->Math.pow(value, 2)).sum();
        Main.sse2 = sse;
        return sse;
    }
    public static double SSA(Map<String, List<Double>> matrica, int brojMjerenja)
    {
        List<Double> efekti = efektiKolona(matrica);
        double ssa_tmp = efekti.stream().mapToDouble(Double::doubleValue).map(value->Math.pow(value, 2)).sum();
        Main.ssa2 = ssa_tmp * brojMjerenja;
        return ssa_tmp * brojMjerenja;
    }
    public static double SST(Map<String, List<Double>> matrica)
    {
        Main.sst2 = SSA(matrica, Main.brojMjerenjaFajl) + SSE(matrica);
        return SSA(matrica, Main.brojMjerenjaFajl) + SSE(matrica);
    }
    public static double FTest(Map<String, List<Double>> matrica, int brojMjerenja, int brojAlternativa)
    {
        double sa = (ANOVA.SSA(matrica, brojMjerenja))/(brojAlternativa-1);
        double se = (ANOVA.SSE(matrica))/(brojAlternativa*(brojMjerenja-1));
        Main.ftest2 = sa/se;
        return sa/se;
    }
    public static boolean compare(double fIzracunato, int brojAlternativa, int brojMjerenja, double postotak)
    {
        int df1 = brojAlternativa - 1;
        int df2 = brojAlternativa * (brojMjerenja - 1);
        FDistribution fDistribution = new FDistribution(df1, df2);
        double fTabelarno = fDistribution.inverseCumulativeProbability(postotak);
        fTab = fTabelarno;
        if (fIzracunato > fTabelarno)
        {
            //System.out.println("Sistemi se razlikuju.");
            return true;
        }
        else
        {
            //System.out.println("Sistemi se ne razlikuju.");
            return false;
        }
    }
    public static void contrast(Map<String, List<Double>> matrica, List<Double> efekti, String a1, String a2)
    {
        List<String> alternative = new ArrayList<>(matrica.keySet());
        List<Double> efektiKopija = new ArrayList<>(efekti);
        int index1 = alternative.indexOf(a1);
        int index2 = alternative.indexOf(a2);
        if(index1 == -1 || index2 == -1)
        {
            System.out.println("Alternative ne postoje.");
            return;
        }
        double kontrast = (efektiKopija.get(index1)*1) + (efektiKopija.get(index2)*(-1));
        kontrast2 = kontrast;
        for(int i = 0; i<efektiKopija.size();i++)
        {
            if(i!=index1 && i!=index2)
            {
                efektiKopija.set(i,0.0);
            }
        }
        System.out.println("Kontrast: " + kontrast);
    }
    public static void intervalPovjerenja(Map<String, List<Double>> matrica, double postotak, int brojAlternativa, int brojMjerenja)
    {
        double sc_1 = Math.sqrt(ANOVA.SSE(matrica)/(brojAlternativa*(brojMjerenja -1)));
        double sc_2 = Math.sqrt(2.0/(brojAlternativa*brojMjerenja));
        double sc = sc_1*sc_2;
        stDev = sc;
        //System.out.println("Standardna devijacija kontrasta: " + sc);

        TDistribution tDistribution = new TDistribution(brojAlternativa*(brojMjerenja-1));
        double tTabelarno = tDistribution.inverseCumulativeProbability(postotak);
        double pocetakIntervla = kontrast2 - tTabelarno*sc;
        double krajIntervala = kontrast2 + tTabelarno*sc;
        pocIntervala = pocetakIntervla;
        krIntervala = krajIntervala;
        //System.out.println("Interval povjerenja: [" + pocetakIntervla + ", " + krajIntervala + "]");
    }
    public static boolean check(Map<String, List<Double>> matrica, String s1, String s2)
    {
        if(matrica.containsKey(s1) && matrica.containsKey(s2))
        {
            return true;
        }
        return false;
    }
}
