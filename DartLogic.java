import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class DartLogic {
	public static boolean checkFinishable(JTextField first, JTextField sec, JTextField third) {
		
		return false;
	}
	
	public static void reset() {
		DartGui.aktuellerSpieler = 0;
		DartGui.firstThrow.setText("");
		DartGui.secondThrow.setText("");
		DartGui.thirdThrow.setText("");
		DartGui.tglDouble.setSelected(false);
		DartGui.tglTriple.setSelected(false);
		for(int i = 0; i < 3; i++) {
			DartGui.summen[i] = new ArrayList<Integer>();
			DartGui.einzelW[i] = new ArrayList<Integer>();
			DartGui.remain[i].setText(Integer.toString(DartGui.pointsToPlay));
			DartGui.pointsPlayed[i] = 0;
		}

		DartGui.comboBoxAnzahlSpieler.setEnabled(true);
		DartGui.comboPunkte.setEnabled(true);
		for(int i = 0; i < 4;i++) {
			for(int p = 0; p < 3; p++) {
				DartGui.statsTable.getModel().setValueAt(null, i, p);
			}
		}
	}
	
	public static int[] tfToSumme() {
		int werte[] = new int[4];
		int summe;
		int absFirst = 0, absSec = 0, absThird = 0;

		
		//get text from textfields.
		if(DartGui.firstThrow.getText().contains("Double")) absFirst = 2 * Integer.parseInt(DartGui.firstThrow.getText().replaceAll("Double ", ""));
		else if(DartGui.firstThrow.getText().contains("Triple")) absFirst = 3 * Integer.parseInt(DartGui.firstThrow.getText().replaceAll("Triple ", ""));
		else try{absFirst = Integer.parseInt(DartGui.firstThrow.getText());}catch(NumberFormatException e) {absFirst = 0;}
		if(DartGui.secondThrow.getText().contains("Double")) absSec = 2 * Integer.parseInt(DartGui.secondThrow.getText().replaceAll("Double ", ""));
		else if(DartGui.secondThrow.getText().contains("Triple")) absSec = 3 * Integer.parseInt(DartGui.secondThrow.getText().replaceAll("Triple ", ""));
		else try{absSec = Integer.parseInt(DartGui.secondThrow.getText());}catch(NumberFormatException e) {absSec = 0;}
		if(DartGui.thirdThrow.getText().contains("Double")) absThird = 2 * Integer.parseInt(DartGui.thirdThrow.getText().replaceAll("Double ", ""));
		else if(DartGui.thirdThrow.getText().contains("Triple")) absThird = 3 * Integer.parseInt(DartGui.thirdThrow.getText().replaceAll("Triple ", ""));
		else try{absThird = Integer.parseInt(DartGui.thirdThrow.getText());}catch(NumberFormatException e) {absThird = 0;}
		summe = absFirst + absSec + absThird;
		werte[0] = absFirst;
		werte[1] = absSec;
		werte[2] = absThird;
		werte[3] = summe;
		return werte;
	}
	public static boolean checkUeberworfen(List<Integer> summen) {
		int gsmt = 0;
		for(int wert : summen) {
			gsmt += wert;
		}
		
		//letzen werte wieder löschen
		DartGui.einzelW[DartGui.aktuellerSpieler].remove(DartGui.einzelW[DartGui.aktuellerSpieler].size()-1);
		DartGui.einzelW[DartGui.aktuellerSpieler].remove(DartGui.einzelW[DartGui.aktuellerSpieler].size()-1);
		DartGui.einzelW[DartGui.aktuellerSpieler].remove(DartGui.einzelW[DartGui.aktuellerSpieler].size()-1);
		DartGui.summen[DartGui.aktuellerSpieler].remove(DartGui.summen[DartGui.aktuellerSpieler].size()-1);
		System.out.println("gsmt_"+ gsmt+" toPlay: "+DartGui.pointsToPlay);
		return gsmt > DartGui.pointsToPlay;
	}
	
	public static void addToTable(int summe, int average) {
		try {
			DartGui.statsTable.getModel().setValueAt(summe, 0, DartGui.aktuellerSpieler);
			DartGui.statsTable.getModel().setValueAt(average, 1, DartGui.aktuellerSpieler);
			DartGui.statsTable.getModel().setValueAt(DartGui.highest[DartGui.aktuellerSpieler], 2, DartGui.aktuellerSpieler);
			DartGui.statsTable.getModel().setValueAt(DartGui.einzelW[DartGui.aktuellerSpieler].size(), 3, DartGui.aktuellerSpieler);
		}catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Bitte gib ganze Zahlen als Werte ein!", "Fehlermeldung.", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
