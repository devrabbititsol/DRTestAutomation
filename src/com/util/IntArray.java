package com.util;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

public class IntArray implements Array
{
	private int[] array;
	
	public IntArray(int[] array)
	{
		if (array == null)
		{
			throw new IllegalArgumentException("parameter array should not be null");
		}
		this.array = array;
	}
	
	public Object getArray() throws SQLException
	{
		return null;
	}
	
	public Object getArray(Map map) throws SQLException
	{
		return null;
	}
	
	public Object getArray(long index, int count) throws SQLException
	{
		return null;
	}

	public Object getArray(long index, int count, Map map) throws SQLException
	{
		return null;
	}
	
	public int getBaseType() throws SQLException
	{
		return Types.INTEGER;
	}

	/**
	 * This method is called by driver ver. 8 but not by ver. 7.
	 */
	public String getBaseTypeName() throws SQLException
	{
		return "int4";
	}
	
	public ResultSet getResultSet() throws SQLException
	{
		return null;
	}
	
	public ResultSet getResultSet(long index, int count) throws SQLException
	{
		return null;
	}
	
	public ResultSet getResultSet(long index, int count, Map map) throws
SQLException
	{
		return null;
	}
	
	public ResultSet getResultSet(Map map) throws SQLException
	{
		return null;
	}
	
	/**
	 * This method is called by both drivers ver. 8 and 7.
	 */
	public String toString()
	{
		String result = "{";
		for (int i = 0; i < this.array.length; ++i)
		{
			if (i > 0)
			{
				result += ",";
			}
			result += this.array[i];
		}
		result += "}";
		return result;
	}

	@Override
	public void free() throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
