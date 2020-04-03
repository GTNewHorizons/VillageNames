package astrotibs.villagenames.name;

public class NamePieces {
	
	// Characters that are replaced:
	// § with \u00a7
	// Á with \u00c1
	// É with \u00c9
	// æ with \u00e6
	// à with \u00e0
	// á with \u00e1
	// ã with \u00e3
	// å with \u00e5
	// ä with \u00e4
	// É with \u00C9
	// è with \u00e8
	// é with \u00e9
	// ë with \u00eb
	// I with dot \u0130
	// í with \u00ed
	// ï with \u00ef
	// ñ with \u00f1
	// ò with \u00f2
	// ó with \u00f3
	// Ö with \u00d6
	// ö with \u00f6
	// ù with \u00f9
	// ú with \u00fa
	// ü with \u00fc
	// û with \u00fb
	// ÿ with \u00ff
	// ž with \u017e
	
	//Characters that can't be properly displayed:
	// A with tilde: \u00c3
	// ð with \u00f0
	// a with macron: \u0101
	// h with caron: \u030c
	// s caron with \u0161
	// s grave with \u017e
	// s comma with \u0219
	// a breve with \u0103
	// g with dot \u0121
	// G with dot \u0120
	// H with stroke \u0126
	// o with two acutes \u0151
	// h with stroke \u0128
	
	
	/*
	 * Village
	 */
	public static final String village_prefix_default =
			"Bandar, Bandar, East, El, Flying, Fort, Half, King, La, Le, Monte, Mount, New, New, North, Old, Port, Port, Port, Port, Port, Port, Port, Queen, S\u00e3o, S\u00e3o, Saint, Saint, Saint, Saint, San, San, San, San, San, San, San, San, Santa, Santo, Santo, South, Sri, St., St., St., St., St., St., St., St., The, The, Valley, West"
			;
	
	public static final String village_root_initial_default = 
			"A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, Aa, Ash, Au, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Bai, Be, Be, Be, Be, Be, Be, Be, Be, Beau, Bei, Bei, Bei, Bi, Bi, Bi, Bi, Bi, Bloe, Bo, Bo, Bo, Bra, Bra, Bra, Bra, Bra, Bra, Bra, Bri, Bri, Bri, Bru, Bru, Bu, Bu, Bu, Bu, Bu, Bu, Bu, Bu, Bu, Bue, Bue, Ca, Ca, Ca, Ca, Ca, Ca, Ca, Ca, Ca, Ca, Ca, Cai, Cai, Cay, Cay, Ce, Cha, Cha, Cha, Chi, Chi, Cley, Cli, Clo, Co, Co, Co, Co, Co, Co, Co, Co, Co, Co, Co, Co, Co, Conde, Cre, Cre, Cru, Cu, Da, Da, Da, Da, Da, Da, De, De, De, Dha, Dha, Di, Di, Dji, Dji, Do, Do, Do, Do, Do, Do, Do, Dou, Dou, Dou, Du, Du, Du, Du, Du, Du, Du, Du, Dy, E, E, E, E, E, E, E, Ed, Ed, Fa, Fa, Fa, Fai, Fi, Fi, Fi, Fi, Fi, Fi, Fra, Free, Free, Fu, Fu, Fy, Ga, Ga, Ga, Ga, Gai, Geo, Geo, Geo, Geo, Geo, Geo, Geo, Geo, Gi, Gi, Go, Goa, Gree, Gro, Gu, Gu, Gua, Gua, Gua, Gy, Ha, Ha, Ha, Ha, Ha, Ha, Ha, Ha, Ha, Ha, Ha, He, He, He, He, Hi, Ho, Ho, Ho, Ho, I, I, I, I, I, I, Ja, Ja, Ja, Ja, Ja, Je, Je, Je, Ji, Jo, Jo, Jo, Jo, Jo, Ju, Ju, Ju, Jua, Jua, Ka, Ka, Ka, Ka, Ka, Ka, Ka, Ka, Ka, Ka, Kai, Kha, Ki, Ki, Ki, Ki, Ki, Ki, Ki, Ki, Ki, Ki, Kie, Ko, Ko, Ko, Ko, Ku, Ku, Ku, Kua, Kua, Kyi, La, La, Li, Li, Li, Li, Li, Li, Li, Li, Li, Li, Li, Lju, Lju, Lo, Lo, Lo, Lo, Lo, Lo, Lo, Lou, Lou, Lu, Lu, Lu, Lu, Lu, Lu, Lu, Luo, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Mba, Me, Me, Me, Me, Mi, Mi, Mi, Mi, Mi, Mo, Mo, Mo, Mo, Mo, Mo, Mo, Mo, Mo, Mo, Mo, Mo, Moo, More, More, Mu, Mu, Mu, My, My, My, N, N, N, Na, Na, Na, Na, Na, Nai, Nai, Nay, Ne, Ni, Ni, Ni, Ni, Nia, Nia, Nou, Nou, Noua, Noua, Nu, Nu, Nuu, Nuu, O, O, O, O, O, O, O, Oua, Oua, Pa, Pa, Pa, Pa, Pa, Pa, Pa, Pa, Pa, Pa, Pa, Pa, Pa, Pau, Pe, Pe, Pe, Phi, Phno, Phno, Pi, Pi, Ply, Po, Po, Po, Po, Pra, Pra, Pra, Pra, Pra, Pre, Pre, Pri, Pri, Pri, Pri, Pu, Pyo, Pyo, Que, Que, Qui, Ra, Ra, Re, Re, Rey, Rey, Ri, Ri, Ri, Ri, Ro, Ro, Ro, Ro, Ro, Roa, Roa, Sa, Sa, Sa, Sa, Sa, Sa, Sa, Sa, Sa, Sa, Sa, Sai, Sai, Schaa, Se, Se, Se, Se, Se, Seou, Seou, Sha, Shi, Si, Si, Si, Sko, Sko, So, So, Spai, Sta, Sta, Ste, Ste, Sto, Sto, Su, Su, Su, Su, Su, Sy, Sy, T, T, T\u00f3, T\u00f3, Ta, Ta, Ta, Ta, Ta, Ta, Ta, Tai, Tai, Te, Te, Te, Te, Te, Te, Tha, Thi, Thi, Thi, Ti, Ti, Ti, Ti, Ti, Ti, To, To, To, To, To, To, To, To, Tre, Tree, Tri, Tri, Troi, Tskhi, Tskhi, Tu, Tu, Tu, Tze, U, U, U, Va, Va, Va, Va, Va, Va, Ve, Vi, Vi, Vi, Vi, Vi, Vi, Vi, Vi, Vie, Vie, Wa, Wa, Wa, Wa, Wa, We, We, Wi, Wi, Wi, Wi, Wi, Wu, Xi, Ya, Ya, Ya, Ya, Ya, Ye, Ye, Yo, Z\u00fc, Za, Za, Zo"
			;
		
