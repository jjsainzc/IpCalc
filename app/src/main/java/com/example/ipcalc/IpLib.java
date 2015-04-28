package com.example.ipcalc;

import android.annotation.SuppressLint;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SuppressLint("DefaultLocale")
public class IpLib {
	public static final String RED_A = "10.0.0.0/255.0.0.0";
	public static final String RED_B = "172.16.0.0/255.240.0.0";
	public static final String RED_C = "192.168.0.0/255.255.0.0";
	public static final String PATRON = "0123456789ABCDEF";

	public static String replicate(String valor, int n) {
		String result = "";

		for (int i = 1; i <= n; i++) {
			result += valor;
		}
		return result;
	}

	public static String reverseText(String v) {
		String result = "";

		for (int i = v.length() - 1; i >= 0; i--) {
			result += v.charAt(i);
		}
		return result;
	}

	
	public static Long base2Dec(String valor, int base) {
		String v;
		int buff;
		long res = 0;
		long exp = 0;
		long result = 0;
		int byte_;

		try {
			v = valor.toUpperCase();

			if ((base < 2) || (base > 16)) {
				return 0L;
			}
			if (v.length() == 0) {
				return result;
			}

			for (int i = v.length() - 1; i >= 0; i--) {
				byte_ = v.charAt(i);
				buff = PATRON.indexOf(byte_) + 1;
				if (buff == 0) {
					return result;
				}
				buff--;
				res += buff * Math.floor(Math.pow(base, exp));
				exp++;
			}
			result = res;
		} catch (Exception e) {
			System.out.println(e.toString());
			result = 0;
		}
		return result;
	}

	public static String dec2Base(long valor, int base) {
		long PEnt;
		long PFrac;
		long v;
		String Buff1 = "";
		String result = "";

		try {
			v = valor;
			if ((base < 2) || (base > 16)) {
				return result;
			}
			if (v == 0) {
				return result;
			}

			while (v != 0) {
				PEnt = (long) Math.floor(v / base);
				PFrac = v % base;
				Buff1 += PATRON.charAt((int) PFrac);
				v = PEnt;
			}
			result = reverseText(Buff1);
		} catch (Exception e) {
			result = "";
		}
		return result;
	}

	public static String ipClase(String ip) {
		int octip[] = new int[4];

		int minrnga[] = { 1, 0, 0, 0 };
		int maxrnga[] = { 126, 255, 255, 254 };
		int minrngb[] = { 128, 1, 0, 0 };
		int maxrngb[] = { 191, 255, 255, 254 };
		int minrngc[] = { 192, 0, 1, 0 };
		int maxrngc[] = { 223, 255, 255, 254 };

		String result;

		try {
			result = "N";
			int i = 0;
			for (String str : ip.split("\\.")) {
				octip[i++] = Integer.parseInt(str);
			}

			if ((octip[0] >= minrnga[0]) && (octip[0] <= maxrnga[0])
					&& (octip[1] >= minrnga[1]) && (octip[1] <= maxrnga[1])
					&& (octip[2] >= minrnga[2]) && (octip[2] <= maxrnga[2])
					&& (octip[3] >= minrnga[3]) && (octip[3] <= maxrnga[3])) {

				return "A";
			}

			if ((octip[0] >= minrngb[0]) && (octip[0] <= maxrngb[0])
					&& (octip[1] >= minrngb[1]) && (octip[1] <= maxrngb[1])
					&& (octip[2] >= minrngb[2]) && (octip[2] <= maxrngb[2])
					&& (octip[3] >= minrngb[3]) && (octip[3] <= maxrngb[3])) {

				return "B";
			}

			if (octip[2] > 0) {
				if ((octip[0] >= minrngc[0]) && (octip[0] <= maxrngc[0])
						&& (octip[1] >= minrngc[1]) && (octip[1] <= maxrngc[1])
						&& (octip[2] >= minrngc[2]) && (octip[2] <= maxrngc[2])
						&& (octip[3] >= minrngc[3]) && (octip[3] <= maxrngc[3])) {

					return "C";
				}
			} else if ((octip[0] >= minrngc[0]) && (octip[0] <= maxrngc[0])
					&& (octip[1] >= minrngc[1]) && (octip[1] <= maxrngc[1])
					&& (octip[3] >= minrngc[3]) && (octip[3] <= maxrngc[3])) {

				return "C";
			}
		} catch (Exception e) {
			result = "N";
		}
		return result;
	}

