package com.manufacturedefrance.main;

import com.manufacturedefrance.techdrawgen.Renderer;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.manufacturedefrance.utils.SpringUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

	private JFrame frame;
	//private JLabel lblPath;
	private Path savePath;
	private HashMap<String, HashMap<String, MyField>> fields;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			Main window = new Main();
			window.frame.setVisible(true);
		});
	}

	/**
	 * Create the application.
	 */
	private Main() {
		initialize();
	}

	private JSONObject getJSONModeles() throws IOException, JSONException {
		String jsonFileName = "modeles.json";
		String encodage = "UTF-8";

		InputStream stream = Main.class.getResourceAsStream("/" + jsonFileName);
		String json = IOUtils.toString(stream, encodage);

		return new JSONObject(json).getJSONObject("modeles");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Manufacture de France - Générateur de verrière");
		frame.getContentPane().setBackground(Color.WHITE);
		
		try {
			JSONObject modelesJSON = getJSONModeles();
			final String[] modeles = JSONObject.getNames(modelesJSON);
			
			this.fields = new HashMap<>();

			// MIDDLE
			JPanel cards = new JPanel(new CardLayout());
			
			for (String modele : modeles) {
				JPanel curCard = new JPanel(new SpringLayout());
				HashMap<String, MyField> curFields = new HashMap<>();

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
			JComboBox<String> cb = new JComboBox<>(modeles);
			cb.setEditable(false);
			cb.addItemListener((ItemEvent evt) -> {
				CardLayout cl = (CardLayout) (cards.getLayout());
				cl.show(cards, (String) evt.getItem());
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
					HashMap<String, Object> renderValues = new HashMap<>();
					
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
			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		} catch (IOException | JSONException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, e.toString());
		}
	}
	
	class MyField {
		private String type;
		private JTextField field;
		
		MyField(String type, JTextField field) {
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
