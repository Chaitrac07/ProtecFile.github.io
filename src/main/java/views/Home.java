package views;

import DAO.userDAO;
import model.User;
import service.SendOTP;
import service.UserService;
import service.generateOTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

public class Home {
    public void Homepage() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(" Welcome to the File Hider App!! ");
        System.out.println("");
        System.out.println("1. Please enter number 1 to SignIn");
        System.out.println("2. Please enter number 2 to Register");
        System.out.println("3. Please enter number 0 to SignOut/Exit");
        int choice;
        try {
            choice = Integer.parseInt(br.readLine());

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        switch (choice) {
            case 1 -> Signin();
            case 2 -> Register();
            case 0 -> System.exit(0);
        }

    }

    private void Signin() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your registered email");
        String email = sc.nextLine();
        try {
            if (userDAO.isexists(email)) {
                String genOTP = generateOTP.getOTP();
                SendOTP.sendOTP(email, genOTP);
                System.out.println("Enter the received OTP");
                String otp = sc.nextLine();

                if (otp.equals(genOTP)) {
                    new Userview(email).home();

                    System.out.println("OTP verified , Welcome to the File Hider App");
                } else {
                    System.out.println("OTP not verified, Please try again!!");
                }
            } else {
                System.out.println("User does not exist !");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    private void Register() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your Name");
        String name = sc.nextLine();
        System.out.println("Enter your email");
        String email = sc.nextLine();

        String genOTP = generateOTP.getOTP();
        SendOTP.sendOTP(email, genOTP);
        System.out.println("Enter the received OTP");
        String otp = sc.nextLine();
        if (otp.equals(genOTP)) {
            User user = new User(name, email);
            int response = UserService.saveUser(user);
            switch (response) {
                case 0 -> System.out.println("User Registered Successfully");
                case 1 -> System.out.println("User already exists");
            }
        } else {
            System.out.println("OTP not verified");
        }

    }

}
