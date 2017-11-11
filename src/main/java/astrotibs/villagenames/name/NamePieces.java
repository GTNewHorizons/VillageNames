package astrotibs.villagenames.name;

public class NamePieces {
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
	
	// Colors that can be used for a sign name
	// 0=black, 1=dark_blue, 2=dark_green, 3=dark_aqua
	// 4=dark_red, 5=dark_purple, 6=gold, 7=gray
	// 8=dark_gray, 9=blue, a=green, b=aqua
	// c=red, d=light_purple, e=yellow, f=white
	
	public static int[] codeColors_default = new int[] {
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, //White, 18%
			1, //Orange, 1%
			4, 4, 4, 4, 4, 4, 4, 4, 4, //Yellow, 9%
			9, 9, //Cyan, 2%
			11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, //Blue 21%
			13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, //Green, 13%
			14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, //Red 31%
			15, 15, 15, 15, 15, //Black 5%
			-1 // If you call this, then call codeColors_remaining instead
		};
	public static int[] codeColors_remaining = new int[] { // Remaining colors
			2,  // Magenta
			3,  // Light blue
			5,  // Lime
			6,  // Pink
			7,  // Gray
			8,  // Silver
			10, // Purple
			12  // Brown
		};
	/*
	 * Village name pieces
	 */
	
	// Prefixes and suffixes
	public static String[] prefix_default = new String[]{"East", "El", "Flying", "Fort", "Fort", "Fort de", "Great", "Half", "King", "Le", "La", "Monte", "Mount", "Mount", "New", "New", "Old", "North", "Port", "Port", "Port", "Port", "Port aux", "Port of", "Port of the", "Queen", "Saint", "Saint", "Saint", "Saint", "San", "San", "San", "San", "San", "San", "San", "San", "Santa", "Santo", "Santo", "S\u00e3o", "S\u00e3o", "South", "Sri", "St.", "St.", "St.", "St.", "St.", "St.", "St.", "St.", "The", "West"};
	public static String[] suffix_default = new String[]{"Bank", "Castle", "Cays", "Canyon", "City", "City", "City", "City", "City", "City", "City", "City", "City", "City", "City", "City", "Cove", "Cove", "Cove", "de la Sierra",  "del Sol", "District", "Ferry", "Estate", "Hollow", "Inn", "Kotte", "Lake", "of the Hill", "of the Mountain", "of the Seas", "of the 7 Seas", "of the Valley", "Park", "Park", "Pine", "Point", "Point", "Port", "Port", "Town", "Town", "Town", "Town", "Town", "Town", "Town", "Village"};
	
	// Single-syllable pieces
	public static String[] oneSylBegin_default = new String[]{"A", "Ba", "Ba", "Be", "Bla", "Blue", "Bo", "Bra", "Bro", "Bu", "Ca", "Cru", "Fi", "Fi", "Fi", "Fra", "Fy", "Geo", "Geo", "Gra", "Gree", "Ha", "Jo", "Jo", "Jua", "Jua", "Ka", "Kie", "Kyi", "Li", "Me", "Mi", "Mi", "Mi", "Moo", "Na", "Nuu", "Nuu", "Pa", "Po", "Pra", "Pra", "Pre", "Pri", "Pri", "Que", "Re", "Roa", "Roa", "Ro", "Ro", "Schaa", "Seou", "Seou", "Si", "Shi", "Spai", "Tree", "Tu", "Tze", "U", "Wa", "Whi", "Yo"};
	public static String[] oneSylEnd_default = new String[]{"ck", "d", "d", "d", "de", "de", "des", "fsk", "ft", "gue", "gue", "gue", "hn's", "hn's", "k", "k", "l", "l", "l", "lb", "le", "lm", "lse", "m", "me", "me", "n", "n", "n", "n", "n", "n", "nk", "nn", "nce", "nce", "nce", "ne", "nsk", "nsk", "pe", "r", "re", "rge", "rge", "rh", "rk", "rn", "rnt", "rshe", "sh", "sh", "st", "te", "v", "v", "ve", "wn", "x", "y", "z", "z"};
	
