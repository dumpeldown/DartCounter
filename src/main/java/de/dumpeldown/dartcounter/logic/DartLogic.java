package de.dumpeldown.dartcounter.logic;

import de.dumpeldown.dartcounter.gui.DartGui;

import javax.swing.*;
import java.util.List;

public class DartLogic {
    private static DartLogic dartLogic;

    public int aktuellerSpielerIndex = 0;

    public DartSpieler[] alleSpieler = new DartSpieler[]{
            new DartSpieler()
    };
    public DartSpieler aktuellerSpieler = alleSpieler[0];

    public static DartLogic getInstance() {
        if (dartLogic == null) dartLogic = new DartLogic();
        return dartLogic;
    }

    public boolean isFinishable() {
        if (!DartGui.doubleOutEnabled) {
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
        DartGui.tglDouble.setSelected(false);
        DartGui.tglTriple.setSelected(false);
        for (DartSpieler dartSpieler : alleSpieler) {
            dartSpieler = new DartSpieler();
        }

        for (JLabel label : DartGui.pointsRemainingLabels) {
            label.setText("501");
            label.setBorder(null);
        }

        DartGui.comboBoxAnzahlSpieler.setEnabled(true);
        DartGui.comboPunkte.setEnabled(true);
        DartGui.comboPunkte.setSelectedIndex(0);
        for (int i = 0; i < 4; i++) {
            for (int p = 0; p < 3; p++) {
                DartGui.statsTable.getModel().setValueAt(null, i, p);
            }
        }
    }

    public int[] tfToSumme() {
        int werte[] = new int[4];
        int summe;
        int absFirst = 0, absSec = 0, absThird = 0;
        //Prüfen, ob werte zwischen 0 und 20 eingegeben wurden.
        if ((Integer.parseInt(DartGui.wuerfeTextFields[0].getText().replace("Double ", "")
                .replace("Triple ", "")) > 20) || Integer.parseInt(DartGui.wuerfeTextFields[0].getText().replace("Double ", "")
                .replace("Triple ", "")) < 0) {
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

}
