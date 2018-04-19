/**
 * 
 */
package processor_GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
	
import processor_Exception.FileIsEmptyException;
import processor_ProcessorMachine.Processor;

/**
 * This class is the main class of the program, provides GUI and starts the program.
 * This program reads a html source file of a website of Thefix.com and writes the following info of the web page to a file:
 * 
 * 
 * @author Van Duong
 *
 */
public class Gui extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4749453246374638728L;
	private static final int FRAME_WIDTH = 450;
	private static final int FRAME_HEIGHT = 400;
	private Container c;
	private JPanel urlPanel;
	private JLabel lbURL;
	private JTextField tfURL;
	private JPanel startPanel;
	private JButton btStart;
	private JButton btQuit;
	private JPanel promptPanel;
	private JPanel panel;
	private URL url;
	private JLabel lbInvalidURL;
	private Processor processor;
	private JLabel lbDone;
	private boolean isValid;
	private JButton btReset;
	private JPanel outputPanel;
	private JLabel outPutlb;
	private JTextArea console;
	private JScrollPane scrollPane;
	private InputStream in;
	private JPanel htmlPane;
	private JPanel topFixPane;
	private JTabbedPane tabbedPane;
	private JLabel campaignName;
	private JTextField campaignField;
	private JLabel subjectLb;
	private JTextField subjectField;
	private JButton submitBtn;
	private JLabel error;
	private JButton quitBtn;
	private JPanel overallPane;
//	private JPanel errorBagLayout;
	private JPanel buttonBagLayout;
	private JButton resetLinks;
	private ArrayList<String> errorList;
