package com.hongmyeoun.goldcalc.view.home.content

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavHostController
import com.hongmyeoun.goldcalc.model.roomDB.character.Character
import com.hongmyeoun.goldcalc.model.roomDB.character.CharacterRepository
import com.hongmyeoun.goldcalc.ui.theme.LightGrayBG
import com.hongmyeoun.goldcalc.view.common.noRippleClickable
import com.hongmyeoun.goldcalc.view.home.content.contentItem.ContentItem
import com.hongmyeoun.goldcalc.viewModel.home.HomeContentVM
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPageContent(
    paddingValues: PaddingValues,
    characterList: List<Character>,
    characterRepository: CharacterRepository,
    navController: NavHostController,
    isLoading: Boolean,
    nowRotate: (Boolean) -> Unit
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { characterList.size })

    Box {
        HorizontalPager(
            modifier = Modifier
                .fillMaxSize()
                .background(LightGrayBG)
                .padding(paddingValues),
            state = pagerState,
            pageSpacing = 8.dp
        ) { page ->
            val characterName = characterList[page].name
            val characterCardVM = remember { HomeContentVM(characterRepository, characterName) }

            Box(
                modifier = Modifier
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - page) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue
                        // 옆 투명도
                        alpha = lerp(
                            start = 0.3f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )

                        // 옆 블록 위아래 배치
                        lerp(
                            start = 1f,
                            stop = 0.8f,
                            fraction = pageOffset.absoluteValue.coerceIn(0f, 1f),
                        ).let {
                            scaleX = it
                            scaleY = it
                        }

                        // 옆 블록 위아래 자연스럽게 애니메이션
                        lerp(
                            start = 1f,
                            stop = 0.8f,
                            fraction = pageOffset.absoluteValue.coerceIn(0f, 1f),
                        ).let {
                            scaleX = it
                            scaleY = it
                            val sign = if (pageOffset > 0) 1 else -1
                            translationX = sign * size.width * (1 - it) / 2
                        }
                    }
            ) {
                ContentItem(
                    navController = navController,
                    cardViewModel = characterCardVM,
                    isLoading = isLoading,
                    isListView = false,
                    nowRotate = { nowRotate(it) }
                )
            }
        }

        // 현재 페이지 표기 및 전체 페이지 표기
        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(paddingValues)
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color = if (pagerState.currentPage == iteration) Color.White else Color.LightGray.copy(alpha = 0.5f)
                val size = if (pagerState.currentPage == iteration) 12.dp else 10.dp
                Box(
                    modifier = Modifier
                        .noRippleClickable { scope.launch { pagerState.scrollToPage(iteration) } }
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(size)
                )
            }
        }
    }
}