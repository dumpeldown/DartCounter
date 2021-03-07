package de.dumpeldown.dartcounter.logic;

import de.dumpeldown.dartcounter.gui.DartGui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DartLogic {
    private static DartLogic dartLogic;

    public int aktuellerSpielerIndex = 0;
    public int anzahlSpieler = 1;
    public static boolean doubleOutEnabled;
    public static int pointsToPlay = 501;

    public DartSpieler[] alleSpieler = new DartSpieler[]{
            new DartSpieler()
    };
    public DartSpieler aktuellerSpieler = alleSpieler[0];

    public static DartLogic getInstance() {
        if (dartLogic == null) dartLogic = new DartLogic();
        return dartLogic;
    }

    public boolean isFinishable() {
        if (!doubleOutEnabled) {
            return true;
        }
        //wenn die pointRemainign nach den geworfenen punkten 1 wäre, dann ist es nicht mit
        // double out finishable.
        return (aktuellerSpieler.getPointsRemaining() - aktuellerSpieler.getSumOfLastThree()) != 1;
    }

    public void reset() {
        aktuellerSpielerIndex = 0;
        DartGui.wuerfeTextFields[0].setText("");
        DartGui.wuerfeTextFields[1].setText("");
        DartGui.wuerfeTextFields[2].setText("");
        DartGui.pointsRemainingLabels[0].setText("512");
        DartGui.pointsRemainingLabels[0].setBorder(BorderFactory.createDashedBorder(Color.RED,
                1.2f, 2f, 3, true));
        DartGui.pointsRemainingLabels[1].setText("0");
        DartGui.pointsRemainingLabels[2].setText("0");
        DartGui.pointsRemainingLabels[0].setVisible(true);
        DartGui.pointsRemainingLabels[1].setVisible(false);
        DartGui.pointsRemainingLabels[2].setVisible(false);
        DartGui.tglDouble.setSelected(false);
        DartGui.tglTriple.setSelected(false);

        DartGui.comboBoxAnzahlSpieler.setEnabled(true);
        DartGui.comboBoxAnzahlSpieler.setSelectedIndex(0);
        DartGui.spielerTextFields[0].setText("Spieler 1");
        DartGui.spielerTextFields[0].setEnabled(true);
        DartGui.spielerTextFields[1].setText("");
        DartGui.spielerTextFields[1].setEnabled(false);
        DartGui.spielerTextFields[2].setText("");
        DartGui.spielerTextFields[2].setEnabled(false);

        DartGui.comboPunkte.setEnabled(true);
        DartGui.comboPunkte.setSelectedIndex(0);
        DartGui.checkBoxDoubleOut.setEnabled(true);
        for (int i = 0; i < 4; i++) {
            for (int p = 0; p < 3; p++) {
                DartGui.statsTable.getModel().setValueAt(null, i, p);
            }
        }
    }

    public int[] tfToSumme() {
        int[] werte = new int[4];
        int summe;
        int absFirst = 0, absSec = 0, absThird = 0;
        //Prüfen, ob werte zwischen 0 und 20 eingegeben wurden.
        try {
            if ((Integer.parseInt(DartGui.wuerfeTextFields[0].getText().replace("Double ", "")
                    .replace("Triple ", "")) > 20) || Integer.parseInt(DartGui.wuerfeTextFields[0].getText().replace("Double ", "")
                    .replace("Triple ", "")) < 0) {
                JOptionPane.showMessageDialog(DartGui.frameDartCounter, "Du hast ungültige Werte " +
                        "eingetragen!");
                return new int[]{-1, -1, -1};
            }
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(DartGui.frameDartCounter, "Du hast ungültige Werte " +
                    "eingetragen!");
            return new int[]{-1, -1, -1};
        }

        //get text from textfields.
        if (DartGui.wuerfeTextFields[0].getText().contains("Double"))
            absFirst = 2 * Integer.parseInt(DartGui.wuerfeTextFields[0].getText().replaceAll("Double ", ""));
        else if (DartGui.wuerfeTextFields[0].getText().contains("Triple"))
            absFirst = 3 * Integer.parseInt(DartGui.wuerfeTextFields[0].getText().replaceAll("Triple ", ""));
        else try {
                absFirst = Integer.parseInt(DartGui.wuerfeTextFields[0].getText());
            } catch (NumberFormatException ignored) {
            }
        if (DartGui.wuerfeTextFields[1].getText().contains("Double")) {
            absSec = 2 * Integer.parseInt(DartGui.wuerfeTextFields[1].getText().replaceAll("Double ", ""));
        } else if (DartGui.wuerfeTextFields[1].getText().contains("Triple")) {
            absSec = 3 * Integer.parseInt(DartGui.wuerfeTextFields[1].getText().replaceAll("Triple ", ""));
        } else {
            try {
                absSec = Integer.parseInt(DartGui.wuerfeTextFields[1].getText());
            } catch (NumberFormatException ignored) {
            }
        }
        if (DartGui.wuerfeTextFields[2].getText().contains("Double"))
            absThird = 2 * Integer.parseInt(DartGui.wuerfeTextFields[2].getText().replaceAll("Double ", ""));
        else if (DartGui.wuerfeTextFields[2].getText().contains("Triple"))
            absThird = 3 * Integer.parseInt(DartGui.wuerfeTextFields[2].getText().replaceAll("Triple ", ""));
        else try {
                absThird = Integer.parseInt(DartGui.wuerfeTextFields[2].getText());
            } catch (NumberFormatException ignored) {
            }
        summe = absFirst + absSec + absThird;
        werte[0] = absFirst;
        werte[1] = absSec;
        werte[2] = absThird;
        werte[3] = summe;
        return werte;
    }

    public static boolean checkUeberworfen(List<Integer> summen) {
        if (summen.size() <= 3) {
            return false;
        }
        int gesamt = 0;
        for (int i = summen.size() - 3; i < summen.size(); i++) {
            System.out.println(i);
            gesamt += summen.get(i);
        }
        System.out.println(gesamt + "," + DartGui.pointsRemainingLabels[dartLogic.aktuellerSpielerIndex].getText());
        return gesamt > Integer.parseInt(DartGui.pointsRemainingLabels[dartLogic.aktuellerSpielerIndex].getText());
    }

    public void addToTable(int summe, double average) {
        try {
            DartGui.statsTable.getModel().setValueAt(summe, 0, aktuellerSpielerIndex);
            DartGui.statsTable.getModel().setValueAt(average, 1, aktuellerSpielerIndex);
            DartGui.statsTable.getModel().setValueAt(aktuellerSpieler.hoechsterWurf, 2,
                    aktuellerSpielerIndex);
            DartGui.statsTable.getModel().setValueAt(aktuellerSpieler.alleWuerfe.size(), 3,
                    aktuellerSpielerIndex);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(DartGui.frameDartCounter, "Bitte gib ganze Zahlen als Werte ein!",
                    "Fehlermeldung.", JOptionPane.OK_OPTION);
        }
    }

    public void setNextPlayer() {
        dartLogic.aktuellerSpielerIndex++;
        if (dartLogic.aktuellerSpielerIndex == anzahlSpieler) {
            dartLogic.aktuellerSpielerIndex = 0;
        }
        dartLogic.aktuellerSpieler = dartLogic.alleSpieler[dartLogic.aktuellerSpielerIndex];
    }
}
