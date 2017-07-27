package generateurCoteVerriere.modeles;

public abstract class ConfGenerique {
	protected String ARC;
	protected String client;
	protected String reference;

	public ConfGenerique(String ARC, String client, String reference) {
		this.ARC = ARC;
		this.client = client;
		this.reference = reference;
	}

	public String getARC(){
		return this.ARC;
	}

	public String getClient(){
		return this.client;
	}

	public String getReference(){
		return this.reference;
	}
}
