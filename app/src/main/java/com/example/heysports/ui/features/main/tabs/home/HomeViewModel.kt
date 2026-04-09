package com.example.heysports.ui.features.main.tabs.home

import com.example.heysports.data.model.UiEffect
import com.example.heysports.data.model.UiState
import com.example.heysports.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

data class Team(
    val id: String,
    val name: String,
    val avatar: String? = null,
)

data class Person(
    val id: String,
    val firstName: String,
    val lastName: String,
    val avatar: String? = null,
)

data class NextMatches(
    val id: String,
    val dateTime: String,
    val location: String,
    val homeTeam: Team,
    val awayTeam: Team
)

data class Matchmaking(
    val id: String,
    val name: String,
    val avatar: String? = null,
    val dateTime: String,
    val location: String,
    val description: String
)

data class MatchLive(
    val id: String,
    val duration: Int,
    val location: String,
    val matchScore: String,
    val homeTeam: Team,
    val awayTeam: Team
)

data class NewsFeed(
    val id: String,
    val user: Person,
    val time: String,
    val status: String,
    val content: String,
    val image: String? = null,
    val like: Int,
    val comment: Int
)

data class HomeUiState(
    var isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val nextMatches: List<NextMatches> = emptyList(),
    val matchmakingSections: List<Matchmaking> = emptyList(),
    val matchesLive: List<MatchLive> = emptyList(),
    val newsFeeds: List<NewsFeed> = emptyList(),
) : UiState

interface HomeUiEffect : UiEffect

val person1 = Person(id = "p01", firstName = "Thành", lastName = "Đoàn", avatar = null)
val person2 = Person(id = "p02", firstName = "Dũng", lastName = "Nguyễn", avatar = null)
val person3 = Person(id = "p03", firstName = "Hùng", lastName = "Vũ", avatar = null)

@HiltViewModel
class HomeViewModel @Inject constructor() :
    BaseViewModel<HomeUiState, HomeUiEffect>(initialState = HomeUiState()) {
    private fun initData() {
        updateState {
            copy(
                nextMatches = listOf(
                    NextMatches(
                        id = "1",
                        dateTime = "19:00 - Tối Nay",
                        location = "Sân bóng Tuyên Sơn(Sân số 5)",
                        homeTeam = Team(id = "1", name = "FC AE Cây Khế"),
                        awayTeam = Team(id = "2", name = "FC Lính Mới")
                    )
                ),
                matchmakingSections = listOf(
                    Matchmaking(
                        id = "1",
                        name = "FC Hải Châu(Cách 2km)",
                        avatar = null,
                        dateTime = "19:00 - Tối Nay",
                        location = "Tuyên Sơn",
                        description = "Cần tìm gấp 1 đội trung bình yếu, đã có sẵn sân cứng, đội nào nhận kèo giao lưu"
                    ),
                    Matchmaking(
                        id = "3",
                        name = "Tiến Thành",
                        avatar = null,
                        dateTime = "19:00 - 20/04",
                        location = "Lê Quý Đôn",
                        description = "Gấp! Team đá nội bộ lấy mồ hôi cần 1 cầu hậu vệ phải, vui vẽ giao lưu là được, tiền sân ăn chia đủ."
                    ),
                    Matchmaking(
                        id = "2",
                        name = "Hải Đăng",
                        avatar = null,
                        dateTime = "15:00 - Ngày Mai",
                        location = "Chuyên Việt",
                        description = "Gấp! Team cần vài cầu đá giải, yêu cầu cứng và tích cực"
                    )
                ),
                matchesLive = listOf(
                    MatchLive(
                        id = "1",
                        duration = 30,
                        location = "Sân Tuyên Sơn, sân 6A",
                        matchScore = "2-1",
                        homeTeam = Team(id = "1", name = "FC AE Cây Khế"),
                        awayTeam = Team(id = "2", name = "FC Lính Mới")
                    ),
                    MatchLive(
                        id = "1",
                        duration = 15,
                        location = "Sân Lê Quý Đôn, A1",
                        matchScore = "0-0",
                        homeTeam = Team(id = "3", name = "FC Hải Châu"),
                        awayTeam = Team(id = "4", name = "FC Cảng Tiên Sa")
                    ),
                    MatchLive(
                        id = "1",
                        duration = 30,
                        location = "Chuyên Việt, sân 2",
                        matchScore = "0-1",
                        homeTeam = Team(id = "5", name = "FC Chợ Đời"),
                        awayTeam = Team(id = "6", name = "FC Bụi")
                    )
                ),
                newsFeeds = listOf(
                    NewsFeed(
                        id = "nf02",
                        user = person2,
                        time = "Hôm qua",
                        status = "Hot Match",
                        content = "Hết mình với đam mê!",
                        image = "https://cdn2.tuoitre.vn/thumb_w/480/471584752817336320/2024/10/3/cau-thu-binh-duong-2-1727925488430773696569.jpg",
                        like = 10,
                        comment = 5
                    ),
                    NewsFeed(
                        id = "nf01",
                        user = person1,
                        time = "5 phút trước",
                        status = "Đang cảm thấy chờ đợi",
                        content = "Trận đấu đỉnh cao cuối tuần!.",
                        image = null,
                        like = 20,
                        comment = 15
                    ),
                    NewsFeed(
                        id = "nf02",
                        user = person3,
                        time = "10 giờ trước",
                        status = "Đẹp đẹp",
                        content = "Check in sân bóng Ngủ Hành Sơn!",
                        image = "https://img.thegioithethao.vn/thumbs/san-bong-da/da-nang/ngu-hanh-son/san-bong-da-ngu-hanh-son/san-bong-Da-ngu-hanh-son-3_thumb_720.webp",
                        like = 30,
                        comment = 1
                    )
                )
            )
        }
    }

    init {
        initData()
    }
}