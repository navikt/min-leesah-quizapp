package no.nav

import no.nav.quizrapid.*
import no.nav.rapid.Assessment
import no.nav.rapid.Question


/**
 * QuizApplication
 *
 * Her skal teamet bygge ut funksjonalitet for å løse oppgavene i leesah-game.
 */
class QuizApplication(private val teamName: String): QuizParticipant(teamName) {

    override fun handle(question: Question) {
        logger.log(question)
        if(question.category == "team-registration") handleRegisterTeam(question)
        if(question.category == "ping-pong") handlePingPong(question)
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

    override fun handle(assessment: Assessment) {
        logger.log(assessment)
    }
}