package edu.uth.resources;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.regex.Pattern;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * This class keeps a cache of all Images, Icons, and ImageIcons loaded by the
 * application. Once an image has been loaded, it is put in the cache, and never removed.
 * If you're only using an image once in your object, you should not need to keep a local
 * pointer to it. It is safe to use the GhcImageLoader.get???() call as your reference.
 *
 */

public class GhcImageLoader {

	private static final long serialVersionUID = -1;
	public final static String BLUE_SKY = "icons/blueSky.jpg";
	public final static String BEACHBALL_RED = "icons/beachBall_red.png";
	public final static String STV_APP = "icons/stv_app.png";
	public final static String VPT_APP = "icons/beachBall_red.png";
	public final static String BULLET_GREEN = "icons/bullet_green.png";
	
	public final static String CUBE = "icons/cube.png";
	public final static String UTH_LOGO = "icons/uth-logo.png";
	public final static String ZIPCODEAPP = "icons/zipCodeApp.png";
	public final static String RADXLOGO = "icons/radxrad.jpg";
	public final static String SETTINGS = "icons/settings.png";
	public final static String RUN = "icons/runExtraSmall.png";
	public final static String CALENDAR = "icons/calendar.png";
	public final static String STUDYID = "icons/studyID.png";
	public final static String QAC = "icons/qac.png";
	public final static String REFRESH = "icons/refresh3.png";
	
	public final static String DELETE_XS = "icons/delete-xs.gif";
	public final static String SAVE_XS = "icons/save-xs.png";

	/**
	 * This constant is the package path to the image loader using the java ClassLoader
	 * resource notion, i.e. "/" instead of "." between package names.
	 *
	 * @see #getImageURL
	 */
	public static final String IMAGE_RESOURCE_PATH_PREFIX = GhcImageLoader.class.getPackage().getName().replace('.', '/') + "/";

	/**
	 * This class exists for static access. We don't want people making their own copy, so
	 * the constructor is private. This method does nothing.
	 **/
	private GhcImageLoader() {
	}

	/**
	 * @param fileName A path to the desired image.
	 * @return An Image of the desired image. Will return null if the image is not found.
	 **/
	public static Image getImage(String fileName) {
		Image rtn = null;
		ImageIcon icon = null;

		if ((fileName != null) && ((icon = GhcImageLoader.getImageIcon(fileName)) != null))
			rtn = icon.getImage();

		return rtn;
	}

	/**
	 * @param fileName A path to the desired icon.
	 * @return An Icon of the desired icon. Will return null if the image is not found.
	 **/
	public static Icon getIcon(String fileName) {
		return GhcImageLoader.getImageIcon(fileName);
	}
	
	/**
	 * @param fileName A path to the desired image.
	 * @return An ImageIcon of the desired image. Will return null if the image is not
	 *         found.
	 **/
	public static ImageIcon getImageIcon(String fileName) {
		ImageIcon icon = null;
		String realPath = IMAGE_RESOURCE_PATH_PREFIX + fileName;

		// try to find a copy by name. return null if we fail.
		try {
			java.io.BufferedInputStream is = new java.io.BufferedInputStream(
					GhcImageLoader.class.getClassLoader().getResourceAsStream(realPath));

			byte[] buffer = new byte[is.available()];
			is.read(buffer);
			icon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(buffer));
			is.close();
		} catch (IOException ioe) {
		}

		return icon;
	}

	/**
	 * This method returns an instace of URL representing the specified file as a resource
	 * in the classpath. This URL can be used in Swing components that support HTML
	 * rendering.
	 *
	 * @param imageFileName the name of the image file
	 *
	 * @return a URL representing the requested image
	 *
	 */
	public static URL getImageURL(final String imageFileName) {
		final String resourcePath = IMAGE_RESOURCE_PATH_PREFIX + imageFileName;
		final URL systemResource = GhcImageLoader.class.getClassLoader().getResource(resourcePath);
		return systemResource;
	}

	public static void preloadImages() {
		new Thread(new ImagePreloaderRunnable()).start();
	}

	protected static class ImagePreloaderRunnable implements Runnable {
		public void run() {
			Pattern pattern = Pattern.compile(".*\\.(jpg|gif|png)", Pattern.CASE_INSENSITIVE);
			Field[] fields = GhcImageLoader.class.getDeclaredFields();
			for (int index = fields.length - 1; index >= 0; index--) {
				Field field = fields[index];
				if (Modifier.isPublic(field.getModifiers()) &&
						Modifier.isStatic(field.getModifiers()) &&
						Modifier.isFinal(field.getModifiers()) &&
						String.class.equals(field.getType())) {
					try {
						String imgName = (String) (field.get(GhcImageLoader.class));
						if (pattern.matcher(imgName).matches()) {
							if (GhcImageLoader.class.getClassLoader().getResource(IMAGE_RESOURCE_PATH_PREFIX + imgName) != null) {
								getImageIcon(imgName);
							}
						}
					} catch (Exception e) {
					}
				}
			}
		}
	}
}
