package com.manufacturedefrance.conf;

import java.util.ArrayList;

public class UnprocessableConfFileException extends Exception {

	UnprocessableConfFileException(ArrayList<String> unprocessedExp) {
		super(unprocessedExp.toString());
	}
}