	public static Boolean ipCheck(String ip, String netm) {
		int octip[] = new int[4];
		int octma[] = new int[4];
		boolean result;
		try {
			if (ipClase(ip).equals("N")) {
				return false;
			}
			int i = 0;
			for (String str : ip.split("\\.")) {
				octip[i++] = Integer.parseInt(str);
			}
			i = 0;
			for (String str : netm.split("\\.")) {
				octma[i++] = Integer.parseInt(str);
			}
			for (i = 0; i <= 3; i++) {
				if ((octma[i] != 0) && (octma[i] != 255)) {
					if ((octip[i] & octma[i]) == octip[i]) {
						return false;
					}
					if (octip[i] == ((octip[i] & octma[i]) + (255 - octma[i]))) {
						return false;
					}
				}
			}
			result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	public static String cNetwork(String ip, String mask) {
		int ipad_oct[] = new int[4];
		int netw_oct[] = new int[4];
		int mask_oct[] = new int[4];
		String result = "";

		try {
			if (ipClase(ip).equals("N")) {
				return "none";
			}

			int i = 0;
			for (String str : ip.split("\\.")) {
				ipad_oct[i++] = Integer.parseInt(str);
			}
			i = 0;
			for (String str : mask.split("\\.")) {
				mask_oct[i++] = Integer.parseInt(str);
			}

			netw_oct[0] = (ipad_oct[0] & mask_oct[0]);
			netw_oct[1] = (ipad_oct[1] & mask_oct[1]);
			netw_oct[2] = (ipad_oct[2] & mask_oct[2]);
			netw_oct[3] = (ipad_oct[3] & mask_oct[3]);

			for (int v : netw_oct) {
				result += (String.valueOf(v) + ".");
			}
			return result.substring(0, result.length() - 1);
		} catch (Exception e) {
			return "none";
		}
	}

	public static String cBroadcast(String ip, String netw, String mask) {
		int ipad_oct[] = new int[4];
		int netw_oct[] = new int[4];
		int mask_oct[] = new int[4];
		int bcat_oct[] = new int[4];
		boolean ok;
		String result = "";

		try {
			if (ipClase(ip).equals("N")) {
				return "none";
			}
			int i = 0;
			for (String str : ip.split("\\.")) {
				ipad_oct[i++] = Integer.parseInt(str);
			}
			i = 0;
			for (String str : mask.split("\\.")) {
				mask_oct[i++] = Integer.parseInt(str);
			}
			i = 0;
			for (String str : netw.split("\\.")) {
				netw_oct[i++] = Integer.parseInt(str);
			}

			ok = (((ipad_oct[0] & mask_oct[0]) == netw_oct[0])
					&& ((ipad_oct[1] & mask_oct[1]) == netw_oct[1])
					&& ((ipad_oct[2] & mask_oct[2]) == netw_oct[2]) && ((ipad_oct[3] & mask_oct[3]) == netw_oct[3]));

			if (ok) {
				bcat_oct[0] = (netw_oct[0]) + (255 - mask_oct[0]);
				bcat_oct[1] = (netw_oct[1]) + (255 - mask_oct[1]);
				bcat_oct[2] = (netw_oct[2]) + (255 - mask_oct[2]);
				bcat_oct[3] = (netw_oct[3]) + (255 - mask_oct[3]);
			} else {
				return "none";
			}

			for (int v : bcat_oct) {
				result += (String.valueOf(v) + ".");
			}
			return result.substring(0, result.length() - 1);
		} catch (Exception e) {
			return "none";
		}
	}

	public static Integer maskToInt(String m) {
		String maskbin;
		int resint = 0;
		int oct;

		try {
			if (!Pattern.compile("[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+")
					.matcher(m).matches()) {
				return -1;
			}
			for (int i = 0; i < 4; i++) {
				oct = Integer.valueOf(m.split("\\.")[i]);
				if ((oct > 255) || (oct < 0)) {
					return -1;
				}
				maskbin = dec2Base(oct, 2);
				try {
					Matcher matcher = Pattern.compile("1").matcher(maskbin);
					while (matcher.find()) {
						resint++;
					}
				} catch (Exception e) {
				}
			}
			return (((resint >= 0) && (resint <= 32)) ? resint : -1);
		} catch (Exception e) {
			return -1;
		}
	}

	public static String intToMask(int n) {
		int otm[] = new int[4];
		
		String maskbin;
		int i = 0;
		int j = 0;
		String result = "";

		try {
			if ((n > 32) || (n < 0)) {
				return "x.x.x.x";
			}
			maskbin = replicate("1", n) + replicate("0", 32 - n);
			do {
				otm[j++] = base2Dec(maskbin.substring(i, i + 8), 2).intValue();
				i += 8;
			} while (i < 31);

			for (int v : otm) {
				result += (String.valueOf(v) + ".");
			}
			return result.substring(0, result.length() - 1);
		} catch (Exception e) {
			return "x.x.x.x";
		}
	}

	public static String num2Ip(Long ipnumber) {
		int oct_v[] = { 0, 0, 0, 0 };
		long ipt;
		String result = "";

		try {
			ipt = ipnumber;
			for (int i = 3; i >= 0; i--) {
				oct_v[3 - i] = (int) Math.floor(ipt / Math.pow(256, i));
				ipt = (long) (((ipt / Math.pow(256, i)) - oct_v[3 - i]) * Math
						.pow(256, i));
			}
			for (int v : oct_v) {
				result += (String.valueOf(v) + ".");
			}
			return result.substring(0, result.length() - 1);
		} catch (Exception e) {
			return "x.x.x.x";
		}
	}

	public static Long ip2Num(String ipstr) {
		int i = 0;
		int octp[] = new int[4];
		long result = 0L;

		try {
			for (String str : ipstr.split("\\.")) {
				octp[i++] = Integer.parseInt(str);
			}
			for (i = 0; i <= 3; i++) {
				result += (octp[i] * Math.pow(256, (3 - i)));
			}

			return result;
		} catch (Exception e) {
			return (long) -1;
		}
	}

	public static Integer countIps(String mask) {
		int b;

		try {
			b = maskToInt(mask);
			return (int) ((b == 32) ? 1 : ((b != -1) ? Math.floor(Math.pow(2,
					(32 - b))) - 2 : -1));
		} catch (Exception e) {
			return 0;
		}
	}

	public static Boolean ipIntranet(String ipstr) {
		int ipoct[] = new int[4];
		int i = 0;

		try {
			if (ipClase(ipstr).equals("N")) {
				return false;
			}
			for (String str : ipstr.split("\\.")) {
				ipoct[i++] = Integer.parseInt(str);
			}

			switch (ipoct[0]) {
			case 10:
				return (!cBroadcast(
						ipstr,
						RED_A.substring(0, RED_A.indexOf("/", 0)),
						RED_A.substring(RED_A.indexOf("/", 0) + 1,
								RED_A.length())).equals("none"));

			case 127:
				return ((ipoct[1] == 0) && (ipoct[2] == 0) && (ipoct[3] == 1));
			case 172:
				if (ipoct[1] == 16) {
					return (!cBroadcast(
							ipstr,
							RED_B.substring(0, RED_B.indexOf("/", 0)),
							RED_B.substring(RED_B.indexOf("/", 0) + 1,
									RED_B.length())).equals("none"));
				}
			case 192:
				if (ipoct[1] == 168) {
					return (!cBroadcast(
							ipstr,
							RED_C.substring(0, RED_C.indexOf("/", 0)),
							RED_C.substring(RED_C.indexOf("/", 0) + 1,
									RED_C.length())).equals("none"));
				}

			}
		} catch (Exception e) {
		}
		return false;
	}

	
}