	public static final String village_root_sylBegin_default =
			"_Chi, _Pe, 'a, 'Dja, 'Dja, a, a, a, a, a, a, ba, ba, ba, ba, ba, bai, be, beye, bi, bi, bi, bli, bli, bli, blja, blja, bo, bo, bo, bou, bou, bra, bra, bre, bre, bu, bu, bu, bu, bu, ccra, ccra, cha, cha, che, cho, cho, ci, ckbu, cke, ckho, ckho, ckla, co, co, cre, cto, cto, cto, da, da, da, da, da, dai, ddi, ddi, de, dee, dga, dgeto, dgeto, dgo, dgo, di, dney, do, doo, dri, dri, du, e, e, e, e, e, e, e, fa, fe, fi, fi, fto, fu, g\u00e5, ga, ga, ga, ga, ga, ga, ga, ga, ga, ga, ga, ga, ge, ghda, ghda, gla, gla, go, go, go, go, go, go, gre, gre, gu, gu, gua, gue, ha, ha, ha, hli, hra, hra, i, i, i\u00fa, ia, ia, ji, ju, ju, ju, ka, ka, ka, ka, ka, ka, ka, kcho, kcho, ke, khu, khu, kja, kja, kku, ku, ku, ku, ku, kyo, kyo, l\u00e9, l\u00e9, la, la, la, la, la, la, la, la, laa, lba, lbroo, le, le, lex, lfhei, lga, lgie, lgie, lgra, lgra, lhi, li, li, li, li, li, li, li, li, li, lka, lle, lle, lle, lle, llege, lley, lli, lli, lli, lma, lmo, lmo, lni, lni, lo, lo, lo, lo, lo, lo, loo, lpa, lsi, lsi, ltai, lva, lva, lvi, m_Pe, m_Pe, m\u00e9, m\u00e9, m\u00e9, m\u00e9, m\u00e9, ma, ma, ma, ma, ma, ma, ma, mbai, mbe, mdu, mesto, mey, mey, mfo, mhe, mi, mi, mi, mma, mma, mou, mou, mou, mou, mpa, mpa, mphi, mphu, mphu, mpo, mra, msi, mste, mste, mu, mu, n_Ba, na, na, na, na, na, na, na, na, na, na, na, na, na, na, naa, nbe, ndblu, ndhoe, ndhoe, ndo, ndo, ndo, ndo, ndo, ng_Ko, nga, nga, nga, nga, nghai, ngko, ngko, ngo, ngspo, ngsto, ngsto, ngsto, ngsto, ngsto, ngui, ngui, ngya, ngya, ngyea, ngyea, nhi, ni, ni, ni, ni, ni, ni, ni, nji, nju, nka, nley, nley, nnsmou, no, no, no, no, no, noi, nra, nro, nro, nsha, nsha, nta, nta, nta, nte, nte, nti, nti, ntia, ntia, nto, nva, nva, nwi, nzi, ou, pa, pa, pa, pe, pe, pe, pe, pei, pei, pi, pi, pi, pi, pje, pje, pli, po, po, po, pu, pu, pyi, r_Sa, ra, ra, ra, ra, ra, ra, ra, ra, ra, ra, ra, ra, ra, ra, ra, ra, ra, ra, rble, re, re, re, re, re, re, re, rfa, rga, rge, rge, rge, rgei, rgeto, rgeto, rgeto, rshe, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, rkha, rki, rli, rli, rli, rlo, rlo, rlo, rme, rna, rne, ro, ro, ro, ro, ro, ro, ro, ro, ro, ro, ro, rpe, rre, rsa, rsa, rsha, rsha, rto, rtou, ru, ru, ru, ru, ru, rwe, s\u00e9, s\u00e9, s\u00ed, sa, sa, sa, sa, sbo, sbo, sby, sby, sca, sca, sce, sce, sco, sco, sco, se, se, seau, seau, sha, sha, shga, shga, shi, shke, shke, shke, shke, si, si, si, si, si, sla, sla, slo, slo, sma, sma, smo, ssau, ssau, ssau, ssau, sse, sse, sse-Te, sse-Te, ssete, ssete, sta, sta, sta, sta, sta, ste, stha, sti, sti, sto, sto, sto, strie, strie, su, su, ta, ta, ta, ta, tai, te, te, te, te, the, the, thma, thma, tho, thri, ti, ti, ti, ti, tin, tio, to, to, to, to, to, tra, tre, tswoo, tta, tto, va, va, va, va, va, ve, vo, wa, wa, wai, wai, wbu, wloo, wso, xe, xe, xi, xi, ya, ya, ya, ya, ya, za, za, zny, zo, zo, zu, zu, zza, zza, "
			+ "_Be, _Be, _Dha, _Ga, _Lu, _Lu, _Mi, _Pa, _Ro, -No, -U, -U, 'a, 'a, 'a, 'a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, ba, ba, ba, bi, bi, bla, bo, ca, ca, ca, ca, chi, ci, ci, ci, ci, cle, co, co, co, da, deau, di, di, di, di, dja, do, do, dou, dou, dzou, dzou, e, e, e, e, e, e, fi, fi, fu, fu, ga, go, go, gua, gua, hea, i, ja, ja, je, je, je, ka, ka, ka, ki, ki, ko, ko, kry, kry, ku, la, la, la, la, laa, lhei, li, li, li, li, li, li, li, li, li, lia, lla, lta, lta, lto, ma, ma, ma, ma, ma, ma, ma, ma, ma, ma, ma, mba, mbo, mbou, mbou, mbu, mbu, me, me, mi, mi, msta, msta, msto, msto, n_Ba, n_Sa, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, nau, nau, nbaa, nbe, nbe, nbu, nbu, nci, nci, nd\u00e9, nda, nda, nda, ndo, ndu, ndu, ne, nge, ngo, ngo, ngto, ngto, ngto, ngwe, ngwe, nha, nha, ni, ni, ni, ni, ni, ni, ni, nje, nje, nki, nki, nna, nna, nne, no, nou, ntei, nto, nu, ny, o, pa, pe, pe, po, po, po, ppo, psbu, qui, ra, ra, ra, ra, ra\u00ed, rbye, rbye, rda, rda, re, re, re, re, rga, ri, ri, ri, ri, ri, ri, ri, ri, rka, rma, rnfo, ro, ro, ro, ro, ro, rra, rra, rra, rre, rta, rta, ru, ru, ru, ru, rul, ry, ry, s_A, s_A, s_Ai, s_Ai, sa, sa, sa, sa, sa, sa, sa, scu, si, si, sko, sko, sla, sla, spo, spo, ssou, ste, t\u00e1, t\u00e1, t\u00f1a, ta, ta, ti, ti, tie, to, to, to, tta, tte_A, tte_A, ty, u, u, va, va, vi, vi, vi, vi, vi, vi, vi, vi, vi, vi, vi, vi, vo, wa, wa, wa, you, "
			+ "\u00f3, \u00f3, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, ba, ba, ba, bi, bo, ca, ca, ce, de, de, fo, ga, ga, ga, ga, ge, ge, go, go, go, gou, gou, ha, ha, ke, ke, kro, la, la, le, le, le, lo, lo, ma, ma, mpu, mpu, mu, na, na, na, na, nca, nda, ndri, ne, ne, o, o, pi, pi, po, r, ra, ra, ra, ra, ra, rde, re, re, ri, ri, ri, sbu, shu, shu, si, si, si, so, sta, sta, ta, te, te, ti, ti, ti, to, tu, tu, va, va, vo, vo, vo, ya, za, "
			+ "a, ba, ba, bo, bo, fa, fa, lie, lie, lpa, lpa, m, m, ne, o, o, ri, ri, wa, wa, "
			+ "pu, vo, vo, "
			+ "ra"
			;
	
