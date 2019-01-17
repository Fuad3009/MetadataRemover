/*
 * MIT License
 *
 * Copyright (c) 2018 Jan Heinrich Reimer
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package rocks.poopjournal.metadataremover.model.metadata

import rocks.poopjournal.metadataremover.model.resources.MediaType
import java.io.File

// TODO Maybe split into readers and writers.
interface MetadataHandler {
    val readableMimeTypes: Set<MediaType>
    val writableMimeTypes: Set<MediaType>

    /**
     * Load the metadata from the [input file][inputFile].
     *
     * Note: The metadata's attributes should explicitly not contain any attribute
     * that is obvious to the file system, e.g. the file name, last modified date, or file size.
     * Instead a [MetadataHandler] should be as specific to it's [readableMimeTypes] as it can.
     */
    suspend fun loadMetadata(mediaType: MediaType, inputFile: File): Metadata?

    /**
     * Remove the metadata from the [input file][inputFile] and save a copy
     * without any contained metadata to the [output file][outputFile].
     *
     * Note: Depending on the file type this process might cause quality loss (e.g. for JPEG's).
     *
     * @return `true` if the removal was successful, `false` otherwise.
     */
    suspend fun removeMetadata(mediaType: MediaType, inputFile: File, outputFile: File): Boolean
}