//	private JProgressBar progressBar;
	private JTextArea errorArea;
	static Integer indexer = 1;
    static List<JLabel> labelList = new ArrayList<JLabel>();
    static List<JTextField> textFieldList = new ArrayList<JTextField>();
	//private JPanel northPanel;
	/**
	 * constructs the GUI
	 */
	public Gui() {
		c = getContentPane();
		processor = Processor.getInstance();
		//Set JFrame info
		setTitle("HTML Processor");
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		c.setLayout(new GridLayout(1, 1));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			
		//create tabbed panel that contains 2 main panels
		tabbedPane = new JTabbedPane();
		
		//create panel for html processor
		htmlPane = new JPanel(new GridLayout(2,0));
		
		//set panel for url input
				urlPanel = new JPanel();
				urlPanel.setLayout(new FlowLayout());
				
				//creat text field, button and lable in the urlPanel
				lbURL = new JLabel("Insert URL:");
				tfURL = new JTextField(25);
				
				//add the above components to urlPanel
				urlPanel.add(lbURL);
				urlPanel.add(tfURL);
				
				//set panel for Start and Quit buttons
				startPanel = new JPanel();
				startPanel.setLayout(new FlowLayout());
				
				//create buttons for startPanel
				btStart = new JButton("Start");
				btQuit = new JButton("Quit");
				btReset = new JButton("Reset");
				
				//add buttons to startPanel
				startPanel.add(btStart);
				startPanel.add(btReset);
				startPanel.add(btQuit);
				
				//set promt Panel
				promptPanel = new JPanel();
				promptPanel.setLayout(new GridLayout(2, 0));
				lbInvalidURL = new JLabel("URL Is Invalid");
				lbDone = new JLabel("COMPLETED!!!");
				
				promptPanel.add(lbInvalidURL);
				promptPanel.add(lbDone);
				
				lbInvalidURL.setVisible(false);
				lbDone.setVisible(false);
				
				//set a panel that contains promptPanel, URLPanel, and startPanel
				panel = new JPanel();
				panel.setLayout(new BorderLayout());

				//set a panel that contains output
				outputPanel = new JPanel();
				outputPanel.setLayout(new GridLayout(2,0));
				
				outPutlb = new JLabel("Output:");
				console = new JTextArea();
				console.setLineWrap(true);
				scrollPane = new JScrollPane(console);
				
				outputPanel.add(outPutlb);
				outputPanel.add(scrollPane);
				
				//add all panels to container
				panel.add(urlPanel, BorderLayout.NORTH);
				panel.add(startPanel, BorderLayout.CENTER);
				panel.add(promptPanel, BorderLayout.SOUTH);

				htmlPane.add(panel);
				htmlPane.add(outputPanel);
				
		//create panel for top fix
		overallPane = new JPanel(new BorderLayout());
		topFixPane = new JPanel(new GridBagLayout());
		overallPane.add(topFixPane, BorderLayout.NORTH);
		//create label and text field for campaign and email subject
		campaignName = new JLabel("Campaign Name: ");
		String date = new SimpleDateFormat("yyyy MMM dd").format(new Date());
		campaignField = new JTextField(25);
		campaignField.setText("Newsletter "+date);
        GridBagConstraints name = new GridBagConstraints();
     // compaign name constraints
        name.gridx = 0;
        name.gridy = 0;
        topFixPane.add(campaignName, name);

        //compaign text field name constraints
        GridBagConstraints textField = new GridBagConstraints();
        textField.gridx = 1;
        textField.gridy = 0;
        topFixPane.add(campaignField, textField);
       
		subjectLb = new JLabel("Email Subject: ");
		subjectField = new JTextField(25);
		subjectField.setText("Best of the Week: ");
		
        GridBagConstraints subject = new GridBagConstraints();
     // subject constraints
        subject.gridx = 0;
        subject.gridy = 1;
        topFixPane.add(subjectLb, subject);
        
        // subject field constraints
        GridBagConstraints field = new GridBagConstraints();
        // subject constraints
           field.gridx = 1;
           field.gridy = 1;
           topFixPane.add(subjectField, field);

        // Create constraints
        GridBagConstraints textFieldConstraints = new GridBagConstraints();
        GridBagConstraints labelConstraints = new GridBagConstraints();

        // Add labels and text fields
        for(int i = 0; i < 6; i++)
        {
        	// Create label and text field for links
            textFieldList.add(new JTextField(25));
            labelList.add(new JLabel("Link " + indexer));
            // Text field constraints
            textFieldConstraints.gridx = 1;
            textFieldConstraints.gridy = i+2;

            // Label constraints
            labelConstraints.gridx = 0;
            labelConstraints.gridy = i+2;

            // Add them to panel
            topFixPane.add(textFieldList.get(i), textFieldConstraints);
            topFixPane.add(labelList.get(i), labelConstraints);
            indexer++;
        }
        
//        errorBagLayout = new JPanel(new GridBagLayout());
        errorArea = new JTextArea();
        errorArea.setEditable(false);
        errorArea.setEnabled(false);
        JScrollPane scrollPanel = new JScrollPane(errorArea);
        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        overallPane.add(scrollPanel, BorderLayout.CENTER);
                
        //add submit button to tol(pFixPane
        buttonBagLayout = new JPanel (new GridBagLayout());
		submitBtn = new JButton("Submit");
		submitBtn.addActionListener(this);
        GridBagConstraints submitConstraints = new GridBagConstraints();
        submitConstraints.gridy = 0;
        submitConstraints.gridx = 0;
        buttonBagLayout.add(submitBtn, submitConstraints);
        
       //add reset button to overallPane
        resetLinks = new JButton("Clear");
        resetLinks.addActionListener(this);
        GridBagConstraints resetBtnConstraints = new GridBagConstraints();
        resetBtnConstraints.gridy = 0;
        resetBtnConstraints.gridx = 2;
        buttonBagLayout.add(resetLinks, resetBtnConstraints);
        
      //add quit button to overalPane
        quitBtn = new JButton("Quit");
        quitBtn.addActionListener(this);
        GridBagConstraints quitBtnConstraints = new GridBagConstraints();
        quitBtnConstraints.gridy = 0;
        quitBtnConstraints.gridx = 3;
        buttonBagLayout.add(quitBtn, quitBtnConstraints);
		overallPane.add(buttonBagLayout, BorderLayout.SOUTH);
		
		tabbedPane.addTab("HTML Processor", null, htmlPane, null);
		tabbedPane.addTab("Best of the Quick Fix", null, overallPane, null);
		c.add(tabbedPane);
		//add ActionListerner to all buttons
		btStart.addActionListener(this);
		btQuit.addActionListener(this);
		btReset.addActionListener(this);
		
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) { 
			if(e.getSource() == btStart) { 
			isValid = true; 
			if(tfURL.getText() == null) {
				isValid = false;
			}
				try {
					readURL();
					if(isValid) {
						lbInvalidURL.setVisible(false);
						writeToConsole();
//						writeToFile();
					}
				} catch (MalformedURLException e1) {
					lbInvalidURL.setVisible(true);
					isValid = false;
					e1.printStackTrace();
				} catch (IOException a) {
					isValid = false;
				}
		}
	
 		if(e.getSource() == btQuit) {
			System.exit(0);
		}
 		if(e.getSource() == quitBtn) {
			System.exit(0);
		}
 		
 		if(e.getSource() == btReset) {
				resetProgram();
 		}
 		
 		if(e.getSource() == submitBtn){	
			StringBuilder string = new StringBuilder();
 	        try {
 	        	setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
 				errorList =	processor.submit(campaignField.getText(), subjectField.getText(), textFieldList );
 				setCursor(null);
 				//submit() should return an errorList, check the size, if size != 0, throw an error panel
 				if(!errorList.isEmpty()){
 					for (String s : errorList){
 						string.append(s +"\n");
 					}
 					errorArea.setEnabled(true);
 					errorArea.setText(string.toString());
 					JOptionPane.showMessageDialog(null,string.toString()+"Please click Clear and re-insert the broken link(s)",  "ERROR", JOptionPane.ERROR_MESSAGE);
 				}
 				
			} catch (Exception e1) {
				setCursor(null);
				errorArea.setText(e1.getMessage());
				e1.printStackTrace();
			}
 		}
 		if(e.getSource() == resetLinks){
 			//check each message and clear the corresponding link
 		 			
 			//clear the error message
 			if(error.getText().contains("1")){
 				textFieldList.get(0).setText("");
 			}if(error.getText().contains("2")){
 				textFieldList.get(1).setText("");
 			}if(error.getText().contains("3")){
 				textFieldList.get(2).setText("");
 			}if(error.getText().contains("4")){
 				textFieldList.get(3).setText("");
 			}if(error.getText().contains("5")){
 				textFieldList.get(4).setText("");
 			}if(error.getText().contains("6")){
 				textFieldList.get(5).setText("");
 			}
 			error.setText("");
 		}
	}
	
	private void writeToConsole() throws IOException {	
		console.setText(processor.getOutput());
		if(processor.isDone()) {
			lbDone.setVisible(true);
			console.setVisible(true);
		}
		else {
			lbDone.setVisible(false);
		}
	}

	private void resetProgram() {  
		processor.resetProgram();
		tfURL.setText(null);
		url = null;
		lbInvalidURL.setVisible(false);
		lbDone.setVisible(false);
		console.setVisible(false);
	}
	
	/**
	 * implements the main part of the program, reads html source file and writes info to file
	 * @throws FileNotFoundException
	 * @throws FileIsEmptyException
	 * @throws IOException
	 */
	private void readURL() throws IOException  {
		url = new URL(tfURL.getText());
		in = url.openStream();

		processor.readFile(in);
		processor.writeParagraph();
		in.close();
	}

	/**
	 * implements the whole program
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		new Gui();
	}

}
