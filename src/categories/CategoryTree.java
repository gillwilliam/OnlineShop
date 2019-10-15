package categories;

import java.util.ArrayList;

import com.sun.istack.NotNull;

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
	
	
	
	public boolean addRootCategory(@NotNull Category category)
	{
		if (isCategoryUnique(category))
		{
			mAllCategories.add(category);
			mRootCategories.add(category);
			return true;
		}
		else
			return false;
	}
	
	
	
	/**
	 * initializes this CategoryTree using values obtained from database
	 */
	public boolean loadFromDatabase()
	{
		// TODO implement
		return false;
	}
	
	
	
	/**
	 * updates the tree in database
	 * @return
	 */
	public boolean updateInDatabase()
	{
		// TODO implement
		return false;
	}
	
	
	
	// getters && setters //////////////////////////////////////////////////////////////
	
	
	
	@NotNull
	public ArrayList<Category> getRootCategories()
	{
		return mRootCategories;
	}
}









