import java.io.IOException;
import java.net.URI;
import java.util.*;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> stringList = new ArrayList<String>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            String output = "";
            for (int i = 0; i < stringList.size(); i++) {
                output += stringList.get(i) + ", ";
            }
            return String.format("List of words: %s", output);
        } else {
            System.out.println("Path: " + url.getPath());
            String[] parameters = url.getQuery().split("=");
            if (url.getPath().contains("/add")) {
                if (parameters[0].equals("s")) {
                    stringList.add(parameters[1]);
                    return String.format("Added word: %s", stringList.get(stringList.size() - 1));
                }
            }
            String searchOutput = "";
            
            if (url.getPath().contains("search")) {
                if (parameters[0].equals("s")) {
                    for (int i = 0; i < stringList.size(); i++) {
                        if (stringList.get(i).contains(parameters[1])) {
                            searchOutput += stringList.get(i) + ", ";
                        }
                    }
                }
                return String.format("Matching words: %s", searchOutput);
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
