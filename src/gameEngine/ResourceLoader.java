package gameEngine;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

/**
 * Utility to load file resources, including images and audio clips.
 * 
 * @author Connor Reinholdtsen
 */
public class ResourceLoader {
    /**
     * Loads an image from the resource file in this project with the given name,
     * relative to the "src" directory. The file extension type (e.g. ".png" or
     * ".jpg") must be included in the file name.
     * 
     * @param fileName the name of the image file, relative to the "src" directory
     * @return a BufferedImage from the resource file with the specified name
     */
    public static BufferedImage loadImage(String fileName) {
	try {
	    URL url = ResourceLoader.class.getClassLoader().getResource(fileName);
	    if (url == null) {
		throw new IllegalArgumentException("Could not find the file specified");
	    }
	    return ImageIO.read(url);
	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	}
    }

    /**
     * Loads an audio clip from the resource file in this project with the given
     * name, relative to the "src" directory. The file extension type must be
     * ".wav", and ".wav" must be included in the file name. The AudioClip returned
     * will automatically close after it finishes playing to prevent resource leaks.
     * 
     * @param fileName the name of the audio clip file, relative to the "src"
     *                 directory
     * @return an AudioClip from the resource file with the specified name
     */
    public static Clip loadAudioClip(String fileName) {
	try {
	    URL url = ResourceLoader.class.getClassLoader().getResource(fileName);
	    if (url == null) {
		throw new IllegalArgumentException("Could not find the file specified");
	    }
	    Clip clip = AudioSystem.getClip();
	    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
	    // to prevent resource leaks, close clips once they finish playing
	    clip.addLineListener(new LineListener() {
		@Override
		public void update(LineEvent myLineEvent) {
		    if (myLineEvent.getType() == LineEvent.Type.STOP) {
			clip.close();
		    }
		}
	    });
	    clip.open(audioInputStream);
	    audioInputStream.close();
	    return clip;
	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	}
    }

    /**
     * Loads a font from the resource file in this project with the given name,
     * relative to the "src" directory. The file extension type must be ".ttf", and
     * ".ttf" must be included in the file name. The font will automatically be
     * registered with the GraphicsEnvironment.
     * 
     * @param fileName the name of the font file, relative to the "src" directory
     * @return a Font from the resource file with the specified name
     */
    public static Font loadFont(String fileName, int fontSize) {
	// must use float when deriving font
	float size = fontSize;
	try {
	    URL url = ResourceLoader.class.getClassLoader().getResource(fileName);
	    if (url == null) {
		throw new IllegalArgumentException("Could not find the file specified");
	    }
	    InputStream inputStream = url.openStream();
	    Font font = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(size);
	    GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
	    return font;
	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	}
    }

    /**
     * Returns a BufferedImage from the given image.
     * 
     * @param image the Image to create the BufferdImage with
     * @return a BufferedImage
     */
    public static BufferedImage toBufferedImage(Image image) {
	if (image instanceof BufferedImage) {
	    return (BufferedImage) image;
	}
	BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
		BufferedImage.TYPE_INT_ARGB);
	Graphics2D g = bufferedImage.createGraphics();
	g.drawImage(image, 0, 0, null);
	g.dispose();
	return bufferedImage;
    }
}
