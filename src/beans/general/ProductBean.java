package beans.general;


import javax.validation.constraints.NotNull;

import categories.Category;
import payments.Price;

public class ProductBean {
	
	
	// CONST //////////////////////////////////////////////////////////////////////////////////////////////////
	public static final int MAX_NAME_LEN 	= 100;
	public static final String NAME_REGEX 	= "^[a-zA-Z0-9  ]{1," + MAX_NAME_LEN + "}$";
	

	private int	  		id;
    private String  	name;
    private Category  	category;
    private Price   	price;
    private String  	description;
    private int     	quantity;
    private String  	imagePath;
    private String  	displayPagePath;
    private String  	editPagePath;



    public ProductBean()
    {
        name = description = imagePath = displayPagePath = editPagePath = "";
        price 		= new Price(0, 0, "EUR");
        category 	= null;
    }



    /**
     * I left it like that, because I've seen someone using this constructor when simulating database
     * so I don't want to break the code, but finally it will be removed
     * @param name
     * @param category
     * @param price
     * @param description
     * @param quantity
     * @param imagePath
     * @param displayPagePath
     * @param editPagePath
     */
    @Deprecated
    public ProductBean(@NotNull String name, @NotNull String category, @NotNull Price price,
                       @NotNull String description, int quantity, @NotNull String imagePath,
                       @NotNull String displayPagePath, @NotNull String editPagePath)
    {
    	if (!name.matches(NAME_REGEX)) throw new IllegalArgumentException();
    	
        this.name               = name;
        //this.category           = category;	NOT VALID ANYMORE!
        this.price              = price;
        this.description        = description;
        this.quantity           = quantity;
        this.imagePath          = imagePath;
        this.displayPagePath    = displayPagePath;
        this.editPagePath       = editPagePath;
    }
    
    
    
    public ProductBean(int id, @NotNull String name, @NotNull Category category, @NotNull Price price,
            @NotNull String description, int quantity, @NotNull String imageStoragePath)
    {
    	if (!name.matches(NAME_REGEX)) throw new IllegalArgumentException("Name doesn't match required pattern");
    	
    	this.id					= id;
        this.name               = name;
        this.category           = category;
        this.price              = price;
        this.description        = description;
        this.quantity           = quantity;
        this.imagePath          = imageStoragePath + name;
        this.displayPagePath	= obtainDisplayPagePath();
        this.editPagePath		= obtainEditPagePath();
    }
    
    
    
    private String obtainDisplayPagePath()
    {
    	// TODO implement
    	return "function obtainDisplayPagePath in ProductBean not implemented";
    }
    
    
    
    private String obtainEditPagePath()
    {
    	// TODO implement
    	return "function obtainEditPagePath in ProductBean not implemented";
    }
    
    
    
    public static boolean isNameValid(String name)
    {
    	return name.matches(NAME_REGEX);
    }
    
    
    
    public boolean updateInDB()
    {
    	// TODO implement
    	return true;
    }
    
    
    
    // getters & setters /////////////////////////////////////////////////////////////////////////////////


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String image) {
        this.imagePath = image;
    }

    public String getDisplayPagePath() {
        return displayPagePath;
    }

    public void setDisplayPagePath(String displayPagePath) {
        this.displayPagePath = displayPagePath;
    }

    public String getEditPagePath() {
        return editPagePath;
    }

    public void setEditPagePath(String editPagePath) {
        this.editPagePath = editPagePath;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
