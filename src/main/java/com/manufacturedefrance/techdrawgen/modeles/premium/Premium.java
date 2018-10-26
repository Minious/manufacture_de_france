package com.manufacturedefrance.techdrawgen.modeles.premium;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.manufacturedefrance.conf.TextFileConf;
import com.manufacturedefrance.conf.UnprocessableConfFileException;
import com.manufacturedefrance.techdrawgen.LignesTexte;
import com.manufacturedefrance.techdrawgen.ModeleGenerique;
import com.manufacturedefrance.techdrawgen.modeles.premium.elements.MontantCorniere;
import com.manufacturedefrance.techdrawgen.modeles.premium.elements.MontantIntermediaire;
import com.manufacturedefrance.techdrawgen.modeles.premium.elements.TraverseCorniere;
import com.manufacturedefrance.svgen.MyCustomSvg;
import com.manufacturedefrance.svgen.MyHandyLayout;
import com.manufacturedefrance.svgen.Padding;
import com.manufacturedefrance.techdrawgen.MyCustomSvgEnhanced.ShiftMode;

public class Premium extends ModeleGenerique {	
	public Premium(Map<String,Object> data) {
		super(data);

		String fileName = "conf_premium.txt";

		HashMap<String, Double> initialMap = new HashMap<>();
		
		initialMap.put("hauteurVerriere", (Double) data.get("hauteurVerriere"));
		initialMap.put("largeurVerriere", (Double) data.get("largeurVerriere"));
		initialMap.put("nbPartitions", ((Integer) data.get("nbPartitions")).doubleValue());
		initialMap.put("epaisseurVitrage", (Double) data.get("epaisseurVitrage"));

		try {
			this.conf = TextFileConf.loadConf(fileName, initialMap);
		} catch (UnprocessableConfFileException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, e.toString());
		}

		MontantCorniere montantCorniere = new MontantCorniere(this.conf, this.data);
		MontantIntermediaire montantIntermediaire = new MontantIntermediaire(this.conf, this.data);
		TraverseCorniere traverseCorniere = new TraverseCorniere(this.conf, this.data);

		int taillePoliceTitre1 = 16;
		int taillePoliceTitre2 = 14;

		MyCustomSvg corniere30x20x3 = new LignesTexte("CORNIERE 30x20x3", taillePoliceTitre2);
		MyCustomSvg T30x3 = new LignesTexte("T de 30x30x3", taillePoliceTitre2);
		
		MyHandyLayout traverseCorniereLayout = new MyHandyLayout();
		MyCustomSvg traverseCorniereChampSvg = new MyCustomSvg();
		MyCustomSvg traverseCorniereFaceSvg = new MyCustomSvg();
		traverseCorniereChampSvg.rotate(Math.PI / 2);
		traverseCorniereFaceSvg.rotate(Math.PI / 2);
		traverseCorniereChampSvg.drawSvg(traverseCorniere.getDessin(), 0, 0);
		traverseCorniereFaceSvg.drawSvg(traverseCorniere.getDessinFace(), 0, 0);
		traverseCorniereLayout.addRow(traverseCorniereChampSvg, ShiftMode.CENTER);
		traverseCorniereLayout.addRow(traverseCorniereFaceSvg, ShiftMode.CENTER);

		MyCustomSvg montantCorniereSvg = new MyCustomSvg();
		MyCustomSvg montantIntermediaireSvg = new MyCustomSvg();
		MyCustomSvg traverseCorniereSvg = traverseCorniereLayout.getSvg();
		montantCorniereSvg.rotate(Math.PI / 2);
		montantIntermediaireSvg.rotate(Math.PI / 2);
		montantCorniereSvg.drawSvg(montantCorniere.getDessin(), 0, 0);
		montantIntermediaireSvg.drawSvg(montantIntermediaire.getDessin(), 0, 0);
		
		MyCustomSvg traverseCorniereTitre = new LignesTexte("TRAVERSES HAUTE ET BASSE", taillePoliceTitre1);
		MyCustomSvg traverseCorniereQte = new LignesTexte("QTE : "+traverseCorniere.getNbElements(), taillePoliceTitre1);
		MyCustomSvg montantCorniereTitre = new LignesTexte("MT LATERAL", taillePoliceTitre1);
		MyCustomSvg montantCorniereQte = new LignesTexte("QTE : "+montantCorniere.getNbElements(), taillePoliceTitre1);
		MyCustomSvg montantIntermediaireTitre = new LignesTexte("MT INTERMEDIAIRE", taillePoliceTitre1);
		MyCustomSvg montantIntermediaireQte = new LignesTexte("QTE : "+montantIntermediaire.getNbElements(), taillePoliceTitre1);

