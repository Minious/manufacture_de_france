package generateurCoteVerriere.modeles.mecanique;

import generateurCoteVerriere.modeles.ConfGenerique;
import generateurCoteVerriere.modeles.ModeleGenerique;

public class Mecanique extends ModeleGenerique {
	private Conf conf;
	
	public Mecanique(String ARC, String client, String reference, double hauteurVerriere, double largeurVerriere, int nbPartitions) {
		this.conf = new Conf(ARC, client, reference, hauteurVerriere, largeurVerriere, nbPartitions);
	}

	@Override
	protected String[] getElementsClasses() {
		return new String[] {"MontantPartition", "ContreCadreMontantPartition", "MontantCorniere", "ContreCadreMontantCorniere", "TraverseCorniere", "ContreCadreTraverseCorniere", "AttachesTraverseCorniere"};
	}

	@Override
	protected String getPackage() {
		return "generateurCoteVerriere.modeles.mecanique.elements";
	}

	@Override
	protected ConfGenerique getConf() {
		// TODO Auto-generated method stub
		return this.conf;
	}

}
