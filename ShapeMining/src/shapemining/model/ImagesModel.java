package shapemining.model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.tree.DefaultMutableTreeNode;

import sax.representation.primitives.Image;
import sax.representation.primitives.Pair;
import sax.utils.ImageMenager;
import sax.utils.SAXCreator;
import utils.sax.representation.SAXEnhanced;


/**
 *  Sax representation of images in memory
 */
public class ImagesModel {
	
	// key - image path 
	private HashMap<String, SAXEnhanced> saxRepresentation;
	private DefaultMutableTreeNode root = null;
	private List<Pair<SAXEnhanced, String>> currentClassification;

	/**
	 * Get current classification.
	 * @return list of pairs of sax and strings
	 */
	public List<Pair<SAXEnhanced, String>> getCurrentClassification() {
		return currentClassification;
	}
	
	/**
	 * Get sax representations.
	 * @return hashmap of strings-saxes names and saxes
	 */
	public HashMap<String, SAXEnhanced> getSaxRepresentation() {
		return saxRepresentation;
	}
	
	/**
	 * Get root of sax tree nodes.
	 * @return root
	 */
	public DefaultMutableTreeNode getRoot() {
		return root;
	}
	
	/**
	 * Get one sax.
	 * @param treePath - tree path to sax node in tree node
	 * @return sax
	 */
	public SAXEnhanced getSax(String treePath){
		if(treePath != null)
			return saxRepresentation.get(treePath);
		else
			return null;
	}
	
	/**
	 * Get saxes from sax tree node or all under tree node.
	 * @param treePartPath - path to sax node or class node in tree.
	 * @return saxes
	 */
	public List<SAXEnhanced> getSaxes(String treePartPath){
		List<SAXEnhanced> result = new ArrayList<SAXEnhanced>();
		for(String key: saxRepresentation.keySet()){
			if(key.startsWith(treePartPath))
				result.add(saxRepresentation.get(key));
		}
		return result;
	}
		
	/**
	 * We assume database files are stored on disk as given:
	 * - database
	 *  |
	 * 	|- class0
	 * 		|- image0
	 * 		|- image01
	 *  ...
	 * 	|- class1
	 * 		|- image10
	 *  ...
	 *  |- classN
	 *  	|- imageN0
	 *  ...
	 *  |- 
	 *  |- imageNOTClASSIFIED-0
	 *  ...
	 *	|- imageNOTClASSIFIED-M
	 *
	 * Other directories or files that are not images are ignored.
	 *  
	 * */
	public ImagesModel(JFileChooser chooser){
		this.saxRepresentation = new HashMap<String, SAXEnhanced>();
		this.currentClassification = new ArrayList<Pair<SAXEnhanced,String>>();
		File database = chooser.getSelectedFile();
		String[] childrenFiles = database.list();
		root = new DefaultMutableTreeNode(database.getName());
		
		for (String main : childrenFiles) {
			String imagePath =  database + "/" + main;
			File imageFile = new File(imagePath);
			if ( !imageFile.isDirectory()) {
				getSAXFromImageFile(imageFile, null, root);
			}else{
				childrenFiles = new File(imagePath).list();
				DefaultMutableTreeNode node = new DefaultMutableTreeNode(database.getName() + "/" + main);
				for (String  subMain: childrenFiles) {
					String subMainImagePath = imagePath + "/" + subMain;
					File subMainImageFile = new File(subMainImagePath);
					if ( !subMainImageFile.isDirectory()) {
						getSAXFromImageFile(subMainImageFile, main, node);
					}
				}
				if( !node.isLeaf() ) //images are in this directory
					root.add(node);
			}
		}	
	}
	
	private void getSAXFromImageFile(File imageFile, String imageClass, DefaultMutableTreeNode node){
		try{
			//System.out.println("Creating: " + imageFile);
			Image image = ImageMenager.loadImage(imageFile.getPath());
			if(image != null){
				SAXEnhanced sax = new SAXEnhanced(imageFile.getPath(), SAXCreator.create(image));
				sax.setImageClass(imageClass);
				String key = (String)node.getUserObject() + "/" +imageFile.getName();
				saxRepresentation.put(key, sax);
				node.add( new DefaultMutableTreeNode(key));
				if(imageClass != null)
					currentClassification.add(new Pair<SAXEnhanced, String>(
							sax, imageClass ));
			}
		}catch(IllegalArgumentException e){
			//System.out.println("couldn't create: " + imageFile);
			//System.out.println(e);
			e.printStackTrace();
		}
	}
}
