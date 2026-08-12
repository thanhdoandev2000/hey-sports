package com.example.heysports.ui.features.main.tabs.home

import com.example.heysports.cores.utils.DateTimeUtils
import com.example.heysports.data.models.dto.LiveMatchDto
import com.example.heysports.data.models.dto.MatchRequestDto
import com.example.heysports.data.models.dto.MatchUpcomingDto
import com.example.heysports.domain.models.MatchWeather
import com.example.heysports.domain.models.UserInfo
import java.util.Calendar

/**
 * Feature-local fallback content for Home.
 *
 * Real server values always win. These samples are used only while a request fails or when a
 * successful response has no content, so Home remains representative during backend setup.
 */
internal object HomeFakeData {
    private const val RESOURCE_URI_PREFIX =
        "android.resource://com.example.heysports/drawable/"

    val user = UserInfo(
        id = "home-fallback-user",
        name = "Thanh Đoàn",
        email = "thanhdoan@heysports.vn",
        phoneNumber = "0905 123 456",
        matchesPlayed = 12,
        rating = 4.8,
        skillLevel = "Khá",
        createdAt = "2025-01-01T00:00:00+07:00"
    )

    fun initialState(): HomeUiState {
        val matches = upcomingMatches()
        return HomeUiState(
            personInfo = user,
            upComingMatches = matches,
            weatherByMatchId = weatherFor(matches),
            liveMatches = liveMatches(),
            newsFeeds = newsFeeds(),
            matchRequests = matchRequests()
        )
    }

    fun userOrFallback(serverUser: UserInfo?): UserInfo = serverUser ?: user

    fun upcomingOrFallback(serverMatches: List<MatchUpcomingDto>): List<MatchUpcomingDto> =
        serverMatches.ifEmpty(::upcomingMatches)

    fun requestsOrFallback(serverRequests: List<MatchRequestDto>): List<MatchRequestDto> =
        serverRequests.ifEmpty(::matchRequests)

    fun liveOrFallback(serverMatches: List<LiveMatchDto>): List<LiveMatchDto> =
        serverMatches.ifEmpty(::liveMatches)

    fun upcomingMatches(): List<MatchUpcomingDto> = listOf(
        MatchUpcomingDto(
            id = -1L,
            hostTeamId = -10L,
            guestTeamId = -11L,
            subPitchId = -101L,
            matchTime = relativeServerTime(days = 1, hourOfDay = 18, minute = 30),
            duration = 90,
            description = "Trận giao hữu cuối tuần",
            hostTeamName = "Hey Sports FC",
            hostTeamAvatar = null,
            guestTeamName = "Sơn Trà United",
            guestTeamAvatar = null,
            subPitchName = "Sân 7A",
            price = 240_000.0,
            type = "Sân 7",
            pitchName = "Sân bóng Tuyên Sơn",
            pitchAddress = "Sân 7A · Tuyên Sơn, Hải Châu, Đà Nẵng",
            pitchLat = 16.0355,
            pitchLng = 108.2235
        )
    )

    fun matchRequests(): List<MatchRequestDto> = listOf(
        MatchRequestDto(
            id = -101L,
            createdAt = relativeServerTime(hoursFromNow = -1),
            postedByType = "TEAM",
            type = "FIND_OPPONENT",
            matchTime = relativeServerTime(days = 1, hourOfDay = 20),
            description = "Cần tìm đội trình độ trung bình - khá, giao lưu vui vẻ và fair-play.",
            status = "OPEN",
            skillLevel = "Trung bình - Khá",
            matchFormat = "7 vs 7",
            contactPhone = "0905 123 456",
            feeType = "FIFTY_FIFTY",
            ageGroup = "18-35",
            teamStyle = "CASUAL",
            teamStatus = "ACTIVE",
            rules = listOf("Không xoạc bóng", "Đúng giờ"),
            teamId = -10L,
            teamName = "Hey Sports FC",
            teamArea = "Hải Châu, Đà Nẵng",
            pitchId = -1L,
            subPitchId = -101L,
            pitchName = "Sân Tuyên Sơn",
            pitchAddress = "Hải Châu, Đà Nẵng",
            pitchLat = "16.0355",
            pitchLong = "108.2235"
        ),
        MatchRequestDto(
            id = -102L,
            createdAt = relativeServerTime(hoursFromNow = -2),
            postedByType = "USER",
            type = "FIND_PLAYER",
            matchTime = relativeServerTime(days = 2, hourOfDay = 19),
            description = "Thiếu một tiền vệ và một hậu vệ cho trận tối thứ Bảy.",
            status = "OPEN",
            skillLevel = "Khá",
            matchFormat = "5 vs 5",
            contactPhone = "0935 678 901",
            feeType = "PITCH_AND_WATER",
            userId = "home-fallback-player",
            userName = "Nguyễn Văn Hùng",
            teamArea = "Sơn Trà, Đà Nẵng",
            pitchId = -2L,
            subPitchId = -201L,
            pitchName = "Sân bóng Chuyên Việt"
        ),
        MatchRequestDto(
            id = -103L,
            createdAt = relativeServerTime(hoursFromNow = -3),
            postedByType = "USER",
            type = "REQUEST_SLOT",
            matchTime = relativeServerTime(days = 3, hourOfDay = 18, minute = 30),
            description = "Mình đá tiền vệ, muốn xin vào đội giao lưu đều đặn khu vực Hải Châu.",
            status = "OPEN",
            skillLevel = "Trung bình - Khá",
            matchFormat = "7 vs 7",
            contactPhone = "0905 246 810",
            ageGroup = "22-32",
            teamStyle = "CASUAL",
            userId = "home-fallback-slot-player",
            userName = "Lê Hoàng Nam",
            userAvatar = null,
            teamArea = "Hải Châu, Đà Nẵng"
        ),
        MatchRequestDto(
            id = -104L,
            createdAt = relativeServerTime(hoursFromNow = -4),
            postedByType = "TEAM",
            type = "RECRUITING_SLOT",
            matchTime = relativeServerTime(days = 4, hourOfDay = 20),
            description = "Đội đang tuyển thêm thủ môn và hậu vệ, sinh hoạt cố định tối thứ Tư.",
            status = "OPEN",
            skillLevel = "Khá",
            matchFormat = "7 vs 7",
            contactPhone = "0935 135 790",
            feeType = "PITCH_AND_WATER",
            ageGroup = "20-35",
            teamStyle = "COORDINATED",
            teamStatus = "RECRUITING",
            teamId = -15L,
            teamName = "Sơn Trà United",
            teamAvatar = null,
            teamArea = "Sơn Trà, Đà Nẵng",
            pitchId = -4L,
            pitchName = "Sân bóng Mân Thái"
        )
    )