		MyHandyLayout layoutDessins = new MyHandyLayout();
		layoutDessins.addRow(new MyCustomSvg[] {traverseCorniereTitre, traverseCorniereQte}, ShiftMode.LEFT);
		layoutDessins.addRow(corniere30x20x3, ShiftMode.LEFT);
		layoutDessins.addRow(traverseCorniereSvg, ShiftMode.CENTER);
		layoutDessins.addRow(new MyCustomSvg[] {montantCorniereTitre, montantCorniereQte}, ShiftMode.LEFT);
		layoutDessins.addRow(corniere30x20x3, ShiftMode.LEFT);
		layoutDessins.addRow(montantCorniereSvg, ShiftMode.CENTER);
		if(montantIntermediaire.getNbElements() > 0) {
			layoutDessins.addRow(new MyCustomSvg[] {montantIntermediaireTitre, montantIntermediaireQte}, ShiftMode.LEFT);
			layoutDessins.addRow(T30x3, ShiftMode.LEFT);
			layoutDessins.addRow(montantIntermediaireSvg, ShiftMode.CENTER);
		}
		
		MyCustomSvg dessins = layoutDessins.getSvg();

		DecimalFormat myFormatter = new DecimalFormat("#.##");
		LignesTexte parcloses = new LignesTexte();
		parcloses.addLignes(Arrays.asList(
			"PARCLOSES : " + conf.get("hauteurParcloseCorniere").intValue(),
			"- " + myFormatter.format(conf.get("longueurParcloseTraverseLaterale")) + " QTE " + conf.get("nbParcloseTraverseLaterale").intValue(),
			"- " + myFormatter.format(conf.get("longueurParcloseMontant")) + " QTE " + conf.get("nbParcloseMontantCorniere").intValue()
		));
		if(conf.get("nbParcloseTraverseCentrale") > 0)
			parcloses.addLigne("- " + myFormatter.format(conf.get("longueurParcloseTraverseCentrale")) + " QTE " + conf.get("nbParcloseTraverseCentrale").intValue());
		if(conf.get("nbParcloseMontantIntermediaire") > 0)
			parcloses.addLignes(Arrays.asList(
				"PARCLOSES : " + conf.get("hauteurParcloseT").intValue(),
				"- " + myFormatter.format(conf.get("longueurParcloseMontant")) + " QTE " + conf.get("nbParcloseMontantIntermediaire").intValue()
			));

		MyCustomSvg vitrages = new LignesTexte(Arrays.asList(
			"VITRAGE :",
				myFormatter.format(conf.get("largeurVitrage")) + " x " + myFormatter.format(conf.get("hauteurVitrage")) + " QTE " + conf.get("nbVitrage").intValue()
		));
		
		MyHandyLayout layoutCartoucheGauche = new MyHandyLayout();
		layoutCartoucheGauche.addRow(new MyCustomSvg[] {parcloses, vitrages}, ShiftMode.CENTER);
		MyCustomSvg cartoucheGauche = layoutCartoucheGauche.getSvg();
		cartoucheGauche.setPadding(new Padding(10));
		cartoucheGauche.setBorders(true);
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
		Date date = new Date();
		MyCustomSvg cartoucheDroite = new LignesTexte(Arrays.asList(
			"ARC : " + data.get("ARC"),
			"Client : " + data.get("client"),
			"C.M. : " + data.get("reference"),
			"Date : " + dateFormat.format(date),
			"Modèle : Premium",
			"Dimensions : " + conf.get("largeurVerriere") + " LARG x " + conf.get("hauteurVerriere") + " HT",
			"Partitions : " + conf.get("nbPartitions").intValue(),
			"Nature vitrage : " + data.get("epaisseurVitrage") + " " + data.get("natureVitrage"),
			"Finition : " + data.get("finition")
		));
		cartoucheDroite.setPadding(new Padding(10));
		cartoucheDroite.setBorders(true);
		
		MyHandyLayout finalLayout = new MyHandyLayout();
		finalLayout.addRow(dessins, ShiftMode.LEFT);
		finalLayout.addRow(new MyCustomSvg[] {cartoucheGauche, cartoucheDroite}, ShiftMode.LEFT);
		
		MyCustomSvg finalSvg = finalLayout.getSvg();
		finalSvg.setPadding(new Padding(10));
		this.svgToRender.put("complete", finalSvg);
	}
}
