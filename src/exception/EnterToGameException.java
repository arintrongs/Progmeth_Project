package exception;

public class EnterToGameException extends Exception {

	@Override
	public String getMessage() {
		return "Please Press [Enter] to enter a game.";
	}

}
