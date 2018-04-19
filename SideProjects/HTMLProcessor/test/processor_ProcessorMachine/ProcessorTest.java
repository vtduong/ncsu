package processor_ProcessorMachine;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

public class ProcessorTest {
	private Processor processor;
	private URL url;
	@Before
	public void setUp() throws Exception {
		url = new URL("");
		processor = Processor.getInstance();
	}

	@Test
	public void testReadFile() throws FileNotFoundException {
		processor.resetNumProcess();
		//int num = processor.getNumProcess();
		try {
			processor.readFile(url.openStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(5, processor.getNumProcess());
		processor.resetNumProcess();
	}
	
	@Test
	public void testGetNumProcess() throws FileNotFoundException{
		try {
			processor.readFile(url.openStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(5, processor.getNumProcess());
		processor.resetNumProcess();
		int num = processor.getNumProcess();
	}
	
	@Test
	public void testIsDone() throws FileNotFoundException{
		//processor.readFile();
		assertEquals(10, processor.getNumProcess());
		//processor.writeParagraph();
		assertTrue(processor.isDone());
	}
	
	@Test
	public void testWriteParagraph() throws FileNotFoundException{
		try {
			processor.readFile(url.openStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		processor.writeParagraph();
		assertEquals(10, processor.getNumProcess());
		processor.resetNumProcess();
	}
	
	@Test
	public void testGetOutput() throws FileNotFoundException{
		//processor.resetProgram();
		String output = "<tr><td align=\"left\" valign=\"top\" bgcolor=\"#FFFFFF\" width=\"178\"><img src=\"" + "http://www.thefix.com/sites/default/files/hepcmain3.png" +
				"\" border=\"0\" width=\"196\" height=\"123\" style=\"padding:8px 13px 0 0;\"></td><td align=\"left\" valign=\"top\" bgcolor=\"#FFFFFF\"><table border=\"0\" cellpadding=\"0\" width=\"381\"><tr><td align=\"left\" style=\"font-family:arial, helvetica, sans-serif;font-size:18px;line-height:19px;\"><span style=\"font-size:14px;text-transform:uppercase;line-height:19px;color:#d31920;font-weight:bold;\">treatment advice//</span> <a href=\"" + 
				"http://www.thefix.com/content/hcvets-front-lines-against-hep-c" + "\" style=\"color:#000000;font-weight:bold;text-decoration:none;\">" + "\n" + "An Ounce of Truth and a Pound of Bullshit"+"\u2014"+"On the Front Lines Against Hep C" + "\n" + 
				"</a></td></tr><tr><td align=\"left\" style=\"font-family:arial, helvetica, sans-serif;font-size:15px;color:#000000;font-weight:normal;line-height:21px;\">" + "\n" + ""+"One in 10 US veterans is infected with HCV—three to five times higher than the general population. What is being done about it? The Fix Q&amp;A with Hep C advocate and HCVets co-founder Tricia Lupole."+"\u2014"+"on activism, policy and that TED talk." + "\n" +
				"<br><em>" + "\n" + "By John Lavit" + "\n" + "</em></td></tr></table></td></tr>";
		try {
			processor.readFile(url.openStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		processor.writeParagraph();
		assertEquals(output, processor.getOutput());
	}

}