	// Syllable 1 transitional
	public static String[] syl1Trans_default = new String[]{"A", "A", "A", "A", "A", "Aa", "Ac", "Ac", "Al", "Al", "Al", "Al", "Al", "Al", "Am", "Am", "Am", "Ark", "At", "Ath", "Auck", "Ba", "Ba", "Ba", "Ba", "Ba", "Bagh", "Bagh", "Ban", "Ban", "Ban", "Ban", "Bang", "Bang", "Basse", "Basse", "Basse", "Basse", "Be", "Be", "Beau", "Bei", "Bei", "Bei", "Bel", "Bel", "Ber", "Ber", "Bis", "Bis", "Bish", "Bish", "Bran", "Bri", "Bridge", "Bridge", "Brus", "Brus", "Bur", "Bur", "Ca", "Cai", "Cai", "Cam", "Car", "Car", "Cas", "Cas", "Cay", "Cay", "Chi", "Cley", "Clif", "Clot", "Co", "Cock", "Col", "Con", "Conde", "Cor", "Cos", "Cos", "Cre", "Cre", "Cus", "Da", "Da", "Da", "Da", "De", "De", "Del", "Dha", "Dha", "Di", "Di", "Do", "Do", "Do", "Dol", "Doug", "Doug", "Du", "Du", "Dub", "Dub", "Dun", "Dus", "Dy", "E", "Ed", "Ed", "Elf", "Es", "Es", "Fa", "Fair", "Fal", "Fi", "Fi", "Free", "Free", "Ga", "Ga", "Gai", "Geor", "Geor", "Geor", "George", "George", "George", "Goats", "Gon", "Groz", "Gy", "Ha", "Har", "Hi", "Hong", "I", "Inns", "Is", "Is", "Ja", "James", "Ji", "Jo", "Jo", "Ju", "Ju", "Ju", "Ka", "Ka", "Ka", "Kai", "Kar", "Khar", "Kings", "Kings", "Kings", "Kings", "Kings", "Kings", "Ko", "Koh", "Kow", "Ku", "Ku", "La", "Li", "Li", "Lim", "Lind", "Lis", "Lis", "Lo", "Lo", "Lon", "Lon", "Lou", "Lou", "Lu", "Luo", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Mar", "Mel", "Mi", "Mid", "Mo", "Mores", "Mores", "Mos", "Mos", "Mum", "Mus", "Mus", "My", "My", "Myth", "Nan", "Nas", "Nas", "New", "Ni", "Ni", "Nia", "Nia", "Nouak", "Nouak", "On", "Or", "Os", "Os", "Pa", "Pa", "Pa", "Pau", "Pe", "Pe", "Pe", "Phnom", "Phnom", "Pi", "Pi", "Ply", "Pra", "Pra", "Pra", "Pur", "Pyong", "Pyong", "Que", "Qui", "Ra", "Re", "Rep", "Ri", "Ri", "Ri", "Ri", "Ro", "Ro", "Roc", "Sa", "Sa", "Sa", "Sai", "Sai", "Se", "Ses", "Shang", "Skop", "Skop", "Stan", "Stan", "Stock", "Stock", "Su", "Su", "Sur", "Syd", "Syl", "Tai", "Tai", "Tal", "Tal", "Tash", "Tash", "Te", "Teh", "Teh", "Temp", "Tha", "Thim", "Thim", "Tim", "To", "To", "To", "To", "Tok", "Tom", "T\u00f3r", "T\u00f3r", "Tow", "Tre", "Troi", "Tu", "Tu", "Va", "Val", "Vec", "Vi", "Vi", "Vi", "Vien", "Vien", "Vio", "War", "War", "War", "We", "Win", "Wind", "Wind", "Wu", "Xi", "Ya", "Ya", "Yan", "Yel", "Za", "Za", "Zo", "Z\u00fc"};
	
	// Syllable 2 terminal
	public static String[] syl2Term_default = new String[]{"^Bal", "^Kong", "^Penh", "^Penh", "-Terre", "-Terre", "a", "a", "'an", "ba", "ba", "bai", "bai", "bat", "ber", "beye", "bliz", "blum", "bon", "bon", "bul", "bul", "bul", "brook", "burn", "by", "by", "cat", "cat", "chol", "chott", "chott", "co", "cow", "cow", "cra", "cra", "cre", "dad", "dad", "deel", "den", "don", "don", "door", "dor", "drid", "drid", "duz", "enne", "enne", "erre", "erre", "fax", "ga", "ga", "gar", "gart", "gate", "ge's", "ge's", "ge's", "giers", "giers", "gole", "gon", "gon", "gos", "grade", "grade", "greb", "greb", "gui", "gui", "ha", "ha", "hai", "ham", "heim", "herst", "hi", "hill", "hill", "hoek", "hoek", "holm", "holm", "ia", "ia", "is", "is", "i\u00fan", "je", "je", "jing", "jing", "jul", "ka", "ka", "kar", "kar", "kek", "kek", "kent", "kent", "ket", "kings'", "kok", "kok", "ku", "ku", "kul", "kyo", "kyo", "la", "la", "lamb", "land", "land", "las", "las", "l\u00e9", "l\u00e9", "lege", "lem", "let", "let", "ley", "ley", "ley", "li", "li", "li", "lin", "lin", "lin", "lin", "lin", "lin", "ling", "lize", "lo", "lo", "lo", "lo", "loom", "loon", "low", "ma", "ma", "man", "man", "m\u00e9", "m\u00e9", "m\u00e9", "mey", "mey", "mo", "mond", "mouth", "mouth", "mur", "naan", "nak", "ney", "nic", "nis", "nis", "nis", "no", "no", "noi", "non", "ny", "ol", "pan", "pei", "pei", "pers", "phu", "phu", "ple", "po", "port", "rac", "ra", "ra", "ran", "ran", "range", "rel", "ren", "ren", "rich", "ril", "ris", "ris", "ro", "ro", "ron", "ror", "ros", "rut", "rut", "sahl", "sau", "sau", "sau", "sau", "saw", "saw", "scent", "scent", "s\u00e9", "s\u00e9", "seau", "seau", "sels", "sels", "shavn", "shavn", "side", "son", "ta", "ta", "tai", "tair", "tam", "ter", "ter", "ter", "terre", "terre", "thar", "thens", "thens", "tiane", "tiane", "tio", "to", "ton", "ton", "ton", "ton", "ton", "tor", "tos", "toum", "town", "town", "town", "town", "town", "town", "town", "town", "town", "town", "tre", "tries", "tries", "va", "wait", "wait", "ward", "ward", "wen", "wich", "wood", "yadh", "yadh", "yang", "yang", "yang", "za", "zo", "zon", "zus", "zus"};
	// Syllable 2 transitional
	public static String[] syl2Trans_default = new String[]{"^Chi", "^es", "^Pe", "'Dja", "'Dja", "a", "a", "a", "a", "a", "al", "an", "an", "ba", "ba", "ba", "bel", "ber", "bi", "bi", "ble", "blja", "blja", "bo", "bo", "bou", "bou", "bral", "bral", "bre", "bre", "bu", "bu", "bu", "cha", "cha", "ches", "ci", "cho", "co", "co", "da", "da", "da", "dain", "dams", "dams", "der", "der", "din", "dis", "dis", "do", "dor", "dor", "dur", "em", "em", "en", "en", "er", "ex", "fa", "fe", "fi", "fi", "fon", "fu", "ga", "ga", "ga", "ga", "ga", "ga", "ga", "ga", "ga", "ga", "ga", "ga", "ga", "g\u00e5t", "ge", "gei", "go", "go", "go", "go", "go", "gu", "gu", "gua", "guer", "han", "i", "ing", "ju", "jum", "jum", "ka", "ka", "ka", "kar", "kar", "ke", "khu", "khu", "kir", "kja", "kja", "ku", "ku", "la", "la", "la", "la", "la", "laan", "lan", "lan", "lem", "lem", "lep", "let", "li", "li", "li", "li", "lin", "ling", "ling", "lips", "lo", "lo", "lom", "lon", "lon", "lotte", "lotte", "ma", "ma", "ma", "ma", "ma", "ma", "man", "man", "mas", "me", "m\u00e9", "m\u00e9", "mil", "min", "min", "mo", "mou", "mou", "mous", "mu", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "ne", "ni", "ni", "ni", "ni", "ni", "nos", "nos", "oun", "pa", "pa", "pa", "pa", "pa", "pe", "pe", "pen", "pen", "pi", "pi", "pi", "pi", "po", "po", "po", "pu", "pu", "pyi", "ra", "ra", "ra", "ra", "ra", "ra", "ra", "ra", "ra", "ra", "ra", "ra", "ra", "ran", "ran", "ran", "ras", "ras", "re", "re", "re", "re", "ren", "ri", "ri", "ri", "ri", "ri", "ri", "ri", "ro", "ro", "ro", "ro", "ro", "ro", "ro", "ron", "ru", "ru", "ru", "sa", "sa", "sa", "se", "se", "sha", "sha", "shan", "shan", "si", "si", "si", "si", "s\u00ed", "s\u00ed", "sin", "sin", "ster", "ster", "sun", "sun", "ta", "ta", "ta", "ta", "ta", "ta", "ta", "ta", "ta", "tan", "te", "te", "te", "te", "ti", "ti", "ti", "ti", "ti", "ti", "ti", "ti", "tin", "to", "to", "to", "to", "to", "to", "to", "to", "tra", "va", "va", "va", "va", "va", "va", "va", "va", "vern", "vi", "vo", "xi", "xi", "ya", "ya", "year", "year", "za", "za", "za", "zi"};
	
	// Syllable 3 terminal
	public static String[] syl3Term_default = new String[]{"^Minh", "'a", "'a", "a", "a", "a", "a", "a", "a", "a", "ah", "bard", "bat", "bat", "be", "be", "bi", "bi", "bo", "bo", "bourg", "bourg", "bul", "burg", "byen", "byen", "can", "can", "cas", "cas", "chi", "cle", "co", "co", "co", "cus", "da", "da", "da", "dam", "dam", "daw", "d\u00e9", "djan", "do", "dor", "dor", "down", "du", "du", "dzou", "dzou", "er", "er", "fi", "fi", "ford", "ga", "gar", "gen", "go", "go", "got", "got", "gua", "gua", "gwe", "gwe", "head", "heim", "ja", "je", "ka", "ka", "ka", "ki", "ki", "ki", "kir", "ko", "ko", "kry", "kry", "la", "la", "la", "la", "lah", "li", "li", "li", "li", "li", "li", "lia", "ma", "ma", "ma", "ma", "ma", "man", "mand", "mba", "mi", "mi", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "\u00f1a", "nas", "nau", "nau", "ne", "ni", "ni", "ni", "ning", "no", "nou", "nu", "ny", "pan", "pest", "pest", "po", "po", "pol", "pol", "pore", "pore", "quil", "ra", "ra", "ra", "ra", "ra", "re", "re", "rest", "rest", "ri", "rin", "ro", "ro", "ru", "ru", "ry", "sa", "sa", "sa", "sa", "stad", "stad", "ta", "ta", "ta", "ta", "ta", "t\u00e1", "t\u00e1", "tar", "tar", "tein", "ter", "ti", "ti", "tie", "tis", "to", "to", "to", "to", "ton", "ton", "ton", "ton", "town", "town", "ty", "us", "us", "van", "van", "vik", "vik", "ville", "ville", "ville", "ville", "wa", "wa", "youne"};	
	// Syllable 3 transitional
	public static String[] syl3Trans_default = new String[]{"^A", "^A", "^A", "^A", "^Ai", "^Ai", "^Ba", "^Dha", "^Ga", "^Lum", "^Lum", "^Pa", "^Ro", "^Sa", "^Sa", "^Se", "^Se", "-No", "-U", "-U", "'a", "'a", "a", "a", "a", "a", "an", "baa", "blan", "bu", "bu", "bu", "ci", "ci", "ci", "ci", "ci", "ci", "di", "di", "di", "di", "dou", "dou", "e", "e", "e", "e", "fu", "fu", "ha", "ha", "i", "ja", "je", "je", "je", "je", "ka", "kun", "li", "li", "li", "ma", "ma", "ma", "ma", "ma", "me", "me", "na", "na", "na", "na", "nes", "ni", "ni", "ni", "o", "ra", "ra", "ra\u00ed", "re", "ri", "ri", "ri", "ri", "ri", "ri", "ro", "ro", "ru", "ru", "rul", "ry", "sa", "sa", "sa", "si", "si", "sko", "sko", "sla", "sla", "sou", "vi", "vi", "vi", "vi", "vi", "vi", "vo", "war"};
	
	// Syllable 4 terminal
	public static String[] syl4Term_default = new String[]{"a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "bad", "bi", "bo", "burg", "ca", "ca", "ca", "ce", "da", "fo", "gen", "gen", "go", "go", "go", "gou", "gou", "kert", "kert", "kro", "la", "la", "laam", "lem", "lem", "lem", "mud", "na", "na", "ne", "ne", "o", "o", "\u00f3n", "\u00f3n", "port", "pur", "pur", "ra", "ra", "ra", "ra", "ra", "res", "res", "rgh", "ri", "shu", "shu", "si", "si", "si", "so", "stad", "stad", "tar", "te", "te", "ti", "ti", "ti", "tor", "tu", "tu", "va", "va", "vo", "vo", "vo", "ya", "za"};
	// Syllable 4 transitional
	public static String[] syl4Trans_default = new String[]{"^la", "^la", "ba", "ba", "de", "de", "de", "dri", "gal", "gal", "ham", "ham", "lo", "lo", "ma", "ma", "na", "na", "pi", "pi", "ri", "ri", "ri", "ri"};
	
	// Syllable 5 terminal
	public static String[] syl5Term_default = new String[]{"a", "ba", "ba", "bo", "bo", "fa", "fa", "lie", "lie", "n", "n", "o", "o", "pa", "pa"};	
	// Syllable 5 transitional
	public static String[] syl5Trans_default = new String[]{"^Be", "^Be", "^Can", "^Can", "^Ve", "^Ve", "ne", "ri", "ri"};
	
	// Syllable 6 terminal
	public static String[] syl6Term_default = new String[]{"lla", "lla", "vo", "vo"};	
	// Syllable 6 transitional
	public static String[] syl6Trans_default = new String[]{"ga", "ga", "pu", "ton", "ton"};
	
	// Syllable 7 terminal
	public static String[] syl7Term_default = new String[]{"ment", "ment", "ra", "wan", "wan"};
	
	
	/*
	 * Villager name pieces
	 */
	
	// Single-syllable pieces
	public static String[] villager_oneSylBegin_default = new String[]{"A", "A", "A", "Ai", "Ba", "Be", "Be", "Bi", "Bi", "Bi", "Bla", "Bo", "Bo", "Bra", "Bra", "Bru", "Bu", "Cai", "Cha", "Che", "Chri", "Chri", "Chri", "Ci", "Ci", "Ci", "Ci", "Ci", "Ci", "Ci", "Ci", "Clou", "Da", "Da", "Dao", "De", "De", "Dji", "Do", "Dua", "E", "Ea", "Ea", "Fa", "Fe", "Fi", "Flo", "Ga", "Gau", "Geo", "Geo", "Geo", "Geo", "Geo", "Geo", "Go", "Guo", "Guy", "Ha", "Hei", "Ho", "Hui", "Hui", "Ja", "Ja", "Ja", "Ja", "Ja", "Ja", "Ja", "Ja", "Ja", "Ja", "Jay", "Ji", "Ji", "Jia", "Jie", "Jo", "Jo", "Joe", "Joe", "Jua", "Ka", "Ka", "Ka", "Kai", "Ki", "Ko", "Kri", "La", "Li", "Li", "Lia", "Lia", "Lo", "Lu", "Lu", "Ma", "Ma", "Ma", "Ma", "Ma", "Mi", "Mi", "Mi", "Na", "Ne", "Ni", "Ni", "No", "Pau", "Pau", "Pe", "Pe", "Pu", "Qia", "Qio", "Rai", "Ray", "Re", "Re", "Ree", "Ree", "Ri", "Ro", "Ru", "Sco", "Sco", "Sco", "Sha", "Sha", "Shu", "Si", "Sji", "Smi", "Smi", "Smy", "Squa", "Tao", "Tha", "Tho", "Ti", "Ti", "To", "To", "To", "Tri", "We", "We", "Wei", "Wei", "Xia", "Xia", "Xu", "Ya", "Ya", "Yo", "Yu", "Ze", "Zhe", "Zo", "Zo", "Zo"};
	public static String[] villager_oneSylEnd_default = new String[]{"b", "bs", "ce", "ck", "ck", "ck", "ck", "cke", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "de", "dge", "dge", "dge", "ff", "ff", "ff", "ggs", "hd", "hn", "hn", "k", "ke", "ke", "l", "l", "le", "les", "ll", "ll", "ll", "ll", "lm", "m", "m", "m", "m", "m", "m", "mes", "mes", "mes", "mes", "mie", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "n", "ne", "ne", "ne", "ne", "ne", "ne", "ng", "ng", "ng", "ng", "ng", "ng", "ng", "ng", "ng", "ng", "ng", "ng", "ng", "ng", "ng", "nh", "nk", "nk", "nn", "nn", "nn", "ps", "r", "rc", "rc", "rc", "rge", "rge", "rge", "rk", "rk", "rl", "rl", "rles", "rn", "rn", "rn", "rth", "rtz", "s", "s", "s", "sch", "sh", "ss", "tch", "tch", "tch", "te", "te", "th", "th", "th", "th", "tt", "tt", "tt", "ve", "x"};
	
	// Syllable 1 transitional
	public static String[] villager_syl1Trans_default = new String[]{"A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "Aa", "Aad", "Ab", "Ab", "Aer", "Ag", "Ah", "Ai", "Ai", "Al", "Am", "An", "An", "An", "An", "An", "An", "An", "An", "An", "An", "An", "Ar", "Ar", "Ar", "Ar", "As", "Ash", "Ash", "Ay", "Ba", "Ba", "Ba", "Ba", "Ba", "Bai", "Bar", "Bar", "Bar", "Be", "Be", "Ben", "Ben", "Ben", "Beth", "Bez", "Bi", "Bik", "Bil", "Bing", "Bis", "Bo", "Bor", "Bra", "Brad", "Bug", "Byb", "Ca", "Ca", "Ca", "Ca", "Ca", "Cag", "Cal", "Cal", "Ce", "Ce", "Ce", "Char", "Char", "Chi", "Chia", "Chlo", "Chlo", "Chris", "Chris", "Chry", "Cin", "Cin", "Cin", "Co", "Co", "Cry", "Cy", "Da", "Da", "Da", "Da", "Da", "Dai", "Dan", "Dar", "Dar", "De", "De", "De", "De", "Dex", "Di", "Di", "Di", "Do", "Do", "Do", "Do", "Do", "Dor", "Dra", "Dun", "Dun", "Dy", "Dy", "Dzog", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "Ed", "Ed", "Ei", "El", "El", "El", "El", "El", "El", "Em", "Em", "Em", "Em", "Em", "Em", "Em", "Em", "En", "Ex", "Fa", "Fa", "Fa", "Fa", "Fa", "Fa", "Fa", "Fa", "Fa", "Fa", "Fa", "Fa", "Fa", "Fat", "Fat", "Fen", "Fen", "Fi", "Flo", "Fong", "Fran", "Fran", "Fre", "Fri", "Fu", "Fu", "Fu", "Ga", "Ga", "Ga", "Ga", "Ga", "Ga", "Gab", "Gae", "Gar", "Gar", "Gar", "Geor", "Geor", "Gi", "Gi", "Gil", "Gil", "Gil", "Gior", "Glo", "Go", "Gol", "Gol", "Gor", "Gret", "G\u00fcn", "Gung", "Guts", "Ha", "Ha", "Ha", "Ha", "Ha", "Ha", "Ha", "Ha", "Ha", "Ham", "Han", "Han", "Har", "Har", "Has", "Has", "Has", "He", "Hei", "Hei", "Hel", "Hen", "Hen", "Her", "Hi", "Hi", "Hi", "Hil", "Hil", "Ho", "Ho", "Hos", "Hos", "Hu", "Hus", "I", "I", "I", "I", "I", "I", "I", "I", "If", "If", "In", "In", "Io", "Ir", "Ja", "Ja", "Ja", "Jac", "Jac", "Jack", "Jay", "Jay", "Je", "Je", "Je", "Jen", "Jes", "Jo", "Jo", "Jo", "Jo", "Jo", "John", "John", "Ju", "Ju", "Ju", "Jun", "Ka", "Ka", "Ka", "Ka", "Kef", "Kel", "Ken", "Kha", "Ki", "Ki", "Ki", "Kil", "Klu", "Kok", "Kop", "Krish", "Ksen", "Ku", "La", "La", "La", "La", "Lak", "Lam", "Lau", "Lau", "Lau", "Le", "Le", "Le", "Le", "Le", "L\u00e9", "Lei", "Len", "Lew", "Li", "Li", "Li", "Li", "Lind", "Lo", "Lo", "Lo", "Lou", "Low", "Lu", "Lu", "Lu", "Lu", "Lu", "Lu", "Lu", "Lu", "Lu", "Lu", "Lu", "Lu", "Lu", "Lu", "Lu", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Ma", "Maar", "Mah", "Mak", "Man", "Man", "Mar", "Mar", "Mar", "Mar", "Mar", "Mar", "Mar", "Mar", "Mar", "Mar", "Mar", "Mar", "Mar", "Mar", "Mau", "Max", "Me", "Me", "Me", "Meh", "Meh", "Mi", "Mi", "Mi", "Mi", "Mi", "Mi", "Mi", "Mi", "Mi", "Min", "Min", "Min", "Mo", "Mo", "Mo", "Mo", "Mo", "Mo", "Mo", "Mo", "Mo", "Mo", "Mo", "Mo", "Mo", "Mo", "Mon", "Mor", "Mor", "Mu", "Mu", "Mu", "Mu", "Muk", "Myr", "Na", "Na", "Na", "Na", "Na", "Na", "Na", "Ne", "Ne", "Ne", "Nea", "Nel", "Ner", "Ni", "Ni", "Ni", "Ni", "Ni", "No", "No", "No", "No", "No", "No", "No", "No", "No", "Nor", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "Od", "Pa", "Pa", "Pa", "Pal", "Pan", "Pan", "Pau", "Pe", "Phae", "Pho", "Pi", "Pi", "Po", "Po", "Pre", "Pri", "Quet", "Quis", "Ra", "Ra", "Ra", "Ra", "Rai", "Rai", "Ras", "Re", "Re", "Re", "Rhi", "Ri", "Ri", "Ri", "Ri", "Ri", "Ro", "Ro", "Ro", "Rob", "Ron", "Row", "Ru", "Ru", "Ru", "Ru", "Ry", "Ry", "Ry", "Sa", "Sa", "Sa", "Sa", "Sa", "Sa", "Sa", "Sad", "Sal", "San", "San", "San", "San", "San", "Sar", "Sar", "Sar", "Scar", "Scar", "Se", "Se", "Se", "Se", "Se", "Sei", "Sel", "Seo", "Sep", "Set", "Sev", "Sha", "She", "Shi", "Shri", "Shu", "Si", "Si", "So", "So", "So", "So", "So", "So", "So", "So", "So", "So", "So", "So", "So", "So", "Sof", "Som", "Sta", "Ste", "Ste", "Stel", "Stra", "Su", "Su", "Syl", "Ta", "Ta", "Ta", "Ta", "Ta", "Tak", "Tal", "Te", "Te", "Te", "Te", "Te", "Tel", "The", "Tho", "Tho", "Tho", "Ti", "Ti", "Ti", "Ti", "Ti", "Ti", "Ti", "Ti", "Tif", "Tif", "Tim", "To", "To", "To", "Tom", "Ton", "Tre", "Tri", "Tsu", "Tsu", "Twin", "U", "Ul", "Un", "Ur", "Va", "Ven", "Vi", "Vi", "Vic", "Vic", "Vik", "Vik", "Vin", "Vin", "Vin", "Wal", "War", "Wen", "Wen", "Wi", "Wil", "Wil", "Wil", "Wil", "Wil", "Wil", "Wil", "Wil", "Wil", "Xan", "Xe", "Xi", "Yas", "Ye", "Yous", "Yu", "Yu", "Yuf", "Zah", "Zan", "Zar", "Ze", "Ze", "Zeb", "Zen", "Zi", "Zig", "Zo", "Zo", "Zur"};
	
	// Syllable 2 terminal
	public static String[] villager_syl2Term_default = new String[]{"^Paul", "^Wei", "-fen", "-hao", "-jun", "-yeon", "a", "a", "a", "a", "a", "ah", "ah", "ah", "ah", "ah", "ah", "am", "am", "am", "am", "am", "am", "an", "an", "an", "an", "an", "\u00e3o", "ba", "be", "bert", "bez", "bib", "bolt", "bos", "bul", "by", "by", "ca", "ca", "cal", "can", "can", "card", "cas", "cas", "cas", "ce", "cent", "cent", "chael", "chael", "chai", "chai", "chard", "chel", "chen", "cil", "cious", "cis", "co", "co", "cus", "cus", "cy", "da", "da", "da", "da", "dam", "dam", "dam", "dane", "de", "death", "deen", "del", "den", "den", "den", "den", "den", "des", "di", "di", "din", "do", "don", "dor", "dra", "dra", "dra", "drei", "drew", "drew", "dy", "dy", "dy", "dy", "dy", "dy", "e", "e", "el", "ell", "erre", "erts", "et", "ey", "fa", "fer", "fie", "fus", "ga", "ga", "ga", "gan", "gan", "gann", "gar", "ger", "ghen", "gi", "gi", "go", "go", "go", "gon", "gon", "gret", "gr\u00e9t", "grjet", "guel", "gus", "gy", "ha", "ha", "ha", "ha", "har", "he", "he", "hei", "helm", "ho", "i", "ia", "ia", "id", "iel", "im", "inj", "ior", "is", "is", "is", "ise", "ith", "ja", "jin", "jin", "jo", "jr", "ka", "ka", "ka", "ka", "ka", "kahn", "kain", "kas", "kas", "kau", "ke", "kel", "ki", "ki", "ko", "kol", "ku", "kub", "kub", "la", "la", "la", "la", "la", "la", "la", "lah", "lak", "lal", "lal", "lan", "lan", "land", "land", "land", "las", "layne", "led", "len", "lene", "les", "let", "lex", "lex", "ley", "ley", "li", "li", "li", "li", "liam", "liam", "liam", "liam", "liam", "liam", "lice", "lie", "lie", "lie", "lik", "lim", "lim", "lom", "los", "luf", "ly", "ly", "ly", "lyn", "ma", "ma", "ma", "ma", "ma", "ma", "ma", "ma", "ma", "ma", "ma", "ma", "ma", "mak", "man", "mar", "mar", "marck", "mas", "mas", "mas", "mas", "med", "mer", "met", "mi", "mil", "mon", "m\u00f3n", "moud", "muh", "muh", "mus", "mus", "my", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "naa", "nan", "ne", "nei", "nes", "net", "neth", "ni", "nie", "no", "no", "non", "ny", "ny", "ny", "ny", "o", "o", "o", "o", "on", "on", "ott", "pe", "phie", "qing", "ra", "ra", "ra", "ra", "ra", "rad", "rah", "ram", "rav", "reet", "reh", "rell", "ren", "ren", "rene", "req", "ret", "ric", "rich", "rick", "rid", "rie", "rie", "rik", "rill", "rim", "rin", "rir", "rir", "ris", "ris", "rit", "ro", "ro", "rom", "ron", "ros", "ru", "rus", "ry", "ry", "ry", "ry", "ry", "ry", "ryl", "sa", "sa", "say", "sef", "sef", "sein", "seph", "sey", "sha", "shal", "shmi", "shoi", "sie", "sif", "sil", "sim", "sin", "son", "son", "son", "stal", "sti", "suf", "ta", "tan", "tan", "tar", "tem", "ten", "ter", "ter", "ter", "than", "than", "than", "ther", "tin", "tine", "tis", "to", "ton", "tor", "tos", "trice", "tur", "u", "uel", "va", "va", "va", "val", "van", "ven", "ves", "vey", "vi", "vid", "vin", "vin", "vin", "vine", "vis", "vit", "wa", "ward", "wu", "ya", "ya", "ya", "ya", "ya", "ya", "ya", "ya", "yep", "za", "zat", "ze", "zel", "zer", "zer"};
	// Syllable 2 transitional
	public static String[] villager_syl2Trans_default = new String[]{"a", "a", "a", "a", "a", "a", "a", "a", "an", "an", "bar", "bas", "bas", "bi", "bi", "bi", "bra", "bri", "bri", "bri", "ce", "ces", "chi", "c\u00ed", "c\u00ed", "cil", "cro", "da", "da", "dal", "de", "de", "de", "deg", "del", "del", "der", "di", "di", "di", "do", "dre", "du", "du", "e", "e", "e", "e", "e", "e", "fa", "fa", "fal", "fi", "fi", "fi", "fi", "fi", "fi", "fi", "fi", "fi", "fi", "f\u00ed", "f\u00ed", "ga", "ga", "ga", "ga", "ge", "gi", "gi", "gi", "gis", "gu", "gus", "gus", "ha", "ha", "ha", "ha", "ha", "ha", "ham", "ham", "ham", "ham", "ham", "ham", "ham", "ham", "hi", "hi", "i", "i", "i", "i", "i", "i", "i", "i", "ja", "ja", "ka", "ka", "ki", "ko", "ko", "ko", "ku", "la", "le", "le", "le", "le", "lea", "lek", "lex", "lex", "lex", "lex", "li", "li", "li", "li", "li", "li", "li", "li", "li", "li", "li", "li", "li", "li", "li", "li", "li", "li", "li", "li", "li", "li", "li", "li", "lis", "lo", "lo", "lu", "ly", "ma", "ma", "ma", "ma", "ma", "ma", "man", "me", "me", "me", "me", "me", "me", "mi", "mi", "mi", "mi", "mi", "mi", "mi", "mi", "mi", "mig", "ming", "mir", "my", "na", "na", "na", "na", "naz", "ni", "ni", "no", "no", "nu", "o", "o", "o", "o", "pa", "pan", "pha", "phi", "phi", "phi", "phi", "pu", "qa", "que", "ra", "ra", "ran", "re", "re", "re", "ren", "ri", "ri", "ri", "ri", "ri", "ri", "ri", "ri", "ri", "ri", "ri", "ri", "ri", "ri", "ri", "ri", "ri", "ri", "ri", "r\u00ed", "r\u00ed", "r\u00ed", "rin", "ro", "ro", "rol", "ru", "ry", "ry", "ry", "sa", "sa", "scil", "se", "so", "so", "sta", "su", "su", "su", "ta", "ta", "ta", "ta", "tas", "te", "te", "te", "te", "the", "tho", "ti", "ti", "ti", "ti", "ti", "ti", "ti", "ti", "ti", "ti", "to", "to", "to", "to", "to", "to", "to", "tou", "u", "u", "u", "um", "vaa", "ven", "vi", "ya", "zal", "ze", "zul"};
	
	// Syllable 3 terminal
	public static String[] villager_syl3Term_default = new String[]{"a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "am", "am", "am", "am", "am", "an", "an", "as", "as", "ba", "ba", "bert", "ca", "ca", "ca", "chi", "co", "da", "da", "da", "den", "dor", "dou", "el", "el", "elle", "fa", "garde", "ger", "go", "hill", "him", "id", "in", "ja", "ja", "ja", "ka", "ka", "ki", "ki", "ko", "ko", "ko", "la", "la", "la", "la", "la", "la", "la", "lah", "lah", "lat", "lene", "lin", "line", "lite", "lon", "lon", "los", "ly", "ly", "lyn", "lyn", "ma", "ma", "ma", "ma", "ma", "ma", "ma", "maa", "mad", "mad", "mad", "mad", "mad", "mad", "mad", "mar", "mat", "med", "med", "med", "med", "med", "med", "meh", "meh", "mesh", "mesh", "mesh", "meth", "mi", "min", "min", "mon", "mos", "mut", "my", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "na", "nath", "ne", "ne", "nel", "nic", "nie", "no", "no", "non", "nor", "ny", "ny", "ny", "o", "o", "on", "os", "pa", "pher", "pher", "pu", "ra", "ra", "ra", "ra", "ra", "ra", "rant", "rat", "re", "re", "rey", "rin", "roth", "roth", "sa", "sa", "sha", "sine", "son", "son", "syl", "ta", "thy", "t\u00edn", "t\u00edn", "to", "to", "trix", "us", "va", "va", "va", "va", "ver", "ver", "ver", "ver", "ver", "way", "ya", "ya", "ya", "ya", "ya", "yah", "zo"};
	// Syllable 3 transitional
	public static String[] villager_syl3Trans_default = new String[]{"-A", "-Rah", "a", "a", "a", "a", "a", "a", "a", "a", "a", "an", "an", "an", "an", "baa", "bel", "bel", "can", "car", "ci", "c\u00f3", "do", "fis", "ga", "gu", "i", "i", "i", "ic", "li", "li", "li", "li", "li", "li", "lo", "lu", "ma", "me", "na", "ni", "ni", "no", "pho", "ri", "ri", "ri", "ri", "san", "sta", "ti", "ti", "to", "tro", "u", "vi", "vi", "vi", "vi", "vi", "vi", "vi", "za"};
	
	// Syllable 4 terminal
	public static String[] villager_syl4Term_default = new String[]{"a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "\u00e1n", "\u00e1n", "be", "beth", "cia", "cia", "dar", "der", "der", "der", "der", "go", "go", "go", "go", "ja", "la", "la", "la", "la", "li", "ma", "man", "mi", "mu", "na", "na", "na", "nin", "o", "on", "phe", "ra", "rii", "sia", "ta", "tar", "te"};
	// Syllable 4 transitional
	public static String[] villager_syl4Trans_default = new String[]{"^Car", "a", "gi", "nas", "o", "te"};
	
	// Syllable 5 terminal
	public static String[] villager_syl5Term_default = new String[]{"a", "men", "ne", "ri", "sus", "tl"};
	
	
	/*
	 * Mineshaft name pieces
	 */
	
	// Prefixes and suffixes
	public static String[] mineshaft_prefix_default = new String[]{"Big", "Black", "Blue", "East", "East", "El", "El", "El", "El", "Golden", "Golden", "Great", "Green", "High", "Lake", "Las", "Little", "Long", "Low", "Lower", "Monte", "Mount", "Mount", "Mount", "New", "New", "New", "New", "North", "North", "Old", "Parc", "Qaf", "Qaf", "Red", "Roter", "San", "San", "South", "South", "St.", "St.", "Upper", "West", "West", "White"};
	public static String[] mineshaft_suffix_default = new String[]{"Bell", "Bridge", "Canyon", "Cave", "Cliff", "Cliff", "Corner", "Crag", "Creek", "Creek", "Creek", "Creek", "Dam", "Dam", "Dam", "del Diablo", "des Iles", "Down", "Down", "Earth", "Eye", "Falls", "Golden", "Grande", "Green", "Grove", "Hall", "Hill", "Hill", "Hill", "Hill", "Hills", "Jungle", "Lake", "Lake", "Lake", "Lake", "Lake", "Leg", "Loch", "Main", "Main", "Main", "Main", "Main", "Main", "Main", "Mascot", "Mia", "Mill", "Mill", "Moss", "Mountain", "Park", "Park", "Park", "Pit", "Point", "Point", "River", "River", "River", "Shaft", "Shore", "Tree", "Tunnel", "Velho"};
	
	// Single-syllable pieces
	public static String[] mineshaft_oneSylBegin_default = new String[]{"A", "Ay", "Ba", "B\u00e4", "Be", "Be", "Bea", "Boo", "Boy", "Cha", "Che", "Ch\u00f9i", "Cli", "Cli", "Co", "Co", "Cro", "Da", "Da", "Da", "Do", "Fe", "Fe", "Fi", "Flee", "Fo", "Fo", "Fro", "Froo", "Geo", "Gi", "Ha", "Hay", "Hi", "Hi", "Ho", "I", "Ka", "Kho", "La", "May", "Me", "Mi", "Na", "Neui", "Noo", "Pi", "Pi", "Prie", "Qui", "Ra", "Ra", "Ru", "Sli", "Smi", "Sna", "Sta", "Stee", "Ta", "To", "Va", "Wa", "We", "We", "Whi", "Wri", "Ye"};
	public static String[] mineshaft_oneSylEnd_default = new String[]{"c", "ce", "d", "dge", "ff", "ffs", "g", "g's", "ght", "k", "k", "ke", "l", "le", "le", "le", "lk", "ll", "ll", "ll", "lls", "lm", "lm", "m", "m", "m", "m", "mbe", "me", "n", "n", "n", "nce", "nd", "ne", "nn", "ns", "nsch", "p", "p", "r", "r", "r", "r", "rce", "rd", "rge's", "rke", "rn", "rr", "rshe", "rst", "s", "s", "ss", "st", "t", "t", "th", "th", "vd", "ves", "w", "w", "we", "wk"};
	
	// Syllable 1 transitional
	public static String[] mineshaft_syl1Trans_default = new String[]{"A", "A", "A", "A", "A", "A", "A", "A", "Ad", "Ag", "Age", "Al", "Al", "Al", "Al", "Ald", "Ape", "Ar", "Ar", "Ar", "Ast", "At", "Ba", "Bal", "Bal", "Bar", "Barn", "Bat", "Bath", "Baus", "Bean", "Bed", "Bed", "Ber", "Bes", "Bi", "Bick", "Bils", "Bir", "Birch", "Black", "Blue", "Bo", "Boch", "Boul", "Brad", "Bren", "Bridge", "Brins", "Bro", "Brods", "Bronze", "Brook", "Brough", "Brun", "Bruns", "Bu", "Buck", "Buck", "Bul", "Bul", "Bull", "Bur", "Bur", "Bur", "Bur", "Bur", "Bwll", "Ca", "Ca", "Ca", "Cal", "Cam", "Can", "Can", "Car", "Ce", "Cen", "Chal", "Chan", "Chat", "Chi", "Chif", "Ci", "Cli", "Clif", "Clif", "Clip", "Clo", "Co", "Co", "Con", "Cool", "Cop", "Cop", "Cop", "Cor", "Cor", "Cran", "Creigh", "Cris", "Cyn", "Dal", "Dan", "Dar", "Den", "Di", "Dis", "Do", "Dol", "Dom", "Drum", "Dvoi", "E", "Eck", "El", "El", "Em", "Es", "Fa", "Fan", "Fern", "Fish", "Flet", "Frank", "Ga", "Gal", "Gar", "Ger", "Get", "Gi", "Gi", "Gib", "Gid", "Gla", "Glei", "Gol", "Goon", "Gras", "Green", "Green", "Gres", "Gui", "Gun", "Gwa", "Ha", "Hal", "Har", "Har", "Har", "Hat", "Hed", "Hen", "Hen", "Her", "Her", "Hi", "Hig", "Hil", "Hill", "Hill", "Home", "Home", "Horn", "Hun", "Husk", "I", "I", "I", "I", "Ja", "Jo", "John", "Jua", "Jun", "Ka", "Ka", "Ka", "Kal", "Kan", "Kath", "Kee", "Kel", "Ken", "Kent", "Kers", "Ki", "Ki", "Kii", "Kil", "Kiln", "Kin", "Kings", "Kit", "Klo", "Ko", "Kof", "Kongs", "Kras", "Krem", "Ku", "Ku", "La", "Lac", "Law", "Law", "Lax", "Leck", "Les", "Li", "Lin", "Lit", "Lo", "L\u00f8k", "Long", "Lu", "Lu", "Lu", "Ly", "Ma", "Ma", "Ma", "Ma", "Malt", "Man", "Mar", "Mar", "Mar", "Mark", "Math", "McAr", "McAr", "McCau", "McIn", "M\u00e9", "Mi", "Mi", "Mil", "Mitch", "Mo", "Mo", "Mor", "Mor", "Mos", "Mos", "Mu", "Mul", "Mur", "Na", "Na", "Na", "Nant", "Ne", "New", "None", "Nor", "Norse", "North", "Nun", "O", "O", "O", "O", "O", "Oak", "Oak", "Or", "Out", "P", "Pa", "Pa", "Pa", "Pa", "Pad", "Par", "Park", "Paul", "Pe", "Pen", "Pen", "Pen", "Per", "P\u00ebr", "Phoe", "Pleas", "Plu", "Pre", "Prim", "Pro", "Pron", "Pu", "Py", "Quin", "Rab", "Rag", "Ram", "Ray", "Re", "Re", "Ridge", "Ris", "Rol", "Round", "Sa", "Sal", "Sand", "Schuy", "Se", "Sea", "Sem", "Ser", "Sha", "Shan", "Shire", "Shut", "Sil", "Sis", "Smug", "Snae", "So", "Sou", "Spa", "Spring", "Stan", "Stan", "Stave", "Steet", "Ster", "Sti", "Strat", "Strong", "Stub", "Sul", "Sum", "Sun", "Sun", "Sun", "Sut", "Ta", "Ta", "Tan", "Tas", "Tau", "Tel", "Ten", "Thas", "Thek", "Tho", "Thur", "Tin", "Tins", "Tow", "Tread", "Tu", "Tu", "Tuf", "Tyldes", "Tyne", "U", "U", "Vic", "Vlah", "Wa", "Wales", "War", "Wat", "West", "Wi", "Wi", "Wie", "Wil", "Wil", "Wil", "Wind", "Wind", "Wind", "Wit", "Wors", "Wu", "Wy", "York", "You", "Yu", "Zas", "Zink", "Zo", "Zoll"};
	
	// Syllable 2 terminal
	public static String[] mineshaft_syl2Term_default = new String[]{"all", "ant", "ant", "ar", "banks", "berg", "berg", "berg", "bin", "bird", "bird", "bit", "burgh", "by", "by", "ca", "chell", "cher", "co", "con", "crest", "croft", "croft", "croft", "cy", "cy", "da", "dale", "dale", "dale", "dall", "dan", "dee", "den", "dic", "din", "don", "dred", "dril", "dy", "ell", "ells", "er", "er", "er", "ey", "fa", "feau", "fell", "fer", "field", "field", "field", "field", "ford", "ford", "ford", "gaj", "gan", "gans", "gar", "gart", "gee", "gie", "gler", "go", "greave", "greaves", "grove", "gus", "gyle", "ham", "hill", "hole", "houn", "house", "hurst", "i", "ie", "ing", "itt", "jek", "ken", "ken", "kiep", "la", "lan", "land", "land", "lands", "lar", "las", "leen", "leigh", "lein", "ler", "lers", "les", "ley", "ley", "ley", "ley", "ley", "ley", "ley", "ley", "ley", "ley", "ley", "ley", "ley", "liam", "lin", "lin", "lin", "ling", "ling", "ling", "llie", "l\u00f3n", "lot", "lou", "lun", "main", "man", "man", "man", "man", "max", "mer", "met", "mier", "mond", "moy", "my", "na", "na", "nar", "nel", "ner", "new", "nish", "nix", "nor", "ny", "o", "oaks", "os", "per", "per", "phin", "pin", "pol", "p\u00fan", "ra", "ra", "ra", "ray", "ray", "ren", "rine", "ris", "rise", "ro", "rock", "rock", "ron", "root", "rose", "rows", "sa", "s\u00e9", "sens", "sham", "sheen", "shi", "shine", "shire", "side", "side", "sion", "son", "son", "son", "sor", "sor", "sor", "stake", "stake", "stone", "such", "ta", "tar", "ter", "ters", "tez", "thorpe", "thur", "thur", "tle", "tle", "to", "to", "ton", "ton", "ton", "ton", "ton", "ton", "ton", "ton", "ton", "ton", "ton", "tree", "ty", "tyn", "tyre", "vel", "ver", "vers", "warke", "was", "wash", "way", "well", "wen", "wick", "wick", "wing", "wood", "wood", "wood", "wood", "wood", "worth", "worth", "y", "zels", "zus"};
	// Syllable 2 transitional
	public static String[] mineshaft_syl2Trans_default = new String[]{"^To", "a", "amp", "an", "an", "av", "ber", "ber", "ber", "ber", "ber", "bi", "bi", "bil", "bu", "bu", "bu", "chi", "cov", "cu", "da", "da", "da", "der", "di", "ding", "dle", "dle", "do", "du", "dy", "er", "fie", "fn", "ga", "gar", "gins", "go", "goor", "gru", "ha", "h\u00e4", "hei", "i", "i", "i", "i", "i", "ing", "ka", "ke", "ker", "ku", "la", "la", "la", "la", "laan", "lau", "len", "li", "li", "li", "li", "licz", "ling", "ling", "lo", "lu", "lu", "lym", "ma", "ma", "ma", "ma", "mels", "mer", "mer", "mfu", "mi", "mis", "mit", "na", "na", "nall", "nas", "ne", "ne", "ner", "ni", "ni", "ni", "ni", "ni", "ning", "no", "no", "ny", "o", "ol", "per", "pu", "pu", "qi", "ra", "ra", "ra", "ra", "ran", "ren", "res", "ri", "ro", "roi", "ru", "sa", "se", "se", "se", "so", "sol", "son", "ta", "tar", "te", "te", "ten", "ter", "ti", "tle", "to", "ton", "ton", "tre", "va", "ve", "ven", "ven", "ver", "ver", "wa", "wa", "yad", "yan", "yel", "yo", "yu"};
	
	// Syllable 3 terminal
	public static String[] mineshaft_syl3Term_default = new String[]{"-Uul", "a", "a", "a", "age", "bach", "bah", "ban", "berg", "bou", "by", "ca", "ca", "car", "carn", "chael", "chee", "deen", "den", "die", "do", "dor", "dre", "dy", "ein", "fields", "ger", "i", "ic", "ka", "ken", "ko", "la", "l\u00e4", "laj", "las", "les", "ley", "ley", "ley", "lie", "mi", "mi", "na", "na", "na", "nant", "naut", "nda", "ndwa", "net", "ni", "nke", "no", "no", "\u00f1o", "on", "pic", "pin", "ra", "ren", "rw", "ry", "set", "shaw", "shaw", "shi", "shore", "son", "son", "son", "ta", "tah", "te", "ter", "ti", "tian", "to", "ton", "ton", "ton", "ton", "ton", "ton", "ton", "ton", "try", "ture", "van", "van", "ville", "ville", "wa", "wa", "wood", "wood", "y", "ye", "z\u00eb"};	
	// Syllable 3 transitional
	public static String[] mineshaft_syl3Trans_default = new String[]{"^Bat", "^Bur", "^Co", "^Tol", "a", "a", "ar", "bas", "bu", "co", "con", "cy", "en", "er", "ey", "fon", "hu", "i", "i", "ku", "ku", "lu", "lu", "lu", "ma", "ma", "mi", "mu", "na", "na", "ni", "ni", "ni", "per", "quar", "que", "ra", "ra", "ri", "ri", "ro", "sa", "sal", "sen", "so", "ver", "wa", "was"};
	
	// Syllable 4 terminal
	public static String[] mineshaft_syl4Term_default = new String[]{"a", "a", "a", "ca", "can", "da", "do", "do", "ed", "gan", "goi", "gwm", "ka", "ka", "ka", "lu", "mi", "ndi", "non", "ra", "ra", "rës", "ry", "ry", "ry", "ry", "sal", "shi", "shi", "si", "ta", "ta", "te", "tein", "thal", "thi", "tine", "tion", "t\u00fan", "wr", "y"};
	// Syllable 4 transitional
	public static String[] mineshaft_syl4Trans_default = new String[]{"chen", "da", "hu", "i", "le", "na", "vaa"};
	
	// Syllable 5 terminal
	public static String[] mineshaft_syl5Term_default = new String[]{"e", "ra", "ra", "ska", "ted", "thu", "ya"};	
	
	
	/*
	 * Stronghold name pieces
	 */

	// Prefixes and suffixes
	public static String[] stronghold_prefix_default = new String[]{"A", "Devils", "Great", "Great", "Per", "Santa"};
	public static String[] stronghold_suffix_default = new String[]{"Box", "Castle", "Citadel", "Citadel", "Eredo", "Fortress", "Fortress", "Fortress", "Hill", "Shrine", "Subterrane", "Zuu"};

	// Single-syllable pieces
	public static String[] stronghold_oneSylBegin_default = new String[]{"Gri", "Hei", "Ja", "Kli", "Me", "Sto", "Wa", "Yo"};
	public static String[] stronghold_oneSylEnd_default = new String[]{"de", "m", "n", "n", "rk", "s", "t", "tz"};

	// Syllable 1 transitional
	public static String[] stronghold_syl1Trans_default = new String[]{"A", "A", "\u00c1", "Af", "Al", "Al", "Am", "An", "At", "Au", "Ba", "Ba", "Bag", "Bar", "Bar", "Bat", "Bei", "Bir", "Bou", "Brim", "Cai", "Cha", "Cha", "Ches", "Con", "Con", "Cor", "Cor", "Cot", "Czech", "Da", "Der", "Di", "Du", "Du", "El", "Er", "Fa", "Fa", "Flo", "Flod", "Ger", "G\u00f6", "Gor", "Gus", "Ha", "He", "Hex", "Hil", "Hin", "Hu", "In", "Jang", "Je", "Je", "Je", "Jus", "Ka", "Ke", "Ko", "Krem", "Kum", "Lon", "Lu", "Lu", "M", "Ma", "Man", "Mar", "Me", "Mes", "Mie", "Mo", "Mont", "New", "Ni", "Ni", "Of", "Pacz", "Pan", "Pan", "Pet", "Ports", "Qry", "Quang", "Ra", "Rha", "Ru", "Sac", "Sax", "Se", "Sen", "Ser", "Ser", "Si", "Sieg", "Sta", "Su", "Sung", "Svea", "Tlax", "Tor", "Tra", "Tri", "Val", "Vic", "Vis", "Xi"};

	// Syllable 2 terminal
	public static String[] stronghold_syl2Term_default = new String[]{"'an", "^Lev", "^Ng\u00e3i", "als", "ax", "bin", "bo", "borg", "by", "co", "den", "don", "fa", "fried", "gan", "go", "gu", "gush", "jan", "jing", "ka", "k\u00f3w", "lin", "lin", "mouth", "nar", "no", "on", "os", "pel", "pent", "pine", "ro", "ry", "sea", "sel", "seong", "stone", "sus", "tav", "ter", "tor", "vas", "wy"};
	// Syllable 2 transitional
	public static String[] stronghold_syl2Trans_default = new String[]{"a", "ba", "bhal", "brov", "brov", "by", "ca", "cas", "ce", "co", "de", "de", "den", "di", "do", "dri", "dzyr", "e", "e", "ghe", "gi", "gle", "la", "lan", "le", "let", "lo", "ma", "ma", "man", "mo", "na", "ne", "ner", "ni", "ni", "o", "o", "ra", "rak", "re", "res", "ri", "ri", "ri", "ro", "ru", "say", "se", "si", "stan", "ster", "ta", "tax", "ter", "tin", "to", "to", "to", "tra", "ve", "vi", "vi", "yar"};

	// Syllable 3 terminal
	public static String[] stronghold_syl3Term_default = new String[]{"a", "a", "an", "an", "as", "bla", "burg", "cae", "cho", "dam", "garh", "gur", "heim", "kot", "la", "la", "lon", "mon", "na", "na", "ne", "nik", "nik", "nin", "not", "ra", "rus", "sa", "sia", "ta", "tia", "tis", "tle", "tov", "tus", "y", "zecz"};	
	// Syllable 3 transitional
	public static String[] stronghold_syl3Trans_default = new String[]{"^Ve", "a", "ba", "di", "gus", "hu", "i", "i", "i", "li", "li", "lo", "men", "mil", "mu", "ne", "ri", "ri", "sa", "si", "slo", "sta", "su", "ti", "va", "vir", "vir"};

	// Syllable 4 terminal
	public static String[] stronghold_syl4Term_default = new String[]{"a", "a", "an", "an", "cus", "dras", "ke", "ke", "k\u0131r", "lem", "lion", "na", "na", "no", "on", "ra", "ri", "ros", "sia", "ta", "ta", "t\u00e1", "vak"};
	// Syllable 4 transitional
	public static String[] stronghold_syl4Trans_default = new String[]{"a", "lin", "no", "ra"};

	// Syllable 5 terminal
	public static String[] stronghold_syl5Term_default = new String[]{"din", "m\u00e1n", "na", "ple"};	
	
	
	
	/*
	 * Temple name pieces
	 */

	// Prefixes and suffixes
	public static String[] temple_prefix_default = new String[]{"Abu", "Abu", "Beit", "Divus", "Jebel", "Jebel", "Mater", "Saint", "San", "San", "San", "Santa", "Santa", "Santo"};
	public static String[] temple_suffix_default = new String[]{"Hill", "Magna", "Qim"};

	// Single-syllable pieces
	public static String[] temple_oneSylBegin_default = new String[]{"A", "Ba", "Baa", "Bor", "Cae", "Cou", "Da", "De", "Do", "F\u00e2", "Ge", "Ma", "Mu", "Pea", "Ra", "Si", "Spe", "U", "Vi", "Zeu"};
	public static String[] temple_oneSylEnd_default = new String[]{"c", "ce", "g", "gh", "l", "l", "lg", "n", "r", "rf", "rr", "rs", "rx", "s", "s", "s", "t", "x"};

	// Syllable 1 transitional
	public static String[] temple_syl1Trans_default = new String[]{"A", "A", "A", "A", "A", "A", "A", "A", "A", "Al", "Alt", "An", "An", "An", "Aph", "Ar", "Ar", "As", "At", "At", "Au", "Au", "Ba", "Ba", "Baal", "Bac", "Bar", "Bar", "Bel", "B\u00f3", "Bor", "Bug", "Car", "Car", "Cas", "Cen", "Ch\00e2te", "Cig", "Clau", "Cle", "Con", "Con", "Con", "Cor", "C\u00f3r", "Cu", "Dak", "De", "De", "Den", "Di", "Di", "Di", "Di", "Do", "Do", "Do", "Do", "E", "E", "E", "E", "E", "E", "Ech", "Er", "Es", "Esh", "Faus", "Fer", "Fi", "Fle", "G", "Ga", "Ge", "Gha", "Giu", "G\u00f6r", "Gran", "Grien", "Ha", "Ha", "Ha", "Ha", "Ha", "Hag", "He", "He", "He", "He", "He", "Heil", "Heph", "Her", "Her", "Hi", "I", "I", "Ja", "Je", "Jor", "Ju", "Ju", "Ju", "Ka", "Kar", "Kha", "Koen", "Kor", "Kur", "La", "Le", "Lep", "Lep", "Li", "Lon", "Lu", "Lux", "Ly", "Lyd", "M", "Ma", "Ma", "Ma", "Ma", "Mack", "Mart", "Men", "Mer", "Mer", "Met", "Mi", "Mi", "Mont", "Mou", "Mu", "Nep", "Nep", "Ni", "Nym", "O", "O", "O", "Pa", "Pa", "Pan", "Pan", "Par", "Pe", "Per", "Phi", "Po", "Po", "Pol", "Por", "Pri", "Pris", "Pu", "Qa", "Qa", "Qer", "Rad", "Re", "Ro", "Ro", "Ro", "Rom", "Ros", "S", "Sa", "Sa", "Saf", "San", "Sar", "Sch\u00fcn", "Se", "Se", "Se", "Se", "Se", "Se", "Sem", "Sha", "Si", "Si", "Sim", "Skor", "So", "So", "Sy", "Sym", "Ta", "Ta", "Ta", "Taf", "Tan", "Tar", "Tau", "Te", "Te", "Tet", "Thu", "Tim", "To", "Ton", "Tra", "Tr\u00e9", "Tu", "Ur", "Var", "Ve", "Ve", "V\u00e9", "Vei", "Ver", "Ves", "Ves", "Ves", "Vin", "Wa", "Wa", "War", "West", "Xan", "Xem", "Y", "Zag", "Zan"};

	// Syllable 2 terminal
	public static String[] temple_syl2Term_default = new String[]{"ba", "bek", "bel", "berg", "bii", "bil", "blains", "bod", "b\u00fchl", "ca", "calfe", "chus", "cole", "cord", "cure", "dan", "di", "di", "din", "don", "don", "dun", "feh", "fra", "fu", "gag", "gar", "gin", "gos", "grat", "ha", "jan", "ka", "kal", "la", "latte", "leb", "li", "lux", "ma", "mae", "matt", "min", "mos", "moun", "mun", "na", "na", "na", "nak", "neb", "neh", "net", "ney", "no", "non", "noum", "nus", "nus", "nus", "or", "ra", "r\u00e9e", "res", "ri", "ron", "sha", "sins", "sis", "sone", "sr", "ta", "tard", "tas", "ten", "tis", "tis", "to", "tor", "tra", "tune", "turn", "wa", "wern", "xay", "zon", "zor"};
	// Syllable 2 transitional
	public static String[] temple_syl2Trans_default = new String[]{"a", "a", "a", "bach", "bas", "beit", "ber", "ber", "bur", "c\u00e1n", "ci", "cle", "cord", "cos", "cra", "cro", "cu", "cu", "dé", "di", "di", "di", "do", "dri", "dri", "e", "gan", "ge", "ge", "ger", "ges", "gi", "gou", "gus", "gus", "har", "hou", "i", "ib", "ig", "ig", "im", "jus", "ka", "la", "lab", "lai", "les", "li", "li", "li", "lin", "lip", "lo", "ma", "me", "me", "men", "mer", "mi", "mi", "mo", "mo", "mo", "nain", "naj", "ne", "ne", "ner", "ni", "n\u00f3", "nog", "nus", "o", "o", "o", "o", "o", "pas", "pas", "pe", "pha", "pha", "phae", "phe", "pho", "phy", "pi", "pil", "pol", "qa", "ra", "ra", "ra", "ra", "rai", "ran", "raw", "re", "rech", "res", "ri", "ri", "ro", "ro", "r\u00f3", "roc", "ru", "saw", "sei", "si", "sip", "sy", "tas", "te", "te", "te", "the", "the", "the", "ti", "to", "t\u00f3", "ton", "tor", "tra", "tro", "tu", "tu", "u", "ud", "ug", "un", "ve", "ve", "vi", "wil", "xi", "xi", "\u017ea", "zer"};

	// Syllable 3 terminal
	public static String[] temple_syl3Term_default = new String[]{"a", "a", "a", "an", "an", "ba", "ba", "bas", "bo", "bourg", "burg", "burgh", "cles", "cuse", "da", "da", "do", "don", "dra", "en", "\u00e0re", "ers", "ga", "ga", "i", "ia", "ib", "ja", "ja", "la", "lat", "ler", "lin", "lo", "lus", "mes", "mis", "mon", "na", "na", "na", "na", "na", "na", "nec", "non", "nore", "nuc", "num", "nus", "o", "ol", "on", "on", "pa", "pos", "ra", "ra", "re", "ren", "ret", "ry", "sha", "si", "sus", "ta", "ta", "ta", "tal", "te", "ter", "ter", "tin", "to", "tum", "tus", "um", "us", "us", "va", "ville", "vis", "zel"};
	// Syllable 3 transitional
	public static String[] temple_syl3Trans_default = new String[]{"a", "bo", "bri", "bri", "de", "de", "di", "e", "e", "es", "es", "gal", "ge", "i", "i", "i", "i", "i", "la", "li", "me", "nar", "nen", "ni", "ni", "ny", "o", "pe", "pei", "pi", "po", "po", "por", "py", "ra", "ri", "ri", "sa", "scu", "scu", "shof", "so", "ta", "ta", "tei", "ter", "the", "ti", "ti", "ti", "to", "to", "u", "u", "un", "us", "wa", "zi"};

	// Syllable 4 terminal
	public static String[] temple_syl4Term_default = new String[]{"a", "a", "an", "an", "as", "ca", "do", "en", "er", "fen", "ga", "ga", "ja", "kos", "le", "lem", "li", "lis", "lis", "lon", "mak", "na", "ne", "ni", "ni", "nia", "no", "nos", "nus", "o", "on", "on", "qua", "ra", "ra", "ra", "rat", "re", "re", "ri", "ri", "sa", "sos", "te", "te", "tus", "tus", "um", "um", "us", "us", "zu"};
	// Syllable 4 transitional
	public static String[] temple_syl4Trans_default = new String[]{"a", "i", "nan", "ne", "stei", "ti"};

	// Syllable 5 terminal
	public static String[] temple_syl5Term_default = new String[]{"cus", "ki", "ne", "nus", "on", "um"};
	
	
	
	/*
	 * Fortress name pieces
	 */
	
	// Prefixes and suffixes
	public static String[] fortress_prefix_default = new String[]{"Nether"};
	public static String[] fortress_suffix_default = new String[]{"Fortress"};
	
	// Single-syllable pieces
	public static String[] fortress_oneSylBegin_default = new String[]{"He", "Sty"};
	public static String[] fortress_oneSylEnd_default = new String[]{"ll", "x"};
	
	// Syllable 1 transitional
	public static String[] fortress_syl1Trans_default = new String[]{"A", "A", "A", "Ach", "Ach", "Aea", "As", "Cad", "Cae", "Cen", "Cer", "Char", "Chi", "Co", "Di", "Di", "E", "E", "Eu", "Fey", "Gor", "Ha", "Har", "Hec", "Hy", "Hyp", "Ir", "La", "Le", "Ler", "Me", "Me", "Mi", "Mict", "Mus", "Or", "Pa", "Pan", "Pe", "Per", "Phae", "Phle", "Plu", "Pro", "Rhad", "Soc", "Tar", "Than", "Ti", "Ti", "Xi"};
	
	// Syllable 2 terminal
	public static String[] fortress_syl2Term_default = new String[]{"cris", "cus", "des", "do", "dra", "dra", "gon", "lan", "lys", "march", "mus", "na", "nos", "nos", "on", "on", "py", "taur", "the", "to", "yu"};
	// Syllable 2 transitional
	public static String[] fortress_syl2Trans_default = new String[]{"a", "a", "a", "bal", "ber", "chil", "cy", "d\u00e6", "er", "gae", "gar", "ge", "kal", "le", "lec", "li", "me", "ne", "o", "pel", "phe", "pho", "ra", "ri", "ry", "se", "siph", "siph", "tar", "vad"};
	
	// Syllable 3 terminal
	public static String[] fortress_syl3Term_default = new String[]{"ba", "del", "heim", "la", "les", "ne", "on", "ra", "ra", "te", "tes", "tha", "thon", "to", "tos", "tus", "us", "us", "us", "us", "us"};
	// Syllable 3 transitional
	public static String[] fortress_syl3Trans_default = new String[]{"a", "da", "man", "mo", "no", "no", "o", "pho", "phy"};
	
	// Syllable 4 terminal
	public static String[] fortress_syl4Term_default = new String[]{"\u00eb", "\u00eb", "le", "mos", "ne", "ne", "thus"};
	// Syllable 4 transitional
	public static String[] fortress_syl4Trans_default = new String[]{"mi", "ni"};
	
	// Syllable 5 terminal
	public static String[] fortress_syl5Term_default = new String[]{"a", "um"};
	
	
	
	
	/*
	 * Monument name pieces
	 * Version 1.8 on
	 */
	
	// Prefixes and suffixes
	public static String[] monument_prefix_default = new String[]{"Dread", "Lost", "Odd", "Sunken"};
	public static String[] monument_suffix_default = new String[]{"Heart", "Mausoleum", "Tomb", "Tongue"};

	// Single-syllable pieces
	public static String[] monument_oneSylBegin_default = new String[]{"Lo", "Ma", "Y", "Ya"};
	public static String[] monument_oneSylEnd_default = new String[]{"m", "s", "s", "t"};

	// Syllable 1 transitional
	public static String[] monument_syl1Trans_default = new String[]{"A", "A", "A", "Al", "An", "Ar", "At", "At", "Bai", "Bi", "Bou", "Ca", "Can", "Can", "Car", "Co", "Cu", "Di", "Dun", "Dwar", "Ei", "Ga", "Ge", "Ge", "Hamp", "He", "He", "He", "Kham", "Ki", "L", "Le", "Lu", "Ly", "Ma", "Mu", "Mu", "Na", "Na", "O", "O", "Pav", "Pha", "Phe", "Point", "Port", "Pu", "Quian", "R'l", "Ra", "Ra", "Ram", "Rha", "Rung", "Sa", "Sa", "Saef", "Sam", "Shi", "Su", "Ta", "Tar", "Tho", "Ti", "Tur", "Yo"};

	// Syllable 2 terminal
	public static String[] monument_syl2Term_default = new String[]{"ae", "an", "ba", "baj", "bhat", "cheng", "dao", "dum", "holt", "ia", "ka", "ka", "lit", "nah", "nis", "ra", "tezh", "til", "til", "ton", "wich", "yeh"};
	// Syllable 2 transitional
	public static String[] monument_syl2Trans_default = new String[]{"^Ne", "^Ro", "bel", "but", "cho", "co", "de", "de", "ex", "gan", "gar", "gi", "ha", "hur", "ke", "la", "lae", "lan", "li", "li", "li", "li", "lo", "lo", "lys", "mi", "mu", "na", "na", "no", "o", "pi", "pol", "ra", "ra", "ran", "ro", "tes", "ti", "ti", "ting", "tre", "ven", "vens"};

	// Syllable 3 terminal
	public static String[] monument_syl3Term_default = new String[]{"a", "he", "ke", "ki", "lee", "nan", "nesse", "ni", "purn", "pus", "ro", "ser", "sos", "tis", "tis", "us", "us", "yal"};
	// Syllable 3 transitional
	public static String[] monument_syl3Trans_default = new String[]{"'r", "^Ba", "^He", "^Pe", "^Ro", "^Si", "^U", "an", "ba", "ca", "cle", "clei", "e", "fa", "fa", "gor", "gu", "je", "ju", "ni", "os", "pe", "rho", "ri", "ta", "tho"};

	// Syllable 4 terminal
	public static String[] monument_syl4Term_default = new String[]{"a", "a", "cac", "dos", "har", "lig", "ni", "ni", "on", "on", "stad", "tri", "wi"};
	// Syllable 4 transitional
	public static String[] monument_syl4Trans_default = new String[]{"^An", "^Gwae", "a", "dri", "i", "i", "li", "lu", "na", "ni", "nu", "nu", "ran"};

	// Syllable 5 terminal
	public static String[] monument_syl5Term_default = new String[]{"a", "a", "a", "a", "cak", "lod", "no", "on", "os"};
	// Syllable 5 transitional
	public static String[] monument_syl5Trans_default = new String[]{"dre", "kon", "pu", "wa"};

	// Syllable 6 terminal
	public static String[] monument_syl6Term_default = new String[]{"as", "da", "ram", "tu"};
	
	
	
	
	/*
	 * EndCity name pieces
	 * Version 1.9 on
	 */
	
	// Prefixes and suffixes
	public static String[] endcity_prefix_default = new String[]{"End", "Mag", "T\u00f3r na"};
	public static String[] endcity_suffix_default = new String[]{"City"};
	
	// Single-syllable pieces
	public static String[] endcity_oneSylBegin_default = new String[]{"I", "Ma", "Me", "Moo", "N\u00f3"};
	public static String[] endcity_oneSylEnd_default = new String[]{"b", "g", "ll", "n", "rs"};
	
	// Syllable 1 transitional
	public static String[] endcity_syl1Trans_default = new String[]{"A", "A", "Ann", "As", "Bar", "Brah", "Cal", "Car", "Ce", "Ce", "Cha", "Di", "Dy", "E", "E", "E", "E", "En", "Eu", "Eu", "Ga", "Gai", "Hau", "Heav", "Hla", "I", "I", "Il", "In", "Ju", "Ka", "Kol", "Lu", "Ma", "Ma", "Mer", "Mi", "Mi", "Nep", "Ni", "O", "Pan", "Plu", "Pur", "Rhe", "Sar", "Sat", "Se", "Sum", "Te", "Ter", "Ti", "Tla", "Tri", "U", "Ul", "Ul", "Um", "Val", "Ve", "Yh", "Yo"};
	
	// Syllable 2 terminal
	public static String[] endcity_syl2Term_default = new String[]{"a", "a", "dath", "den", "en", "gard", "lar", "mas", "mi", "na", "nath", "nith", "nus", "o", "ob", "ra", "res", "ris", "ron", "thar", "thys", "till", "to", "ton", "tune", "urn", "wn", "zakh"};
	// Syllable 2 transitional
	public static String[] endcity_syl2Trans_default = new String[]{"a", "ar", "ber", "bi", "bri", "ce", "cha", "co", "cu", "de", "ga", "ga", "hal", "ke", "lath", "le", "le", "lis", "lo", "ly", "ly", "ma", "me", "mer", "ny", "o", "pi", "ra", "ran", "re", "ri", "ro", "ta", "ti"};
	
	// Syllable 3 terminal
	public static String[] endcity_syl3Term_default = new String[]{"-Leen", "a", "can", "da", "el", "el", "ka", "la", "land", "mede", "ne", "ne", "nek", "nok", "non", "nus", "on", "pa", "ru", "ry", "sa", "ter", "to", "tron"};
	// Syllable 3 transitional
	public static String[] endcity_syl3Trans_default = new String[]{"la", "ma", "me", "mo", "ni", "pe", "pha", "pu", "si", "to"};
	
	// Syllable 4 terminal
	public static String[] endcity_syl4Term_default = new String[]{"a", "cia", "dus", "\u00efs", "ke", "ra", "ry", "tus", "um"};
	// Syllable 4 transitional
	public static String[] endcity_syl4Trans_default = new String[]{"ni"};
	
	// Syllable 5 terminal
	public static String[] endcity_syl5Term_default = new String[]{"um"};
	
	
	
	
	/*
	 * Mansion name pieces
	 * Version 1.11 on
	 */
	
	// Prefixes and suffixes
	public static String[] mansion_prefix_default = new String[]{"East", "Flower of", "Golden", "Grey", "Life of", "North", "Silver", "South", "The", "The", "The", "The", "The", "The", "The", "The", "West", "White"};
	public static String[] mansion_suffix_default = new String[]{"Air", "Brook", "Castle", "Castle", "Castle", "Castle", "Castle", "Champ", "Chateau", "Ch\u00e2teau", "Court", "Court", "Creek", "Farms", "Ferry", "Grove", "Hacienda", "Hall", "Hall", "Hall", "Hall", "Hall", "Hall", "Hall", "Hall", "Hill", "Hill", "Hill", "Hill", "Hill", "House", "House", "House", "House", "House", "House", "House", "House", "House", "House", "House", "House", "House", "House", "House", "Lawn", "Lodge", "Lodge", "Manor", "Manor", "Manor", "Manor", "Mansion", "Mansion", "Mansion", "Mansion", "Mansion", "Mansion", "Mansion", "Mansion", "Mansion", "Mansion", "Mansion", "Mansion", "Meadows", "Oaks", "Palace", "Park", "Pond", "Ranch", "Ridge", "Royal", "Terrace", "Terrace", "Villa", "Villa", "Way"};

	// Single-syllable pieces
	public static String[] mansion_oneSylBegin_default = new String[]{"Be", "Be", "Bo", "Brae", "Bray", "Cha", "Coe", "Da", "Du", "E", "Fleu", "Fri", "Hay", "Hea", "Hi", "Hy", "Ka", "Ly", "McCu", "Mi", "O", "Pa", "Pay", "R\u00ea", "Rei", "Ro", "Sea", "Wa", "Zi"};
	public static String[] mansion_oneSylEnd_default = new String[]{"ck", "d", "de", "es", "ff", "hn", "ke", "l", "ldt", "les", "ll", "lle", "lls", "lm", "ne", "ne", "r", "rls", "rs", "rst", "s", "s", "se", "se", "ve", "wn", "z"};

	// Syllable 1 transitional
	public static String[] mansion_syl1Trans_default = new String[]{"'I", "Al", "An", "An", "Ar", "Ar", "Ard", "Ash", "Bel", "Bel", "Bilt", "Blairs", "Brea", "Ca", "Car", "Cas", "Col", "Cor", "Dar", "Dum", "El", "Esch", "Fair", "Fi", "Flor", "Gal", "Ge", "Glen", "Grey", "Ha", "Har", "Hemp", "Hob", "Hope", "Hun", "Hy", "I", "In", "In", "Ky", "Lynne", "Mar", "Mar", "Mea", "Mir", "Mont", "Ne", "O", "O", "Ow", "Pa", "Pa", "Pa", "Pal", "Pem", "Pens", "Pop", "Pres", "Ral", "Re", "Ri", "Ryn", "Se", "Se", "Sea", "Sha", "Sha", "Shel", "Shin", "Ti", "To", "Town", "Twi", "Un", "Ver", "Vit", "War", "White", "White", "Win", "Wing", "Wing", "Wood", "Xan", "Yad"};

	// Syllable 2 terminal
	public static String[] mansion_syl2Term_default = new String[]{"ble", "bor", "bor", "broke", "burg", "burne", "caim", "ces", "chre", "den", "den", "der", "ding", "do", "dow", "dow", "field", "hall", "ham", "kers", "kuit", "la", "lais", "lais", "land", "lar", "lea", "light", "man", "marsh", "mont", "more", "more", "mours", "ra", "sailles", "send", "ser", "stead", "ston", "stone", "stowe", "tle", "ton", "view", "well", "wers", "wet", "wood", "wood", "wood"};
	// Syllable 2 transitional
	public static String[] mansion_syl2Trans_default = new String[]{"-a", "a", "a", "a", "bar", "der", "di", "dle", "dow", "er", "ge", "he", "is", "la", "lin", "ling", "lo", "met", "mi", "name", "ne", "ne", "o", "ren", "ro", "ros", "ter", "ting", "tor", "ve", "v\u00e9", "ver", "ver", "vo"};

	// Syllable 3 terminal
	public static String[] mansion_syl3Term_default = new String[]{"^Brook", "^Hour", "a", "d\u00e8re", "du", "gel", "gher", "gie", "ka", "lands", "li", "li", "lo", "ni", "o", "rie", "san", "side", "sky", "sol", "son", "thur", "to", "ton", "ton", "ton", "wood"};
	// Syllable 3 transitional
	public static String[] mansion_syl3Trans_default = new String[]{"-La", "a", "an", "fa", "i", "i", "la"};

	// Syllable 4 terminal
	public static String[] mansion_syl4Term_default = new String[]{"o"};
	// Syllable 4 transitional
	public static String[] mansion_syl4Trans_default = new String[]{"^Neck", "ble", "da", "go", "ni", "ty"};

	// Syllable 5 terminal
	public static String[] mansion_syl5Term_default = new String[]{"sa"};
	
	
	
}
