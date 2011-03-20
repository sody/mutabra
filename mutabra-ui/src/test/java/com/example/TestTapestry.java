package com.example;

import org.apache.tapestry5.ioc.internal.services.PropertyAccessImpl;
import org.apache.tapestry5.ioc.services.ClassPropertyAdapter;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.testng.annotations.Test;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class TestTapestry {

	@Test
	public void test() {
		final PropertyAccess access = new PropertyAccessImpl();
		final ClassPropertyAdapter adapter = access.getAdapter(AnotherBean.class);
	}

	public static interface Bean<T> {

		T getValue();
	}

	public static interface AnotherBean extends Bean<Long> {

		String getName();
	}
}
