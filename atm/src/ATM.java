import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

	public class ATM {
		
		private static DecimalFormat df2 = new DecimalFormat("#,##0.00");
		
		public static void main(String[] args) throws Exception  {
			//Import accounts
			
			File f = new File("/Users/tejaskelkar/Downloads/ATM.txt");

			ArrayList<Account> accounts = Account.importFile(f);

			//Account.printAccountList(accounts);

			//Atm session
			int count = 1;
			int accountNumber;
			int pin;
			int accNum = 00000;
			double bal;
			
			boolean opening = true;
			boolean active = false;
			boolean login = true;
			boolean authorizeAcc = false;
			boolean authorizePIN = false;
			Scanner scan = new Scanner(System.in);
			Account selectedAcc =  new Account();
			System.out.println("Welcome to the ATM machine.");
			
			while(login) {
				System.out.println();
				System.out.println("Please select an option:");
				System.out.println("[1] Login");
				System.out.println("[2] Open a new account");
				System.out.println("[3] Exit");
				System.out.print("Enter selection: ");
				switch(scan.nextInt()) {
					case 1: login = false;
							break;
					case 2: 
							while(opening) {
								System.out.print("Please enter a 4 digit PIN for your account: ");
								int p = scan.nextInt();
								System.out.print("Please re-enter your 4 digit PIN: ");
								int q = scan.nextInt();
								if (p == q) {
									System.out.println("Would you like to make an initial deposit?");
									System.out.println("[1] Yes");
									System.out.println("[2] No");
									System.out.print("Enter selection: ");
									int yn = scan.nextInt();
									if(yn == 1) {
										System.out.print("Enter initial deposit amount: ");
										int in = scan.nextInt();
										System.out.println("Congratulations, you have opened an account with a balance of: $" + df2.format(in));
										System.out.println("Your account number is: " + Account.addAccount(accounts, p, in));
										Account.exportFile(f, accounts);
										opening = false;
										login = false;
										
										
									} else if(yn == 2) {
										System.out.println("Congratulations, you have opened an account. Your account number is: " + Account.addAccount(accounts, p, 0));
										Account.exportFile(f, accounts);
										login = false;
										opening = false;
										
									} 
											
								} else
									System.out.println("PINs do not match. Please try again.");
									continue;
							}
							break;
					case 3: login = false;
							return;
					default: System.out.println("Please enter a valid selection.");
				}
			}
			//Authorize users account number, enter -1 to exit
			while (!authorizeAcc) {
				System.out.println();
				System.out.print("Enter your 5 digit account number or enter -1 to exit: ");
				accNum = scan.nextInt();
				if (accNum == -1)
					return;
				if (Account.getAccount(accounts, accNum) == null) {
					System.out.println("Invalid account number. Please try again.");
					continue;
				} else
					selectedAcc = Account.getAccount(accounts, accNum);
					authorizeAcc = true;
				}
			
			accountNumber = Account.getAccount(accounts, accNum).getAccountNum();
			pin = Account.getAccount(accounts, accNum).getPin();
			bal = Account.getAccount(accounts, accNum).getBal();
			
			//Authorize the users PIN, exit on 3 invalid attempts
			while (!authorizePIN) {
				System.out.println();
				System.out.print("Please enter your 4 digit PIN: ");
				int userPin = scan.nextInt();
				if(count == 3) {
					System.out.println("Three invalid attempts. Please try again later.");
					break;
				}
				if (userPin == pin) {
					System.out.println("Success.");
					active = true;
					authorizePIN = true;
					break;
				}
				else
					count++;
				System.out.println("Invalid PIN. Try again.");
				}
			
			while(active) {
				System.out.println();
				System.out.println("Please select an option:");
				System.out.println("[1] Deposit");
				System.out.println("[2] Withdraw");
				System.out.println("[3] Check account balance");
				System.out.println("[4] Exit");
				System.out.print("Enter selection: ");
				switch(scan.nextInt()) {
					case 1: System.out.print("Enter deposit amount or enter 0 to cancel: ");
							double dep = scan.nextDouble();
							if (dep == 0)
								continue;
							Account.getAccount(accounts, accNum).updateBal(dep, 2);
							System.out.println("Deposit sucessful. Account balance is now: $" + df2.format(Account.getAccount(accounts, accNum).getBal()));
							Account.exportFile(f, accounts);
							continue;
					case 2: System.out.print("Enter withdraw amount or enter 0 to cancel: ");
							double with = scan.nextDouble();
							if (with == 0)
								continue;
							Account.getAccount(accounts, accNum).updateBal(with, 1);
							System.out.println("Withdraw sucessful. Account balance is now: $" + df2.format(Account.getAccount(accounts, accNum).getBal()));
							Account.exportFile(f, accounts);
							continue;
					case 3: System.out.println("Your current account balance is: $" + df2.format(Account.getAccount(accounts, accNum).getBal()));
							continue;
					case 4: System.out.println("Thank you for using the ATM.");
							active = false;
							break;
					default: System.out.println("Please enter a valid selection");
				}
			
			}
			Account.exportFile(f, accounts);
	}
		}