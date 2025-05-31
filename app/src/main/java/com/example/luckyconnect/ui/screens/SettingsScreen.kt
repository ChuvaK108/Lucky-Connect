package com.example.luckyconnect.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.luckyconnect.data.*
import com.example.luckyconnect.ui.components.NeonCard
import com.example.luckyconnect.ui.theme.FuturisticColors
import com.example.luckyconnect.viewmodel.VpnViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    viewModel: VpnViewModel = viewModel()
) {
    val settings by viewModel.settings.collectAsState()
    var showProtocolMenu by remember { mutableStateOf(false) }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        FuturisticColors.DeepSpace,
                        FuturisticColors.DarkMatter
                    )
                )
            )
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(16.dp)) }
            
            // Заголовок и кнопка назад
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                color = FuturisticColors.CardSurface,
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Назад",
                            tint = FuturisticColors.CyberBlue
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Text(
                        text = "Настройки",
                        color = FuturisticColors.TextPrimary,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            // Секция "Подключение"
            item {
                Text(
                    text = "ПОДКЛЮЧЕНИЕ",
                    color = FuturisticColors.CyberBlue,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
            }
            
            // Автоподключение
            item {
                SettingsToggleCard(
                    title = "Автоподключение",
                    description = "Автоматически подключаться при запуске",
                    icon = Icons.Default.PlayArrow,
                    checked = settings.autoConnect,
                    onCheckedChange = { 
                        viewModel.updateSettings(settings.copy(autoConnect = it))
                    }
                )
            }
            
            // Kill Switch
            item {
                SettingsToggleCard(
                    title = "Kill Switch",
                    description = "Блокировать интернет при отключении VPN",
                    icon = Icons.Default.Block,
                    checked = settings.killSwitch,
                    onCheckedChange = { 
                        viewModel.updateSettings(settings.copy(killSwitch = it))
                    }
                )
            }
            
            // Выбор протокола
            item {
                NeonCard(
                    accentColor = FuturisticColors.ElectricPurple,
                    onClick = { showProtocolMenu = true }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Icon(
                                imageVector = settings.selectedProtocol.icon,
                                contentDescription = null,
                                tint = FuturisticColors.ElectricPurple,
                                modifier = Modifier.size(24.dp)
                            )
                            
                            Column {
                                Text(
                                    text = "Протокол VPN",
                                    color = FuturisticColors.TextPrimary,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = settings.selectedProtocol.displayName,
                                    color = FuturisticColors.TextSecondary,
                                    fontSize = 14.sp
                                )
                            }
                        }
                        
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Выбрать протокол",
                            tint = FuturisticColors.TextSecondary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
            
            // Секция "Безопасность"
            item {
                Text(
                    text = "БЕЗОПАСНОСТЬ",
                    color = FuturisticColors.CyberBlue,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
            }
            
            // DNS защита
            item {
                SettingsToggleCard(
                    title = "DNS защита",
                    description = "Защита от DNS утечек и вредоносных сайтов",
                    icon = Icons.Default.Security,
                    checked = settings.dnsProtection,
                    onCheckedChange = { 
                        viewModel.updateSettings(settings.copy(dnsProtection = it))
                    }
                )
            }
            
            // Блокировщик рекламы
            item {
                SettingsToggleCard(
                    title = "Блокировщик рекламы",
                    description = "Блокировка рекламы и трекеров",
                    icon = Icons.Default.Shield,
                    checked = settings.adBlocker,
                    onCheckedChange = { 
                        viewModel.updateSettings(settings.copy(adBlocker = it))
                    }
                )
            }
            
            // Секция "О приложении"
            item {
                Text(
                    text = "О ПРИЛОЖЕНИИ",
                    color = FuturisticColors.CyberBlue,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
            }
            
            // Информация о версии
            item {
                NeonCard(
                    accentColor = FuturisticColors.QuantumGold
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = FuturisticColors.QuantumGold,
                            modifier = Modifier.size(24.dp)
                        )
                        
                        Column {
                            Text(
                                text = "Lucky Connect",
                                color = FuturisticColors.TextPrimary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "Версия 1.0.0",
                                color = FuturisticColors.TextSecondary,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
            
            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
        
        // Меню выбора протокола
        if (showProtocolMenu) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(FuturisticColors.VoidBlack.copy(alpha = 0.7f))
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { showProtocolMenu = false },
                contentAlignment = Alignment.Center
            ) {
                NeonCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    accentColor = FuturisticColors.ElectricPurple
                ) {
                    Column {
                        Text(
                            text = "Выберите протокол",
                            color = FuturisticColors.TextPrimary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        
                        VpnProtocol.values().forEach { protocol ->
                            ProtocolOption(
                                protocol = protocol,
                                isSelected = protocol == settings.selectedProtocol,
                                onSelect = {
                                    viewModel.updateSettings(settings.copy(selectedProtocol = protocol))
                                    showProtocolMenu = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsToggleCard(
    title: String,
    description: String,
    icon: ImageVector,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    NeonCard(
        accentColor = if (checked) FuturisticColors.SuccessGreen else FuturisticColors.CyberBlue
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (checked) FuturisticColors.SuccessGreen else FuturisticColors.CyberBlue,
                    modifier = Modifier.size(24.dp)
                )
                
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = title,
                        color = FuturisticColors.TextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1
                    )
                    Text(
                        text = description,
                        color = FuturisticColors.TextSecondary,
                        fontSize = 14.sp,
                        maxLines = 2
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = FuturisticColors.SuccessGreen,
                    checkedTrackColor = FuturisticColors.SuccessGreen.copy(alpha = 0.3f),
                    uncheckedThumbColor = FuturisticColors.TextTertiary,
                    uncheckedTrackColor = FuturisticColors.DarkMatter
                )
            )
        }
    }
}

@Composable
private fun ProtocolOption(
    protocol: VpnProtocol,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    NeonCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        accentColor = if (isSelected) FuturisticColors.SuccessGreen else FuturisticColors.TextTertiary,
        onClick = onSelect
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = protocol.icon,
                    contentDescription = null,
                    tint = if (isSelected) FuturisticColors.SuccessGreen else FuturisticColors.CyberBlue,
                    modifier = Modifier.size(20.dp)
                )
                
                Text(
                    text = protocol.displayName,
                    color = FuturisticColors.TextPrimary,
                    fontSize = 16.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            }
            
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Выбран",
                    tint = FuturisticColors.SuccessGreen,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
} 