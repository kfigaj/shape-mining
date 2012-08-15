package shapemining.controller;

import java.util.List;

import sax.util.test.Settings;
import shapemining.ui.ResultPanel;
import utils.sax.Timer;
import utils.sax.representation.SAXEnhanced;


import discordfinder.algorithm.bruteforce.ImprovedBruteForce;

/**
 * Command which executes improved brute force discord finder algorithm.
 *
 */
public class IBFDAlgorithmCommand extends Command {
	private Object nodeInfo;
	
	/**
	 * Constructor
	 * @param app  - main app class
	 * @param nodeInfo - node in SAX tree. Tells which sax are to be used.
	 */
	public IBFDAlgorithmCommand(HiddenOnProccess app, Object nodeInfo) {
		super(app);
		this.nodeInfo = nodeInfo;
	}

	@Override
	protected Void doInBackground() throws Exception {
		ShapeMiningApp shapeMiningApp = (ShapeMiningApp)app;
		ImprovedBruteForce finder = new ImprovedBruteForce();
		List<SAXEnhanced> saxes = shapeMiningApp.getModel().getSaxes((String) nodeInfo);

		if ( !saxes.isEmpty() ){
			Timer t = new Timer();
			SAXEnhanced quirk = finder.findDiscord( saxes );
			String time = "" + t.elapsed();
			if( quirk != null){
				String resultString = "";
				resultString += quirk.getImagePath() + " is discord in "+ nodeInfo +"\n" +
						"Operation done in: " + time + "[ms]\n\n";
				
				shapeMiningApp.getSkeletonPanel().createResultPanel(ResultPanel.IMPROVED_BRUTE_FORCE,
						ResultPanel.IMPROVED_BRUTE_FORCE_SHORT, Settings.IMPROVED_BRUTE_FORCE, resultString);
				shapeMiningApp.getSkeletonPanel().createImagePanel(quirk, shapeMiningApp);
				
			}
		}
		return null;
	}

}
