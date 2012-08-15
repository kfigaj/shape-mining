package shapemining.controller;

import java.util.List;

import sax.representation.primitives.Pair;
import sax.util.test.Settings;
import shapemining.ui.ResultPanel;
import utils.sax.Timer;
import utils.sax.representation.SAXEnhanced;

import classification.algorithm.nearestneighbour.KNearestNeighbour;


/**
 * Command which executes K-Nearest Neighbour algorithm
 *
 */
public class KNNAlgorithmCommand extends Command {
	private Object nodeInfo;
	
	/**
	 * Constructor
	 * @param app  - main app class
	 * @param nodeInfo - node in tree of SAXes. Tells which sax is to be classified.
	 */
	public KNNAlgorithmCommand(HiddenOnProccess app, Object nodeInfo) {
		super(app);
		this.nodeInfo = nodeInfo;
	}

	@Override
	protected Void doInBackground() throws Exception {
		ShapeMiningApp shapeMiningApp = (ShapeMiningApp)app;
		KNearestNeighbour classifier = new KNearestNeighbour();
		SAXEnhanced sax = shapeMiningApp.getModel().getSax( (String) nodeInfo);
		if (sax != null){
			Timer t = new Timer();
			List<Pair<String, Double>> result = classifier.classify(
					shapeMiningApp.getModel().getCurrentClassification(), sax, Settings.K_VALUE);
			String time = "" + t.elapsed();
			if(result != null){
				String resultString = "";
				resultString += sax.getImagePath() + " is classified as: \n";
				for(Pair<String, Double> s: result){
					resultString += s.getFirstElement() +
							" with support of " + s.getSecondElement()*100 + "%\n";
				}
				resultString += "from " + Settings.K_VALUE + " neighbours. Operation done in: " + time + "[ms]\n\n";
				shapeMiningApp.getSkeletonPanel().createResultPanel(ResultPanel.K_NEAREST_NEIGHBOUR,
						ResultPanel.K_NEAREST_NEIGHBOUR_SHORT, Settings.K_NEAREST_NEIGHBOUR, resultString);
				shapeMiningApp.getSkeletonPanel().createImagePanel(sax, shapeMiningApp);
			}
		}
		return null;
	}

}
