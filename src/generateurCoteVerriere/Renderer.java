package generateurCoteVerriere;

import java.nio.file.Path;
import java.util.HashMap;

import generateurCoteVerriere.modeles.mecanique.Mecanique;

public class Renderer {
	public static void render(String modele, Path savePath, HashMap<String, Object> args) {
		switch(modele) {
			case "Mécanique":
				new Mecanique(
					(String) args.get("ARC"),
					(String) args.get("client"),
					(String) args.get("reference"),
					(Double) args.get("hauteurVerriere"),
					(Double) args.get("largeurVerriere"),
					(Integer) args.get("nbPartitions")
				).generate(savePath);
				break;
			case "premium":
				//new Premium(ARC, client, reference, hauteurVerriere, largeurVerriere, nbPartitions).generate(savePath);
				break;
		}
	}
}
