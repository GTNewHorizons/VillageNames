package astrotibs.villagenames.name;

public class NamePiecesEntities {

	/**
	 * I made this file because putting all the name pools together caused:
	 * "The code for the static initializer is exceeding the 65535 bytes limit"
	 * Beth says there's a better way to load in strings, but IDK man I'm not a
	 * Java wizard get off my back OK?
	 */
	
	/*
	 * Villager name pieces
	 */
	
	// Prefix-Suffix pieces
	public static String[] villager_prefix_default = new String[]{};
	public static String[] villager_suffix_default = new String[]{};
	
	// Single-syllable pieces
	public static String[] villager_oneSylBegin_default = new String[]{"A", "A", "A", "Ai", "Ba", "Be", "Be", "Bi", "Bi", "Bi", "Bla", "Bo", "Bo", "Bra", "Bra", "Bru", "Bu", "Cai", "Cha", "Che", "Chri", "Chri", "Chri", "Ci", "Ci", "Ci", "Ci", "Ci", "Ci", "Ci", "Ci", "Clou", "Da", "Da", "Dao", "De", "De", "Dji", "Do", "Dua", "E", "Ea", "Ea", "Fa", "Fe", "Fi", "Flo", "Ga", "Gau", "Geo", "Geo", "Geo", "Geo", "Geo", "Geo", "Go", "Guo", "Guy", "Ha", "Hei", "Ho", "Hui", "Hui", "Ja", "Ja", "Ja", "Ja", "Ja", "Ja", "Ja", "Ja", "Ja", "Ja", "Jay", "Ji", "Ji", "Jia", "Jie", "Jo", "Jo", "Joe", "Joe", "Jua", "Ka", "Ka", "Ka", "Kai", "Ki", "Ko", "Kri", "La", "Li", "Li", "Lia", "Lia", "Lo", "Lu", "Lu", "Ma", "Ma", "Ma", "Ma", "Ma", "Mi", "Mi", "Mi", "Na", "Ne", "Ni", "Ni", "No", "Pau", "Pau", "Pe", "Pe", "Pu", "Qia", "Qio", "Rai", "Ray", "Re", "Re", "Ree", "Ree", "Ri", "Ro", "Ru", "Sco", "Sco", "Sco", "Sha", "Sha", "Shu", "Si", "Sji", "Smi", "Smi", "Smy", "Squa", "Tao", "Tha", "Tho", "Ti", "Ti", "To", "To", "To", "Tri", "We", "We", "Wei", "Wei", "Xia", "Xia", "Xu", "Ya", "Ya", "Yo", "Yu", "Ze", "Zhe", "Zo", "Zo", "Zo"};
	public static String[] villager_oneSylEnd_default = new String[]{"b", "bs", "ce", "ck", "ck", "ck", "ck", "cke", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "de", "dge", "dge", "dge", "ff", "ff", "ff", "ggs", "hd", "hn", "hn", "k", "ke", "ke", "l", "l", "le", "les", "ll", "ll", "ll", "ll", "lm", "m", "m", "m", "m", "m", "m", "mes", "mes", "mes", "mes", "mie", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "ne", "ne", "ne", "ne", "ne", "ne", "ng", "ng", "ng", "ng", "ng", "ng", "ng", "ng", "ng", "ng", "ng", "ng", "ng", "ng", "ng", "nh", "nk", "nk", "nn", "nn", "nn", "ps", "r", "rc", "rc", "rc", "rge", "rge", "rge", "rk", "rk", "rl", "rl", "rles", "rn", "rn", "rn", "rth", "rtz", "s", "s", "s", "sch", "sh", "ss", "tch", "tch", "tch", "te", "te", "th", "th", "th", "th", "tt", "tt", "tt", "ve", "x"};
	
	// Syllable 1 transitional					
	public static String[] villager_syl1Trans_default = new String[]{"A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "Aa", "Aad", "Ab", "Ab", "Aer", "Ag", "Ah", "Ai", "Ai", "Al", "Al", "Al", "Al", "Al", "Al", "Al", "Am", "An", "An", "An", "An", "An", "An", "An", "An", "An", "An", "An", "Ar", "Ar", "Ar", "Ar", "As", "Ash", "Ash", "Ay", "Ba", "Ba", "Ba", "Ba", "Ba", "Bai", "Bar", "Bar", "Bar", "Be", "Be", "Ben", "Ben", "Ben", "Beth", "Bez", "Bi", "Bik", "Bil", "Bing", "Bis", "Bo", "Bor", "Bra", "Brad", "Bug", "Byb", "Ca", "Ca", "Ca", "Ca", "Cag", "Cal", "Cal", "Ce", "Ce", "Ce", "Char", "Char", "Chi", "Chia", "Chlo", "Chlo", "Chris", "Chris", "Chry", "Cin", "Cin", "Cin", "Co", "Co", "Con", "Cry", "Cy", "Da", "Da", "Da", "Da", "Da", "Dai", "Dan", "Dar", "Dar", "De", "De", "De", "De", "Dex", "Di", "Di", "Di", "Do", "Do", "Do", "Do", "Do", "Dor", "Dra", "Dun", "Dun", "Dy", "Dy", "Dzog", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "Ed", "Ed", "Ei", "El", "El", "El", "El", "El", "El", "Em", "Em", "Em", "Em", "Em", "Em", "Em", "Em", "En", "Ex", "Fa", "Fa", "Fa", "Fa", "Fa", "Fa", "Fa", "Fa", "Fa", "Fa", "Fa", "Fa", "Fa", "Fat", "Fat", "Fen", "Fen", "Fi", "Flo", "Fong", "Fran", "Fran", "Fre", "Fri", "Fu", "Fu", "Fu", "Ga", "Ga", "Ga", "Ga", "Ga", "Ga", "Gab", "Gae", "Gar", "Gar", "Gar", "Geor", "Geor", "Gi", "Gi", "Gil", "Gil", "Gil", "Gior", "Glo", "Go", "Gol", "Gol", "Gor", "Gret", "G\u00fcn", "Gung", "Guts", "Ha", "Ha", "Ha", "Ha", "Ha", "Ha", "Ha", "Ha", "Ha", "Ham", "Han", "Han", "Har", "Har", "Has", "Has", "Has", "He", "Hei", "Hei", "Hel", "Hen", "Hen", "Her", "Hi", "Hi", "Hi", "Hil", "Hil", "Ho", "Ho", "Hos", "Hos", "Hu", "Hus", "I", "I", "I", "I", "I", "I", "I", "I", "If", "If", "In", "In", "Io", "Ir", "Ja", "Ja", "Ja", "Jac", "Jac", "Jack", "Jay", "Jay", "Je", "Je", "Je", "Jen", "Jes", "Jo", "Jo", "Jo", "Jo", "Jo", "John", "John", "Ju", "Ju", "Ju", "Jun", "Ka", "Ka", "Ka", "Ka", "Kef", "Kel", "Ken", "Kha", "Ki", "Ki", "Ki", "Ki", "Klu", "Kok", "Kop", "Krish", "Ksen", "Ku", "La", "La", "La", "La", "Lak", "Lam", "Lau", "Lau", "Lau", "Le", "Le", "Le", "Le", "Le", "L\u00e9", "Lei", "Len", "Lew", "Li", "Li", "Li", "Li", "Lind", "Lo", "Lo", "Lo", "Lou", "Low", "Lu", "Lu", "Lu", "Lu", "Lu", "Lu", "Lu", "Lu", "Lu", "Lu", "Lu", "Lu", "Lu", "Lu", "Lu", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Maar", "Mah", "Mak", "Man", "Man", "Mar", "Mar", "Mar", "Mar", "Mar", "Mar", "Mar", "Mar", "Mar", "Mar", "Mar", "Mar", "Mar", "Mar", "Mau", "Max", "Me", "Me", "Me", "Meh", "Meh", "Mi", "Mi", "Mi", "Mi", "Mi", "Mi", "Mi", "Mi", "Mi", "Min", "Min", "Min", "Mo", "Mo", "Mo", "Mo", "Mo", "Mo", "Mo", "Mo", "Mo", "Mo", "Mo", "Mo", "Mo", "Mo", "Mon", "Mor", "Mor", "Mu", "Mu", "Mu", "Mu", "Muk", "Myr", "Na", "Na", "Na", "Na", "Na", "Na", "Na", "Ne", "Ne", "Ne", "Nea", "Nel", "Ner", "Ni", "Ni", "Ni", "Ni", "Ni", "No", "No", "No", "No", "No", "No", "No", "No", "No", "Nor", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "Od", "Pa", "Pa", "Pa", "Pal", "Pan", "Pau", "Pe", "Per", "Phae", "Pho", "Pi", "Pi", "Po", "Po", "Pre", "Pri", "Quet", "Quis", "Ra", "Ra", "Ra", "Ra", "Rai", "Rai", "Ras", "Re", "Re", "Re", "Rhi", "Ri", "Ri", "Ri", "Ri", "Ri", "Ro", "Ro", "Ro", "Rob", "Ron", "Row", "Ru", "Ru", "Ru", "Ru", "Ry", "Ry", "Ry", "Sa", "Sa", "Sa", "Sa", "Sa", "Sa", "Sa", "Sad", "Sal", "San", "San", "San", "San", "San", "Sar", "Sar", "Sar", "Scar", "Scar", "Se", "Se", "Se", "Se", "Se", "Sei", "Sel", "Seo", "Sep", "Set", "Sev", "Sha", "She", "Shi", "Shri", "Shu", "Si", "Si", "So", "So", "So", "So", "So", "So", "So", "So", "So", "So", "So", "So", "So", "So", "Sof", "Som", "Sta", "Ste", "Ste", "Stel", "Stra", "Su", "Su", "Syl", "Ta", "Ta", "Ta", "Ta", "Ta", "Tak", "Tal", "Te", "Te", "Te", "Te", "Te", "Tel", "The", "Tho", "Tho", "Tho", "Ti", "Ti", "Ti", "Ti", "Ti", "Ti", "Ti", "Ti", "Tif", "Tif", "Tim", "To", "To", "To", "Tom", "Ton", "Tre", "Tri", "Tsu", "Tsu", "Twin", "U", "Ul", "Un", "Ur", "Va", "Ven", "Vi", "Vi", "Vic", "Vic", "Vik", "Vik", "Vin", "Vin", "Vin", "Wal", "War", "Wen", "Wen", "Wi", "Wil", "Wil", "Wil", "Wil", "Wil", "Wil", "Wil", "Wil", "Wil", "Xan", "Xe", "Xi", "Yas", "Ye", "Yous", "Yu", "Yu", "Yuf", "Zah", "Zan", "Zar", "Ze", "Ze", "Zeb", "Zen", "Zi", "Zig", "Zo", "Zo", "Zur"};	
	
