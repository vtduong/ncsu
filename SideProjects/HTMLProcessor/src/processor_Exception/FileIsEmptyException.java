package processor_Exception;

public class FileIsEmptyException extends Exception{
	/**
	 * Added this to shut up checkStyle.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * constructs FileIsEmptyException object 
	 */
	public FileIsEmptyException() {
		this("File is empty");
	}
	
	public FileIsEmptyException(String message){
		super(message);
	}
}
