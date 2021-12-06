package ooga.view;

public class ImageNotFoundException extends Exception{
    public ImageNotFoundException(Class<? extends Exception> reason) {
        System.out.println(
                "Image not found: " + reason);
    }

}
