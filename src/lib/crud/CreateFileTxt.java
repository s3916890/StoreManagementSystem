package lib.crud;

import java.io.File;

public class CreateFileTxt{
    public File path(String direct)  {

        boolean success = false;

        //Creating the path of txt file, if is not available
        File directory = new File(direct);
        if (directory.exists())
        { System.out.println("Directory already exists ...");
        }
        else {
            System.out.println("Directory is not available, creating now");
            success = directory.mkdir();
            if (success) {
                System.out.printf("Created new directory : %s%n", direct);
            }
            else {
                System.out.printf("Failed to create new directory: %s%n", direct);
            }
        }

        return directory;
    }
}

