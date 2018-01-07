package generateurCoteVerriere.modeles.mecanique;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import conf.TextFileConf;
import conf.UnprocessableConfFileException;
import generateurCoteVerriere.LignesTexte;
import generateurCoteVerriere.ModeleGenerique;
import generateurCoteVerriere.modeles.mecanique.elements.AttachesTraverseCorniere;
import generateurCoteVerriere.modeles.mecanique.elements.ContreCadreMontantCorniere;
import generateurCoteVerriere.modeles.mecanique.elements.ContreCadreMontantPartition;
import generateurCoteVerriere.modeles.mecanique.elements.ContreCadreTraverseCorniere;
import generateurCoteVerriere.modeles.mecanique.elements.MontantPartition;
import generateurCoteVerriere.modeles.mecanique.elements.MontantCorniere;
import generateurCoteVerriere.modeles.mecanique.elements.TraverseCorniere;
import myCustomSvgLibrary.MyCustomSvg;
import myCustomSvgLibrary.MyHandyLayout;
import myCustomSvgLibrary.Padding;
import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced.ShiftMode;
import net.objecthunter.exp4j.function.Function;

public class Mecanique extends ModeleGenerique {
	public Mecanique(HashMap<String,Object> data) {
		super(data);

		String fileName = "conf_mecanique.txt";

		HashMap<String, Double> initialMap = new HashMap<String, Double>();

		initialMap.put("hauteurVerriere", (Double) data.get("hauteurVerriere"));
		initialMap.put("largeurVerriere", (Double) data.get("largeurVerriere"));
		initialMap.put("nbPartitions", ((Integer) data.get("nbPartitions")).doubleValue());

		ArrayList<Function> functions = new ArrayList<Function>();
		functions.add(new Function("getNbAttachesIntermediairesTraverseCorniere", 2) {
			@Override
			public double apply(double... args) {
				// TODO
				double entreAxeAttachesSouhaite = args[0];
				double longueurADiviser = args[1];
				int nbAttaches = (int) Math.floor(longueurADiviser / entreAxeAttachesSouhaite);
				double entreAxeAttaches1 = longueurADiviser / nbAttaches;
				double entreAxeAttaches2 = longueurADiviser / (1 + nbAttaches);
				double ecartEntreSouhaiteEtReel1 = Math.abs(entreAxeAttachesSouhaite - entreAxeAttaches1);
				double ecartEntreSouhaiteEtReel2 = Math.abs(entreAxeAttachesSouhaite - entreAxeAttaches2);
				
				return ecartEntreSouhaiteEtReel1 < ecartEntreSouhaiteEtReel2 ? nbAttaches - 1 : nbAttaches;
			}
		});

		try {
			this.conf = TextFileConf.loadConf(fileName, initialMap, functions);
		} catch (IOException | UnprocessableConfFileException e) {
			e.printStackTrace();
		}

		MontantCorniere montantCorniere = new MontantCorniere(this.conf, this.data);
		ContreCadreMontantCorniere contreCadreMontantCorniere = new ContreCadreMontantCorniere(this.conf, this.data);
		MontantPartition montantPartition = new MontantPartition(this.conf, this.data);
		ContreCadreMontantPartition contreCadreMontantPartition = new ContreCadreMontantPartition(this.conf, this.data);
		TraverseCorniere traverseCorniere = new TraverseCorniere(this.conf, this.data);
		ContreCadreTraverseCorniere contreCadreTraverseCorniere = new ContreCadreTraverseCorniere(this.conf, this.data);
		AttachesTraverseCorniere attachesTraverseCorniere = new AttachesTraverseCorniere(this.conf, this.data);
		
		// Montant corniere
		MyHandyLayout montantCorniereLayout = new MyHandyLayout();
		MyCustomSvg montantCorniereSvg = new MyCustomSvg();
		MyCustomSvg contreCadreMontantCorniereSvg = new MyCustomSvg();
		
		montantCorniereSvg.rotate(Math.PI / 2);
		contreCadreMontantCorniereSvg.rotate(Math.PI / 2);
		
		montantCorniereSvg.drawSvg(montantCorniere.getDessin(), 0, 0);
		contreCadreMontantCorniereSvg.drawSvg(contreCadreMontantCorniere.getDessin(), 0, 0);
		
		montantCorniereLayout.addRow(montantCorniereSvg, ShiftMode.CENTER);
		montantCorniereLayout.addRow(contreCadreMontantCorniereSvg, ShiftMode.CENTER);
		
		// Montant partition
		MyHandyLayout montantPartitionLayout = new MyHandyLayout();
		MyCustomSvg montantPartitionSvg = new MyCustomSvg();
		MyCustomSvg contreCadreMontantPartitionSvg = new MyCustomSvg();
		
		montantPartitionSvg.rotate(Math.PI / 2);
		contreCadreMontantPartitionSvg.rotate(Math.PI / 2);
		
		montantPartitionSvg.drawSvg(montantPartition.getDessin(), 0, 0);
		contreCadreMontantPartitionSvg.drawSvg(contreCadreMontantPartition.getDessin(), 0, 0);
		
		montantPartitionLayout.addRow(montantPartitionSvg, ShiftMode.CENTER);
		montantPartitionLayout.addRow(contreCadreMontantPartitionSvg, ShiftMode.CENTER);
		
		// Traverse corniere
		MyHandyLayout traverseCorniereLayout = new MyHandyLayout();
		MyCustomSvg traverseCorniereSvg = new MyCustomSvg();
		MyCustomSvg contreCadreTraverseCorniereSvg = new MyCustomSvg();
		MyCustomSvg attachesTraverseCorniereSvg = new MyCustomSvg();
		
		traverseCorniereSvg.rotate(Math.PI / 2);
		contreCadreTraverseCorniereSvg.rotate(Math.PI / 2);
		attachesTraverseCorniereSvg.rotate(Math.PI / 2);
		
		traverseCorniereSvg.drawSvg(traverseCorniere.getDessin(), 0, 0);
		contreCadreTraverseCorniereSvg.drawSvg(contreCadreTraverseCorniere.getDessin(), 0, 0);
		attachesTraverseCorniereSvg.drawSvg(attachesTraverseCorniere.getDessin(), 0, 0);
		
		traverseCorniereLayout.addRow(traverseCorniereSvg, ShiftMode.CENTER);
		traverseCorniereLayout.addRow(attachesTraverseCorniereSvg, ShiftMode.CENTER);
		traverseCorniereLayout.addRow(contreCadreTraverseCorniereSvg, ShiftMode.CENTER);

		// Final		
		MyCustomSvg traverseCorniereTitre = new LignesTexte("TRAVERSES HAUTE ET BASSE");
		MyCustomSvg traverseCorniereQte = new LignesTexte("QTE : "+traverseCorniere.getNbElements());
		MyCustomSvg montantCorniereTitre = new LignesTexte("MT CORNIERE LATERAL");
		MyCustomSvg montantCorniereQte = new LignesTexte("QTE : "+montantCorniere.getNbElements());
		MyCustomSvg montantPartitionTitre = new LignesTexte("MT INTERMEDIAIRE");
		MyCustomSvg montantPartitionQte = new LignesTexte("QTE : "+montantPartition.getNbElements());

		MyHandyLayout layoutDessins = new MyHandyLayout();
		layoutDessins.addRow(new MyCustomSvg[] {traverseCorniereTitre, traverseCorniereQte}, ShiftMode.LEFT);
		layoutDessins.addRow(traverseCorniereLayout.getSvg(), ShiftMode.CENTER);
		layoutDessins.addRow(new MyCustomSvg[] {montantPartitionTitre, montantPartitionQte}, ShiftMode.LEFT);
		layoutDessins.addRow(montantPartitionLayout.getSvg(), ShiftMode.CENTER);
		layoutDessins.addRow(new MyCustomSvg[] {montantCorniereTitre, montantCorniereQte}, ShiftMode.LEFT);
		layoutDessins.addRow(montantCorniereLayout.getSvg(), ShiftMode.CENTER);

		MyCustomSvg dessins = layoutDessins.getSvg();

		MyCustomSvg vitrages = new LignesTexte(Arrays.asList(new String[] {
			"VITRAGE :",
			conf.get("largeurVitrage") + " x " + conf.get("hauteurVitrage") + " QTE " + conf.get("nbVitrage").intValue()
		}));

		vitrages.setPadding(new Padding(10));
		vitrages.setBorders(true);
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
		Date date = new Date();
		MyCustomSvg cartoucheDroite = new LignesTexte(Arrays.asList(new String[] {
			"ARC : " + data.get("ARC"),
			"Client : " + data.get("client"),
			"C.M. : " + data.get("reference"),
			"Date : " + dateFormat.format(date),
			"Modèle : Mécanique",
			"Dimensions : " + conf.get("largeurVerriere") + " x " + conf.get("hauteurVerriere")
		}));
		cartoucheDroite.setPadding(new Padding(10));
		cartoucheDroite.setBorders(true);
		
		MyHandyLayout finalLayout = new MyHandyLayout();
		finalLayout.addRow(dessins, ShiftMode.LEFT);
		finalLayout.addRow(new MyCustomSvg[] {vitrages, cartoucheDroite}, ShiftMode.LEFT);
		
		MyCustomSvg finalSvg = finalLayout.getSvg();
		finalSvg.setPadding(new Padding(10));
		this.svgToRender.put("complete", finalSvg);
	}

	@Override
	protected String[] getElementsClasses() {
		return new String[] {/*"MontantPartition2", "MontantCorniere2", "TraverseCorniere2", */"MontantPartition", "ContreCadreMontantPartition", "MontantCorniere", "ContreCadreMontantCorniere", "TraverseCorniere", "ContreCadreTraverseCorniere", "AttachesTraverseCorniere"};
	}

	@Override
	protected String getPackage() {
		return "generateurCoteVerriere.modeles.mecanique.elements";
	}
}
