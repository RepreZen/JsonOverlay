package com.reprezen.jovl2;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.reprezen.jovl2.ResolutionException.ReferenceCycleException;
import com.reprezen.jovl2.model.TestModelParser;
import com.reprezen.jovl2.model.intf.Color;
import com.reprezen.jovl2.model.intf.Scalars;
import com.reprezen.jovl2.model.intf.TestModel;

public class ReferenceTests extends Assert {

	private TestModel model;

	@Before
	public void setup() throws IOException {
		model = TestModelParser.parse(ReferenceTests.class.getResource("/refTest.yaml"));
	}

	@Test
	public void testRefAccess() {
		assertEquals("Reference Test", model.getDescription());
		checkScalarsValues(model.getScalar("s1"), true, true);
		checkScalarsValues(model.getScalar("s2"), false, true);
		checkScalarsValues(model.getScalar("s3"), true, true);
		checkScalarsValues(model.getScalar("s4"), true, true);
		checkScalarsValues(model.getScalar("s5"), false, true);
		checkScalarsValues(model.getScalar("ext1"), false, false);
		checkScalarsValues(model.getScalar("ext2"), false, false);
		checkScalarsValues(model.getScalar("ext3"), true, true);
	}

	private void checkScalarsValues(Scalars s, boolean sharedRoot, boolean sharedValues) {
		assertEquals("hello", s.getStringValue());
		assertEquals(Integer.valueOf(10), s.getIntValue());
		assertEquals(3.1, s.getNumberValue());
		assertEquals(Boolean.TRUE, s.getBoolValue());
		assertEquals(Arrays.asList(1, 2, 3), s.getObjValue());
		assertEquals("abcde", s.getPrimValue());
		assertEquals(Color.BLUE, s.getColorValue());
		checkScalarsSharing(s, sharedRoot, sharedValues);
	}

	private void checkScalarsSharing(Scalars s, boolean sharedRoot, boolean sharedValues) {
		Scalars s1 = model.getScalar("s1");
		if (sharedRoot) {
			assertTrue("Scalars objects should be shared", s1 == s);
		} else if (sharedValues) {
			assertTrue("String value should be shared", s1.getStringValue() == s.getStringValue());
		}
	}

	@Test
	public void checkRecursion() {
		assertTrue(model.getScalar("s6").getEmbeddedScalars() == model.getScalar("s5"));
		assertTrue(model.getScalar("s7").getEmbeddedScalars() == model.getScalar("s7"));
		assertTrue(model.getScalar("s8a").getEmbeddedScalars() == model.getScalar("s8b"));
		assertTrue(model.getScalar("s8b").getEmbeddedScalars() == model.getScalar("s8a"));
		assertTrue(model.getScalar("ext1") == model.getScalar("ext2"));
	}

	@Test
	public void checkBadRefs() {
		assertNull(model.getScalar("badPointer"));
		checkBadRef(Overlay.of(model.getScalars()).getReference("badPointer"));
		checkBadRef(Overlay.of(model.getScalars()).getReference("cycle"));
		assertTrue(Overlay.of(model.getScalars()).getReference("cycle")
				.getInvalidReason() instanceof ReferenceCycleException);
	}

	private void checkBadRef(Reference ref) {
		assertTrue(ref.isInvalid());
		assertTrue(ref.getInvalidReason() instanceof ResolutionException);
	}

	@Test
	public void testRoots() {
		assertTrue(model == Overlay.of(model).getRoot());
		assertTrue(model == Overlay.of(model.getScalar("s1")).getRoot());
		Scalars ext1 = model.getScalar("ext1");
		assertTrue(ext1 == Overlay.of(ext1).getRoot());
		assertTrue(model == Overlay.of(model.getScalar("s3")).getRoot());

		assertTrue(model == Overlay.of(model).getModel());
		assertTrue(model == Overlay.of(model.getScalar("s1")).getModel());
		assertNull(Overlay.of(ext1).getModel());
		assertTrue(model == Overlay.of(model.getScalar("s3")).getModel());
	}

	@Test
	public void testFind() {
		assertTrue(model.getScalar("s1") == Overlay.of(model).find("/scalars/s1"));
		assertTrue(model.getScalar("s3") == Overlay.of(model).find("/scalars/s1"));
		assertTrue(model.getScalar("s3") == Overlay.of(model).find("/scalars/s3"));
		assertTrue(model.getScalar("ext1") == Overlay.of(model).find("/scalars/ext1"));
		assertTrue(model.getScalar("ext2") == Overlay.of(model).find("/scalars/ext1"));
		assertTrue(model.getScalar("ext3") == Overlay.of(model).find("/scalars/s1"));
	}
}
