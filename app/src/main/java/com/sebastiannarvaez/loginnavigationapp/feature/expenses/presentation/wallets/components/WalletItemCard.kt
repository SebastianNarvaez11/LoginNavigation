package com.sebastiannarvaez.loginnavigationapp.feature.expenses.presentation.wallets.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sebastiannarvaez.loginnavigationapp.core.presentation.utils.toCurrency
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.models.WalletModel

@Composable
fun WalletItemCard(
    modifier: Modifier = Modifier,
    wallet: WalletModel,
    isSelected: Boolean,
    onPressWallet: (wallet: WalletModel) -> Unit,
    onLongPressWallet: () -> Unit,
    isDeleteModeActive: Boolean = false,
    onDelete: (wallet: WalletModel) -> Unit,
    content: @Composable () -> Unit,
) {
    Box {
        Column(
            modifier = modifier
                .shadow(
                    elevation = 10.dp, clip = true, shape = RoundedCornerShape(10.dp)
                )
                .background(MaterialTheme.colorScheme.surface)
                .combinedClickable(
                    enabled = !isSelected,
                    onClick = { onPressWallet(wallet) },
                    onLongClick = { onLongPressWallet() }
                )
                .then(
                    if (!isSelected) Modifier.height(100.dp) else Modifier
                )
                .padding(16.dp)
                .animateContentSize()) {

            AnimatedVisibility(!isSelected) {
                Column {
                    Text(
                        text = wallet.name,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Saldo",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Light,
                        lineHeight = 10.sp
                    )
                    Text(
                        text = wallet.balance.toCurrency(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        maxLines = 1
                    )
                }
            }

            content()
        }


        AnimatedVisibility(
            visible = isDeleteModeActive,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(x = 0.dp, y = (15).dp),
        ) {
            IconButton(
                onClick = { onDelete(wallet) },
                colors = IconButtonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError,
                    disabledContainerColor = MaterialTheme.colorScheme.error,
                    disabledContentColor = MaterialTheme.colorScheme.onError,
                ),
                modifier = Modifier
                    .size(25.dp)

            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "remove wallet",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}