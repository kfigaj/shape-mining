package discordfinder.algorithm;

import java.util.List;

import utils.sax.representation.SAXEnhanced;



/**
 * Defines template of the algorithm.
 * 
 * 
 */
public interface DiscordFinderAlgorithm {

	/**
	 * Finds the most unusual shape out of given sax representation of images.
	 * 
	 * @param sax - list of saxes
	 * @return sax - discord
	 */
	SAXEnhanced findDiscord(List<SAXEnhanced> sax);
}
