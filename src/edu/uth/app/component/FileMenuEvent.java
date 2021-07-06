package edu.uth.app.component;

import java.io.File;
import java.util.EventObject;

public class FileMenuEvent extends EventObject {
	private File _file;

	public FileMenuEvent( Object source, File file ) {
		super( source );
		_file = file;
	}
	public File file() {
		return _file;
	}

}
