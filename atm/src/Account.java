import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

	public class Account  {
		private static DecimalFormat df1 = new DecimalFormat("#.00");
		
		private int accountNum;
		private int pin;
		private double bal;

		public Account(int accNum,int pin, double bal) {
			this.accountNum = accNum;
			this.pin = pin;
			this.bal = bal;
		}
		
		public Account() {
			this.accountNum = accountNum;
			this.pin = pin;
			this.bal = bal;
		}
		
		public int getAccountNum() {
			return accountNum;
		}

		public int getPin() {
			return pin;
		}
		
		public double getBal() {
			return bal;
		}
		
		//Adds an account and returns new account number
		public static int addAccount(List<Account> accounts, int p, int b) {
			
			int aNum = ((accounts.get(accounts.size() - 1).accountNum) + 1);
			Account acc =  new Account(aNum,p,b);
			accounts.add(acc);
			
			return aNum;
		}
		public double updateBal(double amt, int wd) {
			if (wd == 1)
				bal = bal - amt;
			if (wd == 2)
				bal = bal + amt;
			return bal;
		}
		
		public static Account getAccount(List<Account> accounts, int accNum) {
			for(int i = 0; i < accounts.size(); i++) {
				if(accounts.get(i).accountNum == (accNum)) {
					return accounts.get(i);	
		}
				else
					continue;
		}
		return null;
		}
		
		public static void printAccountList(List<Account> accounts) {
			for(int i = 0; i < accounts.size(); i++) {
				System.out.println(accounts.get(i).toString());
			}
		}
		
		public String toString() {
			String accounts = "";
		
			accounts += "Account Number: " + this.accountNum + "\n";
			accounts += "Pin: " + this.pin + "\n";
			accounts += "Balance: " + this.bal + "\n";
		
		return accounts;
		}
		
		public static ArrayList<Account> importFile(File f) {
			ArrayList<Account> accounts = new ArrayList<Account>();
				
			try {	
				Scanner scanner = new Scanner(f);
			
				while(scanner.hasNextLine()) {
					String x = scanner.nextLine();
					String[] row = x.split(",");
					int accNum = 00000;
					int pin = 0000;
					double bal = -6969;
				
					try {
						accNum = Integer.parseInt(row[0]);
						pin = Integer.parseInt(row[1]);
						bal = Double.parseDouble(row[2]);
					}
					
					catch (ArrayIndexOutOfBoundsException e) {
						System.out.println("Row not imported");
						bal = -6969;
						continue;
					}
					
					catch (NumberFormatException e) {
						System.out.println("No balance");
						continue;
					}
					
					catch (Exception e) {
						System.out.println("Row not imported");
						continue;
					}
					
					Account acc = new Account(accNum, pin, bal);
					accounts.add(acc);
				}
				
			scanner.close();
			
			} 
			
			catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			}
			
			return accounts;
		}
		
			public static void exportFile(File f, ArrayList<Account> accounts) {
				
				try {
					FileWriter writer = new FileWriter(f);
					BufferedWriter bw = new BufferedWriter(writer);
				
					for(int i = 0; i < accounts.size(); i++) {
						Account a = accounts.get(i);
						bw.write(a.accountNum + "," + a.pin + "," + df1.format(a.bal));
						bw.newLine();
						bw.flush();
					}
					bw.close();
				}
				
				catch (IOException e) {
					System.out.println("Not written");
				}
				
			}
	}