	public static final String village_root_terminal_default =
			"^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, 's, 's, 's, b, b, c, c, ch, ch, d, d, d, d, d, d, d, d, d, d, d, d, d, d, de, de, de, de, de, des, dh, dh, fsk, ft, gh, gue, gue, gue, h, h, hl, hn, hn, k, k, k, k, k, k, k, k, k, k, k, k, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l', lb, le, le, ll, ll, lle, lle, lle, lle, lm, lm, lm, ls, ls, lse, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, mb, me, me, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, nce, nce, nce, nd, nd, nd, nd, ne, ne, ne, ne, ng, ng, ng, ng, ng, ng, ng, ng, nh, nh, nh, nn, nne, nne, ns, ns, nsk, nsk, nt, nt, nt, nt, pe, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, rd, rd, rd, rd, re, re, re, rg, rg, rg, rg, rge, rge, rh, rk, rn, rn, rnt, rre, rre, rre, rre, rre, rre, rs, rs, rs, rst, rt, rt, rt, rt, rt, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, sh, sh, st, st, st, st, st, t, t, t, t, t, t, t, t, t, t, t, t, t, te, th, th, tt, tt, v, v, ve, vn, vn, w, w, w, w, w, wn, wn, wn, wn, wn, wn, wn, wn, wn, wn, wn, wn, wn, x, x, z, z, z, z, ze"
			;
	
	public static final String village_suffix_default =
			"Bank, Cantonment, Cantonment, Canyon, Castle, Cays, City, Cove, Cove, de la Sierra, del Sol, District, Estate, Ferry, Hill, Hollow, Inn, Kotte, Lake, Mountain, of the Sea, Park, Park, Pine, Point, Point, Port, Port, Town, Town, Town, Town, Town, Town, Town, Valley, Vella, Vella, Village"
			;
	
	public static final int[] village_syllable_count_weights = new int[]{
			57, 273, 206, 90, 17, 2, 1
			};
	
	
	/*
	 * Mineshaft name pieces
	 */
	public static final String mineshaft_prefix_default =
			"Big, Big, Black, Blue, East, East, El, El, El, El, Golden, Golden, Great, Green, High, Lake, Las, Little, Long, Low, Lower, Monte, Mount, Mount, Mount, New, New, New, New, North, North, Old, Parc, Qaf, Qaf, Red, Roter, San, San, Six, South, South, St., St., Upper, West, West, white"
			;
	
	public static final String mineshaft_root_initial_default =
			"A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, Age, Ape, Ay, B\u00e4, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Bau, Be, Be, Be, Be, Be, Be, Bea, Bea, Bi, Bi, Bi, Bi, Bi, Bla, Blue, Bo, Bo, Boo, Bou, Boy, Bra, Bre, Bri, Bri, Bro, Bro, Bro, Broo, Brou, Bru, Bru, Bru, Bu, Bu, Bu, Bu, Bu, Bu, Bu, Bu, Bu, Bu, Bu, Bw, Ca, Ca, Ca, Ca, Ca, Ca, Ca, Ca, Cau, Ce, Ce, Ch\u00f9i, Cha, Cha, Cha, Cha, Che, Chi, Chi, Ci, Cli, Cli, Cli, Cli, Cli, Cli, Clo, Co, Co, Co, Co, Co, Co, Co, Co, Co, Co, Coo, Cra, Crei, Cri, Cro, Cy, Da, Da, Da, Da, Da, Da, De, Di, Di, Do, Do, Do, Do, Dru, Dvoi, E, E, E, E, E, E, Fa, Fa, Fe, Fe, Fe, Fi, Fi, Fle, Flee, Fo, Fo, Fra, Fro, Froo, Ga, Ga, Ga, Ge, Ge, Geo, Gi, Gi, Gi, Gi, Gi, Gla, Glei, Go, Goo, Gra, Gre, Gree, Gree, Gu, Gui, Gwa, Ha, Ha, Ha, Ha, Ha, Ha, Ha, Hay, He, He, He, He, He, Hi, Hi, Hi, Hi, Hi, Hi, Hi, Ho, Ho, Ho, Ho, Hu, Hu, I, I, I, I, I, I, Ja, Jo, Jo, Ju, Jua, Ka, Ka, Ka, Ka, Ka, Ka, Ka, Ke, Ke, Ke, Ke, Kee, Kho, Ki, Ki, Ki, Ki, Ki, Ki, Ki, Kii, Klo, Ko, Ko, Ko, Kra, Kre, Ku, Ku, L\u00f8, La, La, La, La, La, La, Le, Le, Li, Li, Li, Lo, Lo, Lu, Lu, Lu, Ly, M\u00e9, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, May, Me, Mi, Mi, Mi, Mi, Mi, Mo, Mo, Mo, Mo, Mo, Mo, Mu, Mu, Mu, Na, Na, Na, Na, Na, Ne, Ne, Neui, No, No, No, No, Noo, Nu, O, O, O, O, O, O, Oa, Oa, Ou, P, P\u00eb, Pa, Pa, Pa, Pa, Pa, Pa, Pa, Pau, Pe, Pe, Pe, Pe, Pe, Phoe, Pi, Pi, Plea, Plu, Pre, Pri, Prie, Pro, Pro, Pu, Py, Qui, Qui, Ra, Ra, Ra, Ra, Ra, Ray, Re, Re, Ri, Ri, Ro, Rou, Ru, Sa, Sa, Sa, Schuy, Se, Se, Se, Sea, Sha, Sha, Shi, Shu, Si, Si, Sli, Smi, Smu, Sna, Snae, So, Sou, Spa, Spri, Sta, Sta, Sta, Stave, Ste, Stee, Stee, Sti, Stra, Stro, Stu, Su, Su, Su, Su, Su, Su, Ta, Ta, Ta, Ta, Ta, Tau, Te, Te, Tha, The, Tho, Thu, Ti, Ti, To, To, Trea, Tu, Tu, Tu, Ty, Ty, U, U, Va, Vi, Vla, Wa, Wa, Wa, Wa, Wa, We, We, We, Whi, Wi, Wi, Wi, Wi, Wi, Wi, Wi, Wi, Wi, Wie, Wo, Wri, Wu, Wy, Ye, Yo, You, Yu, Za, Zi, Zo, Zo"
			;
			
