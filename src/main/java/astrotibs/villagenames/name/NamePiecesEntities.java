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
	public static final String VILLAGER_PREFIX_DEFAULT =
			""
			;
	
	public static final String VILLAGER_ROOT_INITIAL_DEFAULT = 
			"\u00C0, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, Aa, Aa, Aa, Ab, Ae, Ai, Ai, Ai, Ai, Ai, Aj, Au, Ay, Ay, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Bai, Be, Be, Be, Be, Be, Be, Be, Be, Be, Be, Be, Bi, Bi, Bi, Bi, Bi, Bi, Bi, Bi, Bla, Ble, Bo, Bo, Bo, Bo, Bo, Bo, Bo, Bra, Bra, Bra, Bra, Bri, Bri, Bru, Bu, Bu, Ca, Ca, Ca, Ca, Ca, Ca, Ca, Ca, Ca, Cai, Ce, Ce, Ce, Ce, Cha, Cha, Cha, Cha, Cha, Che, Chi, Chi, Chia, Chlo, Chlo, Chri, Chri, Chri, Chri, Chri, Chry, Ci, Ci, Ci, Ci, Clou, Clu, Co, Co, Co, Cri, Cri, Cry, Cy, Da, Da, Da, Da, Da, Da, Da, Da, Da, Da, Da, Da, Da, Da, Da, Da, Da, Da, Da, Da, Da, Dai, Dai, De, De, De, De, De, De, De, Deu, Di, Di, Di, Di, Do, Do, Do, Do, Do, Do, Do, Do, Dra, Du, Du, Du, Dua, Dy, Dy, Dy, Dy, Dy, Dzo, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, Ea, Ei, El, Fa, Fa, Fa, Fa, Fa, Fa, Fa, Fa, Fa, Fa, Fa, Fa, Fa, Fa, Fa, Fa, Fa, Fa, Fa, Fe, Fe, Fi, Fi, Fla, Flo, Flo, Fo, Fo, Fra, Fra, Fra, Fre, Fre, Fri, Fu, Fu, G', G\u00fc, Ga, Ga, Ga, Ga, Ga, Ga, Ga, Ga, Ga, Ga, Ga, Ga, Ga, Ga, Gae, Gau, Ge, Geo, Geo, Geo, Geo, Geo, Geo, Geo, Geo, Geo, Gi, Gi, Gi, Gi, Gio, Giu, Giu, Glo, Glo, Go, Go, Go, Go, Gre, Gu, Gu, Guo, Guy, Ha, Ha, Ha, Ha, Ha, Ha, Ha, Ha, Ha, Ha, Ha, Ha, Ha, Ha, Ha, Ha, Ha, Ha, Ha, Ha, Ha, Ha, Ha, Ha, Ha, Ha, Ha, Ha, Hay, He, He, He, He, He, He, He, Hei, Hei, Hi, Hi, Hi, Hi, Hi, Hi, Ho, Ho, Ho, Ho, Ho, Hu, Hu, Hu, Hui, Hui, I, I, I, I, I, I, I, I, I, I, I, I, I, I, I, I, I, I, I, I, I, I, I, Io, Io, J\u00fa, Ja, Ja, Ja, Ja, Ja, Ja, Ja, Ja, Ja, Ja, Ja, Ja, Ja, Ja, Ja, Ja, Ja, Ja, Ja, Ja, Ja, Ja, Ja, Ja, Ja, Ja, Ja, Jay, Jay, Jay, Je, Je, Je, Je, Je, Je, Ji, Ji, Ji, Jia, Jie, Jo, Jo, Jo, Jo, Jo, Jo, Jo, Jo, Jo, Jo, Jo, Jo, Jo, Jo, Jo, Jo, Jo, Jo, Joe, Joe, Joe, Ju, Ju, Ju, Ju, Ju, Ju, Ju, Ju, Jua, Jua, Jua, Jua, Ka, Ka, Ka, Ka, Ka, Ka, Ka, Kai, Kai, Ke, Ke, Ke, Ke, Kha, Ki, Ki, Ki, Ki, Ki, Ki, Ki, Ki, Ko, Ko, Ko, Kri, Kri, Kse, Ku, L\u00e9, L\u00e9, La, La, La, La, La, La, La, La, La, Lau, Lau, Lau, Le, Le, Le, Le, Le, Le, Le, Le, Le, Lei, Li, Li, Li, Li, Li, Li, Li, Li, Li, Li, Li, Li, Li, Li, Li, Li, Lia, Lia, Lo, Lo, Lo, Lo, Lo, Lou, Lou, Lou, Lou, Lou, Lu, Lu, Lu, Lu, Lu, Lu, Lu, Lu, Lu, Lu, Lu, Lu, Lu, Lu, Lu, Lu, Lu, Lu, Lu, Lu, Lu, Lu, Lu, Lu, Ly, M\u00e1, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Maa, Mau, Me, Me, Me, Me, Me, Mei, Mi, Mi, Mi, Mi, Mi, Mi, Mi, Mi, Mi, Mi, Mi, Mi, Mi, Mi, Mi, Mi, Mi, Mi, Mi, Mi, Mi, Mi, Mi, Mo, Mo, Mo, Mo, Mo, Mo, Mo, Mo, Mo, Mo, Mo, Mo, Mo, Mo, Mo, Mo, Mo, Mo, Mo, Mo, Mou, Mu, Mu, Mu, Mu, Mu, Mu, Mu, Mu, My, My, Na, Na, Na, Na, Na, Na, Na, Na, Na, Naa, Ne, Ne, Ne, Ne, Ni, Ni, Ni, Ni, Ni, Ni, Ni, Ni, Ni, Nie, No, No, No, No, No, No, No, No, No, No, No, No, No, No, No, No, Noo, Nu, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, Pa, Pa, Pa, Pa, Pa, Pau, Pau, Pau, Pe, Pe, Pe, Pe, Pe, Pe, Phae, Phi, Pi, Pi, Pi, Po, Po, Pre, Pri, Pri, Pu, Py, Qia, Qio, Qui, R\u00f3, Ra, Ra, Ra, Ra, Ra, Ra, Ra, Ra, Rai, Rai, Ray, Re, Re, Re, Re, Re, Re, Re, Ree, Ree, Reu, Rey, Rhi, Ri, Ri, Ri, Ri, Ri, Ri, Ri, Ro, Ro, Ro, Ro, Ro, Ro, Ro, Ro, Ro, Ro, Ro, Ro, Ru, Ru, Ru, Ru, Ru, Ry, Ry, Ry, Sa, Sa, Sa, Sa, Sa, Sa, Sa, Sa, Sa, Sa, Sa, Sa, Sa, Sa, Sa, Sa, Sa, Sa, Sa, Sa, Sa, Sa, Sa, Sa, Sa, Sca, Sco, Sco, Sco, Se, Se, Se, Se, Se, Se, Se, Se, Se, Se, Se, Se, Sei, Seo, Seo, Seo, Sha, Sha, Sha, Sha, Shay, Shei, Shi, Shie, Shri, Shu, Shu, Shu, Si, Si, Si, Si, Sji, Sla, Smi, Smi, Smy, So, So, So, So, So, So, So, So, So, So, So, So, So, So, So, So, So, So, So, So, So, So, Sou, Squa, Sta, Sta, Sta, Ste, Ste, Ste, Ste, Ste, Stra, Su, Su, Su, Su, Su, Ta, Ta, Ta, Ta, Ta, Ta, Ta, Ta, Tao, Te, Te, Te, Te, Te, Te, Te, Te, Te, Tha, The, Tho, Tho, Tho, Ti, Ti, Ti, Ti, Ti, Ti, Ti, Ti, Ti, Ti, Ti, To, To, To, To, To, To, To, To, Tre, Tre, Tri, Tsu, Tsu, U, U, U, U, U, Va, Va, Va, Va, Va, Ve, Vi, Vi, Vi, Vi, Vi, Vi, Vi, Vi, Vi, Vi, Vo, Vo, Vu, Wa, Wa, Wa, We, We, We, We, Wei, Wei, Wi, Wi, Wi, Wi, Wi, Wi, Wi, Wi, Wi, Wi, Wi, Xa, Xa, Xa, Xe, Xi, Xia, Xia, Xu, Ya, Ya, Ya, Ya, Ya, Ye, Ye, Yi, Yo, You, You, You, Yu, Yu, Yu, Yu, Yu, Yu, Za, Za, Za, Za, Za, Ze, Ze, Ze, Ze, Ze, Zey, Zhe, Zi, Zi, Zo, Zo, Zo, Zo, Zo, Zu"
			;
		
	public static final String VILLAGER_ROOT_SYL_BEGIN_DEFAULT =
			"_Ca, _Cla, _Mi, -fe, -hao, -hui, -ju, -yeo, -yu, \u00e1, \u00e1, \u00e3o, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, ao, ao, ba, ba, ba, ba, ba, ba, ba, ba, ba, bby, bda, bde, bdu, be, be, be, be, be, be, be, be, be, be, be, be, be, bi, bi, bi, bi, bi, bli, bo, bo, bra, bri, bri, bri, bri, bri, bri, bu, bu, bu, by, by, c\u00ed, c\u00ed, c\u00ed, ca, ca, ca, ca, ca, ca, ca, ca, ca, ca, ca, ca, ce, cemea, cha, chae, chae, che, chi, chi, ci, ci, ci, cia, cia, cie, ciou, ckey, ckso, cly, co, co, cque, cto, cto, cto, cy, da, da, da, da, da, da, da, da, da, da, da, da, dda, de, de, de, de, de, de, de, de, de, de, de, de, dga, di, di, di, di, di, dley, do, do, dou, dra, dri, dri, dva, dwa, dy, dya, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, ey, f\u00ed, f\u00ed, fa, fa, fa, fa, fa, fa, fe, fe, ffa, ffa, ffie, fi, fi, fi, fi, fi, fi, fi, fi, fi, fi, fi, fka, fre, fu, fya, ga, ga, ga, ga, ga, ga, ga, ga, gai, gai, gde, gge, gge, ggy, ghe, ghu, gi, gi, gi, gne, gnu, go, go, go, go, go, go, go, go, go, gu, gu, gu, gue, gue, h-mi, ha, ha, ha, ha, ha, ha, ha, ha, ha, ha, ha, ha, ha, ha, ha, ha, ha, ha, ha, ha, ha, ha, hdi, he, hei, hi, hi, hma, hma, hma, hma, hme, hme, hme, hmou, hn_Pau, hnny, hra, hra, hra, i, i, i, i, i, i, i, i, i, i, i, i, i, ia, ia, j, ja, ja, ja, ja, ja, ja, ja, ja, ja, ji, ji, jo, ka, ka, ka, ka, ka, ka, ka, ka, ka, ka, ka, ka, kae, kau, ke, kha, khai, ki, ki, ki, ki, ki, kka, kke, kki, kko, ko, ko, ko, ko, ko, ko, ko, ko, ksa, ksa, ksi, kto, kto, ku, ku, ku, ku, ku, ku, l_Ha, l-Fa, l-Ra, la, la, la, la, la, la, la, la, la, la, la, la, la, la, la, la, la, la, la, la, la, la, la, la, la, la, la, la, la, lay, lbe, lchi, lda, lda, lda, lda, ldo, le, le, le, le, le, le, le, le, le, le, le, le, le, le, le, le, le, le, le, le, le, le, le, le, le, le, le, le, le, lea, ley, lfre, lga, lga, lga, lge, lgi, lhe, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, li, lia, lii, lka, lla, lla, lla, lla, lla, lla, lla, lla, lla, lla, lla, lla, lla, lle, lle, lle, lle, lli, llia, llia, llia, llia, llia, llia, llia, llo, llo, lly, lly, lly, lly, lma, lma, lme, lna, lo, lo, lo, lo, lo, lo, lo, lpe, lphie, lpu, lsa, lso, lta, lte, lthe, lthe, lti, lu, lu, lu, lu, lvi, lvi, lvi, ly, ly, ly, ly, ly, ly, ly, ly, ly, ly, m\u00f3, ma, ma, ma, ma, ma, ma, ma, ma, ma, ma, ma, ma, ma, ma, ma, ma, ma, ma, ma, ma, ma, ma, ma, ma, ma, ma, ma, ma, ma, ma, maa, mchai, me, me, me, me, me, me, me, me, me, me, me, me, me, me, me, me, me, me, me, me, me, meme, mi, mi, mi, mi, mi, mi, mi, mi, mi, mi, mi, mi, mi, mi, mi, mi, mi, mi, mi, mi, mi, mi, mi, mi, mi, mi, mi, mja, mma, mma, mma, mma, mma, mma, mma, mma, mma, mma, mma, mma, mma, mma, mma, mma, mma, mma, mma, mma, mma, mma, mma, mme, mme, mme, mmi, mmy, mmy, mo, mo, mpa, mpy, mri, msa, mu, mu, my, my, my, mza, n_Da, n-ju, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, nbaa, nca, nca, nce, nce, nce, nce, nce, nce, ncho, nci, nci, nda, nda, nda, nda, nde, nde, nde, nde, nde, nde, nde, ndra, ndre, ndre, ndre, ndre, ndrei, ndsay, ndsay, ndy, ndy, ndy, ndy, ndy, ndy, ndy, ndy, ndy, ne, ne, ne, ne, ne, ne, ne, ne, nei, ng_Wei, nga, nge, nge, ngho, ngqi, ngu, ni, ni, ni, ni, ni, ni, ni, ni, ni, ni, ni, nie, nie, nie, nie, nio, nja, nja, nja, nja, nje, nla, nley, nna, nna, nna, nna, nna, nna, nna, nna, nna, nna, nna, nna, nna, nna, nna, nna, nne, nni, nnie, nno, nno, nny, nny, nny, no, no, no, no, no, no, no, no, no, nou, nry, nry, nry, nso, nta, nte, nthe, ntho, nti, nti, nti, nti, nti, nti, nti, nti, nu, nue, nwu, ny, ny, ny, ny, ny, nya, nya, nye, nze, nzhu, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, pa, pa, pa, pha, pha, phe, phe, phi, phi, phi, phi, phi, phie, pho, ppe, pu, pu, pu, r-A, r\u00ed, r\u00ed, r\u00ed, r\u00ed, ra, ra, ra, ra, ra, ra, ra, ra, ra, ra, ra, ra, ra, ra, ra, ra, ra, ra, ra, ra, ra, ra, ra, ra, ra, ra, ra, ra, ra, ra, rba, rbe, rcu, rcu, rdna, rdo, re, re, re, re, re, re, re, re, re, re, re, re, re, re, rey, rga, rga, rgda, rgei, rghe, rgi, rgi, rgi, rgi, rgo, rgr\u00e9, rgre, rgrje, rhi, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, ri, rie, rie, rie, rie, rii, rje, rkai, rke, rko, rku, rla, rla, rla, rla, rle, rle, rlie, rlie, rlo, rlo, rlo, rlo, rma, rmaa, rme, rmi, rna, rne, ro, ro, ro, ro, ro, ro, ro, ro, ro, ro, ro, ro, roo, rpe, rphy, rre, rre, rre, rri, rri, rri, rry, rry, rry, rry, rry, rse, rsu, rte, rte, rtho, rti, rti, rti, rti, ru, ru, ru, rvey, rvi, rvi, rwa, ry, ry, ry, ry, ry, ry, ry, rya, rya, rzo, s\u00e9, sa, sa, sa, sa, sa, sa, sa, sa, sa, sci, sco, sco, se, se, se, sey, sh, sha, sha, sha, shley, shna, shoi, shoo, shqa, shu, si, si, si, si, si, si, sia, sie, ska, ska, smi, smu, sna, sna, snaa, sni, so, so, so, spe, ssa, ssa, sse, sse, sse, sse, ssei, ssi, ssi, ssie, ssie, st\u00ed, st\u00edn, sta, sta, sta, sta, sta, sta, ste, sti, sti, sti, sti, sti, stia, sto, sto, stu, stu, su, su, sy, sy, t\u00e9, t\u00ed, ta, ta, ta, ta, ta, ta, ta, ta, ta, ta, ta, tche, te, te, te, te, te, te, tha, tha, tha, tha, tha, thy, ti, ti, ti, ti, ti, ti, ti, ti, ti, tkhaa, tma, tma, to, to, to, to, to, to, tou, tra, tri, tri, tri, tsi, tsu, tsu, tsu, tsu, tte, tthe, tthi, tti, tto, tviy, tze, u, u, u, u, va, va, va, va, va, va, va, va, vaa, ve, ve, ve, ve, ve, ve, ve, ve, vi, vi, vi, vi, vi, vi, vi, vi, vi, vi, vi, vi, vi, vi, vi, vi, vi, vi, vi, vi, vi, vi, vie, vo, vy, vy, wa, we, we, wi, wkwi, wre, xa, xa, xa, xa, xa, xa, xbloo, xe, xi, xi, xi, xi, xte, ya, ya, ya, ya, ya, ya, ya, ya, ya, ya, ya, ya, ya, ya, z, za, za, za, za, za, ze, ze, ze, zi, zoo, zoo, zta, zzie"
			;
	
	public static final String VILLAGER_ROOT_TERMINAL_DEFAULT =
			"^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, "
			+ "a, b, b, b, b, b, b, b, b, b, b, b, bs, c, c, c, c, ce, ce, ce, ch, ck, ck, ck, ck, ck, ck, ck, ck, ck, cke, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, d, de, dge, dge, dge, e, f, f, f, f, f, f, f, f, f, f, ff, ff, g, ggs, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, h, hd, hd, hn, hn, hn, hn, hn, hr, k, k, k, k, k, k, k, k, k, k, k, ke, ke, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, ld, le, le, les, ll, ll, ll, ll, ll, ll, ll, ll, ll, lle, lm, lm, lm, lt, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, mes, mes, mes, mes, mes, mes, mie, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, nce, nd, nd, nd, nd, nd, nde, ne, ne, ne, ne, ne, ne, ne, ne, ne, ne, ne, ne, ne, ne, ne, ne, ne, ne, ne, ng, ng, ng, ng, ng, ng, ng, ng, ng, ng, ng, ng, ng, ng, ng, ng, ng, ng, ng, ng, nh, nj, nk, nk, nk, nk, nn, nn, nn, nne, nne, nsh, nt, nt, nt, nt, p, p, ph, ppe, ps, q, q, que, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, rc, rc, rc, rd, rd, rd, rd, rde, rge, rge, rge, rge, rk, rk, rl, rl, rles, rm, rn, rn, rre, rt, rt, rts, rtz, rx, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, sch, se, se, se, se, se, sh, sh, sh, sh, ss, ss, ss, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, tch, tch, tch, tch, te, te, te, te, th, th, th, th, th, th, th, th, th, th, th, th, th, tt, tt, tt, tt, tte, tte, v, ve, ve, w, w, w, x, x, x, x, x, x, x, z"
			;
	
	public static final String VILLAGER_SUFFIX_DEFAULT =
			""
			;
	
	public static final int[] VILLAGER_SYLLABLE_COUNT_WEIGHTS = new int[]{
			194, 643, 309, 76, 4
			};
	
	public static final int[] VILLAGER_BLANK_TERMINAL_COUNTS = new int[]{
			26, 290, 185, 57, 3
			};
	
	
	
	/*
	 * Angel name pieces
	 */
	public static final String ANGEL_PREFIX_DEFAULT =
			""
			;
	
	public static final String ANGEL_ROOT_INITIAL_DEFAULT =
			"A, A, A, A, A, A, A, A, A, A, A, A, Ba, Ba, Ca, Ca, Che, Da, Da, Du, E, E, Fe, Fe, Ga, Ga, Ha, Ha, Ha, Ha, Ha, Ha, Ha, Ha, He, He, Ho, I, I, I, Je, Je, Je, Je, Je, Ji, Jo, Ka, Ka, Ke, Ke, Ke, Kha, Ki, Ku, La, Le, Lu, Ma, Maa, Me, Me, Mi, Mi, Mi, Mo, Mu, Mu, Mu, Na, Na, Ne, Ni, Nu, O, O, Pa, Pe, Pha, Po, Pra, Pu, Qa, Ra, Ra, Ra, Ra, Ra, Rai, Re, Ri, Sa, Sa, Sa, Sa, Sa, Sa, Sa, Sche, Se, Se, Sha, Sha, Shi, Si, Te, Te, Tu, U, U, Va, Ve, Wor, Za, Za, Za, Za, Ze, Ze, Zo"
			;
			
	public static final String ANGEL_ROOT_SYL_BEGIN_DEFAULT =
			"'a, ba, ba, ba, bra, bri, cha, cha, chi, ci, d, de, de, dki, dki, dra, dra, dri, dri, fa, fni, gu, gu, ha, ha, ha, ho, hu, ka, kbi, ki, kshmi, la, le, le, li, li, lka, ma, ma, ma, ma, ma, ma, ma, me, mha, mi, mi, mna, msi, mu, mu, mwoo, na, na, nda, ne, ni, ni, ni, nka, nni, no, nri, nri, nu, pha, pha, pha, pha, phi, phi, phki, pho, phsi, po, qu, ra, ra, ra, ra, ra, ra, ra, ra, rbi, re, ri, ri, ri, ri, ri, ri, ro, rti, ru, ru, ru, ru, se, shi, shma, si, sra, sra, ssi, su, su, ta, tha, tza, va, vu, ye, za, zi, zi, zra, zra, "
			+ "'i, 'i, a, bi, bi, chi, ddo, di, di, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, fe, fe, fi, hi, hme, i, i, i, i, i, la, lchu, le, li, lly, lpho, ma, mi, mi, mpha, mu, ni, ni, ni, ni, ni, phi, phi, qi, qqi, qui, ra, re, re, ri, ri, si, tha, thi, tro, va, ya, ze, "
			+ "'i, a, a, a, a, ba, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, e, o, rae, ri, ri, "
			+ "a, e"
			;
	
	public static final String ANGEL_ROOT_TERMINAL_DEFAULT =
			"^, ^, ^, ^, ^, ^, ^, "
			+ "ch, d, h, h, h, h, h, h, h, k, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, n, n, n, n, n, n, n, n, n, r, r, r, r, r, r, r, s, s, t, t, t, t, th"
			;
	
	public static final String ANGEL_SUFFIX_DEFAULT =
			""
			;

	public static final int[] ANGEL_SYLLABLE_COUNT_WEIGHTS = new int[]{
			0, 22, 68, 28, 2
			};

	public static final int[] ANGEL_BLANK_TERMINAL_COUNTS = new int[]{
			0, 2, 3, 2, 0
			};
	
	
	
	/*
	 * Demon name pieces
	 */
	public static final String DEMON_PREFIX_DEFAULT =
			""
			;
	
	public static final String DEMON_ROOT_INITIAL_DEFAULT =
			"\u00d6, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, Aa, Ae, Ai, As, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Baa, Bae, Be, Be, Be, Be, Be, Be, Be, Bhu, Bi, Bi, Bo, Bo, Bu, Bu, Bu, Bue, By, Ca, Ca, Ca, Ca, Caa, Caa, Cai, Ce, Cha, Cha, Che, Cho, Cho, Ci, Ci, Cla, Co, Cro, Cu, D, Da, Da, Da, Da, Da, Da, Dao, De, De, De, De, Di, Dji, Dre, E, E, E, Ei, Ex, Fla, Flau, Fo, Fo, Fo, Fo, Fo, Fo, Fu, Fu, Ga, Ga, Ga, Gaa, Gau, Ghou, Gla, Gla, Go, Go, Gre, Gre, Gri, Gu, Gu, Gu, Gu, Gua, Gua, Ha, Ha, Ha, Ha, Haa, Hau, Hei, Hi, I, I, I, I, I, I, If, Ji, Ji, Ka, Ka, Ka, Ka, Ka, Ki, Ko, Kra, Kro, Ku, Ku, Le, Le, Le, Le, Le, Le, Le, Leo, Li, Li, Li, Li, Lju, Lu, Ly, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Me, Me, Me, Me, Me, Mo, Mo, Mu, Na, Na, Na, Naa, Ne, Ne, Ni, O, O, O, O, O, O, O, O, Pa, Pa, Pai, Pe, Pe, Phe, Pho, Pi, Po, Po, Pre, Pro, Pru, Pu, Ra, Ra, Ra, Rau, Ro, Ru, Ru, S, Sa, Sa, Sa, Sa, Sa, Sa, Sca, Se, Sei, Sha, Shai, She, Si, Si, So, Sti, Sto, Su, Su, Sua, Ta, Ta, Ti, Ti, To, Tri, Tu, Twi, U, Va, Va, Va, Va, Va, Ve, Vi, We, We, We, Ye, Za, Ze, Zi, Zu"
			;
			
	public static final String DEMON_ROOT_SYL_BEGIN_DEFAULT =
			"_To, ba, ba, be, be, be, bha, bi, bi, bi, bli, bli, blo, bno, bo, bo, by, ca, ca, ccu, ce, ce, chie, chu, chu, ci, co, cri, cro, cu, de, dea, dee, di, dra, du, du, e, e, fla, fri, fri, fro, ga, ga, ga, ge, gi, gi, gio, gna, go, go, gra, ha, ha, he, he, hi, hri, jja, ka, ka, ka, ka, ka, ke, ki, ki, ko, ko, kshaa, ku, ku, kva, la, la, la, la, la, la, la, la, lbe, le, le, le, le, le, lgo, li, li, li, li, li, li, li, li, li, llo, llo, llu, lo, lo, lo, lpha, lpha, lphe, lpsa, lsu, lthu, lu, lve, ma, ma, ma, may, mbha, mdu, me, mei, mi, mi, mi, mmo, mo, mo, mo, mo, mo, mo, mo, mo, mo, mpo, mpu, mta, my, mya, na, na, nci, ncu, nde, ndha, ndi, ndi, ndra, ndre, ndro, ne, ne, ne, nga, nggi, ngra, ni, ni, ni, nja, nni, no, no, nshee, nta, nta, nti, nti, nu, nzu, o, pa, pa, pa, pe, pe, phi, pho, po, po, pu, qo, ra, ra, ra, ra, ra, ra, ra, ra, raii, rba, rba, rba, rbe, rca, rca, rchai, rcho, rcho, rcu, rd\u00f6, re, re, ree, rfu, rga, rgo, rgu, ri, ri, ri, ri, ri, ri, rli, rma, rmi, rmu, rne, ro, ro, ro, ro, rra, rso, rthi, ru, ru, ru, rva, sa, sa, sa, sa, sb, se, she, shma, shya, si, si, si, sma, smo, so, so, ssa, ssi, ssya, sta, ste, su, sya, ta, ta, ta, ta, ta, ta, the, thi, thi, thi, thy, ti, ti, to, to, tri, tsco, v-e, vi, vi, vre, vro, ya, yo, yo, za, za, zi, zo, zu, zu, "
			+ "_\u0130, _Da, _Ma, _Mai, _N, _Se, -La, 'e, a, a, a, a, a, a, a, a, ba, ba, bi, bu, bu, ca, ca, ce, ce, cha, chu, dai, ddo, de, do, dya, e, e, e, fa, fa, fi, ge, gi, gli, go, go, go, go, go, go, he, i, i, i, ie, je, je, ka, ka, ka, kku, la, la, lcha, li, li, lka, lly, lma, lo, lze, m_Ma, ma, ma, ma, ma, ma, me, mme, mme, mo, mo, mo, mo, mo, mue, na, nda, nda, ndha, ni, ni, ni, nno, no, nti, nzo, o, o, o, pha, pha, pho, r'e, ra, ra, re, re, ri, ri, ri, ri, ri, ri, ro, ro, rta, ru, ry, ry, ry, ry, sa, si, si, si, si, ske, sta, sto, sto, stro, su, ta, ta, ti, to, u, u, u, va, va, va, ve, vi, y, z'e, za, ze, ze, zou, zu, zzo, "
			+ "-La, a, a, a, a, a, be, bi, bo, bo, bu, ccia, e, ge, gi, ha, la, laa, le, le, li, li, llu, lphu, na, na, na, na, nki, nte, nyu, o, o, o, phe, phe, pi, ra, rgo, rna, rna, ste, su, ta, tha, thi, u, u, vi, ye, "
			+ "a, a, bo, bou, ka, la, la, le, ne, pe, ra, re, ri, ssu, u, "
			+ "li"
			;
	
	public static final String DEMON_ROOT_TERMINAL_DEFAULT =
			"^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, "
			+ "b, b, c, c, c, ch, ch, ch, ch, ck, d, d, d, dh, f, g, g, h, h, h, h, k, k, k, k, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, ll, ll, m, m, m, m, m, m, m, m, m, m, m, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, ne, ne, ng, ng, nn, nn, nn, ns, nth, p, p, pt, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, rck, rd, rge, rt, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, sh, t, t, t, t, t, t, t, t, t, th, th, th, th, th, th, th, th, ts, x, x, x, x, x, x"
			;
	
	public static final String DEMON_SUFFIX_DEFAULT =
			""
			;

	public static final int[] DEMON_SYLLABLE_COUNT_WEIGHTS = new int[]{
			19, 133, 101, 35, 14, 1
			};

	public static final int[] DEMON_BLANK_TERMINAL_COUNTS = new int[]{
			1, 29, 40, 12, 7, 0
			};
	
	
	
	/*
	 * Dragon name pieces
	 */
	public static final String DRAGON_PREFIX_DEFAULT =
			""
			;
	
	public static final String DRAGON_ROOT_INITIAL_DEFAULT =
			"A, A, A, A, A, A, A, A, Ai, B, Ba, Ba, Ba, Bo, Boi, Co, Co, Cu, Ddrai, Dr\u00e1, Dra, Dra, Dre, E, E, E, E, Fa, Go, Gui, Gyo, He, Hua, Hy, I, I, I, J\u00f6, Ku, Ku, Ku, L', L\u00f3, La, La, Le, Li, Lo, Mu, N\u00ed, Na, Nea, No, O, Ou, Pa, Py, Q', Qi, Que, Que, Ry, Ry, S\u00e1, Sei, Sli, Smo, Sy, Te, Te, The, Ti, Ti, Ty, Ve, Vi, Vri, Wy, Wy, Xiu, Ya, Yi, Yo, Za, Zbu, Zi, Zi, Zi, Zmei, Zo"
			;
			
	public static final String DRAGON_ROOT_SYL_BEGIN_DEFAULT =
			"\u00e9, a, a, bi, bre, bzu, ca, co, dh\u00f6, do, do, dra, fni, ga, ga, go, go, ha, jda, jde, ju, kha, k, ko, ku, ku, ku, la, la, lau, lbe, ldra, lla, llu, lshe, ma, mo, moo, ndwor, nglo, nglo, nri, nu, pa, pe, pho, po, ra, ra, re, re, re, rgo, rk\u00e1, rmu, rne, rni, ro, ro, ry, ryu, sha, shu, ta, ta, tho, tra, tza, tza, u, u, u, ve, ve, vi, vre, vre, za, zhi, "
			+ "_Ce, _Da, _Ja, _We, a, a, bo, chi, chi, dra, g\u00e4, gg, gi, ha, ji, la, lc\u00f3, lco, le, lka, ma, ma, mu, na, na, nga, ngba, nky, nsky, nsu, ny, ny, o, phi, q', rfl\u00f3, rha, ru, ssu, t\u00e1, ta, to, tra, ya, ze, "
			+ "a, a, bre, do, gu, ha, la, le, nd, nka, ro, t, tha, tso, u, wa, "
			+ "a, ka, ma, rmu, t, t"
			;
	
	public static final String DRAGON_ROOT_TERMINAL_DEFAULT =
			"^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, "
			+ "c, ch, g, ge, k, k, k, l, l, l, l, l, m, m, n, n, n, n, n, n, n, n, n, n, n, n, n, ng, ng, ng, ng, nt, nt, p, p, q, r, r, r, r, r, r, r, rm, rn, s, s, s, s, sque, t, t, t, t, t, th, tz"
			;
	
	public static final String DRAGON_SUFFIX_DEFAULT =
			""
			;

	public static final int[] DRAGON_SYLLABLE_COUNT_WEIGHTS = new int[]{
			12, 32, 30, 10, 6
			};

	public static final int[] DRAGON_BLANK_TERMINAL_COUNTS = new int[]{
			2, 11, 14, 4, 2
			};
	
	
	
	/*
	 * Golem name pieces
	 */
	public static final String GOLEM_PREFIX_DEFAULT =
			""
			;
	
	public static final String GOLEM_ROOT_INITIAL_DEFAULT =
			"\u00c9, \u0130, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, Ae, Ba, Ba, Ba, Bha, Bi, Bri, Bu, Bu, Ca, Cha, Che, Chri, Co, Co, D, Da, Da, Do, Du, Du, E, E, E, E, E, E, E, Ei, Eu, Fe, Fe, Fra, Fre, Fre, Frie, Gau, Ge, Ge, Ge, Geo, Go, Ha, Ha, He, He, He, He, He, Hja, Ho, Hua, I, I, I, Ja, Je, Je, Je, Jea, Jo, Jo, Jo, Jo, Joe, Ju, Jua, Ka, Ka, Kha, Kle, Kro, Ku, L\u00e9, La, La, Lai, Le, Le, Le, Li, Li, Lou, Lou, Lu, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Me, Mi, Mo, Mu, Na, Naa, Ne, Ne, Ne, Ni, Ni, Ni, No, Nu, O, O, O, Ou, Pe, Po, Ra, Ro, Ro, Sa, Sa, Sa, Sa, Sa, Sa, Se, Shi, Si, Si, Ta, Te, Tha, The, The, Tho, Ti, U, Va, Va, Vai, Ve, Vi, Vi, Vla, Vu, Wa, Wi, Wi, Wi, Wi, Wi, Ya, Ya, Zeu"
			;
			
	public static final String GOLEM_ROOT_SYL_BEGIN_DEFAULT =
			"be, be, bi, bra, bu, cchu, cho, cky, co, cto, da, de, de, de, dfrey, di, do, dra, dri, dwa, dwi, fre, ge, gha, hai, hi, hla, ho, ho, i, i, i, jo, ka, ke, ktha, la, la, lbe, lbre, lca, ldwi, le, le, lfri, lhe, li, li, li, li, li, li, lla, llia, llie, lma, lo, m\u00f3, ma, ma, ma, ma, mbi, mi, mi, mi, mi, mkhi, mo, my, mya, nchi, nci, nco, nda, ndi, ne, nghi, nri, nri, nry, nsto, nte, nu, o, o, o, o, o, o, o, o, pha, phi, phi, phro, po, po, r'e, ra, ra, ra, ra, ra, rdi, re, re, rga, rghe, ri, ri, ri, rma, rme, rmi, ro, ro, ro, rqui, rthu, rtli, ru, ry, s\u00e9, s\u00fa, sa, sca, se, se, sei, she, shi, shma, sme, sta, ste, ste, sto, su, ta, ta, te, the, ti, tla, to, tto, va, va, va, va, vi, vzi, xto, za, zi, zma, zra, "
			+ "a, a, a, bhbhai, bi, ca, co, de, di, do, do, do, do, e, e, e, e, e, e, e, e, e, e, e, e, fa, ha, hi, hmee, l\u00e9, la, li, llo, lo, lo, lpho, ly, ly, ma, ma, mi, msa, na, na, ngke, ni, ni, ni, ni, ni, ni, nna, o, o, o, ou, phe, po, po, rdi, ri, ri, ri, ri, ri, rta, sa, se, so, ta, tai, thy, ti, ti, try, tzky, ve, xa, xi, za, za, "
			+ "\u00f6, a, a, a, a, be, bha, da, e, e, ki, me, mu, na, nde, o, o, ri, si, te, tta, u, u, "
			+ "she, te, u, "
			+ "kia, sva, "
			+ "kia, ra"
			;
	
	public static final String GOLEM_ROOT_TERMINAL_DEFAULT =
			"^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, "
			+ "b, ch, cht, ck, d, d, d, g, h, h, h, hn, k, k, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, ld, ld, lls, lm, lt, m, m, m, m, m, m, m, m, m, m, m, mes, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, nd, ne, ng, nne, nne, p, ph, ph, r, r, r, r, r, r, r, r, r, rd, rd, re, rge, rke, rl, rles, rnst, rt, rt, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, se, t, t, th, th, x"
			;
	
	public static final String GOLEM_SUFFIX_DEFAULT =
			""
			;

	public static final int[] GOLEM_SYLLABLE_COUNT_WEIGHTS = new int[]{
			17, 77, 58, 20, 1, 0, 2
			};

	public static final int[] GOLEM_BLANK_TERMINAL_COUNTS = new int[]{
			1, 22, 19, 9, 0, 0, 1
			};
	
	
	/*
	 * Alien name pieces
	 */
	public static final String ALIEN_PREFIX_DEFAULT =
			""
			;
	
	public static final String ALIEN_ROOT_INITIAL_DEFAULT =
			"'Y, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, Ae, Ai, Ay, B, Ba, Ba, Ba, Bo, Bu, Bya, C, Chau, Co, Co, Cro, Cthaa, Cthae, Cthu, Cthu, Cthy, Cto, Cxa, Cy, Cy\u00e4, D, Da, Dhu, Di, Dray, Dry, Dy, Dy, Dz\u00e9, E, E, Ei, Ei, Fu, Ge, Gha, Ghi, Ghro, Ghro, Gi, Gla, Glee, Gloo, Go, Go, Go, Go, Go, Gtu, Gu, Gu, Gwa, Gz, H, Ha, Ha, Ha, Ha, Hai, Hna, Hziu, I, I, I, I, I, I, I, Ia, Ja, Je, Ju, K, Ka, Ka, Ka, Kaa, Kaa, Kau, Kha, Klaa, Klo, Klu, Kra, Krai, Kri, Ktha, Ktha, Ku, La, Le, Lloi, Lo, Lo, Lo, Lu, Ly, M, M, M, Ma, Mla, Mno, Mo, Mo, Mo, Mo, Mri, My, N, N, N, Na, Nee, Ngi, Ngy, No, No, Nu, Ny, Ny, Ny, Nya, Nya, Nyo, O, O, O, O, O, O, O, O, Oo, Ou, Pa, Pe, Pha, Pha, Po, Psu, Pta, Q, Qu, Qua, R, R, Ra, Ra, Raa, Rha, Rha, Rho, Ri, Ro, S, Sa, Saa, Sca, Se, Se, Se, Sfa, Sha, Sha, Sha, Sha, Shau, She, Shi, Shli, Sho, Shte, Shu, Shu, Shuy, Stha, Su, Su, Swa, Ta, Te, Th, Tha, Tha, Tha, Tha, Tho, To, Tru, Tsa, Tu, Tu, Tu, U, U, U, U, Ui, V, Ve, Vhu, Vi, Vi, Vo, Vo, Vo, Vu, Wa, X, Xa, Xa, Xe, Xi, Xi, Xi, Xi, Xo, Xo, Y, Y, Y, Y, Y, Y, Ya, Ya, Ya, Ye, Ye, Yha, Yha, Yhou, Yi, Yi, Yi, Yo, Yo, Yo, Yo, Yu, Yu, Yu, Z, Z, Za, Ze, Zha, Zi, Zi, Zo, Zo, Zu, Zvi"
			;
			
	public static final String ALIEN_ROOT_SYL_BEGIN_DEFAULT =
			"-Ga, -Ho, -Ka, -Kthu, -Ni, -Ra, 'ba, 'chte, 'e, 'gnu, 'go, 'li, 'lla, 'lo, 'na, 'Na, 'ne, 'ry, 'tha, 'to, 'tya, 'y, a, a, a, b-Ni, b-Te, b'mbu, ba, bb-Tst, bbi, bbo, be, bho, bho, bo, bo, bu, c'Naa, cha, chi, ckla, cn\u00e0, cra, cte, ctho, cto, cto, cu, d-Tha, da, dde_M, de, dh-yaa, dhra, dley, dme, e, g-Hoo, g-Ko, g-Sa, g-Si, g-So, g'Na, ga, gg-Ha, gg-Sha, ggdy, ggha, ggy, gha, gho, ghya, gna, gna, gni, gnu, go, go, go, go, gra, gtha, h'i, h'Thu, ha, ho, i, i, jh'Kaa, jha, k-Sha, ki, kla, ko, kra, kru, l_Tho, l-go, l'kru, la, la, lbe, lda, le, le-O, lgna, lhu, li, li, li, li, lko, lla, lo, lpo, lquo, ltha, lthoo, lti, lu, lu, lu, lzscha, m_Cru, ma, ma, mi, mma, mmu, mna, mo, mo, mqua, mu, mu, n-Te, na, nai, nda, nda, nde, nde, ndo, ndy, nee, ni, nlu, no, no, no, npe, ntoo, nu, o, o, o, o, o, pha, phoo, qua, r-A, r-Ko, r'la, ra, ra, ra, ra, rdi, rdne, rg\u00f2, rga, ri, rla, rlo, rmo, ro, ro, ro, rpa, rqu, rr, rra, rri, rry, rse, rte, rtho, rtll, ru, rva, rwa, sa, sai, sba, sei, sgu, sha, shtu, smi, so, sso, ssu, sta, sta, sta, stu, sty, t'U, ta, te, th-Go, th-Ho, th-O, tha, tha, tha, tha, tha, thne, tho, tho, tho, thu, thu, thya, ti, tla, tli, tli, tu, tzi, u, ue, va, vha, w'ke, w\u00e0, xa, xii, xtyo, xu, xu, ya, ya, yi, za, zho, zo, zta, "
			+ "-Ga, -Gha, -Sa, -Thu, -Y, -ya, 'e, 'i, 'ngo, 'so, \u00f1a, a, a, b_G, ch-Na, cll, dda, dda, do, do, do, e, fu, g, ga, ge, ge, gghua, gghua, ggi, ggu, ggua, ggua, ggua, gh-Yai, gha, gha, ghra, gnni, go, go, go, gtha, gtha, ht_Z, i, ii, ka, ka, ki, klu, ko, l_U, l\u00ff, la, la, la, lca, le, lhu, li, li, lka, lla, lla, lla, lls-H, llu, lo, lo, lo, lpha, lu, lu, lu, lyo, lzhe, m_Shai, m-Zha, ma, ma, mbra, mi, mi, mmo, mo, mo, mpha, n-Go, na, na, nai, nche, ndrra, ne, nnga, no, no, nu, o, pha, pi, pse, qua, r, r_Fau, r'i, ra, ra, rg_Ryo, rga, ri, ri, ro, ro, rra, rtha, rthua, ru, sa, sca, sh-Ho, sha, sha, shu, ssa, sui, ta, ta, th-a, th-Ka, th'lu, tha, the, thna, tho, tho, tho, tho, thrha, tii, tli, tse, tu, u, va, ve, wr, xtla, ya, ye, yi, zo, "
			+ "_Gwa, -B, -Tha, 'u, a, a, a, ba, bhy, cha, cu, d_E, de, g'Na, ga, ggua, gly, gm, go, hn, ka, ko, ku, la, lo, mghi, n'tho, na, ni, nsha, o, o, o, pa, r'He, ra, ra, rgo, rlo, sss, sua, te, th\u00e6, thi, thla, ttau, "
			+ "-se, 'n, a, e, gni, lle, n\u00ef, ndu, nzha, qqa, "
			+ "-Mo, \u00e9, rr'ro, "
			+ "l\u00fb"
			;

	public static final String ALIEN_ROOT_TERMINAL_DEFAULT =
			"^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, "
			+ "'st, b, b, bb, c, c, c, ch, ch, ck, ct, d, d, d, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, g, gh, gh, gn, h, h, h, h, h, h, hl, k, k, k, k, k, k, k, l, l, l, l, l, l, l, l, lbh, ll, ll, m, m, m, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, ng, ng, njh, nn, ns, p, p, ps, q, r, r, r, r, r, r, r, r, r, r, r, r, rhn, rn, rt, rth, rth, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, sh, sh, sh, sh, ss, ss, st, t, t, t, t, t, th, th, th, th, th, th, th, th, th, th, th, th, th, th, th, th, th, th, th, th, th, th, th, th, th, th, th, th, th, th, th, th, th, th, x, x, z, z, z"
			;
	
	public static final String ALIEN_SUFFIX_DEFAULT =
			""
			;
	
	public static final int[] ALIEN_SYLLABLE_COUNT_WEIGHTS = new int[]{
			23, 98, 107, 36, 7, 2, 1
			};
	
	public static final int[] ALIEN_BLANK_TERMINAL_COUNTS = new int[]{
			0, 31, 50, 10, 4, 0, 0
			};
	
	
	

	/*
	 * Goblin name pieces
	 */
	public static final String GOBLIN_PREFIX_DEFAULT =
			""
			;
	
	public static final String GOBLIN_ROOT_INITIAL_DEFAULT =
			"A, A, A, A, A, A, A, A, A, A, A, A, B, Ba, Ba, Bau, Be, Bei, Bla, Ble, Bo, Bo, Bri, Bri, Bro, Bu, Bu, Bu, Ca, Ca, Cae, Ce, Ci, Cl\u00edo, Clu, Cy, Cy, D\u00f6, Da, Da, Di, Do, Do, Do, Drau, Dry, Du, Dwa, E, E, E, E, E, E, E, Fae, Fai, Fau, Fi, Fi, Frey, Frey, Ga, Ga, Ghou, Gi, Glo, Gno, Go, Go, Go, Gre, Gre, Gwi, Gwy, Gwy, Ha, Ha, Ha, He, Hi, Hi, Ho, Ho, Hu, Hu, I, In, Ja, Je, Ji, Jo, Jo, Ka, Ka, Ka, Ke, Ki, Ki, Kla, Kno, Ko, Ko, La, La, Le, Li, Li, Lj\u00f3, Lu, Lu, Ma, Ma, Ma, Ma, Ma, Ma, Me, Mi, Mo, Mo, Mo, Moo, Moo, Na, Na, Nai, Ne, Ne, Ni, Ni, Nu, Nu, Ny, O, O, O, O, Pa, Pi, Po, Po, Poo, Pu, Pu, Ra, Re, Ro, Ru, Sa, Sa, Sa, Sa, Se, See, She, Si, Si, Smy, So, Sphi, Spri, Spri, Su, Sva, Sy, Te, Te, Tho, Ti, Ti, Ti, To, Tra, Tri, Tro, Tro, Tsu, U, U, V\u00e6, Va, Vi, Wa, Wa, We, Xa, Y, Ya, Ya, Ya, Ye, Yu, Zo, Zo"
			;
			
	public static final String GOBLIN_ROOT_SYL_BEGIN_DEFAULT =
			"^, "
			+ "a, a, ba, ba, ba, ba, bau, bbi, be, bgo, bli, bo, bou, bu, ca, ca, cca, ccu, cha, ci, cke, clo, cne, cu, cu, d, dca, dhe, dhna, dle, fa, ga, ga, ga, gbea, gda, gga, gga, gi, gle, go, go, gre, gua, ja, kba, ke, kha, ki, kk\u00e1, kkae, ko, ku, la, la, la, lbe, lca, lco, ldra, le, lge, li, li, lie, lkie, lky, lla, lli, lli, lly, lphoe, lta, lte, lti, ma, ma, mba, mbe, mbie, me, mi, mi, mli, mmi, mmy, mo, mpu, mte, na, na, na, na, na, nca, nde, ndi, ndi, ne, ne, ngku, ngu, ngu, ngvi, ni, nney, nni, nni, nni, no, no, no, no, ntau, nti, nva, nyi, o, ppa, pre, ra, ra, re, re, re, re, re, rgo, rgo, ri, ri, ri, rka, rli, ro, rpy, rri, rri, rry, ru, ry, s\u00e1, sa, si, ste, sto, sto, swa, ta, thy, to, to, tsu, tti, tu, ty, u, u, wa, wca, wnie, xie, ya, ze, "
			+ "-o, -u, a, a, a, ba, be, bi, bi, bli, bo, bu, bu, ce, chau, chau, cie, co, co, da, e, ga, ga, go, go, gu, ha, hu, i, ja, ka, ku, ku, la, lfa, lfa, lfa, lka, ma, mo, na, na, ne, ne, ngway, ngway, ni, nn\u00e1, nta, nye, pa, pe, po, pu, ra, rgei, ri, ri, ri, rie, ro, ro, ro, ro, rra, ru, sa, ta, tau, te, te, to, tsu, twa, vi, voi, "
			+ "a, ba, bi, do, ku, la, llo, me, mo, mo, na, na, nchei, nde, ne, ngga, nna, ntza, pha, ra, rma, tia, to, u, "
			+ "bi, ly, re, ro"
			;
	
	public static final String GOBLIN_ROOT_TERMINAL_DEFAULT =
			"^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, "
			+ "b, b, ch, ch, ck, ck, d, d, d, d, d, g, g, g, k, k, l, l, l, l, ld, lf, ll, lph, ltz, m, me, mp, mph, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, ne, ng, ng, nn, nn, nn, ns, nt, nx, p, p, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, rc, rd, re, rf, rn, rn, rt, s, s, s, s, s, s, s, s, s, s, s, s, st, st, t, t, t, te, th, th, tt, tts, w, wn, x, yle"
			;
	
	public static final String GOBLIN_SUFFIX_DEFAULT =
			""
			;

	public static final int[] GOBLIN_SYLLABLE_COUNT_WEIGHTS = new int[]{
			32, 89, 52, 20, 4
			};

	public static final int[] GOBLIN_BLANK_TERMINAL_COUNTS = new int[]{
			1, 42, 24, 13, 2
			};
	
	
	
	
	/*
	 * Pet name pieces
	 */
	public static final String PET_PREFIX_DEFAULT =
			"Beautiful, Best, Big, Big, Black, Black, Black, Bold, Bold, Brigadier, Brown, Colonel, Country, Dr., Fabulous, Faithful, Famous, Giant, Golden, Handsome, King, La, La, Lil, Little, Master, Mighty, Miss, Mlle., Mother, Mr., Mr., Mrs, Old, Old, Old, Prince, Professor, Red, Red, Red, Saint, Senator, Sweet, The, Tiny, Top, Two, Wise"
			;
	
	public static final String PET_ROOT_INITIAL_DEFAULT =
			"A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, A, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Ba, Bae, Bai, Bai, Be, Be, Be, Be, Be, Be, Be, Be, Be, Be, Be, Be, Be, Bea, Bea, Bea, Bea, Bea, Beau, Bee, Bee, Bi, Bi, Bi, Bi, Bla, Bla, Bla, Bla, Bla, Blai, Bli, Blo, Blo, Blue, Blue, Blue, Bo, Bo, Bo, Bo, Bo, Bo, Bo, Boa, Boo, Boo, Bou, Boye, Bra, Bra, Bre, Bro, Bro, Broo, Bru, Bu, Bu, Bu, Bu, Bu, Bu, Bu, Bu, Bu, Bu, Bu, Bu, Bu, Bu, Bu, Bu, Bu, Bu, Bu, Bu, Bu, Ca, Ca, Ca, Ca, Ca, Ca, Ca, Ca, Ca, Ca, Ca, Ca, Ca, Ca, Ca, Cae, Cai, Cha, Cha, Cha, Cha, Cha, Cha, Cha, Cha, Che, Che, Che, Che, Che, Che, Chee, Chi, Chi, Chi, Chi, Chi, Chlo, Chou, Chro, Chu, Ci, Ci, Ci, Ci, Ci, Co, Co, Co, Co, Co, Co, Co, Co, Co, Co, Cre, Crea, Cri, Cu, Da, Da, Da, Da, Da, Da, Da, Da, Da, Da, Da, Dai, De, De, De, Dee, Dhu, Di, Di, Di, Dia, Do, Do, Do, Do, Do, Do, Do, Do, Do, Doo, Dru, Du, Du, Du, Du, Dzo, E, E, E, E, E, E, E, E, E, E, F\u00e9, Fa, Fa, Fa, Fa, Fa, Fai, Fai, Fay, Fe, Fi, Fi, Fi, Fi, Fi, Fi, Flai, Flo, Flo, Flu, Fly, Fo, Fo, Foo, Fra, Fre, Fre, Fri, Fru, Fu, Ga, Ga, Ga, Ga, Ga, Ga, Gai, Ge, Ge, Ge, Gea, Geo, Geo, Geo, Gi, Gi, Gi, Gi, Gi, Gla, Glo, Gloa, Go, Go, Go, Goo, Gra, Gra, Grau, Grey, Grey, Grey, Gu, Gui, Ha, Ha, Ha, Ha, Ha, Ha, Ha, Ha, Ha, Ha, Ha, Ha, Ha, He, He, He, He, He, Hea, Hei, Hei, Hi, Hi, Hi, Hi, Ho, Ho, Ho, Hoo, Hou, Hu, Hu, Hua, Hy, I, I, I, I, I, I, I, I, I, Ja, Ja, Ja, Ja, Ja, Ja, Ja, Jay, Je, Je, Je, Je, Je, Jea, Jeo, Ji, Ji, Ji, Ji, Jo, Jo, Jo, Jo, Jo, Jo, Jo, Jo, Jo, Joe, Joe, Joy, Ju, Ju, Ju, K, Ka, Ka, Ka, Ka, Ka, Kay, Ke, Ke, Ke, Kha, Khou, Ki, Ki, Ki, Ki, Ki, Ki, Ki, Ki, Ki, Ki, Ko, Ko, Ko, Ko, Ko, Ku, Ky, La, La, La, La, La, La, La, Lai, Le, Le, Le, Le, Le, Le, Lea, Li, Li, Li, Li, Li, Li, Li, Lla, Llu, Lo, Lo, Lo, Lo, Lo, Lo, Lo, Lou, Lou, Lu, Lu, Lu, Lu, Lu, Lu, Lu, M, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Ma, Mar, Mau, Me, Me, Me, Me, Me, Mea, Mi, Mi, Mi, Mi, Mi, Mi, Mi, Mi, Mi, Mi, Mi, Mo, Mo, Mo, Mo, Mo, Mo, Mo, Mo, Moi, Moo, Moo, Mou, Mou, Mou, Mu, Mu, Mu, Mu, Mu, Mu, Na, Na, Na, Na, Ne, Ne, Ne, Ne, Ne, Nee, Neu, Ni, Ni, Ni, Ni, Ni, Ni, No, No, No, No, Nu, Nu, Nui, Ny, O, O, O, O, O, O, O, O, O, O, O, O, O, O, O, Oei, Ol, Pa, Pa, Pa, Pa, Pa, Pa, Pa, Pa, Pa, Pa, Pa, Pa, Pa, Pa, Pe, Pe, Pe, Pe, Pe, Pe, Pe, Pea, Pea, Pha, Pha, Phi, Phi, Pi, Pi, Pi, Pi, Pi, Pi, Pi, Po, Po, Po, Po, Pra, Pre, Pre, Pri, Pri, Pro, Pro, Pu, Pu, Pu, Que, Que, Quee, Ra, Ra, Ra, Ra, Ra, Re, Re, Re, Re, Re, Re, Reu, Ri, Ri, Ri, Ri, Ri, Ri, Ro, Ro, Roy, Ru, Ru, Ru, Ru, Ru, Ru, Ru, Sa, Sa, Sa, Sa, Sa, Sa, Sa, Sa, Sa, Sa, Sa, Sai, Sau, Sca, Schno, Sci, Sco, Se, Se, Se, Se, Se, Se, Se, Sea, Sea, Sea, Sea, Sha, Sha, Sha, She, She, Si, Si, Si, Si, Si, Ske, Ski, Ski, Slei, Smo, Smo, Smo, Smu, Sna, Sni, Sno, Sno, Sno, Snu, So, So, So, So, Spa, Spe, Spi, Spi, Spo, Spri, Squea, Sta, Sta, Ste, Stee, Sti, Sto, Sto, Stre, Strei, Stri, Stro, Stu, Stu, Su, Su, Su, Su, Su, Su, Su, Su, Su, Swa, Sy, Sy, Sy, Sy, Ta, Ta, Ta, Ta, Ta, Ta, Ta, Ta, Ta, Ta, Ta, Ta, Ta, Ta, Ta, Tao, Te, Te, Te, Te, Te, The, Thi, Tho, Thu, Thu, Ti, Ti, Ti, Ti, Ti, Ti, Ti, Ti, Ti, Ti, Ti, Ti, Ti, To, To, To, To, To, To, To, To, Tra, Tra, Tra, Tre, Tri, Tri, Tri, Troi, Tsai, Tsu, Tu, Tu, Tu, Tu, Tu, Tu, U, U, U, U, Va, Va, Vai, Ve, Vee, Vei, Vi, Vo, Wa, Wa, Wa, Wa, Wa, Whi, Whi, Whi, Wi, Wi, Wi, Wi, Wi, Wi, Wi, Wi, Wi, Wi, Wi, Wi, Wi, Wo, Wri, Xa, Xaa, Xiao, Ya, Yea, Za, Za, Za, Za, Ze, Ze, Ze, Ze, Ze, Ze, Zeu, Zi, Zi, Zi, Zi, Zi, Zo, Zo, Zu, Zuy"
			;
			
	public static final String PET_ROOT_SYL_BEGIN_DEFAULT =
			"_Boo, _Ma, -Fi, _Jo, -Miu, ^tu, a, a, a, a, ao, ba, ba, bae, bbie, bby, bby, bby, bby, be, be, be, bee, bi, bi, bi, bi, ble, bo, bo, bo, bo, bou, bso, bu, bu, bui, by, ca, ca, cca, cce, cci, cco, ce, ce, cey, cha, che, che, chi, ci, cie, cke, ckee, cki, ckie, ckie, ckle, ckle, ckle, ckle, ckpa, cksie, ckstea, ckwe, cky, cky, cky, cky, cli, co, co, coa, cta, cu, cy, d_Ne, da, da, da, dda, ddha, ddi, ddie, ddle, ddle, ddy, ddy, ddy, ddy, ddy, de, dge, dge, di, di, di, di, di, di, die, die, dle, dle, do, do, dsto, dy, dy, e, e, e, e, fa, faa, ffa, ffi, ffi, ffi, ffle, ffry, ffy, fi, fley, fra, fri, fto, ga, ga, ga, ga, ge, ge, ge, ge, gga, gge, gge, ggi, ggie, ggy, ggy, ghya, gle, gnsto, go, go, gre, gu, ha, hei, hn_Ra, hnsto, ho, hou, hu, hu, i, i, i, ja, ja, jay, ji, jo, k, k\u00f3, ka, ka, ka, ke, kee, key, key, kgu, ki, kie, kly, ko, ko, ko, ky, ky, la, la, la, la, la, lba, lbe, lbo, lchi, lco, lcy, ldie, ldsmi, le, le, le, le, le, le, le, le, lei, ley, ley, lga, lgoo, li, li, li, li, li, li, li, li, li, li, li, lie, lja, lja, lka, lka, lky, lla, lla, lle, lle, lle, lli, llie, llie, llie, llie, llie, llie, llie, llo, llseye, lly, lly, lly, lly, lly, lly, lly, lly, lly, lly, lme, lme, lo, lo, lo, lo, lsey, lso, lso, ltay, lti, lti, lto, lto, lty, lu, lve, lve, lvi, lwoo, ly, ly, ly, ly, lza, ma, ma, ma, ma, ma, mao, mb, mba, mbi, mble, mbo, mbo, mbo, mboo, mbra, mbrai, me, me, me, mi, mi, mi, mme, mmis, mmy, mmy, mo, mo, mo, mo, mo, mpa, mpe, mphre, mpi, mpsey, mpso, mrei, mse, mu, n_Ne, n_Ti, na, na, na, na, na, na, na, na, na, na, na, na, na, na, na, nba, nbrea, nbri, nca, nce, nce, nce, nce, nchu, nci, nci, ncse, ncy, ncy, nd_O, nda, nda, nda, nday, nde, nde, nde, nde, nde, ndi, ndi, ndie, ndo, ndy, ndy, ne, ne, ne, ne, ne, nehi, nes, neso, ngca, nge, ngfe, nghea, ngo, ngo, ngu, ngwa, ni, ni, ni, nie, nja, njee, njo, nka, nke, nke, nke, nkle, nko, nky, nli, nna, nne, nnie, nnie, nnie, nny, nny, no, no, noo, nri, nri, nsa, nsbo, nse, nse, nsea, nslo, nso, nso, nsta, nsway, nte, nti, nu, nva, nya, nya, o, o, o, o, o, o, o, o, o, o, o, pco, pe, pe, pe, pi, pi, pi, pni, ppe, ppe, ppi, ppo, ppo, ppy, ppy, ppy, ppy, ppy, ppy, ppy, pwri, ra, ra, ra, ra, ra, ra, ra, rao, rba, rba, rbi, rbi, rbi, rbo, rca, rche, rchi, rchie, rda, rda, rdi, re, re, re, re, re, re, re, rebi, rfe, rfie, rga, rghe, ri, ri, ri, ri, rjo, rka, rkle, rky, rla, rle, rley, rley, rley, rli, rlie, rlo, rme, rmi, rmie, rmo, rna, rnbo, rney, ro, ro, ro, ro, ro, ro, ro, ro, ro, rphe, rphy, rri, rri, rri, rri, rrie, rro, rry, rry, rry, rry, rsi, rte, rtha, rto, ru, ru, ry, ry, ry, rzey, rzi, sa, sa, sa, sca, sca, sca, schi, se, se, sha, shi, shka, shley, shroo, si, sie, ski, sley, sley, sli, smo, so, so, spe, spe, spe, spe, sru, ssau, ssi, ssi, ssi, ssie, ssie, st, sta, sta, ste, ste, ste, sti, sti, stle, sty, sty, sty, stya, swa, sy, syu, szta, ta, ta, ta, ta, ta, ta, tba, te, te, te, thi, thle, ti, ti, ti, tku, tle, tma, tra, tro, tsu, tswai, tsy, tte, tte, tte, tte, tti, ttle, ttle, tto, tty, tty, tty, tze, tzkrie, va, va, ve, ve, ve, ve, ve, ve, vei, vi, vi, vi, vie, vlo, vo, wba, wba, wbray, wey, wi, wie, wkeye, wma, wney, wny, wse, wto, wy, xe, xe, xi, xie, xie, xie, xte, y, ya, ya, za, zi, zley, zno, "
			+ "_Po, a, a, a, a, a, ar, ba, be, be, ca, cchi, cco, ce, cie, cto, cu, da, dda, de, di, di, dle, do, do, e, e, fo, fy, ga, ga, ga, ga, gou, gro, i, ka, ka, ko, ko, la, la, la, lda, lde, le, le, lee, lei, leja, leto, li, lla, lla, lle, llo, llo, lo, lo, lo, lro, lso, ly, ly, ma, ma, mbo, me, me, mi, mi, mo, mo, n_Go, n_Ti, na, na, na, na, nche, nci, ndo, ndu, ngbo, ngey, nggla, ngo, ngto, ngto, ngto, nha, ni, nka, nka, nna, no, nsky, nti, nti, ny, ny, ny, o, o, o, o, o, o, pa, pa, pha, pu, quoi, r-Pie, rbe, rbe, rcu, rdo, rfo, rga, rhou, ri, ri, ri, ri, rie, rky, rlie, rmi, rmi, rmo, ro, ro, ro, rou, rou, rsto, rty, ru, rva, ry, sa, sa, sau, scui, sha, sha, shne, si, sio, so, so, ssa, sse, ssey, ste, ste, sti, su, t\u00e1, ta, ta, ta, ta, te, te, tha, thy, tio, tio, tro, tro, tta, ttle, ttle, u, va, vi, vi, vi, vi, vre, way, wi, wti, xa, zza, "
			+ "a, a, b, ca, cke, co, co, da, de, do, ge, ko, la, lu, mo, my, na, na, na, ndra, ne, ngto, ni, no, o, o, o, rte, ru, ru, ry, ti, ti, to, tta, tu, ty, ty, yu, ze, zi, "
			+ "a, ke, to"
			;
	
	public static final String PET_ROOT_TERMINAL_DEFAULT =
			"^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, ^, "
			+ "b, b, b, bbs, bs, c, ce, ce, ch, che, ck, ck, ck, ck, ck, ck, ck, ck, ck, cks, cks, ct, ct, d, d, d, d, d, d, d, d, d, d, d, d, de, dge, dge, f, ff, ff, ff, g, g, g, ge, ge, ge, ggs, gh, gh, gh, gs, h, h, h, h, h, h, h, hm, k, k, k, k, k, k, k, k, k, k, ke, ke, ke, ke, ke, kes, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, l, ld, le, le, lf, lk, ll, ll, ll, ll, ll, ll, lle, lle, lle, lle, lles, lp, lt, lt, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, m, me, me, me, mp, mp, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, n, nce, nce, nce, nce, nd, nd, nd, ne, ne, ne, ne, ne, ne, ne, nes, ng, ng, ng, ng, ng, ng, ng, ngs, nk, nk, nk, nk, nk, nn, nne, nne, ns, ns, ns, ns, ns, nx, p, p, p, p, p, p, p, ps, pse, qui, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, r, rbes, rce, rd, rd, rd, rd, rd, re, re, re, rge, rge, rk, rl, rm, rmed, rn, rp, rs, rs, rt, rt, rt, rt, rt, rt, rt, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, s, se, se, se, se, se, se, se, sh, sh, sh, sh, sk, sp, ss, ss, ss, ss, ss, ss, ss, ss, sse, st, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, t, tch, te, te, te, te, te, te, tes, th, th, th, th, th, ts, tt, tt, tte, tte, tte, tz, v, v, w, w, w, w, w, w, w, w, we, wn, wn, wn, x, x, x, x, x, x, x, x, x, y, y, y, z, ze"
			;
	
	public static final String PET_SUFFIX_DEFAULT =
			"Bean, Blue, Boy, Butler, II, II, II, Jr., Pan, Puff, Ray, Ray, Rouge, the Great, Two, Wee"
			;

	public static final int[] PET_SYLLABLE_COUNT_WEIGHTS = new int[]{
			169, 485, 146, 38, 3
			};

	public static final int[] PET_BLANK_TERMINAL_COUNTS = new int[]{
			18, 265, 64, 18, 1
			};
}