	// Syllable 2 terminal
	public static String[] villager_syl2Term_default = new String[]{"^Paul", "^Wei", "-fen", "-hao", "-jun", "-yeon", "a", "a", "a", "a", "a", "ah", "ah", "ah", "ah", "ah", "ah", "am", "am", "am", "am", "am", "am", "an", "an", "an", "an", "an", "\u00e3o", "ba", "be", "bert", "bez", "bib", "bolt", "bos", "bul", "by", "by", "ca", "ca", "cal", "can", "can", "card", "cas", "cas", "cas", "ce", "cent", "cent", "chael", "chael", "chai", "chai", "chard", "chel", "chen", "cil", "cious", "cis", "co", "co", "cus", "cus", "cus", "cy", "da", "da", "da", "da", "dam", "dam", "dam", "dane", "de", "death", "deen", "del", "den", "den", "den", "den", "den", "des", "di", "di", "din", "do", "don", "dor", "dra", "dra", "dra", "drei", "drew", "drew", "dy", "dy", "dy", "dy", "dy", "dy", "e", "e", "el", "ell", "erre", "erts", "et", "ex", "ex", "ey", "fa", "fer", "fie", "fus", "ga", "ga", "ga", "gan", "gan", "gann", "gar", "ger", "ghen", "gi", "gi", "go", "go", "go", "gon", "gon", "gret", "gr\u00e9t", "grjet", "guel", "gus", "gy", "ha", "ha", "ha", "ha", "har", "he", "he", "hei", "helm", "ho", "i", "ia", "ia", "id", "iel", "im", "inj", "ior", "is", "is", "is", "ise", "ith", "ja", "jin", "jin", "jo", "jr", "ka", "ka", "ka", "ka", "ka", "kahn", "kain", "kas", "kas", "kau", "ke", "kel", "ki", "ko", "kol", "ku", "kub", "kub", "la", "la", "la", "la", "la", "la", "la", "lah", "lak", "lal", "lal", "lan", "lan", "land", "land", "land", "las", "layne", "led", "len", "lene", "les", "let", "ley", "ley", "li", "li", "li", "li", "liam", "liam", "liam", "liam", "liam", "liam", "lice", "lie", "lie", "lie", "lik", "lim", "lim", "lom", "los", "luf", "ly", "ly", "ly", "lyn", "ma", "ma", "ma", "ma", "ma", "ma", "ma", "ma", "ma", "ma", "ma", "ma", "ma", "mak", "man", "mar", "mar", "marck", "mas", "mas", "mas", "mas", "med", "mer", "met", "mi", "mil", "mon", "m\u00f3n", "moud", "muh", "muh", "mus", "mus", "my", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "naa", "nan", "ne", "nei", "nes", "net", "neth", "ni", "nie", "no", "no", "non", "ny", "ny", "ny", "ny", "o", "o", "o", "o", "on", "on", "ott", "pe", "phie", "qing", "ra", "ra", "ra", "ra", "ra", "rad", "rah", "ram", "rav", "reet", "reh", "rell", "ren", "ren", "rene", "req", "ret", "ric", "rich", "rick", "rid", "rie", "rie", "rik", "rill", "rim", "rin", "rir", "rir", "ris", "ris", "rit", "ro", "ro", "rom", "ron", "ros", "ru", "rus", "ry", "ry", "ry", "ry", "ry", "ry", "ryl", "sa", "sa", "say", "sef", "sef", "sein", "seph", "sey", "sha", "shal", "shmi", "shoi", "sie", "sif", "sil", "sim", "sin", "son", "son", "son", "stal", "sti", "suf", "ta", "tan", "tan", "tar", "tem", "ten", "ter", "ter", "ter", "than", "than", "than", "ther", "tin", "tine", "tis", "to", "ton", "tor", "tos", "trice", "tur", "u", "uel", "va", "va", "va", "val", "van", "ven", "ves", "vey", "vi", "vid", "vin", "vin", "vin", "vine", "vis", "vit", "wa", "ward", "wu", "ya", "ya", "ya", "ya", "ya", "ya", "ya", "ya", "yep", "za", "zat", "ze", "zel", "zer", "zer"};
	// Syllable 2 transitional
	public static String[] villager_syl2Trans_default = new String[]{"a", "a", "a", "a", "a", "a", "a", "a", "a", "an", "an", "bar", "bas", "bas", "bi", "bi", "bi", "bra", "bri", "bri", "bri", "ce", "ces", "chi", "cho", "c\u00ed", "c\u00ed", "cil", "cro", "da", "da", "dal", "de", "de", "de", "deg", "del", "del", "der", "di", "di", "di", "dre", "du", "du", "e", "e", "e", "e", "e", "e", "ex", "ex", "ex", "ex", "fa", "fa", "fal", "fi", "fi", "fi", "fi", "fi", "fi", "fi", "fi", "fi", "fi", "f\u00ed", "f\u00ed", "ga", "ga", "ga", "ga", "ge", "gi", "gi", "gi", "gis", "gu", "gus", "gus", "ha", "ha", "ha", "ha", "ha", "ha", "ham", "ham", "ham", "ham", "ham", "ham", "ham", "ham", "hi", "hi", "i", "i", "i", "i", "i", "i", "i", "i", "ja", "ja", "ka", "ka", "ki", "ko", "ko", "ko", "ku", "la", "le", "le", "le", "le", "lea", "lek", "li", "li", "li", "li", "li", "li", "li", "li", "li", "li", "li", "li", "li", "li", "li", "li", "li", "li", "li", "li", "li", "li", "lis", "lo", "lo", "lu", "ly", "ma", "ma", "ma", "ma", "ma", "ma", "man", "me", "me", "me", "me", "me", "me", "mi", "mi", "mi", "mi", "mi", "mi", "mi", "mig", "ming", "mir", "my", "na", "na", "na", "na", "naz", "ni", "ni", "no", "no", "nu", "o", "o", "o", "o", "pa", "pan", "pha", "phi", "phi", "phi", "phi", "pu", "qa", "que", "ra", "ra", "ran", "re", "re", "re", "ren", "ri", "ri", "ri", "ri", "ri", "ri", "ri", "ri", "ri", "ri", "ri", "ri", "ri", "ri", "ri", "ri", "ri", "ri", "ri", "r\u00ed", "r\u00ed", "r\u00ed", "rin", "ro", "ro", "rol", "ru", "ry", "ry", "ry", "sa", "sa", "scil", "se", "se", "so", "so", "sta", "su", "su", "su", "ta", "ta", "ta", "ta", "tas", "te", "te", "te", "te", "the", "tho", "ti", "ti", "ti", "ti", "ti", "ti", "ti", "ti", "ti", "ti", "to", "to", "to", "to", "to", "to", "to", "tou", "tsu", "tsu", "u", "u", "u", "um", "vaa", "ven", "vi", "ya", "zal", "ze", "zul"};	
	
