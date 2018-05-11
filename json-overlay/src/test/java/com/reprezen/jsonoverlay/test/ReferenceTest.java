package com.reprezen.jsonoverlay.test;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.reprezen.jsonoverlay.JsonOverlay;
import com.reprezen.jsonoverlay.Overlay;
import com.reprezen.jsonoverlay.Reference;
import com.reprezen.jsonoverlay.ResolutionException.ReferenceCycleException;
import com.reprezen.jsonoverlay.test.model.TestModelParser;
import com.reprezen.jsonoverlay.test.model.intf.TestModel;
import com.reprezen.jsonoverlay.test.model.intf.TestObj;

public class ReferenceTest extends Assert {

	private TestModel model = null;

	@Before
	public void setup() throws IOException {
		this.model = TestModelParser.parse(this.getClass().getResource("/referenceTests/main.yaml"));
	}

	@Test
	public void testReferenceValidity() {
		TestObj a = model.getMap("a");
		assertEquals("X value for A", a.getX());
		assertEquals("Test model for reference tests", model.getStringVal());
		checkValidMapRef("a");
		checkValidMapRef("bref");
		checkNotMapRef("b");
		checkCycleMapRef("c");
		checkCycleMapRef("x");
		checkCycleMapRef("y");
		checkCycleMapRef("z");
		checkInvalidMapRef("missingFile");
		checkInvalidMapRef("badUrl");
		checkInvalidMapRef("missingJson");
		checkInvalidMapRef("badPointer");

		checkValidListRef(0);
		checkCycleListRef(1);
		checkNotListRef(2);
		checkValidListRef(3);
		checkCycleListRef(4);
		checkCycleListRef(5);
		checkCycleListRef(6);
		checkCycleListRef(7);
		checkInvalidListRef(8);
		checkInvalidListRef(9);
		checkInvalidListRef(10);
		checkInvalidListRef(11);
	}
	
	private void checkValidMapRef(String key) {
		Reference ref = Overlay.of(model.getMaps()).getReference(key);
		assertNotNull("Map value should be a reference", ref);
		assertTrue("Reference should be valid", ref.isValid());
	}

	private void checkNotMapRef(String key) {
		Reference ref = Overlay.of(model.getMaps()).getReference(key);
		assertNull("Map value should not be a reference", ref);
	}
	
	private void checkCycleMapRef(String key) {
		Reference ref = Overlay.of(model.getMaps()).getReference(key);
		assertNotNull("Map value should be a reference", ref);
		assertTrue("Reference should be inValid", ref.isInvalid());
		assertTrue("Reference should be in a cycle", ref.getError() instanceof ReferenceCycleException);
	}

	private void checkInvalidMapRef(String key) {
		Reference ref = Overlay.of(model.getMaps()).getReference(key);
		assertNotNull("Map value should be a reference", ref);
		assertTrue("Reference should be invalid", ref.isInvalid());
	}
	
	private void checkValidListRef(int index) {
		Reference ref = Overlay.of(model.getLists()).getReference(index);
		assertNotNull("Map value should be a reference", ref);
		assertTrue("Reference should be valid", ref.isValid());
	}

	private void checkNotListRef(int index) {
		Reference ref = Overlay.of(model.getLists()).getReference(index);
		assertNull("Map value should not be a reference", ref);
	}
	
	private void checkCycleListRef(int index) {
		Reference ref = Overlay.of(model.getLists()).getReference(index);
		assertNotNull("Map value should be a reference", ref);
		assertTrue("Reference should be inValid", ref.isInvalid());
		assertTrue("Reference should be in a cycle", ref.getError() instanceof ReferenceCycleException);
	}

	private void checkInvalidListRef(int index) {
		Reference ref = Overlay.of(model.getLists()).getReference(index);
		assertNotNull("Map value should be a reference", ref);
		assertTrue("Reference should be invalid", ref.isInvalid());
	}

	@Test
	public void testSharedRefs() {
		checkShared("/description", "/stringVal");
		checkShared("/map/bref", "/map/b");
		checkShared("/list/0", "/map/b");
		checkShared("/list/0", "/list/3");
	}
	
	private void checkShared(String path1, String path2) {
		Overlay<TestModel> ovl = Overlay.of(model);
		Object val1 = Overlay.of(ovl.find(path1)).get();
		Object val2 = Overlay.of(ovl.find(path2)).get();
		assertTrue("Should be shared values", val1 == val2);
	}

	}
