package de.robertron.myternity2.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Configuration {

	private static Properties properties;

	static {
		try {
			properties = new Properties();
			properties.load( new FileInputStream( "etc/myternity2.properties" ) );
		} catch ( final FileNotFoundException e ) {
			e.printStackTrace();
		} catch ( final IOException e ) {
			e.printStackTrace();
		}
	}

	public static String getString( final Key key ) {
		return (String) properties.get( key.getKey() );
	}

	public static int getInt( final Key key ) {
		return Integer.valueOf( getString( key ) );
	}

	public static double getDouble( final Key key ) {
		return Double.valueOf( getString( key ) );
	}

	public static Object getObject( final Key key ) {
		return properties.get( key.getKey() );
	}

}
