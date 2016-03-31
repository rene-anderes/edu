/*
 * Course Agile Software Development
 */ 
package invoker;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class CmdVolLabelIntegrationTest extends IntegrationTestBase {
	
	@Test
	public void setVolumeName() {
		String testLabel = "testLabel";

		drive.setLabel("");
		commandInvoker.executeCommand("label c: " + testLabel,
				testOutput);
		assertTrue(testOutput.toString().length() < 2);
		assertTrue(drive.getLabel().compareTo(testLabel) == 0);
	}

	@Test
	public void getVolumeName() {
		String testLabel = "Test Label";

		drive.setLabel(testLabel);
		commandInvoker.executeCommand("vol", testOutput);
		assertTrue(testOutput.toString().contains(testLabel));
	}

	@Test
	public void deleteCurrentVolumeLabel() {
		String defaultLabel = "Something";
		drive.setLabel(defaultLabel);
		testOutput.setCharacterThatIsRead('Y');
		commandInvoker.executeCommand("label", testOutput);

		assertTrue(testOutput.characterWasRead());
		assertTrue(drive.getLabel().compareTo("") == 0);
		assertTrue(testOutput.toString().toLowerCase().contains(
				"delete current volume label"));
	}

	@Test
	public void sayNoToDeleteLabel() {
		String defaultLabel = "Something";
		drive.setLabel(defaultLabel);
		testOutput.setCharacterThatIsRead('n');
		commandInvoker.executeCommand("label", testOutput);

		assertTrue(testOutput.characterWasRead());
		assertTrue(drive.getLabel().compareTo(defaultLabel) == 0);
		assertTrue(testOutput.toString().toLowerCase().contains(
				"delete current volume label"));
	}
}
