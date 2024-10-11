package org.cyk.ktearth.application.uact

import org.cyk.ktearth.application.ApplicationHandler
import org.springframework.stereotype.Component
import java.time.LocalDateTime

data class ViewPostCmd (
    val targetId: String,
    val postId: String,
)

@Component
class ViewPostHandler: ApplicationHandler<ViewPostCmd, Unit> {

    override fun handler(input: ViewPostCmd) {
    }

}