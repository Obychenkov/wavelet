import java.io.IOException;
import java.net.URI;
import java.util.*;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    String[] stringList = new String[10];
    int size = 0;

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("List of words: %s", Arrays.toString(stringList));
        } else {
            System.out.println("Path: " + url.getPath());
            String[] parameters = url.getQuery().split("=");
            if (url.getPath().contains("/add")) {
                if (parameters[0].equals("s")) {
                    stringList[size] = parameters[1];
                    System.out.println(stringList[0]);
                    size++;
                    return String.format("Added word: %s", stringList[size - 1]);
                }
            }
            String output = "";
            
            if (url.getPath().contains("search")) {
                if (parameters[0].equals("s")) {
                    for (int i = 0; i < size; i++) {
                        if (stringList[i].contains(parameters[1])) {
                            output += stringList[i] + " ";
                        }
                    }
                }
                return String.format("Matching words: %s", output);
            }
            return ("Invalid input");
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
