package com.jdbc;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class AddressBookMain {

	 static Scanner scanner = new Scanner(System.in);

	    public static void main(String[] args) {

	        boolean exit = false;
	        while (!exit) {
	            System.out.println(" Press\n 1 ->  Retrieve data\n 2 -> Exit");
	            switch (scanner.nextInt()) {
	                case 1:
	                    retrieveData();
	                    break;
	                case 2:
	                    exit = true;
	            }
	        }
	    }

	    private static void retrieveData() {
	        AddressBook addressBookRepo = new AddressBook();
	        List<Contacts> employeeInfoList = addressBookRepo.retrieveData();
	        for (Contacts employee : employeeInfoList)
	         {
	            System.out.println(employee + "\n");
	        }
	    }
}