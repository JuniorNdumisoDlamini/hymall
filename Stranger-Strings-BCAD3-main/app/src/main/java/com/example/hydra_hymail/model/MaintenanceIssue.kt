package com.example.hydra_hymail.model


data class MaintenanceIssue(
    val id: String = "",
    val reportedBy: String = "",
    val description: String = "",
    val category: Category = Category.INFRASTRUCTURE,
    val location: LocationData? = null,
    val status: IssueStatus = IssueStatus.PENDING,
    val createdAt: Long = System.currentTimeMillis()
)

enum class IssueStatus {
    PENDING,
    IN_PROGRESS,
    RESOLVED
}
