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

import myCustomSvgLibrary.MyCustomSvg;

public abstract class ModeleGenerique {
	protected HashMap<String, Double> conf;
	protected HashMap<String, Object> data;
	
	protected HashMap<String, MyCustomSvg> svgToRender;
	
	public ModeleGenerique(HashMap<String, Object> data) {
		this.data = data;
		this.conf = new HashMap<String, Double>();
		
		this.svgToRender = new HashMap<String, MyCustomSvg>();
	}
	
	public void generate(Path savePathTemp) {
		Path savePath = savePathTemp.resolve(this.data.get("client")+"_"+this.data.get("reference"));
		
		System.out.println("\n\tChargement...\n");
		
		// Independant elements
		String[] classesStr = this.getElementsClasses();
		
		ArrayList<Class> classes = new ArrayList<Class>();
		for(String classStr : classesStr)
			try {
				classes.add(Class.forName(getPackage() + "." + classStr));
			} catch (ClassNotFoundException e1) {}

		ArrayList<String> svgPaths = new ArrayList<String>();
		ArrayList<String> pdfPaths = new ArrayList<String>();
		ArrayList<ElementGenerique> elems = new ArrayList<ElementGenerique>();
		for(Class curClass : classes)
			try {
				Constructor constructor = curClass.getDeclaredConstructor(HashMap.class, HashMap.class);
				ElementGenerique curElem = (ElementGenerique) constructor.newInstance(this.conf, this.data);
				if(curElem.getNbElements() > 0)
					elems.add(curElem);
			} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		
		try {
			for(int i=0;i<elems.size();i++) {
				elems.get(i).renderImage(savePath.resolve("svg"));
				Path completeSvgSavePath = savePath.resolve("svg").resolve(elems.get(i).getNomFichierDeRendu()+".svg");
				Path completePdfSavePath = savePath.resolve("pdf").resolve(elems.get(i).getNomFichierDeRendu()+".pdf");
				svgPaths.add(completeSvgSavePath.toString());
				pdfPaths.add(completePdfSavePath.toString());
			}
		} catch (IOException e) {}
		
		// HashMap svgToRender
		for(String fileName : this.svgToRender.keySet()) {
			MyCustomSvg curSvg = this.svgToRender.get(fileName);
			Path completeSvgSavePath = savePath.resolve("svg").resolve(fileName + ".svg");
			Path completePdfSavePath = savePath.resolve("pdf").resolve(fileName + ".pdf");
			curSvg.writeToSVG(completeSvgSavePath);
			svgPaths.add(completeSvgSavePath.toString());
			pdfPaths.add(completePdfSavePath.toString());
		}
		
		// Create pdf files
		File outputFile = savePath.resolve("pdf").toFile();
		SVGConverter converter = new SVGConverter();
		converter.setDestinationType(DestinationType.PDF);
		converter.setSources(svgPaths.toArray(new String[svgPaths.size()]));
		converter.setDst(outputFile);
		try {
			converter.execute();
		} catch (SVGConverterException e) {}
		
		// Merge pdf files
		PDFMergerUtility pdf = new PDFMergerUtility();
		pdf.setDestinationFileName(savePath.resolve("result.pdf").toString());
		for(int i=0;i<pdfPaths.size();i++)
			pdf.addSource(pdfPaths.get(i).toString());
		try {
			pdf.mergeDocuments();
			System.out.println("Merge pdf files");
		} catch (COSVisitorException e) {} 
		catch (IOException e) {}
		
		System.out.println("\nTerminé !");
	}

	protected abstract String[] getElementsClasses();
	protected abstract String getPackage();
}
