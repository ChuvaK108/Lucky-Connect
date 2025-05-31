package com.example.luckyconnect.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.luckyconnect.data.VpnConnectionState
import com.example.luckyconnect.ui.theme.FuturisticColors
import kotlin.math.*

/**
 * Футуристичная кнопка подключения с анимированным неоновым эффектом
 */
@Composable
fun FuturisticConnectionButton(
    connectionState: VpnConnectionState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "connectionButton")
    
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowAlpha"
    )
    
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )
    
    val (buttonColor, glowColor, icon, text) = when (connectionState) {
        VpnConnectionState.CONNECTED -> Quadruple(
            FuturisticColors.SuccessGreen,
            FuturisticColors.NeonGreen,
            Icons.Default.Shield,
            "ЗАЩИЩЕНО"
        )
        VpnConnectionState.CONNECTING -> Quadruple(
            FuturisticColors.WarningOrange,
            FuturisticColors.QuantumGold,
            Icons.Default.Sync,
            "ПОДКЛЮЧЕНИЕ..."
        )
        VpnConnectionState.DISCONNECTING -> Quadruple(
            FuturisticColors.WarningOrange,
            FuturisticColors.QuantumGold,
            Icons.Default.Sync,
            "ОТКЛЮЧЕНИЕ..."
        )
        VpnConnectionState.ERROR -> Quadruple(
            FuturisticColors.ErrorRed,
            FuturisticColors.LaserRed,
            Icons.Default.Error,
            "ОШИБКА"
        )
        else -> Quadruple(
            FuturisticColors.CyberBlue,
            FuturisticColors.InfoBlue,
            Icons.Default.PowerSettingsNew,
            "ПОДКЛЮЧИТЬСЯ"
        )
    }
    
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(200.dp)
            .clickable { onClick() }
    ) {
        // Внешнее свечение
        Box(
            modifier = Modifier
                .size(220.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            glowColor.copy(alpha = glowAlpha * 0.3f),
                            Color.Transparent
                        ),
                        radius = 300f
                    ),
                    shape = CircleShape
                )
        )
        
        // Вращающееся кольцо
        if (connectionState == VpnConnectionState.CONNECTING || 
            connectionState == VpnConnectionState.DISCONNECTING) {
            Box(
                modifier = Modifier
                    .size(210.dp)
                    .rotate(rotationAngle)
                    .border(
                        width = 3.dp,
                        brush = Brush.sweepGradient(
                            colors = listOf(
                                Color.Transparent,
                                glowColor.copy(alpha = 0.8f),
                                Color.Transparent
                            )
                        ),
                        shape = CircleShape
                    )
            )
        }
        
        // Основная кнопка
        Card(
            modifier = Modifier
                .size(180.dp)
                .shadow(
                    elevation = 20.dp,
                    shape = CircleShape,
                    ambientColor = glowColor,
                    spotColor = glowColor
                ),
            shape = CircleShape,
            colors = CardDefaults.cardColors(
                containerColor = FuturisticColors.CardSurface
            )
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                buttonColor.copy(alpha = 0.2f),
                                FuturisticColors.CardSurface
                            ),
                            radius = 200f
                        )
                    )
                    .border(
                        width = 2.dp,
                        color = buttonColor.copy(alpha = glowAlpha),
                        shape = CircleShape
                    )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = text,
                        modifier = Modifier
                            .size(48.dp)
                            .let { iconModifier ->
                                if (connectionState == VpnConnectionState.CONNECTING ||
                                    connectionState == VpnConnectionState.DISCONNECTING) {
                                    iconModifier.rotate(rotationAngle)
                                } else iconModifier
                            },
                        tint = buttonColor
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = text,
                        color = buttonColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

/**
 * Неоновая карточка с эффектом glassmorphism
 */
@Composable
fun NeonCard(
    modifier: Modifier = Modifier,
    accentColor: Color = FuturisticColors.CyberBlue,
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    val cardModifier = if (onClick != null) {
        modifier.clickable { onClick() }
    } else modifier
    
    Card(
        modifier = cardModifier
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = accentColor.copy(alpha = 0.3f),
                spotColor = accentColor.copy(alpha = 0.3f)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = FuturisticColors.GlassMorphism
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            FuturisticColors.CardSurface.copy(alpha = 0.8f),
                            FuturisticColors.ElevatedSurface.copy(alpha = 0.6f)
                        )
                    )
                )
                .border(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            accentColor.copy(alpha = 0.5f),
                            Color.Transparent,
                            accentColor.copy(alpha = 0.3f)
                        )
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp),
            content = content
        )
    }
}

/**
 * Анимированный прогресс бар с неоновым эффектом
 */
@Composable
fun NeonProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = FuturisticColors.CyberBlue,
    backgroundColor: Color = FuturisticColors.DarkMatter
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(1000, easing = FastOutSlowInEasing),
        label = "progress"
    )
    
    Box(
        modifier = modifier
            .height(8.dp)
            .fillMaxWidth()
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(animatedProgress)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            color.copy(alpha = 0.6f),
                            color,
                            color.copy(alpha = 0.8f)
                        )
                    ),
                    shape = RoundedCornerShape(4.dp)
                )
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(4.dp),
                    ambientColor = color,
                    spotColor = color
                )
        )
    }
}

/**
 * Статистический индикатор с анимацией
 */
@Composable
fun StatIndicator(
    label: String,
    value: String,
    icon: ImageVector,
    color: Color = FuturisticColors.CyberBlue,
    modifier: Modifier = Modifier
) {
    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )
    
    Row(
        modifier = modifier.scale(scale),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            color.copy(alpha = 0.3f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
                .border(
                    width = 1.dp,
                    color = color.copy(alpha = 0.6f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = color,
                modifier = Modifier.size(20.dp)
            )
        }
        
        Column {
            Text(
                text = label,
                color = FuturisticColors.TextSecondary,
                fontSize = 12.sp
            )
            Text(
                text = value,
                color = FuturisticColors.TextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/**
 * Пульсирующий эффект для важных элементов
 */
@Composable
fun PulsingEffect(
    color: Color = FuturisticColors.CyberBlue,
    size: Float = 100f,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )
    
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )
    
    Box(
        modifier = modifier
            .size(size.dp)
            .scale(scale)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        color.copy(alpha = alpha),
                        Color.Transparent
                    )
                ),
                shape = CircleShape
            )
    )
}

// Вспомогательный класс для четверки значений
data class Quadruple<A, B, C, D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D
) 