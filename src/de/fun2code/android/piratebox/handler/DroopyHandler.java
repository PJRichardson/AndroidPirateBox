package de.fun2code.android.piratebox.handler;

import java.io.IOException;
import java.util.Locale;

import de.fun2code.android.piratebox.Constants;
import de.fun2code.android.piratebox.PirateBoxService;
import sunlabs.brazil.server.Handler;
import sunlabs.brazil.server.Request;
import sunlabs.brazil.server.Server;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Allow Droopy like file uploads on port 8080
 * 
 * @author joschi
 *
 */
public class DroopyHandler implements Handler {
	private String prefix;
	private SharedPreferences preferences;
	private String fupURL = "/fup.xhtml";

	@Override
	public boolean init(Server server, String prefix) {
		this.prefix = prefix;
		preferences = PreferenceManager.getDefaultSharedPreferences(PirateBoxService.getService());
		
		return true;
	}

	@Override
	public boolean respond(Request request) throws IOException {
		String contentType = request.headers.get("Content-Type");
		
		if(contentType != null &&preferences.getBoolean(Constants.PREF_EMULATE_DROOPY, true)
				&& contentType.toLowerCase(Locale.US).contains("multipart")) {
			/*
			 * Rewrite current and original url.
			 */
			request.url = fupURL;
			request.props.setProperty("url.orig", fupURL);
		}
		
		// always return false
		return false;
	}

}
