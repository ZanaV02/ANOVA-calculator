package org.example;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import static org.example.ANOVA.check;
import static org.example.ANOVA.intervalPovjerenja;
import static org.example.UcitavanjePodataka.ucitajPodatkeIzFajla;
//import static org.example.UcitavanjePodataka.ucitajPodatkeIzKonzole;


public class Main
{
    public static double a;
    public static int brojAlternativaKonzola;
    public static int brojAlternativaFajl;
    public static int brojMjerenjaKonzola;
    public static int brojMjerenjaFajl;
    public static Map<String, List<Double>> matricaMjerenjaKonzola = new HashMap<>();
    public static Map<String, List<Double>> matricaMjerenjaFajl = new HashMap<>();
    public static List<Double> srVrijKolona = new ArrayList<>();
    public static double srVrijCijeleMatrice;
    public static List<Double> efekti2 = new ArrayList<>();
    public static double sse2;
    public static double ssa2;
    public static double sst2;
    public static double ftest2;

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("ANOVA");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 750);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.decode("#344955"));
        JLabel label1 = new JLabel("ZAPOČNITE NOVI ANOVA TEST");
        label1.setBounds(350, 150, 750, 40);
        Font font1 = new Font(Font.MONOSPACED, Font.BOLD, 40);
        label1.setFont(font1);
        label1.setForeground(Color.decode("#7C9D96"));
        frame.add(label1);
        JButton button1 = new JButton("START");
        Font font2 = new Font(Font.MONOSPACED, Font.BOLD, 24);
        button1.setFont(font2);
        button1.setBounds(580, 300, 150, 80);
        button1.setBackground(Color.decode("#7C9D96"));
        button1.setForeground(Color.WHITE);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame2 = new JFrame("ANOVA");
                frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame2.setSize(1300, 750);
                frame2.setLayout(null);
                frame2.getContentPane().setBackground(Color.decode("#344955"));
                JLabel label2 = new JLabel("ODABERITE NAČIN UNOSA PODATAKA");//---------------------------način unosa podataka FRAME2
                label2.setBounds(330, 150, 750, 40);
                Font font3 = new Font(Font.MONOSPACED, Font.BOLD, 35);
                label2.setFont(font3);
                label2.setForeground(Color.decode("#7C9D96"));
                frame2.add(label2);
                //-----------------------------------------------------------------------------------------------------------KONZOLA------------------------------------------------------------
                JButton button2 = new JButton("KONZOLA");
                button2.setFont(font2);
                button2.setBounds(580, 300, 150, 80);
                button2.setBackground(Color.decode("#7C9D96"));
                button2.setForeground(Color.decode("#7C9D96"));
                button2.setBorder(BorderFactory.createLineBorder(Color.decode("#7C9D96"), 3, true));
                button2.setContentAreaFilled(false);
                button2.setFocusPainted(true);
                button2.setOpaque(false);
                button2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Font font2 = new Font(Font.MONOSPACED, Font.BOLD, 19);
                        Font font3 = new Font(Font.MONOSPACED, Font.BOLD, 17);
                        JFrame frame3 = new JFrame("ANOVA");
                        frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame3.setSize(1300, 750);
                        frame3.setLayout(null);
                        frame3.getContentPane().setBackground(Color.decode("#344955"));
                        JLabel label3 = new JLabel("UNESITE NAZIV ALTERNATIVE");//---------------------------------unosi broj alternativa FRAME3
                        frame3.add(label3);
                        label3.setBounds(509, 150, 750, 40);
                        label3.setFont(font2);
                        label3.setForeground(Color.decode("#7C9D96"));
                        JTextField textField1 = new JTextField();
                        textField1.setBounds(500, 200, 300, 40);
                        textField1.setFont(font2);
                        textField1.setBackground(Color.decode("#7C9D96"));
                        textField1.setForeground(Color.WHITE);
                        frame3.add(textField1);
                        JLabel label4 = new JLabel("UNESITE VRIJEDNOSTI MJERENJA");//---------------------------------unosi broj mjerenja FRAME3
                        frame3.add(label4);
                        label4.setBounds(504, 255, 750, 40);
                        label4.setFont(font3);
                        label4.setForeground(Color.decode("#7C9D96"));
                        JLabel label5 = new JLabel("(sa razmakom)");
                        Font font4 = new Font(Font.MONOSPACED, Font.BOLD, 14);
                        frame3.add(label5);
                        label5.setBounds(595, 275, 750, 40);
                        label5.setFont(font4);
                        label5.setForeground(Color.decode("#7C9D96"));

                        JTextField textField2 = new JTextField();
                        textField2.setBounds(500, 305, 300, 40);
                        textField2.setFont(font2);
                        textField2.setBackground(Color.decode("#7C9D96"));
                        textField2.setForeground(Color.WHITE);
                        frame3.add(textField2);
                        JButton button7 = new JButton("DODAJ");
                        button7.setFont(font2);
                        button7.setBounds(580, 370, 150, 40);
                        button7.setBackground(Color.decode("#7C9D96"));
                        button7.setForeground(Color.decode("#7C9D96"));
                        button7.setBorder(BorderFactory.createLineBorder(Color.decode("#7C9D96"), 3, true));
                        button7.setContentAreaFilled(false);
                        button7.setFocusPainted(true);
                        button7.setOpaque(false);
                        button7.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String imeAlternativa = textField1.getText();
                                String mjerenjaKonzola = textField2.getText();
                                if (imeAlternativa.isEmpty() || mjerenjaKonzola.isEmpty()) {
                                    JOptionPane.showMessageDialog(frame3, "Polja ne smiju biti prazna.");
                                    return;
                                }
                                List<Double> vrijednostiMjerenja = new ArrayList<>();
                                try
                                {
                                    String[] vrijednosti = mjerenjaKonzola.split(" ");
                                    for (String mjerenje : vrijednosti)
                                    {
                                        vrijednostiMjerenja.add(Double.parseDouble(mjerenje));
                                    }
                                }
                                catch (NumberFormatException ex)
                                {
                                    JOptionPane.showMessageDialog(frame3, "Greska prilikom konverzije broja.");
                                    ex.printStackTrace();
                                }
                                matricaMjerenjaKonzola.put(imeAlternativa, vrijednostiMjerenja);
                                textField1.setText("");
                                textField2.setText("");
                            }
                        });
                        frame3.add(button7);
                        JLabel label6 = new JLabel("UNESITE % ZA F TEST ");
                        frame3.add(label6);
                        label6.setBounds(540, 470, 750, 40);
                        label6.setFont(font2);
                        label6.setForeground(Color.decode("#7C9D96"));

                        JTextField textField6 = new JTextField();
                        textField6.setBounds(500, 510, 300, 40);
                        textField6.setFont(font2);
                        textField6.setBackground(Color.decode("#7C9D96"));
                        textField6.setForeground(Color.WHITE);
                        frame3.add(textField6);

                        JButton button8 = new JButton("POTVRDA");
                        button8.setFont(font2);
                        button8.setBounds(580, 570, 150, 40);
                        button8.setBackground(Color.decode("#7C9D96"));
                        button8.setForeground(Color.decode("#7C9D96"));
                        button8.setBorder(BorderFactory.createLineBorder(Color.decode("#7C9D96"), 3, true));
                        button8.setContentAreaFilled(false);
                        button8.setFocusPainted(true);
                        button8.setOpaque(false);
                        button8.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(matricaMjerenjaKonzola.size()<2)
                                {
                                    JOptionPane.showMessageDialog(frame3,"MORATE UNIJETI MINIMALNO 2 ALTERNATIVE!");
                                    return;
                                }
                                String procenat = textField6.getText();
                                if(procenat.isEmpty())
                                {
                                    JOptionPane.showMessageDialog(frame3, "MORATE UNIJETI PROCENAT");
                                    return;
                                }
                                try
                                {
                                    a = Double.parseDouble(procenat);
                                }
                                catch(NumberFormatException ex)
                                {
                                    JOptionPane.showMessageDialog(frame3, "NEISPRAVAN UNOS");
                                    ex.printStackTrace();
                                }
                                JFrame frame6 = new JFrame("ANOVA");
                                frame6.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                frame6.setSize(1300, 750);
                                frame6.setLayout(null);
                                frame6.getContentPane().setBackground(Color.decode("#344955"));
                                JLabel label12 = new JLabel("REZULTATI ANOVA TESTA");
                                label12.setBounds(500, 30, 750, 40);
                                label12.setFont(font2);
                                label12.setForeground(Color.decode("#7C9D96"));
                                frame6.add(label12);

                                brojMjerenjaKonzola = matricaMjerenjaKonzola.values().stream().mapToInt(List::size).max().orElse(0);
                                brojAlternativaKonzola = matricaMjerenjaKonzola.size();
                                srVrijKolona = ANOVA.srednjaVrijednostKolone(matricaMjerenjaKonzola);
                                srVrijCijeleMatrice = ANOVA.srednjaVrijednostCijeleMatrice(matricaMjerenjaKonzola);
                                efekti2 = ANOVA.efektiKolona(matricaMjerenjaKonzola);
                                sse2 = ANOVA.SSE(matricaMjerenjaKonzola);
                                ssa2 = ANOVA.SSA(matricaMjerenjaKonzola, brojMjerenjaKonzola);
                                sst2 = ANOVA.SST(matricaMjerenjaKonzola);
                                ftest2 = ANOVA.FTest(matricaMjerenjaKonzola, brojMjerenjaKonzola, brojAlternativaKonzola);
                                ANOVA.compare(ftest2, brojAlternativaKonzola, brojMjerenjaKonzola, a/100.0);

                                DefaultTableModel model = new DefaultTableModel();
                                int maxLenght = brojMjerenjaKonzola;
                                model.addColumn("Alternative");
                                for (int i = 1; i <= maxLenght; i++)
                                {
                                    model.addColumn("Mjerenje " + i);
                                }
                                model.addColumn("Efekti");
                                model.addColumn("Srednje vrijednosti kolona");
                                int index = 0;
                                for (Map.Entry<String, List<Double>> entry : matricaMjerenjaKonzola.entrySet())
                                {
                                    String key = entry.getKey();
                                    List<Double> value = entry.getValue();
                                    Object[] row = new Object[maxLenght + 3];
                                    row[0] = key;
                                    for (int i = 0; i < value.size(); i++)
                                    {
                                        row[i + 1] = value.get(i);
                                    }
                                    row[maxLenght + 1] = efekti2.get(index);
                                    row[maxLenght + 2] = srVrijKolona.get(index);
                                    model.addRow(row);
                                    index++;
                                }
                                JTable table = new JTable(model);
                                int rowHeight = table.getRowHeight();
                                JScrollPane scrollPane = new JScrollPane(table);
                                scrollPane.setBounds(70, 100, 1200, rowHeight * (matricaMjerenjaKonzola.size()) + 24);
                                frame6.add(scrollPane);
                                Font font7 = new Font(Font.MONOSPACED, Font.ITALIC, 15);
                                JLabel label6 = new JLabel("~Srednja vrijednost cijele matrice: " + srVrijCijeleMatrice);
                                label6.setBounds(100, 100 + rowHeight * (matricaMjerenjaKonzola.size() + 1), 750, 40);
                                label6.setFont(font7);
                                label6.setForeground(Color.decode("#7C9D96"));
                                frame6.add(label6);
                                JLabel label7 = new JLabel("~SSE: " + sse2);
                                label7.setBounds(100, 100 + rowHeight * (matricaMjerenjaKonzola.size() + 2), 750, 40);
                                label7.setFont(font7);
                                label7.setForeground(Color.decode("#7C9D96"));
                                frame6.add(label7);
                                JLabel label8 = new JLabel("~SSA: " + ssa2);
                                label8.setBounds(100, 100 + rowHeight * (matricaMjerenjaKonzola.size() + 3), 750, 40);
                                label8.setFont(font7);
                                label8.setForeground(Color.decode("#7C9D96"));
                                frame6.add(label8);

                                JLabel label9 = new JLabel("~F izracunato: " + ftest2);
                                label9.setBounds(100, 100 + rowHeight * (matricaMjerenjaKonzola.size() + 4), 750, 40);
                                label9.setFont(font7);
                                label9.setForeground(Color.decode("#7C9D96"));
                                frame6.add(label9);
                                JLabel label10 = new JLabel("~F tabelarno: " + ANOVA.fTab);
                                label10.setBounds(100, 100 + rowHeight * (matricaMjerenjaKonzola.size() + 5), 750, 40);
                                label10.setFont(font7);
                                label10.setForeground(Color.decode("#7C9D96"));
                                frame6.add(label10);
                                JLabel label11 = new JLabel("UNESITE IMENA 2 ALTERNATIVE: ");
                                label11.setBounds(100, 100 + rowHeight * (matricaMjerenjaKonzola.size() + 8), 750, 25);
                                Font font5 = new Font(Font.MONOSPACED, Font.BOLD, 14);
                                label11.setFont(font5);
                                label11.setForeground(Color.decode("#7C9D96"));
                                frame6.add(label11);
                                JTextField textField3 = new JTextField();
                                textField3.setBounds(100, 100 + rowHeight * (matricaMjerenjaKonzola.size() + 10), 280, 26);
                                textField3.setFont(font2);
                                textField3.setBackground(Color.decode("#7C9D96"));
                                textField3.setForeground(Color.WHITE);
                                frame6.add(textField3);
                                JTextField textField4 = new JTextField();
                                textField4.setBounds(100, 100 + rowHeight * (matricaMjerenjaKonzola.size() + 12), 280, 26);
                                textField4.setFont(font2);
                                textField4.setBackground(Color.decode("#7C9D96"));
                                textField4.setForeground(Color.WHITE);
                                frame6.add(textField4);

                                JLabel label13 = new JLabel("UNESITE % ZA INTERVAL POVJERENJA");//------------------interval povjerenja
                                Font aa = new Font(Font.MONOSPACED, Font.BOLD, 13);
                                label13.setBounds(100, 100 + rowHeight * (matricaMjerenjaKonzola.size() + 14), 350, 40);
                                label13.setFont(aa);
                                label13.setForeground(Color.decode("#7C9D96"));
                                frame6.add(label13);
                                JTextField textField5 = new JTextField();
                                textField5.setBounds(100, 100 + rowHeight * (matricaMjerenjaKonzola.size() + 16), 280, 25);
                                textField5.setFont(font2);
                                textField5.setBackground(Color.decode("#7C9D96"));
                                textField5.setForeground(Color.WHITE);
                                frame6.add(textField5);
                                JButton button5 = new JButton("IZRACUNAJ KONTRAST I INTERVAL POVJERENJA");
                                Font font4 = new Font(Font.MONOSPACED, Font.BOLD, 10);
                                button5.setFont(font4);
                                button5.setBounds(105, 100 + rowHeight * (matricaMjerenjaKonzola.size() + 19), 270, 30);
                                button5.setBackground(Color.decode("#7C9D96"));
                                button5.setForeground(Color.decode("#7C9D96"));
                                button5.setBorder(BorderFactory.createLineBorder(Color.decode("#7C9D96"), 3, true));
                                button5.setContentAreaFilled(false);
                                button5.setFocusPainted(true);
                                button5.setOpaque(false);
                                button5.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        String a1 = textField3.getText();
                                        String a2 = textField4.getText();
                                        String procenat = textField5.getText();
                                        if (a1.isEmpty() || a2.isEmpty() || procenat.isEmpty()) {
                                            JOptionPane.showMessageDialog(frame6, "POPUNITE SVA POLJA");
                                            return;
                                        }
                                        if(check(matricaMjerenjaKonzola,a1,a2)==false || a1.equals(a2))
                                        {
                                            JOptionPane.showMessageDialog(frame6, "NEISPRAVAN UNOS ALTERNATIVA");
                                            return;
                                        }
                                        ANOVA.contrast(matricaMjerenjaKonzola, efekti2, a1, a2);
                                        ANOVA.intervalPovjerenja(matricaMjerenjaKonzola, (Double.parseDouble(procenat))/100.0, brojAlternativaKonzola, brojMjerenjaKonzola);
                                        if(ANOVA.pocIntervala<=0 && ANOVA.krIntervala>=0)
                                        {
                                            JOptionPane.showMessageDialog(frame6, "Kontrast je " + ANOVA.kontrast2 + "\nInterval povjerenja: [" + ANOVA.pocIntervala + ", " + ANOVA.krIntervala + "]" + "\nAlternative se statisticki ne razlikuju.");
                                        }
                                        else
                                        {
                                            JOptionPane.showMessageDialog(frame6, "Kontrast je " + ANOVA.kontrast2 + "\nInterval povjerenja: [" + ANOVA.pocIntervala + ", " + ANOVA.krIntervala + "]" + "\nAlternative se statisticki razlikuju.");
                                        }

                                    }
                                });
                                frame6.add(button5);
                                frame6.setVisible(true);
                                frame3.dispose();

                            }
                        });
                        frame3.add(button8);
                        frame3.setVisible(true);
                        frame2.dispose();
                    }
                });
                frame2.add(button2);
                //---------------------------------------------------------------------------------------DATOTEKA---------------------------------------------------------------------------------------------------------
                JButton button3 = new JButton("DATOTEKA");
                button3.setFont(font2);
                button3.setBounds(580, 400, 150, 80);
                button3.setBackground(Color.decode("#7C9D96"));
                button3.setForeground(Color.decode("#7C9D96"));
                button3.setBorder(BorderFactory.createLineBorder(Color.decode("#7C9D96"), 3, true));
                button3.setContentAreaFilled(false);
                button3.setFocusPainted(true);
                button3.setOpaque(false);
                button3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFrame frame4 = new JFrame("ANOVA");
                        frame4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame4.setSize(1300, 750);
                        frame4.setLayout(null);
                        frame4.getContentPane().setBackground(Color.decode("#344955"));
                        JLabel label4 = new JLabel("UNESITE % ZA F TEST");//------------------------------unosi procenata FRAME4
                        Font font1b = new Font(Font.MONOSPACED, Font.BOLD, 20);
                        frame4.add(label4);
                        label4.setBounds(535, 180, 750, 40);
                        label4.setFont(font1b);
                        label4.setForeground(Color.decode("#7C9D96"));
                        JLabel label5 = new JLabel("(% za interval povjerenja)");
                        Font font1c = new Font(Font.MONOSPACED, Font.BOLD, 9);
                        frame4.add(label5);
                        label5.setBounds(495, 202, 750, 40);
                        label5.setForeground(Color.decode("#7C9D96"));
                        label5.setFont(font1c);

                        label5.setFont(font1b);
                        frame4.setVisible(true);
                        JTextField textField1 = new JTextField(); //polje za f test
                        textField1.setBounds(500, 250, 300, 40);
                        textField1.setFont(font2);
                        textField1.setBackground(Color.decode("#7C9D96"));
                        textField1.setForeground(Color.WHITE);
                        frame4.add(textField1);
                        JButton button4 = new JButton("POTVRDA");
                        button4.setFont(font2);
                        button4.setBounds(580, 320, 150, 40);
                        button4.setBackground(Color.decode("#7C9D96"));
                        button4.setForeground(Color.decode("#7C9D96"));
                        button4.setBorder(BorderFactory.createLineBorder(Color.decode("#7C9D96"), 3, true));
                        button4.setContentAreaFilled(false);
                        button4.setFocusPainted(true);
                        button4.setOpaque(false);
                        button4.addActionListener(new ActionListener(){
                            @Override
                            public void actionPerformed(ActionEvent e)
                            {
                                String inputText = textField1.getText();
                                if(inputText.isEmpty() )
                                {
                                    JOptionPane.showMessageDialog(frame4, "Polje ne smije biti prazno.");
                                    return;
                                }
                                try
                                {
                                    a = Double.parseDouble(inputText);
                                    ucitajPodatkeIzFajla(a/100.0);
                                }catch(NumberFormatException ex)
                                {
                                    System.out.println("Greska prilikom konverzije broja.");
                                    ex.printStackTrace();
                                }
                                JFrame frame5 = new JFrame("ANOVA");
                                frame5.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                frame5.setSize(1300, 750);
                                frame5.setLayout(null);
                                frame5.getContentPane().setBackground(Color.decode("#344955"));
                                JLabel label5 = new JLabel("REZULTATI ANOVA TESTA");
                                label5.setBounds(500, 30, 750, 40);
                                label5.setFont(font2);
                                label5.setForeground(Color.decode("#7C9D96"));
                                frame5.add(label5);
                                DefaultTableModel model = new DefaultTableModel();
                                int maxLenght = matricaMjerenjaFajl.values().stream().mapToInt(List::size).max().orElse(0);
                                model.addColumn("Alternative");
                                for(int i = 1; i <= maxLenght; i++)
                                {
                                    model.addColumn("Mjerenje " + i);
                                }
                                model.addColumn("Efekti");
                                model.addColumn("Srednje vrijednosti kolona");
                                int index = 0;
                                for(Map.Entry<String, List<Double>> entry : matricaMjerenjaFajl.entrySet())
                                {
                                    String key = entry.getKey();
                                    List<Double> value = entry.getValue();
                                    Object[] row = new Object[maxLenght + 3];
                                    row[0] = key;
                                    for(int i = 0; i < value.size(); i++)
                                    {
                                        row[i+1] = value.get(i);
                                    }
                                    row[maxLenght + 1] = UcitavanjePodataka.ef.get(index);
                                    row[maxLenght + 2] = srVrijKolona.get(index);
                                    model.addRow(row);
                                    index++;
                                }
                                JTable table = new JTable(model);
                                int rowHeight = table.getRowHeight();
                                JScrollPane scrollPane = new JScrollPane(table);
                                scrollPane.setBounds(70, 100, 1200, rowHeight * (matricaMjerenjaFajl.size() )+24);
                                frame5.add(scrollPane);
                                Font font7 = new Font(Font.MONOSPACED, Font.ITALIC, 15);
                                JLabel label6 = new JLabel("~Srednja vrijednost cijele matrice: " + srVrijCijeleMatrice);
                                label6.setBounds(100, 100 + rowHeight * (matricaMjerenjaFajl.size() + 1), 750, 40);
                                label6.setFont(font7);
                                label6.setForeground(Color.decode("#7C9D96"));
                                frame5.add(label6);
                                JLabel label7 = new JLabel("~SSE: " + sse2);
                                label7.setBounds(100, 100 + rowHeight * (matricaMjerenjaFajl.size() + 2), 750, 40);
                                label7.setFont(font7);
                                label7.setForeground(Color.decode("#7C9D96"));
                                frame5.add(label7);
                                JLabel label8 = new JLabel("~SSA: " + ssa2);
                                label8.setBounds(100, 100 + rowHeight * (matricaMjerenjaFajl.size() + 3), 750, 40);
                                label8.setFont(font7);
                                label8.setForeground(Color.decode("#7C9D96"));
                                frame5.add(label8);
                                JLabel label9 = new JLabel("~F izracunato: " + ftest2);
                                label9.setBounds(100, 100 + rowHeight * (matricaMjerenjaFajl.size() + 4), 750, 40);
                                label9.setFont(font7);
                                label9.setForeground(Color.decode("#7C9D96"));
                                frame5.add(label9);
                                JLabel label10 = new JLabel("~F tabelarno: " + ANOVA.fTab);
                                label10.setBounds(100, 100 + rowHeight * (matricaMjerenjaFajl.size() + 5), 750, 40);
                                label10.setFont(font7);
                                label10.setForeground(Color.decode("#7C9D96"));
                                frame5.add(label10);

                                JLabel label11 = new JLabel("UNESITE IMENA 2 ALTERNATIVE:");
                                label11.setBounds(100, 100 + rowHeight * (matricaMjerenjaFajl.size() + 8), 750, 25);
                                Font font5 = new Font(Font.MONOSPACED, Font.BOLD, 13);
                                label11.setFont(font5);
                                label11.setForeground(Color.decode("#7C9D96"));
                                frame5.add(label11);
                                JTextField textField3 = new JTextField();
                                textField3.setBounds(100, 100 + rowHeight * (matricaMjerenjaFajl.size() + 10), 280, 26);
                                textField3.setFont(font2);
                                textField3.setBackground(Color.decode("#7C9D96"));
                                textField3.setForeground(Color.WHITE);
                                frame5.add(textField3);
                                JTextField textField4 = new JTextField();
                                textField4.setBounds(100, 100 + rowHeight * (matricaMjerenjaFajl.size() + 12), 280, 26);
                                textField4.setFont(font2);
                                textField4.setBackground(Color.decode("#7C9D96"));
                                textField4.setForeground(Color.WHITE);
                                frame5.add(textField4);
                                Font font8 = new Font(Font.MONOSPACED, Font.BOLD, 13);
                                JLabel label14 = new JLabel("UNESITE % ZA INTERVAL POVJERENJA ");//------------------interval povjerenja
                                label14.setBounds(100, 100 + rowHeight * (matricaMjerenjaFajl.size() + 14), 350, 40);
                                label14.setFont(font8);
                                label14.setForeground(Color.decode("#7C9D96"));
                                frame5.add(label14);

                                JTextField textField5 = new JTextField();
                                textField5.setBounds(100, 100 + rowHeight * (matricaMjerenjaFajl.size() + 16), 280, 25);
                                textField5.setFont(font2);
                                textField5.setBackground(Color.decode("#7C9D96"));
                                textField5.setForeground(Color.WHITE);
                                frame5.add(textField5);
                                JButton button5 = new JButton("IZRACUNAJ KONTRAST I INTERVAL POVJERENJA");
                                Font font4 = new Font(Font.MONOSPACED, Font.BOLD, 10);
                                button5.setFont(font4);
                                button5.setBounds(105, 100 + rowHeight * (matricaMjerenjaFajl.size() + 19), 270, 30);
                                button5.setBackground(Color.decode("#7C9D96"));
                                button5.setForeground(Color.decode("#7C9D96"));
                                button5.setContentAreaFilled(false);
                                button5.setFocusPainted(true);
                                button5.setOpaque(false);
                                button5.setBorder(BorderFactory.createLineBorder(Color.decode("#7C9D96"), 3, true));
                                button5.addActionListener(new ActionListener(){
                                    @Override
                                    public void actionPerformed(ActionEvent e)
                                    {
                                        String a1 = textField3.getText();
                                        String a2 = textField4.getText();
                                        String procenat = textField5.getText();
                                        if(a1.isEmpty() || a2.isEmpty() || procenat.isEmpty())
                                        {
                                            JOptionPane.showMessageDialog(frame5, "POPUNITE SVA POLJA");
                                            return;
                                        }
                                        if(check(matricaMjerenjaFajl, a1, a2) == false || a1.equals(a2))
                                        {
                                            JOptionPane.showMessageDialog(frame5, "NEISPRAVAN UNOS ALTERNATIVA");
                                            return;
                                        }
                                        ANOVA.contrast(matricaMjerenjaFajl, efekti2, a1, a2);
                                        ANOVA.intervalPovjerenja(matricaMjerenjaFajl, (Double.parseDouble(procenat))/100.0, brojAlternativaFajl, brojMjerenjaFajl);
                                        if(ANOVA.pocIntervala<=0 && ANOVA.krIntervala>=0)
                                        {
                                            JOptionPane.showMessageDialog(frame5, "Kontrast je " + ANOVA.kontrast2 + "\nInterval povjerenja: [" + ANOVA.pocIntervala + ", " + ANOVA.krIntervala + "]" + "\nAlternative se statisticki ne razlikuju.");
                                        }
                                        else
                                        {
                                            JOptionPane.showMessageDialog(frame5, "Kontrast je " + ANOVA.kontrast2 + "\nInterval povjerenja: [" + ANOVA.pocIntervala + ", " + ANOVA.krIntervala + "]" + "\nAlternative se statisticki razlikuju.");
                                        }
                                    }
                                });
                                frame5.add(button5);

                                frame5.setVisible(true);
                                frame4.dispose();
                            }
                        });
                        frame4.add(button4);
                        frame2.dispose();
                    }
                });
                frame2.add(button3);
                frame2.setVisible(true);
                frame.dispose();
            }
        });
        frame.add(button1);
        frame.setVisible(true);

    }
}