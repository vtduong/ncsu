package processor_ReadAndWrite;

import static org.junit.Assert.*;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

public class WriterTest {

	private Reader reader;
	private Writer writer;
	@Before
	public void setUp() throws Exception {
		URL url = new URL("https://www.thefix.com/recovery-and-reentry");
		 reader = new Reader(url.openStream());
		reader.process();
		 writer = new Writer(reader.getImgInfo(), reader.getUrlInfo(), reader.getTitleInfo(),
				reader.getDescriptionInfo(), reader.getAuthorInfo());
	}

	@Test
	public void test() {
		String expectedOutput = "<a href=\"https://www.thefix.com/vaping-form-harm-reduction\"" +
								"target=\"_blank\"><span style=\"font-size:18px\"><strong><span style=\"color:rgb(211, 25, 32)\">PROBLEM SOLUTION"+
								"//</span>&nbsp;</strong></span></a><a href=\"https://www.thefix.com/vaping-form-harm-reduction\"><span style=\"color:#696969\"><span style=\"font-size:18px\"><strong>Is Vaping an Effective Form of Harm Reduction?</strong></span></span></a><br />"+ "\n"+
								"Although vaping nicotine may be safer than smoking traditional cigarettes, it is still addictive and carries significant risks.<br style=\"line-height: 20.8px;\" />"+"\n"+
								"<em style=\"line-height:20.8px\">By Linda Richter PhD</em>";
		writer.process();
		//assertEquals(expectedOutput, writer.getOutput());
	}

}