	public static final String mineshaft_root_sylBegin_default =
			"_To, a, a, a, a, a, a, bbi, bbi, be, be, be, be, be, bfie, bi, bi, bi, bu, bu, chi, chni, ckbi, cke, cki, ckie, ckle, cko, cno, co, cro, cto, da, da, da, da, da, da, ddi, dfo, dfo, dgee, dgewa, dgeway, di, di, dley, dswo, du, dve, dwa, dwe, dy, e, f, fe, ffeau, ffie, fie, fl\u00f3, fto, fto, ga, ga, ga, ga, ggi, ggle, ghto, ghto, gla, gne, gu, h\u00e4, ha, hna, hnso, i, i, i, je, ka, kda, ke, ke, ke, khou, kie, kke, kna, ku, ky, la, la, la, la, la, laa, lau, lbe, lbi, lby, lde, ldesley, ldo, ldwa, le, leswoo, ley, ley, lfe, lga, lgie, lgoo, lhou, li, li, li, li, li, llcre, llcro, lle, llei, llfa, llgro, lli, lli, lli, lli, lli, llia, llie, llve, lma, lma, lmai, lmoy, lmy, lnhu, lo, lp\u00fa, lphi, lqi, lroo, lse, lse, lstho, ltby, lto, lto, lu, lu, lu, lva, lve, ly, lya, ma, ma, me, me, mesta, mesta, mfu, mi, mie, mla, mme, mme, mmi, mmo, mni, mro, mta, na, na, na, nco, ncy, ncy, nda, nde, ndee, ndho, ndle, ndle, ndo, ndre, ndri, ndso, ndso, ndso, ndwoo, ne, ne, nesi, nesu, nga, nghi, ngma, ngsbe, ngsbu, nhei, ni, ni, ni, ni, ni, ni, ni, ni, nkgru, nkli, nla, nlei, nlou, nme, nna, nne, nne, nne, nni, nno, nny, nny, no, nri, nro, nse, nshi, nsi, nsley, nsley, nso, nswi, nswi, nte, nte, ntga, nto, ntre, ntwoo, nty, nty, nve, nwoo, nye, nzewi, o, pi, po, ppe, ppe, ppe, psto, pu, pu, ra, ra, ra, ra, ra, rba, rchtree, rcro, rcu, rdi, rdy, re, re, reoa, rga, rgo, rgo, rgrea, rgrea, rgy, ri, ri, ri, rkfie, rkha, rkshi, rley, rli, rli, rlo, rma, rmi, rmi, rna, rnbu, rnda, rni, ro, ro, ro, ro, rra, rra, rray, rre, rro, rro, rroi, rsema, rsha, rsley, rsley, rso, rso, rte, rthla, rthu, rthu, rto, rto, ru, rve, rwa, rwi, rwo, s\u00e9, sa, sa, sbe, sca, sco, se, sfo, she, shee, sio, ska, sley, sley, sma, sma, so, so, sshi, sta, ste, ste, stley, sto, sto, stray, sty, sya, ta, ta, tche, tche, tche, te, tfie, tha, the, thlee, tla, tley, to, tra, tte, tti, ttle, ttle, ttle, tto, tto, tto, twoo, ve, ve, ve, wa, we, we, wla, wle, wso, xey, yo, yu, ze, zu, "
			+ "_Ba, _Bu, _To, \u00f1o, a, a, a, a, ba, ba, ba, bou, bu, ca, ca, ca, chae, chee, co, co, czka, de, dee, dko, do, do, dre, dy, e, fo, i, ke, ku, ku, l\u00e4, la, le, ley, li, lla, lla, llta, lsbe, lu, lu, lu, ma, ma, mi, mpi, mpto, mu, n_Co, n-Uu, na, na, na, na, na, na, na, nau, nba, nda, ndwa, nga, nge, ngley, ngto, ngto, ngto, nhu, ni, ni, nke, nmi, nne, nni, no, no, nsvi, ntry, ntu, nwoo, o, pi, qua, r, ra, ra, ra, rca, rcy, rdie, re, rei, rfie, ri, ri, ri, rley, rley, rlie, rmi, rna, rni, ro, rpe, rse, rsha, rso, rwoo, ry, ry, sa, sa, sby, se, sha, shi, sho, so, so, so, sque, sto, ta, te, te, ti, tia, to, to, to, to, tvi, va, va, ve, ve, vni, wa, wa, wa, wa, ye, z\u00eb, "
			+ "a, a, a, ca, ca, che, da, do, do, e, ga, hu, i, ka, ka, le, lgoi, lmi, lu, na, nda, ndi, no, nte, ntei, ntha, ra, rgw, rra, rry, rry, rsa, ry, ry, ry, shi, shi, si, ska, stine, t\u00fa, ta, ta, thi, tio, tr\u00eb, vaa, w, "
			+ "e, nska, ra, ra, te, thu, ya"
			;
	
	public static final String mineshaft_root_terminal_default =
			"^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, c, c, c, c, ce, ch, ch, ck, ck, ck, ck, ck, d, d, d, d, d, d, d, d, d, d, d, de, de, dge, ff, ffs, ft, ft, ft, g, g's, ge, gh, ght, h, h, j, j, k, k, k, ke, ke, ke, ks, l, l, l, l, l, l, l, l, l, ld, ld, ld, ld, lds, le, le, le, le, le, le, le, le, lk, ll, ll, ll, ll, ll, ll, ll, ll, ll, ll, lle, lle, lls, lls, lm, lm, ls, m, m, m, m, m, m, m, m, mbe, me, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, nce, nd, nd, nd, nd, nds, ne, ne, ne, ne, ng, ng, ng, ng, ng, nks, nn, ns, ns, ns, nsch, nt, nt, nt, p, p, p, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, rce, rd, rd, rd, rd, rd, rd, re, re, re, re, rg, rg, rg, rg, rge, rgh, rke, rke, rn, rn, rpe, rr, rs, rs, rs, rshe, rst, rst, rt, rth, rth, s, s, s, s, s, s, s, s, s, s, s, s, se, se, se, sh, sh, ss, st, st, t, t, t, t, t, t, t, t, t, th, th, tt, vd, ve, ve, ves, ves, w, w, w, w, w, w, we, wk, ws, x, x, z"
			;
	
	public static final String mineshaft_suffix_default =
			"Bell, Bridge, Canyon, Cave, Cliff, Cliff, Corner, Crag, Creek, Creek, Creek, Creek, Dam, Dam, Dam, del Diablo, des Iles, Down, Down, Earth, Eye, Falls, Grande, Green, Grove, Hall, Hill, Hill, Hill, Hill, Hills, Jungle, Lake, Lake, Lake, Lake, Lake, Leg, Loch, Main, Main, Main, Main, Main, Main, Main, Mascot, Mia, Mill, Mill, Moss, Mountain, Park, Park, Park, Pit, Point, Point, Queen, River, River, River, Shaft, Shore, Tree, Tunnel, Velho"
			;

	public static final int[] mineshaft_syllable_count_weights = new int[]{
			67, 241, 99, 41, 7
			};
	
	
	/*
	 * Stronghold name pieces
	 */
	public static final String stronghold_prefix_default =
			"A, Devil's, Great, Great, Per, Santa"
			;
	
	public static final String stronghold_root_initial_default =
			"\u00c1, A, A, A, A, A, A, A, A, Au, Ba, Ba, Ba, Ba, Ba, Ba, Bei, Bi, Bou, Bri, Cai, Cha, Cha, Che, Co, Co, Co, Co, Co, Cze, Da, De, Di, Du, E, E, Fa, Fa, Flo, Flo, G\u00f6, Ge, Go, Gri, Gu, Ha, He, He, Hei, Hi, Hi, Hu, I, Ja, Ja, Je, Je, Je, Ju, Ka, Ke, Kli, Ko, Kre, Ku, Lo, Lu, Lu, M, Ma, Ma, Ma, Me, Me, Me, Mie, Mo, Mo, Ne, Ni, Ni, O, Pa, Pa, Pa, Pe, Po, Qry, Qua, Ra, Rha, Ru, Sa, Sa, Se, Se, Se, Se, Si, Sie, Sta, Sto, Su, Su, Svea, Tla, To, Tra, Tri, Va, Vi, Vi, Wa, Xi, Yo"
			;
			
