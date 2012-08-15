package shapemining.controller;

import java.util.NoSuchElementException;

import sax.representation.primitives.Pair;
import sax.representation.primitives.SAX;
import sax.utils.SAXCreator;
import shapemining.model.ImagesModel;
import utils.sax.representation.SAXEnhanced;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

/**
 * 
 *  Update SAX list command. Updates model for application according to new settings.
 *
 */
public class UpdateSAXListCommand extends Command {

	/**
	 * Constructor
	 * @param app - main app class
	 */
	public UpdateSAXListCommand(HiddenOnProccess app) {
		super(app);
	}

	@Override
	protected Void doInBackground() throws Exception {
		try{
			final ShapeMiningApp shapeMiningApp = (ShapeMiningApp)app;
			ImagesModel model = shapeMiningApp.getModel();
		  	if(model != null){
		  		for(SAXEnhanced sax: model.getSaxRepresentation().values()){
		  			SAX updatedSax = SAXCreator.getSaxFromTimeseries(sax.getOriginal());
		  			final SAXEnhanced saxToFind = sax;
		  			sax.updateSax(updatedSax.getSax(), 
		  					updatedSax.getAlphabetCount(), 
		  					updatedSax.getTimeSeries());
		  			try{
			  			Pair<SAXEnhanced, String> classificationPair = Iterables.find(model.getCurrentClassification(), 
		  					new Predicate< Pair<SAXEnhanced, String> >(){
								@Override
								public boolean apply(Pair<SAXEnhanced, String> arg) {
									return arg.getFirstElement().equals(saxToFind);
								}
			  			});
			  			classificationPair.setFirstElement(sax);
		  			}catch(NoSuchElementException e){}
		  		}		
		  		
		  	}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
