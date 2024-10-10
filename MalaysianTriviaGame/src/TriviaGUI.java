import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
*
* @author A189137
*/
public class TriviaGUI extends JFrame{
    TriviaProtocol triviaProtocol;
    ImageIcon image;
    JPanel northPanel, centerPanel, bottomPanel, eastPanel, westPanel;
    JLabel questionLabel, instructionLabel, imageLabel, space1, space2, space3, space4, space5;
    JTextArea outputArea;
    JTextField answerField;
    
    public TriviaGUI(){
    	Container pane = getContentPane();
    	pane.setLayout(new BorderLayout());
    	
    	//create panels
    	northPanel = new JPanel();
    	centerPanel = new JPanel();
    	centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));
    	bottomPanel = new JPanel(new GridLayout(3,1));
    	eastPanel = new JPanel();
        eastPanel.setLayout(new FlowLayout());
        westPanel = new JPanel();
        westPanel.setLayout(new FlowLayout());
    	
    	//create components
    	questionLabel = new JLabel("Let's Play: Malaysian Trivia!");
    	questionLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
    	instructionLabel = new JLabel("Press 'enter' to begin");
    	imageLabel = new JLabel(new ImageIcon("images/thinking.png"));
    	outputArea = new JTextArea(10,30);
    	outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
    	outputArea.setEditable(false);
    	
    	JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    	
    	answerField = new JTextField(45);
    	space1 = new JLabel("      ");
        space2 = new JLabel("      ");
        space3 = new JLabel("      ");
        space4 = new JLabel("      ");
        space5 = new JLabel("      ");
    	
    	northPanel.add(questionLabel);
    	
    	bottomPanel.add(answerField);
    	bottomPanel.add(instructionLabel);
    	bottomPanel.add(space1);
    	centerPanel.add(imageLabel);
    	centerPanel.add(space2);
    	centerPanel.add(scrollPane);
    	centerPanel.add(space3);
        centerPanel.add(bottomPanel);
        
        eastPanel.add(space4);
        westPanel.add(space5);
    	
    	pane.add(northPanel, BorderLayout.NORTH);
    	pane.add(centerPanel, BorderLayout.CENTER);
    	pane.add(eastPanel, BorderLayout.EAST);
        pane.add(westPanel, BorderLayout.WEST);
        
        triviaProtocol = new TriviaProtocol();
        answerField.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String userInput = answerField.getText();
                String output = triviaProtocol.processInput(userInput);
                outputArea.append("\n" + output);
                
                String newImagePath = triviaProtocol.getImagePath();
                setImage(newImagePath);
                
                answerField.setText("");
			}  	
        });
    }
    
    private void setImage(String imagePath) {
    	ImageIcon newImage = new ImageIcon(imagePath);
    	imageLabel.setIcon(newImage);
    }
	
	public static void main(String[] args) {
        TriviaGUI frame = new TriviaGUI();
        frame.setTitle("Malaysian Trivia Game");
        frame.setSize(600, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
