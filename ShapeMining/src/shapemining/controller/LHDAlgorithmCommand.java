package shapemining.controller;

import java.util.List;

import sax.util.test.Settings;
import shapemining.ui.ResultPanel;
import utils.sax.Timer;
import utils.sax.representation.SAXEnhanced;


import discordfinder.algorithm.localsensivityhashing.LocalSensivityHashingAlgorithm;

/**
 * Command which executes Local hashing discord finder algorithm.
 *
 */
public class LHDAlgorithmCommand extends Command {
	private Object nodeInfo;
	
	/**
	 * Constructor
	 * @param app - main app class
	 * @param nodeInfo - node from sax tree. Tells which saxes are to be used.
	 */
	public LHDAlgorithmCommand(HiddenOnProccess app, Object nodeInfo) {
		super(app);
		this.nodeInfo = nodeInfo;
	}

	@Override
	protected Void doInBackground() throws Exception {
		ShapeMiningApp shapeMiningApp = (ShapeMiningApp)app;
		LocalSensivityHashingAlgorithm finder = new LocalSensivityHashingAlgorithm();
		List<SAXEnhanced> saxes = shapeMiningApp.getModel().getSaxes((String) nodeInfo);

		if ( !saxes.isEmpty() ){
			Timer t = new Timer();
			SAXEnhanced quirk = finder.findDiscord( saxes );
			String time = "" + t.elapsed();
			if( quirk != null){
				String resultString = "";
				resultString += quirk.getImagePath() + " is discord in "+ nodeInfo +"\n" +
						"Operation done in: " + time + "[ms]\n\n";
				shapeMiningApp.getSkeletonPanel().createResultPanel(ResultPanel.LOCAL_HASHING,
						ResultPanel.LOCAL_HASHING_SHORT, Settings.LOCAL_HASHING, resultString);
				shapeMiningApp.getSkeletonPanel().createImagePanel(quirk, shapeMiningApp);
			}
			
		}
		return null;
	}

}