	public static final String stronghold_root_sylBegin_default =
			"'a, al, ba, bo, bro, by, cho, co, co, csay, cto, czk\u00f3, dde, di, dri, dzy, e, e, ffa, fri, gfrie, ggu, gi, go, ja, ji, ka, la, lbi, le, li, lle, lo, lpine, lsea, lva, ma, ma, mbha, mli, mo, mste, msto, na, na, nde, nde, ndo, ndo, ne, ng_Ng\u00e3i, ngbo, ngle, ngseo, ni, ni, nne, no, nsta, nto, ntra, ntse, nwy, o, o, pe, r_Le, ra, ra, rce, rde, re, rga, rghe, rgu, ri, ri, rma, ro, rpe, rra, rre, rry, rtsmou, ru, rvi, sby, se, ssi, sta, ste, sti, su, ta, ta, tla, to, tro, tte, tto, ve, vi, wca, xa, xca, xo, ya, "
			+ "a, a, a, a, a, bla, cae, cho, di, gu, gu, hu, i, kli, ko, la, la, lga, li, lo, lo, me, mi, mo, mu, na, na, nbu, ne, ne, ni, ni, ni, no, nti, ntu, ra, rba, rda, rhei, ri, ri, ru, ry, rze, s_Ve, sa, sa, si, sia, slo, sta, stle, su, ti, tia, to, tta, va, vi, vi, vni, xa, "
			+ "a, a, a, a, a, cu, dra, k\u0131, le, lio, na, na, nli, no, no, o, ra, ra, ri, rke, rke, ro, sia, sta, t\u00e1, ta, va, "
			+ "di, m\u00e1, nna, ple"
			;
	
	public static final String stronghold_root_terminal_default =
			"^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, cz, d, de, k, k, l, l, m, m, m, m, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, ne, ng, ng, nt, r, r, r, r, r, rg, rg, rh, rk, s, s, s, s, s, s, s, s, s, s, s, s, sh, t, t, t, th, tz, v, v, v, w, x"
			;
	
	public static final String stronghold_suffix_default =
			"Box, Castle, Citadel, Citadel, Eredo, Fortress, Fortress, Fortress, Hill, Shrine, Subterrane, Zuu"
			;

	public static final int[] stronghold_syllable_count_weights = new int[]{
			8, 44, 36, 23, 4
			};
	
	
	/*
	 * Temple name pieces
	 */
	public static final String temple_prefix_default =
			"Abu, Abu, Beit, Divus, Jebel, Jebel, Mater, Saint, San, San, San, Santa, Santa, Santo"
			;
	
	public static final String temple_root_initial_default =
			"A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, Au, Au, B\u00f3, Ba, Ba, Ba, Ba, Ba, Ba, Baa, Baa, Be, Bo, Bor, Bu, C\u00f3, Ca, Ca, Ca, Cae, Ce, Ch\u00e2, Ci, Clau, Cle, Co, Co, Co, Co, Cou, Cu, Da, Da, De, De, De, De, Di, Di, Di, Di, Do, Do, Do, Do, Do, E, E, E, E, E, E, E, E, E, E, F\u00e2, Fau, Fe, Fi, Fle, G, G\u00f6, Ga, Ge, Ge, Gha, Giu, Gra, Grie, Ha, Ha, Ha, Ha, Ha, Ha, He, He, He, He, He, He, He, He, Hei, Hi, I, I, Ja, Je, Jo, Ju, Ju, Ju, Ka, Ka, Kha, Ko, Koe, Ku, La, Le, Le, Le, Li, Lo, Lu, Lu, Ly, Ly, M, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Me, Me, Me, Me, Mi, Mi, Mo, Mou, Mu, Mu, Ne, Ne, Ni, Ny, O, O, O, Pa, Pa, Pa, Pa, Pa, Pe, Pe, Pea, Phi, Po, Po, Po, Po, Pri, Pri, Pu, Qa, Qa, Qe, Ra, Ra, Re, Ro, Ro, Ro, Ro, Ro, S, Sa, Sa, Sa, Sa, Sa, Sch\u00f6, Se, Se, Se, Se, Se, Se, Se, Sha, Si, Si, Si, Si, Sko, So, So, Spe, Sy, Sy, Ta, Ta, Ta, Ta, Ta, Ta, Tau, Te, Te, Te, Thu, Ti, To, To, Tr\u00e9, Tra, Tu, U, U, V\u00e9, Va, Ve, Ve, Ve, Ve, Ve, Ve, Vei, Vi, Vi, Wa, Wa, Wa, We, Xa, Xe, Y, Za, Za, Zeu"
			;
			
	public static final String temple_root_sylBegin_default =
			"\u017ea, a, a, ba, be, bei, bii, blai, bo, bu, cchu, chnou, ckwi, co, cro, ddi, di, di, di, dney, do, dri, dri, e, ffe, fli, fra, ga, ga, ge, ge, ge, gga, ghou, gi, gi, gi, gno, gou, gra, gu, gu, ha, ha, ja, kka, la, la, la, la, lai, lbe, lc\u00e1, le, le, li, li, li, li, li, llu, lo, ltba, lve, ma, ma, mae, mbe, me, me, me, mi, mi, mi, mna, mne, mo, mo, mo, mphae, mpho, mu, mu, mxi, n\u00f3, na, na, nai, nb\u00fc, nbi, nci, nco, nco, ncra, nd\u00e9, ndo, ndun, ne, ne, nge, ni, ni, nka, nma, nne, nni, nnu, no, no, nta, nte, nthe, ntju, nto, nu, nu, nu, nxay, o, o, o, o, o, pe, pha, pha, pha, phe, phro, phy, pi, pi, po, psi, pti, pto, ptu, qa, r\u00f3, ra, ra, ra, ra, ra, rai, rba, rbe, rco, rcu, rcu, rcu, rda, rdi, rdi, rdo, re, re, rfu, rgo, ri, ri, ri, ri, rka, rmo, rna, rna, rne, rne, ro, ro, ro, rr\u00e9e, rra, rre, rre, rsi, rsi, rta, rtbe, rte, rthe, rto, rtu, ru, rxi, s, sa, sca, scle, sei, sha, shmou, si, sme, so, spa, spa, st\u00f3, sta, sti, sto, stte, sy, tca, te, teli, the, to, tra, tra, tra, tro, tta, tti, tu, tu, u, u, u, ve, vi, wa, we, xo, ze, zo, zo, "
			+ "\u00e0, a, a, a, a, a, a, ba, ba, bba, bo, bou, bri, bsha, chta, chthe, cle, cto, cu, da, da, de, de, di, dna, do, e, e, e, e, e, e, ga, ge, gga, gni, gsho, gtu, i, i, i, ia, ja, ja, jdra, la, la, li, lle, lli, llo, lu, mbri, me, mi, mo, na, na, na, na, na, na, ndo, ne, ne, nga, ni, no, nta, nte, nti, nto, nu, nu, nu, nvi, ny, o, o, o, o, pe, pi, po, po, po, po, ppa, ppei, py, r\u00e1, ra, ra, rbo, rdi, re, re, ri, ri, ri, rme, rno, rnu, rra, rta, rva, ry, sa, sbu, scu, scu, si, si, so, sre, ssi, sta, sta, ste, stei, sti, stu, su, su, ta, te, te, ti, ti, tla, to, u, u, u, u, u, vi, wbu, wwa, ze, zi, "
			+ "a, a, a, a, a, a, ca, e, e, ffe, ga, ga, ja, ko, le, le, li, li, li, lo, lzu, ma, na, na, ne, ne, ni, ni, nia, no, no, nstei, nte, nu, o, o, o, qua, ra, ra, ra, ra, rdo, re, re, ri, ri, ri, rti, so, ssa, stu, stu, te, u, u, u, u, "
			+ "cu, ne, nki, nu, o, u"
			;
	
