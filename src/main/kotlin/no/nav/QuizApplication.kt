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
        if(question.category == "make-grafana-board") handleMakeGrafana(question)
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


    override fun handle(assessment: Assessment) {
        logger.log(assessment)
    }
}