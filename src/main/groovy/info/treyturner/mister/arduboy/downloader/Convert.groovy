package info.treyturner.mister.arduboy.downloader

import info.treyturner.mister.arduboy.downloader.hex2bin.IntelHexException
import info.treyturner.mister.arduboy.downloader.hex2bin.Parser
import info.treyturner.mister.arduboy.downloader.hex2bin.Region
import info.treyturner.mister.arduboy.downloader.hex2bin.listeners.BinWriter
import info.treyturner.mister.arduboy.downloader.hex2bin.listeners.RangeDetector

class Convert {

    /**
     * Convert an Intel .hex file to binary format, suitable for loading into MiSTer Arduboy core
     * @param   hex     The .hex file to convert to binary
     * @param   bin     The output binary
     */
    static void hexToBin(File hex, File bin) {

        try {
            // Get streams
            FileInputStream input = new FileInputStream(hex)
            OutputStream output = new FileOutputStream(bin)

            // Init parser
            Parser parser = new Parser(input)

            // 1st iteration - calculate maximum output range
            RangeDetector rangeDetector = new RangeDetector()
            parser.setDataListener(rangeDetector)
            parser.parse()
            input.getChannel().position(0)

            // Setup the range (pad to 32k)
            Region outputRegion = rangeDetector.getFullRangeRegion()
            String dataTo = "0x7FFF"
            outputRegion.setAddressEnd(Long.parseLong(dataTo.substring(2), 16))

            // 2nd iteration - actually write output
            BinWriter writer = new BinWriter(outputRegion, output, false)
            parser.setDataListener(writer)
            parser.parse()

            // Close streams
            input.close()
            output.close()
        } catch (IntelHexException | IOException ex) {
            System.err.println(ex.toString())
        }

        // Delete the hex
        hex.delete()
    }

    /**
     * Convert a long byte count to human readable form, ie 28510 ->
     * https://gist.github.com/nikbucher/9687112
     * @param   bytes   The number of bytes to be reformatted
     * @return
     */
    static String bytesToHumanReadable(long bytes) {
        Long base = 1024L
        int decimals = 3
        List prefix = ['', 'K', 'M', 'G', 'T']
        int i = Math.log(bytes)/Math.log(base) as Integer
        i = (i >= prefix.size() ? prefix.size()-1 : i)
        Math.round((bytes / base**i) * 10**decimals) / 10**decimals + prefix[i]
    }
}
