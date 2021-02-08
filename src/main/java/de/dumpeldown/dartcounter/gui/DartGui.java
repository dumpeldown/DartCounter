package de.dumpeldown.dartcounter.gui;

import de.dumpeldown.dartcounter.logic.DartLogic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.Window.Type;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class DartGui {
    static JTable table;
    static JTextField spieler1 = new JTextField();
    static JTextField spieler2 = new JTextField();
    static JTextField spieler3 = new JTextField();
    public static boolean doubleOutEnabled;
    public static int pointsToPlay = 501;
    public static int[] pointsPlayed = new int[3];
    public static JTable statsTable;
    public static int anzahlSpieler = 1;
    public static JTextField ersterWurf;
    public static JTextField zweiterWurf;
    public static JTextField dritterWurf;
    public static JToggleButton tglDouble;
    public static JToggleButton tglTriple;
    public static DefaultTableModel statsModel;
    public static JComboBox<Integer> comboPunkte;
    public static JComboBox<Integer> comboBoxAnzahlSpieler;
    public static JLabel[] pointsRemaining = new JLabel[]{new JLabel("0"), new JLabel("0"), new JLabel("0")};
    public static int aktuellerSpieler = 0;
    public static List<Integer>[] alleWuerfe = new List[]{new ArrayList<Integer>(),
            new ArrayList<Integer>(), new ArrayList<Integer>()};
    public static List<Integer>[] summen = new List[]{new ArrayList<Integer>(),
            new ArrayList<Integer>(), new ArrayList<Integer>()};
    public static int[] hoechsterWurf = new int[]{0, 0, 0};

    //TODO:
    // DartLogic-Instanz als Singleton.
    // Hightlight auf den Spieler, der gerade am Zug ist.
    public static void main(String[] args) {
        JFrame frameDartCounter = initFrame();
        JButton resetButton = initResetButton();
        JButton saveButton = initSaveButton();
        JCheckBox checkBoxDoubleOut = initDoubleOutCheckBox();
        JLabel lblPunktzahl = initPunktzahlLabel();
        JLabel anzahlWuerfeLabel = initAnzahlWuerfeLabel();
        initPunkteComboBox();
        initTableModel();
        initSpieler1TextField();
        initSpieler2TextField();
        initSpieler3TextField();
        initAnzahlSpielerComboBox();
        initErsterWurfTextField();
        initZweiterWurfTextField();
        initDritterWurfTextField();
        initDoubleToogleButton();
        initTripleToogleButton();
        initPointsRemainingLabel();
        JSeparator separator = new JSeparator();
        separator.setBounds(0, 0, 0, 0);

        table = new JTable();
        table.setBounds(0, 0, 0, 0);

        JLabel lblAnzahlDerSpieler = new JLabel("Anzahl der Spieler");
        lblAnzahlDerSpieler.setBounds(237, 22, 146, 14);

        JLabel lblLast = new JLabel("Last");
        lblLast.setBounds(237, 115, 57, 14);

        JLabel lblAverage = new JLabel("Average");
        lblAverage.setBounds(237, 130, 57, 14);

        JLabel lblHighest = new JLabel("Highest");
        lblHighest.setBounds(237, 147, 57, 14);

        JLabel label = new JLabel("");
        label.setBounds(247, 99, 46, 14);

        frameDartCounter.getContentPane().add(comboPunkte);
        frameDartCounter.getContentPane().add(separator);
        frameDartCounter.getContentPane().add(spieler1);
        frameDartCounter.getContentPane().add(table);
        frameDartCounter.getContentPane().add(spieler2);
        frameDartCounter.getContentPane().add(spieler3);
        frameDartCounter.getContentPane().add(lblAnzahlDerSpieler);
        frameDartCounter.getContentPane().add(comboBoxAnzahlSpieler);
        frameDartCounter.getContentPane().add(statsTable);
        frameDartCounter.getContentPane().add(label);
        frameDartCounter.getContentPane().add(lblHighest);
        frameDartCounter.getContentPane().add(lblAverage);
        frameDartCounter.getContentPane().add(lblLast);
        frameDartCounter.getContentPane().add(ersterWurf);
        frameDartCounter.getContentPane().add(zweiterWurf);
        frameDartCounter.getContentPane().add(dritterWurf);
        frameDartCounter.getContentPane().add(tglDouble);
        frameDartCounter.getContentPane().add(tglTriple);
        frameDartCounter.getContentPane().add(anzahlWuerfeLabel);
        frameDartCounter.getContentPane().add(pointsRemaining[0]);
        frameDartCounter.getContentPane().add(pointsRemaining[1]);
        frameDartCounter.getContentPane().add(lblPunktzahl);
        frameDartCounter.getContentPane().add(checkBoxDoubleOut);
        frameDartCounter.getContentPane().add(saveButton);
        frameDartCounter.getContentPane().add(resetButton);
        frameDartCounter.getContentPane().add(pointsRemaining[2]);

        frameDartCounter.setVisible(true);
        frameDartCounter.setSize(600, 400);
        frameDartCounter.setTitle("DartCounter by dumpeldown");
        frameDartCounter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private static void initPointsRemainingLabel() {
        pointsRemaining[0].setBounds(314, 78, 46, 14);
        pointsRemaining[1].setBounds(403, 78, 46, 14);
        pointsRemaining[2].setBounds(498, 78, 46, 14);
    }

    private static JLabel initAnzahlWuerfeLabel(){
        JLabel label = new JLabel("Wuerfe");
        label.setFont(new Font("Tahoma", Font.BOLD, 11));
        label.setBounds(237, 164, 46, 14);
        return label;
    }
    private static void initTripleToogleButton() {
        tglTriple = new JToggleButton("Triple");
        tglTriple.setBounds(262, 255, 121, 23);
        tglTriple.addActionListener(e -> tglDouble.setSelected(false));
    }

    private static void initDoubleToogleButton() {
        tglDouble = new JToggleButton("Double");
        tglDouble.setBounds(110, 255, 121, 23);
        tglDouble.addActionListener(e -> tglTriple.setSelected(false));
    }

    private static void initDritterWurfTextField() {
        dritterWurf = new JTextField();
        dritterWurf.setBounds(350, 218, 86, 20);
        dritterWurf.setColumns(10);
        dritterWurf.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                if (!(dritterWurf.getText().contains("Double") || dritterWurf.getText().contains("Triple"))) {
                    if (tglDouble.isSelected()) {
                        dritterWurf.setText("Double " + dritterWurf.getText());
                    }
                    if (tglTriple.isSelected()) {
                        dritterWurf.setText("Triple " + dritterWurf.getText());
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub

            }
        });
    }

    private static void initZweiterWurfTextField() {
        zweiterWurf = new JTextField();
        zweiterWurf.setBounds(200, 218, 86, 20);
        zweiterWurf.setColumns(10);
        zweiterWurf.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                if (!(zweiterWurf.getText().contains("Double") || zweiterWurf.getText().contains("Triple"))) {
                    if (tglDouble.isSelected()) {
                        zweiterWurf.setText("Double " + zweiterWurf.getText());
                    }
                    if (tglTriple.isSelected()) {
                        zweiterWurf.setText("Triple " + zweiterWurf.getText());
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {}
        });
    }

    private static void initErsterWurfTextField() {
        ersterWurf = new JTextField();
        ersterWurf.setBounds(50, 218, 86, 20);
        ersterWurf.setColumns(10);
        ersterWurf.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                if (!(ersterWurf.getText().contains("Double") || ersterWurf.getText().contains("Triple"))) {
                    if (tglDouble.isSelected()) {
                        ersterWurf.setText("Double " + ersterWurf.getText());
                    }
                    if (tglTriple.isSelected()) {
                        ersterWurf.setText("Triple " + ersterWurf.getText());
                    }
                }
            }
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e){}
        });
    }

    private static void initAnzahlSpielerComboBox() {
        comboBoxAnzahlSpieler = new JComboBox<>();
        comboBoxAnzahlSpieler.setBounds(237, 47, 40, 20);
        comboBoxAnzahlSpieler.addItem(1);
        comboBoxAnzahlSpieler.addItem(2);
        comboBoxAnzahlSpieler.addItem(3);
        comboBoxAnzahlSpieler.setSelectedIndex(0);
        comboBoxAnzahlSpieler.addActionListener(e -> handleComboBox());
    }

    private static void handleComboBox() {

        switch (comboBoxAnzahlSpieler.getSelectedIndex() + 1) {
            case 1 -> {
                spieler1.setEnabled(true);
                spieler2.setEnabled(false);
                spieler2.setText("Spieler 2");
                spieler3.setEnabled(false);
                spieler3.setText("Spieler 3");
                anzahlSpieler = 1;
            }
            case 2 -> {
                spieler1.setEnabled(true);
                spieler2.setEnabled(true);
                spieler2.setText("Spieler 2");
                spieler3.setEnabled(false);
                spieler3.setText("Spieler 3");
                anzahlSpieler = 2;
            }
            case 3 -> {
                spieler1.setEnabled(true);
                spieler2.setEnabled(true);
                spieler3.setEnabled(true);
                anzahlSpieler = 3;
            }
            default -> System.out.println("DartGui.handleComboBox: DEFAÚLT CASE");
        }
    }

    private static void initSpieler3TextField() {
        spieler3.setBounds(488, 47, 86, 20);
        spieler3.setEnabled(false);
        spieler3.setColumns(10);
    }

    private static void initSpieler2TextField() {
        spieler2.setBounds(393, 47, 86, 20);
        spieler2.setEnabled(false);
        spieler2.setColumns(10);
    }

    private static void initSpieler1TextField() {
        spieler1.setBounds(304, 47, 79, 20);
        spieler1.setEnabled(true);
        spieler1.setText("Spieler 1");
        spieler1.setColumns(10);
    }

    private static void initTableModel() {
        statsTable = new JTable();
        statsTable.setEnabled(false);
        statsTable.setModel(new DefaultTableModel(
                new Object[][]{
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                },
                new String[]{
                        "Spieler 1", "Spieler 2", "Spieler 3"
                }
        ) {
            final Class<Integer>[] columnTypes = new Class[]{
                    Integer.class, Integer.class, Integer.class
            };

            @Override
            public Class<Integer> getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }
        });

        statsModel = (DefaultTableModel) statsTable.getModel();
        statsTable.setBounds(304, 114, 270, 64);
    }

    private static void initPunkteComboBox() {
        comboPunkte = new JComboBox<>();
        comboPunkte.setBounds(20, 47, 100, 20);
        comboPunkte.addItem(501);
        comboPunkte.addItem(301);
        comboPunkte.setSelectedIndex(0);
        comboPunkte.addActionListener(e -> {
            pointsToPlay = (int) comboPunkte.getSelectedItem();
            System.out.println(pointsToPlay);
            for (int i = 0; i < 3; i++) {
                pointsRemaining[i].setText(Integer.toString(pointsToPlay));
            }
        });
    }

    private static JCheckBox initDoubleOutCheckBox() {
        JCheckBox checkBox = new JCheckBox("Double Out");
        checkBox.setBounds(131, 46, 100, 23);
        checkBox.addActionListener(e -> doubleOutEnabled = checkBox.isSelected());
        return checkBox;
    }

    private static JLabel initPunktzahlLabel() {
        JLabel label = new JLabel("Punktzahl");
        label.setBounds(20, 22, 100, 14);
        return label;
    }

    private static JButton initResetButton() {
        JButton button = new JButton("RESET");
        button.setBounds(10, 327, 89, 23);
        button.addActionListener(e -> DartLogic.reset());
        return button;
    }

    private static JButton initSaveButton() {
        JButton button = new JButton("Speichern");
        button.setFont(new Font("Tahoma", Font.BOLD, 11));
        button.setBounds(461, 217, 113, 23);
        button.addActionListener(event -> handleSaveButton());
        return button;
    }

    private static void handleSaveButton() {

        //Überprüfen, ob jedes Feld ausgefüllt ist.
        if (ersterWurf.getText().isBlank() || zweiterWurf.getText().isBlank() || dritterWurf.getText().isBlank()) {
            JOptionPane jOptionPane = new JOptionPane();
            jOptionPane.createDialog("Es müssen alle drei Würfe eingetragen werden!");
            jOptionPane.setVisible(true);
            return;
        }
        boolean ueberworfen;
        int average = 0;
        int[] werte;
        //nachdem ein ergebnis eingetragen ist, dann kann die spieleranz/punkteanz nicht mehr geändert werden.
        comboBoxAnzahlSpieler.setEnabled(false);
        comboPunkte.setEnabled(false);

        werte = DartLogic.tfToSumme();
        //Wert größer 60 in ein Feld eingetragen.
        if (werte[0] == -1) return;
        //Überprüfen, ob nach diesem Zug der die nächste Punktzahl mit einem Double-out
        // beendet werden kann.
        if (!DartLogic.checkFinishable(werte,
                Integer.parseInt(pointsRemaining[aktuellerSpieler].getText()))) {
            return;
        }


        //werte zu den listen adden
        alleWuerfe[aktuellerSpieler].add(werte[0]);
        alleWuerfe[aktuellerSpieler].add(werte[1]);
        alleWuerfe[aktuellerSpieler].add(werte[2]);
        summen[aktuellerSpieler].add(werte[3]);

        ueberworfen = DartLogic.checkUeberworfen(alleWuerfe[aktuellerSpieler]);
        JOptionPane jOptionPane = new JOptionPane();
        jOptionPane.createDialog("Leider ueberworfen!");
        jOptionPane.setVisible(true);
        System.out.println(ueberworfen);
        if (!ueberworfen) {
            pointsPlayed[aktuellerSpieler] += werte[3];

            pointsRemaining[aktuellerSpieler].setText(Integer.toString(pointsToPlay - pointsPlayed[aktuellerSpieler]));
            //average ausrechen
            for (Object wert : summen[aktuellerSpieler]) {
                average += (int) wert;
            }

            if (!summen[aktuellerSpieler].isEmpty()) {
                average = average / summen[aktuellerSpieler].size();
            }


            //highest berechnen
            if (hoechsterWurf[aktuellerSpieler] < werte[3]) {
                hoechsterWurf[aktuellerSpieler] = werte[3];
            }
        }
        //werte zu JTable einfügen.
        DartLogic.addToTable(werte[3], average);


        //spieler wechseln
        aktuellerSpieler++;
        if (aktuellerSpieler == anzahlSpieler) {
            aktuellerSpieler = 0;
        }
    }

    private static JFrame initFrame() {
        JFrame frameDartCounter = new JFrame();
        frameDartCounter.getContentPane().setForeground(new Color(0, 0, 0));
        frameDartCounter.setFont(null);
        frameDartCounter.setForeground(new Color(255, 0, 51));
        frameDartCounter.setType(Type.POPUP);
        frameDartCounter.getContentPane().setBackground(new Color(153, 204, 255));
        frameDartCounter.getContentPane().setLayout(null);
        return frameDartCounter;
    }
}
