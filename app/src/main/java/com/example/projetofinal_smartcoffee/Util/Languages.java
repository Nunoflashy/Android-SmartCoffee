package com.example.projetofinal_smartcoffee.Util;

/**
 * Class para facilitar a internacionalização
 * com suporte para strings formatadas
 */
public class Languages {
    public static void SetLanguage(String which) {
        language = which;
    }

    public static String UserRegisteredMsg(String username) {
        switch(language) {
            case "pt": return String.format("A sua conta %s foi registada com sucesso!\nBem-Vindo à Smart Coffee", username);
            case "en": return String.format("Your account %s has been registered successfully!\nWelcome to Smart Coffee", username);
        }
        return null;
    }

    public static String BlockConfirmMsg(String username) {
        switch(language) {
            case "pt": return String.format("Tem a certeza que pretende bloquear o utilizador %s?", username);
            case "en": return String.format("Are you sure you want to block the user %s?", username);
        }
        return null;
    }

    public static String BlockedMsg(String username) {
        switch(language) {
            case "pt": return String.format("O utilizador %s foi bloqueado do sistema.", username);
            case "en": return String.format("The user %s was blocked from the system.", username);
        }
        return null;
    }

    public static String UnblockConfirmMsg(String username) {
        switch(language) {
            case "pt": return String.format("Tem a certeza que pretende desbloquear o utilizador %s?", username);
            case "en": return String.format("Are you sure you want to unblock the user %s?", username);
        }
        return null;
    }

    public static String UnblockedMsg(String username) {
        switch(language) {
            case "pt": return String.format("O utilizador %s foi desbloqueado do sistema.", username);
            case "en": return String.format("The user %s was unblocked from the system.", username);
        }
        return null;
    }

    public static String RemoveConfirmMsg(String username) {
        switch(language) {
            case "pt": return String.format("Tem a certeza que pretende remover o utilizador %s?", username);
            case "en": return String.format("Are you sure you want to delete the user %s?", username);
        }
        return null;
    }

    public static String RemovedMsg(String username) {
        switch(language) {
            case "pt": return String.format("O utilizador %s foi removido do sistema.", username);
            case "en": return String.format("The user %s was removed from the system.", username);
        }
        return null;
    }

    public static String UserAlreadyRegisteredMsg(String username) {
        switch(language) {
            case "pt": return String.format("O utilizador %s já se encontra registado!", username);
            case "en": return String.format("The user %s is already registered!", username);
        }
        return null;
    }

    public static String ProductAddedMsg(String productName) {
        switch(language) {
            case "pt": return String.format("Produto %s adicionado!", productName);
            case "en": return String.format("Added product %s!", productName);
        }
        return null;
    }

    public static String ProductInStockMsg(String productName) {
        switch(language) {
            case "pt": return String.format("Produto %s está agora em stock!", productName);
            case "en": return String.format("Product %s is now in stock!", productName);
        }
        return null;
    }

    public static String ProductOutStockMsg(String productName) {
        switch(language) {
            case "pt": return String.format("Produto %s está agora fora de stock!", productName);
            case "en": return String.format("Product %s is now out of stock!", productName);
        }
        return null;
    }

    private static String language = null;
}
