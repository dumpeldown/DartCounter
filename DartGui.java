
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Window.Type;

public class DartGui {
	static JTable table;
	 static JTextField [] txtSpieler = new JTextField[] {new JTextField("Spieler1"),new JTextField("Spieler2"),new JTextField("Spieler3")};
	 static boolean doubleOutEnabled;
	 static int pointsToPlay = 501;
	 static int[] pointsPlayed = new int[3];
	 static JTable statsTable;
	 static int anzahlSpieler = 1;
	 static JTextField firstThrow;
	 static JTextField secondThrow;
	 static JTextField thirdThrow;
	 static JToggleButton tglDouble;
	 static JToggleButton tglTriple;
	 static DefaultTableModel statsModel;
	 static JButton btnReset;
	 static JComboBox<Integer> comboPunkte;
	 static JComboBox<Integer> comboBoxAnzahlSpieler;
	 static JLabel[] remain = new JLabel[] {new JLabel("0"), new JLabel("0"), new JLabel("0")};
	 static int aktuellerSpieler = 0;
	 @SuppressWarnings("unchecked")
	static List<Integer>[] einzelW = new List[] {new ArrayList<Integer>(),new ArrayList<Integer>(),new ArrayList<Integer>()};
	 @SuppressWarnings("unchecked")
	static List<Integer>[] summen = new List[] {new ArrayList<Integer>(),new ArrayList<Integer>(),new ArrayList<Integer>()};
	 static int[] highest = new int[] {0,0,0};

