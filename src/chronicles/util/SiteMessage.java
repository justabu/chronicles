package chronicles.util;

import javax.servlet.http.HttpServletRequest;

public enum SiteMessage {
	storyDeleted("1", "Your story has been deleted."),
	deleteStoryError("2", "Error deleting."),
	editSucessful("3", "Your story has been edited.");
	
	
	private final String parameterKey;
	private final String message;
	
	private SiteMessage(String parameterKey, String message) {
		this.parameterKey = parameterKey;
		this.message = message;
	}
		
	public String getMessage() {
		return message;
	}
	
	public static String messageFor(HttpServletRequest request) {
		String ref = request.getParameter("ref");
		if (ref!=null) {
			for (SiteMessage siteMessage : values()) {
				if(siteMessage.parameterKey.equals(ref)){
					return siteMessage.message;
				}
			}
		}
		return "";
	}

	
}