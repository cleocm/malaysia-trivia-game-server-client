import java.net.*;
import java.io.*;
import java.util.Random;
import javax.sound.sampled.*;

/**
 *
 * @author A188384 & A189137
 */
public class TriviaProtocol {
    protected static final int WAITING = 0;
    private static final int START = 1;
    
    protected static final int CHECKANSWER = 2;
    private static final int ANOTHER = 3;

    private static final int NUMQUESTIONS = 10;

    private int state = WAITING;
    private int currentQuestion = 0;
    private Random random = new Random();
    
    private Clip correctClip;
    private Clip wrongClip;
    private String imagePath;

    private String[] trivia_question = {"What is the national dish of Malaysia, consisting of rice cooked in coconut milk, typically served with anchovies, peanuts, boiled eggs, and a spicy chili paste?", 
                                        "Which mountain in Malaysia has the highest peak in Southeast Asia?", 
                                        "Which festival, also known as the Festival of Lights, is widely celebrated by Malaysians of Indian descent?",
                                        "What is the popular Malaysian street food made of skewered and grilled meat, usually served with a peanut sauce?",
                                        "Which iconic twin towers are a prominent landmark in Kuala Lumpur?",
                                        "What is the capital city of Malaysia?",
                                        "How many stripes are there on the Malaysian national flag, Jalur Gemilang?",
                                        "Which one of the following historical figures is not associated with the fight for independence of Malaysia?",
                                        "Which of the following is not a Malaysian state?",
                                        "In Malaysian culture, what is the traditional art of batik commonly used for?"};
   
    private String[][] trivia_options = {{"Pad Thai", "Sushi", "Nasi Lemak", "Kimchi"},
                                        {"Mount Kinabalu", "Mount Everest", "Mount Fuji", "Mount Rinjani"},
                                        {"Hari Raya Aidilfitri", "Chinese New Year", "Deepavali", "Thaipusam"},
                                        {"Pad Thai", "Nasi Goreng", "Hainanese Chicken Rice", "Satay"},
                                        {"Petronas Towers", "Taipei 101", "Burj Khalifa", "Willis Tower"},
                                        {"Putrajaya", "Kuala Lumpur", "Penang", "Johor Bahru"},
                                        {"10", "12", "14", "16"},
                                        {"Tunku Abdul Rahman", "Datuk Onn Jaafar", "Tun Dr. Ismail Abdul Rahman", "P.Ramlee"},
                                        {"Sabah", "Penang", "Sumatra", "Malacca"},
                                        {"Pottery", "Wood carving", "Fabric decoration", "Paper cutting"}
                                        };
    
    private String[] trivia_answers = { "Nasi Lemak",
                                        "Mount Kinabalu",
                                        "Deepavali",
                                        "Satay",
                                        "Petronas Towers",
                                        "Kuala Lumpur",
                                        "14",
                                        "P.Ramlee",
                                        "Sumatra",
                                        "Fabric decoration"};

    public String processInput(String theInput) {
        String theOutput = null;

        if (state == WAITING) {
        	imagePath = "images/thinking.png";
            theOutput = "Hello! Welcome to our Malaysian Trivia! Type 'Start' when you're ready.\n";
            state = START;
        } 
        else if (state == START) {
            if (theInput.equalsIgnoreCase("Start")) {
            	imagePath = "images/thinking.png";
                String optionsString = "";
                for (String option : trivia_options[currentQuestion]) {
                    optionsString = optionsString.concat(option).concat(", ");
                }

                // Remove the trailing comma and space
                optionsString = optionsString.substring(0, optionsString.length() - 2);
                theOutput = trivia_question[currentQuestion] +" \n\nOptions: "+ optionsString;
                state = CHECKANSWER;
            } 
            else {
            	imagePath = "images/thinking.png";
                theOutput = "Huh? Read the instructions properly pls :/ " +
			    "Try Again! Type 'Start' when you're ready.";
            }
        }
        else if (state == CHECKANSWER) {
            if (theInput.equalsIgnoreCase(trivia_answers[currentQuestion])) {
            	playCorrectSound();
            	imagePath = "images/correct.png";
                theOutput = trivia_answers[currentQuestion] + " is Correct! Play again? (y/n)\n";
                state = ANOTHER;
            } 
            else {
            	playWrongSound();
            	imagePath = "images/incorrect.png";
                theOutput = theInput+" is WRONG! Correct answer is: " + 
			    trivia_answers[currentQuestion] + 
			    ". Try Again! Type 'Start' when you're ready. \n";
                state = START;
            }
        } 
        else if (state == ANOTHER) {
            if (theInput.equalsIgnoreCase("y")) {
                theOutput = "Let's go! Type 'Start' when you're ready. \n";
                currentQuestion = random.nextInt(NUMQUESTIONS);
                state = START;
            } 
            else {
                theOutput = "Bye bye! Jumpa lagi!";
                state = WAITING;
            }
        }
        return theOutput;
    }

	public TriviaProtocol() {
        try {
            AudioInputStream correctStream = AudioSystem.getAudioInputStream(new File("sfx/right_alt.wav"));
            correctClip = AudioSystem.getClip();
            correctClip.open(correctStream);

            AudioInputStream wrongStream = AudioSystem.getAudioInputStream(new File("sfx/wrong.wav"));
            wrongClip = AudioSystem.getClip();
            wrongClip.open(wrongStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
	
	private void playWrongSound() {
    	if (wrongClip != null) {
            wrongClip.setFramePosition(0);
            wrongClip.start();
        }
	}

	private void playCorrectSound() {
		if (correctClip != null) {
            correctClip.setFramePosition(0);
            correctClip.start();
        }
	}
    
	public int getGameState() {
        return state;
    }
	
	public String getImagePath() {
		return imagePath;
	}
}

