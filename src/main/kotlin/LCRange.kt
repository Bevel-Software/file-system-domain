package software.bevel.file_system_domain

data class LCRange(
    val start: LCPosition,
    val end: LCPosition
): Comparable<LCRange> {
    companion object {
        fun empty(): LCRange {
            return LCRange(LCPosition(0, 0), LCPosition(0, 0))
        }
    }

    operator fun contains(other: LCRange): Boolean {
        return start <= other.start && end >= other.end
    }

    override fun compareTo(other: LCRange): Int {
        if(this.start != other.start) return this.start.compareTo(other.start)
        return this.end.compareTo(other.end)
    }

    override fun toString(): String {
        return "$start-$end"
    }
}