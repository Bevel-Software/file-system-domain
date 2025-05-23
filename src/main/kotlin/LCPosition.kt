package software.bevel.file_system_domain

import kotlin.math.max

/**
 * Represents a position in a text document, defined by a line and column number.
 * Line and column numbers are typically 0-indexed.
 *
 * @property line The line number (0-indexed).
 * @property column The column number (0-indexed).
 */
data class LCPosition(
    val line: Int,
    val column: Int
): Comparable<LCPosition> {
    /**
     * Returns a string representation of the position in "line:column" format.
     * @return The string representation.
     */
    override fun toString(): String {
        return "$line:$column"
    }

    /**
     * Compares this position with another position.
     * Comparison is primarily by line number, then by column number.
     *
     * @param other The other [LCPosition] to compare with.
     * @return A negative integer, zero, or a positive integer as this position is less than, equal to, or greater than the specified position.
     */
    override fun compareTo(other: LCPosition): Int {
        if(this.line != other.line) return this.line.compareTo(other.line)
        return this.column.compareTo(other.column)
    }

    /**
     * Adds another [LCPosition] to this position.
     * If the other position's line is 0, only columns are added to the current line.
     * Otherwise, lines are added and the resulting column is taken from the other position.
     *
     * @param other The [LCPosition] to add.
     * @return A new [LCPosition] representing the sum.
     */
    operator fun plus(other: LCPosition): LCPosition {
        return if(other.line == 0)
            LCPosition(this.line, this.column + other.column)
        else
            LCPosition(this.line + other.line, other.column)
    }

    /**
     * Subtracts another [LCPosition] from this position.
     * If the other position's line is 0, only columns are subtracted (result is not less than 0).
     * Otherwise, lines are subtracted (result is not less than 0) and the resulting column remains the current position's column.
     *
     * @param other The [LCPosition] to subtract.
     * @return A new [LCPosition] representing the difference.
     */
    operator fun minus(other: LCPosition): LCPosition {
        return if(other.line == 0)
            LCPosition(this.line, max(this.column - other.column, 0))
        else
            LCPosition(max(0, this.line - other.line), this.column)
    }

    /**
     * Adds an integer value to the column of this position.
     *
     * @param other The integer value to add to the column.
     * @return A new [LCPosition] with the column incremented.
     */
    operator fun plus(other: Int): LCPosition {
        return LCPosition(this.line, this.column + other)
    }

    /**
     * Companion object for [LCPosition].
     */
    companion object {
        /**
         * Represents the zero position (line 0, column 0).
         */
        val ZERO = LCPosition(0, 0)
    }
}