package shapemining.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFileChooser;
import javax.swing.tree.DefaultMutableTreeNode;

import shapemining.model.ImagesModel;
import shapemining.ui.TreePanel;
import utils.sax.representation.SAXEnhanced;


/**
 *  Create SAX list command. Creates model for application and tell parts of 
 *  UI to update theirs views.
 *
 */
public class CreateSAXListCommand extends Command {
	private JFileChooser chooser;

	/**
	 * Create sax list from selected directory.
	 * @param app - main app class
	 * @param chooser - file chooser
	 */
	public CreateSAXListCommand(HiddenOnProccess app, JFileChooser chooser) {
		super(app);
		this.chooser = chooser;
	}

	@Override
	protected Void doInBackground() throws Exception {
		final ShapeMiningApp shapeMiningApp = (ShapeMiningApp)app;
		//create model
		shapeMiningApp.setModel(new ImagesModel(chooser));
	  	if(shapeMiningApp.getModel() != null){
	  		shapeMiningApp.setFileTreePanel(new TreePanel( shapeMiningApp.getModel().getRoot() ));
	  		//show image on double click
	  		shapeMiningApp.getFileTreePanel().getJImageTree().addMouseListener(new MouseAdapter(){
				@Override
				public void mouseClicked(MouseEvent e) {
					super.mouseClicked(e);
					if(e.getClickCount() == 2){
						DefaultMutableTreeNode node = (DefaultMutableTreeNode) shapeMiningApp.
							getFileTreePanel().getJImageTree().getLastSelectedPathComponent();
						if(node != null){
							Object nodeInfo = node.getUserObject();
							if (node.isLeaf()) {
								SAXEnhanced sax = shapeMiningApp.getModel().getSax( (String) nodeInfo);
								if(sax != null)
									shapeMiningApp.getSkeletonPanel().createImagePanel(sax, shapeMiningApp);
							}
						}
					} 
				}
			});
	  		// update tree view
	  		shapeMiningApp.getSkeletonPanel().getJTopLeftScrollPane().
	  			setViewportView(shapeMiningApp.getFileTreePanel());
	  	}
		return null;
	}

}
