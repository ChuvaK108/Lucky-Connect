package com.example.luckyconnect.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random

/**
 * Репозиторий для управления VPN данными
 */
class VpnRepository {
    
    private val _connectionState = MutableStateFlow(VpnConnectionState.DISCONNECTED)
    val connectionState: StateFlow<VpnConnectionState> = _connectionState.asStateFlow()
    
    private val _selectedServer = MutableStateFlow<VpnServer?>(null)
    val selectedServer: StateFlow<VpnServer?> = _selectedServer.asStateFlow()
    
    private val _connectionStats = MutableStateFlow(ConnectionStats())
    val connectionStats: StateFlow<ConnectionStats> = _connectionStats.asStateFlow()
    
    private val _settings = MutableStateFlow(VpnSettings())
    val settings: StateFlow<VpnSettings> = _settings.asStateFlow()
    
    private val _userProfile = MutableStateFlow(UserProfile(email = "user@example.com"))
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()
    
    // Тестовые серверы
    private val testServers = listOf(
        VpnServer("us-1", "Ultra Fast", "США", "Нью-Йорк", "🇺🇸", 15, 23, isOptimal = true),
        VpnServer("uk-1", "Lightning", "Великобритания", "Лондон", "🇬🇧", 28, 45),
        VpnServer("jp-1", "Tokyo Speed", "Япония", "Токио", "🇯🇵", 45, 34),
        VpnServer("de-1", "Berlin Pro", "Германия", "Берлин", "🇩🇪", 22, 56, isPremium = true),
        VpnServer("ca-1", "Maple Shield", "Канада", "Торонто", "🇨🇦", 18, 12),
        VpnServer("au-1", "Outback VPN", "Австралия", "Сидней", "🇦🇺", 68, 78),
        VpnServer("sg-1", "Singapore Turbo", "Сингапур", "Сингапур", "🇸🇬", 35, 29),
        VpnServer("nl-1", "Amsterdam Express", "Нидерланды", "Амстердам", "🇳🇱", 25, 41, isPremium = true),
        VpnServer("fr-1", "Paris Connect", "Франция", "Париж", "🇫🇷", 30, 38),
        VpnServer("br-1", "Rio Stream", "Бразилия", "Рио-де-Жанейро", "🇧🇷", 95, 62),
        VpnServer("in-1", "Mumbai Gateway", "Индия", "Мумбаи", "🇮🇳", 78, 54),
        VpnServer("kr-1", "Seoul Velocity", "Южная Корея", "Сеул", "🇰🇷", 42, 33)
    )
    
    fun getServers(): List<VpnServer> = testServers
    
    fun getOptimalServer(): VpnServer? = testServers.find { it.isOptimal }
    
    suspend fun connect(server: VpnServer? = null) {
        val targetServer = server ?: getOptimalServer() ?: testServers.first()
        
        _connectionState.value = VpnConnectionState.CONNECTING
        _selectedServer.value = targetServer
        
        // Симуляция подключения
        delay(2000)
        
        if (Random.nextFloat() > 0.1f) { // 90% успешных подключений
            _connectionState.value = VpnConnectionState.CONNECTED
            startStatsUpdates()
        } else {
            _connectionState.value = VpnConnectionState.ERROR
        }
    }
    
    suspend fun disconnect() {
        _connectionState.value = VpnConnectionState.DISCONNECTING
        delay(1000)
        _connectionState.value = VpnConnectionState.DISCONNECTED
        _connectionStats.value = ConnectionStats()
    }
    
    private suspend fun startStatsUpdates() {
        var connectionStart = System.currentTimeMillis()
        var totalUpload = 0L
        var totalDownload = 0L
        
        while (_connectionState.value == VpnConnectionState.CONNECTED) {
            delay(1000)
            
            val uploadSpeed = Random.nextLong(100_000, 2_000_000) // 100KB/s - 2MB/s
            val downloadSpeed = Random.nextLong(500_000, 10_000_000) // 500KB/s - 10MB/s
            
            totalUpload += uploadSpeed
            totalDownload += downloadSpeed
            
            _connectionStats.value = ConnectionStats(
                uploadSpeed = uploadSpeed,
                downloadSpeed = downloadSpeed,
                totalUpload = totalUpload,
                totalDownload = totalDownload,
                connectionTime = System.currentTimeMillis() - connectionStart,
                ping = _selectedServer.value?.ping ?: 0
            )
        }
    }
    
    fun updateSettings(newSettings: VpnSettings) {
        _settings.value = newSettings
    }
    
    fun updateUserProfile(newProfile: UserProfile) {
        _userProfile.value = newProfile
    }
} 