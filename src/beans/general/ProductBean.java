package beans.general;


import javax.validation.constraints.NotNull;

import payments.Price;

public class ProductBean {

    private String  name;
    private String  category;
    private Price   price;
    private String  description;
    private int     quantity;
    private String  imagePath;
    private String  displayPagePath;
    private String  editPagePath;



    public ProductBean()
    {
        name = category = description = imagePath = displayPagePath = editPagePath = "";
        price = new Price(0, 0, "EUR");
    }



    public ProductBean(@NotNull String name, @NotNull String category, @NotNull Price price,
                       @NotNull String description, int quantity, @NotNull String imagePath,
                       @NotNull String displayPagePath, @NotNull String editPagePath)
    {
        this.name               = name;
        this.category           = category;
        this.price              = price;
        this.description        = description;
        this.quantity           = quantity;
        this.imagePath          = imagePath;
        this.displayPagePath    = displayPagePath;
        this.editPagePath       = editPagePath;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
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
}
