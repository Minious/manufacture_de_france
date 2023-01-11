package com.manufacturedefrance.techdrawgen.modeles.mecanique;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.manufacturedefrance.conf.MecaniqueConf;
import com.manufacturedefrance.techdrawgen.LignesTexte;
import com.manufacturedefrance.techdrawgen.ModeleGenerique;
import com.manufacturedefrance.techdrawgen.modeles.mecanique.elements.AttachesTraverseCorniere;
import com.manufacturedefrance.techdrawgen.modeles.mecanique.elements.ContreCadreMontantCorniere;
import com.manufacturedefrance.techdrawgen.modeles.mecanique.elements.ContreCadreMontantPartition;
import com.manufacturedefrance.techdrawgen.modeles.mecanique.elements.ContreCadreTraverseCorniere;
import com.manufacturedefrance.techdrawgen.modeles.mecanique.elements.MontantPartition;
import com.manufacturedefrance.techdrawgen.modeles.mecanique.elements.MontantCorniere;
import com.manufacturedefrance.techdrawgen.modeles.mecanique.elements.TraverseCorniere;
import com.manufacturedefrance.svgen.MyCustomSvg;
import com.manufacturedefrance.svgen.MyHandyLayout;
import com.manufacturedefrance.svgen.Padding;
import com.manufacturedefrance.svgen.SvgComponent;
import com.manufacturedefrance.techdrawgen.MyCustomSvgEnhanced.ShiftMode;
import net.objecthunter.exp4j.function.Function;

public class Mecanique extends ModeleGenerique {
	private MecaniqueConf conf;

