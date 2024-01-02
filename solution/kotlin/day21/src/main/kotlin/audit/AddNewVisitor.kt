package audit

import java.time.LocalDateTime

data class AddNewVisitor(val visitor: String, val time: LocalDateTime)