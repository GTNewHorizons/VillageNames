package astrotibs.villagenames.name;

public class NamePiecesMods {
	// Town types
	
	// Characters that are replaced:
	// § with \u00a7
	// Á with \u00c1
	// æ with \u00e6
	// à with \u00e0
	// á with \u00e1
	// ã with \u00e3
	// å with \u00e5
	// ä with \u00e4
	// è with \u00e8
	// é with \u00e9
	// ë with \u00eb
	// í with \u00ed
	// ï with \u00ef
	// ñ with \u00f1
	// ò with \u00f2
	// ó with \u00f3
	// ö with \u00f6
	// ú with \u00fa
	// ü with \u00fc
	// û with \u00fb
	// ÿ with \u00ff
	// ž with \u017e
	
	//Characters that can't be properly displayed:
	// A with tilde: \u00c3
	// a with macron: \u0101
	// s caron with \u0161
	// s/comma with \u0219
	// a breve with \u0103
	// g with dot \u0121
	// G with dot \u0120
	// H with stroke \u0126
	// o with two acutes \u0151
	// h with stroke \u0128
	
	/*
	 * Alien Villager name pieces
	 */
	
	// Single-syllable pieces
	public static String[] alienVillager_oneSylBegin_default = new String[]{"Ae", "Ba", "Cthaa", "Dry", "Ghro", "Glee", "Gloo", "Ha", "Kra", "Krai", "Kri", "La", "Ma", "Nee", "Nu", "O", "Oo", "Tho", "To", "Wa", "Ye", "Yi", "Zha"};
	public static String[] alienVillager_oneSylEnd_default = new String[]{"b", "c", "d", "g", "g", "g", "k", "k", "m", "n", "n", "n", "ng", "ng", "njh", "r", "r", "rn", "st", "t", "th", "th", "th"};
	
	// Syllable 1 transitional
	public static String[] alienVillager_syl1Trans_default = new String[]{"'Ym", "A", "A", "A", "A", "A", "A", "A", "Ab", "Ab", "Ai", "Am", "Ar", "Ar", "Ar", "At", "Ay", "Az", "B", "Ba", "Ba", "Bok", "Bugg", "Bya", "C", "Chaug", "Co", "Co", "Crom", "Cthaegh", "Cthu", "Cthu", "Cthyl", "Ctog", "Cxa", "Cy", "Cy\u00e4", "D", "Da", "Dhu", "Di", "Dray", "Dy", "Dy", "Dz\u00e9", "E", "Ei", "Ei", "En", "Fu", "Ge", "Gha", "Ghis", "Ghroth", "Gi", "Gla", "Go", "Go", "Gog", "Gol", "Gtu", "Gur", "Gur", "Gwar", "Gzx", "H", "Ha", "Ha", "Hai", "Has", "Hnar", "Hziul", "I", "I", "Ial", "Idh", "Ig", "Il", "In", "Is", "Ja", "Je", "Juk", "K", "Ka", "Kaa", "Kaajh", "Kag", "Kas", "Kau", "Khal", "Klaa", "Klos", "Klu", "Ktha", "Kthaw", "Kur", "Lex", "Lloi", "Lo", "Lo", "Lu", "Ly", "M", "M", "Mh", "Mlan", "Mnom", "Mor", "Mor", "Mor", "Mort", "Mril", "My", "N", "Nath", "Nc", "Nc", "Ngir", "Ngyr", "No", "Nor", "Ny", "Ny", "Nyag", "Nyar", "Nyc", "Nyog", "O", "O", "Ob", "Oj", "Ol", "Or", "Ou", "Oz", "Pan", "Per", "Phan", "Phar", "Po", "Psu", "Ptar", "Q", "Qu", "Qua", "R", "Ra", "Raan", "Rag", "Rh", "Rha", "Rhan", "Rho", "Rid", "Ro", "S", "Sa", "Saa", "Sca", "Se", "Se", "Sed", "Sfat", "Sha", "Sha", "Sha", "Shab", "Shau", "Sheb", "Shis", "Shlith", "Sho", "Shte", "Shub", "Shudde", "Shuy", "Stha", "Suc", "Sum", "Swa", "Ta", "Ter", "Th", "Tha", "Tha", "Tha", "Tha", "Tru", "Tsa", "Tu", "Tu", "Tulz", "Ub", "Uit", "Ul", "Ut", "Uv", "Ver", "Vhu", "Vi", "Vile", "Vol", "Volg", "Vor", "Vth", "Vul", "Xa", "Xal", "Xc", "Xex", "Xi", "Xi", "Xin", "Xird", "Xo", "Xox", "Y", "Y", "Y", "Y", "Yad", "Yag", "Yagg", "Yc", "Yegg", "Yhag", "Yhash", "Yhoun", "Yibb", "Yid", "Yo", "Yog", "Yog", "Yor", "Ys", "Yu", "Yuck", "Yug", "Z", "Za", "Ze", "Zig", "Zin", "Zo", "Zoth", "Zs", "Zush", "Zvil"};
	
