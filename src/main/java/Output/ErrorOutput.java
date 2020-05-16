package Output;

public class ErrorOutput implements Output{
    final String type = "error";
    public String message;

    public ErrorOutput(String message) {
        this.message = message;
    }
}
