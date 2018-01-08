package conf;

import java.util.ArrayList;

public class UnprocessableConfFileException extends Exception {

	public UnprocessableConfFileException(ArrayList<String> unprocessedExp) {
		super(unprocessedExp.toString());
	}
}
