package ua.nure.itkn179.kushnarenko.agent;

public class SearchException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7350778143094504319L;
	private String name;

    public SearchException(Exception e) {
        this.name = e.toString();
    }

    public String getName() { return name; }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
