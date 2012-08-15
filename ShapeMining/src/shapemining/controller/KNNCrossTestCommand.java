package shapemining.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

import sax.representation.primitives.Pair;
import sax.util.test.Settings;
import shapemining.ui.ResultPanel;
import utils.sax.RandomNumbersGenerator;
import utils.sax.representation.SAXEnhanced;

import classification.algorithm.nearestneighbour.KNearestNeighbour;


/**
 * Cross test of classification algorithm.
 */
public class KNNCrossTestCommand extends Command {
	private List<Pair<SAXEnhanced, String>> currentClassification;
	private DefaultMutableTreeNode root;
	private static int ITERATIONS = 10;
	private static int PERCENT = 20; //take 20% from each class
	private double[] classifyError;
	private Double endClassifyError, endClassifyErrorStandrardDeviation;
	private KNearestNeighbour classifier;
	private RandomNumbersGenerator rand;

	/**
	 * Constructor
	 * @param app - main app class
	 * @param currentClassification - current classification of saxes representaions of shapes.
	 * @param root - root node of saxes tree.
	 */
	public KNNCrossTestCommand(HiddenOnProccess app, List<Pair<SAXEnhanced, String>> currentClassification,
			DefaultMutableTreeNode root) {
		super(app);
		this.currentClassification = currentClassification;
		this.root = root;
		this.classifyError = new double[ITERATIONS];
		this.endClassifyError = new Double(0);
		this.endClassifyErrorStandrardDeviation = new Double(0);
		this.classifier = new KNearestNeighbour();
		this.rand = new RandomNumbersGenerator();
	}

	
	@Override
	protected Void doInBackground() throws Exception {
		ShapeMiningApp shapeMiningApp = (ShapeMiningApp)app;
		
		for(int iter=0; iter < ITERATIONS; iter++){
			int errors = 0;
			Pair<List<SAXEnhanced>, List<Pair<SAXEnhanced,String>>> data = prepareData(shapeMiningApp);
			List<SAXEnhanced> chosenSaxes = data.getFirstElement();
			List<Pair<SAXEnhanced, String>> iterCurrentClassification = data.getSecondElement();
			
			for(SAXEnhanced sax : chosenSaxes){
				List<Pair<String, Double>> result = classifier.classify(iterCurrentClassification,
																		sax, Settings.K_VALUE);
				// count errors in classification
				if( !sax.getImageClass().equals( result.get(0).getFirstElement() ) ) 
					errors++;
			}
			
			classifyError[iter] = (double)errors*100/chosenSaxes.size(); // in percent
		}

		// count average
		for(double classifyErrorElement : classifyError)
			endClassifyError += classifyErrorElement;
		
		endClassifyError /= ITERATIONS;
		
		for(double classifyErrorElement : classifyError)
			endClassifyErrorStandrardDeviation += Math.pow(classifyErrorElement - endClassifyError, 2);
		
		endClassifyErrorStandrardDeviation /= (ITERATIONS - 1);
		endClassifyErrorStandrardDeviation = Math.sqrt(endClassifyErrorStandrardDeviation);
		
		DecimalFormat df = new DecimalFormat("#.##"); 
		int numberOfElems = (int)(currentClassification.size()*((double)PERCENT/100));

		String resultString = "";
		resultString += "Classification test. Average error results after " + ITERATIONS +
				" iterations on " + numberOfElems + " test elements " +
				"from " + currentClassification.size() + " elements in database.\n" +
				"Average error rate: " + df.format(endClassifyError) + " % (+/- "+
				df.format(endClassifyErrorStandrardDeviation) +").\n\n";
		
		shapeMiningApp.getSkeletonPanel().createResultPanel(ResultPanel.K_NEAREST_NEIGHBOUR_TEST,
				ResultPanel.K_NEAREST_NEIGHBOUR_TEST_SHORT, Settings.K_NEAREST_NEIGHBOUR, resultString);

		return null;
	}
	
	@SuppressWarnings("unchecked")
	private Pair<List<SAXEnhanced>, List<Pair<SAXEnhanced, String>>> prepareData(
			ShapeMiningApp shapeMiningApp){
		List<SAXEnhanced> chosenSaxes = new ArrayList<SAXEnhanced>();

		Enumeration<DefaultMutableTreeNode> children = root.children();
		while( children.hasMoreElements()){
			DefaultMutableTreeNode child = children.nextElement();
			if(!child.isLeaf()){
				Enumeration<DefaultMutableTreeNode> subChildren = child.children();
				Vector<DefaultMutableTreeNode> temp = new Vector<DefaultMutableTreeNode>();
				while(subChildren.hasMoreElements())
					temp.add(subChildren.nextElement());
				
				int[] indices = rand.getNRandomInts((int)( temp.size()*((double)PERCENT/100) ),
						temp.size()); 
				for(int i : indices)
					chosenSaxes.add(shapeMiningApp.getModel()
							.getSax((String) temp.get(i).getUserObject()));		
			}
		}

		List<Pair<SAXEnhanced, String>> iterCurrentClassification = new ArrayList<Pair<SAXEnhanced,String>>();
		iterCurrentClassification.addAll(currentClassification.subList(0, currentClassification.size()));
		
		for (SAXEnhanced sax : chosenSaxes) 
			iterCurrentClassification.remove(sax);
		
		return new Pair<List<SAXEnhanced>, List<Pair<SAXEnhanced,String>>>
			(chosenSaxes,iterCurrentClassification);
	}

}
