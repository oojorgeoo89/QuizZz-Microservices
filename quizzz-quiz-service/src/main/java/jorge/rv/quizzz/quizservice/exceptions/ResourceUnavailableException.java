package jorge.rv.quizzz.quizservice.exceptions;

public class ResourceUnavailableException extends QuizZzException {

	private static final long serialVersionUID = 1L;

	public ResourceUnavailableException() {
		super();
	}

	public ResourceUnavailableException(String message) {
		super(message);
	}
}