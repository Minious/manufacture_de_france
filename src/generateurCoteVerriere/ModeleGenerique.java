package generateurCoteVerriere;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.ArrayList;

import org.apache.batik.apps.rasterizer.DestinationType;
import org.apache.batik.apps.rasterizer.SVGConverter;
import org.apache.batik.apps.rasterizer.SVGConverterException;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.util.PDFMergerUtility;

public abstract class ModeleGenerique {
	public void generate(Path savePathTemp) {
		Path savePath = savePathTemp.resolve(getConf().client+"_"+getConf().getReference());
		
		System.out.println("\n\tChargement...\n");
		
		String[] classesStr = this.getElementsClasses();
		
		ArrayList<Class> classes = new ArrayList<Class>();
		for(String classStr : classesStr)
			try {
				System.out.println(classStr);
				classes.add(Class.forName(getPackage() + "." + classStr));
			} catch (ClassNotFoundException e1) {}

		ArrayList<ElementGenerique> elems = new ArrayList<ElementGenerique>();
		for(int i=0;i<classes.size();i++)
			try {
				System.out.println(classes.get(i));
				elems.add((ElementGenerique) classes.get(i).getDeclaredConstructor(new Class[] {ConfGenerique.class}).newInstance(getConf()));
			} catch (NoSuchMethodException|SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {System.out.println(e);}

		System.out.println(elems);
		
		try {
			for(int i=0;i<elems.size();i++) {
				elems.get(i).renderImage(savePath.resolve("svg"));
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
	protected abstract ConfGenerique getConf();
}
