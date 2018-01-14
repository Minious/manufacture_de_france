package com.manufacturedefrance.techdrawgen;

import com.manufacturedefrance.svgen.MyCustomSvg;
import org.apache.batik.apps.rasterizer.DestinationType;
import org.apache.batik.apps.rasterizer.SVGConverter;
import org.apache.batik.apps.rasterizer.SVGConverterException;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.util.PDFMergerUtility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class ModeleGenerique {
	protected Map<String, Double> conf;
	protected Map<String, Object> data;
	
	protected HashMap<String, MyCustomSvg> svgToRender;
	
	public ModeleGenerique(Map<String, Object> data) {
		this.data = data;
		this.conf = new HashMap<>();
		
		this.svgToRender = new HashMap<>();
	}
	
	public void generate(Path savePathTemp) {
		Path savePath = savePathTemp.resolve(this.data.get("client")+"_"+this.data.get("reference"));
		
		System.out.println("\n\tChargement...\n");

		ArrayList<String> svgPaths = new ArrayList<>();
		ArrayList<String> pdfPaths = new ArrayList<>();

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
		} catch (SVGConverterException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, e.toString());
		}
		
		// Merge pdf files
		PDFMergerUtility pdf = new PDFMergerUtility();
		pdf.setDestinationFileName(savePath.resolve("result.pdf").toString());
		for(String pdfPath : pdfPaths)
			pdf.addSource(pdfPath);
		try {
			pdf.mergeDocuments();
			System.out.println("Merge pdf files");
		} catch (COSVisitorException | IOException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, e.toString());
		}
		
		System.out.println("\nTerminé !");
	}
}
