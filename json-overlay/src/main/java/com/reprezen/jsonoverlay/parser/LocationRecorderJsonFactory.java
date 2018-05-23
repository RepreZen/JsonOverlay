package com.reprezen.jsonoverlay.parser;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.io.IOContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public final class LocationRecorderJsonFactory extends JsonFactory {

	@Override
	protected JsonParser _createParser(InputStream in, IOContext ctxt) throws IOException {
		return new LocationRecorderJsonParser(super._createParser(in, ctxt));
	}

	@Override
	protected JsonParser _createParser(Reader r, IOContext ctxt) throws IOException {
		return new LocationRecorderJsonParser(super._createParser(r, ctxt));
	}

	@Override
	protected JsonParser _createParser(byte[] data, int offset, int len, IOContext ctxt) throws IOException {
		return new LocationRecorderJsonParser(super._createParser(data, offset, len, ctxt));
	}

}