	// Syllable 3 terminal
	public static String[] villager_syl3Term_default = new String[]{"a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "am", "am", "am", "am", "am", "an", "as", "as", "ba", "ba", "bar", "bert", "ca", "ca", "ca", "chi", "co", "da", "da", "da", "den", "dor", "dou", "el", "el", "elle", "fa", "garde", "ger", "go", "hill", "him", "id", "in", "ja", "ja", "ja", "ka", "ka", "ki", "ki", "ko", "la", "la", "la", "la", "la", "la", "la", "lah", "lah", "lat", "lene", "lin", "line", "lite", "lon", "lon", "los", "ly", "ly", "lyn", "lyn", "ma", "ma", "ma", "ma", "ma", "ma", "ma", "maa", "mad", "mad", "mad", "mad", "mad", "mad", "mad", "mar", "mat", "med", "med", "med", "med", "med", "med", "meh", "meh", "mesh", "mesh", "mesh", "meth", "mi", "min", "min", "mon", "mos", "mut", "my", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "nath", "ne", "ne", "nel", "nic", "nie", "no", "no", "non", "nor", "noush", "ny", "ny", "ny", "o", "o", "o", "o", "on", "os", "pa", "pher", "pher", "pu", "ra", "ra", "ra", "ra", "ra", "rant", "rat", "re", "re", "rey", "rin", "roth", "roth", "sa", "sa", "sha", "sine", "son", "son", "syl", "ta", "thy", "t\u00edn", "t\u00edn", "to", "to", "trix", "us", "va", "va", "va", "va", "ver", "ver", "ver", "ver", "ver", "way", "ya", "ya", "ya", "ya", "ya", "yah", "zo"};
	// Syllable 3 transitional
	public static String[] villager_syl3Trans_default = new String[]{"-A", "-Rah", "a", "a", "a", "a", "a", "a", "a", "a", "a", "an", "an", "an", "an", "baa", "bel", "bel", "can", "car", "ci", "c\u00f3", "do", "fis", "ga", "i", "i", "i", "ic", "li", "li", "li", "li", "li", "li", "lo", "lu", "ma", "me", "na", "ni", "ni", "no", "pho", "pho", "ri", "ri", "ri", "ri", "san", "sta", "ti", "ti", "to", "tro", "u", "vi", "vi", "vi", "vi", "vi", "vi", "vi", "za"};
	
	// Syllable 4 terminal
	public static String[] villager_syl4Term_default = new String[]{"a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "\u00e1n", "\u00e1n", "be", "beth", "cia", "cia", "dar", "der", "der", "der", "der", "go", "go", "go", "go", "ja", "la", "la", "la", "li", "ma", "man", "mi", "mu", "na", "na", "na", "ne", "nin", "o", "on", "phe", "ra", "rii", "sia", "ta", "tar", "te"};
	// Syllable 4 transitional
	public static String[] villager_syl4Trans_default = new String[]{"^Car", "a", "gi", "nas", "o", "te"};
	
	// Syllable 5 terminal
	public static String[] villager_syl5Term_default = new String[]{"a", "men", "ne", "ri", "sus", "tl"};
	// Syllable 5 transitional
	public static String[] villager_syl5Trans_default = new String[]{};
	
	// Syllable 6 terminal
	public static String[] villager_syl6Term_default = new String[]{};
	// Syllable 6 transitional
	public static String[] villager_syl6Trans_default = new String[]{};

	// Syllable 7 terminal
	public static String[] villager_syl7Term_default = new String[]{};
	
	
	
	/*
	 * Angel name pieces
	 * Village Names version 3.0 and on
	 */

	// Prefix-Suffix pieces
	public static String[] angel_prefix_default = new String[]{};
	public static String[] angel_suffix_default = new String[]{};
	
	// Single-syllable pieces
	public static String[] angel_oneSylBegin_default = new String[]{};
	public static String[] angel_oneSylEnd_default = new String[]{};

	// Syllable 1 transitional
	public static String[] angel_syl1Trans_default = new String[]{"A", "A", "A", "A", "An", "Ar", "Ar", "Ar", "Az", "Az", "Az", "Ba", "Ba", "Cam", "Cas", "Che", "Dad", "Dan", "Du", "El", "Er", "Gad", "Gar", "Ha", "Ha", "Ha", "Had", "Han", "Han", "Hash", "He", "He", "Hof", "I", "Is", "Is", "Je", "Je", "Je", "Je", "Ji", "Jo", "Kal", "Kam", "Ke", "Kem", "Keph", "Kham", "Ki", "Ku", "Le", "Lu", "Ma", "Maa", "Me", "Me", "Mich", "Mik", "Mo", "Mu", "Mu", "Mun", "Na", "Na", "Net", "Nith", "Nu", "O", "O", "Pa", "Pe", "Phan", "Po", "Pra", "Pur", "Qaph", "Ra", "Rad", "Rag", "Raz", "Rem", "Rik", "Sa", "Sa", "Sab", "Sach", "Sam", "San", "Sar", "Schem", "Se", "Se", "Sham", "Sham", "Sid", "Te", "Ten", "Tu", "Ur", "Uz", "Vas", "Ve", "Worm", "Za", "Zad", "Zad", "Zaph", "Ze", "Ze", "Zoph"};

	// Syllable 2 terminal
	public static String[] angel_syl2Term_default = new String[]{"kar", "kir", "lel", "lik", "mah", "mal", "nin", "phon", "qun", "rut", "rut", "wood", "yel", "zach"};
	// Syllable 2 transitional
	public static String[] angel_syl2Trans_default = new String[]{"'aq", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "ba", "ba", "bad", "bi", "bi", "bra", "char", "ci", "dal", "dri", "e", "e", "fan", "gu", "ha", "hal", "ham", "has", "ho", "hu", "i", "i", "i", "i", "i", "i", "i", "i", "i", "i", "i", "i", "i", "ka", "ki", "ki", "ki", "laph", "li", "ma", "ma", "mel", "na", "na", "nem", "ni", "pha", "phan", "phan", "phi", "pol", "r", "ra", "ra", "ra", "ra", "ra", "ra", "ra", "rach", "rah", "ran", "raph", "raq", "rath", "ri", "ri", "ri", "ri", "ro", "ru", "rub", "sed", "shi", "si", "si", "si", "ta", "ti", "u", "u", "u", "u", "vu"};

	// Syllable 3 terminal
	public static String[] angel_syl3Term_default = new String[]{"'il", "'il", "chus", "don", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "fel", "fer", "fil", "il", "il", "il", "il", "il", "lat", "leth", "man", "ni", "phon", "rel", "thar", "tron", "zel"};
	// Syllable 3 transitional
	public static String[] angel_syl3Trans_default = new String[]{"ar", "bi", "di", "e", "er", "hi", "i", "i", "i", "i", "i", "i", "i", "i", "i", "i", "i", "i", "i", "i", "ly", "me", "mi", "mi", "pha", "qi", "qui", "ri", "u", "ya"};

	// Syllable 4 terminal
	public static String[] angel_syl4Term_default = new String[]{"'il", "ah", "ah", "ah", "ah", "bat", "e", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "on", "rae"};
	// Syllable 4 transitional
	public static String[] angel_syl4Trans_default = new String[]{"i", "i"};

	// Syllable 5 terminal
	public static String[] angel_syl5Term_default = new String[]{"ah", "el"};
	// Syllable 5 transitional
	public static String[] angel_syl5Trans_default = new String[]{};

	// Syllable 6 terminal
	public static String[] angel_syl6Term_default = new String[]{};
	// Syllable 6 transitional
	public static String[] angel_syl6Trans_default = new String[]{};

	// Syllable 7 terminal
	public static String[] angel_syl7Term_default = new String[]{};
	
	
	
	/*
	 * Demon name pieces
	 * Village Names version 3.0 and on
	 */

	// Prefix-Suffix pieces
	public static String[] demon_prefix_default = new String[]{};
	public static String[] demon_suffix_default = new String[]{};
	
	// Single-syllable pieces
	public static String[] demon_oneSylBegin_default = new String[]{"Ai", "Baa", "Bae", "Bue", "Cai", "Cha", "Cho", "Gaa", "Ghou", "Hi", "Ji", "Rau", "Sei", "Sha", "Va", "Vi"};
	public static String[] demon_oneSylEnd_default = new String[]{"l", "l", "l", "m", "m", "m", "ne", "nn", "nn", "nth", "p", "r", "r", "rt", "x", "x"};

	// Syllable 1 transitional
	public static String[] demon_syl1Trans_default = new String[]{"\u00d6r", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "Aa", "Aesh", "Ag", "Ag", "Ah", "Ak", "Al", "Al", "Al", "Al", "Am", "An", "An", "An", "An", "An", "An", "An", "An", "Ar", "Ar", "As", "As", "As", "As", "Ba", "Ba", "Ba", "Ba", "Ba", "Ba", "Bal", "Ban", "Bar", "Bar", "Bath", "Bath", "Be", "Be", "Be", "Bel", "Bel", "Bel", "Ber", "Bhu", "Bif", "Bo", "Bo", "Bu", "Bu", "Bush", "Caa", "Caas", "Can", "Cer", "Cha", "Che", "Cho", "Ci", "Ci", "Class", "Cor", "Cro", "Cul", "D", "Da", "Da", "Da", "Daj", "Dan", "Dan", "De", "De", "De", "Dev", "Div", "Dre", "E", "Eb", "Ei", "Er", "Flau", "Flav", "Fo", "Fo", "For", "For", "For", "For", "Fur", "Fur", "Ga", "Ga", "Gam", "Gau", "Glas", "Glas", "Go", "Gor", "Gre", "Gri", "Gu", "Gu", "Gu", "Gua", "Gual", "Ha", "Haa", "Hal", "Hau", "Hav", "I", "I", "Ib", "If", "In", "Ji", "Ka", "Ka", "Ka", "Ka", "Ka", "Ki", "Ko", "Kram", "Kro", "Ku", "Kum", "Le", "Le", "Le", "Le", "Le", "Le", "Lem", "Leo", "Li", "Li", "Li", "Li", "Lju", "Lu", "Ma", "Ma", "Ma", "Ma", "Ma", "Mal", "Mal", "Mam", "Mar", "Mar", "Mar", "Mas", "Math", "Meph", "Mer", "Mo", "Mor", "Mur", "Na", "Na", "Naa", "Nam", "Ni", "O", "O", "O", "O", "Or", "Or", "Or", "Or", "Pa", "Pai", "Pe", "Pel", "Phe", "Pith", "Po", "Pon", "Pre", "Pro", "Pruf", "Pul", "Ra", "Rak", "Ran", "Ro", "Ru", "S", "Sa", "Sa", "Sab", "Sal", "Salp", "Sem", "Shai", "Shed", "Sil", "Sit", "So", "Sti", "Sto", "Suang", "Suc", "Sur", "Tan", "Ti", "To", "Tu", "U", "Va", "Va", "Va", "Vas", "Ve", "We", "Wen", "Ye", "Za", "Ze", "Zi"};

	// Syllable 2 terminal
	public static String[] demon_syl2Term_default = new String[]{"as", "ax", "bas", "bi", "cas", "cas", "cell", "cell", "chies", "chon", "cong", "cus", "d\u00f6g", "dras", "eth", "fur", "ga", "gan", "gat", "gi", "gion", "gon", "gon", "hab", "hak", "hi", "il", "im", "im", "in", "ith", "jal", "jal", "ki", "ku", "kudh", "la", "lac", "lal", "lam", "las", "las", "las", "li", "li", "lik", "lim", "lin", "lis", "lis", "lith", "loch", "lu", "ma", "mah", "mon", "mon", "mon", "mon", "mosh", "mur", "my", "nard", "ne", "nex", "ni", "ni", "nin", "nock", "par", "par", "pep", "pes", "phas", "phas", "po", "pos", "pus", "qon", "ra", "raii", "ras", "ras", "rat", "rax", "res", "res", "ri", "rit", "rong", "rons", "ros", "ros", "run", "sag", "san", "se", "shaa", "shee", "sheth", "sih", "son", "su", "ta", "ta", "tan", "tan", "tar", "thim", "thus", "ti", "tis", "van", "ver", "yak", "ym", "yol", "zu"};
	// Syllable 2 transitional
	public static String[] demon_syl2Trans_default = new String[]{"-e", "^To", "as", "b'", "ba", "bad", "ban", "be", "be", "be", "ber", "ber", "bha", "bhan", "bi", "bo", "by", "ca", "ca", "cho", "chu", "chul", "ci", "ci", "crin", "cu", "cu", "der", "dha", "di", "dram", "dre", "dro", "du", "e", "e", "e", "el", "ga", "gal", "gen", "go", "gra", "he", "he", "i", "i", "i", "i", "i", "i", "i", "i", "i", "i", "i", "i", "ka", "ka", "ka", "ka", "ka", "kem", "kin", "ko", "ko", "la", "le", "le", "li", "lo", "lo", "ma", "ma", "ma", "may", "mei", "mej", "mi", "min", "mo", "mo", "mo", "mo", "mo", "nam", "ne", "nem", "no", "nos", "nur", "o", "o", "phe", "pho", "pol", "pu", "ra", "ra", "ra", "ri", "ron", "ru", "run", "sa", "sad", "sak", "sal", "si", "si", "so", "so", "su", "ta", "tal", "te", "then", "ti", "ti", "vi", "ya", "ya", "ya", "yas", "yo", "zaz", "zaz", "zi", "zo", "zu"};

	// Syllable 3 terminal
	public static String[] demon_syl3Term_default = new String[]{"'el", "'el", "al", "as", "ax", "bach", "bas", "bus", "bus", "cer", "ces", "cha", "cha", "chu", "da", "dai", "dha", "don", "el", "el", "el", "el", "es", "es", "far", "fer", "ge", "gin", "go", "go", "gor", "gos", "hem", "ie", "in", "je", "ka", "ka", "ku", "la", "lor", "ma", "man", "man", "man", "met", "mon", "moth", "no", "o", "on", "os", "phar", "phar", "ra", "re", "res", "ri", "ris", "rit", "rith", "ros", "roth", "ry", "ry", "rym", "sit", "ta", "ta", "ta", "ta", "ti", "tif", "tor", "tos", "ue", "urge", "us", "us", "us", "va", "vac", "vac", "ve", "ya", "yn", "za", "zon", "zou", "zu"};
	// Syllable 3 transitional
	public static String[] demon_syl3Trans_default = new String[]{"-La", "^\u0130", "^Da", "^Ma", "^Ma", "^Main", "^N", "^Se", "a", "a", "a", "a", "a", "al", "bi", "de", "gor", "i", "i", "i", "i", "in", "ka", "kar", "ke", "la", "ly", "ma", "me", "me", "mo", "o", "ra", "si", "si", "sto", "su", "vil", "ze", "ze"};

	// Syllable 4 terminal
	public static String[] demon_syl4Term_default = new String[]{"ar", "as", "as", "bub", "el", "gon", "ki", "laas", "lar", "lech", "lech", "lis", "lus", "na", "nah", "nah", "nak", "on", "on", "phus", "pid", "ra", "ta", "than", "us", "us", "vits", "ya", "yu"};
	// Syllable 4 transitional
	public static String[] demon_syl4Trans_default = new String[]{"-La", "a", "bi", "bo", "bo", "ge", "ha", "li", "phe", "su", "thi"};

	// Syllable 5 terminal
	public static String[] demon_syl5Term_default = new String[]{"a", "bou", "ka", "las", "las", "les", "pet", "ra", "rept", "us"};
	// Syllable 5 transitional
	public static String[] demon_syl5Trans_default = new String[]{"bo"};

	// Syllable 6 terminal
	public static String[] demon_syl6Term_default = new String[]{"lis"};
	// Syllable 6 transitional
	public static String[] demon_syl6Trans_default = new String[]{};

	// Syllable 7 terminal
	public static String[] demon_syl7Term_default = new String[]{};

	
	
	
	/*
	 * Dragon name pieces
	 * Village Names version 3.0 and on
	 */

	// Prefix-Suffix pieces
	public static String[] dragon_prefix_default = new String[]{};
	public static String[] dragon_suffix_default = new String[]{};
	
	// Single-syllable pieces
	public static String[] dragon_oneSylBegin_default = new String[]{"Ddrai", "Dra", "Dre", "Gyo", "Ku", "L\u00f3", "Nea", "Smo", "Wy", "Ya", "Yo", "Zmei"};
	public static String[] dragon_oneSylEnd_default = new String[]{"c", "g", "k", "k", "m", "ng", "ng", "q", "r", "rm"};

	// Syllable 1 transitional
	public static String[] dragon_syl1Trans_default = new String[]{"A", "A", "A", "A", "A", "Ab", "Ai", "Aj", "Az", "Ba", "Ba", "Boi", "Bol", "Br", "Co", "Con", "Cu", "Dr\u00e1", "Dra", "E", "E", "Ej", "Ev", "Faf", "Gor", "Gui", "Huang", "Hy", "I", "I", "Il", "J\u00f6r", "Ku", "Kul", "La", "La", "Le", "Lind", "Lo", "Mu", "N\u00ed", "Na", "No", "O", "Ou", "Pak", "Py", "Q'", "Qing", "Quet", "Ry", "Ry", "S\u00e1r", "Sei", "Slib", "Te", "Te", "The", "Ti", "Ty", "Ve", "Vish", "Vri", "Wy", "Xiu", "Yil", "Zbu", "Zi", "Zi", "Zir", "Zo"};

	// Syllable 2 terminal
	public static String[] dragon_syl2Term_default = new String[]{"ap", "ca", "don", "dra", "ga", "go", "gon", "kon", "la", "lant", "lant", "laur", "long", "long", "mok", "nir", "pep", "phon", "rasque", "ren", "rit", "ryu", "tan", "thon", "tra", "u", "vern", "vre", "worm", "zu"};
	// Syllable 2 transitional
	public static String[] dragon_syl2Trans_default = new String[]{"\u00e9", "a", "a", "be", "co", "da", "der", "dh\u00f6g", "do", "gar", "hang", "i", "ju", "k\u00e1", "ku", "ku", "kul", "lu", "ma", "moo", "mun", "nen", "ni", "pa", "po", "ra", "re", "ren", "ren", "ro", "ro", "shed", "shus", "ta", "u", "u", "ve", "vi", "y", "zal", "zhi"};

	// Syllable 3 terminal
	public static String[] dragon_syl3Term_default = new String[]{"ba", "chi", "chi", "g\u00e4n", "gi", "gr", "ha", "ha", "jin", "kan", "kyl", "mat", "nas", "ny", "nych", "phis", "ra", "ru", "sky", "su", "suge", "t\u00e1", "tat", "tor", "tra", "zel"};
	// Syllable 3 transitional
	public static String[] dragon_syl3Trans_default = new String[]{"^Cel", "^Da", "^Ja", "^We", "a", "a", "bo", "co", "fl\u00f3t", "gan", "la", "le", "na", "q'", "yan"};

	// Syllable 4 terminal
	public static String[] dragon_syl4Term_default = new String[]{"bre", "do", "dr", "en", "kas", "la", "ros", "than", "tl", "wa"};
	// Syllable 4 transitional
	public static String[] dragon_syl4Trans_default = new String[]{"a", "gu", "ha", "sor", "u"};

	// Syllable 5 terminal
	public static String[] dragon_syl5Term_default = new String[]{"a", "ka", "matz", "mur", "tl"};
	// Syllable 5 transitional
	public static String[] dragon_syl5Trans_default = new String[]{};

	// Syllable 6 terminal
	public static String[] dragon_syl6Term_default = new String[]{};
	// Syllable 6 transitional
	public static String[] dragon_syl6Trans_default = new String[]{};

	// Syllable 7 terminal
	public static String[] dragon_syl7Term_default = new String[]{};
	
	
	
	
	/*
	 * Golem name pieces
	 * Village Names version 3.0 and on
	 */
	
	// Prefix-Suffix pieces
	public static String[] golem_prefix_default = new String[]{};
	public static String[] golem_suffix_default = new String[]{};
	
	// Single-syllable pieces
	public static String[] golem_oneSylBegin_default = new String[]{"Bu", "Cha", "E", "Geo", "Hua", "Ja", "Jea", "Jo", "Joe", "Jua", "Ka", "Ma", "Sa", "Wa", "Wi", "Ya", "Zeu"};
	public static String[] golem_oneSylEnd_default = new String[]{"hn", "lls", "lt", "m", "mes", "n", "n", "ng", "nne", "p", "rge", "rke", "rl", "rles", "rnst", "s"};

	// Syllable 1 transitional
	public static String[] golem_syl1Trans_default = new String[]{"A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "Ae", "Al", "Al", "Al", "Al", "Am", "An", "Aph", "Ar", "Ar", "As", "At", "Az", "Ba", "Bac", "Bald", "Bhak", "Bi", "Brig", "Bur", "Cam", "Che", "Chris", "Co", "Con", "D", "Da", "Dan", "Dob", "Du", "Du", "E", "E", "E", "\u00C9", "Ed", "Ei", "El", "Er", "Eu", "Ferd", "Fev", "Fran", "Fred", "Fred", "Fried", "Gau", "Ge", "Gen", "Ger", "God", "Ha", "Hash", "He", "Hen", "Hen", "Hen", "Her", "Hjal", "Ho", "I", "In", "\u0130s", "Ish", "Je", "Je", "Jer", "Jo", "Jo", "Joph", "Ju", "Kart", "Khaf", "Kle", "Kro", "Kuz", "La", "La", "Lai", "Le", "Le", "L\u00e9", "Les", "Li", "Lib", "Lou", "Lou", "Lud", "Ma", "Ma", "Ma", "Mah", "Mar", "Mar", "Mas", "Mem", "Mi", "Mo", "Mu", "Na", "Naa", "Ne", "Nel", "Neph", "Ni", "Ni", "Nich", "No", "Nur", "Ol", "Oph", "Ot", "Ou", "Pe", "Po", "Ra", "Rob", "Rock", "Sa", "Sa", "Sam", "Sam", "San", "Ser", "Shi", "Si", "Six", "Ta", "Te", "Tha", "The", "The", "Thom", "Tim", "U", "Vai", "Val", "Var", "Ve", "Vi", "Vic", "Vlad", "Vul", "Wil", "Wil", "Wil", "Win", "Ya"};

	// Syllable 2 terminal
	public static String[] golem_syl2Term_default = new String[]{"'el", "aph", "as", "bert", "brecht", "bu", "can", "chus", "cis", "dam", "ert", "frey", "frid", "gas", "gen", "gher", "ghis", "ham", "helm", "im", "is", "ise", "jos", "ke", "la", "lah", "las", "lat", "liam", "lie", "lis", "ma", "mah", "mah", "mal", "mar", "mes", "met", "m\u00f3n", "my", "nus", "on", "quis", "rard", "re", "rene", "ri", "rich", "ro", "rub", "ry", "ry", "san", "s\u00e9", "seph", "ses", "sheth", "ston", "sus", "tan", "te", "ter", "ter", "tha", "thur", "to", "to", "ton", "va", "va", "vid", "ward", "wig", "win", "y", "za", "zi"};
	// Syllable 2 transitional
	public static String[] golem_syl2Trans_default = new String[]{"a", "a", "ah", "bi", "bra", "can", "chis", "co", "cor", "dal", "det", "di", "don", "dran", "er", "er", "er", "ex", "ex", "hai", "him", "ho", "ho", "i", "i", "i", "i", "i", "i", "i", "i", "i", "i", "i", "ka", "khi", "labh", "li", "li", "li", "liz", "lo", "ma", "mi", "mi", "mi", "min", "ne", "o", "o", "o", "o", "o", "o", "o", "o", "o", "po", "pol", "ra", "ra", "rar", "re", "ri", "ri", "ro", "ro", "ro", "sei", "sta", "s\u00fang", "ta", "te", "the", "ti", "to", "tor", "va", "va", "ya", "zi"};

	// Syllable 3 terminal
	public static String[] golem_syl3Term_default = new String[]{"a", "a", "as", "bhai", "bis", "dom", "don", "dore", "el", "el", "el", "el", "el", "el", "el", "el", "el", "el", "enne", "es", "fa", "ham", "him", "ick", "ik", "is", "ke", "las", "lim", "lo", "lo", "ly", "lyn", "ma", "ma", "meel", "mir", "na", "na", "nand", "nim", "o", "os", "oud", "pher", "phon", "pold", "pold", "sa", "sa", "sos", "tas", "thy", "tim", "try", "ver", "za", "zky"};
	// Syllable 3 transitional
	public static String[] golem_syl3Trans_default = new String[]{"a", "an", "can", "co", "de", "di", "di", "do", "et", "i", "i", "i", "i", "i", "l\u00e9", "lo", "ni", "ni", "or", "ri", "tab", "tai", "ti"};

	// Syllable 4 terminal
	public static String[] golem_syl4Term_default = new String[]{"a", "a", "a", "a", "a", "beth", "das", "der", "el", "el", "ha", "ix", "mus", "o", "on", "\u00f6n", "ta", "te", "us", "us"};
	// Syllable 4 transitional
	public static String[] golem_syl4Trans_default = new String[]{"ki", "mes", "si"};

	// Syllable 5 terminal
	public static String[] golem_syl5Term_default = new String[]{"us"};
	// Syllable 5 transitional
	public static String[] golem_syl5Trans_default = new String[]{"he", "tes"};
	
	// Syllable 6 terminal
	public static String[] golem_syl6Term_default = new String[]{};
	// Syllable 6 transitional
	public static String[] golem_syl6Trans_default = new String[]{"kia", "va"};

	// Syllable 7 terminal
	public static String[] golem_syl7Term_default = new String[]{"kiak", "ra"};

	
	
	/*
	 * Alien name pieces
	 */
	
	// Prefix-Suffix pieces
	public static String[] alien_prefix_default = new String[]{};
	public static String[] alien_suffix_default = new String[]{};
	
	// Single-syllable pieces
	public static String[] alien_oneSylBegin_default = new String[]{"Ae", "Ba", "Cthaa", "Dry", "Ghro", "Glee", "Gloo", "Ha", "Kra", "Krai", "Kri", "La", "Ma", "Nee", "Nu", "O", "Oo", "Tho", "To", "Wa", "Ye", "Yi", "Zha"};
	public static String[] alien_oneSylEnd_default = new String[]{"b", "c", "d", "g", "g", "g", "k", "k", "m", "n", "n", "n", "ng", "ng", "njh", "r", "r", "rn", "st", "t", "th", "th", "th"};
	
	// Syllable 1 transitional
	public static String[] alien_syl1Trans_default = new String[]{"'Ym", "A", "A", "A", "A", "A", "A", "A", "Ab", "Ab", "Ai", "Am", "Ar", "Ar", "Ar", "At", "Ay", "Az", "B", "Ba", "Ba", "Bok", "Bugg", "Bya", "C", "Chaug", "Co", "Co", "Crom", "Cthaegh", "Cthu", "Cthu", "Cthyl", "Ctog", "Cxa", "Cy", "Cy\u00e4", "D", "Da", "Dhu", "Di", "Dray", "Dy", "Dy", "Dz\u00e9", "E", "Ei", "Ei", "En", "Fu", "Ge", "Gha", "Ghis", "Ghroth", "Gi", "Gla", "Go", "Go", "Gog", "Gol", "Gtu", "Gur", "Gur", "Gwar", "Gzx", "H", "Ha", "Ha", "Hai", "Has", "Hnar", "Hziul", "I", "I", "Ial", "Idh", "Ig", "Il", "In", "Is", "Ja", "Je", "Juk", "K", "Ka", "Kaa", "Kaajh", "Kag", "Kas", "Kau", "Khal", "Klaa", "Klos", "Klu", "Ktha", "Kthaw", "Kur", "Lex", "Lloi", "Lo", "Lo", "Lu", "Ly", "M", "M", "Mh", "Mlan", "Mnom", "Mor", "Mor", "Mor", "Mort", "Mril", "My", "N", "Nath", "Nc", "Nc", "Ngir", "Ngyr", "No", "Nor", "Ny", "Ny", "Nyag", "Nyar", "Nyc", "Nyog", "O", "O", "Ob", "Oj", "Ol", "Or", "Ou", "Oz", "Pan", "Per", "Phan", "Phar", "Po", "Psu", "Ptar", "Q", "Qu", "Qua", "R", "Ra", "Raan", "Rag", "Rh", "Rha", "Rhan", "Rho", "Rid", "Ro", "S", "Sa", "Saa", "Sca", "Se", "Se", "Sed", "Sfat", "Sha", "Sha", "Sha", "Shab", "Shau", "Sheb", "Shis", "Shlith", "Sho", "Shte", "Shub", "Shudde", "Shuy", "Stha", "Suc", "Sum", "Swa", "Ta", "Ter", "Th", "Tha", "Tha", "Tha", "Tha", "Tru", "Tsa", "Tu", "Tu", "Tulz", "Ub", "Uit", "Ul", "Ut", "Uv", "Ver", "Vhu", "Vi", "Vile", "Vol", "Volg", "Vor", "Vth", "Vul", "Xa", "Xal", "Xc", "Xex", "Xi", "Xi", "Xin", "Xird", "Xo", "Xox", "Y", "Y", "Y", "Y", "Yad", "Yag", "Yagg", "Yc", "Yegg", "Yhag", "Yhash", "Yhoun", "Yibb", "Yid", "Yo", "Yog", "Yog", "Yor", "Ys", "Yu", "Yuck", "Yug", "Z", "Za", "Ze", "Zig", "Zin", "Zo", "Zoth", "Zs", "Zush", "Zvil"};
	
	// Syllable 2 terminal
	public static String[] alien_syl2Term_default = new String[]{"-Gath", "-Ha", "-Hoor", "-Kthu", "-Nihl", "-Oct", "-Shabb", "-Shash", "-Teth", "-Tstll", "-yaa", "'Kaalbh", "'keth", "'kru", "'lla", "'lor", "'mbu", "'Naath", "'rygh", "bek", "bon", "bur", "deh", "dens", "doth", "gha", "gha", "gog", "gon", "gor", "gra", "guth", "gy", "hal", "hash", "hort", "hoth", "hra", "ith", "ki", "kon", "koth", "la", "la", "ley", "lhu", "lith", "llgh", "loth", "lut", "mash", "min", "mo", "mus", "mus", "nar", "nee", "neth", "neth", "ni", "nid", "nus", "od", "ol", "ol", "qu", "qua", "quah", "ra", "rick", "rog", "rot", "rug", "ryx", "scha", "se", "ta", "tha", "thach", "thak", "thar", "thog", "thol", "thoom", "thot", "tis", "tli", "toon", "tu", "tur", "tur", "tyos", "urhn", "w\u00e0", "ya", "ya"};
	// Syllable 2 transitional
	public static String[] alien_syl2Trans_default = new String[]{"-Ax", "-go", "-Gol", "-Hor", "-Ka", "-Ko", "-Kor", "-Nig", "-Om", "-Ra", "-Sa", "-Sit", "-So", "-Te", "-Thad", "'bas", "'chte", "'endr", "'gnu", "'gol", "'Ho", "'ith", "'la", "'li", "'Na", "'Na", "'nar", "'nem", "'thal", "'Thul", "'tog", "'tya", "'Ulls", "'yth", "^Cru", "^M", "^Thor", "a", "a", "a", "a", "a", "a", "at", "ath", "ba", "bad", "bith", "bo", "bo", "cha", "chil", "cra", "da", "da", "da", "da", "de", "dig", "dy", "dy", "eg", "ga", "garg", "g\u00f2", "gor", "ha", "ho", "hog", "i", "i", "iig", "in", "kla", "kra", "la", "la", "lach", "le", "li", "li", "lim", "lo", "lu", "lu", "lur", "ma", "ma", "mel", "mi", "mo", "mon", "mut", "na", "na", "n\u00e0g", "nai", "nal", "nar", "no", "nog", "noth", "nu", "o", "ogh", "oht", "pan", "pes", "phan", "phoom", "pog", "quo", "ra", "ra", "rash", "rth", "ru", "sa", "sai", "sei", "so", "sog", "ssu", "stal", "ta", "ta", "ta", "te", "te", "tep", "tha", "tha", "thal", "thog", "thog", "thu", "thu", "ti", "to", "tol", "tyl", "ueb", "ur", "va", "val", "was", "xu", "ya", "ya", "yi", "zhor", "zil", "zom"};
	
	// Syllable 3 terminal
	public static String[] alien_syl3Term_default = new String[]{"-az", "-Gath", "-Ho", "-Ka", "-Thun", "-ya", "-Yai", "-Zhah", "'ell", "'ig", "'lu", "'ngo", "'st", "^Faugn", "a", "ach", "ath", "bra", "ca", "chenn", "cllp", "dag", "don", "don", "doss", "fu", "gash", "geg", "gen", "gha", "ghua", "ghua", "gorth", "goth", "gua", "gua", "gua", "ha", "hra", "hu", "ka", "kal", "kath", "ki", "kluth", "kon", "la", "la", "la", "la", "la", "lar", "los", "loth", "lu", "lun", "lun", "luq", "ma", "ma", "mis", "mog", "mon", "mon", "na", "nai", "ne", "nga", "nos", "noth", "nus", "ot", "pha", "pha", "pha", "qua", "rah", "rak", "rha", "roth", "ru", "sa", "sa", "sha", "sha", "tal", "tan", "tha", "tha", "tha", "thath", "thess", "thoth", "thoth", "thua", "tii", "tlan", "um", "va", "veg", "wrl", "ya", "yeg", "yig", "\u00ffk", "yoth", "zoth"};
	// Syllable 3 transitional
	public static String[] alien_syl3Trans_default = new String[]{"-Ghah", "-Gor", "-Hr", "-Na", "-Sath", "-Yg", "'i", "'so", "^Gn", "^Ryo", "^Shai", "^Ut", "^Z", "a", "ca", "da", "ebh", "gal", "gi", "gn", "gor", "gu", "i", "ig", "ii", "le", "li", "li", "li", "mi", "na", "\u00f1a", "nniss", "o", "o", "pith", "ra", "ril", "ro", "se", "sed", "shug", "tho", "u", "ui", "zhem"};
	
	// Syllable 4 terminal
	public static String[] alien_syl4Term_default = new String[]{"-Tha", "'Her", "'tho", "a", "a", "ah", "an", "ba", "cha", "den", "ghi", "glys", "gos", "goth", "gua", "ka", "korth", "kug", "la", "loth", "nac", "nb", "nis", "on", "on", "ops", "os", "pac", "rath", "rath", "shal", "suan", "sz", "taus", "tep", "yx"};
	// Syllable 4 transitional
	public static String[] alien_syl4Trans_default = new String[]{"-B", "'Nal", "'uq", "^Eg", "^Gwan", "cu", "ga", "i", "mn", "th\u00e6"};
	
	// Syllable 5 terminal
	public static String[] alien_syl5Term_default = new String[]{"'nk", "a", "du", "e", "le", "nis", "zhah"};
	// Syllable 5 transitional
	public static String[] alien_syl5Trans_default = new String[]{"-serr", "qa", "n\u00ef"};
	
	// Syllable 6 terminal
	public static String[] alien_syl6Term_default = new String[]{"-Mog", "'roth"};
	// Syllable 6 transitional
	public static String[] alien_syl6Trans_default = new String[]{"\u00e9l"};
	
	// Syllable 7 terminal
	public static String[] alien_syl7Term_default = new String[]{"l\u00fbs"};
	
	
	

	/*
	 * Goblin name pieces
	 */

	// Prefix-Suffix pieces
	public static String[] goblin_prefix_default = new String[]{};
	public static String[] goblin_suffix_default = new String[]{};
	
	// Single-syllable pieces
	public static String[] goblin_oneSylBegin_default = new String[]{"Do", "Dwa", "E", "E", "Fae", "Fau", "Ghou", "Gno", "Gwy", "Ha", "I", "Ji", "Li", "Ma", "Ma", "Mo", "Ni", "Ny", "O", "Pa", "Pu", "Smy", "Sphi", "Spri", "Sy", "Tro", "Tro", "Wa", "Wa"};
	public static String[] goblin_oneSylEnd_default = new String[]{"b", "ch", "ck", "g", "g", "g", "l", "lf", "ll", "lph", "me", "mp", "mph", "n", "n", "n", "nn", "nn", "nt", "nx", "rc", "rf", "te", "th", "tts", "tz", "w", "x"};

	// Syllable 1 transitional
	public static String[] goblin_syl1Trans_default = new String[]{"A", "A", "Ad", "\u00c1i", "A", "Al", "An", "A", "Ar", "As", "As", "As", "Ba", "Ban", "Bau", "Bei", "Bel", "Bla", "Blem", "Bo", "Bog", "Bri", "Bri", "Brow", "Buc", "Bug", "Bun", "Bw", "Cae", "Cal", "Cal", "Cen", "Ci", "Cl\u00edodh", "Clur", "Cy", "Cy", "Dag", "Dar", "Di", "Dok", "D\u00f6k", "Do", "Drau", "Dry", "Dul", "E", "E", "Em", "En", "E", "Fai", "Fin", "Fi", "Frey", "Frey", "Gar", "Ga", "Gi", "Glor", "Gob", "Go", "Gor", "Grem", "Gren", "Gwi", "Gwy", "Hal", "Har", "He", "Hi", "Hi", "Hob", "Hob", "Hul", "Hum", "In", "Ja", "Jen", "Jo", "Jo", "Ka", "Kal", "Kap", "Kel", "Ki", "Ki", "Kla", "Knock", "Ko", "Kor", "La", "La", "Le", "Lil", "Lj\u00f3", "Lu", "Lur", "Ma", "Ma", "Mang", "Man", "Me", "Mi", "Mo", "Mo", "Moo", "Moom", "Na", "Nai", "Ne", "Ner", "Nic", "Nu", "Nu", "O", "O", "O", "Pix", "Pol", "Pom", "Poo", "Pu", "Ral", "Red", "Ro", "Ru", "Sa", "Sa", "Sa", "Sa", "Seel", "Sel", "Shel", "Si", "Si", "Son", "Sprig", "Suc", "Sval", "Ten", "Ten", "Tik", "Ti", "Ti", "Tom", "Tra", "Tri", "Tsu", "U", "Un", "V\u00e6t", "Val", "Vi", "Wen", "Xa", "Ya", "Ya", "Ya", "Ye", "Yng", "Yu", "Zom"};

	// Syllable 2 terminal
	public static String[] goblin_syl2Term_default = new String[]{"ad", "ad", "ba", "ba", "bear", "bie", "bit", "bold", "bu", "ca", "ca", "ca", "ca", "cap", "chan", "ci", "co", "da", "db", "del", "dhe", "dine", "dra", "er", "fard", "ga", "ga", "gan", "gar", "gart", "ger", "gid", "gle", "go", "gon", "got", "goyle", "gre", "gu", "gu", "ie", "ie", "ja", "kan", "kha", "kie", "la", "la", "let", "lin", "lin", "na", "na", "na", "ne", "ney", "ni", "nie", "nik", "nin", "nis", "no", "ott", "pa", "py", "r", "ra", "rawn", "ren", "ren", "res", "reth", "rick", "ry","ryl", "sin", "taur", "te", "thyr", "tir", "ton", "tos", "tuns", "tyr", "vi", "wang", "yip", "ze"};
	// Syllable 2 transitional
	public static String[] goblin_syl2Trans_default = new String[]{"ba", "ba", "bar", "bat", "bau", "be", "ber", "ber", "bou", "ca", "can", "clo", "cu", "cu", "cu", "di", "gob", "gua", "i", "i", "i", "kae", "k\u00e1l", "ke", "ki", "ko", "ku", "ku", "kyr", "la", "la", "le", "li", "li", "li", "li", "ly", "ma", "ma", "me", "mi", "ming", "mo", "my", "na", "nan", "nan", "ne", "ne", "no", "no", "no", "phoe", "pre", "pu", "re", "ri", "rin", "ro", "ru", "sal", "s\u00e1l", "ta", "tal", "ter", "ter", "ti", "ti", "to", "tsu", "u", "u", "var", "wa", "ya"};

	// Syllable 3 terminal
	public static String[] goblin_syl3Term_default = new String[]{"a", "a", "ban", "be", "bi", "bo", "bus", "bus", "chaun", "chaun", "co", "core", "da", "es", "far", "far", "far", "gan", "gast", "geist", "go", "gon", "han", "ich", "id", "ie", "ja", "ka", "lang", "lin", "nak", "n\u00e1n", "ne", "ne", "on", "pa", "pes", "pod", "ra", "ra", "ro", "ro", "sa", "ta", "taur", "ter", "vin", "voi", "wa", "way", "yes"};
	// Syllable 3 transitional
	public static String[] goblin_syl3Trans_default = new String[]{"-on", "-u", "a", "ang", "bi", "ceph", "ciel", "gu", "hu", "i", "i", "kant", "ku", "ku", "man", "mo", "ni", "pu", "ro", "ru", "ta", "ter", "ton", "tsu"};

	// Syllable 4 terminal
	public static String[] goblin_syl4Term_default = new String[]{"a", "ba", "bi", "der", "do", "gal", "lam", "lo", "mann", "me", "mo", "mon", "na", "na", "na", "ne", "ra", "tian", "to", "us"};
	// Syllable 4 transitional
	public static String[] goblin_syl4Trans_default = new String[]{"a", "chei", "ku", "za"};

	// Syllable 5 terminal
	public static String[] goblin_syl5Term_default = new String[]{"bi", "ly", "res", "ros"};
	// Syllable 5 transitional
	public static String[] goblin_syl5Trans_default = new String[]{};
	
	// Syllable 6 terminal
	public static String[] goblin_syl6Term_default = new String[]{};
	// Syllable 6 transitional
	public static String[] goblin_syl6Trans_default = new String[]{};

	// Syllable 7 terminal
	public static String[] goblin_syl7Term_default = new String[]{};
	
	
	
	
}
