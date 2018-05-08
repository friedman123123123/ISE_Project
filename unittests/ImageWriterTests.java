package unittests;

import static org.junit.Assert.*;


import org.junit.Test;

import elements.Camera;
import javafx.scene.image.WritablePixelFormat;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;

public class ImageWriterTests {

	@Test
	public void writeImageTest() {
		Color white = new Color(255, 255, 255);
		java.awt.Color w = white.getColor();
		ImageWriter image = new ImageWriter("grid",500,500,50,50);
		for(int i =0; i< image.getHeight()-1; i++){
			for(int j =0; j<image.getWidth()-1;j++){
				if (((i+1)%50 == 0) || (((j+1)%50) == 0))
					image.writePixel(i, j, w);
			}
		}
		image.writeToimage();
	}

}
