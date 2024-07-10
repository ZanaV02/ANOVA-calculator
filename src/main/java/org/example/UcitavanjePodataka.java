package org.example;

import org.apache.commons.math3.distribution.FDistribution;

import java.util.*;
import java.io.*;

public class UcitavanjePodataka
{
    public static List<Double> ef = new ArrayList<>();
    public static void ucitajPodatkeIzFajla(double procenatFTest)
    {
        Map<String, List<Double>> matricaMjerenja = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("Mjerenja.txt"))) {
            String linija;
            int k = 0;//broj alternativa
            while ((linija = br.readLine()) != null)
            {
                List<Double> vrijednostiMjerenja = new ArrayList<>();
                String[] vrijednosti = linija.split(" ");
                Main.brojMjerenjaFajl = vrijednosti.length - 1;
                for (int i = 1; i < vrijednosti.length; i++) {
                    try {
                        vrijednostiMjerenja.add(Double.parseDouble(vrijednosti[i]));
                    } catch (NumberFormatException e) {
                        System.out.println("Greska prilikom konverzije broja.");
                        e.printStackTrace();
                    }
                }
                matricaMjerenja.put(vrijednosti[0], vrijednostiMjerenja);
                Main.matricaMjerenjaFajl = matricaMjerenja;
                k++;
            }
            Main.brojAlternativaFajl = k;
        } catch (IOException e)
        {
            System.out.println("Greska prilikom citanja fajla.");
            e.printStackTrace();
        }
        //System.out.println("Matrica mjerenja: " + matricaMjerenja + " Broj alternativa: " + Main.brojAlternativaFajl + " Broj mjerenja: " + Main.brojMjerenjaFajl);
        //System.out.println("Srednje vrijednosti kolona: " + ANOVA.srednjaVrijednostKolone(matricaMjerenja));
        //System.out.println("Srednja vrijednost cijele matrice: " + ANOVA.srednjaVrijednostCijeleMatrice(matricaMjerenja));
        //System.out.println("Efekti kolona: " + ANOVA.efektiKolona(matricaMjerenja));
//        System.out.println("SSE: " + ANOVA.SSE(matricaMjerenja));
//        System.out.println("SSA: " + ANOVA.SSA(matricaMjerenja, Main.brojMjerenjaFajl));
//        System.out.println("SST: " + ANOVA.SST(matricaMjerenja));
//        System.out.println("F test: " + ANOVA.FTest(matricaMjerenja, Main.brojMjerenjaFajl, Main.brojAlternativaFajl));
//        System.out.println("F tabelarno: " + new FDistribution(Main.brojAlternativaFajl - 1, Main.brojAlternativaFajl * (Main.brojMjerenjaFajl - 1)).inverseCumulativeProbability(procenatFTest));
        ANOVA.compare(ANOVA.FTest(matricaMjerenja, Main.brojMjerenjaFajl, Main.brojAlternativaFajl), Main.brojAlternativaFajl, Main.brojMjerenjaFajl, procenatFTest);
        ef = ANOVA.efektiKolona(matricaMjerenja);
        //ANOVA.contrast(matricaMjerenja, ANOVA.efektiKolona(matricaMjerenja), "A1", "A2", 0.9, Main.brojMjerenjaFajl, Main.brojAlternativaFajl);

    }
}
