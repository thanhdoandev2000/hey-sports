package com.example.heysports.domain.models

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.GroupAdd
import androidx.compose.material.icons.outlined.Handshake
import androidx.compose.material.icons.outlined.LiveTv
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material.icons.outlined.PersonSearch
import androidx.compose.material.icons.outlined.SportsSoccer
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.heysports.R
import com.example.heysports.ui.theme.PrimaryGreen

data class PostModel(
    @param:StringRes val title: Int,
    val icon: ImageVector,
    @param:StringRes val content: Int,
    val color: Color,
    val route: Any
) {
    companion object {
        val items = listOf(
            PostModel(
                title = R.string.postCreateMatch,
                icon = Icons.Outlined.SportsSoccer,
                content = R.string.postCreateMatchDesc,
                color = PrimaryGreen,
                route = "CreateMatchRoute"
            ),
            PostModel(
                title = R.string.postFindOpponent,
                icon = Icons.Outlined.Handshake,
                content = R.string.postFindOpponentDesc,
                color = Color(0xFFE65100),
                route = "FindOpponentRoute"
            ),
            PostModel(
                title = R.string.postNeedPlayer,
                icon = Icons.Outlined.PersonAdd,
                content = R.string.postNeedPlayerDesc,
                color = Color(0xFF1565C0),
                route = "NeedPlayerRoute"
            ),
            PostModel(
                title = R.string.postFindSlot,
                icon = Icons.Outlined.PersonSearch,
                content = R.string.postFindSlotDesc,
                color = Color(0xFF00695C),
                route = "FindSlotRoute"
            ),
            PostModel(
                title = R.string.postJoinTeam,
                icon = Icons.Outlined.GroupAdd,
                content = R.string.postJoinTeamDesc,
                color = Color(0xFF6A1B9A),
                route = "JoinTeamRoute"
            ),
            PostModel(
                title = R.string.postRecruitMember,
                icon = Icons.Outlined.PersonAdd,
                content = R.string.postRecruitMemberDesc,
                color = Color(0xFFB71C1C),
                route = "RecruitMemberRoute"
            ),
            PostModel(
                title = R.string.postWriteArticle,
                icon = Icons.Outlined.EditNote,
                content = R.string.postWriteArticleDesc,
                color = Color(0xFF00838F),
                route = "WriteArticleRoute"
            ),
            PostModel(
                title = R.string.postGoLive,
                icon = Icons.Outlined.LiveTv,
                content = R.string.postGoLiveDesc,
                color = Color(0xFFC62828),
                route = "GoLiveRoute"
            ),
        )
    }
}