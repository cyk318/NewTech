package org.cyk.ktearth.domain.article.domain

import java.util.Date

data class ArticleStat (
    val articleId: String,
    var likeCnt: Long = 0,
    var viewCnt: Long = 0,
    var collectCnt: Long = 0,
    val commentCnt: Long = 0,
    val cTime: Date = Date(),
    var uTime: Date = Date(),
) {

    fun incrViewCnt() {
        this.viewCnt++
        this.uTime = Date()
    }

    fun incrLikeCnt() {
        this.likeCnt++
        this.uTime = Date()
    }

    fun decrLikeCnt() {
        this.likeCnt--
        this.uTime = Date()
    }

    fun incrCommentCnt() {
        this.likeCnt++
        this.uTime = Date()
    }

    fun decrCommentCnt() {
        this.likeCnt--
        this.uTime = Date()
    }

    fun incrCollectCnt() {
        this.collectCnt++
        this.uTime = Date()
    }

    fun decrCollectCnt() {
        this.collectCnt--
        this.uTime = Date()
    }

}
