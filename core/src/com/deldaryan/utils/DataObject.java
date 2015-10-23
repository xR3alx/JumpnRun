package com.deldaryan.utils;

import java.util.HashMap;

public class DataObject {

	public HashMap<String, Integer> ints = new HashMap<String, Integer>();
	public HashMap<String, Long> longs = new HashMap<String, Long>();
	public HashMap<String, Float> floats = new HashMap<String, Float>();
	public HashMap<String, Boolean> booleans = new HashMap<String, Boolean>();
	public HashMap<String, String> strings = new HashMap<String, String>();
	public HashMap<String, DataObject> objs = new HashMap<String, DataObject>();
	public HashMap<String, Object> others = new HashMap<String, Object>();
	
}
