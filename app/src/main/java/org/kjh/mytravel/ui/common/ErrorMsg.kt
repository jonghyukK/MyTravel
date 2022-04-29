package org.kjh.mytravel.ui.common

import org.kjh.mytravel.R

/**
 * MyTravel
 * Class: ErrorMsg
 * Created by jonghyukkang on 2022/04/28.
 *
 * Description:
 */

// todo : Error Case Toast 임시 처리를 위한 부분. 추후 각 에러별 화면 처리 및 핸들링 필요.

enum class ErrorMsg(val res: Int) {
    ERROR_MY_PROFILE(R.string.error_profile_api),
    ERROR_UPDATE_BOOKMARK(R.string.error_bookmark_add_remove_api),
    ERROR_MY_PROFILE_UPDATE(R.string.error_profile_edit_api),
    ERROR_BANNER_API(R.string.error_home_banner_api),
    ERROR_PLACE_RANKING_API(R.string.error_home_ranking_api),
    ERROR_UPLOAD_INPUT_CHECK(R.string.error_check_inputs),
    ERROR_UPLOAD_POST_FAIL(R.string.error_upload_fail),
    ERROR_MAP_SEARCH_FAIL(R.string.error_map_search_fail),
    ERROR_PLACE_BY_CITYNAME_FAIL(R.string.error_whole_place_by_city_name_api)
}