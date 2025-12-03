package com.sebastiannarvaez.loginnavigationapp.feature.expenses.presentation.wallets.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sebastiannarvaez.loginnavigationapp.feature.expenses.domain.models.WalletModel

@Composable
fun WalletItemCard(
    modifier: Modifier = Modifier,
    wallet: WalletModel,
    isSelected: Boolean,
    onPressWallet: (wallet: WalletModel) -> Unit,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .shadow(elevation = 10.dp, clip = true, shape = RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable(enabled = !isSelected) {
                onPressWallet(wallet)
            }
            .then(
                if (!isSelected) Modifier.height(100.dp) else Modifier
            )
            .padding(16.dp)
            .animateContentSize()) {

        AnimatedVisibility(!isSelected) {
            Column {
                Text(text = wallet.name, fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = TextOverflow.Ellipsis)

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Saldo",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Light,
                    lineHeight = 10.sp
                )
                Text(text = "${wallet.balance}", fontWeight = FontWeight.Bold, fontSize = 18.sp, maxLines = 1)
            }
        }

        content()
    }
}