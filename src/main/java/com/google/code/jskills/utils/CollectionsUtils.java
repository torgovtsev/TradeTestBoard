package com.google.code.jskills.utils;

import java.util.List;

public final class CollectionsUtils {

	private CollectionsUtils() {
	}

	public static <T> List<T> castList(final List source) {
		return (List<T>) source;
	}

}
