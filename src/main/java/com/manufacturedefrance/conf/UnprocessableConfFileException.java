package com.manufacturedefrance.conf;

import java.util.List;

public class UnprocessableConfFileException extends Exception {

	UnprocessableConfFileException(List<String> unprocessedExp) {
		super(unprocessedExp.toString());
	}
}