	public static final String temple_root_terminal_default =
			"^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, b, b, b, c, c, c, ce, d, g, g, gh, h, h, hl, k, k, k, l, l, l, l, l, l, l, l, le, lfe, lg, lle, m, m, m, m, m, m, m, m, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, ne, ne, ns, ns, r, r, r, r, r, r, r, r, r, r, rd, rd, re, re, re, rf, rg, rg, rg, rgh, rn, rn, rr, rs, rs, rx, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, se, t, t, t, t, t, t, tt, tte, x, x"
			;
	
	public static final String temple_suffix_default =
			"Hill, Magna, Qim"
			;

	public static final int[] temple_syllable_count_weights = new int[]{
			20, 87, 83, 52, 6
			};
	
	
	
	/*
	 * Fortress name pieces
	 */
	public static final String fortress_prefix_default =
			"Nether"
			;
	
	public static final String fortress_root_initial_default =
			"\u00c9, A, A, A, A, A, A, A, Aea, Ca, Cae, Ce, Ce, Cha, Chi, Ci, Co, Di, Di, Dra, Du, E, E, E, Eu, Fey, Ga, Gj\u00f6, Gja, Go, Ha, Ha, He, He, He, Hy, Hy, I, La, Le, Le, M\u00f3, Me, Me, Mi, Mi, Mu, Mu, N\u00e1, Na, Ni, Ni, O, Pa, Pa, Pa, Pe, Pe, Phae, Phle, Plu, Pro, Rha, So, Sty, Ta, Ta, Tha, Ti, Ti, Tu, Xi, Yo, You"
			;
			
	public static final String fortress_root_sylBegin_default =
			"a, ba, ba, by, ca, che, chi, chly, cra, cri, ctla, cu, cy, da, de, dhgu, dmu, do, dra, dra, du, f, fl, ga, gae, ge, ku, le, le, li, lj\u00fa, lla, ma, ma, me, mi, na, nd\u00e6, ne, no, ntau, o, o, o, pno, ra, rbe, re, rgo, ri, ri, rka, rm, rna, ro, rphe, rpy, rse, rta, ry, si, si, spe, spho, str\u00f6, ta, the, to, va, yu, "
			+ "bu, ci, cto, da, de, dh, dhni, dne, hei, ka, la, lba, lhe, lhei, lla, lle, lu, ma, mo, mu, ne, no, no, pha, pho, pho, phy, ra, ra, rbr\u00fa, ro, rtha, ru, ru, te, te, tho, to, tu, u, u, u, "
			+ "\u00eb, \u00eb, ba, la, le, mi, mo, ne, ne, ni, nthu, ri, "
			+ "a, a, u"
			;
	
	public static final String fortress_root_terminal_default =
			"^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, g, l, l, l, ll, ll, m, m, m, n, n, n, n, n, n, nd, r, r, r, r, rch, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, ss, t, x"
			;
	
	public static final String fortress_suffix_default =
			"Fortress"
			;

	public static final int[] fortress_syllable_count_weights = new int[]{
			4, 28, 30, 9, 3
			};
	
	
	
	/*
	 * Monument name pieces
	 * Version 1.8 on
	 */
	public static final String monument_prefix_default =
			"Dread, Lost, Odd, Sunken"
			;
	
	public static final String monument_root_initial_default =
			"A, A, A, A, A, A, A, A, Aa, Bai, Bi, Bou, Bu, Ca, Ca, Ca, Ca, Can, Co, Cu, Di, Dwa, Ei, Ga, Ge, Ge, Ha, He, He, He, He, Kha, Ki, L, Le, Lo, Lu, Ly, Ma, Ma, Mu, Mu, Na, Na, Ne, Ne, O, O, O, Pa, Pha, Phe, Po, Pu, Quia, R, Ra, Ra, Ra, Rha, Ru, Sa, Sa, Sa, Sae, Shi, Su, Ta, Ta, Tho, Ti, Tu, Y, Ya, Yo"
			;
			
	public static final String monument_root_sylBegin_default =
			"'lye, a, ae, ba, be, che, co, de, du, du, fti, ga, gi, ha, hu, ia, ka, ke, la, lae, le, le, li, li, li, li, lo, ly, mba, mbha, mbu, me, mi, mpto, mu, na, na, na, ncho, ndao, ngho, ni, no, nti, ntre, o, o, po, ra, ra, ra, ra, rde, rga, rka, ro, ro, ro, rpi, rt_Ro, rte, ru, te, ti, ti, ti, tla, tli, ve, ve, vlo, "
			+ "_Pe, _Ro, ', a, ba, ca, cle, clei, fa, fa, go, gu, ke, ki, l_Ba, le, lee, na, ne, nghe, ni, ni, nje, nse, nspu, ntho, nti, o, pe, pu, r_U, rho, ri, rju, rxna, s_He, sso, t_Si, ta, ti, tu, u, u, u, xa, ya, yo, "
			+ "a, a, a, ca, ca, che, do, ha, i, li, li, lu, na, ndri, ni, ni, ni, nu, nu, o, o, r_Gwae, ra, ri, s_A, sta, tri, wa, wi, "
			+ "a, a, a, a, ko, lo, nca, ndre, no, o, o, pu, wa, "
			+ "a, nda, ra, tu"
			;
	
	public static final String monument_root_terminal_default =
			"^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, c, d, d, g, h, h, j, k, l, l, l, lt, m, m, m, n, n, n, n, n, n, n, ng, ng, r, r, rn, s, s, s, s, s, s, s, s, s, s, s, s, sse, t, t, t, zh"
			;
	
	public static final String monument_suffix_default =
			"Heart, Mausoleum, Point, Tomb, Tongue"
			;

	public static final int[] monument_syllable_count_weights = new int[]{
			4, 24, 18, 16, 9, 4
			};
	
	
	
	/*
	 * EndCity name pieces
	 * Version 1.9 on
	 */
	public static final String endcity_prefix_default =
			"End, Mag, T\u00edr"
			;
	
	public static final String endcity_root_initial_default =
			"A, A, A, A, Ba, Brah, Ca, Ca, Ce, Ce, Cha, Di, Dy, E, E, E, E, E, Eu, Eu, Ga, Gai, Hau, Hea, Hla, I, I, I, I, I, Ju, Ka, Ko, Lu, Ma, Ma, Ma, Me, Me, Mi, Mi, Moo, N\u00f3, Ne, Ni, O, Pa, Plu, Pu, Rhe, Sa, Sa, Se, Su, Te, Te, Ti, Tla, Tri, U, U, U, U, Va, Ve, Yh, Yo"
			;
			
