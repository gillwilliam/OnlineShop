package categories;

import java.util.ArrayList;
import java.util.HashSet;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;

/**
 * Contains all root categories (top level categories, that haven't got parent).
 * You can access them via getAllCategories().
 * It's doubly linked tree with link to CategoryTree from each category *
 */
public class CategoryTree {
	
	// CONST ///////////////////////////////////////////////////////////////
	public static final int MAX_NUM_OF_CATEGORIES = 1000000;
	
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
	 * have different ids. Also doesn't let adding new category if maximum
	 * number of categories was reached
	 * @param category category that is supposed to be added
	 * @return true, if category is unique and was successfully added,
	 * false, if category hadn't got unique id or maximum number of categoireies
	 * is reached.
	 */
	boolean addCategory(@NotNull Category category)
	{
		if (mAllCategories.size() < MAX_NUM_OF_CATEGORIES && isCategoryUnique(category))
		{
			mAllCategories.add(category);
			return true;
		}
		else
			return false;
	}
	
	
	
	/**
	 * removes category from this tree
	 * @param categoryId
	 * @return
	 */
	public boolean removeCategory(int categoryId)
	{
		boolean result = false;
		
		Category currCategory = null;
		for (int i = 0; i < mAllCategories.size(); i++)
		{
			currCategory = mAllCategories.get(i);
			
			if (currCategory.getId() == categoryId)
			{	
				removeChildren(currCategory.getSubcategories());
				result = removeCategoryAt(i, categoryId);
			}
		}
		
		return result;
	}
	
	
	
	private boolean removeCategoryAt(int idx, int categoryId)
	{
		Category parent = mAllCategories.get(idx).getParent();
		if (parent != null)
		{
			parent.removeSubcategory(categoryId);
			mAllCategories.remove(idx);	
			
			return true;
		}
		else
			return removeRootCategory(categoryId);
	}
	
	
	
	private void removeChildren(ArrayList<Category> children)
	{
		while(!children.isEmpty())
			removeCategory(children.get(children.size() - 1).getId());
	}
	
	
	
	public boolean addRootCategory(@NotNull Category category)
	{
		String name = category.getName();
		if (isNameUniqueInRoot(name) && name.matches(Category.CATEGORY_NAME_REGEX))
		{
			mRootCategories.add(category);
			
			boolean dbSuccess = updateInDatabase();
			if (dbSuccess)
				return true;
			else
			{
				mRootCategories.remove(category);
				return false;
			}
		}
		else
			return false;
	}
	
	
	
	public boolean isNameUniqueInRoot(String name)
	{
		for (Category rootCategory : mRootCategories)
		{
			if (rootCategory.getName().equals(name))
				return false;
		}
		
		return true;
	}
	
	
	
	private boolean removeRootCategory(int categoryId)
	{
		if (mRootCategories.size() == 1)	// there must be at least one root category
			return false;
		
		for (int i = 0; i < mRootCategories.size(); i++)
		{
			if (mRootCategories.get(i).getId() == categoryId)
			{
				mRootCategories.remove(i);
				
				for (int j = 0; j < mAllCategories.size(); j++)
				{
					if (mAllCategories.get(j).getId() == categoryId)
					{
						mAllCategories.remove(j);
						return true;
					}	
				}
				
				return false;	// only way this code is reached is some serious logic error
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
	
	
	
	/**
	 * @return an ID that haven't occurred so far
	 */
	public int getUniqueId()
	{
		HashSet<Integer> idsInUsage = new HashSet<>();
		
		for (Category category : mAllCategories)
			idsInUsage.add(category.getId());
		
		boolean found 	= false;
		int proposedId 	= -1;
		while (!found)				// maximum number of categories is set, so it won't overflow and always will find a number
		{
			proposedId++;
			
			if (!idsInUsage.contains(proposedId))
				found = true;
		}
		
		return proposedId;
	}
	
	
	
	// getters && setters //////////////////////////////////////////////////////////////
	
	
	
	@NotNull
	public ArrayList<Category> getRootCategories()
	{
		return mRootCategories;
	}
	
	
	
	public int getNumOfCategories()
	{
		return mAllCategories.size();
	}
	
	
	
	@NotNull
	public ArrayList<Category> getAllCategories()
	{
		return mAllCategories;
	}
}









