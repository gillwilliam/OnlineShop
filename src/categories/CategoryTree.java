package categories;

import java.util.ArrayList;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;

public class CategoryTree {
	
	// fields //////////////////////////////////////////////////////////////
	@NotNull
	private ArrayList<Category> mRootCategories;
	/**
	 * all categories contained in this category tree. Doesn't matter at what
	 * level they are. They are stored here so that it can be assured that
	 * categories have unique ids along the whole tree.
	 */
	@NotNull
	private ArrayList<Category> mAllCategories;
	
	
	
	// constructors ////////////////////////////////////////////////////////
	
	
	
	public CategoryTree()
	{
		mRootCategories = new ArrayList<Category>();
		mAllCategories 	= new ArrayList<Category>();
	}
	

	
	// methods //////////////////////////////////////////////////////////////
	
	
	
	private boolean isCategoryUnique(Category category)
	{
		int id = category.getId();
		
		for (Category existingCategory : mAllCategories)
		{
			if (existingCategory.getId() == id)
				return false;
		}
		
		return true;
	}
	
	
	
	/**
	 * checks whether category is unique in this tree and if so, then
	 * stores the category, to make sure that in the future new categories
	 * have different ids.
	 * @param category category that is supposed to be added
	 * @return true, if category is unique and was successfully added,
	 * false, if category hadn't got unique id.
	 */
	boolean addCategory(@NotNull Category category)
	{
		if (isCategoryUnique(category))
		{
			mAllCategories.add(category);
			return true;
		}
		else
			return false;
	}
	
	
	
	boolean removeCategory(int categoryId)
	{
		for (int i = 0; i < mAllCategories.size(); i++)
		{
			if (mAllCategories.get(i).getId() == categoryId)
			{
				mAllCategories.remove(i);
				return true;
			}
		}
		
		return false;
	}
	
	
	
	public boolean addRootCategory(@NotNull Category category)
	{
		if (isCategoryUnique(category))
		{
			mRootCategories.add(category);
			return true;
		}
		else
			return false;
	}
	
	
	
	public boolean removeRootCategory(int categoryId)
	{
		for (int i = 0; i < mRootCategories.size(); i++)
		{
			if (mRootCategories.get(i).getId() == categoryId)
			{
				mRootCategories.remove(i);
				mAllCategories.remove(i);
				return true;
			}
		}
		
		return false;
	}
	
	
	
	/**
	 * returns category from this tree with specified id
	 * @param categoryId id of category to be found
	 * @return category with categoryId or null if there is not
	 * such category
	 */
	@Nullable
	public Category find(int categoryId)
	{		
		for (Category category : mAllCategories)
		{
			if (category.getId() == categoryId)
				return category;
		}
		
		return null;
	}
	
	
	
	/**
	 * initializes this CategoryTree using values obtained from database
	 */
	public boolean loadFromDatabase()
	{
		// TODO implement
		Category cat1 = new Category(1, "cat1", this);
		Category cat11 = new Category(2, "cat11", this);
		Category cat12 = new Category(5, "cat12", this);
		cat1.addSubcategory(cat11);
		cat1.addSubcategory(cat12);
		Category cat111 = new Category(4, "cat111", this);
		cat11.addSubcategory(cat111);
		Category cat2 = new Category(3, "cat3", this);
		
		mRootCategories.add(cat1);
		mRootCategories.add(cat2);

		return false;
	}
	
	
	
	/**
	 * updates the tree in database
	 * @return
	 */
	public boolean updateInDatabase()
	{
		// TODO implement
		return true;
	}
	
	
	
	// getters && setters //////////////////////////////////////////////////////////////
	
	
	
	@NotNull
	public ArrayList<Category> getRootCategories()
	{
		return mRootCategories;
	}
}









