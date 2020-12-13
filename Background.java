import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Background extends JPanel {

	BufferedImage image;

	public Background(String imageName) {
		try {
			image = ImageIO.read(NewView.class.getResource(imageName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
