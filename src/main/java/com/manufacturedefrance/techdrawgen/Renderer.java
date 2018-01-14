package com.manufacturedefrance.techdrawgen;

import java.nio.file.Path;
import java.util.HashMap;

import com.manufacturedefrance.techdrawgen.modeles.mecanique.Mecanique;
import com.manufacturedefrance.techdrawgen.modeles.premium.Premium;

public class Renderer {
	public static void render(String modele, Path savePath, HashMap<String, Object> data) {
		data.put("modele", modele);
		switch(modele) {
			case "Mécanique":
			case "Mecanica":
				new Mecanique(data).generate(savePath);
				break;
			case "Premium":
				new Premium(data).generate(savePath);
				break;
		}
	}
}