	public static final String endcity_root_sylBegin_default =
			"a, a, a, be, bi, cha, da, de, ke, la, la, la, le, le, lha, lli, lo, lo, ltha, lti, ly, ly, ma, ma, mbri, me, mi, mme, na, nce, nde, nga, ni, nnw, nu, ny, o, o, pi, ptu, ra, ra, rco, rcu, re, re, rga, ri, ri, rna, ro, ro, rra, rza, sga, ta, thy, ti, to, to, tu, ve, "
			+ "a, ca, e, e, ka, la, lla, ma, me, me, mo, nda, ne, ne, ni, no, no, nu, pa, pe, pha, pu, rla, rne, ro, ru, ry, sa, si, sto, te, th-Lee, to, tro, "
			+ "\u00ef, a, cia, du, ke, ni, ra, ry, tu, u, "
			+ "u"
			;
	
	public static final String endcity_root_terminal_default =
			"^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, b, b, de, g, k, k, kh, l, l, ll, ll, m, m, n, n, n, n, n, n, n, n, n, n, n, nd, ne, r, r, r, rd, rn, rs, s, s, s, s, s, s, s, s, s, th, th, th"
			;
	
	public static final String endcity_suffix_default =
			"City"
			;

	public static final int[] endcity_syllable_count_weights = new int[]{
			5, 28, 24, 9, 1
			};
	
	
	
	/*
	 * Mansion name pieces
	 * Version 1.11 on
	 */
	public static final String mansion_prefix_default =
			"Flower of, Golden, Grey, Life of, North, Silver, The, The, The, The, The, The, The, The, The, The, The, White"
			;
	
	public static final String mansion_root_initial_default =
			"'I, A, A, A, A, A, A, A, Be, Be, Be, Be, Bi, Blai, Bo, Brae, Bray, Brea, Ca, Ca, Ca, Cha, Co, Co, Coe, Da, Da, Du, Du, E, E, E, Fai, Fi, Fleu, Flo, Fri, Ga, Ge, Gle, Grey, Ha, Ha, Hay, He, Hea, Hi, Ho, Ho, Hu, Hy, Hy, I, I, I, Ka, Ky, Ly, Ly, Ma, Ma, McCu, Mea, Mi, Mi, Mo, Ne, O, O, O, O, Pa, Pa, Pa, Pa, Pa, Pay, Pe, Pe, Po, Pre, R\u00ea, Ra, Re, Rei, Ri, Ro, Ry, Se, Se, Sea, Sea, Sha, Sha, She, Shi, Ti, To, To, Twi, U, Ve, Vi, Wa, Wa, Whi, Whi, Wi, Wi, Wi, Woo, Xa, Ya, Zi"
			;
			
	public static final String mansion_root_sylBegin_default =
			"bble, ce, chre, ddo, dle, dlea, do, do, do, he, ke, kui, la, lai, lai, lbu, lde, li, lla, lli, lme, lmo, lo, lsto, lsto, ltmo, lv\u00e9, mba, mbro, mi, mou, mpstea, na, ncai, nde, ndi, ngdi, nge, ngwoo, ni, nna, nnewoo, nra, nsmo, nta, nte, nti, nwoo, o, pewe, pla, r-a, ra, rbo, rbo, rbu, rde, rdro, re, rfie, rha, rla, rli, rne, rne, ro, rsai, rsde, schma, she, stle, sto, sto, teha, tema, tto, ve, ve, ve, vie, vo, we, we, wnse, wse, "
			+ "_Hou, -La, a, d\u00e8, du, ge, ghe, gie, ka, la, la, li, li, lo, mea, na, ngto, ngto, ni, ni, o, ri, rie, rsi, rsky, rso, rthu, rto, rwoo, sfa, so, ssa, tto, w_Broo, "
			+ "ble, da, go, n_Ne, ni, o, ty, "
			+ "sa"
			;
	
	public static final String mansion_root_terminal_default =
			"^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ck, ck, d, d, d, d, d, d, de, de, es, ff, ght, hn, k, ke, ke, l, l, l, ld, ldt, les, ll, ll, ll, lle, lles, lls, lm, m, m, n, n, n, n, n, n, n, n, n, nd, nd, nds, ne, ne, ne, ng, nt, r, r, r, r, r, r, r, r, r, re, re, re, rg, rls, rne, rs, rs, rs, rs, rsh, rst, s, s, s, s, s, se, se, t, t, ve, w, w, w, we, wn, z"
			;
	
	public static final String mansion_suffix_default =
			"Air, Brook, Castle, Castle, Castle, Castle, Castle, Ch\u00e2teau, Champ, Chateau, Court, Court, Creek, Farms, Ferry, Grove, Hacienda, Hall, Hall, Hall, Hall, Hall, Hall, Hall, Hall, Hill, Hill, Hill, Hill, Hill, House, House, House, House, House, House, House, House, House, House, House, House, House, House, House, Lawn, Lodge, Lodge, Manor, Manor, Manor, Manor, Mansion, Mansion, Mansion, Mansion, Mansion, Mansion, Mansion, Mansion, Mansion, Mansion, Mansion, Mansion, Meadows, Oaks, Palace, Park, Pond, Ranch, Ridge, Royal, Terrace, Terrace, Villa, Villa, Way"
			;

	public static final int[] mansion_syllable_count_weights = new int[]{
			29, 51, 27, 6, 1
			};
	
	
	
	
	/*
	 * Alien Village name pieces
	 */
	public static final String alienvillage_prefix_default =
			""
			;
	
	public static final String alienvillage_root_initial_default =
			"A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, Ae, Ae, Au, Au, B, Ba, Baa, Baa, Be, Be, Bi, Bi, Bla, Bo, Bo, Bo, Bo, Bo, Bo, Bou, Bra, Bray, Bri, Bu, Bu, By, Ca, Ca, Ca, Ca, Ca, Ca, Ca, Ca, Ca, Cau, Cau, Ce, Cha, Cle, Cle, Cloo, Co, Co, Co, Co, Cri, Cu, Cy, Da, Da, Da, Da, Da, De, De, Di, Di, Di, Do, Do, Dra, Dzie, E, E, E, E, E, Eu, Fe, Fe, Fe, Fi, Fla, Fo, Fo, Fre, Fri, Fu, Fu, G, G, G, G\u00e4, Ga, Ga, Ga, Ga, Ga, Gau, Gay, Ge, Gei, Gllo, Glo, Gly, Gna, Gra, Gra, Gri, Gru, Gue, H, Ha, Ha, Ha, Ha, Ha, Ha, Ha, Hae, He, He, He, Hei, Hi, Hi, Hi, Ho, Hu, Hu, Hu, Huy, Hy, Hy, Hy, I, I, I, I, I, Ja, Ja, Ju, K, K, K, K, Ke, Ko, Ko, Kra, Krie, Ku, Ky, Ky, L, La, Le, Leu, Li, Li, Lie, Lu, Lu, Lu, M, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Mae, Mai, Mau, May, Me, Me, Me, Me, Me, Me, Mi, Mi, Mi, Mo, Mo, Mo, Ne, Ni, Ni, Ni, Niu, Nu, Nyi, O, O, O, O, O, O, O, O, O, Pa, Pa, Pa, Pe, Pe, Pe, Pe, Ph, Phe, Pi, Pi, Pi, Pi, Pla, Pla, Plei, Pli, Pni, Po, Po, Pri, Pro, Pru, Ptha, Pu, Py, R\u00e9, R\u00f6, R\u00fc, Ra, Re, Re, Re, Rei, Rei, Rhei, Rhy, Ri, Ri, Ri, Ri, Ro, Roo, Ru, Ru, Sa, Sa, Schr\u00f6, Schr\u00f6, Schr\u00f6, Sci, Se, Se, Seu, Sha, Sha, Shee, Sho, Shu, Si, Si, Sie, Smi, Smy, Sne, So, So, So, So, So, Spei, Spi, Spu, Ste, Sti, Su, Su, Su, Su, Sy, Sy, Ta, Tae, Tau, Te, Te, Te, Te, The, The, Thu, Thu, Thy, Ti, Ti, Ti, To, To, Tra, Tri, U, U, U, V, Va, Va, Ve, Ve, Ve, Vhoo, Vi, Vi, Vi, Vi, Vi, Vla, Vo, Wa, We, Whi, Wo, Wu, Xa, Xe, Xe, Xi, Xi, Xo, Y, Y, Y, Ya, Ya, Ya, Ya, Ya, Ya, Ye, Yi, Yi, Yi, Yli, Yu, Yu, Z, Za, Za, Zi, Zly, Zu"
			;
			
