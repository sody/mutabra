package com.mutabra.domain;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import org.greatage.domain.Repository;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class TestJdoRepository extends Assert {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	private Repository repository;

	@BeforeSuite
	public void setUp() {
		helper.setUp();
	}

	@AfterSuite
	public void tearDown() {
		helper.tearDown();
	}

//	@BeforeClass
//	public void setUpRepository() {
//		final ServiceLocator locator = ServiceLocatorBuilder.createServiceLocator(DomainModule.class);
//		repository = locator.getService(EntityRepository.class);
//	}

//	@Test
//	public void testRepository() {
//		final List<Level> levels = repository.find(Level.class, Pagination.ALL);
//		assertNotNull(levels);
//	}
//
//	@Test(invocationCount = 10)
//	public void doTest() {
//		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
//		assertEquals(ds.prepare(new Query("yam")).countEntities(), 0);
//		ds.put(new Entity("yam"));
//		ds.put(new Entity("yam"));
//		assertEquals(ds.prepare(new Query("yam")).countEntities(), 2);
//	}
}
