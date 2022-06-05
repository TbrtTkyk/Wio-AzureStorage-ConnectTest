package com.example.tablestest;

import com.microsoft.azure.storage.StorageException;

public class PrintHelper {
    public static void PrintException(Throwable t) {
        t.printStackTrace();
        if (t instanceof StorageException) {
            if (((StorageException) t).getExtendedErrorInformation() != null) {
                System.out.println(String.format("\nError: %s", ((StorageException) t).getExtendedErrorInformation().getErrorMessage()));
            }
        }
    }
}
