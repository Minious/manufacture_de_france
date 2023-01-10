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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

	private JFrame frame;
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

	private JSONObject getJSONModeles() throws IOException {
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
					
					JComponent curJComponent = null;
					FieldType curType;
					switch(curFieldJSON.getString("type")) {
						case "String":
							JTextField curJTextFieldString = new JTextField(30);
							curLabel.setLabelFor(curJTextFieldString);
							curCard.add(curJTextFieldString);
							curType = FieldType.STRING;
							curJComponent = curJTextFieldString;
							break;
						case "Double":
							DecimalFormat doubleFormatter = new DecimalFormat("#.###");
							JFormattedTextField curJTextFieldDouble = new JFormattedTextField(doubleFormatter);
							curJTextFieldDouble.setText("0");
							curCard.add(curJTextFieldDouble);
							curType = FieldType.DOUBLE;
							curJComponent = curJTextFieldDouble;
							break;
						case "Integer":
							DecimalFormat integerFormatter = new DecimalFormat("#");
							JFormattedTextField curJFormattedTextFieldInteger = new JFormattedTextField(integerFormatter);
							curJFormattedTextFieldInteger.setText("0");
							curCard.add(curJFormattedTextFieldInteger);
							curType = FieldType.INTEGER;
							curJComponent = curJFormattedTextFieldInteger;
							break;
						case "Dropdown":
							Object[] values = curFieldJSON.getJSONArray("values").toList().toArray();
							JComboBox curJComboBoxDropDown = new JComboBox(values);
							curCard.add(curJComboBoxDropDown);
							curType = FieldType.DROPDOWN;
							curJComponent = curJComboBoxDropDown;
							break;
						default:
							throw new JSONException("Invalid modele field");
					}
					
					MyField myField = new MyField(curType, curJComponent);
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
					
					Map<String, MyField> selectedModelFields = fields.get(curModele);
					Map<String, Object> renderValues = new HashMap<>();
					
					for(Map.Entry<String, MyField> entry : selectedModelFields.entrySet()) {
						FieldType type = entry.getValue().getType();
						String fieldTextValue;
						if(entry.getValue().getType() == FieldType.DROPDOWN)
							fieldTextValue = (String) ((JComboBox) entry.getValue().getComponent()).getSelectedItem();
						else
							fieldTextValue = ((JTextField) entry.getValue().getComponent()).getText();
						Object value = null;
						switch(type) {
							case STRING:
								value = fieldTextValue;
								break;
							case DOUBLE:
								value = Double.parseDouble(fieldTextValue);
								break;
                            case INTEGER:
                                value = Integer.parseInt(fieldTextValue);
                                break;
                            case DROPDOWN:
                                value = fieldTextValue;
                                break;
						}
						renderValues.put(entry.getKey(), value);
					}

					frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					try {
						Renderer.render(curModele, savePath, renderValues);
						JOptionPane.showMessageDialog(frame, "Opération terminée avec succès !");
					} catch(Exception e) {
						JOptionPane.showMessageDialog(
								frame,
								"Veuillez contacter le développeur.",
								"Erreur",
								JOptionPane.ERROR_MESSAGE);
					} finally {
						frame.setCursor(Cursor.getDefaultCursor());
					}
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
		private FieldType type;
		private JComponent component;
		
		MyField(FieldType type, JComponent component) {
			this.type = type;
			this.component = component;
		}

		public FieldType getType() {
			return this.type;
		}

		public JComponent getComponent() {
			return this.component;
		}
	}

	private enum FieldType {
		STRING, DOUBLE, INTEGER, DROPDOWN
	}
}
