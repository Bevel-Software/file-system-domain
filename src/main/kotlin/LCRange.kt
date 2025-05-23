package software.bevel.file_system_domain

/**
 * Represents a range in a text document, defined by a start and end [LCPosition].
 *
 * @property start The starting position of the range (inclusive).
 * @property end The ending position of the range (exclusive or inclusive, depending on context, but typically exclusive for text ranges).
 */
data class LCRange(
    val start: LCPosition,
    val end: LCPosition
): Comparable<LCRange> {
    /**
     * Companion object for [LCRange].
     */
    companion object {
        /**
         * Creates an empty range, where start and end positions are both (0,0).
         * @return An empty [LCRange].
         */
        fun empty(): LCRange {
            return LCRange(LCPosition(0, 0), LCPosition(0, 0))
        }
    }

    /**
     * Checks if this range completely contains another range.
     *
     * @param other The other [LCRange] to check for containment.
     * @return `true` if this range contains the other range, `false` otherwise.
     */
    operator fun contains(other: LCRange): Boolean {
        return start <= other.start && end >= other.end
    }

    /**
     * Compares this range with another range.
     * Comparison is primarily by the start position, then by the end position.
     *
     * @param other The other [LCRange] to compare with.
     * @return A negative integer, zero, or a positive integer as this range is less than, equal to, or greater than the specified range.
     */
    override fun compareTo(other: LCRange): Int {
        if(this.start != other.start) return this.start.compareTo(other.start)
        return this.end.compareTo(other.end)
    }

    /**
     * Returns a string representation of the range in "startPosition-endPosition" format.
     * @return The string representation.
     */
    override fun toString(): String {
        return "$start-$end"
    }
}