package utils;

import java.util.HashMap;
import java.util.Map;

public class SQLUtils {



	public static String getSQLAnd(HashMap<String, String> colsValues)
	{
		StringBuilder res = new StringBuilder();
		
		for (Map.Entry<String, String> colValue : colsValues.entrySet())
		{
			res.append(colValue.getKey());
			res.append("='");
			res.append(colValue.getValue());
			res.append("' AND ");
		}
		
		if (res.length() >= 6)
			res = res.delete(res.length() - 5, res.length());
		
		return res.toString();
	}
	
}
