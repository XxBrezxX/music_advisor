package advisor.controllers;

import advisor.providers.ApiProvider;
import advisor.utils.ProgramArguments;

import java.util.Scanner;

public class MenuApp {
    private final ProgramArguments programArguments;

    public MenuApp(ProgramArguments programArguments) {
        this.programArguments = programArguments;
    }

    public void startUI() {
        Scanner scanner = new Scanner(System.in);
        String input;
        ApiProvider apiProvider = new ApiProvider(programArguments);
        do {
            input = scanner.nextLine();
            switch (input) {
                case "new" -> apiProvider.getNew();
                case "featured" -> apiProvider.getFeatured();
                case "categories" -> apiProvider.getCategories();
                case "exit" -> apiProvider.getExit();
                case "auth" -> apiProvider.authorize();
                case "next" -> apiProvider.next();
                case "prev" -> apiProvider.prev();
                default -> {
                    if (input.contains("playlists")) {
                        apiProvider.getPlaylists(input.split(" ")[1].toLowerCase());
                    }
                }
            }
        } while (!input.equals("asdfg"));
    }
}
