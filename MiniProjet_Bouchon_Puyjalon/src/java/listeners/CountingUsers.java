package listeners;


import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Web application lifecycle listener.
 *
 * @author rbastide
 */
public class CountingUsers implements HttpSessionListener {
	private int numberOfUsers = 0;

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// On incrémente le nombre d'utilisateurs
		se.getSession().getServletContext().log("Creating session");
		numberOfUsers ++;
		// On stocke ce nombre dans le contexte d'application
		se.getSession().getServletContext().setAttribute("numberConnected", numberOfUsers);
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		se.getSession().getServletContext().log("Destroying session");
		numberOfUsers --;
		se.getSession().getServletContext().setAttribute("numberConnected", numberOfUsers);
	}
}
