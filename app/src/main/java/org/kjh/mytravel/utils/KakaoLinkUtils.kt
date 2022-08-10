package org.kjh.mytravel.utils

import android.content.Context
import android.widget.Toast
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.template.model.Button
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link
import org.kjh.mytravel.R
import org.kjh.mytravel.model.Post

/**
 * MyTravel
 * Class: KakaoLinkUtils
 * Created by jonghyukkang on 2022/08/10.
 *
 * Description:
 */

object KakaoLinkUtils {

    fun sendDayLogKakaoLink(ctx: Context, dayLogItem: Post) {
        // when DisAvailable KaKao Sharing.
        if (!ShareClient.instance.isKakaoTalkSharingAvailable(ctx)) {
            Toast.makeText(ctx, ctx.getString(R.string.error_not_installed_kakao), Toast.LENGTH_SHORT).show()
            // todo : Go To PlayStore with KaKaoTalk.
            return
        }

        val linkParams = mapOf("placeName" to dayLogItem.placeName)

        val defaultFeed = FeedTemplate(
            content = Content(
                title       = dayLogItem.placeName,
                description = dayLogItem.content,
                imageUrl    = dayLogItem.imageUrl[0],
                link        = Link(androidExecutionParams = linkParams)
            ),
            buttons = listOf(
                Button(
                    ctx.getString(R.string.kakao_feed_btn_text),
                    Link(androidExecutionParams = linkParams)
                )
            )
        )

        ShareClient.instance.shareDefault(ctx, defaultFeed) { result, error ->
            if (error != null) {
                Toast.makeText(ctx, ctx.getString(R.string.error_failed_share_kakao_link), Toast.LENGTH_SHORT).show()
            } else if (result != null) {
                ctx.startActivity(result.intent)
            }
        }
    }
}