/* 
LEMONADE
STAND
GAME
*/

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class LemonadeStand {

	static int numLemonade = 0;
	static int priceLemonade = 35;
	static String[] itemNames = {"LEMONS", "CUPS OF SUGAR", "CUPS OF WATER"};
	static int[] itemStock = {0, 0, 0};
	static int[] stockMax = {10, 10, 10};
	static int[] priceItems = {15, 10, 5};
	static int[] defaultPriceItems = {15, 10, 5};
	static int money = 100;
	static int day = 1;
	static boolean run = true;
	static String name = "";
	static String currentStore = "Small Lemonade Stand";
	static int numUpgrade = -1;
	static String[] upgrades = {"Big Lemonade Stand", "Mall Kiosk", "Independent Store", "Online Store"};
	static int[] upgradesCost = {250, 500, 750, 1000};
	static int lowestPrice = 25;
	static int highestPrice = 40;
	static int lowestCold = 10;
	static int highestCold = 20;
	static int lowestHot = 40;
	static int highestHot = 50;
	static Random r = new Random();
	static int totalMade = 0;
	static int score = 0;
	static int sellCount = 0;
	static int maxSell = 5;
	static int totalCost = 30;
	static double scorePerDay = score / day;
	static double moneyPerDay = money / day;
	static File save1 = new File("save1.txt");
	static File save2 = new File("save2.txt");
	static File save3 = new File("save3.txt");
	static boolean load = false;
	static String name1 = "";
	static String name2 = "";
	static String name3 = "";
	static int lowestStock = 0;

   public static void main(String[] args) throws FileNotFoundException {
	
		Scanner input1 = new Scanner(save1);
		name1 = input1.nextLine();
		input1.close();
		
		Scanner input2 = new Scanner(save2);
		name2 = input2.nextLine();
		input2.close();
		
		Scanner input3 = new Scanner(save3);
		name3 = input3.nextLine();
		input3.close();
		
		mainMenu();
		
	} // End main
	
	static void randomEvents () {
		priceLemonade = r.nextInt(highestPrice - lowestPrice) + lowestPrice;
		
		for (int i = 0; i < priceItems.length; i++) {
				priceItems[i] = defaultPriceItems[i];
			}
	
		if (day == 1) {
			return;
		}
		
		String outmsg = "News:";
		boolean event = false;
		boolean dayStatus = false;
		if (r.nextInt(10) == 1) {
			outmsg += "\nSugar cane farmers are on a strike today!";
			priceItems[1] += 5;
			event = true;
		}
		if (r.nextInt(10) == 1) {
			outmsg += "\nThe lemon farm had a bad harvest today!";
			priceItems[0] += 5;
			event = true;
		}
		if (r.nextInt(10) == 1) {
			outmsg += "\nA one day drought somehow occured today!";
			priceItems[2] += 5;
			event = true;
		}
		if (r.nextInt(5) == 1) {
			outmsg += "\nIt's a cold day today!";
			priceLemonade = r.nextInt(highestCold - lowestCold) + lowestCold;
			dayStatus = true;
			event = true;
		}
		if (r.nextInt(5) == 1 && !dayStatus) {
			outmsg += "\nIt's a hot day today!";
			priceLemonade = r.nextInt(highestHot - lowestHot) + lowestHot;
			event = true;
		}
		if (event) {
			JOptionPane.showMessageDialog(null, outmsg);
			return;
		}
	}
	
	static void game () throws FileNotFoundException {
		if (!load) {
			name = JOptionPane.showInputDialog(null, "What is your name?");
		}
		if (load) {
			moneyPerDay = money / day;
			scorePerDay = score / day;
		}
		while (run) {
			menu();
		}
		System.exit(0);
	}
	
	static void cheating () throws FileNotFoundException {
		String moneyInput = JOptionPane.showInputDialog(null, "How much money would you like to start off with?");
		String dayInput = JOptionPane.showInputDialog(null, "Which day would you like to start off with?");
		
		try {
			money = Integer.parseInt(moneyInput);
			day = Integer.parseInt(dayInput);
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Invalid.");
			game();
		}
		int moneyTemp = Integer.parseInt(moneyInput);
		int dayTemp = Integer.parseInt(dayInput);
		if ((moneyTemp < 0) || (dayTemp < 0)) {
			JOptionPane.showMessageDialog(null, "Invalid.");
			game();
		}
		else {
			money = moneyTemp;
			day = dayTemp;
			game();
		}
	} // End cheating
	
	static void mainMenu () throws FileNotFoundException {
		String[] mainMenu = {"Play game", "Load game", "Cheat", "Instructions","Exit game"};
		while (run) {
		int selMain = JOptionPane.showOptionDialog(null, "Select an option", "Main Menu", 0, 3, null, mainMenu, mainMenu[0]);
			switch (selMain) {
				case 0:
					game();
					break;
				case 1:
					loadMenu();
					break;
				case 2:
					cheating();
					break;
				case 3:
					JOptionPane.showMessageDialog(null, "Instructions:\n\nPurchase ingredients, make lemonade and then sell them!\nEach day the sell price of lemonade could vary. \nRandom events may also occur that can change the price of the ingredients and the lemonade. \n\nHave fun!");
					break;
				case -1:
				case 4:
				default:
					System.exit(1);
			}
		}
		System.exit(1);
	}
	
	static void menu () throws FileNotFoundException {
		String[] mainMenu = {"Buy ingredients", "[" + itemStock[0] + "] Make lemonade", "[" + (maxSell - sellCount) + "] Sell lemonade", "Upgrade store", "Next day", "Save game"};
		int selMenu = JOptionPane.showOptionDialog(null, "Welome, " + name + "!" + "\nStatus: " + currentStore + "\nDay #" + day + "\nMoney: $" + money + "\nCups of lemonade: " + numLemonade + "\nIngredients stock: " + itemStock[0] + "\nMax sell: " + maxSell + "\nLemonade Sell Price: $" + priceLemonade + "\n\nMoney per day: $" + moneyPerDay + "\nScore per day: " + scorePerDay + "\nScore: " + score + "\nTotal money made: $" + totalMade, "Raf's Lemonade Stand Game", 0, 3, null, mainMenu, mainMenu[4]);
		
		switch (selMenu) {
			case 0:
				buying();
				break;
			case 1:
				making();
				break;
			case 2:
				selling();
				break;
			case 3:
				upgrading();
				break;
			case 4:
				randomEvents();
				totalCost = 0;
				for (int i = 0; i < priceItems.length; i++) {
					totalCost += priceItems[i];
				}
				day++;
				moneyPerDay = money / day;
				scorePerDay = score / day;
				sellCount = 0;
				break;
			case 5:
				saveMenu();
				break;
			default:
				JOptionPane.showMessageDialog(null, "Status: " + currentStore + "\nDays passed: " + day + "\nTotal money made: $" + totalMade + "\nScore: " + score + "\nScore per day: " + scorePerDay + "\nMoney per day: $" + moneyPerDay);
				run = false;
				break;
		}
	}

	static void saveMenu () throws FileNotFoundException {
		String[] saveMenu = {"Slot 1: " + name1, "Slot 2: " + name2, "Slot 3: " + name3, "Go back"};
		int selSave = JOptionPane.showOptionDialog(null, "Choose a slot.", "Save Menu", 0 , 3, null, saveMenu, saveMenu[3]);
		switch (selSave) {
			case -1:
				break;
			case 0:
				save(save1);
				return;
			case 1:
				save(save2);
				return;
			case 2:
				save(save3);
				return;
			case 3:
				break;
			default:
				break;
		}
	}
	
	static void save (File x) throws FileNotFoundException {
		PrintWriter output = new PrintWriter(x);
		output.println(name);
		output.println(money);
		output.println(day);
		output.println(score);
		output.println(numLemonade);
		output.println(itemStock[0]);
		output.println(itemStock[1]);
		output.println(itemStock[2]);
		output.println(stockMax[0]);
		output.println(stockMax[1]);
		output.println(stockMax[2]);
		output.println(currentStore);
		output.println(numUpgrade);
		output.println(totalMade);
		output.println(sellCount);
		output.close();
	} // End saving
	
	static void loadMenu () throws FileNotFoundException {
		String[] loadMenu = {"Load 1: " + name1, "Load 2: " + name2, "Load 3: " + name3, "Go back"};
		int selLoad = JOptionPane.showOptionDialog(null, "Choose a slot.", "Load Menu", 0 , 3, null, loadMenu, loadMenu[3]);
		switch (selLoad) {
			case -1:
				return;
			case 0:
				loading(save1);
				load = true;
				game();
				return;
			case 1:
				loading(save2);
				load = true;
				game();
				return;
			case 2:
				loading(save3);
				load = true;
				game();
				return;
			case 3:
				return;
			default:
				return;
		}
	}
	
	static void loading (File x) throws FileNotFoundException {
		Scanner input = new Scanner(x);
		name = input.nextLine();
		money = Integer.parseInt(input.nextLine());
		day = Integer.parseInt(input.nextLine());
		score = Integer.parseInt(input.nextLine());
		numLemonade = Integer.parseInt(input.nextLine());
		itemStock[0] = Integer.parseInt(input.nextLine());
		itemStock[1] = Integer.parseInt(input.nextLine());
		itemStock[2] = Integer.parseInt(input.nextLine());
		stockMax[0] = Integer.parseInt(input.nextLine());
		stockMax[1] = Integer.parseInt(input.nextLine());
		stockMax[2] = Integer.parseInt(input.nextLine());
		currentStore = input.nextLine();
		numUpgrade = Integer.parseInt(input.nextLine());
		totalMade = Integer.parseInt(input.nextLine());
		sellCount = Integer.parseInt(input.nextLine());
		input.close();
	} // End loading
	
	static void selling () {
		String[] sellMenu = {"Sell one", "Sell all", "Go back"};
		int selSell = JOptionPane.showOptionDialog(null, "Choose an option.", "Sell Menu", 0, 3, null, sellMenu, sellMenu[2]);
		switch (selSell) {
			case -1:
				return;
			case 2:
				return;
			case 0: // Sell one
				if (sellCount >= maxSell) {
					JOptionPane.showMessageDialog(null, "You can't sell anymore lemonade today! Upgrade your store to sell more!");
					return;
				}
				if (numLemonade == 0) {
					JOptionPane.showMessageDialog(null, "You don't have enough lemonade!");
					return;
				}
				else {
					score += 100;
					numLemonade--;
					totalMade += priceLemonade;
					money += priceLemonade;
					sellCount++;
					selling();
					return;
				}
			case 1: // Sell all
				if (numLemonade == 0) {
					JOptionPane.showMessageDialog(null, "You don't have enough lemonade!");
					return;
				}
				if (sellCount >= maxSell) {
					JOptionPane.showMessageDialog(null, "You can't sell anymore lemonade today! Upgrade your store to sell more!");
					return;
				}
				if (numLemonade > maxSell) {
					JOptionPane.showMessageDialog(null, "You can't sell that much lemonade! Upgrade your store to sell more!");
					return;
				}
				if (sellCount != 0 && (sellCount + numLemonade) >= maxSell) {
					JOptionPane.showMessageDialog(null, "You can't sell anymore lemonade today! Upgrade your store to sell more!");
					return;
				}
				else {
					score += 100 * numLemonade;
					totalMade += numLemonade + priceLemonade;
					money += priceLemonade * numLemonade;
					sellCount += numLemonade;
					numLemonade = 0;
				}
				return;
			default:
				return;
		}
	} // End selling
	
	static void making () {
		String[] menuMaking = {"Make one", "Make max", "Go back"};
		lowestStock();
		int selMaking = JOptionPane.showOptionDialog(null, "Choose an option.", "Make menu", 0, 3, null, menuMaking, menuMaking[2]);
		switch (selMaking) {
			case -1:
			case 2:
				return;
			case 0:
				if (lowestStock == 0) {
					JOptionPane.showMessageDialog(null, "You don't have enough ingredients!");
				}
				else {
					for (int i = 0; i < itemStock.length; i++) {
						itemStock[i]--;
					}
					numLemonade++;
					lowestStock();
				}
				return;
			case 1:
				if (lowestStock == 0) {
					JOptionPane.showMessageDialog(null, "You don't have enough ingredients!");
				}
				else {
					for (int i = 0; i <itemStock.length; i++) {
						itemStock[i] -= lowestStock;
					}
					numLemonade += lowestStock;
					lowestStock();
				}
				return;
			default:
				return;
		}
	}
		
	static void upgrading () {
		int nextUpgrade = numUpgrade + 1;
		
		if (nextUpgrade == upgrades.length) {
			JOptionPane.showMessageDialog(null, "Sorry! You're already at the highest upgrade!");
			return;
		}
		
		int choice = JOptionPane.showConfirmDialog(null, "Would you like to upgrade?" + "\n" + upgrades[nextUpgrade] + ": $" + upgradesCost[nextUpgrade]);
		
		if (choice == 0) {
			if (money > upgradesCost[nextUpgrade]) {
				score += 1000;
				money -= upgradesCost[nextUpgrade];
				currentStore = upgrades[nextUpgrade];
				maxSell += 10;
				for (int i = 0; i < stockMax.length; i++) {
					stockMax[i] += 10;
				}
				numUpgrade++;
			}
			else {
				JOptionPane.showMessageDialog(null, "You don't have enough money to upgrade!");
				return;
			}
		}
		else {
			return;
		}
	} // End upgrading
	
	static void lowestStock () {
		lowestStock = itemStock[0];
		for (int i = 0; i < itemStock.length; i++) {
			if (lowestStock > itemStock[i]) {
				lowestStock = itemStock[i];
			}
		}
		return;
	} // End lowesStock
	
	static void buying () {
		String[] buyMenu = {itemNames[0], itemNames[1], itemNames[2], "ONE OF EVERYTHING", "Go back"};
		
		int selBuy = JOptionPane.showOptionDialog(null, "Select a product to buy." + "\n" + "[" + itemStock[0] + "] " + itemNames[0] + ": $" + priceItems[0] + "\n" + "[" + itemStock[1] + "] " + itemNames[1] + ": $" + priceItems[1] + "\n" + "[" + itemStock[2] + "] " + itemNames[2] + ": $" + priceItems[2], "Store", 0, 3, null, buyMenu, buyMenu[3]);
		
		if (selBuy == -1 || selBuy == 4) {
			return;
		}
		
		else if (selBuy == 3) {
			int totalCost = 0;
			for (int i = 0; i < priceItems.length; i++) {
				totalCost += priceItems[i];
			}
			if (money >= totalCost) {
				money -= totalCost;
				for (int i = 0; i < itemStock.length; i++) {
					itemStock[i] += 1;
				}
				lowestStock();
				buying();
			}
			else {
				JOptionPane.showMessageDialog(null, "You don't have enough money!");
				return;
			}
		}
		
		else if (selBuy == 0 || selBuy == 1 || selBuy == 2) {
			if (money > priceItems[selBuy]) {
				money -= priceItems[selBuy];
				itemStock[selBuy] += 1;
				lowestStock();
				buying();
			}
			else {
				JOptionPane.showMessageDialog(null, "You don't have enough money!");
				return;
			}
		}
		else {
			return;
		}
		
	} // End buying

} // End class