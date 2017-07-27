package main;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;

import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;

import generateurCoteVerriere.modeles.mecanique.Conf;
import generateurCoteVerriere.modeles.mecanique.Mecanique;
import generateurCoteVerriere.modeles.mecanique.elements.AttachesTraverseCorniere;
import generateurCoteVerriere.modeles.mecanique.elements.ContreCadreMontantCorniere;
import generateurCoteVerriere.modeles.mecanique.elements.ContreCadreMontantPartition;
import generateurCoteVerriere.modeles.mecanique.elements.ContreCadreTraverseCorniere;
import generateurCoteVerriere.modeles.mecanique.elements.MontantCorniere;
import generateurCoteVerriere.modeles.mecanique.elements.MontantPartition;
import generateurCoteVerriere.modeles.mecanique.elements.TraverseCorniere;

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JFileChooser;
import java.text.Format;

public class Main {

	private JFrame frame;
	private JLabel lblPath;
	private Path savePath;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Mod\u00E8le :");
		lblNewLabel.setBounds(12, 12, 55, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JComboBox cbBoxModele = new JComboBox();
		cbBoxModele.setModel(new DefaultComboBoxModel(new String[] {"M\u00E9canique"}));
		cbBoxModele.setBounds(85, 8, 478, 25);
		frame.getContentPane().add(cbBoxModele);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 49, 551, 16);
		frame.getContentPane().add(separator);
		
		JPanel Mécanique = new JPanel();
		Mécanique.setBounds(0, 66, 587, 251);
		frame.getContentPane().add(Mécanique);
		Mécanique.setLayout(null);
		
		JLabel lblARC = new JLabel("ARC :");
		lblARC.setToolTipText("Accus\u00E9 de r\u00E9ception de commance");
		lblARC.setBounds(30, 42, 202, 16);
		Mécanique.add(lblARC);
		
		final JFormattedTextField textFieldARC = new JFormattedTextField((Format) null);
		textFieldARC.setBounds(246, 40, 306, 20);
		Mécanique.add(textFieldARC);
		
		JLabel lblClient = new JLabel("Client :");
		lblClient.setToolTipText("Accus\u00E9 de r\u00E9ception de commance");
		lblClient.setBounds(30, 72, 202, 16);
		Mécanique.add(lblClient);
		
		final JFormattedTextField textFieldClient = new JFormattedTextField((Format) null);
		textFieldClient.setBounds(246, 70, 306, 20);
		Mécanique.add(textFieldClient);
		
		JLabel lblReference = new JLabel("R\u00E9f\u00E9rence :");
		lblReference.setToolTipText("Accus\u00E9 de r\u00E9ception de commance");
		lblReference.setBounds(30, 102, 202, 16);
		Mécanique.add(lblReference);
		
		final JFormattedTextField textFieldReference = new JFormattedTextField((Format) null);
		textFieldReference.setBounds(246, 100, 306, 20);
		Mécanique.add(textFieldReference);
		
		JLabel lblLargeurDeLa = new JLabel("Largeur de la verri\u00E8re (en mm) :");
		lblLargeurDeLa.setBounds(30, 132, 202, 16);
		Mécanique.add(lblLargeurDeLa);

		DecimalFormat myFormatter = new DecimalFormat("#");
		
		final JFormattedTextField textFieldLargeurVerriere = new JFormattedTextField(myFormatter);
		textFieldLargeurVerriere.setText("0");
		textFieldLargeurVerriere.setBounds(246, 130, 306, 20);
		Mécanique.add(textFieldLargeurVerriere);
		
		JLabel lblHauteurDeLa = new JLabel("Hauteur de la verri\u00E8re (en mm) :");
		lblHauteurDeLa.setBounds(30, 162, 202, 16);
		Mécanique.add(lblHauteurDeLa);
		
		final JFormattedTextField textFieldHauteurVerriere = new JFormattedTextField(myFormatter);
		textFieldHauteurVerriere.setText("0");
		textFieldHauteurVerriere.setBounds(246, 160, 306, 20);
		Mécanique.add(textFieldHauteurVerriere);
		
		JLabel lblNombreDePartitions = new JLabel("Nombre de partitions :");
		lblNombreDePartitions.setBounds(30, 190, 202, 16);
		Mécanique.add(lblNombreDePartitions);
		
		final JFormattedTextField textFieldNbPartitions = new JFormattedTextField(myFormatter);
		textFieldNbPartitions.setText("0");
		textFieldNbPartitions.setBounds(246, 188, 306, 20);
		Mécanique.add(textFieldNbPartitions);
		
		JButton btnGenerer = new JButton("Générer");
		btnGenerer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String ARC = textFieldARC.getText();
				String client = textFieldClient.getText();
				String reference = textFieldReference.getText();
				double largeurVerriere = Double.parseDouble(textFieldLargeurVerriere.getText());
				double hauteurVerriere = Double.parseDouble(textFieldHauteurVerriere.getText());
				int nbPartitions = Integer.parseInt(textFieldNbPartitions.getText());
				
				// Conf conf = new Conf(savePath, ARC, client, reference, hauteurVerriere, largeurVerriere, nbPartitions);

				new Mecanique(ARC, client, reference, hauteurVerriere, largeurVerriere, nbPartitions).generate(savePath);

				JOptionPane.showMessageDialog(frame, "Opération terminée avec succès !");
				//JOptionPane.showMessageDialog(frame, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
				
			}
		});
		btnGenerer.setMnemonic(KeyEvent.VK_G);
		btnGenerer.setBounds(477, 329, 98, 26);
		frame.getContentPane().add(btnGenerer);
		
		lblPath = new JLabel("...");
		lblPath.setBounds(12, 334, 337, 16);
		frame.getContentPane().add(lblPath);

    	savePath = Paths.get("").toAbsolutePath();
    	lblPath.setText(savePath.toString());
		
		JButton btnParcourir = new JButton("Parcourir");
		btnParcourir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JFileChooser chooser = new JFileChooser(); 
			    chooser.setCurrentDirectory(new java.io.File("."));
			    chooser.setDialogTitle("Dossier de réception des images");
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    chooser.setAcceptAllFileFilterUsed(false);
			    
			    if (chooser.showOpenDialog(Main.this.frame) == JFileChooser.APPROVE_OPTION){
			    	savePath = chooser.getSelectedFile().toPath();
			    	lblPath.setText(chooser.getSelectedFile().getPath());
			    }
			}
		});
		btnParcourir.setBounds(367, 329, 98, 26);
		frame.getContentPane().add(btnParcourir);
		frame.setBounds(100, 100, 593, 402);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
