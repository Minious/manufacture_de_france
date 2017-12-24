package generateurCoteVerriere.modeles.mecanica;

import generateurCoteVerriere.ConfGenerique;
import generateurCoteVerriere.ModeleGenerique;

public class Mecanica extends ModeleGenerique {
	private Conf conf;
	
	public Mecanica(String ARC, String client, String reference, double hauteurVerriere, double largeurVerriere, int nbPartitions) {
		this.conf = new Conf(ARC, client, reference, hauteurVerriere, largeurVerriere, nbPartitions);
	}

	@Override
	protected String[] getElementsClasses() {
		return new String[] {"MontantPartition", "MontantCorniere", "TraverseCorniere"};
	}

	@Override
	protected String getPackage() {
		return "generateurCoteVerriere.modeles.mecanica.elements";
	}

	@Override
	protected ConfGenerique getConf() {
		// TODO Auto-generated method stub
		return this.conf;
	}

}
