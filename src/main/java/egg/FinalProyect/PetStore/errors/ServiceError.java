package egg.FinalProyect.PetStore.errors;

public class ServiceError extends Exception{
	
	/**
	 * hola 
	 */
	private static final long serialVersionUID = 1L;

	public ServiceError(String msg) {
		super(msg);
	}

}