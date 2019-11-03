package categories;

import java.util.ArrayList;
import java.util.Arrays;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;

public class Category {
	
	// CONST ////////////////////////////////////////////////////////////////
	private static final String BAD_NAME_EXCEPTION 	= "name must not containt only white characters";
	private static final char PATH_NODES_SEPARATOR 	= '/';
	public static final int MAX_CATEGORY_NAME_LEN 	= 16;
	public static final String CATEGORY_NAME_REGEX 	= "^[a-zA-Z]{1}[a-zA-Z  ]{0," + MAX_CATEGORY_NAME_LEN + "}$";

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
	 * path from root to category, i.e. trousers/men/jeans
	 */
	@NotNull
	private String mPath;
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
	private CategoryTree mTree;
	
	
	
	// constructors /////////////////////////////////////////////////////////
	
	
	
	/**
	 * @throws IllegalArgumentException if name is an empty string or a string 
	 * that contains only white characters
	 * @param id unique id in whole category tree
	 * @param name non-null and non-empty string
	 * @param tree tree to which this category belongs
	 */
	public Category(
			int id, 
			@NotNull String name, 
			@NotNull CategoryTree tree)
	{
		if (name.trim().isEmpty()) throw new IllegalArgumentException(BAD_NAME_EXCEPTION);
		
		mId 			= id;
		// if category is not unique in tree throw exception
		if (!tree.addCategory(this)) throw new NonUniqueCategoryException();
		
		mName 			= name.trim();
		mSubcategories 	= new ArrayList<Category>();
		mTree 			= tree;
		tree.addCategory(this);
		mPath			= mName;
	}
	
	
	
	// methods /////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	
	/**
	 * creates path to this category and stores it in mPath
	 */
	private void createPath()
	{
		ArrayList<String> pathReversed = new ArrayList<>();
		
		addToPath(pathReversed);
		
		StringBuilder path = new StringBuilder();
		
		for (int i = pathReversed.size() - 1; i >= 0; i--)
		{
			path.append(pathReversed.get(i));
			path.append(PATH_NODES_SEPARATOR);
		}
		
		// remove the last separator
		path.deleteCharAt(path.length() - 1);
		
		mPath = path.toString();
	}
	
	
	
	/**
	 * adds this category to path and it's ancestors if any
	 * @param path
	 */
	private void addToPath(ArrayList<String> path)
	{
		path.add(mName);
		
		if (mParent != null)
			mParent.addToPath(path);
	}
	
	
	
	public NameValidationResult isNameValid(String name)
	{
		boolean contentCorrect = name.matches(CATEGORY_NAME_REGEX);
		
		boolean isUnique = false;
		if (mParent != null)
			isUnique = mParent.isNameUniqueInSubcategories(name);
		else
			isUnique = mTree.isNameUniqueInRoot(name);
		
		return new NameValidationResult(contentCorrect, isUnique);
	}

	
	
	/**
	 * adds subcategory to this category
	 * @param category to be added
	 * @return true if category was successfully added, false if category
	 * is not unique in current category tree or is from different tree
	 * than parent
	 */
	public boolean addSubcategory(Category category) 
	{
		boolean isProper = isNameUniqueInSubcategories(category.getName());
		
		if (isProper)
		{
			boolean isInTheSameTree = category.setParent(this);
			if (isInTheSameTree)
			{
				if (mSubcategories.add(category))
				{
					boolean dbSuccess = mTree.updateInDatabase();
					
					if (dbSuccess)
						return true;
					else
					{
						mSubcategories.remove(category);
						return false;
					}
				}
				else
					return false;
			}
			
			return false;
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
	boolean removeSubcategory(int categoryId)
	{
		Category currCategory 	= null;
		int size 				= mSubcategories.size();
		
		for (int i = 0; i < size; i++)
		{
			currCategory = mSubcategories.get(i);
			
			if (currCategory.getId() == categoryId)
			{
				currCategory.mParent = null;
				mSubcategories.remove(i);
				
				boolean dbSuccess = mTree.updateInDatabase();
				if (dbSuccess)
					return true;
				else
				{
					mSubcategories.add(currCategory);
					currCategory.mParent = this;
					return false;
				}
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
	
	
	
	/**
	 * @return true, if the category hasn't got parent,
	 * false otherwise
	 */
	public boolean isRootCategory()
	{
		return mParent == null;
	}
	
	
	
	/**
	 * @return string representing Javascript array, that contains
	 * all subcategories of this category
	 */
	public String getSubcategoriesAsJavascriptArray()
	{
		StringBuilder res = new StringBuilder();
		res.append("[");
		for (Category subcategory : mSubcategories)
		{
			res.append(subcategory.getId() + ",");
		}
		
		if (res.length() > 1)
			res = res.deleteCharAt(res.length() - 1);

		return res.toString() + "]";
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
	
	
	
	/**
	 * 
	 * @param parent
	 * @return true if successfully added, false otherwise (new parent is in different tree)
	 */
	boolean setParent(@Nullable Category parent)
	{
		boolean success = false;
		
		if (parent == null)
		{
			mParent = null;
			success = true;
			createPath();
		}
		else
		{
			if (parent.getTree() == mTree)
			{
				mParent = parent;
				success = true;		
				createPath();
			}
			else
			{
				success = false;
			}
		}
		
		return success;
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
	
	
	
	/**
	 * changes name of this category if is valid and updates in database
	 * @param newName
	 * @return true if successfully updated, false if name was invalid or
	 * couldn't update in database
	 */
	public boolean setName(@NotNull String newName)
	{
		if (isNameValid(newName).isValid())
		{
			String oldName			= mName;	// strings are immutable, so I can do that
			boolean updateSuccess 	= mTree.updateInDatabase();
			
			if (updateSuccess)
			{
				mName = newName;
				return true;
			}
			else
			{
				mName = oldName;
				return false;
			}
		}
		else
			return false;
	}
	
	
	
	public CategoryTree getTree()
	{
		return mTree;
	}
	
	
	
	@NotNull
	public String getPath()
	{
		return mPath;
	}
	
	
	
	// return case classes /////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	public class NameValidationResult {
		
		public boolean isContentValid;
		public boolean isUnique;
		
		public NameValidationResult(boolean isContentValid, boolean isUnique)
		{
			this.isContentValid = isContentValid;
			this.isUnique 		= isUnique;
		}
		
		
		
		public boolean isValid()
		{
			return isContentValid && isUnique;
		}
	}
	
	
	
	// Exceptions ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	public class NonUniqueCategoryException extends RuntimeException {

		// CONST ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		private static final long serialVersionUID = 1L;
		private static final String MESSAGE = "This category has not unique ID in specified CategoryTree";
		
		
		
		public NonUniqueCategoryException()
		{
			super(MESSAGE);
		}
		
	}
}