	@SuppressWarnings("serial")
	public static void main(String[] args) {
		//		summen[0] = new ArrayList<Integer>();
		//		summen[1] = new ArrayList<Integer>();
		//		summen[2] = new ArrayList<Integer>();
		JFrame frmDartcounterByJurek = new JFrame();
		frmDartcounterByJurek.getContentPane().setForeground(new Color(0, 0, 0));
		frmDartcounterByJurek.setFont(null);
		frmDartcounterByJurek.setForeground(new Color(255, 0, 51));
		frmDartcounterByJurek.setType(Type.POPUP);
		frmDartcounterByJurek.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Jurek\\Pictures\\MSQRD\\IMG_20160422_174101.jpg"));
		frmDartcounterByJurek.getContentPane().setBackground(new Color(153, 204, 255));
		frmDartcounterByJurek.getContentPane().setLayout(null);

		JCheckBox checkBoxDoubleOut = new JCheckBox("Double Out");
		checkBoxDoubleOut.setBounds(131, 46, 100, 23);
		checkBoxDoubleOut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doubleOutEnabled = checkBoxDoubleOut.isSelected();

			}
		});
		frmDartcounterByJurek.getContentPane().add(checkBoxDoubleOut);

		JLabel lblPunktzahl = new JLabel("Punktzahl");
		lblPunktzahl.setBounds(20, 22, 100, 14);
		frmDartcounterByJurek.getContentPane().add(lblPunktzahl);


		comboPunkte = new JComboBox<>();
		comboPunkte.setBounds(20, 47, 100, 20);
		comboPunkte.addItem(501);
		comboPunkte.addItem(301);
		comboPunkte.setSelectedIndex(0);
		comboPunkte.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pointsToPlay = (int)comboPunkte.getSelectedItem();
				System.out.println(pointsToPlay);
				for(int i = 0; i < 3; i++) {
					remain[i].setText(Integer.toString(pointsToPlay));
				}
			}
		});
		frmDartcounterByJurek.getContentPane().add(comboPunkte);


		JSeparator separator = new JSeparator();
		separator.setBounds(0, 0, 0, 0);
		frmDartcounterByJurek.getContentPane().add(separator);

		txtSpieler[0] = new JTextField();
		txtSpieler[0].setBounds(304, 47, 79, 20);
		txtSpieler[0].setEnabled(true);
		txtSpieler[0].setText("Spieler 1");
		frmDartcounterByJurek.getContentPane().add(txtSpieler[0]);
		txtSpieler[0].setColumns(10);

		table = new JTable();
		table.setBounds(0, 0, 0, 0);
		frmDartcounterByJurek.getContentPane().add(table);

		txtSpieler[1] = new JTextField("Spieler 2");
		txtSpieler[1].setBounds(393, 47, 86, 20);
		txtSpieler[1].setEnabled(false);
		frmDartcounterByJurek.getContentPane().add(txtSpieler[1]);
		txtSpieler[1].setColumns(10);

		txtSpieler[2] = new JTextField("Spieler 3");
		txtSpieler[2].setBounds(488, 47, 86, 20);
		txtSpieler[2].setEnabled(false);
		frmDartcounterByJurek.getContentPane().add(txtSpieler[2]);
		txtSpieler[2].setColumns(10);

		JLabel lblAnzahlDerSpieler = new JLabel("Anzahl der Spieler");
		lblAnzahlDerSpieler.setBounds(237, 22, 146, 14);
		frmDartcounterByJurek.getContentPane().add(lblAnzahlDerSpieler);

		comboBoxAnzahlSpieler = new JComboBox<Integer>();
		comboBoxAnzahlSpieler.setBounds(237, 47, 40, 20);
		comboBoxAnzahlSpieler.addItem(1);
		comboBoxAnzahlSpieler.addItem(2);
		comboBoxAnzahlSpieler.addItem(3);
		comboBoxAnzahlSpieler.setSelectedIndex(0);
		comboBoxAnzahlSpieler.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				switch(comboBoxAnzahlSpieler.getSelectedIndex()+1) {
				case 1:
					txtSpieler[0].setEnabled(true);
					txtSpieler[1].setEnabled(false);
					txtSpieler[2].setEnabled(false);
					anzahlSpieler = 1;

					break;
				case 2:
					txtSpieler[0].setEnabled(true);
					txtSpieler[1].setEnabled(true);
					txtSpieler[2].setEnabled(false);
					anzahlSpieler = 2;

					break;
				case 3:
					txtSpieler[0].setEnabled(true);
					txtSpieler[1].setEnabled(true);
					txtSpieler[2].setEnabled(true);
					anzahlSpieler = 3;

					break;
				}
			}
		});


		frmDartcounterByJurek.getContentPane().add(comboBoxAnzahlSpieler);

		statsTable = new JTable();
		statsTable.setEnabled(false);
		statsTable.setModel(new DefaultTableModel(
				new Object[][] {
					{null, null, null},
					{null, null, null},
					{null, null, null},
					{null, null, null},
				},
				new String[] {
						"Spieler 1", "Spieler 2", "Spieler 3"
				}
				) {
			Class[] columnTypes = new Class[] {
					Integer.class, Integer.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});

		statsModel = (DefaultTableModel) statsTable.getModel();
		statsTable.setBounds(304, 114, 270, 64);
		frmDartcounterByJurek.getContentPane().add(statsTable);

		JLabel lblLast = new JLabel("Last");
		lblLast.setBounds(237, 115, 57, 14);
		frmDartcounterByJurek.getContentPane().add(lblLast);

		JLabel lblAverage = new JLabel("Average");
		lblAverage.setBounds(237, 130, 57, 14);
		frmDartcounterByJurek.getContentPane().add(lblAverage);

		JLabel lblHighest = new JLabel("Highest");
		lblHighest.setBounds(237, 147, 57, 14);
		frmDartcounterByJurek.getContentPane().add(lblHighest);

		JLabel label = new JLabel("");
		label.setBounds(247, 99, 46, 14);
		frmDartcounterByJurek.getContentPane().add(label);

		firstThrow = new JTextField();
		firstThrow.setBounds(50, 218, 86, 20);
		frmDartcounterByJurek.getContentPane().add(firstThrow);
		firstThrow.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if(!(firstThrow.getText().contains("Double") ||firstThrow.getText().contains("Triple"))) {
					if(tglDouble.isSelected()) {
						firstThrow.setText("Double " + firstThrow.getText());
					}
					if(tglTriple.isSelected()) {
						firstThrow.setText("Triple " + firstThrow.getText());
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
		firstThrow.setColumns(10);

		secondThrow = new JTextField();
		secondThrow.setBounds(200, 218, 86, 20);
		frmDartcounterByJurek.getContentPane().add(secondThrow);
		secondThrow.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if(!(secondThrow.getText().contains("Double") ||secondThrow.getText().contains("Triple"))) {
					if(tglDouble.isSelected()) {
						secondThrow.setText("Double " + secondThrow.getText());
					}
					if(tglTriple.isSelected()) {
						secondThrow.setText("Triple " + secondThrow.getText());
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
		secondThrow.setColumns(10);

		thirdThrow = new JTextField();
		thirdThrow.setBounds(350, 218, 86, 20);
		frmDartcounterByJurek.getContentPane().add(thirdThrow);
		thirdThrow.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if(!(thirdThrow.getText().contains("Double") || thirdThrow.getText().contains("Triple"))) {
					if(tglDouble.isSelected()) {
						thirdThrow.setText("Double " + thirdThrow.getText());
					}
					if(tglTriple.isSelected()) {
						thirdThrow.setText("Triple " + thirdThrow.getText());
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
		thirdThrow.setColumns(10);

		tglDouble = new JToggleButton("Double");
		tglDouble.setBounds(110, 255, 121, 23);
		tglDouble.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tglTriple.setSelected(false);

			}
		});
		frmDartcounterByJurek.getContentPane().add(tglDouble);

		tglTriple = new JToggleButton("Triple");
		tglTriple.setBounds(262, 255, 121, 23);
		tglTriple.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tglDouble.setSelected(false);

			}
		});
		frmDartcounterByJurek.getContentPane().add(tglTriple);
		JLabel lblanzWurfe = new JLabel("Wuerfe");
		lblanzWurfe.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblanzWurfe.setBounds(237, 164, 46, 14);
		frmDartcounterByJurek.getContentPane().add(lblanzWurfe);


		remain[0].setBounds(314, 78, 46, 14);
		frmDartcounterByJurek.getContentPane().add(remain[0]);


		remain[1].setBounds(403, 78, 46, 14);
		frmDartcounterByJurek.getContentPane().add(remain[1]);


		remain[2].setBounds(498, 78, 46, 14);
		frmDartcounterByJurek.getContentPane().add(remain[2]);
		frmDartcounterByJurek.setVisible(true);
		frmDartcounterByJurek.setSize(600, 400);

		JButton saveButton = new JButton("Speichern");
		saveButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean ueberworfen = false;
				int average = 0;
				int[] werte;
				//nachdem ein ergebnis eingetragen ist, dann kann die spieleranz/punkteanz nicht mehr geändert werden.
				comboBoxAnzahlSpieler.setEnabled(false);
				comboPunkte.setEnabled(false);

				//deklarations
				
				//System.out.println("Aktueller Spieler "+aktuellerSpieler+" summe: "+summe +" highest "+highest[aktuellerSpieler]);
				werte = DartLogic.tfToSumme();
				
				

				//werte zu den listen adden
				einzelW[aktuellerSpieler].add(werte[0]);
				einzelW[aktuellerSpieler].add(werte[1]);
				einzelW[aktuellerSpieler].add(werte[2]);
				summen[aktuellerSpieler].add(werte[3]);
				
				ueberworfen = DartLogic.checkUeberworfen(summen[aktuellerSpieler]);
				System.out.println(ueberworfen);

				
				pointsPlayed[aktuellerSpieler] += werte[3];
				
				remain[aktuellerSpieler].setText(Integer.toString(pointsToPlay - pointsPlayed[aktuellerSpieler]));
				//average ausrechen
				for(Object wert : summen[aktuellerSpieler]) {
					average += (int)wert;
				}
				average /= summen[aktuellerSpieler].size();
				
				//highest berechnen
				if(highest[aktuellerSpieler] < werte[3]) {
					highest[aktuellerSpieler] = werte[3];
				}

				//werte zu JTable einfügen.
				DartLogic.addToTable(werte[3], average);


				//spieler wechseln
				aktuellerSpieler++;
				if(aktuellerSpieler == anzahlSpieler) {
					aktuellerSpieler = 0;
				}
			}
		});
		saveButton.setBounds(461, 217, 113, 23);
		frmDartcounterByJurek.getContentPane().add(saveButton);

		btnReset = new JButton("RESET");
		btnReset.setBounds(10, 327, 89, 23);
		frmDartcounterByJurek.getContentPane().add(btnReset);
		btnReset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DartLogic.reset();
			}
		});


		frmDartcounterByJurek.setTitle("DartCounter by Jurek Jesse");
		frmDartcounterByJurek.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}