	// Syllable 2 terminal
	public static String[] alienVillager_syl2Term_default = new String[]{"-Gath", "-Ha", "-Hoor", "-Kthu", "-Nihl", "-Oct", "-Shabb", "-Shash", "-Teth", "-Tstll", "-yaa", "'Kaalbh", "'keth", "'kru", "'lla", "'lor", "'mbu", "'Naath", "'rygh", "bek", "bon", "bur", "deh", "dens", "doth", "gha", "gha", "gog", "gon", "gor", "gra", "guth", "gy", "hal", "hash", "hort", "hoth", "hra", "ith", "ki", "kon", "koth", "la", "la", "ley", "lhu", "lith", "llgh", "loth", "lut", "mash", "min", "mo", "mus", "mus", "nar", "nee", "neth", "neth", "ni", "nid", "nus", "od", "ol", "ol", "qu", "qua", "quah", "ra", "rick", "rog", "rot", "rug", "ryx", "scha", "se", "ta", "tha", "thach", "thak", "thar", "thog", "thol", "thoom", "thot", "tis", "tli", "toon", "tu", "tur", "tur", "tyos", "urhn", "w\u00e0", "ya", "ya"};
	// Syllable 2 transitional
	public static String[] alienVillager_syl2Trans_default = new String[]{"-Ax", "-go", "-Gol", "-Hor", "-Ka", "-Ko", "-Kor", "-Nig", "-Om", "-Ra", "-Sa", "-Sit", "-So", "-Te", "-Thad", "'bas", "'chte", "'endr", "'gnu", "'gol", "'Ho", "'ith", "'la", "'li", "'Na", "'Na", "'nar", "'nem", "'thal", "'Thul", "'tog", "'tya", "'Ulls", "'yth", "^Cru", "^M", "^Thor", "a", "a", "a", "a", "a", "a", "at", "ath", "ba", "bad", "bith", "bo", "bo", "cha", "chil", "cra", "da", "da", "da", "da", "de", "dig", "dy", "dy", "eg", "ga", "garg", "g\u00f2", "gor", "ha", "ho", "hog", "i", "i", "iig", "in", "kla", "kra", "la", "la", "lach", "le", "li", "li", "lim", "lo", "lu", "lu", "lur", "ma", "ma", "mel", "mi", "mo", "mon", "mut", "na", "na", "n\u00e0g", "nai", "nal", "nar", "no", "nog", "noth", "nu", "o", "ogh", "oht", "pan", "pes", "phan", "phoom", "pog", "quo", "ra", "ra", "rash", "rth", "ru", "sa", "sai", "sei", "so", "sog", "ssu", "stal", "ta", "ta", "ta", "te", "te", "tep", "tha", "tha", "thal", "thog", "thog", "thu", "thu", "ti", "to", "tol", "tyl", "ueb", "ur", "va", "val", "was", "xu", "ya", "ya", "yi", "zhor", "zil", "zom"};
	
