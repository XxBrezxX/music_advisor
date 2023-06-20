package advisor.providers;

import advisor.models.New;
import advisor.models.OAuthModel;
import advisor.models.Pair;
import advisor.models.Playlist;
import advisor.utils.ProgramArguments;
import advisor.utils.concreteStrat.CategoryPrinter;
import advisor.utils.concreteStrat.FeaturedPrinter;
import advisor.utils.concreteStrat.NewPrinter;
import advisor.utils.concreteStrat.PlaylistPrinter;
import advisor.utils.context.TypePrinter;

import java.io.IOException;
import java.util.List;

public class ApiProvider {
    private final ProgramArguments programArguments;
    private final OAuthModel oAuthModel;
    private SpotifyProvider spotifyProvider;
    private Boolean authorization;
    private final TypePrinter typePrinter;

    public ApiProvider(ProgramArguments programArguments) {
        this.programArguments = programArguments;
        this.oAuthModel = new OAuthModel(programArguments);
        this.authorization = false;
        this.typePrinter = new TypePrinter();
    }


    //    featured — a list of Spotify-featured playlists with their links fetched from API;
    public void getFeatured() {
        if (!authorization) {
            System.out.println("Please, provide access for application.");
            return;
        }
        try {
            this.typePrinter.setPrinter(
                    new FeaturedPrinter(
                            this.spotifyProvider.getFeatured(),
                            programArguments.getPageSize()
                    )
            );
            this.typePrinter.print();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //    new — a list of new albums with artists and links on Spotify;
    public void getNew() {
        if (!authorization) {
            System.out.println("Please, provide access for application.");
            return;
        }
        try {
            this.typePrinter.setPrinter(
                    new NewPrinter(
                            this.spotifyProvider.getNew(),
                            programArguments.getPageSize()
                    )
            );
            this.typePrinter.print();
//            System.out.println(this.spotifyProvider.getNew());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //    categories — a list of all available categories on Spotify (just their names);
    public void getCategories() {
        if (!authorization) {
            System.out.println("Please, provide access for application.");
            return;
        }
        try {
            this.typePrinter.setPrinter(
                    new CategoryPrinter(
                            this.spotifyProvider.getCategories(),
                            programArguments.getPageSize()
                    )
            );
            this.typePrinter.print();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //    playlists C_NAME, where C_NAME is the name of category. The list contains playlists of this category and their links on Spotify;

    public void getPlaylists(String mood) {
        if (!authorization) {
            System.out.println("Please, provide access for application.");
            return;
        }
        try {
            Pair<Boolean, Object> response = this.spotifyProvider.getPlaylists(mood);
            if (response.getKey()) {
                this.typePrinter.setPrinter(
                        new PlaylistPrinter(
                                (List<Playlist>) response.getValue(),
                                programArguments.getPageSize()
                        )
                );
                this.typePrinter.print();
            } else {
                System.out.println((String) response.getValue());
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //    exit shuts down the application.
    public void getExit() {
//        System.out.println("---GOODBYE!---");
//        System.exit(0);
    }

    public void authorize() {
        oAuthModel.getUserAuth();
        oAuthModel.waitAuth();
        oAuthModel.reqToken();
        this.authorization = oAuthModel.getAuth();
        if (this.authorization) {
            spotifyProvider = new SpotifyProvider(oAuthModel.getToken(), programArguments);
            System.out.println("Success!");
        } else {
            System.out.println("Error :(");
        }
    }

    public void next() {
        if (!this.typePrinter.next()) {
            System.out.println("No more pages.");
            return;
        }
        this.typePrinter.print();
    }

    public void prev() {
        if (!this.typePrinter.prev()) {
            System.out.println("No more pages.");
            return;
        }
        this.typePrinter.print();
    }

}
