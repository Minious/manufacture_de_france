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

import com.manufacturedefrance.conf.PremiumConf;
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
import com.manufacturedefrance.svgen.SvgComponent;
import com.manufacturedefrance.techdrawgen.MyCustomSvgEnhanced.ShiftMode;

public class Premium extends ModeleGenerique {
	private PremiumConf conf;

	public Premium(Map<String,Object> data) {
		super(data);

		this.conf = new PremiumConf(
			(double) data.get("hauteurVerriere"),
			(double) data.get("largeurVerriere"),
			(int) data.get("nbPartitions"),
			Double.parseDouble((String) data.get("epaisseurVitrage"))
		);

		MontantCorniere montantCorniere = new MontantCorniere(this.data, this.conf);
		MontantIntermediaire montantIntermediaire = new MontantIntermediaire(this.data, this.conf);
		TraverseCorniere traverseCorniere = new TraverseCorniere(this.data, this.conf);

		int taillePoliceTitre1 = 20;
		int taillePoliceTitre2 = 16;

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
		layoutDessins.addRow(new MyCustomSvg[] {traverseCorniereTitre, traverseCorniereQte}, ShiftMode.CENTER);
		layoutDessins.addRow(corniere30x20x3, ShiftMode.CENTER);
		layoutDessins.addRow(traverseCorniereSvg, ShiftMode.CENTER);
		layoutDessins.addRow(new MyCustomSvg[] {montantCorniereTitre, montantCorniereQte}, ShiftMode.CENTER);
		layoutDessins.addRow(corniere30x20x3, ShiftMode.CENTER);
		layoutDessins.addRow(montantCorniereSvg, ShiftMode.CENTER);
		if(montantIntermediaire.getNbElements() > 0) {
			layoutDessins.addRow(new MyCustomSvg[] {montantIntermediaireTitre, montantIntermediaireQte}, ShiftMode.CENTER);
			layoutDessins.addRow(T30x3, ShiftMode.CENTER);
			layoutDessins.addRow(montantIntermediaireSvg, ShiftMode.CENTER);
		}
		
		MyCustomSvg dessins = layoutDessins.getSvg();

		LignesTexte parcloses = new LignesTexte();
		parcloses.addLignes(Arrays.asList(
			"PARCLOSES DE " + conf.hauteurParcloseCorniere(),
			"- " + SvgComponent.DOUBLE_FORMAT.format(conf.longueurParcloseTraverseLaterale()) + " QTE " + conf.nbParcloseTraverseLaterale(),
			"- " + SvgComponent.DOUBLE_FORMAT.format(conf.longueurParcloseMontant()) + " QTE " + conf.nbParcloseMontantCorniere()
		));
		if(conf.nbParcloseTraverseCentrale() > 0)
			parcloses.addLigne("- " + SvgComponent.DOUBLE_FORMAT.format(conf.longueurParcloseTraverseCentrale()) + " QTE " + conf.nbParcloseTraverseCentrale());
		if(conf.nbParcloseMontantIntermediaire() > 0)
			parcloses.addLignes(Arrays.asList(
				"PARCLOSES DE " + conf.hauteurParcloseT(),
				"- " + SvgComponent.DOUBLE_FORMAT.format(conf.longueurParcloseMontant()) + " QTE " + conf.nbParcloseMontantIntermediaire()
			));

		MyCustomSvg vitrages = new LignesTexte(Arrays.asList(
			"VITRAGE :",
			SvgComponent.DOUBLE_FORMAT.format(conf.hauteurVitrage()) + " x " + SvgComponent.DOUBLE_FORMAT.format(conf.largeurVitrage()) + " QTE " + conf.nbVitrage()
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
			"Dimensions : " + conf.getHauteurVerriere() + " HT x " + conf.getLargeurVerriere() + " LARG",
			"Partitions : " + conf.getNbPartitions(),
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