	// Syllable 3 terminal
	public static String[] alienVillager_syl3Term_default = new String[]{"-az", "-Gath", "-Ho", "-Ka", "-Thun", "-ya", "-Yai", "-Zhah", "'ell", "'ig", "'lu", "'ngo", "'st", "^Faugn", "a", "ach", "ath", "bra", "ca", "chenn", "cllp", "dag", "don", "don", "doss", "fu", "gash", "geg", "gen", "gha", "ghua", "ghua", "gorth", "goth", "gua", "gua", "gua", "ha", "hra", "hu", "ka", "kal", "kath", "ki", "kluth", "kon", "la", "la", "la", "la", "la", "lar", "los", "loth", "lu", "lun", "lun", "luq", "ma", "ma", "mis", "mog", "mon", "mon", "na", "nai", "ne", "nga", "nos", "noth", "nus", "ot", "pha", "pha", "pha", "qua", "rah", "rak", "rha", "roth", "ru", "sa", "sa", "sha", "sha", "tal", "tan", "tha", "tha", "tha", "thath", "thess", "thoth", "thoth", "thua", "tii", "tlan", "um", "va", "veg", "wrl", "ya", "yeg", "yig", "\u00ffk", "yoth", "zoth"};
	// Syllable 3 transitional
	public static String[] alienVillager_syl3Trans_default = new String[]{"-Ghah", "-Gor", "-Hr", "-Na", "-Sath", "-Yg", "'i", "'so", "^Gn", "^Ryo", "^Shai", "^Ut", "^Z", "a", "ca", "da", "ebh", "gal", "gi", "gn", "gor", "gu", "i", "ig", "ii", "le", "li", "li", "li", "mi", "na", "\u00f1a", "nniss", "o", "o", "pith", "ra", "ril", "ro", "se", "sed", "shug", "tho", "u", "ui", "zhem"};
	
	// Syllable 4 terminal
	public static String[] alienVillager_syl4Term_default = new String[]{"-Tha", "'Her", "'tho", "a", "a", "ah", "an", "ba", "cha", "den", "ghi", "glys", "gos", "goth", "gua", "ka", "korth", "kug", "la", "loth", "nac", "nb", "nis", "on", "on", "ops", "os", "pac", "rath", "rath", "shal", "suan", "sz", "taus", "tep", "yx"};
	// Syllable 4 transitional
	public static String[] alienVillager_syl4Trans_default = new String[]{"-B", "'Nal", "'uq", "^Eg", "^Gwan", "cu", "ga", "i", "mn", "th\u00e6"};
	
	// Syllable 5 terminal
	public static String[] alienVillager_syl5Term_default = new String[]{"'nk", "a", "du", "e", "le", "nis", "zhah"};
	// Syllable 5 transitional
	public static String[] alienVillager_syl5Trans_default = new String[]{"-serr", "qa", "n\u00ef"};
	
	// Syllable 6 terminal
	public static String[] alienVillager_syl6Term_default = new String[]{"-Mog", "'roth"};
	// Syllable 6 transitional
	public static String[] alienVillager_syl6Trans_default = new String[]{"\u00e9l"};
	
	// Syllable 7 terminal
	public static String[] alienVillager_syl7Term_default = new String[]{"l\u00fbs"};
	
	
	
	/*
	 * Alien Village name pieces
	 */
	
	// Single-syllable pieces
	public static String[] alienVillage_oneSylBegin_default = new String[]{"Bi", "Bla", "Bo", "Bo", "Cloo", "Da", "Ga", "Hei", "Hi", "Ko", "Kra", "Pe", "Pla", "Pri", "Ptha", "Roo", "Seu", "Sha", "Spei", "To", "Vhoo", "Wo", "Xo", "Ya", "Yi"};
	public static String[] alienVillage_oneSylEnd_default = new String[]{"fft", "hr", "k", "ksh", "lff", "m", "nc", "nck", "nck", "nd", "nd", "nz", "pff", "re", "rg", "rl", "rp", "rt", "s", "ss", "st", "th", "th", "wes"};
	
