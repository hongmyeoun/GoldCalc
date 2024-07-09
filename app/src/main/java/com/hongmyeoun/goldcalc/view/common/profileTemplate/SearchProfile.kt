
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hongmyeoun.goldcalc.model.constants.viewConst.ButtonText
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterDetail
import com.hongmyeoun.goldcalc.model.lostArkApi.CharacterResourceMapper
import com.hongmyeoun.goldcalc.view.common.profileTemplate.Extra
import com.hongmyeoun.goldcalc.view.common.profileTemplate.ItemLevel
import com.hongmyeoun.goldcalc.view.common.profileTemplate.Levels
import com.hongmyeoun.goldcalc.view.common.profileTemplate.ServerClassName
import com.hongmyeoun.goldcalc.view.common.profileTemplate.TitleCharName

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
fun SearchProfile(
    profile: CharacterDetail,
    onGetClick: () -> Unit,
    enabled: Boolean
) {
    val height = if (enabled) 332.dp else 262.dp

    val characterImage = if (profile.characterImage.isNullOrEmpty()) {
        CharacterResourceMapper.getClassDefaultImg(profile.characterClassName)
    } else {
        profile.characterImage
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
    ) {

        GlideImage(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .scale(1.5f)
                .offset(y = 30.dp),
            model = characterImage,
            contentScale = ContentScale.FillHeight,
            contentDescription = "캐릭터 이미지"
        )

        DefaultsProfilesWithGetButton(
            profile = profile,
            enabled = enabled,
            onGetClick = onGetClick
        )
    }
}

@Composable
private fun DefaultsProfilesWithGetButton(
    profile: CharacterDetail,
    enabled: Boolean,
    onGetClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        ServerClassName(
            serverName = profile.serverName,
            className = profile.characterClassName
        )
        TitleCharName(
            title = profile.title,
            name = profile.characterName
        )
        ItemLevel(level = profile.itemMaxLevel)
        Extra(profile = profile)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Levels(profile)

            if (enabled) {
                Spacer(modifier = Modifier.height(8.dp))
                ElevatedButton(
                    onClick = onGetClick,
                ) {
                    Text(text = ButtonText.GET_PROFILE_BUTTON_TEXT)
                }
            }
        }
    }
}