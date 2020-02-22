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
package info.treyturner.mister.arduboy.downloader.hex2bin

/**
 * Type of one record in Intel HEX file (type of line)
 *
 * @author Jan Breuer
 */
enum RecordType {

    DATA(0x00),
    EOF(0x01),
    EXT_SEG(0x02),
    START_SEG(0x03),
    EXT_LIN(0x04),
    START_LIN(0x05),
    UNKNOWN(0xFF);
    int id

    RecordType(int id) {
        this.id = id
    }

    /**
     * Convert enum value to integer
     *
     * @return record type integer value
     */
    int toInt() {
        return id
    }

    /**
     * Convert integer value to enum value
     *
     * @param id record type integer value
     * @return record type enum value
     */
    static RecordType fromInt(int id) {
        for (RecordType d : values()) {
            if (d.id == id) {
                return d
            }
        }
        return UNKNOWN
    }
}