	// Syllable 1 transitional
	public static String[] alienVillage_syl1Trans_default = new String[]{"A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "Ab", "Ae", "Aes", "Ag", "Al", "Al", "Al", "Al", "Al", "Al", "Al", "Am", "An", "An", "An", "Ar", "Ar", "Ar", "Ar", "Ar", "Ar", "Ar", "Ar", "Ar", "Ar", "Ar", "Arc", "As", "At", "Au", "Aus", "Baa", "Baal", "Bar", "Bel", "Bet", "Bil", "Bo", "Bo", "Bon", "Bos", "Bou", "Brad", "Bray", "Bri", "Bu", "Buck", "Byl", "Bz", "Ca", "Ca", "Ca", "Ca", "Ca", "Car", "Car", "Car", "Car", "Cau", "Cau", "Cel", "Cha", "Cle", "Cle", "Cog", "Con", "Con", "Cor", "Cris", "Cush", "Cy", "Da", "Da", "Dan", "Dar", "De", "De", "Di", "Di", "Dil", "Do", "Dop", "Dra", "Dzie", "E", "E", "Ep", "Et", "Eu", "Ex", "Fe", "Fe", "Fer", "Fi", "Flam", "Fo", "Fo", "Fres", "Fri", "Ful", "Fur", "G", "G", "G", "Ga", "Gal", "G\u00e4rt", "Gas", "Gas", "Gau", "Gay", "Ge", "Gei", "Gllo", "Glo", "Gly", "Gnarr", "Gra", "Gran", "Grim", "Gru", "Guet", "H", "Ha", "Had", "Had", "Hae", "Han", "Har", "Har", "Has", "He", "He", "He", "Hi", "Hi", "Ho", "Hu", "Hum", "Hum", "Huy", "Hy", "Hy", "Hy", "Im", "In", "In", "In", "Ir", "Jan", "Jann", "Ju", "K", "K", "K", "Kel", "Kor", "Kr", "Krie", "Kur", "Ky", "Ky", "L", "La", "Len", "Leusch", "Lie", "Lis", "Lit", "Lu", "Lun", "Lux", "M", "Ma", "Ma", "Maest", "Mai", "Man", "Mar", "Mar", "Mar", "Mau", "Maw", "May", "Med", "Men", "Men", "Mer", "Mer", "Mes", "Mich", "Mil", "Mir", "Mo", "Mor", "Mos", "Nec", "Ni", "Niu", "Niv", "Nu", "Nyil", "O", "O", "O", "O", "O", "Oc", "Og", "Op", "Op", "Pal", "Pal", "Par", "Pe", "Per", "Pet", "Ph", "Pher", "Pi", "Pi", "Pi", "Pit", "Pla", "Plei", "Plin", "Pnid", "Po", "Po", "Pro", "Pru", "Pu", "Py", "Rams", "R\u00e9", "Rec", "Rec", "Rei", "Rein", "Rep", "Rhei", "Rhyl", "Ri", "Ri", "Ri", "Rit", "Ro", "R\u00f6", "Ru", "Ru", "R\u00fcm", "San", "Sar", "Schr\u00f6", "Schr\u00f6", "Schr\u00f6", "Scil", "Se", "Sec", "Shag", "Sheep", "Shon", "Shu", "Sic", "Sieg", "Sir", "Smir", "Smy", "Snel", "So", "Sol", "Som", "Som", "Sor", "Spitz", "Spu", "Ster", "Stil", "Suc", "Sul", "Sum", "Sung", "Syl", "Syr", "Tae", "Tar", "Tau", "Te", "Tem", "Ter", "Tet", "The", "The", "Thu", "Thug", "Thy", "Ti", "Ti", "Tin", "Tos", "Tran", "Tri", "U", "U", "Un", "V", "Va", "Vas", "Ve", "Ven", "Ver", "Vi", "Vi", "Vig", "Vit", "Vix", "Vlad", "Von", "Wan", "We", "Whis", "Wu", "Xan", "Xe", "Xen", "Xi", "Xi", "Y", "Ya", "Ya", "Yad", "Yan", "Yar", "Yek", "Yg", "Yif", "Yil", "Yl", "Yli", "Yu", "Yug", "Z", "Za", "Za", "Zir", "Zly", "Zup"};
	
	// Syllable 2 terminal
	public static String[] alienVillage_syl2Term_default = new String[]{"-Kthun", "-Mei", "-Yu", "'giath", "'in", "'yoth", "'zath", "\u00e6", "ban", "bau", "bey", "big", "bith", "blo", "boldt", "by", "cas", "chab", "cher", "chi", "chy", "clear", "co", "co", "col", "dath", "de", "de", "den", "dha", "dith", "dolf", "doth", "dra", "dr\u00e9", "er", "er", "erre", "fid", "fried", "ga", "gai", "gand", "gas", "gel", "gel", "gens", "ger", "gitte", "gol", "gon", "goth", "goth", "hi", "iell", "ip", "is", "itzsch", "iu", "kard", "kel", "ker", "ker", "kie", "ko", "kos", "la", "la", "land", "las", "le", "ler", "ley", "ley", "ley", "li", "lin", "lisle", "low", "ly", "man", "mans", "mar", "men", "mer", "mus", "na", "na", "nak", "narth", "nau", "ne", "nel", "ner", "ner", "ner", "nes", "ni", "nov", "on", "on", "oph", "oth", "pel", "pelt", "per", "p\u00e8re", "pes", "phes", "phun", "place", "ra", "ra", "ran", "rard", "ri", "ris", "ris", "ro", "rus", "ry", "sam", "sen", "sen", "shanks", "sold", "son", "sov", "steen", "ta", "ta", "tai", "tard", "ter", "ter", "ter", "thor", "ti", "tis", "tit", "to", "to", "ton", "trow", "tur", "ub", "us", "vard", "vaz", "ville", "vin", "vy", "wen", "win", "wing", "yeux", "zen"};
	// Syllable 2 transitional
	public static String[] alienVillage_syl2Trans_default = new String[]{"-Lus", "-Yar", "-yath", "'gil", "'gl", "'gy", "'ll", "'ni", "'nug", "'u", "'yi", "'yl", "^Cot", "a", "a", "ae", "ae", "al", "au", "ber", "bi", "bin", "bliv", "bold", "bri", "bul", "ca", "ca", "ca", "cci", "cel", "cel", "cel", "ces", "cha", "che", "chi", "chy", "ci", "cle", "clot", "co", "co", "co", "cor", "cor", "cor", "cre", "cund", "da", "dai", "dan", "de", "de", "de", "de", "di", "di", "di", "dil", "din", "din", "dro", "drus", "du", "e", "e", "e", "el", "el", "esh", "ga", "ga", "ga", "gae", "gas", "gen", "ghir", "gin", "gin", "go", "gor", "gric", "gu", "hi", "i", "i", "i", "i", "i", "i", "i", "i", "i", "i", "i", "i", "i", "i", "i", "i", "i", "i", "is", "ka", "kar", "kra", "lah", "lar", "lee", "li", "lic", "lip", "lor", "lor", "mal", "mar", "math", "mi", "mi", "mi", "mil", "mo", "mor", "mor", "mor", "n", "nar", "ne", "ner", "ni", "ni", "no", "no", "o", "o", "o", "or", "pa", "par", "pat", "pel", "pel", "pen", "per", "per", "phae", "phon", "pi", "pol", "pol", "por", "por", "quil", "ra", "rak", "ral", "ren", "ren", "ri", "ri", "ro", "sal", "se", "sen", "sen", "si", "si", "si", "si", "stat", "su", "ta", "ta", "tan", "tar", "tat", "ter", "tha", "thi", "thur", "til", "til", "tor", "tra", "tred", "tru", "tu", "tu", "tum", "tyn", "u", "u", "un", "ves", "wul", "x", "ya", "za", "zar"};
	
	// Syllable 3 terminal
	public static String[] alienVillage_syl3Term_default = new String[]{"-Ghun", "-Lih", "-Tha", "-Vho", "'ag", "'hx", "'mnon", "^Rho", "a", "a", "a", "chel", "cus", "des", "des", "di", "di", "di", "dum", "er", "er", "ev", "ga", "gen", "ger", "ger", "geuse", "haut", "hu", "huh", "i", "i", "i", "i", "i", "i", "is", "is", "is", "is", "is", "is", "is", "is", "is", "is", "is", "is", "is", "is", "is", "l", "la", "le", "leev", "lo", "lor", "los", "lu", "mir", "mur", "nac", "n\u00e6", "nak", "ni", "nil", "no", "nosh", "nu", "omph", "ov", "pus", "ra", "riffe", "ris", "rum", "rus", "sa", "sac", "shir", "siz", "ski", "sm", "son", "sus", "sus", "sus", "ta", "tas", "ter", "ter", "thon", "tia", "tis", "tius", "tius", "tlach", "tor", "tov", "tum", "um", "um", "um", "um", "um", "um", "um", "um", "um", "us", "us", "us", "us", "us", "us", "us", "us", "us", "vich", "x", "yand", "zer", "zy"};
	// Syllable 3 transitional
	public static String[] alienVillage_syl3Trans_default = new String[]{"-Al", "-ech", "-U", "^Na", "^Zac", "a", "a", "ae", "ba", "char", "chi", "ci", "cli", "de", "di", "do", "don", "e", "en", "fe", "ge", "gra", "i", "i", "i", "i", "i", "i", "i", "i", "i", "i", "i", "i", "i", "la", "lae", "lar", "lar", "len", "ler", "li", "li", "lon", "ma", "may", "me", "me", "mon", "na", "nel", "ni", "ni", "o", "o", "o", "o", "pat", "phan", "ri", "rus", "tar", "tat", "tat", "tat", "tat", "te", "thar", "thu", "ti", "ti", "tud", "van", "ver", "vi", "vi", "vi"};
	
	// Syllable 4 terminal
	public static String[] alienVillage_syl4Term_default = new String[]{"'ya", "a", "ae", "ae", "chus", "da", "des", "des", "des", "di", "dov", "dru", "dus", "er", "ho", "i", "i", "is", "is", "is", "is", "is", "la", "li", "li", "lis", "mi", "nes", "nis", "no", "on", "ov", "ra", "ra", "ran", "ri", "rum", "tus", "tus", "tus", "um", "um", "us", "us", "us", "us", "us", "us", "us", "us", "us", "us", "us", "vis", "ya"};
	// Syllable 4 transitional
	public static String[] alienVillage_syl4Trans_default = new String[]{"an", "an", "chi", "dae", "en", "i", "i", "i", "i", "i", "i", "mi", "o", "ta", "tat", "tat", "tat", "tat", "tat", "tat", "tat", "ti"};
	
	// Syllable 5 terminal
	public static String[] alienVillage_syl5Term_default = new String[]{"a", "ae", "des", "is", "is", "is", "is", "is", "is", "is", "le", "nis", "nis", "se", "sen", "um", "um", "us", "us", "us"};
	// Syllable 5 transitional
	public static String[] alienVillage_syl5Trans_default = new String[]{"ar", "ti"};
	
	// Syllable 6 terminal
	public static String[] alienVillage_syl6Term_default = new String[]{"ae", "um"};
	
	
	
	/*
	 * Hobgoblin name pieces
	 */

	// Single-syllable pieces
	public static String[] hobgoblin_oneSylBegin_default = new String[]{"Do", "Dwa", "E", "E", "Fae", "Fau", "Ghou", "Gno", "Gwy", "Ha", "I", "Ji", "Li", "Ma", "Ma", "Mo", "Ni", "Ny", "O", "Pa", "Pu", "Smy", "Sphi", "Spri", "Sy", "Tro", "Tro", "Wa", "Wa"};
	public static String[] hobgoblin_oneSylEnd_default = new String[]{"b", "ch", "ck", "g", "g", "g", "l", "lf", "ll", "lph", "me", "mp", "mph", "n", "n", "n", "nn", "nn", "nt", "nx", "rc", "rf", "te", "th", "tts", "tz", "w", "x"};

	// Syllable 1 transitional
	public static String[] hobgoblin_syl1Trans_default = new String[]{"A", "A", "Ad", "\u00c1i", "A", "Al", "An", "A", "Ar", "As", "As", "As", "Ba", "Ban", "Bau", "Bei", "Bel", "Bla", "Blem", "Bo", "Bog", "Bri", "Bri", "Brow", "Buc", "Bug", "Bun", "Bw", "Cae", "Cal", "Cal", "Cen", "Ci", "Cl\u00edodh", "Clur", "Cy", "Cy", "Dag", "Dar", "Di", "Dok", "D\u00f6k", "Do", "Drau", "Dry", "Dul", "E", "E", "Em", "En", "E", "Fai", "Fin", "Fi", "Frey", "Frey", "Gar", "Ga", "Gi", "Glor", "Gob", "Go", "Gor", "Grem", "Gren", "Gwi", "Gwy", "Hal", "Har", "He", "Hi", "Hi", "Hob", "Hob", "Hul", "Hum", "In", "Ja", "Jen", "Jo", "Jo", "Ka", "Kal", "Kap", "Kel", "Ki", "Ki", "Kla", "Knock", "Ko", "Kor", "La", "La", "Le", "Lil", "Lj\u00f3", "Lu", "Lur", "Ma", "Ma", "Mang", "Man", "Me", "Mi", "Mo", "Mo", "Moo", "Moom", "Na", "Nai", "Ne", "Ner", "Nic", "Nu", "Nu", "O", "O", "O", "Pix", "Pol", "Pom", "Poo", "Pu", "Ral", "Red", "Ro", "Ru", "Sa", "Sa", "Sa", "Sa", "Seel", "Sel", "Shel", "Si", "Si", "Son", "Sprig", "Suc", "Sval", "Ten", "Ten", "Tik", "Ti", "Ti", "Tom", "Tra", "Tri", "Tsu", "U", "Un", "V\u00e6t", "Val", "Vi", "Wen", "Xa", "Ya", "Ya", "Ya", "Ye", "Yng", "Yu", "Zom"};

	// Syllable 2 terminal
	public static String[] hobgoblin_syl2Term_default = new String[]{"ad", "ad", "ba", "ba", "bear", "bie", "bit", "bold", "bu", "ca", "ca", "ca", "ca", "cap", "chan", "ci", "co", "da", "db", "del", "dhe", "dine", "dra", "er", "fard", "ga", "ga", "gan", "gar", "gart", "ger", "gid", "gle", "go", "gon", "got", "goyle", "gre", "gu", "gu", "ie", "ie", "ja", "kan", "kha", "kie", "la", "la", "let", "lin", "lin", "na", "na", "na", "ne", "ney", "ni", "nie", "nik", "nin", "nis", "no", "ott", "pa", "py", "r", "ra", "rawn", "ren", "ren", "res", "reth", "rick", "ry","ryl", "sin", "taur", "te", "thyr", "tir", "ton", "tos", "tuns", "tyr", "vi", "wang", "yip", "ze"};
	// Syllable 2 transitional
	public static String[] hobgoblin_syl2Trans_default = new String[]{"ba", "ba", "bar", "bat", "bau", "be", "ber", "ber", "bou", "ca", "can", "clo", "cu", "cu", "cu", "di", "gob", "gua", "i", "i", "i", "kae", "k\u00e1l", "ke", "ki", "ko", "ku", "ku", "kyr", "la", "la", "le", "li", "li", "li", "li", "ly", "ma", "ma", "me", "mi", "ming", "mo", "my", "na", "nan", "nan", "ne", "ne", "no", "no", "no", "phoe", "pre", "pu", "re", "ri", "rin", "ro", "ru", "sal", "s\u00e1l", "ta", "tal", "ter", "ter", "ti", "ti", "to", "tsu", "u", "u", "var", "wa", "ya"};

	// Syllable 3 terminal
	public static String[] hobgoblin_syl3Term_default = new String[]{"a", "a", "ban", "be", "bi", "bo", "bus", "bus", "chaun", "chaun", "co", "core", "da", "es", "far", "far", "far", "gan", "gast", "geist", "go", "gon", "han", "ich", "id", "ie", "ja", "ka", "lang", "lin", "nak", "n\u00e1n", "ne", "ne", "on", "pa", "pes", "pod", "ra", "ra", "ro", "ro", "sa", "ta", "taur", "ter", "vin", "voi", "wa", "way", "yes"};
	// Syllable 3 transitional
	public static String[] hobgoblin_syl3Trans_default = new String[]{"-on", "-u", "a", "ang", "bi", "ceph", "ciel", "gu", "hu", "i", "i", "kant", "ku", "ku", "man", "mo", "ni", "pu", "ro", "ru", "ta", "ter", "ton", "tsu"};

	// Syllable 4 terminal
	public static String[] hobgoblin_syl4Term_default = new String[]{"a", "ba", "bi", "der", "do", "gal", "lam", "lo", "mann", "me", "mo", "mon", "na", "na", "na", "ne", "ra", "tian", "to", "us"};
	// Syllable 4 transitional
	public static String[] hobgoblin_syl4Trans_default = new String[]{"a", "chei", "ku", "za"};

	// Syllable 5 terminal
	public static String[] hobgoblin_syl5Term_default = new String[]{"bi", "ly", "res", "ros"};
	
}
