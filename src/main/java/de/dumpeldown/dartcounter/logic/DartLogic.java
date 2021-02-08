package de.dumpeldown.dartcounter.logic;

import de.dumpeldown.dartcounter.gui.DartGui;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DartLogic {
    public static boolean checkFinishable(int[] werte, int remaining) {
        if (DartGui.doubleOutEnabled) {
            return true;
        }
        //wenn die pointRemainign nach den geworfenen punkten 1 wäre, dann ist es nicht mit
        // double out finishable.
        return remaining - Arrays.stream(werte).sum() != 1;
    }

    public static void reset() {
        DartGui.aktuellerSpieler = 0;
        DartGui.ersterWurf.setText("");
        DartGui.zweiterWurf.setText("");
        DartGui.dritterWurf.setText("");
        DartGui.tglDouble.setSelected(false);
        DartGui.tglTriple.setSelected(false);
        for (int i = 0; i < 3; i++) {
            DartGui.summen[i] = new ArrayList<>();
            DartGui.alleWuerfe[i] = new ArrayList<>();
            DartGui.pointsRemaining[i].setText(Integer.toString(DartGui.pointsToPlay));
            DartGui.pointsPlayed[i] = 0;
        }

        DartGui.comboBoxAnzahlSpieler.setEnabled(true);
        DartGui.comboPunkte.setEnabled(true);
        for (int i = 0; i < 4; i++) {
            for (int p = 0; p < 3; p++) {
                DartGui.statsTable.getModel().setValueAt(null, i, p);
            }
        }
    }

    public static int[] tfToSumme() {
        int werte[] = new int[4];
        int summe;
        int absFirst = 0, absSec = 0, absThird = 0;
        if (Integer.parseInt(DartGui.ersterWurf.getText().replace("Double ", "")
                .replace("Triple ", "")) > 60) {
            return new int[]{-1, -1, -1};
        }

        //get text from textfields.
        if (DartGui.ersterWurf.getText().contains("Double"))
            absFirst = 2 * Integer.parseInt(DartGui.ersterWurf.getText().replaceAll("Double ", ""));
        else if (DartGui.ersterWurf.getText().contains("Triple"))
            absFirst = 3 * Integer.parseInt(DartGui.ersterWurf.getText().replaceAll("Triple ", ""));
        else try {
                absFirst = Integer.parseInt(DartGui.ersterWurf.getText());
            } catch (NumberFormatException ignored) {
            }
        if (DartGui.zweiterWurf.getText().contains("Double")) {
            absSec = 2 * Integer.parseInt(DartGui.zweiterWurf.getText().replaceAll("Double ", ""));
        } else if (DartGui.zweiterWurf.getText().contains("Triple")) {
            absSec = 3 * Integer.parseInt(DartGui.zweiterWurf.getText().replaceAll("Triple ", ""));
        } else {
            try {
                absSec = Integer.parseInt(DartGui.zweiterWurf.getText());
            } catch (NumberFormatException ignored) {
            }
        }
        if (DartGui.dritterWurf.getText().contains("Double"))
            absThird = 2 * Integer.parseInt(DartGui.dritterWurf.getText().replaceAll("Double ", ""));
        else if (DartGui.dritterWurf.getText().contains("Triple"))
            absThird = 3 * Integer.parseInt(DartGui.dritterWurf.getText().replaceAll("Triple ", ""));
        else try {
                absThird = Integer.parseInt(DartGui.dritterWurf.getText());
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
        System.out.println(gesamt + "," + DartGui.pointsRemaining[DartGui.aktuellerSpieler].getText());
        if (gesamt > Integer.parseInt(DartGui.pointsRemaining[DartGui.aktuellerSpieler].getText())) {
            //letzen werte wieder löschen
            DartGui.alleWuerfe[DartGui.aktuellerSpieler].remove(DartGui.alleWuerfe[DartGui.aktuellerSpieler].size() - 1);
            DartGui.alleWuerfe[DartGui.aktuellerSpieler].remove(DartGui.alleWuerfe[DartGui.aktuellerSpieler].size() - 1);
            DartGui.alleWuerfe[DartGui.aktuellerSpieler].remove(DartGui.alleWuerfe[DartGui.aktuellerSpieler].size() - 1);
            DartGui.summen[DartGui.aktuellerSpieler].remove(DartGui.summen[DartGui.aktuellerSpieler].size() - 1);
            return true;
        }
        return false;
    }

    public static void addToTable(int summe, int average) {
        try {
            DartGui.statsTable.getModel().setValueAt(summe, 0, DartGui.aktuellerSpieler);
            DartGui.statsTable.getModel().setValueAt(average, 1, DartGui.aktuellerSpieler);
            DartGui.statsTable.getModel().setValueAt(DartGui.hoechsterWurf[DartGui.aktuellerSpieler], 2, DartGui.aktuellerSpieler);
            DartGui.statsTable.getModel().setValueAt(DartGui.alleWuerfe[DartGui.aktuellerSpieler].size(), 3, DartGui.aktuellerSpieler);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Bitte gib ganze Zahlen als Werte ein!", "Fehlermeldung.", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
