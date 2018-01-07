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

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import conf.TextFileConf;
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
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

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
		String jsonFileName = "modeles.json";
		String encodage = "UTF-8";

		String json = IOUtils.toString(TextFileConf.class.getClassLoader().getResourceAsStream(jsonFileName), encodage);

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
