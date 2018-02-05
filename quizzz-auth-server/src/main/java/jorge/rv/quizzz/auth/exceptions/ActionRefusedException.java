package jorge.rv.quizzz.auth.exceptions;

public class ActionRefusedException extends QuizZzException {

	private static final long serialVersionUID = 1L;

	public ActionRefusedException() {
		super();
	}

	public ActionRefusedException(String message) {
		super(message);
	}
}