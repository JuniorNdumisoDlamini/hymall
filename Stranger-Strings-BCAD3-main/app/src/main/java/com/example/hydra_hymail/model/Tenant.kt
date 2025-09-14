package com.hymall.model

/**
 * Data model class representing a Tenant (store owner) in HyMall.
 *
 * Tenants register with email/password authentication (Firebase Auth),
 * and their store details are saved in Firestore.
 *
 * This model will be mapped directly to Firestore documents.
 */
data class Tenant(
    val tenantId: String = "",       // Unique Firebase UID for tenant
    val storeName: String = "",      // Name of the store (e.g. "Cool Sneakers")
    val ownerName: String = "",      // Full name of the tenant/owner
    val email: String = "",          // Email used for Firebase login
    val phoneNumber: String = "",    // Contact phone number
    val category: String = "",       // Store category (Fashion, Food, Electronics, etc.)
    val description: String = "",    // Short description about the store
    val location: String = "",       // Store location (mall wing, GPS coords, etc.)
    val profileImageUrl: String = "",// Cloudinary or Firebase Storage profile image
    val createdAt: Long = System.currentTimeMillis(), // Timestamp of registration
    val isVerified: Boolean = false  // Admin verification flag
)
