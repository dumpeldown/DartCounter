package de.dumpeldown.dartcounter.gui;

import de.dumpeldown.dartcounter.language.LanguageEnglish;
import de.dumpeldown.dartcounter.language.LanguageGerman;
import de.dumpeldown.dartcounter.logic.DartLogic;
import de.dumpeldown.dartcounter.logic.DartSpieler;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.Window.Type;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.OptionalDouble;

public class DartGui {

    public static JTextField[] spielerTextFields;
    public static JTextField[] wuerfeTextFields;
    private static JComboBox<String> languageComboBox;
    public static JComboBox<Integer> comboPunkte;
    public static JComboBox<Integer> comboBoxAnzahlSpieler;
    public static JCheckBox checkBoxDoubleOut;
    private static JTable table;
    public static JTable statsTable;
    public static JToggleButton tglDouble;
    public static JToggleButton tglTriple;
    private static JLabel lblAnzahlDerSpieler;
    private static JLabel lblLast;
    private static JLabel lblAverage;
    private static JLabel lblHighest;
    private static JLabel lblWuerfe;
    private static JLabel lblPunktzahl;
    private static JLabel themeLabel;
    private static JButton resetButton;
    private static JButton saveButton;
    private static DefaultTableModel statsModel;
    public static JLabel[] pointsRemainingLabels = new JLabel[]{new JLabel("501"), new JLabel(""),
            new JLabel("")};
    public static JFrame frameDartCounter;

    public void start() {

        initFrame();
        initResetButton();
        initSaveButton();
        initDoubleOutCheckBox();
        initPunktzahlLabel();
        initLanguageCheckBox();
        initPunkteComboBox();
        initTableModel();
        initSpieler1TextField();
        initSpieler2TextField();
        initSpieler3TextField();
        initAnzahlSpielerComboBox();
        initDoubleToogleButton();
        initTripleToogleButton();
        initPointsRemainingLabel();
        initWuerfeTextFields();
        initSpielerTextFields();
        initDarkmodeImage();

        table = new JTable();
        table.setBounds(0, 0, 0, 0);

        lblAnzahlDerSpieler = new JLabel("Anzahl der Spieler");
        lblAnzahlDerSpieler.setBounds(220, 22, 146, 14);

        lblLast = new JLabel("Last");
        lblLast.setBounds(200, 115, 100, 14);

        lblAverage = new JLabel("Average");
        lblAverage.setBounds(200, 130, 100, 14);

        lblHighest = new JLabel("Highest");
        lblHighest.setBounds(200, 147, 100, 14);

        lblWuerfe = new JLabel("Wuerfe");
        lblWuerfe.setBounds(200, 164, 100, 14);


        frameDartCounter.getContentPane().add(comboPunkte);
        frameDartCounter.getContentPane().add(table);
        frameDartCounter.getContentPane().add(spielerTextFields[0]);
        frameDartCounter.getContentPane().add(spielerTextFields[1]);
        frameDartCounter.getContentPane().add(spielerTextFields[2]);
        frameDartCounter.getContentPane().add(lblAnzahlDerSpieler);
        frameDartCounter.getContentPane().add(comboBoxAnzahlSpieler);
        frameDartCounter.getContentPane().add(statsTable);
        frameDartCounter.getContentPane().add(lblHighest);
        frameDartCounter.getContentPane().add(lblAverage);
        frameDartCounter.getContentPane().add(lblLast);
        frameDartCounter.getContentPane().add(wuerfeTextFields[0]);
        frameDartCounter.getContentPane().add(wuerfeTextFields[1]);
        frameDartCounter.getContentPane().add(wuerfeTextFields[2]);
        frameDartCounter.getContentPane().add(tglDouble);
        frameDartCounter.getContentPane().add(tglTriple);
        frameDartCounter.getContentPane().add(lblWuerfe);
        frameDartCounter.getContentPane().add(pointsRemainingLabels[0]);
        frameDartCounter.getContentPane().add(pointsRemainingLabels[1]);
        frameDartCounter.getContentPane().add(lblPunktzahl);
        frameDartCounter.getContentPane().add(checkBoxDoubleOut);
        frameDartCounter.getContentPane().add(saveButton);
        frameDartCounter.getContentPane().add(resetButton);
        frameDartCounter.getContentPane().add(pointsRemainingLabels[2]);
        frameDartCounter.getContentPane().add(languageComboBox);
        frameDartCounter.getContentPane().add(themeLabel);

        frameDartCounter.setVisible(true);
        frameDartCounter.setSize(600, 400);
        frameDartCounter.setTitle("DartCounter by dumpeldown");
        frameDartCounter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        handleLanguageChange();
    }

