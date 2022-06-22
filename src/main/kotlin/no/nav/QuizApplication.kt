package no.nav

import no.nav.db.Database
import no.nav.quizrapid.*
import no.nav.rapid.Assessment
import no.nav.rapid.Question


/**
 * QuizApplication
 *
 * Her skal teamet bygge ut funksjonalitet for å løse oppgavene i leesah-game.
 */
class QuizApplication(private val teamName: String, private val database: Database): QuizParticipant(teamName) {

    override fun handle(question: Question) {
        logger.log(question)
        if(question.category == "team-registration") handleRegisterTeam(question)
        if(question.category == "ping-pong") handlePingPong(question)
        if(question.category == "make-grafana-board") handleMakeGrafana(question)
        if(question.category == "make-ingress") handleMakeIngress(question)
        if(question.category == "make-alert") handleMakeAlert(question)
        if(question.category == "deduplication") handleDeduplication(question)
    }



    /**
     * Question handlers
     */

    private fun handleRegisterTeam(question: Question) {
        answer(question.category, question.id(), teamName)
    }

    private fun handlePingPong(question: Question) {
        answer(question.category, question.id(), "pong")
    }

    private fun handleMakeGrafana(question: Question) {
        answer(question.category, question.id(), "https://grafana.nais.io/goto/G7YhSv3nz?orgId=1")
    }
    private fun handleMakeIngress(question: Question) {
        answer(question.category, question.id(), "https://min-leesah-quizapp.dev.intern.nav.no")
    }
    private fun handleMakeAlert(question: Question) {
        answer(question.category, question.id(), "https://nav-it.slack.com/archives/C03JJ2KLZJ5/p1655931106101389")
    }

    private fun handleDeduplication(question: Question) {
        if(!database.flag()) answer(question.category, question.id(), "you wont dupe me!").also { database.settFlag() }
    }

    override fun handle(assessment: Assessment) {
        logger.log(assessment)
    }
}