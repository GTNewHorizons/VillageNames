package astrotibs.villagenames.banner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import javax.annotation.Nullable;

import astrotibs.villagenames.handler.EntityInteractHandler;
import astrotibs.villagenames.integration.ModObjects;
import astrotibs.villagenames.name.NameGenerator;
import astrotibs.villagenames.nbt.VNWorldDataStructure;
import astrotibs.villagenames.utility.FunctionsVN;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.village.Village;

public class BannerGenerator
{
	// Array of color metas and corresponding weights
	public static final int[] COLOR_METAS = new int[]{
			0, // Black
			1, // Red
			2, // Green
			3, // Brown
			4, // Blue
			5, // Purple
			6, // Cyan
			7, // Light Gray
			8, // Gray
			9, // Pink
			10, // Lime
			11, // Yellow
			12, // Light Blue
			13, // Magenta
			14, // Orange
			15 // White
			};
	
	public static final double[] COLOR_WEIGHTS = new double[]{
			121D/3, // Black
			335D/2, // Red
			81D, // Green
			3D, // Brown
			953D/12, // Blue
			1D, // Purple
			10D, // Cyan
			1D, // Light Gray
			1D, // Gray
			1D/2, // Pink
			15D, // Lime
			229D/3, // Yellow
			33D, // Light Blue
			1D/2, // Magenta
			65/6D, // Orange
			409D/3 // White
	};
	
