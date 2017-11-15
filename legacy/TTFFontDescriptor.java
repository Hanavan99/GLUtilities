package com.glutilities.text;

import java.util.ArrayList;
import java.util.List;

import com.glutilities.text.ttf.Table;

public class TTFFontDescriptor {

	private String name;
	private List<Table> tables = new ArrayList<Table>();

	public void add(Table t) {
		tables.add(t);
	}

	public Table getTable(Class<? extends Table> tableName) {
		for (Table t : tables) {
			if (t.getClass().equals(tableName)) {
				return t;
			}
		}
		return null;
	}

}
