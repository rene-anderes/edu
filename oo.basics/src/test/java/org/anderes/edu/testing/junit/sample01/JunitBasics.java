package org.anderes.edu.testing.junit.sample01;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class JunitBasics {

	private Collection<String> collection;

	@BeforeClass
	public static void oneTimeSetUp() {
		System.out.println("@BeforeClass - oneTimeSetUp");
	}

	@AfterClass
	public static void oneTimeTearDown() {
		System.out.println("@AfterClass - oneTimeTearDown");
	}

	@Before
	public void setUp() {
		collection = new ArrayList<String>();
		System.out.println("@Before - setUp");
	}

	@After
	public void tearDown() {
		collection.clear();
		System.out.println("@After - tearDown");
	}

	@Test
	public void shouldBeEmptyCollection() {
		assertTrue(collection.isEmpty());
		System.out.println("@Test - shouldBeEmptyCollection");
	}

	@Test
	public void shouldBeOneItemCollection() {
		collection.add("itemA");
		assertEquals(1, collection.size());
		System.out.println("@Test - shouldBeOneItemCollection");
	}
}
