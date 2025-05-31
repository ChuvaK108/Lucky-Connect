package com.example.luckyconnect.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Состояния VPN подключения
 */
enum class VpnConnectionState {
    DISCONNECTED,
    CONNECTING,
    CONNECTED,
    DISCONNECTING,
    ERROR
}

/**
 * Модель VPN сервера
 */
data class VpnServer(
    val id: String,
    val name: String,
    val country: String,
    val city: String,
    val flagEmoji: String,
    val ping: Int, // в миллисекундах
    val load: Int, // в процентах
    val isPremium: Boolean = false,
    val isOptimal: Boolean = false
)

/**
 * Статистика подключения
 */
data class ConnectionStats(
    val uploadSpeed: Long = 0L, // байт/сек
    val downloadSpeed: Long = 0L, // байт/сек
    val totalUpload: Long = 0L, // байты
    val totalDownload: Long = 0L, // байты
    val connectionTime: Long = 0L, // миллисекунды
    val ping: Int = 0 // миллисекунды
)

/**
 * Настройки VPN
 */
data class VpnSettings(
    val autoConnect: Boolean = false,
    val killSwitch: Boolean = true,
    val dnsProtection: Boolean = true,
    val adBlocker: Boolean = false,
    val selectedProtocol: VpnProtocol = VpnProtocol.WIREGUARD,
    val theme: AppTheme = AppTheme.DARK
)

/**
 * VPN протоколы
 */
enum class VpnProtocol(val displayName: String, val icon: ImageVector) {
    WIREGUARD("WireGuard", Icons.Default.Security),
    OPENVPN("OpenVPN", Icons.Default.VpnKey),
    IKEV2("IKEv2", Icons.Default.Shield)
}

/**
 * Темы приложения
 */
enum class AppTheme {
    LIGHT,
    DARK,
    AUTO
}

/**
 * Уровни подписки
 */
enum class SubscriptionTier {
    FREE,
    PREMIUM,
    ULTIMATE
}

/**
 * Информация о пользователе
 */
data class UserProfile(
    val email: String = "",
    val subscriptionTier: SubscriptionTier = SubscriptionTier.FREE,
    val subscriptionExpiry: Long = 0L,
    val dataUsedToday: Long = 0L,
    val dailyLimit: Long = 1024 * 1024 * 1024L // 1GB для бесплатных пользователей
) 