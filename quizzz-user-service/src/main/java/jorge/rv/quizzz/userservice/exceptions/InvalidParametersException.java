package jorge.rv.quizzz.userservice.exceptions;

public class InvalidParametersException extends QuizZzException {

	private static final long serialVersionUID = 1L;

	public InvalidParametersException() {
		super();
	}

	public InvalidParametersException(String message) {
		super(message);
	}
}
