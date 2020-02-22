# MiSTer-Arduboy-Downloader

Download Arduboy games from public repositories and convert them to binaries for use on MiSTer.

Games are stored as `genre/title (author) [date, time]`

Old versions of games will be deleted if new versions are found.

Currently downloads from [Erwin's Arduboy Collection](https://arduboy.ried.cl/); I'll see what I can do about TEAM a.r.g. or other repos that pop up.

Some games don't appear to boot at present, stay tuned to the [Arduboy MiSTer Core repo](https://github.com/uXeBoy/Arduboy_MiSTer) for progress.


## Requirements

- Java JDK 8


## Usage

You can download and run the JAR:
```
java -jar MiSTerArduboyDownloader-v1.0.jar -h

Usage: MiSTerArduboyDownloader [-hV] [-o=<OUTPUT_PATH>] [-r=<REPO_URL>]
  -h, --help              Show this help message and exit.
  -o, --output=<OUTPUT_PATH>
                          Output path. The folder to download genre folders. Defaults to ./Games/
  -r, --repo=<REPO_URL>   Repo URL. Defaults to Erwin's
  -V, --version           Print version information and exit.
```

Alternately you can run from source in Windows:
```
gradlew.bat run
```
or OSX/Linux:
```
./gradlew run
```
