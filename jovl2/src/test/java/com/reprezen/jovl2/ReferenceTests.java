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
		checkScalarsValues(model.getScalar("s1"), true);
		checkScalarsValues(model.getScalar("s2"), false);
		checkScalarsValues(model.getScalar("s3"), true);
		checkScalarsValues(model.getScalar("s4"), true);
		checkScalarsValues(model.getScalar("s5"), false);
	}

	private void checkScalarsValues(Scalars s, boolean sharedRoot) {
		assertEquals("hello", s.getStringValue());
		assertEquals(Integer.valueOf(10), s.getIntValue());
		assertEquals(3.1, s.getNumberValue());
		assertEquals(Boolean.TRUE, s.getBoolValue());
		assertEquals(Arrays.asList(1, 2, 3), s.getObjValue());
		assertEquals("abcde", s.getPrimValue());
		assertEquals(Color.BLUE, s.getColorValue());
		checkScalarsSharing(s, sharedRoot);
	}

	private void checkScalarsSharing(Scalars s, boolean sharedRoot) {
		Scalars s1 = model.getScalar("s1");
		if (sharedRoot) {
			assertTrue("Scalars objects should be shared", s1 == s);
		} else {
			assertTrue("String value should be shared", s1.getStringValue() == s.getStringValue());
		}
	}

	@Test
	public void checkRecursion() {
		assertTrue(model.getScalar("s6").getEmbeddedScalars() == model.getScalar("s5"));
		assertTrue(model.getScalar("s7").getEmbeddedScalars() == model.getScalar("s7"));
		assertTrue(model.getScalar("s8a").getEmbeddedScalars() == model.getScalar("s8b"));
		assertTrue(model.getScalar("s8b").getEmbeddedScalars() == model.getScalar("s8a"));
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
}
