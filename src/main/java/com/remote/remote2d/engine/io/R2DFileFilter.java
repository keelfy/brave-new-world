package com.remote.remote2d.engine.io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

/**
 * A filter used to denote what file types are able to be loaded by Remote2D.
 * @author Flafla2
 *
 */
public class R2DFileFilter implements FilenameFilter {
	
	/**
	 * A list of all extensions supported by the engine.  You can add to this list
	 * at startup if desired.  To add to the list, use the format <code>.xxx</code>
	 * if you want to support the ".xxx" file format.<br />
	 * 
	 * Keep in mind that using these file formats alone do NOT make them supported.
	 * All file formats supported by Remote2D <i>MUST</i> be followed by either
	 * .xml (for XML files) or .bin (for binary files).  This is strictly enforced
	 * by the engine.<br />
	 * 
	 * Also, do not add the following extensions as they are already used in the
	 * engine (you probably shouldn't save to these formats as well):
	 * <ul>
	 * <li>.anim</li>
	 * <li>.r2d</li>
	 * <li>.entity</li>
	 * </ul>
	 */
	public static ArrayList<String> extensions = new ArrayList<String>();
	
	static
	{
		extensions.add(".anim");
		extensions.add(".r2d");
		extensions.add(".entity");
	}

	@Override
	public boolean accept(File dir, String name) {
		for(String s : extensions)
		{
			if((name.toLowerCase().endsWith(s+".xml") || name.toLowerCase().endsWith(s+".bin")) && new File(dir,name).exists())
				return true;
		}
		
		return false;
	}

}
