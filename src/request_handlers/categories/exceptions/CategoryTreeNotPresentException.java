package request_handlers.categories.exceptions;

public class CategoryTreeNotPresentException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	
	public CategoryTreeNotPresentException()
	{
		super("Category tree wasn't present in context attributes. It must be available under attribute name specified"
				+ "in web.xml as init parameter named category_tree_attr");
	}
}
