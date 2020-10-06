package com.unrec.ituneslibrary.parser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface XmlParser {

    void setFile(File file);

    void setFile(InputStream inputStream) throws IOException;

    void parse() throws Exception;
}
