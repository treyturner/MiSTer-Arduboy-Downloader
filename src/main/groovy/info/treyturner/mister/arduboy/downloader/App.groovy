package info.treyturner.mister.arduboy.downloader

import groovy.json.*
import java.text.SimpleDateFormat
import groovyjarjarpicocli.CommandLine
import groovyjarjarpicocli.CommandLine.Command
import groovyjarjarpicocli.CommandLine.Option

@Command(name = "MiSTerArduboyDownloader", mixinStandardHelpOptions = true, version = "1.0")
class App implements Runnable {

    public static final String ERWIN_REPO_URL = "https://arduboy.ried.cl/repo.json"

    @Option(names = [ "-o", "--output" ],
            description = "Output path. The folder to download genre folders. Defaults to Games/")
    private static String OUTPUT_PATH = "Games"

    @Option(names = [ "-r", "--repo" ], description = "Repo URL. Defaults to Erwin's")
    private static String REPO_URL = ERWIN_REPO_URL

    static void processErwinRepo() {
        // Set time zone for parsing zulu datetimes from the repo
        TimeZone.setDefault(TimeZone.getTimeZone('UTC'))

        // Get repo into an object
        println "Fetching $REPO_URL"
        def repo = new JsonSlurper().parseText(new URL(REPO_URL).text)

        // Pull games from the repo
        def games = repo.items

        println "Games in repo: ${games.size()}"

        games.each { game ->
            game.binaries.each { binary ->

                // Get timestamp of published binary
                Date timestamp
                try {
                    timestamp = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'").parse(game.date)
                } catch(ignored) {
                    timestamp = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss").parse(game.date)
                }

                // Create the download directory handle
                File downloadDir = new File(OUTPUT_PATH + File.separator + game.genre)
                String unicodeEncoded = "${game.title} (${game.author})"
                String unicodeDecoded = StringEscapeUtils.unescapeJava(unicodeEncoded)
                // Sanitize the game name for a file path
                String sanitizedName = unicodeDecoded.replaceAll(/[\\/:*?"<>|]/, "_")
                // Append the timestamp to the downloaded filename
                String fileNameBase = sanitizedName + " [${new SimpleDateFormat("yyyy-MM-dd', 'hh-mm-ss").format(timestamp)}]"

                // Get the file handles
                File hex = new File(downloadDir, "${fileNameBase}.hex")
                File bin = new File(downloadDir, "${fileNameBase}.bin")

                if (!bin.exists()) {
                    println("Downloading ${game.title} to ${bin.getCanonicalPath()}")

                    // Make directory if needed
                    downloadDir.mkdirs()

                    // Look for earlier versions
                    def pattern = ~/${sanitizedName}/
                    downloadDir.eachFileRecurse{
                        if (pattern.matcher(it.name).find()) {
                            println("\tDeleting ${it.getCanonicalPath()} (${Convert.bytesToHumanReadable(it.size())})")
                            it.delete()
                        }
                    }

                    // Download the hex
                    hex.withOutputStream { output ->
                        new URL(binary.filename).withInputStream { input ->
                            output << input
                        }
                    }

                    // Convert hex to bin
                    Convert.hexToBin(hex, bin)
                } else {
                    println("Skipping ${game.title} (found ${bin.getCanonicalPath()})")
                }
            }
        }
    }

    void run() {
        processErwinRepo()
    }

    static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args)
        System.exit(exitCode)
    }
}