	/**
	 * Generates a random banner pattern based on internal Markovian values
	 * for how flag pieces come together in real life.
	 * 
	 * forceBannerColor forces a specific meta value for the base color.
	 * This is intended for backward compatibility with villages that have a previously generated "village color."
	 * Enter a value below 0 to ignore this and draw a random value for the banner.
	 */
	public static Object[] randomBannerArrays(Random random, int forceBannerColor, int forceBannerColor2)
	{
		int chosencountry;
		String[] baseTemplate;
		int[] baseColors;
		String[] accentTemplate;
		int[] accentColors;
		
		/**
		 * For reference: the different banner emblazon patterns
		 * 
		 * "bs"		Base (bottom third colored)
		 * "ts" 	Chief (top third colored)
		 * "ls" 	pale dexter (left third colored)
		 * "rs" 	pale sinister (right third colored)
		 * "cs" 	pale (center vertical line)
		 * "ms" 	fess (center horizontal line)
		 * "drs" 	bend (line from upper-left to lower-right)
		 * "dls" 	bend sinister (line from upper-right to lower-left)
		 * "ss" 	paly (vertical pinstripes)
		 * "cr" 	saltire ('X' shape)
		 * "sc" 	cross
		 * "ld"		per bend sinister (upper-left half colored)
		 * "rud"	per bend (upper-right half colored)
		 * "lud"	per bend inverted (lower-left half colored)
		 * "rd"		per bend sinister inverted (lower-right half colored)
		 * "vh"		per pale (left half colored)
		 * "vhr"	per pale inverted (right half colored)
		 * "hh"		per fess (top half colored)
		 * "hhb"	per fess inverted (bottom half colored)
		 * "bl" 	base dexter canton (square in lower-left corner)
		 * "br" 	base sinister canton (square in lower-right corner)
		 * "tl" 	chief dexter canton (square in upper-left corner)
		 * "tr" 	chief sinister canton (square in upper-right corner)
		 * "bt" 	chevron (triangle at bottom)
		 * "tt" 	inverted chevron (triangle at top)
		 * "bts" 	base indented (scallop shapes at bottom)
		 * "tts" 	chief indented (scallop shapes at top)
		 * "mc"		roundel (circle in center)
		 * "mr"		lozenge (rhombus in center)
		 * "bo"		bordure (border)
		 * "cbo"	dyed bordure indented (fancy border)
		 * "bri"	dyed field masoned (brick pattern)
		 * "gra"	gradient (color at top)
		 * "gru"	base gradient (color at bottom)
		 * "cre"	dyed creeper charge (creeper face)
		 * "sku"	dyed skull charge (skull and crossbones)
		 * "flo"	dyed flower charge (flower icon)
		 * "moj"	dyed Thing (Mojang logo)
		 */
		
		while (true)
		{
			
			// Select the base template to use.
			
			// Set random booleans to decide all possible pattern mirrorings
			boolean b1 = random.nextBoolean();
			boolean b1a = random.nextBoolean();
			boolean b2 = random.nextBoolean();
			boolean b2a = random.nextBoolean();
			boolean b3 = random.nextBoolean();
			boolean b4 = random.nextBoolean();
			boolean ba1 = random.nextBoolean();
			boolean ba1a = random.nextBoolean();
			boolean bac1 = random.nextBoolean();
			int bac2 = random.nextInt(3);
			
			// The symbol to fill in for a random "sigil"
			String sigil = "moj";
			switch(random.nextInt(6))
			{
				case 0:
					sigil = ba1 ? (ba1a ? "tl" : "tr") : (ba1a ? "bl" : "br"); // Canton
					break;
				case 1:
					sigil = "mr"; // Lozenge
					break;
				case 2:
					sigil = "mc"; // Roundel
					break;
				case 3:
					sigil = "flo"; // Flower Charge
					break;
				case 4:
					sigil = "cre"; // Creeper Charge
					break;
			}
			
			
			// Because rarer colors become more common the more you draw, the best solution is
			// to only draw and shuffle the colors AFTER you know how many you need.
			int colorcount = 0;
			
			
			
			/**
			 * 2D object containing the pattern data.
			 * Each row is a decomposed template from a country's flag.
			 * 
			 * Column 0 is a String array with ordered instructions to assemble a base pattern. It could be empty.
			 * Column 1 is a corresponding int array instructing the generator whether any colors should be re-used in the pattern.
			 * Column 2 is a String array with ordered instructions to assemble an accent pattern. It could be empty.
			 * Column 3 is a corresponding int array instructing the generator whether any base colors should be re-used in the accent.
			 * 
			 * Essentially you'll choose a random row and take column 0 and 1 for the base pattern, and then choose another random row
			 * (it could possibly be the same) and take column 2 and 3 for the accent pattern.
			 */
			Object[][] nationBaseTemplates = {
					
					{ new String[]{b1 ? "bs" : "ts", !b1 ? "bs" : "ts"}, new int[]{-2, -2}, new String[]{"flo"}, new int[]{-2} }, // Afghanistan
					{ new String[]{}, new int[]{}, new String[]{"cre"}, new int[]{-2} }, // Albania
					{ new String[]{b1 ? "hh" : "hhb"}, new int[]{-2}, new String[]{"moj"}, new int[]{-2} }, // Algeria
					{ new String[]{b1 ? "bs" : "ts", !b1 ? "bs" : "ts"}, new int[]{-2, -2}, new String[]{"flo"}, new int[]{-2} }, // Andorra
					{ new String[]{b1 ? "vh" : "vhr"}, new int[]{-2}, new String[]{"moj"}, new int[]{-2} }, // Angola
					{ new String[]{b1 ? "vh" : "vhr", b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{"BEHIND", "flo", "mc"}, new int[]{-2, -1} }, // Antigua and Barbuda
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, 1}, new String[]{"mc"}, new int[]{-2} }, // Argentina
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Armenia
					{ new String[]{}, new int[]{}, new String[]{}, new int[]{} }, // Australia
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, 1}, new String[]{}, new int[]{} }, // Austria
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{"moj"}, new int[]{-2} }, // Azerbaijan
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs", b3 ? "bt" : "tt"}, new int[]{-2, 1, -2}, new String[]{}, new int[]{} }, // Bahamas
					{ new String[]{b1 ? "bts" : "tts"}, new int[]{-2}, new String[]{}, new int[]{} }, // Bahrain
					{ new String[]{}, new int[]{}, new String[]{"mc"}, new int[]{-2} }, // Bangladesh
					{ new String[]{b1 ? "bs" : "ts", !b1 ? "bs" : "ts"}, new int[]{-2, 1}, new String[]{sigil}, new int[]{1} }, // Barbados
					{ new String[]{b1 ? "ls" : "rs", b2 ? "bts" : "tts"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Belarus
					{ new String[]{b1 ? "bs" : "ts", !b1 ? "bs" : "ts"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Belgium
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, 1}, new String[]{"mc"}, new int[]{-2} }, // Belize
					{ new String[]{b1 ? "vh" : "vhr", b2 ? "bt" : "tt"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Benin
					{ new String[]{b1 ? (b1a ? "lud" : "rud") : (b1a ? "ld" : "rd")}, new int[]{-2}, new String[]{"cre"}, new int[]{-2} }, // Bhutan
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{sigil}, new int[]{bac1 ? 1 : 2} }, // Bolivia
					{ new String[]{b1 ? (b1a ? "lud" : "rud") : (b1a ? "ld" : "rd"), b1==b1a ? "ts" : "bs"}, new int[]{-2, 1}, new String[]{}, new int[]{} }, // Bosnia and Herzegovina
					{ new String[]{"ss", b2 ? "ls" : "rs", !b2 ? "ls" : "rs"}, new int[]{-2, -2, 2}, new String[]{}, new int[]{} }, // Botswana
					{ new String[]{}, new int[]{}, new String[]{"mr", "mc"}, new int[]{-2} }, // Brazil
					{ new String[]{b1 ? "drs" : "dls", b1 ? (b1a ? "lud" : "rud") : (b1a ? "ld" : "rd")}, new int[]{-2, -2}, new String[]{sigil}, new int[]{-2} }, // Brunei
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Bulgaria
					{ new String[]{b1 ? "vh" : "vhr"}, new int[]{-2}, new String[]{"flo", "mc"}, new int[]{-2, -1} }, // Burkina Faso
					{ new String[]{b1 ? "bt" : "tt", !b1 ? "bt" : "tt"}, new int[]{-2, 1}, new String[]{"cr", "mc"}, new int[]{-2, -1} }, // Burundi
					{ new String[]{"ss", b2 ? "ls" : "rs", !b2 ? "ls" : "rs"}, new int[]{-2, -2, 2}, new String[]{}, new int[]{} }, // Cabo Verde
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, 1}, new String[]{"mr"}, new int[]{-2} }, // Cambodia
					{ new String[]{b1 ? "bs" : "ts", !b1 ? "bs" : "ts"}, new int[]{-2, -2}, new String[]{"flo", "mc"}, new int[]{-2, -1} }, // Cameroon
					{ new String[]{b1 ? "bs" : "ts", !b1 ? "bs" : "ts"}, new int[]{-2, 1}, new String[]{sigil}, new int[]{1} }, // Canada
					{ new String[]{b1 ? "vh" : "vhr", b2 ? "ls" : "rs", !b2 ? "ls" : "rs", "ms"}, new int[]{-2, -2, -2, -2}, new String[]{}, new int[]{} }, // Central African Republic
					{ new String[]{b1 ? "bs" : "ts", !b1 ? "bs" : "ts"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Chad
					{ new String[]{b1 ? "vh" : "vhr"}, new int[]{-2}, new String[]{ba1 ? (ba1a ? "tl" : "tr") : (ba1a ? "bl" : "br")}, new int[]{-2} }, // Chile
					{ new String[]{}, new int[]{}, new String[]{}, new int[]{} }, // China
					{ new String[]{b1 ? "vh" : "vhr", b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Colombia
					{ new String[]{b1 ? "vh" : "vhr", b2 ? "ls" : "rs", !b2 ? "ls" : "rs", b4 ? "bt" : "tt"}, new int[]{-2, -2, -2, -2}, new String[]{}, new int[]{} }, // Comoros
					{ new String[]{b1 ? "drs" : "dls"}, new int[]{-2}, new String[]{}, new int[]{} }, // Congo, DR
					{ new String[]{b1 ? (b1a ? "lud" : "rud") : (b1a ? "ld" : "rd"), b1 ? "drs" : "dls"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Congo, R
					{ new String[]{"ss", "cs"}, new int[]{-2, -2}, new String[]{"mc"}, new int[]{-2} }, // Costa Rica
					{ new String[]{b1 ? "bs" : "ts", !b1 ? "bs" : "ts"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Cote d'Ivoire
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{"cre"}, new int[]{bac1 ? 0 : 1} }, // Croatia
					{ new String[]{"ss", b2 ? "bt" : "tt"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Cuba
					{ new String[]{}, new int[]{}, new String[]{sigil}, new int[]{-2} }, // Cyprus
					{ new String[]{b1 ? "vh" : "vhr"}, new int[]{-2}, new String[]{}, new int[]{} }, // Czechia
					{ new String[]{"sc"}, new int[]{-2}, new String[]{}, new int[]{} }, // Denmark
					{ new String[]{b1 ? "vh" : "vhr", b2 ? "bt" : "tt"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Djibouti
					{ new String[]{"cs", "ms", "sc"}, new int[]{-2, -2, -2}, new String[]{"mc"}, new int[]{-2} }, // Dominica
					{ new String[]{b1 ? (b1a ? "tl" : "tr") : (b1a ? "bl" : "br"), !b1 ? (!b1a ? "tl" : "tr") : (!b1a ? "bl" : "br"), b1 ? (!b1a ? "tl" : "tr") : (!b1a ? "bl" : "br"), !b1 ? (b1a ? "tl" : "tr") : (b1a ? "bl" : "br"), "cs"}, new int[]{-2, 1, -2, 3, 0}, new String[]{"mc"}, new int[]{bac1 ? 1 : 2} }, // Dominican Republic
					{ new String[]{b1 ? "vh" : "vhr", b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{sigil}, new int[]{bac2} }, // Ecuador
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{sigil}, new int[]{-2} }, // Egypt
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, 1}, new String[]{"moj"}, new int[]{-2} }, // El Salvador
					{ new String[]{"sc"}, new int[]{-2}, new String[]{}, new int[]{} }, // England
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Equatorial Guinea
					{ new String[]{b1 ? "vh" : "vhr", b2 ? "bt" : "tt"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Eritrea
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Estonia
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs", "cs"}, new int[]{-2, 1, -2}, new String[]{"mr"}, new int[]{-2} }, // Eswatini
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{"mc"}, new int[]{-2} }, // Ethiopia
					{ new String[]{}, new int[]{}, new String[]{ba1 ? (ba1a ? "tl" : "tr") : (ba1a ? "bl" : "br")}, new int[]{-2} }, // Fiji
					{ new String[]{b1 ? "bs" : "ts", !b1 ? "hhb" : "hh", "cs"}, new int[]{-2, 1, 0}, new String[]{}, new int[]{} }, // Finland
					{ new String[]{b1 ? "bs" : "ts", !b1 ? "bs" : "ts"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // France
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Gabon
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs", "cs"}, new int[]{-2, -2, -2}, new String[]{}, new int[]{} }, // Gambia
					{ new String[]{"sc"}, new int[]{-2}, new String[]{}, new int[]{} }, // Georgia
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Germany
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{"flo", "mc"}, new int[]{-2, -1} }, // Ghana
					{ new String[]{"ss"}, new int[]{-2}, new String[]{ba1 ? (ba1a ? "tl" : "tr") : (ba1a ? "bl" : "br")}, new int[]{0} }, // Greece
					{ new String[]{b1 ? "bt" : "tt", !b1 ? "bt" : "tt"}, new int[]{-2, 1}, new String[]{"bo", "mc"}, new int[]{-2, -1} }, // Grenada
					{ new String[]{b1 ? "bs" : "ts", !b1 ? "bs" : "ts"}, new int[]{-2, 1}, new String[]{"moj"}, new int[]{-2} }, // Guatemala
					{ new String[]{b1 ? "bs" : "ts", !b1 ? "bs" : "ts"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Guinea
					{ new String[]{b1 ? "vh" : "vhr", b2 ? "bs" : "ts"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Guinea-Bissau
					{ new String[]{b1 ? "bt" : "tt"}, new int[]{-2}, new String[]{}, new int[]{} }, // Guyana
					{ new String[]{b1 ? "vh" : "vhr"}, new int[]{-2}, new String[]{"cre"}, new int[]{-2} }, // Haiti
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, 1}, new String[]{}, new int[]{} }, // Honduras
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Hungary
					{ new String[]{"ms", "cs", "sc"}, new int[]{-2, 1, -2}, new String[]{}, new int[]{} }, // Iceland
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{"mc"}, new int[]{-2} }, // India
					{ new String[]{b1 ? "vh" : "vhr"}, new int[]{-2}, new String[]{}, new int[]{} }, // Indonesia
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{"moj"}, new int[]{bac1 ? 1 : 2} }, // Iran
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{sigil}, new int[]{-2} }, // Iraq
					{ new String[]{b1 ? "bs" : "ts", !b1 ? "bs" : "ts"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Ireland
					{ new String[]{"ss", "cs"}, new int[]{-2, 0}, new String[]{"flo"}, new int[]{1} }, // Israel
					{ new String[]{b1 ? "bs" : "ts", !b1 ? "bs" : "ts"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Italy
					{ new String[]{b1 ? "bt" : "tt", !b1 ? "bt" : "tt", "cr"}, new int[]{-2, 1, -2}, new String[]{}, new int[]{} }, // Jamaica
					{ new String[]{}, new int[]{}, new String[]{"mc"}, new int[]{-2} }, // Japan
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs", b3 ? "bt" : "tt"}, new int[]{-2, -2, -2}, new String[]{}, new int[]{} }, // Jordan
					{ new String[]{}, new int[]{}, new String[]{"mc"}, new int[]{-2} }, // Kazakhstan
					{ new String[]{"ss", b2 ? "ls" : "rs", !b2 ? "ls" : "rs"}, new int[]{-2, -2, -2}, new String[]{"mc"}, new int[]{0} }, // Kenya
					{ new String[]{b1 ? "vh" : "vhr", b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{"BEHIND", "mc"}, new int[]{-2} }, // Kiribati
					{ new String[]{}, new int[]{}, new String[]{sigil}, new int[]{-2} }, // Kosovo
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs", b3 ? "bt" : "tt"}, new int[]{-2, -2, -2}, new String[]{}, new int[]{} }, // Kuwait
					{ new String[]{}, new int[]{}, new String[]{"flo"}, new int[]{-2} }, // Kyrgyzstan
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, 1}, new String[]{"mc"}, new int[]{-2} }, // Laos
					{ new String[]{"cs"}, new int[]{-2}, new String[]{}, new int[]{} }, // Lativa
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, 1}, new String[]{sigil}, new int[]{-2} }, // Lebanon
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{sigil}, new int[]{-2} }, // Lethoso
					{ new String[]{"ss"}, new int[]{-2}, new String[]{ba1 ? (ba1a ? "tl" : "tr") : (ba1a ? "bl" : "br")}, new int[]{-2} }, // Liberia
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{"moj"}, new int[]{-2} }, // Libya
					{ new String[]{b1 ? "vh" : "vhr"}, new int[]{-2}, new String[]{}, new int[]{} }, // Liechtenstein
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Lithuania
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Luxembourg
					{ new String[]{b1 ? "vh" : "vhr", b2 ? "bs" : "ts"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Madagascar
					{ new String[]{b1 ? "ls" : "rs", "flo", !b1 ? "ls" : "rs"}, new int[]{-2, 0, -2}, new String[]{}, new int[]{} }, // Malawi
					{ new String[]{"ss"}, new int[]{-2}, new String[]{ba1 ? (ba1a ? "tl" : "tr") : (ba1a ? "bl" : "br")}, new int[]{-2} }, // Malaysia
					{ new String[]{"bo"}, new int[]{-2}, new String[]{"moj"}, new int[]{-2} }, // Maldives
					{ new String[]{b1 ? "bs" : "ts", !b1 ? "bs" : "ts"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Mali
					{ new String[]{b1 ? "hh" : "hhb"}, new int[]{-2}, new String[]{}, new int[]{} }, // Malta
					{ new String[]{b1 ? "drs" : "dls", b1 ? (b1a ? "lud" : "rud") : (b1a ? "ld" : "rd")}, new int[]{-2, 0}, new String[]{}, new int[]{} }, // Marshall Islands
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, 1}, new String[]{"moj"}, new int[]{-2} }, // Mauritania
					{ new String[]{b1 ? "vh" : "vhr", b2 ? "ls" : "rs", !b2 ? "ls" : "rs"}, new int[]{-2, -2, -2}, new String[]{}, new int[]{} }, // Mauritius
					{ new String[]{b1 ? "bs" : "ts", !b1 ? "bs" : "ts"}, new int[]{-2, -2}, new String[]{sigil}, new int[]{-2} }, // Mexico
					{ new String[]{}, new int[]{}, new String[]{"mr"}, new int[]{-2} }, // Micronesia
					{ new String[]{b1 ? "bs" : "ts", !b1 ? "bs" : "ts"}, new int[]{-2, -2}, new String[]{sigil}, new int[]{-2} }, // Moldova
					{ new String[]{b1 ? "vh" : "vhr"}, new int[]{-2}, new String[]{}, new int[]{} }, // Monaco
					{ new String[]{b1 ? "bs" : "ts", !b1 ? "bs" : "ts"}, new int[]{-2, 1}, new String[]{}, new int[]{} }, // Mongolia
					{ new String[]{}, new int[]{}, new String[]{"cre", "bo"}, new int[]{-1} }, // Montenegro
					{ new String[]{}, new int[]{}, new String[]{"flo"}, new int[]{-2} }, // Morocco
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Mozambique
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{"flo", "mc"}, new int[]{-2, -1} }, // Myanmar
					{ new String[]{b1 ? (b1a ? "lud" : "rud") : (b1a ? "ld" : "rd"), b1 ? "drs" : "dls"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Namibia
					{ new String[]{"cs"}, new int[]{-2}, new String[]{}, new int[]{} }, // Nauru
					//{ new String[]{}, new int[]{}, new String[]{}, new int[]{} }, // Nepal
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Netherlands
					{ new String[]{}, new int[]{}, new String[]{ba1 ? (ba1a ? "tl" : "tr") : (ba1a ? "bl" : "br")}, new int[]{-2} }, // New Zealand
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, 1}, new String[]{"flo"}, new int[]{-2} }, // Nicaragua
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{"mc"}, new int[]{bac1 ? 1 : 2} }, // Niger
					{ new String[]{b1 ? "bs" : "ts", !b1 ? "bs" : "ts"}, new int[]{-2, 1}, new String[]{}, new int[]{} }, // Nigeria
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, 1}, new String[]{"mc"}, new int[]{-2} }, // North Korea
					{ new String[]{"sc", "cr"}, new int[]{-2, 1}, new String[]{"flo", "mc"}, new int[]{0, 1} }, // North Macedonia
					{ new String[]{"ms", "cs", "sc"}, new int[]{-2, 1, -2}, new String[]{}, new int[]{} }, // Norway
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs", b3 ? "bs" : "ts"}, new int[]{-2, -2, -2}, new String[]{}, new int[]{} }, // Oman
					{ new String[]{b1 ? "bs" : "ts"}, new int[]{-2}, new String[]{"moj"}, new int[]{-2} }, // Pakistan
					{ new String[]{}, new int[]{}, new String[]{"mc"}, new int[]{-2} }, // Palau
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs", b3 ? "bt" : "tt"}, new int[]{-2, -2, -2}, new String[]{}, new int[]{} }, // Palestine
					{ new String[]{b1 ? "vh" : "vhr", b2 ? (b2a ? "tl" : "tr") : (b2a ? "bl" : "br"), !b2 ? (!b2a ? "tl" : "tr") : (!b2a ? "bl" : "br")}, new int[]{-2, -2, -2, 2}, new String[]{}, new int[]{} }, // Panama
					{ new String[]{b1 ? (b1a ? "lud" : "rud") : (b1a ? "ld" : "rd")}, new int[]{-2}, new String[]{}, new int[]{} }, // Papua New Guinea
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{"mc"}, new int[]{-2} }, // Paraguay
					{ new String[]{b1 ? "bs" : "ts", !b1 ? "bs" : "ts"}, new int[]{-2, 1}, new String[]{"flo"}, new int[]{-2} }, // Peru
					{ new String[]{b1 ? "vh" : "vhr", b2 ? "bt" : "tt"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Philippines
					{ new String[]{b1 ? "vh" : "vhr"}, new int[]{-2}, new String[]{}, new int[]{} }, // Poland
					{ new String[]{b1 ? "hh" : "hhb"}, new int[]{-2}, new String[]{"mc"}, new int[]{-2} }, // Portugal
					{ new String[]{b1 ? "bts" : "tts"}, new int[]{-2}, new String[]{}, new int[]{} }, // Qatar
					{ new String[]{b1 ? "bs" : "ts", !b1 ? "bs" : "ts"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Romania
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Russia
					{ new String[]{b1 ? "vh" : "vhr", b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Rwanda
					{ new String[]{b1 ? (b1a ? "lud" : "rud") : (b1a ? "ld" : "rd"), b1 ? "drs" : "dls"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Saint Kitts and Nevis
					{ new String[]{}, new int[]{}, new String[]{sigil}, new int[]{-2} }, // Saint Lucia
					{ new String[]{b1 ? "bs" : "ts", !b1 ? "bs" : "ts"}, new int[]{-2, -2}, new String[]{sigil}, new int[]{bac1 ? 1 : 2} }, // Saint Vincent and the Grenadines
					{ new String[]{}, new int[]{}, new String[]{ba1 ? (ba1a ? "tl" : "tr") : (ba1a ? "bl" : "br")}, new int[]{-2} }, // Samoa
					{ new String[]{b1 ? "vh" : "vhr"}, new int[]{-2}, new String[]{"moj"}, new int[]{-2} }, // San Marino
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs", b3 ? "bt" : "tt"}, new int[]{-2, 1, -2}, new String[]{}, new int[]{} }, // Sao Tome and Principe
					{ new String[]{}, new int[]{}, new String[]{"cre"}, new int[]{-2} }, // Saudi Arabia
					{ new String[]{"cr"}, new int[]{-2}, new String[]{}, new int[]{} }, // Scotland
					{ new String[]{b1 ? "bs" : "ts", !b1 ? "bs" : "ts"}, new int[]{-2, -2}, new String[]{"flo", "mc"}, new int[]{bac1 ? 1 : 2, -1} }, // Senegal
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{sigil}, new int[]{bac1 ? 1 : 2} }, // Serbia
					//{ new String[]{}, new int[]{}, new String[]{}, new int[]{} }, // Seychelles
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Sierra Leone
					{ new String[]{b1 ? "vh" : "vhr"}, new int[]{-2}, new String[]{}, new int[]{} }, // Singapore
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{sigil}, new int[]{bac2} }, // Slovakia
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{sigil}, new int[]{bac2} }, // Slovenia
					{ new String[]{b1 ? (b1a ? "lud" : "rud") : (b1a ? "ld" : "rd"), b1 ? "drs" : "dls"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Solomon Islands
					{ new String[]{}, new int[]{}, new String[]{"flo", "mc"}, new int[]{-1} }, // Somalia
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs", b3 ? "bt" : "tt"}, new int[]{-2, -2, -2}, new String[]{}, new int[]{} }, // South Africa
					{ new String[]{}, new int[]{}, new String[]{"flo", "mc"}, new int[]{-2} }, // South Korea
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs", "cs", b4 ? "bt" : "tt"}, new int[]{-2, -2, -2, -2}, new String[]{}, new int[]{} }, // South Sudan
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, 1}, new String[]{sigil}, new int[]{1} }, // Spain
					{ new String[]{b1 ? "hh" : "hhb", b1 ? "ts" : "bs"}, new int[]{-2, -2}, new String[]{"bo"}, new int[]{-2} }, // Sri Lanka
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs", b3 ? "bt" : "tt"}, new int[]{-2, -2, -2}, new String[]{}, new int[]{} }, // Sudan
					{ new String[]{"ss", "cs"}, new int[]{-2, -2}, new String[]{"flo", "mc"}, new int[]{-2, -1} }, // Suriname
					{ new String[]{b1 ? "bs" : "ts", !b1 ? "hhb" : "hh", "cs"}, new int[]{-2, 1, 0}, new String[]{}, new int[]{} }, // Sweden
					{ new String[]{"ms", "cs", "bs", "ts", "bo"}, new int[]{-2, 1, 0, 0, 0}, new String[]{}, new int[]{} }, // Switzerland
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Syria
					{ new String[]{}, new int[]{}, new String[]{ba1 ? (ba1a ? "tl" : "tr") : (ba1a ? "bl" : "br")}, new int[]{-2} }, // Taiwan
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{"flo"}, new int[]{-2} }, // Tajikistan
					{ new String[]{b1 ? (b1a ? "lud" : "rud") : (b1a ? "ld" : "rd"), b1 ? "drs" : "dls"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Tanzania
					{ new String[]{"ss", "cs"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Thailand
					{ new String[]{b1 ? "bt" : "tt"}, new int[]{-2}, new String[]{}, new int[]{} }, // Timor-Leste
					{ new String[]{"ss"}, new int[]{-2}, new String[]{ba1 ? (ba1a ? "tl" : "tr") : (ba1a ? "bl" : "br")}, new int[]{-2} }, // Togo
					{ new String[]{}, new int[]{}, new String[]{ba1 ? (ba1a ? "tl" : "tr") : (ba1a ? "bl" : "br")}, new int[]{-2} }, // Tonga
					{ new String[]{b1 ? "drs" : "dls"}, new int[]{-2}, new String[]{}, new int[]{} }, // Trinidad and Tobago
					{ new String[]{}, new int[]{}, new String[]{"mc"}, new int[]{-2} }, // Tunisia
					{ new String[]{}, new int[]{}, new String[]{"moj"}, new int[]{-2} }, // Turkey
					{ new String[]{b1 ? "bts" : "tts"}, new int[]{-2}, new String[]{}, new int[]{} }, // Turkmenistan
					{ new String[]{}, new int[]{}, new String[]{ba1 ? (ba1a ? "tl" : "tr") : (ba1a ? "bl" : "br")}, new int[]{-2} }, // Tuvalu
					{ new String[]{b1 ? "vh" : "vhr", "ss"}, new int[]{-2, -2}, new String[]{"mc"}, new int[]{-2} }, // Uganda
					{ new String[]{b1 ? "vh" : "vhr"}, new int[]{-2}, new String[]{}, new int[]{} }, // Ukraine
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs", b3 ? "bs" : "ts"}, new int[]{-2, -2, -2}, new String[]{}, new int[]{} }, // United Arab Emirates
					{ new String[]{b1 ? "drs" : "dls", !b1 ? "drs" : "dls", "cr", "ms", "cs", "sc"}, new int[]{-2, 1, -2, 1, 1, 3}, new String[]{}, new int[]{} }, // United Kingdom
					{ new String[]{"ss"}, new int[]{-2}, new String[]{ba1 ? (ba1a ? "tl" : "tr") : (ba1a ? "bl" : "br")}, new int[]{-2} }, // United States
					{ new String[]{"ss"}, new int[]{-2}, new String[]{ba1 ? (ba1a ? "tl" : "tr") : (ba1a ? "bl" : "br")}, new int[]{-2} }, // Uruguay
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Uzbekistan
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs", b3 ? "bt" : "tt"}, new int[]{-2, -2, 0}, new String[]{}, new int[]{} }, // Vanuatu
					{ new String[]{b1 ? "hh" : "hhb"}, new int[]{-2}, new String[]{}, new int[]{} }, // Vatican City
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Venezuela
					{ new String[]{}, new int[]{}, new String[]{"flo", "mc"}, new int[]{-1} }, // Vietnam
					{ new String[]{b1 ? "vh" : "vhr"}, new int[]{-2}, new String[]{"cre"}, new int[]{-2} }, // Wales
					{ new String[]{b1 ? "ls" : "rs", !b1 ? "ls" : "rs"}, new int[]{-2, -2}, new String[]{}, new int[]{} }, // Yemen
					{ new String[]{}, new int[]{}, new String[]{ba1 ? (ba1a ? "tl" : "tr") : (ba1a ? "bl" : "br")}, new int[]{-2} }, // Zambia
					{ new String[]{"ss", "cs", b3 ? "bt" : "tt"}, new int[]{-2, -2, -2}, new String[]{}, new int[]{} }, // Zimbabwe
					// Custom flaggy stuff that's otherwise unused patterns
					{ new String[]{"FORCEACCENT", b1 ? "bri" : (b1a ? "gra" : "gru")}, new int[]{-2}, new String[]{ ba1 ? "cbo" : "sku" }, new int[]{-2} }, // v3.1.1 added FORCEACCENT notation
					
			};
			
			// Choose the base
			chosencountry = random.nextInt(nationBaseTemplates.length);
			baseTemplate = (String[]) nationBaseTemplates[chosencountry][0];
			baseColors = (int[]) nationBaseTemplates[chosencountry][1];
			
			// Choose the accent
			// v3.1.1 - Force an accent if the base template contains the FORCEACCENT key in slot 0
			while (true)
			{
				chosencountry = random.nextInt(nationBaseTemplates.length);
				accentTemplate = (String[]) nationBaseTemplates[chosencountry][2];
				accentColors = (int[]) nationBaseTemplates[chosencountry][3];
				
				if (baseTemplate.length > 0 && baseTemplate[0].equals("FORCEACCENT") && accentTemplate.length == 0) {continue;} // Not valid. Try again.
				break;
			}
			
			// v3.1.1 - simplifies calculations below
			int baseTemplateLengthWithoutTriggers = baseTemplate.length - ((baseTemplate.length > 0 && baseTemplate[0].equals("FORCEACCENT")) ? 1 : 0);
			int accentTemplateLengthWithoutTriggers = accentTemplate.length - ((accentTemplate.length > 0 && accentTemplate[0].equals("BEHIND")) ? 1 : 0);
			
			// Do a bunch of checks. If passed, break out.
			boolean illegalcolors = false;
			
			// Check to see if the accent references an index outside baseColors
			for (int cmeta : accentColors)
			{
				if(cmeta>=baseColors.length)
				{
					illegalcolors = true;
					break;
				}
			}
			if (
					baseTemplateLengthWithoutTriggers == 0 && // There is no base pattern, and... 
					((accentColors.length  == 1 && accentColors[0] != -2) // ...the single accent pattern doesn't add any new colors
					|| (accentColors.length == 2 &&
					(accentColors[0] != -2 && accentColors[1] != -2) // ...the double accent pattern doesn't add any new colors
					))
					|| illegalcolors
					) 
			{
				continue;
			}
			
			
			
			if(
					baseTemplateLengthWithoutTriggers == baseColors.length // Changed in v3.1.1
					&& (accentTemplate.length - ((accentTemplate.length > 0 && accentTemplate[0].equals("BEHIND")) ? 1 : 0)) == accentColors.length
					&& (baseTemplateLengthWithoutTriggers + accentTemplate.length - (accentTemplate.length > 0 && accentTemplate[0].equals("BEHIND") ? 1 : 0)) <= 6 // You are limited to six layers in survival
					&& (baseTemplateLengthWithoutTriggers + accentTemplate.length - (accentTemplate.length > 0 && accentTemplate[0].equals("BEHIND") ? 1 : 0)) > 0 // I'd rather not have completely blank banners
					)
			{
				break;
			}
			
		}
		
		
		// Populate the color palette that will be applied to the banner
		
		// Count how many colors are needed
		int colorcount=1; // Starts at 1 because you need one for the banner
		for (int color : baseColors)
		{
			if (color==-2) {colorcount++;}
		}
		for (int color : accentColors)
		{
			if (color==-2) {colorcount++;}
		}
		
		
		ArrayList<Integer> bannerColors = new ArrayList<Integer>();
		
		while (bannerColors.size() < colorcount)
		{
			if (forceBannerColor>=0 && bannerColors.size()==0) {bannerColors.add(forceBannerColor);} // In case you're backporting a banner for a previously created village
			if (forceBannerColor2>=0 && bannerColors.size()==1)  {bannerColors.add(forceBannerColor2);}
			
			int newpotentialcolor = (Integer) FunctionsVN.weightedRandom(COLOR_METAS, COLOR_WEIGHTS, random);
			// Search through the already-collected numbers and see if this new one is unique
			boolean flag = true;
			if (bannerColors.size() > 0)
			{
				for (int i : bannerColors)
				{
					if (
							i==newpotentialcolor // Color is already in the set, so reject it.
							// Reject the color if it should not be paired with certain other colors
							|| ((i==2 && newpotentialcolor==10) || (i==10 && newpotentialcolor==2)) // Lime and Green
							|| ((i==5 && newpotentialcolor==13) || (i==13 && newpotentialcolor==5)) // Magenta and Purple
							|| ((i==6 && newpotentialcolor==12) || (i==12 && newpotentialcolor==6)) // Light Blue and Cyan
							)
					{flag = false; break;}
				}
			}
			if (flag) {bannerColors.add(newpotentialcolor);}
		}
		// DON'T shuffle the set, or else the forced banner color is no longer the base color!
		
		// Now that you have your patterns and colors, construct the arrays that will be passed to the makeBanner function
		
		ArrayList<String> patternArray = new ArrayList<String>();
		ArrayList<Integer> colorArray = new ArrayList<Integer>();
		colorArray.add(bannerColors.get(0)); // Add the base banner color
		
		// Add in the base template and colors
		int colorint = 1;
		boolean isAccentForced = (baseTemplate.length > 0 && baseTemplate[0].equals("FORCEACCENT")); // Added in v3.1.1
		
		for (int i=0; i < baseTemplate.length - (isAccentForced ? 1 : 0); i++) // Changed in v3.1.1
		{
			// Add pattern piece
			patternArray.add(baseTemplate[i+(isAccentForced ? 1 : 0)]); // Changed in v3.1.1
			
			// Add color value
			if (baseColors[i]==-2) // -2 indicates "draw a new color"
			{
				colorArray.add(bannerColors.get(colorint++));
			}
			else if (baseColors[i]==-1) // -1 indicates "duplicate the color in the previous index"; used for accents
			{
				colorArray.add(colorArray.get(colorArray.size()-1));
			}
			else // Otherwise, the value is a reference to a specific layer's color
			{
				colorArray.add(colorArray.get(baseColors[i]));
			}
		}
		
		// Add in the accent template and colors
		boolean isAccentBackground = accentTemplate.length > 0 && accentTemplate[0].equals("BEHIND"); // This switches on if the accent is intended to fall between the starting banner and the base pattern
		
		for (int i=0; i<accentColors.length; i++)
		{
			// Add pattern piece
			if (isAccentBackground)
			{
				// Add behind base pattern
				patternArray.add(i, accentTemplate[i+1]);
			}
			else
			{
				// Add as topmost layer
				patternArray.add(accentTemplate[i]);
			}
			
			
			// Add color value
			if (accentColors[i]==-2) // -2 indicates "draw a new color"
			{
				if (isAccentBackground)
				{
					colorArray.add(i+1, bannerColors.get(colorint++));
				}
				else
				{
					colorArray.add(bannerColors.get(colorint++));
				}
			}
			else if (accentColors[i]==-1) // -1 indicates "duplicate the color in the previous index"; used for accents
			{
				if (isAccentBackground)
				{
					colorArray.add(i+1, colorArray.get(i));
				}
				else
				{
					colorArray.add(colorArray.get(colorArray.size()-1));
				}
				
			}
			else // Otherwise, the value is a reference to a specific layer's color
			{
				if (isAccentBackground)
				{
					colorArray.add(i+1, colorArray.get(accentColors[i]+i));
				}
				else
				{
					colorArray.add(colorArray.get(accentColors[i]));
				}
				
			}
			
		}
		// Okay so now at this stage, we should have a length N pattern array and length N+1 color array.
		
		return new Object[]{patternArray, colorArray}; // Return the bundle, to be re-packaged via FunctionsVN.makeBanner in order to actually make the banner.
				
	}
	
	
    /**
     * Creates a new banner design.
     * Inputs two arrays specifying the patterns and their colors to overlay onto the base.
     * patternColors must be one element longer than patterns, because element 0 is the banner base color.
     */
    public static ItemStack makeBanner(@Nullable ArrayList<String> patterns, @Nullable ArrayList<Integer> patternColors)
    {
		ItemStack bannerstack = ModObjects.chooseModBannerItem();
		
		if (bannerstack==null) {return null;} // Not using a supported mod with banners
		
        NBTTagCompound nbttagcompound1 = new NBTTagCompound();
        
        // Method copied over from 1.10's ItemStack$getSubCompound, which allows you to create the sub compound if one does not exist.
        if (bannerstack.getTagCompound() != null && bannerstack.getTagCompound().hasKey("BlockEntityTag", 10))
        {
        	nbttagcompound1 = bannerstack.getTagCompound().getCompoundTag("BlockEntityTag");
        }
        else
        {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            bannerstack.setTagInfo("BlockEntityTag", nbttagcompound);
            nbttagcompound1 = nbttagcompound;
        }
        
        // TODO - Et Futurum and Gany's Surface use reverse order for dye color meta, which is normal order for block color meta.
        // This will need to change to accommodate mods that use normal dye meta order.
        nbttagcompound1.setInteger("Base", 15-(patternColors.get(0) & 15));
        
        NBTTagList nbttaglist = null;

        if (nbttagcompound1.hasKey("Patterns", 9))
        {
            nbttaglist = nbttagcompound1.getTagList("Patterns", 10);
        }
        else
        {
            nbttaglist = new NBTTagList();
            nbttagcompound1.setTag("Patterns", nbttaglist);
        }
        
        // Set the overlay patterns here
        NBTTagCompound patterntag;
        
        if (patterns!=null && patternColors!= null
        		&& (patterns.size() == patternColors.size()-1)
        		)
        for (int i=0; i < patterns.size(); i++)
        {
        	patterntag = new NBTTagCompound();
            patterntag.setString("Pattern", patterns.get(i));
            // TODO - Et Futurum and Gany's Surface use reverse order for dye color meta, which is normal order for block color meta.
            // This will need to change to accommodate mods that use normal dye meta order.
            patterntag.setInteger("Color", 15-(patternColors.get(i+1) & 15));
            nbttaglist.appendTag(patterntag);
        }
        
        return bannerstack;
    }
	
    /**
     * Creates a new banner design.
     * Inputs an NBTTagCompound containing the banner pattern and color: specifically, BlockEntityTag
     */
    public static ItemStack makeBanner(NBTTagCompound bannertagcompound)
    {
    	ItemStack bannerstack = ModObjects.chooseModBannerItem();
		
		if (bannerstack==null) {return null;} // Not using a supported mod with banners
    	
    	bannerstack.setTagInfo("BlockEntityTag", bannertagcompound);
    	
    	return bannerstack;
    }
    
    
	/**
	 * Inputs a banner stack and returns an NBTTagCompound.
	 * Element 0 is the integer representing the base color.
	 * Element 1 is a tag list containing all of the applied pattern info, in order.
	 */
	public static NBTTagCompound getNBTFromBanner(ItemStack bannerstack)
	{
		NBTTagCompound topcompound = bannerstack.getTagCompound();
		NBTTagCompound nbttagcompound1 = topcompound.getCompoundTag("BlockEntityTag");
        return nbttagcompound1;
	}
	
	
	/**
	 * This method gets and returns the NBTTagCompound containing the village banner data, 
	 * creating it if necessary. It also returns the village's name.
	 * ONLY run this with !world.isRemote
	 */
	public static Object[] getVillageBannerData(EntityLivingBase entity) // v3.1.1 - Changed to EntityLivingBase to allow the player to be an argument
	{
		NBTTagCompound bannerNBT = new NBTTagCompound(); // The thing we're after
		String locationFullName = ""; // Also returned, in case it's useful.
		
		if (!entity.worldObj.isRemote)
		{
			// Generate banner info, regardless of if we make a banner.
    		Object[] newRandomBanner = BannerGenerator.randomBannerArrays(entity.worldObj.rand, -1, -1);
			ArrayList<String> patternArray = (ArrayList<String>) newRandomBanner[0];
			ArrayList<Integer> colorArray = (ArrayList<Integer>) newRandomBanner[1];
			ItemStack villageBanner = BannerGenerator.makeBanner(patternArray, colorArray);
			
			if (villageBanner==null) {return null;} // Not using a supported mod with banners
			
    		int townColorMeta = 15-colorArray.get(0); // This is properly supposed to be reverse dye meta color, because this is block meta color.
    		int townColorMeta2 = colorArray.size()==1 ? townColorMeta : 15-colorArray.get(1);
			
			Village villageNearTarget = entity.worldObj.villageCollectionObj.findNearestVillage((int)entity.posX, (int)entity.posY, (int)entity.posZ, EntityInteractHandler.villageRadiusBuffer);
	        
			if (villageNearTarget != null)
			{
				int centerX = villageNearTarget.getCenter().posX; // Village X position
				int centerY = villageNearTarget.getCenter().posY; // Village Y position
				int centerZ = villageNearTarget.getCenter().posZ; // Village Z position
				
				// Let's see if we can find a sign near that located village center!
				VNWorldDataStructure data = VNWorldDataStructure.forWorld(entity.worldObj, "villagenames3_Village", "NamedStructures");
				NBTTagCompound tagCompound = data.getData();
				Set tagmapKeyset = tagCompound.func_150296_c(); //Gets the town key list: "coordinates"
				
				Iterator itr = tagmapKeyset.iterator();
				String townSignEntry;
				
				boolean signLocated = false; //Use this to record whether or not a sign was found
		        boolean isColony = false; //Use this to record whether or not the village was naturally generated
		        
		        while(itr.hasNext()) { // Going through the list of VN villages
					
					Object element = itr.next();
					townSignEntry = element.toString(); //Text name of village header (e.g. "Kupei, x191 y73 z187")
					
					//The only index that has data is 0:
					NBTTagList nbttaglist = tagCompound.getTagList(townSignEntry, tagCompound.getId());
		            NBTTagCompound villagetagcompound = nbttaglist.getCompoundTagAt(0);
		            
		            // Retrieve the "sign" coordinates
		            int townX = villagetagcompound.getInteger("signX");
		            int townY = villagetagcompound.getInteger("signY");
		            int townZ = villagetagcompound.getInteger("signZ");
		            
		            String namePrefix = villagetagcompound.getString("namePrefix");
		            String nameRoot = villagetagcompound.getString("nameRoot");
		            String nameSuffix = villagetagcompound.getString("nameSuffix");
		            
		            
		            // Now find the nearest Village to that sign's coordinate, within villageRadiusBuffer blocks outside the radius.
		            Village villageNearSign = entity.worldObj.villageCollectionObj.findNearestVillage(townX, townY, townZ, EntityInteractHandler.villageRadiusBuffer);
		            
		            
		            if (villageNearSign == villageNearTarget) { // There is a match between the nearest village to this villager and the nearest village to the sign
		            	
		            	signLocated = true;
		            	
		            	
		            	// Re-Generate banner info so that we can use the pre-generated village color
			    		int previousTownColor = villagetagcompound.getInteger("townColor");
			    		int previousTownColor2 = -1;
			    		if (villagetagcompound.hasKey("townColor2")) {previousTownColor2 = villagetagcompound.getInteger("townColor2");}
			    		Random deterministic = new Random(); deterministic.setSeed(entity.worldObj.getSeed() + FunctionsVN.getUniqueLongForXYZ(townX, townY, townZ));
		            	newRandomBanner = BannerGenerator.randomBannerArrays(deterministic, 15-previousTownColor, 15-previousTownColor2); // This is properly supposed to be reverse dye meta color, because this is block meta color.
						patternArray = (ArrayList<String>) newRandomBanner[0];
						colorArray = (ArrayList<Integer>) newRandomBanner[1];
						villageBanner = BannerGenerator.makeBanner(patternArray, colorArray);
						
						if (villageBanner==null) {return null;}
			            
		            	
		            	// Retrieve the banner info
			            locationFullName = (namePrefix + " " + nameRoot + " " + nameSuffix).trim();
			            
		            	if (villagetagcompound.hasKey("BlockEntityTag"))
		            	{
		            		bannerNBT = villagetagcompound.getCompoundTag("BlockEntityTag");
		            	}
		            	// If there is no banner info, make some
		            	else
		            	{
	        				bannerNBT = BannerGenerator.getNBTFromBanner(villageBanner);
	        				
		            		villagetagcompound.setTag("BlockEntityTag", bannerNBT);
		            		//summon Villager ~ ~ ~ {Profession:1}
		            		// Replace the old tag
		            		nbttaglist.func_150304_a(0, villagetagcompound);
		            		//nbttaglist.removeTag(0);
		            		//nbttaglist.appendTag(villagetagcompound);
		            		tagCompound.setTag(townSignEntry, nbttaglist);
		            		data.markDirty();
		            	}
		            	
		            	break; // No need to keep comparing villages.
		            }
		            
				}
				
		        if (!signLocated) {
		        	
		        	// No well sign was found that matched the villager's village.
		        	// We can assume this is a village WITHOUT a sign. So let's at least give it a name!
		        	
		        	Random deterministic = new Random(); deterministic.setSeed(entity.worldObj.getSeed() + FunctionsVN.getUniqueLongForXYZ(centerX, centerY, centerZ));
		        	String[] newVillageName = NameGenerator.newRandomName("Village", deterministic);
		        	String headerTags = newVillageName[0];
		    		String namePrefix = newVillageName[1];
		    		String nameRoot = newVillageName[2];
		    		String nameSuffix = newVillageName[3];
		    		
		    		locationFullName = (namePrefix + " " + nameRoot + " " + nameSuffix).trim();
		    		
		    		// Make the data bundle to save to NBT
		    		NBTTagList nbttaglist = new NBTTagList();
		    		
		    		NBTTagCompound nbttagcompound1 = new NBTTagCompound();
		            nbttagcompound1.setInteger("signX", centerX);
		            nbttagcompound1.setInteger("signY", centerY);
		            nbttagcompound1.setInteger("signZ", centerZ);
		            nbttagcompound1.setInteger("townColor", townColorMeta); //In case we want to make clay, carpet, wool, glass, etc
		            nbttagcompound1.setInteger("townColor2", townColorMeta2);
		            nbttagcompound1.setString("namePrefix", namePrefix);
		            nbttagcompound1.setString("nameRoot", nameRoot);
		            nbttagcompound1.setString("nameSuffix", nameSuffix);
		            nbttagcompound1.setBoolean("fromEntity", true); // Record whether this name was generated from interaction with an entity
		            
		            // Added in v3.1banner
		            // Form and append banner info
		            bannerNBT = BannerGenerator.getNBTFromBanner(villageBanner);
		            nbttagcompound1.setTag("BlockEntityTag", bannerNBT);
		            
		            nbttaglist.appendTag(nbttagcompound1);
		            
		            // Save the data under a "Villages" entry with unique name based on sign coords
		            data.getData().setTag((namePrefix + " " + nameRoot + " " + nameSuffix).trim() + ", x" + centerX + " y" + centerY + " z" + centerZ, nbttaglist);
		            
		            data.markDirty();
		    		
		        }
			}
			else return null;
			
		}
		
        // Finally, return the banner NBT and village name.
        return new Object[] {bannerNBT, locationFullName};
	}
	
}
