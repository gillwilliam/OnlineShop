package categories;

import java.util.ArrayList;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;

public class Category {
	
	// CONST ////////////////////////////////////////////////////////////////
	private static final String BAD_NAME_EXCEPTION = "name must not containt only white characters";

	// fields ///////////////////////////////////////////////////////////////
	/**
	 * unambiguously identifies category. mId must be unique in the
	 * whole CategoryTree (mTree).
	 */
	private int mId;
	/**
	 * name displayed to user
	 */
	@NotNull
	private String mName;
	/**
	 * List of direct subcategories
	 */
	@NotNull
	private ArrayList<Category> mSubcategories;
	/**
	 * Category for which this category is direct subcategory.
	 * If mParent is null, then it is root category.
	 */
	@Nullable
	private Category mParent;
	/**
	 * Tree of categories to which this category belongs. It is used
	 * to check whether category id is unique in whole tree.
	 */
	@NotNull
	private CategoryTree 		mTree;
	
	
	
	// constructors /////////////////////////////////////////////////////////
	
	
	
	/**
	 * @throws IllegalArgumentException if name is an empty string or a string 
	 * that contains only white characters
	 * @param id unique id in whole category tree
	 * @param name non-null and non-empty string
	 * @param parent direct ancestor of this category, if null, then the category
	 * is a rootCategory
	 * @param tree tree to which this category belongs
	 */
	public Category(
			int id, 
			@NotNull String name, 
			@Nullable Category parent,
			@NotNull CategoryTree tree)
	{
		if (name.trim().isEmpty()) throw new IllegalArgumentException(BAD_NAME_EXCEPTION);
		
		mId 			= id;
		mName 			= name.trim();
		mSubcategories 	= new ArrayList<Category>();
		mParent 		= parent;
		mTree 			= tree;
	}
	
	
	
	// methods //////////////////////////////////////////////////////////////

	
	
	/**
	 * adds subcategory to this category
	 * @param category to be added
	 * @return true if category was successfully added, false if category
	 * is not unique in current category tree
	 */
	public boolean addSubcategory(Category category) 
	{
		boolean isProper = isNameUniqueInSubcategories(category.getName()) && 
				mTree.addCategory(category);
		
		if (isProper)
		{
			mSubcategories.add(category);
			return true;
		}
		else
			return false;
	}
	
	
	
	private boolean isNameUniqueInSubcategories(String categoryName)
	{
		for (Category subcategory : mSubcategories)
		{
			if (subcategory.getName().equals(categoryName))
				return false;
		}
		
		return true;
	}
	
	
	
	/**
	 * removes category with specified id
	 * @param categoryId id of category to be removed
	 * @return true if category was successfully removed,
	 * false if category with such id was not found
	 */
	public boolean removeSubcategory(int categoryId)
	{
		Category currCategory 	= null;
		int size 				= mSubcategories.size();
		
		for (int i = 0; i < size; i++)
		{
			currCategory = mSubcategories.get(i);
			
			if (currCategory.getId() == categoryId)
			{
				mSubcategories.remove(i);
				return true;
			}
		}
		
		return false;
	}
	
	
	
	/**
	 * @return true if category hasn't got any subcategories,
	 * false if category has at least one subcategory
	 */
	public boolean isLeaf()
	{
		return mSubcategories.isEmpty();
	}
	
	
	
	// getters && setters //////////////////////////////////////////////////
	
	
	
	@NotNull
	public ArrayList<Category> getSubcategories()
	{
		return mSubcategories;
	}
	
	
	
	@Nullable
	public Category getParent()
	{
		return mParent;
	}
	
	
	
	public int getId()
	{
		return mId;
	}
	
	
	
	@NotNull
	public String getName()
	{
		return mName;
	}
	
	
	
	public void setName(@NotNull String newName)
	{
		mName = newName;
	}
}
