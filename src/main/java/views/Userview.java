package views;

import DAO.DataDAO;
import model.Data;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Userview {
    private String email;

    Userview(String email) {
        this.email = email;
    }

    public void home() {

        do {
            System.out.println(" Welcome " + this.email);
            System.out.println(" ");
            System.out.println("1. Press 1 to show hidden files");
            System.out.println("2. Press 2 to hide a new file");
            System.out.println("3. Press 3 to unhide a file");
            System.out.println("4. Press 0 to Exit");

            Scanner sc = new Scanner(System.in);
            int ch = Integer.parseInt(sc.nextLine());

            switch (ch) {
                case 1 -> {
                    try {
                        List<Data> files = DataDAO.getAllFiles(this.email);
                        System.out.println("Hidden files:");
                        System.out.println("ID - File Name");
                        System.out.println(" ");
                        for (Data file : files) {
                            System.out.println(file.getId() + " - " + file.getFileName());
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                case 2 -> {
                    System.out.println("Enter the file path that to be hidden");
                    String path = sc.nextLine();
                    File f = new File(path);
                    Data file = new Data(0, f.getName(), path, this.email);
                    try {
                        DataDAO.hideFile(file);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                case 3 -> {
                    List<Data> files = null;
                    try {
                        files = DataDAO.getAllFiles(this.email);
                        System.out.println("Hidden files:");
                        System.out.println("ID - File Name");
                        System.out.println(" ");
                        for (Data file : files) {
                            System.out.println(file.getId() + " - " + file.getFileName());
                        }
                        System.out.println("Enter the ID of file to Unhide");
                        int id = Integer.parseInt(sc.nextLine());
                        boolean isValidId = false;
                        for (Data file : files) {
                            if (file.getId() == id) {
                                isValidId = true;
                                break;
                            }
                        }
                        if (isValidId) {
                            DataDAO.unhide(id);
                        } else {
                            System.out.println("Wrong ID! Try again");
                        }
                    } catch (SQLException | IOException e) {
                        e.printStackTrace();
                    }
                }
                case 0 -> {
                    System.exit(0);
                }
            }
        }
        while (true);
    }
}


