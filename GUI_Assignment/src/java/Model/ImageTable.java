package Model;

/**
 * @author LOH XIN JIE
 */
import DataAccess.DBModel;
import java.io.InputStream;

public class ImageTable extends DBModel {

    private int imageID;
    private String transID;
    private String imageName;
    private String imageContentType;
    private InputStream inputImage;
    private byte[] outputImage;

    public ImageTable() {
        super("imagetable");
    }

    public ImageTable(int imageID) {
        super("imagetable");
        this.imageID = imageID;
    }

    //no image
    public ImageTable(int imageID, String imageName, String imageContentType, String transID) {
        super("imagetable");
        this.imageID = imageID;
        this.imageName = imageName;
        this.imageContentType = imageContentType;
        this.transID = transID;
    }

    //output image  byte[] -> outputStream
    public ImageTable(int imageID, String imageName, String imageContentType, String transID, byte[] outputImage) {
        super("imagetable");
        this.imageID = imageID;
        this.imageName = imageName;
        this.imageContentType = imageContentType;
        this.outputImage = outputImage;
        this.transID = transID;
    }

    //input image
    public ImageTable(int imageID, String imageName, String imageContentType, String transID, InputStream inputImage) {
        super("imagetable");
        this.imageID = imageID;
        this.imageName = imageName;
        this.imageContentType = imageContentType;
        this.inputImage = inputImage;
        this.transID = transID;
    }

    public int getImageId() {
        return imageID;
    }

    public String getImageName() {
        return imageName;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public byte[] getOutputImage() {
        return outputImage;
    }

    public InputStream getInputImage() {
        return inputImage;
    }

    public String getTransID() {
        return transID;
    }

    public void setImageId(int imageID) {
        this.imageID = imageID;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public void setOutputImage(byte[] outputImage) {
        this.outputImage = outputImage;
    }

    public void setInputImage(InputStream inputImage) {
        this.inputImage = inputImage;
    }

    public void setTransID(String transID) {
        this.transID = transID;
    }
}