	public static final String alienvillage_root_sylBegin_default =
			"-Lu, 'gi, 'gy, 'i, 'nu, 'u, 'y, 'yi, 'yo, 'za, a, a, ae, au, ba, bau, bbi, bey, bi, bi, bli, bu, ca, ca, cce, cchi, cci, cci, ce, cha, che, che, chy, ckla, cle, clea, clo, co, co, co, co, cre, cta, cta, cta, cti, cu, dda, ddi, de, de, de, di, di, di, di, di, di, dlee, dley, dley, do, do, e, e, e, e, e, fi, fne, g, g'gia, ga, ga, ga, ga, ga, ge, ge, ge, gfrie, ggai, ggli, ggo, ggo, gi, gi, gne, gni, go, go, gri, hi, i, i, iu, ka, kie, ko, kra, ku, l-Ya, l-ya, l'g, la, lae, lblo, lde, ldha, ldro, le, ley, lgo, li, li, li, li, li, li, li, li, li, lko, lla, lla, lle, lli, lly, lmi, lmi, lo, lo, lpe, lphe, lpho, lpi, ltai, lve, lvi, ma, ma, ma, ma, ma, mbo, mbo, mbri, me, mi, mke, mma, mne, mni, mni, mo, mo, mo, mo, mp\u00e8, mpo, msde, mu, n_Co, n-Yu, na, na, na, nau, nco, nda, ndai, nde, ndi, ndr\u00e9, ndra, ndru, ne, ne, ne, ng-Mei, nge, nge, nghi, ngu, nhi, ni, ni, ni, ni, ni, nie, nna, nnse, no, no, no, nqui, nse, nstee, nsu, nti, nto, o, o, o, o, o, pa, pe, pe, pe, pe, pe, phae, phu, pi, pla, po, po, ppe, ppe, ppo, psha, pso, pu, r', ra, ra, ra, ra, ra, ra, ra, rbi, rby, rca, rce, rcha, rche, rchi, rchy, rco, rctu, rda, rde, rdi, rdu, re, re, rga, rga, rgae, rgi, rgo, rgo, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, rka, rka, rke, rke, rlo, rme, rmi, rna, rne, rno, ro, ro, rpa, rr-Kthu, rry, rsa, rse, rse, rta, rti, rti, rtne, ru, ru, rva, rwi, rza, sa, sca, schne, sco, sco, sco, shma, si, si, si, si, sne, so, spa, spe, sse, ssi, sta, ste, stli, sto, stra, stu, stu, t, t\u00e6, ta, ta, ta, te, te, te, tha, thi, tho, thu, tla, to, to, to, tre, tru, tta, tte, tti, ttro, tu, ty, tya, tzbe, u, va, vi, vi, vy, we, wi, wso, wu, xce, xu, yeu, z'ni, za, ze, zla, "
			+ "_Na, -A, -Li, -U, 'h, a, a, ba, cha, che, chi, ci, ci, cli, co, cu, de, de, de, di, do, do, du, e, e, e, e, e, g-Tha, ge, gra, h-e, i, i, i, i, i, i, i, khu, l, l_Za, l'mno, la, la, lae, ldi, ldi, ldti, le, lee, lfe, lgeu, lhau, lhu, li, li, li, lla, lla, lle, lle, lli, llo, llo, llya, lmay, lo, ls, lski, lso, lu, lze, ma, me, me, mi, mni, mo, mu, n\u00e6, na, nae, ndi, ndi, ne, nga, nge, nge, ni, ni, ni, ni, ni, ni, ni, nni, no, no, nsu, nti, nti, ntla, nu, nu, nu, o, o, o, o, pa, pha, ppu, ra, ra, ra, rdi, rge, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, rlo, rna, rna, rra, rtiu, ru, ru, ru, ru, ru, ru, sa, sh-Vho, shi, so, ssa, ssi, ssu, sta, ste, su, t, ta, ta, ta, ta, ta, te, te, th_Rho, th-Ghu, tha, tho, thu, ti, tia, tiu, to, to, tta, tu, tu, tu, tu, u, u, u, u, u, u, u, u, u, u, u, va, ve, vi, vi, vi, vi, vi, x'a, zy, "
			+ "a, ae, ae, ch'ya, cra, da, dae, de, de, de, di, do, du, e, e, ho, i, i, i, la, ldru, li, li, lli, mi, mi, ndi, ne, ni, ni, ni, no, no, nta, nti, ntu, o, o, ra, ra, ra, rchi, rchu, ri, ri, ri, ru, ru, si, ta, ta, ta, ta, ta, ta, ta, ti, ti, ti, ti, tra, tu, tu, u, u, u, u, u, u, u, u, u, u, u, u, vi, ya, "
			+ "a, a, ae, de, le, ni, ni, nse, nti, nu, se, ti, ti, ti, ti, ti, ti, ti, u, u, u, u, "
			+ "ae, ru"
			;
	
	public static final String alienvillage_root_terminal_default =
			"^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, b, b, c, c, ce, ch, ch, d, d, ffe, fft, g, g, h, h, hr, k, k, k, ksh, l, l, l, l, l, l, l, l, l, l, ld, ldt, lf, lff, ll, lle, lt, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, mph, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, nc, nck, nck, nd, nd, nd, nd, nd, ng, nks, ns, ns, nz, p, pff, ph, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, rd, rd, rd, rd, re, re, rg, rl, rp, rre, rt, rth, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, se, sh, sle, ss, st, t, t, th, th, th, th, th, th, th, th, th, th, th, th, tte, tzsch, v, v, v, v, v, v, v, v, w, w, wes, x, x, x, z, z"
			;
	
	public static final String alienvillage_suffix_default =
			""
			;

	public static final int[] alienvillage_syllable_count_weights = new int[]{
			25, 158, 123, 55, 20, 2
			};
	
	
	
	/*
	 * Custom name pieces
	 */
	public static final String custom_prefix_default =
			""
			;
	
	public static final String custom_root_initial_default =
			"No"
			; 
			
	public static final String custom_root_sylBegin_default =
			""
			;
	
	public static final String custom_root_terminal_default =
			"tch"
			;
	
	public static final String custom_suffix_default =
			""
			;

	public static final int[] custom_syllable_count_weights = new int[]{1, 0, 0, 0, 0, 0, 0};
	
}
