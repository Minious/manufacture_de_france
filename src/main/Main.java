package main;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;

import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.text.JTextComponent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import generateurCoteVerriere.Renderer;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import utils.SpringUtilities;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

public class Main {

	private JFrame frame;
	//private JLabel lblPath;
	private Path savePath;
	private HashMap<String, HashMap<String, MyField>> fields;
	
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

	private JSONObject getJSONModeles() throws IOException, JSONException {
		String jsonFilePath = "modeles.json";
		Charset JSONCharset = Charset.forName("UTF-8");
		List<String> lines = Files.readAllLines(Paths.get(jsonFilePath), JSONCharset);
		String json = String.join("\n", lines);
		JSONObject modeles = new JSONObject(json).getJSONObject("modeles");
		return modeles;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		
		try {
			JSONObject modelesJSON = getJSONModeles();
			final String[] modeles = JSONObject.getNames(modelesJSON);
			
			this.fields = new HashMap<String, HashMap<String, MyField>>();

			// MIDDLE
			JPanel cards = new JPanel(new CardLayout());
			
			for (String modele : modeles) {
				JPanel curCard = new JPanel(new SpringLayout());
				HashMap<String, MyField> curFields = new HashMap<String, MyField>();

				JSONArray curModeleJSON = modelesJSON.getJSONArray(modele);
				int fieldNumber = curModeleJSON.length();

				for (int i=0;i<curModeleJSON.length();i++) {
					JSONObject curFieldJSON = curModeleJSON.getJSONObject(i);
					
					String displayName = curFieldJSON.getString("displayName");
					JLabel curLabel = new JLabel(displayName+" :", JLabel.TRAILING);
					curCard.add(curLabel);
					
					JTextField curJTextField = null;
					switch(curFieldJSON.getString("type")) {
						case "String":
							curJTextField = new JTextField(30);
							curLabel.setLabelFor(curJTextField);
							curCard.add(curJTextField);
							break;
						case "Double":
							DecimalFormat doubleFormatter = new DecimalFormat("#.###");
							curJTextField = new JFormattedTextField(doubleFormatter);
							curJTextField.setText("0");
							curCard.add(curJTextField);
							break;
						case "Integer":
							DecimalFormat integerFormatter = new DecimalFormat("#");
							curJTextField = new JFormattedTextField(integerFormatter);
							curJTextField.setText("0");
							curCard.add(curJTextField);
							break;
					}
					
					MyField myField = new MyField(curFieldJSON.getString("type"), curJTextField);
					curFields.put(curFieldJSON.getString("name"), myField);
				}
				
				this.fields.put(modele, curFields);

				int margin = 6;
				SpringUtilities.makeCompactGrid(
						curCard, fieldNumber, 2, // rows, cols
						margin, margin, // initX, initY
						margin, margin); // xPad, yPad

				cards.add(curCard, modele);
			}
			
			// TOP
			JPanel comboBoxPane = new JPanel();
			JComboBox<String> cb = new JComboBox<String>(modeles);
			cb.setEditable(false);
			cb.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent evt) {
					CardLayout cl = (CardLayout) (cards.getLayout());
					cl.show(cards, (String) evt.getItem());
				}
			});
			comboBoxPane.add(cb);
			
			// BOTTOM
			JPanel bottomPane = new JPanel(new FlowLayout(FlowLayout.TRAILING));
			bottomPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

			savePath = Paths.get("").toAbsolutePath();
			JLabel lblPath = new JLabel(savePath.toString());
			
			JButton btnParcourir = new JButton("Parcourir");
			btnParcourir.addMouseListener(new MouseAdapter() {
				@Override public void mouseClicked(MouseEvent arg0) {
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new java.io.File("."));
					chooser.setDialogTitle("Dossier de réception des images");
					chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					chooser.setAcceptAllFileFilterUsed(false);
			
					if (chooser.showOpenDialog(Main.this.frame) == JFileChooser.APPROVE_OPTION) {
						savePath = chooser.getSelectedFile().toPath();
						lblPath.setText(chooser.getSelectedFile().getPath());
					}
				}
			});
			
			JButton btnGenerer = new JButton("Générer");
			btnGenerer.addMouseListener(new MouseAdapter() {
				@Override public void mouseClicked(MouseEvent arg0) {
					String curModele = (String) cb.getSelectedItem();
					
					HashMap<String, MyField> selectedModelFields = fields.get(curModele);
					HashMap<String, Object> renderValues = new HashMap<String, Object>();
					
					for(String key : selectedModelFields.keySet()) {
						String type = selectedModelFields.get(key).getType();
						String fieldTextValue = selectedModelFields.get(key).getField().getText();
						Object value = null;
						switch(type) {
							case("String"):
								value = fieldTextValue;
								break;
							case("Double"):
								value = Double.parseDouble(fieldTextValue);
								break;
							case("Integer"):
								value = Integer.parseInt(fieldTextValue);
								break;
						}
						renderValues.put(key, value);
					}
					
					Renderer.render(curModele, savePath, renderValues);
					
					JOptionPane.showMessageDialog(frame, "Opération terminée avec succès !");
				}
			});
			btnGenerer.setMnemonic(KeyEvent.VK_G);

			bottomPane.add(lblPath);
			bottomPane.add(btnParcourir);
			bottomPane.add(btnGenerer);
			
			// FIN
			frame.getContentPane().setLayout(new BorderLayout());
			
			frame.getContentPane().add(comboBoxPane, BorderLayout.PAGE_START);
			frame.getContentPane().add(cards, BorderLayout.CENTER);
			frame.getContentPane().add(bottomPane, BorderLayout.PAGE_END);
			
			frame.setResizable(false);
			frame.setLocation(300, 200);
			frame.pack();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * int marginSides = 12; int labelWidth = 60; int marginInter = 10; int
		 * inputsWidth = 480; int rowHeight = 24; int rowMargin = 10;
		 * 
		 * int rowIndex = 0;
		 * 
		 * frame = new JFrame(); frame.setResizable(false);
		 * frame.getContentPane().setBackground(Color.WHITE);
		 * frame.getContentPane().setLayout(null);
		 * 
		 * try { JSONObject modelesJSONObject = getJSONModeles(); String[] modeles =
		 * JSONObject.getNames(modelesJSONObject);
		 * System.out.println(Arrays.asList(modeles));
		 * 
		 * JLabel lblNewLabel = new JLabel("Modèle :");
		 * lblNewLabel.setBounds(marginSides, rowMargin, labelWidth, rowHeight);
		 * frame.getContentPane().add(lblNewLabel);
		 * 
		 * JComboBox<String> cbBoxModele = new JComboBox<String>();
		 * cbBoxModele.setModel(new DefaultComboBoxModel<String>(modeles));
		 * cbBoxModele.setBounds(marginSides + labelWidth + marginInter, rowMargin,
		 * inputsWidth, rowHeight); frame.getContentPane().add(cbBoxModele);
		 * 
		 * JSeparator separator = new JSeparator(); separator.setBounds(marginSides,
		 * rowMargin + rowHeight + rowMargin, labelWidth + marginInter + inputsWidth,
		 * rowHeight); frame.getContentPane().add(separator);
		 * 
		 * JPanel formulaireModele = new JPanel(); formulaireModele.setBounds(0,
		 * rowMargin + 2 * (rowHeight + rowMargin), marginSides + labelWidth +
		 * marginInter + inputsWidth + marginSides, 200);
		 * frame.getContentPane().add(formulaireModele);
		 * //formulaireModele.setLayout(null);
		 * 
		 * } catch (JSONException | IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		/*
		 * JLabel lblNewLabel = new JLabel("Modèle :"); lblNewLabel.setBounds(12, 12,
		 * 55, 16); frame.getContentPane().add(lblNewLabel);
		 * 
		 * JComboBox cbBoxModele = new JComboBox(); cbBoxModele.setModel(new
		 * DefaultComboBoxModel(new String[] {"Mécanique", "Premium"}));
		 * cbBoxModele.setBounds(85, 8, 478, 25);
		 * frame.getContentPane().add(cbBoxModele);
		 * 
		 * JSeparator separator = new JSeparator(); separator.setBounds(12, 49, 551,
		 * 16); frame.getContentPane().add(separator);
		 * 
		 * JPanel Mécanique = new JPanel(); Mécanique.setBounds(0, 66, 587, 251);
		 * frame.getContentPane().add(Mécanique); Mécanique.setLayout(null);
		 * 
		 * JLabel lblARC = new JLabel("ARC :");
		 * lblARC.setToolTipText("Accusé de réception de commande");
		 * lblARC.setBounds(30, 42, 202, 16); Mécanique.add(lblARC);
		 * 
		 * final JFormattedTextField textFieldARC = new JFormattedTextField((Format)
		 * null); textFieldARC.setBounds(246, 40, 306, 20); Mécanique.add(textFieldARC);
		 * 
		 * JLabel lblClient = new JLabel("Client :"); lblClient.setBounds(30, 72, 202,
		 * 16); Mécanique.add(lblClient);
		 * 
		 * final JFormattedTextField textFieldClient = new JFormattedTextField((Format)
		 * null); textFieldClient.setBounds(246, 70, 306, 20);
		 * Mécanique.add(textFieldClient);
		 * 
		 * JLabel lblReference = new JLabel("Référence :"); lblReference.setBounds(30,
		 * 102, 202, 16); Mécanique.add(lblReference);
		 * 
		 * final JFormattedTextField textFieldReference = new
		 * JFormattedTextField((Format) null); textFieldReference.setBounds(246, 100,
		 * 306, 20); Mécanique.add(textFieldReference);
		 * 
		 * JLabel lblLargeurDeLa = new JLabel("Largeur de la verrière (en mm) :");
		 * lblLargeurDeLa.setBounds(30, 132, 202, 16); Mécanique.add(lblLargeurDeLa);
		 * 
		 * DecimalFormat myFormatter = new DecimalFormat("#");
		 * 
		 * final JFormattedTextField textFieldLargeurVerriere = new
		 * JFormattedTextField(myFormatter); textFieldLargeurVerriere.setText("0");
		 * textFieldLargeurVerriere.setBounds(246, 130, 306, 20);
		 * Mécanique.add(textFieldLargeurVerriere);
		 * 
		 * JLabel lblHauteurDeLa = new JLabel("Hauteur de la verrière (en mm) :");
		 * lblHauteurDeLa.setBounds(30, 162, 202, 16); Mécanique.add(lblHauteurDeLa);
		 * 
		 * final JFormattedTextField textFieldHauteurVerriere = new
		 * JFormattedTextField(myFormatter); textFieldHauteurVerriere.setText("0");
		 * textFieldHauteurVerriere.setBounds(246, 160, 306, 20);
		 * Mécanique.add(textFieldHauteurVerriere);
		 * 
		 * JLabel lblNombreDePartitions = new JLabel("Nombre de partitions :");
		 * lblNombreDePartitions.setBounds(30, 190, 202, 16);
		 * Mécanique.add(lblNombreDePartitions);
		 * 
		 * final JFormattedTextField textFieldNbPartitions = new
		 * JFormattedTextField(myFormatter); textFieldNbPartitions.setText("0");
		 * textFieldNbPartitions.setBounds(246, 188, 306, 20);
		 * Mécanique.add(textFieldNbPartitions);
		 * 
		 * JButton btnGenerer = new JButton("Générer"); btnGenerer.addMouseListener(new
		 * MouseAdapter() {
		 * 
		 * @Override public void mouseClicked(MouseEvent arg0) { String ARC =
		 * textFieldARC.getText(); String client = textFieldClient.getText(); String
		 * reference = textFieldReference.getText(); double largeurVerriere =
		 * Double.parseDouble(textFieldLargeurVerriere.getText()); double
		 * hauteurVerriere = Double.parseDouble(textFieldHauteurVerriere.getText()); int
		 * nbPartitions = Integer.parseInt(textFieldNbPartitions.getText());
		 * 
		 * // Conf conf = new Conf(savePath, ARC, client, reference, hauteurVerriere,
		 * largeurVerriere, nbPartitions);
		 * 
		 * if((cbBoxModele.getSelectedItem()).equals("Mécanique")) new Mecanique(ARC,
		 * client, reference, hauteurVerriere, largeurVerriere,
		 * nbPartitions).generate(savePath);
		 * if((cbBoxModele.getSelectedItem()).equals("Mecanica")) new Premium(ARC,
		 * client, reference, hauteurVerriere, largeurVerriere,
		 * nbPartitions).generate(savePath);
		 * 
		 * JOptionPane.showMessageDialog(frame, "Opération terminée avec succès !");
		 * //JOptionPane.showMessageDialog(frame, e.getMessage(), "Erreur",
		 * JOptionPane.ERROR_MESSAGE);
		 * 
		 * } }); btnGenerer.setMnemonic(KeyEvent.VK_G); btnGenerer.setBounds(477, 329,
		 * 98, 26); frame.getContentPane().add(btnGenerer);
		 * 
		 * lblPath = new JLabel("..."); lblPath.setBounds(12, 334, 337, 16);
		 * frame.getContentPane().add(lblPath);
		 * 
		 * savePath = Paths.get("").toAbsolutePath();
		 * lblPath.setText(savePath.toString());
		 * 
		 * JButton btnParcourir = new JButton("Parcourir");
		 * btnParcourir.addMouseListener(new MouseAdapter() {
		 * 
		 * @Override public void mouseClicked(MouseEvent arg0) { JFileChooser chooser =
		 * new JFileChooser(); chooser.setCurrentDirectory(new java.io.File("."));
		 * chooser.setDialogTitle("Dossier de réception des images");
		 * chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		 * chooser.setAcceptAllFileFilterUsed(false);
		 * 
		 * if (chooser.showOpenDialog(Main.this.frame) == JFileChooser.APPROVE_OPTION){
		 * savePath = chooser.getSelectedFile().toPath();
		 * lblPath.setText(chooser.getSelectedFile().getPath()); } } });
		 * btnParcourir.setBounds(367, 329, 98, 26);
		 * frame.getContentPane().add(btnParcourir);
		 */

		// frame.setBounds(150, 150, marginSides + labelWidth + marginInter +
		// inputsWidth + marginSides, 402);
		//frame.setBounds(100, 100, 593, 402);
		/*
		frame.setResizable(false);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		*/
	}
	
	class MyField {
		private String type;
		private JTextField field;
		
		public MyField(String type, JTextField field) {
			this.type = type;
			this.field = field;
		}

		public String getType() {
			return this.type;
		}

		public JTextField getField() {
			return this.field;
		}
		
	}
}