    private void initDarkmodeImage() {
        BufferedImage img = null;
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("brightmode.png");
        try {
            img = ImageIO.read(is);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        ImageIcon icon = new ImageIcon(img);
        themeLabel = new JLabel(icon);
        themeLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                if (frameDartCounter.getContentPane().getBackground().getBlue() == 98) {
                    //darkmode is enabled, enable bright mode
                    frameDartCounter.getContentPane().setForeground(new Color(0, 0, 0));
                    frameDartCounter.setForeground(new Color(255, 0, 51));
                    frameDartCounter.getContentPane().setBackground(new Color(153, 204, 255));
                    checkBoxDoubleOut.setBackground(new Color(153, 204, 255));
                } else {
                    //brightmode is enable, enable dark mode.
                    frameDartCounter.getContentPane().setForeground(new Color(255, 255, 255));
                    frameDartCounter.setForeground(new Color(255, 0, 51));
                    frameDartCounter.getContentPane().setBackground(new Color(98, 98, 98));
                    checkBoxDoubleOut.setBackground(new Color(98, 98, 98));
                }
            }
        });
        themeLabel.setBounds(555, 327, 25, 25);
    }

    private static void initLanguageCheckBox() {
        languageComboBox = new JComboBox<>();
        languageComboBox.addItem("English");
        languageComboBox.addItem("Deutsch");
        languageComboBox.setBounds(461, 327, 89, 23);
        languageComboBox.addActionListener(e -> handleLanguageChange());
    }

    private static void handleLanguageChange() {
        String lang = languageComboBox.getModel().getSelectedItem().toString();
        switch (lang) {
            case "English":
                LanguageEnglish ENGLISH = LanguageEnglish.getInstance();
                lblWuerfe.setText(ENGLISH.anzahlWuerfe);
                lblAverage.setText(ENGLISH.durchschnitt);
                lblHighest.setText(ENGLISH.hoechster);
                lblLast.setText(ENGLISH.letzer);
                lblAnzahlDerSpieler.setText(ENGLISH.spielerAnzahl);
                lblPunktzahl.setText(ENGLISH.punktzahl);
                saveButton.setText(ENGLISH.speichern);
                resetButton.setText(ENGLISH.reset);
                break;
            case "Deutsch":
                LanguageGerman DEUTSCH = LanguageGerman.getInstance();
                lblWuerfe.setText(DEUTSCH.anzahlWuerfe);
                lblAverage.setText(DEUTSCH.durchschnitt);
                lblHighest.setText(DEUTSCH.hoechster);
                lblLast.setText(DEUTSCH.letzer);
                lblAnzahlDerSpieler.setText(DEUTSCH.spielerAnzahl);
                lblPunktzahl.setText(DEUTSCH.punktzahl);
                saveButton.setText(DEUTSCH.speichern);
                resetButton.setText(DEUTSCH.reset);
                break;
        }
    }

    private static void initSpielerTextFields() {
        spielerTextFields = new JTextField[]{
                initSpieler1TextField(),
                initSpieler2TextField(),
                initSpieler3TextField()
        };
        for (JTextField jTextField : spielerTextFields) {
            jTextField.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    jTextField.setText("");
                }

                @Override
                public void focusLost(FocusEvent e) {
                }
            });
        }
    }

    private static void initWuerfeTextFields() {
        wuerfeTextFields = new JTextField[]{
                initErsterWurfTextField(),
                initZweiterWurfTextField(),
                initDritterWurfTextField()
        };
        for (JTextField jTextField : wuerfeTextFields) {
            jTextField.addKeyListener(new KeyListener() {

                @Override
                public void keyTyped(KeyEvent e) {
                    if (!(jTextField.getText().contains("Double") || jTextField.getText().contains("Triple"))) {
                        if (tglDouble.isSelected()) {
                            jTextField.setText("Double " + jTextField.getText());
                        }
                        if (tglTriple.isSelected()) {
                            jTextField.setText("Triple " + jTextField.getText());
                        }
                    }
                }

                @Override
                public void keyPressed(KeyEvent e) {
                }

                @Override
                public void keyReleased(KeyEvent e) {
                }
            });
        }
    }

    private static void initPointsRemainingLabel() {
        pointsRemainingLabels[0].setBounds(314, 78, 46, 14);
        pointsRemainingLabels[1].setBounds(403, 78, 46, 14);
        pointsRemainingLabels[2].setBounds(498, 78, 46, 14);
        pointsRemainingLabels[0].setBorder(BorderFactory.createDashedBorder(Color.RED, 1.2f, 2f,
                3, true));
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

    private static JTextField initDritterWurfTextField() {
        JTextField jTextField = new JTextField();
        jTextField.setBounds(350, 218, 86, 20);
        jTextField.setColumns(10);
        return jTextField;
    }

    private static JTextField initZweiterWurfTextField() {
        JTextField jTextField = new JTextField();
        jTextField.setBounds(200, 218, 86, 20);
        jTextField.setColumns(10);
        return jTextField;
    }

    private static JTextField initErsterWurfTextField() {
        JTextField jTextField = new JTextField();
        jTextField.setBounds(50, 218, 86, 20);
        jTextField.setColumns(10);
        return jTextField;
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
        DartLogic dartLogic = DartLogic.getInstance();

        switch (comboBoxAnzahlSpieler.getSelectedIndex() + 1) {
            case 1:
                spielerTextFields[0].setEnabled(true);
                spielerTextFields[0].setText("Player 1");
                spielerTextFields[1].setEnabled(false);
                spielerTextFields[1].setText("Player 2");
                spielerTextFields[2].setEnabled(false);
                spielerTextFields[2].setText("Player 3");
                pointsRemainingLabels[0].setVisible(true);
                pointsRemainingLabels[1].setVisible(false);
                pointsRemainingLabels[2].setVisible(false);
                dartLogic.anzahlSpieler = 1;
                dartLogic.alleSpieler = new DartSpieler[]{
                        new DartSpieler()
                };
                break;
            case 2:
                spielerTextFields[0].setEnabled(true);
                spielerTextFields[0].setText("Spieler 1");
                spielerTextFields[1].setEnabled(true);
                spielerTextFields[1].setText("Spieler 2");
                spielerTextFields[2].setEnabled(false);
                spielerTextFields[2].setText("Spieler 3");
                pointsRemainingLabels[0].setVisible(true);
                pointsRemainingLabels[1].setVisible(true);
                pointsRemainingLabels[2].setVisible(false);
                dartLogic.anzahlSpieler = 2;
                dartLogic.alleSpieler = new DartSpieler[]{
                        new DartSpieler(),
                        new DartSpieler()
                };
                break;
            case 3:
                spielerTextFields[0].setEnabled(true);
                spielerTextFields[0].setText("Spieler 1");
                spielerTextFields[1].setEnabled(true);
                spielerTextFields[1].setText("Spieler 2");
                spielerTextFields[2].setEnabled(true);
                spielerTextFields[2].setText("Spieler 3");
                pointsRemainingLabels[0].setVisible(true);
                pointsRemainingLabels[1].setVisible(true);
                pointsRemainingLabels[2].setVisible(true);
                dartLogic.anzahlSpieler = 3;
                dartLogic.alleSpieler = new DartSpieler[]{
                        new DartSpieler(),
                        new DartSpieler(),
                        new DartSpieler()
                };
                break;
        }
    }

    private static JTextField initSpieler3TextField() {
        JTextField jTextField = new JTextField();
        jTextField.setBounds(488, 47, 86, 20);
        jTextField.setEnabled(false);
        jTextField.setColumns(10);
        return jTextField;
    }

    private static JTextField initSpieler2TextField() {
        JTextField jTextField = new JTextField();
        jTextField.setBounds(393, 47, 86, 20);
        jTextField.setEnabled(false);
        jTextField.setColumns(10);
        return jTextField;
    }

    private static JTextField initSpieler1TextField() {
        JTextField jTextField = new JTextField();
        jTextField.setBounds(304, 47, 79, 20);
        jTextField.setEnabled(true);
        jTextField.setText("Spieler 1");
        jTextField.setColumns(10);
        return jTextField;
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
        DartLogic dartLogic = DartLogic.getInstance();
        comboPunkte = new JComboBox<>();
        comboPunkte.setBounds(20, 47, 100, 20);
        comboPunkte.addItem(501);
        comboPunkte.addItem(301);
        comboPunkte.setSelectedIndex(0);
        comboPunkte.addActionListener(e -> {
            DartLogic.pointsToPlay = (int) comboPunkte.getModel().getSelectedItem();
            for (int i = 0; i < 3; i++) {
                pointsRemainingLabels[i].setText(Integer.toString(DartLogic.pointsToPlay));
            }
            for (DartSpieler dartSpieler : dartLogic.alleSpieler) {
                dartSpieler.setPointsRemaining(DartLogic.pointsToPlay);
            }
        });
    }

    private static void initDoubleOutCheckBox() {
        checkBoxDoubleOut = new JCheckBox("Double Out");
        checkBoxDoubleOut.setBounds(131, 46, 100, 23);
        checkBoxDoubleOut.setBackground(new Color(153, 204, 255));
        checkBoxDoubleOut.addActionListener(e -> DartLogic.doubleOutEnabled =
                checkBoxDoubleOut.isSelected());
    }

    private static void initPunktzahlLabel() {
        lblPunktzahl = new JLabel();
        lblPunktzahl.setBounds(20, 22, 120, 14);
    }

    private static void initResetButton() {
        DartLogic dartLogic = DartLogic.getInstance();
        resetButton = new JButton();
        resetButton.setBounds(10, 327, 150, 23);
        resetButton.addActionListener(e -> dartLogic.reset());
    }

    private static void initSaveButton() {
        saveButton = new JButton();
        saveButton.setBounds(461, 217, 113, 23);
        saveButton.addActionListener(event -> handleSaveButton());
    }

    private static void handleSaveButton() {
        DartLogic dartLogic = DartLogic.getInstance();
        dartLogic.aktuellerSpieler = dartLogic.alleSpieler[dartLogic.aktuellerSpielerIndex];

        //�berpr�fen, ob jedes Feld ausgef�llt ist.
        if (wuerfeTextFields[0].getText().isBlank() || wuerfeTextFields[1].getText().isBlank() || wuerfeTextFields[2].getText().isBlank()) {
            JOptionPane.showMessageDialog(frameDartCounter, "Es muss f�r jeden Wurf ein Wert " +
                    "eingetragen werden!");
            return;
        }
        boolean ueberworfen;
        OptionalDouble average;
        int[] werte;
        //nachdem ein ergebnis eingetragen ist,
        // dann kann die spieleranz/punkteanz/double-out nicht mehr
        // ge�ndert werden.
        comboBoxAnzahlSpieler.setEnabled(false);
        checkBoxDoubleOut.setEnabled(false);
        comboPunkte.setEnabled(false);

        werte = dartLogic.tfToSumme();
        //Wert gr��er 60 in ein Feld eingetragen.
        if (werte[0] == -1) return;


        //werte zu den listen adden
        dartLogic.aktuellerSpieler.addWuerfe(werte[0], werte[1], werte[2]);

        //�berpr�fen, ob nach diesem Zug der die n�chste Punktzahl mit einem Double-out
        // beendet werden kann.
        boolean finishable = dartLogic.isFinishable();
        if (!finishable) {
            dartLogic.aktuellerSpieler.deleteLastThreeWuerfe();
            dartLogic.setNextPlayer();
            JOptionPane.showMessageDialog(frameDartCounter, "Die �brige Punktzahl kann mit " +
                    "'Double Out' nicht geworfen werden!");
            return;
        }

        ueberworfen = DartLogic.checkUeberworfen(dartLogic.aktuellerSpieler.getAlleWuerfe());
        System.out.println("�berworfen:" + ueberworfen);
        if (ueberworfen) {
            dartLogic.aktuellerSpieler.deleteLastThreeWuerfe();
            dartLogic.setNextPlayer();
            JOptionPane.showMessageDialog(frameDartCounter, "Leider �berworfen!");
            return;
        }

        //wenn nicht �berworfen und finishable
        dartLogic.aktuellerSpieler.updatePointsPlayed(werte[3]);
        dartLogic.aktuellerSpieler.updatePointsRemaining(werte[3]);
        pointsRemainingLabels[dartLogic.aktuellerSpielerIndex].setText(Integer.toString(dartLogic.aktuellerSpieler.getPointsRemaining()));

        //average ausrechen
        average = dartLogic.aktuellerSpieler.getAlleWuerfe().stream()
                .mapToInt(Integer::intValue).average();
        if (average.isEmpty()) {
            JOptionPane.showMessageDialog(DartGui.frameDartCounter, "Fehler beim Ausrechnen des " +
                    "Durchschnitts.");
            average = OptionalDouble.of(0.0);
        }

        //highest berechnen und setzen
        dartLogic.aktuellerSpieler.updateHoechsterWurf(werte[3]);

        //werte zu JTable einf�gen, average mit 2 dizimal stellen.
        dartLogic.addToTable(werte[3], Math.floor(average.getAsDouble() * 100) / 100);

        //spieler wechseln
        dartLogic.setNextPlayer();

        //Reset Borders und nur f�r den n�chsten Spieler setzen.
        for (JLabel label : pointsRemainingLabels) {
            label.setBorder(null);
        }
        pointsRemainingLabels[dartLogic.aktuellerSpielerIndex].setBorder(BorderFactory.createDashedBorder(Color.RED, 1.2f, 2f,
                3, true));
        for (JTextField jTextField : wuerfeTextFields) {
            jTextField.setText("");
        }
    }

    private static void initFrame() {
        frameDartCounter = new JFrame();
        frameDartCounter.getContentPane().setForeground(new Color(0, 0, 0));
        frameDartCounter.setForeground(new Color(255, 0, 51));
        frameDartCounter.setType(Type.POPUP);
        frameDartCounter.getContentPane().setBackground(new Color(153, 204, 255));
        frameDartCounter.getContentPane().setLayout(null);
    }
}
