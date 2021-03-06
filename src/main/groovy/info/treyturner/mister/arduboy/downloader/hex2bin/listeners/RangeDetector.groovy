/**
 * Copyright (c) 2015, Jan Breuer All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package info.treyturner.mister.arduboy.downloader.hex2bin.listeners

import info.treyturner.mister.arduboy.downloader.hex2bin.DataListener
import info.treyturner.mister.arduboy.downloader.hex2bin.MemoryRegions
import info.treyturner.mister.arduboy.downloader.hex2bin.Region

/**
 * First pass listener to calculate data address range for further use
 *
 * @author riilabs
 * @author Jan Breuer
 */
class RangeDetector implements DataListener {

    private final MemoryRegions regions = new MemoryRegions()

    @Override
    void data(long address, byte[] data) {
        regions.add(address, data.length)
    }

    @Override
    void eof() {
        regions.compact()
    }

    void reset() {
        regions.clear()
    }

    Region getFullRangeRegion() {
        return regions.getFullRangeRegion()
    }

    MemoryRegions getMemoryRegions() {
        return regions
    }
}
