package com.google.code.jskills.utils.dal;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.cfg.reveng.DefaultReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.TableIdentifier;

public class JskillsRevengStrategy extends DefaultReverseEngineeringStrategy {

	public static final String ENTITY_SUFFIX = "Entity";

	private static final List<String> EXCLUDE_TABLES;

	static {
		EXCLUDE_TABLES = new ArrayList<String>();
		EXCLUDE_TABLES.add("DATABASECHANGELOG");
		EXCLUDE_TABLES.add("DATABASECHANGELOGLOCK");
	}

	public JskillsRevengStrategy() {
		super();
	}

	@Override
	public String tableToClassName(TableIdentifier tableIdentifier) {
		String defaultName = super.tableToClassName(tableIdentifier);
		return String.format("%s%s", defaultName, ENTITY_SUFFIX);
	}

	@Override
	public boolean excludeTable(TableIdentifier ti) {
		boolean result = super.excludeTable(ti);

		if (EXCLUDE_TABLES.contains(ti.getName())) {
			result = true;
		}

		return result;
	}
}
