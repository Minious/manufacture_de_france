package generateurCoteVerriere.modeles.premium;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import conf.TextFileConf;
import conf.UnprocessableConfFileException;
import generateurCoteVerriere.LignesTexte;
import generateurCoteVerriere.ModeleGenerique;
import generateurCoteVerriere.modeles.premium.elements.MontantCorniere;
import generateurCoteVerriere.modeles.premium.elements.MontantIntermediaire;
import generateurCoteVerriere.modeles.premium.elements.TraverseCorniere;
import myCustomSvgLibrary.MyCustomSvg;
import myCustomSvgLibrary.MyHandyLayout;
import myCustomSvgLibrary.Padding;
import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced.ShiftMode;

public class Premium extends ModeleGenerique {	
	public Premium(HashMap<String,Object> data) {
		super(data);

		String fileName = "conf_premium.txt";

		HashMap<String, Double> initialMap = new HashMap<String, Double>();
		
		initialMap.put("hauteurVerriere", (Double) data.get("hauteurVerriere"));
		initialMap.put("largeurVerriere", (Double) data.get("largeurVerriere"));
		initialMap.put("nbPartitions", ((Integer) data.get("nbPartitions")).doubleValue());

		try {
			this.conf = TextFileConf.loadConf(fileName, initialMap);
		} catch (IOException | UnprocessableConfFileException e) {
			e.printStackTrace();
		}

		MontantCorniere montantCorniere = new MontantCorniere(this.conf, this.data);
		MontantIntermediaire montantIntermediaire = new MontantIntermediaire(this.conf, this.data);
		TraverseCorniere traverseCorniere = new TraverseCorniere(this.conf, this.data);

		MyCustomSvg montantCorniereSvg = new MyCustomSvg();
		MyCustomSvg montantIntermediaireSvg = new MyCustomSvg();
		MyCustomSvg traverseCorniereSvg = new MyCustomSvg();
		montantCorniereSvg.rotate(Math.PI / 2);
		montantIntermediaireSvg.rotate(Math.PI / 2);
		traverseCorniereSvg.rotate(Math.PI / 2);
		System.out.println(montantCorniere.getDessinNew().getBounds());
		montantCorniereSvg.drawSvg(montantCorniere.getDessinNew(), 0, 0);
		System.out.println(montantCorniereSvg.getBounds());
		montantIntermediaireSvg.drawSvg(montantIntermediaire.getDessinNew(), 0, 0);
		traverseCorniereSvg.drawSvg(traverseCorniere.getDessinNew(), 0, 0);
		
		MyCustomSvg traverseCorniereTitre = new LignesTexte("TRAVERSES HAUTE ET BASSE");
		MyCustomSvg traverseCorniereQte = new LignesTexte("QTE : "+traverseCorniere.getNbElements());
		MyCustomSvg montantCorniereTitre = new LignesTexte("MT CORNIERE LATERAL");
		MyCustomSvg montantCorniereQte = new LignesTexte("QTE : "+montantCorniere.getNbElements());
		MyCustomSvg montantIntermediaireTitre = new LignesTexte("MT T INTERMEDIAIRE");
		MyCustomSvg montantIntermediaireQte = new LignesTexte("QTE : "+montantIntermediaire.getNbElements());

		MyHandyLayout layoutDessins = new MyHandyLayout();
		layoutDessins.addRow(new MyCustomSvg[] {traverseCorniereTitre, traverseCorniereQte}, ShiftMode.LEFT);
		layoutDessins.addRow(traverseCorniereSvg, ShiftMode.CENTER);
		layoutDessins.addRow(new MyCustomSvg[] {montantCorniereTitre, montantCorniereQte}, ShiftMode.LEFT);
		layoutDessins.addRow(montantCorniereSvg, ShiftMode.CENTER);
		layoutDessins.addRow(new MyCustomSvg[] {montantIntermediaireTitre, montantIntermediaireQte}, ShiftMode.LEFT);
		layoutDessins.addRow(montantIntermediaireSvg, ShiftMode.CENTER);

		MyCustomSvg dessins = layoutDessins.getSvg();

		MyCustomSvg parcloses = new LignesTexte(Arrays.asList(new String[] {
			"PARCLOSES : " + conf.get("hauteurParcloseCorniere").intValue(),
			"- " + conf.get("longueurParcloseTraverseLaterale") + " QTE " + conf.get("nbParcloseTraverseLaterale").intValue(),
			"- " + conf.get("longueurParcloseTraverseCentrale") + " QTE " + conf.get("nbParcloseTraverseCentrale").intValue(),
			"- " + conf.get("longueurParcloseMontant") + " QTE " + conf.get("nbParcloseMontantCorniere").intValue(),
			"PARCLOSES : " + conf.get("hauteurParcloseT").intValue(),
			"- " + conf.get("longueurParcloseMontant") + " QTE " + conf.get("nbParcloseMontantIntermediaire").intValue(),
		}));

		MyCustomSvg vitrages = new LignesTexte(Arrays.asList(new String[] {
			"VITRAGE :",
			conf.get("largeurVitrage") + " x " + conf.get("hauteurVitrage") + " QTE " + conf.get("nbVitrage").intValue()
		}));
		
		MyHandyLayout layoutCartoucheGauche = new MyHandyLayout();
		layoutCartoucheGauche.addRow(new MyCustomSvg[] {parcloses, vitrages}, ShiftMode.CENTER);
		MyCustomSvg cartoucheGauche = layoutCartoucheGauche.getSvg();
		cartoucheGauche.setPadding(new Padding(10));
		cartoucheGauche.setBorders(true);
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
		Date date = new Date();
		MyCustomSvg cartoucheDroite = new LignesTexte(Arrays.asList(new String[] {
			"ARC : " + data.get("ARC"),
			"Client : " + data.get("client"),
			"C.M. : " + data.get("reference"),
			"Date : " + dateFormat.format(date),
			"Modèle : Premium",
			"Dimensions : " + conf.get("largeurVerriere") + " x " + conf.get("hauteurVerriere")
		}));
		cartoucheDroite.setPadding(new Padding(10));
		cartoucheDroite.setBorders(true);
		
		MyHandyLayout finalLayout = new MyHandyLayout();
		finalLayout.addRow(dessins, ShiftMode.LEFT);
		finalLayout.addRow(new MyCustomSvg[] {cartoucheGauche, cartoucheDroite}, ShiftMode.LEFT);
		
		MyCustomSvg finalSvg = finalLayout.getSvg();
		finalSvg.setPadding(new Padding(10));
		this.svgToRender.put("complete", finalSvg);
		finalSvg.writeToSVG(Paths.get("").toAbsolutePath().resolve("test.svg"));
		
		//montantCorniereSvg.writeToSVG(Paths.get("").toAbsolutePath().resolve("test.svg"));
		//montantCorniere.getDessinNew().writeToSVG(Paths.get("").toAbsolutePath().resolve("test.svg"));
	}

	@Override
	protected String[] getElementsClasses() {
		return new String[] {"MontantCorniere", "MontantIntermediaire", "TraverseCorniere"};
	}

	@Override
	protected String getPackage() {
		return "generateurCoteVerriere.modeles.premium.elements";
	}
}
