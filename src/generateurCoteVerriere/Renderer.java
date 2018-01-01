package generateurCoteVerriere;

import java.nio.file.Path;
import java.util.HashMap;

import generateurCoteVerriere.modeles.mecanique.Mecanique;
import generateurCoteVerriere.modeles.premium.Premium;

public class Renderer {
	public static void render(String modele, Path savePath, HashMap<String, Object> data) {
		switch(modele) {/*
			case "Mécanique":
				new Mecanique(data).generate(savePath);
				break;
				*/
			case "Premium":
				new Premium(data).generate(savePath);
				break;
		}
	}
}
