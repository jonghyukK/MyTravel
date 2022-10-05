package org.kjh.mytravel.utils

import android.content.Context
import android.widget.Toast
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.template.model.Button
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link
import org.kjh.mytravel.R
import org.kjh.mytravel.model.DayLog

/**
 * MyTravel
 * Class: KakaoLinkUtils
 * Created by jonghyukkang on 2022/08/10.
 *
 * Description:
 */

object KakaoLinkUtils {
    private const val KEY_PLACE_NAME = "placeName"

    fun sendDayLogKakaoLink(
        ctx: Context,
        placeName: String,
        content  : String,
        imageUrl : String
    ) {
        // when DisAvailable KaKao Sharing.
        if (isNotAvailableKakaoSharing(ctx)) {
            Toast.makeText(
                ctx,
                ctx.getString(R.string.error_not_installed_kakao),
                Toast.LENGTH_SHORT
            ).show()
            // todo : Go To PlayStore with KaKaoTalk.
            return
        }

        val defaultFeed = makeFeedTemplate(
            placeName = placeName,
            content = content,
            imageUrl = imageUrl,
            btnText = ctx.getString(R.string.kakao_feed_btn_text)
        )

        ShareClient.instance.shareDefault(ctx, defaultFeed) { result, error ->
            if (error != null) {
                Toast.makeText(
                    ctx,
                    ctx.getString(R.string.error_failed_share_kakao_link),
                    Toast.LENGTH_SHORT
                ).show()
            } else if (result != null) {
                ctx.startActivity(result.intent)
            }
        }
    }

    private fun isNotAvailableKakaoSharing(ctx: Context) =
        !ShareClient.instance.isKakaoTalkSharingAvailable(ctx)

    private fun makeFeedTemplate(
        placeName: String,
        content: String,
        imageUrl: String,
        btnText: String
    ): FeedTemplate {
        val linkParams = mapOf(KEY_PLACE_NAME to placeName)

        return FeedTemplate(
            content = Content(
                title = placeName,
                description = content,
                imageUrl = imageUrl,
                link = Link(androidExecutionParams = linkParams)
            ),
            buttons = listOf(
                Button(
                    btnText,
                    Link(androidExecutionParams = linkParams)
                )
            )
        )
    }
}