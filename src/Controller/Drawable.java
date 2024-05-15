package Controller;
/**
 * Interface Drawable representing a list if drawable images or sounds.
 * Draw data string list is an array that contanins a set of the following formats (whitespace next to the colons is ok):
 * "image:fileName:x:y:width:height"
 * "rotatedImage:fileName:x:y:width:height:angle"
 * "text:yourText:x:y:fontSize"
 * "rectangle:x:y:width:height"
 * "sound:fileName"
 */
public interface Drawable {

    String[] getDrawData();
}
