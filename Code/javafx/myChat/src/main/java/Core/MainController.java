package Core;

import javafx.scene.SnapshotParameters;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Samir Moustafa on 5/17/2017.
 */
public class MainController {

    public void handImage(ImageView imageView, File location, TextField browseText) throws IOException {
        double centerX, centerY, radius;
        double realWidth, realHeight, targetLength = 300;
        double divNo = 1;
        BufferedImage bimg = null;
        bimg = ImageIO.read(location);
        realWidth = bimg.getWidth();
        realHeight = bimg.getHeight();
        if (realHeight >= realWidth) {
            divNo = targetLength / realWidth;
            realHeight *= divNo;
            realWidth *= divNo;
        }
        if (realHeight < realWidth) {
            divNo = targetLength / realHeight;
            realHeight *= divNo;
            realWidth *= divNo;
        }
        WritableImage wr = null;
        // convert image buffer to image view
        wr = new WritableImage(bimg.getWidth(), bimg.getHeight());
        PixelWriter pw = wr.getPixelWriter();
        for (int x = 0; x < bimg.getWidth(); x++)
            for (int y = 0; y < bimg.getHeight(); y++) {
                pw.setArgb(x, y, bimg.getRGB(x, y));
            }
        if (wr == null)
            throw new IOException();
        ImageView imView = new ImageView(wr);
        imageView.setImage(imView.getImage());
        imageView.setFitWidth(realWidth);
        imageView.setFitHeight(realHeight);
        centerX = imageView.getFitWidth() / 2;
        centerY = imageView.getFitHeight() / 2;
        radius = Math.min(realWidth, realHeight) / 2;
        Circle clip = new Circle();
        clip.setCenterX(centerX);
        clip.setCenterY(centerY);
        clip.setRadius(radius);
        imageView.setClip(clip);
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage image = imageView.snapshot(parameters, null);
        // remove the rounding clip so that our effect can show through.
        imageView.setClip(null);
        // apply a shadow effect.
        imageView.setEffect(new DropShadow(radius, Color.WHITE));
        // store the rounded image in the imageView.
        imageView.setImage(image);
        browseText.setText(location.getPath());
    }

    public void browsController(Stage Window, ImageView imageView, TextField browseText) {
        double centerX, centerY, radius;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("View Pictures");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        File openFile = fileChooser.showOpenDialog(Window);
        try {
            Image image = new Image(openFile.toURI().toString());
            imageView.setImage(image);
            handImage(imageView, openFile, browseText);
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected boolean isValid(String regex, String value) {
        return value.matches(regex);
    }
}
