package shapemining.controller;

import java.util.ArrayList;
import java.util.List;

import sax.util.test.Settings;
import shapemining.ui.ResultPanel;
import utils.sax.RandomNumbersGenerator;
import utils.sax.Timer;
import utils.sax.representation.SAXEnhanced;


import discordfinder.algorithm.DiscordFinderAlgorithm;
import discordfinder.algorithm.localsensivityhashing.LocalSensivityHashingAlgorithm;

/**
 * Efficiency test of discord finding algorithms.
 */
public class DiscordTestCommand extends Command {
	private static final int DISCORD_STEP = 50;
	private RandomNumbersGenerator rand;
	private DiscordFinderAlgorithm algorithm;

	/**
	 * Constructor of command do test discord algorithms.
	 * @param app  - main app class
	 * @param algorithm - discord finding algorithm to test.
	 */
	public DiscordTestCommand(HiddenOnProccess app, DiscordFinderAlgorithm algorithm) {
		super(app);
		this.algorithm = algorithm;
		this.rand = new RandomNumbersGenerator();
	}

	@Override
	protected Void doInBackground() throws Exception {

		ShapeMiningApp shapeMiningApp = (ShapeMiningApp)app;
		String resultString = "";
		resultString += "Discord efficiency test.\n\n";
		Object[] saxes = shapeMiningApp.getModel().getSaxRepresentation().values().toArray();
		
		for (int numberOfSaxes = DISCORD_STEP; numberOfSaxes < saxes.length; numberOfSaxes += DISCORD_STEP) {
			
			List<SAXEnhanced> saxesForTest = new ArrayList<SAXEnhanced>();
			int[] indices = this.rand.getNRandomInts(numberOfSaxes, saxes.length);
			
			for (int i : indices) {
				saxesForTest.add((SAXEnhanced)saxes[i]);
			}
			
			Timer timer = new Timer();
			this.algorithm.findDiscord(saxesForTest);
			resultString += "Results for "+ numberOfSaxes +" saxes done in "+ timer.elapsed() + "[ms]\n";
			
		}
		
		resultString += "\n";
		
		if( algorithm.getClass() == LocalSensivityHashingAlgorithm.class){
			shapeMiningApp.getSkeletonPanel().createResultPanel(ResultPanel.LOCAL_HASHING_TEST,
					ResultPanel.LOCAL_HASHING_TEST_SHORT, Settings.LOCAL_HASHING, resultString);
		}else{
			shapeMiningApp.getSkeletonPanel().createResultPanel(ResultPanel.IMPROVED_BRUTE_FORCE_TEST,
					ResultPanel.IMPROVED_BRUTE_FORCE_TEST_SHORT, Settings.IMPROVED_BRUTE_FORCE, resultString);
		}
		
		return null;
	}

}
