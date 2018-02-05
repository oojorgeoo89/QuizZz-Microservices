package jorge.rv.quizzz.auth.exceptions;

public class ResourceUnavailableException extends QuizZzException {

	private static final long serialVersionUID = 1L;

	public ResourceUnavailableException() {
		super();
	}

	public ResourceUnavailableException(String message) {
		super(message);
	}
}
