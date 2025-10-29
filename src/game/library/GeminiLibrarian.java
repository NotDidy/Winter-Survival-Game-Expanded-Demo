package game.library;

//import edu.monash.fit2099.engine.actions.Action;
//import edu.monash.fit2099.engine.actions.ActionList;
//import edu.monash.fit2099.engine.actors.Actor;
//import edu.monash.fit2099.engine.displays.Display;
//import edu.monash.fit2099.engine.positions.GameMap;
//import game.actions.TalkAction;
//import game.interfaces.ActionCapable;
//import game.interfaces.Speakable;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
///**
// * The `GeminiLibrarian` class represents a librarian that uses the Gemini AI model
// * to provide responses to user prompts. It implements both `ActionCapable` and `Speakable`
// * interfaces to provide actions and speaking capabilities.
// */
//public class GeminiLibrarian extends Actor implements ActionCapable, Speakable {
//
//    /** The API key for accessing the Gemini AI model. */
//    private static final String API_KEY = ""; // Replace with  actual API key
//
//    /** The request URL for generating content with the Gemini AI model. */
//    private static final String REQUEST_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + API_KEY;
//
//    public GeminiLibrarian(String name, char displayChar, int hitpoints){
//        super(name, displayChar, hitpoints);
//    }
//    @Override
//    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display){
//        display.println(String.valueOf(this));
//        return lastAction;
//    }
//
//    @Override
//    public String getName() {
//        return name;
//    }
//
//    @Override
//    public String speak() {
//        // Ask the user for input
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Enter a prompt for the Gemini Librarian: ");
//        String userPrompt = scanner.nextLine();
//
//        // Call the Gemini API and return the response
//        return geminiCall(userPrompt);
//    }
//
//    /**
//     * Makes a call to the Gemini AI model with the provided prompt.
//     *
//     * @param prompt The prompt to send to the Gemini AI model.
//     * @return The response from the Gemini AI model or a message indicating that the AI is offline.
//     */
//    private String geminiCall(String prompt) {
//        String result = "";
//        try {
//            // Create the URL object
//            URL url = new URL(REQUEST_URL);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//            // Set request method to POST
//            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Content-Type", "application/json; utf-8");
//            connection.setRequestProperty("Accept", "application/json");
//            connection.setDoOutput(true);
//
//            // Create JSON payload
//            String jsonInputString = "{\"contents\": [{\"parts\": [{\"text\": \"" + prompt + "\"}]}]}";
//
//            // Send JSON input
//            try (OutputStream os = connection.getOutputStream()) {
//                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
//                os.write(input, 0, input.length);
//            }
//
//            // Get the response code
//            int code = connection.getResponseCode();
//            System.out.println("Response Code: " + code);
//
//            // Read the response
//            try (BufferedReader br = new BufferedReader(
//                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
//                String responseLine;
//                while ((responseLine = br.readLine()) != null) {
//                    if (responseLine.trim().startsWith("\"text\": ")) {
//                        result = responseLine.trim().substring(9, responseLine.trim().length() - 1);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            return "I am sorry, I cannot help you with that. Gemini offline at the moment.";
//        }
//        if (result == null || result.isEmpty()) {
//            result = "I am sorry, I cannot help you with that.";
//        }
//        return result;
//    }
//
//    @Override
//    public List<Action> allowableActions() {
//        List<Action> actions = new ArrayList<>();
//        actions.add(new TalkAction(this));
//        return actions;
//    }
//}

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.TalkAction;
import game.interfaces.ActionCapable;
import game.interfaces.Speakable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The GeminiLibrarian is an NPC Actor that can talk to the player.
 * Initially, it produces mock dialogue until connected to Gemini API.
 */
public class GeminiLibrarian extends Actor implements ActionCapable, Speakable {

    private final Random random = new Random();

    public GeminiLibrarian(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String speak() {
        // Simple random dialogue for now
        String[] lines = {
                "Hello traveler! I'm the keeper of forgotten knowledge.",
                "Books are the windows to the soul — and I’ve read quite a few.",
                "Did you come here seeking wisdom or adventure?",
                "One day, I might speak with the voice of the Gemini AI!"
        };
        return lines[random.nextInt(lines.length)];
    }

    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();

        // Only allow talk if the other actor is not this one
        if (otherActor != this) {
            actions.add(new TalkAction(this));
        }
        return actions;
    }

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display){
        return lastAction;
    }

    @Override
    public List<Action> allowableActions() {
        return List.of();
    }
}