    fun liveMatches(): List<LiveMatchDto> = listOf(
        LiveMatchDto(
            id = -201L,
            updateAt = relativeServerTime(),
            status = "live",
            currentMinutes = 37,
            half = 1,
            duration = 90,
            hostScore = 2,
            guestScore = 1,
            startTime = relativeServerTime(hoursFromNow = -1),
            hostTeamId = -10L,
            hostTeamName = "Hey Sports FC",
            guestTeamId = -12L,
            guestTeamName = "Hòa Xuân FC",
            subPitchId = -301L,
            subPitchName = "Sân 5B",
            subPitchType = "Sân 5",
            pitchID = -3L,
            pitchName = "Sân bóng Chuyên Việt",
            pitchAddress = "Cẩm Lệ, Đà Nẵng"
        ),
        LiveMatchDto(
            id = -202L,
            updateAt = relativeServerTime(),
            status = "halftime",
            currentMinutes = 45,
            half = 1,
            duration = 90,
            hostScore = 1,
            guestScore = 1,
            startTime = relativeServerTime(hoursFromNow = -1),
            hostTeamId = -13L,
            hostTeamName = "Sơn Trà United",
            guestTeamId = -14L,
            guestTeamName = "Thanh Khê FC",
            subPitchId = -302L,
            subPitchName = "Sân 7A",
            subPitchType = "Sân 7",
            pitchID = -1L,
            pitchName = "Sân bóng Tuyên Sơn",
            pitchAddress = "Hải Châu, Đà Nẵng"
        )
    )

    fun newsFeeds(): List<NewsFeed> = listOf(
        NewsFeed(
            id = "home-fallback-news-1",
            user = UserInfo(
                id = "home-fallback-news-user-1",
                name = "Nguyễn Văn Hùng",
                email = "hung@heysports.vn"
            ),
            time = "2 giờ trước",
            status = "vừa tham gia đội",
            content = "Buổi giao lưu tối qua quá vui. Cảm ơn anh em Hey Sports FC!",
            image = "${RESOURCE_URI_PREFIX}pitch_tuyen_son",
            like = 24,
            comment = 8
        ),
        NewsFeed(
            id = "home-fallback-news-2",
            user = UserInfo(
                id = "home-fallback-news-user-2",
                name = "Trần Minh Khoa",
                email = "khoa@heysports.vn"
            ),
            time = "5 giờ trước",
            status = "đã hoàn thành trận đấu",
            content = "Một trận cầu cân bằng và fair-play. Hẹn tái đấu tuần sau!",
            image = "${RESOURCE_URI_PREFIX}pitch_trung_vuong",
            like = 18,
            comment = 5
        )
    )

    fun weatherFor(matches: List<MatchUpcomingDto>): Map<Long, MatchWeather> =
        matches.mapIndexed { index, match ->
            match.id to MatchWeather(
                temperatureCelsius = 28 + index,
                weatherCode = if (index % 2 == 0) 1 else 2,
                precipitationProbability = 15 + index * 5
            )
        }.toMap()

    private fun relativeServerTime(
        days: Int = 0,
        hoursFromNow: Int = 0,
        hourOfDay: Int? = null,
        minute: Int = 0
    ): String {
        val calendar = DateTimeUtils.getCurrentDate().apply {
            add(Calendar.DAY_OF_YEAR, days)
            add(Calendar.HOUR_OF_DAY, hoursFromNow)
            if (hourOfDay != null) {
                set(Calendar.HOUR_OF_DAY, hourOfDay)
                set(Calendar.MINUTE, minute)
            }
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return with(DateTimeUtils) { calendar.toServerDateTime() }
    }
}
