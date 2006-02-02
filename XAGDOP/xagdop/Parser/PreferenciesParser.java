package xagdop.Parser;

import java.io.File;

import xagdop.Svn.SvnUpdate;

public class PreferenciesParser extends Parser{
	private File preferenciesXML;
	private static PreferenciesParser PPInstance = null;
	
	private PreferenciesParser()
	{
		try {
			preferenciesXML = new File("xagdop/ressources/XML/preferencies.xml"); //debug
			loadTreeInMemory(preferenciesXML);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static PreferenciesParser getInstance() {
		if (PPInstance == null)
			PPInstance = new PreferenciesParser();
		return PPInstance;
	}
	
}
