package com.ycyl.xipai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;

/**
 * 洗牌，重新产生扑克牌 思路:0-12代表红桃A到红桃K 13-25代表梅花 26-38代表方块 39-51代表黑桃
 * 
 * @author ycyl
 *
 */
public class XiPai {

	public static void main(String[] args) {
		// 初始化牌
		System.out.println("初始化牌");
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < 52; i++) {
			list.add(i);
		}
		System.out.println("洗牌.....");
		Collections.shuffle(list);
		System.out.println("洗牌结束，发牌.....");
		for (int i = 0; i < list.size(); i++) {
			// 符号,取模
			int type = (int) (list.get(i) / 13);
			String typeICON = "";
			switch (type) {
			case 0:
				typeICON = "红桃";
				break;
			case 1:
				typeICON = "梅花";
				break;
			case 2:
				typeICON = "方块";
				break;
			case 3:
				typeICON = "黑桃";
				break;
			}

			int num = list.get(i) % 13;
			String numIcon = "";
			switch (num) {
			case 0:
				numIcon = " A";
				break;
			case 10:
				numIcon = " J";
				break;
			case 11:
				numIcon = " Q";
				break;
			case 12:
				numIcon = " K";
				break;
			default:
				numIcon = String.format("%2d", (num+1));
				break;
			}

			System.out.printf(typeICON + numIcon + " ");
			if (i % 13 == 12) {
                System.out.printf("%n");
			}

		}

	}

}
