package processor_ReadAndWrite;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import processor_ProcessorMachine.Processor;

public class ReaderTest {

	private Reader reader;
	@Before
	public void setUp() throws Exception {
		URL url = new URL("https://www.thefix.com/recovery-and-reentry");
		reader = new Reader(url.openStream());
	}

//	@Test
//	public void test() {
//		fail("Not yet implemented");
//	}
	@Test
	public void testProcess(){
		reader.process();
		//compare image
		assertEquals("https://d2opwwnn6mpgl2.cloudfront.net/cdn/farfuture/aiDTcau_Br1x-jgtTcehz8Qo6zf5GbGEY9MFqyyYkag/mtime:1449480685/sites/default/files/opendoor.jpg", reader.getImgInfo());
		//compare URL
		assertEquals("https://www.thefix.com/recovery-and-reentry", reader.getUrlInfo());
		//compare Title
		assertEquals("The Cost of Freedom—Recovery as an Ex-convict", reader.getTitleInfo());
		//compare Description
		assertEquals("Seth Ferranti catches up with fellow prison rehab graduates to see how the program worked for them.", reader.getDescriptionInfo());
		//compare Author
		assertEquals("Seth Ferranti", reader.getAuthorInfo());
		
		assertEquals(5, reader.getNumProcess());
	}

}