	public Mecanique(Map<String, Object> data) {
		super(data);

		this.conf = new MecaniqueConf(
			(double) data.get("hauteurVerriere"),
			(double) data.get("largeurVerriere"),
			(int) data.get("nbPartitions")
		);

		MontantCorniere montantCorniere = new MontantCorniere(this.data, this.conf);
		ContreCadreMontantCorniere contreCadreMontantCorniere = new ContreCadreMontantCorniere(this.data, this.conf);
		MontantPartition montantPartition = new MontantPartition(this.data, this.conf);
		ContreCadreMontantPartition contreCadreMontantPartition = new ContreCadreMontantPartition(this.data, this.conf);
		TraverseCorniere traverseCorniere = new TraverseCorniere(this.data, this.conf);
		ContreCadreTraverseCorniere contreCadreTraverseCorniere = new ContreCadreTraverseCorniere(this.data, this.conf);
		AttachesTraverseCorniere attachesTraverseCorniere = new AttachesTraverseCorniere(this.data, this.conf);

		int taillePoliceTitre1 = 20;
		int taillePoliceTitre2 = 16;

		MyCustomSvg corniere30x30x3 = new LignesTexte("CORNIERE 30x30x3", taillePoliceTitre2);
		MyCustomSvg plat30x3 = new LignesTexte("PLAT 30x3", taillePoliceTitre2);
		MyCustomSvg plat25x3 = new LignesTexte("PLAT 25x3", taillePoliceTitre2);
		
		// Montant corniere
		MyHandyLayout montantCorniereLayout = new MyHandyLayout();
		MyHandyLayout contreCadreMontantCorniereLayout = new MyHandyLayout();
		MyCustomSvg montantCorniereSvg = new MyCustomSvg();
		MyCustomSvg contreCadreMontantCorniereSvg = new MyCustomSvg();
		
		montantCorniereSvg.rotate(Math.PI / 2);
		contreCadreMontantCorniereSvg.rotate(Math.PI / 2);
		
		montantCorniereSvg.drawSvg(montantCorniere.getDessin(), 0, 0);
		contreCadreMontantCorniereSvg.drawSvg(contreCadreMontantCorniere.getDessin(), 0, 0);

		montantCorniereLayout.addRow(corniere30x30x3, ShiftMode.CENTER);
		montantCorniereLayout.addRow(montantCorniereSvg, ShiftMode.CENTER);
		contreCadreMontantCorniereLayout.addRow(plat25x3, ShiftMode.CENTER);
		contreCadreMontantCorniereLayout.addRow(contreCadreMontantCorniereSvg, ShiftMode.CENTER);
		
		// Montant partition
		MyHandyLayout montantPartitionLayout = new MyHandyLayout();
		MyHandyLayout contreCadreMontantPartitionLayout = new MyHandyLayout();
		MyCustomSvg montantPartitionSvg = new MyCustomSvg();
		MyCustomSvg contreCadreMontantPartitionSvg = new MyCustomSvg();
		
		montantPartitionSvg.rotate(Math.PI / 2);
		contreCadreMontantPartitionSvg.rotate(Math.PI / 2);
		
		montantPartitionSvg.drawSvg(montantPartition.getDessin(), 0, 0);
		contreCadreMontantPartitionSvg.drawSvg(contreCadreMontantPartition.getDessin(), 0, 0);

		montantPartitionLayout.addRow(plat30x3, ShiftMode.CENTER);
		montantPartitionLayout.addRow(montantPartitionSvg, ShiftMode.CENTER);
		contreCadreMontantPartitionLayout.addRow(plat30x3, ShiftMode.CENTER);
		contreCadreMontantPartitionLayout.addRow(contreCadreMontantPartitionSvg, ShiftMode.CENTER);
		
		// Traverse corniere
		MyHandyLayout traverseCorniereLayout = new MyHandyLayout();
		MyHandyLayout contreCadreTraverseCorniereLayout = new MyHandyLayout();
		MyCustomSvg traverseCorniereSvg = new MyCustomSvg();
		MyCustomSvg contreCadreTraverseCorniereSvg = new MyCustomSvg();
		MyCustomSvg attachesTraverseCorniereSvg = new MyCustomSvg();
		
		traverseCorniereSvg.rotate(Math.PI / 2);
		contreCadreTraverseCorniereSvg.rotate(Math.PI / 2);
		attachesTraverseCorniereSvg.rotate(Math.PI / 2);
		
		traverseCorniereSvg.drawSvg(traverseCorniere.getDessin(), 0, 0);
		contreCadreTraverseCorniereSvg.drawSvg(contreCadreTraverseCorniere.getDessin(), 0, 0);
		attachesTraverseCorniereSvg.drawSvg(attachesTraverseCorniere.getDessin(), 0, 0);

		traverseCorniereLayout.addRow(corniere30x30x3, ShiftMode.CENTER);
		traverseCorniereLayout.addRow(traverseCorniereSvg, ShiftMode.CENTER);
		traverseCorniereLayout.addRow(attachesTraverseCorniereSvg, ShiftMode.CENTER);
		contreCadreTraverseCorniereLayout.addRow(plat25x3, ShiftMode.CENTER);
		contreCadreTraverseCorniereLayout.addRow(contreCadreTraverseCorniereSvg, ShiftMode.CENTER);

		// Final		
		MyCustomSvg traverseCorniereTitre = new LignesTexte("TRAVERSES HAUTE ET BASSE", taillePoliceTitre1);
		MyCustomSvg contreCadreTraverseCorniereTitre = new LignesTexte("CONTRE-CADRE TRAVERSES", taillePoliceTitre1);
		MyCustomSvg traverseCorniereQte = new LignesTexte("QTE : "+traverseCorniere.getNbElements(), taillePoliceTitre1);
		MyCustomSvg montantCorniereTitre = new LignesTexte("MT CORNIERE LATERAL", taillePoliceTitre1);
		MyCustomSvg contreCadreMontantCorniereTitre = new LignesTexte("CONTRE-CADRE MT LATERAL", taillePoliceTitre1);
		MyCustomSvg montantCorniereQte = new LignesTexte("QTE : "+montantCorniere.getNbElements(), taillePoliceTitre1);
		MyCustomSvg montantPartitionTitre = new LignesTexte("MT INTERMEDIAIRE", taillePoliceTitre1);
		MyCustomSvg contreCadreMontantPartitionTitre = new LignesTexte("CONTRE-CADRE MT INTERMEDIAIRE", taillePoliceTitre1);
		MyCustomSvg montantPartitionQte = new LignesTexte("QTE : "+montantPartition.getNbElements(), taillePoliceTitre1);

		MyHandyLayout layoutDessins = new MyHandyLayout();
		layoutDessins.addRow(new MyCustomSvg[] {traverseCorniereTitre, traverseCorniereQte}, ShiftMode.CENTER);
		layoutDessins.addRow(traverseCorniereLayout.getSvg(), ShiftMode.CENTER);
		layoutDessins.addRow(new MyCustomSvg[] {contreCadreTraverseCorniereTitre, traverseCorniereQte}, ShiftMode.CENTER);
		layoutDessins.addRow(contreCadreTraverseCorniereLayout.getSvg(), ShiftMode.CENTER);
		if(montantPartition.getNbElements() > 0) {
			layoutDessins.addRow(new MyCustomSvg[] {montantPartitionTitre, montantPartitionQte}, ShiftMode.CENTER);
			layoutDessins.addRow(montantPartitionLayout.getSvg(), ShiftMode.CENTER);
			layoutDessins.addRow(new MyCustomSvg[] {contreCadreMontantPartitionTitre, montantPartitionQte}, ShiftMode.CENTER);
			layoutDessins.addRow(contreCadreMontantPartitionLayout.getSvg(), ShiftMode.CENTER);
		}
		layoutDessins.addRow(new MyCustomSvg[] {montantCorniereTitre, montantCorniereQte}, ShiftMode.CENTER);
		layoutDessins.addRow(montantCorniereLayout.getSvg(), ShiftMode.CENTER);
		layoutDessins.addRow(new MyCustomSvg[] {contreCadreMontantCorniereTitre, montantCorniereQte}, ShiftMode.CENTER);
		layoutDessins.addRow(contreCadreMontantCorniereLayout.getSvg(), ShiftMode.CENTER);

		MyCustomSvg dessins = layoutDessins.getSvg();

		MyCustomSvg vitrages = new LignesTexte(Arrays.asList(
			"VITRAGE :",
			SvgComponent.DOUBLE_FORMAT.format(conf.hauteurVitrage()) + " x " + SvgComponent.DOUBLE_FORMAT.format(conf.largeurVitrage()) + " QTE " + conf.nbVitrage()
		));

		vitrages.setPadding(new Padding(10));
		vitrages.setBorders(true);
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
		Date date = new Date();
		MyCustomSvg cartoucheDroite = new LignesTexte(Arrays.asList(
			"ARC : " + data.get("ARC"),
			"Client : " + data.get("client"),
			"C.M. : " + data.get("reference"),
			"Date : " + dateFormat.format(date),
			"Modèle : " + data.get("modele"),
			"Dimensions : " + conf.getLargeurVerriere() + " x " + conf.getHauteurVerriere(),
			"Partitions : " + conf.getNbPartitions(),
            "Nature vitrage : " + data.get("epaisseurVitrage") + " " + data.get("natureVitrage"),
            "Finition : " + data.get("finition")
		));
		cartoucheDroite.setPadding(new Padding(10));
		cartoucheDroite.setBorders(true);
		
		MyHandyLayout finalLayout = new MyHandyLayout();
		finalLayout.addRow(dessins, ShiftMode.LEFT);
		finalLayout.addRow(new MyCustomSvg[] {vitrages, cartoucheDroite}, ShiftMode.LEFT);
		
		MyCustomSvg finalSvg = finalLayout.getSvg();
		finalSvg.setPadding(new Padding(10));
		this.svgToRender.put("complete", finalSvg);
	}
}
