package com.example.luckyconnect.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.luckyconnect.data.*
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel для управления VPN функциональностью
 */
class VpnViewModel : ViewModel() {
    
    private val repository = VpnRepository()
    
    // Exposed state flows
    val connectionState: StateFlow<VpnConnectionState> = repository.connectionState
    val selectedServer: StateFlow<VpnServer?> = repository.selectedServer
    val connectionStats: StateFlow<ConnectionStats> = repository.connectionStats
    val settings: StateFlow<VpnSettings> = repository.settings
    val userProfile: StateFlow<UserProfile> = repository.userProfile
    
    /**
     * Получить список всех серверов
     */
    fun getServers(): List<VpnServer> = repository.getServers()
    
    /**
     * Получить оптимальный сервер
     */
    fun getOptimalServer(): VpnServer? = repository.getOptimalServer()
    
    /**
     * Подключиться к VPN
     */
    fun connect(server: VpnServer? = null) {
        viewModelScope.launch {
            repository.connect(server)
        }
    }
    
    /**
     * Отключиться от VPN
     */
    fun disconnect() {
        viewModelScope.launch {
            repository.disconnect()
        }
    }
    
    /**
     * Переключить состояние подключения
     */
    fun toggleConnection() {
        when (connectionState.value) {
            VpnConnectionState.DISCONNECTED, VpnConnectionState.ERROR -> connect()
            VpnConnectionState.CONNECTED -> disconnect()
            else -> { /* Игнорируем во время переходных состояний */ }
        }
    }
    
    /**
     * Обновить настройки
     */
    fun updateSettings(newSettings: VpnSettings) {
        repository.updateSettings(newSettings)
    }
    
    /**
     * Обновить профиль пользователя
     */
    fun updateUserProfile(newProfile: UserProfile) {
        repository.updateUserProfile(newProfile)
    }
    
    /**
     * Получить отфильтрованные серверы
     */
    fun getFilteredServers(query: String = "", showOnlyFree: Boolean = false): List<VpnServer> {
        return repository.getServers().filter { server ->
            val matchesQuery = query.isEmpty() || 
                server.name.contains(query, ignoreCase = true) ||
                server.country.contains(query, ignoreCase = true) ||
                server.city.contains(query, ignoreCase = true)
            
            val matchesSubscription = !showOnlyFree || !server.isPremium
            
            matchesQuery && matchesSubscription
        }
    }
    
    /**
     * Получить серверы по регионам
     */
    fun getServersByRegion(): Map<String, List<VpnServer>> {
        return repository.getServers().groupBy { server ->
            when {
                server.country in listOf("США", "Канада") -> "Северная Америка"
                server.country in listOf("Великобритания", "Германия", "Нидерланды", "Франция") -> "Европа"
                server.country in listOf("Япония", "Сингапур", "Южная Корея", "Индия") -> "Азия"
                server.country in listOf("Австралия") -> "Океания"
                server.country in listOf("Бразилия") -> "Южная Америка"
                else -> "Другие"
            }
        }
    }
} 