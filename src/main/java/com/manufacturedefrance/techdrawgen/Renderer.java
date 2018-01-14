package com.manufacturedefrance.techdrawgen;

import java.nio.file.Path;
import java.util.Map;

import com.manufacturedefrance.techdrawgen.modeles.mecanique.Mecanique;
import com.manufacturedefrance.techdrawgen.modeles.premium.Premium;

public class Renderer {

	private Renderer() {
		throw new IllegalStateException("Utility class");
	}

	public static void render(String modele, Path savePath, Map<String, Object> data) {
		data.put("modele", modele);
		switch(modele) {
			case "Mécanique":
			case "Mecanica":
				new Mecanique(data).generate(savePath);
				break;
			case "Premium":
				new Premium(data).generate(savePath);
				break;
			default:
				throw new IllegalStateException("Inexisting Modele");
		}
	}
}
