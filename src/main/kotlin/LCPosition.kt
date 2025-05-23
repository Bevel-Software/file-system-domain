package software.bevel.file_system_domain

import kotlin.math.max

data class LCPosition(
    val line: Int,
    val column: Int
): Comparable<LCPosition> {
    override fun toString(): String {
        return "$line:$column"
    }

    override fun compareTo(other: LCPosition): Int {
        if(this.line != other.line) return this.line.compareTo(other.line)
        return this.column.compareTo(other.column)
    }

    operator fun plus(other: LCPosition): LCPosition {
        return if(other.line == 0)
            LCPosition(this.line, this.column + other.column)
        else
            LCPosition(this.line + other.line, other.column)
    }

    operator fun minus(other: LCPosition): LCPosition {
        return if(other.line == 0)
            LCPosition(this.line, max(this.column - other.column, 0))
        else
            LCPosition(max(0, this.line - other.line), this.column)
    }

    operator fun plus(other: Int): LCPosition {
        return LCPosition(this.line, this.column + other)
    }

    companion object {
        val ZERO = LCPosition(0, 0)
    }
}