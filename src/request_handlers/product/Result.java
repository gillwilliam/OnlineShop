package request_handlers.product;

public abstract class Result {
	
	public boolean success;
	public String message;
	
	public Result(boolean success, String message)
	{
		this.success = success;
		this.message = message;
	}
}
