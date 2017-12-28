package generateurCoteVerriere;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.batik.apps.rasterizer.DestinationType;
import org.apache.batik.apps.rasterizer.SVGConverter;
import org.apache.batik.apps.rasterizer.SVGConverterException;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.util.PDFMergerUtility;

public abstract class ModeleGenerique {
	protected HashMap<String, Double> conf;
	protected String ARC;
	protected String client;
	protected String reference;
	
	public ModeleGenerique(String ARC, String client, String reference) {
		this.ARC = ARC;
		this.client = client;
		this.reference = reference;
	}
	
	public void generate(Path savePathTemp) {
		Path savePath = savePathTemp.resolve(this.client+"_"+this.reference);
		
		System.out.println("\n\tChargement...\n");
		
		String[] classesStr = this.getElementsClasses();
		
		ArrayList<Class> classes = new ArrayList<Class>();
		for(String classStr : classesStr)
			try {
				System.out.println(classStr);
				classes.add(Class.forName(getPackage() + "." + classStr));
			} catch (ClassNotFoundException e1) {}

		ArrayList<ElementGenerique> elems = new ArrayList<ElementGenerique>();
		for(Class curClass : classes)
			try {
				System.out.println(curClass);
				Constructor constructor = curClass.getDeclaredConstructor(HashMap.class);
				ElementGenerique curElem = (ElementGenerique) constructor.newInstance(this.conf);
				if(curElem.getNbElements() > 0)
					elems.add(curElem);
			} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}

		System.out.println(elems);
		
		try {
			for(int i=0;i<elems.size();i++) {
				elems.get(i).renderImage(savePath.resolve("svg"), ARC, client, reference);
				System.out.println(elems);
			}
		} catch (IOException e) {}

		ArrayList<String> elemsPaths = new ArrayList<String>();
		for(int i=0;i<elems.size();i++)
			elemsPaths.add(savePath.resolve("svg").resolve(elems.get(i).getNomFichierDeRendu()+".svg").toString());
		
		// Create pdf files
		File outputFile = savePath.resolve("pdf").toFile();
		SVGConverter converter = new SVGConverter();
		converter.setDestinationType(DestinationType.PDF);
		converter.setSources(elemsPaths.toArray(new String[elemsPaths.size()]));
		converter.setDst(outputFile);
		try {
			converter.execute();
		} catch (SVGConverterException e) {}
		
		// Merge pdf files
		PDFMergerUtility pdf = new PDFMergerUtility();
		pdf.setDestinationFileName(savePath.resolve("result.pdf").toString());
		for(int i=0;i<elems.size();i++)
			pdf.addSource(savePath.resolve("pdf").resolve(elems.get(i).getNomFichierDeRendu()+".pdf").toString());
		try {
			pdf.mergeDocuments();
			System.out.println("Merge pdf files");
		} catch (COSVisitorException e) {} 
		catch (IOException e) {}
		
		System.out.println("\nTerminé !");
	}

	protected abstract String[] getElementsClasses();
	protected abstract String getPackage();
	//protected abstract ConfGenerique getConf();
}
