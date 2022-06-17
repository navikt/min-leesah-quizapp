package no.nav

import no.nav.quizrapid.*
import no.nav.rapid.Assessment
import no.nav.rapid.Question


/**
 * QuizApplication
 *
 * Her skal teamet bygge ut funksjonalitet for å løse oppgavene i leesah-game.
 */
class QuizApplication(teamName: String): QuizParticipant(teamName) {

    override fun handle(question: Question) {
        logger.log(question)
    }

    override fun handle(assessment: Assessment) {
        logger.log(assessment)
    }
}